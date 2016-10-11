package com.mlh.out;

import java.sql.Connection;
import java.sql.SQLException;

import com.mbag.log.LogTools;
import com.mlh.model.pick.PickTable;
import com.mlh.out.parser.InsertPickTable;
import com.mlh.util.JDBCUtil;

public class DBOutPutHandler implements IOutPutHandler{

	private Connection conn;
	
	private String subSql;
	
	public void init(Object...objs) {
		 this.subSql = (String)objs[0];
		 conn = JDBCUtil.getConnection();
	}
	
	/*@Deprecated
	public void outPut(List<List<String>> data) {
		InsertRuleParser parser = new InsertRuleParser(subSql,conn);
		try {
			parser.insertData(data);
		} catch (Exception e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}*/

	public void outPut(PickTable table){
		InsertPickTable insert = new InsertPickTable(subSql,conn);
		insert.InsertTable(table);
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}

}
