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

		//同步股票信息
		ISync siss = new StockSync();
		islist.add(siss);
		//同步股票行情信息
		int datenum = StatesDBUtil.getDateNumNoRefresh();//几天未执行
		ISync ismh = new MarketHistSync(datenum);
		islist.add(ismh);
		//计算均值信息
		ISync ismus = new MeanUpdateServer(datenum);
		islist.add(ismus);
		//策略选股
		ISync isSC = new StrategyComputeServer();		
		islist.add(isSC);
		
		//顺序执行
		for(int i=0;i<islist.size();i++){			
			islist.get(i).beginSync();
			islist.get(i).sync(null);
			islist.get(i).endSync();
		}

	}

}
