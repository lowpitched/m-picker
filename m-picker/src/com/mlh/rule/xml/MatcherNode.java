package com.mlh.rule.xml;

import java.util.List;

import org.dom4j.Node;

public class MatcherNode extends MNode{

	public MatcherNode(Node node) {
		super(node);
	}

	private String type;
	
	private String value;
	
	private int index;
	
	private List<MNode> list;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<MNode> getNodes() {
		return list;
	}

	public void setNodes(List<MNode> list) {
		this.list = list;
	} 
	
}
