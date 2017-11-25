package com.tgrape.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StgFactory {
	private static Logger logger = LogManager.getLogger(StgFactory.class);
	public static SProperty create(String scode) {
		SProperty sp = null;
		if(scode.compareTo("QSSZ")==0){
			logger.info("----ǿ�����ǲ���ѡ��");
			sp = new QSSZStrategy();
		}else if(scode.compareTo("WFX")==0){
			logger.info("----�޷��ղ���ѡ��");
			sp = new WFXStrategy();
		}else if(scode.compareTo("TP18")==0){
			logger.info("----ͻ��18����ѡ��");
			sp = new TP18Strategy();
		}else if(scode.compareTo("TP54")==0){
			logger.info("----ͻ��54����ѡ��");
			sp = new TP54Strategy();
		}else if(scode.compareTo("QW")==0){
			logger.info("----���Ȳ���ѡ��");
			sp = new QWStrategy();
		}else if(scode.compareTo("6LY")==0){
			logger.info("----����������");
			sp = new SixSzStrategy();
		}else if(scode.equalsIgnoreCase("BuyPnt")){
			logger.info("----����������");
			sp = new BuyPointStrategy();
		}
	
		return sp;
	}

}
