package com.xbd.oa.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.xbd.erp.base.pojo.sys.FSPBean;
import com.xbd.oa.annotation.FSP;
import com.xbd.oa.annotation.helper.AnnoConst;
import com.xbd.oa.annotation.helper.AnnoHelper;

public class FspInterceptor extends AbstractInterceptor implements PreResultListener{

	private static final long serialVersionUID = 1L;
	public final static String BACKFLAG_YES = "0";
	public final static String BACKFLAG_NO = "1";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		dealPaginationBefore(invocation);
		return  invocation.invoke();

	}

	private void dealPaginationBefore(ActionInvocation invocation) {
		invocation.addPreResultListener(this);
		ActionProxy proxy = invocation.getProxy();
		try {
		FSP fsp = AnnoHelper.getFspIsPagination(proxy.getAction(), proxy
				.getMethod());
		Class parentActionClass = proxy.getAction().getClass().getSuperclass();
		Field field = parentActionClass.getDeclaredField("fsp");
		field.setAccessible(true);
		FSPBean fspBean = (FSPBean)field.get(proxy.getAction());
		if(fspBean == null) fspBean = new FSPBean();
		
		if(fspBean.getMap() != null){
			for ( Object key : fspBean.getMap().keySet()) {
				fspBean.getMap().put(key, dealp(fspBean.getMap().get(key)));
			}
		}
		field.set(proxy.getAction(), fspBean);
		
		
		if (fsp != null) {
			if(fsp.hasBack().equals(AnnoConst.HAS_BACK_YES)){
				//写入列表的回退功能
				String key = proxy.getAction().getClass().getSimpleName()+"_"+proxy.getMethod();
				Field fieldSuperBackflag = parentActionClass.getDeclaredField("superBackflag");
				fieldSuperBackflag.setAccessible(true);
				String superBackflag = (String)fieldSuperBackflag.get(proxy.getAction());
				if(BACKFLAG_YES.equals(superBackflag)){
					FSPBean oldFsp = (FSPBean) ServletActionContext.getRequest().getSession().getAttribute(key);
					if(oldFsp != null){
						fspBean = oldFsp;
						field.set(proxy.getAction(), fspBean);
					}
				}
			}
			if(!fsp.queryBy().equals(AnnoConst.NONE)){
				if(fsp.isPagination()){fspBean.setPageFlag("1");}
				if(!fsp.parseFucName().equals("")){ 
					fspBean.set(FSPBean.FSP_QUERY_BY_XML,fsp.parseFucName());
				}else{
					return ;
				}
				
				String by = fsp.queryBy();
				int pageSize = fsp.pageSize();
				String orderBy = fsp.orderBy();
				String methName = "";
				if (by.equals(AnnoConst.EQL)) {
					methName = "getObjectsByEql";
				} else {
					methName = "getObjectsBySql";
				}
				Method meth = parentActionClass.getMethod(methName, new Class[]{FSPBean.class,});
				//添加fsp附加条件
				if(pageSize > 1){
					fspBean.setPageSize(pageSize);
				}
				if(orderBy != null && !orderBy.equals("")){
					fspBean.set(FSPBean.FSP_ORDER,orderBy);
				}
				//执行查询语句
				Object retobj = meth.invoke(proxy.getAction(), fspBean);
				//得到查询结果
				//给Action 的beans赋值
				Field fieldSuperList = parentActionClass.getDeclaredField("superList");
				fieldSuperList.setAccessible(true);
				fieldSuperList.set(proxy.getAction(), retobj);
			}
		}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void dealPaginationAfter(ActionInvocation invocation) {
		ActionProxy proxy = invocation.getProxy();
		try {
		FSP fsp = AnnoHelper.getFspIsPagination(proxy.getAction(), proxy
				.getMethod());
		if(fsp == null) return;
		Class parentActionClass = proxy.getAction().getClass().getSuperclass();
		Field field = parentActionClass.getDeclaredField("fsp");
		field.setAccessible(true);
		FSPBean fspBean = (FSPBean)field.get(proxy.getAction());
		
		if(fsp.hasBack().equals(AnnoConst.HAS_BACK_YES)){
			String key = proxy.getAction().getClass().getSimpleName()+"_"+proxy.getMethod();
			ServletActionContext.getRequest().getSession().setAttribute(key,fspBean);
		}
		if(ServletActionContext.getRequest().getAttribute("pageControls")!= null){
			return ;
		}
		if (fsp.isPagination()) {
			String by = fsp.queryBy();
			String methName = "";
			if (by.equals(AnnoConst.EQL)) {
				methName = "processPageInfoEql";
			} else {
				methName = "processPageInfoSql";
			}
				Method meth = parentActionClass.getMethod(methName, new Class[0]);
				Object retobj = meth.invoke(proxy.getAction(), null);
				fspBean.setPageFlag("1");	
		}else{
			fspBean.setPageFlag("0");
		}
		field.set(proxy.getAction(), fspBean);

		
		
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void beforeResult(ActionInvocation invocation, String resultCode) {
		//System.out.println("!!beforeResult");
		dealPaginationAfter(invocation);
		
	}
	/**
	 * 处理fsp字符参数为字符数组的问题
	 * 
	 * @param object
	 * @return
	 */
	public static String dealp(Object object) {
		if (object instanceof String[]) {
			String[] ss = (String[]) object;
			if (ss[0] != null) {
				return ss[0];
			}
		} else if (object instanceof String) {
			return (String) object;
		}
		return null;
	}
	
		
}
