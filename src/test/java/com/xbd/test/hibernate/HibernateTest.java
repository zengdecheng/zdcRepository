//package com.xbd.test.hibernate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.beanutils.DynaBean;
//import org.apache.commons.beanutils.LazyDynaBean;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.xbd.erp.base.dao.BaseDao;
//import com.xbd.erp.base.dao.impl.BaseDaoImpl;
//import com.xbd.erp.base.dao.query.DaoQueryByXml;
//import com.xbd.erp.base.pojo.sys.FSPBean;
//import com.xbd.oa.dao.impl.BxDaoImpl;
//
//public class HibernateTest {
//	public Configuration cfg;
//	public SessionFactory sessionFactory;
//	
//	@Before
//	public void before(){
////		cfg = new Configuration().configure();
////		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
////		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
//	}
//	
//	@Test
//	public void createSQL(){
//	//	SchemaExport export = new SchemaExport(cfg);
//	//	export.create(false, true);
//	
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("aa", "aaa");
//		DynaBean bean = new LazyDynaBean();
//		System.out.println(bean.get("aa"));
//	}
//	
//	@Test
//	public void exportOut(){
//		FSPBean fsp = new FSPBean();
//		fsp.set(FSPBean.FSP_QUERY_BY_XML, BxDaoImpl.LIST_ORGWF_BY_SQL);
//		fsp.set(FSPBean.FSP_ORDER, " order by oo.begin_time desc");
//		fsp.set("org", "aaaa");
//		DaoQueryByXml xml = new DaoQueryByXml(true);
//		List params = new ArrayList();
//		BaseDao baseDao = new BaseDaoImpl();
//		String sql = xml.queryByXml(fsp, params, baseDao);
//		System.out.println(sql);
//		System.out.println(params);
//	}
//	
//	@Test
//	public void saveObject(){
////		Session session = sessionFactory.getCurrentSession();
////		Transaction tx = session.beginTransaction();
////		OaOrderDetail ood = new OaOrderDetail();
////		//Timestamp t = new Timestamp(new Date().getTime());
////		Timestamp t = new Timestamp(new Date().getTime()/1000*1000);
////		System.out.println(new Date().getTime());
////		System.out.println(t);
////		ood.setWfRealStart(t);
////		System.out.println("保存前："+ood.getWfRealStart());
////		session.save(ood);
////		System.out.println("保存后："+ood.getWfRealStart());
////		tx.commit();
////		
////		session = sessionFactory.getCurrentSession();
////		tx = session.beginTransaction();
////		OaOrderDetail oodDB=(OaOrderDetail) session.get(OaOrderDetail.class, ood.getId());
////		System.out.println("查询出："+oodDB.getWfRealStart());
////		tx.commit();
//	}
//	
//	public enum UrlFilter {  
//		login, putOrderNumOnPhone, viewOrderDetailOnPhone, noOrderNum  
//	} 
//	@Test
//	public void testEnum(){
//		String aa = "login";
//		
//		System.out.println(UrlFilter.noOrderNum);
//	}
//	
//	
//}
