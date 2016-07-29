package com.kh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate(String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String formatDate(Date date, String formatString) {
		DateFormat df = new SimpleDateFormat(formatString);
		return df.format(date);
	}

	/**
	 * 解析日期字符串
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyyMMdd");
	}

	public static Date parseDate(String dataStr, String formatStr) {
		DateFormat df = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = df.parse(dataStr);
		} catch (ParseException e) {
			// e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取两个时间间隔的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long getDiffDays(Date startDate, Date endDate) {
		long difftime = endDate.getTime() - startDate.getTime();
		return difftime / (24L * 60L * 60L * 1000L);
	}
	
	/**
	 * 日增加
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinutes(Date date,int amount) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.MINUTE, amount);
        return calendar.getTime();  
    }
	
	/**
	 * 日增加
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDay(Date date,int amount) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();  
    }
	
	/** 
     * 月增加
     * @param date 
     * @return 
     */  
    public static Date addMonth(Date date,int amount) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.MONTH, amount);  
        return calendar.getTime();  
    } 

	/**
	 * 判断日期是否为周末
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case 1:
			return true;
		case 7:
			return true;
		default:
			return false;
		}
	}

	/**
	 * 是否夏时令
	 * @param date
	 * @return
	 */
	public static boolean isSummer(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.MONTH);
		return (day>4&&day<10);
	}
	
	public static void main(String[] args) {
		Date start = DateUtils.parseDate("20160101", "yyyyMMdd");
		for(int i=0;i<12;i++){
			Date currert = DateUtils.addMonth(start, i);
			System.out.println(DateUtils.formatDate(currert, "yyyy-MM-dd")+" : "+(DateUtils.isSummer(currert)?"夏时令":"冬时令"));
		}
	}
}
