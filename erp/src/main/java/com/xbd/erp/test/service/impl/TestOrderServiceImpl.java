package com.xbd.erp.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.test.dao.TestOaOrderDao;
import com.xbd.erp.test.service.TestOrderService;
import com.xbd.oa.vo.OaOrder;

@Service("oaOrderService")
public class TestOrderServiceImpl extends BaseServiceImpl<OaOrder> implements TestOrderService {
	
	private TestOaOrderDao oaOrderDao;
	
	public TestOaOrderDao getOaOrderDao() {
		return oaOrderDao;
	}
	@Resource(name="oaOrderDao")
	public void setOaOrderDao(TestOaOrderDao oaOrderDao) {
		super.setBaseHibernateDao(oaOrderDao);
	}

	public List<OaOrder> getAllOaOrder() {
		return this.oaOrderDao.getAll();
	}

}
