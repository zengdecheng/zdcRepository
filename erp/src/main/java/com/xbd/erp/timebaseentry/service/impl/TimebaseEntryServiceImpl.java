package com.xbd.erp.timebaseentry.service.impl;

import java.util.Iterator;
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
	}

	@Override
	public void saveOaTimebaseEntries(List<OaTimebaseEntry> oaTimebaseEntries) throws Exception {
		// TODO Auto-generated method stub
		for (OaTimebaseEntry oaTimebaseEntry : oaTimebaseEntries) {
			this.timebaseEntryDao.saveOrUpdate(oaTimebaseEntry);
		}
	}

}
