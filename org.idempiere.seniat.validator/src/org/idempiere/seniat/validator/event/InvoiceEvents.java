package org.idempiere.seniat.validator.event;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.doc.AfterComplete;
import org.adempiere.base.event.annotations.doc.AfterPost;
import org.adempiere.base.event.annotations.doc.BeforeComplete;
import org.adempiere.base.event.annotations.doc.BeforePrepare;
import org.adempiere.base.event.annotations.doc.BeforeReverseAccrual;
import org.adempiere.base.event.annotations.doc.BeforeReverseCorrect;
import org.adempiere.base.event.annotations.doc.BeforeVoid;
import org.adempiere.base.event.annotations.po.AfterChange;
import org.adempiere.base.event.annotations.po.BeforeChange;
import org.adempiere.base.event.annotations.po.BeforeNew;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MBroadcastMessage;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MAllocationLine;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MConversionRate;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MMailText;
import org.compiere.model.MOrg;
import org.compiere.model.MSequence;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTax;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.osgi.service.event.Event;
import java.util.Calendar;
import java.util.GregorianCalendar;

@EventTopicDelegate
@ModelEventTopic(modelClass = MInvoice.class)
public class InvoiceEvents extends ModelEventDelegate<MInvoice>{
	private static CLogger log = CLogger.getCLogger(InvoiceEvents.class);

	private static final String COLUMNNAME_IsOverwriteDocNoWhenReversing = "IsOverwriteDocNoWhenReversing";

	/**	Current Business Partner				*/
	private int m_Current_C_BPartner_ID 		= 	0;
	/**	Current Allocation						*/
	private MAllocationHdr m_Current_Alloc 		= 	null;
	
	public InvoiceEvents(MInvoice po, Event event) {
		super(po, event);
	}
	
	@BeforePrepare
	public void onBeforePrepare() {
		MInvoice invoice = getModel();
		if(invoice.isSOTrx() && !MSysConfig.getBooleanValue("AllowInvoicesPreviousPeriod", true, invoice.getAD_Client_ID(), invoice.getAD_Org_ID())) { 
			int count = DB.getSQLValue(invoice.get_TrxName(), "SELECT COUNT(*) FROM M_InOut io JOIN M_InOutLine iol ON (io.M_InOut_ID = iol.M_InOut_ID) JOIN C_OrderLine ol ON (iol.C_OrderLine_ID = ol.C_OrderLine_ID) JOIN C_Order o ON (ol.C_Order_ID = o.C_Order_ID AND o.DocStatus = 'CO') WHERE io.MovementType = 'C-' AND io.DocStatus IN ('CO','CL') AND ol.QtyDelivered > ol.QtyInvoiced and TO_CHAR(io.MovementDate::date,'YYYYMM') < TO_CHAR(?::date,'YYYYMM')", new Object[]{invoice.getDateAcct()});
			if(count>0) {
				throw new AdempiereException("No es posible procesar el documento "+invoice.getDocumentInfo()+", porque aun existen "+count+" notas entregas por facturar del periodo pasado.");
			}
		}
	}
	
	@BeforeNew
	@BeforeChange
	public void onBNC() {
		
		  MInvoice invoice = getModel();
		  MInvoice invoiceTrue = new MInvoice(null, invoice.getC_Invoice_ID(), invoice.get_TrxName());
		    
		    // Obtenemos el día del mes de la factura.
		    Calendar cal = new GregorianCalendar();
		    cal.setTime(invoice.getDateInvoiced());
		    int invoiceDay = cal.get(Calendar.DAY_OF_MONTH);

		    // Si la factura se está creando en el periodo "Q2" (día 16 o posterior)...
		    if (MSysConfig.getBooleanValue("CONFIGURATION_SENIAT", true, invoice.getAD_Client_ID()) && invoiceDay > 15) {
		        // ...buscamos solo las entregas pendientes del periodo "Q1" (día 15 o anterior).
		        // La vista ftu_rv_dni ya filtra por entregas no facturadas.
		        String sql = "SELECT COUNT(*) FROM ftu_rv_dni WHERE EXTRACT(DAY FROM movementdate) <= 15 AND AD_Org_ID = "+invoice.getAD_Org_ID();
		        int pendingDeliveriesCount = DB.getSQLValue(null, sql);
		        String doctype =invoiceTrue.getDocBaseType(); 
		        // Si encontramos entregas del periodo "Q1"...
		        if (doctype != null && doctype.equalsIgnoreCase("ARI") && pendingDeliveriesCount > 0 && !invoice.get_ValueAsBoolean("IsFiscalWarningAccepted") && !(invoice.getDocStatus().equalsIgnoreCase(X_C_Invoice.DOCSTATUS_Completed) || invoice.getDocStatus().equalsIgnoreCase(X_C_Invoice.DOCSTATUS_Closed))) {
		            String warningMessage = "ADVERTENCIA FISCAL - SENIAT\n\n"
		                                  + "Se ha detectado una posible irregularidad en el cierre contable.\n"
		                                  + "Existen Ordenes de Entrega del periodo anterior no facturadas.\n"
		                                  + "Esto podría constituir una evasión fiscal conforme a la normativa vigente.\n\n"
		                                  + "Para anular esta advertencia, por favor, marque el campo 'Acepta Facturar con Entregas Pendientes?' y vuelva a intentar.";
		            throw new AdempiereException(warningMessage);
		        }
		    }
		
	}
	
	@BeforeComplete
	public void onBeforeComplete() {
		MInvoice invoice = getModel();
		MDocType docType = (MDocType) invoice.getC_DocType();
		String msgExistCN = Msg.translate(Env.getCtx(), "AlreadyExists") + ": " + Msg.getElement(Env.getCtx(), "LVE_ControlNumber");
		String msgMandataryCN = Msg.translate(Env.getCtx(), "FillMandatory") + ": " + Msg.getElement(Env.getCtx(), "LVE_ControlNumber");
		String msgMandataryINo = Msg.translate(Env.getCtx(), "FillMandatory") + ": " + Msg.getElement(Env.getCtx(), "LVE_POInvoiceNo");
		String msgSeqNotFound = Msg.translate(Env.getCtx(), "SequenceDocNotFound") + " " + Msg.getElement(Env.getCtx(), "LVE_ControlNoSequence_ID");
		
		String where = "AD_Org_ID=? AND C_BPartner_ID=? AND C_Invoice_ID!=? AND IsSOTrx=? AND DocStatus IN ('CO','CL') ";

		if (invoice.getReversal_ID() == 0)
		{
			if(docType.get_ValueAsBoolean("IsControlNoDocument")) {
				if (invoice.isSOTrx()) {
					String controlSequence = null;
					if (invoice.get_Value("LVE_ControlNumber") == null) {
						if (docType.get_Value("LVE_ControlNoSequence_ID") == null) {
							throw new AdempiereException(msgSeqNotFound);
						}
						
						int controlNoSequence_ID = docType.get_ValueAsInt("LVE_ControlNoSequence_ID");
						MSequence seq = new MSequence(Env.getCtx(), controlNoSequence_ID, invoice.get_TrxName());
						controlSequence = MSequence.getDocumentNoFromSeq(seq, invoice.get_TrxName(), invoice);

						Query query = new Query(Env.getCtx(), MInvoice.Table_Name, where + "AND LVE_ControlNumber=?", invoice.get_TrxName());

						while (query.setParameters(invoice.getAD_Org_ID(), invoice.getC_BPartner_ID(), invoice.get_ID(), invoice.isSOTrx(), controlSequence).count() > 0) {
							seq.setCurrentNext(seq.getCurrentNext() + 1);
							seq.saveEx();
							controlSequence = MSequence.getDocumentNoFromSeq(seq, invoice.get_TrxName(), invoice);
						}
						//	Added by Jorge Colmenarez, 2024-12-18 11:20
						//	Support for limit control number sequence ticket #130 GSS
						if(seq.get_ValueAsBoolean("IsLimitControlNo")) {
							int currentNext = getSequence(seq, invoice.getAD_Org_ID())-1;
							int qtytonotified = getQtyBeforeNotified(seq, invoice.getAD_Org_ID());
							int limitseqno = getLimitSequence(seq, invoice.getAD_Org_ID());
							if(currentNext>limitseqno)
								throw new AdempiereException("Debe cambiar el correlativo de control asignado ya alcanzo el máximo correlativo="+limitseqno);
							if(currentNext>=(limitseqno-qtytonotified)) {
								Trx trx = null;
								String trxName = null;
								//	try run process
								try {
									trxName = Trx.createTrxName("showWarningControlNo");
									trx = Trx.get(trxName, true);
									trx.setDisplayName(InvoiceEvents.class.getName()+"_showWarning");
									MBroadcastMessage bm = new MBroadcastMessage(Env.getCtx(), 0, trxName);
									bm.setTitle("Aviso de Correlativo de Control");
									bm.setBroadcastMessage("Ya se estan agotando los correlativos de control disponibles, queda "+qtytonotified+" o menos disponibles para alcanzar el correlativo limite "+limitseqno+" correlativo a asignar "+currentNext);
									bm.setBroadcastType(MBroadcastMessage.BROADCASTTYPE_Immediate);
									bm.setTarget(MBroadcastMessage.TARGET_User);
									bm.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
									bm.setBroadcastFrequency(MBroadcastMessage.BROADCASTFREQUENCY_JustOnce);
									bm.saveEx(trxName);
									ProcessInfo pi = new ProcessInfo("Aviso de Control", 200019, bm.get_Table_ID(), bm.get_ID()); // Process AD_PublishBroadcastMessage from core
									pi.setAD_User_ID(Env.getAD_User_ID(Env.getCtx()));
									pi.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
									ServerProcessCtl.process(pi, trx, true);
								} catch (AdempiereException e){
						    		if (trx != null)
						    			trx.rollback();
						    		throw e;
						    	}
						    	finally{
						    		if (trx != null)
						    			trx.close();
						    	}
							}
						}
						//	End Jorge Colmenarez
						invoice.set_ValueOfColumn("LVE_ControlNumber", controlSequence);
						invoice.saveEx();
					} else {
						boolean existCN = new Query(Env.getCtx(), MInvoice.Table_Name, where + "AND LVE_ControlNumber=?", invoice.get_TrxName()).setParameters(invoice.getAD_Org_ID(), invoice.getC_BPartner_ID(), invoice.get_ID(), invoice.isSOTrx(), invoice.get_Value("LVE_ControlNumber")).count() > 0;
						if (existCN) {
							throw new AdempiereException(msgExistCN);
						}
					}
				} else {
					if (invoice.get_Value("LVE_ControlNumber") == null) {
						throw new AdempiereException(msgMandataryCN);
					} else if (invoice.get_Value("LVE_POInvoiceNo") == null) {
						throw new AdempiereException(msgMandataryINo);
					} else {
						boolean existCN = new Query(Env.getCtx(), MInvoice.Table_Name, where + "AND LVE_ControlNumber=? AND LVE_POInvoiceNo=?", invoice.get_TrxName()).setParameters(invoice.getAD_Org_ID(), invoice.getC_BPartner_ID(), invoice.get_ID(), invoice.isSOTrx(), invoice.get_Value("LVE_ControlNumber"), invoice.get_Value("LVE_POInvoiceNo")).count() > 0;
						if (existCN) {
							throw new AdempiereException(msgExistCN);
						}
					}
				}
			}
		}
	}
	
	@AfterComplete
	public void onAfterComplete() {
		MInvoice invoice = getModel();
		MDocType docType = (MDocType) invoice.getC_DocType();

		if(!invoice.isSOTrx() && !(invoice.getDocumentNo().equals(invoice.get_ValueAsString("LVE_POInvoiceNo"))) 
				&& !invoice.get_ValueAsString("LVE_POInvoiceNo").equalsIgnoreCase(""))
		{
			String reverse = (invoice.isReversal() ? "^" : "");
			if(invoice.isReversal())
			{
				invoice.set_ValueOfColumn("LVE_POInvoiceNo",invoice.get_ValueAsString("LVE_POInvoiceNo")+reverse);	
			}
			
			if (docType.get_ValueAsBoolean(COLUMNNAME_IsOverwriteDocNoWhenReversing)
					&& invoice.getReversal_ID() > 0)
				invoice.setDocumentNo(String.valueOf(invoice.get_ID()));
			else
				invoice.setDocumentNo(invoice.get_ValueAsString("LVE_POInvoiceNo"));
			
			invoice.saveEx(invoice.get_TrxName());
		}
		
		if(MSysConfig.getBooleanValue("CONFIGURATION_SENIAT", true, invoice.getAD_Client_ID()) && invoice.isSOTrx() && invoice.get_ValueAsBoolean("IsFiscalWarningAccepted")) {
			try	{
				MClient m_client;
				//
				int mailTextID = MSysConfig.getIntValue("TEMPLATE_MAIL_SENIAT", 0, invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
				String message = "<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0;\">\n"
						+ "  <div style=\"max-width: 650px; margin: 10px auto; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.05);\">\n"
						+ "    \n"
						+ "    <div style=\"background-color: #2c3e50; color: #ffffff; padding: 20px; text-align: center;\">\n"
						+ "      <h2 style=\"margin: 0; font-size: 20px; text-transform: uppercase;\">Reporte de Irregularidad Fiscal</h2>\n"
						+ "      <p style=\"margin: 5px 0 0 0; font-size: 12px; opacity: 0.8;\">AUDITORÍA AUTOMÁTICA @AD_Client_ID@</p>\n"
						+ "    </div>\n"
						+ "\n"
						+ "    <div style=\"padding: 30px; background-color: #ffffff;\">\n"
						+ "      <p><strong>Señores SENIAT - División de Fiscalización</strong></p>\n"
						+ "      <p>El sistema ERP ha registrado la emisión de una factura saltando las validaciones de notas de entrega pendientes (Evasión de Control de Inventario).</p>\n"
						+ "\n"
						+ "      <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px; font-size: 14px;\">\n"
						+ "        <tr style=\"background-color: #f9f9f9;\">\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold; width: 140px;\">Empresa Emisora:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee;\">@AD_Client_ID@</td>\n"
						+ "        </tr>\n"
						+ "        <tr>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold;\">Nro. Factura:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; color: #000;\">@DocumentNo@</td>\n"
						+ "        </tr>\n"
						+ "        <tr>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold;\">Fecha Emisión:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee;\">@DateInvoiced@</td>\n"
						+ "        </tr>\n"
						+ "        <tr>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold;\">Cliente / Razón Social:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee;\">@C_BPartner_ID@</td>\n"
						+ "        </tr>\n"
						+ "        <tr>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold;\">RIF / ID Fiscal:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee;\">@TaxID@</td>\n"
						+ "        </tr>\n"
						+ "         <tr>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee; font-weight: bold;\">Monto Total:</td>\n"
						+ "          <td style=\"padding: 8px; border-bottom: 1px solid #eee;\">@GrandTotal@</td>\n"
						+ "        </tr>\n"
						+ "      </table>\n"
						+ "\n"
						+ "      <div style=\"background-color: #fff5f5; border-left: 5px solid #dc3545; padding: 15px; margin: 20px 0; border-radius: 4px;\">\n"
						+ "        <span style=\"color: #dc3545; font-weight: bold; display: block; text-transform: uppercase; font-size: 12px;\">⚠️ Control de Seguridad Vulnerado:</span>\n"
						+ "        <div style=\"font-style: italic; color: #444; font-size: 13px; margin-top: 5px;\">\n"
						+ "          El usuario <strong>@UpdatedBy@</strong> procedió a facturar ignorando la advertencia: \"Existen notas de entrega del periodo anterior no facturadas\".\n"
						+ "        </div>\n"
						+ "      </div>\n"
						+ "\n"
						+ "      <p style=\"font-size: 12px; color: #666;\">\n"
						+ "        * Este reporte se genera automáticamente al detectar la activación del flag de excepción en el documento de venta.\n"
						+ "      </p>\n"
						+ "    </div>\n"
						+ "\n"
						+ "    <div style=\"background-color: #f8f9fa; padding: 15px; text-align: center; font-size: 12px; color: #777; border-top: 1px solid #e0e0e0;\">\n"
						+ "      <p><strong>Sistema de Gestión @AD_Org_ID@</strong><br>\n"
						+ "      Traza de Auditoría Interna</p>\n"
						+ "    </div>\n"
						+ "  </div>\n"
						+ "</body>\n"
						+ "</html>";
				String subject = "ALERTA SENIAT: Irregularidad en Factura @DocumentNo@ - @DateInvoiced@";
				String seniatEmail = MSysConfig.getValue("EMAIL_TO_SENIAT", "oficinadatacomm@gmail.com", invoice.getAD_Client_ID(), invoice.getAD_Org_ID());
				message = parseFiscalAlert(message,invoice);
				//	Client Info
				m_client = MClient.get (invoice.getCtx());
				//
				EMail email = m_client.createEMail(seniatEmail, subject, message);
				log.warning("Create Email SENIAT="+email);				
				if(mailTextID>0) {
					MMailText m_MailText = new MMailText (invoice.getCtx(),mailTextID, invoice.get_TrxName());
					m_MailText.setPO(invoice);
					m_MailText.setBPartner(invoice.getC_BPartner_ID());
					m_MailText.getMailText(true);
					message = Msg.parseTranslation(invoice.getCtx(), message);
					message = parseFiscalAlert(message,invoice);
					subject = m_MailText.getMailHeader() != null ? m_MailText.getMailHeader() : "";
					if (m_MailText.isHtml()) {
						email.setMessageHTML(subject, message);
					} else {
						email.setSubject (subject);
						email.setMessageText (message);
					}
				}
				String msg = email.send();
				log.warning("Sended SENIAT Msg ="+msg);
			}catch(Exception e) {
				log.log(Level.SEVERE, "@ERROR@ @RequestActionEMailNoTo@" + e.getMessage());
			}
		}
		
		/**
		 * Automatic Allocation Between Credit/Debit Notes with DocAffected
		 * @author Jorge Colmenarez <mailto:jcolmenarez@frontuari.net>, 2020-04-30 09:34
		 */
		if(!invoice.isReversal()) {
			if (docType.get_ValueAsBoolean("IsAutoAllocation")) {
				if(!invoice.isPaid()|| invoice.getReversal_ID() == 0)
					AutomaticAllocation(invoice);
			}
		}
	}
	
	@AfterPost
	public void onAfterPost() {
		MInvoice invoice = getModel();
		MDocType docType = (MDocType) invoice.getC_DocType();
		/**
		 * Automatic Allocation Between Credit/Debit Notes with DocAffected
		 * @author Jorge Colmenarez <mailto:jcolmenarez@frontuari.net>, 2020-04-30 09:34
		 */
		if(!invoice.isReversal()) {
			if (docType.get_ValueAsBoolean("IsAutoAllocation")) {
				if(!invoice.isPaid() || invoice.getReversal_ID() == 0) {
					String sqlCountSchemas = "SELECT COUNT(*) FROM C_AcctSchema WHERE AD_Client_ID = ? AND IsActive='Y'";
				    int activeSchemaCount = DB.getSQLValue(invoice.get_TrxName(), sqlCountSchemas, invoice.getAD_Client_ID());
				    String sqlCountPosts = "SELECT COUNT(DISTINCT C_AcctSchema_ID) FROM Fact_Acct WHERE AD_Table_ID = ? AND Record_ID = ?";
				    int postCount = DB.getSQLValue(invoice.get_TrxName(), sqlCountPosts, MInvoice.Table_ID, invoice.getC_Invoice_ID());
				    if (postCount == activeSchemaCount) {
				    	String sql = "SELECT COUNT(*) FROM C_AllocationLine al "
					               + "INNER JOIN C_AllocationHdr ah ON al.C_AllocationHdr_ID = ah.C_AllocationHdr_ID "
					               + "WHERE al.C_Invoice_ID = ? AND ah.DocStatus IN ('CO','CL')";
					    int existingAllocations = DB.getSQLValue(invoice.get_TrxName(), sql, invoice.getC_Invoice_ID());
					    if (existingAllocations <= 0) {
							AutomaticAllocation(invoice); 
					    }
				    }
				}
			}
		}
	}
	
	@BeforeVoid
	@BeforeReverseCorrect
	@BeforeReverseAccrual
	public void beforeReverseOrVoid() {
		
		MInvoice invoice = getModel();
		MDocType docType = (MDocType) invoice.getC_DocType();
		
		String sql = "SELECT COUNT(*) From C_AllocationHDR allo "
				+ "JOIN C_AllocationLine allol ON allo.C_AllocationHdr_ID = allol.C_AllocationHdr_ID "
				+ "WHERE allol.C_Invoice_ID = "+invoice.getC_Invoice_ID()+" AND allo.DocStatus IN ('CO','CL')";
		int C_AllocationHrd_ID = DB.getSQLValue(invoice.get_TrxName(), sql);
		
		if(C_AllocationHrd_ID>0) {				
			
				invoice.setProcessMessage("Esta Documento tiene una asignacion activa si desea anularlo o reversarlo debe anular la asignacion ");
				throw new AdempiereException(invoice.getProcessMsg());
			}
		
		//Added By Argenis Rodríguez for OverWrite DocumentNo When Complete
		docType = docType.get_ID() == 0 ? new MDocType(invoice.getCtx(), invoice.getC_DocTypeTarget_ID(), invoice.get_TrxName())
				: docType;
		boolean isOverWrite = docType.get_ValueAsBoolean(COLUMNNAME_IsOverwriteDocNoWhenReversing);
		
		if (isOverWrite)
		{
			String docNo = String.valueOf(invoice.get_ID());
			invoice.setDocumentNo(docNo);
			invoice.saveEx();
		}
		//End By Argenis Rodríguez
	}
		
	@AfterChange
	public void onAfterChange() {
		MInvoice invoice = getModel();
		if(invoice.get_ValueAsInt("LVE_invoiceAffected_ID") > 0)
		{
			DB.executeUpdate("UPDATE C_InvoiceLine SET LVE_invoiceAffected_ID = ? WHERE C_Invoice_ID = ?", new Object[] {invoice.get_ValueAsInt("LVE_invoiceAffected_ID"),invoice.get_ID()},true,invoice.get_TrxName());
		}
	}
		
	
	/**
	 * Automatic Allocation between Credit/Debit Notes and Document Affected
	 * @author Jorge Colmenarez <mailto:jcolmenarez@frontuari.net>, 2020-04-30 09:30
     * @author Argenis Rodríguez
	 * @param m_Invoice
	 */
	private void AutomaticAllocation(MInvoice m_Invoice)
	{
		StringBuffer whereClause = new StringBuffer();
		StringBuffer whereParam = new StringBuffer();
		List<Object> parameters	= new ArrayList<Object>();
		MInvoiceLine[] list  = null;
		m_Current_Alloc = null;
			
		whereClause.append(" AND LVE_invoiceAffected_ID IS NOT NULL");
		BigDecimal m_InvoiceApplyAmt = Env.ZERO;
		
		whereParam.append("C_Invoice_ID=? ");
		parameters.add(m_Invoice.get_ID());
		
		list = getInvoiceLines(m_Invoice,whereClause.toString());
		BigDecimal invoiceAffectedNewOpenAmt = Env.ZERO;
		BigDecimal lineAmount = Env.ZERO;
		for (MInvoiceLine mInvoiceLine : list) {
			int invoiceID = mInvoiceLine.get_ValueAsInt("LVE_invoiceAffected_ID");
			MInvoice invoiceAffected = MInvoice.get(m_Invoice.getCtx(), invoiceID); 
			//	Added by Jorge Colmenarez, 2021-08-31 13:51
			//	Calculate TotalLine
			lineAmount = getTotalLine(mInvoiceLine);
			
			BigDecimal invoiceAffectedOpenAmt = getOpenAmt(invoiceAffected);
			
			//	Support for convertion on CreditNote currency
			invoiceAffectedOpenAmt = MConversionRate.convert(m_Invoice.getCtx(), invoiceAffectedOpenAmt, invoiceAffected.getC_Currency_ID(), 
					m_Invoice.getC_Currency_ID(), invoiceAffected.getDateInvoiced(), m_Invoice.getC_ConversionType_ID(),m_Invoice.getAD_Client_ID(), m_Invoice.getAD_Org_ID());
			//	End Jorge Colmenarez
			if(m_Invoice.isCreditMemo() && lineAmount.compareTo(invoiceAffectedOpenAmt) > 0)
				lineAmount = invoiceAffectedOpenAmt;
			
			//	Credit Notes
			if(m_Invoice.isCreditMemo())
				invoiceAffectedNewOpenAmt =  invoiceAffectedOpenAmt.subtract(lineAmount);
			else	//	Debit Notes
				invoiceAffectedNewOpenAmt =  invoiceAffectedOpenAmt.add(lineAmount);
			
			
			if(invoiceAffectedNewOpenAmt.compareTo(Env.ZERO) < 0)
				continue;
			
			addAllocation(m_Invoice.getC_BPartner_ID(), invoiceAffectedNewOpenAmt, lineAmount, m_Invoice, invoiceAffected.getC_Invoice_ID());
			m_InvoiceApplyAmt = m_InvoiceApplyAmt.add(lineAmount);
		}
		//	Complete Allocation
		try {
			completeAllocation(m_Invoice, m_InvoiceApplyAmt);
			if (m_Invoice.testAllocation(true))
				m_Invoice.saveEx();
		} catch (Exception e) {
			log.warning(e.getMessage());
			m_Current_Alloc.deleteEx(true);
		} finally {
			whereClause = new StringBuffer();
			whereParam = new StringBuffer();
			parameters	= new ArrayList<Object>();;
			list  = null;
			m_Current_Alloc = null;
		}
	}
	
	/**
	 * Complete Allocation
	 * @author <a href="mailto:dixon.22martinez@gmail.com">Dixon Martinez</a> 10/12/2014, 17:23:23
     * @author Argenis Rodríguez
	 * @param m_Invoice 
	 * @param m_AmtAllocated 
	 * @return void
	 */
	private void completeAllocation(MInvoice m_Invoice, BigDecimal m_PayAmt) throws Exception{
		if(m_Current_Alloc != null){
			if(m_Current_Alloc.getDocStatus().equals(DocumentEngine.STATUS_Drafted)){
				BigDecimal newOpenAmt = m_Invoice.getOpenAmt().abs();
				
				MAllocationLine aLine = null;
				if(!m_Invoice.isCreditMemo()) {
					newOpenAmt = newOpenAmt.subtract(m_PayAmt);
					if(m_Invoice.isSOTrx())
						aLine = new MAllocationLine (m_Current_Alloc, m_PayAmt, Env.ZERO, Env.ZERO, newOpenAmt);
					else
						aLine = new MAllocationLine (m_Current_Alloc, m_PayAmt.negate(), Env.ZERO, Env.ZERO, newOpenAmt.negate());
				}  else {
					newOpenAmt = newOpenAmt.subtract(m_PayAmt);
					if(m_Invoice.isSOTrx())
						aLine = new MAllocationLine (m_Current_Alloc, m_PayAmt.negate(),Env.ZERO, Env.ZERO, newOpenAmt.negate());
					else
						aLine = new MAllocationLine (m_Current_Alloc, m_PayAmt,Env.ZERO, Env.ZERO, newOpenAmt);
				}
				//
				aLine.setDocInfo(m_Current_C_BPartner_ID, 0, m_Invoice.getC_Invoice_ID());
				aLine.saveEx();
				//	
				if(m_Current_Alloc.getDocStatus().equals(DocumentEngine.STATUS_Drafted)){
					log.fine("Current Allocation = " + m_Current_Alloc.getDocumentNo());
					m_Current_Alloc.setDocAction(DocumentEngine.ACTION_Complete);
					m_Current_Alloc.processIt(DocumentEngine.ACTION_Complete);
					m_Current_Alloc.saveEx();			
				}	
			}
			m_Current_Alloc = null;
			m_Current_C_BPartner_ID = -1;
		}
	}
		

	/**
	 * Add Document Allocation
	 * @author <a href="mailto:dixon.22martinez@gmail.com">Dixon Martinez</a> 10/12/2014, 17:23:45
	 * @author Argenis Rodríguez
	 * @param p_C_BPartner_ID
	 * @param amtDocument
	 * @param openAmt
	 * @param m_Invoice
	 * @param p_C_Invoice_ID
	 * @return void
	 */
	private void addAllocation(int p_C_BPartner_ID, BigDecimal openAmt,
			BigDecimal payAmt, MInvoice m_Invoice, int p_C_Invoice_ID) {
		if(m_Current_C_BPartner_ID != p_C_BPartner_ID){
			
			MDocType dtAll = (MDocType) m_Invoice.getC_DocType();
			
			m_Current_Alloc = new MAllocationHdr(Env.getCtx(), false,	//	automatic
					m_Invoice.getDateInvoiced(), m_Invoice.getC_Currency_ID(), Env.getContext(Env.getCtx(), "#AD_User_Name")+" "+Msg.translate(Env.getAD_Language(Env.getCtx()), "IsAutoAllocation"), m_Invoice.get_TrxName());
			m_Current_Alloc.setDateAcct(m_Invoice.getDateAcct());
			m_Current_Alloc.setAD_Org_ID(m_Invoice.getAD_Org_ID());
			m_Current_Alloc.setC_DocType_ID(dtAll.get_ValueAsInt("C_DocTypeAllocation_ID"));
			m_Current_Alloc.saveEx();
		}
		//	
		
		MInvoice invoiceAffected = new MInvoice(m_Invoice.getCtx(), p_C_Invoice_ID, m_Invoice.get_TrxName());
		
		/*if(m_Invoice.isCreditMemo())
			openAmt = openAmt.negate();*/
		
		MAllocationLine aLine;
		if(invoiceAffected.isSOTrx())
		{
			if(!m_Invoice.isCreditMemo())
				payAmt = payAmt.negate();
			aLine = new MAllocationLine (m_Current_Alloc, payAmt, 
					Env.ZERO, Env.ZERO, openAmt);
		}
		else
		{
			if(m_Invoice.isCreditMemo())
				payAmt = payAmt.negate();
			aLine = new MAllocationLine (m_Current_Alloc, payAmt, 
					Env.ZERO, Env.ZERO, openAmt.negate());
		}
		if(aLine != null)
		{
			aLine.setDocInfo(p_C_BPartner_ID, 0, p_C_Invoice_ID);
			aLine.saveEx();
		}
		//
		m_Current_C_BPartner_ID = p_C_BPartner_ID;
	}
		
	/**
	 * 	Get Invoice Lines of Invoice
	 * 	@param whereClause starting with AND
	 * 	@return lines
	 */
	public MInvoiceLine[] getInvoiceLines (MInvoice mInvoice, String whereClause)
	{
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MInvoiceLine> list = new Query(Env.getCtx(), I_C_InvoiceLine.Table_Name, whereClauseFinal, mInvoice.get_TrxName())
										.setParameters(mInvoice.getC_Invoice_ID())
										.setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line)
										.list();
		return list.toArray(new MInvoiceLine[list.size()]);
	}	//	getLines
		
	/**
	 * Get Total Line Amount
	 * @param MInvoiceLine
	 * @return totalLine
	 */
	public BigDecimal getTotalLine(MInvoiceLine il)
	{
		BigDecimal totalLine = Env.ZERO;
		
		if(!il.getC_Invoice().isSOTrx())
			totalLine = il.getLineTotalAmt();
		else
		{
			MInvoice i = new MInvoice(il.getCtx(), il.getC_Invoice_ID(), il.get_TrxName());
			BigDecimal netLine = il.getLineNetAmt();
			MTax t = (MTax) il.getC_Tax();
			totalLine = t.calculateTax(netLine, true, i.getC_Currency().getStdPrecision());
			totalLine = netLine.add(totalLine);
		}
		
		return totalLine;
	}
	
	/**
	 * Get OpenAmt from invoice
	 * @author Jorge Colmenarez <mailto:jcolmenarez@frontuari.net>, 2020-04-30 09:20
     * @author Argenis Rodríguez
	 * @param invoice
	 * @return openamt
	 */
	public BigDecimal getOpenAmt (MInvoice invoice)
	{
		BigDecimal m_openAmt = Env.ZERO;
		
		if (invoice.isPaid())
			return Env.ZERO;
		
		m_openAmt = invoice.getGrandTotal();
		
		BigDecimal allocated = invoice.getAllocatedAmt();
		if (allocated != null)
		{
			m_openAmt = m_openAmt.subtract(allocated);
		}
		//
		if (invoice.isCreditMemo())
			return m_openAmt.negate();
		return m_openAmt;
	}	//	getOpenAmt

	/***
	 * get Sequence No for Control Number
	 * @param seq
	 * @param AD_Org_ID
	 * @return
	 */
	private int getSequence(MSequence seq, int AD_Org_ID) {
		int SeqNo = 0;
		
		if(seq.isOrgLevelSequence()) {
			SeqNo = DB.getSQLValue(seq.get_TrxName(), "SELECT CurrentNext FROM AD_Sequence_No WHERE IsActive = 'Y' AND AD_Sequence_ID = ? AND AD_Org_ID = ?", seq.get_ID(), AD_Org_ID);
		}else
			SeqNo = seq.getCurrentNext();
		
		return SeqNo;
	}

	/***
	 * get Limit Sequence for Control Number
	 * @param seq
	 * @param AD_Org_ID
	 * @return
	 */
	private int getLimitSequence(MSequence seq, int AD_Org_ID) {
		int limitSeq = 0;
		
		if(seq.isOrgLevelSequence()) {
			limitSeq = DB.getSQLValue(seq.get_TrxName(), "SELECT LimitSeqNo FROM AD_Sequence_No WHERE IsActive = 'Y' AND AD_Sequence_ID = ? AND AD_Org_ID = ?", seq.get_ID(), AD_Org_ID);
		}else
			limitSeq = seq.get_ValueAsInt("LimitSeqNo");
		
		return limitSeq;
	}

	/***
	 * get Quantity before notified Sequence for Control Number
	 * @param seq
	 * @param AD_Org_ID
	 * @return
	 */
	private int getQtyBeforeNotified(MSequence seq, int AD_Org_ID) {
		int qty = 0;
		
		if(seq.isOrgLevelSequence()) {
			qty = DB.getSQLValue(seq.get_TrxName(), "SELECT QtyBeforeNotified FROM AD_Sequence_No WHERE AD_Sequence_ID = ? AND AD_Org_ID = ?", seq.get_ID(), AD_Org_ID);
		}else
			qty = seq.get_ValueAsInt("QtyBeforeNotified");
		
		return qty;
	}
	
	/**
	 * Parsea el HTML de la alerta fiscal con datos reales de la factura.
	 * @param htmlTemplate El contenido HTML crudo de la plantilla
	 * @param invoice El objeto MInvoice que disparó la alerta
	 * @return String HTML procesado
	 */
	public String parseFiscalAlert(String htmlTemplate, MInvoice invoice) {
	    
	    if (htmlTemplate == null || invoice == null) return "";

	    String processedHtml = htmlTemplate;

	    // 1. Formateadores (Para que se vea profesional)
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

	    // 2. Obtener objetos relacionados
	    // Cliente (Para Razón Social y RIF)
	    MBPartner bp = new MBPartner(Env.getCtx(), invoice.getC_BPartner_ID(), invoice.get_TrxName());
	    // Usuario que hizo la acción (Para el "updatedBy")
	    MUser user = new MUser(Env.getCtx(), invoice.getUpdatedBy(), invoice.get_TrxName());
	    // Empresa (Client)
	    MClient client = MClient.get(Env.getCtx(), invoice.getAD_Client_ID());
	    MOrg org = new MOrg(Env.getCtx(), invoice.getAD_Org_ID(), invoice.get_TrxName());

	    // 3. Reemplazo de Variables (Usamos replace() encadenado)
	    
	    // --- @AD_Client_ID@ : Nombre de la Empresa ---
	    processedHtml = processedHtml.replace("@AD_Client_ID@", 
	        client.getName() != null ? client.getName() : "N/A");

	    // --- @DocumentNo@ : Número de Factura ---
	    processedHtml = processedHtml.replace("@DocumentNo@", 
	        invoice.getDocumentNo() != null ? invoice.getDocumentNo() : "---");

	    // --- @DateInvoiced@ : Fecha Facturación ---
	    processedHtml = processedHtml.replace("@DateInvoiced@", 
	        invoice.getDateInvoiced() != null ? dateFormat.format(invoice.getDateInvoiced()) : "");

	    // --- @C_BPartner_ID@ : Razón Social del Cliente ---
	    processedHtml = processedHtml.replace("@C_BPartner_ID@", 
	        bp.getName() != null ? bp.getName() : "Cliente Desconocido");

	    // --- @TaxID@ : RIF (Manejo especial por si es null) ---
	    // NOTA: Si usas LVE y el RIF está en otro campo (ej. LVE_TaxID), cambia bp.getTaxID()
	    String rif = bp.getTaxID(); 
	    processedHtml = processedHtml.replace("@TaxID@", 
	        rif != null ? rif : "Sin RIF");

	    // --- @GrandTotal@ : Monto Total ---
	    processedHtml = processedHtml.replace("@GrandTotal@", 
	        moneyFormat.format(invoice.getGrandTotal()) + " " + invoice.getCurrencyISO());

	    // --- @UpdatedBy@ : Usuario que se saltó la norma ---
	    processedHtml = processedHtml.replace("@UpdatedBy@", 
	        user.getName() != null ? user.getName() : "Sistema");
	    // --- @AD_Org_ID@ : Organizacion/Sucursal ---
	    processedHtml = processedHtml.replace("@AD_Org_ID@", 
	        org.getName() != null ? org.getName() : "PRINCIPAL");

	    return processedHtml;
	}
}
