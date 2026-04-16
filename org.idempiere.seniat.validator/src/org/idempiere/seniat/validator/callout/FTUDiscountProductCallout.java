package org.idempiere.seniat.validator.callout;

import java.math.BigDecimal;

import org.adempiere.base.annotation.Callout;
import org.adempiere.webui.window.Dialog;

import org.idempiere.seniat.validator.base.CustomCallout;

@Callout(tableName = "M_DiscountSchemaBreak", columnName = "BreakDiscount")
public class FTUDiscountProductCallout extends CustomCallout {

	@Override
	protected String start() {
		String column = getColumnName();
		if(column.equals("BreakDiscount") && getValue()!=null) {
			BigDecimal discount = (BigDecimal)getValue();
			if(discount.compareTo(new BigDecimal(100))>=0) {
				Dialog.error(getWindowNo(), "El descuento no puede ser mayor o igual a 100%");
				getTab().setValue("BreakDiscount", 0);
			}
		}
		return null;
	}

}
