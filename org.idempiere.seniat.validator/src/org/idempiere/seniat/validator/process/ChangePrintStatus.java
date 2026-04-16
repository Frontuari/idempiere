package org.idempiere.seniat.validator.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MMovement;
import org.compiere.model.MTable;

import org.idempiere.seniat.validator.base.CustomProcess;

@org.adempiere.base.annotation.Process
public class ChangePrintStatus extends CustomProcess {

	private MTable t = null;
	
	@Override
	protected void prepare() {
		t = new MTable(getCtx(), getTable_ID(), get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		
		if(t.getTableName().equals("C_Invoice")) {
			MInvoice i = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
			if(i.isSOTrx() && i.getDocStatus().equalsIgnoreCase(i.DOCSTATUS_Completed)) {
				i.set_ValueOfColumn("IsPrinted", true);
				i.set_ValueOfColumn("PrintCounter", i.get_ValueAsInt("PrintCounter") + 1);
				i.saveEx();
			}
		}else if(t.getTableName().equals("M_InOut")) {
			MInOut io = new MInOut(getCtx(), getRecord_ID(), get_TrxName());
			if(io.isSOTrx() && io.getMovementType().equals("C-") && io.getDocStatus().equalsIgnoreCase(io.DOCSTATUS_Completed)) {
				io.set_ValueOfColumn("IsPrinted", true);
				io.set_ValueOfColumn("PrintCounter", io.get_ValueAsInt("PrintCounter") + 1);
				io.saveEx();
			}
		}else if(t.getTableName().equals("M_Movement")) {
			MMovement m = new MMovement(getCtx(), getRecord_ID(), get_TrxName());
			if(m.getC_DocType().isSOTrx() && m.getDocStatus().equalsIgnoreCase(m.DOCSTATUS_Completed)) {
				m.set_ValueOfColumn("IsPrinted", true);
				m.set_ValueOfColumn("PrintCounter", m.get_ValueAsInt("PrintCounter") + 1);
				m.saveEx();
			}
		}else
			throw new AdempiereException(t.getTableName()+" no implementada aun.");
		
		return null;
	}

}
