package com.mlh.out.parser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mbag.log.LogTools;
import com.mlh.model.out.TabField;
import com.mlh.util.DateUtil;
@Deprecated
public class InsertRuleParser {

	private String subSql;
	private String tableName;
	private String[] fieldNames;
	private List<TabField> fields;
	private Connection conn;
	
	public InsertRuleParser(String sqlRule,Connection conn){
		try {
			this.subSql=sqlRule;
			this.conn = conn;
			String[] words = subSql.split(" ");
			if(words.length<=3||subSql.split("(").length<=1){
				throw new RuntimeException("weblist配置文件insert语句语法错误");
			}
			this.tableName=words[2];
			String tmpStr = subSql.split("(")[1];
			this.fieldNames=tmpStr.substring(0,tmpStr.length()-1).split(",");
		
			Statement st = conn.createStatement();
			String sql = "select " +
					"		column_name,data_type,data_length " +
					"     from " +
					"		user_tab_cols " +
					"     where " +
					"		table_name='"+tableName.toUpperCase()+"' " +
					"		and column_name in (";
			StringBuilder builder = new StringBuilder(sql);
			String orderBySql = "order by case column_name case column_name ";
			StringBuilder orderBy = new StringBuilder(orderBySql);
			for(int i=0;i<fieldNames.length-1;i++){
				builder.append("'").append(fieldNames[i].toUpperCase()).append("'").append(",");
				orderBy.append(" when ").append(fieldNames[i]).append(" then ").append(i);
			}
			builder.append("'").append(fieldNames[fieldNames.length-1].toUpperCase()).append("'").append(")");
			orderBy.append(" when ").append(fieldNames[fieldNames.length-1].toUpperCase())
			       .append(" then ").append(fieldNames.length-1).append(" end ");
			ResultSet rs = st.executeQuery(builder.toString()+orderBy.toString());
			while(rs.next()){
				String columnName = rs.getString("column_name");
				String dataType = rs.getString("data_type");
				String dataLength = rs.getString("data_length");
				TabField field = new TabField();
				field.setFieldName(columnName);
				field.setFieldLength(dataLength);
				if(dataType.equals("VARCHAR")||dataType.equals("VARCHAR2")){
					field.setFieldType(TabField.TYPE_VARCHAR);
				}else if(dataType.equals("LONG")||dataType.equals("NUMBER")){
					field.setFieldType(TabField.TYPE_LONG);
				}else if(dataType.equals("INTEGER")){
					field.setFieldType(TabField.TYPE_INTEGER);
				}else if(dataType.equals("DATE")){
					field.setFieldType(TabField.TYPE_DATE);
				}else if(dataType.equals("NUMBER")
						||dataType.equals("DOUBLE")
						||dataType.equals("FLOAT")){
					field.setFieldType(TabField.TYPE_DOUBLE);
				}
				fields.add(field);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}
	
	public void insertData(List<List<String>> data) throws Exception{
		List<List<TabField>> tab = new ArrayList<List<TabField>>();
		for(List<String> row:data){
			if(row.size()!=fieldNames.length){
				throw new Exception("抓取字段个数："+row.size()+"|insert规则字段个数："+fieldNames.length+"【字段个数不匹配】");
			}
			List<TabField> tabRow = new ArrayList<TabField>();
			for(int i=0;i<fieldNames.length;i++){
				row.get(i);
				TabField field = newTabField(fields.get(i));
				int type = field.getFieldType();
				if(type==TabField.TYPE_LONG){
					field.setLongValue(Long.parseLong(row.get(i)));
				}else if(type==TabField.TYPE_INTEGER){
					field.setIntValue(Integer.parseInt(row.get(i)));
				}else if(type==TabField.TYPE_DOUBLE){
					field.setDoubleValue(Double.parseDouble(row.get(i)));
				}else if(type==TabField.TYPE_VARCHAR){
					field.setCharValue(row.get(i));
				}else if(type==TabField.TYPE_DATE){
					field.setDateValue(new java.sql.Date(DateUtil.parseDate(row.get(i)).getTime()));
				}else{
					throw new Exception("不支持的数据格式");
				}
				tabRow.add(field);
			}
			tab.add(tabRow);
		}
		insert(tab);
	}
	
	private void insert(List<List<TabField>> tab) throws Exception {
		try {
			if(null==conn||conn.isClosed()){
				throw new Exception("数据库连接不存在或已关闭");
			}
			String preSql = subSql+ " values(";
			StringBuilder builder = new StringBuilder(preSql);
			for(int i=0;i<fieldNames.length-1;i++){
				builder.append("?").append(",");
			}
			builder.append("?").append(")");
			PreparedStatement ps = conn.prepareStatement(builder.toString());
			for(int j=0;j<tab.size();j++){
				List<TabField> row = tab.get(j);
				for(int i=0;i<row.size();i++){
					TabField field = row.get(i);
					int fieldType = field.getFieldType();
					if(fieldType==TabField.TYPE_DATE){
						ps.setDate(i+1,field.getDateValue() );
					}else if(fieldType==TabField.TYPE_LONG){
						ps.setLong(i+1, field.getLongValue());
					}else if(fieldType==TabField.TYPE_INTEGER){
						ps.setInt(i+1, field.getIntValue());
					}else if(fieldType==TabField.TYPE_DOUBLE){
						ps.setDouble(i+1, field.getDoubleValue());
					}else if(fieldType==TabField.TYPE_DOUBLE){
						ps.setDouble(i+1, field.getDoubleValue());
					}else if(fieldType==TabField.TYPE_VARCHAR){
						ps.setString(i+1, field.getCharValue());
					}
				}
				ps.addBatch();
				if(j%1000==0||j==(tab.size()-1)){
					ps.executeBatch();
					ps.clearBatch();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private TabField newTabField(TabField field){
		TabField tf = new TabField();
		tf.setCharValue(field.getCharValue());
		tf.setDateValue(field.getDateValue());
		tf.setDoubleValue(field.getDoubleValue());
		tf.setFieldLength(field.getFieldLength());
		tf.setFieldName(field.getFieldName());
		tf.setFieldType(field.getFieldType());
		tf.setIntValue(field.getIntValue());
		tf.setLongValue(field.getLongValue());
		return tf;
	}
	
	public void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}
	
}