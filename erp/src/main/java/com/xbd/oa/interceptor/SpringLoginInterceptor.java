package com.xbd.oa.interceptor;

/*public class SpringLoginInterceptor implements MethodInterceptor {
    private static final Logger log = Logger.getLogger(SpringLoginInterceptor.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("拦截开始！");
        Object[] args = invocation.getArguments();  
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ActionMapping  mapping = null;
        for (int i = 0 ; i < args.length ; i++ )    {
          if (args[i] instanceof HttpServletRequest) request = (HttpServletRequest)args[i];  
          if (args[i] instanceof HttpServletResponse) response = (HttpServletResponse)args[i];  
          if (args[i] instanceof ActionMapping) mapping = (ActionMapping)args[i];  
        }
        if (request != null && mapping != null) {
            String url=request.getRequestURI();  
            HttpSession session = request.getSession(true);    
            String usercode = (String) request.getRemoteUser();// 登录人
            String user_role = (String)session.getAttribute("user_role");//登录人角色
            
            if (usercode == null || usercode.equals("")) {
                if ( url.indexOf("Login")<0 && url.indexOf("login")<0 ) {
                    
                    return mapping.findForward("loginInterceptor");
                }  
                return invocation.proceed();
            }
            else {
                return invocation.proceed();
            }
        }
        else {
            return invocation.proceed();
        }
    }
}*/
/*public class LoginInterceptor extends AbstractInterceptor {

	public static final Logger logger = Logger.getLogger(LoginInterceptor.class);
	private static final long serialVersionUID = 6386740305263305557L;

	private static final String NEED_LOGIN_AJAX = "ajaxLogin";
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
				Struts2Utils.renderText(NEED_LOGIN_AJAX);
				return null;
			}
			return NEED_LOGIN_BX;
		}
		if (action.startsWith("ext")) {
			return invocation.invoke();
		}
		return null;

	}
}*/