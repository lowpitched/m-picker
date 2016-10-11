package com.mlh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {

	private UrlUtil(){}
	
	public static String unitOppsiteUrl(String url,String oppsiteUrl){
		if(!oppsiteUrl.startsWith(".")&&!oppsiteUrl.startsWith("/")){
			return oppsiteUrl;
		}
		Pattern p = Pattern.compile("\\.\\.");
		Matcher m = p.matcher(oppsiteUrl);
		int count = 0;
		while(m.find()){
			count++;
		}
		String purl = "";
		if(oppsiteUrl.startsWith("/")){
			purl = oppsiteUrl.substring(1);
		}else if(oppsiteUrl.startsWith("../")||oppsiteUrl.startsWith("./")){
			purl = oppsiteUrl.replaceAll("\\.\\.\\/", "").replaceAll("\\.\\/", "");
		}
		String[] newStr = url.replaceAll("http://", "").split("[\\/]");
		for(int i=1;i<=count+1;i++){
			newStr[newStr.length-i]="";
		}
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<newStr.length;i++){
			if(newStr[i]!=null&&!"".equals(newStr[i]))
				builder.append(newStr[i]).append("/");
		}
		return "http://"+builder.toString()+purl;
	}
	
	public static void main(String[] args) {
		System.out.println(unitOppsiteUrl("http://www.baidu.com/a/b/c/d.action","www.sina.com"));
	}
}
