package com.xbd.erp.category.action;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.use.base.FSPBean;

import com.xbd.oa.action.BaseAction;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderDetail;
@Results({ @Result(name = "page4list", type = "redirect", location = "category/list") })
public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = 1302383805516363265L;

	public static final Logger logger = Logger.getLogger(CategoryAction.class);

	/**
	 * 品类对象
	 */
	private OaCategory oaCategory;

	/**
	 * 
	 * @Title: list
	 * @Description: TODO查询品类列表
	 *
	 * @author lanyu
	 * @return
	 */
	// page4list
	public String list() {
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_BY_SQL);
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		beans = manager.getObjectsBySql(fsp);
		fsp.setRecordCount(getObjectsCountSql(fsp));
		return "category/page4list";
	}

	/**
	 * 
	 * @Title: page4add
	 * @Description: TODO添加品类表单
	 *
	 * @author lanyu
	 * @return
	 */
	// page4add
	public String page4add() {
		return "category/page4add";
	}

	/**
	 * 
	 * @Title: add
	 * @Description: TODO添加品类
	 *
	 * @author lanyu
	 * @return
	 */
	public String add() {
		if (null != oaCategory) {
			manager.saveObject(oaCategory);
		}
		return "page4list";
	}

	/**
	 * 
	 * @Title: page4detail
	 * @Description: TODO品类详情获取
	 *
	 * @author 张华
	 * @return
	 */
	public String page4detail() {
		oaCategory = findOaCategory(oaCategory);
		return "category/page4detail";
	}

	/**
	 * 
	 * @Title: page4edit
	 * @Description: TODO编辑品类跳转
	 *
	 * @author 张华
	 * @return
	 */
	public String page4edit() {
		oaCategory = findOaCategory(oaCategory);
		return "category/page4edit";
	}

	/**
	 * 
	 * @Title: edit
	 * @Description: TODO编辑品类保存
	 *
	 * @author 张华
	 * @return
	 */
	public String edit() {
		if (null != oaCategory) {
			manager.saveObject(oaCategory);
		}
		return "page4list";
	}

	/**
	 * 
	 * @Title: getOaCategory
	 * @Description: TODO获取品类数据
	 *
	 * @author 张华
	 */
	private OaCategory findOaCategory(OaCategory oaCategory) {
		if (null != oaCategory && null != oaCategory.getId()) {
			oaCategory = (OaCategory) manager.getObject(OaCategory.class, oaCategory.getId());
		}
		return oaCategory;
	}

	/**
	 * 
	 * @Title: hour2ms
	 * @Description: TODO把品类中涉及时间的字段以小时转换为毫秒，用于存储到数据库
	 *
	 * @author 张华
	 * @param oaCategory
	 * @return
	 */
	private OaCategory hour2ms(OaCategory oaCategory) {
		try {
			if (null != oaCategory.getDahuoCyc() && oaCategory.getDahuoCyc() > 0) {
				oaCategory.setDahuoCyc(oaCategory.getDahuoCyc() * 60 * 60 * 1000);
			}
			if (null != oaCategory.getDabanCyc() && oaCategory.getDabanCyc() > 0) {
				oaCategory.setDabanCyc(oaCategory.getDabanCyc() * 60 * 60 * 1000);
			}

			if (null != oaCategory.getEmbroidery() && oaCategory.getEmbroidery() > 0) {
				oaCategory.setEmbroidery(oaCategory.getEmbroidery() * 60 * 60 * 1000);
			} else {
				oaCategory.setEmbroidery(0l);
			}
			if (null != oaCategory.getWashwaterTime() && oaCategory.getWashwaterTime() > 0) {
				oaCategory.setWashwaterTime(oaCategory.getWashwaterTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setWashwaterTime(0l);
			}
			if (null != oaCategory.getPrintingTime() && oaCategory.getPrintingTime() > 0) {
				oaCategory.setPrintingTime(oaCategory.getPrintingTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setPrintingTime(0l);
			}

			if (null != oaCategory.getFoldingTime() && oaCategory.getFoldingTime() > 0) {
				oaCategory.setFoldingTime(oaCategory.getFoldingTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setFoldingTime(0l);
			}
			if (null != oaCategory.getDalanTime() && oaCategory.getDalanTime() > 0) {
				oaCategory.setDalanTime(oaCategory.getDalanTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setDalanTime(0l);
			}
			if (null != oaCategory.getBeadsTime() && oaCategory.getBeadsTime() > 0) {
				oaCategory.setBeadsTime(oaCategory.getBeadsTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setBeadsTime(0l);
			}

			if (null != oaCategory.getSellWait() && oaCategory.getSellWait() > 0) {
				oaCategory.setSellWait(oaCategory.getSellWait() * 60 * 60 * 1000);
			} else {
				oaCategory.setSellWait(0l);
			}
			if (null != oaCategory.getPaymentWait() && oaCategory.getPaymentWait() > 0) {
				oaCategory.setPaymentWait(oaCategory.getPaymentWait() * 60 * 60 * 1000);
			} else {
				oaCategory.setPaymentWait(0l);
			}
			if (null != oaCategory.getOtherTime() && oaCategory.getOtherTime() > 0) {
				oaCategory.setOtherTime(oaCategory.getOtherTime() * 60 * 60 * 1000);
			} else {
				oaCategory.setOtherTime(0l);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return oaCategory;
	}

	/**
	 * 
	 * @Title: ms2hour
	 * @Description: TODO把品类中涉及时间的字段以毫秒转换为小时，用于显示到前台
	 *
	 * @author 张华
	 * @param oaCategory
	 * @return
	 */
	private OaCategory ms2hour(OaCategory oaCategory) {
		try {
			if (null != oaCategory.getDahuoCyc() && oaCategory.getDahuoCyc() > 0) {
				oaCategory.setDahuoCyc(oaCategory.getDahuoCyc() / 60 / 60 / 1000);
			}
			if (null != oaCategory.getDabanCyc() && oaCategory.getDabanCyc() > 0) {
				oaCategory.setDabanCyc(oaCategory.getDabanCyc() / 60 / 60 / 1000);
			}

			if (null != oaCategory.getEmbroidery() && oaCategory.getEmbroidery() > 0) {
				oaCategory.setEmbroidery(oaCategory.getEmbroidery() / 60 / 60 / 1000);
			} else {
				oaCategory.setEmbroidery(0l);
			}
			if (null != oaCategory.getWashwaterTime() && oaCategory.getWashwaterTime() > 0) {
				oaCategory.setWashwaterTime(oaCategory.getWashwaterTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setWashwaterTime(0l);
			}
			if (null != oaCategory.getPrintingTime() && oaCategory.getPrintingTime() > 0) {
				oaCategory.setPrintingTime(oaCategory.getPrintingTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setPrintingTime(0l);
			}

			if (null != oaCategory.getFoldingTime() && oaCategory.getFoldingTime() > 0) {
				oaCategory.setFoldingTime(oaCategory.getFoldingTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setFoldingTime(0l);
			}
			if (null != oaCategory.getDalanTime() && oaCategory.getDalanTime() > 0) {
				oaCategory.setDalanTime(oaCategory.getDalanTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setDalanTime(0l);
			}
			if (null != oaCategory.getBeadsTime() && oaCategory.getBeadsTime() > 0) {
				oaCategory.setBeadsTime(oaCategory.getBeadsTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setBeadsTime(0l);
			}

			if (null != oaCategory.getSellWait() && oaCategory.getSellWait() > 0) {
				oaCategory.setSellWait(oaCategory.getSellWait() / 60 / 60 / 1000);
			} else {
				oaCategory.setSellWait(0l);
			}
			if (null != oaCategory.getPaymentWait() && oaCategory.getPaymentWait() > 0) {
				oaCategory.setPaymentWait(oaCategory.getPaymentWait() / 60 / 60 / 1000);
			} else {
				oaCategory.setPaymentWait(0l);
			}
			if (null != oaCategory.getOtherTime() && oaCategory.getOtherTime() > 0) {
				oaCategory.setOtherTime(oaCategory.getOtherTime() / 60 / 60 / 1000);
			} else {
				oaCategory.setOtherTime(0l);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return oaCategory;
	}
	
	public String updateHisDate(){
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_BY_SQL);
		beans = manager.getObjectsBySql(fsp);
		for (int i = 0; i < beans.size(); i++) {
			OaOrder oaOrder = (OaOrder)manager.getObject(OaOrder.class, Integer.parseInt(beans.get(i).get("id").toString()));
			if(beans.get(i).get("style_class") != null && !"".equals(beans.get(i).get("style_class").toString())){
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_ALL_BY_SQL);
				fsp.set("style_class", beans.get(i).get("style_class").toString());
				superList = manager.getObjectsBySql(fsp);
				if(superList.size() > 0){
					
					oaOrder.setSellReadyTime(Long.parseLong(superList.get(0).get("sell_wait").toString())); //销售准备时间
					if(oaOrder.getType().equals("2")){ //标准缓冲时间
						oaOrder.setStandardTime(Long.parseLong(superList.get(0).get("daban_cyc").toString()));
					}else{
						oaOrder.setStandardTime(Long.parseLong(superList.get(0).get("dahuo_cyc").toString()));
					}
					//特殊工艺时间
					Long craftTime = (superList.get(0).get("embroidery") != null ? Long.parseLong(superList.get(0).get("embroidery").toString()) : 0) 
									+(superList.get(0).get("washwater_time") != null ? Long.parseLong(superList.get(0).get("washwater_time").toString()) : 0)
									+(superList.get(0).get("printing_time") != null ? Long.parseLong(superList.get(0).get("printing_time").toString()) : 0)
									+(superList.get(0).get("folding_time") != null ? Long.parseLong(superList.get(0).get("folding_time").toString()) : 0)
									+(superList.get(0).get("dalan_time") != null ? Long.parseLong(superList.get(0).get("dalan_time").toString()) : 0)
									+(superList.get(0).get("beads_time") != null ? Long.parseLong(superList.get(0).get("beads_time").toString()) : 0)
									+(superList.get(0).get("other_time") != null ? Long.parseLong(superList.get(0).get("other_time").toString()) : 0);
					oaOrder.setCraftTime(craftTime);
					
					//货期
					if(oaOrder.getPreVersionDate() != null){
						Date preVersion = (Date)oaOrder.getPreVersionDate();
						
						Calendar c = Calendar.getInstance();  
			            c.setTime(preVersion);  
			            c.add(c.DAY_OF_YEAR, oaOrder.getPreproductDays());
			            Date temp_date = c.getTime(); 
			            oaOrder.setGoodsTime(new Timestamp(temp_date.getTime()));
					}else{
						oaOrder.setGoodsTime(oaOrder.getExceptFinish());
					}
					
				}
			}
			
			manager.saveObject(oaOrder);
		}
		return null;
	}
	
	
	public String updateHisDate2(){
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_ORDER_BY_SQL);
		beans = manager.getObjectsBySql(fsp);
		
		for (int i = 0; i < beans.size(); i++) {
			Map<Integer, String> stepMap_01 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_02 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_03 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_04 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_05 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_06 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_07 = new HashMap<Integer, String>();
			Map<Integer, String> stepMap_08 = new HashMap<Integer, String>();
			
			OaOrder oaOrder = (OaOrder)manager.getObject(OaOrder.class, Integer.parseInt(beans.get(i).get("id").toString()));
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_OA_ORDER_DETAIL_ORDER_BY_SQL);
			fsp.set("oa_order", beans.get(i).get("id").toString());
			superList = manager.getObjectsBySql(fsp);
			for(int j = 0; j < superList.size(); j++){
				OaOrderDetail  oaOrderDetail = (OaOrderDetail)manager.getObject(OaOrderDetail.class, Integer.parseInt(superList.get(j).get("id").toString()));
				if(superList.get(j).get("wf_step").equals("b_mr_improve_2")){
					stepMap_01.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				if(superList.get(j).get("wf_step").equals("c_mr_improve_2")){
					stepMap_01.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("b_ppc_confirm_3")){
					stepMap_02.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				if(superList.get(j).get("wf_step").equals("c_ppc_assign_3")){
					stepMap_02.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("b_pur_confirm_4")){
					stepMap_03.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				if(superList.get(j).get("wf_step").equals("c_fi_pay_4")){
					stepMap_03.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("b_ppc_confirm_5")){
					stepMap_04.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				if(superList.get(j).get("wf_step").equals("c_ppc_factoryMsg_5")){
					stepMap_04.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("b_qc_confirm_6")){
					stepMap_05.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				if(superList.get(j).get("wf_step").equals("c_qc_cutting_6")){
					stepMap_05.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
			
				if(superList.get(j).get("wf_step").equals("c_ppc_confirm_7")){
					stepMap_06.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("c_qc_printing_8")){
					stepMap_07.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
				if(superList.get(j).get("wf_step").equals("c_ppc_confirm_9")){
					stepMap_08.put((Integer)superList.get(j).get("id"), superList.get(j).get("wf_step").toString());
				}
				
			}
			
			if(stepMap_01.size() > 1){
				updateOaderDetail(stepMap_01);
			}
			
			if(stepMap_02.size() > 1){
				updateOaderDetail(stepMap_02);
			}
			
			if(stepMap_03.size() > 1){
				updateOaderDetail(stepMap_03);
			}
			
			if(stepMap_04.size() > 1){
				updateOaderDetail(stepMap_04);
			}
			
			if(stepMap_05.size() > 1){
				updateOaderDetail(stepMap_05);
			}
			
			if(stepMap_06.size() > 1){
				updateOaderDetail(stepMap_06);
			}
			
			if(stepMap_07.size() > 1){
				updateOaderDetail(stepMap_07);
			}
			
			if(stepMap_08.size() > 1){
				updateOaderDetail(stepMap_08);
			}
			
		}
		return null;
	}
	
	
	public void updateOaderDetail(Map<Integer, String> stepMap){
		int [] k = new int[stepMap.size()];
		int t = 0;
		for (Integer key : stepMap.keySet()) {
		    k[t] = key;
		    t++;
		}
		Arrays.sort(k);
		for (int i = 0; i < k.length-1; i++) {
			OaOrderDetail  oaOrderDetail = (OaOrderDetail)manager.getObject(OaOrderDetail.class, k[i]);
			oaOrderDetail.setBackFlag("1");
			manager.saveObject(oaOrderDetail);
		}
	}

	public OaCategory getOaCategory() {
		return oaCategory;
	}

	public void setOaCategory(OaCategory oaCategory) {
		this.oaCategory = oaCategory;
	}
}
