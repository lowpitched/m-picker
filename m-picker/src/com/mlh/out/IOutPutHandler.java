package com.mlh.out;

import com.mlh.model.pick.PickTable;

public interface IOutPutHandler {

	public void init(Object...objs);
	
//	public void outPut(List<List<String>> data);
	
	public void outPut(PickTable table);
	
	public void close();
	
}
