package com.mlh.rule.xml;

import java.util.List;

import org.dom4j.Node;

public class WebNode extends MNode{

	public WebNode(Node node) {
		super(node);
	}

	/***属性*/
	private String url;
	
	private boolean isUse;
	
	private String method;
	
	private String params;
	/**子matcher节点*/
	private List<MNode> list;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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
