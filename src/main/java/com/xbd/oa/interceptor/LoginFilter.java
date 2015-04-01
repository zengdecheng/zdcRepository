package com.xbd.oa.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xbd.oa.vo.OaStaff;

public class LoginFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	private static final List<String> IGNORE_METHOD = new ArrayList<String>();

	static {
		IGNORE_METHOD.add("/bx/login");
		IGNORE_METHOD.add("/bx/putOrderNumOnPhone");
		IGNORE_METHOD.add("/bx/viewOrderDetailOnPhone");
		IGNORE_METHOD.add("/bx/noOrderNum");
		IGNORE_METHOD.add("/bx/notification");
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpSession session = request.getSession();
		String url = request.getServletPath();
		String contextPath = request.getContextPath();
		response.setHeader("Access-Control-Allow-Origin", "*");
		
		if(url.contains(".") || isFilter(url)){
			chain.doFilter(sRequest, sResponse);
		}else{
			OaStaff sysStaff = (OaStaff) session.getAttribute("session_login_bx");
			if (sysStaff == null) {// 转入管理员登陆页面
				response.sendRedirect(contextPath + "/");
				return;
			}else {
				chain.doFilter(sRequest, sResponse);
			}
		}
	}
	
	public Boolean isFilter(String url){
		for(String str:IGNORE_METHOD){
			if(url.startsWith(str)){
				return true;
			}
		}
		return false;
	}
}

