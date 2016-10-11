package com.mlh.rule.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.mbag.log.LogTools;
import com.mlh.rule.IRule;
import com.mlh.rule.IRuleElement;
import com.mlh.rule.UrlParser;
import com.mlh.util.DomUtil;

public class XmlRule implements IRule{

	private List<String> urlList;
	
	private List<XmlRuleElement> elements = new ArrayList<XmlRuleElement>();
	
	public XmlRule(Node node){
		init(node);
	}
	
	@SuppressWarnings("unchecked")
	private void init(Node node){
		try{
			Document doc = DomUtil.getDocFrmTxt(node.asXML());
			if(null==urlList){
				this.urlList = UrlParser.getMultiUrl(doc.getRootElement().attributeValue("url"));
			}
			List<Node> children = doc.getRootElement().elements();
			if(null==children||children.size()==0){
				return;
			}
			XmlRuleElement element = new XmlRuleElement();
			for(Node n:children){
				int nodeType = MNode.getNodeType(n);
				if(nodeType==MNode.TYPE_MATCHER){
					MatcherNode mn = new MatcherNode(n);
					element.addNode(mn);
				}else if(nodeType==MNode.TYPE_OUT){
					OutNode on = new OutNode(n);
					element.addNode(on);
				}else if(nodeType==MNode.TYPE_FIELD){
					FieldNode fn = new FieldNode(n);
					element.addNode(fn);
				}else if(nodeType==MNode.TYPE_LINK){
					LinkNode ln = new LinkNode(n);
					element.addNode(ln);
				}
			}
			elements.add(element);
			for(Node n:children){
				init(n);
			}
		}catch(Exception e){
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public IRuleElement getRule(int index) {
		return elements.get(index);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("D:\\Workspaces\\MyEclipse for Spring 8.6\\m-picker\\config\\weblist.xml"));
		Element root = doc.getRootElement();
		List<Node> nodes = root.selectNodes("//web");
		XmlRule rule = new XmlRule(nodes.get(0));
		System.out.println(rule);
	}
	
}
