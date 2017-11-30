package com.tgrape.strategy;

import java.util.List;

public class SixSzStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<30)
			return false;
		float hs = huanshou6(mdplist);
		if(hs>30)
			return false;
		
		if(max3(30,mdplist))
			return false;
		
		if(!this.sixLY(mdplist))
			return false;
		
		
		return true;
	}

	private boolean max3(int num, List<MarketDayProperty> mdplist) {
		float max = mdplist.get(0).P_HIGH;
		for(int i=0;i<num+1&&i<mdplist.size();i++){
			if(i<3){
				if( mdplist.get(i).P_HIGH>max){
					max = mdplist.get(i).P_HIGH;
				}
			}else{
				if(mdplist.get(i).P_END>max)
					return false;
			}
				
		}
		return true;
	}

	private float huanshou6(List<MarketDayProperty> mdplist) {
		float hs = 0;
		for(int i=0;i<6;i++)
			hs += mdplist.get(i).TURN_OVER;
		return hs;
	}
	
	

	/**
	 * 六连阳策略
	 * @param mdplist
	 * @return
	 */
	private boolean sixLY(List<MarketDayProperty> mdplist) {
		float low = mdplist.get(0).P_LOW;
		float high = mdplist.get(0).P_HIGH;
		int lower_num = 0;
		int higher_num = 0;
		for(int i=0; i<6; i++){
			if(low > mdplist.get(i).P_LOW)
				low = mdplist.get(i).P_LOW;
			if(high < mdplist.get(i).P_HIGH)
				high = mdplist.get(i).P_HIGH;
			if(mdplist.get(i).P_END<mdplist.get(i).P_START){
				lower_num++;
			}
			if(mdplist.get(i).P_HIGH>mdplist.get(i+1).P_END){
				higher_num++;
			}
		}
		//下跌数量>1
		if(lower_num>0)
			return false;
		//上涨数量<5
		if(higher_num<6)
			return false;
		//上涨超过20%
		if(high>low*1.30)
			return false;
		return true;
	}

	
}
