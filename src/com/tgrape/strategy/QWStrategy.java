package com.tgrape.strategy;

import java.util.List;

public class QWStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<30)
			return false;
		if(this.turnover6(mdplist)<3)
			return false;
		if(!this.lowest(3,mdplist) ){
			return false;
		}
		if(this.shangying(mdplist))
			return false;
		if(this.yinxian(3,mdplist)){
			return false;
		}
		if(this.lowest(18,mdplist))
			return false;
		if(!this.qw(mdplist))
			return false;
		
		return true;
	}

	private boolean yinxian(int num,List<MarketDayProperty> mdplist) {
		for(int i=1;i<num+1&&i<mdplist.size();i++){
			if(mdplist.get(i).P_END>mdplist.get(i).P_START)
				return false;
		}
		return true;
	}

	/**
	 * …œ”∞œﬂ
	 * @param mdplist
	 * @return
	 */
	private boolean shangying(List<MarketDayProperty> mdplist) {
		for(int i=0;i<3&&i<mdplist.size();i++){
			float high = mdplist.get(i).P_HIGH;
			float low = mdplist.get(i).P_END;
			if(mdplist.get(i).P_START < low)
				low = mdplist.get(i).P_START;
			if(high>(mdplist.get(i).P_START*0.035+low))
				return true;
		}
		return false;
	}
	private boolean qw(List<MarketDayProperty> mdplist) {
		float xy = mdplist.get(0).P_END;
		if(mdplist.get(0).P_END>mdplist.get(0).P_START)
				xy = mdplist.get(0).P_START;
		if(xy>(mdplist.get(0).P_LOW+mdplist.get(0).P_START*0.06))
			return true;
		if(xy<(mdplist.get(0).P_LOW+mdplist.get(0).P_START*0.03))
			return false;
		if(mdplist.get(0).P_END<mdplist.get(0).P_START*0.99)
			return false;
		return true;
	}

	private boolean lowest(int num,List<MarketDayProperty> mdplist) {
		float p0 = mdplist.get(0).P_LOW;
		for(int i=1;i<num+1&&i<mdplist.size();i++){
			if(p0>mdplist.get(i).P_START){
				return false;
			}
		}
		return true;
	}

	private float turnover6(List<MarketDayProperty> mdplist) {
		float t6 = 0;
		for(int i=0;i<6;i++)
			t6 += mdplist.get(i).TURN_OVER;
		return t6;
	}

	

}
