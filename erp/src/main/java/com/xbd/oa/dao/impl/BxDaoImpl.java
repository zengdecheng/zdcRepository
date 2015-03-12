package com.xbd.oa.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;

import org.use.base.FSPBean;
import org.use.base.dao.impl.DaoHibernate;

import com.xbd.oa.dao.BaseDao;
import com.xbd.oa.vo.OaClothesSizeDetail;
@SuppressWarnings("serial") 
public class BxDaoImpl extends DaoHibernate implements BaseDao {
	public static final String GET_SYSITEMCATS = "getSysItemcats";
	/**
	 * 基础面辅料属性表
	 */
	public static final String LIST_SYS_MATTER_PROP_BY_EQL = "listSysMatterPropByEql";
	public static final String LIST_OA_STAFF = "list_oa_staff";
	public static final String LIST_ORDER_SQL = "list_order_sql";
	public static final String GET_OA_MATERIAL_LIST = "get_oa_material_list";
	public static final String GET_OA_CUS_MATERIAL_LIST = "get_oa_cus_material_list";
	
	public static final String LIST_ORDERWF_BY_SQL = "list_orderwf_by_sql";
	
	
	public static final String GET_ORDER_LIST = "get_order_list";
	public static final String GET_DETAIL_EXCEL = "get_detail_excel";
	
	public static final String LIST_ORDERWF_HIS_BY_SQL = "list_orderwf_his_by_sql";
	public static final String LIST_ORDERWF_HIS_YH_BY_SQL = "list_orderwf_his_yh_by_sql";
	public static final String LIST_ORDER_HIS_DETAIL_BY_SQL = "list_order_his_detail_by_sql";

	public static final String GET_OA_ORG = "get_oa_org";
	public static final String GET_OA_STAFF = "get_oa_staff";
	public static final String LOGIN = "login";
	public static final String PROCESSORDER = "processorder";
	public static final String GET_OA_STAFF_PWD="get_oa_staff_pwd";

    /**
     * 订单进度报表
     */
	public static final String GET_ORDER_LIST_JINDU_INFO="get_order_list_jindu_info";

	/**
	 * 组织机构列表
	 */
	public static final String List_OA_ORG_BY_EQL = "list_oa_org_by_eql";
	/**
	 * 人员列表
	 */
	public static final String LIST_OA_STAFF_BY_EQL = "list_oa_staff_by_eql";
	public static final Object LIST_OA_STAFF_BY_SQL = "list_oa_staff_by_sql";
	/**
	 * 时间基准列表
	 */
	public static final String LIST_OA_TIMEBASE_ENTRY_BY_SQL = "list_oa_timebase_entry_by_sql";
	public static final String LIST_OA_TIMEBASE_BY_SQL = "list_oa_timebase_by_sql";
	/**
	 * 数据字典列表
	 */
	public static final String LIST_OA_DT_BY_SQL = "list_oa_dt_by_sql";
	public static final String LIST_OA_DT_BY_CODE_SQL = "list_oa_dt_by_code_sql";
	public static final String GET_OA_ORDER = "get_oa_order";
	public static final String LIST_OA_TIMEBASE_BY_EQL = "list_oa_timebase_by_eql";
	public static final String LIST_OA_TIMEBASE_ENTRY_BY_EQL = "list_oa_timebase_entry_by_eql";
	public static final String LIST_OA_ORDER_DETAIL_BY_SQL = "list_oa_order_detail_by_sql";
	public static final String LIST_OA_DT_ENTRY_BY_EQL = "list_oa_dt_entry_by_eql";
	public static final String GET_MAX_INX_OA_DT_BY_SQL = "get_max_inx_oa_dt_by_sql";

	/*
	 * 关联订单
	 */
	public static final String LIST_OA_ASSORDER_BY_SQL = "list_oa_assorder_by_sql";
	public static final String LIST_OA_SALES_BY_SQL = "list_oa_sales_by_sql";
	
	public static final String GET_OA_ORG_BY_SQL = "get_oa_org_by_sql";
	
	public static final String GET_ORDER_LIST_EXCEL_INFO = "get_order_list_excel_info";
	
	/**
	 * 提醒任务
	 */
	public static final String GET_OA_ORDER_DETAIL_BY_SQL ="get_oa_order_detail_by_sql";
	//邮件信息
	public static final String GET_OA_ORDER_DETAIL_FOR_MAIL = "get_oa_order_detail_for_mail";
	//是否部门主管
	public static final String IF_MANAGER = "if_manager";
	//下属的全部带完成任务
	public static final String LIST_ORGWF_BY_SQL = "list_orgwf_by_sql";
	
	
	/**
	 * 大货订单时间表
	 */
	public static final String LIST_OA_ORDER_DHTC_BY_SQL = "list_oa_order_dhtc_by_sql";//款号，mr提交，财务确认尾款，QA出货
	public static final String LIST_OA_ORDER_DHTC_BY_SQL2 = "list_oa_order_dhtc_by_sql2";//常遇春
	public static final String LIST_OA_ORDER_DETAIL_FI_DHTC_BY_SQL = "list_oa_order_detail_fi_dhtc_by_sql";//财务提交
	public static final String LIST_OA_ORDER_DETAIL_IT_DHTC_BY_SQL = "list_oa_order_detail_it_dhtc_by_sql";// 技术部
	public static final String LIST_OA_ORDER_DETAIL_PUR_DHTC_BY_SQL = "list_oa_order_detail_pur_dhtc_by_sql";//采购部
	public static final String LIST_OA_ORDER_DETAIL_CQC_DHTC_BY_SQL = "list_oa_order_detail_cqc_dhtc_by_sql";//CQC
	public static final String LIST_OA_ORDER_DETAIL_QC_DHTC_BY_SQL = "list_oa_order_detail_qc_dhtc_by_sql"; //MQC
	public static final String LIST_OA_ORDER_DETAIL_QA_DHTC_BY_SQL = "list_oa_order_detail_qa_dhtc_by_sql"; //qa提交
	public static final String LIST_OA_ORDER_DETAIL_FI2_DHTC_BY_SQL = "list_oa_order_detail_fi2_dhtc_by_sql";//fi确认尾款
	public static final String LIST_OA_ORDER_DETAIL_QA2_DHTC_BY_SQL = "list_oa_order_detail_qa2_dhtc_by_sql";//qa发货
	public static final String LIST_OA_ORDER_DETAIL_ALL_DHTC_BY_SQL = "list_oa_order_detail_all_dhtc_by_sql";//某个订单的某个流程的所有步骤包括退回
	
	public static final String LIST_OA_ORDER_DEPARTMENTTIME_BY_SQL = "list_oa_order_departmenttime_by_sql";
	/**
	 * 通过款号查找订单
	 */
	public static final String GET_ORDER_FROM_STYLE_CODE_BY_SQL="get_order_from_style_code_by_sql";
	
	/**
	 * 通过订单id查看订单下一流程所需时间 
	 */
	public static final String GET_OA_ORDER_DETAIL_TIME_FOR_NOTIFY_STAFF_BY_SQL = "get_oa_order_detail_time_for_notify_staff_by_sql";

	/**
	 * lanyu
	 * 统计订单的及时率
	 */
	public static final String LIST_TIMELYRATECOUNT_BY_SQL="list_timelyRateCount_by_sql";
	//查询各部门当前订单数量的统计
	public static final String LIST_STEP_ORDER_COUNT_BY_SQL="list_step_order_count_by_sql";
	//各部门的提交时间
	public static final String LIST_OA_ORDER_DETAIL_DAHUOTIMELYRATE_BY_SQL = "list_oa_order_detail_dahuotimelyrate_by_sql";//财务提交
	//查找今天新增的订单
	public static final String LIST_NEW_ORDER_ADD_BY_SQL="list_new_order_add_by_sql";
	//查询样衣打版今天的订单
	public static final String LIST_OA_ORDER_DBTC_BY_SQL="list_oa_order_dbtc_by_sql";
	
	/**
	 * 获取剩余时间不足3小时流程 及 超时流程，通知操作人
	 */
	public static final String GET_OA_ORDER_DETAIL_FOR_AUTONOTIFY_BY_SQL = "get_oa_order_detail_for_autonotify_by_sql";
	
	/**
	 * 查询流程详情
	 */
	public static final String GET_OA_ORDER_DETAIL_BY_EQL = "get_oa_order_detail_by_eql";
	
	/**
	 * 查询部门主管手机号
	 */
	public static final String GET_OA_ORG_LEADER_LINKPHONE_BY_SQL = "get_oa_org_leader_linkphone_by_sql";

	/**
	 * 查询用料搭配明细
	 */
	public static final String GET_MATERIAL_LIST_BY_EQL = "get_material_list_by_eql";

	/**
	 * 查询客供料明细
	 */
	public static final String GET_CUS_MATERIAL_LIST_BY_EQL = "get_cus_material_list_by_eql";
	/**
	 * 查找尺码表表头
	 */
	public static final String LIST_CLOTHES_SIZE_BY_EQL="list_clothes_size_by_eql";
	/**
	 *查询尺码表内容的表头 
	 */
	public static final String GET_ORDER_NUM_TITLE_BY_EQL="get_order_num_title_by_eql";
	/**
	 * 查询尺码表中各个属性的值
	 */
	public static final String GET_CLOTHES_SIZE_DETAIL_BY_SQL="get_clothes_size_detail_by_sql";
	/**
	 * 查询加工说明
	 */
	public static final String GET_PROCESS_EXPLAIN_BY_EQL="get_process_explain_by_eql";
	/**
	 * 查询异动跟踪信息
	 */
	public static final String GET_TRACKE_BY_EQL="get_tracke_by_eql";
	/**
	 * 查询打版的用料说明清单
	 */
	public static final String LIST_DA_BAN_INFO_BY_SQL="list_da_ban_info_by_sql";
	/**
	 * 查询大货的用料说明清单
	 */
	public static final String LIST_DA_HUO_INFO_BY_SQL="list_da_huo_info_by_sql";
	/**
	 * 查询面辅料采购清单大货
	 */
	public static final String GET_DA_HUO_MATERIAL_PURCHASE_DESC_BY_SQL="get_da_huo_material_purchase_desc_by_sql";
	/**
	 * 查询面辅料采购清单打版
	 */
	public static final String GET_DA_BAN_MATERIAL_PURCHASE_DESC_BY_SQL="get_da_ban_material_purchase_desc_by_sql";
	/***
	 *查询关联订单
	 */
	public static final String LIST_RELATION_ORDER_BY_SQL="list_relation_order_by_sql";
	/**
	 * 查询管理信息
	 */
	public static final String GET_MANAGER_INFO_BY_SQL="get_manager_info_by_sql";
	
	/**
	 * 查询物料成本
	 */
	public static final String LIST_MATERIAL_COST_BY_SQL="list_material_cost_by_sql";
	/**
	 * 查询外协成本与加工成本
	 */
	public static final String GET_COST_BY_EQL="get_cost_by_eql";
	/**
	 * 查询mr确认信息
	 */
	public static final String GET_MR_CONFIRM_BY_EQL="get_mr_confirm_by_eql";

	/**
	 *物料申购
	 */
	public  static final  String GET_MATERIALS_BUY = "get_materials_buy";
	public  static final  String GET_MATERIALS_BUY2 = "get_materials_buy2";
	public  static final  String GET_CQC_BY_SQL = "get_cqc_by_sql";
	public  static final  String GET_QITAO_BY_SQL = "get_qitao_by_sql";
	public  static final  String GET_QITAODETAIL_BY_SQL = "get_qitaodetail_by_sql";
	public  static final  String GET_DETAIL_WORKER = "get_detail_worker";

	/**
	 * 查询物流信息
	 */
	public  static final  String GET_OALOGISTICS_BY_SQL = "get_oalogistics_by_sql";
	public  static final  String GET_OAQA_BY_SQL = "get_oaqa_by_sql";

	
	// 查询CQC节点信息
	public  static final  String GET_CQC_INFO_BY_EQL = "get_cqc_info_by_eql";
	public  static final  String GET_QITAO_INFO_BY_EQL = "get_qitao_info_by_eql";
	// 查询QA节点信息
	public  static final  String GET_QA_INFO= "get_qa_info";
	
	/**
	 * 获取CQC节点的裁减数量信息
	 */
	public static final String GET_SHEAR_NUM_BY_SQL = "get_shear_num_by_sql";

	/**
	 * 获取TPE车缝数量信息
	 */
	public static final String GET_SEWING_NUM_INFO_BY_EQL = "get_sewing_num_info_by_eql";

	/**
	 * 查看订单是否退回
	 */
	public static final String GET_COUNT_DETAIL="get_count_detail";
	/**
	 * 查询每个节点的详细时间
	 */
	public static final String LIST_ORDER_DETAIL_TIME_BY_SQL="list_order_detail_time_by_sql";
	/**
	 * 获取要发送微信的客户
	 */
	public static final String GET_SEND_WECHAT_CUS_BY_SQL_STRING = "get_send_wechat_cus_by_sql_string";
	
	public static void sessionFlush() {
		DaoHibernate.getSession().clear();
		DaoHibernate.getSession().evict(OaClothesSizeDetail.class);
		DaoHibernate.getSession().flush();
	}
	@Override
	public String parseFspBean2Eql(FSPBean fsp,List params) {
		throw new RuntimeException("this is base dao ,not call parseFspBean2Eql method");
	}
	
	@Override
	public String parseFspBean2Sql(FSPBean fsp,List params) {
		String caseSql = " and oa_org in ( " 
	    + " SELECT a.id FROM  oa_org a LEFT JOIN oa_org b ON a.pid =b.id  " 
	    + " left join oa_org c on b.pid=c.id  " 
	    + " left join oa_org d on c.pid=d.id " 
	    + " where 1=1              " 
	    + " AND(a.id=? or b.id=? or c.id=? or d.id=?) " 
	    + " )";
		
		String oaOrg = fsp.get("oaOrg").toString();
		if(null != params){
			params.add(oaOrg);
			params.add(oaOrg);
			params.add(oaOrg);
			params.add(oaOrg);
		}
		return caseSql;
	}
	
	
	@Override
	public EntityManager getEntityManager() {
		return null;
	}
	@Override
	public String getTableName() {
		return "oa_staff";
	}
	@Override
	public String getVoName() {
		return "Bx";
	}
	@Override
	public Class getVoClass(){
		return null;
	}
}