package com.mlh.model.pick;

import java.util.Date;

/**
 * 抓取最小单元，可理解为表中的一个字段
 * @author songwei
 *
 */
public class PickUnit {

	public static final short TYPE_STRING=1;
	
	public static final short TYPE_INTEGER=2;

	public static final short TYPE_LONG=3;

	public static final short TYPE_DOUBLE=4;

	public static final short TYPE_SHORT=5;

	public static final short TYPE_FLOAT=6;

	public static final short TYPE_DATE=7;

	public static final short TYPE_BOOLEAN=8;

	public static final short TYPE_BYTE=9;

	public static final short TYPE_BIN=10;

	private String srcString;
	
	private short type;
	
	private String stringValue;
	
	private int intValue;
	
	private long longValue;
	
	private double doubleValue;
	
	private short shortValue;
	
	private float floatValue;
	
	private boolean booleanValue;
	
	private byte byteValue;
	
	private byte[] binValue;

	private Date dateValue;

	public PickUnit(String value,short type){
		this.type=type;
		if(type==TYPE_STRING){
			this.stringValue=value;
		}else if(type==TYPE_INTEGER){
			this.intValue=Integer.parseInt(value);
		}else if(type==TYPE_LONG){
			this.longValue=Long.parseLong(value);
		}else if(type==TYPE_DOUBLE){
			this.doubleValue=Double.parseDouble(value);
		}else if(type==TYPE_SHORT){
			this.shortValue=Short.parseShort(value);
		}else if(type==TYPE_FLOAT){
			this.floatValue=Float.parseFloat(value);
		}else if(type==TYPE_BOOLEAN){
			this.booleanValue=Boolean.parseBoolean(value);
		}else if(type==TYPE_BYTE){
			this.byteValue=Byte.parseByte(value);
		}
	}
	
	public PickUnit(String stringValue){
		this.type=PickUnit.TYPE_STRING;
		this.stringValue=stringValue;
	}
	
	public PickUnit(long longValue){
		this.type=PickUnit.TYPE_LONG;
		this.longValue=longValue;
	}
	
	public PickUnit(int intValue){
		this.type=PickUnit.TYPE_INTEGER;
		this.intValue=intValue;
	}
	
	public PickUnit(double doubleValue){
		this.type=PickUnit.TYPE_DOUBLE;
		this.doubleValue=doubleValue;
	}
	
	public PickUnit(float floatValue){
		this.type=PickUnit.TYPE_FLOAT;
		this.floatValue=floatValue;
	}
	
	public PickUnit(short shortValue){
		this.type=PickUnit.TYPE_SHORT;
		this.shortValue=shortValue;
	}
	
	public PickUnit(Date dateValue){
		this.type=PickUnit.TYPE_DATE;
		this.dateValue=dateValue;
	}
	
	public PickUnit(boolean booleanValue){
		this.type=PickUnit.TYPE_BOOLEAN;
		this.booleanValue=booleanValue;
	}
	
	public PickUnit(byte byteValue){
		this.type=PickUnit.TYPE_BYTE;
		this.byteValue=byteValue;
	}
	
	public PickUnit(byte[] binValue){
		this.type=PickUnit.TYPE_BIN;
		this.binValue=binValue;
	}
	
	public short getType() {
		return type;
	}

	public String getStringValue() {
		return stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public long getLongValue() {
		return longValue;
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public short getShortValue() {
		return shortValue;
	}

	public float getFloatValue() {
		return floatValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public boolean getBooleanValue() {
		return booleanValue;
	}

	public byte getByteValue() {
		return byteValue;
	}

	public byte[] getBinValue() {
		return binValue;
	}
	
	public String getSrcString() {
		return srcString;
	}

	public void setSrcString(String srcString) {
		this.srcString = srcString;
	}

	@Override
	public String toString() {
		if(type==TYPE_STRING){
			return this.stringValue;
		}else if(type==TYPE_INTEGER){
			return this.intValue+"";
		}else if(type==TYPE_LONG){
			return this.longValue+"";
		}else if(type==TYPE_DOUBLE){
			return this.doubleValue+"";
		}else if(type==TYPE_SHORT){
			return this.shortValue+"";
		}else if(type==TYPE_FLOAT){
			return this.floatValue+"";
		}else if(type==TYPE_BOOLEAN){
			return this.booleanValue+"";
		}else if(type==TYPE_BYTE){
			return this.byteValue+"";
		}else{
			return "";
		}
	}
	
}
