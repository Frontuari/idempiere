package org.idempiere.seniat.validator.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.base.annotation.Process;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import org.idempiere.seniat.validator.base.CustomProcess;

/**
 * Complete Invoices by Selection
 * @author Jorge Colmenarez, Frontuari, C.A <https://frontuari.net>
 * @since 21 feb. 2025
 */
@Process
public class CompleteInvoices extends CustomProcess {

	@Override
	protected void prepare() {
		// Nothing to prepare
	}

	@Override
	protected String doIt() throws Exception {
		int cnt = 0;
		
		String sql = """
				SELECT i.* 
				FROM C_Invoice i
				INNER JOIN T_Selection ts ON ts.T_Selection_ID = i.C_Invoice_ID
				WHERE ts.AD_PInstance_ID = ?
				""";

		try (PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName())) {
			pstmt.setInt(1, getAD_PInstance_ID());
			
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					String newTrxName = Trx.createTrxName("InvProc");
					Trx trx = Trx.get(newTrxName, true);
					
					if (trx == null) {
						log.severe("Fallo al crear nueva transacción para la factura.");
						continue;
					}
					
					try {
						MInvoice i = new MInvoice(getCtx(), rs, trx.getTrxName());
						i.setDocStatus(MInvoice.DOCSTATUS_Drafted);
						i.setDocAction(MInvoice.DOCACTION_Complete);
						i.saveEx();
						
						if (isSequenceLimitReached(i)) {
							addBufferLog(i.get_ID(), i.getDateAcct(), null, 
									"ERROR: Correlativos agotados. Verifique la secuencia del tipo de documento.", 
									MInvoice.Table_ID, i.get_ID());
							trx.rollback();
							continue;
						}
						
						// Process It
						if (!i.processIt(MInvoice.DOCACTION_Complete)) {
							String msg = i.getProcessMsg();
							addBufferLog(i.get_ID(), i.getDateAcct(), null,
									"Fallo al procesar " + i.getDocumentNo() + ": " + msg, 
									MInvoice.Table_ID, i.get_ID());
							trx.rollback();
						} else {
							i.saveEx();
							trx.commit();
							cnt++;
							addBufferLog(i.get_ID(), i.getDateAcct(), null, i.getDocumentInfo(), MInvoice.Table_ID, i.get_ID());
						}
					} catch (Exception e) {
						log.severe("Excepcion al procesar factura. Forzando rollback. Detalle: " + e.getMessage());
						trx.rollback();
					} finally {
						if (trx.isActive()) {
							trx.close();
						}
					}
				}
			}
		} catch (SQLException e) {
			throw new AdempiereException("Ocurrió un error al ejecutar la consulta de selección: " + e.getMessage(), e);
		}
		
		return "Documentos procesados: " + cnt;
	}
	
	private boolean isSequenceLimitReached(MInvoice invoice) {
		String sqlControlSeq = "SELECT LVE_ControlNoSequence_ID FROM C_DocType WHERE C_DocType_ID = ?";
		int controlSeqId = DB.getSQLValueEx(get_TrxName(), sqlControlSeq, invoice.getC_DocTypeTarget_ID());
		
		if (controlSeqId <= 0) {
			return false;
		}
		
		MSequence sequence = new MSequence(getCtx(), controlSeqId, get_TrxName());  
		
		String sqlSeq = """
				SELECT CurrentNext, LimitSeqNo 
				FROM AD_Sequence_No 
				WHERE AD_Sequence_ID = ? AND AD_Org_ID = ? AND IsActive = 'Y'
				""";
				
		try (PreparedStatement pstmt = DB.prepareStatement(sqlSeq, get_TrxName())) {
			pstmt.setInt(1, sequence.getAD_Sequence_ID());
			pstmt.setInt(2, invoice.getAD_Org_ID());
			
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					long currentNext = rs.getLong("CurrentNext");
					long limitSeqNo = rs.getLong("LimitSeqNo");
					if (limitSeqNo > 0 && currentNext > limitSeqNo) {
						log.warning("Límite de secuencia alcanzado para la factura: " 
								+ invoice.getDocumentNo() + ". Secuencia: " + sequence.getName());
						return true;
					}
				}
			}
		} catch (SQLException e) {
			log.severe("Error al verificar límite de secuencia: " + e.getMessage());
		}
		
		return false;
	}

}