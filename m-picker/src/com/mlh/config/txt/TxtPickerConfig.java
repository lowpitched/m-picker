package com.mlh.config.txt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mbag.log.LogTools;
import com.mlh.config.DefaultPickerConfig;
import com.mlh.config.IPickerConfig;
import com.mlh.config.txt.parser.ConfigParser;

/**
 * 读取及解析weblist.txt配置文件
 * @author menglihao
 * @since 20140505
 * @version 1.0
 */
public class TxtPickerConfig extends DefaultPickerConfig{

	private static long lastModifyTime;
	
	private void initPickerConfig(String path){
		InputStream in = null;
		try{
			File file = null;
			if(null==path){
				path = ConfigParser.class.getResource("weblist.txt").getPath();
				super.path=path;
				in = ConfigParser.class.getResourceAsStream("weblist.txt");
			}else{
				file = new File(path);
				in = new FileInputStream(file);
			}
			lastModifyTime=file.lastModified();
			ConfigParser parser = new ConfigParser(in);
			super.rules=parser.getRules();
		}catch(Exception e){
			LogTools.e(this.getClass(), e.getMessage(),e);
		}finally{
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
					LogTools.e(this.getClass(), e.getMessage(),e);

				}
			}
		}
		
		//后期可以加一层校验
	}
	
	protected static long getLastModifyTime() {
		return lastModifyTime;
	}

	public IPickerConfig config(String path) {
		TxtPickerConfig config = new TxtPickerConfig();
		config.initPickerConfig(path);
		return config;
	}

	@Override
	public IPickerConfig config() {
		return config(null);
	}
	
}
