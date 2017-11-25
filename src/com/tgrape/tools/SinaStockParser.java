package com.tgrape.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;
import com.tgrape.sync.obj.MarketDay;
import com.tgrape.sync.obj.T_Stock;

/**
 * 
 * @author grape
 * <br>get all stock from sina
 */
public class SinaStockParser implements StockParser {
	private static Logger logger = LogManager.getLogger(SinaStockParser.class);

	private MyHttpGet shg   = new SinaHttpGet();
	//http://quote.eastmoney.com/stock_list.html
	public List<T_Stock> parse(String url) {
		logger.info("------------- begin to parse Stock from url "+url);
		String stockS = null;
		if(null!=url)
			stockS = shg.getHttp(url, null);
		else
			stockS = shg.getHttp("http://quote.eastmoney.com/stock_list.html",null);
		if(null==stockS){
			logger.info("------------- end to parse Stock from url "+url);
			return null;
		}else{
			List<T_Stock> tlist = this.getStockList(stockS);
			logger.info("------------- end to parse Stock from url "+url);
			return tlist;
		}
		
	}

	public List<T_Stock> getStockList(String html){
		//logger.info("------------- begin to get StockList from html "+html);
        List<T_Stock> ls=new ArrayList<T_Stock>();
        //<li><a target="_blank" href="http://quote.eastmoney.com/sz300616.html">???¡¤????(300616)</a></li>
        Pattern pattern = Pattern.compile("(?<=.html\">)(.+?)(?=</a></li>)");
        Matcher matcher = pattern.matcher(html);
        SqlExecutor se = new SqlExecutor();        
        List<String> slist = se.listAllStocks();
        Map<String,String> params = new HashMap<String,String>();
        while(matcher.find()){
        	String tmp = matcher.group();
        	if( tmp.contains("(") && tmp.contains(")") ){
        		tmp = tmp.substring(tmp.indexOf("(")+1, tmp.indexOf(")"));
        	}
        	
        	if(!slist.contains(tmp) && null!=tmp && tmp.length()==6 && (tmp.startsWith("30") ||tmp.startsWith("00") || tmp.startsWith("60"))){
        		T_Stock ts = new T_Stock();
        		ts.setSTKCODE(tmp);
        		params.put("stock", tmp);
        		MarketDay md = new SinaMarketParser().parse(null, params);
        		ts.setSTKNAME(md.STKNAME);
        		logger.info("got one stock "+tmp+":"+md.STKNAME);
        		ls.add(ts);
        	}
        }
        logger.info("------------- end to get StockList from html ");
        return ls;
    }
}
