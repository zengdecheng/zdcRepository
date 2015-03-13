package com.xbd.erp.category.action;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.use.base.FSPBean;

import com.xbd.oa.action.BaseAction;
import com.xbd.oa.dao.impl.BxDaoImpl;
import com.xbd.oa.vo.OaCategory;

@Results({
	@Result(name="page4list",type="redirect",location="category/list")
})
public class CategoryAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private OaCategory oaCategory;

	/**查询品类列表
	 * @author lanyu
	 * @return
	 */
	// page4list
	public String list(){
		fsp=new FSPBean();
		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_CATEGORY_BY_SQL);
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		beans=manager.getObjectsBySql(fsp);
		return "category/page4list";
	}
	/**
	 * 添加品类表单
	 * @author lanyu
	 * @return
	 */
	//page4add
	public String page4add(){
		return "category/page4add";
	}
	/**
	 * 添加品类
	 * @return
	 */
	public String add(){
		if(null!=oaCategory){
			manager.saveObject(oaCategory);
		}
		return "page4list";
	}
	
	
	public OaCategory getOaCategory() {
		return oaCategory;
	}
	public void setOaCategory(OaCategory oaCategory) {
		this.oaCategory = oaCategory;
	}
}
