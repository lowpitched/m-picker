package com.mlh.config.xml.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.mbag.log.LogTools;
import com.mlh.config.xml.XmlPickerConfig;
import com.mlh.rule.xml.XmlRule;

public class XmlConfigParser {

	@SuppressWarnings("unchecked")
	public XmlPickerConfig parser(InputStream in){
		XmlPickerConfig config = new XmlPickerConfig();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(in);
			Element root = doc.getRootElement();
			List<Node> nodes = root.selectNodes("/picker/web[not(@isUse)]|/piciker/web[@isUse='true']");
			for(Node node:nodes){
				XmlRule rule = new XmlRule(node.getDocument());
				config.addRule(rule);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
		return config;
	}

	public static void main(String[] args) throws FileNotFoundException {
		XmlConfigParser parser = new XmlConfigParser();
		parser.parser(new FileInputStream(new File("D:\\Workspaces\\MyEclipse for Spring 8.6\\m-picker\\config\\weblist.xml")));
	}
	
}
