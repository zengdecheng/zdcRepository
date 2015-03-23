package com.xbd.test.hibernate;

import java.util.Date;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

public class HibernateTest {

	@Test
	public void createSQL(){
		Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		export.create(false, true);
	}
	
	@Test
	public void createSQL1(){
		System.out.println((new Date()).getTime());
	}
}
