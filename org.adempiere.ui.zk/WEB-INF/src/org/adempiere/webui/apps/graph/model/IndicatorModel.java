/******************************************************************************
 * Copyright (C) 2013 Heng Sin Low                                            *
 * Copyright (C) 2013 Trek Global                 							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.apps.graph.model;

import java.awt.Color;

import org.compiere.model.MGoal;

/**
 * Model for performance indicator (meter/gauge)
 * @author hengsin
 */
public class IndicatorModel {
	public MGoal goalModel;
	/** not used in billboard implementation */
	public Color chartBackground;
	public Color dialBackground;
	/** not used in billboard implementation */
	public Color needleColor;
	public Color tickColor;
}
