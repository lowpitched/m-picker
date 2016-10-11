package com.mlh.out.parser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.mbag.log.LogTools;
import com.mlh.model.pick.PickRow;
import com.mlh.model.pick.PickTable;
import com.mlh.model.pick.PickUnit;

public class InsertPickTable {

	private String sql;
	
	private Connection conn;
	
	public InsertPickTable(String sql,Connection conn){
		this.sql=sql;
		this.conn=conn;
	}
	
	public void InsertTable(PickTable table){
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int rowNum = table.getRowNum();
			for(int i=0;i<rowNum;i++){
				PickRow row = table.getRow(i);
				List<PickUnit> units = row.getUnits();
				ps.setString(1, row.getId());
				int j=2;
				if(null!=row.getForeignId()||!"".equals(row.getForeignId())){
					ps.setString(2, row.getForeignId());
					j=3;
				}
				LogTools.i(this.getClass(), sql);
				for(PickUnit unit:units){
					if(unit.getType()==PickUnit.TYPE_STRING){
						ps.setString(j,unit.getStringValue());
					}else if(unit.getType()==PickUnit.TYPE_INTEGER){
						ps.setInt(j, unit.getIntValue());
					}else if(unit.getType()==PickUnit.TYPE_LONG){
						ps.setLong(j, unit.getLongValue());
					}else if(unit.getType()==PickUnit.TYPE_DOUBLE){
						ps.setDouble(j, unit.getDoubleValue());
					}else if(unit.getType()==PickUnit.TYPE_SHORT){
						ps.setShort(j, unit.getShortValue());
					}else if(unit.getType()==PickUnit.TYPE_FLOAT){
						ps.setFloat(j, unit.getFloatValue());
					}else if(unit.getType()==PickUnit.TYPE_BOOLEAN){
						ps.setBoolean(j, unit.getBooleanValue());
					}else if(unit.getType()==PickUnit.TYPE_BYTE){
						ps.setByte(j, unit.getByteValue());
					}else if(unit.getType()==PickUnit.TYPE_BIN){
						ps.setBytes(j, unit.getBinValue());
					}else if(unit.getType()==PickUnit.TYPE_DATE){
						ps.setDate(j, new java.sql.Date(unit.getDateValue().getTime()));
					}
					LogTools.i(this.getClass(), unit.toString());
					j++;
				}
				ps.addBatch();
				if((i!=0&&i%1000==0)||i==rowNum-1){
					ps.executeBatch();
					ps.clearBatch();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LogTools.e(this.getClass(), e.getMessage(),e);
		}
	}
	
}
