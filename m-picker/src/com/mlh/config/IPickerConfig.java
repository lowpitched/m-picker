package com.mlh.config;

import java.util.List;

import com.mlh.rule.IRule;

public interface IPickerConfig {

	 IPickerConfig config();

	 IPickerConfig config(String path);
	
	 IRule getRule(int i);
	
	 boolean addRule(IRule rule);
	
	 void addRule(int i,IRule rule);

	 IRule remove(int index);
	
	 boolean remove(IRule rule);
	
	 void setRules(List<IRule> rules);

	 int getRuleNum();

}
