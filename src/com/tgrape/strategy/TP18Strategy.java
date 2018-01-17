package com.tgrape.strategy;

import java.util.List;

public class TP18Strategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<18)
			return false;
		if(!this.turnover30(mdplist))
			return false;
		if(this.shangying(mdplist))
			return false;
		if(this.lowest(15,mdplist))
			return false;
		if(!this.tp18(mdplist) ){
			return false;
		}
		if(!maxTurnover(18,mdplist)){
			return false;
		}
		
		return true;
	}


	private boolean tp18(List<MarketDayProperty> mdplist) {
		float max6 = 0;
		for(int i=0;i<6;i++){
			if(mdplist.get(i).P_HIGH>max6)
				max6 = mdplist.get(i).P_HIGH;
		}
		float max18 = 0;
		for(int i=6;i<24&&mdplist.size()>24;i++){
			if(mdplist.get(i).P_HIGH>max18)
				max18 = mdplist.get(i).P_HIGH;
		}
		if(max6>max18*0.986)
			return true;
		return false;
	}

	private boolean turnover30(List<MarketDayProperty> mdplist) {
		float sum = 0f;
		for(int i=0;i<3;i++){
			sum += mdplist.get(i).TURN_OVER;
		}
		if(sum>10)
			return true;
		if(mdplist.get(0).P_END>=mdplist.get(1).P_END*1.09
				|| mdplist.get(1).P_END>=mdplist.get(2).P_END*1.09) 
			return true;
		return false;
	}


}
