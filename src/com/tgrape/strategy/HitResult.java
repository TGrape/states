package com.tgrape.strategy;

public class HitResult {
	
	public String STGCODE;
	public String STKCODE;
	public float TURNOVER3;
	public float TURNOVER6;
	

	
	public String insert() {
		String isql = "insert into T_STG_REC(STGCODE,STKCODE,TURNOVER3,TURNOVER6,CREATE_DATE) values("
				+ "'"+STGCODE+"','"+STKCODE+"',"+TURNOVER3+","+TURNOVER6+",sysdate)";
		return isql;
	}

}
