package com.mlh.rule;

import com.mlh.http.IWebInfoSpider;
import com.mlh.model.pick.PickTable;

public interface IRuleElement {

	IRuleElement init(Object obj,IRuleElement next);
	
	IRuleElement nextElement();
	
	PickTable matcher(PickTable table,String url,IWebInfoSpider spider);

	boolean isOut();
}
