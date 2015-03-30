package com.xbd.erp.test.action;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.xbd.erp.base.action.BaseAction;
import com.xbd.erp.test.service.TestOrderService;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.vo.OaOrder;

public class TestOrderAction extends BaseAction<OaOrder>{

	private static final long serialVersionUID = 1L;
	
	public TestOrderService oaOrderService;
	
	public TestOrderService getOaOrderService() {
		return oaOrderService;
	}

	@Resource(name="oaOrderService")
	public void setOaOrderService(TestOrderService oaOrderService) {
		this.oaOrderService = oaOrderService;
	}
	
	//调用baseService自带方法
	//http://localhost:8080/test_order!test1.action
	public void test1()throws Exception {
		this.writeJson(this.oaOrderService.getAll());
	}
	//调用自身service方法
	public void test2()throws Exception {
		this.writeJson(this.oaOrderService.getAllOaOrder());
	}
	
	public String page4list() throws Exception {
		System.out.println(JSON.toJSONStringWithDateFormat(this.oaOrderService.getAllOaOrder(),DateUtil.ALL_24H));
		return null;
	}

	public String page4add() throws Exception {
		return null;
	}

	public String page4update() throws Exception {
		return null;
	}

	public String add() throws Exception {
		return null;
	}

	public String update() throws Exception {
		return null;
	}

	public String delete() throws Exception {
		return null;
	}
	
}
