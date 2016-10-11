package com.mlh.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.mlh.config.IPickerConfig;
import com.mlh.exception.RequestErrException;
import com.mlh.http.IWebInfoSpider;
import com.mlh.model.pick.PickRow;
import com.mlh.model.pick.PickTable;
import com.mlh.model.pick.PickUnit;
import com.mlh.rule.IRule;
import com.mlh.rule.IRuleElement;

public class PickerEngine {

	private IWebInfoSpider spider;
	
	private IPickerConfig config;
	
	public PickerEngine(){
		
	}
	
	public PickerEngine(IWebInfoSpider spider,IPickerConfig config){
		this.spider=spider;
		this.config=config;
	}

	public void work() throws ClientProtocolException, RequestErrException, IOException{
		int num = config.getRuleNum();
		for(int i=0;i<num;i++){
			IRule rule = config.getRule(i);
			List<String> urlList = rule.getUrlList();
			for(String url:urlList){
				int type = spider.getSpiderType();
				if(type==IWebInfoSpider.SPIDER_TYPE_STRING){
					String response = spider.requestString(url);
					PickUnit unit = new PickUnit(response);
					List<PickUnit> list = new ArrayList<PickUnit>();
					list.add(unit);
					PickRow row = new PickRow("","",list);
					PickTable table = new PickTable();
					table.addRow(row);
					pick(table,rule.getRule(0),url);
				}else if(type==IWebInfoSpider.SPIDER_TYPE_BYTEARR){
//					byte[] response = spider.requestByteArr(url);  TODO
				}else if(type==IWebInfoSpider.SPIDER_TYPE_OBJECT){
//					Object boject = spider.requestObj(url);   TODO
				}
			}
		}
	}
	
	
	
	private void pick(PickTable table, IRuleElement ruleElement,String url) {
		PickTable tab = ruleElement.matcher(table,url,spider);
		if(null!=ruleElement.nextElement()){
			pick(tab,ruleElement.nextElement(),url);
		}
	}

	public void setSpider(IWebInfoSpider spider) {
		this.spider = spider;
	}

	public void setConfig(IPickerConfig config) {
		this.config = config;
	}
	
}
