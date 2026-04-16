package org.idempiere.seniat.validator.callout;

import org.adempiere.base.annotation.Callout;
import org.compiere.model.X_C_Invoice;

import org.idempiere.seniat.validator.base.CustomCallout;

@Callout(tableName = X_C_Invoice.Table_Name, columnName = "LVE_POInvoiceNo")
public class FTUInvoiceCallout extends CustomCallout{


	@Override
	protected String start() {
		
		boolean isSoTrx = "Y".equals(getTab().getValue(X_C_Invoice.COLUMNNAME_IsSOTrx));
		
		if (isSoTrx)
			return null;
		
		if ("LVE_POInvoiceNo".equals(getColumnName()))
			setValue(X_C_Invoice.COLUMNNAME_DocumentNo, getValue());
		
		return null;
	}

}
