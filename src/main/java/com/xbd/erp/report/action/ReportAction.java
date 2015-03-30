package com.xbd.erp.report.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.LazyDynaBean;

import com.xbd.erp.base.action.Action;
import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.utils.Struts2Utils;

public class ReportAction extends Action {

	private static final long serialVersionUID = 1L;
	private BaseDao<?> baseDao;
	public BaseDao<?> getBaseDao() {
		return baseDao;
	}
	@Resource(name="baseDaoImpl")
	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}


    /**
     * 今日日报报表
     * @return
     * @author 范蠡
     */
    public String toDayReport(){
        Map<String, String> mapCount = new HashMap<String, String>();
        //今日报表总数表
        getToDayReportAleays(mapCount);
        Struts2Utils.getRequest().setAttribute("mapCount", mapCount);
        return "report/toDay";
    }

    public void getToDayReport_DaHuo(Map<String, String> mapCount){
        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_TODAY_REPORT_ORDERDETAIL_BY_SQL);
        fsp.set("type", "3");
        fsp.setStaticSqlPart(" and oa_order in ("+mapCount.get("daHuoIds")+")");
        beans = baseDao.getObjectsBySql(fsp);

        int mrCount = 0;

        for (int i = 0; i < beans.size(); i++){
            switch (beans.get(i).get("wf_step").toString()){
                case "c_mr_improve_2":  //MR节点
                    mrCount++;
                    if(beans.get(i).get("wf_real_start") != null){

                    }
                    break;
            }
        }

    }


    public void getToDayReportAleays(Map<String, String> mapCount){
        fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_TODAY_REPORT_BY_SQL);
        beans = baseDao.getObjectsBySql(fsp);
        int orderNums = 0;
        for(LazyDynaBean lazy : beans){
            if(lazy.get("type").toString().equals("2")){
                //打版订单数量
                mapCount.put("daban", lazy.get("num").toString());
                //打版订单Ids
                mapCount.put("daBanIds", lazy.get("ids").toString());
                //打版预计数量
                mapCount.put("daBan_want_cnt", lazy.get("want_cnt") != null ? lazy.get("want_cnt").toString() : "");
                orderNums += Integer.parseInt(lazy.get("num").toString());
            }else if(lazy.get("type").toString().equals("3")){
                //大货订单数量
                mapCount.put("dahuo", lazy.get("num").toString());
                //大货订单Ids
                mapCount.put("daHuoIds", lazy.get("ids").toString());
                //大货预计数量
                mapCount.put("daHuo_want_cnt", lazy.get("want_cnt") != null ? lazy.get("want_cnt").toString() : "");
                orderNums += Integer.parseInt(lazy.get("num").toString());
            }
        }
        //订单总数
        mapCount.put("orderNums", orderNums+"");
    }

	public String hello(){
		System.out.println("aaaaaaaaaaaaaa");
		return "hello";
	}
	
	public String world(){
		System.out.println("aaaaaaaaaaaaaa");
		return "tabpage/tabCQC";
	}
	
	public String execute(){
		System.out.println("aaaaaaaaaaaaaa");
		return "hello";
	}
}
