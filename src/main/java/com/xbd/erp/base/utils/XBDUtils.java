package com.xbd.erp.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xbd.erp.base.activiti.WorkFlow;
import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.utils.ConstantUtil;
import com.xbd.oa.utils.DateUtil;
import com.xbd.oa.utils.JdbcHelper;
import com.xbd.oa.utils.POIUtilsEx;
import com.xbd.oa.utils.PathUtil;
import com.xbd.oa.utils.ResourceUtil;
import com.xbd.oa.utils.Struts2Utils;
import com.xbd.oa.utils.WebUtil;
import com.xbd.oa.utils.WorkFlowUtil;
import com.xbd.oa.vo.OaClothesSize;
import com.xbd.oa.vo.OaClothesSizeDetail;
import com.xbd.oa.vo.OaCost;
import com.xbd.oa.vo.OaCqc;
import com.xbd.oa.vo.OaCusMaterialList;
import com.xbd.oa.vo.OaDaBanInfo;
import com.xbd.oa.vo.OaDaHuoInfo;
import com.xbd.oa.vo.OaLogistics;
import com.xbd.oa.vo.OaMaterialList;
import com.xbd.oa.vo.OaMrConfirm;
import com.xbd.oa.vo.OaOrder;
import com.xbd.oa.vo.OaOrderDetail;
import com.xbd.oa.vo.OaOrderNum;
import com.xbd.oa.vo.OaProcessExplain;
import com.xbd.oa.vo.OaQa;
import com.xbd.oa.vo.OaQiTao;
import com.xbd.oa.vo.OaQiTaoDetail;
import com.xbd.oa.vo.OaTpe;
import com.xbd.oa.vo.base.CustomCell;

@SuppressWarnings("deprecation")
public class XBDUtils {
	private static DecimalFormat decimalFormat = new DecimalFormat("0.##");
	private static DecimalFormat decimalFormat3 = new DecimalFormat("0.###");
	public static ApplicationContext context;
	protected static BaseDao<?> baseDao;
	static {
		if (baseDao == null) {
			context = new ClassPathXmlApplicationContext("spring.xml");
			baseDao = (BaseDao<?>) context.getBean("baseDaoImpl");
		}
	}
	
	/**
	 * 大货：
	 * 		新建大货订单		c_create_dahuo_1
	 * 		补充订单信息		c_mr_improve_2
	 * 		技术				c_ppc_assign_3
	 * 		缓冲				c_ppc_huanchong_4
	 * 		采购				c_fi_pay_5
	 * 		CQC				c_ppc_factoryMsg_6
	 * 		特殊工艺			c_art_7
	 * 		齐套				c_qitao_8
	 * 		车缝				c_qc_cutting_9
	 * 		尾查				c_ppc_confirm_10
	 * 		财务确认			c_qc_printing_11	
	 * 		发货				c_ppc_confirm_12
	 * 样衣
	 * 		新建订单			b_create_yangyi_1
	 * 		补充信息			b_mr_improve_2
	 *  	采购面料			b_ppc_confirm_3
	 *   	技术				b_pur_confirm_4
	 *    	特殊工艺			b_art_5
	 *     	技术				b_tech_6
	 *      成本核算			b_ppc_confirm_7	
	 *      mr确认			b_qc_confirm_8
	 */
	
	
	
	/**
	 * 启动第一个节点
	 * @param processInstanceByKey
	 * @param curUser
	 * @param oaOrder
	 */
	public static void startOrderWf(String processInstanceByKey, String curUser, OaOrder oaOrder) {
		Map<String,Object> variabes =new HashMap<String,Object>();
		variabes.put("oa_order", oaOrder.getId());
		WorkFlow.startProcess(processInstanceByKey, variabes).getId();
		
		// 开始节点和第一步是连起来做的，每个流程的第一个节点不扩展createHandle
//		Task task = WorkFlowUtil.getOnlyCurTaskByProcessInstanceId(processInstanceId);
//		task.setAssignee(curUser);
//		firstEditBizOrderAndDetail(processInstanceId, curUser, oaOrder, task);
//		WorkFlowUtil.completeTask(task.getId());
		/*
		 * //2.修改订单主表 //oaOrder.set
		 * 
		 * //3.存入第一步的订单细节表 OaOrderDetail oaOrderDetail = new OaOrderDetail(); oaOrderDetail.setOaOrder(oaOrder.getId()); oaOrderDetail.setOperator(owner); oaOrderDetail.setProcId(processId);
		 * //oaOrderDetail.set
		 * 
		 * //4.使工作流工作流走到下一个节点 WorkFlowUtil.completeTask(task.getId());
		 */
	}
	
	/**
	 * 核心处理方法
	 * @param orderId  订单ID
	 * @param processOrder	订单处理标识
	 */
	public static void processOrder(OaOrder dbOaOrder){
		// 查询该订单节点信息
		OaOrderDetail oaOrderDetail = (OaOrderDetail) baseDao.getObject(OaOrderDetail.class, dbOaOrder.getOaOrderDetail());
		// 样衣打版第六个流程节点（MR确认）不写Excel，排除在外
		if ("c_mr_improve_2".equals(dbOaOrder.getWfStep()) 
				|| "c_ppc_huanchong_4".equals(dbOaOrder.getWfStep()) 
				|| "c_fi_pay_5".equals(dbOaOrder.getWfStep())
				|| "b_mr_improve_2".equals(dbOaOrder.getWfStep())
				|| "b_ppc_confirm_3".equals(dbOaOrder.getWfStep())
				|| "b_pur_confirm_4".equals(dbOaOrder.getWfStep())
				|| "b_ppc_confirm_7".equals(dbOaOrder.getWfStep())
			) {
			oaOrderDetail.setAttachment(createExcelByOrderId(dbOaOrder.getId()));
			baseDao.saveObject(oaOrderDetail); // 保存附件
		}
		
		Map<String,Object> variables = new HashMap<String,Object>();
		if("c_ppc_factoryMsg_6".equals(dbOaOrder.getWfStep()) || "b_pur_confirm_4".equals(dbOaOrder.getWfStep()) ){
			//variables.put("oa_order_detail", oaOrderDetail.getId());
			if(StringUtils.isNotBlank(dbOaOrder.getStyleCraft())){
				variables.put("art", "true");
			}else{
				variables.put("art", "false");
			}
		}
		WorkFlow.nextUserTask(oaOrderDetail.getTaskId(), variables);
		
		
		if ("c_ppc_confirm_7".equals(dbOaOrder.getWfStep())) {// 如果是qa节点确认流转后 如果财务节点的支付方式3:7 或者 月结 是财务节点自动流转到物流节点
			if ("3:7".equals(dbOaOrder.getPayType()) || "月结".equals(dbOaOrder.getPayType()) || "月结30天".equals(dbOaOrder.getPayType())) {
				dbOaOrder = (OaOrder) baseDao.getObject(OaOrder.class, dbOaOrder.getId());
				oaOrderDetail = (OaOrderDetail) baseDao.getObject(OaOrderDetail.class, dbOaOrder.getOaOrderDetail());
				oaOrderDetail.setWorker("");
				WorkFlow.nextUserTask(oaOrderDetail.getTaskId());
			}
		}

		// 如果是打板的第四步 在流转完成后要向第五步中添加默认的三条数据
		if ("b_pur_confirm_4".equals(dbOaOrder.getWfStep())) {
			saveMaterialDefaultData(dbOaOrder.getId());
		}
		
	}
	
	/**
	 * 保存物料成本中默认的三条数据
	 * 
	 * @param orderId
	 * @author yunpeng
	 */
	private static void saveMaterialDefaultData(int orderId) {
		boolean fleg = true;
		// 查询是否已经退回，如果已经退回则不添加默认的三条数据
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_COUNT_DETAIL);
		fsp.set("orderId", orderId);
		LazyDynaMap map = baseDao.getOnlyObjectBySql(fsp);
		if (null != map.get("wf_step_num") && (Long) map.get("wf_step_num") >= 2) {
			fleg = false;
		}
		if (fleg) {
			// 保存第一个默认数据
			OaMaterialList oaMaterialList = new OaMaterialList();
			oaMaterialList.setOaOrderId(orderId);
			oaMaterialList.setMaterialName("唛头");
			oaMaterialList.setType("辅料");
			baseDao.saveObject(oaMaterialList);
			OaDaBanInfo oaDaBanInfo = new OaDaBanInfo();
			oaDaBanInfo.setOaMaterialList(oaMaterialList.getId());
			oaDaBanInfo.setUnitNum(2.00f);
			oaDaBanInfo.setCpPrice(0.05f);
			oaDaBanInfo.setCpTotalPrice(0.10f);
			baseDao.saveObject(oaDaBanInfo);

			// 保存第二个默认数据
			OaMaterialList oaMaterialList2 = new OaMaterialList();
			oaMaterialList2.setOaOrderId(orderId);
			oaMaterialList2.setMaterialName("包装物料");
			oaMaterialList2.setType("辅料");
			baseDao.saveObject(oaMaterialList2);
			OaDaBanInfo oaDaBanInfo2 = new OaDaBanInfo();
			oaDaBanInfo2.setOaMaterialList(oaMaterialList2.getId());
			oaDaBanInfo2.setUnitNum(1.00f);
			oaDaBanInfo2.setCpPrice(0.50f);
			oaDaBanInfo2.setCpTotalPrice(0.50f);
			baseDao.saveObject(oaDaBanInfo2);

			// 保存第三个默认数据
			OaMaterialList oaMaterialList3 = new OaMaterialList();
			oaMaterialList3.setOaOrderId(orderId);
			oaMaterialList3.setMaterialName("车缝线");
			oaMaterialList3.setType("辅料");
			baseDao.saveObject(oaMaterialList3);
			OaDaBanInfo oaDaBanInfo3 = new OaDaBanInfo();
			oaDaBanInfo3.setOaMaterialList(oaMaterialList3.getId());
			oaDaBanInfo3.setUnitNum(1.00f);
			oaDaBanInfo3.setCpPrice(0.30f);
			oaDaBanInfo3.setCpTotalPrice(0.30f);
			baseDao.saveObject(oaDaBanInfo3);
		}
	}
	
	/**
	 * 保存mr补充信息节点
	 * @param oaOrder
	 * @param dbOaOrder
	 * @param pageOaOrderDetail
	 * @param oaMaterialLists
	 * @param materialDelIds
	 * @param oaCusMaterialLists
	 * @param cusMaterialDelIds
	 * @return
	 */
	public static boolean saveMR(OaOrder oaOrder,OaOrder dbOaOrder,OaOrderDetail pageOaOrderDetail,List<OaMaterialList> oaMaterialLists,String materialDelIds,List<OaCusMaterialList> oaCusMaterialLists,String cusMaterialDelIds){
		try {
			dbOaOrder.setCity(oaOrder.getCity()); // 分公司
			dbOaOrder.setIsUrgent(oaOrder.getIsUrgent()); // 是否紧急
			dbOaOrder.setClothClass(oaOrder.getClothClass()); // 二级分类，品类
			dbOaOrder.setSampleSize(oaOrder.getSampleSize()); // 样板码数
			dbOaOrder.setSendtype(oaOrder.getSendtype()); // 发货方式
			dbOaOrder.setMemo(oaOrder.getMemo()); // Mr备注
			dbOaOrder.setTpeName(oaOrder.getTpeName()); // tpe，即qc
			dbOaOrder.setPreVersionDate(oaOrder.getPreVersionDate()); // 保存产前版日期
			dbOaOrder.setStyleCraft(oaOrder.getStyleCraft());// 保存生产工艺
			dbOaOrder.setStyleClass(oaOrder.getStyleClass());
			dbOaOrder.setCraftTime(oaOrder.getCraftTime());// 特殊工艺用时
			dbOaOrder.setFeedingTime(oaOrder.getFeedingTime());// 建议投料日期
			dbOaOrder.setStandardTime(oaOrder.getStandardTime());
			dbOaOrder.setSellReadyTime(oaOrder.getSellReadyTime());
			// 判断产前版日期，获得新的货期
			if (null != oaOrder.getPreVersionDate()) {
				if (null == oaOrder.getPreproductDays() || 0 >= oaOrder.getPreproductDays().intValue()) {
					dbOaOrder.setPreproductDays(1);
				}
				dbOaOrder.setGoodsTime(new Timestamp(dbOaOrder.getPreVersionDate().getTime() + dbOaOrder.getPreproductDays() * 24 * 60 * 60 * 1000));
			}
			// 关联订单时，保存被关联订单的ID、订单编号、类型 Add by ZQ 2014-12-22
			String relatedOrderCode = oaOrder.getRelatedOrderCode();
			if (StringUtils.isNotEmpty(relatedOrderCode)) {
				dbOaOrder.setRelatedOrderCode(oaOrder.getRelatedOrderCode());
				dbOaOrder.setRelatedOrderId(oaOrder.getRelatedOrderId());
				dbOaOrder.setRelatedOrderType(oaOrder.getRelatedOrderType());
			}
			baseDao.saveObject(dbOaOrder);
			String materIds = saveOaMaterialList(oaMaterialLists,dbOaOrder.getId(),materialDelIds); // 保存用料搭配信息
			saveOaCusMaterialList(oaCusMaterialLists,dbOaOrder.getId(),cusMaterialDelIds); // 保存客供料信息
			saveOaOrderDetail(pageOaOrderDetail,dbOaOrder.getOaOrderDetail(), dbOaOrder.getMemo());// 保存上传的附件和mr备注到detail
			/**
			 * MR选择关联订单时，后台保存操作调取复制该关联订单的采购、技术、核价等信息 Add by ZQ 2014-12-22
			 */
			// 选中关联订单的操作标志
			String isChoose = Struts2Utils.getParameter("isChoose");
			if (StringUtils.isNotEmpty(relatedOrderCode) && "choosed".equals(isChoose)) {
				Integer orderId = dbOaOrder.getId();// 订单ID
				Integer relatedOrderId = oaOrder.getRelatedOrderId();// 关联订单ID
				boolean isTypeSame = dbOaOrder.getType().equals(dbOaOrder.getRelatedOrderType());// 是否为同一类型
				String[] oldMaterIds = Struts2Utils.getRequest().getParameterValues("oldMaterIds");
				// 当保存了关联订单的用料时,才复制保存对应的清单
				if (null != oldMaterIds) {
					copyToExtraSavePurchase(orderId, relatedOrderId, dbOaOrder.getType(), oldMaterIds, materIds, isTypeSame);// 复制采购部分
				}

				// update by 张华 2015-01-16
				// 查询订单尺码和关联订单尺码是否一样，一样则直接复制关联订单技术节点尺寸表，不一样则不复制
				OaOrderNum oaOrderNum = (OaOrderNum) baseDao.getObject(OaOrderNum.class, dbOaOrder.getOaOrderNumId());
				OaOrder relatedOrder = (OaOrder) baseDao.getObject(OaOrder.class, relatedOrderId);
				OaOrderNum relatedOrderNum = (OaOrderNum) baseDao.getObject(OaOrderNum.class, relatedOrder.getOaOrderNumId());
				boolean ifCopySize = false;
				String copySize = "'";
				if (oaOrderNum.getTitle().equals(relatedOrderNum.getTitle())) {
					ifCopySize = true;
				} else {
					for (int i = 0; i < (oaOrderNum.getTitle().split("-").length - 1); i++) {
						copySize += "-";
					}
					copySize += "'";
				}
				copyToExtraSaveTechnology(orderId, relatedOrderId, ifCopySize, copySize);// 复制技术部分
				copyToExtraSaveCalculation(orderId, relatedOrderId);// 复制核价部分
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 保存缓冲信息
     * @param oaOrderDetail
     * @param oaOrder
	 * @return
	 */
	public static boolean saveHuanChong(OaOrderDetail oaOrderDetail,OaOrder oaOrder){
		// TODO: 根据业务需要编写
        try {
            saveManegeInfo(oaOrderDetail,oaOrder.getOaOrderDetail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	/**
	 * 保存技术节点信息
	 * @param pageOrder
	 * @param oaOrderDetail
	 * @param oaClothesSize
	 * @param oaProcessExplain
	 * @param oaDaHuoInfos
	 * @param oaDaBanInfos
	 * @param oaMaterialLists
	 * @param oaClothesSizeDetails
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @param clothesSizeDetailDelIds
	 * @return
	 */
	public static boolean saveTech(OaOrder dbOaOrder,OaOrderDetail oaOrderDetail,OaClothesSize oaClothesSize,OaProcessExplain oaProcessExplain,List<OaDaHuoInfo> oaDaHuoInfos,List<OaDaBanInfo> oaDaBanInfos,List<OaMaterialList> oaMaterialLists,List<OaClothesSizeDetail> oaClothesSizeDetails,String materialDelIds,String materialDescDelIds,String clothesSizeDetailDelIds){
		try {
			// 1.保存用料说明的部分信息
			saveMaterialInfo(dbOaOrder.getType(), dbOaOrder.getId(), oaDaHuoInfos, oaDaBanInfos, oaMaterialLists, materialDelIds, materialDescDelIds);;
			// 保存尺寸表表头
			saveClothesSize(dbOaOrder,oaClothesSize);
			// 2.保存尺寸表的详细信息
			saveClothesSizeDetail(dbOaOrder.getId(),clothesSizeDetailDelIds,oaClothesSizeDetails);
			// 3.保存加工说明信息
			saveProcessExplain(oaProcessExplain,dbOaOrder.getId());
			// 4.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrderDetail,dbOaOrder.getOaOrderDetail());

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 保存采购节点信息
	 * @param dbOaOrder
	 * @param oaOrderDetail
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @param oaMaterialLists
	 * @param oaDaHuoInfos
	 * @param oaDaBanInfos
	 * @return
	 */
	public static boolean savePur(OaOrder dbOaOrder,OaOrderDetail oaOrderDetail,String materialDelIds,String materialDescDelIds,List<OaMaterialList> oaMaterialLists,List<OaDaHuoInfo> oaDaHuoInfos,List<OaDaBanInfo> oaDaBanInfos){
		try {
			String type = dbOaOrder.getType();
			int orderId = dbOaOrder.getId();
			int orderDetailId = dbOaOrder.getOaOrderDetail();
			// 1.保存面料采购的详细信息
			saveMaterialDescInfo(type, orderId, materialDelIds, materialDescDelIds, oaMaterialLists, oaDaHuoInfos, oaDaBanInfos);
			// 2.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrderDetail,orderDetailId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 保存核价节点信息
	 * @param dbOaOrder
	 * @param oaOrderDetail
	 * @param oaCost
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @param oaMaterialLists
	 * @param oaDaBanInfos
	 * @return
	 */
	public static boolean saveCost(OaOrder dbOaOrder,OaOrderDetail oaOrderDetail,OaCost oaCost,String materialDelIds,String materialDescDelIds,List<OaMaterialList> oaMaterialLists,List<OaDaBanInfo> oaDaBanInfos){
		try {
			int orderId = dbOaOrder.getId();
			int oaOrderDetailId = dbOaOrder.getOaOrderDetail();
			// 1.保存物料成本信息
			saveMaterialCost(orderId, materialDelIds, materialDescDelIds, oaMaterialLists, oaDaBanInfos);
			// 2.保存成本及加工成本
			saveCost(orderId,oaCost);
			// 2.保存管理信息 需要保存技术备注和新上传的附件
			saveManegeInfo(oaOrderDetail,oaOrderDetailId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	} 
	
	/**
	 * 保存mr确认节点
	 * @param dbOaOrder
	 * @param oaMrConfirm
	 * @param oaOrderDetail
	 * @return
	 */
	public static boolean saveMRSure(OaOrder dbOaOrder,OaMrConfirm oaMrConfirm,OaOrderDetail oaOrderDetail){
		boolean flag = true;
		try {
			saveMrConfirm(dbOaOrder.getId(),oaMrConfirm); // 保存MR确认信息
			saveManegeInfo(oaOrderDetail,dbOaOrder.getOaOrderDetail());// 保存管理信息，保存MR备注和上传的附件
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 保存CQC节点信息
	 * @param oaOrderDetail_id
	 * @param oaOrderDetail
	 * @param oaCqcLists
	 * @param delQiTaoDetails
	 * @param oaQiTao
	 * @param oaDaHuoInfos
	 * @param oaQiTaoDetails
	 * @return
	 */
	public static boolean saveCQC(String oaOrderDetail_id,OaOrderDetail oaOrderDetail,List<OaCqc> oaCqcLists,String delQiTaoDetails,OaQiTao oaQiTao,List<OaDaHuoInfo> oaDaHuoInfos,List<OaQiTaoDetail> oaQiTaoDetails){
		boolean flag = true;
		try {
			if (oaCqcLists != null && oaCqcLists.size() > 0) {
				for (int i = 0; i < oaCqcLists.size(); i++) {
					baseDao.saveObject(oaCqcLists.get(i));
				}
			}
			delQiTaoDetail(delQiTaoDetails);
			saveDaHuo(oaDaHuoInfos);
			saveQiTao(oaQiTao,oaQiTaoDetails);
			saveOaOrderDetail(oaOrderDetail_id,oaOrderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 保存TPE节点信息
	 * @param oaOrder
	 * @param oaTpe
	 * @param oaOrderDetail
	 * @return
	 */
	public static boolean saveTPE(OaOrder oaOrder,OaTpe oaTpe,OaOrderDetail oaOrderDetail){
		try {
			if (null != oaTpe) {
				oaTpe.setOaOrderId(oaOrder.getId());
				baseDao.saveObject(oaTpe);
			}
			saveManegeInfo(oaOrderDetail,oaOrder.getOaOrderDetail());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 保存QA节点信息
	 * @param oaOrderDetail_id
	 * @param dbOaOrder
	 * @param oaQa
	 * @param oaOrderDetail
	 * @return
	 */
	public static boolean saveQA(String oaOrderDetail_id,OaOrder dbOaOrder,OaQa oaQa,OaOrderDetail oaOrderDetail){		
		boolean flag = true;
		try {
			if (oaQa != null) {
				baseDao.saveObject(oaQa);
			}
			saveOaOrderDetail(oaOrderDetail_id, oaOrderDetail);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	
	
	/**
	 * 保存物流节点信息
	 * @param oaOrderDetail_id
	 * @param logCheckBoxVal
	 * @param oaQa
	 * @param oaOrderDetail
	 * @return
	 */
	public static boolean saveLogistics(String oaOrderDetail_id,String logCheckBoxVal,OaOrderDetail oaOrderDetail){
		boolean bl = false;
		try {
			saveOaOrderDetail(oaOrderDetail_id, oaOrderDetail);
			delOaLogistics(logCheckBoxVal);
			bl = true;
		} catch (Exception e) {
			bl = false;
			e.printStackTrace();
		}
		return bl;
	}

	/**
	 * 保存财务节点信息
	 * @param oaOrderDetail
	 * @param oaOrder
	 * @return
	 */
	public static boolean saveFinance(OaOrderDetail oaOrderDetail,OaOrder oaOrder) {
		try {
			saveManegeInfo(oaOrderDetail,oaOrder.getOaOrderDetail());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除物流
	 * @param logCheckBoxVal
	 */
	private static void delOaLogistics(String logCheckBoxVal) {
		if (logCheckBoxVal != null && !"".equals(logCheckBoxVal)) {
			String str[] = logCheckBoxVal.split(",");
			for (String s : str) {
				OaLogistics qtd = (OaLogistics) baseDao.getObject(OaLogistics.class, Integer.parseInt(s.trim()));
				if (qtd != null) {
					baseDao.delObject(qtd);
				}
			}
		}
	}
	
	/**
	 * 在cqc结点保存orderDetail数据
	 * @param oaOrderDetail_id
	 * @param oaOrderDetail
	 * @throws Exception
	 */
	private static void saveOaOrderDetail(String oaOrderDetail_id,OaOrderDetail oaOrderDetail) throws Exception {
		if (oaOrderDetail != null && oaOrderDetail_id != null && !"".equals(oaOrderDetail_id)) {
			OaOrderDetail dbOdetail = (OaOrderDetail) baseDao.getObject(OaOrderDetail.class, Integer.parseInt(oaOrderDetail_id));
			if (dbOdetail != null) {
				dbOdetail.setContent(oaOrderDetail.getContent());
				dbOdetail.setOtherFile(oaOrderDetail.getOtherFile());
				dbOdetail.setWorker(oaOrderDetail.getWorker());
				dbOdetail.setWorkTime(oaOrderDetail.getWorkTime());
				baseDao.saveObject(dbOdetail);
			}
		}
	}
	
	/**
	 * 保存齐套信息
	 * @param oaQiTao
	 * @param oaQiTaoDetails
	 * @throws Exception
	 */
	private static void saveQiTao(OaQiTao oaQiTao,List<OaQiTaoDetail> oaQiTaoDetails) throws Exception {
		if (oaQiTao != null) {
			baseDao.saveObject(oaQiTao);
			if (oaQiTaoDetails != null && oaQiTaoDetails.size() > 0) {
				for (int i = 0; i < oaQiTaoDetails.size(); i++) {
					if (oaQiTaoDetails.get(i) != null) {
						oaQiTaoDetails.get(i).setOaQiTaoId(oaQiTao.getId());
						oaQiTaoDetails.get(i).setCreateTime(new Date());
						baseDao.saveObject(oaQiTaoDetails.get(i));
					}
				}
			}
		}
	}
	
	/**
	 * 修改大货信息
	 * @param oaDaHuoInfos
	 * @throws Exception
	 */
	private static void saveDaHuo(List<OaDaHuoInfo> oaDaHuoInfos) throws Exception {
		if (oaDaHuoInfos != null && oaDaHuoInfos.size() > 0) {
			for (int i = 0; i < oaDaHuoInfos.size(); i++) {
				if (oaDaHuoInfos.get(i).getId() != null && oaDaHuoInfos.get(i).getId() > 0) {
					OaDaHuoInfo dahuo = (OaDaHuoInfo) baseDao.getObject(OaDaHuoInfo.class, oaDaHuoInfos.get(i).getId()); // 获取原始数据
					if (dahuo != null) {
						if (oaDaHuoInfos.get(i).getUnitNum() != null) {
							dahuo.setUnitNum(oaDaHuoInfos.get(i).getUnitNum()); // 修改单间用量
						}
						if (oaDaHuoInfos.get(i).getOrg() != null) {
							dahuo.setOrg(oaDaHuoInfos.get(i).getOrg()); // 修改单件单位
						}
						if (oaDaHuoInfos.get(i).getNeedNum() != null) {
							dahuo.setNeedNum(oaDaHuoInfos.get(i).getNeedNum()); // 修改需求单位
						}
						baseDao.saveObject(dahuo);
					}
				}
			}
		}
	}
	
	/**
	 * 删除齐套detail数据
	 * @param delQiTaoDetails
	 * @throws Exception
	 */
	private static void delQiTaoDetail(String delQiTaoDetails) throws Exception {
		if (delQiTaoDetails != null && !"".equals(delQiTaoDetails)) {
			String str[] = delQiTaoDetails.split(",");
			for (String s : str) {
				OaQiTaoDetail qtd = (OaQiTaoDetail) baseDao.getObject(OaQiTaoDetail.class, Integer.parseInt(s.trim()));
				if (qtd != null) {
					baseDao.delObject(qtd);
				}
			}
		}
	}

	/**
	 * 保存MR确认信息
	 * @param oaOrderId
	 * @param oaMrConfirm
	 * @throws Exception
	 */
	private static void saveMrConfirm(int oaOrderId,OaMrConfirm oaMrConfirm) throws Exception {
		if (null != oaMrConfirm) {
			oaMrConfirm.setOaOrder(oaOrderId);
			baseDao.saveObject(oaMrConfirm);
		}
	}
	
	/**
	 * 保存外协成本以及加工成本
	 * @param orderId
	 */
	private static void saveCost(int orderId,OaCost oaCost) throws Exception {
		if (null != oaCost) {
			oaCost.setOaOrderId(orderId);
			baseDao.saveObject(oaCost);
		}
	}
	
	/**
	 * 保存物料成本表格的内容
	 * @param orderId
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @param oaMaterialLists
	 * @param oaDaBanInfos
	 * @throws Exception
	 */
	private static void saveMaterialCost(int orderId,String materialDelIds,String materialDescDelIds,List<OaMaterialList> oaMaterialLists,List<OaDaBanInfo> oaDaBanInfos) throws Exception {
		delOaMaterialList(materialDelIds);
		delOaDaBanInfo(materialDescDelIds);

		if (null != oaMaterialLists) {
			for (int i = 0; i < oaMaterialLists.size(); i++) {
				// 先保存面料搭配明细
				OaMaterialList ml = oaMaterialLists.get(i);
				// 判断只要其中一个属性存在值，即存储到数据库中
				boolean orderNumFlag = false;
				if (null != ml) {
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor())) {
						orderNumFlag = true;
					}
					if (orderNumFlag && null == ml.getId()) {
						ml.setOaOrderId(orderId);
						baseDao.saveObject(ml);
					}
					// 保存打版采购清单
					OaDaBanInfo odi = oaDaBanInfos.get(i);
					if (null != odi && null != odi.getId()) { // 说明已存在
						OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) baseDao.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
						// 设置新的数据到oaDaBanInfo
						oaDaBanInfo.setUnitNum(odi.getUnitNum());// 标准单件用量
						oaDaBanInfo.setCpPrice(odi.getCpPrice());// 大货单价
						oaDaBanInfo.setShearPrice(odi.getShearPrice());// 散剪单价
						oaDaBanInfo.setCpLoss(odi.getCpLoss());// 损耗率
						oaDaBanInfo.setCpTotalPrice(odi.getCpTotalPrice());// 大货金额
						oaDaBanInfo.setCpShearPrice(odi.getCpShearPrice());// 散剪金额
						oaDaBanInfo.setCpMemo(odi.getCpMemo());// 核价备注
						if (null == oaDaBanInfo || null == oaDaBanInfo.getId()) {
							oaDaBanInfo.setOaMaterialList(ml.getId()); // 用料表Id
						}
						baseDao.saveObject(oaDaBanInfo);
					} else { // 新数据，直接保存
						odi.setOaMaterialList(ml.getId());
						baseDao.saveObject(odi);
					}
				}
			}
		}
	}
	
	/**
	 * 保存面辅料采购清单
	 * @param type
	 * @param orderId
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @param oaMaterialLists
	 * @param oaDaHuoInfos
	 * @param oaDaBanInfos
	 * @throws Exception
	 */
	private static void saveMaterialDescInfo(String type, int orderId,String materialDelIds,String materialDescDelIds,List<OaMaterialList> oaMaterialLists,List<OaDaHuoInfo> oaDaHuoInfos,List<OaDaBanInfo> oaDaBanInfos) throws Exception {
		// 删除面辅料采购信息
		delOaMaterialList(materialDelIds);

		if (StringUtils.isNotBlank(type) && "3".equals(type)) {
			// 保存之前要先删除要删除的数据 删除大货清单信息
			delOaDaHuoInfo(materialDescDelIds);

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (null != ml) {
						if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || StringUtils.isNotBlank(ml.getSupplierName()) || StringUtils.isNotBlank(ml.getSupplierAddr()) || StringUtils.isNotBlank(ml.getSupplierTel()) || StringUtils.isNotBlank(ml.getPosition())) {
							orderNumFlag = true;
						}
						if (orderNumFlag) {
							// 保存大货采购清单
							OaDaHuoInfo odi = oaDaHuoInfos.get(i);
							if (null != ml && null != ml.getId()) {
								OaMaterialList oml = (OaMaterialList) baseDao.getObject(OaMaterialList.class, ml.getId());
								oml.setMaterialProp(ml.getMaterialProp());
								oml.setMaterialName(ml.getMaterialName());
								oml.setType(ml.getType());
								oml.setColor(ml.getColor());
								oml.setSupplierName(ml.getSupplierName());
								oml.setSupplierAddr(ml.getSupplierAddr());
								oml.setSupplierTel(ml.getSupplierTel());
								oml.setPosition(ml.getPosition());
								// 面料需要保存order_num
								oml.setOrderNum(ml.getOrderNum());
								baseDao.saveObject(oml);
							} else {
								ml.setOaOrderId(orderId);
								baseDao.saveObject(ml);
							}
							if (null != odi && null != odi.getId()) { // 说明已存在
								OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) baseDao.getObject(OaDaHuoInfo.class, odi.getId()); // 先查询
								// 设置新的数据到oaDaHuoInfo
								oaDaHuoInfo.setBuffon(odi.getBuffon()); // 布封
								oaDaHuoInfo.setUnitNum(odi.getUnitNum()); // 标准单件用量
								oaDaHuoInfo.setNeedNum(odi.getNeedNum());// 需求数量
								oaDaHuoInfo.setOrg(odi.getOrg());// 采购单位
								oaDaHuoInfo.setNum(odi.getNum());// 采购数量
								oaDaHuoInfo.setPrice(odi.getPrice());// 单价
								oaDaHuoInfo.setTotalPrice(odi.getTotalPrice());// 总金额
								oaDaHuoInfo.setTestPrice(odi.getTestPrice());// 验布费用
								oaDaHuoInfo.setFreight(odi.getFreight());// 运费
								oaDaHuoInfo.setTotal(odi.getTotal());// 总计
								oaDaHuoInfo.setBuyerLoss(odi.getBuyerLoss());// 采购损
								oaDaHuoInfo.setPaperTube(odi.getPaperTube());// 纸筒
								oaDaHuoInfo.setDeviation(odi.getDeviation());// 空差
								oaDaHuoInfo.setPurMemo(odi.getPurMemo());// 备注
								// oaDaHuoInfo.setPosition(odi.getPosition()); // 位置
								if (null == oaDaHuoInfo || null == oaDaHuoInfo.getId()) {
									oaDaHuoInfo.setOaMaterialList(ml.getId()); // 用料表Id
								}
								baseDao.saveObject(oaDaHuoInfo);
							} else { // 新数据，直接保存
								odi.setOaMaterialList(ml.getId());
								baseDao.saveObject(odi);
							}
						}
					}
				}
			}
		} else if (StringUtils.isNotBlank(type) && "2".equals(type)) {
			// 保存之前要先删除要删除的数据 删除打版清单信息
			delOaDaBanInfo(materialDescDelIds);
			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || StringUtils.isNotBlank(ml.getSupplierName()) || StringUtils.isNotBlank(ml.getSupplierAddr()) || StringUtils.isNotBlank(ml.getSupplierTel()) || StringUtils.isNotBlank(ml.getPosition())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) baseDao.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							oml.setSupplierName(ml.getSupplierName());
							oml.setSupplierAddr(ml.getSupplierAddr());
							oml.setSupplierTel(ml.getSupplierTel());
							oml.setPosition(ml.getPosition());
							baseDao.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							baseDao.saveObject(ml);
						}
						// 保存大货采购清单
						OaDaBanInfo odi = oaDaBanInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) baseDao.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaBanInfo
							oaDaBanInfo.setWeight(odi.getWeight());// 克重
							oaDaBanInfo.setComponent(odi.getComponent());// 成分
							oaDaBanInfo.setDeliveryTime(odi.getDeliveryTime());// 货期
							oaDaBanInfo.setBuyerLoss(odi.getBuyerLoss());// 采购损
							oaDaBanInfo.setPaperTube(odi.getPaperTube());// 纸筒
							oaDaBanInfo.setDeviation(odi.getDeviation());// 空差
							oaDaBanInfo.setShearPrice(odi.getShearPrice());// 散剪单价
							oaDaBanInfo.setUnit(odi.getUnit());// 单位
							oaDaBanInfo.setGoodsUnit(odi.getGoodsUnit());// 大货单位
							oaDaBanInfo.setGoodsPrice(odi.getGoodsPrice());// 大货单价
							oaDaBanInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaBanInfo.setPurMemo(odi.getPurMemo()); // 备注
							if (null == oaDaBanInfo || null == oaDaBanInfo.getId()) {
								oaDaBanInfo.setOaMaterialList(ml.getId()); // 用料表Id
							}
							baseDao.saveObject(oaDaBanInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							baseDao.saveObject(odi);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 保存管理信息
	 * @param oaOrderDetail
	 * @param orderDetailId
	 * @throws Exception
	 */
	private static void saveManegeInfo(OaOrderDetail oaOrderDetail,int orderDetailId) throws Exception {
		OaOrderDetail dbOaOrderDetail = (OaOrderDetail) baseDao.getObject(OaOrderDetail.class, orderDetailId);
		if (StringUtils.isNotBlank(oaOrderDetail.getOtherFile())) {
			dbOaOrderDetail.setOtherFile(oaOrderDetail.getOtherFile());
		}
		if (StringUtils.isNotBlank(oaOrderDetail.getContent())) {
			dbOaOrderDetail.setContent(oaOrderDetail.getContent());
		}
		if (StringUtils.isNotBlank(oaOrderDetail.getWorker())) {
			dbOaOrderDetail.setWorker(oaOrderDetail.getWorker());
		} else {
			dbOaOrderDetail.setWorker(WebUtil.getCurrentLoginBx().getLoginName());
		}
		if (null != oaOrderDetail.getWorkTime()) {
			dbOaOrderDetail.setWorkTime(oaOrderDetail.getWorkTime());
		}
		baseDao.saveObject(dbOaOrderDetail);
	}
	
	/**
	 * 技术节点保存加工说明
	 * @param oaProcessExplain
	 * @param orderId
	 * @throws Exception
	 */
	private static void saveProcessExplain(OaProcessExplain oaProcessExplain,int orderId) throws Exception {
		oaProcessExplain.setOaOrderId(orderId);
		baseDao.saveObject(oaProcessExplain);
	}
	
	/**
	 * 保存尺寸详情列表
	 * @param orderId
	 * @param clothesSizeDetailDelIds
	 * @param oaClothesSizeDetails
	 * @throws Exception
	 */
	private static void saveClothesSizeDetail(int orderId,String clothesSizeDetailDelIds,List<OaClothesSizeDetail> oaClothesSizeDetails) throws Exception {
		// 删除尺寸表详情信息
		if (StringUtils.isNotEmpty(clothesSizeDetailDelIds)) {
			String[] clothesSizeDetailDelId = clothesSizeDetailDelIds.split(",");
			for (String delId : clothesSizeDetailDelId) {
				if (StringUtils.isNotEmpty(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaClothesSizeDetail oaClothesSizeDetail = (OaClothesSizeDetail) baseDao.getObject(OaClothesSizeDetail.class, id);
						if (null != oaClothesSizeDetail && null != oaClothesSizeDetail.getId()) {
							baseDao.delObject(oaClothesSizeDetail);
						}
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		// 循环存储尺寸表详情信息
		if (null != oaClothesSizeDetails) {
			for (OaClothesSizeDetail oaClothesSizeDetail : oaClothesSizeDetails) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				String orderNumsString[] = oaClothesSizeDetail.getClothSize().split("-");
				boolean orderNumFlag = false;
				for (String string : orderNumsString) {
					if (StringUtils.isNotEmpty(string)) {
						orderNumFlag = true;
						break;
					}
				}
				if (orderNumFlag || StringUtils.isNotEmpty(oaClothesSizeDetail.getPosition()) || StringUtils.isNotEmpty(oaClothesSizeDetail.getSamplePageSize()) || StringUtils.isNotEmpty(oaClothesSizeDetail.getTolerance())) {
					oaClothesSizeDetail.setOaOrder(orderId);
					baseDao.saveObject(oaClothesSizeDetail);
				}
			}
		}
	}

	/**
	 * 保存尺寸表表头和order表中的样板基码
	 * @param oaOrder
	 * @throws Exception
	 */
	private static void saveClothesSize(OaOrder oaOrder,OaClothesSize oaClothesSize) throws Exception {
		oaClothesSize.setOaOrderId(oaOrder.getId());
		baseDao.saveObject(oaClothesSize);
		oaOrder.setSampleSize(oaClothesSize.getSampleSize());
		baseDao.saveObject(oaOrder);
	}
	
	/**
	 * 保存技术节点用料说明信息
	 * @param type
	 * @param orderId
	 * @param oaDaHuoInfos
	 * @param oaDaBanInfos
	 * @param oaMaterialLists
	 * @param materialDelIds
	 * @param materialDescDelIds
	 * @throws Exception
	 */
	private static void saveMaterialInfo(String type, int orderId,List<OaDaHuoInfo> oaDaHuoInfos,List<OaDaBanInfo> oaDaBanInfos,List<OaMaterialList> oaMaterialLists,String materialDelIds,String materialDescDelIds) throws Exception {
		delOaMaterialList(materialDelIds);

		if (StringUtils.isNotBlank(type) && "3".equals(type) && null != oaDaHuoInfos) {
			// 保存之前要先删除要删除的数据 删除大货清单信息
			// update by 张华 2015-01-20
			delOaDaHuoInfo(materialDescDelIds);

			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					OaDaHuoInfo dhi = oaDaHuoInfos.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || null != dhi.getBuffon() || null != dhi.getUnitNum() || StringUtils.isNotBlank(dhi.getPosition()) || StringUtils.isNotBlank(dhi.getItMemo())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) baseDao.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							baseDao.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							baseDao.saveObject(ml);
						}
						// 保存大货的用料说明信息
						OaDaHuoInfo odi = oaDaHuoInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) baseDao.getObject(OaDaHuoInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaHuoInfo
							oaDaHuoInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaHuoInfo.setUnitNum(odi.getUnitNum()); // 单件用量
							oaDaHuoInfo.setPosition(odi.getPosition()); // 位置
							baseDao.saveObject(oaDaHuoInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							baseDao.saveObject(odi);
						}
					}
				}
			}
		} else if (StringUtils.isNotBlank(type) && "2".equals(type) && null != oaDaBanInfos) {
			// 保存之前要先删除要删除的数据 删除打版清单信息
			delOaDaBanInfo(materialDescDelIds);
			if (null != oaMaterialLists) {
				for (int i = 0; i < oaMaterialLists.size(); i++) {
					// 先保存面料搭配明细
					OaMaterialList ml = oaMaterialLists.get(i);
					OaDaBanInfo dbi = oaDaBanInfos.get(i);
					// 判断只要其中一个属性存在值，即存储到数据库中
					boolean orderNumFlag = false;
					if (StringUtils.isNotBlank(ml.getMaterialName()) || StringUtils.isNotBlank(ml.getMaterialProp()) || StringUtils.isNotBlank(ml.getColor()) || null != dbi.getBuffon() || null != dbi.getUnitNum() || StringUtils.isNotBlank(dbi.getPosition()) || StringUtils.isNotBlank(dbi.getItMemo())) {
						orderNumFlag = true;
					}
					if (orderNumFlag) {
						if (null != ml && null != ml.getId()) {
							OaMaterialList oml = (OaMaterialList) baseDao.getObject(OaMaterialList.class, ml.getId());
							oml.setMaterialProp(ml.getMaterialProp());
							oml.setMaterialName(ml.getMaterialName());
							oml.setType(ml.getType());
							oml.setColor(ml.getColor());
							baseDao.saveObject(oml);
						} else {
							ml.setOaOrderId(orderId);
							baseDao.saveObject(ml);
						}
						// 保存打板的用料说明信息
						OaDaBanInfo odi = oaDaBanInfos.get(i);
						if (null != odi && null != odi.getId()) { // 说明已存在
							OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) baseDao.getObject(OaDaBanInfo.class, odi.getId()); // 先查询
							// 设置新的数据到oaDaHuoInfo
							oaDaBanInfo.setBuffon(odi.getBuffon()); // 布封
							oaDaBanInfo.setUnitNum(odi.getUnitNum()); // 单件用量
							oaDaBanInfo.setPosition(odi.getPosition()); // 位置
							baseDao.saveObject(oaDaBanInfo);
						} else { // 新数据，直接保存
							odi.setOaMaterialList(ml.getId());
							baseDao.saveObject(odi);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 删除打版清单数据
	 */
	private static void delOaDaBanInfo(String materialDescDelIds) {
		if (StringUtils.isNotBlank(materialDescDelIds)) {
			String[] delIds = materialDescDelIds.split(",");
			for (String delId : delIds) {
				if (StringUtils.isNotBlank(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaDaBanInfo oaDaBanInfo = (OaDaBanInfo) baseDao.getObject(OaDaBanInfo.class, id);
						if (null != oaDaBanInfo && null != oaDaBanInfo.getId()) {
							baseDao.delObject(oaDaBanInfo);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 删除大货清单数据
	 * @param materialDescDelIds
	 */
	private static void delOaDaHuoInfo(String materialDescDelIds) {
		if (StringUtils.isNotBlank(materialDescDelIds)) {
			String[] delIds = materialDescDelIds.split(",");
			for (String delId : delIds) {
				if (StringUtils.isNotBlank(delId)) {
					try {
						int id = Integer.parseInt(delId);
						OaDaHuoInfo oaDaHuoInfo = (OaDaHuoInfo) baseDao.getObject(OaDaHuoInfo.class, id);
						if (null != oaDaHuoInfo && null != oaDaHuoInfo.getId()) {
							baseDao.delObject(oaDaHuoInfo);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 复制保存核价信息表
	 */
	private static void copyToExtraSaveCalculation(Integer orderId, Integer relatedOrderId) {
	}

	/**
	 * @Description 复制保存技术信息表
	 * @author ZQ
	 * @Date 2014-12-22
	 * @param orderId 订单ID
	 * @param relatedOrderId 关联订单ID
	 * @param ifCopySize  标识是否复制技术节点尺寸详情
	 */
	private static void copyToExtraSaveTechnology(Integer orderId, Integer relatedOrderId, boolean ifCopySize, String copySize) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = JdbcHelper.getConnection();
			// 手动提交
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_SIZE_SQL, orderId, relatedOrderId));// 尺码表
			if (ifCopySize) {
				stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_DETAILS_SIZE_SQL, "cloth_size", orderId, relatedOrderId));// 尺码详情表
			} else {
				stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_CLOTHES_DETAILS_SIZE_SQL, copySize, orderId, relatedOrderId));// 尺码详情表
			}
			stmt.addBatch(MessageFormat.format(ConstantUtil.TECH_PROCESS_EXPLAIN_SQL, orderId, relatedOrderId));// 加工说明
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// 异常时回滚数据,事务恢复自动提交
			try {
				if (!conn.isClosed()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} finally {
			JdbcHelper.close(stmt, conn);
		}
	}
	
	/**
	 * @Description 复制保存采购信息表
	 * @author ZQ
	 * @Date 2014-12-22
	 * @param orderId 订单ID
	 * @param relatedOrderId 关联订单ID
	 * @param type 订单类型
	 * @param oldMaterIds 关联过来的用料IDs
	 * @param materIds 保存提交的用料IDs
	 * @param isTypeSame  订单与关联订单是否为同一类型
	 */
	private static void copyToExtraSavePurchase(Integer orderId, Integer relatedOrderId, String type, String[] oldMaterIds, String materIds, boolean isTypeSame) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			String sqlStr = "";
			String delStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DEL_DABAN_INVENTORY_SQL : ConstantUtil.DEL_DAHUO_INVENTORY_SQL;
			String[] matIds = materIds.split(",");
			// 如果类型一样,则取整个清单表SQL,否则取部分清单表SQL
			if (isTypeSame) {
				sqlStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DABAN_INVENTORY_SQL : ConstantUtil.DAHUO_INVENTORY_SQL;
			} else {
				sqlStr = ConstantUtil.OA_ORDER_TYPE_2.equals(type) ? ConstantUtil.DABAN_INVENTORY_PART_SQL : ConstantUtil.DAHUO_INVENTORY_PART_SQL;
			}
			conn = JdbcHelper.getConnection();
			// 手动提交
			conn.setAutoCommit(false);
			/**
			 * update by 张华 2015-01-20 现在关联订单只在订单详情时才可进行关联，关联之后不可再次关联，所以把删除数据代码暂时注释
			 */
			stmt = conn.createStatement();
			int i = 0;
			for (String oldMaterId : oldMaterIds) {
				stmt.addBatch(MessageFormat.format(sqlStr, matIds[i], oldMaterId));
				i++;
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			// 异常时回滚数据,事务恢复自动提交
			try {
				if (!conn.isClosed()) {
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				throw e1;
			}
			throw e;
		} finally {
			JdbcHelper.close(stmt, conn);
		}
	}
	
	/**
	 * 保存订单详情中上传的文件及备注
	 * @param pageOaOrderDetail
	 * @param oaOrderDetail
	 * @param mrMemo
	 * @throws Exception
	 */
	private static void saveOaOrderDetail(OaOrderDetail pageOaOrderDetail,Integer oaOrderDetail, String mrMemo) throws Exception {
		OaOrderDetail dbOaOrderDetail = (OaOrderDetail) baseDao.getObject(OaOrderDetail.class, oaOrderDetail);
		dbOaOrderDetail.setOtherFile(pageOaOrderDetail.getOtherFile()); // 保存上传附件
		dbOaOrderDetail.setContent(mrMemo); // 保存mr备注
		baseDao.saveObject(dbOaOrderDetail);
	}

	/**
	 * 保存客供料信息
	 * @param oaCusMaterialLists
	 * @param orderId
	 * @param cusMaterialDelIds
	 * @throws Exception
	 */
	private static void saveOaCusMaterialList(List<OaCusMaterialList> oaCusMaterialLists,int orderId,String cusMaterialDelIds) throws Exception {
		// 删除客供料信息
		if (StringUtils.isNotEmpty(cusMaterialDelIds)) {
			String cusMaterialIds[] = cusMaterialDelIds.split(",");
			for (String cusMaterialId : cusMaterialIds) {
				try {
					int id = Integer.parseInt(cusMaterialId);
					OaCusMaterialList oaCusMaterialList = (OaCusMaterialList) baseDao.getObject(OaCusMaterialList.class, id);
					if (null != oaCusMaterialList && null != oaCusMaterialList.getId()) {
						baseDao.delObject(oaCusMaterialList);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		if (null != oaCusMaterialLists) {
			// 循环存储客供料信息
			for (OaCusMaterialList oaCusMaterialList : oaCusMaterialLists) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				String orderNumsString[] = oaCusMaterialList.getOrderNum().split("-");
				boolean orderNumFlag = false;
				for (String string : orderNumsString) {
					if (StringUtils.isNotEmpty(string)) {
						orderNumFlag = true;
						break;
					}
				}
				if (orderNumFlag || StringUtils.isNotEmpty(oaCusMaterialList.getMaterialName()) || (null != oaCusMaterialList.getAmount() && oaCusMaterialList.getAmount() > 0) || (null != oaCusMaterialList.getConsume() && oaCusMaterialList.getConsume() > 0) || (null != oaCusMaterialList.getTotal() && oaCusMaterialList.getTotal() > 0) || "否".equals(oaCusMaterialList.getIsComplete()) || StringUtils.isNotEmpty(oaCusMaterialList.getMemo())) {
					if (null == oaCusMaterialList.getAmount()) {
						oaCusMaterialList.setAmount(0f);
					}
					if (null == oaCusMaterialList.getConsume()) {
						oaCusMaterialList.setConsume(0f);
					}
					if (null == oaCusMaterialList.getTotal()) {
						oaCusMaterialList.setTotal(0f);
					}
					oaCusMaterialList.setOaOrderId(orderId);
					baseDao.saveObject(oaCusMaterialList);
				}
			}
		}
	}
	
	/**
	 * 保存用料搭配信息
	 * @param oaMaterialLists
	 * @param orderId
	 * @param materialDelIds
	 * @return
	 * @throws Exception
	 */
	private static String saveOaMaterialList(List<OaMaterialList> oaMaterialLists,int orderId,String materialDelIds) throws Exception {
		// 拼接新增成功的用料ID
		String materIds = "";
		// 删除用料搭配信息
		// update by 张华 2015-01-20
		delOaMaterialList(materialDelIds);
		if (null != oaMaterialLists) {
			// 循环存储用料搭配信息
			for (OaMaterialList oaMaterialList : oaMaterialLists) {
				// 判断只要其中一个属性存在值，即存储到数据库中
				if (StringUtils.isNotEmpty(oaMaterialList.getMaterialProp()) || StringUtils.isNotEmpty(oaMaterialList.getMaterialName()) || "辅料".equals(oaMaterialList.getType()) || StringUtils.isNotEmpty(oaMaterialList.getColor()) || StringUtils.isNotEmpty(oaMaterialList.getSupplierName()) || StringUtils.isNotEmpty(oaMaterialList.getSupplierAddr()) || StringUtils.isNotEmpty(oaMaterialList.getSupplierTel()) || (null != oaMaterialList.getOrderNum() && oaMaterialList.getOrderNum() > 0) || StringUtils.isNotEmpty(oaMaterialList.getPosition())) {
					if (null == oaMaterialList.getOrderNum()) {
						oaMaterialList.setOrderNum(0f);
					}
					oaMaterialList.setOaOrderId(orderId);
					baseDao.saveObject(oaMaterialList);
					materIds += "," + oaMaterialList.getId();
				}
			}
			if (materIds.length() >= 1)
				materIds = materIds.substring(1);
		}
		return materIds;
	}
	
	/**
	 * 删除用料搭配信息
	 * @param materialDelIds
	 */
	private static void delOaMaterialList(String materialDelIds) {
		if (StringUtils.isNotEmpty(materialDelIds)) {
			String materialIds[] = materialDelIds.split(",");
			for (String materialId : materialIds) {
				try {
					int id = Integer.parseInt(materialId);
					OaMaterialList oaMaterialList = (OaMaterialList) baseDao.getObject(OaMaterialList.class, id);
					if (null != oaMaterialList && null != oaMaterialList.getId()) {
						baseDao.delObject(oaMaterialList);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 * 通过order的id生成excel by fangwei 2014-12-17
	 */
	public static String createExcelByOrderId(Integer orderId) {
		String baseExcelFile = null;
		OaOrder oaOrder = (OaOrder) baseDao.getObject(OaOrder.class, orderId);
		String erp209 = "2015-4-13 13:30:00";
		if(oaOrder.getBeginTime().compareTo(DateUtil.parseDate(erp209))>0){
			baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseExcelFile"));
		}else{
			baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("oldBaseExcelFile"));
		}
		if (null == oaOrder || null == oaOrder.getId()) {
			throw new RuntimeException("the parameter oaOrder.id is null!");
		}
		String url = null;
		Map<String, Object> fillInfo = getOrderExcelInfo(oaOrder);
		try {
			String fileName = "ERP-Order-" + oaOrder.getSellOrderCode() + "-" + (System.currentTimeMillis() / 1000 * 1000 + "").substring(6, 13) + ".xlsx";
			File file = new File(PathUtil.getOaFileDir());
			if (!file.exists()) {
				file.mkdirs();
			}

			OutputStream os = new FileOutputStream(new File(file, fileName));
			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());

			if (!"2".equals(node)) {
				FSPBean fsp = new FSPBean();
				fsp.set("oa_order", oaOrder.getId());
				fsp.set("wf_step", (Integer.parseInt(node) - 1) + "");
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
				LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
				String lastExcelFile = (String) bean.get("attachment");
				if (StringUtils.isNotBlank(lastExcelFile)) {
					Struts2Utils.getResponse().setHeader("Content-Disposition", "attachment;filename=" + PathUtil.url2FileName(lastExcelFile));
					baseExcelFile = PathUtil.url2Path(lastExcelFile);
					if (!new File(baseExcelFile).exists()) {
						// 服务器中 excel 不存在
						throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment in server [" + baseExcelFile + "]is not exists!");
					}
				} else {
					// 数据库中无excel 地址信息
					throw new RuntimeException("the orderDetail <" + bean.get("id") + "> attachment field is not exists!");
				}
			}
			fillInfo.put("fileUrl", baseExcelFile);
			POIUtilsEx.processExcel(os, fillInfo);
			url = PathUtil.path2Url(PathUtil.getOaFileDir().concat(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	// 填充list,返回模板中对应的excel索引
	// by fangwei 2014-12-17
	public static Map<String, Object> getOrderExcelInfo(OaOrder oaOrder) {
		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		Map<String, Object> fillInfo = new HashMap<String, Object>();
		if ("2".equals(node)) {
			fillInfo = createOrderInfo(oaOrder);
		} else if (oaOrder.getType().equals("2")) {
			// 打板
			switch (node) {
			case "3":
				fillInfo = getPurchaseInfo(oaOrder);
				break;
			case "4":
				fillInfo = getTechnologyInfo(oaOrder);
				break;
			case "5":
				fillInfo = getPricingInfo(oaOrder);
				break;
			default:
				break;
			}
		} else if (oaOrder.getType().equals("3")) {
			// 大货
			switch (node) {
			case "3":
				fillInfo = getTechnologyInfo(oaOrder);
				break;
			case "4":
				fillInfo = getPurchaseInfo(oaOrder);
				break;
			case "5":
				break;
			case "6":
				break;
			case "7":
				break;
			case "8":
				break;
			case "9":
				break;
			default:
				break;
			}
		}
		return fillInfo;
	}
	
	// 订单节点 Excel
	// by fangwei 2014-12-17
	@SuppressWarnings("unchecked")
	public static Map<String, Object> createOrderInfo(OaOrder oaOrder) {
		String erp209 = "2015-4-13 13:30:00";
		Map<String,Object> fillInfo = new HashMap<String,Object>();
		String baseExcelFile = null;
		if(oaOrder.getBeginTime().compareTo(DateUtil.parseDate(erp209))>0){
			baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("baseExcelFile"));
		}else{
			baseExcelFile = Struts2Utils.getSession().getServletContext().getRealPath(ResourceUtil.getString("oldBaseExcelFile"));
		}
		fillInfo.put("fileUrl", baseExcelFile);
		fillInfo.put("sheetNames", "订单");
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		Map<String,Object> sheet1DynaRow = new TreeMap<String,Object>();
		fillInfo.put("订单FileInfo", sheet1FileInfo);
		fillInfo.put("订单DynaRow", sheet1DynaRow);
		if(oaOrder.getBeginTime().compareTo(DateUtil.parseDate(erp209))>0){
			sheet1FileInfo.put("1,1", oaOrder.getCusCode());
			sheet1FileInfo.put("1,7", oaOrder.getSellOrderCode());
			sheet1FileInfo.put("1,16", DateUtil.formatDate(oaOrder.getBeginTime(),DateUtil.YMD));
			sheet1FileInfo.put("2,1", oaOrder.getOrderCode());
			sheet1FileInfo.put("2,7,", oaOrder.getStyleDesc());
			sheet1FileInfo.put("2,16", DateUtil.formatDate(oaOrder.getExceptFinish(),DateUtil.YMD));
			sheet1FileInfo.put("3,1", oaOrder.getSales());
			sheet1FileInfo.put("3,7", oaOrder.getTpeName());
			sheet1FileInfo.put("3,16", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
			sheet1FileInfo.put("4,1", oaOrder.getMrName());
			sheet1FileInfo.put("4,7", oaOrder.getConfirmStaff());
			sheet1FileInfo.put("6,14,12,16", oaOrder.getPictureFront());
			sheet1FileInfo.put("6,16,12,18", oaOrder.getPictureBack());
			
			OaOrderNum oaOrderNum = (OaOrderNum) baseDao.getObject(OaOrderNum.class, oaOrder.getOaOrderNumId());
			//标题
			String[] titles = oaOrderNum.getTitle().split("-");
			//尺码行
			String[] infos = oaOrderNum.getNumInfo().split(",");
			//尺码动态添加的行
			Integer oaOrderNumAddRows = infos.length>4?infos.length-4:0; 
			sheet1DynaRow.put("07", oaOrderNumAddRows);
			
			sheet1FileInfo.put(11+oaOrderNumAddRows+oaOrderNumAddRows+",1", new CustomCell("SUM(N8:INDIRECT(\"N\"&(ROW()-1)))", "Expression").setAlignment(CellStyle.ALIGN_RIGHT));
			sheet1FileInfo.put(12+oaOrderNumAddRows+",1", oaOrder.getStyleCraft());
			sheet1FileInfo.put(12+oaOrderNumAddRows+",16", oaOrder.getSampleSize());
			FSPBean fsp = new FSPBean();
			fsp.set("oa_order_id", oaOrder.getId());
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_MATERIAL_LIST);
			List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
			//面料动态添加的行
			Integer oaMaterialListAddRows = beans.size()>15?beans.size()-15:0; 
			sheet1DynaRow.put(15 + oaOrderNumAddRows +"", oaMaterialListAddRows);
			
			if (titles.length > 12)
				throw new RuntimeException("the oa_order_num info is invalid!");
			for (int i = 0; i < titles.length; i++) {
				sheet1FileInfo.put("6," + (i + 1), titles[i]);
				sheet1FileInfo.put(38+oaOrderNumAddRows+oaMaterialListAddRows+"," + (i + 3), titles[i]);
			}
			for (int i = 0; i < infos.length; i++) {
				String[] nums = infos[i].split("-");
				for (int j = 0; j < nums.length; j++) {
					if(j==0) {
						sheet1FileInfo.put(i + 7 + "," + j, nums[j]);
					}else{
						sheet1FileInfo.put(i + 7 + "," + j+",Integer", nums[j]);
					}
				}
				sheet1FileInfo.put(i + 7 + ",13", new CustomCell("IF(INDIRECT(\"A\"&ROW())<>\"\",SUM(INDIRECT(\"B\"&ROW()):INDIRECT(\"M\"&ROW())),\"\")", "Expression"));
			}
			sheet1FileInfo.put(31+oaOrderNumAddRows+oaMaterialListAddRows+",0", new CustomCell(oaOrder.getMemo(),"String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
			
			// 材料清单
			int t = oaOrderNumAddRows;
			for (DynaBean bean : beans) {
				sheet1FileInfo.put(15 + t + ",0", bean.get("material_prop") == null ? "" : bean.get("material_prop").toString());
				sheet1FileInfo.put(15 + t + ",1", bean.get("material_name") == null ? "" : bean.get("material_name").toString());
				sheet1FileInfo.put(15 + t + ",4", bean.get("type") == null ? "" : bean.get("type").toString());
				sheet1FileInfo.put(15 + t + ",5", bean.get("color") == null ? "" : bean.get("color").toString());
				sheet1FileInfo.put(15 + t + ",6", bean.get("supplier_name") == null ? "" : bean.get("supplier_name").toString());
				sheet1FileInfo.put(15 + t + ",7", bean.get("supplier_addr") == null ? "" : bean.get("supplier_addr").toString());
				sheet1FileInfo.put(15 + t + ",14", bean.get("supplier_tel") == null ? "" : bean.get("supplier_tel").toString());
				sheet1FileInfo.put(15 + t + ",15,Double", bean.get("order_num") == null ? "" : bean.get("order_num").toString());
				sheet1FileInfo.put(15 + t++ + ",16", bean.get("position") == null ? "" : bean.get("position").toString());
			}

			// 客供料明细
			fsp = new FSPBean();
			fsp.set("oa_order_id", oaOrder.getId());
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_CUS_MATERIAL_LIST);
			beans = baseDao.getObjectsBySql(fsp);
			
			Integer OaCusMaterialListAddRows = beans.size()>4?beans.size()-4:0; 
			sheet1DynaRow.put(39+oaOrderNumAddRows+oaMaterialListAddRows+"", OaCusMaterialListAddRows);
			sheet1FileInfo.put(43+oaOrderNumAddRows+oaMaterialListAddRows+OaCusMaterialListAddRows+",1", new CustomCell(oaOrder.getSendtype(),"String").setAlignment(CellStyle.ALIGN_LEFT));

			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());
			fsp = new FSPBean();
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", (Integer.parseInt(node)) + "");
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put(44+ oaOrderNumAddRows+oaMaterialListAddRows+OaCusMaterialListAddRows + ",1", bean.get("operator") == null ? "" : bean.get("operator").toString());

			int ct = oaOrderNumAddRows+oaMaterialListAddRows;
			for (DynaBean b : beans) {
				sheet1FileInfo.put((39 + ct) + ",0", b.get("material_name") == null ? "" : b.get("material_name").toString());
				sheet1FileInfo.put((39 + ct) + ",1,Double", b.get("amount") == null ? "" : b.get("amount").toString());
				sheet1FileInfo.put((39 + ct) + ",2,Double-0.0%", b.get("consume") == null ? "" : (((Float)b.get("consume"))/100));
				sheet1FileInfo.put((39 + ct) + ",15,Double", b.get("total") == null ? "" : b.get("total").toString());
				sheet1FileInfo.put((39 + ct) + ",16", b.get("is_complete") == null ? "" : b.get("is_complete").toString());
				String[] nums = ((String) b.get("order_num")).split("-");
				for (int j = 0; j < nums.length; j++) {
					sheet1FileInfo.put((39 + ct) + "," + (3 + j), nums[j]);
				}
				sheet1FileInfo.put((39 + ct++) + ",17", b.get("memo") == null ? "" : b.get("memo").toString());
			}
		}else{
			sheet1FileInfo.put("1,1", oaOrder.getCusCode());
			sheet1FileInfo.put("1,6", oaOrder.getSellOrderCode());
			sheet1FileInfo.put("1,12", DateUtil.formatDate(oaOrder.getBeginTime(),DateUtil.YMD));
			sheet1FileInfo.put("2,1", oaOrder.getOrderCode());
			sheet1FileInfo.put("2,6,", oaOrder.getStyleDesc());
			sheet1FileInfo.put("2,12", DateUtil.formatDate(oaOrder.getExceptFinish(),DateUtil.YMD));
			sheet1FileInfo.put("3,1", oaOrder.getSales());
			sheet1FileInfo.put("3,6", oaOrder.getTpeName());
			sheet1FileInfo.put("3,12", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
			sheet1FileInfo.put("4,1", oaOrder.getMrName());
			sheet1FileInfo.put("4,6", oaOrder.getConfirmStaff());
			sheet1FileInfo.put("6,10,12,12", oaOrder.getPictureFront());
			sheet1FileInfo.put("6,12,12,14", oaOrder.getPictureBack());
			
			OaOrderNum oaOrderNum = (OaOrderNum) baseDao.getObject(OaOrderNum.class, oaOrder.getOaOrderNumId());
			//标题
			String[] titles = oaOrderNum.getTitle().split("-");
			//尺码行
			String[] infos = oaOrderNum.getNumInfo().split(",");
			//尺码动态添加的行
			Integer oaOrderNumAddRows = infos.length>4?infos.length-4:0; 
			sheet1DynaRow.put("07", oaOrderNumAddRows);
			
			sheet1FileInfo.put(11+oaOrderNumAddRows+oaOrderNumAddRows+",1", new CustomCell("SUM(J8:INDIRECT(\"J\"&(ROW()-1)))", "Expression").setAlignment(CellStyle.ALIGN_RIGHT));
			sheet1FileInfo.put(12+oaOrderNumAddRows+",1", oaOrder.getStyleCraft());
			sheet1FileInfo.put(12+oaOrderNumAddRows+",12", oaOrder.getSampleSize());
			FSPBean fsp = new FSPBean();
			fsp.set("oa_order_id", oaOrder.getId());
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_MATERIAL_LIST);
			List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
			//面料动态添加的行
			Integer oaMaterialListAddRows = beans.size()>15?beans.size()-15:0; 
			sheet1DynaRow.put(15 + oaOrderNumAddRows +"", oaMaterialListAddRows);
			
			if (titles.length > 12)
				throw new RuntimeException("the oa_order_num info is invalid!");
			for (int i = 0; i < titles.length; i++) {
				sheet1FileInfo.put("6," + (i + 1), titles[i]);
				sheet1FileInfo.put(38+oaOrderNumAddRows+oaMaterialListAddRows+"," + (i + 3), titles[i]);
			}
			for (int i = 0; i < infos.length; i++) {
				String[] nums = infos[i].split("-");
				for (int j = 0; j < nums.length; j++) {
					if(j==0) {
						sheet1FileInfo.put(i + 7 + "," + j, nums[j]);
					}else{
						sheet1FileInfo.put(i + 7 + "," + j+",Integer", nums[j]);
					}
				}
				sheet1FileInfo.put(i + 7 + ",13", new CustomCell("IF(INDIRECT(\"A\"&ROW())<>\"\",SUM(INDIRECT(\"B\"&ROW()):INDIRECT(\"H\"&ROW())),\"\")", "Expression"));
			}
			sheet1FileInfo.put(31+oaOrderNumAddRows+oaMaterialListAddRows+",0", new CustomCell(oaOrder.getMemo(),"String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
			
			// 材料清单
			int t = oaOrderNumAddRows;
			for (DynaBean bean : beans) {
				sheet1FileInfo.put(15 + t + ",0", bean.get("material_prop") == null ? "" : bean.get("material_prop").toString());
				sheet1FileInfo.put(15 + t + ",1", bean.get("material_name") == null ? "" : bean.get("material_name").toString());
				sheet1FileInfo.put(15 + t + ",3", bean.get("type") == null ? "" : bean.get("type").toString());
				sheet1FileInfo.put(15 + t + ",4", bean.get("color") == null ? "" : bean.get("color").toString());
				sheet1FileInfo.put(15 + t + ",5", bean.get("supplier_name") == null ? "" : bean.get("supplier_name").toString());
				sheet1FileInfo.put(15 + t + ",6", bean.get("supplier_addr") == null ? "" : bean.get("supplier_addr").toString());
				sheet1FileInfo.put(15 + t + ",10", bean.get("supplier_tel") == null ? "" : bean.get("supplier_tel").toString());
				sheet1FileInfo.put(15 + t + ",11,Double", bean.get("order_num") == null ? "" : bean.get("order_num").toString());
				sheet1FileInfo.put(15 + t++ + ",12", bean.get("position") == null ? "" : bean.get("position").toString());
			}

			// 客供料明细
			fsp = new FSPBean();
			fsp.set("oa_order_id", oaOrder.getId());
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_OA_CUS_MATERIAL_LIST);
			beans = baseDao.getObjectsBySql(fsp);
			
			Integer OaCusMaterialListAddRows = beans.size()>4?beans.size()-4:0; 
			sheet1DynaRow.put(39+oaOrderNumAddRows+oaMaterialListAddRows+"", OaCusMaterialListAddRows);
			sheet1FileInfo.put(43+oaOrderNumAddRows+oaMaterialListAddRows+OaCusMaterialListAddRows+",1", new CustomCell(oaOrder.getSendtype(),"String").setAlignment(CellStyle.ALIGN_LEFT));

			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());
			fsp = new FSPBean();
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", (Integer.parseInt(node)) + "");
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put(44+ oaOrderNumAddRows+oaMaterialListAddRows+OaCusMaterialListAddRows + ",1", bean.get("operator") == null ? "" : bean.get("operator").toString());

			int ct = oaOrderNumAddRows+oaMaterialListAddRows;
			for (DynaBean b : beans) {
				sheet1FileInfo.put((39 + ct) + ",0", b.get("material_name") == null ? "" : b.get("material_name").toString());
				sheet1FileInfo.put((39 + ct) + ",1,Double", b.get("amount") == null ? "" : b.get("amount").toString());
				sheet1FileInfo.put((39 + ct) + ",2,Double-0.0%", b.get("consume") == null ? "" : (((Float)b.get("consume"))/100));
				sheet1FileInfo.put((39 + ct) + ",11,Double", b.get("total") == null ? "" : b.get("total").toString());
				sheet1FileInfo.put((39 + ct) + ",12", b.get("is_complete") == null ? "" : b.get("is_complete").toString());
				String[] nums = ((String) b.get("order_num")).split("-");
				for (int j = 0; j < nums.length; j++) {
					sheet1FileInfo.put((39 + ct) + "," + (3 + j), nums[j]);
				}
				sheet1FileInfo.put((39 + ct++) + ",13", b.get("memo") == null ? "" : b.get("memo").toString());
			}
		}
		return fillInfo;
	}

	// 技术节点 Excel
	// by fangwei 2014-12-17
	public static Map<String, Object> getTechnologyInfo(OaOrder oaOrder) {
		//时间判断，
		String erp209 = "2015-4-13 13:30:00";
		Map<String,Object> fillInfo = new HashMap<String,Object>();
		fillInfo.put("sheetNames", "技术");
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		Map<String,Object> sheet1DynaRow = new TreeMap<String,Object>();
		fillInfo.put("技术FileInfo", sheet1FileInfo);
		fillInfo.put("技术DynaRow", sheet1DynaRow);
		if(oaOrder.getBeginTime().compareTo(DateUtil.parseDate(erp209))>0){
			//新
			sheet1FileInfo.put("1,1", oaOrder.getSellOrderCode());
			sheet1FileInfo.put("1,5,", oaOrder.getStyleDesc());
			sheet1FileInfo.put("1,12", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
			
			// 材料清单
			FSPBean fsp = new FSPBean();
			fsp.set("orderId", oaOrder.getId());
			if ("2".equals(oaOrder.getType())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_BAN_INFO_BY_SQL);
			} else if ("3".equals(oaOrder.getType())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_HUO_INFO_BY_SQL);
			}
			List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
			Integer oaMaterialListAddRows =beans.size()>15?beans.size()-15:0;
			sheet1DynaRow.put("3", oaMaterialListAddRows);
			int t = 0;
			for (DynaBean bean : beans) {
				sheet1FileInfo.put((3 + t) + ",0", bean.get("material_prop") == null ? "" : bean.get("material_prop").toString());
				sheet1FileInfo.put((3 + t) + ",1", bean.get("material_name") == null ? "" : bean.get("material_name").toString());
				sheet1FileInfo.put((3 + t) + ",4", bean.get("type") == null ? "" : bean.get("type").toString());
				sheet1FileInfo.put((3 + t) + ",5", bean.get("color") == null ? "" : bean.get("color").toString());
				sheet1FileInfo.put((3 + t) + ",6,Double", bean.get("buffon") == null ? "" : bean.get("buffon"));
				sheet1FileInfo.put((3 + t) + ",7,Double", bean.get("unit_num") == null || "".equals(bean.get("unit_num").toString()) ? "" : bean.get("unit_num"));
				sheet1FileInfo.put((3 + t++) + ",9", bean.get("position") == null ? "" : bean.get("position").toString());
			}
			
			// 查询尺寸表表头
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_NUM_TITLE_BY_EQL);
			fsp.set("orderNumId", oaOrder.getOaOrderNumId());
			OaOrderNum oaOrderNum = (OaOrderNum) baseDao.getOnlyObjectByEql(fsp);
			t = 0;
			for (String title : oaOrderNum.getTitle().split("-")) {
				sheet1FileInfo.put(19+oaMaterialListAddRows+"," + (1 + t++), title);
			}
			
			// 查询尺寸表
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CLOTHES_SIZE_BY_EQL);
			fsp.set("orderId", oaOrder.getId());
			OaClothesSize oaClothesSize = (OaClothesSize) baseDao.getOnlyObjectByEql(fsp);
			if (null != oaClothesSize) {
				sheet1FileInfo.put(18+oaMaterialListAddRows+",7", oaClothesSize.getUnit());
				sheet1FileInfo.put(18+oaMaterialListAddRows+",13", oaClothesSize.getSampleSize());
			}
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CLOTHES_SIZE_DETAIL_BY_SQL);
			fsp.set("orderId", oaOrder.getId());
			List<LazyDynaMap> OaClothesSizeDetailList = baseDao.getObjectsBySql(fsp);
			List<OaClothesSizeDetail> oaClothesSizeDetails = copyOaClothesSizeDetailList2Vo(OaClothesSizeDetailList);
			
			Integer oaClothesAddRows = oaClothesSizeDetails.size()>12?oaClothesSizeDetails.size()-12:0;
			sheet1DynaRow.put(20+ oaMaterialListAddRows +"", oaClothesAddRows);
			if (oaClothesSizeDetails.size() > 0) {
				t = 0;
				for (OaClothesSizeDetail oaClothesSizeDetail : oaClothesSizeDetails) {
					sheet1FileInfo.put((20+ oaMaterialListAddRows + t) + ",0", oaClothesSizeDetail.getPosition());
					String nums = oaClothesSizeDetail.getClothSize();
					int m = 0;
					for (String num : nums.split("-")) {
						if (StringUtils.isNotBlank(num)) {
							try {
								sheet1FileInfo.put((20+ oaMaterialListAddRows + t) + "," + (1 + m++)+",Double", Float.parseFloat(num));
							} catch (Exception e) {
								sheet1FileInfo.put((20+ oaMaterialListAddRows + t) + "," + (1 + m++), "");
							}
						}
					}
					sheet1FileInfo.put((20+ oaMaterialListAddRows + t) + ",13", oaClothesSizeDetail.getSamplePageSize());
					sheet1FileInfo.put((20+ oaMaterialListAddRows + t++) + ",14,Double", oaClothesSizeDetail.getTolerance());
				}
			}
			
			// 工艺要求
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_PROCESS_EXPLAIN_BY_EQL);
			fsp.set("orderId", oaOrder.getId());
			OaProcessExplain oaProcessExplain = (OaProcessExplain) baseDao.getOnlyObjectByEql(fsp);
			if (null != oaProcessExplain) {
				sheet1FileInfo.put((32 + oaMaterialListAddRows + oaClothesAddRows) + ",0", new CustomCell("特殊工艺要求：" + oaProcessExplain.getSpecialArt(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((33 + oaMaterialListAddRows + oaClothesAddRows) + ",0", new CustomCell("裁床工艺要求：" + oaProcessExplain.getCutArt(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((32 + oaMaterialListAddRows + oaClothesAddRows) + ",7," + (34 + oaMaterialListAddRows + oaClothesAddRows) + ",14", oaProcessExplain.getMeasurePic());
				
				sheet1FileInfo.put((34 + oaMaterialListAddRows + oaClothesAddRows) + ",0", new CustomCell("车缝工艺要求：" + oaProcessExplain.getSewing(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((35 + oaMaterialListAddRows + oaClothesAddRows) + ",0," + (37 + oaMaterialListAddRows + oaClothesAddRows) + ",14", oaProcessExplain.getSewingPic());
				
				sheet1FileInfo.put((38 + oaMaterialListAddRows + oaClothesAddRows) + ",1", new CustomCell(oaProcessExplain.getTailButton(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((39 + oaMaterialListAddRows + oaClothesAddRows) + ",1", new CustomCell(oaProcessExplain.getTailIroning(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((40 + oaMaterialListAddRows + oaClothesAddRows) + ",1", new CustomCell(oaProcessExplain.getTailCard(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
				sheet1FileInfo.put((41 + oaMaterialListAddRows + oaClothesAddRows) + ",1", new CustomCell(oaProcessExplain.getTailPackaging(), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
			}
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MANAGER_INFO_BY_SQL);
			fsp.set("orderId", oaOrder.getId());
			fsp.set("node", "3");
			fsp.set(FSPBean.FSP_ORDER, " order by id desc");
			LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put((42 + oaMaterialListAddRows + oaClothesAddRows) + ",0", new CustomCell("备注：" + (bean.get("content") == null ? "" : bean.get("content").toString()), "String").setAlignment(CellStyle.ALIGN_LEFT).setVerticalAlignment(CellStyle.VERTICAL_TOP));
			
			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());
			fsp = new FSPBean();
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", node);
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put((43 + oaMaterialListAddRows + oaClothesAddRows) + ",1", bean.get("operator") == null ? "" : bean.get("operator").toString());
			sheet1FileInfo.put((43 + oaMaterialListAddRows + oaClothesAddRows) + ",8", bean.get("wf_real_start") == null ? "" : DateUtil.formatDate((java.sql.Timestamp) bean.get("wf_real_start")));
		}else{
			//旧
			sheet1FileInfo.put("1,1", oaOrder.getSellOrderCode());
			sheet1FileInfo.put("1,3,", oaOrder.getStyleDesc());
			sheet1FileInfo.put("1,8", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");
			
			// 材料清单
			FSPBean fsp = new FSPBean();
			fsp.set("orderId", oaOrder.getId());
			if ("2".equals(oaOrder.getType())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_BAN_INFO_BY_SQL);
			} else if ("3".equals(oaOrder.getType())) {
				fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_DA_HUO_INFO_BY_SQL);
			}
			List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
			int t = 0;
			for (DynaBean bean : beans) {
				sheet1FileInfo.put((3 + t) + ",0", bean.get("material_prop") == null ? "" : bean.get("material_prop").toString());
				sheet1FileInfo.put((3 + t) + ",1", bean.get("material_name") == null ? "" : bean.get("material_name").toString());
				sheet1FileInfo.put((3 + t) + ",2", bean.get("type") == null ? "" : bean.get("type").toString());
				sheet1FileInfo.put((3 + t) + ",3", bean.get("color") == null ? "" : bean.get("color").toString());
				sheet1FileInfo.put((3 + t) + ",4", bean.get("buffon") == null ? "" : bean.get("buffon").toString());
				sheet1FileInfo.put((3 + t) + ",5", bean.get("unit_num") == null || "".equals(bean.get("unit_num").toString()) ? "" : decimalFormat.format(Float.parseFloat(bean.get("unit_num").toString())));
				sheet1FileInfo.put((3 + t++) + ",6", bean.get("position") == null ? "" : bean.get("position").toString());
			}
			
			// 查询尺寸表表头
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_ORDER_NUM_TITLE_BY_EQL);
			fsp.set("orderNumId", oaOrder.getOaOrderNumId());
			OaOrderNum oaOrderNum = (OaOrderNum) baseDao.getOnlyObjectByEql(fsp);
			t = 0;
			for (String title : oaOrderNum.getTitle().split("-")) {
				sheet1FileInfo.put("19," + (1 + t++), title);
			}
			
			// 查询尺寸表
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CLOTHES_SIZE_BY_EQL);
			fsp.set("orderId", oaOrder.getId());
			OaClothesSize oaClothesSize = (OaClothesSize) baseDao.getOnlyObjectByEql(fsp);
			if (null != oaClothesSize) {
				sheet1FileInfo.put("18,5", oaClothesSize.getUnit());
				sheet1FileInfo.put("18,8", oaClothesSize.getSampleSize());
			}
			
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_CLOTHES_SIZE_DETAIL_BY_SQL);
			fsp.set("orderId", oaOrder.getId());
			List<LazyDynaMap> OaClothesSizeDetailList = baseDao.getObjectsBySql(fsp);
			List<OaClothesSizeDetail> oaClothesSizeDetails = copyOaClothesSizeDetailList2Vo(OaClothesSizeDetailList);
			
			int size = 12;
			if (oaClothesSizeDetails.size() > 0) {
				// size >12 则需要动态增行
				size = oaClothesSizeDetails.size();
				if (size > 12) {
					sheet1DynaRow.put("31", (size - 12) + "");
				} else {
					size = 12;
				}
				t = 0;
				for (OaClothesSizeDetail oaClothesSizeDetail : oaClothesSizeDetails) {
					sheet1FileInfo.put((20 + t) + ",0", oaClothesSizeDetail.getPosition());
					String nums = oaClothesSizeDetail.getClothSize();
					int m = 0;
					for (String num : nums.split("-")) {
						if (StringUtils.isNotBlank(num)) {
							try {
								sheet1FileInfo.put((20 + t) + "," + (1 + m++), decimalFormat.format(Float.parseFloat(num)));
							} catch (Exception e) {
								sheet1FileInfo.put((20 + t) + "," + (1 + m++), "");
							}
						}
					}
					sheet1FileInfo.put((20 + t) + ",8", oaClothesSizeDetail.getSamplePageSize());
					sheet1FileInfo.put((20 + t++) + ",9", oaClothesSizeDetail.getTolerance());
				}
			}
			
			// 工艺要求
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_PROCESS_EXPLAIN_BY_EQL);
			fsp.set("orderId", oaOrder.getId());
			OaProcessExplain oaProcessExplain = (OaProcessExplain) baseDao.getOnlyObjectByEql(fsp);
			if (null != oaProcessExplain) {
				sheet1FileInfo.put((32 + size - 12) + ",0", "特殊工艺要求：" + oaProcessExplain.getSpecialArt());
				sheet1FileInfo.put((33 + size - 12) + ",0", "裁床工艺要求：" + oaProcessExplain.getCutArt());
				sheet1FileInfo.put((32 + size - 12) + ",5," + (34 + size - 12) + ",10", oaProcessExplain.getMeasurePic());
				
				sheet1FileInfo.put((34 + size - 12) + ",0", "车缝工艺要求：" + oaProcessExplain.getSewing());
				sheet1FileInfo.put((35 + size - 12) + ",0," + (37 + size - 12) + ",10", oaProcessExplain.getSewingPic());
				
				sheet1FileInfo.put((38 + size - 12) + ",1", oaProcessExplain.getTailButton());
				sheet1FileInfo.put((39 + size - 12) + ",1", oaProcessExplain.getTailIroning());
				sheet1FileInfo.put((40 + size - 12) + ",1", oaProcessExplain.getTailCard());
				sheet1FileInfo.put((41 + size - 12) + ",1", oaProcessExplain.getTailPackaging());
			}
			fsp = new FSPBean();
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_MANAGER_INFO_BY_SQL);
			fsp.set("orderId", oaOrder.getId());
			fsp.set("node", "3");
			fsp.set(FSPBean.FSP_ORDER, " order by id desc");
			LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put((42 + size - 12) + ",0", "备注：" + (bean.get("content") == null ? "" : bean.get("content").toString()));
			
			String node = oaOrder.getWfStep();
			node = node.substring(node.lastIndexOf("_") + 1, node.length());
			fsp = new FSPBean();
			fsp.set("oa_order", oaOrder.getId());
			fsp.set("wf_step", node);
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
			bean = baseDao.getOnlyObjectBySql(fsp);
			sheet1FileInfo.put((43 + size - 12) + ",1", bean.get("operator") == null ? "" : bean.get("operator").toString());
			sheet1FileInfo.put((43 + size - 12) + ",8", bean.get("wf_real_start") == null ? "" : DateUtil.formatDate((java.sql.Timestamp) bean.get("wf_real_start")));
		}
		return fillInfo;
	}

	// 采购节点Excel
	// by fangwei 2014-12-17
	public static Map<String, Object> getPurchaseInfo(OaOrder oaOrder) {
		Map<String,Object> fillInfo = new HashMap<String,Object>();

		fillInfo.put("sheetNames", "采购");
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		Map<String,Object> sheet1DynaRow = new TreeMap<String,Object>();
		fillInfo.put("采购FileInfo", sheet1FileInfo);
		fillInfo.put("采购DynaRow", sheet1DynaRow);
		
		sheet1FileInfo.put("1,1", oaOrder.getSellOrderCode());
		sheet1FileInfo.put("1,4", oaOrder.getStyleDesc());
		sheet1FileInfo.put("1,8", oaOrder.getMrName());
		sheet1FileInfo.put("1,25", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");

		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		FSPBean fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", node);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
		sheet1FileInfo.put("1,12", bean.get("worker") == null ? "" : bean.get("worker").toString());
		sheet1FileInfo.put("1,16", bean.get("work_time") == null ? "" : DateUtil.formatDate((java.sql.Timestamp) bean.get("work_time"),DateUtil.YMD));

		fsp = new FSPBean();
		fsp.set("orderId", oaOrder.getId());
		if (oaOrder.getType().equals("2")) {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_BAN_MATERIAL_PURCHASE_DESC_BY_SQL);
		} else {
			fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DA_HUO_MATERIAL_PURCHASE_DESC_BY_SQL);
		}
		List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
		sheet1DynaRow.put("4", beans.size()>15?beans.size()-15:0);
		
		int t = 0;
		for (DynaBean b : beans) {
			sheet1FileInfo.put((4 + t) + ",0", b.get("position") == null ? "" : b.get("position").toString());
			sheet1FileInfo.put((4 + t) + ",1", b.get("material_prop") == null ? "" : b.get("material_prop").toString());
			sheet1FileInfo.put((4 + t) + ",2", b.get("type") == null ? "" : b.get("type").toString());
			sheet1FileInfo.put((4 + t) + ",3", b.get("material_name") == null ? "" : b.get("material_name").toString());
			sheet1FileInfo.put((4 + t) + ",4", b.get("color") == null ? "" : b.get("color").toString());
			sheet1FileInfo.put((4 + t) + ",5", b.get("supplier_name") == null ? "" : b.get("supplier_name").toString());
			sheet1FileInfo.put((4 + t) + ",6", b.get("supplier_addr") == null ? "" : b.get("supplier_addr").toString());
			sheet1FileInfo.put((4 + t) + ",7", b.get("supplier_tel") == null ? "" : b.get("supplier_tel").toString());
			if (oaOrder.getType().equals("3")) {
				sheet1FileInfo.put((4 + t) + ",8", b.get("buffon") == null ? "" : b.get("buffon").toString());
				sheet1FileInfo.put((4 + t) + ",9", b.get("unit_num") == null ? "" : decimalFormat.format((float) b.get("unit_num")));
				sheet1FileInfo.put((4 + t) + ",10", b.get("order_num") == null ? "" : b.get("order_num").toString());
				sheet1FileInfo.put((4 + t) + ",11", b.get("need_num") == null ? "" : decimalFormat.format((float) b.get("need_num")));
				sheet1FileInfo.put((4 + t) + ",12", b.get("org") == null ? "" : b.get("org").toString());
				sheet1FileInfo.put((4 + t) + ",13", b.get("num") == null ? "" : b.get("num").toString());
				sheet1FileInfo.put((4 + t) + ",14", b.get("price") == null ? "" : decimalFormat.format((float) b.get("price")));
				sheet1FileInfo.put((4 + t) + ",15", b.get("total_price") == null ? "" : decimalFormat.format((float) b.get("total_price")));
				sheet1FileInfo.put((4 + t) + ",16", b.get("test_price") == null ? "" : decimalFormat.format((float) b.get("test_price")));
				sheet1FileInfo.put((4 + t) + ",17", b.get("freight") == null ? "" : decimalFormat.format((float) b.get("freight")));
				sheet1FileInfo.put((4 + t) + ",18", b.get("total") == null ? "" : decimalFormat.format((float) b.get("total")));
				sheet1FileInfo.put((4 + t) + ",19", b.get("buyer_loss") == null ? "" : b.get("buyer_loss").toString());
				sheet1FileInfo.put((4 + t) + ",20", b.get("paper_tube") == null ? "" : decimalFormat.format((float) b.get("paper_tube")));
				sheet1FileInfo.put((4 + t) + ",21", b.get("deviation") == null ? "" : decimalFormat.format((float) b.get("deviation")));
				sheet1FileInfo.put((4 + t++) + ",22", b.get("pur_memo") == null ? "" : b.get("pur_memo").toString());
			} else {
				sheet1FileInfo.put((4 + t) + ",23,Integer", b.get("weight") == null ? "" : b.get("weight").toString());
				sheet1FileInfo.put((4 + t) + ",24", b.get("component") == null ? "" : b.get("component").toString());
				sheet1FileInfo.put((4 + t) + ",25,Integer-show", b.get("delivery_time") == null ? "" : b.get("delivery_time").toString());
				sheet1FileInfo.put((4 + t) + ",26,Double-0.0%", b.get("buyer_loss") == null ? "" : (((Float)b.get("buyer_loss"))/100));
				sheet1FileInfo.put((4 + t) + ",27,Double", b.get("paper_tube") == null ? "" : b.get("paper_tube").toString());
				sheet1FileInfo.put((4 + t) + ",28,Double", b.get("deviation") == null ? "" : b.get("deviation").toString());
				sheet1FileInfo.put((4 + t) + ",29,Double-@", b.get("shear_price") == null ? "" : (float) b.get("shear_price"));
				sheet1FileInfo.put((4 + t) + ",30", b.get("unit") == null ? "" : b.get("unit").toString());
				sheet1FileInfo.put((4 + t) + ",31,Double-@", b.get("goods_price") == null ? "" : (float) b.get("goods_price"));
				sheet1FileInfo.put((4 + t) + ",32", b.get("goods_unit") == null ? "" : b.get("goods_unit").toString());
				sheet1FileInfo.put((4 + t) + ",33,Double", b.get("buffon") == null ? "" : (float) b.get("buffon"));
				sheet1FileInfo.put((4 + t++) + ",34", b.get("pur_memo") == null ? "" : b.get("pur_memo").toString());
			}
		}
		return fillInfo;
	}

	// 核价节点Excel
	// by fangwei 2014-12-17
	public static Map<String, Object> getPricingInfo(OaOrder oaOrder) {
		Map<String,Object> fillInfo = new HashMap<String,Object>();
		fillInfo.put("sheetNames", "核价");
		Map<String,Object> sheet1FileInfo = new HashMap<String,Object>();
		Map<String,Object> sheet1DynaRow = new TreeMap<String,Object>();
		fillInfo.put("核价FileInfo", sheet1FileInfo);
		fillInfo.put("核价DynaRow", sheet1DynaRow);
		
		sheet1FileInfo.put("1,1", oaOrder.getSellOrderCode());
		sheet1FileInfo.put("1,3", oaOrder.getStyleDesc());
		sheet1FileInfo.put("1,8", oaOrder.getType().equals("2") ? "样衣打版" : "大货生产");

		String node = oaOrder.getWfStep();
		node = node.substring(node.lastIndexOf("_") + 1, node.length());
		
		FSPBean fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_MATERIAL_COST_BY_SQL);
		fsp.set("orderId", oaOrder.getId());
		List<LazyDynaMap> beans = baseDao.getObjectsBySql(fsp);
		Integer oaMaterialListAddRows =beans.size()>18?beans.size()-18:0;
		sheet1DynaRow.put("4", oaMaterialListAddRows);
		int t = 0;
		for (DynaBean bean : beans) {
			sheet1FileInfo.put((4 + t) + ",0", bean.get("material_prop") == null ? "" : (String) bean.get("material_prop"));
			sheet1FileInfo.put((4 + t) + ",1", bean.get("type") == null ? "" : (String) bean.get("type"));
			sheet1FileInfo.put((4 + t) + ",2", bean.get("material_name") == null ? "" : (String) bean.get("material_name"));
			sheet1FileInfo.put((4 + t) + ",3", bean.get("color") == null ? "" : (String) bean.get("color"));
			sheet1FileInfo.put((4 + t) + ",4,Double", bean.get("buffon") == null ? "" : bean.get("buffon").toString());
			sheet1FileInfo.put((4 + t) + ",5,Double", bean.get("unit_num") == null ? "" : (float) bean.get("unit_num"));
			sheet1FileInfo.put((4 + t) + ",6,Double-@", bean.get("cp_price") == null ? "" : bean.get("cp_price").toString());
			sheet1FileInfo.put((4 + t) + ",7,Double-@", bean.get("shear_price") == null ? "" : bean.get("shear_price").toString());
			sheet1FileInfo.put((4 + t) + ",8,Double", bean.get("cp_loss") == null ? "" : bean.get("cp_loss").toString());
			sheet1FileInfo.put((4 + t) + ",9,Double", bean.get("cp_total_price") == null ? "" : (float) bean.get("cp_total_price"));
			sheet1FileInfo.put((4 + t) + ",10,Double", bean.get("cp_shear_price") == null ? "" : (float) bean.get("cp_shear_price"));
			sheet1FileInfo.put((4 + t++) + ",11", bean.get("cp_memo") == null ? "" : bean.get("cp_memo").toString());
		}
		
		
		fsp = new FSPBean();
		fsp.set("oa_order", oaOrder.getId());
		fsp.set("wf_step", node);
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_DETAIL_EXCEL);
		LazyDynaMap bean = baseDao.getOnlyObjectBySql(fsp);
		sheet1FileInfo.put("1,12", bean.get("wf_real_start") == null ? "" : DateUtil.formatDate((java.sql.Timestamp) bean.get("wf_real_start")));
		sheet1FileInfo.put((27+oaMaterialListAddRows)+",0,"+(38+oaMaterialListAddRows)+",4", (String) bean.get("other_file"));
		sheet1FileInfo.put(39 +oaMaterialListAddRows+ ",1", (String) bean.get("operator"));


		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.GET_COST_BY_EQL);
		fsp.set("orderId", oaOrder.getId());
		OaCost oaCost = (OaCost) baseDao.getOnlyObjectByEql(fsp);
		if (oaCost != null) {
			sheet1FileInfo.put(22+oaMaterialListAddRows + ",9,Double", oaCost.getMGoodsPrice() == null ? "" : oaCost.getMGoodsPrice());
			sheet1FileInfo.put(22+oaMaterialListAddRows + ",10,Double", oaCost.getMShearPrice() == null ? "" : oaCost.getMShearPrice());

			sheet1FileInfo.put(24+oaMaterialListAddRows + ",1,Double", oaCost.getOStamp() == null ? "" : oaCost.getOStamp());
			sheet1FileInfo.put(24+oaMaterialListAddRows + ",3,Double", oaCost.getOEmbroider() == null ? "" : oaCost.getOEmbroider());
			sheet1FileInfo.put(24+oaMaterialListAddRows + ",6,Double", oaCost.getOWash() == null ? "" : oaCost.getOWash());
			sheet1FileInfo.put(24+oaMaterialListAddRows + ",10,Double", oaCost.getWaiXieTotalPrice() == null ? "" : oaCost.getWaiXieTotalPrice());
			sheet1FileInfo.put(24+oaMaterialListAddRows + ",11", oaCost.getOMemo() == null ? "" : oaCost.getOMemo().toString());

			sheet1FileInfo.put(28+oaMaterialListAddRows + ",9,Double", oaCost.getD1() == null ? "" : oaCost.getD1());
			sheet1FileInfo.put(28+oaMaterialListAddRows + ",10,Double", oaCost.getD2() == null ? "" : oaCost.getD2());
			sheet1FileInfo.put(28+oaMaterialListAddRows + ",11,Double", oaCost.getD3() == null ? "" : oaCost.getD3());
			sheet1FileInfo.put(28+oaMaterialListAddRows + ",12", oaCost.getPMemo() == null ? "" : oaCost.getPMemo().toString());

			if (oaCost.getPCutting() != null) {
				String[] pCutting = oaCost.getPCutting().split(",");
				sheet1FileInfo.put(28+oaMaterialListAddRows + ",5,Double", pCutting[0] == null ? "" : Float.parseFloat(pCutting[0]));
				sheet1FileInfo.put(28+oaMaterialListAddRows + ",6,Double", pCutting[1] == null ? "" : Float.parseFloat(pCutting[1]));
				sheet1FileInfo.put(28+oaMaterialListAddRows + ",7,Double", pCutting[2] == null ? "" : Float.parseFloat(pCutting[2]));
			}

			if (oaCost.getPSew() != null) {
				String[] pSew = oaCost.getPSew().split(",");
				sheet1FileInfo.put(29+oaMaterialListAddRows + ",5,Double", pSew[0] == null ? "" : Float.parseFloat(pSew[0]));
				sheet1FileInfo.put(29+oaMaterialListAddRows + ",6,Double", pSew[1] == null ? "" : Float.parseFloat(pSew[1]));
				sheet1FileInfo.put(29+oaMaterialListAddRows + ",7,Double", pSew[2] == null ? "" : Float.parseFloat(pSew[2]));
			}

			if (oaCost.getPLast() != null) {
				String[] pLast = oaCost.getPLast().split(",");
				sheet1FileInfo.put(30+oaMaterialListAddRows + ",5,Double", pLast[0] == null ? "" : Float.parseFloat(pLast[0]));
				sheet1FileInfo.put(30+oaMaterialListAddRows + ",6,Double", pLast[1] == null ? "" : Float.parseFloat(pLast[1]));
				sheet1FileInfo.put(30+oaMaterialListAddRows + ",7,Double", pLast[2] == null ? "" : Float.parseFloat(pLast[2]));
			}

			sheet1FileInfo.put(31+oaMaterialListAddRows + ",5,Double", oaCost.getBhe1() == null ? "" : oaCost.getBhe1());
			sheet1FileInfo.put(31+oaMaterialListAddRows + ",6,Double", oaCost.getBhe2() == null ? "" : oaCost.getBhe2());
			sheet1FileInfo.put(31+oaMaterialListAddRows + ",7,Double", oaCost.getBhe3() == null ? "" : oaCost.getBhe3());

			sheet1FileInfo.put(35+oaMaterialListAddRows + ",9,Integer", oaCost.getOrderNum1() == null ? "" : oaCost.getOrderNum1().toString());
			sheet1FileInfo.put(35+oaMaterialListAddRows + ",10,Integer", oaCost.getOrderNum2() == null ? "" : oaCost.getOrderNum2().toString());
			sheet1FileInfo.put(35+oaMaterialListAddRows + ",11,Integer", oaCost.getOrderNum3() == null ? "" : oaCost.getOrderNum3().toString());

			sheet1FileInfo.put(36+oaMaterialListAddRows + ",9,Double", oaCost.getBtotal1() == null ? "" : oaCost.getBtotal1());
			sheet1FileInfo.put(36+oaMaterialListAddRows + ",10,Double", oaCost.getBtotal2() == null ? "" : oaCost.getBtotal2());
			sheet1FileInfo.put(36+oaMaterialListAddRows + ",11,Double", oaCost.getBtotal3() == null ? "" : oaCost.getBtotal3());

			sheet1FileInfo.put(37+oaMaterialListAddRows + ",9,Double", oaCost.getDtotal1() == null ? "" : oaCost.getDtotal1());
			sheet1FileInfo.put(37+oaMaterialListAddRows + ",10,Double", oaCost.getDtotal2() == null ? "" : oaCost.getDtotal2());
			sheet1FileInfo.put(37+oaMaterialListAddRows + ",11,Double", oaCost.getDtotal3() == null ? "" : oaCost.getDtotal3());
		}
		return fillInfo;
	}

	/**
	 * @Title: copyOaClothesSizeDetailList2Vo
	 * @Description: TODO尺寸详情表，由List<LazyDynaMap>转为List<OaClothesSizeDetail>
	 * @author 张华
	 * @param list
	 * @return
	 */
	public static List<OaClothesSizeDetail> copyOaClothesSizeDetailList2Vo(List<LazyDynaMap> list) {
		List<OaClothesSizeDetail> oaClothesSizeDetails = new ArrayList<OaClothesSizeDetail>();
		for (LazyDynaMap map : list) {
			OaClothesSizeDetail oaClothesSizeDetail = new OaClothesSizeDetail();
			oaClothesSizeDetail.setId((Integer) map.get("id"));
			oaClothesSizeDetail.setOaOrder((Integer) map.get("oa_order"));
			oaClothesSizeDetail.setPosition((String) map.get("position"));
			oaClothesSizeDetail.setClothSize((String) map.get("cloth_size"));
			oaClothesSizeDetail.setSamplePageSize((String) map.get("sample_page_size"));
			oaClothesSizeDetail.setTolerance((String) map.get("tolerance"));
			oaClothesSizeDetails.add(oaClothesSizeDetail);
		}
		return oaClothesSizeDetails;
	}

}
