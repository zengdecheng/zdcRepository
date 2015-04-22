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
	 *            要保存的基准时间设置细节数据集合
	 * @param timebaseId
	 *            关联基准时间设置id
	 */
	public void saveOaTimebaseEntries(List<OaTimebaseEntry> oaTimebaseEntries, Integer timebaseId);

	/**
	 * 
	 * @Title: listTimebaseEntries
	 * @Description: TODO根据基准时间设置id获取基准时间设置细节数据
	 *
	 * @author 张华
	 * @param timebaseId
	 * @return
	 */
	public List<OaTimebaseEntry> listTimebaseEntries(Integer timebaseId);
}
