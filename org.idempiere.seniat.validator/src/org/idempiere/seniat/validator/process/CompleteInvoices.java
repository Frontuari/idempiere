package org.idempiere.seniat.validator.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.base.annotation.Process;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;

import org.idempiere.seniat.validator.base.CustomProcess;

/***
 * Complete Invoices by Selection
 * @author Jorge Colmenarez, Frontuari, C.A <https://frontuari.net>
 * @since 21 feb. 2025
 */
@Process
public class CompleteInvoices extends CustomProcess {

	@Override
	protected void prepare() {
		
	}

	@Override
	protected String doIt() throws Exception {
		String msg = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT i.* from C_Invoice i "
				+ " JOIN (SELECT T_Selection_ID FROM T_Selection WHERE T_Selection.AD_PInstance_ID = "+ getAD_PInstance_ID()+" ) Ts "
				+ "on Ts.T_Selection_ID =  i.C_Invoice_ID";
		int cnt = 0;
		try {
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next()){
				MInvoice i = new MInvoice(getCtx(), rs, get_TrxName());
				i.setDocStatus(MInvoice.STATUS_Drafted);
				i.setDocAction(MInvoice.ACTION_Complete);
				i.saveEx();
				if(!i.processIt(MInvoice.ACTION_Complete))
					msg = i.getProcessMsg();
				else {
					i.saveEx();
					cnt++;
					addBufferLog(i.get_ID(), i.getDateAcct(), null, i.getDocumentInfo(), MInvoice.Table_ID, i.get_ID());
				}
			}
		}catch (Exception e) {
			throw new AdempiereException("Ocurrio un error al completar la factura="+e.getMessage()+" "+msg);
		}finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;	
		}
		
		return "Documentos procesados: "+cnt;
	}

}
