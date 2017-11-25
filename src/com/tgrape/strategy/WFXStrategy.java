package com.tgrape.strategy;

import java.util.List;

public class WFXStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<54){
			return false;
		}
		if(mdplist.get(0).P_HIGH>mdplist.get(30).P_LOW){
			return false;
		}
		if(this.xd36per(mdplist)&& this.xy2per(mdplist)){
			return true;
		}
		
		return false;
	}

	/**
	 * 6天内有-2%下影线
	 * @param mdplist
	 * @return
	 */
	private boolean xy2per(List<MarketDayProperty> mdplist) {
		for(int i=0;i<mdplist.size()&&i<6;i++){
			if(mdplist.get(i).P_END-mdplist.get(i).P_LOW>(mdplist.get(i+1).P_END*0.03))
				return true;
		}
		return false;
	}

	/**
	 * 下跌>36
	 * @param mdplist
	 * @return
	 */
	private boolean xd36per(List<MarketDayProperty> mdplist) {
		float max54 = this.max54(mdplist);
		float min6 = this.min6(mdplist);
		if(max54*0.6>min6)
			return true;
		else
			return false;
	}

	private float min6(List<MarketDayProperty> mdplist) {
		float min6 = 0.0f;
		for(int i=0;i<mdplist.size()&&i<6;i++){
			if(mdplist.get(i).P_LOW<min6)
				min6 = mdplist.get(i).P_LOW;
		}
		return min6;
	}

	private float max54(List<MarketDayProperty> mdplist) {
		float max54 = 0.0f;
		for(int i=0;i<mdplist.size()&&i<54;i++){
			if(mdplist.get(i).P_HIGH>max54)
				max54 = mdplist.get(i).P_HIGH;
		}
		return max54;
	}

}
