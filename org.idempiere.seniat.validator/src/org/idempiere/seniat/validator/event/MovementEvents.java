package org.idempiere.seniat.validator.event;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.doc.BeforeComplete;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MBroadcastMessage;
import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MSequence;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.osgi.service.event.Event;

@EventTopicDelegate
@ModelEventTopic(modelClass = MMovement.class)
public class MovementEvents extends ModelEventDelegate<MMovement>{

	public MovementEvents(MMovement po, Event event) {
		super(po, event);
	}

	@BeforeComplete
	public void onBeforeComplete() {
		MMovement move = getModel();
		MDocType docType = (MDocType) move.getC_DocType();
		String msgExistCN = Msg.translate(Env.getCtx(), "AlreadyExists") + ": " + Msg.getElement(Env.getCtx(), "LVE_ControlNumber");
		String msgSeqNotFound = Msg.translate(Env.getCtx(), "SequenceDocNotFound") + " " + Msg.getElement(Env.getCtx(), "LVE_ControlNoSequence_ID");

		String where = "AD_Org_ID=? AND C_BPartner_ID=? AND M_Movement_ID!=? AND DocStatus IN ('CO','CL') ";

		if (move.getReversal_ID() == 0)
		{
			if(docType.get_ValueAsBoolean("IsControlNoDocument")) {
				String controlSequence = null;
				if (move.get_Value("LVE_ControlNumber") == null) {
					if (docType.get_Value("LVE_ControlNoSequence_ID") == null) {
						throw new AdempiereException(msgSeqNotFound);
					}

					int controlNoSequence_ID = docType.get_ValueAsInt("LVE_ControlNoSequence_ID");
					MSequence seq = new MSequence(Env.getCtx(), controlNoSequence_ID, move.get_TrxName());
					controlSequence = MSequence.getDocumentNoFromSeq(seq, move.get_TrxName(), move);

					Query query = new Query(Env.getCtx(), MMovement.Table_Name, where + "AND LVE_ControlNumber=?", move.get_TrxName());

					while (query.setParameters(move.getAD_Org_ID(), move.getC_BPartner_ID(), move.get_ID(), controlSequence).count() > 0) {
						seq.setCurrentNext(seq.getCurrentNext() + 1);
						seq.saveEx();
						controlSequence = MSequence.getDocumentNoFromSeq(seq, move.get_TrxName(), move);
					}
					//	Added by Jorge Colmenarez, 2024-12-18 11:20
					//	Support for limit control number sequence ticket #130 GSS
					if(seq.get_ValueAsBoolean("IsLimitControlNo")) {
						int currentNext = getSequence(seq, move.getAD_Org_ID())-1;
						int qtytonotified = getQtyBeforeNotified(seq, move.getAD_Org_ID());
						int limitseqno = getLimitSequence(seq, move.getAD_Org_ID());
						if(currentNext>limitseqno)
							throw new AdempiereException("Debe cambiar el correlativo de control asignado ya alcanzo el máximo correlativo="+limitseqno);
						if(currentNext>=(limitseqno-qtytonotified)){
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
					move.set_ValueOfColumn("LVE_ControlNumber", controlSequence);
					move.saveEx();
				} else {
					boolean existCN = new Query(Env.getCtx(), MMovement.Table_Name, where + "AND LVE_ControlNumber=?", move.get_TrxName()).setParameters(move.getAD_Org_ID(), move.getC_BPartner_ID(), move.get_ID(), move.get_Value("LVE_ControlNumber")).count() > 0;
					if (existCN) {
						throw new AdempiereException(msgExistCN);
					}
				}
			}
		}
	}

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
}
