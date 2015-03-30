package com.xbd.erp.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Id;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.dao.query.DaoQueryByXml;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.erp.base.pojo.sys.Page;
import com.xbd.oa.utils.GenericsUtils;
import com.xbd.oa.utils.LOMSDynaBean;

@Repository("baseDaoImpl")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static Logger logger = Logger.getLogger(BaseDaoImpl.class);

	private final static DaoQueryByXml daoXml = new DaoQueryByXml(true);

	private SessionFactory sessionFactory;

	protected Class<T> entityClass;
	
	Collection<DynaBean> cle = null;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(this.getClass());
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}

	public Session getSession() throws HibernateException {
		return sessionFactory.getCurrentSession();
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	@Deprecated
	public void saveObject(Object o) {
		if (o != null) {
			getSession().saveOrUpdate(o);
		}
	}

	@Deprecated
	public void delObject(Object o) {
		if (o != null) {
			getSession().delete(o);
		}
	}

	@Deprecated
	public Object getObject(Class clazz, Serializable id) {
		return getSession().get(clazz, id);
	}

	@Deprecated
	public List getObjectsByEql(String eql) {
		return getObjectsByEql(eql, -1, -1);
	}

	@Deprecated
	public List getObjectsByEql(String eql, List params) {
		return getObjectsByEql(eql, -1, -1, params);
	}

	@Deprecated
	public List getObjectsByEql(String eql, int begin, int size) {
		return getObjectsByEql(eql, begin, size, null);
	}

	@Deprecated
	public List getObjectsByEql(String eql, int begin, int size, List params) {
		List<?> objs = null;
		Session s = getSession();
		org.hibernate.Query query = s.createQuery(eql);
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		if (begin > -1 && size > -1) {
			query.setMaxResults(size).setFirstResult(begin);
		}
		objs = query.list();
		return objs;
	}

	@Deprecated
	public List getObjectsBySql(String sql, Class clz) {
		return getObjectsBySql(sql, -1, -1, clz);
	}

	@Deprecated
	public List getObjectsBySql(String sql, int begin, int size, Class clz) {
		return getObjectsBySql(sql, begin, size, null, clz);
	}

	@Deprecated
	public List getObjectsBySql(String sql, List params, Class clz) {
		return getObjectsBySql(sql, -1, -1, params, clz);
	}

	@Deprecated
	public List getObjectsBySql(String sql, int begin, int size, List params, Class clz) {
		List objs = null;
		javax.persistence.Query query = null;
		logger.warn("FSP: sql " + sql);
		if (clz == null) {
			List<LazyDynaMap> list = new ArrayList<LazyDynaMap>();
			try {
				Collection<DynaBean> collection = query(sql, params, begin, size);
				list = changeCollectionToList(collection);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return list;
		} else {
			query = getEntityManager().createNativeQuery(sql, clz);
		}
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i + 1, params.get(i));
			}
		}
		if (begin > -1 && size > -1) {
			query.setMaxResults(size).setFirstResult(begin);
		}
		objs = query.getResultList();
		return objs;
	}

	@Deprecated
	public List getObjectsBySql(FSPBean fsp) {
		List<?> objs = new ArrayList();
		List<?> params = new ArrayList();
		String sql = "";
		String caseSql = "";
		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			sql = daoXml.queryByXml(fsp, params, this);
		} else {
			sql = "select * from " + getTableName() + " where 1=1 ";
			caseSql = parseFspBean2Sql(fsp, params);
			if (caseSql == null) {
				caseSql = "";
			}
			sql = sql + caseSql;
		}
		if (fsp.getMap().get(FSPBean.FSP_ORDER) != null) {
			sql = sql + fsp.getMap().get(FSPBean.FSP_ORDER);
		}
		if (fsp.isPagination()) {
			objs = getObjectsBySql(sql, fsp.getPageFirstResult(), fsp.getPageSize(), params, null);
		} else {
			objs = getObjectsBySql(sql, params, null);
		}
		return objs;
	}

	@Deprecated
	public Object getOnlyObjectByEql(FSPBean fsp) {
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.setPageNo(1);
		fsp.setPageSize(1);
		List<?> list = getObjectsByEql(fsp);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Deprecated
	public LazyDynaMap getOnlyObjectBySql(FSPBean fsp) {
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.setPageNo(1);
		fsp.setPageSize(1);
		List<LazyDynaMap> list = (List<LazyDynaMap>) getObjectsBySql(fsp);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Deprecated
	public List getObjectsByEql(FSPBean fsp) {
		List objs = new ArrayList();
		List params = new ArrayList();
		String eql = "";
		String caseSql = "";

		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			eql = daoXml.queryByXml(fsp, params, this);
		} else {
			eql = "select v from " + getVoName() + " v where 1=1 ";
			caseSql = parseFspBean2Eql(fsp, params);
		}
		if (caseSql == null) {
			caseSql = "";
		}
		eql = eql + caseSql;
		if (fsp.getMap().get(FSPBean.FSP_ORDER) != null) {
			eql = eql + fsp.getMap().get(FSPBean.FSP_ORDER);
		} else {
			try {
				if (fsp.get(FSPBean.FSP_QUERY_BY_XML) == null) {
					Field field = null;
					Field[] fields = getVoClass().newInstance().getClass().getDeclaredFields();
					for (Field f : fields) {
						if (f.isAnnotationPresent(Id.class)) {
							f.setAccessible(true);
							field = f;
						}
					}
					if (field != null) {
						eql = eql + " order by " + field.getName() + " desc";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (fsp.isPagination()) {
			objs = getObjectsByEql(eql, fsp.getPageFirstResult(), fsp.getPageSize(), params);
		} else {
			objs = getObjectsByEql(eql, params);
		}
		return objs;
	}

	@Deprecated
	public Long getObjectsCountSql(FSPBean fsp) {
		List params = new ArrayList();
		String sql = "";
		String caseSql = "";
		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			sql = daoXml.queryByXml(fsp, params, this);
		} else {
			sql = "select * from " + getTableName() + " where 1=1 ";
			caseSql = parseFspBean2Sql(fsp, params);
			if (caseSql == null) {
				caseSql = "";
			}
			sql = sql + caseSql;
		}
		sql = "select count(*) cnt from ( " + sql + " ) cnt_table_tmp";

		List<LazyDynaMap> list = null;
		try {
			Collection<DynaBean> collection = query(sql, params, -1, -1);
			list = changeCollectionToList(collection);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (list != null && list.size() > 0) {
			return (Long) list.get(0).get("cnt");
		}
		return new Long(0);
	}

	@Deprecated
	public EntityManager getEntityManager() {
		return null;
	}

	@Deprecated
	public String parseFspBean2Eql(FSPBean fsp, List params) {
		throw new RuntimeException("this is base dao ,not call parseFspBean2Eql method");
	}

	@Deprecated
	public String parseFspBean2Sql(FSPBean fsp, List params) {
		String caseSql = " and oa_org in ( " + " SELECT a.id FROM  oa_org a LEFT JOIN oa_org b ON a.pid =b.id  " + " left join oa_org c on b.pid=c.id  " + " left join oa_org d on c.pid=d.id " + " where 1=1              " + " AND(a.id=? or b.id=? or c.id=? or d.id=?) " + " )";

		String oaOrg = fsp.get("oaOrg").toString();
		if (null != params) {
			params.add(oaOrg);
			params.add(oaOrg);
			params.add(oaOrg);
			params.add(oaOrg);
		}
		return caseSql;
	}

	@Deprecated
	public String getTableName() {
		return "oa_staff";
	}

	@Deprecated
	public Class<?> getVoClass() {
		return null;
	}

	@Deprecated
	public String getVoName() {
		return "Bx";
	}

	private void formatParam(Object x, PreparedStatement pstmt)
			throws SQLException {
		ParamIndexC paramIndexC = new ParamIndexC();
		formatParamRecursion(x, pstmt,paramIndexC);
	}
	class ParamIndexC{
		Integer paramIndex = new Integer(1);
	}

	private void formatParamRecursion(Object x, PreparedStatement pstmt,ParamIndexC paramIndexC) throws SQLException {
		if (x instanceof Collection) {
			for (Iterator iter = ((Collection) x).iterator(); iter.hasNext();) {
				formatParamRecursion(iter.next(), pstmt,paramIndexC);
			}
		} else if (x == null) {
			pstmt.setObject(paramIndexC.paramIndex, null);
			paramIndexC.paramIndex++;
		} else if (x instanceof String) {
			if (((String) x).contains(",")) {
				pstmt.setObject(paramIndexC.paramIndex, (String) x);
			}
			pstmt.setString(paramIndexC.paramIndex, (String) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof java.sql.Date) {
			pstmt.setDate(paramIndexC.paramIndex, (java.sql.Date) x);
			paramIndexC.paramIndex++;
		}else if (x instanceof java.sql.Timestamp) {
			pstmt.setTimestamp(paramIndexC.paramIndex, (java.sql.Timestamp) x);
			paramIndexC.paramIndex++;
		}else if (x instanceof BigDecimal) {
			pstmt.setBigDecimal(paramIndexC.paramIndex, (BigDecimal) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof Integer) {
			pstmt.setInt(paramIndexC.paramIndex, (Integer) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof Boolean) {
			pstmt.setBoolean(paramIndexC.paramIndex, (Boolean) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof Long) {
			pstmt.setLong(paramIndexC.paramIndex, (Long) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof byte[]) {
			pstmt.setBytes(paramIndexC.paramIndex, (byte[]) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof Timestamp) {
			pstmt.setTimestamp(paramIndexC.paramIndex, (Timestamp) x);
			paramIndexC.paramIndex++;
		} else if (x instanceof DynaBean) {
			Collection c = changeDynaBeanToCollection((DynaBean) x);
			formatParamRecursion(c, pstmt,paramIndexC);
		}
	}
	
	private Collection changeDynaBeanToCollection(DynaBean bean) {
		List list = new ArrayList();
		DynaProperty[] dp = bean.getDynaClass().getDynaProperties();
		for (int i = 0; i < dp.length; i++) {
			DynaProperty property = dp[i];
			list.add(bean.get(property.getName()));
		}
		return list;
	}
	
	
	public Collection<DynaBean> query(String sql, Object param, long begin, long size) throws Exception {
		StringBuffer buf = new StringBuffer();
		if (begin > -1 && size > -1) {
			buf.append(sql).append(" LIMIT ").append(begin).append(",").append(size);
		}else{
			buf.append(sql);
		}
		return query(buf.toString(), param);
	}
	
	public Collection<DynaBean> query(final String sql, final Object param) throws Exception {
		getSession().doWork(new Work(){  
			public void execute(Connection conn) throws SQLException{  
				PreparedStatement ps = null;
				ResultSet rs = null;
				ps = conn.prepareStatement(sql);
				formatParam(param, ps);
				rs = ps.executeQuery();
				RowSetDynaClass rsdc = new RowSetDynaClass(rs);
				cle = rsdc.getRows();
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}  
		});
		return cle;
	}
	
	private static List<LazyDynaMap> changeCollectionToList(Collection<DynaBean> cle) {
		List<LazyDynaMap> list = new ArrayList<LazyDynaMap>();
		for (DynaBean bean : cle) {
			LOMSDynaBean lBean = new LOMSDynaBean();
			DynaProperty[] dp = bean.getDynaClass().getDynaProperties();
			for (int i = 0; i < dp.length; i++) {
				DynaProperty property = dp[i];
				if (bean.get(property.getName()) != null) {
					lBean.set(property.getName(), bean.get(property.getName()));
				}

			}
			list.add(lBean);
		}
		return list;
	}

	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/

	public void save(Object o) {
		this.getSession().save(o);
	}

	public void save(Object[] os) {
		Session session = this.getSession();
		for (int i = 0; i < os.length; i++) {
			session.save(os[i]);
			if ((i + 1) % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
	}

	public T load(Serializable id) {
		return (T) this.getSession().load(entityClass, id);
	}

	public T get(Serializable id) {
		return (T) this.getSession().get(entityClass, id);
	}

	public Object get(Class<?> T, Serializable id) {
		return (T) this.getSession().get(T, id);
	}

	public List<T> getAll() {
		return this.getSession().createQuery("from " + entityClass.getName()).list();
	}

	public List<? extends Object> getAll(Class<?> T) {
		return this.getSession().createQuery("from " + T.getName()).list();
	}

	public void merge(Object o) {
		this.getSession().merge(o);
	}

	public void refresh(Object o) {
		this.getSession().refresh(o);
	}

	public void update(Object o) {
		this.getSession().update(o);
	}

	public void removeById(Serializable id) {
		this.remove(this.get(id));
	}

	public void remove(Object o) {
		this.getSession().delete(o);
	}

	public void remove(Object[] os) {
		Session session = this.getSession();
		for (int i = 0; i < os.length; i++) {
			session.delete(os[i]);
			if ((i + 1) % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void removeAll(Collection<T> entities) {
		executeHql("delete from " + entityClass.getName(), null);
	}

	public Integer executeSql(String sql, Map<String, Object> params) {
		return this.createSQLObjQuery(sql, params).executeUpdate();
	}

	public Integer executeHql(String hql, Map<String, Object> params) {
		return this.createHQLQuery(hql, params).executeUpdate();
	}

	public Integer countByHql(String hql, Map<String, Object> params) {
		Query query = this.createHQLQuery(hql, params);
		try {
			return (Integer) query.uniqueResult();
		} catch (Exception e) {
			return query.list().size();
		}
	}

	public Integer countBySql(String sql, Map<String, Object> params) {
		Query query = this.createSQLObjQuery(sql, params);
		try {
			return (Integer) query.uniqueResult();
		} catch (Exception e) {
			return query.list().size();
		}
	}

	public Integer countByCriteria(Class<?> T, Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(T);
		for (String key : params.keySet()) {
			criteria.add(Restrictions.like(key, params.get(key)));
		}
		criteria.setProjection(Projections.rowCount());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}

	public Integer countByDetachedCriteria(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
		int total = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		// criteria.setProjection(null);
		// criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return total;
	}

	public List<? extends Object> findListByEntity(Class<?> T, int firstResult, int maxResults) {
		Criteria criteria = this.getSession().createCriteria(T);
		return criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public T findUniqueByHql(String hql, Map<String, Object> params) {
		return (T) this.createHQLQuery(hql, params).uniqueResult();
	}

	public Object findUniqueByHql(Class<?> T, String hql, Map<String, Object> params) {
		return (T) this.createHQLQuery(hql, params).uniqueResult();
	}

	public T findUniqueBySql(String sql, Map<String, Object> params) {
		return (T) this.createSQLEntityQuery(sql, params).uniqueResult();
	}

	public Object findUniqueBySql(Class<?> T, String sql, Map<String, Object> params) {
		return (T) this.createSQLObjQuery(sql, params).uniqueResult();
	}

	public T findUniqueByProperty(String name, Object value) {
		Criteria cr = this.getSession().createCriteria(this.getEntityClass());
		cr.add(Restrictions.eq(name, value));
		return (T) cr.uniqueResult();
	}

	public T findUniqueByProperty(Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass());
		for (String key : params.keySet()) {
			criteria.add(Restrictions.like(key, params.get(key)));
		}
		return (T) criteria.uniqueResult();
	}

	public Object findUniqueByProperty(Class<?> T, String name, Object value) {
		Criteria cr = this.getSession().createCriteria(T);
		cr.add(Restrictions.eq(name, value));
		return (T) cr.uniqueResult();
	}

	public Object findUniqueByProperty(Class<?> T, Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(T);
		for (String key : params.keySet()) {
			criteria.add(Restrictions.like(key, params.get(key)));
		}
		return (T) criteria.uniqueResult();
	}

	public List<T> findListByHql(String hql, Map<String, Object> params) {
		return this.createHQLQuery(hql, params).list();
	}

	public List<? extends Object> findListByHql(Class<?> T, String hql, Map<String, Object> params) {
		return (List<T>) this.createHQLQuery(hql, params).list();
	}

	public List<T> findListByHql(String hql, int pageNo, int pageSize, Map<String, Object> params) {
		return this.createHQLQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<? extends Object> findListByHql(Class<?> T, String hql, int pageNo, int pageSize, Map<String, Object> params) {
		return (List<T>) this.createHQLQuery(hql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<T> findListBySql(String sql, int pageNo, int pageSize, Map<String, Object> params) {
		return this.createSQLEntityQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<? extends Object> findListBySql(Class<?> T, String sql, int pageNo, int pageSize, Map<String, Object> params) {
		return (List<T>) this.createSQLEntityQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<T> findListBySql(String sql, Map<String, Object> params) {
		return this.createSQLEntityQuery(sql, params).list();
	}

	public List<? extends Object> findListBySql(Class<?> T, String sql, Map<String, Object> params) {
		return (List<T>) this.createSQLEntityQuery(sql, params).list();
	}

	public List<Map<String, Object>> findListMapBySql(String sql, int pageNo, int pageSize, Map<String, Object> params) {
		return this.createSQLMapQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<Map<String, Object>> findListMapBySql(String sql, Map<String, Object> params) {
		return this.createSQLMapQuery(sql, params).list();
	}

	public List<? extends Object> findListObjBySql(String sql, Map<String, Object> params) {
		return this.createSQLObjQuery(sql, params).list();
	}

	public List<? extends Object> findListObjBySql(String sql, int pageNo, int pageSize, Map<String, Object> params) {
		return this.createSQLObjQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<? extends Object[]> findListObjArrBySql(String sql, Map<String, Object> params) {
		return this.createSQLObjQuery(sql, params).list();
	}

	public List<Object[]> findListObjArrBySql(String sql, int pageNo, int pageSize, Map<String, Object> params) {
		return this.createSQLObjQuery(sql, params).setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	public List<T> findListByProperty(String name, Object value) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.eq(name, value));
		return criteria.list();
	}

	public List<T> findListByProperty(Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.allEq(params));
		return criteria.list();
	}

	public List<T> findListByProperty(Map<String, Object> params, int firstResult, int maxResults) {
		Criteria criteria = this.getSession().createCriteria(this.entityClass);
		criteria.add(Restrictions.allEq(params));
		return criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public List<? extends Object> findListByProperty(Class<?> T, String name, Object value) {
		Criteria criteria = this.getSession().createCriteria(T);
		criteria.add(Restrictions.eq(name, value));
		return (List<T>) criteria.list();
	}

	public List<? extends Object> findListByProperty(Class<?> T, Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(T);
		criteria.add(Restrictions.allEq(params));
		return (List<T>) criteria.list();
	}

	public List<? extends Object> findListByProperty(Class<?> T, Map<String, Object> params, int firstResult, int maxResults) {
		Criteria criteria = this.getSession().createCriteria(T);
		criteria.add(Restrictions.allEq(params));
		return (List<T>) criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public List<? extends Object> findInByProperty(Class<?> T, String propertyName, List<Object> values) {
		Criteria criteria = this.getSession().createCriteria(T);
		criteria.add(Restrictions.in(propertyName, values));
		return (List<T>) criteria.list();
	}

	public List<? extends Object> findLikeByProperty(Class<?> T, Map<String, Object> params) {
		Criteria criteria = this.getSession().createCriteria(T);
		for (String key : params.keySet()) {
			criteria.add(Restrictions.like(key, params.get(key)));
		}
		return (List<T>) criteria.list();
	}

	public List<T> findListByCriteria(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
		return criteria.list();
	}

	public List<T> findListByCriteria(DetachedCriteria detachedCriteria, int firstResult, int maxResults) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
		return criteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public Page findPageByHql(String hql, String countHql, int pageNo, int pageSize, Map<String, Object> params) {
		Integer total = this.countByHql(countHql, params);
		Query query = this.createHQLQueryForPage(hql, pageSize, pageNo, params);
		if (total == null)
			total = 0;
		return new Page(total, pageNo, pageSize, query.list());
	}

	public Page findPageBySql(String sql, String countSql, int pageNo, int pageSize, Map<String, Object> params) {
		Integer total = this.countBySql(countSql, params);
		Query query = this.createSQLQueryForPage(sql, pageSize, pageNo, params);
		if (total == null)
			total = 0;
		return new Page(total, pageNo, pageSize, query.list());
	}

	public Page findPageByCriteria(Criteria criteria, Integer pageNo, Integer pageSize) {
		Integer totalRows = criteria.list().size();
		criteria.setFirstResult((pageNo - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		return new Page(totalRows, pageNo, pageSize, criteria.list());
	}

	public Page findPageByDetachedCriteria(DetachedCriteria detache, Integer pageNo, Integer pageSize) {
		Criteria cr = detache.getExecutableCriteria(this.getSession());
		Integer totalRows = cr.list().size();
		cr.setFirstResult((pageNo - 1) * pageSize);
		cr.setMaxResults(pageSize);
		return new Page(totalRows, pageNo, pageSize, cr.list());
	}

	private Query createSQLQueryForPage(String sql, Integer pageNo, Integer pageSize, Map<String, Object> params) {
		Query query = this.createSQLEntityQuery(sql, params);
		if (pageSize != null && pageNo != null)
			this.queryForPage(query, pageSize, pageNo);
		return query;
	}

	private Query createHQLQueryForPage(String hql, Integer pageNo, Integer pageSize, Map<String, Object> params) {
		Query query = this.createHQLQuery(hql, params);
		if (pageSize != null && pageNo != null)
			this.queryForPage(query, pageSize, pageNo);
		return query;
	}

	private void queryForPage(Query query, Integer pageNo, Integer pageSize) {
		Integer firstResult = pageSize * (pageNo - 1);
		Integer maxResult = pageSize * pageNo;
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
	}

	private Query createHQLQuery(String hql, Map<String, Object> params) {
		return this.setParameter(this.getSession().createQuery(hql), params);
	}

	private Query createSQLObjQuery(String sql, Map<String, Object> params) {
		return this.setParameter(this.getSession().createSQLQuery(sql), params);
	}

	private Query createSQLMapQuery(String sql, Map<String, Object> params) {
		return this.setParameter(this.getSession().createSQLQuery(sql), params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	}

	private Query createSQLEntityQuery(String sql, Map<String, Object> params) {
		return this.setParameter(this.getSession().createSQLQuery(sql).addEntity(this.getEntityClass()), params);
	}

	private Query setParameter(Query query, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				Object obj = params.get(key);
				if (obj instanceof Collection<?>) {
					query.setParameterList(key, (Collection<?>) obj);
				} else if (obj instanceof Object[]) {
					query.setParameterList(key, (Object[]) obj);
				} else {
					query.setParameter(key, obj);
				}
			}
		}
		return query;
	}
}
