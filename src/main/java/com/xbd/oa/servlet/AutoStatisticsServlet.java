package com.xbd.oa.servlet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.LazyDynaMap;
import org.use.base.FSPBean;
import org.use.base.utils.BeanFactory;
import org.use.base.utils.database.DataAccessManager;
import org.use.base.utils.database.PoolMySqlManager;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.business.impl.StManagerImpl;
import com.xbd.oa.dao.impl.StDaoImpl;
import com.xbd.oa.utils.BizUtil;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.vo.TimeRateNode;
import com.xbd.oa.vo.TimeRateStaff;
import com.xbd.oa.vo.TimeRateWeek;

/**
 * Servlet implementation class AutoStatisticsServlet
 */
@WebServlet("/AutoStatisticsServlet")
public class AutoStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static BaseManager stMgr = getStMgr();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static DataAccessManager dm;

	public AutoStatisticsServlet(){
		statisticsEveryDayTask();
	}
	
	public static BaseManager getStMgr() {
		if (stMgr == null) {
			stMgr = new StManagerImpl();
		}
		return stMgr;
	}

	public synchronized static DataAccessManager getDm() {
		if (dm == null) {
			dm = (DataAccessManager) BeanFactory
					.getBean(PoolMySqlManager.class);
		}
		return dm;
	}

	
	/**
	 * 每日统计  (每月统计订单量）
	 */
	public static void statisticsEveryDayTask() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();
				
				
				String today = sdf.format(cal.getTime());
				String runDay = sdf.format(getFirstDay().getTime());
				System.out.println( today + " target time " + runDay);
				if( today.equals(runDay)){
					//如果为月份第一天 则执行
//					lastOrderTask();
				}
				copyDate();
				nodeTask();
				staffTask();
				orderTask();
			}
//ConstantUtil.NOTIFYSTAFF_TIMER
		},day2Run(), ConstantUtil.NOTIFYSTAFF_TIMER);
	}

	/**
	 * 设置每天的运行时间
	 */
	public static long day2Run() {
		Calendar cal = Calendar.getInstance();
		long time = 0L;
//		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 14);
		cal.set(Calendar.MINUTE, 50);
		cal.set(Calendar.SECOND, 0);
		
		long now = Calendar.getInstance().getTimeInMillis();//当前时间
		long target = cal.getTimeInMillis();//目标执行时间
		
		if( target - now < 0 ){
			cal.add(Calendar.DAY_OF_MONTH, 1);
			time = (cal.getTimeInMillis() - now);
		}else{
			time = (target - now );
		}
		
		return time;
	}

	/**
	 * 设置每周运行时间 每周一 2:00
	 */
	public static Date week2Run() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 2);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		Date date = new Date();
		date = cal.getTime();
		System.out.println("周一：" + date);
		if (date.before(new Date())) {
			Calendar cal2 = Calendar.getInstance();
			System.out.println("周二：" + cal2.get(Calendar.DAY_OF_WEEK));
			// int i = date.compareTo(cal2.getTime());// -1 小于 0 相等 1 大于
			// int diff = cal.get(Calendar.DAY_OF_WEEK) -
			// cal2.get(Calendar.DAY_OF_WEEK);
			// System.out.println("diff:" + diff);
			// if( diff < 0 ){
			// diff = 7 + diff;
			// System.out.println(diff);
			// }
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, 7);
			date = cal.getTime();
			System.out.println(date);
		}
		return date;
	}

	public static Date month2Run(){
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 1);
		System.out.println(cal.getTime());
		
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		System.out.println(cal.getTime());
		System.out.println( "max day " + cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date date = new Date();
		date = cal.getTime();
//		System.out.println(date);
		// 如果开始时间小于当前时间，则延后一天运行
		if (date.before(new Date())) {
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			date = cal.getTime();
//			System.out.println(date);
		}

		return date;
	}
	
	
	/**
	 * 订单统计 （每月）
	 */
	public static void orderTask(){
		int count = 0;//超时订单数
		int total = 0;//总订单数
		Calendar cal = getFirstDay();
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.STATISTICS_ORDER_TIMERATE_BY_SQL_1);//未完成订单
		List<LazyDynaMap> beans1 = stMgr.getObjectsBySql(fsp);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.STATISTICS_ORDER_TIMERATE_BY_SQL_2);//当月已完成订单
		fsp.set("time", sdf.format(cal.getTime()) );
		List<LazyDynaMap> beans2 = stMgr.getObjectsBySql(fsp);
		System.out.println( " order task total : " + beans1.size() + beans2.size());
		Timestamp now = new Timestamp(System.currentTimeMillis());//当前时间
		StringBuffer sb = new StringBuffer();
		for(LazyDynaMap bean : beans2){//完成订单
			//计算订单是否超时
			
			Timestamp planStart = (Timestamp)bean.get("wf_plan_start");//订单开始时间
			Timestamp finish = (Timestamp)bean.get("end_time");//订单结束时间
			long duration = (long)bean.get("wf_step_duration");//当前步骤所需时间
			Timestamp planFinish = BizUtil.culPlanDate(planStart, duration);
			if( planFinish.before(finish)){
				System.out.println("planFinish : " + planFinish +"finish : " + finish);
				count++;
				sb.append(bean.get("style_code")  + "\r\n");
			}else{
//				sb.append(bean.get("style_code") + "\r\n");
			}
		}
		sb.append("not complete");
		for(LazyDynaMap bean : beans1){//未完成订单
			//计算当前步骤是否超时
			Timestamp start = (Timestamp)bean.get("wf_real_start");//当前步骤开始时间
			long duration = (long)bean.get("wf_step_duration");//当前步骤所需时间
			long diff = duration - BizUtil.getWorkTimeBetween(now, start);
			if(diff < 0 ){
				count ++;//超时订单数+1
				sb.append(bean.get("style_code")  + "\r\n");
			}else{
//				sb.append(bean.get("style_code")  + "\r\n");
			}
		}
		System.out.println( "=====================================" +sb.toString());
		
		int month = cal.get(Calendar.MONTH);//第几周
		total = beans1.size() + beans2.size();
		float rate = (float) (1 - (float)count/(float)total);
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OA_ORDER_WEEK_TIMERATE_BY_EQL);
		fsp.set("weekNo", month);//更改为month
		TimeRateWeek trw = (TimeRateWeek)stMgr.getOnlyObjectByEql(fsp);
		if( trw  == null ){
			trw = new TimeRateWeek();
		}
		trw.setOrderTimeout(count);
		trw.setOrderTotal(total);
		trw.setWeekNo(month);
		trw.setTimeRate(rate*100);
		stMgr.saveObject(trw);
	}
	
	
	
	/**
	 * 节点统计
	 */
	public static void nodeTask() {
		FSPBean fsp = new FSPBean();
		Calendar cal = getFirstDay();
		for(int i=1;i<10;i++){ //第一步及时率永远是100%
			List<LazyDynaMap> list = null;
			fsp.set(FSPBean.FSP_QUERY_BY_XML,StDaoImpl.STATISTICS_NODE_TIMERATE_BY_SQL);
			fsp.set("start", sdf.format( cal.getTime()));// 本周开始时间
			fsp.set("step", getNodeCode(i));// 节点名称
			list = getStMgr().getObjectsBySql(fsp);
			int nodeTotal = list.size();// 总订单数
			int nodeTimeout = 0;// 超时订单数
			float rate = 0;
			
			for (LazyDynaMap bean : list) {
				long times = (long)bean.get("times");
				int oa_order = (int)bean.get("oa_order");
				long duration = (long) bean.get("wf_step_duration");// 流程所需时间
				long useTime = 0L;
				if( times > 1 ){
					//节点有退回
					fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OA_ORDER_DETAIL_NOW_BY_SQL);
					fsp.set("orderId", oa_order);
					fsp.set("step", getNodeCode(i));
					List<LazyDynaMap> stepList = stMgr.getObjectsBySql(fsp);
					for( LazyDynaMap stepDetail : stepList){
						Timestamp start = (Timestamp) stepDetail.get("wf_real_start");// 流程开始时间
						Timestamp finish = new Timestamp(System.currentTimeMillis());// 流程未结束则使用当前时间
						if (stepDetail.get("wf_real_finish") != null) {
							finish = (Timestamp) stepDetail.get("wf_real_finish");// 流程结束时间
						}
						useTime += BizUtil.getWorkTimeBetween(finish, start);
					}
				}else{
					Timestamp start = (Timestamp) bean.get("wf_real_start");// 流程开始时间
					Timestamp finish = new Timestamp(System.currentTimeMillis());// 流程未结束则使用当前时间
					if (bean.get("wf_real_finish") != null) {
						finish = (Timestamp) bean.get("wf_real_finish");// 流程结束时间
					}
					useTime = BizUtil.getWorkTimeBetween(finish, start);
				}
				// 判断是否超时
				long diff = duration - useTime;
				System.out.println(diff);
				if (diff < 0) {
					nodeTimeout++;
				}
			}
			if(nodeTotal == 0){
				rate = 0;
			}else{
				rate = (float) (1 - (float)nodeTimeout/(float)nodeTotal);
			}
			
			System.out.println("total:" + nodeTotal + " timeoutStep:" + nodeTimeout + " rate: " + rate);
			TimeRateNode trn = new TimeRateNode();
			trn.setNode(i);
			trn.setNodeTimeout(nodeTimeout);
			trn.setNodeTotal(nodeTotal);
			trn.setTimeRate(rate*100);
			trn.setDayWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1);
			trn.setWeekNo(cal.get(Calendar.MONTH));//相关月份
			trn.setNodeName(getNodeName(i));
			stMgr.saveObject(trn);
		}
	}

	public static void staffTask(){
		FSPBean fsp = new FSPBean();
		List<LazyDynaMap> beans = null;
		Calendar cal = getFirstDay();
		int month = cal.get(Calendar.MONTH);//第几周
		String begin = sdf.format(cal.getTime());//一周开始时间
		System.out.println(cal);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OPERATOR_BY_SQL);
		fsp.set("start", begin);
		beans = getStMgr().getObjectsBySql(fsp);
		Timestamp finish = new Timestamp(System.currentTimeMillis());// 流程未结束则使用当前时间
		for(LazyDynaMap operator : beans ){
			int total = 0;
			int timeout = 0;
			float rate = 0;
			String name = operator.get("operator").toString();
			System.out.println(name);
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.STATISTICS_STAFF_TIMERATE_BY_SQL);
			fsp.set("start",begin);
			fsp.set("operator", name);
//			fsp.set("operator", "郝大通");
			System.out.println( name );
			beans = getStMgr().getObjectsBySql(fsp);
			total = beans.size();
			for(LazyDynaMap bean : beans){
				long times = (long)bean.get("times");
				int oa_order = (int)bean.get("oa_order");
				long useTime = 0L; //所需时间
				long duration = 0L;//用时
				if( times > 1 ){
					//员工流程中参与多个步骤  计算多个步骤的累加计划完成时间
					fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OA_ORDER_DETAIL_STEP_DURATION_BY_EQL);
					fsp.set("orderId", oa_order);
					fsp.set("operator", name);
					List<LazyDynaMap> durationList = stMgr.getObjectsBySql(fsp);
					for(LazyDynaMap d : durationList){
						duration += (long)d.get("wf_step_duration");
					}
					
					//步骤有回退  累计每个步骤所用时间
					fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OA_ORDER_DETAIL_NOW_BY_SQL);
					fsp.set("orderId", oa_order);
					fsp.set("operator", name);
					List<LazyDynaMap> stepList = stMgr.getObjectsBySql(fsp);
					for( LazyDynaMap step : stepList ){
						Timestamp start = (Timestamp)step.get("wf_real_start");//节点开始时间
						System.out.println(start);
						if (step.get("wf_real_finish") != null) {
							finish = (Timestamp) step.get("wf_real_finish");// 流程结束时间
						}
						useTime += BizUtil.getWorkTimeBetween(finish, start);
					}
				}else{
					//步骤无回退
					Timestamp start = (Timestamp)bean.get("wf_real_start");//节点开始时间
					duration = (long)bean.get("wf_step_duration");
					System.out.println(start);
					if (bean.get("wf_real_finish") != null) {
						finish = (Timestamp) bean.get("wf_real_finish");// 流程结束时间
					}
					useTime = BizUtil.getWorkTimeBetween(finish, start);
				}
//				long duration = (long)bean.get("wf_step_duration");//节点所需时间
				
				long diff = duration - useTime;
				if( diff < 0 ){
					timeout++;
				}
			}
			if( total != 0 ){
				rate = (float) (1 - (float)timeout/(float)total);
			}
			
			TimeRateStaff trs = new TimeRateStaff();
			trs.setNodeTimeout(timeout);
			trs.setNodeTotal(total);
			trs.setOperator(name);
			trs.setOrg(departmentName(operator.get("oa_org").toString()));
			trs.setTimeRate(rate*100);
			trs.setWeekNo(month);
			stMgr.saveObject(trs);
			
		}
	}
	

	public static String getNodeCode(int index){
		switch(index){
		case 1: return "c_create_dahuo_1";
		case 2: return "c_fi_confirm_2";
		case 3: return "c_ppc_assign_3";
		case 4: return "c_fi_pay_4";
		case 5: return "c_ppc_factoryMsg_5";
		case 6: return "c_qc_cutting_6";
		case 7: return "c_ppc_confirm_7";
		case 8: return "c_qc_printing_8";
		case 9: return "c_ppc_confirm_9";
		default : return "no";
		}
	}
	
	public static String getNodeName(int index){
		switch(index){
		case 1: return "新建大货订单";
		case 2: return "确认定金到帐";
		case 3: return "制作纸样、生产工艺制造单";
		case 4: return "采购面料";
		case 5: return "验布、裁剪";
		case 6: return "车缝";
		case 7: return "尾查";
		case 8: return "确认尾款到账";
		case 9: return "发货";
		default : return "no";
		}
	}
	
	private static String departmentName(String org){
		switch (org) {
			case "4" : return "PPC";
			case "5" : return "MR";
			case "6" : return "PUR";
			case "7" : return "QA";
			case "8" : return "IT";
			case "9" : return "QC";
			case "11" : return "FI";
			case "12" : return "CP";
			case "14" : return "CQC";
			default : return "Unknown";
		}
	}
	
	/**
	 * 获取当月第一天
	 * @return 当月第一天
	 */
	public static Calendar getFirstDay(){
		Calendar cal = Calendar.getInstance();
		//calender 函数中月份从0开始，所以要 +1
		cal.set(Calendar.DAY_OF_MONTH,1);
		return cal;
	}
	
	/**
	 * 获取月第一天
	 * @param 与本月相隔的月份 0 本月  1上月 2上上月
	 * @return 月份第一天
	 */
	public static Calendar getFirstDay(int interval){
		Calendar cal = Calendar.getInstance();
		//calender 函数中月份从0开始，所以要 -1
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.MONTH, month - interval );//
		cal.set(Calendar.DAY_OF_MONTH,1);
		return cal;
	}
	
	public static long getRunTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 整理数据
	 */
	public static void copyDate(){
		System.out.println("start copy ...");
//		String delOrder = " truncate table time_rate_oo ";//清空 临时order 表
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		//删除本月部分数据
		String delNode = " delete from time_rate_node where week_no = " + month;//清除本月信息
		String delStaff = " delete from time_rate_staff where week_no = " + month;//清除本月信息
		//清空表
		String delOrder = "  truncate table time_rate_oo";//清除本月信息
		String delOrderDetail = " truncate table time_rate_ood ";
		//复制数据
		String ooSql = "insert into time_rate_oo select * from oa_order where type='3' and status = '0'";//复制oa_order表中的大货订单
		String oodSql = " insert into time_rate_ood (select ood.* from oa_order_detail ood,time_rate_oo oo where oo.id = ood.oa_order) ";//复制oa_order_detail表中大货订单
		try{
			dm = getDm();
			dm.excuteStmt(delOrder);
			dm.excuteStmt(delOrderDetail);
			dm.excuteStmt(delNode);
			dm.excuteStmt(delStaff);
			dm.excuteStmt(ooSql);
			dm.excuteStmt(oodSql);
		}catch(Exception e){
			System.out.println("大货订单复制失败！！！ copy failed ====" + System.currentTimeMillis() );
			e.printStackTrace();
		}
		System.out.println(" copy complete !");
	}
	
	
	
	public static void main(String args[]) {
//		week2Run();
//		month2Run();
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = getFirstDay(0);
		Calendar cal3 = getFirstDay(1);
		Calendar cal4 = getFirstDay(2);
//		for( int i =31;i>0;i--){
//			cal.set(Calendar.DAY_OF_MONTH, i);
////			cal2.set(Calendar.MONTH, i);
//
//			String today = sdf.format(cal.getTime());
//			String runDay = sdf.format(cal2.getTime());
//			if( today.equals(runDay)){
//				System.out.println( "it`s run time" );
//			}
//			System.out.println( today + " target time " + runDay);
//		}
		System.out.println(cal.getTime());
		System.out.println( cal2.getTime() );
		System.out.println( cal3.getTime() );
		System.out.println( cal4.getTime() );
	}
}
