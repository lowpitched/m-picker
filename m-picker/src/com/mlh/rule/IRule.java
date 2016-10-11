package com.mlh.rule;

import java.util.List;

public interface IRule {

	List<String> getUrlList();

	IRuleElement getRule(int index);

}
