package com.xbd.erp.category.service;

import java.util.List;
import java.util.Map;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaCategory;
import com.xbd.oa.vo.OaTimebase;
import com.xbd.oa.vo.OaTimebaseEntry;

public interface CategoryService extends BaseService<OaCategory> {

	/**
	 * 
	 * @Title: edit
	 * @Description: TODO编辑品类信息，且包括基准时间设置信息
	 *
	 * @author 张华
	 * @param oaCategory
	 *            品类对象
	 * @param dhTimebase
	 *            大货基准时间数据对象
	 * @param dhTimebaseEntries
	 *            大货基准时间细节数据对象
	 * @return
	 */
	public boolean edit(OaCategory oaCategory, OaTimebase dhTimebase, List<OaTimebaseEntry> dhTimebaseEntries);

}
