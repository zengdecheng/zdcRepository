package com.xbd.erp.timebase.service;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaTimebase;

public interface TimebaseService extends BaseService<OaTimebase> {

	/**
	 * 
	 * @Title: saveOaTimebase
	 * @Description: TODO保存基准时间设置
	 *
	 * @author 张华
	 * @param oaTimebase
	 * @throws Exception
	 */
	public void saveOaTimebase(OaTimebase oaTimebase) throws Exception;
}
