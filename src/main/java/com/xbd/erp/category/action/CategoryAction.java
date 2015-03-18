package com.xbd.erp.category.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.use.base.FSPBean;

import com.xbd.oa.action.BaseAction;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaStaff;

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
		fsp = new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_BY_SQL);
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		beans = manager.getObjectsBySql(fsp);
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
			oaCategory = hour2ms(oaCategory);
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
			oaCategory = hour2ms(oaCategory);
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

		return oaCategory;
	}

	public OaCategory getOaCategory() {
		return oaCategory;
	}

	public void setOaCategory(OaCategory oaCategory) {
		this.oaCategory = oaCategory;
	}
}
