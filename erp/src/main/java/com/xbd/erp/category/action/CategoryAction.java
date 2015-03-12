package com.xbd.erp.category.action;

import org.use.base.FSPBean;

import com.xbd.oa.action.BaseAction;
import com.xbd.oa.dao.impl.BxDaoImpl;

public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**查询品类列表
	 * @author lanyu
	 * @return
	 */
	public String categoryList(){
		fsp=new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_BY_SQL);
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		beans=manager.getObjectsBySql(fsp);
		return "category/categoryList";
	}
	/**
	 * 添加品类
	 * @author lanyu
	 * @return
	 */
	public String addCategoryInput(){
		return "category/addCategory";
	}
}
