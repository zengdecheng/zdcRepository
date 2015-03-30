package com.xbd.erp.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.LazyDynaMap;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.erp.base.pojo.sys.Page;

@SuppressWarnings("all")
public interface BaseDao<T> {
	@Deprecated
	public void saveObject(Object o);

	@Deprecated
	public void delObject(Object o);

	@Deprecated
	public Object getObject(Class clazz, Serializable id);
	
	@Deprecated
	public List getObjectsByEql(String eql);
	
	@Deprecated
	public List getObjectsByEql(String eql, List params);
	
	@Deprecated
	public List getObjectsByEql(String eql, int begin, int size);
	
	@Deprecated
	public List getObjectsByEql(String eql, int begin, int size, List params);

	@Deprecated
	public List getObjectsBySql(FSPBean fsp);

	@Deprecated
	public List getObjectsByEql(FSPBean fsp);

	@Deprecated
	public Object getOnlyObjectByEql(FSPBean fsp);

	@Deprecated
	public LazyDynaMap getOnlyObjectBySql(FSPBean fsp);

	@Deprecated
	public Long getObjectsCountSql(FSPBean fsp);

	@Deprecated
	public abstract String parseFspBean2Sql(FSPBean fsp, List params);

	@Deprecated
	public abstract String parseFspBean2Eql(FSPBean fsp, List params);
	
	public abstract String getVoName();
	
	public abstract Class<?> getVoClass();
	
	
	/*------------------------------------------------------------------*/
	public Class<?> getEntityClass();

	/**
	 * <完整保存实体>
	 * 
	 * @param t
	 *            实体参数
	 */
	public void save(Object o);

	/**
	 * <批量保存实体>
	 * 
	 * @param os
	 *            实体数组对象
	 */
	public void save(Object[] os);

	/**
	 * <保存或更新实体>
	 * 
	 * @param t
	 *            实体
	 */
	public void saveOrUpdate(T t);

	/**
	 * <加载实体的load方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 */
	public T load(Serializable id);

	/**
	 * <查找的get方法>
	 * 
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 */
	public T get(Serializable id);

	/**
	 * <查找的get方法>
	 * 
	 * @param T
	 *            实体的类型
	 * @param id
	 *            实体的id
	 * @return 查询出来的实体
	 */
	public Object get(Class<?> T, Serializable id);

	/**
	 * <得到所有的实体>
	 * 
	 * @return 所有的实体
	 */
	public List<T> getAll();

	/**
	 * <得到所有的实体>
	 * 
	 * @param T
	 *            实体的类型
	 * @return 所有的实体
	 */
	public List<? extends Object> getAll(Class<?> T);

	/**
	 * <修改实体>
	 * 
	 * @param t
	 *            实体对象
	 */
	public void merge(Object o);

	/**
	 * <refresh>
	 * 
	 * @param t
	 *            实体
	 */
	public void refresh(Object o);

	/**
	 * <update>
	 * 
	 * @param t
	 *            实体
	 */
	public void update(Object o);

	/**
	 * <根据ID删除数据>
	 * 
	 * @param Id
	 *            实体id
	 * @return 是否删除成功
	 */
	public void removeById(Serializable id);

	/**
	 * <删除表中的t数据>
	 * 
	 * @param t
	 *            实体
	 */
	public void remove(Object o);

	/**
	 * <批量删除实体>
	 * 
	 * @param os
	 *            要删除的对象数组
	 */
	public void remove(Object[] os);

	/**
	 * <删除所有>
	 * 
	 * @param entities
	 *            实体的Collection集合
	 */
	public void removeAll(Collection<T> entities);

	/**
	 * <执行Sql语句>
	 * 
	 * @param sqlString
	 *            sql
	 * @param values
	 *            不定参数数组
	 */
	public Integer executeSql(String sql, Map<String, Object> params);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @return 影响的数量
	 */
	public Integer executeHql(String hql, Map<String, Object> params);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @return 符合条件的对象的数量
	 */
	public Integer countByHql(String hql, Map<String, Object> params);

	/**
	 * <根据SQL得到记录数>
	 * 
	 * @param sql
	 *            SQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 记录总数
	 */
	public Integer countBySql(String sql, Map<String, Object> params);

	public Integer countByCriteria(Class<?> T, Map<String, Object> params);

	public Integer countByDetachedCriteria(DetachedCriteria detachedCriteria);

	public List<? extends Object> findListByEntity(Class<?> T, int firstResult, int maxResults);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @return 实体对象L
	 */
	public T findUniqueByHql(String hql, Map<String, Object> params);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @return 实体对象L
	 */
	public Object findUniqueByHql(Class<?> T, String hql, Map<String, Object> params);

	/**
	 * <根据SQL语句查找唯一实体>
	 * 
	 * @param sql
	 *            SQL语句
	 * @param values
	 *            不定参数的Object数组
	 * @return 查询实体
	 */
	public T findUniqueBySql(String sql, Map<String, Object> params);

	public Object findUniqueBySql(Class<?> T, String sql, Map<String, Object> params);

	public T findUniqueByProperty(String name, Object value);

	public T findUniqueByProperty(Map<String, Object> params);

	public Object findUniqueByProperty(Class<?> T, String name, Object value);

	public Object findUniqueByProperty(Class<?> T, Map<String, Object> params);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @return 实体对象List
	 */
	public List<T> findListByHql(String hql, Map<String, Object> params);

	/**
	 * <通过键值对参数来查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            Map参数
	 * @param T
	 *            实体类型
	 * @return 实体对象List
	 */
	public List<? extends Object> findListByHql(Class<?> T, String hql, Map<String, Object> params);

	/**
	 * <通过键值对参数来分页查询>
	 * 
	 * @param hql
	 *            HQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param params
	 *            Map参数
	 * @return 符合条件的对象集合
	 */
	public List<T> findListByHql(String hql, int pageNo, int pageSize, Map<String, Object> params);

	public List<? extends Object> findListByHql(Class<?> T, String hql, int pageNo, int pageSize, Map<String, Object> params);

	/**
	 * <SQL分页查询>
	 * 
	 * @param Sql
	 *            SQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return 符合条件的对象集合
	 */
	public List<T> findListBySql(String sql, int pageNo, int pageSize, Map<String, Object> params);

	public List<? extends Object> findListBySql(Class<?> T, String sql, int pageNo, int pageSize, Map<String, Object> params);

	public List<T> findListBySql(String sql, Map<String, Object> params);

	public List<? extends Object> findListBySql(Class<?> T, String sql, Map<String, Object> params);

	public List<Map<String, Object>> findListMapBySql(String sql, int pageNo, int pageSize, Map<String, Object> params);

	public List<Map<String, Object>> findListMapBySql(String sql, Map<String, Object> params);

	public List<? extends Object> findListObjBySql(String sql, Map<String, Object> params);

	public List<? extends Object> findListObjBySql(String sql, int pageNo, int pageSize, Map<String, Object> params);

	public List<? extends Object[]> findListObjArrBySql(String sql, Map<String, Object> params);

	public List<Object[]> findListObjArrBySql(String sql, int pageNo, int pageSize, Map<String, Object> params);

	public List<T> findListByProperty(String name, Object value);

	public List<T> findListByProperty(Map<String, Object> params);

	public List<T> findListByProperty(Map<String, Object> params, int firstResult, int maxResults);

	public List<? extends Object> findListByProperty(Class<?> T, String name, Object value);

	public List<? extends Object> findListByProperty(Class<?> T, Map<String, Object> params);

	public List<? extends Object> findListByProperty(Class<?> T, Map<String, Object> params, int firstResult, int maxResults);

	public List<? extends Object> findInByProperty(Class<?> T, String propertyName, List<Object> values);

	public List<? extends Object> findLikeByProperty(Class<?> T, Map<String, Object> params);

	public List<T> findListByCriteria(DetachedCriteria detachedCriteria);

	public List<T> findListByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults);

	/**
	 * <HQL分页查询>
	 * @param hql
	 *            HQL语句
	 * @param countHql
	 *            查询记录条数的HQL语句
	 * @param pageNo
	 *            下一页
	 * @param pageSize
	 *            一页总条数
	 * @param values
	 *            不定Object数组参数
	 * @return Page的封装类，里面包含了页码的信息以及查询的数据List集合
	 */
	public Page findPageByHql(String hql, String countHql, int pageNo, int pageSize, Map<String, Object> params);

	public Page findPageBySql(String sql, String countSql, int pageNo, int pageSize, Map<String, Object> params);

	public Page findPageByCriteria(Criteria criteria, Integer pageNo, Integer pageSize);

	public Page findPageByDetachedCriteria(DetachedCriteria detache, Integer pageNo, Integer pageSize);
}
