package com.xbd.oa.dao.impl;
import java.util.List;

import javax.persistence.EntityManager;

import org.use.base.FSPBean;
import org.use.base.dao.impl.DaoHibernate;

import com.xbd.oa.dao.BaseDao;
@SuppressWarnings("serial") 
public class StDaoImpl extends DaoHibernate implements BaseDao {
	
	/**
	 * 统计订单及时率(获取本周所有订单，未完成+本周已完成）
	 */
	public static final String STATISTICS_ORDER_TIMERATE_BY_SQL_1 = "statistics_order_timerate_by_sql_1";
	public static final String STATISTICS_ORDER_TIMERATE_BY_SQL_2 = "statistics_order_timerate_by_sql_2";
	
	/**
	 * 统计节点及时率
	 */
	public static final String STATISTICS_NODE_TIMERATE_BY_SQL = "statistics_node_timerate_by_sql";
	
	/**
	 * 统计员工及时率
	 */
	public static final String STATISTICS_STAFF_TIMERATE_BY_SQL = "statistics_staff_timerate_by_sql";
	
	/**
	 * 统计当前超时节点
	 */
	public static final String STATISTICS_TIMEOUT_ORDER_BY_SQL = "statistics_timeout_order_by_sql";
	
	/**
	 * 获取本周节点及时率
	 */
	public static final String GET_NODE_TIMERATE_THIS_WEEK_BY_SQL = "get_node_timerate_this_week_by_sql";
	
	/**
	 * 获取流程操作人
	 */
	public static final String GET_OPERATOR_BY_SQL =  "get_operator_by_sql";
	
	/**
	 * 员工及时率
	 */
	public static final String GET_STAFF_TIMERATE_BY_SQL = "get_staff_timerate_by_sql";
	
	/**
	 * 某一节点及时率详情
	 */
	public static final String GET_NODE_TIMERATE_DETAIL_BY_SQL = "get_node_timerate_detail_by_sql";
	
	/**
	 * 获取订单详情
	 */
	public static final String GET_OA_ORDER_DETAIL_BY_SQL = "get_oa_order_detail_by_sql";
	public static final String GET_OA_ORDER_DETAIL_NOW_BY_SQL = "get_oa_order_detail_now_by_sql";
	
	/**
	 * 用户登录
	 */
	public static final String list_oa_staff = "list_oa_staff";
	
	/**
	 * 获取周及时率
	 */
	public static final String GET_OA_ORDER_WEEK_TIMERATE_BY_SQL = "get_oa_order_week_timerate_by_sql";
	public static final String GET_OA_ORDER_WEEK_TIMERATE_BY_EQL = "get_oa_order_week_timerate_by_eql";
	
	/**
	 * 获取员工 在订单中计划用时长 可能会有多个节点的时长
	 */
	public static final String GET_OA_ORDER_DETAIL_STEP_DURATION_BY_EQL = "get_oa_order_detail_step_duration_by_eql";
	
	@Override
	public String parseFspBean2Eql(FSPBean fsp,List params) {
		throw new RuntimeException("this is base dao ,not call parseFspBean2Eql method");
	}
	
	@Override
	public String parseFspBean2Sql(FSPBean fsp,List params) {
		return null;
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
		return "St";
	}
	@Override
	public Class getVoClass(){
		return null;
	}
}