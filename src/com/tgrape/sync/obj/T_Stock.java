package com.tgrape.sync.obj;

public class T_Stock implements Operation {

	/*
	 *  STKCODE	VARCHAR2(16),
		STKNAME	VARCHAR2(64),
		HIST_HIGH	NUMBER(*,2),
		HIST_LOW	NUMBER(*,2),
		HIST_VOLUMN_MAX	NUMBER(*,2),
		HIST_VOLUMN_MIN	NUMBER(*,2),
		HIST_10RATE_COUNT	NUMBER(2),
		UPDATE_TIME	DATE
	 */
	private String STKCODE = null;
	private String STKNAME = null;
	private String HIST_HIGH = null;
	private String HIST_LOW = null;
	private String HIST_VOLUMN_MAX = null;
	private String HIST_VOLUMN_MIN = null;
	private String HIST_10RATE_COUNT = null;
	/**
	 * 更新时间  YYYY-MM-DD HH24:MI:SS
	 */
	private String UPDATE_TIME = null;
	
	
	//insert into SY_COMM_CONFIG(CONF_ID, S_MTIME ) values('2Zt45FyiB83FTOdnskZ3',to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') 
	public String insert() {
		String is = null;
		if(null!=STKCODE){
			if(null!=UPDATE_TIME)
				is = "insert into T_Stock(STKCODE, UPDATE_TIME ) values('"+STKCODE+"',to_date("+UPDATE_TIME+",'YYYY-MM-DD HH24:MI:SS')) ";
			else
				is = "insert into T_Stock(STKCODE, UPDATE_TIME) values ('"+STKCODE+"',sysdate) ";
		}
		return is;
	}
	public String update() {
		String is = null;
		if(null!=STKCODE){			
				is = "update T_Stock set  STKNAME = '"+STKNAME+"' where STKCODE='"+STKCODE+"'";
		}
		return is;
	}

	public String getSTKCODE() {
		return STKCODE;
	}


	public void setSTKCODE(String sTKCODE) {
		STKCODE = sTKCODE;
	}


	public String getSTKNAME() {
		return STKNAME;
	}


	public void setSTKNAME(String sTKNAME) {
		STKNAME = sTKNAME;
	}


	public String getHIST_HIGH() {
		return HIST_HIGH;
	}


	public void setHIST_HIGH(String hIST_HIGH) {
		HIST_HIGH = hIST_HIGH;
	}


	public String getHIST_LOW() {
		return HIST_LOW;
	}


	public void setHIST_LOW(String hIST_LOW) {
		HIST_LOW = hIST_LOW;
	}


	public String getHIST_VOLUMN_MAX() {
		return HIST_VOLUMN_MAX;
	}


	public void setHIST_VOLUMN_MAX(String hIST_VOLUMN_MAX) {
		HIST_VOLUMN_MAX = hIST_VOLUMN_MAX;
	}


	public String getHIST_VOLUMN_MIN() {
		return HIST_VOLUMN_MIN;
	}


	public void setHIST_VOLUMN_MIN(String hIST_VOLUMN_MIN) {
		HIST_VOLUMN_MIN = hIST_VOLUMN_MIN;
	}


	public String getHIST_10RATE_COUNT() {
		return HIST_10RATE_COUNT;
	}


	public void setHIST_10RATE_COUNT(String hIST_10RATE_COUNT) {
		HIST_10RATE_COUNT = hIST_10RATE_COUNT;
	}


	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}


	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}


	

}
