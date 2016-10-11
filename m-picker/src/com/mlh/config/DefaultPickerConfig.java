package com.mlh.config;

import java.util.List;

import com.mlh.rule.IRule;

public abstract class DefaultPickerConfig implements IPickerConfig{

	protected List<IRule> rules;
	
	protected String path;

	public IRule getRule(int i) {
		return rules.get(i);
	}

	public boolean addRule(IRule rule) {
		return rules.add(rule);
	}

	public void addRule(int i, IRule rule) {
		rules.add(i,rule);
	}

	public IRule remove(int index) {
		return rules.remove(index);
	}

	public boolean remove(IRule rule) {
		return rules.remove(rule);
	}

	public void setRules(List<IRule> rules) {
		this.rules = rules;
	}

	public int getRuleNum() {
		return rules.size();
	}

	public String getPath() {
		return path;
	}
	
}
