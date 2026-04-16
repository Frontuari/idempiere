package org.idempiere.seniat.validator.callout;

import java.math.BigDecimal;

import org.adempiere.base.annotation.Callout;
import org.adempiere.webui.window.Dialog;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.X_M_ProductPrice;

import org.idempiere.seniat.validator.base.CustomCallout;

@Callout(tableName = X_M_ProductPrice.Table_Name, columnName = {"PriceStd","PriceList","PriceLimit"})
public class FTUProductPriceCallout extends CustomCallout {

	@Override
	protected String start() {
		String colName = getColumnName();
		boolean isPLSo = false;
		MPriceListVersion plv = new MPriceListVersion(getCtx(), (Integer)getTab().getValue("M_PriceList_Version_ID"),null);
		if(plv != null) {
			MPriceList pl = new MPriceList(getCtx(), plv.getM_PriceList_ID(), null);
			if(pl != null)
				isPLSo = pl.isSOPriceList();
		}
		if(colName.equals("PriceStd") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio estandar no puede ser negativo!");
				getTab().setValue("PriceStd", oldvalue);
			}
			if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio estandar debe ser mayor a cero!");
				getTab().setValue("PriceStd", oldvalue);
			}
		}
		if(colName.equals("PriceList") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio de lista no puede ser negativo!");
				getTab().setValue("PriceList", oldvalue);
			}
			if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio estandar debe ser mayor a cero!");
				getTab().setValue("PriceList", oldvalue);
			}
		}
		if(colName.equals("PriceLimit") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio limite no puede ser negativo!");
				getTab().setValue("PriceLimit", oldvalue);
			}
			if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio estandar debe ser mayor a cero!");
				getTab().setValue("PriceLimit", oldvalue);
			}	
		}
		return null;
	}

}
