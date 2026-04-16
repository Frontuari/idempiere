package org.idempiere.seniat.validator.event;

import java.math.BigDecimal;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.po.BeforeChange;
import org.adempiere.base.event.annotations.po.BeforeNew;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.osgi.service.event.Event;

@EventTopicDelegate
@ModelEventTopic(modelClass = MOrderLine.class)
public class FTUOrderLineEvents extends ModelEventDelegate<MOrderLine> {

	public FTUOrderLineEvents(MOrderLine po, Event event) {
		super(po, event);
	}
	
	@BeforeNew
	@BeforeChange
	public void onBeforeNC() {
		MOrderLine ol = getModel();
		MOrder o = new MOrder(ol.getCtx(), ol.getC_Order_ID(), ol.get_TrxName());
		// Updated by Marcos Reyes 2025-12-10 14:59
		// Adjusted the validation to prevent prices from being zero or less
		if(o.isSOTrx()) {
			if(ol.getPriceEntered().compareTo(BigDecimal.ZERO)<=0 
					|| ol.getPriceActual().compareTo(BigDecimal.ZERO)<=0)
				throw new AdempiereException("¡El precio no puede ser menor o igual a cero!");
		}
	}

}
