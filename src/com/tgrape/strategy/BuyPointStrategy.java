package com.tgrape.strategy;

import java.util.List;

public class BuyPointStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<54)
			return false;
	
		if(!this.tp30(mdplist) ){
			return false;
		}
		if(this.lowest(12,mdplist))
			return false;
		if(mdplist.get(0).P_HIGH>70)
			return false;
		if(shangying(mdplist)){
			return false;
		}
		if(max(18,mdplist))
			return false;
		if(!tiaozheng10(mdplist)){
			return false;
		}
		return true;
	}
	private boolean tiaozheng10(List<MarketDayProperty> mdplist) {
		float max6 = mdplist.get(0).P_HIGH;
		float min6 = mdplist.get(0).P_LOW;
		for(int i=1;i<6;i++){
			if(mdplist.get(i).P_HIGH>max6)
				max6 = mdplist.get(i).P_HIGH;
			if(mdplist.get(i).P_LOW<min6)
				min6 = mdplist.get(i).P_LOW;
		}
		if(min6<max6*0.94 && min6>max6*0.7)
			return true;
		
		return false;
	}


	
	private boolean tp30(List<MarketDayProperty> mdplist) {
		float max8 = mdplist.get(0).P_HIGH;
		for(int i=1;i<5;i++){
			if(mdplist.get(i).P_HIGH>max8)
				max8 = mdplist.get(i).P_HIGH;
		}
		float max54 = 0;
		for(int i=5;i<34&&i<mdplist.size();i++){
			if(mdplist.get(i).P_HIGH>max54)
				max54 = mdplist.get(i).P_HIGH;
		}
		if(max8>max54)
			return true;
		return false;
	}

	

}
