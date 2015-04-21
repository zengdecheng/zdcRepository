package com.xbd.erp.timebase.dao.impl;

import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.impl.BaseDaoImpl;
import com.xbd.erp.timebase.dao.TimebaseDao;
import com.xbd.oa.vo.OaTimebase;

@Repository("timebaseDao")
public class TimebaseDaoImpl extends BaseDaoImpl<OaTimebase> implements TimebaseDao {

}
