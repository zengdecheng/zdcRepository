package com.xbd.erp.category.service;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaCategory;

public interface CategoryService extends BaseService<OaCategory> {
	
	/**
	 * 
	 * @Title: save保存品类信息
	 * @Description: TODO
	 *
	 * @author 张华
	 * @param oaCategory
	 */
	public void save(OaCategory oaCategory);

	/**
	 * 
	 * @Title: findOaCategory
	 * @Description: TODO根据id获取品类数据
	 *
	 * @author 张华
	 * @param id
	 *            品类id
	 * @return
	 */
	public OaCategory findOaCategory(Integer id);

}
