package org.idempiere.seniat.validator.event;

import java.math.BigDecimal;

import org.adempiere.base.annotation.EventTopicDelegate;
import org.adempiere.base.annotation.ModelEventTopic;
import org.adempiere.base.event.annotations.ModelEventDelegate;
import org.adempiere.base.event.annotations.po.BeforeNew;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.osgi.service.event.Event;

@EventTopicDelegate
@ModelEventTopic(modelClass = MInvoiceLine.class)
public class InvoiceLineEvents extends ModelEventDelegate<MInvoiceLine> {

	public InvoiceLineEvents(MInvoiceLine po, Event event) {
		super(po, event);
	}
	
	@BeforeNew
	public void beforeNew() {
		MInvoiceLine il = getModel();
		MInvoice i = il.getParent();
		if(il.get_ValueAsInt("LVE_invoiceAffected_ID") == 0 && i.get_ValueAsInt("LVE_invoiceAffected_ID") > 0)
		{
			il.set_ValueOfColumn("LVE_invoiceAffected_ID", i.get_ValueAsInt("LVE_invoiceAffected_ID"));
		}
		// Updated by Marcos Reyes 2025-12-10 14:59
		// Adjusted the validation to prevent prices from being zero or less
		if(i.isSOTrx() && !i.isReversal()) {
			if(il.getPriceEntered().compareTo(BigDecimal.ZERO)<=0 
					|| il.getPriceActual().compareTo(BigDecimal.ZERO)<=0)
				throw new AdempiereException("¡El precio no puede ser menor o igual a cero!");
		}
	}

}
