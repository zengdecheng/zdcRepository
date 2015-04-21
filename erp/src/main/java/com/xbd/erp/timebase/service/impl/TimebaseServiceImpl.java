package com.xbd.erp.timebase.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xbd.erp.base.service.impl.BaseServiceImpl;
import com.xbd.erp.timebase.dao.TimebaseDao;
import com.xbd.erp.timebase.service.TimebaseService;
import com.xbd.oa.vo.OaTimebase;

@Service("timebaseService")
public class TimebaseServiceImpl extends BaseServiceImpl<OaTimebase> implements TimebaseService {

	private TimebaseDao timebaseDao; // timebaseDao的Dao

	public TimebaseDao getTimebaseDao() {
		return timebaseDao;
	}

	@Resource(name = "timebaseDao")
	public void setTimebaseDao(TimebaseDao timebaseDao) {
		super.setBaseHibernateDao(timebaseDao);
	}

	@Override
	public void saveOaTimebase(OaTimebase oaTimebase) throws Exception {
		// TODO Auto-generated method stub
		this.timebaseDao.saveOrUpdate(oaTimebase);
	}

}
