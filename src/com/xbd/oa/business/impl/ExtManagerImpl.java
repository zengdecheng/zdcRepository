package com.xbd.oa.business.impl;
import org.use.base.annotation.EJB;
import org.use.base.dao.Dao;
import org.use.base.manager.impl.ManagerHibernate;

import com.xbd.oa.dao.BaseDao;
@SuppressWarnings("serial") 
public class ExtManagerImpl extends ManagerHibernate implements com.xbd.oa.business.BaseManager {
	@EJB(name="com.xbd.oa.dao.impl.ExtDaoImpl")
	private BaseDao dao;
	@Override
	public Dao getDao() {
		return dao;
	}
	
	
}