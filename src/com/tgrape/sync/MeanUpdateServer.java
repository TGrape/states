package com.tgrape.sync;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;
import com.tgrape.sync.obj.MDHist;

public class MeanUpdateServer implements ISync{
	
	private  SqlExecutor se = new SqlExecutor();
	private static Logger logger = LogManager.getLogger(MeanUpdateServer.class);
	private int datecount = 1;//-1求所有；
	public MeanUpdateServer(){
		// do nothing
	}
	public MeanUpdateServer(int daynum){
		this.datecount = daynum;
	}
	public void beginSync() {
		logger.info("begin to update MarketDay");
	}

	public void sync(String url) {
		List<String> sl = se.listAllStocks();
		String startDate = getStartDate();
		String endDate = getEndDate();
		for(String stock : sl){
			logger.info("----begin to update Mean for stock {}, time {}-{} ",stock, startDate, endDate);
			MDHist mdh = null;
			List<String> sqllist = null;
			if(datecount!=-1){
				mdh = se.listHistMarket(stock, startDate, endDate, datecount);
				sqllist = mdh.updateCount(startDate, endDate, datecount);
			}else{
				mdh = se.listAllHistMarket(stock);
				mdh.mean();
				sqllist = mdh.update();
			}
			
			for(int i=0;i<sqllist.size();i++){
				se.execute(sqllist.get(i));
			}
			logger.info("----end to update Mean for stock{}, time {}-{} ",stock, startDate, endDate);
		}		
		se.close();
	}

	private String getEndDate() {
		Format format = new SimpleDateFormat("yyyyMMdd");
		String d = format.format(new Date());		 
		return d;
	}

	private String getStartDate() {
		
		Date date=new Date();//取时间  
		Calendar calendar = new GregorianCalendar();  
		calendar.setTime(date);  
		calendar.add(Calendar.DATE,0-datecount);//把日期往后增加一天.整数往后推,负数往前移动  
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果   
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		String dateString = formatter.format(date); 
		return dateString;
	}

	public void endSync() {
		logger.info("end to update MarketDay");
	}

	public static void main(String[] args) {
		ISync ss = new MeanUpdateServer();
		ss.beginSync();
		ss.sync(null);
		ss.endSync();
	}
	 
}
