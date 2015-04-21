package com.xbd.erp.timebaseentry.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.impl.BaseDaoImpl;
import com.xbd.erp.timebaseentry.dao.TimebaseEntryDao;
import com.xbd.oa.vo.OaTimebaseEntry;

@Repository("timebaseEntryDao")
public class TimebaseEntryDaoImpl extends BaseDaoImpl<OaTimebaseEntry> implements TimebaseEntryDao {

}
