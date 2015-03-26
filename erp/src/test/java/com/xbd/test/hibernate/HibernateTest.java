package com.xbd.test.hibernate;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

import com.xbd.oa.vo.OaOrderDetail;

public class HibernateTest {
	public Configuration cfg;
	public SessionFactory sessionFactory;
	
	@Before
	public void before(){
		cfg = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		sessionFactory = cfg.buildSessionFactory(serviceRegistry);
	}
	
	@Test
	public void createSQL(){
		SchemaExport export = new SchemaExport(cfg);
		export.create(false, true);
	}
	
	@Test
	public void exportOut(){
		System.out.println(System.currentTimeMillis()/1000*1000);
	}
	
	@Test
	public void saveObject(){
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		OaOrderDetail ood = new OaOrderDetail();
		//Timestamp t = new Timestamp(new Date().getTime());
		Timestamp t = new Timestamp(new Date().getTime()/1000*1000);
		System.out.println(new Date().getTime());
		System.out.println(t);
		ood.setWfRealStart(t);
		System.out.println("保存前："+ood.getWfRealStart());
		session.save(ood);
		System.out.println("保存后："+ood.getWfRealStart());
		tx.commit();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		OaOrderDetail oodDB=(OaOrderDetail) session.get(OaOrderDetail.class, ood.getId());
		System.out.println("查询出："+oodDB.getWfRealStart());
		tx.commit();
	}
}
