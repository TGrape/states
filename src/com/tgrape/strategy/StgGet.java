package com.tgrape.strategy;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.db.SqlExecutor;

public class StgGet {
	private static Logger logger = LogManager.getLogger(StgGet.class);
	public static List<SProperty> getStg(){
		List<SProperty> splist= null;
		SqlExecutor se = new SqlExecutor();
		try {
			splist = se.listAllStrategy();
		} catch (SQLException e) {
			logger.error("---- get strategy err");
			e.printStackTrace();
		}finally{
			se.close();
		}
		return splist;
	}

}
