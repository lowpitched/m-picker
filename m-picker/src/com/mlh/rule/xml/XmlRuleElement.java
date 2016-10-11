package com.mlh.rule.xml;

import java.util.ArrayList;
import java.util.List;

import com.mlh.http.IWebInfoSpider;
import com.mlh.model.pick.PickTable;
import com.mlh.rule.IRuleElement;

public class XmlRuleElement implements IRuleElement{

	private List<MNode> nodes = new ArrayList<MNode>();
	
	private IRuleElement nextElement;

	private boolean isOut=false;

	public IRuleElement nextElement() {
		return null;
	}

	public PickTable matcher(PickTable table, String url, IWebInfoSpider spider) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public IRuleElement init(Object obj, IRuleElement next) {
		nodes = (List<MNode>) obj;
		if(nodes.get(0).getNodeType()==MNode.TYPE_OUT){
			this.isOut=true;
		}
		return this;
	}

	public IRuleElement getNextElement() {
		return nextElement;
	}

	public List<MNode> getNodes() {
		return nodes;
	}

	public boolean addNode(MNode node){
		return nodes.add(node);
	}
	
	public void addNode(int index,MNode node){
		nodes.add(index,node);
	}
	
	@Override
	public boolean isOut() {
		return isOut;
	}
	
}
