package com.tgrape.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.sync.obj.MarketDay;

public class SinaMarketParser implements MarketParser {

	private static Logger logger = LogManager.getLogger(SinaMarketParser.class);

	private MyHttpGet shg   = new SinaHttpGet();
	public MarketDay parse(String url,Map<String,String> params) {
		/**
		http://hq.sinajs.cn/list=sh601006
			这个url会返回一串文本，例如：
			var hq_str_sh601006="大秦铁路, 27.55, 27.25, 26.91, 27.55, 26.20, 26.91, 26.92, 
			22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89, 14300,
			26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150, 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
			这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
			0：”大秦铁路”，股票名字；
			1：”27.55″，今日开盘价；
			2：”27.25″，昨日收盘价；
			3：”26.91″，当前价格；
			4：”27.55″，今日最高价；
			5：”26.20″，今日最低价；
			6：”26.91″，竞买价，即“买一”报价；
			7：”26.92″，竞卖价，即“卖一”报价；
			8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
			9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
			10：”4695″，“买一”申请4695股，即47手；
			11：”26.91″，“买一”报价；
			12：”57590″，“买二”
			13：”26.90″，“买二”
			14：”14700″，“买三”
			15：”26.89″，“买三”
			16：”14300″，“买四”
			17：”26.88″，“买四”
			18：”15100″，“买五”
			19：”26.87″，“买五”
			20：”3100″，“卖一”申报3100股，即31手；
			21：”26.92″，“卖一”报价
			(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
			30：”2008-01-11″，日期；
			31：”15:05:32″，时间；
			*/
		String stock = params.get("stock");
		stock = this.pStock(stock);
		String u = "http://hq.sinajs.cn/list="+stock;
		String shtml = shg.httpReturn(u);
		String rt = shtml.substring(shtml.indexOf("\"")+1,shtml.lastIndexOf("\""));
		String[] slist = rt.split(",");
		MarketDay md = new MarketDay();
		if(slist.length>0){
			md.STKNAME = slist[0];
		}
		md.STKCODE = params.get("stock");
		return md;
	}
	public List<MarketDay> parseHist(String url,Map<String,String> params) {
		//http://q.stock.sohu.com/hisHq?code=cn_601766&start=20140101&end=20140414
		//[{"status":0,"hq":[["2014-04-14","4.70","4.65","-0.05","-1.06%","4.62","4.70","406770","18940.69","0.40%"],
		//["2014-04-11","4.82","4.70","-0.13","-2.69%","4.68","4.82","759950","35928.29","0.75%"]
		/**
		 * 0 日期
		 * 1 开盘
		 * 2 收盘
		 * 3 价格涨跌
		 * 4 价格涨跌幅
		 * 5 最低价
		 * 6 最高价
		 * 7 交易量手
		 * 8 交易金额
		 * 9 换手率
		 */
		logger.info("----- begin to parse hist of "+params.get("stock"));
		List<MarketDay> mdlist = new ArrayList<MarketDay>();
		if(params.containsKey("startDate")&&params.containsKey("endDate")){
			String stock = params.get("stock");
			String startDate = params.get("startDate");
			String endDate = params.get("endDate");
			String u = "http://q.stock.sohu.com/hisHq?code=cn_"+stock+"&start="+startDate+"&end="+endDate;
			String shtml = shg.httpReturn(u);
			Pattern pattern = Pattern.compile("(?<=\\[\")(.+?)(?=\\])",1);//(?<=\\[)(\\S+)(?=\\])
		    Matcher matcher = pattern.matcher(shtml);
		    while(matcher.find()){
		    	MarketDay md = new MarketDay();
	        	String tmp = matcher.group();
	        	tmp = tmp.replaceAll("\"", "");
	        	tmp = tmp.replaceAll("%", "");
	        	tmp = tmp.replaceAll("-", "");
	        	String[] dl = tmp.split(",");
	        	if(dl.length>=10){
	        		md.YEARMMDD = dl[0];//日期
	        		md.DAY_VOLUMN = dl[8];//交易量
	        		md.P_END = dl[2];//收盘价
	        		md.P_START = dl[1];//开盘价
	        		md.P_HIGH = dl[6];//最高价
	        		md.P_LOW = dl[5];//最低价
	        		md.TURN_OVER = dl[9];//换手率
	        		md.STKCODE = params.get("stock");
	        		mdlist.add(md);
	        	}        	
	        }
		    if(mdlist.size()<1)
				logger.info("html "+u+" result : "+shtml);
		}
		logger.info("----- end to parse hist of "+params.get("stock"));
		
		return mdlist;		
	}
	private String pStock(String stock) {
		String ss = null;
		if(stock.startsWith("60"))
			ss = "sh"+stock;
		else 
			ss = "sz"+stock;
		return ss;
	}

}
