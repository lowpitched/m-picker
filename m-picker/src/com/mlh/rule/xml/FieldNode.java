package com.mlh.rule.xml;

import org.dom4j.Node;


public class FieldNode extends MNode{
	
	//String-Integer-Double-Float-Short-Character-byte-bytes-Date
	private String type; 
	//xpath-regex
	private String matchType;
	//xpath-regex expression
	private String matchValue;
	//format
	private String format;
	
	private LinkNode links;
	
	public FieldNode(Node node) {
		super(node);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public LinkNode getLinks() {
		return links;
	}
	public void setLinks(LinkNode links) {
		this.links = links;
	}
	
}
