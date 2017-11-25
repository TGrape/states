package com.tgrape.tools;

import java.util.List;

import com.tgrape.sync.obj.T_Stock;

public interface StockParser {

	public List<T_Stock> parse(String url);
}
