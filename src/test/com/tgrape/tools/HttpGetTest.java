package test.com.tgrape.tools;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.tgrape.tools.MyHttpGet;
import com.tgrape.tools.SinaHttpGet;

public class HttpGetTest {

	@Test
	public void test() {
		MyHttpGet hg = new SinaHttpGet();
		String url = "http://hq.sinajs.cn/list=sh601006";
		String rt = hg.httpReturn(url);		
		System.out.println(rt);
		
		Assert.assertTrue(rt.contains("sh601006"));
		String real = "var hq_str_sh601006=\"´óÇØÌúÂ·,7.060,7.060,7.050,7.080,7.050,7.050,7.060,17006659,120108881.000,1225500,7.050,1339300,7.040,1172400,7.030,1124600,7.020,407900,7.010,182500,7.060,1003900,7.070,1027048,7.080,431100,7.090,836319,7.100,2017-02-21,13:08:49,00\";";
		//Assert.assertTrue(real.compareToIgnoreCase(rt)==0);
		System.out.println("rt.length:"+rt.length());
		System.out.println("real.length:"+real.length());
//		Assert.assertTrue((rt.length())==(real.length()));
//		Assert.assertTrue(real==rt);
		Map<String,String> params = new HashMap<String,String>();
		params.put("list", "sh601006");
		
		String hrt = hg.getHttp("http://hq.sinajs.cn", params);
		Assert.assertTrue(hrt.compareToIgnoreCase(rt)==0);
		System.out.println(hrt);
		String slist = hg.getHttp("http://quote.eastmoney.com/stock_list.html", null);
		System.out.println(slist);
	}
	
	

}
