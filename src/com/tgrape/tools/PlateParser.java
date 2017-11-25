package com.tgrape.tools;

import java.util.List;

import com.tgrape.sync.obj.T_Plate;

public interface PlateParser {

	List<T_Plate> parser(String url);
}
