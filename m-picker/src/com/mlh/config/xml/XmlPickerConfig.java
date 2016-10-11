package com.mlh.config.xml;

import java.io.IOException;
import java.io.InputStream;

import com.mbag.log.LogTools;
import com.mlh.config.DefaultPickerConfig;
import com.mlh.config.IPickerConfig;
import com.mlh.config.xml.parser.XmlConfigParser;

public class XmlPickerConfig extends DefaultPickerConfig{

	public IPickerConfig config() {
		InputStream in = null;
		try{
			in = this.getClass().getResourceAsStream("weblist.xml");
			if(null==in){
				throw new RuntimeException("类路径下没有找到weblist.xml");
			}
			XmlConfigParser parser = new XmlConfigParser();
			return parser.parser(in);
		}catch(Exception e){
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}finally{
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					LogTools.e(this.getClass(), e.getMessage(),e);
				}
			}
		}
		return null;
	}

	public IPickerConfig config(String path) {
		return null;
	}
	
}
