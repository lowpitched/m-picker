package com.mlh.config.txt.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.mbag.log.LogTools;
import com.mlh.rule.IRule;
import com.mlh.rule.txt.TxtRule;

public class ConfigParser {

	private List<IRule> ruleObjs=new ArrayList<IRule>();
	
	public ConfigParser(InputStream in){
		init(in);
	}
	
	private void init(InputStream in){
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder lines = new StringBuilder();
			String buff = "";
			boolean isBegin = false;
			while((buff=reader.readLine())!=null){
				if(buff.equals("{".trim())){
					isBegin = true;
				}else if(buff.equals("}".trim())){
					break;
				}else{
					if(isBegin){
						lines.append(buff).append("\r\n");
					}
				}
			}
			String[] rules = lines.toString().split("\r\n\r\n");
			for(int i=0;i<rules.length;i++){
				String[] ruleLines = rules[i].split("\r\n");
				if(ruleLines.length==0) continue;
				String url = "";
				List<String> handleFlow = new ArrayList<String>();
				boolean firstLoop = true;
				for(int j=0;j<ruleLines.length;j++){
					if(null==ruleLines[j]
							||"".equals(ruleLines[j])
							||ruleLines[j].startsWith("#")){
						continue;
					}
					if(firstLoop){
						firstLoop = false;
						url = ruleLines[j];
						LogTools.i(this.getClass(), ruleLines[j]);
						continue;
					}
					handleFlow.add(ruleLines[j]);
					LogTools.i(this.getClass(), ruleLines[j]);
				}
				LogTools.i(this.getClass(), "\r\n-----------------");
				ruleObjs.add(TxtRule.newInstance(url, handleFlow));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("weblist配置文件解析错误");
		}finally{
			if(null!=reader){
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("读取配置文件weblist流关闭失败");
				}
			}
		}
		
	}
	
	public List<IRule> getRules(){
		return ruleObjs;
	}
	
	public static void main(String[] args) {
		InputStream in = ConfigParser.class.getResourceAsStream("weblist.txt");
		ConfigParser parser = new ConfigParser(in);
		List<IRule> rules = parser.getRules();
		System.out.println(rules);
	}
	
}
