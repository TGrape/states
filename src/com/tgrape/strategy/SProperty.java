package com.tgrape.strategy;

import java.util.List;

public abstract class SProperty {
	
	public String SCODE;
	public String NAME;
	public String STGDESC;
	
	public abstract boolean hit(List<MarketDayProperty> mdplist) ;

	protected boolean lowest(int num,List<MarketDayProperty> mdplist) {
		float p0 = mdplist.get(0).P_LOW;
		for(int i=1;i<mdplist.size()&&i<3;i++)
			if(mdplist.get(i).P_LOW<p0)
				p0 = mdplist.get(i).P_LOW;
		for(int i=3;i<num+1&&i<mdplist.size();i++){
			if(p0>mdplist.get(i).P_END){
				return false;
			}
		}
		return true;
	}
	protected boolean max(int num,List<MarketDayProperty> mdplist) {
		float p0 = mdplist.get(0).P_HIGH;
		for(int i=1;i<num+1&&i<mdplist.size();i++){
			if(p0<mdplist.get(i).P_HIGH){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ÉÏÓ°Ïß
	 * @param mdplist
	 * @return
	 */
	protected boolean shangying(List<MarketDayProperty> mdplist) {
		for(int i=0;i<3;i++){
			float high = mdplist.get(i).P_HIGH;
			float low = mdplist.get(i).P_END;
			if(mdplist.get(i).P_START < low)
				low = mdplist.get(i).P_START;
			if(high>(mdplist.get(i).P_START*0.04+low))
				return true;
		}
		return false;
	}
}
