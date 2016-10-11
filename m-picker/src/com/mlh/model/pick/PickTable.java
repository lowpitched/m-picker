package com.mlh.model.pick;

import java.util.ArrayList;
import java.util.List;

public class PickTable {

	private String url;
	
	private List<PickRow> rows=new ArrayList<PickRow>();
	
	public PickTable(){}
	
	public PickTable(List<PickRow> rows){
		this.rows=rows;
	}
	
	public PickTable(String url,List<PickRow> rows){
		this.url=url;
		this.rows=rows;
	}
	
	public boolean addRow(PickRow row){
		return rows.add(row);
	}
	
	public void addRow(int index,PickRow row){
		rows.add(index,row);
	}
	
	public boolean addAll(List<PickRow> rows){
		return this.rows.addAll(rows);
	}
	
	public void addAll(int index,List<PickRow> rows){
		this.rows.addAll(index,rows);
	}
	
	public PickRow removeRow(int index){
		return rows.remove(index);
	}
	
	public boolean removeRow(PickRow row){
		return rows.remove(row);
	}
	
	public PickRow getRow(int index){
		return rows.get(index);
	}
	
	public int getRowNum(){
		return rows.size();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
