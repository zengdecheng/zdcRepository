package com.xbd.erp.test.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.impl.BaseDaoImpl;
import com.xbd.erp.test.dao.TestOaOrderDao;
import com.xbd.oa.vo.OaOrder;

@Repository("oaOrderDao")
public class TestOaOrderDaoImpl extends BaseDaoImpl<OaOrder> implements TestOaOrderDao{

}
