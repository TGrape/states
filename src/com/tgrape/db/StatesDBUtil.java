package com.tgrape.db;

public class StatesDBUtil {
	
	public static int getDateNumNoRefresh(){
		SqlExecutor se = new SqlExecutor();
		int datenum = se.queryNofreshDate();
		se.close();
		return datenum;
	}

}
