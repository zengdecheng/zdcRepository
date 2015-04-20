package com.xbd.erp.util.service;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaTracke;

import java.util.Map;

public interface OrderUtilService extends BaseService<Object> {

    public void getTracke(int orderId, String node, Map resMap) throws Exception;
}
