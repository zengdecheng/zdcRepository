package com.xbd.erp.qitao.service;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaQiTao;

import java.util.List;
import java.util.Map;

public interface QiTaoService extends BaseService<OaQiTao> {
//	public List<OaQiTao> getAllOaOrder();

    public List<OaQiTao> getAllQiTao(int orderId, Map resMap);
}
