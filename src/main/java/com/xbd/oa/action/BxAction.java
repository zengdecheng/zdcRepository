package com.xbd.oa.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import jxl.write.WriteException;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.use.base.FSPBean;
import org.use.base.action.Action;
import org.use.base.annotation.EJB;
import org.use.base.annotation.FSP;
import org.use.base.annotation.helper.AnnoConst;
import org.use.base.manager.Manager;
import org.use.base.utils.base.DateUtils;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.dao.impl.BxDaoImpl;
//import com.xbd.oa.servlet.AutoNotifyServlet_stop;
import com.xbd.oa.utils.BizUtil;
import com.xbd.oa.utils.CommonSort;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.utils.ExcelUtils;
import com.xbd.oa.utils.JdbcHelper;
import com.xbd.oa.utils.MailUtil;
import com.xbd.oa.utils.POIUtils;
import com.xbd.oa.utils.POIUtilsEx;
import com.xbd.oa.utils.PathUtil;
import com.xbd.oa.utils.ResourceUtil;
import com.xbd.oa.utils.SendWeChatMsg;
import com.xbd.oa.utils.Struts2Utils;
import com.xbd.oa.utils.WebUtil;
import com.xbd.oa.utils.WorkFlowUtil;
import com.xbd.oa.utils.XbdBuffer;
import com.xbd.oa.vo.CustomCell;
import com.xbd.oa.vo.OaClothesSize;
import com.xbd.oa.vo.OaClothesSizeDetail;
import com.xbd.oa.vo.OaCost;
import com.xbd.oa.vo.OaCqc;
import com.xbd.oa.vo.OaCusMaterialList;
import com.xbd.oa.vo.OaDaBanInfo;
import com.xbd.oa.vo.OaDaHuoInfo;
import com.xbd.oa.vo.OaDt;
import com.xbd.oa.vo.OaLogistics;
import com.xbd.oa.vo.OaMaterialList;
import com.xbd.oa.vo.OaMrConfirm;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderDetail;
import com.xbd.oa.vo.OaOrderNum;
import com.xbd.oa.vo.OaOrg;
import com.xbd.oa.vo.OaProcessExplain;
import com.xbd.oa.vo.OaQa;
import com.xbd.oa.vo.OaQiTao;
import com.xbd.oa.vo.OaQiTaoDetail;
import com.xbd.oa.vo.OaStaff;
import com.xbd.oa.vo.OaTimebase;
import com.xbd.oa.vo.OaTimebaseEntry;
import com.xbd.oa.vo.OaTpe;
import com.xbd.oa.vo.OaTracke;


public class BxAction extends Action {

	public String getWhb() {
		return whb;
	}

	public void setWhb(String whb) {
		this.whb = whb;
	}

	private static final long serialVersionUID = 1095934307773047305L;
	public static final Logger logger = Logger.getLogger(BxAction.class);
	private static DecimalFormat decimalFormat=new DecimalFormat("0.##");
	private static DecimalFormat decimalFormat3=new DecimalFormat("0.###");
	@EJB(name = "com.xbd.oa.business.impl.BxManagerImpl")
	private BaseManager manager;
	private String nickName;
	private OaStaff oaStaff;
	private String searchName;
	private List<LazyDynaMap> beans;
	// 新增订单
	private OaOrder oaOrder;
	private String whb;

	//OA请求CRM密钥
	private String miyaoString="63933DCBD897B1441745062495B9652D";
	
	private String orderColor;

	/**
	 * 表单元素组合的对象
	 */
	private LazyDynaMap bean = new LazyDynaMap();

	private OaOrderDetail oaOrderDetail;

	/**
	 * 订单所属部门
	 */
	private String group;

	/**
	 * json返回数据
	 */
	private String result;

	/**
	 * 选择tree节点的id
	 */
	private String treeNode;

	/**
	 * 员工id
	 */
	private String oaStaffId;

	/**
	 * 登录账号
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 旺旺
	 */
	private String linkww;

	/**
	 * 手机号
	 */
	private String linkphone;
	/**
	 * 员工所属角色id
	 */
	private Integer oaRole;

	/**
	 * 员工所属组织单元id
	 */
	private String oaOrgId;

	/**
	 * 基准时间对象
	 */
	private OaTimebase oaTimebase;

	/**
	 * 基准时间集合
	 */
	private List<OaTimebase> oaTimebases = new ArrayList<OaTimebase>();

	/**
	 * 基准时间细节的集合
	 */
	private List<OaTimebaseEntry> oaTimebaseEntries = new ArrayList<OaTimebaseEntry>();

	/**
	 * 修改个人密码的旧密码
	 */
	private String oldPwd;

	/**
	 * 修改个人密码的新密码
	 */
	private String newPwd;

	/**
	 * 品类的集合
	 */
	private List<OaDt> oaDts = new ArrayList<OaDt>();

	/**
	 * 品类
	 */
	private OaDt oaDt;

	/**
	 * 部门订单统计
	 */
	private List<LazyDynaMap> stepCount;

	/**
	 * 用料搭配信息
	 */
	private List<OaMaterialList> oaMaterialLists;

	/**
	 * 保存CQC信息
	 */
	private List<OaCqc> oaCqcLists;

	/**
	 * 保存齐套信息
	 */
	private OaQiTao oaQiTao;

	private OaQa oaQa;

	/**
	 * 保存齐套详细信息
	 */
	private List<OaQiTaoDetail> oaQiTaoDetails;

	/**
	 * 要删除的用料搭配信息id集合
	 */
	private String materialDelIds;
	/**
	 * 客供料信息
	 */
	private List<OaCusMaterialList> oaCusMaterialLists;
	/**
	 * 要删除的客供料信息id集合
	 */
	private String cusMaterialDelIds;

	private String systemEnvironment = XbdBuffer.getClothesSizesByPosition("systemEnvironment");
	/**
	 * 保存大货用料说明
	 */
	private List<OaDaHuoInfo> oaDaHuoInfos;
	/**
	 * 保存款式码数详细信息
	 */
	private List<OaClothesSizeDetail> oaClothesSizeDetails;
	/**
	 * 保存加工信息
	 */
	private OaProcessExplain oaProcessExplain;
	/**
	 * 保存异动跟踪信息
	 */
	private OaTracke oaTracke;
	/**
	 * 保存打版用料信息
	 */
	private List<OaDaBanInfo> oaDaBanInfos;

	/**
	 * 关联订单集合
	 */
	private List<OaOrder> relationOrderList;
	/**
	 * 尺寸表表头
	 */
	private OaClothesSize oaClothesSize;
	/**
	 * 尺寸表详情删除的信息
	 */
	private String clothesSizeDetailDelIds;
	/**
	 * 面辅料采购清单删除的信息
	 */
	private String materialDescDelIds;
	/**
	 * 打板成本信息
	 */
	private OaCost oaCost;
	/**
	 * MR确认信息
	 */
	private OaMrConfirm oaMrConfirm;

	private  String delQiTaoDetails;

	private String oaOrderDetail_id;

	/**
	 * tpe车缝信息
	 */
	private OaTpe oaTpe;

	/**
	 * 物流信息
	 */
	private OaLogistics oaLogistics;
	private String logCheckBoxVal;

	// 上传图片
	private File file;
	private String fileFileName;

	@Override
	public Manager getBiz() {
		return manager;
	}

	/**
	 *
	 * <B>辛巴达员工登录</B><br>
	 * 功能详细描述
	 *
	 * @author hongliang [返回类型说明]
	 * @see [相关类或方法]
	 */
	public void login() {
		try {
			final String loginName = Struts2Utils.getParameter("loginName");
			final String password = Struts2Utils.getParameter("password");

			if (null == loginName || null == password) {
				Struts2Utils.renderText("error");
				return;
			}

			final FSPBean fspTemp = new FSPBean();
			fspTemp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LOGIN);
			fspTemp.set("loginName", loginName);
			fspTemp.set("password", password);

			OaStaff oaStaff = (OaStaff) manager.getOnlyObjectByEql(fspTemp);
			if (null == oaStaff) {
				Struts2Utils.renderText("error");
				return;
			}
			// 判断是否为部门主管
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.IF_MANAGER);
			fsp.set("name", oaStaff.getLoginName());
			OaOrg oo = (OaOrg) manager.getOnlyObjectByEql(fsp);
			if (oo != null) {
				WebUtil.setManager(oo);
			}
			logger.warn(oaStaff.getLoginName() + " 于 " + DateUtil.formatDate(new Date(), DateUtil.ALL_24H) + " 登陆系统");
			WebUtil.setCurrentLoginBx(oaStaff);
			Struts2Utils.renderText("success");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 *
	 * <B>退出</B><br>
	 * 功能详细描述
	 *
	 * @author hongliang
	 * @return [返回类型说明]
	 * @see [相关类或方法]
	 */
	public String logout() {
		Cookie[] cookies = Struts2Utils.getRequest().getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				Struts2Utils.getResponse().addCookie(cookie);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("清空Cookies发生异常！");
		}

		Struts2Utils.getSession().invalidate();
		return "logout";
	}

	@SuppressWarnings("unchecked")
	public String todo() throws ParseException {
		String orderField = Struts2Utils.getParameter("orderField");
		Boolean orderType = Boolean.parseBoolean(Struts2Utils.getParameter("orderType"));
		if (WebUtil.ifManager()) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORGWF_BY_SQL);
			fsp.set("org", WebUtil.getCurrentLoginBx().getOaOrg());
		} else {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_BY_SQL);
			fsp.set("operator", WebUtil.getCurrentLoginBx().getLoginName());
		}
		fsp.set(FSPBean.FSP_ORDER, " order by oo.begin_time desc");
		beans = getObjectsBySql(fsp);
		//统计值[生产总数][黑色订单][红色订单][黄色订单][绿色订单][蓝色订单][总数]
		Integer[] counts = {0,0,0,0,0,0,beans.size()};
		//优先级计算
		for(LazyDynaMap bean:beans){
			counts[0] += (Integer)bean.get("want_cnt");
			//订单周期
			Long sellReadyTime = bean.get("sell_ready_time")==null?0:(Long) bean.get("sell_ready_time");
			Long standardTime = bean.get("standard_time")==null?0:(Long) bean.get("standard_time");
			Long craftTime = bean.get("craft_time")==null?0:(Long) bean.get("craft_time");
			Long orderTime = (sellReadyTime+standardTime+craftTime)/9*24;
			//交期
			Timestamp goodsTime = (Timestamp) bean.get("goods_time");
			//当前工作时间
			Timestamp workTime = BizUtil.getOperatingTime(new Timestamp(new Date().getTime()));
			Integer persent = (int) ((workTime.getTime()-goodsTime.getTime()+orderTime - 60*60*1000*24d)/orderTime*100);
			if(persent>100){
				counts[1] += 1;
			}else if(persent>=66){
				counts[2] += 1;
			}else if(persent>=33){
				counts[3] += 1;
			}else if(persent>=0){
				counts[4] += 1;
			}else if(persent<0){
				counts[5] += 1;
			}
			bean.set("data", persent);
		}
		bean.set("counts", counts);
		CommonSort<LazyDynaMap> cs = new CommonSort<LazyDynaMap>();
		if(StringUtils.isBlank(orderField)){
			orderType = false;
			orderField = "data";
		}
		cs.ListPropertySort(beans, orderField, orderType);
		bean.set("orderType", orderType);
		bean.set("orderField", orderField);
		fsp.setRecordCount(beans.size());
		beans = beans.subList((fsp.getPageNo()-1)*fsp.getPageSize(), (fsp.getPageNo()-1)*fsp.getPageSize()+(int)fsp.getRecordCount()%fsp.getPageSize());
		processPageInfo(getObjectsCountSql(fsp));
		return "todo";
	}
	
	@FSP(hasBack = AnnoConst.HAS_BACK_YES)
	public String order_list() {
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by oo.begin_time desc");
		if (fsp.get("outtime") != null && "1".equals((String) fsp.get("outtime"))) {
			fsp.setPageSize(100);
		}
		if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
			fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
		}

		superList = getObjectsBySql(fsp);
		if (fsp.get("outtime") != null && "1".equals((String) fsp.get("outtime"))) {
			List<LazyDynaMap> list = new ArrayList<LazyDynaMap>();
			for (int i = 0; i < superList.size(); i++) {
				LazyDynaMap bean = superList.get(i);
				long wsd = (long) bean.get("wf_step_duration");
				long diff = wsd - BizUtil.getWorkTimeBetween(new Timestamp(System.currentTimeMillis()), (Timestamp) bean.get("wf_real_start"));
				if (diff < 0) {
					// 超时
					list.add(bean);
					superList.remove(i);
					i--;
				} else {
					// 未超时

				}
				// 所有超时订单加入list,移除superList中的超时项
			}
			// list.addAll(superList);
			superList = list;// 仅超时订单
		}
		processPageInfo(getObjectsCountSql(fsp));

		fsp.set("org_name", XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()));
		return "order_list";
	}


	/**
	 * 导出历史订单分析报表
	 */
	public String outExcel(){
		//导出excel基本参数设置
		Map<String,Object> fillInfo = new HashMap<String,Object>();

        String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseReportExcelFile"));
        String sheetNames = "历史订单分析报表";
        fillInfo.put("fileUrl", baseExcelFile);
        fillInfo.put("sheetNames", sheetNames);

		//查询导出数据
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_HIS_BY_SQL);
		if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
			fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
		}
		if(!"true".equals(Struts2Utils.getParameter("hidStatusFlag"))){
			fsp.set("status", "0"); //默认为正常的订单
			fsp.set("wf_step", "finish_999"); //默认为已完成
		} else {
			if ("0".equals((String)fsp.get("status"))) { //已完成
				fsp.set("wf_step", "finish_999");
			} else if("1".equals((String)fsp.get("status"))) { //作废
				fsp.set("wf_step", null);
			} else { //全部
				fsp.set("if_all", "finish_999");
			}
		}
		//判断查询数据类型，大货与打版流程不一样  by 范蠡
		if(fsp.getMap().get("type") == null || "".equals(fsp.getMap().get("type"))){
			if(fsp.getMap().get("del_operator") != null && !"".equals(fsp.getMap().get("del_operator"))){
				fsp.set("del_operator_all", "c_ppc_assign_3");
			}
		}else{
			if(fsp.getMap().get("type") != null && "2".equals(fsp.getMap().get("type"))){
				fsp.set("del_operator_b", "b_pur_confirm_4");
			}else if(fsp.getMap().get("type") != null && "3".equals(fsp.getMap().get("type"))){
				fsp.set("del_operator_c", "c_ppc_assign_3");
			}
		}

		superList = getObjectsBySql(fsp);
		//声明excel主体数据Map容器
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		int i = 0;
		int j = 1;
		for(LazyDynaMap lazyMap : superList){
			//bean = superList.get(i);
			Long nowDate = new Date().getTime();
			Long begin_time = ((Date) lazyMap.get("begin_time")).getTime();
			Long except_finish = ((Date) lazyMap.get("except_finish")).getTime();

			//优先级
//			if (0 != begin_time && 0 != except_finish) {
//				float baifenzhu = (nowDate - begin_time + 24 * 60 * 60 * 1000) / (except_finish - begin_time + 24 * 60 * 60 * 1000);
//				float data = baifenzhu * 100;
//				if(data > 0 && data <=33){
//					sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
//				}else if(data > 33 && data <=66){
//					sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.ORANGE.index).setFontColor(IndexedColors.WHITE.index));
//				}else if(data > 66 && data <=100){
//					sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.RED.index).setFontColor(IndexedColors.WHITE.index));
//				}else if(data > 100){
//					sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLACK.index).setFontColor(IndexedColors.WHITE.index));
//				}else{
//					sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
//                }
//			}else{
//				sheet1FileInfo.put(j+",0", 0+"%");
//			}
			sheet1FileInfo.put(j+",0", lazyMap.get("sell_order_code").toString());//订单号
			if(lazyMap.get("type") != null && "2".equals(lazyMap.get("type").toString())){
				sheet1FileInfo.put(j+",1", "样衣打版");//订单类型
                StringBuffer buf = new StringBuffer();
                if(lazyMap.get("bqcdel_wf_real_finish") != null && lazyMap.get("bqcdel_wf_plan_start") != null
                        && lazyMap.get("bqcdel_wf_step_duration") != null){
//                    long diff = ((Date)lazyMap.get("bqcdel_wf_real_finish")).getTime() - ((Date)lazyMap.get("bqcdel_wf_plan_start")).getTime() + (long)lazyMap.get("bqcdel_wf_step_duration");
                    Timestamp jihuaTime = new Timestamp(((Date)lazyMap.get("bqcdel_wf_plan_start")).getTime() + (long)lazyMap.get("bqcdel_wf_step_duration"));
                    long diff = BizUtil.getWorkTimeBetween((Timestamp) lazyMap.get("bqcdel_wf_real_finish"), jihuaTime);
                    if (diff > 0) {
                        buf.append("超时");
                    } else {
                        buf.append("剩余");
                    }
                    buf.append(WebUtil.getTimeDisPlayExcel(Math.abs(diff)));
                    sheet1FileInfo.put(j+",7", buf.toString());//订单进度
                }

                sheet1FileInfo.put(j+",18",lazyMap.get("mrdb_wf_real_start") !=null ? new CustomCell(lazyMap.get("mrdb_wf_real_start"), "Date") : new CustomCell());//MR补录订单日期
                sheet1FileInfo.put(j+",23", lazyMap.get("jsdel_operator") != null ? lazyMap.get("jsdel_operator") : "");//打版技术

                //计算实际生产周期
                if(superList.get(i).get("bqcdel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("bqcdel_wf_real_finish"))
                        && superList.get(i).get("mrdb_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdb_wf_real_start"))){

                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("bqcdel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdb_wf_real_start"));
                    sheet1FileInfo.put(j+",20", WebUtil.getTimeDisPlayExcel(actualTime));//实际生产周期
                }

                if(lazyMap.get("bqcdel_wf_real_finish") != null && !"".equals(lazyMap.get("bqcdel_wf_real_finish"))){

                    Date now = (Date)lazyMap.get("bqcdel_wf_real_finish");
                    Timestamp tamp = new Timestamp(now.getTime());
                    Date beginTime = (Date)lazyMap.get("begin_time");
                    Date exceptFinish = (Date)lazyMap.get("except_finish");
                    double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
                    DecimalFormat df = new DecimalFormat("#");
                    String time_consume = df.format(time);
                    //订单完成时TOC百分比
                    int data =Integer.parseInt(time_consume);
                    if(data > 0 && data <=33){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 33 && data <=66){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.ORANGE.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 66 && data <=100){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.RED.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 100){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLACK.index).setFontColor(IndexedColors.WHITE.index));
                    }else{
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                    }

//                    sheet1FileInfo.put(j+",34", time_consume+"%");
                }

                //计算实际订单周期
                if(superList.get(i).get("bqcdel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("bqcdel_wf_real_finish"))
                        && superList.get(i).get("mrdb_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdb_wf_real_start"))){

                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("bqcdel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdb_wf_real_start"));
                    sheet1FileInfo.put(j+",35", WebUtil.getTimeDisPlayExcel(actualTime));//实际生产周期
                }

				//sheet1FileInfo.put(i+",37", logsCounts);//实际订单周期
			}else if(lazyMap.get("type") != null && "3".equals(lazyMap.get("type").toString())){
				sheet1FileInfo.put(j+",1", "大货生产");//订单类型
                StringBuffer buf = new StringBuffer();
                if(lazyMap.get("wldel_wf_real_finish") != null && lazyMap.get("wldel_wf_plan_start") != null
                        && lazyMap.get("wldel_wf_step_duration") != null){
//                    long diff = ((Date)lazyMap.get("wldel_wf_real_finish")).getTime() - ((Date)lazyMap.get("wldel_wf_plan_start")).getTime() + (long)lazyMap.get("wldel_wf_step_duration");
                    Timestamp jihuaTime = new Timestamp(((Date)lazyMap.get("wldel_wf_plan_start")).getTime() + (long)lazyMap.get("wldel_wf_step_duration"));
                    long diff = BizUtil.getWorkTimeBetween((Timestamp) lazyMap.get("wldel_wf_real_finish"), jihuaTime);
                    if (diff > 0) {
                        buf.append("超时");
                    } else {
                        buf.append("剩余");
                    }
                    buf.append(WebUtil.getTimeDisPlayExcel(Math.abs(diff)));
                    sheet1FileInfo.put(j+",7", buf.toString());//订单进度
                }

                sheet1FileInfo.put(j+",18",lazyMap.get("mrdel_wf_real_start") !=null ? new CustomCell(lazyMap.get("mrdel_wf_real_start"), "Date") : new CustomCell());//MR补录订单日期


                sheet1FileInfo.put(j+",23", lazyMap.get("jshdel_operator") != null ? lazyMap.get("jshdel_operator") : "");//大货技术
                //计算实际生产周期
                if(superList.get(i).get("qadel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("qadel_wf_real_finish"))
                        && superList.get(i).get("mrdel_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdel_wf_real_start"))){

                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("qadel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdel_wf_real_start"));
                    sheet1FileInfo.put(j+",20", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//实际生产周期
                }



                if(lazyMap.get("wldel_wf_real_finish") != null && !"".equals(lazyMap.get("wldel_wf_real_finish"))){

                    Date now = (Date)lazyMap.get("wldel_wf_real_finish");
                    Timestamp tamp = new Timestamp(now.getTime());
                    Date beginTime = (Date)lazyMap.get("begin_time");
                    Date exceptFinish = (Date)lazyMap.get("except_finish");
                    double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
                    DecimalFormat df = new DecimalFormat("#");
                    String time_consume = df.format(time);
                    //订单完成时TOC百分比
                    int data =Integer.parseInt(time_consume);
                    if(data > 0 && data <=33){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 33 && data <=66){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.ORANGE.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 66 && data <=100){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.RED.index).setFontColor(IndexedColors.WHITE.index));
                    }else if(data > 100){
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLACK.index).setFontColor(IndexedColors.WHITE.index));
                    }else{
                        sheet1FileInfo.put(j+",33",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                    }
//                    sheet1FileInfo.put(j+",34", time_consume+"%");
                }

                //计算实际订单周期
                if(superList.get(i).get("wldel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("wldel_wf_real_finish"))
                        && superList.get(i).get("mrdel_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdel_wf_real_start"))){
                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("wldel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdel_wf_real_start"));
                    sheet1FileInfo.put(j+",35", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//实际生产周期
                }
			}
			sheet1FileInfo.put(j+",2", lazyMap.get("style_code").toString());//款号
			sheet1FileInfo.put(j+",3", lazyMap.get("style_desc").toString());//款式描述
			sheet1FileInfo.put(j+",4", lazyMap.get("cus_name").toString());//客户名称
			sheet1FileInfo.put(j+",5", lazyMap.get("want_cnt").toString());//数量
//			sheet1FileInfo.put(j+",7", lazyMap.get("begin_time") !=null ? lazyMap.get("begin_time") : "");//下单日期
            sheet1FileInfo.put(j+",6",lazyMap.get("begin_time") !=null ? new CustomCell(lazyMap.get("begin_time"), "Date") : new CustomCell());




			sheet1FileInfo.put(j+",8", lazyMap.get("mr_name") != null ? lazyMap.get("mr_name") : "");//负责MR
			sheet1FileInfo.put(j+",9", lazyMap.get("sales") != null ? lazyMap.get("sales") : "");//负责销售
			sheet1FileInfo.put(j+",10", lazyMap.get("sewing_factory") != null ? lazyMap.get("sewing_factory") : "");//工厂
			sheet1FileInfo.put(j+",11", lazyMap.get("tpe_name") != null ? lazyMap.get("tpe_name") : "");//TPE
			sheet1FileInfo.put(j+",12", lazyMap.get("sewing_total") != null ? lazyMap.get("sewing_total") : "");//车缝产出数量
			sheet1FileInfo.put(j+",13", lazyMap.get("qualified_total") != null ? lazyMap.get("qualified_total") : "");//合格数量
			sheet1FileInfo.put(j+",14", lazyMap.get("unqualified_total") != null ? lazyMap.get("unqualified_total") : "");//次品数量
			if(lazyMap.get("sewing_total") != null && !"".equals(lazyMap.get("sewing_total")) && lazyMap.get("qualified_total") != null && !"".equals(lazyMap.get("qualified_total"))){
				sheet1FileInfo.put(j+",15", (int)new BigDecimal((Float.parseFloat(lazyMap.get("qualified_total").toString())/Float.parseFloat(lazyMap.get("sewing_total").toString())) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue() + "%");//合格率
			}else{
				sheet1FileInfo.put(j+",15", "");//合格率
			}
            sheet1FileInfo.put(j+",16",lazyMap.get("qadel_wf_real_finish") !=null ? new CustomCell(lazyMap.get("qadel_wf_real_finish"), "Date") : new CustomCell());//QA完成日期

			if(lazyMap.get("qadel_wf_real_finish") != null){
				Date now = (Date)lazyMap.get("qadel_wf_real_finish");
				Timestamp tamp = new Timestamp(now.getTime());
				Date beginTime = (Date)lazyMap.get("begin_time");
				Date exceptFinish = (Date)lazyMap.get("except_finish");
                double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
				DecimalFormat df = new DecimalFormat("#");
				String time_consume = df.format(time);
                //QA完成时TOC百分比
                int data =Integer.parseInt(time_consume);
                if(data > 0 && data <=33){
                    sheet1FileInfo.put(j+",17",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                }else if(data > 33 && data <=66){
                    sheet1FileInfo.put(j+",17",new CustomCell(data+"%", "String").setCellColor(IndexedColors.ORANGE.index).setFontColor(IndexedColors.WHITE.index));
                }else if(data > 66 && data <=100){
                    sheet1FileInfo.put(j+",17",new CustomCell(data+"%", "String").setCellColor(IndexedColors.RED.index).setFontColor(IndexedColors.WHITE.index));
                }else if(data > 100){
                    sheet1FileInfo.put(j+",17",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLACK.index).setFontColor(IndexedColors.WHITE.index));
                }else{
                    sheet1FileInfo.put(j+",17",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
                }

//				sheet1FileInfo.put(j+",18", time_consume+"%");
			}


            sheet1FileInfo.put(j+",19",lazyMap.get("except_finish") !=null ? new CustomCell(lazyMap.get("except_finish"), "Date") : new CustomCell());//合同交期


			sheet1FileInfo.put(j+",21", lazyMap.get("style_craft") != null ? lazyMap.get("style_craft") : "");//特殊工艺
			sheet1FileInfo.put(j+",22", lazyMap.get("fidel_operator") != null ? lazyMap.get("fidel_operator") : "");//采购
			sheet1FileInfo.put(j+",24", lazyMap.get("cqdel_operator") != null ? lazyMap.get("cqdel_operator") : "");//核价
			sheet1FileInfo.put(j+",25", lazyMap.get("cqcdel_operator") != null ? lazyMap.get("cqcdel_operator") : "");//CQC
			sheet1FileInfo.put(j+",26", lazyMap.get("qadel_operator") != null ? lazyMap.get("qadel_operator") : "");//QA
			sheet1FileInfo.put(j+",27", lazyMap.get("style_class") != null ? lazyMap.get("style_class") : "");//一级分类
            /**
             * 从oa_dt表中查询二级分类数据
             */
            FSPBean  dtFsp = new FSPBean();
            dtFsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_DT_BY_CODE_SQL);
            dtFsp.set("code", lazyMap.get("cloth_class"));
            beans = manager.getObjectsBySql(dtFsp);
            for(int n = 0; n < beans.size(); n++){
                sheet1FileInfo.put(j+",28", beans.get(n).get("value") != null ? beans.get(n).get("value") : "");//二级分类
            }
            beans.clear();

			sheet1FileInfo.put(j+",29", lazyMap.get("style_type") != null ? lazyMap.get("style_type") : "");//男女款
			sheet1FileInfo.put(j+",30", lazyMap.get("order_code") != null ? lazyMap.get("order_code") : "");//合同号
			sheet1FileInfo.put(j+",31", lazyMap.get("pay_type") != null ? lazyMap.get("pay_type") : "");//付款方式
            sheet1FileInfo.put(j+",32",lazyMap.get("end_time") !=null ? new CustomCell(lazyMap.get("end_time"), "Date") : new CustomCell());//订单完成日期

			FSPBean  logFsp = new FSPBean();
			logFsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OALOGISTICS_BY_SQL);
			logFsp.set("oaOrderId", lazyMap.get("id"));
			beans = manager.getObjectsBySql(logFsp);
			int logsCounts = 0;
			for(int n = 0; n < beans.size(); n++){
				logsCounts += Double.parseDouble(beans.get(n).get("delivery_num") != null ? beans.get(n).get("delivery_num").toString() : "0");
			}
			sheet1FileInfo.put(j+",34", logsCounts);//发货数量

            //合同周期
            if(superList.get(i).get("except_finish") != null
                    && !"".equals(superList.get(i).get("except_finish"))
                    && superList.get(i).get("begin_time") != null
                    && !"".equals(superList.get(i).get("begin_time"))){

                Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("except_finish"),(Timestamp)superList.get(i).get("begin_time"));
                sheet1FileInfo.put(j+",36", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//合同周期
            }

            //与交期差额
            if(superList.get(i).get("end_time") != null
                    && !"".equals(superList.get(i).get("end_time"))
                    && superList.get(i).get("begin_time") != null
                    && !"".equals(superList.get(i).get("begin_time"))){
                Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("end_time"),(Timestamp)superList.get(i).get("begin_time"));
                sheet1FileInfo.put(j+",37", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//与交期差额
            }

            sheet1FileInfo.put(j+",38", superList.get(i).get("terminate_memo") != null ? superList.get(i).get("mr_if_repeat") : "");//终止原因


            //sheet1FileInfo.put(i+",41", "需要");//终止原因
            if(superList.get(i).get("mr_if_repeat") != null && !superList.get(i).get("mr_if_repeat").toString().equals("")){
                if(superList.get(i).get("mr_if_repeat").toString().equals("0")){
                    sheet1FileInfo.put(j+",39", "需要");//是否需要复版
                }else{
                    sheet1FileInfo.put(j+",39", "不需要");//是否需要复版
                }

            }
            if(superList.get(i).get("mr_if_qualified") != null && !superList.get(i).get("mr_if_qualified").toString().equals("")){
                if(superList.get(i).get("mr_if_qualified").toString().equals("0")){
                    sheet1FileInfo.put(j+",40", "合格");//是否合格
                }else{
                    sheet1FileInfo.put(j+",40", "不合格");//是否合格
                }
            }
            sheet1FileInfo.put(j+",41", superList.get(i).get("p_sam") != null ? superList.get(i).get("p_sam") : "");//核价SAM

			i++;
			j++;
		}
//		sheet1FileInfo.put("0,0,Expression","RAND()");
//		sheet1FileInfo.put("0,1,Expression","RAND()*5");
//		sheet1FileInfo.put("0,2,Expression","RAND()");
//		sheet1FileInfo.put("0,3,Expression","1+6");
//		sheet1FileInfo.put("0,4,Expression","SUM(A1:D1)");
//		sheet1FileInfo.put("1,1,5,5","http://erp.singbada.cn/images/login_pic_1.jpg");
//
//		sheet1FileInfo.put("9,9",new CustomCell(1234.123, "Double-¥#,##0.0"));
//		sheet1FileInfo.put("8,9",new CustomCell(11.6, "Double"));
//		sheet1FileInfo.put("8,8",new CustomCell(new Date(), "Date"));
//		sheet1FileInfo.put("8,7",new CustomCell("蓝背景", "String").setCellColor(IndexedColors.BLUE.index));
//		sheet1FileInfo.put("8,6",new CustomCell("彩边框", "String").setBorderColors(IndexedColors.GREEN.index));
//		sheet1FileInfo.put("8,5",new CustomCell("无边框", "String").setHasBorder(false));
//		sheet1FileInfo.put("8,4",new CustomCell("红粗斜", "String").setFontBoldWeight(Font.BOLDWEIGHT_BOLD).setFontColor(IndexedColors.RED.index).setFontItalic(true));
//		sheet1FileInfo.put("8,3",new CustomCell("楷体", "String").setFontName("楷体"));
//		sheet1FileInfo.put("8,2",new CustomCell("粗体", "String").setFontBoldWeight(Font.BOLDWEIGHT_BOLD));
//		sheet1FileInfo.put("8,1",new CustomCell("红色字体", "String").setFontColor(IndexedColors.RED.index));
//		sheet1FileInfo.put("8,0",new CustomCell("蓝色字体", "String").setFontColor(IndexedColors.BLUE.index));
//		sheet1FileInfo.put("7,9",new CustomCell("自定义字号", "String").setFontSize((short) 9));
//		sheet1FileInfo.put("9,8,Double", 1111111.231D);
//		sheet1FileInfo.put("7,8,Double", 11.231D);
//		sheet1FileInfo.put("7,7,Boolean", false);//
//		sheet1FileInfo.put("7,6,Calendar-yyyy/MM/dd hh", Calendar.getInstance());
//		sheet1FileInfo.put("7,5,Calendar", Calendar.getInstance());//
//		sheet1FileInfo.put("7,4,Date-yy-MM-dd", new Date());
//		sheet1FileInfo.put("7,3,Date", new Date());
//		sheet1FileInfo.put("7,2", new Date());//
//		sheet1FileInfo.put("7,1", 111);//
//		sheet1FileInfo.put("7,0", new CustomCell("自定义格式", "String"));
//		Map<String,Object> sheet1DynaRow = new HashMap<String,Object>();
//		//sheet1DynaRow.put("10", 1);
//		//sheet1DynaRow.put("2", 1);
//		Map<String,Object> sheet1MergeCell = new HashMap<String,Object>();
//		sheet1MergeCell.put("4,4,0,4", true);

		fillInfo.put("历史订单分析报表FileInfo", sheet1FileInfo);
		fillInfo.put("订单进度跟踪报表-大货DynaRow", null);
		fillInfo.put("订单进度跟踪报表-大货MergeCell", null);

//        OutputStream os = null;
        try {
//            os = new FileOutputStream(outputFile);

            OutputStream os = Struts2Utils.getResponse().getOutputStream();
            Struts2Utils.getResponse().setContentType("application/msexcel");//x-msdownload
            Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=ERP-REPORT-"+(System.currentTimeMillis() + "").substring(6, 13) + ".xlsx");
            POIUtilsEx.processExcel(os,fillInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
//        POIUtilsEx.processExcel(os, fillInfo);
        return null;
	}



    /**
     * 导出在制订单分析报表
     */
    public void outExcelZz(){
        //导出excel基本参数设置
        Map<String,Object> fillInfo = new HashMap<String,Object>();
        String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseReportExcelFile"));
        String sheetNames = "在制订单分析报表";
        fillInfo.put("fileUrl", baseExcelFile);
        fillInfo.put("sheetNames", sheetNames);
        //查询导出数据
        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_LIST);
        if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
            fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
        }
        if(fsp.getMap().get("odel_wf_real_start") != null && !"".equals(fsp.getMap().get("odel_wf_real_start"))){
            fsp.set("odel_wf_step", "c_mr_improve_2");
        }
        if(fsp.getMap().get("odelqa_wf_real_start") != null && !"".equals(fsp.getMap().get("odelqa_wf_real_start"))){
            fsp.set("odelqa_wf_step", "c_ppc_confirm_7");
        }
        
        beans = getObjectsBySql(fsp);
        //声明excel主体数据Map容器
        Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
        
        long newTime = BizUtil.getWorkTime(new Timestamp(new Date().getTime())).getTime();
        int cycle = 0;
        
        List<LazyDynaMap> colorList = new ArrayList<LazyDynaMap>();
		
		for(int i = 0; i < beans.size(); i++){
			
			//toc部分
			//订单周期
			Long sellReadyTime = beans.get(i).get("sell_ready_time")==null?0:(Long) beans.get(i).get("sell_ready_time");
			Long standardTime = beans.get(i).get("standard_time") ==null?0:(Long) beans.get(i).get("standard_time");
			Long craftTime = beans.get(i).get("craft_time") ==null?0:(Long) beans.get(i).get("craft_time");
			Long orderTime = (sellReadyTime+standardTime+craftTime)/9*24;
			//交期
			Timestamp goodsTime = (Timestamp) beans.get(i).get("goods_time");
			//当前工作时间
			Timestamp workTime = BizUtil.getOperatingTime(new Timestamp(new Date().getTime()));
			Integer cycle1 = (int) ((workTime.getTime()-goodsTime.getTime()+orderTime - 60*60*1000*24d)/orderTime*100);
			
			beans.get(i).set("data", cycle1);
			if(orderColor != null && !"".equals(orderColor)){
				if("-1".equals(orderColor)){
					if(cycle1 < 0){
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("0".equals(orderColor)){
					if(cycle1 > 0 && cycle1 <= 33){
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("33".equals(orderColor)){
					if(cycle1 > 33 && cycle1 <= 66){
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("66".equals(orderColor)){
					if(cycle1 > 66 && cycle1 <= 100){
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("100".equals(orderColor)){
					if(cycle1 > 100){
						colorList.add(beans.get(i));
					}
					continue;
				}
			}
			
		}
		
		if(orderColor != null && !"".equals(orderColor)){
			beans = colorList;
		}
		
		for(int i = 0; i < beans.size(); i++){
			for (int j = i; j < beans.size(); j++)
            {
                if (Integer.parseInt(beans.get(i).get("data").toString()) < Integer.parseInt(beans.get(j).get("data").toString()))
                {
                	LazyDynaMap temp = beans.get(i);
                	beans.set(i, beans.get(j));
                	beans.set(j, temp);
                }
            }
		}
        
		superList = beans;
        
        int i = 0;
        int j = 1;
        for(LazyDynaMap lazyMap : superList){
            //bean = superList.get(i);
//            Long nowDate = new Date().getTime();
//            Long begin_time = ((Date) lazyMap.get("begin_time")).getTime();
//            Long except_finish = ((Date) lazyMap.get("except_finish")).getTime();
            Date beginTime = (Date)lazyMap.get("begin_time");
            Date exceptFinish = (Date)lazyMap.get("except_finish");

            //优先级
            int data = lazyMap.get("data") != null ? (int)Double.parseDouble(lazyMap.get("data").toString()) : 0;
            if(data > 0 && data <=33){
                sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.GREEN.index).setFontColor(IndexedColors.WHITE.index));
            }else if(data > 33 && data <=66){
                sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.ORANGE.index).setFontColor(IndexedColors.WHITE.index));
            }else if(data > 66 && data <=100){
                sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.RED.index).setFontColor(IndexedColors.WHITE.index));
            }else if(data > 100){
                sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLACK.index).setFontColor(IndexedColors.WHITE.index));
            }else{
                sheet1FileInfo.put(j+",0",new CustomCell(data+"%", "String").setCellColor(IndexedColors.BLUE.index).setFontColor(IndexedColors.WHITE.index));
            }
            sheet1FileInfo.put(j+",1", lazyMap.get("sell_order_code").toString());//订单号


            if(lazyMap.get("type") != null && "2".equals(lazyMap.get("type").toString())){
                sheet1FileInfo.put(j+",2", "样衣打版");//订单类型
            }else if(lazyMap.get("type") != null && "3".equals(lazyMap.get("type").toString())){
                sheet1FileInfo.put(j+",2", "大货生产");//订单类型
            }
            sheet1FileInfo.put(j+",3", lazyMap.get("style_code").toString());//款号
            sheet1FileInfo.put(j+",4", lazyMap.get("style_desc").toString());//款式描述
            sheet1FileInfo.put(j+",5", lazyMap.get("cus_name").toString());//客户名称
            sheet1FileInfo.put(j+",6", lazyMap.get("want_cnt").toString());//数量
            sheet1FileInfo.put(j+",7", lazyMap.get("begin_time") !=null ? new CustomCell(lazyMap.get("begin_time"), "Date") : new CustomCell());//下单日期
//            sheet1FileInfo.put(j+",8", lazyMap.get("style_class") != null ? lazyMap.get("style_class") : "");//品类
            sheet1FileInfo.put(j+",8", lazyMap.get("feeding_time") != null ? lazyMap.get("feeding_time") : "");//建议投料日
            sheet1FileInfo.put(j+",9", lazyMap.get("mr_name") != null ? lazyMap.get("mr_name") : "");//负责MR
            sheet1FileInfo.put(j+",10", lazyMap.get("wf_step_name") != null ? lazyMap.get("wf_step_name") : "");//当前节点
            sheet1FileInfo.put(j+",11", lazyMap.get("operator") != null ? lazyMap.get("operator") : "");//当前负责人
            sheet1FileInfo.put(j+",12", lazyMap.get("sales") != null ? lazyMap.get("sales") : "");//负责销售
            sheet1FileInfo.put(j+",13", lazyMap.get("sewing_factory") != null ? lazyMap.get("sewing_factory") : "");//工厂
            sheet1FileInfo.put(j+",14", lazyMap.get("tpe_name") != null ? lazyMap.get("tpe_name") : "");//TPE
            sheet1FileInfo.put(j+",15", lazyMap.get("sewing_total") != null ? lazyMap.get("sewing_total") : "");//车缝产出数量
            sheet1FileInfo.put(j+",16", lazyMap.get("qualified_total") != null ? lazyMap.get("qualified_total") : "");//合格数量
            sheet1FileInfo.put(j+",17", lazyMap.get("unqualified_total") != null ? lazyMap.get("unqualified_total") : "");//次品数量
            if(lazyMap.get("sewing_total") != null && !"".equals(lazyMap.get("sewing_total")) && lazyMap.get("qualified_total") != null && !"".equals(lazyMap.get("qualified_total"))){
                sheet1FileInfo.put(j+",18", (int)new BigDecimal((Float.parseFloat(lazyMap.get("qualified_total").toString())/Float.parseFloat(lazyMap.get("sewing_total").toString())) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue() + "%");//合格率
            }else{
                sheet1FileInfo.put(j+",18", "");//合格率
            }

//            list_oa_order_detail_by_sql

            Date qaTime = null;
            Date mrTime = null;

            /**
             * 从oa_dt表中查询二级分类数据
             */
            FSPBean detailFsp = new FSPBean();
            detailFsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
            detailFsp.set("oa_order", lazyMap.get("id"));
            beans = manager.getObjectsBySql(detailFsp);
            for(int n = 0; n < beans.size(); n++){
                if(beans.get(n).get("wf_step") != null && "c_ppc_confirm_7".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",19",beans.get(n).get("wf_real_finish") !=null ? new CustomCell(beans.get(n).get("wf_real_finish"), "Date") : new CustomCell());//QA完成日期
                    //QA完成时TOC百分比
                    Date now = (Date)beans.get(n).get("wf_real_finish");
                    if(now != null){
                        Timestamp tamp = new Timestamp(now.getTime());
                        double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
                        DecimalFormat df = new DecimalFormat("#");
                        String time_consume = df.format(time);
                        sheet1FileInfo.put(j+",20", time_consume+"%");
                    }else{
                        sheet1FileInfo.put(j+",20", "");
                    }

                    qaTime = now;
                }

                if(beans.get(n).get("wf_step") != null && "b_mr_improve_2".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",21",beans.get(n).get("wf_real_start") !=null ? new CustomCell(beans.get(n).get("wf_real_start"), "Date") : new CustomCell());//MR补录订单日期-打版
                    mrTime = (Date)beans.get(n).get("wf_real_start");
                }else if(beans.get(n).get("wf_step") != null && "c_mr_improve_2".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",21",beans.get(n).get("wf_real_start") !=null ? new CustomCell(beans.get(n).get("wf_real_start"), "Date") : new CustomCell());//MR补录订单日期-大货
                    mrTime = (Date)beans.get(n).get("wf_real_start");
                }


                if(beans.get(n).get("wf_step") != null && "c_fi_pay_4".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",25", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//大货采购
                }else if(beans.get(n).get("wf_step") != null && "b_ppc_confirm_3".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",25", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//打版采购
                }

                if(beans.get(n).get("wf_step") != null && "c_ppc_assign_3".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",26", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//大货技术
                }else if(beans.get(n).get("wf_step") != null && "b_pur_confirm_4".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",26", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//打版技术
                }

                if(beans.get(n).get("wf_step") != null && "b_ppc_confirm_5".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",27", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//核价
                }

                if(beans.get(n).get("wf_step") != null && "c_ppc_factoryMsg_5".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",28", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//CQC
                }

                if(beans.get(n).get("wf_step") != null && "c_ppc_confirm_7".equals(beans.get(n).get("wf_step").toString())){
                    sheet1FileInfo.put(j+",29", beans.get(n).get("operator") != null ? beans.get(n).get("operator") : "");//CQC
                }

                //实际生产周期
                Long actualTime = null;
                if(qaTime != null && mrTime != null){
                    actualTime = qaTime.getTime() - mrTime.getTime();
                }
                sheet1FileInfo.put(j+",23", actualTime != null ? WebUtil.getTimeDisPlayExcel(actualTime) : "");
//                sheet1FileInfo.put(j+",19",new CustomCell(lazyMap.get("qadel_wf_real_finish") !=null ? lazyMap.get("qadel_wf_real_finish") : new Date(), "Date"));//QA完成日期
            }
            beans.clear();

            sheet1FileInfo.put(j+",22", lazyMap.get("except_finish") !=null ? new CustomCell(lazyMap.get("except_finish"), "Date") : new CustomCell());//合同交期


            sheet1FileInfo.put(j+",24", lazyMap.get("style_craft") != null ? lazyMap.get("style_craft") : "");//特殊工艺


            sheet1FileInfo.put(j+",30", lazyMap.get("style_class") != null ? lazyMap.get("style_class") : "");//一级分类
            /**
             * 从oa_dt表中查询二级分类数据
             */
//            FSPBean  dtFsp = new FSPBean();
//            dtFsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_DT_BY_CODE_SQL);
//            dtFsp.set("code", lazyMap.get("cloth_class"));
//            beans = manager.getObjectsBySql(dtFsp);
//            for(int n = 0; n < beans.size(); n++){
//                sheet1FileInfo.put(j+",31", beans.get(n).get("value") != null ? beans.get(n).get("value") : "");//二级分类
//            }
//            beans.clear();
            sheet1FileInfo.put(j+",31", lazyMap.get("value") != null ? lazyMap.get("value") : "");//二级分类

            sheet1FileInfo.put(j+",32", lazyMap.get("style_type") != null ? lazyMap.get("style_type") : "");//男女款
            sheet1FileInfo.put(j+",33", lazyMap.get("order_code") != null ? lazyMap.get("order_code") : "");//合同号
            sheet1FileInfo.put(j+",34", lazyMap.get("pay_type") != null ? lazyMap.get("pay_type") : "");//付款方式
            sheet1FileInfo.put(j+",35", superList.get(i).get("p_sam") != null ? superList.get(i).get("p_sam") : "");//核价SAM
            
            sheet1FileInfo.put(j+",36", lazyMap.get("odel_wf_real_finish") !=null ? new CustomCell(lazyMap.get("odel_wf_real_finish"), "Date") : new CustomCell());//MR完成日期
            sheet1FileInfo.put(j+",37", lazyMap.get("ppc_wf_real_finish") !=null ? new CustomCell(lazyMap.get("ppc_wf_real_finish"), "Date") : new CustomCell());//技术完成日期
            i++;
            j++;
        }

        fillInfo.put("在制订单分析报表FileInfo", sheet1FileInfo);
        fillInfo.put("订单进度跟踪报表-大货DynaRow", null);
        fillInfo.put("订单进度跟踪报表-大货MergeCell", null);

//        OutputStream os = null;
        try {
//            os = new FileOutputStream(outputFile);

            OutputStream os = Struts2Utils.getResponse().getOutputStream();
            Struts2Utils.getResponse().setContentType("Application/msexcel");
            Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=ERP-REPORT-"+(System.currentTimeMillis() + "").substring(6, 13) + ".xlsx");
            POIUtilsEx.processExcel(os,fillInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        POIUtilsEx.processExcel(os, fillInfo);
    }

	/**
	 * 历史订单分析报表
	 * @return
	 * @autuor 范蠡
	 */
	@FSP(hasBack = AnnoConst.HAS_BACK_YES)
	public String order_his() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_HIS_BY_SQL);
		if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
			fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
		}
		if(!"true".equals(Struts2Utils.getParameter("hidStatusFlag"))){
			fsp.set("status", "0"); //默认为正常的订单
			fsp.set("wf_step", "finish_999"); //默认为已完成
		} else {
			if ("0".equals((String)fsp.get("status"))) { //已完成
				fsp.set("wf_step", "finish_999");
			} else if("1".equals((String)fsp.get("status"))) { //作废
				fsp.set("wf_step", null);
			} else { //全部
				fsp.set("if_all", "finish_999");
			}
		}
		//判断查询数据类型，大货与打版流程不一样  by 范蠡
//		if(fsp.getMap().get("type") == null || "".equals(fsp.getMap().get("type"))){
//			if(fsp.getMap().get("del_operator") != null && !"".equals(fsp.getMap().get("del_operator"))){
//				fsp.set("del_operator_all", "c_ppc_assign_3");
//			}
//		}else{
//			if(fsp.getMap().get("type") != null && "2".equals(fsp.getMap().get("type"))){
//				fsp.set("del_operator_b", "b_pur_confirm_4");
//			}else if(fsp.getMap().get("type") != null && "3".equals(fsp.getMap().get("type"))){
//				fsp.set("del_operator_c", "c_ppc_assign_3");
//			}
//		}

		superList = getObjectsBySql(fsp);
		Map<String, String> statisticsMap = new HashMap<String, String>();
		int count = 0;
		int qualified_nums = 0;
		int beansNums = superList.size();
		int timeOut = 0;
		float toc = 0;
        long jishiTime = 0;

		Date date=new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//String time=sdf.format(date);
		LazyDynaMap bean =new LazyDynaMap();
		java.text.DecimalFormat df=new java.text.DecimalFormat("#");

		for(int i = 0; i < superList.size(); i++) {
			count += Double.parseDouble(superList.get(i).get("sewing_total") != null ? superList.get(i).get("sewing_total").toString() : "0");
            if(superList.get(i).get("qualified_total") != null){
                qualified_nums += Float.parseFloat(superList.get(i).get("qualified_total").toString());
            }
			if (superList.get(i).get("end_time") != null && !"".equals(superList.get(i).get("end_time"))) {
				long out = ((Date) superList.get(i).get("except_finish")).getTime() - ((Date) superList.get(i).get("end_time")).getTime();

                if (out > 0) {
					timeOut++;
				}

                if(superList.get(i).get("type") != null && "2".equals(superList.get(i).get("type").toString())){ //打版
                    if(superList.get(i).get("bqcdel_wf_real_finish") != null && superList.get(i).get("mrdb_wf_real_start") != null){
                        jishiTime += BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("bqcdel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdb_wf_real_start"));
                    }

                    if(superList.get(i).get("bqcdel_wf_real_finish") != null && !"".equals(superList.get(i).get("bqcdel_wf_real_finish"))){

                        Date now = (Date)superList.get(i).get("bqcdel_wf_real_finish");
                        Timestamp tamp = new Timestamp(now.getTime());
                        Date beginTime = (Date)superList.get(i).get("begin_time");
                        Date exceptFinish = (Date)superList.get(i).get("except_finish");
                        double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
//                        DecimalFormat dbtoc = new DecimalFormat("#");
                        String time_consume = df.format(time);
                        //订单完成时TOC百分比
                        int data =Integer.parseInt(time_consume);
                        toc += data;

//                    sheet1FileInfo.put(j+",34", time_consume+"%");
                    }


                }else if(superList.get(i).get("type") != null && "3".equals(superList.get(i).get("type").toString())){ //大货
                    if(superList.get(i).get("qadel_wf_real_finish") != null && superList.get(i).get("mrdel_wf_real_start") != null){
                        jishiTime += BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("qadel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdel_wf_real_start"));
                    }

                    if(superList.get(i).get("wldel_wf_real_finish") != null && !"".equals(superList.get(i).get("wldel_wf_real_finish"))){

                        Date now = (Date)superList.get(i).get("wldel_wf_real_finish");
                        Timestamp tamp = new Timestamp(now.getTime());
                        Date beginTime = (Date)superList.get(i).get("begin_time");
                        Date exceptFinish = (Date)superList.get(i).get("except_finish");
                        double time = ((double) (tamp.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000) / (double) (exceptFinish.getTime() - beginTime.getTime() + 24 * 60 * 60 * 1000)) * 100;
                        String time_consume = df.format(time);
                        //订单完成时TOC百分比
                        int data =Integer.parseInt(time_consume);
                        toc += data;
                    }
                }
			}


			if (superList.get(i).get("wf_step") != null && "finish_999".equals(superList.get(i).get("wf_step"))) {
				bean = superList.get(i);
				Long nowDate = date.getTime();
				Long begin_time = ((Date) superList.get(i).get("begin_time")).getTime();
				Long except_finish = ((Date) superList.get(i).get("except_finish")).getTime();

//				if (0 != begin_time && 0 != except_finish) {
//					float baifenzhu = (nowDate - begin_time + 24 * 60 * 60 * 1000) / (except_finish - begin_time + 24 * 60 * 60 * 1000);
////					float data = baifenzhu * 100;
//					toc += baifenzhu;
//				}
			}
		}

		statisticsMap.put("want_count", count+"");
		statisticsMap.put("qualified_nums", qualified_nums+"");
		if(count > 0){
			statisticsMap.put("qalu", (int)new BigDecimal((qualified_nums/(float)count) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()+"");
		}else{
			statisticsMap.put("qalu", "0");
		}

		if(beansNums > 0){
			statisticsMap.put("timeOutLv", (int)new BigDecimal((timeOut/(float)beansNums) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()+"");
			statisticsMap.put("actualDays", WebUtil.getTimeDisPlayExcel(jishiTime/beansNums) +"");
			statisticsMap.put("toclv", (int)new BigDecimal((toc/(float)beansNums) ).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue() +"");
		}



//		statisticsMap.put("greenNum", greenNum+""); //new BigDecimal((greenNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
//		statisticsMap.put("orangeNum", orangeNum+"");//new BigDecimal((orangeNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
//		statisticsMap.put("redNum", redNum+"");
//		statisticsMap.put("blackNum", blackNum+"");

		Struts2Utils.getRequest().setAttribute("statisticsMap", statisticsMap);


		superList.clear();
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		superList = getObjectsBySql(fsp);

		for(int i = 0; i < superList.size(); i++){
			if(superList.get(i).get("type") != null && "2".equals(superList.get(i).get("type").toString())){ //打版
                if(superList.get(i).get("bqcdel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("bqcdel_wf_real_finish"))
                        && superList.get(i).get("mrdb_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdb_wf_real_start"))){

                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("bqcdel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdb_wf_real_start"));
                    superList.get(i).set("actualDay", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//实际生产周期
                }
			}else if(superList.get(i).get("type") != null && "3".equals(superList.get(i).get("type").toString())){ //大货
                if(superList.get(i).get("qadel_wf_real_finish") != null
                        && !"".equals(superList.get(i).get("qadel_wf_real_finish"))
                        && superList.get(i).get("mrdel_wf_real_start") != null
                        && !"".equals(superList.get(i).get("mrdel_wf_real_start"))){
                    Long actualTime = BizUtil.getWorkTimeBetween((Timestamp)superList.get(i).get("qadel_wf_real_finish"),(Timestamp)superList.get(i).get("mrdel_wf_real_start"));
                    superList.get(i).set("actualDay", WebUtil.getTimeDisPlayExcel(Math.abs(actualTime)));//实际生产周期
                }
            }
		}
		processPageInfo(getObjectsCountSql(fsp));
		return "order_his";
	}


    /**
     * 历史订单分析报表--优化方案
     * @return
     * @autuor 范蠡
     */
    @FSP(hasBack = AnnoConst.HAS_BACK_YES)
    public String order_hisaaaaa() {
        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_HIS_YH_BY_SQL);
        if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
            fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
        }
        if(!"true".equals(Struts2Utils.getParameter("hidStatusFlag"))){
            fsp.set("status", "0"); //默认为正常的订单
            fsp.set("wf_step", "finish_999"); //默认为已完成
        } else {
            if ("0".equals((String)fsp.get("status"))) { //已完成
                fsp.set("wf_step", "finish_999");
            } else if("1".equals((String)fsp.get("status"))) { //作废
                fsp.set("wf_step", null);
            } else { //全部
                fsp.set("if_all", "finish_999");
            }
        }

        fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
        boolean istrue = false;
        String wf_step = "";
        String operator = "";
        String worker = "";
        String sewing_factory = "";
        String sewing_total = "";
        String qualified_total = "";
        Timestamp qadel_wf_real_finish = null;

        //列出页面查询条件
        //MR跟单
        String mr_name = fsp.getMap().get("mr_name") != null ? fsp.getMap().get("mr_name").toString() : "";
        //TPE
        String tpe_name = fsp.getMap().get("tpe_name") != null ? fsp.getMap().get("tpe_name").toString() : "";
        //技术
        String del_operator = fsp.getMap().get("del_operator") != null ? fsp.getMap().get("del_operator").toString() : "";
        //核价
        String cqdel_operator = fsp.getMap().get("cqdel_operator") != null ? fsp.getMap().get("cqdel_operator").toString() : "";
        //CQC
        String cqcdel_operator = fsp.getMap().get("cqcdel_operator") != null ? fsp.getMap().get("cqcdel_operator").toString() : "";
        //QA
        String qadel_operator = fsp.getMap().get("qadel_operator") != null ? fsp.getMap().get("qadel_operator").toString() : "";
        //QA完工日期
        Timestamp qadel_wf_real_finish1 = fsp.getMap().get("qadel_wf_real_finish1") != null ? (Timestamp)fsp.getMap().get("qadel_wf_real_finish1") : null;
        Timestamp qadel_wf_real_finish2 = fsp.getMap().get("qadel_wf_real_finish2") != null ? (Timestamp)fsp.getMap().get("qadel_wf_real_finish2") : null;

        beans = getObjectsBySql(fsp);
        //LIST_ORDER_HIS_DETAIL_BY_SQL

        processPageInfo(getObjectsCountSql(fsp));
        return "order_his";
    }

	public String staff_add() {
		if (oaStaff != null) {
			saveObject(oaStaff);
		}
		return "staff_add";
	}

	public String staff_list() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_STAFF);
		fsp.set("login_name", searchName);
		beans = getObjectsBySql(fsp);
		processPageInfo(getObjectsCountSql(fsp));
		return "staff_add";
	}

	// 修改密码页面
	public String modify_pwd() {
		return "modify_pwd";
	}

	public void modifypwd() {
		String curUser = WebUtil.getCurrentLoginBx().getLoginName();
		final FSPBean fspTemp = new FSPBean();
		fspTemp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LOGIN);
		fspTemp.set("loginName", curUser);
		fspTemp.set("password", oldPwd);
		OaStaff oaStaff = (OaStaff) manager.getOnlyObjectByEql(fspTemp);
		if (null == oaStaff) {
			Struts2Utils.renderText("error"); // 旧密码输入不正确
			return;
		}

		// 修改个人密码并保存
		oaStaff.setPassword(newPwd);
		manager.saveObject(oaStaff);
		Struts2Utils.renderText("ok");
	}

	/**
	 * <B>辛巴达OA新增订单页面</B><br>
	 * 详细功能描述 四种订单类型：模拟报价、样衣打版、大货生产、售后服务
	 *
	 * @author lihouxuan
	 */
	public String addOrder() {
		long t1 = System.currentTimeMillis();
		System.out.println("开始时间：" + t1);
		if (!"mr".equals(XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()))) {
			return "authError";
		}
		if (oaOrder != null) {
			// import com.xbd.oa.utils.BizUtil.startOrderWf;
			String processDifinitionKey = "";
			switch (oaOrder.getType()) {
			case "1":
				processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS1;
				break;
			case "2":
				processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS2;
				break;
			case "3":
				processDifinitionKey = ConstantUtil.WORKFLOW_KEY_PROCESS3;
				break;
			default:
				throw new RuntimeException("根据订单类型:" + oaOrder.getType() + "没有找到相关工作流程定义");
			}
			Timestamp t = BizUtil.getOperatingTime(new Timestamp(System.currentTimeMillis()));
			oaOrder.setBeginTime(t);
			oaOrder.setTimeRate(1.0f);// 不需要计算折算率
			// oaOrder.setTimeRate(BizUtil.computeTimeRate(oaOrder.getBeginTime(),
			// oaOrder.getExceptFinish(), processDifinitionKey,
			// oaOrder.getClothClass()));
			oaOrder.setStatus("0");
			saveObject(oaOrder);
			// TODO 保存之后会有 orderid 可以提醒下一流程操作人
			String curUser = WebUtil.getCurrentLoginBx().getLoginName();
			BizUtil.startOrderWf(processDifinitionKey, curUser, oaOrder);// 启动工作流
			// notifyNextWorkflow(oaOrder.getId()); 不在发送短信提醒
		}
		String redirect = new String();
		switch (oaOrder.getType()) {
		case "1":
			if (getWhb().equals("1")) {
				redirect = "addOrder_list";
			} else if (getWhb().equals("0")) {
				redirect = "addOrderBaojia";
			}
			break;
		case "2":
			if (getWhb().equals("1")) {
				redirect = "addOrder_list";
			} else if (getWhb().equals("0")) {
				redirect = "addOrderDaban";
			}
			break;
		case "3":
			redirect = "addOrder_list";
			break;
		case "4":
			redirect = "addOrder_list";
			break;
		}
		return redirect;
	}

	public void getAssociationOrder() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ASSORDER_BY_SQL);
		beans = getObjectsBySql(fsp);
		result = JSONArray.fromObject(beans).toString();
		System.out.println(result);
		Struts2Utils.renderText(result);
	}

	public void getSales() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_SALES_BY_SQL);
		beans = getObjectsBySql(fsp);
		result = JSONArray.fromObject(beans).toString();
		Struts2Utils.renderText(result);
	}

	public void getOrderTypeList() {
		List<Map<String, String>> orderTypeList = XbdBuffer.getOaDtList("1");
		result = JSONArray.fromObject(orderTypeList).toString();
		Struts2Utils.renderText(result);
	}

	public String add_baojia() {
		if (!"mr".equals(XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()))) {
			return "authError";
		}
		List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
		bean.set("cloth_class_list", cloth_class_list);
		return "add_baojia";
	}

	public String add_daban() {
		if (!"mr".equals(XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()))) {
			return "authError";
		}
		List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
		bean.set("cloth_class_list", cloth_class_list);
		return "add_daban";
	}

	public String add_dahuo() {
		if (!"mr".equals(XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()))) {
			return "authError";
		}
		List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
		bean.set("cloth_class_list", cloth_class_list);
		return "add_dahuo";
	}

	public String add_shouhou() {
		if (!"mr".equals(XbdBuffer.getOrgNameById(WebUtil.getCurrentLoginBx().getOaOrg()))) {
			return "authError";
		}
		return "add_shouhou";
	}

	/**
	 * 查询组织单位列表
	 *
	 * @return
	 */
	public String getSysOrg() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG);
		beans = getObjectsBySql(fsp);
		// 封装json数据
		result = JSONArray.fromObject(beans).toString();
		return "getSysOrg";
	}

	/**
	 * 查询组织单位，封装成json数据
	 */
	public void getJsonSysOrg() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG);
		beans = getObjectsBySql(fsp);
		// 封装json数据
		result = JSONArray.fromObject(beans).toString();
		Struts2Utils.renderText(result);
	}

	/**
	 * 根据组织id查询员工，封装成json数据
	 */
	public void getJsonOaStaff() {
		// FSPBean temp = new FSPBean();
		// 查询组织员工
		if (null != treeNode) {
			fsp.set("oaOrg", treeNode);
		} else {
			fsp.set("oaOrg", 1);
		}
		beans = manager.getObjectsBySql(fsp);

		List<OaStaff> oaStaffs = new ArrayList<OaStaff>();
		for (int i = 0; i < beans.size(); i++) {
			OaStaff oaStaff = new OaStaff();
			oaStaff.setId(Integer.parseInt(beans.get(i).get("id").toString()));
			oaStaff.setLoginName(beans.get(i).get("login_name").toString());
			oaStaff.setPassword(beans.get(i).get("password").toString());
			// 加入角色的id
			oaStaff.setOaRole((Integer) beans.get(i).get("oa_role"));

			oaStaff.setLinkww(beans.get(i).get("linkww") == null ? "" : beans.get(i).get("linkww").toString());
			oaStaff.setLinkphone(beans.get(i).get("linkphone") == null ? "" : beans.get(i).get("linkphone").toString());
			oaStaff.setMemo(beans.get(i).get("memo") == null ? "" : beans.get(i).get("memo").toString());
			oaStaff.setOaOrg(Integer.parseInt(beans.get(i).get("oa_org").toString()));
			oaStaffs.add(oaStaff);
		}

		// 查询该组织部门的管理员
		OaOrg oaOrg = null;
		String admin = "";
		if (null != treeNode) {
			oaOrg = (OaOrg) manager.getObject(OaOrg.class, Integer.parseInt(treeNode));
			if (null != oaOrg && null != oaOrg.getAdmin()) {
				admin = oaOrg.getAdmin();
			}
		}

		// 封装json数据
		LazyDynaMap mapRes = new LazyDynaMap();
		mapRes.set("admin", admin);
		mapRes.set("data", oaStaffs);
		result = JSONArray.fromObject(mapRes).toString();
		Struts2Utils.renderText(result);
	}

	/**
	 * 创建用户
	 *
	 * @return
	 */
	public void jsonCreateStaff() {
		// 校验是否有重复登录名
		fsp.set("loginName", loginName);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LOGIN);
		OaStaff loginUser = (OaStaff) manager.getOnlyObjectByEql(fsp);

		if (null != loginUser) {
			result = "loginNameError";
			Struts2Utils.renderText(result);
			return;
		}

		// 添加表单内容到对象中，并保存
		OaStaff oaStaff = new OaStaff();
		oaStaff.setLoginName(loginName);
		oaStaff.setPassword(password);
		oaStaff.setLinkphone(linkphone);
		oaStaff.setLinkww(linkww);
		oaStaff.setOaOrg(Integer.parseInt(oaOrgId));
		manager.saveObject(oaStaff);
		result = "ok";
		XbdBuffer.refreshOaStaffList();
		Struts2Utils.renderText(result);
	}

	/**
	 * 删除用户
	 *
	 * @return
	 */
	public void jsonDelStaff() {
		// 以员工id查询到员工对象
		OaStaff oaStaff = (OaStaff) manager.getObject(OaStaff.class, Integer.parseInt(oaStaffId));
		if (null != oaStaff && null != oaStaff.getId()) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG);
			fsp.set("admin", oaStaff.getLoginName());

			beans = getObjectsBySql(fsp);
			if (null != beans && beans.size() > 0) {
				result = "delStaffError";
				Struts2Utils.renderText(result);
				return;
			}
			// 删除该对象
			manager.delObject(oaStaff);
			XbdBuffer.refreshOaStaffList();
		}

		result = "ok";
		Struts2Utils.renderText(result);
	}

	/**
	 * 修改用户
	 *
	 * @return
	 */
	public void jsonUpdateStaff() {
		// 以员工id查询到员工对象
		OaStaff oaStaff = (OaStaff) manager.getObject(OaStaff.class, Integer.parseInt(oaStaffId));

		// 修改员工信息并保存
		oaStaff.setLinkww(linkww);
		oaStaff.setLinkphone(linkphone);
		if (oaRole != 0) {
			oaStaff.setOaRole(oaRole);
		}
		manager.saveObject(oaStaff);
		result = "ok";
		XbdBuffer.refreshOaStaffList();
		Struts2Utils.renderText(result);
	}

	/**
	 * 修改密码
	 */
	public void jsonPwInit() {
		// 以员工id查询到员工对象
		OaStaff oaStaff = (OaStaff) manager.getObject(OaStaff.class, Integer.parseInt(oaStaffId));

		// 修改员工密码并保存
		oaStaff.setPassword(password);
		manager.saveObject(oaStaff);
		result = "ok";
		Struts2Utils.renderText(result);
	}

	/**
	 * 修改部门管理员
	 */
	public void jsonUpdateAdmin() {
		// 查询该组织部门的管理员
		OaOrg oaOrg = null;
		oaOrg = (OaOrg) manager.getObject(OaOrg.class, Integer.parseInt(oaOrgId));

		oaOrg.setAdmin(loginName);
		manager.saveObject(oaOrg);
		result = "ok";
		XbdBuffer.refreshOaOrgList();
		// AutoNotifyServlet_stop.refreshLeaderPhone();//刷新部门主管手机号
		Struts2Utils.renderText(result);
	}

	/**
	 * 处理订单
	 */
	public String processOrder() {
 		if (null == this.oaOrderDetail || null == this.oaOrderDetail.getId()) {
			return "confirmOrder";
		}

		// 订单详情查询
		oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, this.oaOrderDetail.getId());
		OaOrder oaOrder = null;
		boolean flag = false;//由于技术和采购同时上线，临时做处理，之后删掉即可

		if (null != oaOrderDetail && null != oaOrderDetail.getOaOrder()) {
			oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrderDetail.getOaOrder());
			if(!oaOrder.getWfStep().equals("c_ppc_factoryMsg_5") && !oaOrder.getWfStep().equals("c_qc_cutting_6") && !oaOrder.getWfStep().equals("c_ppc_confirm_7") && !oaOrder.getWfStep().equals("c_qc_printing_8") && !oaOrder.getWfStep().equals("c_ppc_confirm_9")){
				flag = true;
			}else{
				int iFlag = DateUtil.compare_date("2015-01-14 00:00:01", DateUtil.formatDate(oaOrder.getBeginTime()));
				if (iFlag <= 0) {
					flag = true;
				}
			}
		}

		//测试
		if (flag) {
			getOrderDetail(oaOrder); // 新的处理方案，获取订单信息
			//当前detail的ID
			bean.set("orderDetailId", oaOrderDetail.getId());
			// 当前节点index
			String wfStepIndex = oaOrder.getWfStep();
			wfStepIndex = wfStepIndex.substring(wfStepIndex.length() - 1, wfStepIndex.length());
			bean.set("wfStepIndex", wfStepIndex); // 当前节点index
			// 获取到上传文件和图片的网络地址
			String attachment = oaOrderDetail.getAttachment() == null ? "" : oaOrderDetail.getAttachment();
			// 把文件的网络地址转为文件名称并放入bean中
			bean.set("attachmentName", PathUtil.url2FileName(attachment));
			if (null != oaOrder && null != oaOrder.getId()) {
				if ("2".equals(oaOrder.getType())) { // 样衣打版
					return "processDaban";
				} else if ("3".equals(oaOrder.getType())) { // 大货生产
					return "processDahuo";
				}
			}
		} else { // 旧的流程处理逻辑暂时保存，全部更新后去掉以下代码即可
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.PROCESSORDER);
			fsp.set("id", this.oaOrderDetail.getId());
			bean = manager.getOnlyObjectBySql(fsp);
			if (null != bean && null != bean.get("id")) {
				int oa_order = (Integer) bean.get("oa_order");
				oaOrder = (OaOrder) manager.getObject(OaOrder.class, oa_order);
			}
			getOrderDetailOld(oaOrder); // 旧的处理方案
			// 查询是否能退回
			if (StringUtils.isNotEmpty((String) bean.get("task_id"))) {
				if (WorkFlowUtil.ifTurnTransitionBack((String) bean.get("task_id"))) {
					bean.set("isBack", 1);
				} else {
					bean.set("isBack", 0);
				}
			}
		}
		
		return "processOrder";
	}

	/**
	 * 编辑订单
	 *
	 * @return
	 */
	public String editOrder() {
		if (null == oaOrder || null == oaOrder.getId()) {
			return "addOrder_list";
		}
		oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
		return "editOrder";
	}

	/**
	 * 编辑订单保存
	 *
	 * @return
	 */
	public String updateOrder() {
		if (null == oaOrder || null == oaOrder.getId()) {
			return "addOrder_list";
		}
		OaOrder oaOrder1 = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
		oaOrder1.setStyleCode(oaOrder.getStyleCode());
		saveObject(oaOrder1);
		return "addOrder_list";
	}

	/**
	 * 确认流转订单
	 */
	public String confirmOrder() {
		OaOrderDetail oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, this.oaOrderDetail.getId());

		// 修改订单信息并保存
		oaOrderDetail.setAttachment(this.oaOrderDetail.getAttachment());
		oaOrderDetail.setPic(this.oaOrderDetail.getPic());
		oaOrderDetail.setContent(this.oaOrderDetail.getContent());
		manager.saveObject(oaOrderDetail);

		if (null != group && !"".equals(group.trim())) {
			Map map = new HashMap();
			map.put("group", group);
			BizUtil.nextStepWithCase(oaOrderDetail, map);
		} else {
			BizUtil.nextStep(oaOrderDetail);
		}
//		sendMail(this.oaOrderDetail.getId());// 发送Email提醒
		System.out.println("当前步骤 :" + oaOrderDetail.getWfStepName());
		// notifyNextWorkflow(oaOrderDetail.getOaOrder());
		return "confirmOrder";
	}

	/**
	 * 流程流转时，提醒客户及下一流程操作人
	 *
	 * @param orderId
	 */
	/*
	 * public void notifyNextWorkflow(int orderId){ if( !AutoNotifyServlet_stop.ifRunTime() ){ return; } //TODO 客户发送短信提醒 1 每个流程只发送一次 2 有客户手机号则发送 //判断下一流程的操作时间是否大于3小时 小于3小时直接提醒操作人 try { fsp = new FSPBean(); fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_DETAIL_TIME_FOR_NOTIFY_STAFF_BY_SQL); fsp.set(FSPBean.FSP_ORDER, " order by id desc "); fsp.set("oaOrder", orderId); beans = manager.getObjectsBySql(fsp); if(beans != null && beans.size()>0 ){ LazyDynaMap map = beans.get(0); int detail_id = (int)map.get("id"); String style_code = map.get("style_code").toString();//款号 String inx = map.get("inx").toString();//第几步 String type = map.get("type").toString();//订单类型 String step_name = ifLastWrokflow(inx, type, map.get("wf_step_name").toString());//当前流程名称
	 *
	 * long duration = (long) map.get("wf_step_duration");//流程所需时间 String params;//短信模板参数 if( duration < ConstantUtil.NOTIFYSTAFF_BEFORE3HOURS && map.get("linkphone") != null ){ //流程所需时间少于3小时 直接提醒 String linkphone = map.get("linkphone").toString();//员工联系电话 params = "{\"style_code\":\"" + style_code + "\"}"; System.out.println("流程不足3小时直接提醒===" + step_name+ "===流程时长:" + duration/60/1000); SMSUtils.sendTempletMessage(linkphone, ConstantUtil.TEMPLET_NOFIFY_OPERATOR, params); AutoNotifyServlet_stop.changeState(detail_id, ConstantUtil.NOTIFY_TYPE_REMIND); } if( "1".equals( map.get("times").toString() ) && map.get("tel") != null ){ //TODO 流程最后一步 修改提醒步骤为 订单完成 String customerphone = map.get("tel").toString(); params = "{\"style_code\":\"" + style_code + "\",\"step_name\":\"" + URLEncoder.encode(step_name,"UTF-8") + "\"}"; System.out.println("提醒客户流程流转"); SMSUtils.sendTempletMessage(customerphone, ConstantUtil.TEMPLET_NOTIFY_CUSTOMER, params); } } } catch (UnsupportedEncodingException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	/**
	 * 确认退回订单
	 */
	public String backOrder() {
		OaOrderDetail oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, this.oaOrderDetail.getId());
		BizUtil.backStep(oaOrderDetail);// 订单回退

		OaOrder oaOrder = null;
		if (null != oaOrderDetail && null != oaOrderDetail.getOaOrder()) {
			oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrderDetail.getOaOrder());
		}

		 //如果是大货类型，退回到财务节点，进行判断付款方式是否为3:7或月结，如果是直接退回到上个节点
		if("3".equals(oaOrder.getType()) && "c_qc_printing_8".equals(oaOrder.getWfStep())) {
			if("3:7".equals(oaOrder.getPayType()) || "月结".equals(oaOrder.getPayType())){
				oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, oaOrder.getOaOrderDetail());
				BizUtil.backStep(oaOrderDetail);
			}
		}

		return "confirmOrder";
	}

	/**
	 * 查看订单整个流程详情
	 *
	 * @return
	 */
	public String viewOrderDetail() {
		if (null == oaOrder || null == oaOrder.getId()) {
			return "confirmOrder";
		}
		oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
		if (null == oaOrder || null == oaOrder.getId()) {
			return "confirmOrder";
		}

		// 把品类code换成value
		if (null != oaOrder.getClothClass() && !"".equals(oaOrder.getClothClass())) {
			List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
			for (Map<String, String> map : cloth_class_list) {
				if (map.get("code").equals(oaOrder.getClothClass())) {
					oaOrder.setClothClass(map.get("value"));
				}
			}
		} else {
			oaOrder.setClothClass("基础类");
		}
		Timestamp except_finish = (Timestamp) oaOrder.getExceptFinish();
		Date except_finish_Date = new Date(except_finish.getTime());// 预期完成时间

		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
		fsp.set("oa_order", oaOrder.getId());
		beans = getObjectsBySql(fsp);
		List<LazyDynaMap> list = new ArrayList<LazyDynaMap>();
		for (LazyDynaMap map : beans) {
			// 格式化完成时间
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long wf_step_duration = (Long) map.get("wf_step_duration"); // 此处单位毫秒
			Timestamp wf_real_finish = (Timestamp) map.get("wf_real_finish");
			boolean flag = false;
			if (null == wf_real_finish) {
				wf_real_finish = new Timestamp(System.currentTimeMillis());
				flag = true;
			}
			Timestamp wf_real_start = (Timestamp) map.get("wf_real_start");// 实际开始时间

			Timestamp wf_plan_start = (Timestamp) map.get("wf_plan_start");
			// 计算实际完成时间偏差
			long between = wf_step_duration - BizUtil.getWorkTimeBetween(wf_real_finish, wf_real_start);
			StringBuffer offset = new StringBuffer(between > 0 ? "剩余" : "超期");
			between = Math.abs(between);
			offset.append(WebUtil.getTimeDisPlay(between));

			double d1 = (long) map.get("wf_step_duration");
			double d2 = 3600 * 1000;
			double d = d1 / d2;

			map.set("wf_real_start", df.format((Timestamp) map.get("wf_real_start")));
			map.set("wf_step_duration", d);
			if (!flag) {
				map.set("wf_real_finish", offset.toString());// 计算出实际完成时间偏差存放到map中
				map.set("wf_real_offset", df.format(wf_real_finish));// 计算出实际完成时间偏差存放到map中
			} else {
				map.set("wf_real_finish", "正在处理");
			}

			// 计算实际偏差
			between = wf_step_duration - BizUtil.getWorkTimeBetween(wf_real_finish, wf_plan_start);
			offset = new StringBuffer(between > 0 ? "剩余" : "超期");
			between = Math.abs(between);
			offset.append(WebUtil.getTimeDisPlay(between));
			map.set("offset", offset.toString());// 计算出实际偏差存放到map中

			/*
			 * // 计算总剩余时间 between = BizUtil.getWorkTimeBetween(wf_real_finish, except_finish); offset = new StringBuffer(between < 0 ? "剩余" : "超期"); between = Math.abs(between); offset.append(WebUtil.getTimeDisPlay(between));
			 */

			map.set("total", DateUtils.getYYYY_MM_DD(except_finish));// 计算出实际偏差存放到map中

			// 获取到上传文件和图片的网络地址
			String attachment = (String) map.get("attachment") == null ? "" : (String) map.get("attachment");
			String pic = (String) map.get("pic") == null ? "" : (String) map.get("pic");
			map.set("attachment", attachment);
			map.set("pic", pic);
			// 把文件的网络地址转为文件名称并放入bean中
			map.set("attachment_name", PathUtil.url2FileName(attachment));
			map.set("pic_name", PathUtil.url2FileName(pic));

			list.add(map);
		}
		beans = list;
		return "viewOrderDetail";
	}

	/**
	 * 在手机上 输入订单号
	 */
	public String putOrderNumOnPhone() {

		return "putOrderNumOnPhone";
	}

	/**
	 * 查无此订单号
	 */
	public String noOrderNum() {
		return "noOrderNum";
	}

	/**
	 * 在手机上 查看手机订单的流程
	 */
	public String viewOrderDetailOnPhone() {
		String styleCode = oaOrder.getStyleCode();
		FSPBean fspTemp = new FSPBean();
		fspTemp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_FROM_STYLE_CODE_BY_SQL);
		fspTemp.set(FSPBean.FSP_ORDER, " order by id desc");
		fspTemp.set("styleCode", styleCode);
		beans = getObjectsBySql(fspTemp);
		System.out.println("beans.size:" + beans.size());
		if (beans.size() <= 0) {
			return "noOrderNum";
		} else {
			oaOrder = (OaOrder) manager.getObject(OaOrder.class, beans.get(0).get("id"));
			if (null == oaOrder || null == oaOrder.getId()) {
				return "noOrderNum";
			}
		}

		// 把品类code换成value
		if (null != oaOrder.getClothClass() && !"".equals(oaOrder.getClothClass())) {
			List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
			for (Map<String, String> map : cloth_class_list) {
				if (map.get("code").equals(oaOrder.getClothClass())) {
					oaOrder.setClothClass(map.get("value"));
				}
			}
		} else {
			oaOrder.setClothClass("基础类");
		}
		Timestamp except_finish = (Timestamp) oaOrder.getExceptFinish();
		Date except_finish_Date = new Date(except_finish.getTime());// 预期完成时间

		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
		fsp.set("oa_order", oaOrder.getId());
		beans = getObjectsBySql(fsp);
		List<LazyDynaMap> list = new ArrayList<LazyDynaMap>();
		for (LazyDynaMap map : beans) {
			// 格式化完成时间
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long wf_step_duration = (Long) map.get("wf_step_duration"); // 此处单位毫秒
			Timestamp wf_real_finish = (Timestamp) map.get("wf_real_finish");
			boolean flag = false;
			if (null == wf_real_finish) {
				wf_real_finish = new Timestamp(System.currentTimeMillis());
				flag = true;
			}
			Timestamp wf_real_start = (Timestamp) map.get("wf_real_start");// 实际开始时间

			Timestamp wf_plan_start = (Timestamp) map.get("wf_plan_start");
			// 计算实际完成时间偏差
			long between = wf_step_duration - BizUtil.getWorkTimeBetween(wf_real_finish, wf_real_start);
			StringBuffer offset = new StringBuffer(between > 0 ? "剩余" : "超期");
			between = Math.abs(between);
			offset.append(WebUtil.getTimeDisPlay(between));

			double d1 = (long) map.get("wf_step_duration");
			double d2 = 3600 * 1000;
			double d = d1 / d2;

			map.set("wf_real_start", df.format((Timestamp) map.get("wf_real_start")));
			map.set("wf_step_duration", d);
			if (!flag) {
				map.set("wf_real_finish", offset.toString());// 计算出实际完成时间偏差存放到map中
				map.set("wf_real_offset", df.format(wf_real_finish));// 计算出实际完成时间偏差存放到map中
			} else {
				map.set("wf_real_finish", "正在处理");
			}

			// 计算实际偏差
			between = wf_step_duration - BizUtil.getWorkTimeBetween(wf_real_finish, wf_plan_start);
			offset = new StringBuffer(between > 0 ? "剩余" : "超期");
			between = Math.abs(between);
			offset.append(WebUtil.getTimeDisPlay(between));
			map.set("offset", offset.toString());// 计算出实际偏差存放到map中

			/*
			 * // 计算总剩余时间 between = BizUtil.getWorkTimeBetween(wf_real_finish, except_finish); offset = new StringBuffer(between < 0 ? "剩余" : "超期"); between = Math.abs(between); offset.append(WebUtil.getTimeDisPlay(between));
			 */

			map.set("total", DateUtils.getYYYY_MM_DD(except_finish));// 计算出实际偏差存放到map中

			// 获取到上传文件和图片的网络地址
			String attachment = (String) map.get("attachment") == null ? "" : (String) map.get("attachment");
			String pic = (String) map.get("pic") == null ? "" : (String) map.get("pic");
			map.set("attachment", attachment);
			map.set("pic", pic);
			// 把文件的网络地址转为文件名称并放入bean中
			map.set("attachment_name", PathUtil.url2FileName(attachment));
			map.set("pic_name", PathUtil.url2FileName(pic));

			list.add(map);
		}
		beans = list;

		return "viewOrderDetailOnPhone";
	}

	/**
	 * 查询是否有权限分配订单，有权限则返回成员列表
	 */
	public void jsonGetStaff() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG);
		fsp.set("admin", WebUtil.getCurrentLoginBx().getLoginName());
		bean = manager.getOnlyObjectBySql(fsp);
		if (null == bean || null == bean.get("id")) {
			result = "authorityError";
			Struts2Utils.renderText(result);
			return;
		}
		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_STAFF);
		fspBean.set("oa_org", bean.get("id")); // 本组内的分配
		fspBean.set("login_name", bean.get("admin")); // 排除订单当前所属人，即管理员

		beans = getObjectsBySql(fspBean);
		// 封装json数据
		result = JSONArray.fromObject(beans).toString();
		Struts2Utils.renderText(result);
	}

	/**
	 * 分配订单处理
	 */
	public void jsonAssignOrder() {
		if (null != oaOrderDetail && null != oaOrderDetail.getId() && null != oaStaffId && !"".equals(oaStaffId)) {
			oaStaff = (OaStaff) manager.getObject(OaStaff.class, Integer.parseInt(oaStaffId));

			if (null == oaStaff || null == oaStaff.getId()) {
				result = "oaStaffError";
				Struts2Utils.renderText(result);
				return;
			}
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG_BY_SQL);
			fsp.set("login_name", oaStaff.getLoginName());
			bean = manager.getOnlyObjectBySql(fsp);

			if ("mr".equals(XbdBuffer.getOrgNameById(oaStaff.getOaOrg()))) { // 如果分配人员为mr，则更新订单信息中的mr跟单
				oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
				oaOrder.setMrName(oaStaff.getLoginName());
				saveObject(oaOrder);
			}

			BizUtil.assignOther(oaOrderDetail.getId(), oaStaff.getLoginName(), (String) bean.get("name")); // 分配订单
			result = "ok";
			Struts2Utils.renderText(result);
			return;
		}
		result = "error";
		Struts2Utils.renderText(result);
	}

	/**
	 * mr提前中止订单
	 */
	public String orderFinish() {
		if (null != oaOrder && null != oaOrder.getId()) {
			oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
			if (null != oaOrder && null != oaOrder.getId()) {
				oaOrder.setStatus("1");
				manager.saveObject(oaOrder);
			}
		}
		return "confirmOrder";
	}

	/**
	 * 基准时间设置第一页
	 *
	 * @return
	 */
	public String timebaseConfig() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_BY_EQL);
		oaTimebases = this.getObjectsByEql(fsp);
		List<Map<String, String>> cloth_class_list = XbdBuffer.getOaDtList(ConstantUtil.DT_TYPE_CLOTH_CLASS);
		for (Map ccl : cloth_class_list) {
			for (OaTimebase otb : oaTimebases) {
				if (otb.getClothClass().equals(ccl.get("code"))) {
					otb.setClothClass(ccl.get("value").toString());
				}
			}
		}
		List<LazyDynaMap> process_list = WorkFlowUtil.getProcessDefinitionList();
		for (LazyDynaMap pl : process_list) {
			for (OaTimebase otb : oaTimebases) {
				if (otb.getDefineKey().equals(pl.get("key"))) {
					otb.setDefineKey(pl.get("name").toString());
				}
			}
		}
		return "timebaseConfig";
	}

	/**
	 * 添加基准时间设置
	 *
	 * @return
	 */
	public String timebaseConfig1() {
		if (null != oaTimebase && null != oaTimebase.getId()) {
			oaTimebase = (OaTimebase) manager.getObject(OaTimebase.class, oaTimebase.getId());
		}
		return "timebaseConfig1";
	}

	/**
	 * 添加基准时间设置细节
	 *
	 * @return
	 */
	public String timebaseConfig2() {
		List<LazyDynaMap> tasksDefinitionList = WorkFlowUtil.getTasksDefinitionList(oaTimebase.getDefineKey());
		// 表示为整体的修改，oaTimebase已存在
		if (null != oaTimebase && null != oaTimebase.getId()) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_ENTRY_BY_EQL);
			fsp.set("oaTimebase", oaTimebase.getId());
			List<OaTimebaseEntry> entries = this.getObjectsByEql(fsp);// 获取基准时间细节

			// 防止流程节点更改，做以下处理
			for (int i = 0; i < tasksDefinitionList.size(); i++) {
				LazyDynaMap map = tasksDefinitionList.get(i);
				OaTimebaseEntry oaTimebaseEntry = new OaTimebaseEntry();
				oaTimebaseEntry.setStep((String) map.get("id"));
				oaTimebaseEntry.setStepName((String) map.get("name"));
				oaTimebaseEntry.setStepDuration((long) 0);
				for (OaTimebaseEntry entry : entries) {
					if (((String) map.get("id")).equals(entry.getStep())) {
						oaTimebaseEntry.setStepDuration(entry.getStepDuration());
						break;
					}
				}
				oaTimebaseEntries.add(oaTimebaseEntry);
			}
		} else { // 否则为新增
			long duration = this.oaTimebase.getTotalDuration() / (tasksDefinitionList.size() - 1);
			for (int i = 0; i < tasksDefinitionList.size(); i++) {
				LazyDynaMap map = tasksDefinitionList.get(i);
				OaTimebaseEntry oaTimebaseEntry = new OaTimebaseEntry();
				oaTimebaseEntry.setStep((String) map.get("id"));
				oaTimebaseEntry.setStepName((String) map.get("name"));
				oaTimebaseEntry.setStepDuration(i == 0 ? 0 : duration);
				oaTimebaseEntries.add(oaTimebaseEntry);
			}
		}

		return "timebaseConfig2";
	}

	/**
	 * 保存基准时间设置
	 *
	 * @return
	 */
	public String saveTimebaseConfig() {
		if (null != oaTimebase) {
			manager.saveObject(this.oaTimebase);// 保存基准时间
			if (null != oaTimebase.getId() && oaTimebase.getId() > 0) {
				for (OaTimebaseEntry entry : this.oaTimebaseEntries) {
					entry.setOaTimebase(oaTimebase.getId());
					manager.saveObject(entry);// 保存基准时间细节
				}
			}
		}
		return "saveTimebaseConfig";
	}

	/**
	 * 编辑基准时间设置
	 *
	 * @return
	 */
	public String editTimebaseConfig() {
		oaTimebase = (OaTimebase) manager.getObject(OaTimebase.class, oaTimebase.getId());// 获取基准时间
		List<LazyDynaMap> tasksDefinitionList = WorkFlowUtil.getTasksDefinitionList(oaTimebase.getDefineKey());// 获取流程节点
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_ENTRY_BY_EQL);
		fsp.set("oaTimebase", oaTimebase.getId());
		List<OaTimebaseEntry> entries = this.getObjectsByEql(fsp);// 获取基准时间细节

		// 防止流程节点更改，做以下处理
		for (int i = 0; i < tasksDefinitionList.size(); i++) {
			LazyDynaMap map = tasksDefinitionList.get(i);
			OaTimebaseEntry oaTimebaseEntry = new OaTimebaseEntry();
			oaTimebaseEntry.setStep((String) map.get("id"));
			oaTimebaseEntry.setStepName((String) map.get("name"));
			oaTimebaseEntry.setStepDuration((long) 0);
			for (OaTimebaseEntry entry : entries) {
				if (((String) map.get("id")).equals(entry.getStep())) {
					oaTimebaseEntry.setStepDuration(entry.getStepDuration());
					break;
				}
			}
			oaTimebaseEntries.add(oaTimebaseEntry);
		}
		return "timebaseConfig2";
	}

	/**
	 * 编辑基准时间设置保存
	 *
	 * @return
	 */
	public String updateTimebaseConfig() {
		if (null != oaTimebase && null != oaTimebase.getId()) {
			if (oaTimebase.getId() > 0) {
				manager.saveObject(oaTimebase);
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_ENTRY_BY_EQL);
				fsp.set("oaTimebase", oaTimebase.getId());
				List<OaTimebaseEntry> entries = this.getObjectsByEql(fsp);// 获取基准时间细节
				for (OaTimebaseEntry entry : entries) {
					this.delObject(entry);
				}
				for (OaTimebaseEntry entry : this.oaTimebaseEntries) {
					entry.setOaTimebase(oaTimebase.getId());
					manager.saveObject(entry);// 保存基准时间细节
				}
			}
		}
		return "saveTimebaseConfig";
	}

	/**
	 * 删除基准时间设置
	 *
	 * @return
	 */
	public String delTimebaseConfig() {
		if (null != oaTimebase && null != oaTimebase.getId()) {
			// StringBuffer sql = new StringBuffer("");
			// sql.append("delete from oa_timebase_entry where oa_timebase = ");
			// sql.append(oaTimebase.getId());
			//
			// sql = new StringBuffer("");
			// sql.append("delete from oa_timebase where id = ");
			// sql.append(oaTimebase.getId());
			oaTimebase = (OaTimebase) manager.getObject(OaTimebase.class, oaTimebase.getId());// 获取基准时间
			if (null != oaTimebase && null != oaTimebase.getId()) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_TIMEBASE_ENTRY_BY_EQL);
				fsp.set("oaTimebase", oaTimebase.getId());
				List<OaTimebaseEntry> entries = this.getObjectsByEql(fsp);// 获取基准时间细节
				for (OaTimebaseEntry entry : entries) {
					delObject(entry);// 删除基准时间细节
				}
				delObject(oaTimebase);// 删除基准时间
			}
		}
		return "saveTimebaseConfig";
	}

	/**
	 * 品类列表
	 *
	 * @return
	 */
	public String clothClass_list() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_DT_ENTRY_BY_EQL);
		fsp.set("type", ConstantUtil.DT_TYPE_CLOTH_CLASS);
		oaDts = this.getObjectsByEql(fsp);// 获取基准时间细节
		return "clothClass_list";
	}

	/**
	 * 添加品类
	 *
	 * @return
	 */
	public String addClothClass() {
		return "addClothClass";
	}

	/**
	 * 保存品类
	 *
	 * @return
	 */
	public String saveClothClass() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MAX_INX_OA_DT_BY_SQL);
		fsp.set("type", ConstantUtil.DT_TYPE_CLOTH_CLASS);
		bean = manager.getOnlyObjectBySql(fsp);
		Integer maxInx = 1;
		if (null != bean && null != bean.get("maxinx")) {
			maxInx = Integer.parseInt(bean.get("maxinx").toString()) + 1;
		}
		for (OaDt oaDt : oaDts) {
			oaDt.setType(ConstantUtil.DT_TYPE_CLOTH_CLASS);
			oaDt.setCode(maxInx.toString());
			oaDt.setInx(maxInx);
			oaDt.setMemo("");
			this.saveObject(oaDt);
			maxInx++;
		}
		XbdBuffer.refreshDt(); // 重新获取数据字典
		return "saveClothClass";
	}

	/**
	 * 编辑品类
	 *
	 * @return
	 */
	public String editClothClass() {
		oaDt = (OaDt) manager.getObject(OaDt.class, oaDt.getId());// 获取品类
		return "addClothClass";
	}

	/**
	 * 编辑品类保存
	 *
	 * @return
	 */
	public String updateClothClass() {
		if (null != oaDt && null != oaDt.getId()) {
			saveObject(oaDt);
		}
		XbdBuffer.refreshDt(); // 重新获取数据字典
		return "saveClothClass";
	}

	/**
	 * 删除品类
	 *
	 * @return
	 */
	public String delClothClass() {
		oaDt = (OaDt) manager.getObject(OaDt.class, oaDt.getId());// 获取品类
		if (null != oaDt) {
			delObject(oaDt);
		}
		XbdBuffer.refreshDt(); // 重新获取数据字典
		return "saveClothClass";
	}

	/**
	 * 推送信息
	 *
	 * @return
	 */
	public void notification() {
		if (WebUtil.getCurrentLoginBx() != null) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORDERWF_BY_SQL);
			fsp.set("operator", WebUtil.getCurrentLoginBx().getLoginName());
			long count = getObjectsCountSql(fsp);

			Struts2Utils.renderText(count + "");
			// int count = 0;
			// fsp.set(FSPBean.FSP_QUERY_BY_XML,BxDaoImpl.GET_OA_ORDER_DETAIL_BY_SQL);
			// fsp.set("time", new java.sql.Timestamp(new Date().getTime()));
			// fsp.set("staff_name", staff.getLoginName());
			// beans = getObjectsBySql(fsp);
			// for( LazyDynaMap temp : beans){
			// if (temp.get("wf_real_finish") == null ||
			// temp.get("wf_real_finish").equals("0000-00-00 00:00:00")){
			// count++;
			// }
			// }

		} else {
			Struts2Utils.renderText("0");
		}
	}

	/**
	 * 发送邮件，通知下一工作流人员
	 *
	 * @param body
	 *            邮件内容
	 * @param login_name
	 *            发送人姓名
	 * @see MailUtil.sendBySmtp("接收地址", "抄送地址", "密送地址", "发件人名称", "邮件标题", "邮件内容", "发送方", "邮件服务器", "邮箱密码");
	 */
	private void sendMail(Integer staff_id) {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_DETAIL_FOR_MAIL);
		fsp.set("id", staff_id);
		LazyDynaMap body = manager.getOnlyObjectBySql(fsp);
		String name = (String) body.get("operator");
		// 如果数据库有关联邮箱 则使用关联邮箱 否则使用拼音组合为邮箱
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_STAFF);
		fsp.set("login_name", name);
		LazyDynaMap temp = manager.getOnlyObjectBySql(fsp);
		String to = null;
		if (StringUtils.isEmpty((String) temp.get("email"))) {
			to = XbdBuffer.hanyu2Pinyin(name) + "@singbada.cn";
		} else {
			to = (String) temp.get("email");
		}
		String wf_plan_finish = BizUtil.culPlanDate((Timestamp) body.get("wf_real_start"), (Long) body.get("wf_step_duration")).toString().substring(0, 16);
		String subject = (String) body.get("style_code") + (String) body.get("wf_step_name") + "需要您在" + wf_plan_finish + "内完成";
		String content = "尊敬的" + (String) body.get("operator") + ":</br>" + "您好，款号为【<span style='color:#5fa207;font-weight:bold';>" + (String) body.get("style_code") + "</span>】的订单已由【<span style='color:#5fa207;font-weight:bold';>" + WebUtil.getCurrentLoginBx().getLoginName() + "</span>】流转到您这边，请于【<span style='color:#5fa207;font-weight:bold';>" + wf_plan_finish + "</span>】内完成【<span style='color:#5fa207;font-weight:bold';>" + (String) body.get("wf_step_name") + "</span>】，<a style='color:blue;' href='http://oa.singbada.cn/bx/todo'>点击处理</a>。谢谢！</br>让世界因为传统供应链的改变而发生改变！" + to + "</br>&nbsp;&nbsp;&nbsp;&nbsp;顺颂</br>商祺</br>互联网研发中心致上！";
		MailUtil.sendBySmtp(to, "", "", WebUtil.getCurrentLoginBx().getLoginName(), subject, content, "changyuchun@singbada.cn", "smtp.qq.com", "lingdong");
	}

	private String ifLastWrokflow(String inx, String type, String stepName) {
		if (ConstantUtil.DABAN_LAST_STEP_INX.equals(inx) && ConstantUtil.OA_ORDER_TYPE_2.equals(type)) {
			stepName = "订单完成";
		}
		if (ConstantUtil.DAHUO_LAST_STEP_INX.equals(inx) && ConstantUtil.OA_ORDER_TYPE_3.equals(type)) {
			stepName = "发货完成";
		}
		return stepName;

	}

	/**
	 * 获取大货时间控制表
	 *
	 * @see 如果改变大货流程则本功能须修改
	 */
	// FIXME
	public void getDHTC() {
		// 单个大货订单号查询
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
		fsp.set("oa_order", 1656);
		beans = manager.getObjectsBySql(fsp);
		List list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String stepName = null;
		if (beans != null /* && beans.size() > 9 */) {
			LazyDynaMap map = new LazyDynaMap();
			for (int i = 0, j = 1; i < beans.size(); i++, j++) {
				stepName = beans.get(i).get("wf_step").toString();
				String stepCount = stepName.substring(stepName.length() - 1);
				String key = getColName(stepCount);
				if (j == Integer.parseInt(stepName.substring(stepName.length() - 1))) {// 正常流转，未退回上一步
					map.set(key + "_tj", sdf.format((Date) beans.get(i).get("wf_real_finish")));// 提交时间
					if (StringUtils.isEmpty((String) map.get(key + "_th"))) {
						map.set(key + "_th", "");
					}
					if (StringUtils.isEmpty((String) map.get(key + "_reth"))) {
						map.set(key + "_retj", "");
					}
				} else {// 退回上一步
						// i = 4 key =3 j=5
					map.set(key + "_retj", sdf.format((Date) beans.get(i).get("wf_real_finish")));
					map.set(key + "_th", sdf.format((Date) beans.get(i - 1).get("wf_real_finish")));
					j -= 2;
				}
			}
			list.add(map);

			String titles = "款号,MR提交时间,财务提交时间,技术部提交时间,退回技术部时间,再次提交时间,采购部提交时间,退回采购部时间,再次提交时间,CQC提交时间,退回CQC时间,再次提交时间,MQC提交时间,退回MQC时间,再次提交时间,QA提交时间,财务确认尾款时间,QA出货时间";
			String keys = "style_code,mr_tj,fi1_tj,it_tj,it_th,it_retj,pur_tj,pur_th,pur_retj,cqc_tj,cqc_th,cqc_retj,mqc_tj,mqc_th,mqc_retj,qa1_tj,fi2_tj,qa2_tj";
			BizUtil.generateDownLoadCsv(list, titles, keys);
			Struts2Utils.renderText("已生成CSV文件");
			// list.add(map);
		}

	}

	private String getColName(String inx) {
		switch (inx) {
		case "1":
			inx = "mr";
			break;
		case "2":
			inx = "fi1";
			break;
		case "3":
			inx = "it";
			break;
		case "4":
			inx = "pur";
			break;
		case "5":
			inx = "cqc";
			break;
		case "6":
			inx = "mqc";
			break;
		case "7":
			inx = "qa1";
			break;
		case "8":
			inx = "fi2";
			break;
		case "9":
			inx = "qa2";
			break;
		default:
			inx = "";
		}
		return inx;
	}

	/**
	 * 获取大货时间控制表
	 *
	 * @return
	 * @param fsp
	 *            .map.start
	 * @param fsp
	 *            .map.end 第一次开始时间 f_start 第一次结束时间 f_finish 最后开始时间 l_start 最后结束时间 wf_real_finish
	 *
	 *            group by orderid
	 */
	public void getDHTC2() {
		List list = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// 得到款号和mr提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DHTC_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by id ");
		// fsp.set("begin_time", "2014-05-25");
		// fsp.set("end_time", "2014-06-01");

		List<LazyDynaMap> basic = manager.getObjectsBySql(fsp);

		fsp.set(FSPBean.FSP_ORDER, " order by oa_order ");
		// 得到财务提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_FI_DHTC_BY_SQL);

		List<LazyDynaMap> fi = manager.getObjectsBySql(fsp);

		// 技术部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_IT_DHTC_BY_SQL);

		List<LazyDynaMap> it = manager.getObjectsBySql(fsp);

		// 采购部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_PUR_DHTC_BY_SQL);

		List<LazyDynaMap> pur = manager.getObjectsBySql(fsp);

		// CQC 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_CQC_DHTC_BY_SQL);

		List<LazyDynaMap> cqc = manager.getObjectsBySql(fsp);

		// MQC(QC) 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_QC_DHTC_BY_SQL);

		List<LazyDynaMap> mqc = manager.getObjectsBySql(fsp);

		// QA 提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_QA_DHTC_BY_SQL);

		List<LazyDynaMap> qa = manager.getObjectsBySql(fsp);

		for (int i = 0; i < basic.size(); i++) {
			LazyDynaMap map = new LazyDynaMap();
			map.set("style_code", basic.get(i).get("style_code"));
			map.set("mr_tj", sdf.format((Date) basic.get(i).get("begin_time")));
			map.set("fi2_tj", sdf.format((Date) basic.get(i).get("wf_real_start")));
			map.set("qa2_tj", sdf.format((Date) basic.get(i).get("end_time")));

			map.set("fi1_tj", sdf.format((Date) fi.get(i).get("wf_real_finish")));// 财务提交时间
			// 若 财务提交时间与it开始时间相同 则it部没有被退回过
			if (it.get(i).get("f_start").toString().equals(it.get(i).get("l_start").toString())) {
				map.set("it_tj", sdf.format((Date) it.get(i).get("wf_real_finish")));// it
				map.set("it_th", "");
				map.set("it_retj", "");
			} else {
				map.set("it_tj", sdf.format((Date) it.get(i).get("f_finish"))); // 上次提交时间
																				// （或第一次提交时间）
				map.set("it_th", sdf.format((Date) it.get(i).get("l_start")));// 退回时间
				map.set("it_retj", sdf.format((Date) it.get(i).get("wf_real_finish")));// 再次提交时间
			}

			// 采购部
			if (pur.get(i).get("f_start").toString().equals(pur.get(i).get("l_start"))) {
				map.set("pur_tj", sdf.format((Date) pur.get(i).get("wf_real_finish")));
				map.set("pur_th", "");
				map.set("pur_retj", "");
			} else {
				// FIXME 没有第一次的提交时间
				map.set("pur_tj", sdf.format((Date) pur.get(i).get("f_finish")));
				map.set("pur_th", sdf.format((Date) pur.get(i).get("l_start")));
				map.set("pur_retj", sdf.format((Date) pur.get(i).get("wf_real_finish")));
			}

			// CQC
			if (cqc.get(i).get("f_start").toString().equals(cqc.get(i).get("l_start").toString())) {
				map.set("cqc_tj", sdf.format((Date) cqc.get(i).get("wf_real_finish")));
				map.set("cqc_th", "");
				map.set("cqc_retj", "");
			} else {
				// FIXME 没有第一次的提交时间
				map.set("cqc_tj", sdf.format((Date) cqc.get(i).get("f_finish")));
				map.set("cqc_th", sdf.format((Date) cqc.get(i).get("l_start")));
				map.set("cqc_retj", sdf.format((Date) cqc.get(i).get("wf_real_finish")));
			}

			// MQC
			if (mqc.get(i).get("f_start").toString().equals(mqc.get(i).get("l_start").toString())) {
				map.set("mqc_tj", sdf.format((Date) cqc.get(i).get("wf_real_finish")));
				map.set("mqc_th", "");
				map.set("mqc_retj", "");
			} else {
				// FIXME 没有第一次的提交时间
				map.set("mqc_tj", sdf.format((Date) mqc.get(i).get("f_finish")));
				map.set("mqc_th", sdf.format((Date) mqc.get(i).get("l_start")));
				map.set("mqc_retj", sdf.format((Date) mqc.get(i).get("wf_real_finish")));
			}

			map.set("qa1_tj", sdf.format((Date) qa.get(i).get("wf_real_finish")));

			list.add(map);
		}

		String titles = "款号,MR提交时间,财务提交时间,技术部提交时间,退回技术部时间,再次提交时间,采购部提交时间,退回采购部时间,再次提交时间,CQC提交时间,退回CQC时间,再次提交时间,MQC提交时间,退回MQC时间,再次提交时间,QA提交时间,财务确认尾款时间,QA出货时间";
		String keys = "style_code,mr_tj,fi1_tj,it_tj,it_th,it_retj,pur_tj,pur_th,pur_retj,cqc_tj,cqc_th,cqc_retj,mqc_tj,mqc_th,mqc_retj,qa1_tj,fi2_tj,qa2_tj";
		String url = BizUtil.generateDownLoadCsv(list, titles, keys);
		Struts2Utils.renderText(PathUtil.path2Url(url));

	}

	/**
	 * 生产时间导出功能（所有订单）
	 *
	 */
	public String getDepartmentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DEPARTMENTTIME_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by ood.id ");
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);

		// fsp.set("orderId", 1658);
		superList = new ArrayList<LazyDynaMap>();
		beans = manager.getObjectsBySql(fsp);
		for (LazyDynaMap bean : beans) {
			ifOutTime(bean);// 判断是否超时
			if ((long) bean.get("times") == 1) {
				bean.remove("l_start");
				bean.remove("wf_real_finish");
			} else if ((long) bean.get("times") > 2) {
				// 回退次数超过两次 无法统计所用时间
				bean.remove("l_start");
				bean.remove("wf_real_finish");
				bean.remove("out_time");
				bean.set("memo", "回退次数超过一次，请核对此订单");
			}
			System.out.println((long) bean.get("times"));
			superList.add(bean);
		}

		// String titles =
		// "款号,MR提交时间,财务提交时间,技术部提交时间,退回技术部时间,再次提交时间,采购部提交时间,退回采购部时间,再次提交时间,CQC提交时间,退回CQC时间,再次提交时间,MQC提交时间,退回MQC时间,再次提交时间,QA提交时间,财务确认尾款时间,QA出货时间";
		String titles = "操作人,款号,订单号,开始时间,结束时间,第二次开始时间,第二次结束时间,备注,是否超时";
		// String keys =
		// "style_code,mr_tj,fi1_tj,it_tj,it_th,it_retj,pur_tj,pur_th,pur_retj,cqc_tj,cqc_th,cqc_retj,mqc_tj,mqc_th,mqc_retj,qa1_tj,fi2_tj,qa2_tj";
		String keys = "operator,style_code,oa_order,f_start,f_finish,l_start,wf_real_finish,memo,out_time";
		String url = BizUtil.generateDownLoadCsv(superList, titles, keys);
		fsp.set("url", PathUtil.path2Url(url));
		// Struts2Utils.renderText(PathUtil.path2Url(url));
		processPageInfo(manager.getObjectsCountSql(fsp));
		return "DHTC";
	}

	/**
	 * 大货时间
	 */
	public void getDHTC3() {
		List dataList = new ArrayList();

		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");

		// 得到款号和mr提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DHTC_BY_SQL2);
		fsp.set(FSPBean.FSP_ORDER, " order by id ");
		// fsp.set("begin_time", "2014-05-25");
		// fsp.set("end_time", "2014-06-01");

		List<LazyDynaMap> basic = manager.getObjectsBySql(fsp);

		fsp.set(FSPBean.FSP_ORDER, " GROUP BY oa_order ORDER BY oa_order ");
		// 得到财务提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_FI_DHTC_BY_SQL);

		List<LazyDynaMap> fi = manager.getObjectsBySql(fsp);

		// 技术部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_IT_DHTC_BY_SQL);

		List<LazyDynaMap> it = manager.getObjectsBySql(fsp);

		// 采购部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_PUR_DHTC_BY_SQL);

		List<LazyDynaMap> pur = manager.getObjectsBySql(fsp);

		// CQC 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_CQC_DHTC_BY_SQL);

		List<LazyDynaMap> cqc = manager.getObjectsBySql(fsp);

		// MQC(QC) 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_QC_DHTC_BY_SQL);

		List<LazyDynaMap> mqc = manager.getObjectsBySql(fsp);

		// QA 提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_QA_DHTC_BY_SQL);

		List<LazyDynaMap> qa = manager.getObjectsBySql(fsp);

		// fi2 确认尾款
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_FI2_DHTC_BY_SQL);

		List<LazyDynaMap> fi2 = manager.getObjectsBySql(fsp);

		// qa2 发货
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_QA2_DHTC_BY_SQL);

		List<LazyDynaMap> qa2 = manager.getObjectsBySql(fsp);

		// times
		// max(wf_real_finish) wf_real_finish, 最后完成时间
		// min(wf_real_finish) f_finish, 第一次完成时间
		// max(wf_real_start) l_start, 最后一次开始时间
		// min(wf_real_start) f_start, 第一次开始时间
		for (int i = 0; i < basic.size(); i++) {
			ArrayList<Map> data = new ArrayList<Map>();
			Timestamp t1 = null;// 开始时间
			Timestamp t2 = null;// 结束时间
			long l1 = 0L;// 时差
			// long l2 = 0L;//时差
			long orderSum = 0L;// 单个订单完成时长
			long orderDuration = 0L;// 每个订单计划时长
			long wf_step_duration = 0L;// 当前步骤所需时长

			// 序号
			Map num = new HashMap();
			num.put("time", i + 1 + "");
			num.put("flag", "1");
			// 款号
			Map style_code = new HashMap();
			style_code.put("time", (String) basic.get(i).get("style_code"));
			style_code.put("flag", "1");
			data.add(num);
			data.add(style_code);

			// 财务部 fi
			String fi_time = "无法计算";
			wf_step_duration = (long) fi.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) fi.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = (Timestamp) fi.get(i).get("f_start");
				t2 = (Timestamp) fi.get(i).get("f_finish");
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) fi.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) fi.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) fi.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) fi.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) fi.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_fi_confirm_2");
				f.set("order_id", fi.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// 技术部 it
			String it_time = "无法计算";
			wf_step_duration = (long) it.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) it.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) it.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) it.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) it.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) it.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) it.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) it.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) it.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_ppc_assign_3");
				f.set("order_id", it.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// 采购部 pur
			String pur_time = "无法计算";
			wf_step_duration = (long) pur.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) pur.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) pur.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) pur.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) pur.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) pur.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) pur.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) pur.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) pur.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_fi_pay_4");
				f.set("order_id", pur.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// CQC
			String cqc_time = "无法计算";
			wf_step_duration = (long) cqc.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) cqc.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) cqc.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) cqc.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				// long between = wf_step_duration - l1 ;
				cqc_time = WebUtil.getTimeDisPlay4DH(l1);
			} else if ((long) cqc.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) cqc.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) cqc.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) cqc.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) cqc.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_ppc_factoryMsg_5");
				f.set("order_id", cqc.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
				cqc_time = WebUtil.getTimeDisPlay4DH(l1);
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// MQC
			String mqc_time = "无法计算";
			wf_step_duration = (long) mqc.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) mqc.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) mqc.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) mqc.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				// long between = wf_step_duration - (l1 + l2);
				mqc_time = WebUtil.getTimeDisPlay4DH(l1);
			} else if ((long) mqc.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) mqc.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) mqc.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) mqc.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) mqc.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_qc_cutting_6");
				f.set("order_id", mqc.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// QA验货
			String qa_time = "无法计算";
			wf_step_duration = (long) mqc.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) qa.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) qa.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) qa.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) qa.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) qa.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) qa.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) qa.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) qa.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_ppc_confirm_7");
				f.set("order_id", qa.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// TODO //查询语句需添加
			// 财务确认尾款
			String fi2_time = "无法计算";
			wf_step_duration = (long) fi2.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) fi2.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) fi2.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) fi2.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) fi2.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) fi2.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) fi2.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) fi2.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) fi2.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set("order_id", fi2.get(i).get("oa_order"));
				f.set("step", "c_qc_printing_8");
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));

			// QA发货
			String qa2_time = "无法计算";
			wf_step_duration = (long) qa2.get(i).get("wf_step_duration");
			orderDuration += wf_step_duration;
			if ((long) qa2.get(i).get("times") == 1) {
				// 没有退回的步骤
				t1 = new Timestamp(((Date) qa2.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) qa2.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

			} else if ((long) qa2.get(i).get("times") == 2) {
				// 退回两步，可以计算时长
				t1 = new Timestamp(((Date) qa2.get(i).get("f_start")).getTime());
				t2 = new Timestamp(((Date) qa2.get(i).get("f_finish")).getTime());
				l1 = BizUtil.getWorkTimeBetween(t2, t1);

				t1 = new Timestamp(((Date) qa2.get(i).get("l_start")).getTime());
				t2 = new Timestamp(((Date) qa2.get(i).get("wf_real_finish")).getTime());
				l1 += BizUtil.getWorkTimeBetween(t2, t1);

				// long between = wf_step_duration - (l1 + l2);
			} else {
				FSPBean f = new FSPBean();
				l1 = 0L;
				f.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL);
				f.set(FSPBean.FSP_ORDER, " ORDER BY id ");
				f.set("step", "c_ppc_confirm_9");
				f.set("order_id", qa2.get(i).get("oa_order"));
				beans = manager.getObjectsBySql(f);
				// 找出该订单所有当前步骤，计算时间
				for (LazyDynaMap bean : beans) {
					t1 = (Timestamp) bean.get("f_start");
					t2 = (Timestamp) bean.get("f_finish");
					l1 += BizUtil.getWorkTimeBetween(t2, t1);
				}
			}
			orderSum += l1;
			data.add(ifOutTime(l1, wf_step_duration));
			data.add(ifOutTime(orderSum, orderDuration));// 订单花费的时间
			data.add(ifOutTime(orderDuration, 0L));
			dataList.add(data);
		}
		try {

			String fileName = PathUtil.getTempDownloadPath() + "/" + sdf.format(new Date()) + ".xls";// 生成的excel文件名

			OutputStream os = new FileOutputStream(fileName, true);
			ExcelUtils.createExcel4OA(os, "大货生产", 0, 0, dataList);
			Struts2Utils.renderText(PathUtil.path2Url(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否超时
	 *
	 * @param use_time
	 * @param plan_time
	 * @return HashMap
	 */
	private HashMap ifOutTime(long use_time, long plan_time) {
		HashMap map = new HashMap();
		if (plan_time - use_time >= 0) {
			// 未超时
			map.put("flag", "0");
		} else {
			// 超时
			map.put("flag", "1");
		}
		map.put("time", WebUtil.getTimeDisPlay4DH(use_time));

		return map;
	}

	/**
	 * 时间控制表导出（样衣打版）
	 */
	public void getDepartmentTimeYY() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List list = new ArrayList();

		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DEPARTMENTTIME_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by ood.id ");
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);

		beans = manager.getObjectsBySql(fsp);
		for (LazyDynaMap bean : beans) {
			// ifOutTime(bean);//判断是否超时
			if ((long) bean.get("times") == 1) {
				;
				bean.remove("l_start");
				bean.remove("wf_real_finish");
			} else if ((long) bean.get("times") > 2) {
				// 回退次数超过两次 无法统计所用时间
				bean.remove("l_start");
				bean.remove("wf_real_finish");
				bean.remove("out_time");
				bean.set("memo", "回退次数超过一次，请核对此订单");
			}
			System.out.println((long) bean.get("times"));
			list.add(bean);
		}

		// String titles =
		// "款号,MR提交时间,财务提交时间,技术部提交时间,退回技术部时间,再次提交时间,采购部提交时间,退回采购部时间,再次提交时间,CQC提交时间,退回CQC时间,再次提交时间,MQC提交时间,退回MQC时间,再次提交时间,QA提交时间,财务确认尾款时间,QA出货时间";
		String titles = "操作人,款号,订单号,开始时间,结束时间,第二次开始时间,第二次结束时间,备注,是否超时";
		// String keys =
		// "style_code,mr_tj,fi1_tj,it_tj,it_th,it_retj,pur_tj,pur_th,pur_retj,cqc_tj,cqc_th,cqc_retj,mqc_tj,mqc_th,mqc_retj,qa1_tj,fi2_tj,qa2_tj";
		String keys = "operator,style_code,oa_order,f_start,f_finish,l_start,wf_real_finish,memo,out_time";
		String url = BizUtil.generateDownLoadCsv(list, titles, keys);
		Struts2Utils.renderText(PathUtil.path2Url(url));
	}

	// 判断超时多久
	private String ifOutTime(LazyDynaMap bean) {
		long plan_time = (long) bean.get("wf_step_duration");
		Timestamp f_start = (Timestamp) bean.get("f_start");
		Timestamp f_end = (Timestamp) bean.get("f_finish");
		Timestamp l_start = (Timestamp) bean.get("l_start");
		Timestamp l_end = (Timestamp) bean.get("wf_real_finish");

		// used_time = f_end.getTime() - f_start.getTime();
		// used_time = used_time + l_end.getTime() - l_start.getTime();
		//
		// if( used_time - (long)bean.get("wf_step_duration") <= 0){
		// bean.set("out_time", "未超时");
		// }else{
		// int out_time = (int)(used_time -
		// (long)bean.get("wf_step_duration"))/1000/60/60;
		// System.out.println(out_time);//43200000 176132000
		// bean.set("out_time", "超时" + out_time + "小时");
		// }

		Timestamp wf_real_start = (Timestamp) bean.get("wf_real_start");// 实际开始时间

		Timestamp wf_plan_start = (Timestamp) bean.get("wf_plan_start");
		// 计算实际完成时间偏差
		long between = plan_time - BizUtil.getWorkTimeBetween(f_end, f_start) - BizUtil.getWorkTimeBetween(l_end, l_start);
		StringBuffer offset = new StringBuffer(between > 0 ? "剩余" : "超期");
		between = Math.abs(between);
		bean.set("out_time", offset);

		return "";
	}

	/**
	 * lanyu 大货每天及时率统计
	 *
	 * @throws ParseException
	 */
	public void getDaBanTimelyRate() {
		List dataList = new ArrayList();
		// SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到款号和mr提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DBTC_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by id ");
		Date date = new Date();
		// Date date=sdf.parse("2014-09-22 17:06:16");
		Timestamp tamp = new Timestamp(date.getTime());
		// fsp.set("end_time",tamp);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tamp);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		Timestamp tamp1 = new Timestamp(date.getTime());
		// fsp.set("begin_time", tamp1);

		List<LazyDynaMap> basic = manager.getObjectsBySql(fsp);

		fsp.set(FSPBean.FSP_ORDER, " GROUP BY oa_order ORDER BY oa_order ");
		// 得到财务提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("type", "2");
		fsp.set("wfStep", "b_fi_confirm_2");
		List<LazyDynaMap> fi = manager.getObjectsBySql(fsp);

		// 采购部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "b_ppc_confirm_3");
		List<LazyDynaMap> pur = manager.getObjectsBySql(fsp);

		// 技术部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "b_pur_confirm_4");
		List<LazyDynaMap> it = manager.getObjectsBySql(fsp);

		// 核价中心 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "b_ppc_confirm_5");
		List<LazyDynaMap> heJia = manager.getObjectsBySql(fsp);

		// MR部门 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "b_qc_confirm_6");
		List<LazyDynaMap> mr = manager.getObjectsBySql(fsp);
		// times
		// max(wf_real_finish) wf_real_finish, 最后完成时间
		// min(wf_real_finish) f_finish, 第一次完成时间
		// max(wf_real_start) l_start, 最后一次开始时间
		// min(wf_real_start) f_start, 第一次开始时间

		// 对各部门异动订单的计数
		int dingJin2 = 0;
		int mianLiao3 = 0;
		int daBan4 = 0;
		int heSuan5 = 0;
		int mrCheck6 = 0;
		int daBanYiDong = 0;
		// 对各部门正常订单的计数
		int dingJin2J = 0;
		int mianLiao3J = 0;
		int daBan4J = 0;
		int heSuan5J = 0;
		int mrCheck6J = 0;
		// 以下是各部门流进流出的统计
		int intoFiNumber = 0;// 财务确定定金 流进订单数量
		int outflowFiNumber = 0;// 财务确定定金 流出订单数量
		int notFinishFi = 0;

		int intoItNumber = 0;
		int outflowItNumber = 0;
		int notFinishIt = 0;
		int intoPurNumber = 0;
		int outflowPurNumber = 0;
		int notFinishPur = 0;

		int intoHeJiaNumber = 0;
		int outflowHeJiaNumber = 0;
		int notFinishHeJia = 0;
		int intoMrNumber = 0;
		int outflowMrNumber = 0;
		int notFinishMr = 0;
		Map check = new HashMap();
		for (int i = 0; i < basic.size(); i++) {
			// ArrayList<Map> data = new ArrayList<Map>();
			Timestamp t1 = null;// 开始时间
			Timestamp t2 = null;// 结束时间
			long l1 = 0L;// 时差
			// long orderSum = 0L;//单个订单完成时长
			long wf_step_duration = 0L;// 当前步骤所需时长
			// 财务部 fi
			String fi_time = "无法计算";
			if (fi.size() > i) {
				wf_step_duration = (long) fi.get(i).get("wf_step_duration");
				/* if( (long)fi.get(i).get("times") == 1 ){ */
				// 没有退回的步骤
				t1 = (Timestamp) fi.get(i).get("f_start");
				if (fi.get(i).get("f_finish") != null) {
					t2 = (Timestamp) fi.get(i).get("f_finish");
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowFiNumber++;
					}
				} else {
					notFinishFi++;
				}
				// l1 = BizUtil.getWorkTimeBetween(t2,t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoFiNumber++;
					// l1 = BizUtil.getWorkTimeBetween(t2,t1);
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						dingJin2++;
					} else {
						// 未超时
						dingJin2J++;
					}
				}
			}
			// haha here
			// 技术部 it
			String it_time = "无法计算";
			if (it.size() > i) {
				wf_step_duration = (long) it.get(i).get("wf_step_duration");
				/* if( (long)it.get(i).get("times") == 1 ){ */
				// 没有退回的步骤
				t1 = new Timestamp(((Date) it.get(i).get("f_start")).getTime());
				if (it.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) it.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowItNumber++;
					}
				} else {
					notFinishIt++;

				}
				// l1 = BizUtil.getWorkTimeBetween(t2, t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoItNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时
						daBan4++;
					} else {
						// 未超时
						daBan4J++;
					}
				}
			}
			// 采购部 pur
			String pur_time = "无法计算";
			if (pur.size() > i) {
				wf_step_duration = (long) pur.get(i).get("wf_step_duration");
				/* if( (long)pur.get(i).get("times") == 1 ){ */
				// 没有退回的步骤
				t1 = new Timestamp(((Date) pur.get(i).get("f_start")).getTime());
				if (pur.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) pur.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowPurNumber++;
					}
				} else {
					notFinishPur++;
				}
				// l1 = BizUtil.getWorkTimeBetween(t2,t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoPurNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						mianLiao3++;
					} else {
						// 未超时
						mianLiao3J++;
					}
				}
			}
			// 核价中心
			String cqc_time = "无法计算";
			if (heJia.size() > i) {
				wf_step_duration = (long) heJia.get(i).get("wf_step_duration");
				/* if( (long)heJia.get(i).get("times") == 1 ){ */
				// 没有退回的步骤
				t1 = new Timestamp(((Date) heJia.get(i).get("f_start")).getTime());
				if (heJia.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) heJia.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowHeJiaNumber++;
					}
				} else {
					notFinishHeJia++;
				}
				// l1 = BizUtil.getWorkTimeBetween(t2,t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoHeJiaNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						heSuan5++;
					} else {
						// 未超时
						heSuan5J++;
					}
				}
			}

			// MR确认时间
			String mqc_time = "无法计算";
			if (mr.size() > i) {
				wf_step_duration = (long) mr.get(i).get("wf_step_duration");
				/* if( (long)mr.get(i).get("times") == 1 ){ */
				// 没有退回的步骤
				t1 = new Timestamp(((Date) mr.get(i).get("f_start")).getTime());
				if (mr.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) mr.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowMrNumber++;
					}
				} else {
					notFinishMr++;
				}
				// l1 = BizUtil.getWorkTimeBetween(t2,t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoMrNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时
						mrCheck6++;
					} else {
						// 未超时
						mrCheck6J++;
					}
				}
			}

		}
		// 查询今天新增的订单数量
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_NEW_ORDER_ADD_BY_SQL);
		fsp.set("type", "2");
		fsp.set("orderBeginTime", tamp1);
		fsp.set("orderTodayMaxTime", tamp);
		LazyDynaMap orderToday = (LazyDynaMap) manager.getOnlyObjectBySql(fsp);
		int orderTodayCount = 0;
		if (orderToday != null) {
			orderTodayCount = Integer.parseInt(orderToday.get("ordertoday").toString());
		}
		int orderSum = basic.size();
		// 添加序号
		DecimalFormat df = new DecimalFormat("0.##");
		for (int i = 1; i < 7; i++) {
			ArrayList<String> data = new ArrayList<String>();
			data.add(i + "");
			switch (i) {
			case 1:
				data.add("财务部确定定金");
				data.add(intoFiNumber + "款");
				data.add(outflowFiNumber + "款");
				data.add(notFinishFi + "款");
				data.add(dingJin2 + "款");
				data.add(dingJin2J + "款");
				if ((dingJin2 + dingJin2J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) dingJin2J / (dingJin2 + dingJin2J) * 100) + "%");
				}
				break;
			case 2:
				data.add("采购部");
				data.add(intoPurNumber + "款");
				data.add(outflowPurNumber + "款");
				data.add(notFinishPur + "款");
				data.add(mianLiao3 + "款");
				data.add(mianLiao3J + "款");
				if ((mianLiao3 + mianLiao3J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) mianLiao3J / (mianLiao3 + mianLiao3J) * 100) + "%");
				}
				break;
			case 3:
				data.add("技术部");
				data.add(intoItNumber + "款");
				data.add(outflowItNumber + "款");
				data.add(notFinishIt + "款");
				data.add(daBan4 + "款");
				data.add(daBan4J + "款");
				if ((daBan4 + daBan4J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) daBan4J / (daBan4 + daBan4J) * 100) + "%");
				}
				break;
			case 4:
				data.add("核价中心");
				data.add(intoHeJiaNumber + "款");
				data.add(outflowHeJiaNumber + "款");
				data.add(notFinishHeJia + "款");
				data.add(heSuan5 + "款");
				data.add(heSuan5J + "款");
				if ((heSuan5 + heSuan5J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) heSuan5J / (heSuan5 + heSuan5J) * 100) + "%");
				}
				break;
			case 5:
				data.add("MR部门");
				data.add(intoMrNumber + "款");
				data.add(outflowMrNumber + "款");
				data.add(notFinishMr + "款");
				data.add(mrCheck6 + "款");
				data.add(mrCheck6J + "款");
				if ((mrCheck6 + mrCheck6J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) mrCheck6J / (mrCheck6 + mrCheck6J) * 100) + "%");
				}
				break;
			case 6:
				SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
				data.add("汇报日期：" + sdf1.format(tamp));
				data.add("");
				data.add("流转订单数");
				data.add((notFinishFi + notFinishIt + notFinishPur + notFinishHeJia + notFinishMr) + "款");
				data.add("今日新增订单：" + orderTodayCount + "款" + "    " + "今日订单总数：" + orderSum + "款");
				data.add("");
				data.add("");
				break;

			}
			dataList.add(data);
		}
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddHHmmss");
			String fileName = PathUtil.getTempDownloadPath() + "/" + "Yangyi" + sdf2.format(new Date()) + ".xls";// 生成的excel文件名

			OutputStream os = new FileOutputStream(fileName, true);
			// ExcelUtils.createExcel4OA(os, "大货生产", 0, 0, dataList);
			ExcelUtils.createExcelOADaHuoRate(os, "样衣打版", 0, 0, dataList);
			Struts2Utils.renderText(PathUtil.path2Url(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * lanyu 大货每天及时率统计
	 *
	 * @throws ParseException
	 */
	public void getDaHuoTimelyRate() {
		List dataList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到款号和mr提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DHTC_BY_SQL);
		fsp.set(FSPBean.FSP_ORDER, " order by id ");
		Date date = new Date();
		// Test
		// Date date=sdf.parse("2014-09-22 23:06:16");
		Timestamp tamp = new Timestamp(date.getTime());
		// fsp.set("end_time","2014-07-24 23:07:45");
		// fsp.set("end_time",tamp);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tamp);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		Timestamp tamp1 = new Timestamp(date.getTime());
		// Test
		// fsp.set("begin_time", "2014-07-24");
		// fsp.set("begin_time", tamp1);

		List<LazyDynaMap> basic = manager.getObjectsBySql(fsp);

		fsp.set(FSPBean.FSP_ORDER, " GROUP BY oa_order ORDER BY oa_order ");
		// 得到财务提交时间
		// fsp=new FSPBean();
		// fsp.set(FSPBean.FSP_QUERY_BY_XML,
		// BxDaoImpl.LIST_OA_ORDER_DETAIL_FI_DHTC_BY_SQL);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("type", "3");
		// fsp.set("f_start", tamp1);不应该只求今天开始的订单
		// fsp.set("l_start", tamp);
		fsp.set("wfStep", "c_fi_confirm_2");
		List<LazyDynaMap> fi = manager.getObjectsBySql(fsp);

		// 技术部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_ppc_assign_3");
		List<LazyDynaMap> it = manager.getObjectsBySql(fsp);

		// 采购部 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_fi_pay_4");
		List<LazyDynaMap> pur = manager.getObjectsBySql(fsp);

		// CQC 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_ppc_factoryMsg_5");
		List<LazyDynaMap> cqc = manager.getObjectsBySql(fsp);

		// MQC(QC) 时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_qc_cutting_6");
		List<LazyDynaMap> mqc = manager.getObjectsBySql(fsp);

		// QA 提交时间
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_ppc_confirm_7");
		List<LazyDynaMap> qa = manager.getObjectsBySql(fsp);

		// fi2 确认尾款
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_qc_printing_8");
		List<LazyDynaMap> fi2 = manager.getObjectsBySql(fsp);

		// qa2 发货
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL);
		fsp.set("wfStep", "c_ppc_confirm_9");
		List<LazyDynaMap> qa2 = manager.getObjectsBySql(fsp);

		// times
		// max(wf_real_finish) wf_real_finish, 最后完成时间
		// min(wf_real_finish) f_finish, 第一次完成时间
		// max(wf_real_start) l_start, 最后一次开始时间
		// min(wf_real_start) f_start, 第一次开始时间

		// 对各部门异动订单的计数
		int daoZhang2 = 0;
		int zhiYang3 = 0;
		int mianLiao4 = 0;
		int yanBu5 = 0;
		int cheFeng6 = 0;
		int weiCha7 = 0;
		int weiKuan8 = 0;
		int faHuo9 = 0;
		int daHuoYiDong = 0;
		// 对各部门正常订单的计数
		int daoZhang2J = 0;
		int zhiYang3J = 0;
		int mianLiao4J = 0;
		int yanBu5J = 0;
		int cheFeng6J = 0;
		int weiCha7J = 0;
		int weiKuan8J = 0;
		int faHuo9J = 0;
		Map check = new HashMap();
		// 以下是各部门流进流出的统计
		int intoFiNumber = 0;// 财务确定定金 流进订单数量
		int outflowFiNumber = 0;// 财务确定定金 流出订单数量
		int notFinishFi = 0;

		int intoItNumber = 0;
		int outflowItNumber = 0;
		int notFinishIt = 0;
		int intoCQCNumber = 0;
		int outflowCQCNumber = 0;
		int notFinishCQC = 0;

		int intoPurNumber = 0;
		int outflowPurNumber = 0;
		int notFinishPur = 0;
		int intoMQCNumber = 0;
		int outflowMQCNumber = 0;
		int notFinishMQC = 0;
		int intoQANumber = 0;
		int outflowQANumber = 0;
		int notFinishQA = 0;
		int intoFi2Number = 0;
		int notFinishFi2 = 0;
		int outflowFi2Number = 0;
		int intoQA2Number = 0;
		int outflowQA2Number = 0;
		int notFinishQA2 = 0;
		for (int i = 0; i < basic.size(); i++) {
			// ArrayList<Map> data = new ArrayList<Map>();
			Timestamp t1 = null;// 开始时间
			Timestamp t2 = null;// 结束时间
			long l1 = 0L;// 时差
			long wf_step_duration = 0L;// 当前步骤所需时长

			// 财务部 fi
			String fi_time = "无法计算";
			if (fi.size() > i) {
				wf_step_duration = (long) fi.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = (Timestamp) fi.get(i).get("f_start");
				if (fi.get(i).get("f_finish") != null) {
					t2 = (Timestamp) fi.get(i).get("f_finish");
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowFiNumber++;
					}
				} else {
					notFinishFi++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoFiNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时
						daoZhang2++;
					} else {
						// 未超时
						daoZhang2J++;
					}
				}
			}

			// 技术部 it
			String it_time = "无法计算";
			if (it.size() > i) {
				wf_step_duration = (long) it.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) it.get(i).get("f_start")).getTime());
				if (it.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) it.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowItNumber++;
					}
				} else {
					notFinishIt++;
				}
				l1 = BizUtil.getWorkTimeBetween(t2, t1);
				if (t1.getTime() >= tamp1.getTime()) {
					intoItNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						zhiYang3++;
					} else {
						// 未超时
						zhiYang3J++;
					}
				}
			}
			// 采购部 pur
			String pur_time = "无法计算";
			if (pur.size() > i) {
				wf_step_duration = (long) pur.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) pur.get(i).get("f_start")).getTime());
				if (pur.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) pur.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowPurNumber++;
					}
				} else {
					notFinishPur++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoPurNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						mianLiao4++;
					} else {
						// 未超时
						mianLiao4J++;
					}
				}
			}
			// CQC
			String cqc_time = "无法计算";
			if (cqc.size() > i) {
				wf_step_duration = (long) cqc.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) cqc.get(i).get("f_start")).getTime());
				if (cqc.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) cqc.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowCQCNumber++;
					}
				} else {
					notFinishCQC++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoCQCNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						yanBu5++;
					} else {
						// 未超时
						yanBu5J++;
					}
				}
			}
			// MQC
			String mqc_time = "无法计算";
			if (mqc.size() > i) {
				wf_step_duration = (long) mqc.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) mqc.get(i).get("f_start")).getTime());
				if (t1.getTime() >= tamp1.getTime()) {
					intoMQCNumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						cheFeng6++;
					} else {
						// 未超时
						cheFeng6J++;
					}
				}
				if (mqc.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) mqc.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowMQCNumber++;
					}
				} else {
					notFinishMQC++;
				}
			}
			// QA验货
			String qa_time = "无法计算";
			if (qa.size() > i) {
				wf_step_duration = (long) mqc.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) qa.get(i).get("f_start")).getTime());
				if (qa.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) qa.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowQANumber++;
					}
				} else {
					notFinishQA++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoQANumber++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时
						weiCha7++;
					} else {
						// 未超时
						weiCha7J++;
					}
				}

			}
			// TODO //查询语句需添加
			// 财务确认尾款
			String fi2_time = "无法计算";
			if (fi2.size() > i) {
				wf_step_duration = (long) fi2.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) fi2.get(i).get("f_start")).getTime());
				if (fi2.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) fi2.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowFi2Number++;
					}
				} else {
					notFinishFi2++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoFi2Number++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						weiKuan8++;
					} else {
						// 未超时
						weiKuan8J++;
					}
				}
			}

			// QA发货
			String qa2_time = "无法计算";
			if (qa2.size() > i) {
				wf_step_duration = (long) qa2.get(i).get("wf_step_duration");
				// 没有退回的步骤
				t1 = new Timestamp(((Date) qa2.get(i).get("f_start")).getTime());
				if (qa2.get(i).get("f_finish") != null) {
					t2 = new Timestamp(((Date) qa2.get(i).get("f_finish")).getTime());
					if (t2.getTime() <= tamp.getTime() && t2.getTime() >= tamp1.getTime()) {
						outflowQA2Number++;
					}
				} else {
					notFinishQA2++;
				}
				if (t1.getTime() >= tamp1.getTime()) {
					intoQA2Number++;
					long diff = wf_step_duration - BizUtil.getWorkTimeBetween(tamp, t1);
					if (diff < 0) {
						// 超时

						faHuo9++;
					} else {
						// 未超时
						faHuo9J++;
					}
				}

			}
		}
		// 查询今天新增的订单数量
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_NEW_ORDER_ADD_BY_SQL);
		fsp.set("orderBeginTime", tamp1);
		fsp.set("orderTodayMaxTime", tamp);
		LazyDynaMap orderToday = (LazyDynaMap) manager.getOnlyObjectBySql(fsp);
		int orderTodayCount = 0;
		if (orderToday != null) {
			orderTodayCount = Integer.parseInt(orderToday.get("ordertoday").toString());
		}
		int orderSum = basic.size();
		// 添加序号
		DecimalFormat df = new DecimalFormat("0.##");
		for (int i = 1; i < 10; i++) {
			ArrayList<String> data = new ArrayList<String>();
			data.add(i + "");
			switch (i) {
			case 1:
				data.add("财务部确定定金");
				data.add(intoFiNumber + "款");
				data.add(outflowFiNumber + "款");
				data.add(notFinishFi + "款");
				data.add(daoZhang2 + "款");
				data.add(daoZhang2J + "款");
				if ((daoZhang2 + daoZhang2J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) daoZhang2J / (daoZhang2 + daoZhang2J) * 100) + "%");
				}
				break;
			case 2:
				data.add("技术部");
				data.add(intoItNumber + "款");
				data.add(outflowItNumber + "款");
				data.add(notFinishIt + "款");
				data.add(zhiYang3 + "款");
				data.add(zhiYang3J + "款");
				if ((zhiYang3 + zhiYang3J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) zhiYang3J / (zhiYang3 + zhiYang3J) * 100) + "%");
				}
				break;
			case 3:
				data.add("采购部");
				data.add(intoPurNumber + "款");
				data.add(outflowPurNumber + "款");
				data.add(notFinishPur + "款");
				data.add(mianLiao4 + "款");
				data.add(mianLiao4J + "款");
				if ((mianLiao4 + mianLiao4J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) mianLiao4J / (mianLiao4 + mianLiao4J) * 100) + "%");
				}
				break;
			case 4:
				data.add("CQC");
				data.add(intoCQCNumber + "款");
				data.add(outflowCQCNumber + "款");
				data.add(notFinishCQC + "款");
				data.add(yanBu5 + "款");
				data.add(yanBu5J + "款");
				if ((yanBu5 + yanBu5J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) yanBu5J / (yanBu5 + yanBu5J) * 100) + "%");
				}
				break;
			case 5:
				data.add("MQC");
				data.add(intoMQCNumber + "款");
				data.add(outflowMQCNumber + "款");
				data.add(notFinishMQC + "款");
				data.add(cheFeng6 + "款");
				data.add(cheFeng6J + "款");
				if ((cheFeng6 + cheFeng6J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) cheFeng6J / (cheFeng6 + cheFeng6J) * 100) + "%");
				}
				break;
			case 6:
				data.add("QA");
				data.add(intoQANumber + "款");
				data.add(outflowQANumber + "款");
				data.add(notFinishQA + "款");
				data.add(weiCha7 + "款");
				data.add(weiCha7J + "款");
				if ((weiCha7 + weiCha7J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) weiCha7J / (weiCha7 + weiCha7J) * 100) + "%");
				}
				break;
			case 7:
				data.add("财务部确认尾款");
				data.add(intoFi2Number + "款");
				data.add(outflowFi2Number + "款");
				data.add(notFinishFi2 + "款");
				data.add(weiKuan8 + "款");
				data.add(weiKuan8J + "款");
				if ((weiKuan8 + weiKuan8J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) weiKuan8J / (weiKuan8 + weiKuan8J) * 100) + "%");
				}
				break;
			case 8:
				data.add("QA发货");
				data.add(intoQA2Number + "款");
				data.add(outflowQA2Number + "款");
				data.add(notFinishQA2 + "款");
				data.add(faHuo9 + "款");
				data.add(faHuo9J + "款");
				if ((faHuo9 + faHuo9J) == 0) {
					data.add("");
				} else {
					data.add(df.format((float) faHuo9J / (faHuo9 + faHuo9J) * 100) + "%");
				}
				break;
			case 9:
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
				data.add("汇报日期：" + sdf2.format(tamp));
				data.add("");
				data.add("流转订单数");
				data.add((notFinishFi + notFinishIt + notFinishPur + notFinishCQC + notFinishFi2 + notFinishMQC + notFinishQA + notFinishQA2) + "款");
				data.add("今日新增订单：" + orderTodayCount + "款" + "    " + "今日订单总数：" + orderSum + "款");
				data.add("");
				data.add("");
				break;
			}
			dataList.add(data);
		}
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddHHmmss");
			String fileName = PathUtil.getTempDownloadPath() + "/" + "DaHuo" + sdf2.format(new Date()) + ".xls";// 生成的excel文件名

			OutputStream os = new FileOutputStream(fileName, true);
			// ExcelUtils.createExcel4OA(os, "大货生产", 0, 0, dataList);
			ExcelUtils.createExcelOADaHuoRate(os, "大货生产", 0, 0, dataList);
			Struts2Utils.renderText(PathUtil.path2Url(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 对订单的及时率进行统计
	 *
	 * @author lanyu
	 * @return
	 * @throws ParseException
	 */
	public String timelyRateCount() {
		// 对各部门异动订单的计数
		int daoZhang2 = 0;
		int zhiYang3 = 0;
		int mianLiao4 = 0;
		int yanBu5 = 0;
		int cheFeng6 = 0;
		int weiCha7 = 0;
		int weiKuan8 = 0;
		int faHuo9 = 0;
		int daHuoYiDong = 0;
		// 对各部门正常订单的计数
		int daoZhang2J = 0;
		int zhiYang3J = 0;
		int mianLiao4J = 0;
		int yanBu5J = 0;
		int cheFeng6J = 0;
		int weiCha7J = 0;
		int weiKuan8J = 0;
		int faHuo9J = 0;
		// 1.查询出当前所有未完成的大货订单
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_TIMELYRATECOUNT_BY_SQL);
		fsp.set("type", 3);
		beans = getObjectsBySql(fsp);
		float daHuoOrderTotal = beans.size();

		// Test
		/*
		 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date date=sdf.parse("2014-09-18 23:00:00"); Timestamp tamp=new Timestamp(date.getTime());
		 */
		if (!beans.isEmpty()) {
			for (int i = 0; i < beans.size(); i++) {
				LazyDynaMap bean = beans.get(i);
				long wsd = (long) bean.get("wf_step_duration");
				// Test
				/*
				 * long diff = wsd - BizUtil.getWorkTimeBetween( tamp, (Timestamp)bean.get("wf_real_start"));
				 */
				long diff = wsd - BizUtil.getWorkTimeBetween(new Timestamp(System.currentTimeMillis()), (Timestamp) bean.get("wf_real_start"));
				if (diff < 0) {
					// 超时
					String step = bean.get("wf_step").toString();
					switch (step) {
					case "c_fi_confirm_2":
						daoZhang2++;
						break;
					case "c_ppc_assign_3":
						zhiYang3++;
						break;
					case "c_fi_pay_4":
						mianLiao4++;
						break;
					case "c_ppc_factoryMsg_5":
						yanBu5++;
						break;
					case "c_qc_cutting_6":
						cheFeng6++;
						break;
					case "c_ppc_confirm_7":
						weiCha7++;
						break;
					case "c_qc_printing_8":
						weiKuan8++;
						break;
					case "c_ppc_confirm_9":
						faHuo9++;
						break;
					}
					daHuoYiDong++;
				} else {
					String step = bean.get("wf_step").toString();
					switch (step) {
					case "c_fi_confirm_2":
						daoZhang2J++;
						break;
					case "c_ppc_assign_3":
						zhiYang3J++;
						break;
					case "c_fi_pay_4":
						mianLiao4J++;
						break;
					case "c_ppc_factoryMsg_5":
						yanBu5J++;
						break;
					case "c_qc_cutting_6":
						cheFeng6J++;
						break;
					case "c_ppc_confirm_7":
						weiCha7J++;
						break;
					case "c_qc_printing_8":
						weiKuan8J++;
						break;
					case "c_ppc_confirm_9":
						faHuo9J++;
						break;
					}

				}
			}
		}

		// 3.查询各个步骤的订单数量统计
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_STEP_ORDER_COUNT_BY_SQL);
		fsp.set("type", 3);
		stepCount = manager.getObjectsBySql(fsp);
		// 4.计算各个部门的工作及时率
		float daoZhangTotal = 0;
		float daoZhangRate = 0;
		float zhiYangTotal = 0;
		float zhiYangRate = 0;
		float mianLiaoTotal = 0;
		float mianLiaoRate = 0;
		float yanBuTotal = 0;
		float yanBuRate = 0;
		float cheFengTotal = 0;
		float cheFengRate = 0;
		float weiChaTotal = 0;
		float weiChaRate = 0;
		float weiKuanTotal = 0;
		float weiKuanRate = 0;
		float faHuoTotal = 0;
		float faHuoRate = 0;
		DecimalFormat df = new DecimalFormat("0.##");
		if (!stepCount.isEmpty()) {
			for (int i = 0; i < stepCount.size(); i++) {
				LazyDynaMap bean = stepCount.get(i);
				String step = bean.get("wf_step").toString();
				switch (step) {
				case "c_fi_confirm_2":
					daoZhangTotal = Integer.parseInt(bean.get("stepcount").toString());
					daoZhangRate = daoZhang2J / daoZhangTotal * 100;
					fsp.set("daoZhangRate", df.format(daoZhangRate));
					break;
				case "c_ppc_assign_3":
					zhiYangTotal = Integer.parseInt(bean.get("stepcount").toString());
					zhiYangRate = zhiYang3J / zhiYangTotal * 100;
					fsp.set("zhiYangRate", df.format(zhiYangRate));
					break;
				case "c_fi_pay_4":
					mianLiaoTotal = Integer.parseInt(bean.get("stepcount").toString());
					mianLiaoRate = mianLiao4J / mianLiaoTotal * 100;
					fsp.set("mianLiaoRate", df.format(mianLiaoRate));
					break;
				case "c_ppc_factoryMsg_5": {
					yanBuTotal = Integer.parseInt(bean.get("stepcount").toString());
					yanBuRate = yanBu5J / yanBuTotal * 100;
					fsp.set("yanBuRate", df.format(yanBuRate));
				}
					break;
				case "c_qc_cutting_6":
					cheFengTotal = Integer.parseInt(bean.get("stepcount").toString());
					cheFengRate = cheFeng6J / cheFengTotal * 100;
					fsp.set("cheFengRate", df.format(cheFengRate));
					break;
				case "c_ppc_confirm_7":
					weiChaTotal = Integer.parseInt(bean.get("stepcount").toString());
					weiChaRate = weiCha7J / weiChaTotal * 100;
					fsp.set("weiChaRate", df.format(weiChaRate));
					break;
				case "c_qc_printing_8":
					weiKuanTotal = Integer.parseInt(bean.get("stepcount").toString());
					weiKuanRate = weiKuan8J / weiKuanTotal * 100;
					fsp.set("weiKuanRate", df.format(weiKuanRate));
					weiKuan8++;
					break;
				case "c_ppc_confirm_9":
					faHuoTotal = Integer.parseInt(bean.get("stepcount").toString());
					faHuoRate = faHuo9J / faHuoTotal * 100;
					fsp.set("faHuoRate", df.format(faHuoRate));
					faHuo9++;
					break;
				}
			}
		}
		fsp.set("daoZhang2", daoZhang2);
		fsp.set("zhiYang3", zhiYang3);
		fsp.set("yanBu5", yanBu5);
		fsp.set("mianLiao4", mianLiao4);
		fsp.set("cheFeng6", cheFeng6);
		fsp.set("weiCha7", weiCha7);
		fsp.set("weiKuan8", weiKuan8);
		fsp.set("faHuo9", faHuo9);
		fsp.set("timelyTotal", daHuoYiDong);
		float timelyRate = (daHuoOrderTotal - daHuoYiDong) / daHuoOrderTotal;
		fsp.set("timelyRate", df.format(timelyRate * 100));
		return "timelyRateCount";
	}

	/**
	 * 统计样衣打板的
	 */
	public String daBanTimelyRateCount() {
		int dingJin2 = 0;
		int mianLiao3 = 0;
		int daBan4 = 0;
		int heSuan5 = 0;
		int mrCheck6 = 0;
		int daBanYiDong = 0;
		int dingJin2J = 0;
		int mianLiao3J = 0;
		int daBan4J = 0;
		int heSuan5J = 0;
		int mrCheck6J = 0;
		// 1.查询出当前所有未完成的大货订单
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_TIMELYRATECOUNT_BY_SQL);
		fsp.set("type", 2);
		beans = getObjectsBySql(fsp);
		float daBanOrderTotal = beans.size();
		if (!beans.isEmpty()) {
			for (int i = 0; i < beans.size(); i++) {
				LazyDynaMap bean = beans.get(i);
				long wsd = (long) bean.get("wf_step_duration");
				long diff = wsd - BizUtil.getWorkTimeBetween(new Timestamp(System.currentTimeMillis()), (Timestamp) bean.get("wf_real_start"));
				if (diff < 0) {
					// 超时
					String step = bean.get("wf_step").toString();
					switch (step) {
					case "b_fi_confirm_2":
						dingJin2++;
						break;
					case "b_ppc_confirm_3":
						mianLiao3++;
						break;
					case "b_pur_confirm_4":
						daBan4++;
						break;
					case "b_ppc_confirm_5":
						heSuan5++;
						break;
					case "b_qc_confirm_6":
						mrCheck6++;
						break;
					}
					daBanYiDong++;
				} else {
					String step = bean.get("wf_step").toString();
					switch (step) {
					case "b_fi_confirm_2":
						dingJin2J++;
						break;
					case "b_ppc_confirm_3":
						mianLiao3J++;
						break;
					case "b_pur_confirm_4":
						daBan4J++;
						break;
					case "b_ppc_confirm_5":
						heSuan5J++;
						break;
					case "b_qc_confirm_6":
						mrCheck6J++;
						break;
					}

				}
			}
		}

		// 3.查询打版各个步骤的订单数量统计
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_STEP_ORDER_COUNT_BY_SQL);
		fsp.set("type", 2);
		stepCount = manager.getObjectsBySql(fsp);
		float dingJinTotal = 0;
		float dingJinRate = 0;
		float mianLiaoTotal = 0;
		float mianLiaoRate = 0;
		float daBanTotal = 0;
		float daBanRate = 0;
		float heSuanTotal = 0;
		float heSuanRate = 0;
		float mrCheckTotal = 0;
		float mrCheckRate = 0;
		DecimalFormat df = new DecimalFormat("0.##");
		if (!stepCount.isEmpty()) {
			for (int i = 0; i < stepCount.size(); i++) {
				LazyDynaMap bean = stepCount.get(i);
				String step = bean.get("wf_step").toString();
				switch (step) {
				case "b_fi_confirm_2":
					dingJinTotal = Integer.parseInt(bean.get("stepcount").toString());
					dingJinRate = dingJin2J / dingJinTotal * 100;
					fsp.set("dingJinRate", df.format(dingJinRate));
					break;
				case "b_ppc_confirm_3":
					mianLiaoTotal = Integer.parseInt(bean.get("stepcount").toString());
					mianLiaoRate = mianLiao3J / mianLiaoTotal * 100;
					fsp.set("mianLiaoRate", df.format(mianLiaoRate));
					break;
				case "b_pur_confirm_4":
					daBanTotal = Integer.parseInt(bean.get("stepcount").toString());
					daBanRate = daBan4J / daBanTotal * 100;
					fsp.set("daBanRate", df.format(daBanRate));
					break;
				case "b_ppc_confirm_5": {
					heSuanTotal = Integer.parseInt(bean.get("stepcount").toString());
					heSuanRate = heSuan5J / heSuanTotal * 100;
					fsp.set("heSuanRate", df.format(heSuanRate));
				}
					break;
				case "b_qc_confirm_6":
					mrCheckTotal = Integer.parseInt(bean.get("stepcount").toString());
					mrCheckRate = mrCheck6J / mrCheckTotal * 100;
					fsp.set("mrCheckRate", df.format(mrCheckRate));
					break;
				}
			}
		}
		fsp.set("dingJin2", dingJin2);
		fsp.set("mianLiao3", mianLiao3);
		fsp.set("daBan4", daBan4);
		fsp.set("heSuan5", heSuan5);
		fsp.set("mrCheck6", mrCheck6);
		fsp.set("timelyTotal", daBanYiDong);
		float timelyRate = (daBanOrderTotal - daBanYiDong) / daBanOrderTotal;
		fsp.set("timelyRate", df.format(timelyRate * 100));

		return "daBanTimelyRateCount";
	}

	// 订单管理-订单列表 update by 范蠡 2015-01-30
	public String orderList() throws ParseException {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_LIST);
		if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
			fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
		}
		if(fsp.getMap().get("odel_wf_real_start") != null && !"".equals(fsp.getMap().get("odel_wf_real_start"))){
			fsp.set("odel_wf_step", "c_mr_improve_2");
		}
		if(fsp.getMap().get("odelqa_wf_real_start") != null && !"".equals(fsp.getMap().get("odelqa_wf_real_start"))){
			fsp.set("odelqa_wf_step", "c_ppc_confirm_7");
		}
		
		Struts2Utils.getRequest().setAttribute("orderColor", orderColor);
		if(fsp.getMap().get("yxjOrderHid") != null && "3".equals(fsp.getMap().get("yxjOrderHid"))){
			
			fsp.set(FSPBean.FSP_ORDER, " order by oder.begin_time desc");
			fsp.getMap().put("yxjOrderHid", "4");	
		}else if(fsp.getMap().get("yxjOrderHid") != null && "4".equals(fsp.getMap().get("yxjOrderHid"))){
			fsp.set(FSPBean.FSP_ORDER, " order by oder.begin_time");
			fsp.getMap().put("yxjOrderHid", "3");	
		}  
		
		
		beans = getObjectsBySql(fsp);
		Map<String, String> statisticsMap = new HashMap<String, String>();
		
		//toc部分声明变量
		long newTime = BizUtil.getWorkTime(new Timestamp(new Date().getTime())).getTime();
		//统计部分声明变量
		int count = 0;
		int beansNums = beans.size();
		int greenNum = 0;
		int orangeNum = 0;
		int redNum = 0;
		int blackNum = 0;
		int blueNum = 0;
		
		List<LazyDynaMap> colorList = new ArrayList<LazyDynaMap>();
		
		for(int i = 0; i < beans.size(); i++){
//			float data = 0;
//			int data1 = 0;
//			//toc部分
//			float order_Cycle = 0;
////					(beans.get(i).get("sell_ready_time") != null ? (float)beans.get(i).get("sell_ready_time") : 0) 
////					+ (beans.get(i).get("standard_time") != null ? (float)beans.get(i).get("standard_time") : 0)
////					+ (beans.get(i).get("craft_time") != null ? (float)beans.get(i).get("craft_time") : 0);
//			
//			
//			if(order_Cycle > 0){
//				long except_finish = beans.get(i).get("goods_time") != null ? BizUtil.getWorkTime((Timestamp)beans.get(i).get("goods_time")).getTime() : 0;
//				data = newTime - (except_finish - order_Cycle  + (9 * 60 * 60 * 1000)) / order_Cycle ;
//						
//				data = new BigDecimal(data).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();	
////				data1  = (int)(data * 100);
//				String d = data+"";
//				data = Float.parseFloat(d.substring(0, d.indexOf(".")+3)) * 100;
//				
//				cycle = (int)data;
//			}
			
			
			//订单周期
			Long sellReadyTime = beans.get(i).get("sell_ready_time")==null?0:(Long) beans.get(i).get("sell_ready_time");
			Long standardTime = beans.get(i).get("standard_time") ==null?0:(Long) beans.get(i).get("standard_time");
			Long craftTime = beans.get(i).get("craft_time") ==null?0:(Long) beans.get(i).get("craft_time");
			Long orderTime = (sellReadyTime+standardTime+craftTime)/9*24;
			//交期
			Timestamp goodsTime = (Timestamp) beans.get(i).get("goods_time");
			//当前工作时间
			Timestamp workTime = BizUtil.getOperatingTime(new Timestamp(new Date().getTime()));
			Integer cycle = (int) ((workTime.getTime()-goodsTime.getTime()+orderTime - 60*60*1000*24d)/orderTime*100);
			
			
			beans.get(i).set("data", cycle);
			if(orderColor != null && !"".equals(orderColor)){
				if("-1".equals(orderColor)){
					if(cycle < 0){
						blueNum++;
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("0".equals(orderColor)){
					if(cycle > 0 && cycle <= 33){
						greenNum++;
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("33".equals(orderColor)){
					if(cycle > 33 && cycle <= 66){
						orangeNum++;
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("66".equals(orderColor)){
					if(cycle > 66 && cycle <= 100){
						redNum++;
						colorList.add(beans.get(i));
					}
					continue;
				}
				if("100".equals(orderColor)){
					if(cycle > 100){
						blackNum++;
						colorList.add(beans.get(i));
					}
					continue;
				}
			}else{
				if(cycle < 0){
					blueNum++;
				}else if(cycle > 0 && cycle <= 33){
					greenNum++;
				}else if(cycle > 33 && cycle <= 66){
					orangeNum++;
				}else if(cycle > 66 && cycle <= 100){
					redNum++;
				}else if(cycle > 100){
					blackNum++;
				}
			}
			//计算统计部分
			count += Double.parseDouble(beans.get(i).get("want_cnt") != null ? beans.get(i).get("want_cnt").toString() : "0");
			
		}
		
		if(orderColor != null && !"".equals(orderColor)){
			beans = colorList;
		}
		
		if(fsp.getMap().get("yxjOrderHid") == null || "".equals(fsp.getMap().get("yxjOrderHid"))
				 || "1".equals(fsp.getMap().get("yxjOrderHid"))){
			for(int i = 0; i < beans.size(); i++){
				for (int j = i; j < beans.size(); j++)
	            {
	                if (Integer.parseInt(beans.get(i).get("data").toString()) < Integer.parseInt(beans.get(j).get("data").toString()))
	                {
	                	LazyDynaMap temp = beans.get(i);
	                	beans.set(i, beans.get(j));
	                	beans.set(j, temp);
	                }
	            }
			}
			fsp.getMap().put("yxjOrderHid", "2");
		}else if(fsp.getMap().get("yxjOrderHid") == null || "".equals(fsp.getMap().get("yxjOrderHid"))
				 || "2".equals(fsp.getMap().get("yxjOrderHid"))){
			for(int i = 0; i < beans.size(); i++){
				for (int j = i; j < beans.size(); j++)
	            {
	                if (Integer.parseInt(beans.get(i).get("data").toString()) > Integer.parseInt(beans.get(j).get("data").toString()))
	                {
	                	LazyDynaMap temp = beans.get(i);
	                	beans.set(i, beans.get(j));
	                	beans.set(j, temp);
	                }
	            }
			}
			fsp.getMap().put("yxjOrderHid", "1");
		} 
		
		
		
		 
		statisticsMap.put("want_count", count+"");
		statisticsMap.put("greenNum", greenNum+""); //new BigDecimal((greenNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
		statisticsMap.put("orangeNum", orangeNum+"");//new BigDecimal((orangeNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
		statisticsMap.put("redNum", redNum+"");
		statisticsMap.put("blueNum", blueNum+"");
		statisticsMap.put("blackNum", blackNum+"");

		if(beansNums > 0){
			statisticsMap.put("blue", (int)new BigDecimal((blueNum/(float)beansNums) * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue()+"");
			statisticsMap.put("green", (int)new BigDecimal((greenNum/(float)beansNums) * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue()+""); //new BigDecimal((greenNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
			statisticsMap.put("orange", (int)new BigDecimal((orangeNum/(float)beansNums) * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue()+"");//new BigDecimal((orangeNum/beansNums) * 10).setScale(5, BigDecimal.ROUND_HALF_UP).floatValue()
			statisticsMap.put("red", (int)new BigDecimal((redNum/(float)beansNums) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue()+"");
			statisticsMap.put("black", (int)new BigDecimal((blackNum/(float)beansNums) * 100).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue()+"");
		}
		Struts2Utils.getRequest().setAttribute("statisticsMap", statisticsMap);

		
		fsp.setRecordCount(beans.size());
		fsp.setPageSize(fsp.getPageSize());
		fsp.setPageNo(fsp.getPageNo());
		
		if(beans.size() >  (fsp.getPageNo()-1) * fsp.getPageSize() + fsp.getPageSize()){
			beans = beans.subList((fsp.getPageNo()-1) * fsp.getPageSize(), (fsp.getPageNo()-1) * fsp.getPageSize() + fsp.getPageSize());
		}else{
			beans = beans.subList((fsp.getPageNo()-1) * fsp.getPageSize(), beans.size());
		}
		
		return "orderList";
	}

    /**
     * 用于订单进度报表计算耗时，与是否超时状态
     * @param ldm
     * @param finishTime
     * @param startTime
     * @return
     */
    public long computationProgress(LazyDynaMap ldm, Date finishTime, Date startTime, long durationTime,
                                    int state, String ldmKey){
        long haoshiTime = 0;
        if(finishTime != null && startTime != null){
            haoshiTime = Math.abs(BizUtil.getWorkTimeBetween((Timestamp) finishTime, (Timestamp) startTime));
            if(state == 0){
                return haoshiTime;
            }else{
                long cmpTime = haoshiTime - durationTime;
                String color="";
                if(cmpTime<=0){
                    color="#309865";//表示 改节点没有超时 颜色为绿色
                    ldm.set(ldmKey, "green");
                }else if(cmpTime>0 && cmpTime<=4*60*60*1000){
                    color="#FFFC05";//表示该节点已经超时，超时的时间少于4小时 为黄色
                    ldm.set(ldmKey, "yellow");
                }else if(cmpTime>4*60*60*1000){
                    color="#FC0303";//表示该节点已经超时，超时时间大于4小时 为红色
                    ldm.set(ldmKey, "red");
                }
            }
        }

        return haoshiTime;
    }

    public List<LazyDynaMap> rtnOrogressReport(){
        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_LIST_JINDU_INFO);
        if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
            fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
        }
        String mrName = fsp.getMap().get("mr_name") != null ? fsp.getMap().get("mr_name").toString() : "";
        String orderType = fsp.getMap().get("type") != null ? fsp.getMap().get("type").toString() : "3";

        if(fsp.getMap().get("type") == null){
            fsp.set("type", orderType);
        }

        if(fsp.getMap().get("wf_step") != null && !"".equals(fsp.getMap().get("wf_step"))
                && fsp.getMap().get("operator") != null && !"".equals(fsp.getMap().get("operator"))){
            switch (fsp.getMap().get("wf_step").toString()){
                case "c_mr_improve_2":
                    fsp.setStaticSqlPart(" and mrdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' and mrdel_operator = '"+fsp.getMap().get("operator").toString()+"'");
                    break;
                case "c_ppc_assign_3":
                    fsp.setStaticSqlPart(" and jshdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' and jshdel_operator = '"+fsp.getMap().get("operator").toString()+"'");
                    break;
                case "c_fi_pay_4":
                    fsp.setStaticSqlPart(" and fidel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(fidel_worker) then fidel_operator = '"+fsp.getMap().get("operator").toString()+"' else fidel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "c_ppc_factoryMsg_5":
                    fsp.setStaticSqlPart(" and cqcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(cqcdel_worker) then cqcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cqcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "c_qc_cutting_6":
                    fsp.setStaticSqlPart(" and tpedel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(tpedel_worker) then tpedel_operator = '"+fsp.getMap().get("operator").toString()+"' else tpedel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "c_ppc_confirm_7":
                    fsp.setStaticSqlPart(" and qadel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(qadel_worker) then qadel_operator = '"+fsp.getMap().get("operator").toString()+"' else qadel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "c_qc_printing_8":
                    fsp.setStaticSqlPart(" and cwdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(cwdel_worker) then cwdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cwdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "c_ppc_confirm_9":
                    fsp.setStaticSqlPart(" and wldel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(wldel_worker) then wldel_operator = '"+fsp.getMap().get("operator").toString()+"' else wldel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "b_mr_improve_2":
                    fsp.setStaticSqlPart(" and mrdb_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' and mrdel_operator = '"+fsp.getMap().get("operator").toString()+"'");
                    break;
                case "b_ppc_confirm_3":
                    fsp.setStaticSqlPart(" and ppcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(ppcdel_worker) then ppcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else ppcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "b_pur_confirm_4":
                    fsp.setStaticSqlPart(" and jsdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(jsdel_worker) then jsdel_operator = '"+fsp.getMap().get("operator").toString()+"' else jsdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "b_ppc_confirm_5":
                    fsp.setStaticSqlPart(" and cqdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(cqdel_worker) then cqdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cqdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                case "b_qc_confirm_6":
                    fsp.setStaticSqlPart(" and bqcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' AND case when ISNULL(bqcdel_worker) then bqcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else bqcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end ");
                    break;
                default:
                    break;
            }
        }else{
            fsp.setStaticSqlPart("");
        }


//        if(StringUtils.isNotBlank(mrName)){
//            fsp.setStaticSqlPart("and CONCAT(ood.worker,\" \",ood.operator) like (\"%"+mrName+"%");
//        }else{
//            fsp.setStaticSqlPart("");
//        }
        beans = getObjectsBySql(fsp);
        return beans;
    }
    
    
    
    public void liuruTime(FSPBean fsp, StringBuffer sb, String wf_real_start, String wf_real_finish){
    	if(fsp.getMap().get("start_time1") != null && !"".equals(fsp.getMap().get("start_time1"))){
    		sb.append(" and "+wf_real_start+" > '"+fsp.getMap().get("start_time1").toString()+"' ");
    	}
    	
    	if(fsp.getMap().get("start_time2") != null && !"".equals(fsp.getMap().get("start_time2"))){
    		sb.append(" and "+wf_real_start+" < '"+fsp.getMap().get("start_time2").toString()+"' ");
    	}
    	
    	
    	if(fsp.getMap().get("end_time1") != null && !"".equals(fsp.getMap().get("end_time1"))){
    		sb.append(" and "+wf_real_finish+" > '"+fsp.getMap().get("end_time1").toString()+"' ");
    	}
    	
    	if(fsp.getMap().get("end_time2") != null && !"".equals(fsp.getMap().get("end_time2"))){
    		sb.append(" and "+wf_real_finish+" < '"+fsp.getMap().get("end_time2").toString()+"' ");
    	}
    }

    /**
     * 订单进度报表
     * @return
     * @author 范蠡
     */
	public String orderProgressReport(){
        int [] arrayDH = new int[8];
        int [] arrayDB = new int[5];


        int count = 0;
        int tpeCount = 0;
        long huizong_sum = 0;
        long avgMRDB = 0;
        long avgPurDB = 0;
        long avgTechnologyDB = 0;
        long avgHeJiaDB = 0;
        long avgMrCheckDB = 0;

        long avgMRDH = 0;
        long avgTechnologyDH = 0;
        long avgPurDH = 0;
        long avgCQCDH = 0;
        long avgTPEDH = 0;
        long avgQADH = 0;
        long avgFinanceDH = 0;
        long avgLogisticsDH = 0;
        long qualifiedNumSumDH = 0;
        long unqualifiedNumSumDH = 0;

        long haoshi = 0;

        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_LIST_JINDU_INFO);
        if (!WebUtil.ifAdmin() && !WebUtil.ifManager()) { // 如果不是管理员就要加上查询条件
            fsp.set("his_opt", WebUtil.getCurrentLoginBx().getLoginName());
        }
        String mrName = fsp.getMap().get("mr_name") != null ? fsp.getMap().get("mr_name").toString() : "";
        String orderType = fsp.getMap().get("type") != null ? fsp.getMap().get("type").toString() : "3";

        if(fsp.getMap().get("type") == null){
            fsp.set("type", orderType);
        }

        StringBuffer sb = new StringBuffer();
        if(fsp.getMap().get("wf_step") != null && !"".equals(fsp.getMap().get("wf_step"))){
            switch (fsp.getMap().get("wf_step").toString()){
                case "c_mr_improve_2":
                	sb = new StringBuffer();
                	sb.append(" and mrdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" and mrdel_operator = '"+fsp.getMap().get("operator").toString()+"' ");
                	}
                	liuruTime(fsp, sb, "mrdel_wf_real_start", "mrdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_ppc_assign_3":
                    sb = new StringBuffer();
                	sb.append(" and jshdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" and jshdel_operator = '"+fsp.getMap().get("operator").toString()+"' ");
                	}
                	liuruTime(fsp, sb, "jshdel_wf_real_start", "jshdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_fi_pay_4":
                    sb = new StringBuffer();
                	sb.append(" and fidel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(fidel_worker) then fidel_operator = '"+fsp.getMap().get("operator").toString()+"' else fidel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "fidel_wf_real_start", "fidel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_ppc_factoryMsg_5":
                    sb = new StringBuffer();
                	sb.append(" and cqcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(cqcdel_worker) then cqcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cqcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "cqc_wf_real_start", "cqc_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_qc_cutting_6":
                    sb = new StringBuffer();
                	sb.append(" and tpedel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(tpedel_worker) then tpedel_operator = '"+fsp.getMap().get("operator").toString()+"' else tpedel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "tpedel_wf_real_start", "tpedel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_ppc_confirm_7":
                    sb = new StringBuffer();
                	sb.append(" and qadel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(qadel_worker) then qadel_operator = '"+fsp.getMap().get("operator").toString()+"' else qadel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "qadel_wf_real_start", "qadel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_qc_printing_8":
                    sb = new StringBuffer();
                	sb.append(" and cwdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(cwdel_worker) then cwdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cwdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "cwdel_wf_real_start", "cwdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "c_ppc_confirm_9":
                	sb = new StringBuffer();
                	sb.append(" and wldel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(wldel_worker) then wldel_operator = '"+fsp.getMap().get("operator").toString()+"' else wldel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "wldel_wf_real_start", "wldel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "b_mr_improve_2":
                    sb = new StringBuffer();
                	sb.append(" and mrdb_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" and mrdb_operator = '"+fsp.getMap().get("operator").toString()+"' ");
                	}
                	
                	liuruTime(fsp, sb, "mrdb_wf_real_start", "mrdb_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "b_ppc_confirm_3":
                    sb = new StringBuffer();
                	sb.append(" and ppcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(ppcdel_worker) then ppcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else ppcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "ppcdel_wf_real_start", "ppcdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "b_pur_confirm_4":
                    sb = new StringBuffer();
                	sb.append(" and jsdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(jsdel_worker) then jsdel_operator = '"+fsp.getMap().get("operator").toString()+"' else jsdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "jsdel_wf_real_start", "jsdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "b_ppc_confirm_5":
                    sb = new StringBuffer();
                	sb.append(" and cqdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(cqdel_worker) then cqdel_operator = '"+fsp.getMap().get("operator").toString()+"' else cqdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "cqdel_wf_real_start", "cqdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                case "b_qc_confirm_6":
                    sb = new StringBuffer();
                	sb.append(" and bqcdel_wf_step = '"+fsp.getMap().get("wf_step").toString()+"' ");
                	if(fsp.getMap().get("operator") != null && !"all".equals(fsp.getMap().get("operator"))){
                		sb.append(" AND case when ISNULL(bqcdel_worker) then bqcdel_operator = '"+fsp.getMap().get("operator").toString()+"' else bqcdel_worker = '"+fsp.getMap().get("operator").toString()+"' end  ");
                	}
                	
                	liuruTime(fsp, sb, "bqcdel_wf_real_start", "bqcdel_wf_real_finish");
                    fsp.setStaticSqlPart(sb.toString());
                    break;
                default:
                    break;
            }
        }else{
            fsp.setStaticSqlPart("");
        }


//        if(StringUtils.isNotBlank(mrName)){
//            fsp.setStaticSqlPart("and CONCAT(ood.worker,\" \",ood.operator) like (\"%"+mrName+"%");
//        }else{
//            fsp.setStaticSqlPart("");
//        }
        beans = getObjectsBySql(fsp);

        boolean isBack = false;

        for(int i = 0; i < beans.size(); i++){
            if(orderType != null && orderType.equals("3")){  //大货生产
                long huizong = 0;
                isBack = false;
                haoshi = 0;
                if(beans.get(i).get("mrdel_wf_step_duration") != null && (long)beans.get(i).get("mrdel_wf_step_duration") > 0){ //大货详情
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("mrdel_wf_real_finish"), (Date)beans.get(i).get("mrdel_wf_real_start"), (long)beans.get(i).get("mrdel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[0] += 1;
                        avgMRDH += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("jshdel_wf_step_duration") != null && (long)beans.get(i).get("jshdel_wf_step_duration") > 0){  //技术

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("jshdel_wf_real_finish"), (Date)beans.get(i).get("jshdel_wf_real_start"), (long)beans.get(i).get("jshdel_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[1] += 1;
                        avgTechnologyDH += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("fidel_wf_step_duration") != null && (long)beans.get(i).get("fidel_wf_step_duration") > 0){  //采购
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("fidel_wf_real_finish"), (Date)beans.get(i).get("fidel_wf_real_start"), (long)beans.get(i).get("fidel_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[2] += 1;
                        avgPurDH += haoshi;
                        huizong += haoshi;
                    }

                }

                if(beans.get(i).get("cqc_wf_step_duration") != null && (long)beans.get(i).get("cqc_wf_step_duration") > 0){  //CQC

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cqc_wf_real_finish"), (Date)beans.get(i).get("cqc_wf_real_start"), (long)beans.get(i).get("cqc_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[3] += 1;
                        avgCQCDH += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("tpedel_wf_step_duration") != null && (long)beans.get(i).get("tpedel_wf_step_duration") > 0){  //tpe

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("tpedel_wf_real_finish"), (Date)beans.get(i).get("tpedel_wf_real_start"), (long)beans.get(i).get("tpedel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[4] += 1;
                        avgTPEDH += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("qadel_wf_step_duration") != null && (long)beans.get(i).get("qadel_wf_step_duration") > 0){  //QA
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("qadel_wf_real_finish"), (Date)beans.get(i).get("qadel_wf_real_start"), (long)beans.get(i).get("qadel_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[5] += 1;
                        avgQADH += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("cwdel_wf_step_duration") != null && (long)beans.get(i).get("cwdel_wf_step_duration") > 0){  //采购

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cwdel_wf_real_finish"), (Date)beans.get(i).get("cwdel_wf_real_start"), (long)beans.get(i).get("cwdel_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[6] += 1;
                        avgFinanceDH += haoshi;
                        huizong += haoshi;
                    }

                }

                if(beans.get(i).get("wldel_wf_step_duration") != null && (long)beans.get(i).get("wldel_wf_step_duration") > 0){  //发货

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("wldel_wf_real_finish"), (Date)beans.get(i).get("wldel_wf_real_start"), (long)beans.get(i).get("wldel_wf_step_duration"), 0, null);

                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[7] += 1;
                        avgLogisticsDH += haoshi;
                        huizong += haoshi;
                    }
                }
                huizong_sum += huizong;
            }else if(orderType != null && orderType.equals("2")){
                long huizong = 0;
                isBack = false;
                if(beans.get(i).get("mrdb_wf_step_duration") != null && (long)beans.get(i).get("mrdb_wf_step_duration") > 0){ //MR用时

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("mrdb_wf_real_finish"), (Date)beans.get(i).get("mrdb_wf_real_start"), (long)beans.get(i).get("mrdb_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[0] += 1;
                        avgMRDB += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("ppcdel_wf_step_duration") != null && (long)beans.get(i).get("ppcdel_wf_step_duration") > 0){ //采购用时

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("ppcdel_wf_real_finish"), (Date)beans.get(i).get("ppcdel_wf_real_start"), (long)beans.get(i).get("ppcdel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[1] += 1;
                        avgPurDB += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("jsdel_wf_step_duration") != null && (long)beans.get(i).get("jsdel_wf_step_duration") > 0){ //技术用时

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("jsdel_wf_real_finish"), (Date)beans.get(i).get("jsdel_wf_real_start"), (long)beans.get(i).get("jsdel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[2] += 1;
                        avgTechnologyDB += haoshi;
                        huizong += haoshi;
                    }
                }

                if(beans.get(i).get("cqdel_wf_step_duration") != null && (long)beans.get(i).get("cqdel_wf_step_duration") > 0){ //核价用时

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cqdel_wf_real_finish"), (Date)beans.get(i).get("cqdel_wf_real_start"), (long)beans.get(i).get("cqdel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[3] += 1;
                        avgHeJiaDB += haoshi;
                        huizong += haoshi;
                    }
                }
                if(beans.get(i).get("bqcdel_wf_step_duration") != null && (long)beans.get(i).get("bqcdel_wf_step_duration") > 0){ //MR确认用时

                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("bqcdel_wf_real_finish"), (Date)beans.get(i).get("bqcdel_wf_real_start"), (long)beans.get(i).get("bqcdel_wf_step_duration"), 0, null);
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[4] += 1;
                        avgMrCheckDB += haoshi;
                        huizong += haoshi;
                    }
                }


            }

            count += getOrdreNum(beans.get(i).get("num_info") != null ? beans.get(i).get("num_info").toString() : null);
            tpeCount += beans.get(i).get("sewing_total") != null ? Double.parseDouble(beans.get(i).get("sewing_total").toString()) : 0;
            qualifiedNumSumDH += beans.get(i).get("qualified_total") != null ? Double.parseDouble(beans.get(i).get("qualified_total").toString()) : 0;
            unqualifiedNumSumDH += beans.get(i).get("unqualified_total") != null ? Double.parseDouble(beans.get(i).get("unqualified_total").toString()) : 0;
        }
        Map<String, String> mapCount = new HashMap<String, String>();
        mapCount.put("avgMRDB", WebUtil.getTimeDisPlayExcel(avgMRDB > 0 ? avgMRDB / arrayDB[0] : 0));
        mapCount.put("avgPurDB", WebUtil.getTimeDisPlayExcel(avgPurDB > 0 ? avgPurDB/arrayDB[1] : 0));
        mapCount.put("avgTechnologyDB", WebUtil.getTimeDisPlayExcel(avgTechnologyDB > 0 ? avgTechnologyDB/arrayDB[2] : 0));
        mapCount.put("avgHeJiaDB", WebUtil.getTimeDisPlayExcel(avgHeJiaDB > 0 ? avgHeJiaDB/arrayDB[3] : 0));
        mapCount.put("avgMrCheckDB", WebUtil.getTimeDisPlayExcel(avgMrCheckDB > 0 ?  avgMrCheckDB/arrayDB[4] : 0));
        long avgOrderSumDB = (avgMRDB > 0 ? avgMRDB / arrayDB[0] : 0) + (avgPurDB > 0 ? avgPurDB/arrayDB[1] : 0)
                +(avgTechnologyDB > 0 ? avgTechnologyDB/arrayDB[2] : 0) + (avgHeJiaDB > 0 ? avgHeJiaDB/arrayDB[3] : 0)
                +(avgMrCheckDB > 0 ?  avgMrCheckDB/arrayDB[4] : 0);
        mapCount.put("avgOrderSumDB", WebUtil.getTimeDisPlayExcel(avgOrderSumDB));
        mapCount.put("orderNumSumDB", count > 0 ? count + "": "");

        mapCount.put("avgMRDH", WebUtil.getTimeDisPlayExcel(avgMRDH > 0 ? avgMRDH / arrayDH[0] : 0));
        mapCount.put("avgTechnologyDH", WebUtil.getTimeDisPlayExcel(avgTechnologyDH > 0 ? avgTechnologyDH/arrayDH[1] : 0));
        mapCount.put("avgPurDH", WebUtil.getTimeDisPlayExcel(avgPurDH > 0 ? avgPurDH/arrayDH[2] : 0));
        mapCount.put("avgCQCDH", WebUtil.getTimeDisPlayExcel(avgCQCDH > 0 ? avgCQCDH / arrayDH[3] : 0));
        mapCount.put("avgTPEDH", WebUtil.getTimeDisPlayExcel(avgTPEDH > 0 ?  avgTPEDH/arrayDH[4] : 0));
        mapCount.put("avgQADH", WebUtil.getTimeDisPlayExcel(avgQADH > 0 ?  avgQADH/arrayDH[5] : 0));
        mapCount.put("avgFinanceDH", WebUtil.getTimeDisPlayExcel(avgFinanceDH > 0 ?  avgFinanceDH/arrayDH[6] : 0));
        mapCount.put("avgLogisticsDH", WebUtil.getTimeDisPlayExcel(avgLogisticsDH > 0 ?  avgLogisticsDH/arrayDH[7] : 0));
        long avgOrderSumDH = (avgMRDH > 0 ? avgMRDH / arrayDH[0] : 0) + (avgTechnologyDH > 0 ? avgTechnologyDH/arrayDH[1] : 0)
                + (avgPurDH > 0 ? avgPurDH/arrayDH[2] : 0) + (avgCQCDH > 0 ? avgCQCDH / arrayDH[3] : 0)
                + (avgTPEDH > 0 ?  avgTPEDH/arrayDH[4] : 0) + (avgQADH > 0 ?  avgQADH/arrayDH[5] : 0)
                + (avgFinanceDH > 0 ?  avgFinanceDH/arrayDH[6] : 0) + (avgLogisticsDH > 0 ?  avgLogisticsDH/arrayDH[7] : 0);
        mapCount.put("avgOrderSumDH", WebUtil.getTimeDisPlayExcel(avgOrderSumDH));
        mapCount.put("orderNumSumDH", count > 0 ? count + "": "");
        mapCount.put("sewingNumSumDH", tpeCount > 0 ? tpeCount + "": "");
        mapCount.put("qualifiedNumSumDH", qualifiedNumSumDH > 0 ? qualifiedNumSumDH + "" : "");
        mapCount.put("unqualifiedNumSumDH", unqualifiedNumSumDH > 0 ? unqualifiedNumSumDH + "" : "");

        Struts2Utils.getRequest().setAttribute("mapCount", mapCount);
        beans.clear();


        fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
        beans = getObjectsBySql(fsp);
        count = 0;
        for(int i = 0; i < beans.size(); i++){
            isBack = false;
            haoshi = 0;
            if(orderType != null && orderType.equals("3")){  //大货生产
                long huizong = 0;
                long huizong_duration = 0;
                if(beans.get(i).get("mrdel_wf_step_duration") != null && (long)beans.get(i).get("mrdel_wf_step_duration") > 0){ //大货详情
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("mrdel_wf_real_finish"), (Date)beans.get(i).get("mrdel_wf_real_start"), (long)beans.get(i).get("mrdel_wf_step_duration"), 1, "c_mr_improve_2_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[0] += 1;
                        beans.get(i).set("c_mr_improve_2", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("mrdel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("jshdel_wf_step_duration") != null && (long)beans.get(i).get("jshdel_wf_step_duration") > 0){  //技术
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("jshdel_wf_real_finish"), (Date)beans.get(i).get("jshdel_wf_real_start"), (long)beans.get(i).get("jshdel_wf_step_duration"), 1, "c_ppc_assign_3_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[1] += 1;
                        beans.get(i).set("c_ppc_assign_3", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("jshdel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("fidel_wf_step_duration") != null && (long)beans.get(i).get("fidel_wf_step_duration") > 0){  //采购
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("fidel_wf_real_finish"), (Date)beans.get(i).get("fidel_wf_real_start"), (long)beans.get(i).get("fidel_wf_step_duration"), 1, "c_fi_pay_4_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[2] += 1;
                        beans.get(i).set("c_fi_pay_4", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("fidel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("cqc_wf_step_duration") != null && (long)beans.get(i).get("cqc_wf_step_duration") > 0){  //CQC
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cqc_wf_real_finish"), (Date)beans.get(i).get("cqc_wf_real_start"), (long)beans.get(i).get("cqc_wf_step_duration"), 1, "c_ppc_factoryMsg_5_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[3] += 1;
                        beans.get(i).set("c_ppc_factoryMsg_5", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("cqc_wf_step_duration");
                    }
                }

                if(beans.get(i).get("tpedel_wf_step_duration") != null && (long)beans.get(i).get("tpedel_wf_step_duration") > 0){  //tpe
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("tpedel_wf_real_finish"), (Date)beans.get(i).get("tpedel_wf_real_start"), (long)beans.get(i).get("tpedel_wf_step_duration"), 1, "c_qc_cutting_6_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[4] += 1;
                        beans.get(i).set("c_qc_cutting_6", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("tpedel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("qadel_wf_step_duration") != null && (long)beans.get(i).get("qadel_wf_step_duration") > 0){  //QA
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("qadel_wf_real_finish"), (Date)beans.get(i).get("qadel_wf_real_start"), (long)beans.get(i).get("qadel_wf_step_duration"), 1, "c_ppc_confirm_7_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[5] += 1;
                        beans.get(i).set("c_ppc_confirm_7", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("qadel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("cwdel_wf_step_duration") != null && (long)beans.get(i).get("cwdel_wf_step_duration") > 0){  //财务
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cwdel_wf_real_finish"), (Date)beans.get(i).get("cwdel_wf_real_start"), (long)beans.get(i).get("cwdel_wf_step_duration"), 1, "c_qc_printing_8_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[6] += 1;
                        beans.get(i).set("c_qc_printing_8", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("cwdel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("wldel_wf_step_duration") != null && (long)beans.get(i).get("wldel_wf_step_duration") > 0){  //发货
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("wldel_wf_real_finish"), (Date)beans.get(i).get("wldel_wf_real_start"), (long)beans.get(i).get("wldel_wf_step_duration"), 1, "c_ppc_confirm_9_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDH[7] += 1;
                        beans.get(i).set("c_ppc_confirm_9", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("wldel_wf_step_duration");
                    }
                }

                long cmpTime = huizong - huizong_duration;
                if(cmpTime<=0){
                    beans.get(i).set("huizong_color", "green");
                }else if(cmpTime>0 && cmpTime<=4*60*60*1000){
                    beans.get(i).set("huizong_color", "yellow");
                }else if(cmpTime>4*60*60*1000){
                    beans.get(i).set("huizong_color", "red");
                }
                beans.get(i).set("huizong", WebUtil.getTimeDisPlayExcel(huizong));
            }else if(orderType != null && orderType.equals("2")){
                long huizong = 0;
                long huizong_duration = 0;
                isBack = false;
                if(beans.get(i).get("mrdb_wf_step_duration") != null && (long)beans.get(i).get("mrdb_wf_step_duration") > 0){ //MR用时
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("mrdb_wf_real_finish"), (Date)beans.get(i).get("mrdb_wf_real_start"), (long)beans.get(i).get("mrdb_wf_step_duration"), 1, "b_mr_improve_2_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[0] += 1;
                        beans.get(i).set("b_mr_improve_2", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("mrdb_wf_step_duration");
                    }
                }

                if(beans.get(i).get("ppcdel_wf_step_duration") != null && (long)beans.get(i).get("ppcdel_wf_step_duration") > 0){ //采购用时
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("ppcdel_wf_real_finish"), (Date)beans.get(i).get("ppcdel_wf_real_start"), (long)beans.get(i).get("ppcdel_wf_step_duration"), 1, "b_ppc_confirm_3_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[1] += 1;
                        beans.get(i).set("b_ppc_confirm_3", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("ppcdel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("jsdel_wf_step_duration") != null && (long)beans.get(i).get("jsdel_wf_step_duration") > 0){ //技术用时
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("jsdel_wf_real_finish"), (Date)beans.get(i).get("jsdel_wf_real_start"), (long)beans.get(i).get("jsdel_wf_step_duration"), 1, "b_pur_confirm_4_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[2] += 1;
                        beans.get(i).set("b_pur_confirm_4", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("jsdel_wf_step_duration");
                    }
                }

                if(beans.get(i).get("cqdel_wf_step_duration") != null && (long)beans.get(i).get("cqdel_wf_step_duration") > 0){ //核价用时
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("cqdel_wf_real_finish"), (Date)beans.get(i).get("cqdel_wf_real_start"), (long)beans.get(i).get("cqdel_wf_step_duration"), 1, "b_ppc_confirm_5_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[3] += 1;
                        beans.get(i).set("b_ppc_confirm_5", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("cqdel_wf_step_duration");
                    }
                }
                if(beans.get(i).get("bqcdel_wf_step_duration") != null && (long)beans.get(i).get("bqcdel_wf_step_duration") > 0){ //MR确认用时
                    haoshi = computationProgress(beans.get(i), (Date)beans.get(i).get("bqcdel_wf_real_finish"), (Date)beans.get(i).get("bqcdel_wf_real_start"), (long)beans.get(i).get("bqcdel_wf_step_duration"), 1, "b_qc_confirm_6_color");
                    if(isBack){
                        haoshi = 0;
                    }else if(haoshi == 0){
                        isBack = true;
                    }else{
                        arrayDB[4] += 1;
                        beans.get(i).set("b_qc_confirm_6", WebUtil.getTimeDisPlayExcel(haoshi));
                        huizong += haoshi;
                        huizong_duration += (long)beans.get(i).get("bqcdel_wf_step_duration");
                    }
                }

                long cmpTime = huizong - huizong_duration;
                if(cmpTime<=0){
                    beans.get(i).set("huizong_color", "green");
                }else if(cmpTime>0 && cmpTime<=4*60*60*1000){
                    beans.get(i).set("huizong_color", "yellow");
                }else if(cmpTime>4*60*60*1000){
                    beans.get(i).set("huizong_color", "red");
                }

                beans.get(i).set("huizong", WebUtil.getTimeDisPlayExcel(huizong));
            }



//            count =+ Integer.parseInt(beans.get(i).get("sewing_total").toString() != null ? beans.get(i).get("sewing_total").toString() : "0");

            beans.get(i).set("order_num", getOrdreNum(beans.get(i).get("num_info") != null ? beans.get(i).get("num_info").toString() : null));
            beans.get(i).set("unqualifiedNumSum", beans.get(i).get("unqualified_total") != null ? beans.get(i).get("unqualified_total") : "0");
            beans.get(i).set("qualifiedNumSum", beans.get(i).get("qualified_total") != null ? beans.get(i).get("qualified_total") : "0");
        }

        processPageInfo(getObjectsCountSql(fsp));
		return "orderProgressReport";
	}
	
	/**
	 * 根据分组动态获取每个组的成员
	 */
	public void jsonGetOperator(){
		String groupName=Struts2Utils.getParameter("name");
		List<Map> results=new ArrayList<Map>();
		List<LazyDynaMap> temp=XbdBuffer.getStaffsByGroupName(groupName);
		if(temp.size()>0){
			for(LazyDynaMap map:temp){
				Map rList=new HashMap();
				rList.put("id", map.get("id"));
				rList.put("oa_org", map.get("oa_org"));
				rList.put("login_name", map.get("login_name"));
				results.add(rList);
			}	
		}
		Struts2Utils.renderJson(results);
	}
	public long calRealTimeOfTwoNode(LazyDynaMap temp,String wf_step,LazyDynaMap bean,long wf_step_duration){
		// 计算实际耗时
		long cmpTime=0L;
		long realTime=0L;
		if (null != temp.get("wf_real_start") && null != temp.get("wf_real_finish")) {
			realTime = BizUtil.getWorkTimeBetween((Timestamp) temp.get("wf_real_finish"), (Timestamp) temp.get("wf_real_start"));
			cmpTime=realTime-wf_step_duration;
			String color="";
			if(cmpTime<=0){
				color="#309865";//表示 改节点没有超时 颜色为绿色
			}else if(cmpTime>0 && cmpTime<=4*60*60*1000){
				color="#FFFC05";//表示该节点已经超时，超时的时间少于4小时 为黄色
			}else if(cmpTime>4*60*60*1000){
				color="#FC0303";//表示该节点已经超时，超时时间大于4小时 为红色
			}
			String realTimes = DateUtil.longToddhhmm(realTime);
			bean.set(wf_step+"", realTimes+"-"+color);// 实际耗时
		} else {
			bean.set(""+wf_step, "");// 实际耗时
		}
		return realTime;
	}

	/**
	 *
	 * @Title: orderDetail
	 * @Description: TODO订单详情查看
	 *
	 * @author 张华
	 * @return
	 */
	public String orderDetail() {
		if (null == this.oaOrderDetail || null == this.oaOrderDetail.getId()) {
			return "orderDetail";
		}
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.PROCESSORDER);
		fsp.set("id", this.oaOrderDetail.getId());
		bean = manager.getOnlyObjectBySql(fsp);
		OaOrder oaOrder = null;
		if (null != bean && null != bean.get("id")) {
			int oa_order = (Integer) bean.get("oa_order");
			oaOrder = (OaOrder) manager.getObject(OaOrder.class, oa_order);
		}
		bean.set("orderDetail", "true");
		if (null != oaOrder && null != oaOrder.getId()) {
			getOrderDetail(oaOrder);
			//当前detail的ID
			bean.set("orderDetailId", this.oaOrderDetail.getId());
			// 当前节点index
			String wfStepIndex = oaOrder.getWfStep();
			wfStepIndex = wfStepIndex.substring(wfStepIndex.length() - 1, wfStepIndex.length());
			bean.set("wfStepIndex", wfStepIndex); // 当前节点index
			
			String terminate = Struts2Utils.getParameter("terminate");
			if ("true".equals(terminate)) {
				//需要同步CRM中订单的状态
				String oaSec=ExternalAction.getMD5Str(miyaoString+oaOrder.getSellOrderCode());
				bean.set("oaSec", oaSec);
				bean.set("sellOrderId", oaOrder.getSellOrderId());
				return "toTerminateOrder";
			} else if ("2".equals(oaOrder.getType())) { // 样衣打版
				return "processDaban";
			} else if ("3".equals(oaOrder.getType())) { // 大货生产
				return "processDahuo";
			}
		}
		
		return "orderDetail";
	}

	/**
	 *
	 * @Title: getOrderDetail
	 * @Description: TODO获取订单信息
	 *
	 * @author 张华
	 * @return
	 */
	private void getOrderDetail(OaOrder oaOrder) {
		// 订单基本信息
		bean.set("styleCode", oaOrder.getStyleCode()); // 款号
		bean.set("orderCode", oaOrder.getSellOrderCode());// 订单编号
		String orderTypeString = WebUtil.getOrderTypeStr(oaOrder.getType());
		bean.set("orderTypeNum", oaOrder.getType()); // 订单类型
		bean.set("orderType", orderTypeString); // 订单类型
		bean.set("wantCnt", oaOrder.getWantCnt()); // 订单数量
		bean.set("beginTime", oaOrder.getBeginTime()); // 创建时间
		bean.set("customerCode", oaOrder.getCusCode()); // 客户编号
		bean.set("customerName", oaOrder.getCusName()); // 客户名称
		bean.set("sales", oaOrder.getSales()); // 销售
		bean.set("mr", oaOrder.getMrName()); // Mr名称
		// 基本信息
		bean.set("city", oaOrder.getCity()); // 分公司
		bean.set("isUrgent", oaOrder.getIsUrgent()); // 是否加急
		// 款式信息
		bean.set("styleDesc", oaOrder.getStyleDesc()); // 款式描述
		bean.set("styleType", oaOrder.getStyleType()); // 款式类型
		bean.set("styleClass", oaOrder.getStyleClass()); // 款式品类，一级分类
		bean.set("clothClass", oaOrder.getClothClass()); // 品类，二级品类
		bean.set("styleCraft", oaOrder.getStyleCraft()); // 特殊工艺
		bean.set("sampleSize", oaOrder.getSampleSize()); // 样板码数
		bean.set("picFront", oaOrder.getPictureFront()); // 款式正面图
		bean.set("picBack", oaOrder.getPictureBack()); // 款式背面图
		// 数量信息通过json获取
		// 客户信息
		bean.set("payType", oaOrder.getPayType()); // 付款方式
		bean.set("contractCode", oaOrder.getOrderCode()); // 合同编号
		bean.set("sendType", oaOrder.getSendtype()); // 发货方式
		bean.set("salesMemo", oaOrder.getSellMemo()); // 销售备注
		bean.set("mrMemo", oaOrder.getMemo()); // Mr备注
		// 审核信息
		bean.set("sellOrderId", oaOrder.getSellOrderId());// 销售的订单Id
		// 管理信息
		bean.set("tpe", oaOrder.getTpeName()); // tpe
		// 尺寸、用料搭配明细、客供料明细
		bean.set("orderSizeId", oaOrder.getOaOrderNumId()); // 尺寸数量
		bean.set("orderId", oaOrder.getId()); // 订单ID
		bean.set("wfStep", oaOrder.getWfStep()); // 订单节点ID
		// 用于判断是否为提前终止订单，及终止订单信息
		bean.set("status", oaOrder.getStatus()); // 订单状态
		bean.set("terminateMemo", oaOrder.getTerminateMemo()); // 终止备注
		bean.set("terminateUser", oaOrder.getTerminateUser()); // 终止操作人
		bean.set("terminateTime", oaOrder.getTerminateTime()); // 终止时间
		
		//用于重复打板
		bean.set("repeatNum",oaOrder.getRepeatNum());//重复打板次数
		bean.set("repeatReason", oaOrder.getRepeatReason());//重复打板原因
		//大货新增加的产前版
		bean.set("isPreproduct", oaOrder.getIsPreproduct());//是否需要产前版
		bean.set("preVersionDate", oaOrder.getPreVersionDate());//产前版日期
		// 获取关联订单的信息 Add by ZQ 2014-12-22
		bean.set("relatedOrderCode", oaOrder.getRelatedOrderCode());
		bean.set("relatedOrderId", oaOrder.getRelatedOrderId());
		bean.set("relatedOrderType", oaOrder.getRelatedOrderType());
		bean.set("hisOpt", oaOrder.getHisOpt());
		bean.set("feeding_time", oaOrder.getFeedingTime());
		bean.set("relatedOrderDetailId", "");
		if (null != oaOrder.getRelatedOrderId() && oaOrder.getRelatedOrderId() > 0) { // 关联订单存在，则查询关联订单最新detail的Id
			// FSPBean fspBean = new FSPBean();
			// fspBean.set(FSPBean.FSP_QUERY_BY_XML,
			// BxDaoImpl.LIST_OA_ORDER_DETAIL_BY_SQL);
			// fspBean.set("oa_order", oaOrder.getRelatedOrderId());
			// fspBean.set(FSPBean.FSP_ORDER, " order by inx desc");
			// LazyDynaMap map = manager.getOnlyObjectBySql(fspBean);
			// bean.set("relatedOrderDetailId", map.get("id"));
			OaOrder order = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getRelatedOrderId());
			if (null != order) {
				bean.set("relatedOrderDetailId", order.getOaOrderDetail());
			}
		}

		// 得到当前是那个节点
		// 当前节点index
		String wfStepIndex = oaOrder.getWfStep();
		switch (wfStepIndex) {
		case "c_mr_improve_2":
			bean.set("currentNode", "订单详情");
			break;
		case "c_ppc_assign_3":
			bean.set("currentNode", "技术");
			break;
		case "c_fi_pay_4":
			bean.set("currentNode", "采购");
			break;
		case "c_ppc_factoryMsg_5":
			bean.set("currentNode", "CQC");
			break;
		case "c_qc_cutting_6":
			bean.set("currentNode", "TPE");
			break;
		case "c_ppc_confirm_7":
			bean.set("currentNode", "QA");
			break;
		case "c_qc_printing_8":
			bean.set("currentNode", "财务");
			break;
		case "c_ppc_confirm_9":
			bean.set("currentNode", "物流");
			break;
		case "b_mr_improve_2":
			bean.set("currentNode", "订单详情");
			break;
		case "b_ppc_confirm_3":
			bean.set("currentNode", "采购");
			break;
		case "b_pur_confirm_4":
			bean.set("currentNode", "技术");
			break;
		case "b_ppc_confirm_5":
			bean.set("currentNode", "核价");
			break;
		case "b_qc_confirm_6":
			bean.set("currentNode", "MR");
			break;
		case "finish_999":
			bean.set("currentNode", "已完成");
			break;
		}
		Date now = new Date();
		DecimalFormat df = new DecimalFormat("0");
		Timestamp tamp = new Timestamp(now.getTime());
		if ("finish_999".equals(wfStepIndex)) {
			tamp = oaOrder.getEndTime();
		}
		
		//订单周期
		Long sellReadyTime = oaOrder.getSellReadyTime();
		Long standardTime = oaOrder.getStandardTime();
		Long craftTime = oaOrder.getCraftTime();
		Long orderTime = (sellReadyTime+standardTime+craftTime)/9*24;
		//交期
		Timestamp goodsTime = oaOrder.getGoodsTime();
		//当前工作时间
		Timestamp workTime = null;
		if(("finish_999").equals(oaOrder.getWfStep())){
			workTime = BizUtil.getOperatingTime(oaOrder.getEndTime());
		}else{
			workTime = BizUtil.getOperatingTime(new Timestamp(new Date().getTime()));
		}
		Integer persent = (int) ((workTime.getTime()-goodsTime.getTime()+orderTime - 60*60*1000*24d)/orderTime*100);
		bean.set("time_consume", persent);// 设置当前的耗时
	}

	/**
	 *
	 * @Title: getOrderDetailOld
	 * @Description: TODO旧流程获取订单信息方法，暂时使用，流程改造完成可删除
	 *
	 * @author 张华
	 * @param oaOrder
	 */
	private void getOrderDetailOld(OaOrder oaOrder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp except_finish = (Timestamp) oaOrder.getExceptFinish();
		Date except_finish_Date = new Date(except_finish.getTime());//
		// 预期完成时间

		StringBuffer orderTitle = new StringBuffer();
		String is_urgent = oaOrder.getIsUrgent();// 是否加急
		String type = oaOrder.getType();// 订单类型
		if (null != is_urgent && "0".equals(is_urgent.trim())) {
			orderTitle.append("<span>[加急]</span>");
		}
		if ("3".equals(oaOrder.getType())) {
			orderTitle.append(oaOrder.getOrderCode()).append("-");
		}
		orderTitle.append(oaOrder.getStyleCode()).append("款-");
		orderTitle.append(oaOrder.getWantCnt()).append("件-");
		orderTitle.append(oaOrder.getHisOpt());
		bean.set("fileName", oaOrder.getCusCode() + "-" + oaOrder.getStyleCode()); // 上传文件名
		bean.set("orderTitle", orderTitle);
		bean.set("orderCode", oaOrder.getOrderCode());
		bean.set("orderPic", oaOrder.getStyleUrl());
		bean.set("wantCnt", oaOrder.getWantCnt() + " 件");
		bean.set("exceptFinish", df.format(oaOrder.getExceptFinish()));
		if (null != type && "3".equals(type.trim())) { // 类型3为大货，则存付款类型
			bean.set("payType", oaOrder.getPayType());
			bean.set("exceptPrice", "-1");
		} else { // 其他存期望价格
			bean.set("exceptPrice", oaOrder.getPriceMin() + " - " + oaOrder.getPriceMax() + " 元/件");
		}

		// 格式化上个节点完成时间
		Timestamp wf_real_finish = (Timestamp) bean.get("wf_real_finish");
		Date real_finish_date = new Date(wf_real_finish.getTime());
		bean.set("wf_real_finish", df.format(real_finish_date));

		// 计算实际偏差
		Timestamp wf_plan_start = (Timestamp) bean.get("wf_plan_start");
		Long wf_step_duration = (Long) bean.get("wf_step_duration"); //
		// 此处单位毫秒
		Timestamp plan_finish_date = BizUtil.culPlanDate(wf_plan_start, wf_step_duration);// 计划完成时间
		Timestamp now = new Timestamp(System.currentTimeMillis());
		bean.set("wf_plan_finish", df.format(plan_finish_date));//
		// 计划完成时间存到bean中

		int wf_plan_finish_flag = 1;
		long between = wf_step_duration - BizUtil.getWorkTimeBetween(now, wf_plan_start);
		if (between > 0) {
			wf_plan_finish_flag = 0;
			between = Math.abs(between);
		}
		StringBuffer offset = new StringBuffer(wf_plan_finish_flag == 0 ? "剩余" : "超期");// 存储实际偏差
		offset.append(WebUtil.getTimeDisPlay(between));
		bean.set("offset", offset.toString());// 计算出的实际偏差存放到bean中

		// 计算总剩余时间
		int except_finish_flag = 1;
		between = BizUtil.getWorkTimeBetween(now, except_finish);
		if (between < 0) {
			except_finish_flag = 0;
			between = Math.abs(between);
		}
		StringBuffer rest = new StringBuffer(except_finish_flag == 0 ? "剩余" : "超期");
		rest.append(WebUtil.getTimeDisPlay(between));
		bean.set("rest", rest.toString());// 计算出的总剩余时间存放到bean中

		// 获取到上传文件和图片的网络地址
		String attachment = (String) bean.get("top_attachment") == null ? "" : (String) bean.get("top_attachment");
		String pic = (String) bean.get("top_pic") == null ? "" : (String) bean.get("top_pic");
		// 把文件的网络地址转为文件名称并放入bean中
		bean.set("top_attachment_name", PathUtil.url2FileName(attachment));
		bean.set("top_pic_name", PathUtil.url2FileName(pic));
		// 获取到上传文件和图片的网络地址
		attachment = (String) bean.get("attachment") == null ? "" : (String) bean.get("attachment");
		// 把文件的网络地址转为文件名称并放入bean中
		bean.set("attachment_name", PathUtil.url2FileName(attachment));
		pic = (String) bean.get("pic") == null ? "" : (String) bean.get("pic");
		// 把文件的网络地址转为文件名称并放入bean中
		bean.set("pic_name", PathUtil.url2FileName(attachment));

		// 查询是否有分配订单权限
		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORG);
		fspBean.set("admin", WebUtil.getCurrentLoginBx().getLoginName());
		LazyDynaMap map = manager.getOnlyObjectBySql(fspBean);
		if (null == map || null == map.get("id")) { // 查询不到，则没有，存0
			bean.set("isAssign", 0);
		} else {
			bean.set("isAssign", 1); // 查到，则存1
		}

		// if (WorkFlowUtil.ifTurnTransitionBack((String) bean.get("task_id")))
		// {
		// bean.set("isBack", 1);
		// } else {
		// bean.set("isBack", 0);
		// }

		// 查询是否有提前中止订单的权限
		int orgId = WebUtil.getCurrentLoginBx().getOaOrg();
		OaOrg oaOrg = (OaOrg) manager.getObject(OaOrg.class, orgId);
		if (null != oaOrg && "ppc".equals(oaOrg.getName())) {
			bean.set("isFinish", 1);
		}
	}

	/**
	 *
	 * @Title: jsonGetSecondNode
	 * @Description: TODO流程改造第二节点，获取第二个节点页面信息
	 *
	 * @author 张华
	 */
	public void jsonGetSecondNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
			String orderSizeIdStr = Struts2Utils.getParameter("orderSizeId");// 获取尺码数量Id
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String isChoose = Struts2Utils.getParameter("isChoose");// 是否为关联订单操作
			// 关联定的时，查询关联订单详情
			if (StringUtils.isNotEmpty(isChoose)) {
				OaOrder order = (OaOrder) manager.getObject(OaOrder.class,
						Integer.parseInt(orderIdStr));
				resMap.put("chooseOaOrder", order);
			}
			getOrderSize(orderSizeIdStr, resMap); // 获取订单尺码数量
			getMaterialList(orderIdStr, resMap); // 获取用料搭配信息、客供料信息
			getManagerInfo(Integer.parseInt(orderIdStr), "2", wfStepIndex,resMap); // 查询管理信息
			//品类列表
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CATEGORY);
			List<LazyDynaMap> categorys = getObjectsBySql(fsp);
			resMap.put("categorys", categorys);
			
			resMap.put("code", 0);
			resMap.put("msg", "订单详情节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "订单详情节点-信息查询出错");
		}

		Struts2Utils.writeJson(resMap);
	}
	
	public void getFeedingTime() {
		Integer orderId = Integer.parseInt(Struts2Utils.getParameter("orderId"));
		Long craftTime = Long.parseLong(Struts2Utils.getParameter("craftTime"));
		Long standardTime = Long.parseLong(Struts2Utils.getParameter("standardTime"));
		String productTime = Struts2Utils.getParameter("productTime");
		OaOrder order = (OaOrder) manager.getObject(OaOrder.class,orderId);
		Timestamp feedingTime = null;
		if(StringUtils.isNotBlank(productTime)){
			Timestamp ptime = new Timestamp(DateUtil.parseDate(productTime).getTime());
			feedingTime = BizUtil.culPlanDate(ptime,(order.getPreproductDays()==null?0:order.getPreproductDays())*24*60*60*1000 + 18*60*60*1000L-craftTime-standardTime);
		}else{
			feedingTime = BizUtil.culPlanDate(order.getGoodsTime(), 18*60*60*1000L-craftTime-standardTime);
		}
		Struts2Utils.writeJson(DateUtil.formatDate(feedingTime));
	}

	/**
	 *
	 * @Title: getOrderSize
	 * @Description: TODO获取订单尺码数量
	 *
	 * @author 张华
	 * @param orderSizeIdStr
	 * @param resMap
	 */
	private void getOrderSize(String orderSizeIdStr, Map resMap)
			throws Exception {
		int orderSizeId = 0;
		if (StringUtils.isBlank(orderSizeIdStr)) { // 判断订单Id是否为空
			throw new RuntimeException("尺码数量ID不能为空");
		} else {
			try { // 判断订单Id是否填写正确
				orderSizeId = Integer.parseInt(orderSizeIdStr);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				throw e;
			}
			// 获取订单基本信息
			OaOrderNum oaOrderNum = (OaOrderNum) manager.getObject(
					OaOrderNum.class, orderSizeId);
			if (null != oaOrderNum && null != oaOrderNum.getId()) {
				String title[] = oaOrderNum.getTitle().split("-");
				String numInfo[] = oaOrderNum.getNumInfo().split(",");

				resMap.put("orderSizeTitle", title);
				resMap.put("orderSizeInfo", numInfo);
			} else {
				throw new RuntimeException("尺码数量ID为" + orderSizeId + "查询不到相关数据");
			}
		}
	}

	/**
	 *
	 * @Title: getMaterialList
	 * @Description: TODO获取用料搭配信息、客供料信息
	 *
	 * @author 张华
	 * @param orderIdStr
	 * @param resMap
	 */
	private void getMaterialList(String orderIdStr, Map resMap)
			throws Exception {
		int orderId = 0;
		if (StringUtils.isBlank(orderIdStr)) { // 判断订单Id是否为空
			throw new RuntimeException("订单ID不能为空");
		} else {
			try { // 判断订单Id是否填写正确
				orderId = Integer.parseInt(orderIdStr);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				throw e;
			}
			FSPBean fsp = new FSPBean();
			// 获取订单用料搭配明细列表
			fsp.set(FSPBean.FSP_QUERY_BY_XML,
					BxDaoImpl.GET_MATERIAL_LIST_BY_EQL);
			fsp.set("oaOrderId", orderId);
			List<OaMaterialList> oaMaterialList = getObjectsByEql(fsp);

			// 获取订单客供料明细列表
			fsp.set(FSPBean.FSP_QUERY_BY_XML,
					BxDaoImpl.GET_CUS_MATERIAL_LIST_BY_EQL);
			List<OaCusMaterialList> oaCusMaterialList = getObjectsByEql(fsp);

			resMap.put("oaMaterialList", oaMaterialList); // 用料搭配明细列表
			resMap.put("oaCusMaterialList", oaCusMaterialList); // 客供料明细列表
		}
	}

	/**
	 *
	 * @Title: jsonProcessOrder
	 * @Description: TODO处理订单，走流程
	 *
	 * @author 张华
	 */
	public void jsonProcessOrder() {
		String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
		String processOrder = Struts2Utils.getParameter("processOrder");// 处理订单标识
		Map resMap = new HashMap();// 返回结果
		int orderId = 0;

		try { // 判断订单Id是否填写正确
			orderId = Integer.parseInt(orderIdStr);
			// 获取订单基本信息
			OaOrder oaOrder1 = (OaOrder) manager.getObject(OaOrder.class, orderId);
			if (null != oaOrder1 && null != oaOrder1.getId()) {
				// 判断订单在哪一步，然后调用对应的方法
				boolean res = false;
				// 'b_ppc_confirm_3','b_ppc_confirm_5','b_qc_confirm_6' mr确认
				// 'c_fi_pay_4','c_ppc_factoryMsg_5','c_qc_cutting_6','c_ppc_confirm_7','c_qc_printing_8','c_ppc_confirm_9'
				if ("b_mr_improve_2".equals(oaOrder1.getWfStep()) || "c_mr_improve_2".equals(oaOrder1.getWfStep())) {
					res = saveOrderSecond(oaOrder1);
				} else if ("b_pur_confirm_4".equals(oaOrder1.getWfStep()) || "c_ppc_assign_3".equals(oaOrder1.getWfStep())) {
					res = saveOrderThird(oaOrder1);
				} else if ("b_ppc_confirm_3".equals(oaOrder1.getWfStep()) || "c_fi_pay_4".equals(oaOrder1.getWfStep())) {
					res = saveOrderFour(oaOrder1);
				} else if ("b_ppc_confirm_5".equals(oaOrder1.getWfStep())) {
					res = saveOrderDaBanFive(oaOrder1);
				} else if ("b_qc_confirm_6".equals(oaOrder1.getWfStep())) {
					res = saveOrderDaBanSix(oaOrder1);
				} else if ("c_qc_cutting_6".equals(oaOrder1.getWfStep())) {
					res = saveOrderDaHuoSix(oaOrder1);
					// 车缝节点发送微信消息
					String userId = oaOrder1.getCusCode();
//					String content = "亲，您的订单" + oaOrder1.getSellOrderCode() + "「" + oaOrder1.getStyleDesc() + "」已开始车缝。";
					String content = "亲，收到您「" + oaOrder1.getStyleDesc() + "」的布片，已交由经验老练的工友们精心缝制，哒哒哒……哒哒哒……敬请期待！";
					SendWeChatMsg.sendMsg(userId, content);
				} else if ("c_ppc_factoryMsg_5".equals(oaOrder1.getWfStep())) {
					res = saveCqc();
					// 验布、裁减节点发送微信消息
					String userId = oaOrder1.getCusCode();
//					String content = "亲，您的订单" + oaOrder1.getSellOrderCode() + "「" + oaOrder1.getStyleDesc() + "」面料完成采购，正在验布、剪裁。";
					String content = "客官，您「" + oaOrder1.getStyleDesc() + "」的面料已开始验布、裁片，全自动裁床保证精度、提升速度，duang的一下就完成了。";
					SendWeChatMsg.sendMsg(userId, content);
				} else if ("c_ppc_confirm_7".equals(oaOrder1.getWfStep())) {
					res = saveQA();
				} else if ("c_ppc_confirm_9".equals(oaOrder1.getWfStep())) {
					res = saveLogistics();
					// 发货节点发送微信消息
					String userId = oaOrder1.getCusCode();
//					String content = "亲，您的订单" + oaOrder1.getSellOrderCode() + "「" + oaOrder1.getStyleDesc() + "」已经发货，请保持手机畅通。";
					String content = "期待是一种幸福，也是心痛的根源，您的宝贝儿「" + oaOrder1.getStyleDesc() + "」已经交给靠谱的快递小哥，期待着带给您惊喜，如有惊吓请拨打010-57115491，怒吼：“找莫大！”";
					SendWeChatMsg.sendMsg(userId, content);
				} else if ("c_qc_printing_8".equals(oaOrder1.getWfStep())) {
					res = saveOrderDaHuoEight(oaOrder1);// 保存财务节点
				}
				// 处理流程，流转到下一步
				if (res && "true".equals(processOrder)) {
					// 查询该订单节点信息
					OaOrderDetail oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, oaOrder1.getOaOrderDetail());
					// 样衣打版第六个流程节点（MR确认）不写Excel，排除在外
					if (!"b_qc_confirm_6".equals(oaOrder1.getWfStep()) && !"c_qc_printing_8".equals(oaOrder1.getWfStep()) && !"c_ppc_confirm_9".equals(oaOrder1.getWfStep())) {
						oaOrderDetail.setAttachment(createExcelByOrderId(oaOrder1.getId()));
					}
					saveObject(oaOrderDetail); // 保存附件
					BizUtil.nextStep(oaOrderDetail);
					// 如果是打板的第四步 在流转完成后要向第五步中添加默认的三条数据
					if ("b_pur_confirm_4".equals(oaOrder1.getWfStep())) {
						saveMaterialDefaultData(oaOrder1.getId());
					}
					if("c_ppc_confirm_7".equals(oaOrder1.getWfStep())){//如果是qa节点确认流转后 如果财务节点的支付方式3:7 或者 月结 是财务节点自动流转到物流节点
						if("3:7".equals(oaOrder1.getPayType()) || "月结".equals(oaOrder1.getPayType()) || "月结30天".equals(oaOrder1.getPayType())){
							oaOrder1 = (OaOrder) manager.getObject(OaOrder.class, oaOrder1.getId());
							oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, oaOrder1.getOaOrderDetail());
							oaOrderDetail.setWorker("");
							BizUtil.nextStep(oaOrderDetail);
						}
					}
				}
				if (res) {
					resMap.put("code", 0);
					resMap.put("msg", "处理订单成功");
				} else {
					resMap.put("msg", "处理订单失败");
				}
			} else {
				resMap.put("msg", "处理订单失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "处理订单失败");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: saveOrderSecond
	 * @Description: TODO订单第二步操作，Mr补全订单
	 *
	 * @author 张华
	 * @param oaOrder1
	 * @return
	 */
	private boolean saveOrderSecond(OaOrder oaOrder1) {
		try {
			oaOrder1.setCity(oaOrder.getCity()); // 分公司
			oaOrder1.setIsUrgent(oaOrder.getIsUrgent()); // 是否紧急
			oaOrder1.setClothClass(oaOrder.getClothClass()); // 二级分类，品类
			oaOrder1.setSampleSize(oaOrder.getSampleSize()); // 样板码数
			oaOrder1.setSendtype(oaOrder.getSendtype()); // 发货方式
			oaOrder1.setMemo(oaOrder.getMemo()); // Mr备注
			oaOrder1.setTpeName(oaOrder.getTpeName()); // tpe，即qc
			oaOrder1.setPreVersionDate(oaOrder.getPreVersionDate()); // 保存产前版日期
			oaOrder1.setStyleCraft(oaOrder.getStyleCraft());//保存生产工艺
			oaOrder1.setStyleClass(oaOrder.getStyleClass());
			oaOrder1.setCraftTime(oaOrder.getCraftTime());//特殊工艺用时
			oaOrder1.setFeedingTime(oaOrder.getFeedingTime());//建议投料日期
			// 关联订单时，保存被关联订单的ID、订单编号、类型 Add by ZQ 2014-12-22
			String relatedOrderCode = oaOrder.getRelatedOrderCode();
			if (StringUtils.isNotEmpty(relatedOrderCode)) {
				oaOrder1.setRelatedOrderCode(oaOrder.getRelatedOrderCode());
				oaOrder1.setRelatedOrderId(oaOrder.getRelatedOrderId());
				oaOrder1.setRelatedOrderType(oaOrder.getRelatedOrderType());
			}
			saveObject(oaOrder1);
			String materIds = saveOaMaterialList(oaOrder1.getId()); // 保存用料搭配信息
			saveOaCusMaterialList(oaOrder1.getId()); // 保存客供料信息
			saveOaOrderDetail(oaOrder1.getOaOrderDetail(), oaOrder1.getMemo());// 保存上传的附件和mr备注到detail

			/**
			 * MR选择关联订单时，后台保存操作调取复制该关联订单的采购、技术、核价等信息 Add by ZQ 2014-12-22
			 */
			// 选中关联订单的操作标志
			String isChoose = Struts2Utils.getParameter("isChoose");
			if (StringUtils.isNotEmpty(relatedOrderCode) && "choosed".equals(isChoose)) {
				Integer orderId = oaOrder1.getId();// 订单ID
				Integer relatedOrderId = oaOrder.getRelatedOrderId();// 关联订单ID
				boolean isTypeSame = oaOrder1.getType().equals(oaOrder1.getRelatedOrderType());// 是否为同一类型
				String[] oldMaterIds = Struts2Utils.getRequest().getParameterValues("oldMaterIds");
				// 当保存了关联订单的用料时,才复制保存对应的清单
				if (null != oldMaterIds) {
					copyToExtraSavePurchase(orderId, relatedOrderId, oaOrder1.getType(), oldMaterIds, materIds, isTypeSame);// 复制采购部分
				}

				// update by 张华 2015-01-16
				// 查询订单尺码和关联订单尺码是否一样，一样则直接复制关联订单技术节点尺寸表，不一样则不复制
				OaOrderNum oaOrderNum = (OaOrderNum) manager.getObject(OaOrderNum.class, oaOrder1.getOaOrderNumId());
				OaOrder relatedOrder = (OaOrder) manager.getObject(OaOrder.class, relatedOrderId);
				OaOrderNum relatedOrderNum = (OaOrderNum) manager.getObject(OaOrderNum.class, relatedOrder.getOaOrderNumId());
				boolean ifCopySize = false;
				String copySize = "'";
				if (oaOrderNum.getTitle().equals(relatedOrderNum.getTitle())) {
					ifCopySize = true;
				} else {
					for (int i = 0; i < (oaOrderNum.getTitle().split("-").length - 1); i++) {
						copySize += "-";
					}
					copySize += "'";
				}
				copyToExtraSaveTechnology(orderId, relatedOrderId, ifCopySize, copySize);// 复制技术部分
				copyToExtraSaveCalculation(orderId, relatedOrderId);// 复制核价部分
			}

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * @Description 复制保存核价信息表
	 * @author ZQ
	 * @Date 2014-12-22
	 */
	private void copyToExtraSaveCalculation(Integer orderId,Integer relatedOrderId) {
		// TODO Auto-generated method stub

	}

	/**
	 * @Description 复制保存采购信息表
	 * @author ZQ
	 * @Date 2014-12-22
	 *
	 * @param orderId 订单ID
	 * @param relatedOrderId 关联订单ID
	 * @param type 订单类型
	 * @param oldMaterIds 关联过来的用料IDs
	 * @param materIds 保存提交的用料IDs
	 * @param isTypeSame 订单与关联订单是否为同一类型
	 */
	private void copyToExtraSavePurchase(Integer orderId, Integer relatedOrderId, String type, String[] oldMaterIds, String materIds, boolean isTypeSame) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			String sqlStr = "";
			String delStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DEL_DABAN_INVENTORY_SQL : ConstantUtil.DEL_DAHUO_INVENTORY_SQL;
			String[] matIds = materIds.split(",");
			// 如果类型一样,则取整个清单表SQL,否则取部分清单表SQL
			if (isTypeSame) {
				sqlStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DABAN_INVENTORY_SQL : ConstantUtil.DAHUO_INVENTORY_SQL;
			} else {
				sqlStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DABAN_INVENTORY_PART_SQL : ConstantUtil.DAHUO_INVENTORY_PART_SQL;
			}
			conn = JdbcHelper.getConnection();
			// 手动提交
			conn.setAutoCommit(false);
			/**
			 * update by 张华 2015-01-20 现在关联订单只在订单详情时才可进行关联，关联之后不可再次关联，所以把删除数据代码暂时注释
			 */
			// stmt = conn.createStatement();
			// //删除已存在的用料对应的清单
			// if (StringUtils.isNotEmpty(materialDelIds)) {
			// String materialIds[] = materialDelIds.substring(1).split(",");
			// for(String mid : materialIds){
			// stmt.addBatch(MessageFormat.format(delStr,mid));
			// }
			// }
			// stmt.executeBatch();
			// 根据提交保存的关联订单对应的用料记录复制保存清单
			stmt = conn.createStatement();
			int i = 0;
			for (String oldMaterId : oldMaterIds) {
				stmt.addBatch(MessageFormat.format(sqlStr, matIds[i], oldMaterId));
				i++;
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// 异常时回滚数据,事务恢复自动提交
			try {
				if (!conn.isClosed()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} finally {
			JdbcHelper.close(stmt, conn);
		}
	}

	/**
	 * @Description 复制保存技术信息表
	 * @author ZQ
	 * @Date 2014-12-22
	 * @param orderId 订单ID
	 * @param relatedOrderId 关联订单ID
	 * @param ifCopySize 标识是否复制技术节点尺寸详情
	 */
	// update by 张华 2015-01-16
	private void copyToExtraSaveTechnology(Integer orderId, Integer relatedOrderId, boolean ifCopySize, String copySize) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = JdbcHelper.getConnection();
			// 手动提交
			conn.setAutoCommit(false);
			/**
			 * update by 张华 2015-01-20 现在关联订单只在订单详情时才可进行关联，关联之后不可再次关联，所以把删除数据代码暂时注释
			 */
			// stmt = conn.createStatement();
			// //删除已存在的
			// stmt.addBatch(MessageFormat.format(ConstantUtil.DEL_TECH_CLOTHES_SIZE_SQL, orderId));//尺码表
			// stmt.addBatch(MessageFormat.format(ConstantUtil.DEL_TECH_CLOTHES_DETAILS_SIZE_SQL, orderId));//尺码详情表
			// stmt.addBatch(MessageFormat.format(ConstantUtil.DEL_TECH_PROCESS_EXPLAIN_SQL, orderId));//加工说明
			// stmt.executeBatch();
			// 复制插入尺码表、尺码详情表、加工说明
			// 需要添加的
			stmt = conn.createStatement();
			stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_SIZE_SQL, orderId, relatedOrderId));// 尺码表
			if (ifCopySize) {
				stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_DETAILS_SIZE_SQL, "cloth_size", orderId, relatedOrderId));// 尺码详情表
			} else {
				stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_DETAILS_SIZE_SQL, copySize, orderId, relatedOrderId));// 尺码详情表
			}
			stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_PROCESS_EXPLAIN_SQL, orderId, relatedOrderId));// 加工说明
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// 异常时回滚数据,事务恢复自动提交
			try {
				if (!conn.isClosed()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} finally {
			JdbcHelper.close(stmt, conn);
		}
	}

	/**
	 *
	 * @Title: saveOaOrderDetail
	 * @Description: TODO保存订单详情页中上传的文件及备注
	 *
	 * @author 张华
	 * @param oaOrderDetail
	 * @param mrMemo
	 */
	private void saveOaOrderDetail(Integer oaOrderDetail, String mrMemo) throws Exception {
		OaOrderDetail oaDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, oaOrderDetail);
		oaDetail.setOtherFile(this.oaOrderDetail.getOtherFile()); //保存上传附件
		oaDetail.setContent(mrMemo); //保存mr备注
		manager.saveObject(oaDetail);
	}

	/**
	 *
	 * @Title: saveOaMaterialList
	 * @Description: TODO保存用料搭配信息
	 *
	 * @author 张华
	 * @param orderId
	 */
	private String saveOaMaterialList(int orderId) throws Exception {
		// 拼接新增成功的用料ID
		String materIds = "";
		// 删除用料搭配信息
		// update by 张华 2015-01-20
		delOaMaterialList();

		if (null != oaMaterialLists) {
			// 循环存储用料搭配信息
			for (OaMaterialList oaMaterialList : oaMaterialLists) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				if (StringUtils.isNotEmpty(oaMaterialList.getMaterialProp()) || StringUtils.isNotEmpty(oaMaterialList.getMaterialName()) || "辅料".equals(oaMaterialList.getType())
						|| StringUtils.isNotEmpty(oaMaterialList.getColor()) || StringUtils.isNotEmpty(oaMaterialList.getSupplierName()) || StringUtils.isNotEmpty(oaMaterialList.getSupplierAddr())
						|| StringUtils.isNotEmpty(oaMaterialList.getSupplierTel()) || (null != oaMaterialList.getOrderNum() && oaMaterialList.getOrderNum() > 0)
						|| StringUtils.isNotEmpty(oaMaterialList.getPosition())) {
					if (null == oaMaterialList.getOrderNum()) {
						oaMaterialList.setOrderNum(0f);
					}
					oaMaterialList.setOaOrderId(orderId);
					saveObject(oaMaterialList);
					materIds += "," + oaMaterialList.getId();
				}
			}
			if (materIds.length() >= 1)
				materIds = materIds.substring(1);
		}

		return materIds;
	}

	/**
	 *
	 * @Title: delOaMaterialList
	 * @Description: TODO删除用料搭配信息
	 *
	 * @author 张华
	 */
	private void delOaMaterialList() {
		if (StringUtils.isNotEmpty(materialDelIds)) {
			String materialIds[] = materialDelIds.split(",");
			for (String materialId : materialIds) {
				try {
					int id = Integer.parseInt(materialId);
					OaMaterialList oaMaterialList = (OaMaterialList) manager.getObject(OaMaterialList.class, id);
					if (null != oaMaterialList && null != oaMaterialList.getId()) {
						delObject(oaMaterialList);
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 *
	 * @Title: saveOaCusMaterialList
	 * @Description: TODO保存客供料信息
	 *
	 * @author 张华
	 * @param orderId
	 */
	private void saveOaCusMaterialList(int orderId) throws Exception {
		// 删除客供料信息
		if (StringUtils.isNotEmpty(cusMaterialDelIds)) {
			String cusMaterialIds[] = cusMaterialDelIds.split(",");
			for (String cusMaterialId : cusMaterialIds) {
				try {
					int id = Integer.parseInt(cusMaterialId);
					OaCusMaterialList oaCusMaterialList = (OaCusMaterialList) manager.getObject(OaCusMaterialList.class, id);
					if (null != oaCusMaterialList && null != oaCusMaterialList.getId()) {
						delObject(oaCusMaterialList);
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		if (null != oaCusMaterialLists) {
			// 循环存储客供料信息
			for (OaCusMaterialList oaCusMaterialList : oaCusMaterialLists) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				String orderNumsString[] = oaCusMaterialList.getOrderNum().split("-");
				boolean orderNumFlag = false;
				for (String string : orderNumsString) {
					if (StringUtils.isNotEmpty(string)) {
						orderNumFlag = true;
						break;
					}
				}
				if (orderNumFlag || StringUtils.isNotEmpty(oaCusMaterialList.getMaterialName()) || (null != oaCusMaterialList.getAmount() && oaCusMaterialList.getAmount() > 0) || (null != oaCusMaterialList.getConsume() && oaCusMaterialList.getConsume() > 0) || (null != oaCusMaterialList.getTotal() && oaCusMaterialList.getTotal() > 0) || "否".equals(oaCusMaterialList.getIsComplete()) || StringUtils.isNotEmpty(oaCusMaterialList.getMemo())) {
					if (null == oaCusMaterialList.getAmount()) {
						oaCusMaterialList.setAmount(0f);
					}
					if (null == oaCusMaterialList.getConsume()) {
						oaCusMaterialList.setConsume(0f);
					}
					if (null == oaCusMaterialList.getTotal()) {
						oaCusMaterialList.setTotal(0f);
					}
					oaCusMaterialList.setOaOrderId(orderId);
					saveObject(oaCusMaterialList);
				}
			}
		}
	}

	/**
	 * 保存第三个节点的信息
	 *
	 * @author yunpeng
	 * @return
	 */
	//update by 张华 2015-01-20
	private boolean saveOrderThird(OaOrder oaOrder) {
		try {
			String type = oaOrder.getType();
			int orderId = oaOrder.getId();
			// 1.保存用料说明的部分信息
			saveMaterialInfo(type, oaOrder.getId());
			// 保存尺寸表表头
			saveClothesSize(oaOrder);
			// 2.保存尺寸表的详细信息
			saveClothesSizeDetail(orderId);
			// 3.保存加工说明信息
			saveProcessExplain(orderId);
			// 4.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrder.getOaOrderDetail());

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 保存第8个节点(财务)的信息
	 *
	 * @author yunpeng
	 * @return
	 */
	private boolean saveOrderDaHuoEight(OaOrder oaOrder) {
		try {
			// 1.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrder.getOaOrderDetail());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}
	/**
	 * 保存尺寸表表头和order表中的样板基码
	 *
	 * @param orderId
	 */
	// update by 张华 2015-01-20
	private void saveClothesSize(OaOrder oaOrder) throws Exception {
		oaClothesSize.setOaOrderId(oaOrder.getId());
		manager.saveObject(oaClothesSize);
		oaOrder.setSampleSize(oaClothesSize.getSampleSize());
		manager.saveObject(oaOrder);
	}

	/**
	 *
	 * @Title: saveManegeInfo
	 * @Description: TODO保存管理信息
	 *
	 * @param orderDetailId
	 * @throws Exception
	 */
	// update by 张华 2015-01-20
	private void saveManegeInfo(int orderDetailId) throws Exception {
		OaOrderDetail oaOrderDetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, orderDetailId);
		if (StringUtils.isNotBlank(this.oaOrderDetail.getOtherFile())) {
			oaOrderDetail.setOtherFile(this.oaOrderDetail.getOtherFile());
		}
		if (StringUtils.isNotBlank(this.oaOrderDetail.getContent())) {
			oaOrderDetail.setContent(this.oaOrderDetail.getContent());
		}
		if (StringUtils.isNotBlank(this.oaOrderDetail.getWorker())) {
			oaOrderDetail.setWorker(this.oaOrderDetail.getWorker());
		} else {
			oaOrderDetail.setWorker(WebUtil.getCurrentLoginBx().getLoginName());
		}
		if (null != this.oaOrderDetail.getWorkTime()) {
			oaOrderDetail.setWorkTime(this.oaOrderDetail.getWorkTime());
		}
		manager.saveObject(oaOrderDetail);
	}

	/**
	 * json方式保存异动跟踪信息
	 */
	public void jsonSaveTracke() {
		Map resMap = new HashMap();
		try {
			Date now = new Date();
			Timestamp tamp = new Timestamp(now.getTime());
			oaTracke.setTime(tamp);
			oaTracke.setUser(WebUtil.getCurrentLoginBx().getLoginName());
			manager.saveObject(oaTracke);
			resMap.put("oaTrackeUser", oaTracke.getUser());
			resMap.put("oaTrackeTime", DateUtil.formatDate(oaTracke.getTime()));
			resMap.put("code", 0);
			resMap.put("msg", "保存成功");
		} catch (Exception e) {
			// TODO: handle exception
			resMap.put("msg", "保存失败");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: saveProcessExplain
	 * @Description: TODO技术节点保存加工说明
	 *
	 * @param orderId
	 * @throws Exception
	 */
	// update by 张华 2015-01-20
	private void saveProcessExplain(int orderId) throws Exception {
		oaProcessExplain.setOaOrderId(orderId);
		manager.saveObject(oaProcessExplain);
	}

	/**
	 *
	 * @Title: saveClothesSizeDetail
	 * @Description: TODO保存尺寸详情列表
	 *
	 * @param orderId
	 * @throws Exception
	 */
	// update by 张华 2015-01-20
	private void saveClothesSizeDetail(int orderId) throws Exception {
		// 删除尺寸表详情信息
		if (StringUtils.isNotEmpty(clothesSizeDetailDelIds)) {
			String[] clothesSizeDetailDelId = clothesSizeDetailDelIds.split(",");
			for (String delId : clothesSizeDetailDelId) {
				if (StringUtils.isNotEmpty(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaClothesSizeDetail oaClothesSizeDetail = (OaClothesSizeDetail) manager.getObject(OaClothesSizeDetail.class, id);
						if (null != oaClothesSizeDetail && null != oaClothesSizeDetail.getId()) {
							delObject(oaClothesSizeDetail);
						}
					} catch (NumberFormatException e) {
						// TODO: handle exception
					}
				}
			}
		}
		// 循环存储尺寸表详情信息
		if (null != oaClothesSizeDetails) {
			for (OaClothesSizeDetail oaClothesSizeDetail : oaClothesSizeDetails) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				String orderNumsString[] = oaClothesSizeDetail.getClothSize().split("-");
				boolean orderNumFlag = false;
				for (String string : orderNumsString) {
					if (StringUtils.isNotEmpty(string)) {
						orderNumFlag = true;
						break;
					}
				}
				if (orderNumFlag || StringUtils.isNotEmpty(oaClothesSizeDetail.getPosition()) || StringUtils.isNotEmpty(oaClothesSizeDetail.getSamplePageSize())
						|| StringUtils.isNotEmpty(oaClothesSizeDetail.getTolerance())) {
					oaClothesSizeDetail.setOaOrder(orderId);
					saveObject(oaClothesSizeDetail);
				}
			}
		}
	}

	/**
	 *
	 * @Title: saveMaterialInfo
	 * @Description: TODO保存技术节点用料说明信息
	 *
	 * @param type
	 * @param orderId
	 * @throws Exception
	 */
	// update by 张华 2015-01-20
	private void saveMaterialInfo(String type, int orderId) throws Exception {
		// 删除用料搭配明细信息
		// update by 张华 2015-01-20
		delOaMaterialList();

		if (StringUtils.isNotBlank(type) && "3".equals(type) && null != oaDaHuoInfos) {
			// 保存之前要先删除要删除的数据 删除大货清单信息
			// update by 张华 2015-01-20
			delOaDaHuoInfo();

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					OaDaHuoInfo dhi = oaDaHuoInfos.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || null != dhi.getBuffon()
							|| null != dhi.getUnitNum() || StringUtils.isNotBlank(dhi.getPosition()) || StringUtils.isNotBlank(dhi.getItMemo())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) manager.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							manager.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							manager.saveObject(ml);
						}
						// 保存大货的用料说明信息
						OaDaHuoInfo odi = oaDaHuoInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) manager.getObject(OaDaHuoInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaHuoInfo
							oaDaHuoInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaHuoInfo.setUnitNum(odi.getUnitNum()); // 单件用量
							oaDaHuoInfo.setPosition(odi.getPosition()); // 位置
							// oaDaHuoInfo.setItMemo(odi.getItMemo()); // 备注
							// if (null == oaDaHuoInfo || null == oaDaHuoInfo.getId()) {
							// oaDaHuoInfo.setOaMaterialList(ml.getId()); // 用料表Id
							// }
							manager.saveObject(oaDaHuoInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							manager.saveObject(odi);
						}
					}
				}
			}
		} else if (StringUtils.isNotBlank(type) && "2".equals(type) && null != oaDaBanInfos) {
			// 保存之前要先删除要删除的数据 删除打版清单信息
			// update by 张华 2015-01-20
			delOaDaBanInfo();

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					OaDaBanInfo dbi = oaDaBanInfos.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || null != dbi.getBuffon()
							|| null != dbi.getUnitNum() || StringUtils.isNotBlank(dbi.getPosition()) || StringUtils.isNotBlank(dbi.getItMemo())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) manager.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							manager.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							manager.saveObject(ml);
						}
						// 保存打板的用料说明信息
						OaDaBanInfo odi = oaDaBanInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) manager.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaHuoInfo
							oaDaBanInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaBanInfo.setUnitNum(odi.getUnitNum()); // 单件用量
							oaDaBanInfo.setPosition(odi.getPosition()); // 位置
							// oaDaBanInfo.setItMemo(odi.getItMemo()); // 备注
							// if (null == oaDaBanInfo || null == oaDaBanInfo.getId()) {
							// oaDaBanInfo.setOaMaterialList(ml.getId()); // 用料表Id
							// }
							manager.saveObject(oaDaBanInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							manager.saveObject(odi);
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @Title: delOaDaHuoInfo
	 * @Description: TODO删除大货清单数据
	 *
	 * @author 张华
	 */
	private void delOaDaHuoInfo() {
		if (StringUtils.isNotBlank(materialDescDelIds)) {
			String[] delIds = materialDescDelIds.split(",");
			for (String delId : delIds) {
				if (StringUtils.isNotBlank(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) manager.getObject(OaDaHuoInfo.class, id);
						if (null != oaDaHuoInfo && null != oaDaHuoInfo.getId()) {
							manager.delObject(oaDaHuoInfo);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 *
	 * @Title: delOaDaBanInfo
	 * @Description: TODO删除打版清单数据
	 *
	 * @author 张华
	 */
	private void delOaDaBanInfo() {
		if (StringUtils.isNotBlank(materialDescDelIds)) {
			String[] delIds = materialDescDelIds.split(",");
			for (String delId : delIds) {
				if (StringUtils.isNotBlank(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) manager.getObject(OaDaBanInfo.class, id);
						if (null != oaDaBanInfo && null != oaDaBanInfo.getId()) {
							manager.delObject(oaDaBanInfo);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 保存面辅料采购清单
	 *
	 * @param type
	 * @param orderId
	 */
	// update by 张华 2015-01-20
	private void saveMaterialDescInfo(String type, int orderId) throws Exception {
		// 删除面辅料采购信息
		delOaMaterialList();

		if (StringUtils.isNotBlank(type) && "3".equals(type)) {
			// 保存之前要先删除要删除的数据 删除大货清单信息
			// update by 张华 2015-01-20
			delOaDaHuoInfo();

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {

					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (null != ml) {
						if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor())
								|| StringUtils.isNotBlank(ml.getSupplierName()) || StringUtils.isNotBlank(ml.getSupplierAddr()) || StringUtils.isNotBlank(ml.getSupplierTel())
								|| StringUtils.isNotBlank(ml.getPosition())) {
							orderNumFlag = true;
						}
						if (orderNumFlag) {
							// 保存大货采购清单
							OaDaHuoInfo odi = oaDaHuoInfos.get(i);
							if (null != ml && null != ml.getId()) {
								OaMaterialList oml = (OaMaterialList) manager.getObject(OaMaterialList.class, ml.getId());
								oml.setMaterialProp(ml.getMaterialProp());
								oml.setMaterialName(ml.getMaterialName());
								oml.setType(ml.getType());
								oml.setColor(ml.getColor());
								oml.setSupplierName(ml.getSupplierName());
								oml.setSupplierAddr(ml.getSupplierAddr());
								oml.setSupplierTel(ml.getSupplierTel());
								oml.setPosition(ml.getPosition());
								// 面料需要保存order_num
								oml.setOrderNum(ml.getOrderNum());
								manager.saveObject(oml);
							} else {
								ml.setOaOrderId(orderId);
								manager.saveObject(ml);
							}
							if (null != odi && null != odi.getId()) { // 说明已存在
								OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) manager.getObject(OaDaHuoInfo.class, odi.getId()); // 先查询
								// 设置新的数据到oaDaHuoInfo
								oaDaHuoInfo.setBuffon(odi.getBuffon()); // 布封
								oaDaHuoInfo.setUnitNum(odi.getUnitNum()); // 标准单件用量
								oaDaHuoInfo.setNeedNum(odi.getNeedNum());// 需求数量
								oaDaHuoInfo.setOrg(odi.getOrg());// 采购单位
								oaDaHuoInfo.setNum(odi.getNum());// 采购数量
								oaDaHuoInfo.setPrice(odi.getPrice());// 单价
								oaDaHuoInfo.setTotalPrice(odi.getTotalPrice());// 总金额
								oaDaHuoInfo.setTestPrice(odi.getTestPrice());// 验布费用
								oaDaHuoInfo.setFreight(odi.getFreight());// 运费
								oaDaHuoInfo.setTotal(odi.getTotal());// 总计
								oaDaHuoInfo.setBuyerLoss(odi.getBuyerLoss());// 采购损
								oaDaHuoInfo.setPaperTube(odi.getPaperTube());// 纸筒
								oaDaHuoInfo.setDeviation(odi.getDeviation());// 空差
								oaDaHuoInfo.setPurMemo(odi.getPurMemo());// 备注
								// oaDaHuoInfo.setPosition(odi.getPosition()); // 位置
								if (null == oaDaHuoInfo || null == oaDaHuoInfo.getId()) {
									oaDaHuoInfo.setOaMaterialList(ml.getId()); // 用料表Id
								}
								manager.saveObject(oaDaHuoInfo);
							} else { // 新数据，直接保存
								odi.setOaMaterialList(ml.getId());
								manager.saveObject(odi);
							}
						}
					}
				}
			}
		} else if (StringUtils.isNotBlank(type) && "2".equals(type)) {
			// 保存之前要先删除要删除的数据 删除打版清单信息
			// update by 张华 2015-01-20
			delOaDaBanInfo();

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor())
							|| StringUtils.isNotBlank(ml.getSupplierName()) || StringUtils.isNotBlank(ml.getSupplierAddr()) || StringUtils.isNotBlank(ml.getSupplierTel())
							|| StringUtils.isNotBlank(ml.getPosition())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) manager.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							oml.setSupplierName(ml.getSupplierName());
							oml.setSupplierAddr(ml.getSupplierAddr());
							oml.setSupplierTel(ml.getSupplierTel());
							oml.setPosition(ml.getPosition());
							manager.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							manager.saveObject(ml);
						}
						// 保存大货采购清单
						OaDaBanInfo odi = oaDaBanInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) manager.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaBanInfo
							oaDaBanInfo.setWeight(odi.getWeight());// 克重
							oaDaBanInfo.setComponent(odi.getComponent());// 成分
							oaDaBanInfo.setDeliveryTime(odi.getDeliveryTime());// 货期
							oaDaBanInfo.setBuyerLoss(odi.getBuyerLoss());// 采购损
							oaDaBanInfo.setPaperTube(odi.getPaperTube());// 纸筒
							oaDaBanInfo.setDeviation(odi.getDeviation());// 空差
							oaDaBanInfo.setShearPrice(odi.getShearPrice());// 散剪单价
							oaDaBanInfo.setUnit(odi.getUnit());// 单位
							oaDaBanInfo.setGoodsUnit(odi.getGoodsUnit());// 大货单位
							oaDaBanInfo.setGoodsPrice(odi.getGoodsPrice());// 大货单价
							oaDaBanInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaBanInfo.setPurMemo(odi.getPurMemo()); // 备注
							if (null == oaDaBanInfo || null == oaDaBanInfo.getId()) {
								oaDaBanInfo.setOaMaterialList(ml.getId()); // 用料表Id
							}
							manager.saveObject(oaDaBanInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							manager.saveObject(odi);
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @Title: jsonGetThirdNode
	 * @Description: TODO流程改造第三节点，获取第三个节点页面信息
	 *
	 * @author 张华
	 */
	public void jsonGetThirdNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("oaOrder.id");
			String orderNumId = Struts2Utils
					.getParameter("oaOrder.oaOrderNumId");
			String type = Struts2Utils.getParameter("oaOrder.type");// 订单的类型
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "3";// 查询管理信息时节点，默认为大货
			if ("2".equals(type)) { // 为样衣时，技术节点为4
				node = "4";
			}
			int orderId = 0;

			// 1.判断订单Id是否为空
			orderId = Integer.valueOf(orderIds);
			// 1查询用料说明的内容
			getMaterialDesc(orderId, type, resMap);
			// 2.查询尺寸表头
			getClothesSize(orderId, resMap);
			// 3.查询尺码表内容表头
			int orderNum = Integer.valueOf(orderNumId);
			getOaOrderNum(orderNum, resMap);
			// 4.查询尺寸表中的内容
			getClothesSizeDetail(orderId, resMap);
			// 5.查询加工说明
			getProcessExplain(orderId, resMap);
			// 6.查询管理信息
			getManagerInfo(orderId, node, wfStepIndex, resMap);
			// 7.查询异动跟踪信息
			getTracke(orderId, node, resMap);

			resMap.put("code", 0);
			resMap.put("msg", "技术节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "技术节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}


	private void getWorker(int orderId, Map resMap) {
		try {
			FSPBean fsp = new FSPBean();
			fsp.set("oa_order", orderId);
			fsp.set("wf_step", "4");
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_WORKER);
			bean = manager.getOnlyObjectBySql(fsp);
			if (bean != null) {
				resMap.put("worker", bean.get("worker") != null ? bean.get("worker") : "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @Title: getManagerInfo
	 * @Description: TODO查询管理信息 update 2015-1-8
	 *
	 * @author 张华
	 * @param orderId
	 * @param node
	 * @param wfStepIndex
	 * @param resMap
	 */
	private void getManagerInfo(int orderId, String nodeStr, String wfStepIndex, Map resMap) throws Exception {
		LazyDynaMap tampMap = null;
		int stepIndex = Integer.parseInt(wfStepIndex); // 当前正在处理的订单节点index
		int node = Integer.parseInt(nodeStr);

		// 如果要获取信息的节点已经处理过或者正在处理才进行查询管理信息
		if (stepIndex >= node) {
			FSPBean fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MANAGER_INFO_BY_SQL);
			fsp.set("orderId", orderId);
			fsp.set("node", nodeStr);
			fsp.set(FSPBean.FSP_ORDER, " order by id desc");
			tampMap = manager.getOnlyObjectBySql(fsp);
		}

		Map oaOrderDetail = new HashMap();
		if (null != tampMap) {
			oaOrderDetail.put("id", (null == tampMap.get("id")) ? "" : tampMap.get("id"));
			oaOrderDetail.put("operator", (null == tampMap.get("operator")) ? "" : tampMap.get("operator"));
			oaOrderDetail.put("worker", (null == tampMap.get("worker")) ? tampMap.get("operator") : tampMap.get("worker"));
			if (null != tampMap.get("wf_real_start")) {
				String tampDate = DateUtil.formatDate((Date) tampMap.get("wf_real_start"));
				oaOrderDetail.put("wf_real_start", tampDate);// 实际开始时间
			} else {
				oaOrderDetail.put("wf_real_start", "");// 实际开始时间
			}
			if (null != tampMap.get("work_time")) {
				String tampDate = DateUtil.formatDate((Date) tampMap.get("work_time"));
				oaOrderDetail.put("processTime", tampDate);// 处理日期
			} else {
				Date now = new Date();
				String processTime = DateUtil.formatDate(now);
				oaOrderDetail.put("processTime", processTime);// 处理日期
			}
			oaOrderDetail.put("content", (null == tampMap.get("content")) ? "" : tampMap.get("content"));// 技术备注
			oaOrderDetail.put("other_file", (null == tampMap.get("other_file")) ? "" : tampMap.get("other_file"));// 附件
			oaOrderDetail.put("other_file_name", PathUtil.url2FileName(oaOrderDetail.get("other_file").toString())); // 附件名称
			oaOrderDetail.put("wf_step", tampMap.get("wf_step"));// 节点ID

			Timestamp wf_real_start = (Timestamp) tampMap.get("wf_real_start");// 实际开始时间
			Timestamp wf_real_finish = (Timestamp) tampMap.get("wf_real_finish"); // 实际结束时间
			long wf_step_duration = (long) tampMap.get("wf_step_duration"); // 标准时长

			if (null != wf_real_finish) {
				String tampDate = DateUtil.formatDate((Date) wf_real_finish);
				oaOrderDetail.put("wf_real_finish", tampDate);// 实际完成时间
			} else {
				wf_real_finish = new Timestamp(System.currentTimeMillis());
				oaOrderDetail.put("wf_real_finish", "");// 实际完成时间
			}
			String standardTime = DateUtil.longTohhmm(wf_step_duration);
			oaOrderDetail.put("wf_step_duration", standardTime);// 标准工时

			// 得到进度信息
			long between = wf_step_duration - BizUtil.getWorkTimeBetween(wf_real_finish, wf_real_start);
			StringBuffer schedule = new StringBuffer(between >= 0 ? "提前" : "超时");
			between = Math.abs(between);
			schedule.append(WebUtil.getTimeDisPlay(between));
			oaOrderDetail.put("schedule", schedule);// 进度信息

			// 计算实际耗时
			if (null != tampMap.get("wf_real_start") && null != tampMap.get("wf_real_finish")) {
				long real_time = BizUtil.getWorkTimeBetween((Timestamp) tampMap.get("wf_real_finish"), (Timestamp) tampMap.get("wf_real_start"));
				String realTime = DateUtil.longTohhmm(real_time);
				oaOrderDetail.put("real_time", realTime);// 实际耗时
			} else {
				oaOrderDetail.put("real_time", "");// 实际耗时
			}
			// 计算当前订单流入该节点的耗时= （订单流入日期-下单日期+1）÷（交货日期-下单日期+1）*100%
			OaOrder oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
			Date begin_time = oaOrder.getBeginTime();
			Date except_finish = oaOrder.getExceptFinish();
			// 查询出最早的订单流入该节点的日期
			if (null != tampMap.get("wf_real_start")) {
				Timestamp tamp = (Timestamp) tampMap.get("wf_real_start");
				double time = ((double) (tamp.getTime() - begin_time.getTime() + 24 * 60 * 60 * 1000) / (double) (except_finish.getTime() - begin_time.getTime() + 24 * 60 * 60 * 1000)) * 100;
				DecimalFormat df = new DecimalFormat("#");
				String time_consume = df.format(time);
				oaOrderDetail.put("step_start_time_consume", time_consume);// 设置当前节点流入的耗时
			} else {
				oaOrderDetail.put("step_start_time_consume", "");
			}
			// 计算当前订单流出该节点的耗时= （订单流出日期-下单日期+1）÷（交货日期-下单日期+1）*100%
			if (null != tampMap.get("wf_real_finish")) {
				Timestamp tamp = (Timestamp) tampMap.get("wf_real_finish");
				double time = ((double) (tamp.getTime() - begin_time.getTime() + 24 * 60 * 60 * 1000) / (double) (except_finish.getTime() - begin_time.getTime() + 24 * 60 * 60 * 1000)) * 100;
				DecimalFormat df = new DecimalFormat("#");
				String time_consume = df.format(time);
				oaOrderDetail.put("step_finish_time_consume", time_consume);// 设置当前节点流入的耗时
			} else {
				oaOrderDetail.put("step_finish_time_consume", "");
			}
		}

		resMap.put("oaOrderDetail", oaOrderDetail);
	}

	/**
	 *
	 * @Title: getTracke
	 * @Description: TODO查询订单异动信息
	 *
	 * @param orderId
	 * @param node
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getTracke(int orderId, String node, Map resMap)
			throws Exception {
		FSPBean fspBean = new FSPBean();// 在同一个方法中使用多个fsp时要重新生成 否则会重复内容
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_TRACKE_BY_EQL);
		fspBean.set("orderId", orderId);
		fspBean.set("node", node);
		List<OaTracke> oaTrackes = manager.getObjectsByEql(fspBean);
		List<Map> oaTrackeList = new ArrayList<Map>();
		for (OaTracke tracke : oaTrackes) {
			Map mapOaTrackes = new HashMap();
			mapOaTrackes.put("id", tracke.getId());
			mapOaTrackes.put("memo", tracke.getMemo());
			mapOaTrackes.put("node", tracke.getNode());
			mapOaTrackes.put("user", tracke.getUser());
			mapOaTrackes.put("oaOrder", tracke.getOaOrder());
			String tempTime = DateUtil.formatDate(tracke.getTime());
			mapOaTrackes.put("time", tempTime);
			oaTrackeList.add(mapOaTrackes);
		}
		resMap.put("oaTrackes", oaTrackeList);
	}

	/**
	 *
	 * @Title: getProcessExplain
	 * @Description: TODO流程改造第三节点，获取加工说明
	 *
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getProcessExplain(int orderId, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_PROCESS_EXPLAIN_BY_EQL);
		fsp.set("orderId", orderId);
		OaProcessExplain processExplain = (OaProcessExplain) manager
				.getOnlyObjectByEql(fsp);
		resMap.put("processExplain", processExplain);
	}

	/**
	 *
	 * @Title: getClothesSizeDetail
	 * @Description: TODO流程改造第三节点，获取尺寸表信息列表
	 *
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getClothesSizeDetail(int orderId, Map resMap) throws Exception {
		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CLOTHES_SIZE_DETAIL_BY_SQL);
		fspBean.set("orderId", orderId);
		List<LazyDynaMap> list = manager.getObjectsBySql(fspBean);
		List<OaClothesSizeDetail> oaClothesSizeDetails = copyOaClothesSizeDetailList2Vo(list);
		resMap.put("oaClothesSizeDetails", oaClothesSizeDetails);
	}

	/**
	 *
	 * @Title: copyOaClothesSizeDetailList2Vo
	 * @Description: TODO尺寸详情表，由List<LazyDynaMap>转为List<OaClothesSizeDetail>
	 *
	 * @author 张华
	 * @param list
	 * @return
	 */
	private List<OaClothesSizeDetail> copyOaClothesSizeDetailList2Vo(List<LazyDynaMap> list) {
		List<OaClothesSizeDetail> oaClothesSizeDetails = new ArrayList<OaClothesSizeDetail>();
		for (LazyDynaMap map : list) {
			OaClothesSizeDetail oaClothesSizeDetail = new OaClothesSizeDetail();
			oaClothesSizeDetail.setId((Integer) map.get("id"));
			oaClothesSizeDetail.setOaOrder((Integer) map.get("oa_order"));
			oaClothesSizeDetail.setPosition((String) map.get("position"));
			oaClothesSizeDetail.setClothSize((String) map.get("cloth_size"));
			oaClothesSizeDetail.setSamplePageSize((String) map.get("sample_page_size"));
			oaClothesSizeDetail.setTolerance((String) map.get("tolerance"));
			oaClothesSizeDetails.add(oaClothesSizeDetail);
		}

		return oaClothesSizeDetails;
	}

	/**
	 *
	 * @Title: getOaOrderNum
	 * @Description: TODO流程改造第三节点，获取尺码表内容表头
	 *
	 * @param orderNum
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getOaOrderNum(int orderNum, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_NUM_TITLE_BY_EQL);
		fsp.set("orderNumId", orderNum);
		OaOrderNum oaOrderNum = (OaOrderNum) manager.getOnlyObjectByEql(fsp);
		resMap.put("oaOrderNum", oaOrderNum);
	}

	/**
	 *
	 * @Title: getClothesSize
	 * @Description: TODO流程改造第三节点，获取尺寸表头信息
	 *
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getClothesSize(int orderId, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CLOTHES_SIZE_BY_EQL);
		fsp.set("orderId", orderId);
		OaClothesSize oaClothesSize = (OaClothesSize) manager
				.getOnlyObjectByEql(fsp);
		resMap.put("oaClothesSize", oaClothesSize);
	}

	/**
	 *
	 * @Title: getMaterialDesc
	 * @Description: TODO流程改造第三节点，获取用料说明列表
	 *
	 * @author 张华
	 * @param orderId
	 * @param type
	 * @param resMap
	 * @throws Exception
	 */
	public void getMaterialDesc(int orderId, String type, Map resMap) throws Exception {
		List<Map> rList = new ArrayList<Map>();
		FSPBean fsp = new FSPBean();
		if ("2".equals(type)) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_BAN_INFO_BY_SQL);
		} else if ("3".equals(type)) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_HUO_INFO_BY_SQL);
		}
		fsp.set("orderId", orderId);
		List<LazyDynaMap> oaDaBanInfos = manager.getObjectsBySql(fsp);
		for (LazyDynaMap map : oaDaBanInfos) {
			Map tamp = new HashMap();
			tamp.put("material_prop", (null == map.get("material_prop")) ? "" : map.get("material_prop"));
			tamp.put("material_name", (null == map.get("material_name")) ? "" : map.get("material_name"));
			tamp.put("type", (null == map.get("type")) ? "" : map.get("type"));
			tamp.put("color", (null == map.get("color")) ? "" : map.get("color"));
			tamp.put("id", (null == map.get("id")) ? "" : map.get("id"));
			tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
			tamp.put("unit_num", (null == map.get("unit_num")) ? "" : map.get("unit_num"));
			tamp.put("position", (null == map.get("position")) ? "" : map.get("position"));
			tamp.put("memo", (null == map.get("memo")) ? "" : map.get("memo"));
			tamp.put("oa_material_list", map.get("materiallistid"));
			rList.add(tamp);
		}
		resMap.put("materialDescList", rList);
	}

	public String getNickName() {
		return nickName;
	}

	/**
	 * 通过类型得到技术页面尺寸表的部位说明和公差
	 */
	public void getClothesPositionAndTolerance() {
		String position = Struts2Utils.getParameter("position");
		Map resMap = new HashMap();
		if (StringUtils.isNotBlank(position)) {
			resMap.put("code", 0);
			resMap.put("position", XbdBuffer.getClothesSizesByPosition(position + "_rule"));
			resMap.put("tolerance", XbdBuffer.getClothesSizesByPosition(position + "_tolerance"));
		} else {
			resMap.put("msg", "没有服装的类型！");
		}
		Struts2Utils.renderJson(resMap);

	}

	// 订单节点 Excel
	// by fangwei 2014-12-17
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> createOrderInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();
		params.put("1,1", oaOrder.getCusCode());
		params.put("1,6", oaOrder.getSellOrderCode());
		params.put("1,12", DateUtil.formatDate(oaOrder.getBeginTime()));
		params.put("2,1", oaOrder.getOrderCode());
		params.put("2,6,", oaOrder.getStyleDesc());
		params.put("2,12", DateUtil.formatDate(oaOrder.getExceptFinish()));
		params.put("3,1", oaOrder.getSales());
		params.put("3,6", oaOrder.getTpeName());
		params.put("3,12", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
		params.put("4,1", oaOrder.getMrName());
		params.put("4,6", oaOrder.getConfirmStaff());
		params.put("6,10,12,12", oaOrder.getPictureFront());
		params.put("6,12,12,14", oaOrder.getPictureBack());
		params.put("12,1", oaOrder.getStyleCraft());
		params.put("12,12", oaOrder.getSampleSize());
		params.put("31,0", oaOrder.getMemo());
		params.put("43,1", oaOrder.getSendtype());
		// 数量信息
		OaOrderNum oaOrderNum = (OaOrderNum) manager.getObject(OaOrderNum.class, oaOrder.getOaOrderNumId());
		String[] titles = oaOrderNum.getTitle().split("-");
		if (titles.length > 7)
			throw new RuntimeException("the oa_order_num info is invalid!");
		for (int i = 0; i < titles.length; i++) {
			params.put("6," + (i + 1), titles[i]);
			params.put("38," + (i + 3), titles[i]);
		}
		String[] infos = oaOrderNum.getNumInfo().split(",");
		int[] totals = new int[titles.length + 1];
		for (int i = 0; i < infos.length; i++) {
			String[] nums = infos[i].split("-");
			int sum = 0;
			for (int j = 0; j < nums.length; j++) {
				params.put(i + 7 + "," + j, nums[j]);
				if (j == 0)
					continue;
				totals[j - 1] += Integer.parseInt(nums[j]);
				sum += Integer.parseInt(nums[j]);
			}
			totals[totals.length - 1] += sum;
			params.put(i + 7 + ",9", sum + "");
		}
		for (int i = 0; i < totals.length - 1; i++) {
			params.put("11," + (i + 1), totals[i] + "");
		}
		params.put("11,9", totals[totals.length - 1] + "");

		// 材料清单
		fsp.set("oa_order_id", oaOrder.getId());
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_MATERIAL_LIST);
		beans = getObjectsBySql(fsp);
		int t = 0;
		for (DynaBean bean : beans) {
			params.put((15+ t) + ",0", bean.get("material_prop")==null?"":bean.get("material_prop").toString());
			params.put((15 + t) + ",1", bean.get("material_name")==null?"":bean.get("material_name").toString());
			params.put((15+ t) + ",3", bean.get("type")==null?"":bean.get("type").toString());
			params.put((15 + t) + ",4", bean.get("color")==null?"":bean.get("color").toString());
			params.put((15 + t) + ",5", bean.get("supplier_name")==null?"":bean.get("supplier_name").toString());
			params.put((15 + t) + ",6", bean.get("supplier_addr")==null?"":bean.get("supplier_addr").toString());
			params.put((15 + t) + ",10", bean.get("supplier_tel")==null?"":bean.get("supplier_tel").toString());
			params.put((15 + t) + ",11", bean.get("order_num")==null?"":bean.get("order_num").toString());
			params.put((15 + t++) + ",12", bean.get("position")==null?"":bean.get("position").toString());
		}

		// 客供料明细
		fsp.set("oa_order_id", oaOrder.getId());
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_CUS_MATERIAL_LIST);
		beans = getObjectsBySql(fsp);

		// beans.size() >4 则需要动态增行
		int cusSize = beans.size();
		if (cusSize > 4) {
			dynaRow.put("40", (cusSize - 4) + "");
		} else {
			cusSize = 4;
		}
		params.put(39 + cusSize + ",1", oaOrder.getSendtype());



		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", (Integer.parseInt(node))+"");
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		params.put(40 + cusSize + ",1", bean.get("operator")==null?"":bean.get("operator").toString());

		int ct = 0;
		for (DynaBean bean : beans) {
			params.put((39 + ct) + ",0", bean.get("material_name")==null?"":bean.get("material_name").toString());
			params.put((39 + ct) + ",1", bean.get("amount")==null?"":bean.get("amount").toString());
			params.put((39 + ct) + ",2", bean.get("consume")==null?"":bean.get("consume").toString() + "%");
			params.put((39 + ct) + ",11", bean.get("total")==null?"":bean.get("total").toString());
			params.put((39 + ct) + ",12", bean.get("is_complete")==null?"":bean.get("is_complete").toString());
			String[] nums = ((String) bean.get("order_num")).split("-");
			for (int j = 0; j < nums.length; j++) {
				params.put((39 + ct) + "," + (3 + j), nums[j]);
			}
			params.put((39 + ct++) + ",13", bean.get("memo")==null?"":bean.get("memo").toString());
		}
		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// 技术节点 Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getTechnologyInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();
		params.put("1,1", oaOrder.getSellOrderCode());
		params.put("1,3,", oaOrder.getStyleDesc());
		params.put("1,8", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");

		// 材料清单
		fsp = new FSPBean();
		fsp.set("orderId", oaOrder.getId());
		if ("2".equals(oaOrder.getType())) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_BAN_INFO_BY_SQL);
		} else if ("3".equals(oaOrder.getType())) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_HUO_INFO_BY_SQL);
		}
		beans = getObjectsBySql(fsp);
		int t = 0;
		for (DynaBean bean : beans) {
			params.put((3 + t) + ",0", bean.get("material_prop")==null?"":bean.get("material_prop").toString());
			params.put((3 + t) + ",1", bean.get("material_name")==null?"":bean.get("material_name").toString());
			params.put((3 + t) + ",2", bean.get("type")==null?"":bean.get("type").toString());
			params.put((3 + t) + ",3", bean.get("color")==null?"":bean.get("color").toString());
			params.put((3 + t) + ",4", bean.get("buffon")==null?"":bean.get("buffon").toString());
			params.put((3 + t) + ",5", bean.get("unit_num")==null || "".equals(bean.get("unit_num").toString()) ? "": decimalFormat.format(Float.parseFloat(bean.get("unit_num").toString())));
			params.put((3 + t++) + ",6", bean.get("position")==null?"":bean.get("position").toString());
		}

		// 查询尺寸表表头
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_NUM_TITLE_BY_EQL);
		fsp.set("orderNumId", oaOrder.getOaOrderNumId());
		OaOrderNum oaOrderNum = (OaOrderNum) manager.getOnlyObjectByEql(fsp);
		t = 0;
		for (String title : oaOrderNum.getTitle().split("-")) {
			params.put("19," + (1 + t++), title);
		}

		// 查询尺寸表
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CLOTHES_SIZE_BY_EQL);
		fsp.set("orderId", oaOrder.getId());
		OaClothesSize oaClothesSize = (OaClothesSize) manager.getOnlyObjectByEql(fsp);
		if (null != oaClothesSize) {
			params.put("18,5", oaClothesSize.getUnit());
			params.put("18,8", oaClothesSize.getSampleSize());
		}

		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CLOTHES_SIZE_DETAIL_BY_SQL);
		fsp.set("orderId", oaOrder.getId());
		List<LazyDynaMap> OaClothesSizeDetailList = manager.getObjectsBySql(fsp);
		List<OaClothesSizeDetail> oaClothesSizeDetails = copyOaClothesSizeDetailList2Vo(OaClothesSizeDetailList);

		int size = 12;
		if (oaClothesSizeDetails.size()>0) {
			// size >12 则需要动态增行
			size = oaClothesSizeDetails.size();
			if (size > 12) {
				dynaRow.put("31", (size - 12) + "");
			} else {
				size = 12;
			}
			t = 0;
			for (OaClothesSizeDetail oaClothesSizeDetail : oaClothesSizeDetails) {
				params.put((20 + t) + ",0", oaClothesSizeDetail.getPosition());
				String nums = oaClothesSizeDetail.getClothSize();
				int m = 0;
				for (String num : nums.split("-")) {
					if(StringUtils.isNotBlank(num)){
						try {
							params.put((20 + t) + "," + (1 + m++), decimalFormat.format(Float.parseFloat(num)));
						} catch (Exception e) {
							params.put((20 + t) + "," + (1 + m++), "");
						}
					}
				}
				params.put((20 + t) + ",8", oaClothesSizeDetail.getSamplePageSize());
				params.put((20 + t++) + ",9", oaClothesSizeDetail.getTolerance());
			}
		}

		// 工艺要求
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_PROCESS_EXPLAIN_BY_EQL);
		fsp.set("orderId", oaOrder.getId());
		OaProcessExplain oaProcessExplain = (OaProcessExplain) manager.getOnlyObjectByEql(fsp);
		if (null != oaProcessExplain) {
			params.put((32 + size - 12) + ",0", "特殊工艺要求：" + oaProcessExplain.getSpecialArt());
			params.put((33 + size - 12) + ",0", "裁床工艺要求：" + oaProcessExplain.getCutArt());
			params.put((32 + size - 12) + ",5,"+(34 + size - 12) + ",10",oaProcessExplain.getMeasurePic());

			params.put((34 + size - 12) + ",0", "车缝工艺要求："+oaProcessExplain.getSewing());
			params.put((35 + size - 12) + ",0,"+(37 + size - 12) + ",10",oaProcessExplain.getSewingPic());

			params.put((38 + size - 12) + ",1",oaProcessExplain.getTailButton());
			params.put((39 + size - 12) + ",1",oaProcessExplain.getTailIroning());
			params.put((40 + size - 12) + ",1",oaProcessExplain.getTailCard());
			params.put((41 + size - 12) + ",1",oaProcessExplain.getTailPackaging());

		}



		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MANAGER_INFO_BY_SQL);
		fsp.set("orderId", oaOrder.getId());
		fsp.set("node", "3");
		fsp.set(FSPBean.FSP_ORDER, " order by id desc");
		bean = manager.getOnlyObjectBySql(fsp);
		params.put((42 + size - 12) + ",0", "备注：" + (bean.get("content")==null?"":bean.get("content").toString()));

		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", node);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		params.put((43 + size - 12) + ",1",bean.get("operator")==null?"":bean.get("operator").toString());
		params.put((43 + size - 12) + ",8",bean.get("wf_real_start")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("wf_real_start")));
		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// 采购节点Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getPurchaseInfo(OaOrder oaOrder) {
		//表头信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();
		params.put("1,1", oaOrder.getSellOrderCode());
		params.put("1,4", oaOrder.getStyleDesc());
		params.put("1,8", oaOrder.getMrName());
		params.put("1,25", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");

		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", node);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		params.put("1,12",bean.get("worker")==null?"":bean.get("worker").toString());
		params.put("1,16", bean.get("work_time")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("work_time")));

		fsp = new FSPBean();
		fsp.set("orderId", oaOrder.getId());
		if(oaOrder.getType().equals("2")){
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_BAN_MATERIAL_PURCHASE_DESC_BY_SQL);
		}else{
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_HUO_MATERIAL_PURCHASE_DESC_BY_SQL);
		}
		beans = getObjectsBySql(fsp);
		int t=0;
		for(DynaBean bean : beans){
			params.put((4+t)+",0",bean.get("position")==null?"":bean.get("position").toString());
			params.put((4+t)+",1",bean.get("material_prop")==null?"":bean.get("material_prop").toString());
			params.put((4+t)+",2",bean.get("type")==null?"":bean.get("type").toString());
			params.put((4+t)+",3",bean.get("material_name")==null?"":bean.get("material_name").toString());
			params.put((4+t)+",4",bean.get("color")==null?"":bean.get("color").toString());
			params.put((4+t)+",5",bean.get("supplier_name")==null?"":bean.get("supplier_name").toString());
			params.put((4+t)+",6",bean.get("supplier_addr")==null?"":bean.get("supplier_addr").toString());
			params.put((4+t)+",7",bean.get("supplier_tel")==null?"":bean.get("supplier_tel").toString());
			if(oaOrder.getType().equals("3")){
				params.put((4+t)+",8",bean.get("buffon")==null?"":bean.get("buffon").toString());
				params.put((4+t)+",9",bean.get("unit_num")==null?"":decimalFormat.format((float)bean.get("unit_num")));
				params.put((4+t)+",10",bean.get("order_num")==null?"":bean.get("order_num").toString());
				params.put((4+t)+",11",bean.get("need_num")==null?"":decimalFormat.format((float)bean.get("need_num")));
				params.put((4+t)+",12",bean.get("org")==null?"":bean.get("org").toString());
				params.put((4+t)+",13",bean.get("num")==null?"":bean.get("num").toString());
				params.put((4+t)+",14",bean.get("price")==null?"":decimalFormat.format((float)bean.get("price")));
				params.put((4+t)+",15",bean.get("total_price")==null?"":decimalFormat.format((float)bean.get("total_price")));
				params.put((4+t)+",16",bean.get("test_price")==null?"":decimalFormat.format((float)bean.get("test_price")));
				params.put((4+t)+",17",bean.get("freight")==null?"":decimalFormat.format((float)bean.get("freight")));
				params.put((4+t)+",18",bean.get("total")==null?"":decimalFormat.format((float)bean.get("total")));
				params.put((4+t)+",19",bean.get("buyer_loss")==null?"":bean.get("buyer_loss").toString());
				params.put((4+t)+",20",bean.get("paper_tube")==null?"":decimalFormat.format((float)bean.get("paper_tube")));
				params.put((4+t)+",21",bean.get("deviation")==null?"":decimalFormat.format((float)bean.get("deviation")));
				params.put((4+t++)+",22",bean.get("pur_memo")==null?"":bean.get("pur_memo").toString());
			}else{
				params.put((4+t)+",23",bean.get("weight")==null?"":bean.get("weight").toString());
				params.put((4+t)+",24",bean.get("component")==null?"":bean.get("component").toString());
				params.put((4+t)+",25",bean.get("delivery_time")==null?"":bean.get("delivery_time").toString());
				params.put((4+t)+",26",bean.get("buyer_loss")==null?"":bean.get("buyer_loss").toString());
				params.put((4+t)+",27",bean.get("paper_tube")==null?"":bean.get("paper_tube").toString());
				params.put((4+t)+",28",bean.get("deviation")==null?"":bean.get("deviation").toString());
				params.put((4+t)+",29,3Double",bean.get("shear_price")==null?"":decimalFormat3.format((float)bean.get("shear_price")));
				params.put((4+t)+",30",bean.get("unit")==null?"":bean.get("unit").toString());
				params.put((4+t)+",31,3Double",bean.get("goods_price")==null?"":decimalFormat3.format((float)bean.get("goods_price")));
				params.put((4+t)+",32",bean.get("goods_unit")==null?"":bean.get("goods_unit").toString());
				params.put((4+t)+",33",bean.get("buffon")==null?"":decimalFormat.format((float)bean.get("buffon")));
				params.put((4+t++)+",34",bean.get("pur_memo")==null?"":bean.get("pur_memo").toString());
			}
		}
		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// 核价节点Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getPricingInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();
		params.put("1,1", oaOrder.getSellOrderCode());
		params.put("1,3", oaOrder.getStyleDesc());
		params.put("1,8", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
		//params.put("27,0,38,4", oaOrder.getPictureFront());

		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", node);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		//params.put("1,10",(String)bean.get("operator"));
		params.put("1,12", bean.get("wf_real_start")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("wf_real_start")));
		params.put("27,0,38,4", (String)bean.get("other_file"));
		params.put(39+",1",(String)bean.get("operator"));

		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_MATERIAL_COST_BY_SQL);
		fsp.set("orderId", oaOrder.getId());
		beans = getObjectsBySql(fsp);
		int t=0;
		for(DynaBean bean : beans){
			params.put((4+t)+",0",bean.get("material_prop")==null?"":(String)bean.get("material_prop"));
			params.put((4+t)+",1",bean.get("type")==null?"":(String)bean.get("type"));
			params.put((4+t)+",2",bean.get("material_name")==null?"":(String)bean.get("material_name"));
			params.put((4+t)+",3",bean.get("color")==null?"":(String)bean.get("color"));
			params.put((4+t)+",4",bean.get("buffon")==null?"":bean.get("buffon").toString());
			params.put((4+t)+",5",bean.get("unit_num")==null?"":decimalFormat.format((float)bean.get("unit_num")));
			params.put((4+t)+",6",bean.get("cp_price")==null?"":bean.get("cp_price").toString());
			params.put((4+t)+",7",bean.get("shear_price")==null?"":bean.get("shear_price").toString());
			params.put((4+t)+",8",bean.get("cp_loss")==null?"":bean.get("cp_loss").toString());
			params.put((4+t)+",9",bean.get("cp_total_price")==null?"":decimalFormat.format((float)bean.get("cp_total_price")));
			params.put((4+t)+",10",bean.get("cp_shear_price")==null?"":decimalFormat.format((float)bean.get("cp_shear_price")));
			params.put((4+t++)+",11",bean.get("cp_memo")==null?"":bean.get("cp_memo").toString());
		}

		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_COST_BY_EQL);
		fsp.set("orderId", oaOrder.getId());
		OaCost oaCost=(OaCost)manager.getOnlyObjectByEql(fsp);
		if(oaCost!=null){
			params.put(22+",9",oaCost.getMGoodsPrice()==null?"":decimalFormat.format(oaCost.getMGoodsPrice()));
			params.put(22+",10",oaCost.getMShearPrice()==null?"":decimalFormat.format(oaCost.getMShearPrice()));

			params.put(24+",1",oaCost.getOStamp()==null?"":decimalFormat.format(oaCost.getOStamp()));
			params.put(24+",3",oaCost.getOEmbroider()==null?"":decimalFormat.format(oaCost.getOEmbroider()));
			params.put(24+",6",oaCost.getOWash()==null?"":decimalFormat.format(oaCost.getOWash()));
			params.put(24+",10",oaCost.getWaiXieTotalPrice()==null?"":decimalFormat.format(oaCost.getWaiXieTotalPrice()));
			params.put(24+",11",oaCost.getOMemo()==null?"":oaCost.getOMemo().toString());

			params.put(28+",9",oaCost.getD1()==null?"":decimalFormat.format(oaCost.getD1()));
			params.put(28+",10",oaCost.getD2()==null?"":decimalFormat.format(oaCost.getD2()));
			params.put(28+",11",oaCost.getD3()==null?"":decimalFormat.format(oaCost.getD3()));
			params.put(28+",12",oaCost.getPMemo()==null?"":oaCost.getPMemo().toString());

			if(oaCost.getPCutting()!=null){
				String []pCutting = oaCost.getPCutting().split(",");
				params.put(28+",5",pCutting[0]==null?"":decimalFormat.format(Float.parseFloat(pCutting[0])));
				params.put(28+",6",pCutting[1]==null?"":decimalFormat.format(Float.parseFloat(pCutting[1])));
				params.put(28+",7",pCutting[2]==null?"":decimalFormat.format(Float.parseFloat(pCutting[2])));
			}

			if(oaCost.getPSew()!=null){
				String []pSew = oaCost.getPSew().split(",");
				params.put(29+",5",pSew[0]==null?"":decimalFormat.format(Float.parseFloat(pSew[0])));
				params.put(29+",6",pSew[1]==null?"":decimalFormat.format(Float.parseFloat(pSew[1])));
				params.put(29+",7",pSew[2]==null?"":decimalFormat.format(Float.parseFloat(pSew[2])));
			}

			if(oaCost.getPLast()!=null){
				String []pLast = oaCost.getPLast().split(",");
				params.put(30+",5",pLast[0]==null?"":decimalFormat.format(Float.parseFloat(pLast[0])));
				params.put(30+",6",pLast[1]==null?"":decimalFormat.format(Float.parseFloat(pLast[1])));
				params.put(30+",7",pLast[2]==null?"":decimalFormat.format(Float.parseFloat(pLast[2])));
			}


			params.put(31+",5",oaCost.getBhe1()==null?"":decimalFormat.format(oaCost.getBhe1()));
			params.put(31+",6",oaCost.getBhe2()==null?"":decimalFormat.format(oaCost.getBhe2()));
			params.put(31+",7",oaCost.getBhe3()==null?"":decimalFormat.format(oaCost.getBhe3()));

			params.put(35+",9",oaCost.getOrderNum1()==null?"":oaCost.getOrderNum1().toString());
			params.put(35+",10",oaCost.getOrderNum2()==null?"":oaCost.getOrderNum2().toString());
			params.put(35+",11",oaCost.getOrderNum3()==null?"":oaCost.getOrderNum3().toString());

			params.put(36+",9",oaCost.getBtotal1()==null?"":decimalFormat.format(oaCost.getBtotal1()));
			params.put(36+",10",oaCost.getBtotal2()==null?"":decimalFormat.format(oaCost.getBtotal2()));
			params.put(36+",11",oaCost.getBtotal3()==null?"":decimalFormat.format(oaCost.getBtotal3()));

			params.put(37+",9",oaCost.getDtotal1()==null?"":decimalFormat.format(oaCost.getDtotal1()));
			params.put(37+",10",oaCost.getDtotal2()==null?"":decimalFormat.format(oaCost.getDtotal2()));
			params.put(37+",11",oaCost.getDtotal3()==null?"":decimalFormat.format(oaCost.getDtotal3()));
		}

		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// CQC节点Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getCQCInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> sheet1 = new HashMap<String,Object>();
		Map<String,Object> sheet2 = new HashMap<String,Object>();
		Map<String, String> params1 = new HashMap<String, String>();
		Map<String, String> dynaRow1 = new HashMap<String, String>();
		Map<String, String> params2 = new HashMap<String, String>();
		Map<String, String> dynaRow2 = new HashMap<String, String>();
		sheet1.put("sheetIndex", "6");
		sheet1.put("dynaRow", dynaRow1);
		sheet1.put("params", params1);
		sheet2.put("sheetIndex", "9");
		sheet2.put("dynaRow", dynaRow2);
		sheet2.put("params", params2);
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CQC_INFO_BY_EQL);
		fsp.set("oaOrderId", oaOrder.getId());
		beans = getObjectsBySql(fsp);
		if(beans.size()>0){
			String str = beans.get(0).get("title")==null?"":beans.get(0).get("title").toString();
			if(str!=""){
				String titles[] = str.split("-");
				int n = 0;
				for(String s:titles){
					params1.put("2,"+(29+n++),s);
				}
			}
			int t = 0;
			for(DynaBean bean : beans){
				params1.put((3+t)+",10",bean.get("apply_unit_num")==null?"":bean.get("apply_unit_num").toString());
				params1.put((3+t)+",11",bean.get("unit_num")==null?"":decimalFormat.format((float)bean.get("unit_num")));
				params1.put((3+t)+",13",bean.get("need_num")==null?"":bean.get("need_num").toString());
				params1.put((3+t)+",18",bean.get("receive_time")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("receive_time"),"MM/dd"));
				params1.put((3+t)+",20",bean.get("receive_num")==null?"":decimalFormat.format((float)bean.get("receive_num")));
				params1.put((3+t)+",24",bean.get("receive_rate")==null?"":bean.get("receive_rate").toString());
				params1.put((3+t)+",25",bean.get("receive_memo")==null?"":bean.get("receive_memo").toString());
				String nums = beans.get(0).get("shear_num_info")==null?"":beans.get(t).get("shear_num_info").toString();
				Float f =0f;
				if(nums!=""){
					String shearNumInfos[] = nums.split(",");
					int n = 0;
					for(String s:shearNumInfos){
						if(s == null || "".equals(s.trim())){
							continue;
						}
						f+=Float.parseFloat(s);
						params1.put((3+t)+","+(29+n++),s.trim());
					}
				}
				params1.put((3+t)+",36",f.toString());
				params1.put((3+t)+",41",bean.get("loss_bundles")==null?"":bean.get("loss_bundles").toString());
				params1.put((3+t)+",42",bean.get("loss_other")==null?"":bean.get("loss_other").toString());
				params1.put((3+t)+",43",bean.get("loss_oddments")==null?"":bean.get("loss_oddments").toString());
				params1.put((3+t)+",44",bean.get("loss_yiyou")==null?"":decimalFormat.format((float)bean.get("loss_yiyou")));
				params1.put((3+t)+",45",bean.get("loss_company")==null?"":decimalFormat.format((float)bean.get("loss_company")));
				params1.put((3+t++)+",46",bean.get("loss_memo")==null?"":bean.get("loss_memo").toString());
			}
		}


		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_QITAO_INFO_BY_EQL);
		fsp.set("oaOrderId", oaOrder.getId());
		beans = getObjectsBySql(fsp);
		if(beans.size()>0){
			params2.put(1+",1",beans.get(0).get("sell_order_code")==null?"":beans.get(0).get("sell_order_code").toString());
			params2.put(2+",1",beans.get(0).get("style_code")==null?"":beans.get(0).get("style_code").toString());
			params2.put(1+",3",beans.get(0).get("qitao_receive_time")==null?"":beans.get(0).get("qitao_receive_time").toString());
			params2.put(2+",3",beans.get(0).get("qitao_send_time")==null?"":beans.get(0).get("qitao_send_time").toString());
			int t = 0;
			dynaRow2.put("6", (beans.size() - 5) + "");
			for(DynaBean bean : beans){
				params2.put((4+t)+",0",bean.get("project")==null?"":bean.get("project").toString());
				params2.put((4+t)+",1",bean.get("tracke")==null?"":bean.get("tracke").toString());
				params2.put((4+t)+",2",bean.get("department")==null?"":bean.get("department").toString());
				params2.put((4+t++)+",3",bean.get("operator")==null?"":bean.get("operator").toString());
			}
		}
		list.add(sheet1);
		list.add(sheet2);
		return list;
	}

	// TPE节点Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getTPEInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();

		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_SEWING_NUM_INFO_BY_EQL);
		fspBean.set("oaOrderId", oaOrder.getId());
		OaTpe oaTpe = (OaTpe) manager.getOnlyObjectByEql(fspBean);
		params.put("2,8",oaTpe.getSewingFactory().toString());
		params.put("12,8",oaTpe.getSewingTotal().toString());
		String[] infos = oaTpe.getSewingNum().split(",");
		for(int i=0;i<infos.length;i++){
			String []nums = infos[i].split("-");
			Float f = 0f;
			for(int j=0;j<nums.length;j++){
				params.put((5+i)+","+(j),nums[j]);
				if(j==0) continue;
				f+= Float.parseFloat(nums[j]);
			}
			params.put((5+i)+",8",f.toString());
		}

		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", "6");
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		params.put("14,1",bean.get("content")==null?"":bean.get("content").toString());
		params.put("17,1",bean.get("worker")==null?"":bean.get("worker").toString());
		params.put("17,8",bean.get("wf_real_start")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("wf_real_start")));

		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// QA节点Excel
	// by fangwei 2014-12-17
	public List<Map<String, Object>> getQAInfo(OaOrder oaOrder) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> dynaRow = new HashMap<String, Object>();

		fsp=new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_QA_INFO);
		fsp.set("oaOrderId", oaOrder.getId());
		bean = manager.getOnlyObjectBySql(fsp);

		if(bean.get("qualified_num_info")!=null){
			String[] numInfos=bean.get("qualified_num_info").toString().split(",");
			for(int i=0;i<numInfos.length;i++){
				String[] nums = numInfos[i].split("-");
				for(int j=0;j<nums.length;j++){
					params.put((5+i)+","+j,nums[j]);
				}
			}
		}

		if(bean.get("unqualified_num_info")!=null){
			String[] numInfos=bean.get("unqualified_num_info").toString().split(",");
			for(int i=0;i<numInfos.length;i++){
				String[] nums = numInfos[i].split("-");
				for(int j=0;j<nums.length;j++){
					params.put((15+i)+","+j,nums[j]);
				}
			}
		}

		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", "7");
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		bean = manager.getOnlyObjectBySql(fsp);
		params.put("24,0",bean.get("content")==null?"":bean.get("content").toString());
		params.put("27,1",bean.get("worker")==null?"":bean.get("worker").toString());
		params.put("27,7",bean.get("wf_real_start")==null?"":DateUtil.formatDate((java.sql.Timestamp)bean.get("wf_real_start")));

		list.add(dynaRow);
		list.add(params);
		return list;
	}

	// 填充list,返回模板中对应的excel索引
	// by fangwei 2014-12-17
	public List<Map<String, Object>> processOrder(OaOrder order) {
		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		String sheetNode = null;
		if ("2".equals(node)) {
			sheetNode = "2";
			list = createOrderInfo(oaOrder);
		} else if (oaOrder.getType().equals("2")) {
			// 打板
			// 采购 技术 核价 MR
			switch (node) {
			case "3":
				list = getPurchaseInfo(oaOrder);
				sheetNode = "3";
				break;
			case "4":
				list = getTechnologyInfo(oaOrder);
				sheetNode = "4";
				break;
			case "5":
				list = getPricingInfo(oaOrder);
				sheetNode = "5";
				break;
			case "6":
				break;
			default:
				break;
			}
		} else if (oaOrder.getType().equals("3")) {
			// 大货
			// 技术 采购 CQC TPE QA 财务 物流 异动
			switch (node) {
			case "3":
				list = getTechnologyInfo(oaOrder);
				sheetNode = "4";
				break;
			case "4":
				list = getPurchaseInfo(oaOrder);
				sheetNode = "3";
				break;
			case "5":
				lists = getCQCInfo(oaOrder);
				sheetNode = "999";
				break;
			case "6":
				list = getTPEInfo(oaOrder);
				sheetNode = "7";
				break;
			case "7":
				list = getQAInfo(oaOrder);
				sheetNode = "8";
				break;
			case "8":
				break;
			case "9":
				break;
			default:
				break;
			}
		}
		if(sheetNode == "999"){
			return lists;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("node", sheetNode);
		list.add(map);
		return list;
	}

	/**
	 * 通过order的id生成excel by fangwei 2014-12-17
	 */
	public String createExcelByOrderId(Integer orderId) {
		String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseExcelFile"));
		oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
		if (null == oaOrder || null == oaOrder.getId()) {
			throw new RuntimeException("the parameter oaOrder.id is null!");
		}
		String url = null;
		List<Map<String, Object>> list = processOrder(oaOrder);
		try {
			String fileName = "ERP-Order-" + oaOrder.getSellOrderCode() + "-" + (System.currentTimeMillis() + "").substring(6, 13) + ".xlsx";
			File file = new File(PathUtil.getOaFileDir());
			if (!file.exists()) {
				file.mkdirs();
			}

			OutputStream os = new FileOutputStream(new File(file, fileName));
			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());

			if (!"2".equals(node)) {
				fsp.set("oa_order", oaOrder.getId());
				fsp.set("wf_step", (Integer.parseInt(node)-1)+"");
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
				bean = manager.getOnlyObjectBySql(fsp);
				String lastExcelFile = (String) bean.get("attachment");
				if (StringUtils.isNotBlank(lastExcelFile)) {
					Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=" + PathUtil.url2FileName(lastExcelFile));
					baseExcelFile = PathUtil.url2Path(lastExcelFile);
					if (!new File(baseExcelFile).exists()) {
						// 服务器中 excel 不存在
						throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment in server [" + baseExcelFile + "]is not exists!");
					}
				} else {
					// 数据库中无excel 地址信息
					throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment field is not exists!");
				}
			}
			if("c_ppc_factoryMsg_5".equals(oaOrder.getWfStep())){
				POIUtils.processExcel(os, baseExcelFile,list);
			}else {
				POIUtils.processExcel(os, baseExcelFile, (String) list.get(2).get("node"), list.get(0), list.get(1));
			}
			url = PathUtil.path2Url(PathUtil.getOaFileDir().concat(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	/**
	 * 通过模板导出订单列表的excel by fangwei 2014-12-17
	 */
	public void downOAOrder() throws Exception {
		// 请求下载节点
		String node = Struts2Utils.getParameter("node");
		if (StringUtils.isBlank(node)) {
			throw new RuntimeException("the parameter node is blank!");
		}
		if (null == oaOrder || null == oaOrder.getId()) {
			throw new RuntimeException("the parameter oaOrder.id is null!");
		}
		OutputStream os = Struts2Utils.getResponse().getOutputStream();
		Struts2Utils.getResponse().setContentType("Application/msexcel");
		List<Map<String, Object>> list = null;
		String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseExcelFile"));
		oaOrder = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
		// order当前所在节点
		String nowNode = oaOrder.getWfStep();
		nowNode = nowNode.substring(nowNode.lastIndexOf("_") + 1, nowNode.length());
		if (nowNode.equals("2")) {
			// 通过模板生成excel
			list = createOrderInfo(oaOrder);
			Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=ERP-ORDER-" + oaOrder.getSellOrderCode() + "-" + (System.currentTimeMillis() + "").substring(6, 13) + ".xlsx");
			POIUtils.processExcel(os, baseExcelFile, "2", list.get(0), list.get(1));
		} else if (node.compareTo(nowNode) < 0) {
			// 下载之前节点excel
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", node);
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			bean = manager.getOnlyObjectBySql(fsp);
			String file = (String) bean.get("attachment");
			if (StringUtils.isNotBlank(file)) {
				Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=" + PathUtil.url2FileName(file));
				baseExcelFile = PathUtil.url2Path(file);
				if (new File(baseExcelFile).exists()) {
					os.write(FileUtils.readFileToByteArray(new File(baseExcelFile)));
					os.flush();
					os.close();
				} else {
					// 服务器中 excel 不存在
					throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment in server [" + baseExcelFile + "]is not exists!");
				}
			} else {
				// 数据库中无excel 地址信息
				throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment field is not exists!");
			}
		} else if (node.compareTo(nowNode) == 0) {
			// 下载当前节点excel
			// 得到上一个节点excel的位置
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", (Integer.parseInt(node) - 1) + "");
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			bean = manager.getOnlyObjectBySql(fsp);
			String file = (String) bean.get("attachment");
			if (StringUtils.isBlank(file)) {
				throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment field is not exists!");
			}
			baseExcelFile = PathUtil.url2Path(file);
			list = processOrder(oaOrder);
			Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=ERP-ORDER-" + oaOrder.getSellOrderCode() + "-" + (System.currentTimeMillis() + "").substring(6, 13) + ".xlsx");
			// 根据上一个节点来填充excel
			if("c_ppc_factoryMsg_5".equals(oaOrder.getWfStep())){
				POIUtils.processExcel(os, baseExcelFile,list);
			}else {
				POIUtils.processExcel(os, baseExcelFile, (String) list.get(2).get("node"), list.get(0), list.get(1));
			}
		}
	}

	/**
	 * 获得第四个节点页面所需的数据(大货)
	 */
	//update by 张华 2015-1-9
	public void jsonGetFourNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("orderId");
			String type = Struts2Utils.getParameter("type");
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "4";// 用于查询订单详情是那个节点 是节点的尾数 默认为大货
			if (StringUtils.isNotEmpty(type) && "2".equals(type)) {
				node = "3";
			}
			int orderId = 0;

			// 1.判断订单Id是否为空
			orderId = Integer.valueOf(orderIds);
			// 1查询面辅料采购清单
			getMaterialPurchaseDesc(orderId, type, resMap);
			// 2.查询异动跟踪信息
			getTracke(orderId, node, resMap);
			// 3.查询管理信息
			getManagerInfo(orderId, node, wfStepIndex, resMap);

			resMap.put("code", 0);
			resMap.put("msg", "采购节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "采购节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: getMaterialPurchaseDesc
	 * @Description: TODO获得面辅料采购清单
	 *
	 * @param orderId
	 * @param type
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getMaterialPurchaseDesc(int orderId, String type, Map resMap) throws Exception {
		List<Map> rList = new ArrayList<Map>();
		FSPBean fsp = new FSPBean();
		if ("2".equals(type)) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_BAN_MATERIAL_PURCHASE_DESC_BY_SQL);
			fsp.set("orderId", orderId);
			List<LazyDynaMap> oaDaBanInfos = manager.getObjectsBySql(fsp);
			for (LazyDynaMap map : oaDaBanInfos) {
				Map tamp = new HashMap();
				tamp.put("marterialListId", (null == map.get("materiallistid")) ? "" : map.get("materiallistid"));
				tamp.put("position", (null == map.get("position")) ? "" : map.get("position"));
				tamp.put("oa_order_id", (null == map.get("oa_order_id")) ? "" : map.get("oa_order_id"));
				tamp.put("material_prop", (null == map.get("material_prop")) ? "" : map.get("material_prop"));
				tamp.put("material_name", (null == map.get("material_name")) ? "" : map.get("material_name"));
				tamp.put("type", (null == map.get("type")) ? "" : map.get("type"));
				tamp.put("color", (null == map.get("color")) ? "" : map.get("color"));
				tamp.put("supplier_name", (null == map.get("supplier_name") ? "" : map.get("supplier_name")));
				tamp.put("supplier_addr", (null == map.get("supplier_addr") ? "" : map.get("supplier_addr")));
				tamp.put("supplier_tel", (null == map.get("supplier_tel") ? "" : map.get("supplier_tel")));
				tamp.put("daBanId", (null == map.get("dabanid")) ? "" : map.get("dabanid"));
				tamp.put("weight", (null == map.get("weight") ? "" : map.get("weight")));
				tamp.put("component", (null == map.get("component") ? "" : map.get("component")));
				tamp.put("delivery_time", (null == map.get("delivery_time") ? "" : map.get("delivery_time")));
				tamp.put("buyer_loss", (null == map.get("buyer_loss") ? "" : map.get("buyer_loss")));
				tamp.put("paper_tube", (null == map.get("paper_tube") ? "" : map.get("paper_tube")));
				tamp.put("deviation", (null == map.get("deviation") ? "" : map.get("deviation")));
				tamp.put("shear_price", (null == map.get("shear_price") ? "" : map.get("shear_price")));
				tamp.put("unit", (null == map.get("unit") ? "" : map.get("unit")));
				tamp.put("goods_price", (null == map.get("goods_price") ? "" : map.get("goods_price")));
				tamp.put("goods_unit", (null == map.get("goods_unit") ? "" : map.get("goods_unit")));
				tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
				tamp.put("pur_memo", (null == map.get("pur_memo")) ? "" : map.get("pur_memo"));
				rList.add(tamp);
			}
		} else if ("3".equals(type)) {
			//.查询大货采购清单的信息
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_HUO_MATERIAL_PURCHASE_DESC_BY_SQL);
			fsp.set("orderId", orderId);
			List<LazyDynaMap> oaDaBanInfos = manager.getObjectsBySql(fsp);
			for (LazyDynaMap map : oaDaBanInfos) {
				Map tamp = new HashMap();
				tamp.put("marterialListId", (null == map.get("materiallistid")) ? "" : map.get("materiallistid"));
				tamp.put("position", (null == map.get("position")) ? "" : map.get("position"));
				tamp.put("oa_order_id", (null == map.get("oa_order_id")) ? "" : map.get("oa_order_id"));
				tamp.put("material_prop", (null == map.get("material_prop")) ? "" : map.get("material_prop"));
				tamp.put("material_name", (null == map.get("material_name")) ? "" : map.get("material_name"));
				tamp.put("type", (null == map.get("type")) ? "" : map.get("type"));
				tamp.put("color", (null == map.get("color")) ? "" : map.get("color"));
				tamp.put("supplier_name", (null == map.get("supplier_name") ? "" : map.get("supplier_name")));
				tamp.put("supplier_addr", (null == map.get("supplier_addr") ? "" : map.get("supplier_addr")));
				tamp.put("supplier_tel", (null == map.get("supplier_tel") ? "" : map.get("supplier_tel")));
				tamp.put("daHuoId", (null == map.get("dahuoid")) ? "" : map.get("dahuoid"));
				tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
				tamp.put("unit_num", (null == map.get("unit_num") ? "" : map.get("unit_num")));
				tamp.put("order_num", (null == map.get("order_num") ? "" : map.get("order_num")));
				if (StringUtils.isBlank(map.get("need_num").toString()) || (Float)map.get("need_num")==0) {
					// 如果需求数量为空时，重新计算need_num的值//需求数量=单件用量*订单数量
					if (StringUtils.isNotBlank(map.get("order_num").toString()) && StringUtils.isNotBlank(map.get("unit_num").toString()) && null != map.get("unit_num") && null != map.get("order_num")) {
						tamp.put("need_num", Float.parseFloat(map.get("unit_num").toString()) * Float.parseFloat(map.get("order_num").toString()));
					}else{
						tamp.put("need_num", "");
					}
				} else {
					tamp.put("need_num", map.get("need_num"));

				}
				tamp.put("org", (null == map.get("org") ? "" : map.get("org")));
				tamp.put("num", (null == map.get("num") ? "" : map.get("num")));//采购数量
				tamp.put("price", (null == map.get("price") ? "" : map.get("price")));//单价
				//总金额=采购数量*单价
				if((Float)map.get("total_price")==0){
					//如果总金额为空时
					if(null!=map.get("num") && null!=map.get("price")){
						tamp.put("total_price",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString()));
					}else{
						tamp.put("total_price", "");
					}
				}else{
					tamp.put("total_price", map.get("total_price"));
				}

				tamp.put("test_price", (null == map.get("test_price") ? "" : map.get("test_price")));//验布费用
				tamp.put("freight", (null == map.get("freight") ? "" : map.get("freight")));//运费
				if(StringUtils.isBlank(map.get("total").toString())){
					//如果总金额为空时，进行重新计算
					//合计=总金额+验布费用+运费
					if(null!=map.get("num") && null!=map.get("price") && null!=map.get("test_price") && null!=map.get("freight")){
						tamp.put("total",Float.parseFloat(map.get("price").toString())*Float.parseFloat(map.get("num").toString())
								+ Float.parseFloat(map.get("test_price").toString()) + Float.parseFloat(map.get("freight").toString()) );
					}else if(null!=map.get("num") && null!=map.get("price") && null!=map.get("test_price") ){
						tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString())
								+ Float.parseFloat(map.get("test_price").toString()));
					}else if(null!=map.get("num") && null!=map.get("price") && null!=map.get("freight") && StringUtils.isBlank(map.get("total").toString())){
						tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString())
								+  Float.parseFloat(map.get("freight").toString()));
					}else if(null!=map.get("num") && null!=map.get("price")){
						tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString()) );
					}else{
						tamp.put("total",map.get("price").toString());
					}
				}else{
					tamp.put("total",map.get("total"));
				}

				tamp.put("buyer_loss", (null == map.get("buyer_loss") ? "" : map.get("buyer_loss")));
				tamp.put("paper_tube", (null == map.get("paper_tube") ? "" : map.get("paper_tube")));
				tamp.put("deviation", (null == map.get("deviation") ? "" : map.get("deviation")));
				tamp.put("pur_memo", (null == map.get("pur_memo")) ? "" : map.get("pur_memo"));
				rList.add(tamp);
			}
		}

		resMap.put("materialPurchaseDescList", rList);
	}

	/**
	 * 保存第四个节点（采购）
	 *
	 * @author yunpeng
	 */
	private boolean saveOrderFour(OaOrder oaOrder) {
		//update by 张华 2015-01-20
		try {
			String type = oaOrder.getType();
			int orderId = oaOrder.getId();
			int oaOrderDetail = oaOrder.getOaOrderDetail();
			// 1.保存面料采购的详细信息
			saveMaterialDescInfo(type, orderId);
			// 2.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrderDetail);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 保存物料成本中默认的三条数据
	 *
	 * @param orderId
	 * @author yunpeng
	 */
	private void saveMaterialDefaultData(int orderId) {
		boolean fleg = true;
		// 查询是否已经退回，如果已经退回则不添加默认的三条数据
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_COUNT_DETAIL);
		fsp.set("orderId", orderId);
		LazyDynaMap map = manager.getOnlyObjectBySql(fsp);
		if (null != map.get("wf_step_num") && (Long) map.get("wf_step_num") >= 2) {
			fleg = false;
		}
		if (fleg) {
			// 保存第一个默认数据
			OaMaterialList oaMaterialList = new OaMaterialList();
			oaMaterialList.setOaOrderId(orderId);
			oaMaterialList.setMaterialName("唛头");
			oaMaterialList.setType("辅料");
			manager.saveObject(oaMaterialList);
			OaDaBanInfo oaDaBanInfo = new OaDaBanInfo();
			oaDaBanInfo.setOaMaterialList(oaMaterialList.getId());
			oaDaBanInfo.setUnitNum(2.00f);
			oaDaBanInfo.setCpPrice(0.05f);
			oaDaBanInfo.setCpTotalPrice(0.10f);
			manager.saveObject(oaDaBanInfo);

			// 保存第二个默认数据
			OaMaterialList oaMaterialList2 = new OaMaterialList();
			oaMaterialList2.setOaOrderId(orderId);
			oaMaterialList2.setMaterialName("包装物料");
			oaMaterialList2.setType("辅料");
			manager.saveObject(oaMaterialList2);
			OaDaBanInfo oaDaBanInfo2 = new OaDaBanInfo();
			oaDaBanInfo2.setOaMaterialList(oaMaterialList2.getId());
			oaDaBanInfo2.setUnitNum(1.00f);
			oaDaBanInfo2.setCpPrice(0.50f);
			oaDaBanInfo2.setCpTotalPrice(0.50f);
			manager.saveObject(oaDaBanInfo2);

			// 保存第三个默认数据
			OaMaterialList oaMaterialList3 = new OaMaterialList();
			oaMaterialList3.setOaOrderId(orderId);
			oaMaterialList3.setMaterialName("车缝线");
			oaMaterialList3.setType("辅料");
			manager.saveObject(oaMaterialList3);
			OaDaBanInfo oaDaBanInfo3 = new OaDaBanInfo();
			oaDaBanInfo3.setOaMaterialList(oaMaterialList3.getId());
			oaDaBanInfo3.setUnitNum(1.00f);
			oaDaBanInfo3.setCpPrice(0.30f);
			oaDaBanInfo3.setCpTotalPrice(0.30f);
			manager.saveObject(oaDaBanInfo3);
		}
	}

	/**
	 *
	 * @Title: jsonGetDaBanFiveNode
	 * @Description: TODO查询打板第五个节点的数据（核价）
	 *
	 */
	//update by 张华 2015-1-9
	public void jsonGetDaBanFiveNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("orderId");
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "5";// 用于查询订单详情是那个节点 是节点的尾数 默认为大货
			int orderId = 0;

			// 1.判断订单Id是否为空
			orderId = Integer.valueOf(orderIds);
			// 1查询物料成本表格信息
			getMaterialCost(orderId, resMap);
			// 2.查询外协成本及加工成本
			getCost(orderId, resMap);
			// 3.查询管理信息
			getManagerInfo(orderId, node, wfStepIndex, resMap);
			// 4.查询异动跟踪信息
			getTracke(orderId, node, resMap);

			resMap.put("oaOrder", manager.getObject(OaOrder.class,orderId));
			resMap.put("code", 0);
			resMap.put("msg", "核价节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "核价节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: getCost
	 * @Description: TODO查询外协成本加工成本
	 *
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getCost(int orderId, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_COST_BY_EQL);
		fsp.set("orderId", orderId);
		OaCost oaCost = (OaCost) manager.getOnlyObjectByEql(fsp);
		resMap.put("oaCost", oaCost);
	}

	/**
	 *
	 * @Title: getMaterialCost
	 * @Description: TODO查询物料成本表格
	 *
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	//update by 张华 2015-1-9
	private void getMaterialCost(int orderId, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_MATERIAL_COST_BY_SQL);
		fsp.set("orderId", orderId);
		List<LazyDynaMap> mcs = manager.getObjectsBySql(fsp);
		List<Map> rList = new ArrayList<Map>();
		for (LazyDynaMap map : mcs) {
			Map tamp = new HashMap();
			tamp.put("ml_id", (null == map.get("ml_id")) ? "" : map.get("ml_id"));
			tamp.put("material_prop", (null == map.get("material_prop")) ? "" : map.get("material_prop"));
			tamp.put("type", (null == map.get("type")) ? "" : map.get("type"));
			tamp.put("material_name", (null == map.get("material_name")) ? "" : map.get("material_name"));
			tamp.put("color", (null == map.get("color")) ? "" : map.get("color"));
			tamp.put("dbi_id", (null == map.get("dbi_id")) ? "" : map.get("dbi_id"));
			tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
			tamp.put("unit_num", (null == map.get("unit_num")) ? "" : map.get("unit_num"));
			tamp.put("cp_price", (null == map.get("cp_price")) ? "" : map.get("cp_price"));
			tamp.put("shear_price", (null == map.get("shear_price")) ? "" : map.get("shear_price"));
			tamp.put("cp_loss", (null == map.get("cp_loss")) ? "" : map.get("cp_loss"));
			tamp.put("cp_total_price", (null == map.get("cp_total_price")) ? "" : map.get("cp_total_price"));
			tamp.put("cp_shear_price", (null == map.get("cp_shear_price")) ? "" : map.get("cp_shear_price"));
			tamp.put("cp_memo", (null == map.get("cp_memo")) ? "" : map.get("cp_memo"));
			tamp.put("goods_price", (null == map.get("goods_price")) ? "" : map.get("goods_price"));
			tamp.put("goods_unit", (null == map.get("goods_unit")) ? "" : map.get("goods_unit"));
			tamp.put("paper_tube", (null == map.get("paper_tube")) ? "" : map.get("paper_tube"));
			tamp.put("deviation", (null == map.get("deviation")) ? "" : map.get("deviation"));
			tamp.put("weight", (null == map.get("weight")) ? "" : map.get("weight"));
			tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
			tamp.put("buyer_loss", (null == map.get("buyer_loss")) ? "" : map.get("buyer_loss"));
			rList.add(tamp);
		}

		resMap.put("materialCost", rList);
	}

	/**
	 * 保存打版第5个节点（核价）
	 *
	 * @author yunpeng
	 */
	private boolean saveOrderDaBanFive(OaOrder oaOrder) {
		//update 张华 2015-01-20
		//update 蓝玉 2015-01-29
		try {
			int orderId = oaOrder.getId();
			int oaOrderDetail = oaOrder.getOaOrderDetail();
			// 1.保存物料成本信息
			saveMaterialCost(orderId);
			// 2.保存成本及加工成本
			saveCost(orderId);
			// 2.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrderDetail);

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 保存外协成本以及加工成本
	 *
	 * @param orderId
	 */
	//update by 张华 2015-01-20
	private void saveCost(int orderId) throws Exception {
		if (null != oaCost) {
			oaCost.setOaOrderId(orderId);
			manager.saveObject(oaCost);
		}
	}

	/**
	 * 保存物料成本表格的内容
	 *
	 * @param orderId
	 */
	private void saveMaterialCost(int orderId) throws Exception {
		// 删除面辅料采购信息
		// update by 张华 2015-01-20
		delOaMaterialList();

		// 保存之前要先删除要删除的数据
		// update by 张华 2015-01-20
		delOaDaBanInfo();

		if (null != oaMaterialLists) {
			for (int i = 0; i < oaMaterialLists.size(); i++) {
				// 先保存面料搭配明细
				OaMaterialList ml = oaMaterialLists.get(i);
				// 判断只要其中一个属性存在值，即存储到数据库中
				boolean orderNumFlag = false;
				if (null != ml) {
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor())) {
						orderNumFlag = true;
					}
					if (orderNumFlag && null == ml.getId()) {
						ml.setOaOrderId(orderId);
						manager.saveObject(ml);
					}
					// 保存打版采购清单
					OaDaBanInfo odi = oaDaBanInfos.get(i);
					if (null != odi && null != odi.getId()) { // 说明已存在
						OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) manager.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
						// 设置新的数据到oaDaBanInfo
						// oaDaBanInfo.setBuffon(odi.getBuffon()); // 布封
						oaDaBanInfo.setUnitNum(odi.getUnitNum());// 标准单件用量
						oaDaBanInfo.setCpPrice(odi.getCpPrice());// 大货单价
						oaDaBanInfo.setShearPrice(odi.getShearPrice());// 散剪单价
						oaDaBanInfo.setCpLoss(odi.getCpLoss());// 损耗率
						oaDaBanInfo.setCpTotalPrice(odi.getCpTotalPrice());// 大货金额
						oaDaBanInfo.setCpShearPrice(odi.getCpShearPrice());// 散剪金额
						oaDaBanInfo.setCpMemo(odi.getCpMemo());// 核价备注
						if (null == oaDaBanInfo || null == oaDaBanInfo.getId()) {
							oaDaBanInfo.setOaMaterialList(ml.getId()); // 用料表Id
						}
						manager.saveObject(oaDaBanInfo);
					} else { // 新数据，直接保存
						odi.setOaMaterialList(ml.getId());
						manager.saveObject(odi);
					}
				}
			}
		}
	}
	/******* 蓝玉方法结束 ****************/

	/**
	 *
	 * @Title: jsonGetDaBanSixNode
	 * @Description: TODO流程改造样衣打版第六节点，获取第六个节点页面信息（MR确认）
	 *
	 * @author 张华
	 */
	public void jsonGetDaBanSixNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			int orderId = 0;
			orderId = Integer.valueOf(orderIdStr); // 转换订单Id为数字
			// 查询MR确认信息
			getMrConfirmInfo(orderId, resMap);
			// 查询管理信息
			getManagerInfo(orderId, "6", wfStepIndex, resMap);

			resMap.put("code", 0);
			resMap.put("msg", "MR确认节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "MR确认节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: getMrConfirmInfo
	 * @Description: TODO获取MR确认信息
	 *
	 * @author 张华
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	private void getMrConfirmInfo(int orderId, Map resMap) throws Exception {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MR_CONFIRM_BY_EQL);
		fsp.set("orderId", orderId);
		OaMrConfirm oaMrConfirm = (OaMrConfirm) manager.getOnlyObjectByEql(fsp);
		resMap.put("oaMrConfirm", oaMrConfirm);
	}

	/**
	 *
	 * @Title: saveOrderDaBanSix
	 * @Description: TODO样衣打版保存第六个节点信息（MR确认）
	 *
	 * @author 张华
	 * @param oaOrder
	 * @return
	 */
	private boolean saveOrderDaBanSix(OaOrder oaOrder) {
		boolean flag = true;
		try {
			saveMrConfirm(oaOrder.getId()); // 保存MR确认信息
			saveManegeInfo(oaOrder.getOaOrderDetail());// 保存管理信息，保存MR备注和上传的附件
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 *
	 * @Title: saveMrConfirm
	 * @Description: TODO保存MR确认信息
	 *
	 * @author 张华
	 * @param oaOrderId
	 * @throws Exception
	 */
	private void saveMrConfirm(int oaOrderId) throws Exception {
		if (null != oaMrConfirm) {
			oaMrConfirm.setOaOrder(oaOrderId);
			manager.saveObject(oaMrConfirm);
		}
	}

	/**
	 * 查询关联订单的列表数据
	 *
	 */
	public String orderRelationList() {
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_RELATION_ORDER_BY_SQL);
		relationOrderList = manager.getObjectsBySql(fsp);
		processPageInfo(getObjectsCountSql(fsp));

		return "orderRelationList";
	}

	/**
	 *
	 * @Title: fillOrderDate
	 * @Description: TODO从关联订单导入用料说明 客供料信息
	 *
	 */
	//update by 张华 2015-1-9
//	public void fillOrderDate() {
//		Map resMap = new HashMap();// 返回结果
//		try {
//			String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
//			String orderSizeIdStr = Struts2Utils.getParameter("orderSizeId");// 获取尺码数量Id
//			String type = Struts2Utils.getParameter("type");
//			int orderId = Integer.parseInt(orderIdStr);
//
//			// 1.查询用料搭配信息、客供料明细
//			getMaterialList(orderIdStr, resMap); // 获取用料搭配信息、客供料信息
//			// 2.查询用料说明信息
//			getMaterialDesc(orderId, type, resMap);
//
//			resMap.put("code", 0);
//			resMap.put("msg", "关联订单信息导入成功");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			resMap.put("msg", "关联订单信息导入出错");
//		}
//
//		Struts2Utils.renderJson(resMap);
//	}

	/**
	 *
	 * @Title: jsonUploadPic
	 * @Description: TODO图片上传json方式
	 *
	 * @author 张华
	 * @throws IOException
	 */
	public void jsonUploadPic() throws IOException {
		String uploadPath = PathUtil.getOaPicDir();
		if (!new File(uploadPath).exists()) {
			new File(uploadPath).mkdirs();
		}
		if (file != null) {
			String fileName = System.currentTimeMillis() + fileFileName.substring(fileFileName.indexOf("."));
			FileUtils.copyFile(file, new File(uploadPath, fileName));
			fileFileName = PathUtil.path2Url(uploadPath.concat(fileName));
		}

		Struts2Utils.renderText(fileFileName);
	}

	public void jsonGetFiveNode(){
		Map resMap = new HashMap();
		try {
			String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");
			String node = "5";
			int orderId = 0;
			try { // 判断订单Id是否填写正确
				orderId = Integer.parseInt(orderIdStr);
			} catch (NumberFormatException e) {
				resMap.put("code", 2102);
				resMap.put("msg", "订单ID填写错误");
			}
			OaOrder oaOrder = oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
			if (oaOrder != null) {
				resMap.put("oaOrder", oaOrder); // 订单信息
				getMaterialList2(orderId, resMap); // 用料清单
				getDaHuoList(orderId, resMap); // 大货清单
				//getCqcStroe(orderId, resMap); // cqc数据
				getQiTao(orderId, resMap);
				// 3.查询管理信息
				getManagerInfo(orderId, node, wfStepIndex, resMap);
				// 4.查询异动跟踪信息
				getTracke(orderId, node, resMap);
				getOaOrderNum(oaOrder.getOaOrderNumId(), resMap);
				getWorker(orderId,resMap);

			} else {
				resMap.put("code", 2101);
				resMap.put("msg", "查询无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Utils.writeJson(resMap);
	}

	public void jsonGetQANode(){
		Map resMap = new HashMap();
		try {
			String orderIdStr = Struts2Utils.getParameter("orderId");// 获取订单Id
			String wfStepIndex = "7";
			String node = "7";
			int orderId = 0;
			try { // 判断订单Id是否填写正确
				orderId = Integer.parseInt(orderIdStr);
			} catch (NumberFormatException e) {
				resMap.put("code", 2102);
				resMap.put("msg", "订单ID填写错误");
			}
			OaOrder oaOrder = oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);
			if (oaOrder != null) {
				resMap.put("oaOrder", oaOrder); // 订单信息
				getQAInfo(orderId,resMap);
				// 3.查询管理信息
				getManagerInfo(orderId, node, wfStepIndex, resMap);
				// 4.查询异动跟踪信息
				getTracke(orderId, node, resMap);
				getWorker(orderId,resMap);
			} else {
				resMap.put("code", 2101);
				resMap.put("msg", "查询无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Struts2Utils.writeJson(resMap);
	}


	private void getQAInfo(int orderId, Map resMap) {
		if (orderId <= 0) { // 判断订单Id是否为空
			resMap.put("code", 2101);
			resMap.put("msg", "订单ID不能为空");
		} else {
			// 获取订单用料搭配明细列表
			fsp=new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_QA_INFO);
			fsp.set("oaOrderId", orderId);
			bean = manager.getOnlyObjectBySql(fsp);
			if(bean!=null){
				if(bean.get("title")!=null){
					String [] title = ((String)bean.get("title")).split("-");
					bean.set("title", title);
				}

				if(bean.get("num_info")!=null){
					String str = (String)bean.get("num_info");
					bean.set("length", str.split(",").length);
				}

				String[] colors =null;
				if(bean.get("sewing_num")!=null){
					String[] num_info = ((String)bean.get("sewing_num")).split(",");
					colors = new String[num_info.length];
					for(int i=0;i<num_info.length;i++){
						colors[i] =num_info[i].substring(0, num_info[i].indexOf("-"));
					}
				}

				if(bean.get("qualified_num_info")!=null){
					String [] numInfo = ((String)bean.get("qualified_num_info")).split(",");
					String [][] qualified_num_info = new String[numInfo.length][];
					for(int i=0;i<numInfo.length;i++){
						qualified_num_info[i] = numInfo[i].split("-");
					}
					bean.set("qualified_num_info", qualified_num_info);
				}else{
					bean.set("qualified_num_info", colors);
				}

				if(bean.get("unqualified_num_info")!=null){
					String [] numInfo = ((String)bean.get("unqualified_num_info")).split(",");
					String [][] qualified_num_info = new String[numInfo.length][];
					for(int i=0;i<numInfo.length;i++){
						qualified_num_info[i] = numInfo[i].split("-");
					}
					bean.set("unqualified_num_info", qualified_num_info);
				}else{
					bean.set("unqualified_num_info",colors);
				}
				resMap.put("qaInfo", bean.getMap());
			}
			FSPBean fspBean = new FSPBean();
			fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_SEWING_NUM_INFO_BY_EQL);
			fspBean.set("oaOrderId", orderId);
			OaTpe oaTpe = (OaTpe) manager.getOnlyObjectByEql(fspBean);

			resMap.put("oaTpe", oaTpe);
			resMap.put("code", 0);
			resMap.put("msg", "查询成功");
		}
	}

	private void getMaterialList2(int orderId, Map resMap) {
		if (orderId <= 0) { // 判断订单Id是否为空
			resMap.put("code", 2101);
			resMap.put("msg", "订单ID不能为空");
		} else {
			// 获取订单用料搭配明细列表
			FSPBean fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MATERIAL_LIST_BY_EQL);
			fsp.set("oaOrderId", orderId);
			List<OaMaterialList> oaMaterialList = getObjectsByEql(fsp);
			resMap.put("oaMaterialList", oaMaterialList); // 用料搭配明细列表
			resMap.put("code", 0);
			resMap.put("msg", "查询成功");

			getCqcStroe(orderId, resMap, oaMaterialList);
		}
	}


	private void getDaHuoList(int orderId, Map resMap){
		if (orderId <= 0) { // 判断订单Id是否为空
			resMap.put("code", 2101);
			resMap.put("msg", "订单ID不能为空");
		} else {
			List<Map> rList = new ArrayList<Map>();
			FSPBean fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_HUO_MATERIAL_PURCHASE_DESC_BY_SQL);
			fsp.set("orderId", orderId+"");
			List<LazyDynaMap> oaDaBanInfos = manager.getObjectsBySql(fsp);
			for (LazyDynaMap map : oaDaBanInfos) {
				Map tamp = new HashMap();
				tamp.put("marterialListId", (null == map.get("materiallistid")) ? "" : map.get("materiallistid"));
				tamp.put("position", (null == map.get("position")) ? "" : map.get("position"));
				tamp.put("oa_order_id", (null == map.get("oa_order_id")) ? "" : map.get("oa_order_id"));
				tamp.put("material_prop", (null == map.get("material_prop")) ? "" : map.get("material_prop"));
				tamp.put("material_name", (null == map.get("material_name")) ? "" : map.get("material_name"));
				tamp.put("type", (null == map.get("type")) ? "" : map.get("type"));
				tamp.put("color", (null == map.get("color")) ? "" : map.get("color"));
				tamp.put("supplier_name", (null == map.get("supplier_name") ? "" : map.get("supplier_name")));
				tamp.put("supplier_addr", (null == map.get("supplier_addr") ? "" : map.get("supplier_addr")));
				tamp.put("supplier_tel", (null == map.get("supplier_tel") ? "" : map.get("supplier_tel")));
				tamp.put("daHuoId", (null == map.get("dahuoid")) ? "" : map.get("dahuoid"));
				tamp.put("buffon", (null == map.get("buffon")) ? "" : map.get("buffon"));
				tamp.put("unit_num", (null == map.get("unit_num") ? "" : map.get("unit_num")));
				tamp.put("order_num", (null == map.get("order_num") ? "" : map.get("order_num")));
				tamp.put("need_num", (null == map.get("need_num") ? "" : map.get("need_num")));
				tamp.put("org", (null == map.get("org") ? "" : map.get("org")));
				tamp.put("num", (null == map.get("num") ? "" : map.get("num")));//采购数量
				tamp.put("price", (null == map.get("price") ? "" : map.get("price")));//单价
				//总金额=采购数量*单价
				if(null!=map.get("num") && null!=map.get("price")){
					tamp.put("total_price",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString()));
				}else{
					tamp.put("total_price", "");
				}
				tamp.put("test_price", (null == map.get("test_price") ? "" : map.get("test_price")));//验布费用
				tamp.put("freight", (null == map.get("freight") ? "" : map.get("freight")));//运费
				//合计=总金额+验布费用+运费
				if(null!=map.get("num") && null!=map.get("price") && null!=map.get("test_price") && null!=map.get("freight")){
					tamp.put("total",Float.parseFloat(map.get("price").toString())*Float.parseFloat(map.get("num").toString())
							+ Float.parseFloat(map.get("test_price").toString()) + Float.parseFloat(map.get("freight").toString()) );
				}else if(null!=map.get("num") && null!=map.get("price") && null!=map.get("test_price")){
					tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString())
							+ Float.parseFloat(map.get("test_price").toString()));
				}else if(null!=map.get("num") && null!=map.get("price") && null!=map.get("freight")){
					tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString())
							+  Float.parseFloat(map.get("freight").toString()));
				}else if(null!=map.get("num") && null!=map.get("price")){
					tamp.put("total",Float.parseFloat(map.get("num").toString())*Float.parseFloat(map.get("price").toString()) );
				}else{
					tamp.put("total","");
				}
				tamp.put("buyer_loss", (null == map.get("buyer_loss") ? "" : map.get("buyer_loss")));
				tamp.put("paper_tube", (null == map.get("paper_tube") ? "" : map.get("paper_tube")));
				tamp.put("deviation", (null == map.get("deviation") ? "" : map.get("deviation")));
				tamp.put("pur_memo", (null == map.get("pur_memo")) ? "" : map.get("pur_memo"));
				rList.add(tamp);
			}
			resMap.put("materialPurchaseDescList", rList);
			resMap.put("code", 0);
		}
	}

	private String getNollObject(Object o){
		if(o != null){
			return o.toString();
		}else{
			return "";
		}
	}

	public float getNotNullFloat(Float nums){
		if(nums == null){
			return 0;
		}else{
			return nums;
		}
	}

	private void getCqcStroe(int orderId, Map resMap, List<OaMaterialList> oaMaterialList){
		List<OaCqc> oaCqcList = new ArrayList<OaCqc>();
		if(oaMaterialList != null && oaMaterialList.size() > 0){
			for(int i = 0; i < oaMaterialList.size(); i++){
				FSPBean fsp = new FSPBean();
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CQC_BY_SQL);
				fsp.set("oaOrderId", oaMaterialList.get(i).getId());
				beans = manager.getObjectsBySql(fsp);
				if(beans != null && beans.size() > 0){
					for(int j = 0; j < beans.size(); j++){
						OaCqc c = new OaCqc();
						c.setId(Integer.parseInt(getNollObject(beans.get(j).get("id"))));
						c.setOaMaterialList(Integer.parseInt(getNollObject(beans.get(j).get("oa_material_list"))));
						c.setApplyUnitNum(getNollObject(beans.get(j).get("apply_unit_num")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("apply_unit_num"))));
						c.setReceiveTime(beans.get(j).get("receive_time") != null ? (Date) beans.get(j).get("receive_time") : null);
						c.setReceiveNum(getNollObject(beans.get(j).get("receive_num")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("receive_num"))));
						c.setReceiveRate(getNollObject(beans.get(j).get("receive_rate")));
						c.setReceiveMemo(getNollObject(beans.get(j).get("receive_memo")));
						c.setLossBundles(getNollObject(beans.get(j).get("loss_bundles")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("loss_bundles"))));
						c.setLossOther(getNollObject(beans.get(j).get("loss_other")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("loss_other"))));
						c.setLossOddments(getNollObject(beans.get(j).get("loss_oddments")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("loss_oddments"))));
						c.setLossMemo(getNollObject(beans.get(j).get("loss_memo")));
						c.setLossCompany(getNollObject(beans.get(j).get("loss_company")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("loss_company"))));
						c.setLossYiyou(getNollObject(beans.get(j).get("loss_yiyou")).equals("") ? null : Float.parseFloat(getNollObject(beans.get(j).get("loss_yiyou"))));
						c.setShearNumInfo(getNollObject(beans.get(j).get("shear_num_info")));
						oaCqcList.add(c);
					}
				}
			}
		}
		resMap.put("oaCqcList", oaCqcList);
		resMap.put("code", 0);
		resMap.put("msg", "查询成功");
	}




	private void getQiTao(int orderId, Map resMap) {
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_QITAO_BY_SQL);
		fsp.set("oaOrderId", orderId);
		beans = manager.getObjectsBySql(fsp);
		if(beans != null && beans.size() > 0){
			OaQiTao qitao = new OaQiTao();
			qitao.setId(Integer.parseInt(getNollObject(beans.get(0).get("id"))));
			qitao.setOaOrderId(Integer.parseInt(getNollObject(beans.get(0).get("oa_order_id"))));
			qitao.setQitaoReceiveTime(beans.get(0).get("qitao_receive_time") != null ? (Date) beans.get(0).get("qitao_receive_time") : null);
			qitao.setQitaoSendTime(beans.get(0).get("qitao_send_time") != null ? (Date) beans.get(0).get("qitao_send_time") : null);
			resMap.put("oaQiTao", qitao);
			resMap.put("code", 0);
			if(qitao != null && qitao.getId() > 0){
				getQiTaoDetial(qitao.getId(), resMap);
			}
		}
	}

	private void getQiTaoDetial(int qitaoId, Map resMap){
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_QITAODETAIL_BY_SQL);
		fsp.set("oaQitaoId", qitaoId);
		List<OaQiTaoDetail> oaQitaoDetailList = new ArrayList<OaQiTaoDetail>();
	 	beans = manager.getObjectsBySql(fsp);
		if(beans != null && beans.size() > 0){
			for(int i = 0; i < beans.size(); i++){
				OaQiTaoDetail c = new OaQiTaoDetail();
				c.setId(Integer.parseInt(getNollObject(beans.get(i).get("id"))));
				c.setOaQiTaoId(Integer.parseInt(getNollObject(beans.get(i).get("oa_qi_tao_id"))));
				c.setProject(getNollObject(beans.get(i).get("project")));
				c.setTracke(getNollObject(beans.get(i).get("tracke")));
				c.setDepartment(getNollObject(beans.get(i).get("department")));
				c.setOperator(getNollObject(beans.get(i).get("operator")));
				c.setCreateTime(beans.get(i).get("create_time") != null ? (Date)beans.get(i).get("create_time") : new Date());
				oaQitaoDetailList.add(c);
			}
		}
		resMap.put("oaQiTaoDetails", oaQitaoDetailList);
		resMap.put("code", 0);
	}

	/**
	 *
	 * @Title: jsonGetDaHuoSixNode
	 * @Description: TODO改造第六个流程节点，大货类型（TPE）
	 *
	 * @author 张华
	 */
	public void jsonGetDaHuoSixNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("oaOrderId");
			String orderNumId = Struts2Utils.getParameter("oaOrderNumId");
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "6"; // 当前节点的index，后面查询需要
			int orderId = Integer.valueOf(orderIds);
			int orderNum = Integer.valueOf(orderNumId);

			// 查询尺码表内容
			getOaOrderNum(orderNum, resMap);
			OaOrderNum oaOrderNum = (OaOrderNum) resMap.get("oaOrderNum");
			resMap.remove("oaOrderNum");
			resMap.put("sizeTitle", oaOrderNum.getTitle());//尺寸表表头
			resMap.put("sizeNumInfo", oaOrderNum.getNumInfo());//尺寸表颜色数量信息

			// 查询CQC裁减数量
			getShearNum(orderId, resMap);

			// 查询车缝数量
			getSewingNumInfo(orderId, resMap);

			// 查询管理信息
			getManagerInfo(orderId, node, wfStepIndex, resMap);

			// 查询异动跟踪信息
			getTracke(orderId, node, resMap);

			resMap.put("code", 0);
			resMap.put("msg", "TPE节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "TPE节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: jsonGetDaHuoEightNode
	 * @Description: TODO改造第8个流程节点，大货类型（财务）
	 *
	 * @author 蓝玉
	 */
	public void jsonGetDaHuoEightNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("oaOrderId");
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "8"; // 当前节点的index，后面查询需要
			int orderId = Integer.valueOf(orderIds);

			// 查询管理信息
			getManagerInfo(orderId, node, wfStepIndex, resMap);

			resMap.put("code", 0);
			resMap.put("msg", "财务节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "财务节点-信息查询出错");
		}

		Struts2Utils.renderJson(resMap);
	}

	/**
	 *
	 * @Title: getShearNum
	 * @Description: TODO获取CQC节点的裁减数量信息
	 *
	 * @author 张华
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	private void getShearNum(int orderId, Map resMap) throws Exception {
		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_SHEAR_NUM_BY_SQL);
		fspBean.set("oaOrderId", orderId);
		List<LazyDynaMap> shearNumList = getObjectsBySql(fspBean);
		List<Map> rList = new ArrayList<Map>();
		for (LazyDynaMap map : shearNumList) {
			Map temp = new HashMap();
			String shearColor = (String) map.get("color"); //色号
			String shearNum = (String) map.get("shear_num_info");
			if (StringUtils.isBlank(shearNum)) {
				shearNum = "";
				String sizeTitles[] = ((String) resMap.get("sizeTitle")).split("-");
				for (int i = 0; i < sizeTitles.length; i++) { //拼接裁减数量信息，数据都为空
					shearNum += ",";
				}
				shearNum = shearNum.substring(0, shearNum.length() - 1);
			}
			temp.put("shearNum", shearNum);
			temp.put("shearColor", shearColor);
			rList.add(temp);
		}
		resMap.put("shearNum", rList);
	}

	/**
	 *
	 * @Title: getSewingNumInfo
	 * @Description: TODO获取TPE车缝数量信息
	 *
	 * @author 张华
	 * @param orderId
	 * @param resMap
	 * @throws Exception
	 */
	private void getSewingNumInfo(int orderId, Map resMap) throws Exception {
		FSPBean fspBean = new FSPBean();
		fspBean.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_SEWING_NUM_INFO_BY_EQL);
		fspBean.set("oaOrderId", orderId);
		OaTpe oaTpe = (OaTpe) manager.getOnlyObjectByEql(fspBean);

		String sewingNum = (null == oaTpe ? "" : oaTpe.getSewingNum()); // 车缝数量
		if (StringUtils.isBlank(sewingNum)) { // 判断车缝数量是否为空，为空则拼接尺码数量的颜色
			// 解析尺码数量颜色并拼接
			String sizeNumInfos[] = ((String) resMap.get("sizeNumInfo")).split(",");
			resMap.remove("sizeNumInfo");

			String sizeTitles[] = ((String) resMap.get("sizeTitle")).split("-");
			String shearNumSize = "";
			for (int i = 0; i < sizeTitles.length; i++) { // 拼接裁减数量信息，数据都为空
				shearNumSize += "-";
			}
			for (String temp : sizeNumInfos) {
				if (StringUtils.isBlank(sewingNum)) {
					sewingNum = temp.split("-")[0] + shearNumSize;
				} else {
					sewingNum += "," + temp.split("-")[0] + shearNumSize;
				}
			}
		}

		resMap.put("sewingNum", sewingNum.split(",")); // 车缝数量
		resMap.put("sewingTotal", null == oaTpe ? "" : oaTpe.getSewingTotal()); // 车缝产出数量
		resMap.put("sewingFactory", null == oaTpe ? "" : oaTpe.getSewingFactory()); // 生产工厂
		resMap.put("sewingId", null == oaTpe ? "" : oaTpe.getId()); // 车缝数量信息Id
	}

	/**
	 *
	 * @Title: saveOrderDaHuoSix
	 * @Description: TODO保存tpe流程节点
	 *
	 * @author 张华
	 * @param oaOrder
	 */
	private boolean saveOrderDaHuoSix(OaOrder oaOrder) {
		try {
			// 保存tpe车缝数量信息
			if (null != oaTpe) {
				oaTpe.setOaOrderId(oaOrder.getId());
				saveObject(oaTpe);
			}
			// 保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrder.getOaOrderDetail());

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 保存cqc结点所有数据
	 *
	 * @return
	 * @author 范蠡
	 */
	private boolean saveCqc() {
		boolean flag = true;
		try {
			if (oaCqcLists != null && oaCqcLists.size() > 0) {
				for (int i = 0; i < oaCqcLists.size(); i++) {
					manager.saveObject(oaCqcLists.get(i));
					/*
					 * if(oaCqcLists.get(i).getId() != null && oaCqcLists.get(i).getId() > 0){ OaCqc cqc = (OaCqc)manager.getObject(OaCqc.class, oaCqcLists.get(i).getId()); if(cqc != null){
					 * if(oaCqcLists.get(i).getApplyUnitNum() != null && oaCqcLists.get(i).getApplyUnitNum() > 0){ cqc.setApplyUnitNum(oaCqcLists.get(i).getApplyUnitNum()); }
					 * if(oaCqcLists.get(i).getReceiveTime() != null){ cqc.setReceiveTime(oaCqcLists.get(i).getReceiveTime()); } if(oaCqcLists.get(i).getReceiveNum() != null &&
					 * oaCqcLists.get(i).getReceiveNum() > 0){ cqc.setReceiveNum(oaCqcLists.get(i).getReceiveNum()); } if(oaCqcLists.get(i).getReceiveRate() != null){
					 * cqc.setReceiveRate(oaCqcLists.get(i).getReceiveRate()); } if(oaCqcLists.get(i).getReceiveMemo() != null){ cqc.setReceiveMemo(oaCqcLists.get(i).getReceiveMemo()); }
					 * if(oaCqcLists.get(i).getLossBundles() != null && oaCqcLists.get(i).getLossBundles() > 0){ cqc.setLossBundles(oaCqcLists.get(i).getLossBundles()); }
					 * if(oaCqcLists.get(i).getLossOther() != null && oaCqcLists.get(i).getLossOther() > 0){ cqc.setLossOther(oaCqcLists.get(i).getLossOther()); }
					 * if(oaCqcLists.get(i).getLossOddments() != null && oaCqcLists.get(i).getLossOddments() > 0){ cqc.setLossOddments(oaCqcLists.get(i).getLossOddments()); }
					 * if(oaCqcLists.get(i).getLossMemo() != null){ cqc.setLossMemo(oaCqcLists.get(i).getLossMemo()); } if(oaCqcLists.get(i).getShearNumInfo() != null){
					 * cqc.setShearNumInfo(oaCqcLists.get(i).getShearNumInfo()); } if(oaCqcLists.get(i).getLossCompany() != null){ cqc.setLossCompany(oaCqcLists.get(i).getLossCompany()); }
					 * if(oaCqcLists.get(i).getLossYiyou() != null){ cqc.setLossYiyou(oaCqcLists.get(i).getLossYiyou()); } manager.saveObject(cqc); }else{ manager.saveObject(oaCqcLists.get(i)); }
					 * }else{ manager.saveObject(oaCqcLists.get(i)); }
					 */
					// 以上注释代码 页面值非空验证 现在页面可以为空 则注释了 如果需求改了要求非空了 可以放开处理
				}
			}
			delQiTaoDetail(delQiTaoDetails);
			saveDaHuo();
			saveQiTao();
			saveOaOrderDetail();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	public boolean saveQA(){
		boolean flag = true;
		try {
			if(oaQa != null){
				manager.saveObject(oaQa);
			}
			saveOaOrderDetail();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	/**
	 * 删除齐套detail数据
	 *
	 * @param delQiTaoDetails
	 * @author 范蠡
	 */
	//update by 张华 2015-01-20
	private void delQiTaoDetail(String delQiTaoDetails) throws Exception {
		if (delQiTaoDetails != null && !"".equals(delQiTaoDetails)) {
			String str[] = delQiTaoDetails.split(",");
			for (String s : str) {
				OaQiTaoDetail qtd = (OaQiTaoDetail) manager.getObject(OaQiTaoDetail.class, Integer.parseInt(s.trim()));
				if (qtd != null) {
					manager.delObject(qtd);
				}
			}
		}
	}

	/**
	 * 在cqc结点保存orderDetail数据
	 *
	 * @author 范蠡
	 */
	// update by 张华 2015-01-20
	private void saveOaOrderDetail() throws Exception {
		if (oaOrderDetail != null && oaOrderDetail_id != null && !"".equals(oaOrderDetail_id)) {
			OaOrderDetail odetail = (OaOrderDetail) manager.getObject(OaOrderDetail.class, Integer.parseInt(oaOrderDetail_id));
			if (odetail != null) {
				odetail.setContent(oaOrderDetail.getContent());
				odetail.setOtherFile(oaOrderDetail.getOtherFile());
				odetail.setWorker(oaOrderDetail.getWorker());
				odetail.setWorkTime(this.oaOrderDetail.getWorkTime());
				manager.saveObject(odetail);
			}
		}
	}

	/**
	 * 添加齐套detail信息
	 *
	 * @param qt
	 * @author 范蠡
	 */
	// update by 张华 2015-01-20
	private void saveQitaoDetail(OaQiTao qt) throws Exception {
		if (oaQiTaoDetails != null && oaQiTaoDetails.size() > 0) {
			for (int i = 0; i < oaQiTaoDetails.size(); i++) {
				/*
				 * if(oaQiTaoDetails.get(i).getId() != null && oaQiTaoDetails.get(i).getId() > 0){ OaQiTaoDetail oadetail = (OaQiTaoDetail)manager.getObject(OaQiTaoDetail.class,
				 * oaQiTaoDetails.get(i).getId()); if(oaQiTaoDetails.get(i).getDepartment() != null && !"".equals(oaQiTaoDetails.get(i).getDepartment())){
				 * oadetail.setDepartment(oaQiTaoDetails.get(i).getDepartment()); } if(oaQiTaoDetails.get(i).getOperator() != null && !"".equals(oaQiTaoDetails.get(i).getOperator())){
				 * oadetail.setOperator(oaQiTaoDetails.get(i).getOperator()); } if(oaQiTaoDetails.get(i).getProject() != null && !"".equals(oaQiTaoDetails.get(i).getProject())){
				 * oadetail.setProject(oaQiTaoDetails.get(i).getProject()); } if(oaQiTaoDetails.get(i).getTracke() != null && !"".equals(oaQiTaoDetails.get(i).getTracke())){
				 * oadetail.setTracke(oaQiTaoDetails.get(i).getTracke()); } manager.saveObject(oadetail); }else{ oaQiTaoDetails.get(i).setOaQiTaoId(qt.getId()); oaQiTaoDetails.get(i).setCreateTime(new
				 * Date()); manager.saveObject(oaQiTaoDetails.get(i)); }
				 */
				// 以上注释代码 页面值非空验证 现在页面可以为空 则注释了 如果需求改了要求非空了 可以放开处理
				if(oaQiTaoDetails.get(i)!=null){
					oaQiTaoDetails.get(i).setOaQiTaoId(qt.getId());
					oaQiTaoDetails.get(i).setCreateTime(new Date());
					manager.saveObject(oaQiTaoDetails.get(i));
				}
			}
		}
	}

	/**
	 * 修改大货信息
	 *
	 * @author 范蠡
	 * */
	// update by 张华 2015-01-20
	private void saveDaHuo() throws Exception {
		if (oaDaHuoInfos != null && oaDaHuoInfos.size() > 0) {
			for (int i = 0; i < oaDaHuoInfos.size(); i++) {
				if (oaDaHuoInfos.get(i).getId() != null && oaDaHuoInfos.get(i).getId() > 0) {
					OaDaHuoInfo dahuo = (OaDaHuoInfo) manager.getObject(OaDaHuoInfo.class, oaDaHuoInfos.get(i).getId()); // 获取原始数据
					if (dahuo != null) {
						if (oaDaHuoInfos.get(i).getUnitNum() != null) {
							dahuo.setUnitNum(oaDaHuoInfos.get(i).getUnitNum()); // 修改单间用量
						}
						if (oaDaHuoInfos.get(i).getOrg() != null) {
							dahuo.setOrg(oaDaHuoInfos.get(i).getOrg()); // 修改单件单位
						}
						if (oaDaHuoInfos.get(i).getNeedNum() != null) {
							dahuo.setNeedNum(oaDaHuoInfos.get(i).getNeedNum()); // 修改需求单位
						}
						manager.saveObject(dahuo);
					}
				}
			}
		}
	}

	/**
	 * 保存齐套信息
	 * @author 范蠡
	 */
	// update by 张华 2015-01-20
	private void saveQiTao() throws Exception {
		if (oaQiTao != null) {
			/*
			 * if(oaQiTao.getId() != null && oaQiTao.getId() > 0){ OaQiTao tao = (OaQiTao)manager.getObject(OaQiTao.class, oaQiTao.getId()); if(tao != null){ if(oaQiTao.getQitaoReceiveTime() != null)
			 * { tao.setQitaoReceiveTime(oaQiTao.getQitaoReceiveTime()); } if(oaQiTao.getQitaoSendTime() != null) { tao.setQitaoSendTime(oaQiTao.getQitaoSendTime()); } manager.saveObject(tao); }else{
			 * manager.saveObject(oaQiTao); } }else{ manager.saveObject(oaQiTao); }
			 */
			// 以上注释代码 页面值非空验证 现在页面可以为空 则注释了 如果需求改了要求非空了 可以放开处理
			manager.saveObject(oaQiTao);
			saveQitaoDetail(oaQiTao);
		}
	}

	/**
	 *
	 * @Title: jsonGetDaHuoSixNode
	 * @Description: TODO改造第六个流程节点，大货类型（TPE）
	 *
	 * @author 张华
	 */
	public void jsonGetDaHuoNineNode() {
		Map resMap = new HashMap();// 返回结果
		try {
			String orderIds = Struts2Utils.getParameter("oaOrderId");
			String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
			String node = "9"; // 当前节点的index，后面查询需要
			int orderId = Integer.valueOf(orderIds);
			OaOrder oaOrder = (OaOrder) manager.getObject(OaOrder.class, orderId);

			if(oaOrder != null){
				resMap.put("oaOrder", oaOrder);
				getOaOrderNum(oaOrder.getOaOrderNumId(), resMap);
				//获取物流信息
				getOaLogistics(orderId, resMap);
				getOaQaList(orderId, resMap);
				// 3.查询管理信息
				getManagerInfo(orderId, node, wfStepIndex, resMap);

				getTracke(orderId, node, resMap);
			}
			resMap.put("code", 0);
			resMap.put("msg", "物流节点-信息查询成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resMap.put("msg", "物流节点-信息查询出错");
		}

		Struts2Utils.writeJson(resMap);
	}

	/**
	 * 获取物流信息
	 * @param orderId
	 * @param resMap
	 * @author 范蠡
	 */
	public void getOaLogistics(Integer orderId, Map resMap){
		FSPBean fsp = new FSPBean();
		List<OaLogistics> logList = new ArrayList<OaLogistics>();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OALOGISTICS_BY_SQL);
		fsp.set("oaOrderId", orderId);
		beans = manager.getObjectsBySql(fsp);
		if(beans != null && beans.size() > 0){
			for(LazyDynaBean bean : beans){
				OaLogistics logistics = new OaLogistics();
				logistics.setId(Integer.parseInt(getNollObject(bean.get("id"))));
				logistics.setOaOrderId(Integer.parseInt(getNollObject(bean.get("oa_order_id"))));
				logistics.setLogisticsNum(getNollObject(bean.get("logistics_num")));
				logistics.setLogisticsCompany(getNollObject(bean.get("logistics_company")));
				logistics.setDeliveryPle(getNollObject(bean.get("delivery_ple")));
				logistics.setDeliveryNum(Float.parseFloat(getNollObject(bean.get("delivery_num"))));
				logistics.setDeliveryTime(bean.get("delivery_time") != null ? (Date) bean.get("delivery_time") : null);
				logistics.setCarNum(getNollObject(bean.get("car_num")));
				logistics.setFileUrl(getNollObject(bean.get("file_url")));
				logistics.setRemarks(getNollObject(bean.get("remarks")));
				logList.add(logistics);
			}
		}
		resMap.put("oaLogisticsList", logList);
	}

	/**
	 * 获取QA合格产品信息
	 * @param orderId
	 * @param resMap
	 * @author 范蠡
	 */
	public void getOaQaList(Integer orderId, Map resMap){
		List<OaQa> qaList = new ArrayList<OaQa>();

		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OAQA_BY_SQL);
		fsp.set("oaOrderId", orderId);
		beans = manager.getObjectsBySql(fsp);
		if(beans != null && beans.size() > 0){
			for(LazyDynaBean bean : beans){
				OaQa oqa = new OaQa();
				oqa.setId(Integer.parseInt(getNollObject(bean.get("id"))));
				oqa.setQualifiedNumInfo(getNollObject(bean.get("qualified_num_info")));
				oqa.setQualifiedTotal(Float.parseFloat(getNollObject(bean.get("qualified_total"))));
				qaList.add(oqa);
			}
		}
		resMap.put("oaQaList", qaList);
	}


	/**
	 * 保存物流信息
	 * @author 范蠡
	 */
	public void saveOaLogistics(){
		Map resMap = new HashMap();
		try {
			if(oaLogistics != null){
				manager.saveObject(oaLogistics);
				resMap.put("oaLogistics",oaLogistics);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		Struts2Utils.renderJson(resMap);
	}

	/**
	 * 保存物流结点信息
	 * @return
	 */
	public boolean saveLogistics(){
		boolean bl = false;
		try {
			saveOaOrderDetail();
			delOaLogistics();
			bl = true;
		}catch (Exception e){
			bl = false;
			e.printStackTrace();
		}
		return bl;
	}

	private void delOaLogistics(){
		if (logCheckBoxVal != null && !"".equals(logCheckBoxVal)) {
			String str[] = logCheckBoxVal.split(",");
			for (String s : str) {
				OaLogistics qtd = (OaLogistics) manager.getObject(OaLogistics.class, Integer.parseInt(s.trim()));
				if (qtd != null) {
					manager.delObject(qtd);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Title: terminateOrder
	 * @Description: TODO提前终止订单
	 *
	 * @author 张华
	 */
	public void terminateOrder() {
		Map resMap = new HashMap(); //返回结果
		try {
			// 订单id不能为空
			if (null != oaOrder || null != oaOrder.getId()) {
				// 终止订单原因不能为空
				if (StringUtils.isNotBlank(oaOrder.getTerminateMemo())) {
					OaOrder oaOrder1 = (OaOrder) manager.getObject(OaOrder.class, oaOrder.getId());
					
					oaOrder1.setStatus("1"); // 更改订单状态实现终止订单
					oaOrder1.setTerminateMemo(oaOrder.getTerminateMemo()); // 终止订单原因
					oaOrder1.setTerminateUser(WebUtil.getCurrentLoginBx().getLoginName()); // 终止订单操作人
					oaOrder1.setTerminateTime(new Timestamp(System.currentTimeMillis())); // 终止订单时间
					saveObject(oaOrder1);
					resMap.put("code", 0);
					resMap.put("msg", "终止订单成功");
				} else {
					resMap.put("msg", "终止订单原因不能为空");
				}
			} else {
				// 订单id为空抛出异常
				throw new RuntimeException("order ID is null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			resMap.put("msg", "终止订单错误");
			e.printStackTrace();
		}

		Struts2Utils.renderJson(resMap);
	}
	
	public OaLogistics getOaLogistics() {
		return oaLogistics;
	}

	public void setOaLogistics(OaLogistics oaLogistics) {
		this.oaLogistics = oaLogistics;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public OaStaff getOaStaff() {
		return oaStaff;
	}

	public void setOaStaff(OaStaff oaStaff) {
		this.oaStaff = oaStaff;
	}

	public List<LazyDynaMap> getBeans() {
		return beans;
	}

	public void setBeans(List<LazyDynaMap> beans) {
		this.beans = beans;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public OaOrder getOaOrder() {
		return oaOrder;
	}

	public void setOaOrder(OaOrder oaOrder) {
		this.oaOrder = oaOrder;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the treeNode
	 */
	public String getTreeNode() {
		return treeNode;
	}

	/**
	 * @param treeNode
	 *            the treeNode to set
	 */
	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode;
	}

	/**
	 * @return the oaStaffId
	 */
	public String getOaStaffId() {
		return oaStaffId;
	}

	/**
	 * @param oaStaffId
	 *            the oaStaffId to set
	 */
	public void setOaStaffId(String oaStaffId) {
		this.oaStaffId = oaStaffId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the linkww
	 */
	public String getLinkww() {
		return linkww;
	}

	/**
	 * @param linkww
	 *            the linkww to set
	 */
	public void setLinkww(String linkww) {
		this.linkww = linkww;
	}

	/**
	 * @return the linkphone
	 */
	public String getLinkphone() {
		return linkphone;
	}

	/**
	 * @param linkphone
	 *            the linkphone to set
	 */
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	/**
	 * @return the oaOrgId
	 */
	public String getOaOrgId() {
		return oaOrgId;
	}

	/**
	 * @param oaOrgId
	 *            the oaOrgId to set
	 */
	public void setOaOrgId(String oaOrgId) {
		this.oaOrgId = oaOrgId;
	}

	/**
	 * @return the bean
	 */
	public LazyDynaMap getBean() {
		return bean;
	}

	/**
	 * @param bean
	 *            the bean to set
	 */
	public void setBean(LazyDynaMap bean) {
		this.bean = bean;
	}

	/**
	 * @return the oaOrderDetail
	 */
	public OaOrderDetail getOaOrderDetail() {
		return oaOrderDetail;
	}

	/**
	 * @param oaOrderDetail
	 *            the oaOrderDetail to set
	 */
	public void setOaOrderDetail(OaOrderDetail oaOrderDetail) {
		this.oaOrderDetail = oaOrderDetail;
	}

	/**
	 * @return the oaTimebase
	 */
	public OaTimebase getOaTimebase() {
		return oaTimebase;
	}

	/**
	 * @param oaTimebase
	 *            the oaTimebase to set
	 */
	public void setOaTimebase(OaTimebase oaTimebase) {
		this.oaTimebase = oaTimebase;
	}

	/**
	 * @return the oaTimebases
	 */
	public List<OaTimebase> getOaTimebases() {
		return oaTimebases;
	}

	/**
	 * @param oaTimebases
	 *            the oaTimebases to set
	 */
	public void setOaTimebases(List<OaTimebase> oaTimebases) {
		this.oaTimebases = oaTimebases;
	}

	/**
	 * @return the oaTimebaseEntries
	 */
	public List<OaTimebaseEntry> getOaTimebaseEntries() {
		return oaTimebaseEntries;
	}

	/**
	 * @param oaTimebaseEntries
	 *            the oaTimebaseEntries to set
	 */
	public void setOaTimebaseEntries(List<OaTimebaseEntry> oaTimebaseEntries) {
		this.oaTimebaseEntries = oaTimebaseEntries;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the oldPwd
	 */
	public String getOldPwd() {
		return oldPwd;
	}

	/**
	 * @param oldPwd
	 *            the oldPwd to set
	 */
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	/**
	 * @return the newPwd
	 */
	public String getNewPwd() {
		return newPwd;
	}

	/**
	 * @param newPwd
	 *            the newPwd to set
	 */
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * @return the oaDts
	 */
	public List<OaDt> getOaDts() {
		return oaDts;
	}

	/**
	 * @param oaDts
	 *            the oaDts to set
	 */
	public void setOaDts(List<OaDt> oaDts) {
		this.oaDts = oaDts;
	}

	/**
	 * @return the oaDt
	 */
	public OaDt getOaDt() {
		return oaDt;
	}

	/**
	 * @param oaDt
	 *            the oaDt to set
	 */
	public void setOaDt(OaDt oaDt) {
		this.oaDt = oaDt;
	}

	public Integer getOaRole() {
		return oaRole;
	}

	public void setOaRole(Integer oaRole) {
		this.oaRole = oaRole;
	}

	public List<LazyDynaMap> getStepCount() {
		return stepCount;
	}

	public void setStepCount(List<LazyDynaMap> stepCount) {
		this.stepCount = stepCount;
	}

	public List<OaMaterialList> getOaMaterialLists() {
		return oaMaterialLists;
	}

	public void setOaMaterialLists(List<OaMaterialList> oaMaterialLists) {
		this.oaMaterialLists = oaMaterialLists;
	}

	public List<OaCusMaterialList> getOaCusMaterialLists() {
		return oaCusMaterialLists;
	}

	public void setOaCusMaterialLists(List<OaCusMaterialList> oaCusMaterialLists) {
		this.oaCusMaterialLists = oaCusMaterialLists;
	}

	public String getMaterialDelIds() {
		return materialDelIds;
	}

	public void setMaterialDelIds(String materialDelIds) {
		this.materialDelIds = materialDelIds;
	}

	public String getCusMaterialDelIds() {
		return cusMaterialDelIds;
	}

	public void setCusMaterialDelIds(String cusMaterialDelIds) {
		this.cusMaterialDelIds = cusMaterialDelIds;
	}

	public String getSystemEnvironment() {
		return systemEnvironment;
	}

	public void setSystemEnvironment(String systemEnvironment) {
		this.systemEnvironment = systemEnvironment;
	}

	public List<OaDaHuoInfo> getOaDaHuoInfos() {
		return oaDaHuoInfos;
	}

	public void setOaDaHuoInfos(List<OaDaHuoInfo> oaDaHuoInfos) {
		this.oaDaHuoInfos = oaDaHuoInfos;
	}

	public List<OaClothesSizeDetail> getOaClothesSizeDetails() {
		return oaClothesSizeDetails;
	}

	public void setOaClothesSizeDetails(List<OaClothesSizeDetail> oaClothesSizeDetails) {
		this.oaClothesSizeDetails = oaClothesSizeDetails;
	}

	public OaProcessExplain getOaProcessExplain() {
		return oaProcessExplain;
	}

	public void setOaProcessExplain(OaProcessExplain oaProcessExplain) {
		this.oaProcessExplain = oaProcessExplain;
	}

	public OaTracke getOaTracke() {
		return oaTracke;
	}

	public void setOaTracke(OaTracke oaTracke) {
		this.oaTracke = oaTracke;
	}

	public List<OaDaBanInfo> getOaDaBanInfos() {
		return oaDaBanInfos;
	}

	public void setOaDaBanInfos(List<OaDaBanInfo> oaDaBanInfos) {
		this.oaDaBanInfos = oaDaBanInfos;
	}

	public List<OaOrder> getRelationOrderList() {
		return relationOrderList;
	}

	public void setRelationOrderList(List<OaOrder> relationOrderList) {
		this.relationOrderList = relationOrderList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public OaClothesSize getOaClothesSize() {
		return oaClothesSize;
	}

	public void setOaClothesSize(OaClothesSize oaClothesSize) {
		this.oaClothesSize = oaClothesSize;
	}

	public String getClothesSizeDetailDelIds() {
		return clothesSizeDetailDelIds;
	}

	public void setClothesSizeDetailDelIds(String clothesSizeDetailDelIds) {
		this.clothesSizeDetailDelIds = clothesSizeDetailDelIds;
	}

	public String getMaterialDescDelIds() {
		return materialDescDelIds;
	}

	public void setMaterialDescDelIds(String materialDescDelIds) {
		this.materialDescDelIds = materialDescDelIds;
	}

	public OaCost getOaCost() {
		return oaCost;
	}

	public void setOaCost(OaCost oaCost) {
		this.oaCost = oaCost;
	}

	public OaMrConfirm getOaMrConfirm() {
		return oaMrConfirm;
	}

	public void setOaMrConfirm(OaMrConfirm oaMrConfirm) {
		this.oaMrConfirm = oaMrConfirm;
	}
	public List<OaCqc> getOaCqcLists() {
		return oaCqcLists;
	}

	public void setOaCqcLists(List<OaCqc> oaCqcLists) {
		this.oaCqcLists = oaCqcLists;
	}

	public OaQiTao getOaQiTao() {
		return oaQiTao;
	}

	public void setOaQiTao(OaQiTao oaQiTao) {
		this.oaQiTao = oaQiTao;
	}

	public List<OaQiTaoDetail> getOaQiTaoDetails() {
		return oaQiTaoDetails;
	}

	public void setOaQiTaoDetails(List<OaQiTaoDetail> oaQiTaoDetails) {
		this.oaQiTaoDetails = oaQiTaoDetails;
	}

	public OaTpe getOaTpe() {
		return oaTpe;
	}

	public void setOaTpe(OaTpe oaTpe) {
		this.oaTpe = oaTpe;
	}

	public String getDelQiTaoDetails() {
		return delQiTaoDetails;
	}

	public void setDelQiTaoDetails(String delQiTaoDetails) {
		this.delQiTaoDetails = delQiTaoDetails;
	}

	public String getOaOrderDetail_id() {
		return oaOrderDetail_id;
	}

	public void setOaOrderDetail_id(String oaOrderDetail_id) {
		this.oaOrderDetail_id = oaOrderDetail_id;
	}

	public OaQa getOaQa() {
		return oaQa;
	}

	public void setOaQa(OaQa oaQa) {
		this.oaQa = oaQa;
	}

	public String getLogCheckBoxVal() {
		return logCheckBoxVal;
	}

	public void setLogCheckBoxVal(String logCheckBoxVal) {
		this.logCheckBoxVal = logCheckBoxVal;
	}

	public String getOrderColor() {
		return orderColor;
	}

	public void setOrderColor(String orderColor) {
		this.orderColor = orderColor;
	}
	
	// 得到订单进度跟踪报表 by fangwei 2015-02-04
	public void outputOrderList(){
        beans = rtnOrogressReport();
        String type ="3";
        if(fsp.get("type").toString().equals("2")){
        	type="2";
        }
        
		StringBuilder sb = new StringBuilder();
		for (LazyDynaMap map : beans) {
			sb.append(map.get("id")+",");
		}
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_LIST_EXCEL_INFO);
		fsp.setStaticSqlPart("oo.id in ("+sb.substring(0, sb.length()-1)+")");
		beans = getObjectsBySql(fsp);
		
		Map<String, Object> fillInfo = new HashMap<String,Object>();
		Map<String,Object> sheetFileInfo = new HashMap<String,Object>();
		if("3".equals(type)){
			ProcessDaHuoJinDu(fillInfo, sheetFileInfo);
		}else if("2".equals(type)){
			ProcessDaBanJinDu(fillInfo, sheetFileInfo);
		}
		try {
			OutputStream os = Struts2Utils.getResponse().getOutputStream();
			Struts2Utils.getResponse().setContentType("Application/msexcel");
			Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=ERP-REPORT-"+(System.currentTimeMillis() + "").substring(6, 13) + ".xlsx");
			POIUtilsEx.processExcel(os,fillInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Integer getOrdreNum(String numInfos){
		Integer rtNum = 0;
		if(StringUtils.isNotBlank(numInfos)){
			String [] nums1 = numInfos.split(",");
			if(nums1.length>0){
				for(int i=0;i<nums1.length;i++){
					String[] nums2 = nums1[i].split("-");
					if(nums2.length>0){
						for(int m=1;m<nums2.length;m++){
							rtNum+= Integer.parseInt(nums2[m]);
						}
					}
				}
			}
		}
		return rtNum;
	}

	private void ProcessDaBanJinDu(Map<String, Object> fillInfo, Map<String, Object> sheetFileInfo) {
		int t = 0;
		Long totalTime = 0L;
		Integer lastId =0 ;
		Boolean isNew =false;
		for(LazyDynaMap map : beans){
			String wf_step = map.get("wf_step").toString();
			if(!((Integer) map.get("id")).equals(lastId)){
				lastId = (Integer) map.get("id");
				totalTime =0L;
				t++;
				isNew=true;
			}
			Long real_time = 0L;
			if(map.get("wf_real_finish")!=null){
				real_time = Math.abs(BizUtil.getWorkTimeBetween((Timestamp) map.get("wf_real_finish"), (Timestamp) map.get("wf_real_start")));
			}
			Long plan_time = (Long) map.get("wf_step_duration");
			if(real_time!=null){
				totalTime+=real_time;
			}
			switch (wf_step) {
			case "b_create_yangyi_1":
				if(isNew){
					Integer orderNum = getOrdreNum(map.get("num_info")==null?"":(String)map.get("num_info"));
					sheetFileInfo.put(t+",21,Double",orderNum);
					sheetFileInfo.put(t+",4",map.get("value")==null?"":map.get("value").toString());
					sheetFileInfo.put(t+",3",map.get("style_class")==null?"":map.get("style_class").toString());
					sheetFileInfo.put(t+",2",map.get("cus_name")==null?"":map.get("cus_name").toString());
					sheetFileInfo.put(t+",1",map.get("sell_order_code")==null?"":map.get("sell_order_code").toString());
					sheetFileInfo.put(t+",0",new CustomCell("IF(ISNUMBER(INDIRECT(\"A\"&ROW()-1)),INDIRECT(\"A\"&ROW()-1)+1,1)", "Expression"));
					
					sheetFileInfo.put(t+",52",new CustomCell("SUM(AV"+(t+1)+":AZ"+(t+1)+")", "Expression"));
					isNew =false;
				}
				break;
			case "b_mr_improve_2":
				if(real_time!=null){
					getColorIndex2(real_time, plan_time, sheetFileInfo, map, t, "operator", 5, 16, 10);
				}
				break;
			case "b_ppc_confirm_3":
				if(real_time!=null){
					getColorIndex2(real_time, plan_time, sheetFileInfo, map, t, "worker", 6, 17, 11);
				}
				break;
			case "b_pur_confirm_4":
				if(real_time!=null){
					getColorIndex2(real_time, plan_time, sheetFileInfo, map, t, "worker", 7, 18, 12);
				}
				break;
			case "b_ppc_confirm_5":
				if(real_time!=null){
					getColorIndex2(real_time, plan_time, sheetFileInfo, map, t, "worker", 8, 19, 13);
				}
				break;
			case "b_qc_confirm_6":
				if(real_time!=null){
					getColorIndex2(real_time, plan_time, sheetFileInfo, map, t, "worker", 9, 20, 14);
				}
				break;
			default:
				break;
			}
			
			Long except_time = 0L;
			except_time = Math.abs(BizUtil.getWorkTimeBetween((Timestamp) map.get("except_finish"), (Timestamp) map.get("begin_time")));
			Short color;
			if(except_time!=null){
				if(except_time>totalTime){
					color = IndexedColors.GREEN.index;
				}else if((totalTime-except_time)<=14400000L){
					color = IndexedColors.YELLOW.index;
				}else{
					color = IndexedColors.RED.index;
				}
				sheetFileInfo.put(t+",15", new CustomCell("IF(AND(BA"+(t+1)+"<>\"\",BA"+(t+1)+"<>0),TEXT(BA"+(t+1)+"/86400,\"[h]小时mm分\"),\"\")", "Expression").setCellColor(color));
			}
		}
		String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseReportExcelFile"));
		String sheetNames = "订单进度跟踪报表-打版";
		fillInfo.put("fileUrl", baseExcelFile);
		fillInfo.put("sheetNames", sheetNames);
		
		Map<String,Object> sheetMergeCell = new HashMap<String,Object>();
		
		sheetMergeCell.put(++t+","+t+",0,7", true);
		sheetFileInfo.put(t+",8", "平均值：");
		sheetFileInfo.put(t+",21",new CustomCell("\"总数:\"&SUM(INDIRECT(\"V2\"):INDIRECT(\"V\"&(ROW()-1)))", "Expression"));
		
		sheetFileInfo.put(t+",47", new CustomCell("IF(ISERR(AVERAGE(AV1:AV"+t+")),\"\",AVERAGE(AV1:AV"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",48", new CustomCell("IF(ISERR(AVERAGE(AW1:AW"+t+")),\"\",AVERAGE(AW1:AW"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",49", new CustomCell("IF(ISERR(AVERAGE(AX1:AX"+t+")),\"\",AVERAGE(AX1:AX"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",50", new CustomCell("IF(ISERR(AVERAGE(AY1:AY"+t+")),\"\",AVERAGE(AY1:AY"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",51", new CustomCell("IF(ISERR(AVERAGE(AZ1:AZ"+t+")),\"\",AVERAGE(AZ1:AZ"+t+"))", "Expression","0.#%"));
		
		sheetFileInfo.put(t+",10", new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),1)<>\"\",INDEX(AV:AZ,ROW(),1)<>0),TEXT(INDEX(AV:AZ,ROW(),1)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",11", new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),2)<>\"\",INDEX(AV:AZ,ROW(),2)<>0),TEXT(INDEX(AV:AZ,ROW(),2)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",12", new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),3)<>\"\",INDEX(AV:AZ,ROW(),3)<>0),TEXT(INDEX(AV:AZ,ROW(),3)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",13", new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),4)<>\"\",INDEX(AV:AZ,ROW(),4)<>0),TEXT(INDEX(AV:AZ,ROW(),4)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",14", new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),5)<>\"\",INDEX(AV:AZ,ROW(),5)<>0),TEXT(INDEX(AV:AZ,ROW(),5)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",15", new CustomCell("IF(AND(SUM(AV"+(t+1)+":AZ"+(t+1)+")<>\"\",SUM(AV"+(t+1)+":AZ"+(t+1)+")<>0),TEXT(SUM(AV"+(t+1)+":AZ"+(t+1)+")/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		
		
		sheetFileInfo.put(t+",16", new CustomCell("IF(ISERR(AVERAGE(Q2:Q"+t+")),\"\",AVERAGE(Q2:Q"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",17", new CustomCell("IF(ISERR(AVERAGE(R2:R"+t+")),\"\",AVERAGE(R2:R"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",18", new CustomCell("IF(ISERR(AVERAGE(S2:S"+t+")),\"\",AVERAGE(S2:S"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",19", new CustomCell("IF(ISERR(AVERAGE(T2:T"+t+")),\"\",AVERAGE(T2:T"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",20", new CustomCell("IF(ISERR(AVERAGE(U2:U"+t+")),\"\",AVERAGE(U2:U"+t+"))", "Expression","0.#%"));
		
		fillInfo.put(sheetNames+"FileInfo", sheetFileInfo);
		fillInfo.put(sheetNames+"MergeCell", sheetMergeCell);
	}
	
	private void ProcessDaHuoJinDu(Map<String, Object> fillInfo, Map<String, Object> sheetFileInfo) {
		int t = 0;
		Long totalTime = 0L;
		Integer lastId =0 ;
		Boolean isNew =false;
		for(LazyDynaMap map : beans){
			String wf_step = map.get("wf_step").toString();
			if(!((Integer) map.get("id")).equals(lastId)){
				lastId = (Integer) map.get("id");
				totalTime =0L;
				t++;
				isNew=true;
			}
			Long real_time = 0L;
			if(map.get("wf_real_finish")!=null){
				real_time = Math.abs(BizUtil.getWorkTimeBetween((Timestamp) map.get("wf_real_finish"), (Timestamp) map.get("wf_real_start")));
			}
			Long plan_time = (Long) map.get("wf_step_duration");
			if(real_time!=null){
				totalTime+=real_time;
			}
			switch (wf_step) {
			case "c_create_dahuo_1":
				if(isNew){
					sheetFileInfo.put(t+",34","");
					sheetFileInfo.put(t+",33,Double",map.get("unqualified_total")==null?"":map.get("unqualified_total").toString());
					sheetFileInfo.put(t+",32,Double",map.get("qualified_total")==null?"":map.get("qualified_total").toString());
					sheetFileInfo.put(t+",31,Double",map.get("sewing_total")==null?"":map.get("sewing_total").toString());
					Integer orderNum = getOrdreNum(map.get("num_info")==null?"":(String)map.get("num_info"));
					sheetFileInfo.put(t+",30,Double",orderNum);
					sheetFileInfo.put(t+",12",map.get("sewing_factory")==null?"":map.get("sewing_factory").toString());
					sheetFileInfo.put(t+",4",map.get("value")==null?"":map.get("value").toString());
					sheetFileInfo.put(t+",3",map.get("style_class")==null?"":map.get("style_class").toString());
					sheetFileInfo.put(t+",2",map.get("cus_name")==null?"":map.get("cus_name").toString());
					sheetFileInfo.put(t+",1",map.get("sell_order_code")==null?"":map.get("sell_order_code").toString());
					sheetFileInfo.put(t+",0",new CustomCell("IF(ISNUMBER(INDIRECT(\"A\"&ROW()-1)),INDIRECT(\"A\"&ROW()-1)+1,1)", "Expression"));
					sheetFileInfo.put(t+",58",new CustomCell("SUM(AY"+(t+1)+":BF"+(t+1)+")", "Expression"));
					isNew =false;
				}
				break;
			case "c_mr_improve_2":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "operator", 5, 22, 13);
				}
				break;
			case "c_ppc_assign_3":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "operator", 6, 23, 14);
				}
				break;
			case "c_fi_pay_4":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "worker", 7, 24, 15);
				}
				break;
			case "c_ppc_factoryMsg_5":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "worker", 8, 25, 16);
				}
				break;
			case "c_qc_cutting_6":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "worker", 9,26,17);
				}
				break;
			case "c_ppc_confirm_7":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "worker", 10, 27, 18);
				}
				break;
			case "c_qc_printing_8":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo, map, t, "worker", 11, 28, 19);
				}
				break;
			case "c_ppc_confirm_9":
				if(real_time!=null){
					getColorIndex(real_time, plan_time, sheetFileInfo,  map, t, "sewing_factory", 12, 29, 20);
				}
				break;
			default:
				break;
			}
			
			Long except_time = 0L;
			except_time = Math.abs(BizUtil.getWorkTimeBetween((Timestamp) map.get("except_finish"), (Timestamp) map.get("begin_time")));
			Short color;
			if(except_time!=null){
				if(totalTime==0l){
					color = IndexedColors.WHITE.index;
				}else if(except_time>totalTime){
					color = IndexedColors.GREEN.index;
				}else if((totalTime-except_time)<=14400000L){
					color = IndexedColors.YELLOW.index;
				}else{
					color = IndexedColors.RED.index;
				}
				sheetFileInfo.put(t+",21", new CustomCell("IF(AND(BG"+(t+1)+"<>\"\",BG"+(t+1)+"<>0),TEXT(BG"+(t+1)+"/86400,\"[h]小时mm分\"),\"\")", "Expression").setCellColor(color));
			}
		}
		String baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseReportExcelFile"));
		String sheetNames = "订单进度跟踪报表-大货";
		fillInfo.put("fileUrl", baseExcelFile);
		fillInfo.put("sheetNames", sheetNames);
		Map<String,Object> sheetMergeCell = new HashMap<String,Object>();
		
		sheetMergeCell.put(++t+","+t+",0,11", true);
		sheetFileInfo.put(t+",12", "平均值：");
		sheetFileInfo.put(t+",30",new CustomCell("\"总数:\"&SUM(INDIRECT(\"AE2\"):INDIRECT(\"AE\"&(ROW()-1)))", "Expression"));
		sheetFileInfo.put(t+",31",new CustomCell("\"总数:\"&SUM(INDIRECT(\"AF2\"):INDIRECT(\"AF\"&(ROW()-1)))", "Expression"));
		sheetFileInfo.put(t+",32",new CustomCell("\"总数:\"&SUM(INDIRECT(\"AG2\"):INDIRECT(\"AG\"&(ROW()-1)))", "Expression"));
		sheetFileInfo.put(t+",33",new CustomCell("\"总数:\"&SUM(INDIRECT(\"AH2\"):INDIRECT(\"AH\"&(ROW()-1)))", "Expression"));
		sheetFileInfo.put(t+",34",new CustomCell("\"总数:\"&SUM(INDIRECT(\"AI2\"):INDIRECT(\"AI\"&(ROW()-1)))", "Expression"));
		
		sheetFileInfo.put(t+",50", new CustomCell("IF(ISERR(AVERAGE(AY1:AY"+t+")),\"\",AVERAGE(AY1:AY"+t+"))", "Expression"));
		sheetFileInfo.put(t+",51", new CustomCell("IF(ISERR(AVERAGE(AZ1:AZ"+t+")),\"\",AVERAGE(AZ1:AZ"+t+"))", "Expression"));
		sheetFileInfo.put(t+",52", new CustomCell("IF(ISERR(AVERAGE(BA1:BA"+t+")),\"\",AVERAGE(BA1:BA"+t+"))", "Expression"));
		sheetFileInfo.put(t+",53", new CustomCell("IF(ISERR(AVERAGE(BB1:BB"+t+")),\"\",AVERAGE(BB1:BB"+t+"))", "Expression"));
		sheetFileInfo.put(t+",54", new CustomCell("IF(ISERR(AVERAGE(BC1:BC"+t+")),\"\",AVERAGE(BC1:BC"+t+"))", "Expression"));
		sheetFileInfo.put(t+",55", new CustomCell("IF(ISERR(AVERAGE(BD1:BD"+t+")),\"\",AVERAGE(BD1:BD"+t+"))", "Expression"));
		sheetFileInfo.put(t+",56", new CustomCell("IF(ISERR(AVERAGE(BE1:BE"+t+")),\"\",AVERAGE(BE1:BE"+t+"))", "Expression"));
		sheetFileInfo.put(t+",57", new CustomCell("IF(ISERR(AVERAGE(BF1:BF"+t+")),\"\",AVERAGE(BF1:BF"+t+"))", "Expression"));
		
		sheetFileInfo.put(t+",13", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),1)<>\"\",INDEX(AY:BF,ROW(),1)<>0),TEXT(INDEX(AY:BF,ROW(),1)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",14", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),2)<>\"\",INDEX(AY:BF,ROW(),2)<>0),TEXT(INDEX(AY:BF,ROW(),2)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",15", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),3)<>\"\",INDEX(AY:BF,ROW(),3)<>0),TEXT(INDEX(AY:BF,ROW(),3)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",16", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),4)<>\"\",INDEX(AY:BF,ROW(),4)<>0),TEXT(INDEX(AY:BF,ROW(),4)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",17", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),5)<>\"\",INDEX(AY:BF,ROW(),5)<>0),TEXT(INDEX(AY:BF,ROW(),5)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",18", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),6)<>\"\",INDEX(AY:BF,ROW(),6)<>0),TEXT(INDEX(AY:BF,ROW(),6)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",19", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),7)<>\"\",INDEX(AY:BF,ROW(),7)<>0),TEXT(INDEX(AY:BF,ROW(),7)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",20", new CustomCell("IF(AND(INDEX(AY:BF,ROW(),8)<>\"\",INDEX(AY:BF,ROW(),8)<>0),TEXT(INDEX(AY:BF,ROW(),8)/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		sheetFileInfo.put(t+",21", new CustomCell("IF(AND(SUM(AY"+(t+1)+":BF"+(t+1)+")<>\"\",SUM(AY"+(t+1)+":BF"+(t+1)+")<>0),TEXT(SUM(AY"+(t+1)+":BF"+(t+1)+")/86400,\"[h]小时mm分\"),\"\")", "Expression"));
		
		
		sheetFileInfo.put(t+",22", new CustomCell("IF(ISERR(AVERAGE(W2:W"+t+")),\"\",AVERAGE(W2:W"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",23", new CustomCell("IF(ISERR(AVERAGE(X2:X"+t+")),\"\",AVERAGE(X2:X"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",24", new CustomCell("IF(ISERR(AVERAGE(Y2:Y"+t+")),\"\",AVERAGE(Y2:Y"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",25", new CustomCell("IF(ISERR(AVERAGE(Z2:Z"+t+")),\"\",AVERAGE(Z2:Z"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",26", new CustomCell("IF(ISERR(AVERAGE(AA2:AA"+t+")),\"\",AVERAGE(AA2:AA"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",27", new CustomCell("IF(ISERR(AVERAGE(AB2:AB"+t+")),\"\",AVERAGE(AB2:AB"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",28", new CustomCell("IF(ISERR(AVERAGE(AC2:AC"+t+")),\"\",AVERAGE(AC2:AC"+t+"))", "Expression","0.#%"));
		sheetFileInfo.put(t+",29", new CustomCell("IF(ISERR(AVERAGE(AD2:AD"+t+")),\"\",AVERAGE(AD2:AD"+t+"))", "Expression","0.#%"));
		
		fillInfo.put(sheetNames+"FileInfo", sheetFileInfo);
		fillInfo.put(sheetNames+"MergeCell", sheetMergeCell);
	}
	private static Short getColorIndex2(Long real_time,Long plan_time,Map<String,Object> sheetFileInfo,LazyDynaMap map,Integer index,String operator,Integer opratorIndex,Integer dataIndex,Integer timeIndex){
		String op ="";
		if(map.get(operator)!=null){
			op = (String) map.get(operator);
		}else{
			if("worker".equals(operator)){
				if(StringUtils.isNotBlank(map.get("operator").toString())){
					op = (String) map.get("operator"); 
				}
			}else if("operator".equals(operator)){
				if(StringUtils.isNotBlank(map.get("worker").toString())){
					op = (String) map.get("worker"); 
				}
			}
		}
		sheetFileInfo.put(index+","+opratorIndex, "".equals(op)?new CustomCell():op);
		sheetFileInfo.put(index+","+dataIndex, new CustomCell("IF(AND(INDIRECT(\"AZ\"&ROW())<>\"\",INDIRECT(\"AZ\"&ROW())<>0),ROUND(INDEX(AV:AZ,ROW(),"+(dataIndex-15)+")/BA"+(index+1)+",3),\"\")", "Expression","0.#%"));
		
		Short color ;
		if(plan_time>real_time){
			color = IndexedColors.GREEN.index;
		}else if((real_time-plan_time)<=14400000L){
			color = IndexedColors.YELLOW.index;
		}else{
			color = IndexedColors.RED.index;
		}
		if(real_time>0){
			if(timeIndex>10){
				sheetFileInfo.put(index+","+(timeIndex+37),new CustomCell("IF(AND(INDIRECT(ADDRESS(ROW(),COLUMN()-1))<>\"\",INDIRECT(ADDRESS(ROW(),COLUMN()-1))<>0),"+real_time/1000+",\"\")", "Expression","0"));
			}else{
				sheetFileInfo.put(index+","+(timeIndex+37)+",Integer",real_time/1000);
			}
		}else if(real_time ==0){
			sheetFileInfo.put(index+","+(timeIndex+37),"");
		}
		sheetFileInfo.put(index+","+timeIndex, new CustomCell("IF(AND(INDEX(AV:AZ,ROW(),"+(timeIndex-9)+")<>\"\",INDEX(AV:AZ,ROW(),"+(timeIndex-9)+")<>0),TEXT(INDEX(AV:AZ,ROW(),"+(timeIndex-9)+")/86400,\"[h]小时mm分\"),\"\")", "Expression").setCellColor(color));
		return color;
	}
	
	
	private static Short getColorIndex(Long real_time,Long plan_time,Map<String,Object> sheetFileInfo,LazyDynaMap map,Integer index,String operator,Integer opratorIndex,Integer dataIndex,Integer timeIndex){
		String op ="";
		if(map.get(operator)!=null){
			op = (String) map.get(operator);
		}else{
			if("worker".equals(operator)){
				if(StringUtils.isNotBlank(map.get("operator").toString())){
					op = (String) map.get("operator"); 
				}
			}else if("operator".equals(operator)){
				if(StringUtils.isNotBlank(map.get("worker").toString())){
					op = (String) map.get("worker"); 
				}
			}
		}
		sheetFileInfo.put(index+","+opratorIndex, "".equals(op)?new CustomCell():op);
		sheetFileInfo.put(index+","+dataIndex, new CustomCell("IF(AND(INDIRECT(\"BF\"&ROW())<>\"\",INDIRECT(\"BF\"&ROW())<>0),ROUND(INDEX(AY:BG,ROW(),"+(dataIndex-21)+")/BG"+(index+1)+",3),\"\")", "Expression","0.#%"));
		Short color ;
		if(plan_time>real_time){
			color = IndexedColors.GREEN.index;
		}else if((real_time-plan_time)<=14400000L){
			color = IndexedColors.YELLOW.index;
		}else{
			color = IndexedColors.RED.index;
		}
		
		if(real_time>0){
			if(timeIndex>13){
				sheetFileInfo.put(index+","+(timeIndex+37),new CustomCell("IF(AND(INDIRECT(ADDRESS(ROW(),COLUMN()-1))<>\"\",INDIRECT(ADDRESS(ROW(),COLUMN()-1))<>0),"+real_time/1000+",\"\")", "Expression","0"));
			}else{
				sheetFileInfo.put(index+","+(timeIndex+37)+",Integer",real_time/1000);
			}
		} else if(real_time ==0){
			sheetFileInfo.put(index+","+(timeIndex+37),"");
		}
		sheetFileInfo.put(index+","+timeIndex, new CustomCell("IF(AND(INDEX(AY:BF,ROW(),"+(timeIndex-12)+")<>\"\",INDEX(AY:BF,ROW(),"+(timeIndex-12)+")<>0),TEXT(INDEX(AY:BF,ROW(),"+(timeIndex-12)+")/86400,\"[h]小时mm分\"),\"\")", "Expression","0").setCellColor(color));
		return color;
	}
}
