package com.tgrape.strategy;

import java.util.List;

public class FLTPStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<18)
			return false;
		if(mdplist.get(0).P_END>100)
			return false;
		if(this.shangying(mdplist))
			return false;
		if(this.bodong(mdplist))
			return false;
		if( this.tp(15,mdplist) && fangliang(mdplist)  ){
			return true;
		}
		
		
		
		return false;
	}

	private boolean bodong(List<MarketDayProperty> mdplist) {
		float max = 0;
		float min = 1000;
		for(int i=0;i<mdplist.size()&&i<10;i++){
			if(mdplist.get(i).P_HIGH>max)
				max = mdplist.get(i).P_HIGH;
			if(mdplist.get(i).P_LOW<min)
				min = mdplist.get(i).P_LOW;
		}
		if(max>min*1.30)
			return true;
		
		return false;
	}

	/**
	 * 放量
	 * @param mdplist
	 * @return
	 */
	private boolean fangliang(List<MarketDayProperty> mdplist) {
		
		for(int i=0;i<8;i++){
			if(mdplist.get(i).P_END>mdplist.get(i).P_START*1.05 //突破>3%
					&& (mdplist.get(i).TURN_OVER>=0.9*(	mdplist.get(i+1).TURN_OVER+
											mdplist.get(i+2).TURN_OVER+
											mdplist.get(i+3).TURN_OVER) 
								|| mdplist.get(i).TURN_OVER>5 
								||mdplist.get(i).DAY_VOLUMN>50000) //放量
					&&!(	mdplist.get(0).P_END<mdplist.get(0).P_START  //非三连阴
							&& mdplist.get(1).P_END<mdplist.get(1).P_START 
							&& mdplist.get(2).P_END<mdplist.get(2).P_START)
					&&!(	mdplist.get(1).P_END<mdplist.get(1).P_START  //非三连阴
							&& mdplist.get(2).P_END<mdplist.get(2).P_START 
							&& mdplist.get(3).P_END<mdplist.get(3).P_START)
					&&!(	mdplist.get(0).P_END<mdplist.get(1).P_END  //非连续下跌
							&& mdplist.get(1).P_END<mdplist.get(2).P_END 
							&& mdplist.get(2).P_END<mdplist.get(3).P_END)
					&&!(	mdplist.get(1).P_END<mdplist.get(2).P_END  //非连续下跌
							&& mdplist.get(2).P_END<mdplist.get(3).P_END 
							&& mdplist.get(3).P_END<mdplist.get(4).P_END)){
				if(i>1){
					for(int j=i-1;j>=0;j--){
						if((mdplist.get(j).DAY_VOLUMN/mdplist.get(i).DAY_VOLUMN)<0.18 && mdplist.get(j).P_END<mdplist.get(j+1).P_END*1.09)
							return false;
					}
				}
				return true;
			}
		}
		return false;
	}


	private boolean tp(int daynum, List<MarketDayProperty> mdplist) {
		float max6 = 0;
		for(int i=0;i<6;i++){
			if(mdplist.get(i).P_HIGH>max6)
				max6 = mdplist.get(i).P_HIGH;
		}
		float max = 0;
		for(int i=6;i<daynum&&i<mdplist.size();i++){
			if(mdplist.get(i).P_END>max)
				max = mdplist.get(i).P_END;
		}
		
		if(max6>max)
			return true;
		return false;
	}

	
	


}
