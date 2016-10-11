package com.mlh.rule.txt;

import java.util.ArrayList;
import java.util.List;

import com.mlh.rule.IRule;
import com.mlh.rule.IRuleElement;
import com.mlh.rule.UrlParser;

/**
 * 根据PickerConfig类封装规则
 * 
 * @author mlh
 * @since 20140505
 * @version 1.0
 */
public class TxtRule implements IRule{

	private List<String> urlList;// 处理后的url

	private List<IRuleElement> rules = new ArrayList<IRuleElement>();// 规则

	private TxtRule(String url, List<String> handleFlow) {
		setUrl(url);
		TxtRuleElement curr = null;
		for(int i=handleFlow.size()-1;i>=0;i--){
			if(null==handleFlow.get(i)||"".equals(handleFlow.get(i))) continue;
			if(i==handleFlow.size()-1){
				curr = (TxtRuleElement) new TxtRuleElement().init(handleFlow.get(i), null);
				rules.add(0,curr);
			}else{
				curr = (TxtRuleElement) new TxtRuleElement().init(handleFlow.get(i), (TxtRuleElement)rules.get(0));
				rules.add(0,curr);
			}
		}
	}
	
	public static TxtRule newInstance(String url, List<String> handleFlow) {
		return new TxtRule(url, handleFlow);
	}

	protected void setUrl(String url) {
		UrlParser.getMultiUrl(url);
	}

	public IRuleElement getRule(int index) {
		return rules.get(index);
	}

	public IRuleElement removeRule(int index) {
		return rules.remove(index);
	}

	public boolean removeRule(IRuleElement rule) {
		return rules.remove(rule);
	}

	public boolean addRule(IRuleElement rule) {
		return rules.add(rule);
	}

	public void addRule(IRuleElement rule, int index) {
		rules.add(index, rule);
	}

	public int ruleSize() {
		return rules.size();
	}

	public List<String> getUrlList() {
		return urlList;
	}


}
