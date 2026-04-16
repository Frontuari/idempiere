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

import java.util.List;
import java.util.stream.Collectors;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * Custom Process
 */
public abstract class CustomProcess extends SvrProcess {

	private List<String> selection = null;
	
	private List<List<Object>> getRows() {
		
		List<List<Object>> rows = null;
		
		StringBuffer sql = new StringBuffer("SELECT ViewID FROM T_Selection")
				.append(" WHERE AD_PInstance_ID=?");
		
		rows = DB.getSQLArrayObjectsEx(get_TrxName(), sql.toString(), getAD_PInstance_ID());
		
		return rows;
	}
	
	private List<String> getSelectionFromDB() {
		
		return getRows()
			.stream()
			.filter(row -> row.size() > 0)
			.map(row -> (String) row.get(0))
			.collect(Collectors.toList());
	}
	
	/*
	 * Obtains the selection by An Integer
	 */
	public List<String> getSelection() {
		
		if (selection == null || selection.isEmpty())
			selection = getSelectionFromDB();
		
		return selection;
	}
	
	public List<Integer> getSelectionAsInt() {
		
		return getSelection()
				.stream()
				.filter(select -> select != null && !select.isEmpty())
				.map(select -> Integer.parseInt(select))
				.collect(Collectors.toList());
	}
	
	public boolean isSelection() {
		
		selection = getSelection();
		return selection != null && !selection.isEmpty();
	}

	/**
	 * Get parameter
	 * 
	 * @param parameterName Parameter name to find
	 * @return null if no exist
	 */
	protected Object getParameter(String parameterName) {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (name != null)
				if (name.equals(parameterName))
					return para[i].getParameter();
		}
		return null;
	}

}
