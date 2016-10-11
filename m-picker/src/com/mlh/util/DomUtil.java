package com.mlh.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

@SuppressWarnings("unchecked")
public class DomUtil {

	public static int levelNum=1;
	
	private DomUtil(){}
	
	public static Document getDocFrmTxt(String text) throws DocumentException{
		return DocumentHelper.parseText(text);
	}
	
	public static List<Node> getNodeFrmTxt(String text,String xpath) throws DocumentException{
		return getDocFrmTxt(text).selectNodes(xpath);
	}
	
	public static Node getNodeFrmTxt(String text,String xpath,int index) throws DocumentException{
		return getNodeFrmTxt(text, xpath).get(index);
	}
	
	public static Node getSingleNodeFrmTxt(String text,String xpath) throws Exception{
		 if(getDocFrmTxt(text).selectNodes(xpath).size()>1
				 ||getDocFrmTxt(text).selectNodes(xpath).size()==0){
			 throw new Exception(text+"中"+xpath+"个数大于1或等于0");
		 }
		 return (Node)getDocFrmTxt(text).selectNodes(xpath).get(0);
	}
	
	public static List<String> getNodeTxtFrmText(String text,String xpath) throws DocumentException{
		List<Node> nodes = getDocFrmTxt(text).selectNodes(xpath);
		List<String> texts = new ArrayList<String>();
		for(Node node:nodes){
			texts.add(node.getStringValue());
		}
		return texts;
	}
	
	public static String getNodeAsXml(String text,String xpath,int index) throws DocumentException{
		return getNodeFrmTxt(text,xpath,index).asXML();
	}
	
	public static List<String> getNodeAsXml(String text,String xpath) throws DocumentException{
		List<String> list = new ArrayList<String>();
		 List<Node> nodes = getNodeFrmTxt(text,xpath);
		 for(Node node:nodes){
			 list.add(node.asXML());
		 }
		 return list;
	}
	
	public static String getNodeTxtFrmTxt(String text,String xpath,int index) throws DocumentException{
		return getNodeTxtFrmText(text, xpath).get(index);
	}
	
	public static String getSingleNodeTxtFrmTxt(String text,String xpath) throws Exception{
		if(getNodeTxtFrmText(text, xpath).size()>1
				||getNodeTxtFrmText(text, xpath).size()==0){
			throw new Exception("匹配的标签个数为0或超过1个");
		}
		return getNodeTxtFrmText(text, xpath).get(0);
	}
	/**
	 * 获取xml的最大层次数量
	 * @return
	 */
	
	/*public static int getXmlMaxLevel(Node node){
		try {
			String xml = node.asXML();
			System.out.println(xml);
			List<Node> eles = DomUtil.getDocFrmTxt(xml).getRootElement().elements();
			if(null==eles||eles.size()==0){
				list.add(levelNum);
				return levelNum;
			}else{
				levelNum++;
				for(Node n:eles){
					int i = getXmlMaxLevel(n);
					System.out.println(i);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return levelNum;
	}*/
	
}
