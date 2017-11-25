package com.tgrape.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tgrape.strategy.MarketDayProperty;
import com.tgrape.strategy.SProperty;
import com.tgrape.strategy.StgFactory;
import com.tgrape.sync.obj.MDHist;


public class SqlExecutor {
	private static Logger logger = LogManager.getLogger(SqlExecutor.class);
	private Connection conn = null;
	private Statement stmt = null;
	private String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:STATES";
    private String user = "STATES";// 用户名,系统默认的账户名
    private String password = "liuyuding0329";// 你安装时选设置的密码
	public SqlExecutor(){		
        try {
        	try {
				Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			 logger.error("连接oracle失败！url="+url);
			e.printStackTrace();
			System.exit(1);
		}// 获取连接
        logger.info("连接oracle成功！url="+url);
	}
	public void execute(String sql) {
		if(null!=stmt){
			try {
				if(stmt.isClosed()){
					if(conn.isClosed()){
						conn = DriverManager.getConnection(url, user, password);
						conn.setAutoCommit(true);
					}
					stmt = conn.createStatement();						
				}
			} catch (SQLException e1) {
				logger.error("-- stmt reconn err!");
				e1.printStackTrace();
			}
			try {
				logger.info("执行 sql="+sql);
				if(sql.startsWith("insert"))
					stmt.execute(sql);
				else if(sql.startsWith("update"))
					stmt.executeUpdate(sql);
			} catch (SQLException e) {
				logger.error("执行sql错误！sql="+sql);
				e.printStackTrace();
			}
		}
	}
	private ResultSet executeSql(String sql) throws SQLException{
		logger.info("------------begin to executeSql {}", sql);
		if(null!=stmt){
			try {
				if(stmt.isClosed()){
					if(conn.isClosed()){
						conn = DriverManager.getConnection(url, user, password);
						conn.setAutoCommit(true);
					}	
					stmt = conn.createStatement();
				}
				ResultSet rs = stmt.executeQuery(sql);
				logger.info("------------end to executeSql {}",sql);
				return rs;	
			} catch (SQLException e1) {
				logger.error("-- stmt reconn err!");
				e1.printStackTrace();
				return null;
			}
		}else 
			return null;
	}
	public void close(){
		try {
			stmt.close();
			conn.close();
			logger.info("关闭oracle连接成功！url="+url);
		} catch (SQLException e) {
			logger.error("关闭oracle失败！");
			e.printStackTrace();
		}
		
		
	}
	public List<String> listAllStocks() {
		List<String> slist = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = this.executeSql("select STKCODE from T_STOCK order by STKCODE");
			while(rs.next()){
				slist.add(rs.getString("STKCODE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return slist;
	}
	
	public List<String> listNoHistStocks() {
		List<String> slist = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = this.executeSql("select STKCODE from T_STOCK minus select distinct(STKCODE) from T_Market_Day order by STKCODE desc");
			while(rs.next()){
				slist.add(rs.getString("STKCODE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return slist;
	}
	public MDHist listHistMarket(String stock, String startDate, String endDate, int datecount) {
		MDHist mdh = new MDHist();
		ResultSet rs = null;
		try {
			//select * from (select STKCODE,YEARMMDD,P_END from T_Market_Day where STKCODE='600009' and YEARMMDD<='20170316' order by YEARMMDD desc) where rownum<=184
			String sql = "select * from (select STKCODE,YEARMMDD,P_END from T_Market_Day where STKCODE='"+stock+"' and YEARMMDD<='"+endDate+"' order by YEARMMDD desc) where rownum<="+(180+datecount+1);
			rs = this.executeSql( sql );
			while(rs.next()){
				mdh.STKCODE = rs.getString("STKCODE");
				mdh.YEARMMDD_list.add( rs.getString("YEARMMDD") );
				mdh.P_END_list.add( rs.getFloat("P_END") );				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mdh;		
	}
	public MDHist listAllHistMarket(String stock) {
		MDHist mdh = new MDHist();
		ResultSet rs = null;
		try {
			//select * from (select STKCODE,YEARMMDD,P_END from T_Market_Day where STKCODE='600009' and YEARMMDD<='20170316' order by YEARMMDD desc) where rownum<=184
			rs = this.executeSql("select STKCODE,YEARMMDD,P_END from T_Market_Day where STKCODE='"+stock+"'" );
			while(rs.next()){
				mdh.STKCODE = rs.getString("STKCODE");
				mdh.YEARMMDD_list.add( rs.getString("YEARMMDD") );
				mdh.P_END_list.add( rs.getFloat("P_END") );				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mdh;		
	}
	public List<String> listNonameStocks() {
		List<String> slist = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = this.executeSql("select STKCODE from T_STOCK where stkname is null");
			while(rs.next()){
				slist.add(rs.getString("STKCODE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return slist;
	}
	public List<SProperty> listAllStrategy() throws SQLException {
		String sql = "select SCODE,NAME from ZD_STRATEGE where isused=1";
		ResultSet rs = this.executeSql(sql);
		List<SProperty> splist = new ArrayList<SProperty>();
		while(rs.next()){			
			String scode = rs.getString("SCODE");
			String name= rs.getString("NAME");
			SProperty sp = StgFactory.create(scode);
			sp.SCODE = scode;
			sp.NAME  = name;
			splist.add(sp);
		}
		return splist;
	}
	public List<MarketDayProperty> listAllHistMarket(String stock, String dateToday) {
		List<MarketDayProperty> mdplist = new ArrayList<MarketDayProperty>();
		ResultSet rs = null;
		try { 			
			String sql = "select count(*) cnt from  T_Market_Day "
							+ "where STKCODE='"+stock+"' and YEARMMDD='"+dateToday+"' " ;
			rs = this.executeSql(sql);
			int count = 1;
			while(rs.next()){
				count = rs.getInt("cnt");
			}
			if(count==0)
				return mdplist; 
			else{
				sql = "select STKCODE,YEARMMDD,P_START,P_HIGH,P_LOW,P_END,DAY_VOLUMN,TURN_OVER from T_Market_Day "
								+ "where STKCODE='"+stock+"' and YEARMMDD<='"+dateToday+"' order by YEARMMDD desc" ;
				rs = this.executeSql(sql);
				while(rs.next()){
					MarketDayProperty mdp = new MarketDayProperty();
					mdp.STKCODE = rs.getString("STKCODE");
					mdp.YEARMMDD = rs.getString("YEARMMDD");
					mdp.P_START = rs.getFloat("P_START");
					mdp.P_END = rs.getFloat("P_END");
					mdp.P_HIGH = rs.getFloat("P_HIGH");
					mdp.P_LOW = rs.getFloat("P_LOW");
					mdp.DAY_VOLUMN = rs.getFloat("DAY_VOLUMN");
					mdp.TURN_OVER = rs.getFloat("TURN_OVER");
					mdplist.add(mdp);			
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mdplist;	
	}
	public int queryNofreshDate() {
		//select floor(sysdate -(select max(CUR_DATE) from t_market_day where stkcode='600000' )) from dual
		String sql = "select floor(sysdate -(select max(CUR_DATE) from t_market_day where stkcode='600000' )) from dual";
		ResultSet rs = null;
		int d = 1;
		try {
			rs = this.executeSql(sql);
			while(rs.next()){
				d = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}
}
