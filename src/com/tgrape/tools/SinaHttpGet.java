package com.tgrape.tools;

import java.util.Map;

public class SinaHttpGet extends MyHttpGet {

	private String httpuri = "http://hq.sinajs.cn/";
	//http://hq.sinajs.cn/list=sh601006
	
	public String getHttp(String url, Map<String, String> params) {
		if(null==params)
			return this.httpReturn(url);
		else if(params.keySet().size()>0){
			if(params.keySet().contains("list")){
				String list = params.get("list");
				if(null!=url)
					httpuri = url;
				String httpurl = httpuri + "/list="+list;
				return this.httpReturn(httpurl);
			}
		}
		return null;
	}

}
