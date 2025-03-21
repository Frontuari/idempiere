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
import org.compiere.util.KeyNamePair;

/** Generated Model for ASP_Process
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="ASP_Process")
public class X_ASP_Process extends PO implements I_ASP_Process, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_ASP_Process (Properties ctx, int ASP_Process_ID, String trxName)
    {
      super (ctx, ASP_Process_ID, trxName);
      /** if (ASP_Process_ID == 0)
        {
			setAD_Process_ID (0);
			setASP_Level_ID (0);
			setASP_Status (null);
// S
        } */
    }

    /** Standard Constructor */
    public X_ASP_Process (Properties ctx, int ASP_Process_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ASP_Process_ID, trxName, virtualColumns);
      /** if (ASP_Process_ID == 0)
        {
			setAD_Process_ID (0);
			setASP_Level_ID (0);
			setASP_Status (null);
// S
        } */
    }

    /** Standard Constructor */
    public X_ASP_Process (Properties ctx, String ASP_Process_UU, String trxName)
    {
      super (ctx, ASP_Process_UU, trxName);
      /** if (ASP_Process_UU == null)
        {
			setAD_Process_ID (0);
			setASP_Level_ID (0);
			setASP_Status (null);
// S
        } */
    }

    /** Standard Constructor */
    public X_ASP_Process (Properties ctx, String ASP_Process_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, ASP_Process_UU, trxName, virtualColumns);
      /** if (ASP_Process_UU == null)
        {
			setAD_Process_ID (0);
			setASP_Level_ID (0);
			setASP_Status (null);
// S
        } */
    }

    /** Load Constructor */
    public X_ASP_Process (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System
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
      StringBuilder sb = new StringBuilder ("X_ASP_Process[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Process)MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_ID)
			.getPO(getAD_Process_ID(), get_TrxName());
	}

	/** Set Process.
		@param AD_Process_ID Process or Report
	*/
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Process.
		@return Process or Report
	  */
	public int getAD_Process_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_ASP_Level getASP_Level() throws RuntimeException
	{
		return (org.compiere.model.I_ASP_Level)MTable.get(getCtx(), org.compiere.model.I_ASP_Level.Table_ID)
			.getPO(getASP_Level_ID(), get_TrxName());
	}

	/** Set ASP Level.
		@param ASP_Level_ID ASP Level
	*/
	public void setASP_Level_ID (int ASP_Level_ID)
	{
		if (ASP_Level_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ASP_Level_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ASP_Level_ID, Integer.valueOf(ASP_Level_ID));
	}

	/** Get ASP Level.
		@return ASP Level	  */
	public int getASP_Level_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Level_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getASP_Level_ID()));
    }

	/** Set ASP Process.
		@param ASP_Process_ID ASP Process
	*/
	public void setASP_Process_ID (int ASP_Process_ID)
	{
		if (ASP_Process_ID < 1)
			set_ValueNoCheck (COLUMNNAME_ASP_Process_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_ASP_Process_ID, Integer.valueOf(ASP_Process_ID));
	}

	/** Get ASP Process.
		@return ASP Process	  */
	public int getASP_Process_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ASP_Process_UU.
		@param ASP_Process_UU ASP_Process_UU
	*/
	public void setASP_Process_UU (String ASP_Process_UU)
	{
		set_Value (COLUMNNAME_ASP_Process_UU, ASP_Process_UU);
	}

	/** Get ASP_Process_UU.
		@return ASP_Process_UU	  */
	public String getASP_Process_UU()
	{
		return (String)get_Value(COLUMNNAME_ASP_Process_UU);
	}

	/** ASP_Status AD_Reference_ID=53234 */
	public static final int ASP_STATUS_AD_Reference_ID=53234;
	/** Hide = H */
	public static final String ASP_STATUS_Hide = "H";
	/** Show = S */
	public static final String ASP_STATUS_Show = "S";
	/** Undefined = U */
	public static final String ASP_STATUS_Undefined = "U";
	/** Set ASP Status.
		@param ASP_Status ASP Status
	*/
	public void setASP_Status (String ASP_Status)
	{

		set_Value (COLUMNNAME_ASP_Status, ASP_Status);
	}

	/** Get ASP Status.
		@return ASP Status	  */
	public String getASP_Status()
	{
		return (String)get_Value(COLUMNNAME_ASP_Status);
	}
}