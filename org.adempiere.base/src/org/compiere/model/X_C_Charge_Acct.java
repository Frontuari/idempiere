/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Charge_Acct
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_Charge_Acct")
public class X_C_Charge_Acct extends PO implements I_C_Charge_Acct, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_C_Charge_Acct (Properties ctx, int C_Charge_Acct_ID, String trxName)
    {
      super (ctx, C_Charge_Acct_ID, trxName);
      /** if (C_Charge_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Charge_ID (0);
			setCh_Expense_Acct (0);
        } */
    }

    /** Standard Constructor */
    public X_C_Charge_Acct (Properties ctx, int C_Charge_Acct_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Charge_Acct_ID, trxName, virtualColumns);
      /** if (C_Charge_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Charge_ID (0);
			setCh_Expense_Acct (0);
        } */
    }

    /** Standard Constructor */
    public X_C_Charge_Acct (Properties ctx, String C_Charge_Acct_UU, String trxName)
    {
      super (ctx, C_Charge_Acct_UU, trxName);
      /** if (C_Charge_Acct_UU == null)
        {
			setC_AcctSchema_ID (0);
			setC_Charge_ID (0);
			setCh_Expense_Acct (0);
        } */
    }

    /** Standard Constructor */
    public X_C_Charge_Acct (Properties ctx, String C_Charge_Acct_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Charge_Acct_UU, trxName, virtualColumns);
      /** if (C_Charge_Acct_UU == null)
        {
			setC_AcctSchema_ID (0);
			setC_Charge_ID (0);
			setCh_Expense_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Charge_Acct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_Charge_Acct[")
        .append(get_UUID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return (org.compiere.model.I_C_AcctSchema)MTable.get(getCtx(), org.compiere.model.I_C_AcctSchema.Table_ID)
			.getPO(getC_AcctSchema_ID(), get_TrxName());
	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID Rules for accounting
	*/
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Charge_Acct_UU.
		@param C_Charge_Acct_UU C_Charge_Acct_UU
	*/
	public void setC_Charge_Acct_UU (String C_Charge_Acct_UU)
	{
		set_Value (COLUMNNAME_C_Charge_Acct_UU, C_Charge_Acct_UU);
	}

	/** Get C_Charge_Acct_UU.
		@return C_Charge_Acct_UU	  */
	public String getC_Charge_Acct_UU()
	{
		return (String)get_Value(COLUMNNAME_C_Charge_Acct_UU);
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_ID)
			.getPO(getC_Charge_ID(), get_TrxName());
	}

	/** Set Charge.
		@param C_Charge_ID Additional document charges
	*/
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getCh_Expense_A() throws RuntimeException
	{
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_ID)
			.getPO(getCh_Expense_Acct(), get_TrxName());
	}

	/** Set Charge Account.
		@param Ch_Expense_Acct Charge Account
	*/
	public void setCh_Expense_Acct (int Ch_Expense_Acct)
	{
		set_Value (COLUMNNAME_Ch_Expense_Acct, Integer.valueOf(Ch_Expense_Acct));
	}

	/** Get Charge Account.
		@return Charge Account
	  */
	public int getCh_Expense_Acct()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ch_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}