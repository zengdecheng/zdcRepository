package com.xbd.erp.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.service.BaseService;

public class BaseServiceImpl<T> implements BaseService<T> {

	public static final Logger logger = Logger.getLogger(BaseServiceImpl.class);

	private BaseDao<T> baseDao;

	public void setBaseHibernateDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	public T get(Serializable id) {
		return this.baseDao.get(id);
	}
	
	public Object get(Class<?> T, Serializable id) {
		return this.baseDao.get(T , id);
	}
	
	public List<T> getAll() {
		return this.baseDao.getAll();
	}

	public List<?> getAll(Class<?> T) {
		return this.baseDao.getAll(T);
	}
	
	public void save(Object o) {
		this.baseDao.save(o);
	}

	public void save(Object... os) {
		this.baseDao.save(os);
	
	}
	
	public void merge(Object o) {
		this.baseDao.merge(o);
	}

	public void remove(Object o) {
		this.baseDao.remove(o);
	}

	public void removeById(Serializable id) {
		this.baseDao.removeById(id);
	}
}
