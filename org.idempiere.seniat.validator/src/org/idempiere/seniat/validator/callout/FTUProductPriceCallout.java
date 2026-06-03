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
		Object plvObj = getTab().getValue("M_PriceList_Version_ID");
		if (plvObj != null) {
			int plvID = (Integer) plvObj;
			if (plvID > 0) {
				MPriceListVersion plv = new MPriceListVersion(getCtx(), plvID, null);
				if (plv.get_ID() > 0) {
					MPriceList pl = new MPriceList(getCtx(), plv.getM_PriceList_ID(), null);
					if (pl.get_ID() > 0) {
						isPLSo = pl.isSOPriceList();
					}
				}
			}
		}
		if(colName.equals("PriceStd") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(oldvalue == null || value.compareTo(oldvalue) == 0)
				return null;
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio estandar no puede ser negativo!");
				getTab().setValue("PriceStd", oldvalue);
			}
			else if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio estandar debe ser mayor a cero!");
				getTab().setValue("PriceStd", oldvalue);
			}
		}
		if(colName.equals("PriceList") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(oldvalue == null || value.compareTo(oldvalue) == 0)
				return null;
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio de lista no puede ser negativo!");
				getTab().setValue("PriceList", oldvalue);
			}
			else if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio de lista debe ser mayor a cero!");
				getTab().setValue("PriceList", oldvalue);
			}
		}
		if(colName.equals("PriceLimit") && getValue()!=null) {
			BigDecimal value = (BigDecimal)getValue();
			BigDecimal oldvalue = (BigDecimal)getOldValue();
			if(oldvalue == null || value.compareTo(oldvalue) == 0)
				return null;
			if(value.signum()<0) {
				Dialog.error(getWindowNo(), "El precio limite no puede ser negativo!");
				getTab().setValue("PriceLimit", oldvalue);
			}
			else if(isPLSo && value.signum()==0) {
				Dialog.error(getWindowNo(), "El precio límite debe ser mayor a cero!");
				getTab().setValue("PriceLimit", oldvalue);
			}	
		}
		return null;
	}

}
