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
			���url�᷵��һ���ı������磺
			var hq_str_sh601006="������·, 27.55, 27.25, 26.91, 27.55, 26.20, 26.91, 26.92, 
			22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89, 14300,
			26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150, 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
			����ַ������������ƴ����һ�𣬲�ͬ����������ö��Ÿ����ˣ����ճ���Ա��˼·��˳��Ŵ�0��ʼ��
			0����������·������Ʊ���֣�
			1����27.55�壬���տ��̼ۣ�
			2����27.25�壬�������̼ۣ�
			3����26.91�壬��ǰ�۸�
			4����27.55�壬������߼ۣ�
			5����26.20�壬������ͼۣ�
			6����26.91�壬����ۣ�������һ�����ۣ�
			7����26.92�壬�����ۣ�������һ�����ۣ�
			8����22114263�壬�ɽ��Ĺ�Ʊ�������ڹ�Ʊ������һ�ٹ�Ϊ������λ��������ʹ��ʱ��ͨ���Ѹ�ֵ����һ�٣�
			9����589824680�壬�ɽ�����λΪ��Ԫ����Ϊ��һĿ��Ȼ��ͨ���ԡ���Ԫ��Ϊ�ɽ����ĵ�λ������ͨ���Ѹ�ֵ����һ��
			10����4695�壬����һ������4695�ɣ���47�֣�
			11����26.91�壬����һ�����ۣ�
			12����57590�壬�������
			13����26.90�壬�������
			14����14700�壬��������
			15����26.89�壬��������
			16����14300�壬�����ġ�
			17����26.88�壬�����ġ�
			18����15100�壬�����塱
			19����26.87�壬�����塱
			20����3100�壬����һ���걨3100�ɣ���31�֣�
			21����26.92�壬����һ������
			(22, 23), (24, 25), (26,27), (28, 29)�ֱ�Ϊ���������������ĵ������
			30����2008-01-11�壬���ڣ�
			31����15:05:32�壬ʱ�䣻
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
		 * 0 ����
		 * 1 ����
		 * 2 ����
		 * 3 �۸��ǵ�
		 * 4 �۸��ǵ���
		 * 5 ��ͼ�
		 * 6 ��߼�
		 * 7 ��������
		 * 8 ���׽��
		 * 9 ������
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
	        		md.YEARMMDD = dl[0];//����
	        		md.DAY_VOLUMN = dl[8];//������
	        		md.P_END = dl[2];//���̼�
	        		md.P_START = dl[1];//���̼�
	        		md.P_HIGH = dl[6];//��߼�
	        		md.P_LOW = dl[5];//��ͼ�
	        		md.TURN_OVER = dl[9];//������
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
