/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2025 Frontuari, C.A. <https://frontuari.net> and contributors (see README.md file).
 */

package org.idempiere.seniat.validator.base;

import org.adempiere.base.event.FactsEventData;
import org.adempiere.base.event.LoginEventData;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.osgi.service.event.Event;

/**
 * Class for custom event
 */
public abstract class CustomEvent {

	private PO po;
	private Event event;
	private ProcessInfo pi;
	private FactsEventData fed;
	private LoginEventData led;

	/**
	 * Gets the persistent object
	 * 
	 * @return Persistent objectPersistent object
	 */
	protected PO getPO() {
		return po;
	}

	/**
	 * Gets the event
	 * 
	 * @return Event
	 */
	protected Event getEvent() {
		return event;
	}

	/**
	 * Gets the process info object
	 *
	 * @return ProcessInfo
	 */
	protected ProcessInfo getProcessInfo() {
		return pi;
	}

	/**
	 * Gets event type
	 * 
	 * @return Event type. Example IEventTopics.DOC_AFTER_COMPLETE
	 */
	protected String getEventType() {
		if (event == null)
			return null;
		return event.getTopic();
	}
	
	/**
	 * Gets Facts Event Data
	 * @return fed
	 */
	protected FactsEventData getFactsEventData()
	{
		if(event == null)
			return null;
		return fed;
	}
	
	/**
	 * Gets Login Event Data
	 * @return fed
	 */
	protected LoginEventData getLoginEventData()
	{
		if(event == null)
			return null;
		return led;
	}

	/**
	 * Executes the event in CustomEventFactory
	 * 
	 * @param po    Persistent object
	 * @param event Event
	 * @param led 	LoginEventData
	 */
	protected void doHandleEvent(PO po, Event event, LoginEventData led) {
		this.po = po;
		this.event = event;
		this.led = led;
		doHandleEvent();
	}

	/**
	 * Executes the event in CustomEventFactory
	 * 
	 * @param po    Persistent object
	 * @param event Event
	 * @param fed 	FactsEventData
	 */
	protected void doHandleEvent(PO po, Event event, FactsEventData fed) {
		this.po = po;
		this.event = event;
		this.fed = fed;
		doHandleEvent();
	}

	/**
	 * Executes the event in CustomEventFactory
	 * 
	 * @param po    Persistent object
	 * @param event Event
	 */
	protected void doHandleEvent(PO po, Event event) {
		this.po = po;
		this.event = event;
		doHandleEvent();
	}

	/**
	 * Executes the event in CustomEventFactory
	 *
	 * @param pi    Process Info
	 * @param event Event
	 */
	protected void doHandleEvent(ProcessInfo pi, Event event) {
		this.pi = pi;
		this.event = event;
		doHandleEvent();
	}

	/**
	 * Executes the event in CustomEventFactory
	 *
	 * @param po    Persistent object
	 * @param event Event
	 */
	protected void doHandleEvent(Event event) {
		this.event = event;
		doHandleEvent();
	}

	/**
	 * Custom event execution
	 */
	protected abstract void doHandleEvent();

}
