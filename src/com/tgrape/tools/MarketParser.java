package com.tgrape.tools;

import java.util.List;
import java.util.Map;

import com.tgrape.sync.obj.MarketDay;

public interface MarketParser {
	
	MarketDay parse(String url,Map<String,String> params);

	public List<MarketDay> parseHist(String url,Map<String,String> params);
}
