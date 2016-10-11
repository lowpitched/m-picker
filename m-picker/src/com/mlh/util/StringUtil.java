package com.mlh.util;

public class StringUtil {

	public static final int FRONT_CONTAIN_INDEX=0;
	
	public static final int BEHIND_CONTAIN_INDEX=1;
	
	public static final int NONE_CONTAIN_INDEX=2;
	
	private StringUtil(){}
	/**
	 * 根据索引切割文本，indexContain参数指定
	 * 包含索引的子串
	 * @param text
	 * @param index
	 * @param indexContain
	 * @return
	 */
	public static String[] splitByIndex(String text,int index,int indexContain){
		String[] result = new String[2];
		if(indexContain==FRONT_CONTAIN_INDEX){
			result[0]=text.substring(0,index+1);
			result[1]=text.substring(index+1);
		}else if(indexContain==BEHIND_CONTAIN_INDEX){
			result[0]=text.substring(0,index);
			result[1]=text.substring(index);
		}else if(indexContain==NONE_CONTAIN_INDEX){
			result[0]=text.substring(0,index);
			result[1]=text.substring(index+1);
		}else{
			throw new IllegalArgumentException("参数错误"+indexContain);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String[] split = StringUtil.splitByIndex("0123456789", 3, StringUtil.NONE_CONTAIN_INDEX);
		System.out.println(split[0]+"-----"+split[1]);
	}
	
}
