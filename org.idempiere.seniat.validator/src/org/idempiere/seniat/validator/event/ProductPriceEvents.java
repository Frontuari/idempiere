package org.idempiere.seniat.validator.event;

import java.math.BigDecimal;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.po.BeforeChange;
import org.adempiere.base.event.annotations.po.BeforeNew;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.osgi.service.event.Event;

@EventTopicDelegate
@ModelEventTopic(modelClass = MProductPrice.class)
public class ProductPriceEvents extends ModelEventDelegate<MProductPrice> {

	public ProductPriceEvents(MProductPrice po, Event event) {
		super(po, event);
	}
	
	@BeforeNew
	@BeforeChange
	public void onBeforeSave() {
		MProductPrice pp = getModel();
		
		boolean isPLSo = false;
		if (pp.getM_PriceList_Version_ID() > 0) {
			MPriceListVersion plv = new MPriceListVersion(pp.getCtx(), pp.getM_PriceList_Version_ID(), pp.get_TrxName());
			if (plv.get_ID() > 0) {
				MPriceList pl = new MPriceList(pp.getCtx(), plv.getM_PriceList_ID(), pp.get_TrxName());
				if (pl.get_ID() > 0) {
					isPLSo = pl.isSOPriceList();
				}
			}
		}

		BigDecimal priceStd = pp.getPriceStd();
		BigDecimal priceList = pp.getPriceList();
		BigDecimal priceLimit = pp.getPriceLimit();

		// PriceStd Validation
		if (priceStd != null) {
			if (priceStd.compareTo(BigDecimal.ZERO) < 0) {
				throw new AdempiereException("¡El precio estándar no puede ser negativo!");
			}
			if (isPLSo && priceStd.compareTo(BigDecimal.ZERO) == 0) {
				throw new AdempiereException("¡El precio estándar debe ser mayor a cero!");
			}
		}

		// PriceList Validation
		if (priceList != null) {
			if (priceList.compareTo(BigDecimal.ZERO) < 0) {
				throw new AdempiereException("¡El precio de lista no puede ser negativo!");
			}
			if (isPLSo && priceList.compareTo(BigDecimal.ZERO) == 0) {
				throw new AdempiereException("¡El precio de lista debe ser mayor a cero!");
			}
		}

		// PriceLimit Validation
		if (priceLimit != null) {
			if (priceLimit.compareTo(BigDecimal.ZERO) < 0) {
				throw new AdempiereException("¡El precio límite no puede ser negativo!");
			}
			if (isPLSo && priceLimit.compareTo(BigDecimal.ZERO) == 0) {
				throw new AdempiereException("¡El precio límite debe ser mayor a cero!");
			}
		}
	}
}