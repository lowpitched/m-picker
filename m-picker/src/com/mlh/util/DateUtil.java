package com.mlh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mbag.log.LogTools;

public class DateUtil {

	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	private DateUtil(){}
	
	public static Date parseDate(String date){
		try {
			return format.parse(date);
		} catch (ParseException e) {
			LogTools.e(DateUtil.class, e.getMessage(),e);
		}
		return null;
	}
	
}
