package com.xbd.oa.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.lang3.StringUtils;
import org.use.base.FSPBean;
import org.use.base.utils.base.FileUtils;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderDetail;

public class BizUtil {
	private static BaseManager bxMgr;

	/**
	 * 启动工作流,开始节点和第一个用户任务是连起来做完的 . 创建订单算一个usertask节点，在工作开始后自动完成，并新增订单子节点及更新订单主表。在 设计该节点的时候，不扩展activiti:taskListener ，持续时间 step_duration 设置 为零。
	 * 
	 * @param type
	 *            订单类型
	 * @param owner
	 *            启动的所有人
	 * @param oaOrder
	 *            订单vo,此为已经保存过的订单。
	 */
	public static void startOrderWf(String processInstanceByKey, String curUser, OaOrder oaOrder) {

		String processInstanceId = WorkFlowUtil.startWf(processInstanceByKey);
		WorkFlowUtil.getPe().getRuntimeService().setVariable(processInstanceId, "oa_order", oaOrder.getId());

		// 开始节点和第一步是连起来做的，每个流程的第一个节点不扩展createHandle
		Task task = WorkFlowUtil.getOnlyCurTaskByProcessInstanceId(processInstanceId);
		task.setAssignee(curUser);
		firstEditBizOrderAndDetail(processInstanceId, curUser, oaOrder, task);
		WorkFlowUtil.completeTask(task.getId());
		/*
		 * //2.修改订单主表 //oaOrder.set
		 * 
		 * //3.存入第一步的订单细节表 OaOrderDetail oaOrderDetail = new OaOrderDetail(); oaOrderDetail.setOaOrder(oaOrder.getId()); oaOrderDetail.setOperator(owner); oaOrderDetail.setProcId(processId);
		 * //oaOrderDetail.set
		 * 
		 * //4.使工作流工作流走到下一个节点 WorkFlowUtil.completeTask(task.getId());
		 */
	}

	/**
	 * 经理指派给某一个员工
	 * 
	 * @param oaOrderDetailId
	 * @param assigner
	 */
	public static void assignOther(Integer oaOrderDetailId, String assigner, String groupName) {
		OaOrderDetail oaOrderDetail = (OaOrderDetail) getBxMgr().getObject(OaOrderDetail.class, oaOrderDetailId);
		OaOrder oaOrder = (OaOrder) getBxMgr().getObject(OaOrder.class, oaOrderDetail.getOaOrder());
		WorkFlowUtil.getPe().getTaskService().setAssignee(oaOrderDetail.getTaskId(), assigner);
		oaOrderDetail.setOperator(assigner);
		oaOrder.setOperator(assigner);
		getBxMgr().saveObject(oaOrderDetail);
		setOaOrderHisOpt(oaOrder, assigner, groupName);
		getBxMgr().saveObject(oaOrder);
	}

	/**
	 * 执行保存流程节点完成
	 * 
	 * @param OaOrderDetailId
	 * @param procId
	 * @param taskId
	 */
	public static void nextStep(OaOrderDetail oaOrderDetail) {
		// 1向流程传入OaOrderDetailId作为流程变量
		WorkFlowUtil.getPe().getRuntimeService().setVariable(oaOrderDetail.getProcId(), "oa_order_detail", oaOrderDetail.getId());
		// 2完成此流程
		WorkFlowUtil.completeTask(oaOrderDetail.getTaskId());
	}

	/**
	 * 执行保存流程节点完成
	 * 
	 * @param OaOrderDetailId
	 * @param procId
	 * @param taskId
	 */
	public static void backStep(OaOrderDetail oaOrderDetail) {
		// 1向流程传入OaOrderDetailId作为流程变量
		WorkFlowUtil.getPe().getRuntimeService().setVariable(oaOrderDetail.getProcId(), "oa_order_detail", oaOrderDetail.getId());
		// 2完成此流程
		WorkFlowUtil.turnTransitionBack(oaOrderDetail.getTaskId(), null);
	}

	/**
	 * 执行带条件的保存流程节点完成
	 * 
	 * @param oaOrderDetail
	 */
	public static void nextStepWithCase(OaOrderDetail oaOrderDetail, Map<String, String> params) {
		// 1向流程传入OaOrderDetailId作为流程变量
		WorkFlowUtil.getPe().getRuntimeService().setVariable(oaOrderDetail.getProcId(), "oa_order_detail", oaOrderDetail.getId());
		// 2传入条件变量
		// params
		for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			WorkFlowUtil.getPe().getRuntimeService().setVariable(oaOrderDetail.getProcId(), key, params.get(key));
		}
		// 3完成此流程
		WorkFlowUtil.completeTask(oaOrderDetail.getTaskId());
	}

	/**
	 * 根据组名得到工作流任务节点需要指派的人
	 * 
	 * @param group
	 */
	public static String getAssigneeByGroup(String groupName, String processInstanceId, Integer oaOrderInt) {
		String adminName = "";
		// 看此流程是否以前被组内某个成员做过，做了的话就直接分配给组员，不需要组长再分配。
		OaOrder oaOrder = (OaOrder) getBxMgr().getObject(OaOrder.class, oaOrderInt);
		if (oaOrder != null) {
			String hisOpt = oaOrder.getHisOpt();
			String[] ss = hisOpt.split(";");
			for (String groupAndStaff : ss) {
				if (groupAndStaff.startsWith(groupName)) {
					adminName = groupAndStaff.substring(groupAndStaff.indexOf(":") + 1);
					break;
				}
			}
		}
		// 根据组的名字算出组内管理员
		if (adminName == "") {
			adminName = XbdBuffer.getAdminByGroupName(groupName);
		}
		return adminName;
	}

	/**
	 * 第一个节点的修改业务模块，包含order主表和order_detail子表
	 * 
	 * @param processInstanceId
	 * @param curUser
	 * @param oaOrder
	 * @param task
	 */
	private static void firstEditBizOrderAndDetail(String processInstanceId, String curUser, OaOrder oaOrder, Task task) {
		Timestamp curTime = getOperatingTime(new Timestamp(System.currentTimeMillis()));// 当前时间

		// 1.加入新的流程详细节点
		OaOrderDetail newOaOrderDetail = new OaOrderDetail();
		newOaOrderDetail.setOaOrder(oaOrder.getId());
		newOaOrderDetail.setProcId(processInstanceId);
		newOaOrderDetail.setTaskId(task.getId());
		newOaOrderDetail.setInx(1);
		newOaOrderDetail.setOperator(curUser);

		// update by 张华 2014-12-24
		String temp = oaOrder.getCusCode() + "-" + oaOrder.getStyleCode();
		if (StringUtils.isNotEmpty(oaOrder.getFileUrl())) {
			String dinFilePath = PathUtil.url2Path(oaOrder.getFileUrl().replaceAll("new", temp));
			FileUtils.copyFile(PathUtil.url2Path(oaOrder.getFileUrl()), dinFilePath);
			newOaOrderDetail.setAttachment(PathUtil.path2Url(dinFilePath));
		}
		if (StringUtils.isNotEmpty(oaOrder.getPicUrl())) {
			String dinPicPath = PathUtil.url2Path(oaOrder.getPicUrl().replaceAll("new", temp));
			FileUtils.copyFile(PathUtil.url2Path(oaOrder.getPicUrl()), dinPicPath);
			newOaOrderDetail.setPic(PathUtil.path2Url(dinPicPath));
		}
		newOaOrderDetail.setContent(oaOrder.getSellMemo()); // 设置销售备注到detail中
		// newOaOrderDetail.setContent(oaOrder.getMemo());

		newOaOrderDetail.setWfStep(task.getTaskDefinitionKey());
		newOaOrderDetail.setWfStepName(task.getName());
		newOaOrderDetail.setWfPlanStart(curTime);// 计划开始时间
		newOaOrderDetail.setWfRealStart(curTime);// 实际开始时间
		newOaOrderDetail.setWfStepDuration(0l);// 节点持续时间 毫秒数
		newOaOrderDetail.setSmsRemind(ConstantUtil.NOTIFYSTAFF_STATE_REMIND_NO);
		newOaOrderDetail.setSmsTimeout(ConstantUtil.NOTIFYSTAFF_STATE_TIMEOUT);
		getBxMgr().saveObject(newOaOrderDetail); // 保存本节点信息

		WorkFlowUtil.getPe().getRuntimeService().setVariable(processInstanceId, "oa_order_detail", newOaOrderDetail.getId());
		// 2因为直接跳转到第二节点所以此时不更新主表里面工作流的信息
		oaOrder.setBeginTime(curTime);
		oaOrder.setOaOrderDetail(newOaOrderDetail.getId());
		setOaOrderHisOpt(oaOrder, curUser, "sales");// 写死组为mr,因为所有发起者都是mr
		getBxMgr().saveObject(oaOrder); // 保存本节点信息
	}

	/**
	 * 修改业务模块，包含order主表和order_detail子表
	 * 
	 * @param id
	 *            oa_order_detail 上个流程子节点 订单详细表 id
	 * @param delegateTask
	 *            代理工作任务
	 * @param assignment
	 *            指派人
	 */
	public static void editBizOrderAndDetail(Integer id, DelegateTask delegateTask, String assignment, String groupName, String description) {
		// TODO Auto-generated method stub
		// 1.上一个节点的完成时间
		Timestamp curTime = getOperatingTime(new Timestamp(System.currentTimeMillis()));// 当前时间
		OaOrderDetail lastOaOrderDetail = (OaOrderDetail) getBxMgr().getObject(OaOrderDetail.class, id);// 上一个流程节点的订单详细vo
		OaOrder oaOrder = (OaOrder) getBxMgr().getObject(OaOrder.class, lastOaOrderDetail.getOaOrder());// 主流程，订单vo
		lastOaOrderDetail.setWfRealFinish(curTime);

		// update by 张华 2014-12-29
		if (lastOaOrderDetail.getWorkTime() == null) {
			lastOaOrderDetail.setWorkTime(curTime);
		}

		// update by 张华 2015-03-11
		// 判断如果此流程流转属于退回上一步，那么设置此节点和之前节点back_flag字段为1
		try {
			int stepIndex1 = Integer.parseInt(lastOaOrderDetail.getWfStep().substring(lastOaOrderDetail.getWfStep().lastIndexOf("_") + 1, lastOaOrderDetail.getWfStep().length()));
			int stepIndex2 = Integer.parseInt(delegateTask.getTaskDefinitionKey().substring(delegateTask.getTaskDefinitionKey().lastIndexOf("_") + 1, delegateTask.getTaskDefinitionKey().length()));
			if (stepIndex1 > stepIndex2) {
				lastOaOrderDetail.setBackFlag("1");
			}
			// 查询上一节点，并设置back_flag字段为1
			FSPBean fsp = new FSPBean();
			fsp.set("oaOrder", lastOaOrderDetail.getOaOrder());
			fsp.set("wfStep", delegateTask.getTaskDefinitionKey());
			// fsp.set("inx", lastOaOrderDetail.getInx().intValue());
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_DETAIL_BY_EQL);
			List<OaOrderDetail> list = getBxMgr().getObjectsByEql(fsp);
			for (OaOrderDetail oaOrderDetail : list) {
				oaOrderDetail.setBackFlag("1");
				getBxMgr().saveObject(oaOrderDetail);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		getBxMgr().saveObject(lastOaOrderDetail); // 保存上个节点信息

		// 2.1 计算三个时间 计划完成时间 ,总剩余时间,实际完成偏差值
		// (1)本节点持续时长,计划累计时长. (需要乘以时间折算率)

		String ss[] = delegateTask.getProcessDefinitionId().split(":");// 取数组的第一个元素就是key
																		// ，如oawf1:4:304

		LazyDynaMap oaTimebaseLazyDynaMap = XbdBuffer.getOaTimebaseEntry(ss[0], delegateTask.getTaskDefinitionKey(), oaOrder.getClothClass()); // 本节点设定时长
		double durationD = ((Long) oaTimebaseLazyDynaMap.get("step_duration")) * oaOrder.getTimeRate();
		Long duration = Math.round(durationD);
		double calculateDurationD = ((Long) oaTimebaseLazyDynaMap.get("calculate_duration")) * oaOrder.getTimeRate();
		Long calculateDuration = Math.round(calculateDurationD);
		// (2)本节点计划开始时间 。 = 订单开始时间+ 累计计划持续时间
		// Timestamp planStart = new Timestamp(oaOrder.getBeginTime().getTime()
		// + calculateDuration);
		Timestamp planStart = culPlanDate(oaOrder.getBeginTime(), calculateDuration); // 本节点计划开始时间 。 = 订单开始时间+ 累计计划持续时间

		// 2.加入新的流程详细节点
		OaOrderDetail newOaOrderDetail = new OaOrderDetail();
		newOaOrderDetail.setOaOrder(lastOaOrderDetail.getOaOrder());
		newOaOrderDetail.setProcId(delegateTask.getProcessInstanceId());
		newOaOrderDetail.setTaskId(delegateTask.getId());
		newOaOrderDetail.setInx(lastOaOrderDetail.getInx() + 1);
		newOaOrderDetail.setOperator(assignment);

		newOaOrderDetail.setWfStep(delegateTask.getTaskDefinitionKey());
		newOaOrderDetail.setWfStepName(delegateTask.getName());
		newOaOrderDetail.setWfPlanStart(planStart);// 计划开始时间
		newOaOrderDetail.setWfRealStart(curTime);// 实际开始时间
		newOaOrderDetail.setWfStepDuration(duration);// 节点持续时间 毫秒数
		newOaOrderDetail.setDiscrip(description);
		newOaOrderDetail.setSmsRemind(ConstantUtil.NOTIFYSTAFF_STATE_REMIND_NO);
		// 若流程计划所需时间duration为0时，不设置超时提醒
		if (duration > 0) {
			newOaOrderDetail.setSmsTimeout(ConstantUtil.NOTIFYSTAFF_STATE_TIMEOUT_NO);
		} else {
			newOaOrderDetail.setSmsTimeout(ConstantUtil.NOTIFYSTAFF_STATE_TIMEOUT);
		}

		getBxMgr().saveObject(newOaOrderDetail); // 保存本节点信息

		// 3修改oa_order 主表信息,包括 流程节点字段step,本节点内计划完成时间
		// plan_finish,本节点原计划开始时间total_plan_start(算计划偏差值),历史操作人
		oaOrder.setWfStep(delegateTask.getTaskDefinitionKey());
		oaOrder.setWfStepName(delegateTask.getName());
		oaOrder.setWfPlanStart(planStart);// 计划开始时间
		oaOrder.setWfRealStart(curTime);// 实际开始时间
		oaOrder.setWfStepDuration(duration);// 节点持续时间 毫秒数
		oaOrder.setOperator(assignment);
		oaOrder.setOaOrderDetail(newOaOrderDetail.getId());
		setOaOrderHisOpt(oaOrder, assignment, groupName);
		getBxMgr().saveObject(oaOrder); // 保存本节点信息
	}

	/**
	 * 根据节点时间和duration算出一个时间（除去非工作时间外）
	 * 
	 * @param date
	 *            传入要计算的日期时间
	 * @param duration
	 *            节点时长
	 * @return
	 */
	public static Timestamp culPlanDate(Timestamp date, Long duration) {
		// date = getWorkTime(date); // 得到一个工作时间
		if (duration >= 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 18:00:00");
			String endWork_str = df.format(date);
			Timestamp endWork = null;// 工作结束时间
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				endWork = new Timestamp(df.parse(endWork_str).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long between = endWork.getTime() - date.getTime();// 算出开始时间与结束时间差
			if (between >= duration) { // 时间差大于duration则该节点开始时间仍属于今天工作时间内
				return new Timestamp(date.getTime() + duration);
			} else { // 结束时间已不属于今天的范围
				long newDuration = duration - between;

				// 1000*60*60 1个小时,15个小时即是晚6:00到早9:00的非工作时间
				Timestamp newDate = new Timestamp(endWork.getTime() + (1000 * 60 * 60 * 15)); // 得到一个新的计划开始时间，即第二天上午9点

				newDate = getWorkTime(newDate); // 得到一个工作时间

				return culPlanDate(newDate, newDuration);
			}
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 9:00:00");
			String startWork_str = df.format(date);
			Timestamp startWork = null;// 工作开始时间
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startWork = new Timestamp(df.parse(startWork_str).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Timestamp newDate = null;
			long newDuration = duration;
			// 1000*60*60 1个小时,15个小时即是晚6:00到早9:00的非工作时间
			newDate = new Timestamp(startWork.getTime() - (1000 * 60 * 60 * 15)); // 得到一个新的计划开始时间，即前一天下午6点

			if (getWorkTime(startWork).getTime() == startWork.getTime()) { // 说明是工作日
				long between = date.getTime() - startWork.getTime();// 算出开始时间与结束时间差
				if (between >= -duration) { // 时间差大于duration则该节点开始时间仍属于今天工作时间内
					return new Timestamp(date.getTime() + duration);
				} else { // 结束时间已不属于今天的范围
					newDuration = duration + between;
				}
			}
			return culPlanDate(newDate, newDuration);
		}
	}

	/**
	 * 得到工作时间
	 * 
	 * @param newPlanStart
	 * @return
	 */
	public static Timestamp getWorkTime(Timestamp newPlanStart) {
		SimpleDateFormat df = new SimpleDateFormat("E");// 格式化得到星期
		String day = df.format(newPlanStart);
		df = new SimpleDateFormat("yyyy-MM-dd"); // 格式化得到日期
		String dateStr = df.format(newPlanStart);
		String holidays = ResourceUtil.getString("oa.holidays"); // 配置文件中的节假日
		String workday = ResourceUtil.getString("oa.workday"); // 配置文件中的工作日

		// 判断是否为周日
		if ("星期日".equals(day)) {
			if (workday.indexOf(dateStr) < 0) { // 不包含在工作日中，则加24个小时
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 9:00:00");
				String planStart = sdf.format(newPlanStart);
				Timestamp startWork = null;// 工作开始时间
				try {
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					startWork = new Timestamp(sdf.parse(planStart).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newPlanStart = new Timestamp(startWork.getTime() + (1000 * 60 * 60 * 24)); // 得到一个新的计划开始时间，即第二天上午9点
			} else { // 包含在工作日中
				return newPlanStart;
			}
		} else {
			if (holidays.indexOf(dateStr) >= 0) { // 包含在节假日中，则加24个小时
				newPlanStart = new Timestamp(newPlanStart.getTime() + (1000 * 60 * 60 * 24)); // 得到一个新的计划开始时间，即第二天上午9点
			} else {
				return newPlanStart;
			}
		}
		return getWorkTime(newPlanStart);
	}

	/**
	 * 根据传入的时间，判断归入到工作时间内
	 * 
	 * @param ts
	 * @return
	 */
	public static Timestamp getOperatingTime(Timestamp ts) {
		// 排除节假日在外，得到一个日期
		try {
			ts = excludeHolidays(ts);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd 9:00:00");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 18:00:00");
		String startWork_str = df1.format(ts);
		Timestamp startWork = null;// 工作开始时间
		String endWork_str = df2.format(ts);
		Timestamp endWork = null;// 工作结束时间
		try {
			startWork = new Timestamp(df.parse(startWork_str).getTime());
			endWork = new Timestamp(df.parse(endWork_str).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long between1 = ts.getTime() - startWork.getTime();// 算出开始时间与操作时间差
		long between2 = endWork.getTime() - ts.getTime();// 算出结束时间与操作时间差
		if (between1 < 0) {
			return startWork;
		}
		if (between2 < 0) {
			return endWork;
		}

		return ts;
	}

	/**
	 * 根据传入参数ts，把配置的节假日排除在外
	 * 
	 * @param ts
	 * @return
	 * @throws ParseException
	 */
	private static Timestamp excludeHolidays(Timestamp ts) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String tsString = df.format(ts);
		String holidays = ResourceUtil.getString("oa.holidays"); // 获取到配置的节假日
		String workday = ResourceUtil.getString("oa.workday"); // 获取得到配置文件中的工作日

		// 判断该时间是否包含在配置的节假日中，不包含则直接返回，包含则减去一天格式化进行回调
		if (holidays.indexOf(tsString) < 0) { // 不包含直接返回该时间

			df = new SimpleDateFormat("E"); // 格式化得到星期
			String day = df.format(ts);
			df = new SimpleDateFormat("yyyy-MM-dd"); // 格式化得到日期
			String dateStr = df.format(ts);

			// 如果计算得出的ts是星期日且不包含在工作日中，则减去一天，否则正常以ts计算
			if (day.equals("星期日") && workday.indexOf(dateStr) < 0) {
				ts = new Timestamp(ts.getTime() - (1000 * 60 * 60 * 24));// 1000*60*60
																			// 1个小时
				return excludeHolidays(ts);
			}
			return ts;
		} else {
			df = new SimpleDateFormat("yyyy-MM-dd 18:00:01");
			tsString = df.format(ts);
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			ts = new Timestamp(df.parse(tsString).getTime() - (1000 * 60 * 60 * 24));
			return excludeHolidays(ts);
		}
	}

	/**
	 * 算出两个时间的工作时间差
	 * 
	 * @param ts1
	 *            当前时间或完成的时间
	 * @param ts2
	 *            计划开始时间
	 * @return
	 */
	public static long getWorkTimeBetween(Timestamp ts1, Timestamp ts2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date day_date1 = new Date();
		Date day_date2 = new Date();
		ts1 = getOperatingTime(ts1);
		ts2 = getOperatingTime(ts2);
		boolean flag = true;
		if (ts1.getTime() < ts2.getTime()) {
			Timestamp ts = ts1;
			ts1 = ts2;
			ts2 = ts;
			flag = false;
		}
		try {
			day_date1 = df.parse(df.format(ts1));
			day_date2 = df.parse(df.format(ts2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int days = (int) ((day_date1.getTime() - day_date2.getTime()) / (1000 * 60 * 60 * 24)); // 差几天
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts2);
		int sundays = (cal.get(Calendar.DAY_OF_WEEK) - 1 + days) / 7; // 差几个周日
		days -= sundays; // 周日除外

		String holidays = ResourceUtil.getString("oa.holidays"); // 配置文件中的节假日
		String workday = ResourceUtil.getString("oa.workday"); // 配置文件中的工作日
		// 排除节假日、添加工作日
		while (day_date1.compareTo(day_date2) > 0) {
			cal.setTime(day_date2);
			cal.add(Calendar.DATE, 1); // 加一天
			day_date2 = cal.getTime();
			String dateString = df.format(day_date2); // 日期字符串
			int weekday = cal.get(Calendar.DAY_OF_WEEK); // 得到星期

			// 如果是周日，则判断是否包含在工作日中即可，否则判断是否包含在节假日中
			if (1 == weekday) { // 判断为周日
				if (workday.indexOf(dateString) >= 0) { // 包含在工作日中
					days += 1;
				}
			} else { // 周一到周六
				if (holidays.indexOf(dateString) >= 0) { // 包含在节假日中
					days -= 1;
				}
			}
		}

		// 计算时间差
		df = new SimpleDateFormat("2000-01-01 HH:mm:ss");
		try {
			day_date1 = df.parse(df.format(ts1));
			day_date2 = df.parse(df.format(ts2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long times = day_date1.getTime() - day_date2.getTime(); // 得出时间差

		long between = days * (1000 * 60 * 60 * 9) + times; // 得出两个日期时间差
		if (flag) {
			return between;
		} else {
			return -between;
		}
	}

	/**
	 * 最后个节点的修改业务模块，包含order主表和order_detail子表
	 * 
	 * @param processInstanceId
	 * @param curUser
	 * @param oaOrder
	 * @param task
	 */
	public static void lastEditBizOrderAndDetail(Integer id) {

		// 1.上一个节点的完成时间
		Timestamp curTime = getOperatingTime(new Timestamp(System.currentTimeMillis()));// 当前时间
		OaOrderDetail myOaOrderDetail = (OaOrderDetail) getBxMgr().getObject(OaOrderDetail.class, id);// 上一个流程节点的订单详细vo
		OaOrder oaOrder = (OaOrder) getBxMgr().getObject(OaOrder.class, myOaOrderDetail.getOaOrder());// 主流程，订单vo
		myOaOrderDetail.setWfRealFinish(curTime);
		getBxMgr().saveObject(myOaOrderDetail); // 保存上个节点信息

		// 2因为直接跳转到第二节点所以此时不更新主表里面工作流的信息
		oaOrder.setEndTime(curTime);
		oaOrder.setWfStep("finish_999");
		oaOrder.setWfStepName("订单完成");
		getBxMgr().saveObject(oaOrder); // 保存本节点信息
	}

	/**
	 * 计算时间折算率
	 * 
	 * @param realStartTime
	 *            实际开始时间
	 * @param exceptFinish
	 *            期望交期
	 * @param type
	 *            订单类型
	 */
	public static Float computeTimeRate(Timestamp realStartTime, Timestamp exceptFinish, String processDifinitionKey, String clothClass) {
		FSPBean fsp = new FSPBean();
		fsp.set("define_key", processDifinitionKey);
		fsp.set("cloth_class", clothClass);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_BY_SQL);
		LazyDynaMap timeBase = getBxMgr().getOnlyObjectBySql(fsp);
		if (timeBase == null) {// 找不到该品类的时间基准就找基本类的时间基准
			fsp = new FSPBean();
			fsp.set("define_key", processDifinitionKey);
			fsp.set("cloth_class", ConstantUtil.DT_TYPE_CLOTH_CLASS_BASE);
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_BY_SQL);
			timeBase = getBxMgr().getOnlyObjectBySql(fsp);
		}
		if (timeBase == null) {
			throw new RuntimeException("没有找到流程定义processDifinitionKey:" + processDifinitionKey + "服装品类clothClass:" + clothClass + "的时间基线timebase");
		}

		Long durationTotalTime = (Long) timeBase.get("total_duration");
		return computeTimeRate(realStartTime, exceptFinish, durationTotalTime);
	}

	/**
	 * 计算时间折算率
	 * 
	 * @param realStartTime
	 *            实际开始时间
	 * @param exceptFinish
	 *            期望交期
	 * @param durationTotalTime
	 *            持续时长
	 */
	private static Float computeTimeRate(Timestamp realStartTime, Timestamp exceptFinish, long durationTotalTime) {
		Float f = (0f + exceptFinish.getTime() - realStartTime.getTime()) / durationTotalTime;
		System.out.println(f);
		BigDecimal bg = new BigDecimal(f);
		float f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		if (f1 > 1) {
			f1 = 1f;
		}
		System.out.println(f1);
		return f1;
	}

	/**
	 * 填写历史操作人，一个组只允许填写一个操作人。该字段格式如下ppc:刘厚萱;ie:杨逍;采购:郭靖
	 * 
	 * @param oaOrder
	 * @param assignment
	 * @param groupName
	 */
	private static void setOaOrderHisOpt(OaOrder oaOrder, String assignment, String groupName) {
		String hisOpt = oaOrder.getHisOpt();
		if (hisOpt == null) {
			hisOpt = "";
		}
		String ruleStr = groupName + ":" + assignment;
		if (hisOpt.contains(ruleStr)) {
			return;
		} else { // 如果不包括，有两种情况第一种是这个组有别的组员做过了，第二种是没有组没有人做过
			String[] ss = hisOpt.split(";");
			boolean isExist = false;
			StringBuffer buf = new StringBuffer();
			for (String groupAndStaff : ss) {
				String[] gs = groupAndStaff.split(":");
				if (gs[0].equals(groupName)) {
					gs[1] = assignment; // 第一种情况
					isExist = true;
					buf.append(gs[0]).append(":").append(gs[1]).append(";");
				} else {
					buf.append(groupAndStaff).append(";");
				}
			}
			if (!isExist) {
				buf.append(ruleStr).append(";");
			}
			oaOrder.setHisOpt(buf.toString());
		}
		String[] ss = hisOpt.split(";");
		for (String groupAndStaff : ss) {
			if (groupAndStaff.startsWith(groupName)) {
				// adminName =
				// groupAndStaff.substring(groupAndStaff.indexOf(":"));
				break;
			}
		}
	}

	/**
	 * 生成csv文件
	 * 
	 * @param list
	 * @param titles
	 * @param keys
	 * @return
	 */
	public static String generateDownLoadCsv(List<LazyDynaMap> list, String titles, String keys) {
		String str = generateCsvStr(list, titles, keys);
		String path = PathUtil.getTmpDownCsvPath();
		try {
			FileUtils.writeFile(str.getBytes("gb2312"), path);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	private static String generateCsvStr(List<LazyDynaMap> list, String titles, String keys) {
		StringBuffer buf = new StringBuffer();
		buf.append(titles).append("\n");
		String[] ss = keys.split(",");
		// buf.append(titles).append("\n");
		if (list != null && list.size() > 0) {
			for (LazyDynaMap lazyDynaMap : list) {
				for (String key : ss) {
					Object item = lazyDynaMap.get(key);
					if (item != null) {
						// if (item instanceof Timestamp) {
						// item = sdf.format((Timestamp) item);
						// }
						buf.append(item).append(",");
					} else {
						buf.append(",");
					}
				}
				buf.append("\n");
			}
		}
		return buf.toString();
	}

	public static BaseManager getBxMgr() {
		if (bxMgr == null) {
			bxMgr = new com.xbd.oa.business.impl.BxManagerImpl();
		}
		return bxMgr;
	}

	public static void test() {
		OaOrder oaOrder = new OaOrder();
		oaOrder.setClothClass("1");
		oaOrder.setTimeRate(1.0f);
		getBxMgr().saveObject(oaOrder);
		startOrderWf(ConstantUtil.WORKFLOW_KEY_PROCESS1, "骆冰", oaOrder);
	}

	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), 9L * 1000 * 60 * 60));
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), 18L * 1000 * 60 * 60));
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), 27L * 1000 * 60 * 60));
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), -27L * 1000 * 60 * 60));
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), -36L * 1000 * 60 * 60));
			// System.out.println(culPlanDate(new Timestamp(df.parse("2015-04-03 18:00:00").getTime()), -45L * 1000 * 60 * 60));

			System.out.println(getWorkTime(new Timestamp(df.parse("2015-03-22 10:00:00").getTime())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
