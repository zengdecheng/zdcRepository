package com.xbd.erp.util.service.impl;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.util.dao.OrderUtilDao;
import com.xbd.erp.util.service.OrderUtilService;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.vo.OaTracke;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderUtilService")
public class OrderUtilServiceImpl extends BaseServiceImpl<Object> implements OrderUtilService {
	private OrderUtilDao orderUtilDao;

    public OrderUtilDao getOrderUtilDao() {
        return orderUtilDao;
    }

    @Resource(name = "orderUtilDao")
    public void setOrderUtilDao(OrderUtilDao orderUtilDao) {
//        super.setBaseHibernateDao(orderUtilDao);
        this.orderUtilDao = orderUtilDao;
    }

    // update by 范蠡 2015-4-17
    public void getTracke(int orderId, String node, Map resMap) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("oaOrder", orderId);
        List<OaTracke> trackeList = (List<OaTracke>) this.orderUtilDao.findLikeByProperty(OaTracke.class, params);
        List<Map> oaTrackeList = new ArrayList<Map>();
        for (OaTracke tracke : trackeList) {
//            OaTracke tracke = (OaTracke)object;
            Map mapOaTrackes = new HashMap();
            mapOaTrackes.put("id", tracke.getId());
            mapOaTrackes.put("memo", tracke.getMemo());
            mapOaTrackes.put("node", tracke.getNode());
            mapOaTrackes.put("user", tracke.getUser());
            mapOaTrackes.put("oaOrder", tracke.getOaOrder());
            String tempTime = DateUtil.formatDate(tracke.getTime());
            mapOaTrackes.put("time", tempTime);
            oaTrackeList.add(mapOaTrackes);
        }
        resMap.put("oaTrackes", oaTrackeList);
    }


}
