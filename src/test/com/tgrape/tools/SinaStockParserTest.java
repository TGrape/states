package test.com.tgrape.tools;

import static org.junit.Assert.*;

import org.junit.Test;

import com.tgrape.tools.SinaStockParser;

public class SinaStockParserTest {

	@Test
	public void test() {
		SinaStockParser ssp = new SinaStockParser();
		ssp.parse("http://quote.eastmoney.com/stock_list.html");
	}

}
