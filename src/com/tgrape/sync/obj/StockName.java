package com.tgrape.sync.obj;

public class StockName {
	public String STKCODE = "";
	public String STKNAME = "";
	
	public StockName(String sc, String sn){
		this.STKCODE = sc;
		this.STKNAME = sn;
	}
	public String update() {
		String sql = "update T_Stock set STKNAME='"+this.STKNAME+"' where STKCODE='"+this.STKCODE+"' ";
		return sql;
	}

	
}
