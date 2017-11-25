package com.tgrape.sync;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;
import com.tgrape.sync.obj.MarketDay;
import com.tgrape.sync.obj.StockName;
import com.tgrape.tools.BackMarketParser;
import com.tgrape.tools.MarketGet;
import com.tgrape.tools.MarketParser;
import com.tgrape.tools.SinaMarketParser;

public class MarketHistSync implements ISync{

	private static Logger logger = LogManager.getLogger(MarketHistSync.class);
	private MarketGet mg = new MarketGet();
	private String hurl = "sina";
	private int startDate = -1;
	public MarketHistSync(){
		//do nothing
	}
	public MarketHistSync(int daynum){
		this.startDate = 0-daynum+1;
	}
	
	public void sync(String url) {
		logger.info("------- sync marcket!");
		MarketParser parser = null;
		if(null!=url)
			hurl = url;
		if(hurl.compareToIgnoreCase("sina")==0)	
			parser = new SinaMarketParser();
		else 
			parser = new BackMarketParser();
		
		SqlExecutor se = new SqlExecutor();
		
		List<String> sl = se.listAllStocks();
		String startDate = this.getStartDate();
		String endDate = this.getEndDate();
		List<String> stockNonameList = se.listNonameStocks();

		List<String> isqlList = new ArrayList<String>();
		for(String stock : sl){
			logger.info("----begin to sync market for stock "+stock);
			Map<String,String> params = new HashMap<String,String>();
			params.put("stock", stock);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			List<MarketDay> mdlist = mg.getMarketHist(url, params, parser );
			
			if(stockNonameList.contains(stock)){
				MarketDay md  = mg.getMarketToday(url, params, parser);			
				StockName sn = new StockName(stock,md.STKNAME);
				se.execute( sn.update() );
			}
			
			
			if(null!=mdlist && mdlist.size()>0)
				for(int i=mdlist.size()-1;i>=0;i--){
					isqlList.add( mdlist.get(i).insert() );
				}
		
			logger.info("----end to get market sql for stock "+stock);
		}
		for(String sql : isqlList)
			se.execute(sql);
		logger.info("----end to exe market sql for all stock ");
		se.close();
	}

	private String getEndDate() {
		Format format = new SimpleDateFormat("yyyyMMdd");
		String d = format.format(new Date());		 
		return d;
	}

	@SuppressWarnings("static-access")
	private String getStartDate() {
//		Format format = new SimpleDateFormat("yyyyMMdd");
//		Integer d = Integer.parseInt( format.format( new Date() ) );
//		if(isFirst)
//			d = d-20000;
		Date date=new Date();//取时间  
		Calendar calendar = new GregorianCalendar();  
		calendar.setTime(date);  
		calendar.add(calendar.DATE,startDate);//把日期往后增加一天.整数往后推,负数往前移动  
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果   
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		String dateString = formatter.format(date); 
		return dateString;
	}

	public void beginSync() {
		logger.info("------- before sync marcket hist");

	}

	public void endSync() {
		logger.info("------- end sync marcket hist");

	}
	
	public static void main(String[] args) {
		ISync ss = new MarketHistSync();
		ss.beginSync();
		ss.sync(null);
		ss.endSync();
	}
	 

}
