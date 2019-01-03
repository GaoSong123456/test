package com.test.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  日期工具类
 * @author kevin
 * @since 1.0.0
 * @create 2018/8/5
 *
 */
public class DateUtil {
	
	
	public static String dateToString(Date date,String dateFormat) throws Exception{
		SimpleDateFormat format=new SimpleDateFormat(dateFormat);
		return format.format(date);
	}
	
	public static Timestamp strToDate(String str, String dateFormat) throws Exception{
		SimpleDateFormat format=new SimpleDateFormat(dateFormat);
        Date parse = format.parse(str);
        java.sql.Timestamp dateSQL = new java.sql.Timestamp(parse.getTime());
        return dateSQL;
	}
}
