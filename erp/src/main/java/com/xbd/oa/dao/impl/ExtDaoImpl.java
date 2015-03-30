package com.xbd.oa.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.impl.BaseDaoImpl;

@SuppressWarnings("all")
@Repository("extDaoImpl")
public class ExtDaoImpl extends BaseDaoImpl {
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
	public static final String GET_OA_ORDER_BY_SELL_ORDER_CODE = "get_oa_order_by_sell_order_code";
	/**
	 * cmr获取品类列表
	 */
	public static final String GET_CATEGORY_BY_SQL = "get_category_by_sql";


	@Override
	public String getVoName() {
		return "Ext";
	}
}