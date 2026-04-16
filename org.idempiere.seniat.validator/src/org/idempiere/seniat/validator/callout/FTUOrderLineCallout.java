package org.idempiere.seniat.validator.callout;

import java.math.BigDecimal;

import org.adempiere.base.annotation.Callout;
import org.adempiere.webui.window.Dialog;
import org.compiere.model.MOrderLine;

import org.idempiere.seniat.validator.base.CustomCallout;

@Callout(tableName = MOrderLine.Table_Name, columnName = MOrderLine.COLUMNNAME_Discount)
public class FTUOrderLineCallout extends CustomCallout {

	@Override
	protected String start() {
		String column = getColumnName();
		if(column.equals("Discount") && getValue()!=null) {
			BigDecimal discount = (BigDecimal)getValue();
			if(discount.compareTo(new BigDecimal(100))>=0) {
				Dialog.error(getWindowNo(), "El descuento no puede ser mayor o igual a 100%");
				getTab().setValue("Discount", BigDecimal.ZERO);
			}
		}
		return null;
	}

}
