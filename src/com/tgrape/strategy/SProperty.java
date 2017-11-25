package com.tgrape.strategy;

import java.util.List;

public abstract class SProperty {
	
	public String SCODE;
	public String NAME;
	public String STGDESC;
	
	public abstract boolean hit(List<MarketDayProperty> mdplist) ;

}
