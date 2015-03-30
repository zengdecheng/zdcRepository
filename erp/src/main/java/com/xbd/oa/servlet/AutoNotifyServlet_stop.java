package com.xbd.oa.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.beanutils.LazyDynaMap;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.utils.BizUtil;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.SMSUtils;
import com.xbd.oa.vo.OaOrderDetail;

/**
 * Servlet implementation class AutoNotify
 * 
 * 已停用  超时订单发送短信通知 主管及操作人
 */
@WebServlet("/AutoNotify")
public class AutoNotifyServlet_stop extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, String> leaderPhone = new HashMap<String, String>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH");

	private static BaseDao baseDao;
	
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@Resource(name="bxDaoImpl")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AutoNotifyServlet_stop() {
//		SMSUtils.updateAccessToken();//
//		refreshLeaderPhone();// 刷新部门主管手机号
//		workflowNotificatioinTask();// 定时发送通知
	}

	/**
	 * 流转提醒 1.9:00---18:00 之间进行提醒 2.30min/次 3.每个流程仅提醒1次 在数据库中用字段控制 a sms_remind
	 * 流程到达提醒;b sms_timeout 流程超时提醒 4.员工手机号需完善
	 * 
	 */
	public static void workflowNotificatioinTask() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if( ifRunTime() ){
						workflowNotify2staff();
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

			}

		}, 0, ConstantUtil.NOTIFYSTAFF_TIMER);

	}

	/**
	 * 流程不足3小时，提醒操作人
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void workflowNotify2staff()
			throws UnsupportedEncodingException {
		long t1 = System.currentTimeMillis()/1000*1000;
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML,
				BxDaoImpl.GET_OA_ORDER_DETAIL_FOR_AUTONOTIFY_BY_SQL);
		List<LazyDynaMap> remindList = baseDao.getObjectsBySql(fsp);// 当前正在进行的流程

		for (LazyDynaMap remind : remindList) {
			int detail_id = (int) remind.get("detail_id");// 流程id
			String linkphone = remind.get("linkphone").toString();// 操作人手机号
			String style_code = remind.get("style_code").toString();// 款号
			String step_name = remind.get("wf_step_name").toString();// 流程名称
			String operator = remind.get("operator").toString();// 流程操作人
			String orgId = remind.get("oa_org").toString();

			Timestamp time = new Timestamp(System.currentTimeMillis()/1000*1000);
			long duration = (long) remind.get("wf_step_duration"); // 流程所需时间
			Timestamp start_time = (Timestamp) remind.get("wf_real_start"); // 流程实际开始时间
			long interval = duration
					- BizUtil.getWorkTimeBetween(time, start_time); // 开始时间与当前时间的时间差

			// 判断当前流程是否已提醒
			if ("1".equals(remind.get("sms_remind").toString())
					&& interval < ConstantUtil.NOTIFYSTAFF_BEFORE3HOURS) {
				String remind_params = "{\"style_code\":\"" + style_code
						+ "\"}";// 短信模板参数
				System.out.println("======定时任务======流程提醒：" + remind_params + linkphone);
				SMSUtils.sendTempletMessage(linkphone,
						ConstantUtil.TEMPLET_NOFIFY_OPERATOR, remind_params);// 短信通知操作人
				changeState(detail_id, ConstantUtil.NOTIFY_TYPE_REMIND);
			}
			// 判断流程超时是否已提醒
			if ("1".equals(remind.get("sms_timeout").toString())
					&& interval < 0) {
				String timeout_params = "{\"style_code\":\"" + style_code
						+ "\",\"step_name\":\""
						+ URLEncoder.encode(step_name, "UTF-8")
						+ "\",\"operator\":\""
						+ URLEncoder.encode(operator, "UTF-8") + "\"}";// 短信模板参数
				System.out.println("======定时任务======超时订单提醒：" + timeout_params);
				SMSUtils.sendTempletMessage(linkphone,
						ConstantUtil.TEMPLET_NOTIFY_OUTTIME, timeout_params);// 短信通知操作人
				// TODO 获取主管手机号
				String leader_phone = leaderPhone.get(orgId);
				System.out.println("leaderPhone :" + leader_phone);
				SMSUtils.sendTempletMessage(leader_phone,
						ConstantUtil.TEMPLET_NOTIFY_OUTTIME, timeout_params);// 短信通知主管
				changeState(detail_id, ConstantUtil.NOTIFY_TYPE_TIMEOUT);
			}
		}

		System.out.println("结束时间：" + (System.currentTimeMillis()/1000*1000 - t1));
	}

	/**
	 * 修改 流转提醒和流程超时提醒 状态
	 * 
	 * @return
	 */
	public static void changeState(Integer id, String type) {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_DETAIL_BY_EQL);
		fsp.set("id", id);
		OaOrderDetail ood = (OaOrderDetail) baseDao.getOnlyObjectByEql(fsp);
		if (ConstantUtil.NOTIFY_TYPE_REMIND.equals(type)) {
			// 设置当前流程已提醒
			ood.setSmsRemind(ConstantUtil.NOTIFYSTAFF_STATE_REMIND);
		}
		if (ConstantUtil.NOTIFY_TYPE_TIMEOUT.equals(type)) {
			// 设置当前流程超时已提醒
			ood.setSmsTimeout(ConstantUtil.NOTIFYSTAFF_STATE_TIMEOUT);
		}
		baseDao.saveObject(ood);
	}

	/**
	 * 刷新部门主管手机号
	 */
	public static void refreshLeaderPhone() {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML,
				BxDaoImpl.GET_OA_ORG_LEADER_LINKPHONE_BY_SQL);
		List<LazyDynaMap> leaders = baseDao.getObjectsBySql(fsp);
		for (LazyDynaMap leader : leaders) {
			if (leader.get("linkphone") != null) {
				String orgId = leader.get("id").toString(); // 部门id
				String linkphone = leader.get("linkphone").toString(); // 主管手机号
				leaderPhone.put(orgId, linkphone);
			}
		}
	}

	public static boolean ifRunTime() {
		boolean flag = false;
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		/*** 定制每日8:00执行方法 ***/
		start.set(Calendar.HOUR_OF_DAY, 8);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		/*** 定制每日20:00方法 ***/
		end.set(Calendar.HOUR_OF_DAY, 20);
		end.set(Calendar.MINUTE, 00);
		end.set(Calendar.SECOND, 0);

		// 第一次执行定时任务的时间
		Date now = new Date();
		System.out.println(start.getTime());
		System.out.println(now);
		System.out.println(end.getTime());
		//每天8:00开始到20:00之间执行定时任务
		if (now.after(start.getTime()) && now.before(end.getTime())) {
			flag = true;
		} else {
			// 不执行
		}
		
		return flag;
	}

}
