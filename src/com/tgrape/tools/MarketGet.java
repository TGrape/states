package com.tgrape.tools;

import java.util.List;
import java.util.Map;

import com.tgrape.sync.obj.MarketDay;

public class MarketGet {
	
	public MarketDay getMarketToday(String url, Map<String,String> params,MarketParser parser){		
		return parser.parse(url, params);
	}

	public List<MarketDay> getMarketHist(String url, Map<String, String> params, MarketParser parser) {
		return parser.parseHist(url, params);
	}

}
