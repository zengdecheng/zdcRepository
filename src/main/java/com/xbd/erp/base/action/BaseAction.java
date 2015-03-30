package com.xbd.erp.base.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.xbd.oa.utils.DateUtil;

public abstract class BaseAction<T> extends ActionSupport implements ServletContextAware, ServletResponseAware, ServletRequestAware, SessionAware {

	private static final long serialVersionUID = 1L;

	protected ServletContext servletContext;

	protected HttpServletRequest httpServletRequest;

	protected HttpServletResponse httpServletResponse;

	protected HttpSession httpSession;

	protected Map<String, Object> session;

	public abstract String page4list() throws Exception;

	public abstract String page4add() throws Exception;

	public abstract String page4update() throws Exception;

	public abstract String add() throws Exception;

	public abstract String update() throws Exception;

	public abstract String delete() throws Exception;

	public static final Logger logger = Logger.getLogger(BaseAction.class);

	public void writeJson(Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, DateUtil.ALL_24H);
			httpServletResponse.setContentType("text/plain;charset=utf-8");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
			httpServletResponse.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.httpServletResponse = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.httpServletRequest = request;
		this.httpSession = request.getSession();
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	protected Integer getPageSize() {
		String pageSize = this.httpServletRequest.getParameter("pageSize");
		return StringUtils.isBlank(pageSize) ? 10 : Integer.valueOf(pageSize);
	}

	public Integer getPageNo() {
		String pageNo = this.httpServletRequest.getParameter("pageNo");
		return StringUtils.isBlank(pageNo) ? 1 : Integer.valueOf(pageNo);
	}

}
