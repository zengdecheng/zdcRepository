package com.xbd.erp.base.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	
	/**
     * 获取对象，如果没有找到指定的对象，则返回null
     * @param id
     * @return
     */
    public T get(Serializable id) ;
    
    /**
     * 获取对象，如果没有找到指定的对象，则返回null
     * @param entity
     * @param id
     * @return
     */
	public Object get(Class<?> T, Serializable id);
    
    /**
     * 获得所有实体
     * @return
     */
    public List<T> getAll() ;
    
    /**
     * 获得所有实体
     * 
     * @return
     * @throws OrmException
     */
    public List<?> getAll(Class<?> T) ;
    
    /**
     * 保存实体
     * @param o
     */
    public void save(Object o) ;
    public void save(Object... os);
    
    /**
     * 修改实体
     * @param o
     */
    public void merge(Object o) ;
    
    /**
     * 根据id删除实体
     * @param id
     */
    public void removeById(Serializable id) ;
    
}
