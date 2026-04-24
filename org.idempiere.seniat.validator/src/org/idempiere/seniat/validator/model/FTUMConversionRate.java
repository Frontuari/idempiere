package org.idempiere.seniat.validator.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MConversionRate;
import org.compiere.model.MSysConfig;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

@org.adempiere.base.Model(table = "C_Conversion_Rate")
public class FTUMConversionRate extends MConversionRate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 968111285728893469L;

	public FTUMConversionRate(Properties ctx, int C_Conversion_Rate_ID, String trxName) {
		super(ctx, C_Conversion_Rate_ID, trxName);
	}

	public FTUMConversionRate(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public FTUMConversionRate(PO po, int C_ConversionType_ID, int C_Currency_ID, int C_Currency_ID_To,
			BigDecimal MultiplyRate, Timestamp ValidFrom) {
		super(po, C_ConversionType_ID, C_Currency_ID, C_Currency_ID_To, MultiplyRate, ValidFrom);
	}
	
	/**
	 * 	Before Save.
	 * 	- Same Currency
	 * 	- Date Range Check
	 * 	- Set To date to 2056
	 *	@param newRecord new
	 *	@return true if OK to save
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	From - To is the same
		if (getC_Currency_ID() == getC_Currency_ID_To())
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@C_Currency_ID@ = @C_Currency_ID@"));
			return false;
		}
		//	Nothing to convert
		if (getMultiplyRate().compareTo(Env.ZERO) <= 0)
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@MultiplyRate@ <= 0"));
			return false;
		}

		//	Date Range Check
		Timestamp from = getValidFrom();
		if (getValidTo() == null) {
			// setValidTo (TimeUtil.getDay(2056, 1, 29));	//	 no exchange rates after my 100th birthday
			log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_ValidTo));
			return false;
		}
		Timestamp to = getValidTo();
		
		if (to.before(from))
		{
			SimpleDateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			log.saveError("Error", df.format(to) + " < " + df.format(from));
			return false;
		}

		if (isActive()) {
			//	Added by Jorge Colmenarez, 2022-04-19 15:15
			boolean updatePreviousRate = MSysConfig.getBooleanValue("CONVERSION_RATE_UPDATE_PREV_RATE", false);
			if(updatePreviousRate && newRecord) {
				String sql = "SELECT C_Conversion_Rate_ID FROM "+MConversionRate.Table_Name+" WHERE C_ConversionType_ID="+getC_ConversionType_ID()
				+ " AND C_Currency_ID="+getC_Currency_ID()+ " AND C_Currency_ID_To="+getC_Currency_ID_To()+" AND ValidFrom<'"+getValidFrom()+"' "
						+ "AND AD_Org_ID="+getAD_Org_ID()+" AND AD_Client_ID="+getAD_Client_ID()+" ORDER BY ValidFrom DESC";
				int C_Conversion_Rate_ID = DB.getSQLValue(get_TrxName(), sql);
				if(C_Conversion_Rate_ID>0) {
					FTUMConversionRate prevRate = new FTUMConversionRate(getCtx(),C_Conversion_Rate_ID,get_TrxName());
					sql = "SELECT '"+getValidFrom()+"'::timestamp - '0.00001 second'::interval";
					prevRate.setValidTo(DB.getSQLValueTS(get_TrxName(), sql));
					prevRate.saveEx(get_TrxName());
				}
			} else {
			//	End Jorge Colmenarez
				String whereClause = "(? BETWEEN ValidFrom AND ValidTo OR ? BETWEEN ValidFrom AND ValidTo) "
						+ "AND C_Currency_ID=? AND C_Currency_ID_To=? "
						+ "AND C_Conversiontype_ID=? "
						+ "AND AD_Client_ID=? AND AD_Org_ID=?";
				List<FTUMConversionRate> convs = new Query(getCtx(), FTUMConversionRate.Table_Name, whereClause, get_TrxName())
						.setOnlyActiveRecords(true)
						.setParameters(getValidFrom(), getValidTo(), 
								getC_Currency_ID(), getC_Currency_ID_To(),
								getC_ConversionType_ID(),
								getAD_Client_ID(), getAD_Org_ID())
						.list();
				for (FTUMConversionRate conv : convs) {
					if (conv.getC_Conversion_Rate_ID() != getC_Conversion_Rate_ID()) {
						log.saveError("Error", "Conversion rate overlaps with: "	+ conv.getValidFrom());
						return false;
					}
				}
			}
		}

		return true;
	}	//	beforeSave

	private volatile static boolean recursiveCall = false;
	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (success && !recursiveCall) {
			String whereClause = "ValidFrom=? "//AND ValidTo=? " // Commented by Jorge Colmenarez, 2022-04-19 15:18
					+ "AND C_Currency_ID=? AND C_Currency_ID_To=? "
					+ "AND C_ConversionType_ID=? "
					+ "AND AD_Client_ID=? AND AD_Org_ID=?";
			FTUMConversionRate reciprocal = new Query(getCtx(), FTUMConversionRate.Table_Name, whereClause, get_TrxName())
					.setParameters(getValidFrom(), //getValidTo(), // Commented by Jorge Colmenarez, 2022-04-19 15:18
							getC_Currency_ID_To(), getC_Currency_ID(),
							getC_ConversionType_ID(),
							getAD_Client_ID(), getAD_Org_ID())
					.firstOnly();
			if (reciprocal == null) {
				// create reciprocal rate
				reciprocal = new FTUMConversionRate(getCtx(), 0, get_TrxName());
				reciprocal.setValidFrom(getValidFrom());
				reciprocal.setValidTo(getValidTo());
				reciprocal.setC_ConversionType_ID(getC_ConversionType_ID());
				reciprocal.setAD_Client_ID(getAD_Client_ID());
				reciprocal.setAD_Org_ID(getAD_Org_ID());
				// invert
				reciprocal.setC_Currency_ID(getC_Currency_ID_To());
				reciprocal.setC_Currency_ID_To(getC_Currency_ID());
			}
			// avoid recalculation
			reciprocal.set_Value(COLUMNNAME_DivideRate, getMultiplyRate());
			reciprocal.set_Value(COLUMNNAME_MultiplyRate, getDivideRate());
			recursiveCall = true;
			try {
				reciprocal.saveEx();
			} finally {
				recursiveCall = false;
			}
		}
		return success;
	}
	
	// Added by Marcos Reyes 2026-04-06 11:25
	// Make public the PO set client organization method through this wrapper
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID) {
	    super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}

}
