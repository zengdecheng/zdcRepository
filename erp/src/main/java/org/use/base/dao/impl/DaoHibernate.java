package org.use.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.Query;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.use.base.FSPBean;
import org.use.base.annotation.helper.AnnoHelper;
import org.use.base.daoquery.DaoQueryByXml;
import org.use.base.utils.BeanFactory;
import org.use.base.utils.base.RegularUtils;
import org.use.base.utils.database.DataAccessManager;
import org.use.base.utils.database.PoolMySqlManager;
import org.use.base.utils.exception.TtcException;

public abstract class DaoHibernate extends DaoEjb {
	private final static String EQL_COUNT_REGULAR = "^\\s*select\\s*(\\w+[\\.\\w+]*)[,\\w+[\\.\\w+]*]*\\s*from(.*)";
	
	private static final SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(DaoHibernate.class);


	static {
		try {

			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	private DataAccessManager dm;

	private static DaoQueryByXml daoXml = new DaoQueryByXml(true);

	public static Session getSession() throws HibernateException {
		return sessionFactory.openSession();
	}

	public static SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}
	public void emFlush() {
		throw new RuntimeException("this method is n't come true");
	}
	public EntityTransaction getTransaction() {
		//return getEntityManager().getTransaction();
		throw new RuntimeException("this method is n't come true");
	}
	

	public void commitTransaction() {
		throw new RuntimeException("this method is n't come true");
	}

	public void delObject(Object o) {
		//Object id = AnnoHelper.getAnnoContent(o, Id.class);
		//Object obj = getSession().get(o.getClass(),(Integer)id);
		//getEntityManager().remove(obj);
		org.hibernate.Session s = getSession();
//		s.setFlushMode(FlushMode.MANUAL);
		org.hibernate.Transaction t = s.beginTransaction();
		s.delete(o);
		t.commit();
//		s.clear();
//		s.flush();
	}

	public Object getObject(Class clazz, Object id) {
		//有问题
		org.hibernate.Session s = getSession();
		org.hibernate.Transaction t = s.beginTransaction();
		Object object = s.get(clazz, (Serializable)id);
		t.commit();
		return object;
	}

	public List getObjectsByEql(String eql) {
		return getObjectsByEql(eql, -1, -1);
	}

	public List getObjectsByEql(String eql, List params) {
		return getObjectsByEql(eql, -1, -1, params);
	}

	public List getObjectsByEql(String eql, int begin, int size) {
		return getObjectsByEql(eql, begin, size, null);
	}

	public List getObjectsByEql(String eql, int begin, int size, List params) {
		/*List objs = null;
		Query query = getEntityManager().createQuery(eql);
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i + 1, params.get(i));
			}
		}
		if (begin > -1 && size > -1) {
			query.setMaxResults(size).setFirstResult(begin);
		}
		objs = query.getResultList();
		return objs;*/
		List objs = null;
//		org.hibernate.Query query = getSession().createQuery(eql);
		org.hibernate.Session s = getSession();
		org.hibernate.Transaction t = s.beginTransaction();
		org.hibernate.Query query = s.createQuery(eql);
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				//query.setParameter(i+1, params.get(i));
				query.setParameter(i, params.get(i));
			}
		}
		if (begin > -1 && size > -1) {
			query.setMaxResults(size).setFirstResult(begin);
		}
		objs = query.list();
		t.commit();
		return objs;
	}

	public List getObjectsBySql(String sql, Class clz) {
		return getObjectsBySql(sql, -1, -1, clz);
	}

	public List getObjectsBySql(String sql, int begin, int size, Class clz) {
		return getObjectsBySql(sql, begin, size, null, clz);
	}

	public List getObjectsBySql(String sql, List params, Class clz) {
		return getObjectsBySql(sql, -1, -1, params, clz);
	}

	public List getObjectsBySql(String sql, int begin, int size, List params,
			Class clz) {
		List objs = null;
		Query query = null;
//		log.debug("fuying sql " + sql);
		log.warn("FSP: sql " + sql);
		if (clz == null) {
			// 采用自定义的lazydynamap的list发布到前台

			return getDm().queryForList(sql, params, begin, size);

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

	public List getObjectsBySql(FSPBean fsp) {
		List objs = new ArrayList();
		List params = new ArrayList();
		String sql = "";
		String caseSql = "";
		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			// begin--加入xml配置sql语句
			sql = daoXml.queryByXml(fsp, params, this);
			// end--加入xml配置sql语句
		} else {
			sql = "select * from " + getTableName() + " where 1=1 ";
			caseSql = parseFspBean2Sql(fsp, params);
			if (caseSql == null) {
				caseSql = "";
			}
			sql = sql + caseSql;
		}

		/*
		 * try { c = Class.forName(getVoName()); } catch (ClassNotFoundException
		 * e) { e.printStackTrace(); throw new RuntimeException(e); }
		 */
		if (fsp.getMap().get(FSPBean.FSP_ORDER) != null) {
			sql = sql + fsp.getMap().get(FSPBean.FSP_ORDER);
		}
		if (fsp.isPagination()) {
			objs = getObjectsBySql(sql, fsp.getPageFirstResult(), fsp
					.getPageSize(), params, null);
		} else {
			objs = getObjectsBySql(sql, params, null);
		}
		return objs;
	}

	public Long getObjectsCount(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveObject(Object o) {
		/*Object object = AnnoHelper.getAnnoContent(o, Id.class);
		if (object != null) {
			getEntityManager().merge(o);
		} else {
			getEntityManager().persist(o);
		}*/
		// modify by hongliang on 2009-12-22 下午02:34:26 comments:添加transaction，不然更新操作无效
		org.hibernate.Session s = getSession();
//		s.setFlushMode(FlushMode.MANUAL);
		org.hibernate.Transaction t = s.beginTransaction();
		
		Object object = AnnoHelper.getAnnoContent(o, Id.class);
		if (object != null) {
			s.update(o);
		} else {
			s.save(o);
		}
		t.commit();
//		s.clear();
//		s.flush();
		
	}
	public void saveObjectFlush(Object o) {
		
		// modify by hongliang on 2009-12-23 下午02:50:41 comments:修改为mysql+hibername使用
		org.hibernate.Session s = getSession();
		org.hibernate.Transaction t = s.beginTransaction();
		
		Object object = AnnoHelper.getAnnoContent(o, Id.class);
		if (object != null) {
			s.merge(o);
		} else {
			s.persist(o);
		}
		t.commit();
		s.flush();
		/*
		Object object = AnnoHelper.getAnnoContent(o, Id.class);
		if (object != null) {
			getEntityManager().merge(o);
		} else {
			getEntityManager().persist(o);
		}
		getEntityManager().flush();
		*/
	}
	public Object getOnlyObjectByEql(FSPBean fsp){
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.setPageNo(1);
		fsp.setPageSize(1);
		List list = getObjectsByEql(fsp);
		if(list!= null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public LazyDynaMap getOnlyObjectBySql(FSPBean fsp){
		fsp.setPageFlag(FSPBean.ACTIVE_PAGINATION);
		fsp.setPageNo(1);
		fsp.setPageSize(1);
		List<LazyDynaMap> list = getObjectsBySql(fsp);
		if(list!= null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	public List getObjectsByEql(FSPBean fsp) {
		List objs = new ArrayList();
		List params = new ArrayList();
		String eql = "";
		String caseSql = "";
		
		if(fsp.get(FSPBean.FSP_QUERY_BY_XML) != null){
// begin--加入xml配置sql语句
			eql = daoXml.queryByXml(fsp, params, this);
			
// end--加入xml配置sql语句
		}
		else{
		 eql = "select v from " + getVoName() + " v where 1=1 ";
		 caseSql = parseFspBean2Eql(fsp, params);
		}
		if (caseSql == null) {
			caseSql = "";
		}
		eql = eql + caseSql;
		if(fsp.getMap().get(FSPBean.FSP_ORDER) != null ){
			eql = eql + fsp.getMap().get(FSPBean.FSP_ORDER);
		}else{
			try {
				if(fsp.get(FSPBean.FSP_QUERY_BY_XML) == null){
					Field field = AnnoHelper.getAnnoField(getVoClass().newInstance(), Id.class);
						if(field != null ){
							eql = eql + " order by "+field.getName()+" desc";
						}
				}
			} catch (Exception e) {
				throw new TtcException(e);
			}
		}
		if (fsp.isPagination()) {
			objs = getObjectsByEql(eql, fsp.getPageFirstResult(), fsp
					.getPageSize(), params);
		} else {
			objs = getObjectsByEql(eql, params);
		}
		return objs;
	}

	public Long getObjectsCountEql(FSPBean fsp) {
		List params = new ArrayList();
		String eql = "";
		String caseSql = "";

		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			// begin--加入xml配置sql语句
			eql = daoXml.queryByXml(fsp, params, this);
			String[] ss;
			try {
				ss = RegularUtils.getMatcherPart(EQL_COUNT_REGULAR, eql);
				if(ss.length==3){
					eql = "select count("+ss[1]+") from "+ss[2];
				}else{
					throw new TtcException("eql is not count:"+eql);
				}
			} catch (MalformedPatternException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new TtcException(e);
			}
			
			
			//eql = "select count(r) from IN ( " + eql + " ) r";
			// end--加入xml配置sql语句

		} else {
			eql = "select count(v) from " + getVoName() + " v where 1=1 ";
			caseSql = parseFspBean2Eql(fsp, params);
			if (caseSql == null) {
				caseSql = "";
			}
			eql = eql + caseSql;
		}

		org.hibernate.Session s = getSession();
		org.hibernate.Transaction t = s.beginTransaction();
//		org.hibernate.Query query = getSession().createQuery(eql);
		org.hibernate.Query query = s.createQuery(eql);
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				//query.setParameter(i + 1, params.get(i));
				query.setParameter(i, params.get(i));
			}
		}
		Object result = query.uniqueResult();
		t.commit();
		return new Long(result.toString());
	}

	public Long getObjectsCountSql(FSPBean fsp) {
		List params = new ArrayList();
		String sql = "";
		String caseSql = "";
		if (fsp.get(FSPBean.FSP_QUERY_BY_XML) != null) {
			// begin--加入xml配置sql语句
			sql = daoXml.queryByXml(fsp, params, this);
			// end--加入xml配置sql语句
		} else {
			sql = "select * from " +getTableName() + " where 1=1 ";
			caseSql = parseFspBean2Sql(fsp, params);
			if (caseSql == null) {
				caseSql = "";
			}
			sql = sql + caseSql;
		}

		// 加入求count语句
		sql = "select count(*) cnt from ( " + sql + " ) cnt_table_tmp";

		List<LazyDynaMap> list = getDm().queryForList(sql, params, -1, -1);
		if(list != null && list.size() >0 ){
			return  (Long)list.get(0).get("cnt");
		}
		return new Long(0);
	}

	/**
	 * fsp:前台的查询参数 name:fsp的map中参数的名称
	 * 
	 * 
	 * 此方法用于界面的查询变量，如fsp.map.name到后台之后value从String变成了String[]， 然后根据vo的类型转换变量类型
	 */

	public Object dealFspEqlLike(FSPBean fsp, String name) {
		Object object = dealFspEql(fsp, name);
		if (object instanceof String) {
			String value = (String) object;
			return "%" + value + "%";
		}
		throw new RuntimeException(
				"the value is not String type , is n't allowed use like %%");
	}

	/**
	 * fsp:前台的查询参数 name:fsp的map中参数的名称 convertFlag是否进行系统转换,也不再从true进行，false是不进行
	 * 
	 * 此方法用于界面的查询变量，如fsp.map.name到后台之后value从String变成了String[]， 然后根据vo的类型转换变量类型
	 */

	public Object dealFspEql(FSPBean fsp, String name) {
		Object object = fsp.getMap().get(name);
		Object valueObj = null;
		Field field = null;
		if (object == null) {
			return null;
		}
		if (object instanceof String[]) {
			String[] ss = (String[]) object;
			if (ss[0] != null) {
				valueObj = ss[0].trim();
			}
		} else if (object instanceof String) {
			valueObj = ((String) object).trim();
		} else {
			valueObj = object;
		}
		try {
			if (valueObj == null || valueObj.equals("")) {
				return null;
			}
			//适用于base类
			if(getVoClass() == null) return valueObj;
			// 判断vo里面是否有此属性
			boolean hasFiled = false;
			for (Field declaredField : getVoClass().getDeclaredFields()) {
				if (declaredField.getName().equals(name)) {
					hasFiled = true;
					break;
				}
			}
			if (hasFiled) {
				field = getVoClass().getDeclaredField(name);
			}
			if (field == null)
				return valueObj;
			if (valueObj instanceof String) {
				String value = (String) valueObj;

				value = value.trim();
				if (field.getType().equals(Integer.class)) {
					return Integer.valueOf(value);
				} else if (field.getType().equals(Float.class)) {
					return Float.valueOf(value);
				} else if (field.getType().equals(Long.class)) {
					return Long.valueOf(value);
				} else if (field.getType().equals(Double.class)) {
					return Double.valueOf(value);
				} else if (field.getType().equals(BigDecimal.class)) {
					return new BigDecimal(value);
				} else

				{
					return value;
				}
			} else {
				return valueObj;
			}
		} catch (Exception e) {
			throw new RuntimeException("not convert the value:" + object
					+ " to type:" + field);
		}
	}

	public DataAccessManager getDm() {
		if (dm == null) {
			dm = (DataAccessManager) BeanFactory
					.getBean(PoolMySqlManager.class);
		}
		return dm;
	}

	public abstract EntityManager getEntityManager();

	public abstract String parseFspBean2Eql(FSPBean fsp, List params);

	public abstract String parseFspBean2Sql(FSPBean fsp, List params);

	public abstract String getVoName();

	public abstract String getTableName();

	public abstract Class getVoClass();
}
