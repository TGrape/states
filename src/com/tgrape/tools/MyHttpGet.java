package com.tgrape.tools;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;

import com.sun.media.jfxmedia.logging.Logger;


public abstract class MyHttpGet {
	

	public abstract String getHttp(String url,Map<String,String> params);
	
	public String httpReturn(String url){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
			
		String rt = null;
		CloseableHttpResponse response = null;
		try {
			
			
			response = httpclient.execute(httpget);
		
		
			HttpEntity entity = response.getEntity();
			if (entity != null) {
//				long len = entity.getContentLength();
				
				//if (len != -1 && len < 2048) {
					rt  = EntityUtils.toString(entity);
				//} else {
				// Stream content out
				//}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			rt = null;
		}finally {
			if(null!=response){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return rt;		
	}

}
