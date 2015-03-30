package com.xbd.oa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public abstract class DateUtils {

	private static DateFormat dateTimeFormatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	private static final long MSECONDS_OF_ONE_DAY = 60 * 60 * 1000 * 24;

	private static final Calendar cal = Calendar.getInstance();

	private static final String dateFormatStr = "yyyy-MM-dd";
	
	private static final SimpleDateFormat monthDayformat = new SimpleDateFormat(
			"MM/dd");

	public static Date getCurrentDate() {
		return new Date();
	}

	public static String getDateString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static String getDateString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String getDateTimeString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static String getDateTimeString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}

	public static Date parseDate(String date, String format)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	public static Date parseDateTime(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.parse(date);
	}

	public static Date parseDateTime(String date, String format)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	// write by steven.shi 2004-10-18
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month - 1);
		cl.set(Calendar.DAY_OF_MONTH, 1);
		return cl.getTime();
	}

	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONTH, month - 1);
		cl.set(Calendar.DAY_OF_MONTH, cl
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cl.getTime();
	}

	/*
	 * write by steven.shi 2004-10-18 orgDate format:YYYY-MM-DD
	 */
	public static Date convertOrgDate(String orgDate) {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, Integer.parseInt(orgDate.substring(0, 4)));
		cl.set(Calendar.MONTH, Integer.parseInt(orgDate.substring(5, 7)) - 1);
		cl.set(Calendar.DAY_OF_MONTH, Integer
				.parseInt(orgDate.substring(8, 10)));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		return cl.getTime();
	}

	/*
	 * write by steven.shi 2004-10-18 orgDate format:YYYY-MM-DD
	 */
	public static Date convertRealTimeDate(String orgDate) {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, Integer.parseInt(orgDate.substring(0, 4)));
		cl.set(Calendar.MONTH, Integer.parseInt(orgDate.substring(5, 7)) - 1);
		cl.set(Calendar.DAY_OF_MONTH, Integer
				.parseInt(orgDate.substring(8, 10)));
		return cl.getTime();
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:YYYY-MM-DD
	 * YYYY:M:D YYYY/M/DD
	 */
	public static String getNextDate(String date) {

		Calendar cd = Calendar.getInstance();
		StringTokenizer token = new StringTokenizer(date, "-/ :");
		if (token.hasMoreTokens()) {
			cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.YEAR, 1970);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
		} else {
			cd.set(Calendar.MONTH, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MINUTE, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.SECOND, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MILLISECOND, 0);
		}

		long nextTime = cd.getTimeInMillis() + 24 * 60 * 60 * 1000;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(nextTime));
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:(YYYY-MM-DD)
	 * (YYYY:M:D HH:MM:SS) (YYYY/M/DD hh:MM)
	 */
	public static Date stringToDate(String date) {
		if (date == null)
			return null;

		Calendar cd = Calendar.getInstance();
		StringTokenizer token = new StringTokenizer(date, "-/ :");
		if (token.hasMoreTokens()) {
			cd.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.YEAR, 1970);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MONTH, Integer.parseInt(token.nextToken()) - 1);
		} else {
			cd.set(Calendar.MONTH, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.HOUR_OF_DAY, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MINUTE, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MINUTE, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.SECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.SECOND, 0);
		}
		if (token.hasMoreTokens()) {
			cd.set(Calendar.MILLISECOND, Integer.parseInt(token.nextToken()));
		} else {
			cd.set(Calendar.MILLISECOND, 0);
		}

		return cd.getTime();
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:(YYYY-MM-DD)
	 * (YYYY:M:D HH:MM:SS) (YYYY/M/DD hh:MM)
	 */
	public static String dateToString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date).trim();

	}

	public static String dateTimeToString(Date date) {
		if (date == null)
			return "";
		return dateTimeFormatter.format(date).trim();

	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:(YYYY-MM-DD)
	 * (YYYY:M:D HH:MM:SS) (YYYY/M/DD hh:MM)
	 */
	public static String dateToString(Date date, String format) {

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date).trim();
	}

	/*
	 * Get the Next Date Write by Jeffy pan 2004-10-21 Date Format:(YYYY-MM-DD)
	 * (YYYY:M:D HH:MM:SS) (YYYY/M/DD hh:MM)
	 */
	public static int getDays(String fromDate, String endDate) {

		long from = stringToDate(fromDate).getTime();
		long end = stringToDate(endDate).getTime();

		return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
	}

	public static int getDays(Date fromDate, Date endDate) {

		long from = fromDate.getTime();
		long end = endDate.getTime();

		return (int) ((end - from) / (24 * 60 * 60 * 1000)) + 1;
	}

	public static String getTakeTime(Date startDate, Date endDate) {
		int minute = 0;
		try {
			minute = (int) (endDate.getTime() - startDate.getTime())
					/ (1000 * 60);
			return String.valueOf(minute);
		} catch (Exception e) {
			return "";
		}

	}

	/*
	 * 获取月份的第一天 written by Sammy: 2004-10-26
	 */
	public static int getFirstDateOfMonth(int year, int month) {

		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONDAY, month - 1);
		return cl.getActualMinimum(Calendar.DAY_OF_MONTH);
	}

	/*
	 * 获取月份的最后一天 written by Sammy: 2004-10-26
	 */
	public static int getLastDateOfMont(int year, int month) {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.YEAR, year);
		cl.set(Calendar.MONDAY, month - 1);
		return cl.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date) {
		if (date == null)
			return null;
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
		return jd;
	}

	public static java.sql.Date convertObjToSQLDate(Object obj) {
		if (obj == null || "".equals(obj.toString().trim()))
			return null;

		Calendar cl = Calendar.getInstance();
		cl.setTime((java.util.Date) obj);
		java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
		return jd;
	}

	public static java.sql.Timestamp convertUtilDateToSQLDateWithTime(
			java.util.Date date) {
		if (date != null) {
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Date convertStringToSQLDate(String dateString) {
		return (convertUtilDateToSQLDate(stringToDate(dateString)));
	}

	public static java.sql.Date convertToSQLDateWithoutTime(java.util.Date date) {
		String dateString = dateFormatter.format(date);
		return convertStringToSQLDate(dateString);
	}

	/**
	 * get offset to the previous sunday from the specific date
	 * 
	 * @param from
	 *            the specific start date
	 * @param from
	 *            the specific end date
	 * @return offset to the previous sunday
	 * @throws ParseException
	 */
	public static List getAllSundays(Date from, Date to) {
		List sundayList = new ArrayList();
		int offset = getOffsetToNextSunday(from);
		Date firstSunday = addDate(from, offset);
		Date current = firstSunday;
		while (current.compareTo(to) <= 0) {
			sundayList.add(current);
			current = addDate(current, 7);
		}
		return sundayList;
	}

	/**
	 * get offset to the next sunday from the specific date
	 * 
	 * @param date
	 *            the specific date
	 * @return offset to the next sunday
	 * @throws ParseException
	 */
	public static int getOffsetToNextSunday(Date date) {
		if (getDayOfWeek2(date) == 1)
			return 0;
		return 8 - getDayOfWeek2(date);
	}

	/**
	 * get day index of a week for the specific date
	 * 
	 * @param date
	 *            the specific date
	 * @return day index of a week,Mon. is 1,Tues. is 2,Wed. is 3,Thurs. is
	 *         4,Fri. is 5,Sat. is 6,Sun. is 7
	 * @throws ParseException
	 */
	public static int getDayOfWeek(Date date) {
		if (date == null)
			return 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (result == 0)
			result = 7;
		return result;
	}

	/**
	 * get day index of a week for the specific date
	 * 
	 * @param date
	 *            the specific date
	 * @return day index of a week,Sun. is 1,Mon. is 2,Tues. is 3,Wed. is
	 *         4,Thurs. is 5,Fri. is 6,Sat. is 7
	 * @throws ParseException
	 */
	public static int getDayOfWeek2(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * add days to the specific date
	 * 
	 * @param SourceDate
	 *            the specific date
	 * @param days
	 *            day count to be added
	 * @return java.util.Date object after add days
	 * @throws ParseException
	 */
	public static Date addDate(Date sourceDate, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	
	

	/**
	 * add days to the specific date
	 * 
	 * @param SourceDate
	 *            the specific date
	 * @param days
	 *            day count to be added
	 * @return java.util.Date object after add days
	 * @throws ParseException
	 */
	public static Date addDate(String stringDate, int days) {
		Date sourceDate = stringToDate(stringDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * add MONTH 
	 * 日期后推多少月份
	 */
	public static Date addMonth(String stringDate , int months){
		Date sourceDate = stringToDate(stringDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
		
	};
	
	/**
	 * add MONTH 
	 * 日期后推多少月份
	 */
	public static Date addMonth(Date sourceDate , int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
		
	};
	
	/**
	 * @param stringDate
	 * @return
	 */
	public static String addOneDay(Date sourceDate) {

		Date newDate = addDate(sourceDate, 1);
		return dateToString(newDate);

	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException
	 */
	public static long subDate(Date from, Date to) throws ParseException {
		long value = Math.abs(to.getTime() - from.getTime());
		return value / MSECONDS_OF_ONE_DAY;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 * @throws ParseException
	 */
	public static long subDate(String from, String to) throws ParseException {
		return subDate(stringToDate(from), stringToDate(to));
	}

	/*
	 * 返回时间列表 (startDate, endDate, days[])
	 */
	public static List getStringDateList(String startDate, String endDate,
			int[] days) {

		List dateList = new ArrayList();

		int days2 = DateUtils.getDays(startDate, endDate);
		Date fromDate2 = DateUtils.stringToDate(startDate);
		Date endDate2 = DateUtils.stringToDate(endDate);

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < days2; i++) {

			cal.setTime(fromDate2);
			cal.add(Calendar.DATE, i);

			for (int j = 0; j < days.length; j++) {
				// 星期数等于所选的
				if (days[j] == cal.get(Calendar.DAY_OF_WEEK)) {

					dateList.add(DateUtils.dateToString(cal.getTime()));
				}// if
			}// for

		}// for

		return dateList;
	}

	public static List getDateList(String startDate, String endDate, int[] days) {

		List dateList = new ArrayList();

		int days2 = DateUtils.getDays(startDate, endDate);
		Date fromDate2 = DateUtils.stringToDate(startDate);
		Date endDate2 = DateUtils.stringToDate(endDate);

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < days2; i++) {

			cal.setTime(fromDate2);
			cal.add(Calendar.DATE, i);

			for (int j = 0; j < days.length; j++) {
				// 星期数等于所选的
				if (days[j] == cal.get(Calendar.DAY_OF_WEEK)) {
					dateList.add(cal.getTime());
				}// if
			}// for

		}// for

		return dateList;
	}

	public static int compareDate(Date firstDate, Date secondDate) {
		return firstDate.compareTo(secondDate);
	}

	/**
	 * 当前时间
	 * 
	 * @return
	 */
	public final static Date currentTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 日期转换字符串
	 * 
	 * @param date
	 * @return
	 */
	public final static String getYYYYMMDD(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 * 
	 * @param date
	 * @return
	 */
	public final static String getYYYYMMDDHHMMSS(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		return simpledateformat.format(date);
	}

	/**
	 * 时间转换字符串
	 * 
	 * @param date
	 * @return YYYY-MM-DD
	 */
	public final static String getYYYY_MM_DD(Date date) {
		if (date != null) {
			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					dateFormatStr);
			return simpledateformat.format(date);
		} else
			return null;
	}

	/**
	 * 字符串转换时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static final Date controlTime(String dateString) {
		String s1 = dateString.substring(0, 4);
		String s2 = "";
		String s3 = "";
		String s4 = dateString.substring(5);
		int i = s4.indexOf("-");
		if (i == 1) {
			s2 = s4.substring(0, 1);
			s3 = s4.substring(2);
		} else {
			s2 = s4.substring(0, 2);
			s3 = s4.substring(3, 5);
		}
		cal.set((new Integer(s1)).intValue(), (new Integer(s2)).intValue() - 1,
				(new Integer(s3)).intValue());

		Date date = cal.getTime();
		return date;
	}

	/**
	 * 取年份
	 * 
	 * @param date
	 * @return
	 */
	public static final String getYear(Date date) {
		cal.setTime(date);
		return String.valueOf(cal.get(1));
	}

	/**
	 * 取月份
	 * 
	 * @param date
	 * @return
	 */
	public static final String getMonth(Date date) {
		String s = "";
		cal.setTime(date);
		if (cal.get(2) < 9)
			s = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1);
	}

	/**
	 * 取日
	 * 
	 * @param date
	 * @return
	 */
	public static final String getDay(Date date) {
		String s = "";
		String s1 = "";
		cal.setTime(date);
		if (cal.get(2) < 9)
			s = "0";
		if (cal.get(5) < 10)
			s1 = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(2) + 1)
				+ s1 + String.valueOf(cal.get(5));
	}

	/**
	 * 取星期X
	 * 
	 * @param date
	 * @return
	 */
	public static final String getWeek(Date date) {
		String s = "";
		cal.setTime(date);
		if (cal.get(3) < 10)
			s = "0";
		return String.valueOf(cal.get(1)) + s + String.valueOf(cal.get(3));
	}

	/**
	 * 取季节
	 * 
	 * @param date
	 * @return
	 */
	public static final String getSeason(Date date) {
		cal.setTime(date);
		int i = cal.get(2);
		byte byte0 = 1;
		if (i >= 3 && i <= 5)
			byte0 = 2;
		if (i >= 6 && i <= 8)
			byte0 = 3;
		if (i >= 9 && i <= 11)
			byte0 = 4;
		return String.valueOf(cal.get(1)) + "0" + String.valueOf(byte0);
	}

	/**
	 * 取现在完整时间字符串
	 * 
	 * @return
	 */
	public static final String getNowFormatTimeString() {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 取根据指定时间格式取当前系统时间
	 * 
	 * @param strFormat
	 *            日期时间格式，比如“yyyy-MM-dd HH:mm:ss”
	 * @return
	 */
	public static final String getNowDateTime(String strFormat) {
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat(strFormat);
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 转换完整的时间字符串
	 * 
	 * @param date
	 * @return
	 */
	public static final String getFormatTimeString(Date date) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}

	public static final String getFormatString(Date date, String formatStr) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr);
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final List getAllDays(String beginStr, String endStr) {
		Date begin = getDate(beginStr);
		Date end = getDate(endStr);
		return getAllDays(begin, end);
	}

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final List getAllDays(Date begin, Date end) {
// Modified By FJP 2007.6.10		
//		Date curDate = null;
//		List list = new ArrayList();
//		if (begin != null && end != null) {
//			long count = (end.getTime() - begin.getTime())
//					/ (1000 * 60 * 60 * 24);
//			for (int i = 0; i <= count; i++) {
//				curDate = new Date(begin.getTime() + (1000 * 60 * 60 * 24) * i);
//				list.add(getYYYY_MM_DD(curDate));
//			}
//		}
		if (begin == null || end == null || begin.getTime() > end.getTime())
			return null;
		
		List list = new ArrayList();
		if (begin != null && end != null) {
			Calendar calBegin = Calendar.getInstance();
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(end);
						
			long count = (end.getTime() - begin.getTime())
					/ (1000 * 60 * 60 * 24);
			for (int i = 0; i <= count; i++) {
				calBegin.setTime(begin);
				calBegin.add(Calendar.DAY_OF_YEAR, i);
				Date curDate = calBegin.getTime();
				list.add(getYYYY_MM_DD(curDate));
			}
		}
		return list;
	}
/**
 * 
 * @param dateStr YYYY-MM-DD
 * @return
 */
	public static Date getDate(String dateStr) {
		try {
			return new SimpleDateFormat(dateFormatStr).parse(dateStr);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	public static String changeYYYYMMDDToYYYY_MM_DD(String day) {
		SimpleDateFormat YYYYMMDDFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat YYYY_MM_DDFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return YYYY_MM_DDFormat.format(YYYYMMDDFormat.parse(day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String changeYYYY_MM_DDToYYYYMMDD(String day) {
		SimpleDateFormat YYYY_MM_DDFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat YYYYMMDDFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			return YYYYMMDDFormat.format(YYYY_MM_DDFormat.parse(day));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 转字符串为SQL Date
	 * 
	 * @param day
	 *            yyyymmdd
	 * @return
	 */
	public static java.sql.Date convertStringtoSqldate(String day) {
		String d = changeYYYYMMDDToYYYY_MM_DD(day);
		try {
			Date date = parseDate(d);
			return convertToSQLDateWithoutTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * 按照指定的日期格式返回给定日期时间串的相应格式。
	 * 
	 * @param myDate
	 * @param myDate
	 *            Date 如果该参数为空则取当前日历；
	 * @param strFormat
	 * @param strFormat
	 *            String 如果该参数不合法，则返回格式为“yyyy-MM-dd”的日期字符串； 日期格式意义如下： Letter
	 *            Date or Time Component Presentation Examples G Era designator
	 *            Text AD y Year Year 1996; 96 M Month in year Month July; Jul;
	 *            07 w Week in year Number 27 W Week in month Number 2 D Day in
	 *            year Number 189 d Day in month Number 10 F Day of week in
	 *            month Number 2 E Day in week Text Tuesday; Tue a Am/pm marker
	 *            Text PM H Hour in day (0-23) Number 0 k Hour in day (1-24)
	 *            Number 24 K Hour in am/pm (0-11) Number 0 h Hour in am/pm
	 *            (1-12) Number 12 m Minute in hour Number 30 s Second in minute
	 *            Number 55 S Millisecond Number 978 z Time zone General time
	 *            zone Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822
	 *            time zone -0800
	 * 
	 * @return String
	 */
	public static String getFormatDate(Date myDate, String strFormat)
			throws Exception {
		SimpleDateFormat dateFormatter = null;
		String strDateTime = null;

		if (strFormat == null || strFormat.equals(""))
			dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else
			dateFormatter = new SimpleDateFormat(strFormat);

		strDateTime = dateFormatter.format(myDate);

		return strDateTime;
	}
	/**
	 * 得到指定日期前后n天的日期
	 * @param oriDate
	 * @param n 后n天的参数应该是正数，前n天的参数应该是负数
	 * @return
	 */
	public static Date getNDayFromCur(Date oriDate,int n){
		Date date = new Date(oriDate.getTime()+24*60*60*1000*n);
		return date;
	}
	
	/**
	 * Iata项目短日期时间yy-MM-dd HH:mm
	 * @param myDate
	 * @return
	 * @throws Exception
	 */
	public static String getIataTime(Date myDate)
	throws Exception {
		return getFormatDate(myDate,"yy-MM-dd HH:mm");
	}
	/**
	 * ata项目报告用日期yy-MM-dd HH:mm
	 * @param myDate
	 * @return
	 * @throws Exception
	 */
	public static String getIataDate(Date myDate)
	throws Exception {
		SimpleDateFormat dateFormatter = null;
		String strDateTime = null;
			dateFormatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		strDateTime = dateFormatter.format(myDate);
		return strDateTime;
		
	}


	/**
	 * YYMonDD to YYmmdd
	 * @param dayStr
	 * @return
	 */
	public static String getYYMMDDbyYYMonDD(String dayStr) {
		String MM = dayStr.substring(2,5);
		if(MM.equals("JAN")){
			MM = "01";
		}else if(MM.equals("FEB")){
			MM = "02";
		}else if(MM.equals("MAR")){
			MM = "03";
		}else if(MM.equals("APR")){
			MM = "04";
		}else if(MM.equals("MAY")){
			MM = "05";
		}else if(MM.equals("JUN")){
			MM = "06";
		}else if(MM.equals("JUL")){
			MM = "07";
		}else if(MM.equals("AUG")){
			MM = "08";
		}else if(MM.equals("SEP")){
			MM = "09";
		}else if(MM.equals("OCT")){
			MM = "10";
		}else if(MM.equals("NOV")){
			MM = "11";
		}else if(MM.equals("DEC")){
			MM = "12";
		}
		return dayStr.substring(0,2)+MM+dayStr.substring(5,7);
	}
	
	/**
	 * yanglei 按日期得到星期几的英文简写
	 * 如：周一为Mon，周二为Tues
	 * @param date
	 * @return
	 */
	public static String getWekByDate(String dateStr){
		return getWekByDate(getDate(dateStr));
	}
	public static String getWekByDate(Date date){
		int week_no = getDayOfWeek(date);
		String wek = null;
		if(week_no==1){
			wek = "Mon";
		}else if(week_no==2){
			wek = "Tues";
		}else if(week_no==3){
			wek = "Wed";
		}else if(week_no==4){
			wek = "Thurs";
		}else if(week_no==5){
			wek = "Fri";
		}else if(week_no==6){
			wek = "Sat";
		}else if(week_no==7){
			wek = "Sun";
		}else {
			wek = "";
		}
		return wek;
	}
	public static String getWekByDateChs(Date date){
		int week_no = getDayOfWeek(date);
		String wek = null;
		if(week_no==1){
			wek = "周一";
		}else if(week_no==2){
			wek = "周二";
		}else if(week_no==3){
			wek = "周三";
		}else if(week_no==4){
			wek = "周四";
		}else if(week_no==5){
			wek = "周五";
		}else if(week_no==6){
			wek = "周六";
		}else if(week_no==7){
			wek = "周日";
		}else {
			wek = "";
		}
		return wek;
	}
	
	private static String[] tiangan = {"癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬"};
	private static String[] dizhi = {"亥", "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌"};
	
	public static String getLunarYear(int sunYear)
	{
		int tianganIndex = (sunYear - 3) % 10;
		int dizhiIndex = (sunYear - 3) % 12;
		
		return tiangan[tianganIndex] + dizhi[dizhiIndex];
	}
	public static String getMonthDay(Date date){
		return monthDayformat.format(date);
	}
	public static void main(String[] args) throws Exception {
		// String str = parseDateTime("2007-4-10 12:30:01","yyyy-MM-dd
		// HH:mm:ss");
		//System.out.println(parseDate("20070605","YYYYMMDD"));
		System.out.println(System.currentTimeMillis()-10*60*1000);
		System.out.println(getWekByDate(new Date()));
		System.out.println(getMonthDay(new Date()));
		Date date = new Date();
		System.out.println(addDate(date, -1));
	}

}
