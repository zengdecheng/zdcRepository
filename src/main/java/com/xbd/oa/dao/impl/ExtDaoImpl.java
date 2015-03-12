package com.xbd.oa.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.use.base.FSPBean;
import org.use.base.dao.impl.DaoHibernate;

import com.xbd.oa.dao.BaseDao;

@SuppressWarnings("serial")
public class ExtDaoImpl extends DaoHibernate implements BaseDao {
	/**
	 * 获取订单列表
	 */
	public static final String LIST_OA_ORDER_BY_SQL = "list_oa_order_by_sql";

	/**
	 * 获取订单详情
	 */
	public static final String LIST_OA_ORDER_DETAIL_BY_SQL = "list_oa_order_detail_by_sql";
	/**
	 * 根据crm的订单编号查询oa_order中是否有该订单
	 */
	public static final String GET_OA_ORDER_BY_SELL_ORDER_CODE="get_oa_order_by_sell_order_code";

	@Override
	public String parseFspBean2Eql(FSPBean fsp, List params) {
		throw new RuntimeException(
				"this is base dao ,not call parseFspBean2Eql method");
	}

	@Override
	public String parseFspBean2Sql(FSPBean fsp, List params) {
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
		return "Ext";
	}

	@Override
	public Class getVoClass() {
		return null;
	}
}