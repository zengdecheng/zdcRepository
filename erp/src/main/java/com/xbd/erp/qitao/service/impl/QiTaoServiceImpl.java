package com.xbd.erp.qitao.service.impl;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.qitao.dao.QiTaoDao;
import com.xbd.erp.qitao.service.QiTaoService;
import com.xbd.oa.vo.OaQiTao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("qiTaoService")
public class QiTaoServiceImpl extends BaseServiceImpl<OaQiTao> implements QiTaoService {
	
	private QiTaoDao qiTaoDao;

	public QiTaoDao getQiTaoDao() {
		return qiTaoDao;
	}
    @Resource(name="qiTaoDao")
	public void setQiTaoDao(QiTaoDao qiTaoDao) {
		super.setBaseHibernateDao(qiTaoDao);
	}


    public List<OaQiTao> getAllQiTao(int orderId, Map resMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("oaOrderId", orderId);
        List<OaQiTao> qiTaoList = (List<OaQiTao>) this.qiTaoDao.findListByProperty(OaQiTao.class, params);

        return null;
    }

//	public List<OaOrder> getAllOaOrder() {
//		return this.oaOrderDao.getAll();
//	}

}
