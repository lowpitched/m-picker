package com.mlh.rule.xml;

import java.util.List;

import org.dom4j.Node;

public class LinkNode extends MNode{

	private String matchType;
	private String matchValue;
	private List<MNode> list;
	
	public LinkNode(Node node) {
		super(node);
	}
	
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchValue() {
		return matchValue;
	}
	public void setMatchValue(String matchValue) {
		this.matchValue = matchValue;
	}
	
	public boolean addMatcher(MatcherNode matcher){
		return list.add(matcher);
	}
	
	public void addMatcher(int index,MatcherNode matcher){
		list.add(index,matcher);
	}
	
	public MNode removeMatcher(int index){
		return list.remove(index);
	}
	
	public boolean removeMatcher(MatcherNode matcher){
		return list.remove(matcher);
	}
	
	public int getMatcherSize(){
		return list.size();
	}
	
}
