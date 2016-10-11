package com.mlh.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mlh.config.IPickerConfig;
import com.mlh.config.txt.TxtPickerConfig;
import com.mlh.engine.PickerEngine;
import com.mlh.exception.RequestErrException;
import com.mlh.http.IWebInfoSpider;
import com.mlh.http.StringWebInfoSpider;

public class Main {

	public static void main(String[] args) throws ClientProtocolException, RequestErrException, IOException {
		IPickerConfig config = new TxtPickerConfig().config("D:\\Workspaces\\MyEclipse for Spring 8.6\\m-picker\\config\\weblist.txt");
		IWebInfoSpider spider = new StringWebInfoSpider();
		PickerEngine engine = new PickerEngine(spider,config);
		engine.work();
	}
	
}
