/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaElement;
import org.compiere.model.MDistribution;
import org.compiere.model.MDistributionLine;
import org.compiere.model.MElementValue;
import org.compiere.model.MFactAcct;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *  Accounting Fact
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
 *  
 *  BF [ 2789949 ] Multicurrency in matching posting
 */
public final class Fact
{
	/**
	 *	Constructor
	 *  @param  document    pointer to document
	 *  @param  acctSchema  Account Schema to create accounts
	 *  @param  defaultPostingType  the default Posting type (actual,..) for this posting
	 */
	public Fact (Doc document, MAcctSchema acctSchema, String defaultPostingType)
	{
		m_doc = document;
		m_acctSchema = acctSchema;
		m_postingType = defaultPostingType;
		// Fix [ 1884676 ] Fact not setting transaction
		m_trxName = document.getTrxName();
		//
		if (log.isLoggable(Level.CONFIG)) log.config(toString());
	}	//	Fact


	/**	Log					*/
	private static final CLogger	log = CLogger.getCLogger(Fact.class);

	/** Document            */
	private Doc             m_doc = null;
	/** Accounting Schema   */
	private MAcctSchema	    m_acctSchema = null;
	/** Transaction			*/
	private String m_trxName;

	/** Posting Type        */
	private String		    m_postingType = null;

	/** Actual Balance Type */
	public static final String	POST_Actual = MFactAcct.POSTINGTYPE_Actual;
	/** Budget Balance Type */
	public static final String	POST_Budget = MFactAcct.POSTINGTYPE_Budget;
	/** Encumbrance Posting */
	public static final String	POST_Commitment = MFactAcct.POSTINGTYPE_Commitment;
	/** Encumbrance Posting */
	public static final String	POST_Reservation = MFactAcct.POSTINGTYPE_Reservation;


	/** Is Converted        */
	private boolean		    m_converted = false;

	/** Lines               */
	private ArrayList<FactLine>	m_lines = new ArrayList<FactLine>();


	/**
	 *  Dispose
	 */
	public void dispose()
	{
		m_lines.clear();
		m_lines = null;
	}   //  dispose

	/**
	 *	Create and convert Fact Line.
	 *  Used to create a DR and/or CR entry
	 *
	 *	@param  docLine     the document line or null
	 *  @param  account     if null, line is not created
	 *  @param  C_Currency_ID   the currency
	 *  @param  debitAmt    debit amount, can be null
	 *  @param  creditAmt  credit amount, can be null
	 *  @return Fact Line
	 */
	public FactLine createLine (DocLine docLine, MAccount account,
		int C_Currency_ID, BigDecimal debitAmt, BigDecimal creditAmt)
	{
	//	log.fine("createLine - " + account	+ " - Dr=" + debitAmt + ", Cr=" + creditAmt);

		//  Data Check
		if (account == null)
		{
			if (log.isLoggable(Level.INFO)) log.info("No account for " + docLine 
				+ ": Amt=" + debitAmt + "/" + creditAmt 
				+ " - " + toString());			
			return null;
		}
		//
		FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), 
			m_doc.get_ID(),
			docLine == null ? 0 : docLine.get_ID(), m_trxName);
		//  Set Info & Account
		line.setDocumentInfo(m_doc, docLine);
		line.setPostingType(m_postingType);
		line.setAccount(m_acctSchema, account);

		//  Amounts - one needs to not zero
		if (!line.setAmtSource(C_Currency_ID, debitAmt, creditAmt))
		{
			if (docLine == null || docLine.getQty() == null || docLine.getQty().signum() == 0)
			{
				if (log.isLoggable(Level.FINE)) log.fine("Both amounts & qty = 0/Null - " + docLine		
					+ " - " + toString());			
				return null;
			}
			if (log.isLoggable(Level.FINE)) log.fine("Both amounts = 0/Null, Qty=" + docLine.getQty() + " - " + docLine		
				+ " - " + toString());			
		}
		//  Convert
		line.convert();
		//  Optionally overwrite Acct Amount
		if (docLine != null 
			&& (docLine.getAmtAcctDr() != null || docLine.getAmtAcctCr() != null))
			line.setAmtAcct(docLine.getAmtAcctDr(), docLine.getAmtAcctCr());
		//
		if (log.isLoggable(Level.FINE)) log.fine(line.toString());
		add(line);
		return line;
	}	//	createLine

	/**
	 *  Add Fact Line
	 *  @param line fact line
	 */
	public void add (FactLine line)
	{
		m_lines.add(line);
	}   //  add

	/**
	 *  Remove Fact Line
	 *  @param line fact line
	 */
	public void remove (FactLine line)
	{
		m_lines.remove(line);
	}   //  remove

	/**
	 *	Create and convert Fact Line.
	 *  Used to create either a DR or CR entry
	 *
	 *	@param  docLine     Document Line or null
	 *  @param  accountDr   Account to be used if Amt is DR balance
	 *  @param  accountCr   Account to be used if Amt is CR balance
	 *  @param  C_Currency_ID Currency
	 *  @param  Amt if negative Cr else Dr
	 *  @return FactLine
	 */
	public FactLine createLine (DocLine docLine, MAccount accountDr, MAccount accountCr,
		int C_Currency_ID, BigDecimal Amt)
	{
		if (Amt.signum() < 0)
			return createLine (docLine, accountCr, C_Currency_ID, null, Amt.abs());
		else
			return createLine (docLine, accountDr, C_Currency_ID, Amt, null);
	}   //  createLine

	/**
	 *	Create and convert Fact Line.
	 *  Used to create either a DR or CR entry
	 *
	 *	@param  docLine Document line or null
	 *  @param  account   Account to be used
	 *  @param  C_Currency_ID Currency
	 *  @param  Amt if negative Cr else Dr
	 *  @return FactLine
	 */
	public FactLine createLine (DocLine docLine, MAccount account,
		int C_Currency_ID, BigDecimal Amt)
	{
		if (Amt.signum() < 0)
			return createLine (docLine, account, C_Currency_ID, null, Amt.abs());
		else
			return createLine (docLine, account, C_Currency_ID, Amt, null);
	}   //  createLine

	/**
	 *  Is Posting Type
	 *  @param  PostingType - see POST_*
	 *  @return true if document is posting type
	 */
	public boolean isPostingType (String PostingType)
	{
		return m_postingType.equals(PostingType);
	}   //  isPostingType

	/**
	 *	Is converted
	 *  @return true if converted
	 */
	public boolean isConverted()
	{
		return m_converted;
	}	//	isConverted

	/**
	 *	Get AcctSchema
	 *  @return AcctSchema
	 */
	public MAcctSchema getAcctSchema()
	{
		return m_acctSchema;
	}	//	getAcctSchema

	
	/**************************************************************************
	 *	Are the lines Source Balanced
	 *  @return true if source lines balanced
	 */
	public boolean isSourceBalanced()
	{
		//AZ Goodwill
		//  Multi-Currency documents are source balanced by definition
		//  No lines -> balanced
		if (m_lines.size() == 0 || m_doc.isMultiCurrency())
			return true;
		
		// If there is more than 1 currency in fact lines, it is a multi currency doc
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < m_lines.size(); i++){
			FactLine line = (FactLine)m_lines.get(i);
			if (line.getC_Currency_ID() > 0 && !list.contains(line.getC_Currency_ID()))
				list.add(line.getC_Currency_ID());
	
		}
		if (list.size() > 1 )
			return true;
				
		BigDecimal balance = getSourceBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue) {
			if (log.isLoggable(Level.FINER)) log.finer(toString());
		} else {
			log.warning ("NO - Diff=" + balance + " - " + toString());
		}
		return retValue;
	}	//	isSourceBalanced

	/**
	 *	Return Source Balance
	 *  @return source balance
	 */
	protected BigDecimal getSourceBalance()
	{
		BigDecimal result = Env.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			result = result.add (line.getSourceBalance());
		}
	//	log.fine("getSourceBalance - " + result.toString());
		return result;
	}	//	getSourceBalance

	/**
	 *	Create Source Line for Suspense Balancing.
	 *  Only if Suspense Balancing is enabled and not a multi-currency document
	 *  (double check as otherwise the rule should not have fired)
	 *  If not balanced create balancing entry in currency of the document
	 *  @return FactLine
	 */
	public FactLine balanceSource()
	{
		if (!m_acctSchema.isSuspenseBalancing() || m_doc.isMultiCurrency())
			return null;
		BigDecimal diff = getSourceBalance();
		if (log.isLoggable(Level.FINER)) log.finer("Diff=" + diff);

		//  new line
		FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), 
			m_doc.get_ID(), 0, m_trxName);
		line.setDocumentInfo(m_doc, null);
		line.setAD_Org_ID(m_doc.getAD_Org_ID());
		line.setPostingType(m_postingType);

		//	Account
		line.setAccount(m_acctSchema, m_acctSchema.getSuspenseBalancing_Acct());

		//  Amount
		if (diff.signum() < 0)   //  negative balance => DR
			line.setAmtSource(m_doc.getC_Currency_ID(), diff.abs(), Env.ZERO);
		else                                //  positive balance => CR
			line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, diff);
			
		//  Convert
		line.convert();
		//
		if (log.isLoggable(Level.FINE)) log.fine(line.toString());
		m_lines.add(line);
		return line;
	}   //  balancingSource

	
	/**************************************************************************
	 *  Are all segments balanced
	 *  @return true if segments are balanced
	 */
	public boolean isSegmentBalanced()
	{
		//  No lines -> balanced
		if (m_lines.size() == 0)
			return true;
		
		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		//  check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced() && !isSegmentBalanced (ase.getElementType()))
				return false;
		}
		return true;
	}   //  isSegmentBalanced

	/**
	 *  Is Source Segment balanced.
	 *  @param  segmentType - see AcctSchemaElement.SEGMENT_*
	 *  Implemented only for Org
	 *  Other sensible candidates are Project, User1/2
	 *  @return true if segments are balanced
	 */
	public boolean isSegmentBalanced (String segmentType)
	{
		if (segmentType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer,BigDecimal> map = new HashMap<Integer,BigDecimal>();
			//	Added by Jorge Colmenarez, 2025-02-24 13:06
			HashMap<Integer,Balance> newMap = new HashMap<Integer,Balance>();
			//	End Jorge Colmenarez
			//  Add up values by organization
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = (FactLine)m_lines.get(i);
				Integer key = Integer.valueOf(line.getAD_Org_ID());
				BigDecimal bal = line.getSourceBalance();
				BigDecimal oldBal = (BigDecimal)map.get(key);
				if (oldBal != null)
					bal = bal.add(oldBal);
				map.put(key, bal);
				//	Added by Jorge Colmenarez, 2025-02-24 13:06
				Balance oldBalance = (Balance)newMap.get(key);
				if (oldBalance == null)
				{
					oldBalance = new Balance (line.getAmtSourceDr(), line.getAmtSourceCr());
					newMap.put(key, oldBalance);
				}
				else
					oldBalance.add(line.getAmtSourceDr(), line.getAmtSourceCr());
				//	End Jorge Colmenarez
			}
			//	Added by Jorge Colmenarez, 2025-02-24 13:06
			//	Create Array List OrgBalance
			List<OrgBalance> orgToDistributive = new ArrayList<>();
			Iterator<Integer> keys = newMap.keySet().iterator();
			while (keys.hasNext())
			{
				Integer key = keys.next();
				Balance difference = newMap.get(key);
				orgToDistributive.add(new OrgBalance(key, difference));
			}
			List<OrgBalance> distribuidos1 = distribuir(orgToDistributive.iterator());
			List<OrgBalance> filtrados1 = filtrarPorMontoYPadre(distribuidos1);
			if(filtrados1.size()>0) {
				//  check if there are not balance entries involving multiple organizations
				Map<Integer, BigDecimal> notBalance = new HashMap<>();			
				//for(Map.Entry<Integer, BigDecimal> entry : map.entrySet())
				for (OrgBalance ob : filtrados1)
				{
					BigDecimal bal = ob.balance.getBalance();
					if (bal.signum() != 0)
					{
						notBalance.put(ob.org, ob.balance.getBalance());
					}
				}
				if (notBalance.size() > 1)
				{
					return false;
				}
			}
			//	End Jorge Colmenarez
			if (log.isLoggable(Level.FINER)) log.finer("(" + segmentType + ") - " + toString());
			return true;
		}
		if (log.isLoggable(Level.FINER)) log.finer("(" + segmentType + ") (not checked) - " + toString());
		return true;
	}   //  isSegmentBalanced

	/**
	 *  Balance all segments.
	 *  - For all balancing segments
	 *      - For all segment values
	 *          - If balance &lt;&gt; 0 create dueTo/dueFrom line
	 *              overwriting the segment value
	 */
	public void balanceSegments()
	{
		MAcctSchemaElement[] elements = m_acctSchema.getAcctSchemaElements();
		//  check all balancing segments
		for (int i = 0; i < elements.length; i++)
		{
			MAcctSchemaElement ase = elements[i];
			if (ase.isBalanced())
				balanceSegment (ase.getElementType());
		}
	}   //  balanceSegments

	/**
	 *  Balance Source Segment
	 *  @param elementType segment element type
	 */
	private void balanceSegment (String elementType)
	{
		//  no lines -> balanced
		if (m_lines.size() == 0)
			return;

		if (log.isLoggable(Level.FINE)) log.fine ("(" + elementType + ") - " + toString());

		//  Org
		if (elementType.equals(MAcctSchemaElement.ELEMENTTYPE_Organization))
		{
			HashMap<Integer,Balance> map = new HashMap<Integer,Balance>();
			//  Add up values by key
			for (int i = 0; i < m_lines.size(); i++)
			{
				FactLine line = (FactLine)m_lines.get(i);
				Integer key = Integer.valueOf(line.getAD_Org_ID());
				Balance oldBalance = (Balance)map.get(key);
				if (oldBalance == null)
				{
					oldBalance = new Balance (line.getAmtSourceDr(), line.getAmtSourceCr());
					map.put(key, oldBalance);
				}
				else
					oldBalance.add(line.getAmtSourceDr(), line.getAmtSourceCr());
			}

			//  Create entry for non-zero element
			Iterator<Integer> keys = map.keySet().iterator();
			//	Added by Jorge Colmenarez, 2025-02-24 09:25
			//	Create Array List OrgBalance
			List<OrgBalance> orgToDistributive = new ArrayList<>();
			while (keys.hasNext())
			{
				Integer key = keys.next();
				Balance difference = map.get(key);
				orgToDistributive.add(new OrgBalance(key, difference));
			}
			List<OrgBalance> distribuidos1 = distribuir(orgToDistributive.iterator());
			List<OrgBalance> filtrados1 = filtrarPorMontoYPadre(distribuidos1);
			if(filtrados1.size()>0) {
				for (OrgBalance ob : filtrados1) {
					Integer key = ob.org;
					Balance difference = ob.balance;
				/*while (keys.hasNext())
				{	
					Integer key = keys.next();
					Balance difference = map.get(key);*/
				//	End Jorge Colmenarez
					if (log.isLoggable(Level.INFO)) log.info (elementType + "=" + key + ", " + difference);
					//
					if (!difference.isZeroBalance())
					{
						//  Create Balancing Entry
						FactLine line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), 
							m_doc.get_ID(), 0, m_trxName);
						line.setDocumentInfo(m_doc, null);
						line.setPostingType(m_postingType);
						//  Amount & Account
						if (difference.getBalance().signum() < 0)
						{
							//	Modified by Jorge Colmenarez, 2025-02-24 14:30
							if (m_doc.getPO().get_ValueAsInt("Reversal_ID")>0)
							{
								line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
								line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), Env.ZERO);
							}
							//	End Jorge Colmenarez
							else
							{
								line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
								line.setAmtSource(m_doc.getC_Currency_ID(), difference.getPostBalance(), Env.ZERO);
							}
						}
						else
						{
							//	Modified by Jorge Colmenarez, 2025-02-24 14:30
							if (m_doc.getPO().get_ValueAsInt("Reversal_ID")>0)
							{
								line.setAccount(m_acctSchema, m_acctSchema.getDueFrom_Acct(elementType));
								line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, difference.getPostBalance());
							}
							//	End Jorge Colmenarez
							else
							{
								line.setAccount(m_acctSchema, m_acctSchema.getDueTo_Acct(elementType));
								line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, difference.getPostBalance());
							}
						}
						line.convert();
						line.setAD_Org_ID(key.intValue());
						//	Added by Jorge Colmenarez, 2025-02-24 13:45
						//	Set BPartner Company
						if(ob.getBPartnerID()>0)
							line.setC_BPartner_ID(ob.getBPartnerID());
						//	End Jorge Colmenarez
						//
						m_lines.add(line);
						if (log.isLoggable(Level.FINE)) log.fine("(" + elementType + ") - " + line);
					}
				}
			}
			map.clear();
		}
	}   //  balanceSegment

	
	/**************************************************************************
	 *	Are the lines Accounting Balanced
	 *  @return true if accounting lines are balanced
	 */
	public boolean isAcctBalanced()
	{
		//  no lines -> balanced
		if (m_lines.size() == 0)
			return true;
		BigDecimal balance = getAcctBalance();
		boolean retValue = balance.signum() == 0;
		if (retValue) {
			if (log.isLoggable(Level.FINER)) log.finer(toString());
		} else {
			log.warning("NO - Diff=" + balance + " - " + toString());
		}
		return retValue;
	}	//	isAcctBalanced

	/**
	 *	Return Accounting Balance
	 *  @return true if accounting lines are balanced
	 */
	protected BigDecimal getAcctBalance()
	{
		BigDecimal result = Env.ZERO;
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			result = result.add(line.getAcctBalance());
		}
	//	log.fine(result.toString());
		return result;
	}	//	getAcctBalance

	/**
	 *  Balance Accounting Currency.
	 *  If the accounting currency is not balanced,
	 *      if Currency balancing is enabled
	 *          create a new line using the currency balancing account with zero source balance
	 *      or
	 *          adjust the line with the largest balance sheet account
	 *          or if no balance sheet account exist, the line with the largest amount
	 *  @return FactLine
	 */
	public FactLine balanceAccounting()
	{
		BigDecimal diff = getAcctBalance();		//	DR-CR
		if (log.isLoggable(Level.FINE)) log.fine("Balance=" + diff 
			+ ", CurrBal=" + m_acctSchema.isCurrencyBalancing() 
			+ " - " + toString());
		FactLine line = null;

		BigDecimal BSamount = Env.ZERO;
		FactLine BSline = null;
		BigDecimal PLamount = Env.ZERO;
		FactLine PLline = null;

		//  Find line biggest BalanceSheet or P&L line
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine l = (FactLine)m_lines.get(i);
			BigDecimal amt = l.getAcctBalance().abs();
			if (l.isBalanceSheet() && amt.compareTo(BSamount) > 0)
			{
				BSamount = amt;
				BSline = l;
			}
			else if (!l.isBalanceSheet() && amt.compareTo(PLamount) > 0)
			{
				PLamount = amt;
				PLline = l;
			}
		}
		
		//  Create Currency Balancing Entry
		if (m_acctSchema.isCurrencyBalancing())
		{
			line = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(), 
				m_doc.get_ID(), 0, m_trxName);
			line.setDocumentInfo (m_doc, null);
			line.setPostingType (m_postingType);
			line.setAD_Org_ID(m_doc.getAD_Org_ID());
			line.setAccount (m_acctSchema, m_acctSchema.getCurrencyBalancing_Acct());
			
			//  Amount
			line.setAmtSource(m_doc.getC_Currency_ID(), Env.ZERO, Env.ZERO);
			line.convert();
			//	Accounted
			BigDecimal drAmt = Env.ZERO;
			BigDecimal crAmt = Env.ZERO;
			boolean isDR = diff.signum() < 0;
			BigDecimal difference = diff.abs();
			if (isDR)
				drAmt = difference;
			else
				crAmt = difference;
			//	Switch sides
			boolean switchIt = BSline != null 
				&& ((BSline.isDrSourceBalance() && isDR)
					|| (!BSline.isDrSourceBalance() && !isDR));
			if (switchIt)
			{
				drAmt = Env.ZERO;
				crAmt = Env.ZERO;
				if (isDR)
					crAmt = difference.negate();
				else
					drAmt = difference.negate();
			}
			line.setAmtAcct(drAmt, crAmt);
			if (log.isLoggable(Level.FINE)) log.fine(line.toString());
			m_lines.add(line);
		}
		else	//  Adjust biggest (Balance Sheet) line amount
		{
			if (BSline != null)
				line = BSline;
			else
				line = PLline;
			if (line == null)
				log.severe ("No Line found");
			else
			{
				if (log.isLoggable(Level.FINE)) log.fine("Adjusting Amt=" + diff + "; Line=" + line);
				line.currencyCorrect(diff);
				if (log.isLoggable(Level.FINE)) log.fine(line.toString());
			}
		}   //  correct biggest amount

		return line;
	}   //  balanceAccounting

	/**
	 * 	Check Accounts of Fact Lines
	 *	@return true if success
	 */
	public boolean checkAccounts()
	{
		//  no lines -> nothing to distribute
		if (m_lines.size() == 0)
			return true;
		
		//	For all fact lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine line = (FactLine)m_lines.get(i);
			MAccount account = line.getAccount();
			if (account == null)
			{
				log.warning("No Account for " + line);
				return false;
			}
			MElementValue ev = account.getAccount();
			if (ev == null)
			{
				log.warning("No Element Value for " + account 
					+ ": " + line);
				m_doc.p_Error = account.toString();
				return false;
			}
			if (ev.isSummary())
			{
				log.warning("Cannot post to Summary Account " + ev 
					+ ": " + line);
				m_doc.p_Error = ev.toString();
				return false;
			}
			if (!ev.isActive())
			{
				log.warning("Cannot post to Inactive Account " + ev 
					+ ": " + line);
				m_doc.p_Error = ev.toString();
				return false;
			}

		}	//	for all lines
		
		return true;
	}	//	checkAccounts
	
	/**
	 * 	GL Distribution of Fact Lines
	 *	@return true if success
	 */
	public boolean distribute()
	{
		//  no lines -> nothing to distribute
		if (m_lines.size() == 0)
			return true;
		
		ArrayList<FactLine> newLines = new ArrayList<FactLine>();
		//	For all fact lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine dLine = (FactLine)m_lines.get(i);
			MDistribution[] distributions = MDistribution.get (dLine.getAccount(), 
				m_postingType, m_doc.getC_DocType_ID(), dLine.getDateAcct());
			//	No Distribution for this line
			//AZ Goodwill
			//The above "get" only work in GL Journal because it's using ValidCombination Account
			if (distributions == null || distributions.length == 0)
			{
				distributions = MDistribution.get (dLine.getCtx(), dLine.getC_AcctSchema_ID(),
					m_postingType, m_doc.getC_DocType_ID(), dLine.getDateAcct(),
					dLine.getAD_Org_ID(), dLine.getAccount_ID(),
					dLine.getM_Product_ID(), dLine.getC_BPartner_ID(), dLine.getC_Project_ID(),
					dLine.getC_Campaign_ID(), dLine.getC_Activity_ID(), dLine.getAD_OrgTrx_ID(),
					dLine.getC_SalesRegion_ID(), dLine.getC_LocTo_ID(), dLine.getC_LocFrom_ID(),
					dLine.getUser1_ID(), dLine.getUser2_ID());
				if (distributions == null || distributions.length == 0)
					continue;
			}
			//end AZ
			//	Just the first
			if (distributions.length > 1)
				log.warning("More than one Distribution for " + dLine.getAccount());
			MDistribution distribution = distributions[0];

			// FR 2685367 - GL Distribution delete line instead reverse
			if (distribution.isCreateReversal()) {
				//	Add Reversal
				FactLine reversal = dLine.reverse(distribution.getName());
				if (log.isLoggable(Level.INFO)) log.info("Reversal=" + reversal);
				newLines.add(reversal);		//	saved in postCommit
			} else {
				// delete the line being distributed
				m_lines.remove(i);    // or it could be m_lines.remove(dLine);
				i--;
			}

			//	Prepare
			distribution.distribute(dLine.getAccount(), dLine.getSourceBalance(), dLine.getQty(), dLine.getC_Currency_ID());
			MDistributionLine[] lines = distribution.getLines(false);
			for (int j = 0; j < lines.length; j++)
			{
				MDistributionLine dl = lines[j];
				if (!dl.isActive() || dl.getAmt().signum() == 0)
					continue;
				FactLine factLine = new FactLine (m_doc.getCtx(), m_doc.get_Table_ID(),
					m_doc.get_ID(), dLine.getLine_ID(), m_trxName);
				//  Set Info & Account
				factLine.setDocumentInfo(m_doc, dLine.getDocLine());
				factLine.setDescription(dLine.getDescription());
				factLine.setAccount(m_acctSchema, dl.getAccount());
				factLine.setPostingType(m_postingType);
				if (dl.isOverwriteOrg())	//	set Org explicitly
					factLine.setAD_Org_ID(dl.getOrg_ID());
				else
					factLine.setAD_Org_ID(dLine.getAD_Org_ID());
				// Silvano - freepath - F3P - Bug#2904994 Fact distribtution only overwriting Org
				if(dl.isOverwriteAcct())
					factLine.setAccount_ID(dl.getAccount_ID());
				else
					factLine.setAccount_ID(dLine.getAccount_ID());
				if(dl.isOverwriteActivity())
					factLine.setC_Activity_ID(dl.getC_Activity_ID());
				else
					factLine.setC_Activity_ID(dLine.getC_Activity_ID());
				if(dl.isOverwriteBPartner())
					factLine.setC_BPartner_ID(dl.getC_BPartner_ID());
				else
					factLine.setC_BPartner_ID(dLine.getC_BPartner_ID());
				if(dl.isOverwriteCampaign())
					factLine.setC_Campaign_ID(dl.getC_Campaign_ID());
				else
					factLine.setC_Campaign_ID(dLine.getC_Campaign_ID());
				if(dl.isOverwriteLocFrom())
					factLine.setC_LocFrom_ID(dl.getC_LocFrom_ID());
				else
					factLine.setC_LocFrom_ID(dLine.getC_LocFrom_ID());
				if(dl.isOverwriteLocTo())
					factLine.setC_LocTo_ID(dl.getC_LocTo_ID());
				else
					factLine.setC_LocTo_ID(dLine.getC_LocTo_ID());
				if(dl.isOverwriteOrgTrx())
					factLine.setAD_OrgTrx_ID(dl.getAD_OrgTrx_ID());
				else
					factLine.setAD_OrgTrx_ID(dLine.getAD_OrgTrx_ID());
				if(dl.isOverwriteProduct())
					factLine.setM_Product_ID(dl.getM_Product_ID());
				else
					factLine.setM_Product_ID(dLine.getM_Product_ID());
				if(dl.isOverwriteProject())
					factLine.setC_Project_ID(dl.getC_Project_ID());
				else
					factLine.setC_Project_ID(dLine.getC_Project_ID());
				if(dl.isOverwriteSalesRegion())
					factLine.setC_SalesRegion_ID(dl.getC_SalesRegion_ID());
				else
					factLine.setC_SalesRegion_ID(dLine.getC_SalesRegion_ID());
				if(dl.isOverwriteUser1())				
					factLine.setUser1_ID(dl.getUser1_ID());
				else
					factLine.setUser1_ID(dLine.getUser1_ID());
				if(dl.isOverwriteUser2())				
					factLine.setUser2_ID(dl.getUser2_ID());					
				else
					factLine.setUser2_ID(dLine.getUser2_ID());
				factLine.setUserElement1_ID(dLine.getUserElement1_ID());
				factLine.setUserElement2_ID(dLine.getUserElement2_ID());
				// F3P end
				//
				if (dLine.getAmtAcctCr().signum() != 0) // isCredit
					factLine.setAmtSource(dLine.getC_Currency_ID(), null, dl.getAmt().negate());
				else
					factLine.setAmtSource(dLine.getC_Currency_ID(), dl.getAmt(), null);
				factLine.setQty(dl.getQty());
				//  Convert
				factLine.convert();
				//
				String description = distribution.getName() + " #" + dl.getLine();
				if (dl.getDescription() != null)
					description += " - " + dl.getDescription();
				factLine.addDescription(description);
				//
				if (log.isLoggable(Level.INFO)) log.info(factLine.toString());
				newLines.add(factLine);
			}
		}	//	for all lines
		
		//	Add Lines
		for (int i = 0; i < newLines.size(); i++)
			m_lines.add(newLines.get(i));
		
		return true;
	}	//	distribute	
	
	/**************************************************************************
	 * String representation
	 * @return String
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Fact[");
		sb.append(m_doc.toString());
		sb.append(",").append(m_acctSchema.toString());
		sb.append(",PostType=").append(m_postingType);
		sb.append("]");
		return sb.toString();
	}	//	toString

	/**
	 *	Get Lines
	 *  @return FactLine Array
	 */
	public FactLine[] getLines()
	{
		FactLine[] temp = new FactLine[m_lines.size()];
		m_lines.toArray(temp);
		return temp;
	}	//	getLines

	/**
	 *  Save Fact
	 *  @param trxName transaction
	 *  @return true if all lines were saved
	 */
	public boolean save (String trxName)
	{
		m_trxName = trxName;
		//  save Lines
		for (int i = 0; i < m_lines.size(); i++)
		{
			FactLine fl = (FactLine)m_lines.get(i);
			if (!fl.save(trxName))  //  abort on first error
				return false;
		}
		return true;
	}   //  commit

	/**
	 * 	Get Transaction
	 *	@return trx
	 */
	public String get_TrxName() 
	{
		return m_trxName;
	}	//	getTrxName

	/**
	 * 	Set Transaction name
	 * 	@param trxName
	 */
	@SuppressWarnings("unused")
	private void set_TrxName(String trxName) 
	{
		m_trxName = trxName;
	}	//	set_TrxName
	
	/**
     * Método que recibe un Iterator de OrgBalance y distribuye el saldo de la
     * organización que es única en un lado (positivo o negativo) en función de
     * los importes del otro grupo.
     */
    public static List<OrgBalance> distribuir(Iterator<OrgBalance> keys) {
        List<OrgBalance> positivos = new ArrayList<>();
        List<OrgBalance> negativos = new ArrayList<>();
        
        // Separamos en dos grupos según el signo del saldo
        while (keys.hasNext()) {
            OrgBalance ob = keys.next();
            BigDecimal saldo = ob.balance.getBalance();
            if (saldo.signum() > 0) {
                positivos.add(ob);
            } else if (saldo.signum() < 0) {
                negativos.add(ob);
            }
        }
        
        List<OrgBalance> resultado = new ArrayList<>();
        
        // Caso 1: Hay una única organización con saldo negativo y varias positivas
        if (negativos.size() == 1 && positivos.size() > 1) {
            // Se dejan las organizaciones positivas sin modificar
            resultado.addAll(positivos);
            // Se toma el único negativo y se reparte en tantos registros como positivos existan,
            // asignándole a cada uno un importe negativo igual al saldo de cada positivo.
            OrgBalance negUnico = negativos.get(0);
            for (OrgBalance pos : positivos) {
                // El monto a asignar es el mismo que el saldo del registro positivo
                BigDecimal monto = pos.balance.getBalance();
                // Se crea un Balance con DR=0 y CR = monto, de modo que getBalance() sea -monto
                Balance nuevoBalance = new Balance(BigDecimal.ZERO, monto);
                resultado.add(new OrgBalance(negUnico.org, nuevoBalance));
            }
        }
        // Caso 2: Hay una única organización con saldo positivo y varias negativas
        else if (positivos.size() == 1 && negativos.size() > 1) {
            // Se dejan las organizaciones negativas sin modificar
            resultado.addAll(negativos);
            // Se toma la única positiva y se reparte en tantos registros como negativos existan,
            // asignándole a cada uno un importe positivo igual al valor absoluto del saldo negativo.
            OrgBalance posUnico = positivos.get(0);
            for (OrgBalance neg : negativos) {
                BigDecimal monto = neg.balance.getBalance().abs();
                // Se crea un Balance con DR = monto y CR = 0, de modo que getBalance() sea monto
                Balance nuevoBalance = new Balance(monto, BigDecimal.ZERO);
                resultado.add(new OrgBalance(posUnico.org, nuevoBalance));
            }
        }
        // Caso 3: O bien ambos grupos tienen un solo registro o ambos tienen varios registros.
        // En este ejemplo se devuelven los registros sin modificar (se podría extender la lógica según el requerimiento).
        else {
            resultado.addAll(positivos);
            resultado.addAll(negativos);
        }
        
        return resultado;
    }
    
    /**
     * Método para filtrar la lista resultante de la distribución.
     * Agrupa por el monto exacto (balance.getBalance()) y, para cada grupo,
     * si todos los registros tienen el mismo parent org, se excluyen del resultado.
     */
    public static List<OrgBalance> filtrarPorMontoYPadre(List<OrgBalance> distribuidos) {
        Map<BigDecimal, List<OrgBalance>> grupos = new HashMap<>();

        // Agrupar por monto exacto
        for (OrgBalance ob : distribuidos) {
            BigDecimal monto = ob.balance.getBalance().abs();
            grupos.computeIfAbsent(monto, k -> new ArrayList<>()).add(ob);
        }

        List<OrgBalance> resultado = new ArrayList<>();
        int groupQty = 0;
        // Iterar sobre cada grupo
        for (Map.Entry<BigDecimal, List<OrgBalance>> entry : grupos.entrySet()) {
            List<OrgBalance> grupo = entry.getValue();
            groupQty = grupo.size();
            // Si hay varios, verificamos si todos tienen el mismo parent
            int parent = grupo.get(0).getParent();
            boolean mismosPadres = true;
            for (OrgBalance ob : grupo) {
                if (ob.getParent() != parent) {
                	grupo.get(0).setBPartnerID(ob.getBPartnerOrg());
                	grupo.get(1).setBPartnerID(grupo.get(0).getBPartnerOrg());
                    mismosPadres = false;
                    break;
                }
            }
            // Si NO todos tienen el mismo parent o hay un solo grupo, conservamos los registros
            if(groupQty==1) {
            	parent = distribuidos.get(0).getParent();
            	for (OrgBalance ob : distribuidos) {
            		if(ob.getParent()!=parent) {
            			distribuidos.get(0).setBPartnerID(ob.getBPartnerOrg());
            			distribuidos.get(1).setBPartnerID(distribuidos.get(0).getBPartnerOrg());
                        mismosPadres = false;
                        break;
            		}
            	}
            }
            if (!mismosPadres) {
                resultado.addAll(grupo);
            }
            // Caso contrario (todos tienen el mismo padre): se excluyen del resultado.
        }
        return resultado;
    }
    
	
    // Clase auxiliar para agrupar la organización y su Balance
    public static class OrgBalance {
        public int org;
        public Balance balance;
        public int parentorg;
        public int bPartnerOrg;
        public int bPartnerID;
        
        public OrgBalance(int org, Balance balance) {
            this.org = org;
            this.balance = balance;
            setParent(org);
            setBPartnerOrg(org);
        }
        
        private void setParent(int org) {
        	this.parentorg = DB.getSQLValue(null, "SELECT COALESCE(Parent_Org_ID,0) FROM AD_OrgInfo WHERE AD_Org_ID = ?", org);
        }
        
        public void setBPartnerID(int bpID) {
        	this.bPartnerID = bpID;
        }
        
        public int getBPartnerID() {
        	return bPartnerID;
        }
        
        public void setBPartnerOrg(int org) {
        	this.bPartnerOrg = DB.getSQLValue(null,"SELECT COALESCE(C_BPartner_ID,0) FROM C_BPartner WHERE TaxID = (SELECT TaxID FROM AD_OrgInfo WHERE AD_Org_ID = ?)",org);
        }
        
        public int getBPartnerOrg() {
        	return bPartnerOrg;
        }
        
        public int getParent() {
        	return parentorg;
        }
        
        @Override
        public String toString() {
            return "OO=" + org + ", Balance=" + balance+" , bpOrg=" + bPartnerOrg+" , bpID="+ bPartnerID;
        }
    }

	/**
	 * 	Fact Balance Utility
	 *																																																											
	 *  @author Jorg Janke
	 *  @version $Id: Fact.java,v 1.2 2006/07/30 00:53:33 jjanke Exp $
	 */
	public static class Balance
	{
		/**
		 * 	New Balance
		 *	@param dr DR
		 *	@param cr CR
		 */
		public Balance (BigDecimal dr, BigDecimal cr)
		{
			DR = dr;
			CR = cr;
		}
		
		/** DR Amount	*/
		public BigDecimal DR = Env.ZERO;
		/** CR Amount	*/
		public BigDecimal CR = Env.ZERO;
		
		/**
		 * 	Add 
		 *	@param dr DR
		 *	@param cr CR
		 */
		public void add (BigDecimal dr, BigDecimal cr)
		{
			DR = DR.add(dr);
			CR = CR.add(cr);
		}
		
		/**
		 * 	Get Balance
		 *	@return balance
		 */
		public BigDecimal getBalance()
		{
			return DR.subtract(CR);
		}	//	getBalance
		
		/**
		 * 	Get Post Balance
		 *	@return absolute balance - negative if reversal
		 */
		public BigDecimal getPostBalance()
		{
			BigDecimal bd = getBalance().abs();
			if (isReversal())
				return bd.negate();
			return bd;
		}	//	getPostBalance

		/**
		 * 	Zero Balance
		 *	@return true if 0
		 */
		public boolean isZeroBalance()
		{
			return getBalance().signum() == 0;
		}	//	isZeroBalance
		
		/**
		 * 	Reversal
		 *	@return true if both DR/CR are negative or zero
		 */
		public boolean isReversal()
		{
			return DR.signum() <= 0 && CR.signum() <= 0;
		}	//	isReversal
		
		/**
		 * 	String Representation
		 *	@return info
		 */
		public String toString ()
		{
			StringBuilder sb = new StringBuilder ("Balance[");
			sb.append ("DR=").append(DR)
				.append ("-CR=").append(CR)
				.append(" = ").append(getBalance())
				.append ("]");
			return sb.toString ();
		} //	toString
		
	}	//	Balance
	
}   //  Fact