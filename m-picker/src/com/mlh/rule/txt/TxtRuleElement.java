package com.mlh.rule.txt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.DocumentException;

import com.mbag.log.LogTools;
import com.mlh.constant.LinkStore;
import com.mlh.exception.RequestErrException;
import com.mlh.http.IWebInfoSpider;
import com.mlh.model.pick.PickRow;
import com.mlh.model.pick.PickTable;
import com.mlh.model.pick.PickUnit;
import com.mlh.out.DBOutPutHandler;
import com.mlh.out.IOutPutHandler;
import com.mlh.out.TxtOutPutHandler;
import com.mlh.rule.IRuleElement;
import com.mlh.util.DomUtil;
import com.mlh.util.Identity;
import com.mlh.util.UrlUtil;
/**
 * 
 * @author mlh
 * @version 1.0
 */
public class TxtRuleElement implements IRuleElement{

	public static final int OUT_INSERT=1;
	
	public static final int OUT_TXT=2;
	
	public static final int OUT_XLS=3;
	
	public static final String REGEX="regex:";
	
	public static final String XPATH="xpath:";
	
	public static final String LINK="link:";
	
	public static final String CLASS="class:";
	
	public static final String OUT="out:";
	
	public static final String SEPARATOR=":";
	
	private int matcherIndex;
	
	private boolean isRegex;
	
	private boolean isXpath;
	
	private boolean isNeedOut;
	
	private int outType;
	
	private boolean isOut;
	
	private String content;
	
	private boolean isLink;
	
	private boolean isJavaClass;
	
	private String srcContent;
	
	private short unitType;
	
	private String expression;
	
	private IRuleElement nextElement;

	public IRuleElement init(Object ruleLine,IRuleElement next){
		initRuleElement((String)ruleLine);
		if(null!=next&&!"".equals(next)){
			nextElement = next;
			if(nextElement.isOut()){
				this.isNeedOut=true;
			}
		}
		return this;
	}
	
	private void initRuleElement(String ruleLine){
		this.srcContent=ruleLine;
		if(ruleLine.startsWith(LINK)){
			isLink = true;
			ruleLine = ruleLine.substring(LINK.length());
		}
		if(ruleLine.startsWith(CLASS)){
			isJavaClass = true;
			ruleLine = ruleLine.substring(CLASS.length());
		}
		if(ruleLine.startsWith(REGEX)){
			isRegex=true;
			ruleLine = ruleLine.substring(REGEX.length());
		}
		if(ruleLine.startsWith(XPATH)){
			isXpath=true;
			ruleLine = ruleLine.substring(XPATH.length());
		}
		if(ruleLine.startsWith(OUT)){
			isOut=true;
			ruleLine = ruleLine.substring(OUT.length());
			if(ruleLine.toLowerCase().startsWith("insert")){
				this.outType=OUT_INSERT;
			}else if(ruleLine.toLowerCase().endsWith(".txt")){
				this.outType=OUT_TXT;
			}else if(ruleLine.toLowerCase().endsWith(".xls")){
				this.outType=OUT_XLS;
			}
			content=ruleLine;
		}
		if(isRegex||isXpath){
			if(ruleLine.endsWith(")")){
				String index = ruleLine.substring(ruleLine.length()-2,ruleLine.length()-1);
				if("*".equals(index)){
					matcherIndex = -1;
				}else{
					matcherIndex = Integer.parseInt(index);
				}
				ruleLine = ruleLine.substring(0,ruleLine.length()-4);
			}else{
				matcherIndex = -1;
			}
			String[] matches = ruleLine.split(SEPARATOR,2);
			if(matches.length!=2){
				throw new RuntimeException("weblist≈‰÷√Œƒº˛”Ô∑®¥ÌŒÛ°æ"+srcContent+"°ø");
			}
			content = matches[0];
			expression = matches[1];
			String[] typeExpress = expression.replace("{", "").replace("}", "").split(SEPARATOR);
			String type = typeExpress[0];
			if("string".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_STRING;
			}else if("long".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_LONG;
			}else if("integer".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_INTEGER;
			}else if("float".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_FLOAT;
			}else if("double".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_DOUBLE;
			}else if("short".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_SHORT;
			}else if("number".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_DOUBLE;
			}else if("date".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_DATE;
			}else if("byte".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_BYTE;
			}else if("binary".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_BIN;
			}else if("boolean".equalsIgnoreCase(type)){
				unitType=PickUnit.TYPE_BOOLEAN;
			}
			if(typeExpress.length==2){
				expression = typeExpress[1];
			}
		}
		
	}
	
	private PickTable outPut(PickTable table){
		IOutPutHandler out = null;
		if(outType==OUT_TXT){
			out = new TxtOutPutHandler();
		}else if(outType==OUT_INSERT){
			out = new DBOutPutHandler();
		}else if(outType==OUT_XLS){
			
		}
		out.init(content);
		out.outPut(table);
		out.close();
		return table;
	}
	
	@Override
	public PickTable matcher(PickTable table,String url,IWebInfoSpider spider) {
		if(isOut){
			return outPut(table);
		}else{
			int rowNum = table.getRowNum();
			PickTable result = new PickTable();
			for(int i=0;i<rowNum;i++){
				PickRow row = table.getRow(i);
				int unitSize = row.getUnitSize();
				for(int j=0;j<unitSize;j++){
					PickUnit unit = row.getUnit(j);
					PickRow newRow = null;
					if(isNeedOut){
						newRow = match(unit,Identity.uuid(),row.getId(),url,spider);
					}else{
						newRow = match(unit,row.getId(),row.getForeignId(),url,spider);
					}
					result.addRow(newRow);
				}
			}
			return result;
		}
	}
	
	
	private PickRow match(PickUnit unit,String id,String foreignId,String url,IWebInfoSpider spider) {
		String info = unit.getStringValue();
		PickRow row = null;
		if(isLink){
			if(isRegex){
				row = findLinkByRegex(info,id,foreignId,url,spider);
			}else if(isXpath){
				row = findLinkByXpath(unit.getSrcString(),id,foreignId,url,spider);
			}
		}else{
			if(isRegex){
				row = findRowByRegex(info,id,foreignId);
			}else if(isXpath){
				row = findRowByXpath(info,id,foreignId);
			}
			
		}
		return row;
	}
	
	
	private PickRow findRowByXpath(String info, String id, String foreignId) {
		try {
			PickRow row = new PickRow(id,foreignId,new ArrayList<PickUnit>());
			List<String> list = null;
			List<String> srcList = null;
			if(matcherIndex==-1){
				if(isNeedOut){
					list = DomUtil.getNodeTxtFrmText(info, content);
					srcList = DomUtil.getNodeAsXml(info, content);
				}else{
					list = DomUtil.getNodeAsXml(info, content);
					srcList = list;
				}
			}else{
				list = new ArrayList<String>();
				srcList = new ArrayList<String>();
				if(isNeedOut){
					list.add(DomUtil.getNodeTxtFrmTxt(info, content, matcherIndex));
					srcList.add(DomUtil.getNodeAsXml(info, content,matcherIndex));
				}else{
					list.add(DomUtil.getNodeAsXml(info, content,matcherIndex));
					srcList.add(DomUtil.getNodeAsXml(info, content,matcherIndex));
				}
			}
			int i=0;
			for(String value:list){
				PickUnit unit = new PickUnit(value,unitType);
				row.addUnit(unit);
				if(srcList!=null){
					unit.setSrcString(srcList.get(i));
				}
				i++;
			}
			return row;
		} catch (DocumentException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
		return null;
	}


	private PickRow findRowByRegex(String info, String id, String foreignId) {
		PickRow row = new PickRow(id,foreignId,new ArrayList<PickUnit>());
		Pattern p = Pattern.compile(content);
		Matcher m = p.matcher(info);
		int i=0;
		while(m.find()){
			String g = m.group();
			if(matcherIndex==-1){
				PickUnit unit = new PickUnit(g,unitType);
				row.addUnit(unit);
			}else{
				if(i==matcherIndex){
					PickUnit unit = new PickUnit(g,unitType);
					row.addUnit(unit);
				}
			}
			i++;
		}
		return row;
	}

	private PickRow findLinkByXpath(String info, String id, String foreignId, String url,IWebInfoSpider spider) {
		try {
			PickRow row = new PickRow(id,foreignId,new ArrayList<PickUnit>());
			List<String> oppsiteUrls = null;
			if(matcherIndex==-1){
				oppsiteUrls = DomUtil.getNodeTxtFrmText(info, content);
			}else{
				oppsiteUrls = new ArrayList<String>();
				oppsiteUrls.add(DomUtil.getNodeTxtFrmTxt(info, content, matcherIndex));
			}
			for(String oUrl:oppsiteUrls){
				String newUrl = UrlUtil.unitOppsiteUrl(url, oUrl);
				if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_STRING){
					String response = spider.requestString(newUrl);
					row.addUnit(new PickUnit(response,PickUnit.TYPE_STRING));
				}else if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_BYTEARR){
					//TODO
				}else if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_OBJECT){
					//TODO
				}
			}
			return row;
		} catch (DocumentException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		} catch (RequestErrException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
		return null;
	}

	private PickRow findLinkByRegex(String info, String id, String foreignId, String url,IWebInfoSpider spider) {
		try{
			PickRow row = new PickRow(id,foreignId,new ArrayList<PickUnit>());
			List<String> oppsiteUrls = null;
			Pattern p = Pattern.compile(content);
			Matcher m = p.matcher(info);
			int i=0;
			while(m.find()){
				String g = m.group();
				if(matcherIndex==-1){
					oppsiteUrls.addAll(DomUtil.getNodeTxtFrmText(g, LinkStore.LINK_XPATH));
				}else{
					if(i==matcherIndex){
						oppsiteUrls = DomUtil.getNodeTxtFrmText(g, LinkStore.LINK_XPATH);
					}
				}
				i++;
			}
			for(String oUrl: oppsiteUrls){
				String newUrl = UrlUtil.unitOppsiteUrl(url, oUrl);
				if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_STRING){
					String response = spider.requestString(newUrl);
					row.addUnit(new PickUnit(response,PickUnit.TYPE_STRING));
				}else if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_BYTEARR){
					//TODO
				}else if(spider.getSpiderType()==IWebInfoSpider.SPIDER_TYPE_OBJECT){
					//TODO
				}
			}
			return row;
		}catch(Exception e){
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
		return null;
	}

	public short getUnitType() {
		return unitType;
	}

	public String getExpression() {
		return expression;
	}

	public boolean isXpath() {
		return isXpath;
	}

	public String getSrcContent() {
		return srcContent;
	}

	public int getMatcherIndex() {
		return matcherIndex;
	}

	public boolean isOut() {
		return isOut;
	}

	public String getContent() {
		return content;
	}

	public boolean isLink() {
		return isLink;
	}

	public boolean isJavaClass() {
		return isJavaClass;
	}

	public boolean isRegex() {
		return isRegex;
	}

	public int getOutType() {
		return outType;
	}

	public boolean isNeedOut() {
		return isNeedOut;
	}

	public IRuleElement nextElement() {
		return nextElement;
	}


}
