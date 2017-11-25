package com.tgrape.sync.obj;

import java.util.ArrayList;
import java.util.List;

public class MDHist {
	
	public String STKCODE = null;
	public List<String> YEARMMDD_list = new ArrayList<String>();
	public List<Float> P_END_list = new ArrayList<Float>();
	public List<Float> MEAN6_list = new ArrayList<Float>();
	public List<Float> MEAN18_list = new ArrayList<Float>();
	public List<Float> MEAN54_list = new ArrayList<Float>();
	public List<Float> MEAN180_list = new ArrayList<Float>();
	
	public List<String> updateCount(String startDate, String endDate, int count){
		List<String> sqllist = new ArrayList<String>();
		//检查合法性，确认最终日期有数据
		/*
		 * && 
				( YEARMMDD_list.contains("endDate")
				   || YEARMMDD_list.contains("startDate")
		 */
		if(P_END_list.size()>count  ){
//			int starIndex = YEARMMDD_list.indexOf(endDate);
			int endIndex = YEARMMDD_list.indexOf(startDate);
			if(endIndex == -1)
				endIndex = count;
			for(int i=0;i<count&&i<=endIndex;i++){
				//计算180日均值
				Float mean180 = 0.0f;
				Float mean54 = 0.0f;
				Float mean18 = 0.0f;
				Float mean6 = 0.0f;
				if(P_END_list.size()>180+i){
					mean180 = mean(P_END_list,i,180);//i之后180求平均值
					mean54 = mean(P_END_list,i,54);//i之后54求平均值
					mean18 = mean(P_END_list,i,18);//i之后18求平均值
					mean6 = mean(P_END_list,i,6);//i之后6求平均值
				}else if(P_END_list.size()>54+i){
					mean54 = mean(P_END_list,i,54);
					mean18 = mean(P_END_list,i,18);
					mean6 = mean(P_END_list,i,6);
				}else if(P_END_list.size()>18+i){
					mean18 = mean(P_END_list,i,18);
					mean6 = mean(P_END_list,i,6);
				}else if(P_END_list.size()>6+i){
					mean6 = mean(P_END_list,i,6);
				}
				
				MEAN180_list.add(mean180);				
				MEAN54_list.add(mean54);
				MEAN18_list.add(mean18);
				MEAN6_list.add(mean6);
			}
			for(int i=0;i<count&&i<=endIndex;i++){
				String sql = "update T_Market_Day set MEAN6="+MEAN6_list.get(i)+", MEAN18="+MEAN18_list.get(i)+","
						+ " MEAN54="+MEAN54_list.get(i)+", MEAN180="+MEAN180_list.get(i) 
								+ " where STKCODE='"+STKCODE+"' and YEARMMDD='"+YEARMMDD_list.get(i)+"'";
				sqllist.add(sql);
			}
		}
		return sqllist;
	}
	private Float mean(List<Float> p_END_list, int i, int count) {
		Float sum = 0.0f;
		for(int index=i+1; index<count+i+1; index++){
			sum += p_END_list.get(i);
		}
		Float mean = sum/count;
		return mean;
	}
	public List<String> update() {
		List<String> sqllist = new ArrayList<String>();
		if(YEARMMDD_list.size()-P_END_list.size()==0
				&& P_END_list.size()-MEAN6_list.size()==0
				&& MEAN6_list.size()-MEAN18_list.size()==0
				&& MEAN18_list.size()-MEAN54_list.size()==0
				&& MEAN54_list.size()-MEAN180_list.size()==0){
			for(int i=0;i<P_END_list.size();i++){
				String sql = "update T_Market_Day set MEAN6="+MEAN6_list.get(i)+", MEAN18="+MEAN18_list.get(i)+","
							+ " MEAN54="+MEAN54_list.get(i)+", MEAN180="+MEAN180_list.get(i) 
									+ " where STKCODE='"+STKCODE+"' and YEARMMDD='"+YEARMMDD_list.get(i)+"'";
				sqllist.add(sql);
			}
		}
		return sqllist;
	}
	
	public void mean() {
		for(int i=0;i<YEARMMDD_list.size();i++){
			if(i<5){
				MEAN6_list.add(0F);
				MEAN18_list.add(0F);
				MEAN54_list.add(0F);
				MEAN180_list.add(0F);
			}else if(i<17){
				MEAN6_list.add(getSum(i,6)/6);
				MEAN18_list.add(0F);
				MEAN54_list.add(0F);
				MEAN180_list.add(0F);
			}else if(i<53){
				MEAN6_list.add(getSum(i,6)/6);
				MEAN18_list.add(getSum(i,18)/18);
				MEAN54_list.add(0F);
				MEAN180_list.add(0F);
			}else if(i<180){
				MEAN6_list.add(getSum(i,6)/6);
				MEAN18_list.add(getSum(i,18)/18);
				MEAN54_list.add(getSum(i,54)/54);
				MEAN180_list.add(0F);
			}else{
				MEAN6_list.add(getSum(i,6)/6);
				MEAN18_list.add(getSum(i,18)/18);
				MEAN54_list.add(getSum(i,54)/54);
				MEAN180_list.add(getSum(i,180)/180);
			}				
		}
		
	}

	private Float getSum(int i,int num) {
		Float sum = 0.0F;
		for(int index=i;index>=i-num+1;index--){
			sum += P_END_list.get(index);
		}
		return sum;
	}
}
