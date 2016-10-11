package com.mlh.model.out;

import java.sql.Date;

public class TabField {

	public static final int TYPE_INTEGER=1;
	
	public static final int TYPE_LONG=1;

	public static final int TYPE_VARCHAR=3;

	public static final int TYPE_DATE=4;
	
	public static final int TYPE_DOUBLE=5;
	
	private String fieldName;
	
	private int fieldType;
	
	private String fieldLength;
	
	private int intValue;
	
	private long longValue;
	
	private double doubleValue;
	
	private String charValue;
	
	private Date dateValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public String getCharValue() {
		return charValue;
	}

	public void setCharValue(String charValue) {
		this.charValue = charValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	
}
