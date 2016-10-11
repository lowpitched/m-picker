package com.mlh.rule.xml;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import com.mbag.log.LogTools;
import com.mlh.util.DomUtil;

public abstract class MNode {

	public static final int TYPE_MATCHER = 0;

	public static final int TYPE_OUT = 1;

	public static final int TYPE_FIELD = 2;

	public static final int TYPE_LINK = 3;

	private int nodeType;

	public MNode(Node node) {
		newInstance(node);
	}

	@SuppressWarnings("unchecked")
	private <T> T newInstance(Node node) {
		try {
			MNode obj = (MNode) this;
			Field[] fields = this.getClass().getDeclaredFields();
			Iterator<Attribute> it = node.getDocument().getRootElement()
					.attributeIterator();
			while (it.hasNext()) {
				Attribute attr = (Attribute) it.next();
				String attrName = attr.getName();
				String attrValue = attr.getValue();
				for (Field field : fields) {
					if (field.getName().equals(attrName)) {
						field.setAccessible(true);
						String simpleName = field.getType().getSimpleName();
						if (simpleName.equals("String")) {
							field.set(obj, attrValue);
						} else if (simpleName.equals("Integer")
								|| simpleName.equals("int")) {
							field.set(obj, Integer.parseInt(attrValue));
						} else if (simpleName.equals("Long")
								|| simpleName.equals("long")) {
							field.set(obj, Long.parseLong(attrValue));
						} else if (simpleName.equals("Float")
								|| simpleName.equals("float")) {
							field.set(obj, Float.parseFloat(attrValue));
						} else if (simpleName.equals("Double")
								|| simpleName.equals("double")) {
							field.set(obj, Double.parseDouble(attrValue));
						} else if (simpleName.equals("Short")
								|| simpleName.equals("short")) {
							field.set(obj, Short.parseShort(attrValue));
						} else if (simpleName.equals("Character")
								|| simpleName.equals("char")) {
							field.set(obj, attrValue);
						}
					}
				}
			}
			String nodeName = node.getName();
			if (nodeName.equals("matcher")) {
				obj.setNodeType(MNode.TYPE_MATCHER);
			} else if (nodeName.equals("out")) {
				obj.setNodeType(MNode.TYPE_OUT);
			} else if (nodeName.equals("field")) {
				obj.setNodeType(MNode.TYPE_FIELD);
			} else if (nodeName.equals("link")) {
				obj.setNodeType(MNode.TYPE_LINK);
			}
			return (T) this;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(), e);
		}
		return null;
	}

	protected void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}

	protected int getNodeType() {
		return nodeType;
	}

	public static void main(String[] args) throws DocumentException,
			FileNotFoundException {
		Node node = DomUtil.getNodeFrmTxt(
				"<table value='123' type='string' index='1'><tr></tr></table>",
				"/table", 0);
		MatcherNode m = new MatcherNode(node);
		System.out.println(m.getType());
		System.out.println(m.getValue());
		System.out.println(m.getIndex());
	}
	
	public static int getNodeType(Node node) throws Exception{
		String nodeName = node.getName();
		if (nodeName.equals("matcher")) {
			return MNode.TYPE_MATCHER;
		} else if (nodeName.equals("out")) {
			return MNode.TYPE_OUT;
		} else if (nodeName.equals("field")) {
			return MNode.TYPE_FIELD;
		} else if (nodeName.equals("link")) {
			return MNode.TYPE_LINK;
		}else{
			throw new Exception("不支持的xml节点类型");
		}
	}
}
