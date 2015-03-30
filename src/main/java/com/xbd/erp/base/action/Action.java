package com.xbd.erp.base.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xbd.erp.base.dao.BaseDao;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.erp.base.pojo.sys.PageControls;
import com.xbd.oa.utils.XbdBuffer;

@SuppressWarnings("all")
public abstract class Action extends ActionSupport{
	private static final long serialVersionUID = 1L;

	public FSPBean fsp = new FSPBean();
	public List<LazyDynaMap> superList = new ArrayList<LazyDynaMap>();
	public String superBackflag;
	public List<LazyDynaMap> beans= new ArrayList<LazyDynaMap>();
	public LazyDynaMap bean= new LazyDynaMap();

	private String systemEnvironment = XbdBuffer.getClothesSizesByPosition("systemEnvironment");

	public abstract void setBaseDao(BaseDao<?> baseDao);
	public abstract BaseDao<?> getBaseDao();

	@Deprecated
	public void saveObject(Object o) {
		getBaseDao().saveObject(o);
	}

	@Deprecated
	public void delObject(Object o) {
		getBaseDao().delObject(o);
	}

	@SuppressWarnings("unchecked")
	@Deprecated
	public Object getObject(@SuppressWarnings("rawtypes") Class clazz, Serializable id) {
		return getBaseDao().getObject(clazz, id);
	}

	@Deprecated
	public Long getObjectsCountSql(FSPBean fsp) {
		return getBaseDao().getObjectsCountSql(fsp);
	}

	@Deprecated
	public Long getObjectsCountSql() {
		return getBaseDao().getObjectsCountSql(fsp);
	}

	@Deprecated
	public Long getObjectsCountEql() {
		return getBaseDao().getObjectsCountSql(fsp);
	}

	@Deprecated
	public List getObjectsBySql(FSPBean fsp) {
		return getBaseDao().getObjectsBySql(fsp);
	}

	@Deprecated
	public List getObjectsByEql(FSPBean fsp) {
		return getBaseDao().getObjectsByEql(fsp);
	}

	@Deprecated
	public void processPageInfoEql() {
		processPageInfo(getObjectsCountEql());
	}

	@Deprecated
	public void processPageInfoSql() {
		processPageInfo(getObjectsCountSql());
	}

	@Deprecated
	public void processPageInfo(long pageCount) {
		PageControls pageControls = new PageControls(fsp, pageCount);
		fsp.setRecordCount(pageCount);
		ServletActionContext.getRequest().setAttribute("pageControls", pageControls);
	}
	
	
	public String execute() throws Exception {
		return null;
	}
	protected HttpServletRequest getHttpRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public FSPBean getFsp() {
		return fsp;
	}

	public void setFsp(FSPBean fsp) {
		this.fsp = fsp;
	}

	public List<LazyDynaMap> getSuperList() {
		return superList;
	}

	public void setSuperList(List<LazyDynaMap> superList) {
		this.superList = superList;
	}

	public String getSuperBackflag() {
		return superBackflag;
	}

	public void setSuperBackflag(String superBackflag) {
		this.superBackflag = superBackflag;
	}

	public LazyDynaMap getBean() {
		return bean;
	}

	public void setBean(LazyDynaMap bean) {
		this.bean = bean;
	}

	public String getSystemEnvironment() {
		return systemEnvironment;
	}
}
