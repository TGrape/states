package com.tgrape.strategy;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;
import com.tgrape.sync.ISync;

public class StrategyComputeServer implements ISync,Runnable{
	private static Logger logger = LogManager.getLogger(StrategyComputeServer.class);

	public void hit(){
		String dateToday = this.getCurDate();
		SqlExecutor se = new SqlExecutor();
		List<String> stklist= se.listAllStocks();
		List<String> stklistnohist = se.listNoHistStocks();
		stklist.removeAll(stklistnohist);
		List<SProperty> splist = StgGet.getStg();
		List<HitResult> hrlist = new ArrayList<HitResult>();
 		for(String stock : stklist){
 			List<MarketDayProperty> mdplist = se.listAllHistMarket(stock,dateToday);
 			for(SProperty sp : splist){
 				if(this.hit(sp, mdplist)){
 					HitResult hr = new HitResult();
 					hr.STGCODE = sp.SCODE;
 					hr.STKCODE = stock;
 					hr.TURNOVER3 = sum(mdplist,3);
 					hr.TURNOVER6 = sum(mdplist,6);
 					hrlist.add(hr);
 					logger.info("~_~ congratulation! stock {} hit strategy {} ",stock,sp.NAME);
 				}else{
 					logger.info(" sorry! stock {} does not hit strategy {} ",stock,sp.NAME);
 				}
 			}
		}
 		logger.info("Strategy hit stock num : {}",hrlist.size());
 		String sql  = null;
 		for(HitResult hr : hrlist){
 			sql = hr.insert();
 			if(sql!=null)
 				se.execute(sql);
 		}
 		logger.info("今天可关注股票量:{}",hrlist.size());
	}

	private float sum(List<MarketDayProperty> mdplist, int count) {
		float sum = 0.0f;
		if(count<mdplist.size()){
			for(int i=0;i<count;i++)
				sum += mdplist.get(i).TURN_OVER;
		}
		return sum;
	}

	private boolean hit(SProperty sp, List<MarketDayProperty> mdplist) {		
		return sp.hit(mdplist);
	}

	private String getCurDate() {
		Format format = new SimpleDateFormat("yyyyMMdd");
		String d = format.format(new Date());		 
		return d;
	}

	public void beginSync() {
		logger.info("------- before compute stock hit");

	}

	public void endSync() {
		logger.info("------- end compute stock hit");

	}
	
	public static void main(String[] args) {
		ISync ss = new StrategyComputeServer();
		ss.beginSync();
		ss.sync(null);
		ss.endSync();
	}

	@Override
	public void sync(String url) {
		this.hit();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	 

}
