package com.xbd.erp.test.service;

import java.util.List;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaOrder;

public interface TestOrderService extends BaseService<OaOrder> {
	public List<OaOrder> getAllOaOrder();
}
