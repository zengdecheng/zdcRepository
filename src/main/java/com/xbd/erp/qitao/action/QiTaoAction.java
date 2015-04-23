package com.xbd.erp.qitao.action;

import com.alibaba.fastjson.JSON;
import com.xbd.erp.base.action.BaseAction;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.erp.base.utils.XBDUtils;
import com.xbd.erp.qitao.service.QiTaoService;
import com.xbd.erp.util.service.OrderUtilService;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.utils.Struts2Utils;
import com.xbd.oa.vo.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QiTaoAction extends BaseAction<OaQiTao>{

	private static final long serialVersionUID = 1L;
	
	public QiTaoService qiTaoService;

    public OrderUtilService orderUtilService;

    private String oaOrderId;

    public OrderUtilService getOrderUtilService() {
        return orderUtilService;
    }

    @Resource(name="orderUtilService")
    public void setOrderUtilService(OrderUtilService orderUtilService) {
        this.orderUtilService = orderUtilService;
    }

    public QiTaoService getQiTaoService() {
		return qiTaoService;
	}

	@Resource(name="qiTaoService")
	public void setQiTaoService(QiTaoService qiTaoService) {
		this.qiTaoService = qiTaoService;
	}
	
	//调用baseService自带方法
	//http://localhost:8080/test_order!test1.action
//	public void test1()throws Exception {
//		this.writeJson(this.oaOrderService.getAll());
//	}
//	//调用自身service方法
//	public void test2()throws Exception {
//		this.writeJson(this.oaOrderService.getAllOaOrder());
//	}


    /**
     *
     * @Title: jsonGetDaHuoQiTaoNode
     * @Description: 齐套节点
     *
     * @author 范蠡
     */
    public void getQiTaoInfo() {
        Map resMap = new HashMap();// 返回结果
        try {
            String orderIds = Struts2Utils.getParameter("oaOrderId");
            String wfStepIndex = Struts2Utils.getParameter("wfStepIndex");// 获取正在处理的订单节点index
            String node = "8"; // 当前节点的index，后面查询需要
            int orderId = Integer.valueOf(orderIds);
            OaOrder oaOrder = (OaOrder) qiTaoService.get(OaOrder.class, orderId);
            if (oaOrder != null) {
                resMap.put("oaOrder", oaOrder);
//                getOaOrderNum(oaOrder.getOaOrderNumId(), resMap);
//                // 获取物流信息
//                getOaLogistics(orderId, resMap);
//                getOaQaList(orderId, resMap);
//                // 3.查询管理信息
//                getManagerInfo(orderId, node, wfStepIndex, resMap);

                if(wfStepIndex != null && "8".equals(wfStepIndex)){
                    OaOrderDetail qiTaoDetail = qiTaoService.getOaOrderDetail(orderId, "c_dahuo_8");
                    resMap.put("oaQiTaoStep", JSON.parseObject(qiTaoDetail.getOaQiTao(), OaQiTaoStep.class));
                }
                XBDUtils.getManagerInfo(orderId, wfStepIndex, wfStepIndex, resMap);
                XBDUtils.getTracke(orderId, resMap);
            }
            resMap.put("code", 0);
            resMap.put("msg", "物流节点-信息查询成功");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            resMap.put("msg", "物流节点-信息查询出错");
        }

        Struts2Utils.writeJson(resMap);
    }


    @Override
    public String page4list() throws Exception {
        return null;
    }

    @Override
    public String page4add() throws Exception {
        return null;
    }

    @Override
    public String page4update() throws Exception {
        return null;
    }

    @Override
    public String add() throws Exception {
        return null;
    }

    @Override
    public String update() throws Exception {
        return null;
    }

    @Override
    public String delete() throws Exception {
        return null;
    }


    public String getOaOrderId() {
        return oaOrderId;
    }

    public void setOaOrderId(String oaOrderId) {
        this.oaOrderId = oaOrderId;
    }
}
