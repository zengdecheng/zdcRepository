package com.xbd.oa.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xbd.oa.utils.WebUtil;

/**
 * <B>功能简述</B><br>
 * 功能详细描述
 * 
 * @author hongliang
 * @see [相关类或方法]
 * @since [产品/模块版本]
 */
public class LoginInterceptor extends AbstractInterceptor {

	public static final Logger logger = Logger.getLogger(LoginInterceptor.class);
	private static final long serialVersionUID = 6386740305263305557L;

	private static final String NEED_LOGIN_AJAX = "ajaxLogin";
	private static final String NEED_LOGIN_FT = "shopLogin";
	private static final String NEED_LOGIN_FX = "factoryLogin";
	private static final String NEED_LOGIN_BX = "adminLogin";

	private static final List<String> IGNORE_METHOD = new ArrayList<String>();

	static {
		IGNORE_METHOD.add("login");
		IGNORE_METHOD.add("putOrderNumOnPhone");
		IGNORE_METHOD.add("viewOrderDetailOnPhone");
		IGNORE_METHOD.add("noOrderNum");
		IGNORE_METHOD.add("notification");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		final ActionProxy proxy = invocation.getProxy();
		final ActionContext ctx = invocation.getInvocationContext();
		final String action = proxy.getActionName();
		final String methodName = proxy.getMethod();

		final HttpServletRequest req = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		final HttpServletResponse res = (HttpServletResponse) ctx.get(StrutsStatics.HTTP_RESPONSE);
		res.setHeader("Access-Control-Allow-Origin", "*");
		if (IGNORE_METHOD.contains(methodName)) {
			return invocation.invoke();
		}

		if (action.startsWith("bx")) {
			if (WebUtil.isBxLogined()) {
				return invocation.invoke();
			}
			if (WebUtil.isAjaxRequest(req)) {
				res.getOutputStream().write(NEED_LOGIN_AJAX.getBytes("UTF-8"));
				return null;
			}
			return NEED_LOGIN_BX;
		}
		if (action.startsWith("st")) {
			return invocation.invoke();
		}
		if (action.startsWith("ext")) {
			return invocation.invoke();
		}
		return null;

	}

}