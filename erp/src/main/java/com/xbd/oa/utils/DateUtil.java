package com.xbd.oa.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * 2014.11.27
 * 
 * @author fangwei
 */

public class DateUtil {

	enum Signal {
		YMD, ALL_12H, ALL_24H
	}

	/** 输出格式: 2013-01-01 */
	public static final String YMD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";

	/** 输出格式: 2013-01-01 01:00:00 */
	public static final String ALL_12H = "yyyy-MM-dd hh:mm:ss";

	/** 输出格式: 2013-01-01 13:00:00 */
	public static final String ALL_24H = "yyyy-MM-dd HH:mm:ss";

	public static Date getNow() {
		DateFormat dateFormat = new SimpleDateFormat(DateUtil.ALL_24H);
		return (Date) ConvertUtils.convert(dateFormat.format(new Date()), Date.class);
	}

	public static String longToddhhmm(Long time) {
		time = time / 1000;
		long day = time / (24 * 3600);
		long hour = time % (24 * 3600) / 3600;
		long minute = time % 3600 / 60;
		return day + "天" + hour + "小时" + minute + "分";
	}
	public static String longToddhhmm(Date begin, Date end) {
		return longToddhhmm(end.getTime() - begin.getTime());
	}

	public static String longTohhmm(Long time) {
		time = time / 1000;
		long hour = time / 3600;
		long minute = time % 3600 / 60;
		return hour + "小时" + minute + "分";
	}

	public static String longTohhmm(Date begin, Date end) {
		return longTohhmm(end.getTime() - begin.getTime());
	}
	

	/**
	 * 字符串转date
	 * 
	 * @param source
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static Date parseDate(String source, String pattern) {
		if (source == null || source.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串转date
	 * 
	 * @param source
	 *            格式 ： 2013-01-01 13:00:00
	 * @return
	 */
	public static Date parseDate(String source) {
		if (source == null || source.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.ALL_24H);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(ALL_24H);
		return sdf.format(date);
	}

	public static String formatDate(Timestamp date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(ALL_24H);
		return sdf.format(date);
	}

	public static String formatDate(Date date, String formart) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formart);
		return sdf.format(date);
	}

	public static int compare_date(String date1, String date2) {

		DateFormat df = new SimpleDateFormat(ALL_24H);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static int compare_time(Long time1, Long time2) {

		try {
			if (time1 > time2) {
				return 1;
			} else if (time1 < time2) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		String aString = DateUtil.formatDate(new Date());
		System.out.println(aString);
		Date date = DateUtil.parseDate(aString, ALL_24H);
		System.out.print(date);

		int i = compare_date("1999-12-12 15:21:00", "1999-12-11 09:59:00");
		System.out.println("i==" + i);

	}

}
