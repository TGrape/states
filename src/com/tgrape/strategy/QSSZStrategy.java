package com.tgrape.strategy;

import java.util.List;

public class QSSZStrategy extends SProperty {

	@Override
	public boolean hit(List<MarketDayProperty> mdplist) {
		if(mdplist.size()<30){
			return false;
		}
		if(mdplist.get(0).P_HIGH<mdplist.get(5).P_LOW){
			return false;
		}
		if(shangying(mdplist)){
			return false;
		}
		if(lowest(3,mdplist)){
			return false;
		}
		if(bigyinxian(mdplist)){
			return false;
		}
		if(this.qssz3(mdplist)){
			return true;
		}
		if(this.qssz6(mdplist)){
			return true;
		}
		return false;
	}
	private boolean bigyinxian(List<MarketDayProperty> mdplist) {
		for(int i=0;i<18&&i<mdplist.size();i++){
			if(mdplist.get(i).P_START*0.92>mdplist.get(i).P_END){
				return true;
			}
		}
		return false;
	}
	
	

	/**
	 * 强势上涨3天
	 * @param mdplist
	 * @return
	 */
	private boolean qssz3(List<MarketDayProperty> mdplist) {
		if(mdplist.get(0).P_HIGH>mdplist.get(2).P_LOW*1.18 &&
				mdplist.get(0).P_HIGH>mdplist.get(0).P_LOW*1.03 &&
				mdplist.get(1).P_HIGH>mdplist.get(1).P_LOW*1.03 &&
				mdplist.get(2).P_HIGH>mdplist.get(2).P_LOW*1.03)
			return true;
		else
			return false;
	}

	/**
	 * 强势上涨6天
	 * @param mdplist
	 * @return
	 */
	private boolean qssz6(List<MarketDayProperty> mdplist) {
		if(mdplist.get(0).P_HIGH>mdplist.get(5).P_LOW*1.30 &&
				mdplist.get(0).P_END>mdplist.get(0).P_START*0.99 &&
				mdplist.get(1).P_END>mdplist.get(1).P_START*0.99 &&
				mdplist.get(2).P_END>mdplist.get(2).P_START*0.99 &&
				mdplist.get(3).P_END>mdplist.get(3).P_START*0.99 &&
				mdplist.get(4).P_END>mdplist.get(4).P_START*0.99 &&
				mdplist.get(5).P_END>mdplist.get(5).P_START*0.99 &&
				mdplist.get(0).P_HIGH>mdplist.get(1).P_END &&
				mdplist.get(1).P_HIGH>mdplist.get(2).P_END &&
				mdplist.get(2).P_HIGH>mdplist.get(3).P_END)
			return true;
		else
			return false;
	}

}
