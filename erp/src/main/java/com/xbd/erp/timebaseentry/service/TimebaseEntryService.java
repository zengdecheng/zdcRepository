package com.xbd.erp.timebaseentry.service;

import java.util.List;

import com.xbd.erp.base.service.BaseService;
import com.xbd.oa.vo.OaTimebaseEntry;

public interface TimebaseEntryService extends BaseService<OaTimebaseEntry> {

	/**
	 * 
	 * @Title: saveOaTimebaseEntries
	 * @Description: TODO保存基准时间设置细节
	 *
	 * @author 张华
	 * @param oaTimebaseEntries
	 * @throws Exception
	 */
	public void saveOaTimebaseEntries(List<OaTimebaseEntry> oaTimebaseEntries) throws Exception;
}
