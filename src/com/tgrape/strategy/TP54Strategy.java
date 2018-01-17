package com.tgrape.strategy;

import java.util.List;

public class TP54Strategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<54)
			return false;
		
		if(this.shangying(mdplist))
			return false;
		if(this.lowest(12,mdplist))
			return false;
		if(!this.tp54(mdplist) ){
			return false;
		}
		if(!maxTurnover(30,mdplist)){
			return false;
		}
		
		return true;
	}

	
	
	private boolean tp54(List<MarketDayProperty> mdplist) {
		float max8 = mdplist.get(0).P_HIGH;
		for(int i=1;i<8;i++){
			if(mdplist.get(i).P_HIGH>max8)
				max8 = mdplist.get(i).P_HIGH;
		}
		float max54 = 0;
		for(int i=8;i<60&&i<mdplist.size();i++){
			if(mdplist.get(i).P_HIGH>max54)
				max54 = mdplist.get(i).P_HIGH;
		}
		if(max8>max54)
			return true;
		return false;
	}

//	private boolean turnover30(List<MarketDayProperty> mdplist) {
//		float sum = 0f;
//		for(int i=0;i<3;i++){
//			sum += mdplist.get(i).TURN_OVER;
//		}
//		if(sum>=30)
//			return true;
//		if(mdplist.get(0).P_END>=mdplist.get(1).P_END*1.09
//				|| mdplist.get(1).P_END>=mdplist.get(2).P_END*1.09) 
//			return true;
//		return false;
//	}

}
