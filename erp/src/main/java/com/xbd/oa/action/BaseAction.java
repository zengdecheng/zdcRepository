package com.xbd.oa.action;

import java.util.List;

import org.apache.commons.beanutils.LazyDynaMap;
import org.use.base.FSPBean;
import org.use.base.action.Action;
import org.use.base.annotation.EJB;
import org.use.base.manager.Manager;

import com.xbd.oa.business.BaseManager;
import com.xbd.oa.utils.XbdBuffer;

public class BaseAction extends Action {
	private static final long serialVersionUID = 1L;

	@EJB(name = "com.xbd.oa.business.impl.BxManagerImpl")
	public BaseManager manager;

	public List<LazyDynaMap> beans;
	public LazyDynaMap bean;

	protected FSPBean fsp = new FSPBean();

	private String systemEnvironment = XbdBuffer.getClothesSizesByPosition("systemEnvironment");

	public Manager getBiz() {
		return manager;
	}

	public List<LazyDynaMap> getBeans() {
		return beans;
	}

	public void setBeans(List<LazyDynaMap> beans) {
		this.beans = beans;
	}

	public LazyDynaMap getBean() {
		return bean;
	}

	public void setBean(LazyDynaMap bean) {
		this.bean = bean;
	}

	public FSPBean getFsp() {
		return fsp;
	}

	public void setFsp(FSPBean fsp) {
		this.fsp = fsp;
	}

	public String getSystemEnvironment() {
		return systemEnvironment;
	}
}
