package com.mlh.out;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import com.mbag.log.LogTools;
import com.mlh.model.pick.PickRow;
import com.mlh.model.pick.PickTable;
import com.mlh.model.pick.PickUnit;

public class TxtOutPutHandler implements IOutPutHandler{

	private File file;
	
	private RandomAccessFile rFile;
	
	private String path;
	
	public void init(Object...objs) {
		try {
			String rule = (String)objs[0];
			if(null==rule||"".equals(rule)){
				throw new Exception(rule+"文件路径不能为空");
			}
			this.path=rule;
			if(null==file){
				file = new File(path);
			}
			file.createNewFile();
			rFile = new RandomAccessFile(file,"rw");
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		} catch (Exception e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}
	
	public void outPut(PickTable table){
		if(!file.exists()){
			throw new RuntimeException("输出文件创建失败");
		}
		try {
			int rowNum = table.getRowNum();
			for(int i=0;i<rowNum;i++){
				PickRow row = table.getRow(i);
				int unitSize = row.getUnitSize();
				rFile.write((row.getId()+"\t").getBytes());
				rFile.write((row.getForeignId()+"\t").getBytes());
				for(int j=0;j<unitSize;j++){
					PickUnit unit = row.getUnit(j);
					rFile.write((unit.toString()+"\t").getBytes());
				}
				rFile.write("\r\n".getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}
	
	public void outPut(List<List<String>> data) {
		if(!file.exists()){
			throw new RuntimeException("输出文件创建失败");
		}
		try {
			for(List<String> list:data){
				for(String field:list){
					rFile.write((field+"\t").getBytes());
				}
				rFile.write("\r\n".getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}

	public String getPath() {
		return path;
	}

	public void close(){
		try {
			rFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}

	
}
