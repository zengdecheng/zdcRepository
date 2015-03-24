package com.xbd.oa.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.lang.StringUtils;
import org.use.base.FSPBean;
import org.use.base.action.Action;
import org.use.base.annotation.EJB;
import org.use.base.manager.Manager;
import org.use.base.utils.base.DateUtils;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.dao.impl.ExtDaoImpl;
import com.xbd.oa.utils.BizUtil;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.utils.Struts2Utils;
import com.xbd.oa.utils.WebUtil;
import com.xbd.oa.utils.XbdBuffer;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderNum;

public class ExternalAction extends Action {
	private static final long serialVersionUID = 1L;

	@EJB(name = "com.xbd.oa.business.impl.ExtManagerImpl")
	private BaseManager manager;
	private OaOrder oaOrder;
	private OaOrderNum oaOrderNum;
	private LazyDynaMap bean;

	public Manager getBiz() {
		return manager;
	}

	/******************* NPS所需接口开始 ********************/
	/**
	 * @Title: getOrderByCusCode
	 * @Description: TODO获取订单list接口，根据客户编号以获取 错误编号10打头
	 * @author 张华
	 */
	public void getOrderByCusCode() {
		String cusCode = Struts2Utils.getParameter("cusCode");// 获取参数客户编号
		Map resMap = new HashMap();// 返回结果

		if (StringUtils.isBlank(cusCode)) {
			resMap.put("code", 1001);
			resMap.put("msg", "客户编号不能为空");
		} else {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, ExtDaoImpl.LIST_OA_ORDER_BY_SQL);
			fsp.set(FSPBean.FSP_ORDER, " order by oo.begin_time desc ");
			fsp.set("cus_code", cusCode);
			List<LazyDynaMap> beans = manager.getObjectsBySql(fsp); // 查询order
			List list = new ArrayList();

			// 对查询到的order进行解析、计算偏差等
			for (LazyDynaMap bean : beans) {
				Map map = new HashMap();
				Timestamp wfPlanStart = (Timestamp) bean.get("wf_plan_start");
				Timestamp wfRealStart = (Timestamp) bean.get("wf_real_start");
				Timestamp wfRealEnd = (Timestamp) bean.get("end_time");
				Timestamp exceptFinish = (Timestamp) bean.get("except_finish");
				long wfStepDuration = (Long) bean.get("wf_step_duration");
				String type = (String) bean.get("type");
				String wfStep = (String) bean.get("wf_step");

				map.put("id", bean.get("id")); // 订单ID
				map.put("styleCode", bean.get("style_code")); // 样衣款号
				map.put("isUrgent", bean.get("is_urgent")); // 是否加急
				map.put("wfStepName", bean.get("wf_step_name")); // 当前流程

				/*
				 * 判断该订单是否已结束，结束后已结束时间计算偏差，未结束以当前时间计算偏差
				 */
				if ("finish_999".equals(wfStep)) {
					map.put("nodePlanOffset", minusTimeAndOffset(wfRealEnd, wfRealStart, wfStepDuration)); // 当前节点计划完成时间
					map.put("orderPlanOffset", minusTimeAndOffset(wfRealEnd, wfPlanStart, wfStepDuration)); // 订单计划偏差
					map.put("totalRest", minusTime(wfRealEnd, exceptFinish)); // 计划剩余时间
				} else {
					map.put("nodePlanOffset", WebUtil.minusTimeAndOffset(wfRealStart, wfStepDuration)); // 当前计划完成时间
					map.put("orderPlanOffset", WebUtil.minusTimeAndOffset(wfPlanStart, wfStepDuration)); // 订单计划偏差
					map.put("totalRest", WebUtil.minusTime(exceptFinish)); // 计划剩余时间
				}

				map.put("type", WebUtil.getOrderTypeStr(type)); // 订单类型
				map.put("city", bean.get("city")); // 区域
				map.put("operator", bean.get("operator")); // 操作人

				list.add(map);
			}

			resMap.put("code", 0);
			resMap.put("msg", "查询成功");
			resMap.put("orderList", list);
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 
	 * @Title: getOrderDetail
	 * @Description: TODO获取订单进度详情接口，根据订单ID以获取 错误编号11打头
	 * 
	 * @author 张华
	 */
	public void getOrderDetail() {
		String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单ID
		int orderId = 0;
		Map resMap = new HashMap();// 返回结果

		if (StringUtils.isBlank(orderIdStr)) {
			resMap.put("code", 1101);
			resMap.put("msg", "订单ID不能为空");
		} else {
			try {
				orderId = Integer.parseInt(orderIdStr);
				OaOrder oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
				if (null == oaOrder || null == oaOrder.getId()) {
					resMap.put("code", 1103);
					resMap.put("msg", "订单ID不存在");
				} else {
					// 把品类code换成value
					if (null != oaOrder.getClothClass() && !"".equals(oaOrder.getClothClass())) {
						List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
						for (Map<String, String> map : cloth_class_list) {
							if (map.get("code").equals(oaOrder.getClothClass())) {
								oaOrder.setClothClass(map.get("value"));
								break;
							}
						}
					} else {
						oaOrder.setClothClass("基础类");
					}
					Timestamp except_finish = (Timestamp) oaOrder.getExceptFinish();// 预期完成时间

					fsp.set(FSPBean.FSP_QUERY_BY_XML, ExtDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
					fsp.set("oa_order", oaOrder.getId());
					List<LazyDynaMap> beans = getObjectsBySql(fsp);
					List list = new ArrayList();

					for (LazyDynaMap bean : beans) {
						Map map = new HashMap();
						// 格式化完成时间
						Long wf_step_duration = (Long) bean.get("wf_step_duration"); // 此处单位毫秒
						Timestamp wf_real_finish = (Timestamp) bean.get("wf_real_finish");// 实际完成时间
						boolean flag = false;
						if (null == wf_real_finish) {
							wf_real_finish = new Timestamp(System.currentTimeMillis());
							flag = true;
						}
						Timestamp wf_real_start = (Timestamp) bean.get("wf_real_start");// 实际开始时间
						Timestamp wf_plan_start = (Timestamp) bean.get("wf_plan_start");// 计划开始时间

						// 计算节点实际完成时间偏差
						// long between = wf_step_duration
						// - BizUtil.getWorkTimeBetween(wf_real_finish,
						// wf_real_start);
						// StringBuffer offset = new StringBuffer(
						// between > 0 ? "剩余" : "超时");
						// between = Math.abs(between);
						// offset.append(WebUtil.getTimeDisPlay(between));
						String offset = minusTimeAndOffset(wf_real_finish, wf_real_start, wf_step_duration);
						// 把时长从毫秒转换为小时
						double d1 = (long) bean.get("wf_step_duration");
						double d2 = 3600 * 1000;
						double d = d1 / d2;

						map.put("wfStepName", bean.get("wf_step_name")); // 流程节点名称
						map.put("wfRealStart", DateUtil.formatDate((Timestamp) bean.get("wf_real_start"))); // 实际开始时间
						map.put("wfStepDuration", d); // 流程节点时长
						if (!flag) {
							map.put("wfRealFinish", offset);// 计算节点实际完成时间偏差存放到map中
							// map.put("wf_real_offset",
							// df.format(wf_real_finish));// 计算出实际完成时间偏差存放到map中
						} else {
							map.put("wfRealFinish", "正在处理");
						}

						// 计算订单实际偏差
						// between = wf_step_duration
						// - BizUtil.getWorkTimeBetween(wf_real_finish,
						// wf_plan_start);
						// offset = new StringBuffer(between > 0 ? "剩余" : "超时");
						// between = Math.abs(between);
						// offset.append(WebUtil.getTimeDisPlay(between));
						offset = minusTimeAndOffset(wf_real_finish, wf_plan_start, wf_step_duration);

						map.put("totalOffset", offset);// 计算出实际偏差存放到map中
						map.put("exceptFinish", DateUtils.getYYYY_MM_DD(except_finish));// 期望完成时间
						map.put("city", oaOrder.getCity());
						map.put("operator", bean.get("operator")); // 操作人

						// 获取到上传文件和图片的网络地址
						String attachment = (String) bean.get("attachment") == null ? "" : (String) bean.get("attachment");
						String pic = (String) bean.get("pic") == null ? "" : (String) bean.get("pic");
						// map.put("attachment", attachment);
						// map.put("pic", pic);
						// // 把文件的网络地址转为文件名称并放入bean中
						// map.put("attachment_name",
						// PathUtil.url2FileName(attachment));
						// map.put("pic_name", PathUtil.url2FileName(pic));

						list.add(map);
					}

					resMap.put("code", 0);
					resMap.put("msg", "查询成功");
					resMap.put("orderDetailList", list);
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				resMap.put("code", 1102);
				resMap.put("msg", "订单ID填写错误");
			}
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 
	 * @Title: getOrderInfo
	 * @Description: TODO获取订单详情接口，根据订单ID以获取 错误编号12打头
	 * 
	 * @author 张华
	 */
	public void getOrderInfo() {
		String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单ID
		int orderId = 0;
		Map resMap = new HashMap();// 返回结果

		if (StringUtils.isBlank(orderIdStr)) {
			resMap.put("code", 1201);
			resMap.put("msg", "订单ID不能为空");
		} else {
			try {
				orderId = Integer.parseInt(orderIdStr);
				OaOrder oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
				if (null == oaOrder || null == oaOrder.getId()) {
					resMap.put("code", 1203);
					resMap.put("msg", "订单ID不存在");
				} else {
					// 把品类code换成value
					if (null != oaOrder.getClothClass() && !"".equals(oaOrder.getClothClass())) {
						List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
						for (Map<String, String> map : cloth_class_list) {
							if (map.get("code").equals(oaOrder.getClothClass())) {
								oaOrder.setClothClass(map.get("value"));
								break;
							}
						}
					} else {
						oaOrder.setClothClass("基础类");
					}

					resMap.put("code", 0);
					resMap.put("msg", "查询成功");
					resMap.put("order", oaOrder);
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				resMap.put("code", 1202);
				resMap.put("msg", "订单ID填写错误");
			}
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 
	 * @Title: getOrderTypeList
	 * @Description: TODO获取订单品类数据 错误编号13打头
	 * 
	 * @author 张华
	 */
	public void getOrderTypeList() {
		Map resMap = new HashMap();// 返回结果
		try {
			List<Map<String, String>> orderTypeList = XbdBuffer.getOaDtList("1");
			resMap.put("code", 0);
			resMap.put("msg", "查询成功");
			resMap.put("typeList", orderTypeList);
		} catch (Exception e) {
			// TODO: handle exception
			resMap.put("code", 1301);
			resMap.put("msg", "获取订单品类失败");
		}
		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 
	 * @Title: addOrder
	 * @Description: TODO新增订单 四种订单类型：模拟报价、样衣打版、大货生产、售后服务 错误编号14打头
	 * 
	 * @author 张华
	 * @return
	 */
	public void addOrder() {
		Map resMap = new HashMap();// 返回结果
		try {
			String typeString = Struts2Utils.getParameter("type"); // 订单类型
			String salesString = Struts2Utils.getParameter("sales");// 销售／业务担当
			// String operator = Struts2Utils.getParameter("operator");// 操作人
			String operator = salesString;// 操作人
			OaOrder oaOrder = new OaOrder();// 订单对象
			String processDifinitionKey = "";
			// operator = StringUtils.isBlank(operator) ? salesString :
			// operator;

			// 参数判断验证，成功添加并启动流程，失败则返回错误代码
			if (validateOrder(oaOrder, resMap, processDifinitionKey)) {
				switch (typeString) {
				case "1":
					processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS1;
					break;
				case "2":
					processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS2;
					break;
				case "3":
					processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS3;
					break;
				}
				Timestamp t = BizUtil.getOperatingTime(new Timestamp(System.currentTimeMillis()));
				oaOrder.setBeginTime(t);
				oaOrder.setTimeRate(1.0f);
				// 不需要计算折算率
				// oaOrder.setTimeRate(BizUtil.computeTimeRate(oaOrder.getBeginTime(),
				// oaOrder.getExceptFinish(), processDifinitionKey,
				// oaOrder.getClothClass()));
				oaOrder.setStatus("0");
				saveObject(oaOrder);
				// TODO 保存之后会有 orderid 可以提醒下一流程操作人
				BizUtil.startOrderWf(processDifinitionKey, operator, oaOrder);// 启动工作流
				// notifyNextWorkflow(oaOrder.getId()); 不在发送短信提醒
				resMap.put("code", 0);
				resMap.put("msg", "启动流程任务成功");
			}
		} catch (Exception e) {
			// TODO: handle exception
			resMap.put("code", 1401);
			resMap.put("msg", "启动流程任务失败");
		}
		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 
	 * @Title: validateOrder
	 * @Description: TODO验证添加订单接口传过来的参数
	 * 
	 * @author 张华
	 * @param oaOrder
	 * @return
	 */
	private static boolean validateOrder(OaOrder oaOrder, Map resMap, String processDifinitionKey) {
		String typeString = Struts2Utils.getParameter("type"); // 订单类型
		String isUrgentString = Struts2Utils.getParameter("isUrgent");// 是否加急
		// String oaOrderString = Struts2Utils.getParameter("oaOrder");// 关联订单
		String cusCodeString = Struts2Utils.getParameter("cusCode");// 客户编号
		String salesString = Struts2Utils.getParameter("sales");// 销售／业务担当
		String orderCodeString = Struts2Utils.getParameter("orderCode");// 订单编号
		String contractAmountString = Struts2Utils.getParameter("contractAmount");
		String styleCodeString = Struts2Utils.getParameter("styleCode");// 款号
		String telString = Struts2Utils.getParameter("tel");// 客户手机
		String clothClassString = Struts2Utils.getParameter("clothClass");// 品类
		String cityString = Struts2Utils.getParameter("city");// 所属地区
		String priceMinString = Struts2Utils.getParameter("priceMin");// 价格区间，最低价格
		String priceMaxString = Struts2Utils.getParameter("priceMax");// 价格区间，最高价格
		String wantCntString = Struts2Utils.getParameter("wantCnt");// 订单数量
		String exceptFinishString = Struts2Utils.getParameter("exceptFinish");// 预计交期
		String payTypeString = Struts2Utils.getParameter("payType");// 付款方式
		String styleUrlString = Struts2Utils.getParameter("styleUrl");// 样式图片
		String fileUrlString = Struts2Utils.getParameter("fileUrl");// 上传文件
		String picUrlString = Struts2Utils.getParameter("picUrl");// 上传图片
		String memosString = Struts2Utils.getParameter("memo");// 合同条款及备注
		// String operator = Struts2Utils.getParameter("operator");// 操作人

		// 订单类型判断
		if (StringUtils.isBlank(typeString)) {
			resMap.put("code", 1402);
			resMap.put("msg", "订单类型不能为空");
			return false;
		}
		switch (typeString) {
		case "1":
			break;
		case "2":
			break;
		case "3":
			break;
		default:
			resMap.put("code", 1403);
			resMap.put("msg", "根据订单类型:" + typeString + "没有找到相关工作流程定义");
			return false;
		}
		isUrgentString = "0".equals(isUrgentString) ? "0" : "1"; // 0为加急，1为正常
		// 客户编号
		if (StringUtils.isBlank(cusCodeString)) {
			resMap.put("code", 1404);
			resMap.put("msg", "客户编号不能为空");
			return false;
		}
		// 销售／业务担当
		if (StringUtils.isBlank(salesString)) {
			resMap.put("code", 1405);
			resMap.put("msg", "销售／业务担当不能为空");
			return false;
		}
		// 款号
		if (StringUtils.isBlank(styleCodeString)) {
			resMap.put("code", 1406);
			resMap.put("msg", "订单款号不能为空");
			return false;
		}
		styleCodeString = styleCodeString.replaceAll(" ", "_");// 分隔符替换
		// 品类
		if (StringUtils.isBlank(clothClassString)) {
			resMap.put("code", 1407);
			resMap.put("msg", "订单品类不能为空");
			return false;
		}
		// 所属地区
		cityString = "杭州".equals(cityString) ? "杭州" : "广州";
		// 最低价格和最高价格
		Float priceMin = 0f, priceMax = 0f;
		if ("1".equals(typeString) || "2".equals(typeString)) { // 订单为模拟报价或样衣打版才需要验证最低和最高价格
			if (StringUtils.isBlank(priceMinString)) {
				resMap.put("code", 1408);
				resMap.put("msg", "订单最低价格不能为空");
				return false;
			}
			if (StringUtils.isBlank(priceMaxString)) {
				resMap.put("code", 1409);
				resMap.put("msg", "订单最高价格不能为空");
				return false;
			}
			try {
				priceMin = Float.parseFloat(priceMinString);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				resMap.put("code", 1410);
				resMap.put("msg", "订单最低价格错误");
				return false;
			}
			try {
				priceMax = Float.parseFloat(priceMaxString);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				resMap.put("code", 1411);
				resMap.put("msg", "订单最高价格错误");
				return false;
			}
		}
		// 订单数量
		if (StringUtils.isBlank(wantCntString)) {
			resMap.put("code", 1412);
			resMap.put("msg", "订单数量不能为空");
			return false;
		}
		try {
			Integer.parseInt(wantCntString);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			resMap.put("code", 1413);
			resMap.put("msg", "订单数量错误");
			return false;
		}
		// 预计交期
		Timestamp exceptFinish = null;
		if (StringUtils.isBlank(exceptFinishString)) {
			resMap.put("code", 1414);
			resMap.put("msg", "预计交期不能为空");
			return false;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			exceptFinish = new Timestamp(df.parse(exceptFinishString).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			resMap.put("code", 1415);
			resMap.put("msg", "预计交期错误");
			return false;
		}
		// 样式图片
		styleUrlString = (null == styleUrlString) ? "" : styleUrlString;
		// 上传文件
		fileUrlString = (null == fileUrlString) ? "" : fileUrlString;
		// 上传图片
		picUrlString = (null == picUrlString) ? "" : picUrlString;

		// 合同金额
		Float contractAmount = 0f;
		if ("2".equals(typeString) || "3".equals(typeString)) { // 订单为样衣打版或大货生产才需要验证合同金额
			// 订单是大货生产，合同金额为必填
			if ("3".equals(typeString)) {
				if (StringUtils.isBlank(contractAmountString)) {
					resMap.put("code", 1416);
					resMap.put("msg", "合同金额不能为空");
					return false;
				}
			} else { // 样衣打版时，合同金额为空是转换为0
				contractAmountString = StringUtils.isBlank(contractAmountString) ? "0" : contractAmountString;
			}
			try {
				contractAmount = Float.parseFloat(contractAmountString);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				resMap.put("code", 1417);
				resMap.put("msg", "合同金额错误");
				return false;
			}
		}

		oaOrder.setType(typeString);
		oaOrder.setIsUrgent(isUrgentString);
		// oaOrder.setOaOrder(oaOrderString); //关联订单暂时不需要
		oaOrder.setCusCode(cusCodeString);
		oaOrder.setSales(salesString);
		oaOrder.setOrderCode(orderCodeString);
		oaOrder.setStyleCode(styleCodeString);
		oaOrder.setTel(telString);
		oaOrder.setClothClass(clothClassString);
		oaOrder.setCity(cityString);
		oaOrder.setPriceMin(priceMin);
		oaOrder.setPriceMax(priceMax);
		oaOrder.setWantCnt(Integer.parseInt(wantCntString));
		oaOrder.setExceptFinish(exceptFinish);
		oaOrder.setPayType(payTypeString);
		oaOrder.setStyleUrl(styleUrlString);
		oaOrder.setFileUrl(fileUrlString);
		oaOrder.setPicUrl(picUrlString);
		oaOrder.setMemo(memosString);
		// oaOrder.setOperator(operator);
		oaOrder.setContractAmount(contractAmount);

		return true;
	}

	/**
	 * 
	 * @Title: minusTimeAndOffset
	 * @Description: TODO针对已完成的订单，计算当前节点计划完成时间
	 * 
	 * @author 张华
	 * @param realFinishTime
	 *            实际结束时间
	 * @param timestamp
	 * @param offset
	 *            节点时长
	 * @return
	 */
	private static String minusTimeAndOffset(Timestamp realFinishTime, Timestamp timestamp, long offset) {
		StringBuffer buf = new StringBuffer();
		long diff = offset - BizUtil.getWorkTimeBetween(realFinishTime, timestamp);
		if (diff < 0) {
			buf.append("超时");
		} else {
			buf.append("剩余");
		}
		long l = Math.abs(diff);
		buf.append(WebUtil.getTimeDisPlay(l));
		return buf.toString();
	}

	/**
	 * 
	 * @Title: minusTime
	 * @Description: TODO针对已完成的订单，计算计划剩余时间
	 * 
	 * @author 张华
	 * @param realFinishTime
	 *            实际结束时间
	 * @param timestamp
	 *            期望完成时间
	 * @return
	 */
	private static String minusTime(Timestamp realFinishTime, Timestamp timestamp) {
		StringBuffer buf = new StringBuffer();
		long diff = BizUtil.getWorkTimeBetween(realFinishTime, timestamp);
		if (diff > 0) {
			buf.append("超时");
		} else {
			buf.append("剩余");
		}
		long l = Math.abs(diff);
		buf.append(WebUtil.getTimeDisPlay(l));
		return buf.toString();
	}

	/******************* NPS所需接口结束 ********************/

	/******************* CRM所需接口开始 ********************/
	public void getMrStaff() {
		Map resMap = new HashMap();// 返回结果
		List<LazyDynaMap> beans = XbdBuffer.getStaffsByGroupName(XbdBuffer.getOrgNameById(5));
		List reList = new ArrayList();
		for (LazyDynaMap bean : beans) {
			Map map = new HashMap();
			map.put("id", bean.get("id"));
			map.put("loginName", bean.get("login_name"));
			reList.add(map);
		}
		resMap.put("code", "0");
		resMap.put("msg", "获取mr成功");
		resMap.put("resList", reList);
		Struts2Utils.renderJson(resMap);
	}

	/***
	 * 错误从2001开始
	 * 
	 * @author yunpeng 保存crm中的数据到order表和orderNumber表中
	 */
	public void saveOaOrderAndOaOrderNumber() {
		String md5 = Struts2Utils.getParameter("md5");// 获得订单的密文
		String sellOrderCode = Struts2Utils.getParameter("oaOrder.sellOrderCode");// 获得订单的编号
		String sellOrderId = Struts2Utils.getParameter("oaOrder.sellOrderId");// 获得订单的ID

		Map resMap = new HashMap<>();
		if (StringUtils.isBlank(sellOrderId)) {
			resMap.put("code", 2001);
			resMap.put("msg", "订单ID不能为空！");
		} else {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, ExtDaoImpl.GET_OA_ORDER_BY_SELL_ORDER_CODE);
			fsp.set("sellOrderId", sellOrderId);
			bean = manager.getOnlyObjectBySql(fsp);
			if (null != bean) {
				resMap.put("code", 2002);
				resMap.put("msg", "该订单已经存在！");
			} else {
				if (codeCheck(sellOrderCode, md5, resMap)) {
					try {
						String processDifinitionKey = "";
						if (!"2".equals(oaOrder.getType()) && !"3".equals(oaOrder.getType())) {
							resMap.put("code", 2004);
							resMap.put("msg", "根据订单类型:" + oaOrder.getType() + "没有找到相关工作流程定义");
						} else {
							switch (oaOrder.getType()) {
							case "2":
								processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS2;
								break;
							case "3":
								processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS3;
								break;
							}
							manager.saveObject(oaOrderNum);
							int orderNum = oaOrderNum.getId();
							oaOrder.setOaOrderNumId(orderNum);
							// update by 张华 2014-12-24
							if (StringUtils.isEmpty(oaOrder.getStyleUrl()) || "null".equals(oaOrder.getStyleUrl())) {
								oaOrder.setStyleUrl(null);
							}
							if (StringUtils.isEmpty(oaOrder.getPicUrl()) || "null".equals(oaOrder.getPicUrl())) {
								oaOrder.setPicUrl(null);
							}
							if (StringUtils.isEmpty(oaOrder.getFileUrl()) || "null".equals(oaOrder.getFileUrl())) {
								oaOrder.setFileUrl(null);
							}
							oaOrder.setStatus("0");
							oaOrder.setTimeRate(1.0f);// 不需要计算折算率

							// update by 张华 2015-03-13
							// 设置默认销售准备时间9小时，标准缓冲时间（打版默认缓冲时间：38小时，大货默认缓冲时间：117小时），特殊工艺时间0。此处时间都指为工作时间
							// 暂时设置品类（原一级分类）为空，crm同步一级分类后再进行同步
							oaOrder.setStyleClass(null);
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 18:00:00");
							oaOrder.setGoodsTime(new Timestamp(df.parse(df.format(oaOrder.getExceptFinish())).getTime()));
							oaOrder.setSellReadyTime(9 * 60 * 60 * 1000L);
							oaOrder.setCraftTime(0L);

							// 判断产前版是否存在，并进行数据格式化
							if ("1".equals(oaOrder.getIsPreproduct()) && null == oaOrder.getPreproductDays() && 0 == oaOrder.getPreproductDays()) { // 需要产前版
								oaOrder.setPreproductDays(1);
							} else { // 不需要产前版
								oaOrder.setPreVersionDate(null);
								oaOrder.setPreproductDays(null);
							}

							if ("2".equals(oaOrder.getType())) {
								oaOrder.setStandardTime(38 * 60 * 60 * 1000L);
							} else {
								oaOrder.setStandardTime(117 * 60 * 60 * 1000L);
							}
							manager.saveObject(oaOrder);

							// TODO 保存之后会有 orderid 可以提醒下一流程操作人
							BizUtil.startOrderWf(processDifinitionKey, oaOrder.getSales(), oaOrder);// 启动工作流、
							resMap.put("code", 0);
							resMap.put("msg", "保存数据成功！");
						}
					} catch (Exception e) {
						e.printStackTrace();
						resMap.put("code", 2005);
						resMap.put("msg", "保存数据失败！");
					}
				}
			}
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 验证订单的密文是否正确
	 * 
	 * @param code
	 * @param md5
	 * @param resMap
	 * @return
	 */
	public boolean codeCheck(String code, String md5, Map resMap) {
		String miyaoString = "xbd88888888";// 验值
		String codeCheck = getMD5Str(getMD5Str(miyaoString) + code);
		if (!md5.equals(codeCheck)) {
			resMap.put("code", 2003);
			resMap.put("msg", "订单密文不正确！");
			return false;
		} else {
			return true;
		}
	}

	/***
	 * 订单加密方法
	 * 
	 * @author yunpeng
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * CRM获取品类
	 * 
	 * @author bzt
	 * @return
	 */
	public void getCategory() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, ExtDaoImpl.GET_CATEGORY_BY_SQL);
		List<LazyDynaMap> beans = manager.getObjectsBySql(fsp);
		String result = "";
		try {
			if (null != beans && beans.size() > 0) {
				result = JSONArray.fromObject(beans).toString();
				// System.out.println(result);
				Struts2Utils.renderText(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Struts2Utils.renderText("no");
		}
	}

	/******************* CRM所需接口结束 ********************/

	public OaOrder getOaOrder() {
		return oaOrder;
	}

	public void setOaOrder(OaOrder oaOrder) {
		this.oaOrder = oaOrder;
	}

	public OaOrderNum getOaOrderNum() {
		return oaOrderNum;
	}

	public void setOaOrderNum(OaOrderNum oaOrderNum) {
		this.oaOrderNum = oaOrderNum;
	}

	public LazyDynaMap getBean() {
		return bean;
	}

	public void setBean(LazyDynaMap bean) {
		this.bean = bean;
	}
}
