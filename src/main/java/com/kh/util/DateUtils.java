package com.kh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 */
	public static String getCurrentDate(String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

	/**
	 * ��ʽ������
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * ��ʽ������
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
	 * ���������ַ���
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
	 * ��ȡ����ʱ����������
	 * 
	 * @param date
	 * @return
	 */
	public static long getDiffDays(Date startDate, Date endDate) {
		long difftime = endDate.getTime() - startDate.getTime();
		return difftime / (24L * 60L * 60L * 1000L);
	}
	
	/**
	 * ������
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
	 * ������
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
     * ������
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
	 * �ж������Ƿ�Ϊ��ĩ
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
	 * �Ƿ���ʱ��
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
			System.out.println(DateUtils.formatDate(currert, "yyyy-MM-dd")+" : "+(DateUtils.isSummer(currert)?"��ʱ��":"��ʱ��"));
		}
	}
}
