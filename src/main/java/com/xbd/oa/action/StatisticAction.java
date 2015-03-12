//package com.xbd.oa.action;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import opensource.jpinyin.PinyinHelper;
//
//import org.apache.commons.beanutils.LazyDynaMap;
//import org.apache.commons.lang3.StringUtils;
//import org.use.base.FSPBean;
//import org.use.base.action.Action;
//import org.use.base.annotation.EJB;
//import org.use.base.manager.Manager;
//
//import com.xbd.oa.business.BaseManager;
//import com.xbd.oa.dao.impl.StDaoImpl;
//import com.xbd.oa.servlet.AutoStatisticsServlet;
//import com.xbd.oa.utils.BizUtil;
//import com.xbd.oa.utils.ConstantUtil;
//import com.xbd.oa.utils.GetMonth;
//import com.xbd.oa.utils.Struts2Utils;
//import com.xbd.oa.utils.WebUtil;
//
//public class StatisticAction extends Action {
//	private List<LazyDynaMap> beans = null;
//	private LazyDynaMap bean = null;
//	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//	private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//
//	@EJB(name = "com.xbd.oa.business.impl.StManagerImpl")
//	private BaseManager manager;
//	
//	Map map = new HashMap();
//	@Override
//	public Manager getBiz() {
//		// TODO Auto-generated method stub
//		return manager;
//	}
//	/**
//	 * 登录接口
//	 */
//	public void login(){
//		String uname = Struts2Utils.getParameter("loginName");
//		String passwd = Struts2Utils.getParameter("passwd");
//	
//		if( validateUser(uname,passwd) ){
//			//登录成功
//			map.put("code", "0");
//			map.put("msg", "success");
//		}else{
//			//登录失败
//			map.put("code", "1");
//			map.put("msg", "failed");
//		}
//		Struts2Utils.renderJson(map);
//	}
//	/**
//	 * 首页数据
//	 */
//	public void index(){
//		//获取节点统计数据  每日更新
//		//根据页面传递的参数,获取相应的月份,1 代表上月份,2 代码上上月份,不穿代表本月
//		String flag = Struts2Utils.getParameter("flag");
//		List ntrList = new ArrayList();
//			fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_NODE_TIMERATE_THIS_WEEK_BY_SQL);
//			fsp.set(FSPBean.FSP_ORDER, " order by node ");
//
//			fsp.set("weekNo",GetMonth.getFindMonth(flag));
////			fsp.set("dayWeek", cal.get(Calendar.DAY_OF_WEEK) - 1 );//获取昨天的数据
//			beans = manager.getObjectsBySql(fsp);
//  			for(LazyDynaMap bean:beans){
// 				Map map = new HashMap();
//				map.put("name", bean.get("node_name"));
// 				map.put("nodeCode", bean.get("node"));
//				System.out.println(bean.get("node_name")+","+bean.get("time_rate").toString());
//   				Float rate = (Float)bean.get("time_rate");
//				map.put("time_rate", rate);
// 				map.put("value", rate.intValue());
// 				ntrList.add(map);
//			}
//		List topStrList = new ArrayList();//员工前三名统计
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_STAFF_TIMERATE_BY_SQL);
////		fsp.set(FSPBean.FSP_ORDER, " order by time_rate desc");
//		fsp.set(FSPBean.FSP_ORDER,"order by time_rate desc,node_total desc");
//		
//		fsp.set("weekNo",GetMonth.getFindMonth(flag));
// 		List<LazyDynaMap> list = manager.getObjectsBySql(fsp);
// 		if( list != null && list.size()>0 ){
//			for(int i=0;i<3;i++){
//   				Map temp = new HashMap();
//    				temp.put("name",list.get(i).get("operator"));
//				temp.put("total", list.get(i).get("node_total"));
//				temp.put("department",list.get(i).get("org"));
// 				Float rate = (Float)list.get(i).get("time_rate");
//				temp.put("time_rate", rate);
//  				System.out.println(list.get(i).get("operator").toString()
//  						+","+list.get(i).get("week_no").toString()
//   						+","+list.get(i).get("time_rate").toString());
//				temp.put("value",rate.intValue());
// 				topStrList.add(temp);
//			}
//		}
//		//员工统计  每日更新
//		List lowStrList = new ArrayList();//员工后三名统计
//		if( list != null && list.size()>0 ){
//		for(int i=list.size()-3;i<list.size();i++){
//			Map temp = new HashMap();
//			temp.put("name",list.get(i).get("operator"));
// 			temp.put("total", list.get(i).get("node_total"));
//			temp.put("department",list.get(i).get("org"));
//			System.out.println(list.get(i).get("operator").toString()
//					+","+list.get(i).get("node_total").toString()
//					+","+list.get(i).get("org").toString());
//			Float rate = (Float)list.get(i).get("time_rate");
//			temp.put("time_rate", rate);
// 			temp.put("value",rate.intValue());
//			lowStrList.add(temp);
//		 }
//		}
//		map.put("ntrList", ntrList);//节点及时率 列表
//		map.put("topStrList", topStrList);//前三员工及时率 列表
// 		map.put("lowStrList",lowStrList);//倒数3个low
//		map.put("toOrder", getOrderTimeout());//已超时订单数 timeout order
//		map.put("thisWeek", 0);//周一是传输0
//		map.put("lastWeek",0);
//		map.put("longWeek",0);
//		
//		//获取每月统计数据  每月更新数据
//		fsp = new FSPBean();
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_OA_ORDER_WEEK_TIMERATE_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER, "order by id desc ");
//		
////		fsp.set("weekNo",GetMonth.getFindMonth(flag));
//		beans = manager.getObjectsBySql(fsp);
//		flag = StringUtils.isBlank(flag)? "0" : flag;
//		for(int i = 0;i<beans.size() && i<3;i++){
//			Float f = (Float)beans.get(i).get("time_rate");
//			map.put("time_rate", f);
//			if(i == 0){
//				
//				map.put("thisWeek",f.intValue());
//			}
//			if(i == 1 ){
//				map.put("lastWeek",f.intValue());
//			}
//			if(i == 2 ){
//				map.put("longWeek",f.intValue());
//			}
//			map.put("orderTotal", beans.get(Integer.valueOf(flag)).get("order_total"));
//			System.out.println( "flag == " + flag);
//			System.out.println(beans.get(i).get("week_no")+","+beans.get(i).get("time_rate"));
//			
//		}
//		Struts2Utils.renderJson(map);
//	}
//	
//	/**
//	 * 员工及时率 更多
//	 * staff time rate
//	 */
//	public void STRStatistics(){
//		String flag = Struts2Utils.getParameter("flag");
//		List topStrList = new ArrayList();
//		List otherStrList = new ArrayList();
//		
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_STAFF_TIMERATE_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER, " order by time_rate desc,node_total desc ");
//		//判断页面传递数据
//		fsp.set("weekNo",GetMonth.getFindMonth(flag));
//		List<LazyDynaMap> list = manager.getObjectsBySql(fsp);
//		int i = 0;
//		for(LazyDynaMap bean : list ){
//			Map temp = new HashMap();
//			if(i < 3){
//				temp.put("name",bean.get("operator"));
//				temp.put("total", bean.get("node_total"));
//				temp.put("department",bean.get("org"));
//				Float rate = (Float)bean.get("time_rate");
//				System.out.println(bean.get("operator").toString()
//						+","+bean.get("week_no").toString()
//						+","+bean.get("org").toString()
//						+","+bean.get("time_rate").toString());
//				temp.put("value",rate.intValue());
//				topStrList.add(temp);
//			}else{
//				temp.put("name",bean.get("operator"));
//				temp.put("total", bean.get("node_total"));
//				temp.put("department",bean.get("org"));
//				temp.put("value",bean.get("time_rate"));
//				System.out.println(bean.get("operator").toString()
//						+","+bean.get("week_no").toString()
//						+","+bean.get("org").toString()
//						+","+bean.get("time_rate").toString());
//				otherStrList.add(temp);
//			}
//			i++;
//		}
//		map.put("topStrList",topStrList);
//		map.put("otherStrList",otherStrList);
//		Struts2Utils.renderJson(map);
//	}
//	/**
//	 * 本周 某一节点及时率详情
//	 * 节点固定
//	 */
//	public void nodeTRDetail(){
//		String flag = Struts2Utils.getParameter("flag");
//		//nodeCode
//		String code = Struts2Utils.getParameter("nodeCode");
//		String type = Struts2Utils.getParameter("type"); //1 all 2.on 3,timeout
//		Calendar cal =  AutoStatisticsServlet.getFirstDay();
//		List nodeList = new ArrayList();
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_NODE_TIMERATE_DETAIL_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER, " order by ood.wf_real_start desc ");
////		fsp.set("nodeCode",AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)));
////		fsp.set("start", sdf1.format(cal.getTime()));
//		// 根据参数判断要查询的相应的月份 1上月 2 上上月
//		if ("1".equals(flag)) {
//		fsp.set("nodeCode",AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)));
//		fsp.set("start_wf_real_finish", GetMonth.getFirstMonth());
//		fsp.set("end_wf_real_finish",GetMonth.getLastMonth());
//		} else if ("2".equals(flag)) {
//			fsp.set("nodeCode",AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)));
//			fsp.set("start_wf_real_finish", GetMonth.getSecondMonth());
//			fsp.set("end_wf_real_finish", GetMonth.getLastSecondMonth());
//		} else {
//			//GetMonth.getNowMonth()
//			//fsp.set("nodeCode",AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)));
//			fsp.set("nodeCode",AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)));
//			fsp.set("start", GetMonth.getNowMonth());
////			fsp.set("start_wf_real_finish", GetMonth.getNowMonth());
////			fsp.set("end_wf_real_finish",sdf1.format(cal.getTime()));
//		}
//			
//		
//		System.out.println(AutoStatisticsServlet.getNodeCode(Integer.valueOf(code)) + sdf1.format(cal.getTime()));
//		List<LazyDynaMap> list = manager.getObjectsBySql(fsp);
//		System.out.println(list.size());
//		for(LazyDynaMap bean : list){
//			Map map = new HashMap();
//			Timestamp start = (Timestamp)bean.get("wf_real_start");
//			Timestamp finish = bean.get("wf_real_finish") == null ? new Timestamp( AutoStatisticsServlet.getRunTime() ) : (Timestamp)bean.get("wf_real_finish");//完成时间
//			System.out.println( "=====" + finish);
//			long duration = (long)bean.get("wf_step_duration");
//			long between = BizUtil.getWorkTimeBetween(finish, start);
//			long diff = duration - between;
//			
//			map.put("orderId", bean.get("oa_order"));
//			map.put("startTime", sdf.format( (Timestamp)bean.get("wf_real_start")) );//开始时间
//			map.put("planTime", sdf.format( BizUtil.culPlanDate(start,duration)));//计划完成时间
//			map.put("nodeName", bean.get("wf_step_name"));
//			map.put("department",departmentName(bean.get("oa_org").toString()));
//			//TODO 订单偏差
//			map.put("orderDiffTime", WebUtil.minusTimeAndOffset((Timestamp)bean.get("wf_plan_start"),duration ));
//			map.put("operator", bean.get("operator"));
//			map.put("cusCode", bean.get("cus_code"));
//			map.put("styleCode", bean.get("style_code"));
//			map.put("nodeTakeTime",getTimeDisPlay(between));
//			map.put("nodeDuration", getTimeDisPlay(duration));
//			//TODO 节点偏差
//			if( "1".equals(type)){//全部订单
//				map.put("nodeDiffTime",( diff < 0 ?"超时":"剩余" )+ WebUtil.getTimeDisPlay(Math.abs(diff)));
//				nodeList.add(map);
//			}
//			if("2".equals(type) && diff >= 0 ){//未超时订单
//				map.put("nodeDiffTime","剩余" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				nodeList.add(map);
//			}
//			if("3".equals(type) && -diff <= ConstantUtil.STATISTICS_TIMEOUT_SERIOUS && -diff > 0  ){//一般超时
//				map.put("nodeDiffTime","超时" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				nodeList.add(map);
//			}
//			if("4".equals(type) && -diff > ConstantUtil.STATISTICS_TIMEOUT_SERIOUS ){//严重超时
//				map.put("nodeDiffTime","超时" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				nodeList.add(map);
//			}
//		}
//		
//		map.put("nodeList", nodeList);
//		Struts2Utils.renderJson(map);
//	}
//	
//	/**
//	 * 本周 员工及时率详情
//	 * 操作人固定，节点可能有多种
//	 */
//	public void staffTRDetail(){
//		String flag = Struts2Utils.getParameter("flag");
//		String name = Struts2Utils.getParameter("name");//员工名称
//		String type = Struts2Utils.getParameter("type");//进度
//		Calendar cal =  AutoStatisticsServlet.getFirstDay();
//		List strList = new ArrayList();
//
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.GET_NODE_TIMERATE_DETAIL_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER, " order by ood.wf_real_start desc ");
////		fsp.set("staff", name);
////		fsp.set("start", sdf1.format( cal.getTime()));
//		
//		// 根据参数判断要查询的相应的月份 1上月 2 上上月
//		if ("1".equals(flag)) {
//			fsp.set("staff", name);
//			fsp.set("start_wf_real_finish", GetMonth.getFirstMonth());
//			fsp.set("end_wf_real_finish", GetMonth.getLastMonth());
//		} else if ("2".equals(flag)) {
//			fsp.set("staff", name);
//			fsp.set("start_wf_real_finish", GetMonth.getSecondMonth());
//			fsp.set("end_wf_real_finish", GetMonth.getLastSecondMonth());
//		} else {
//			fsp.set("staff", name);
//			//fsp.set("start", sdf1.format( cal.getTime()));
//			fsp.set("start", GetMonth.getNowMonth());
//		}
//		
//		System.out.println( sdf1.format( cal.getTime()) );
//		List<LazyDynaMap> list = manager.getObjectsBySql(fsp);
//		
//		for(LazyDynaMap bean : list ){
//			Map map = new HashMap();
//			Timestamp start = (Timestamp)bean.get("wf_real_start");
//			Timestamp finish = bean.get("wf_real_finish") == null ? new Timestamp(AutoStatisticsServlet.getRunTime()) : (Timestamp)bean.get("wf_real_finish");//完成时间
//			long duration = (long)bean.get("wf_step_duration");
//			long between = BizUtil.getWorkTimeBetween(finish, start);
//			long diff = duration - between;
//			
//			map.put("orderId", bean.get("oa_order"));
//			map.put("staffId", bean.get("operator"));
//			map.put("startTime", sdf.format(start) );
//			map.put("planTime", sdf.format( BizUtil.culPlanDate(start,duration)));//计划完成时间
//			map.put("department", departmentName(bean.get("oa_org").toString()));
//			map.put("orderDiffTime", WebUtil.minusTimeAndOffset(start, duration));
//			map.put("nodeDuration", getTimeDisPlay(duration));
//			map.put("nodeTakeTime",getTimeDisPlay(between));
//			map.put("operator", name);
//			map.put("cusCode", bean.get("cus_code"));
//			map.put("styleCode", bean.get("style_code"));
////			map.put("nodeDiffTime", ( diff < 0 ?"超时":"剩余" )+ WebUtil.getTimeDisPlay(Math.abs(diff)));
//			if( "1".equals(type)){//全部订单
//				map.put("nodeDiffTime",( diff < 0 ?"超时":"剩余" )+ WebUtil.getTimeDisPlay(Math.abs(diff)));
//				strList.add(map);
//			}
//			if("2".equals(type) && diff >= 0 ){//未超时订单
//				map.put("nodeDiffTime","剩余" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				strList.add(map);
//			}
//			if("3".equals(type) && -diff <= ConstantUtil.STATISTICS_TIMEOUT_SERIOUS && -diff > 0   ){//一般超时
//				map.put("nodeDiffTime","超时" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				strList.add(map);
//			}
//			if("4".equals(type) && -diff > ConstantUtil.STATISTICS_TIMEOUT_SERIOUS ){//严重超时
//				map.put("nodeDiffTime","超时" + WebUtil.getTimeDisPlay(Math.abs(diff)));
//				strList.add(map);
//			}
//			
//		}
//		
//		map.put("strList", strList);
//		Struts2Utils.renderJson(map);
//	}
//	
//	/**
//	 * 所有订单
//	 */
//	public void orderAll(){
//		String flag = Struts2Utils.getParameter("flag");
//		Calendar cld = Calendar.getInstance();
//		Calendar cal = AutoStatisticsServlet.getFirstDay();
//		List orderAll = new ArrayList();
//		FSPBean fsp = new FSPBean();
//		List<LazyDynaMap> beans1 = new ArrayList<LazyDynaMap>();
//		
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.STATISTICS_ORDER_TIMERATE_BY_SQL_1);
//		fsp.set(FSPBean.FSP_ORDER, " order by begin_time desc");
//		// 根据参数判断要查询的相应的月份 ,1上月, 2 上上月,默认查询当月
//		if ("1".equals(flag)) {
//			fsp.set("gt_end_time", GetMonth.getFirstMonth());
//			fsp.set("lt_end_time", GetMonth.getLastMonth());
//		} else if ("2".equals(flag)) {
//			fsp.set("gt_end_time", GetMonth.getSecondMonth());
//			fsp.set("lt_end_time", GetMonth.getLastSecondMonth());
//		} else {
//			fsp.set("wf_real_start", GetMonth.getNowMonth());
//		}
//		beans1 = manager.getObjectsBySql(fsp);
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.STATISTICS_ORDER_TIMERATE_BY_SQL_2);
//		fsp.set(FSPBean.FSP_ORDER, " order by begin_time desc");
//		// 根据参数判断要查询的相应的月份 ,1上月, 2 上上月,默认查询当月
//		if ("1".equals(flag)) {
//			fsp.set("gt_end_time", GetMonth.getFirstMonth());
//			fsp.set("lt_end_time", GetMonth.getLastMonth());
//		} else if ("2".equals(flag)) {
//			fsp.set("gt_end_time", GetMonth.getSecondMonth());
//			fsp.set("lt_end_time", GetMonth.getLastSecondMonth());
//		} else {
//			fsp.set("start_time", GetMonth.getNowMonth());
//		}
//		
//		System.out.println( sdf1.format(cal.getTime()) );
//		List<LazyDynaMap> beans2 = manager.getObjectsBySql(fsp);
//		Calendar cal1 = Calendar.getInstance();
//		cal1.set(Calendar.HOUR_OF_DAY,9);
//		cal1.set(Calendar.MINUTE, 0);
//		cal1.set(Calendar.SECOND, 0);
//		Timestamp now = new Timestamp(cal1.getTimeInMillis());
//		System.out.println(now);
//		
//		//未完成订单
//		for( LazyDynaMap bean : beans1 ){
//			Map map = new HashMap();
//			Timestamp start = (Timestamp)bean.get("begin_time");//订单开始时间
//			Timestamp realStart = (Timestamp)bean.get("wf_real_start");//本步骤开始时间
//			Timestamp planStart = (Timestamp)bean.get("wf_plan_start");//订单开始时间
//			long duration = (long)bean.get("wf_step_duration");//当前步骤所需时间
//			long diff = duration - BizUtil.getWorkTimeBetween(now, realStart);//节点偏差    节点超时即算作订单超时
////			long diff = duration - BizUtil.getWorkTimeBetween(now, planStart);//jie
//			Timestamp planFinish = BizUtil.culPlanDate(planStart, duration);
////			long diff = planFinish.getTime() - now.getTime();
//			
//			
//			map.put("orderId", bean.get("id"));
//			map.put("startTime", sdf.format(start) );//订单开始时间
////			map.put("planTime", sdf.format( BizUtil.culPlanDate(realStart,duration)));//计划完成时间
//			map.put("planTime", sdf.format( planFinish ));//计划完成时间
//			map.put("orderDiffTime",( diff < 0 ?"超时":"剩余") + WebUtil.getTimeDisPlay(Math.abs(diff)));//当前节点偏差
//			if( "T3156".equals( bean.get("style_code").toString())){
//				System.out.println( planFinish + "====" + duration  + "===="+ now + "===="+ diff +( diff < 0 ?"超时":"剩余") + WebUtil.getTimeDisPlay(Math.abs(diff)));
//			}
//			System.out.println(bean.get("style_code") + "=======" +( diff < 0 ?"超时":"剩余") + WebUtil.getTimeDisPlay(Math.abs(diff)));
//			map.put("cusCode", bean.get("cus_code"));
//			map.put("styleCode", bean.get("style_code"));
//			orderAll.add(map);
//		}
//		//完成订单
//		for( LazyDynaMap bean: beans2 ){
//			Map map = new HashMap();
//			Timestamp start = (Timestamp)bean.get("begin_time");//订单开始时间
//			Timestamp realStart = (Timestamp)bean.get("wf_real_start");//最后步骤开始时间
//			Timestamp end = (Timestamp)bean.get("end_time");//订单完成时间
//			Timestamp planStart = (Timestamp)bean.get("wf_plan_start");
//			long duration = (long)bean.get("wf_step_duration");
//			long diff = duration - BizUtil.getWorkTimeBetween(end,planStart);//订单总体偏差
//			System.out.println( bean.get("style_code") + "=====时间："  + diff + " times: " + WebUtil.getTimeDisPlay(Math.abs(diff)));
//			
//			map.put("orderId", bean.get("id"));
//			map.put("startTime", sdf.format(start) );//订单开始时间
//			map.put("planTime", sdf.format(BizUtil.culPlanDate(planStart, duration)));//计划完成时间
//			map.put("orderDiffTime",( diff < 0 ?"超时":"剩余") + WebUtil.getTimeDisPlay(Math.abs(diff)));
//			map.put("cusCode", bean.get("cus_code"));
//			map.put("styleCode", bean.get("style_code"));
//			orderAll.add(map);
//		}
//		System.out.println("total order :" + beans1.size() + "已完成:"+ beans2.size());
//		map.put("orderAll", orderAll);
//		Struts2Utils.renderJson(map);
//
//	}
//	
//	
//	/**
//	 * 本周 订单详情（实时）
//	 */
//	public void orderDetail(){
////		String flag = Struts2Utils.getParameter("flag");
//		String orderId = Struts2Utils.getParameter("orderId");//传入参数
//		fsp.set(FSPBean.FSP_QUERY_BY_XML,StDaoImpl.GET_OA_ORDER_DETAIL_NOW_BY_SQL);
//		fsp.set("orderId" ,orderId);
//		Calendar cal = Calendar.getInstance();
//		// 根据参数判断要查询的相应的月份 ,1上月, 2 上上月,默认查询当月
////		if (flag.equals("1")) {
////			fsp.set("start_time", GetMonth.getFirstMonth());
////			fsp.set("end_time", GetMonth.getLastMonth());
////		} else if (flag.equals("2")) {
////			fsp.set("start_time", GetMonth.getSecondMonth());
////			fsp.set("end_time", GetMonth.getLastSecondMonth());
////		} else {
////			fsp.set("start_time", GetMonth.getNowMonth());
////			fsp.set("end_time",sdf1.format(cal.getTime()));
////		}
//		beans = manager.getObjectsBySql(fsp);
//		List orderDetail = new ArrayList();
//		//TODO today
//		for(LazyDynaMap bean : beans){
//			Map m = new HashMap();
//			Timestamp start = (Timestamp)bean.get("wf_real_start");
//			Timestamp finish = bean.get("wf_real_finish") == null ? new Timestamp(System.currentTimeMillis()) : (Timestamp)bean.get("wf_real_finish");//完成时间
//			long duration = (long)bean.get("wf_step_duration");
//			long usedTime = BizUtil.getWorkTimeBetween(finish, start);
//			long diff = duration - usedTime ;
//			
//			m.put("nodeName", bean.get("wf_step_name"));
//			m.put("startTime", sdf.format(start) );
//			m.put("department",departmentName(bean.get("oa_org").toString()));
//			m.put("staff",bean.get("operator"));
//			m.put("useTime", WebUtil.getTimeDisPlay(usedTime));
//			m.put("nodeDuration", getTimeDisPlay(duration));
//			m.put("endTime", bean.get("wf_real_finish") == null ? "进行中" : sdf.format( (Timestamp)bean.get("wf_real_finish")));
//			m.put("diffTime", ( diff < 0 ?"超时":"剩余" )+ WebUtil.getTimeDisPlay(Math.abs(diff)));
//			
//			orderDetail.add(m);
//		}
//		
//		map.put("orderDetail", orderDetail);
//		Struts2Utils.renderJson(map);
//	}
//	
//	/**
//	 * 超时订单列表 （实时）
//	 */
//	public void orderTimeout(){
////		String flag = Struts2Utils.getParameter("flag");
//		Calendar cal = Calendar.getInstance();
//		List strList = new ArrayList();
//		fsp.set(FSPBean.FSP_QUERY_BY_XML,StDaoImpl.STATISTICS_TIMEOUT_ORDER_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER," order by ood.wf_real_start desc ");
//		// 根据参数判断要查询的相应的月份 ,1上月, 2 上上月,默认查询当月
////		if (flag.equals("1")) {
////			fsp.set("start_time", GetMonth.getFirstMonth());
////			fsp.set("end_time", GetMonth.getLastMonth());
////		} else if (flag.equals("2")) {
////			fsp.set("start_time", GetMonth.getSecondMonth());
////			fsp.set("end_time", GetMonth.getLastSecondMonth());
////		} else {
////			fsp.set("start_time", GetMonth.getNowMonth());
////			fsp.set("end_time", sdf1.format(cal.getTime()));
////		}
//		
//		beans = manager.getObjectsBySql(fsp);
//		Timestamp now = new Timestamp(System.currentTimeMillis());
//		for(LazyDynaMap bean : beans){
//			Map m = new HashMap();
//			Timestamp start = (Timestamp)bean.get("wf_real_start");//节点开始时间
//			long duration = (long)bean.get("wf_step_duration");//节点所需时间
//			long between = BizUtil.getWorkTimeBetween(now, start);
//			long diff = duration - between;
//			if(diff < 0 ){
//				diff = -diff ;
//				m.put("orderId", bean.get("oa_order"));
//				m.put("startTime", sdf.format( start ));
//				m.put("planTime", sdf.format( BizUtil.culPlanDate(start,duration)));//计划完成时间
//				m.put("department", departmentName(bean.get("oa_org").toString()));
//				m.put("orderDiffTime", WebUtil.minusTimeAndOffset((Timestamp)bean.get("wf_plan_start"),duration));
//				m.put("nodeDuration", getTimeDisPlay(duration));
//				m.put("nodeTakeTime",getTimeDisPlay(between));
//				m.put("operator", bean.get("operator"));
//				m.put("cusCode", bean.get("cus_code"));
//				m.put("styleCode", bean.get("style_code"));
//				m.put("nodeDiffTime", "超时" + WebUtil.getTimeDisPlay(diff));
//				strList.add(m);
//			}
//		}
//		
//		map.put("strList", strList);
//		Struts2Utils.renderJson(map);
//	}
//	
//	/**
//	 * 获取超时订单总数
//	 * @return
//	 */
//	private int getOrderTimeout(){
////		String flag = Struts2Utils.getParameter("flag");
//		Calendar cal = Calendar.getInstance();
//		FSPBean tempFsp = new FSPBean();
//		int count = 0;
//		//查找当前正在进行的订单。判断当前节点是否超时
//		tempFsp.set(FSPBean.FSP_QUERY_BY_XML,StDaoImpl.STATISTICS_TIMEOUT_ORDER_BY_SQL);
////		if ("1".equals(flag)) {
////			tempFsp.set("start_time", GetMonth.getFirstMonth());
////			tempFsp.set("end_time", GetMonth.getLastMonth());
////		} else if ("2".equals(flag)) {
////			tempFsp.set("start_time", GetMonth.getSecondMonth());
////			tempFsp.set("end_time", GetMonth.getLastSecondMonth());
////		} else {
////			tempFsp.set("start_time", GetMonth.getNowMonth());
////			tempFsp.set("end_time", sdf1.format(cal.getTime()));
////		}
//		beans = manager.getObjectsBySql(tempFsp);
//		Timestamp now = new Timestamp(System.currentTimeMillis());
//		for(LazyDynaMap bean : beans){
//			Timestamp start = (Timestamp)bean.get("wf_real_start");//节点开始时间
//			System.out.println(start);
//			long between = (long)bean.get("wf_step_duration");//节点所需时间
//			long diff = between - BizUtil.getWorkTimeBetween(now, start);
//			if( diff < 0 ){
//				count++;
//			}
//		}
//		System.out.println( "超时订单总数： " + count);
//		return count;
//	}
//	
//	private String departmentName(String org){
//		switch (org) {
//			case "4" : return "PPC";
//			case "5" : return "MR";
//			case "6" : return "PUR";
//			case "7" : return "QA";
//			case "8" : return "IT";
//			case "9" : return "QC";
//			case "11" : return "FI";
//			case "12" : return "CP";
//			case "14" : return "CQC";
//			default : return "Unknown";
//		}
//	}
//	
//	/**
//	 *验证用户
//	 * @param args
//	 */
//	private boolean validateUser(String name,String passwd){
//		boolean flag = false;
//		if( StringUtils.isNotBlank(name) && StringUtils.isNotBlank(passwd)){
//			System.out.println("not null");
//			fsp.set(FSPBean.FSP_QUERY_BY_XML, StDaoImpl.list_oa_staff);
//			fsp.set("login_name", name);
//			fsp.set("passwd",passwd);
////			fsp.set("role", "13");//7剑
//			
//			bean = manager.getOnlyObjectBySql(fsp);
//			if(bean != null && bean.get("id") != null){
//				flag = true;
//			}
//		}
//		return flag;
//	}
//	
////	public static void main(String args[]){
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////		Calendar cal = Calendar.getInstance();
////		System.out.println( cal.getFirstDayOfWeek() );
////		System.out.println( cal.getWeeksInWeekYear() );
////		
////		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
////		System.out.println( sdf.format(cal.getTime()) );
////		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
////		System.out.println( sdf.format(cal.getTime()) );
////			
////	}
//	
//	public void nodeTest(){
//		AutoStatisticsServlet.copyDate();
//		System.out.println("SQL data copy  complete!");
//		AutoStatisticsServlet.orderTask();
//		System.out.println("order Task  complete!");
//		AutoStatisticsServlet.nodeTask();
//		System.out.println("node Task  complete!");
//		AutoStatisticsServlet.staffTask();
//		System.out.println("staff Task  complete!");
////		AutoStatisticsServlet.day2Run().getTime();
//		AutoStatisticsServlet.statisticsEveryDayTask();
//	}
//	
//	private static  String getTimeDisPlay(long l) {
//		StringBuffer buf = new StringBuffer();
//		long hour = (l / (60 * 60 * 1000) );
//		long min = ((l / (60 * 1000)) -  hour * 60);
//		long s = (l / 1000 - hour * 60 * 60 - min * 60);
//
//		if (hour > 0) {
//			buf.append(hour + "小时");
//			buf.append(min + "分");
//		} else if (min > 0) {
//			buf.append(min + "分");
//			buf.append(s + "秒");
//		} else if (s > 0) {
//			buf.append("0分");
//			buf.append(s + "秒");
//		} else {
//			buf.append("0分");
//			buf.append("0秒");
//		}
//		return buf.toString();
//	}
//	
//	public static void main(String[] 	args	){
////		System.out.println(getTimeDisPlay(144000000L));
//		PinyinHelper pinyin = new PinyinHelper();
//		String str = "模子";
////		pinyin.convertToPinyinArray(str);
//	System.out.println(	pinyin.convertToPinyinString("模子", ""));
//	System.out.println(	pinyin.getShortPinyin(str));
////		pinyin. 
//	}
//	
//}
