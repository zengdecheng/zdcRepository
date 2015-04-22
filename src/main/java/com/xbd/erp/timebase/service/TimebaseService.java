package com.xbd.erp.timebase.service;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaTimebase;

public interface TimebaseService extends BaseService<OaTimebase> {

	/**
	 * 
	 * @Title: saveOaTimebase
	 * @Description: TODO保存基准时间设置
	 *
	 * @author 张华
	 * @param oaTimebase
	 *            基准时间设置数据对象
	 * @param oaCategory
	 *            品类对象
	 */
	public void saveOaTimebase(OaTimebase oaTimebase, OaCategory oaCategory);

	/**
	 * 
	 * @Title: findTimebase
	 * @Description: TODO根据品类id查询基准时间设置
	 *
	 * @author 张华
	 * @param categoryId
	 *            品类id
	 * @return
	 */
	public OaTimebase findTimebase(Integer categoryId);
}
