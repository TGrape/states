package com.tgrape.sync;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;
import com.tgrape.sync.obj.T_Stock;
import com.tgrape.tools.SinaStockParser;
import com.tgrape.tools.StockParser;

public class StockSync implements ISync{

	private static Logger logger = LogManager.getLogger(StockSync.class);
	private String html = "http://quote.eastmoney.com/stocklist.html";
	private StockParser ssp = new SinaStockParser();
	SqlExecutor se = new SqlExecutor();
	//http://quote.eastmoney.com/stocklist.html
	public void sync(String url) {
		logger.info("------- syncing stock");
		List<T_Stock> tslist =  ssp.parse(html);
		String isql = null;
		for(T_Stock ts : tslist){
			isql = ts.insert();
			if(isql != null){
				se.execute(isql);				
			}
			isql = ts.update();
			if(isql != null){
				se.execute(isql);				
			}
		}
		
		se.close();
		
	}

	public void beginSync() {
		logger.info("------- before sync stock");
	}

	public void endSync() {
		logger.info("------- end sync stock");

	}
	
	public static void main(String[] args) {
		ISync ss = new StockSync();
		ss.beginSync();
		ss.sync(null);
		ss.endSync();
	}

}
