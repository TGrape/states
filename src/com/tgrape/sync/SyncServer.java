package com.tgrape.sync;

public class SyncServer {

	private static MarketHistSync msh = null;
	private static PlateSync ps = null;
	private static StockSync ss = null;
	
	private static boolean if_ms_hist = false;
	private static boolean if_ps = false;
	private static boolean if_ss = false;

	private static String url  = null;
	public static void main(String[] args) {

		if(args.length==0){
			err();
			System.exit(1);
		}
		
		if(args.length>=1){
			url = args[0];
		}else if(args.length>=2){
			if(args[1].compareTo("1")==0)
				if_ms_hist = true;
		}else if(args.length>=3){
			if(args[2].compareTo("1")==0)
				if_ps = true;
		}else if(args.length>=4){
			if(args[3].compareTo("1")==0)
				if_ss = true;
		}
		if(if_ms_hist && null != msh)
			msh.sync(url);
		if(if_ps && null != ps)
			ps.sync(url);
		if(if_ss && null != ss)
			ss.sync(url);
		
	}

	private static void err() {
		System.err.println("****** args : sync_website if_sync_market sync_stock sync_plate");
	}

}
