package org.idempiere.seniat.validator.callout;

import org.adempiere.base.annotation.Callout;
import org.adempiere.webui.window.Dialog;
import org.compiere.model.X_AD_Sequence_No;

import org.idempiere.seniat.validator.base.CustomCallout;

@Callout(tableName = X_AD_Sequence_No.Table_Name, columnName = "CurrentNext")
public class FTUSequenceNoCallout extends CustomCallout {

	@Override
	protected String start() {
		String colName = getColumnName();
		if(colName.equals("CurrentNext")) {
			boolean isControlNo = getTab().getParentTab().getValueAsBoolean("IsLimitControlNo");
			if(isControlNo) {
				int value = (Integer)getValue();
				int oldValue = (Integer)getOldValue();
				if(value<0) {
					Dialog.error(getWindowNo(), "La siguiente secuencia no puede ser negativa!");
					getTab().setValue("CurrentNext", oldValue);
				}
				else if(value<oldValue) {
					Dialog.error(getWindowNo(), "La siguiente secuencia ("+value+") no puede menor a la secuencia anterior ("+oldValue+")!");
					getTab().setValue("CurrentNext", oldValue);
				}
			}
		}
		return null;
	}

}
