package com.xbd.erp.qitao.service.impl;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.qitao.dao.QiTaoDao;
import com.xbd.erp.qitao.service.QiTaoService;
import com.xbd.oa.vo.OaOrderDetail;
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
        this.qiTaoDao = qiTaoDao;
	}


    public List<OaQiTao> getAllQiTao(int orderId, Map resMap) {
        return null;
    }

    /**
     * @deprecated 齐套节点加载 获取信息
     * @param orderId
     * @param wfStep
     * @return
     */
    public OaOrderDetail getOaOrderDetail(int orderId, String wfStep) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("oaOrder", orderId);
        params.put("wfStep", wfStep);
        List<OaOrderDetail> qiTaoList  = (List<OaOrderDetail>) qiTaoDao.findListByProperty(OaOrderDetail.class, params);
        if (qiTaoList != null && qiTaoList.size() > 0) {
            return qiTaoList.get(0);
        }else{
            return null;
        }
    }

//	public List<OaOrder> getAllOaOrder() {
//		return this.oaOrderDao.getAll();
//	}

}
