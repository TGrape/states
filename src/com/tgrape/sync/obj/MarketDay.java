package com.tgrape.sync.obj;

public class MarketDay implements Operation{


	public String STKCODE = null; 
	public String STKNAME = null;
	public String P_HIGH = null;
	public String P_LOW = null;
	public String P_START = null;
	public String P_END = null;
	public String YEARMMDD = null;
	public String DAY_VOLUMN = null;
	public String TURN_OVER = null;
	public String CUR_DATE = null;
	public String MEAN6 = null;
	public String MEAN18 = null;
	public String MEAN54 = null;
	public String MEAN180 = null;
	public String SCORE = null;
	

	public String insert() {
		String sql = "";
		if(null!=STKCODE)
			sql = "insert into T_Market_Day(STKCODE,YEARMMDD,P_HIGH,P_LOW,P_START,P_END,DAY_VOLUMN,TURN_OVER,CUR_DATE)"
					+ " values ('"+STKCODE+"', '"+YEARMMDD+"', "+P_HIGH+", "+P_LOW+", "+P_START+", "+P_END+", "+DAY_VOLUMN+", "+TURN_OVER+","
							+ "to_date('"+YEARMMDD+"','yyyymmdd') )";
		return sql;
	}

}
