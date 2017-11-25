package com.tgrape.db.main;

import java.util.ArrayList;
import java.util.List;

import com.tgrape.db.StatesDBUtil;
import com.tgrape.strategy.StrategyComputeServer;
import com.tgrape.sync.ISync;
import com.tgrape.sync.MarketHistSync;
import com.tgrape.sync.MeanUpdateServer;
import com.tgrape.sync.StockSync;

public class MainControl {

	public static void main(String[] args) {

		List<ISync> islist = new ArrayList<ISync>();

		//ͬ����Ʊ��Ϣ
		ISync siss = new StockSync();
		islist.add(siss);
		//ͬ����Ʊ������Ϣ
		int datenum = StatesDBUtil.getDateNumNoRefresh();//����δִ��
		ISync ismh = new MarketHistSync(datenum);
		islist.add(ismh);
		//�����ֵ��Ϣ
		ISync ismus = new MeanUpdateServer(datenum);
		islist.add(ismus);
		//����ѡ��
		ISync isSC = new StrategyComputeServer();		
		islist.add(isSC);
		
		//˳��ִ��
		for(int i=0;i<islist.size();i++){			
			islist.get(i).beginSync();
			islist.get(i).sync(null);
			islist.get(i).endSync();
		}

	}

}
