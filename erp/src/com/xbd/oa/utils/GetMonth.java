package com.xbd.oa.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xbd.oa.servlet.AutoStatisticsServlet;

/**
 * 获取相应的日期
 */
public class GetMonth {
	private static Calendar cal = Calendar.getInstance();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 本月的第一天
	 */
	public static String getNowMonth(){
		cal.set(cal.DAY_OF_MONTH, 1);
		String firstnowMonth = dateFormat.format(cal.getTime());
		System.out.println("本月第一天" + firstnowMonth);
		return firstnowMonth;
	}
	/**
	 * 上月的第一天
	 */
	public static String getFirstMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONDAY, -1);
		cal.set(cal.DAY_OF_MONTH, 1);
		String firstMonth = dateFormat.format(cal.getTime());
		System.out.println("上月第一天" + firstMonth);
		return firstMonth;
	}
	/**
	 * 上月的最后一天
	 */
	public static String getLastMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH);
		cal.set(cal.MONTH, month - 1);
		cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
		String lastMonth = dateFormat.format(cal.getTime());
		System.out.println("上月的最后一天" + lastMonth);
		return lastMonth;
	}
	/**
	 * 上上月的第一天
	 */
	public static String getSecondMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.MONDAY, -2);
		cal.set(cal.DAY_OF_MONTH, 1);
		String secondMonth = dateFormat.format(cal.getTime());
		System.out.println("上上月的第一天" + secondMonth);
		return secondMonth;
	}
	/**
	 * 上上月的最后一天
	 */
	public static String getLastSecondMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH);
		cal.set(cal.MONTH, month - 2);
		cal.set(cal.DAY_OF_MONTH, cal.getActualMaximum(cal.DAY_OF_MONTH));
		String lastSecondMonth = dateFormat.format(cal.getTime());
		System.out.println("上上月最一天" + lastSecondMonth);
		return lastSecondMonth;
	}
	/**
	 * 获取本月的月份
	 */
	public static int getSingleMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH) +1;
		System.out.println(month);
		return month;
	}
	/**
	 * 获取上月的月份
	 */
	public static int getOneMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH);
		System.out.println(month);
		return month;
	}
	/**
	 * 获取上上月的月份
	 */
	public static int getTwoMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(cal.MONTH)-1;
		System.out.println(month);
		return month;
	}
	/**
	 * 根据flag返回单个相应要查询月份的
	 */
	public static int getFindMonth(String flag){
		//判断页面传递数据
		if("1".equals(flag)){
			 int getmonth = GetMonth.getOneMonth();
			 return getmonth;
		}else if("2".equals(flag)){
			 int getmonth = GetMonth.getTwoMonth();
			 return getmonth;
		}else{
			 int getmonth = GetMonth.getSingleMonth();
			 return getmonth;
		}
	}
	/**
	 * 获得yyyy-MM-dd这样的月份
	 */
	public static Map getFullMonth(String flag) {
		Map<String, Object> timeMap = new HashMap<String, Object>();
		if ("1".equals(flag)) {
			timeMap.put("firstMonth", GetMonth.getFirstMonth());
			timeMap.put("lastMonth", GetMonth.getLastMonth());
			return timeMap;
		} else if ("2".equals(flag)) {
			timeMap.put("secondMonth", GetMonth.getSecondMonth());
			timeMap.put("lastSecondMonth", GetMonth.getLastSecondMonth());
			return timeMap;
		} else {
			timeMap.put("nowMonth",GetMonth.getNowMonth());
			return timeMap;
		}
		
	}
	public static List<Object> getListMonth(String flag) {
		List<Object> timeList = new ArrayList<Object>();
		if ("1".equals(flag)) {
			timeList.add(GetMonth.getFirstMonth());
			timeList.add(GetMonth.getLastMonth());
			return timeList;
		} else if ("2".equals(flag)) {
			timeList.add(GetMonth.getSecondMonth());
			timeList.add(GetMonth.getLastSecondMonth());
			return timeList;
		} else {
			timeList.add(GetMonth.getNowMonth());
			return timeList;
		}
		
	}
	
}
