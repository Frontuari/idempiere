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

/** Generated Model for AD_Document_Action_Access
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="AD_Document_Action_Access")
public class X_AD_Document_Action_Access extends PO implements I_AD_Document_Action_Access, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_AD_Document_Action_Access (Properties ctx, int AD_Document_Action_Access_ID, String trxName)
    {
      super (ctx, AD_Document_Action_Access_ID, trxName);
      /** if (AD_Document_Action_Access_ID == 0)
        {
			setAD_Ref_List_ID (0);
			setAD_Role_ID (0);
			setC_DocType_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_AD_Document_Action_Access (Properties ctx, int AD_Document_Action_Access_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Document_Action_Access_ID, trxName, virtualColumns);
      /** if (AD_Document_Action_Access_ID == 0)
        {
			setAD_Ref_List_ID (0);
			setAD_Role_ID (0);
			setC_DocType_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_AD_Document_Action_Access (Properties ctx, String AD_Document_Action_Access_UU, String trxName)
    {
      super (ctx, AD_Document_Action_Access_UU, trxName);
      /** if (AD_Document_Action_Access_UU == null)
        {
			setAD_Ref_List_ID (0);
			setAD_Role_ID (0);
			setC_DocType_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_AD_Document_Action_Access (Properties ctx, String AD_Document_Action_Access_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Document_Action_Access_UU, trxName, virtualColumns);
      /** if (AD_Document_Action_Access_UU == null)
        {
			setAD_Ref_List_ID (0);
			setAD_Role_ID (0);
			setC_DocType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Document_Action_Access (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client
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
      StringBuilder sb = new StringBuilder ("X_AD_Document_Action_Access[")
        .append(get_UUID()).append("]");
      return sb.toString();
    }

	/** Set AD_Document_Action_Access_UU.
		@param AD_Document_Action_Access_UU AD_Document_Action_Access_UU
	*/
	public void setAD_Document_Action_Access_UU (String AD_Document_Action_Access_UU)
	{
		set_Value (COLUMNNAME_AD_Document_Action_Access_UU, AD_Document_Action_Access_UU);
	}

	/** Get AD_Document_Action_Access_UU.
		@return AD_Document_Action_Access_UU	  */
	public String getAD_Document_Action_Access_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_Document_Action_Access_UU);
	}

	public org.compiere.model.I_AD_Ref_List getAD_Ref_List() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Ref_List)MTable.get(getCtx(), org.compiere.model.I_AD_Ref_List.Table_ID)
			.getPO(getAD_Ref_List_ID(), get_TrxName());
	}

	/** Set Reference List.
		@param AD_Ref_List_ID Reference List based on Table
	*/
	public void setAD_Ref_List_ID (int AD_Ref_List_ID)
	{
		if (AD_Ref_List_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, Integer.valueOf(AD_Ref_List_ID));
	}

	/** Get Reference List.
		@return Reference List based on Table
	  */
	public int getAD_Ref_List_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Ref_List_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Role)MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_ID)
			.getPO(getAD_Role_ID(), get_TrxName());
	}

	/** Set Role.
		@param AD_Role_ID Responsibility Role
	*/
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_ID)
			.getPO(getC_DocType_ID(), get_TrxName());
	}

	/** Set Document Type.
		@param C_DocType_ID Document type or rules
	*/
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}