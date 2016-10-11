package com.mlh.rule.xml;

import java.util.List;

import org.dom4j.Node;

public class OutNode extends MNode{

	public OutNode(Node node) {
		super(node);
	}

	private String type;//file¡¢DB
	
	private String target;//Â·¾¶
	
	private String sql;//sql
	
	private String identity;//Ö÷¼ü
	
	private List<MNode> list;//Êä³ö×Ö¶Î

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public boolean addField(FieldNode field){
		return list.add(field);
	}
	
	public void addField(int index,FieldNode field){
		list.add(index,field);
	}
	
	public MNode removeField(int index){
		return list.remove(index);
	}
	
	public boolean removeField(FieldNode field){
		return list.remove(field);
	}
	
	public int getFieldSize(){
		return list.size();
	}
}
