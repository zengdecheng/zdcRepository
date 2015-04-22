package com.xbd.erp.timebaseentry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.timebaseentry.dao.TimebaseEntryDao;
import com.xbd.erp.timebaseentry.service.TimebaseEntryService;
import com.xbd.oa.vo.OaTimebaseEntry;

@Service("timebaseEntryService")
public class TimebaseEntryServiceImpl extends BaseServiceImpl<OaTimebaseEntry> implements TimebaseEntryService {

	private TimebaseEntryDao timebaseEntryDao; // timebaseEntryDao的Dao

	public TimebaseEntryDao getTimebaseEntryDao() {
		return timebaseEntryDao;
	}

	@Resource(name = "timebaseEntryDao")
	public void setTimebaseEntryDao(TimebaseEntryDao timebaseEntryDao) {
		super.setBaseHibernateDao(timebaseEntryDao);
		this.timebaseEntryDao = timebaseEntryDao;
	}

	@Override
	public void saveOaTimebaseEntries(List<OaTimebaseEntry> oaTimebaseEntries, Integer timebaseId) {
		// TODO Auto-generated method stub
		try {
			// 保存基准时间设置细节
			for (OaTimebaseEntry oaTimebaseEntry : oaTimebaseEntries) {
				oaTimebaseEntry.setOaTimebase(timebaseId); // 基准时间设置Id
				this.timebaseEntryDao.saveOrUpdate(oaTimebaseEntry); // 保存基准时间设置细节
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<OaTimebaseEntry> listTimebaseEntries(Integer timebaseId) {
		// TODO Auto-generated method stub
		try {
			return this.timebaseEntryDao.findListByProperty("oaTimebase", timebaseId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
