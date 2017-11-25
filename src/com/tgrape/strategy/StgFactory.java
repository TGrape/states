package com.tgrape.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StgFactory {
	private static Logger logger = LogManager.getLogger(StgFactory.class);
	public static SProperty create(String scode) {
		SProperty sp = null;
		if(scode.compareTo("QSSZ")==0){
			logger.info("----强势上涨策略选股");
			sp = new QSSZStrategy();
		}else if(scode.compareTo("WFX")==0){
			logger.info("----无风险策略选股");
			sp = new WFXStrategy();
		}else if(scode.compareTo("TP18")==0){
			logger.info("----突破18策略选股");
			sp = new TP18Strategy();
		}else if(scode.compareTo("TP54")==0){
			logger.info("----突破54策略选股");
			sp = new TP54Strategy();
		}else if(scode.compareTo("QW")==0){
			logger.info("----企稳策略选股");
			sp = new QWStrategy();
		}else if(scode.compareTo("6LY")==0){
			logger.info("----六连阳策略");
			sp = new SixSzStrategy();
		}else if(scode.equalsIgnoreCase("BuyPnt")){
			logger.info("----超级买点策略");
			sp = new BuyPointStrategy();
		}
	
		return sp;
	}

}
