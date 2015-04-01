package com.xbd.erp.base.pojo.sys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * this bean created is to regular case for search
 * 
 * @author IBM
 * 
 */
@SuppressWarnings("all")
public class FSPBean implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -9085790281702338044L;
	private final int DEFAULT_PAGE_SIZE = 12;
	public final static String ACTIVE_PAGINATION = "1"; // 分页
	public final static String NEGACTIVE_PAGINATION = "0";// 不分页
	public final static String FSP_ORDER = "fsp_order"; // 排序
	public final static String FSP_QUERY_BY_XML = "query_by_xml"; // 用xml的配置文件

	private int pageNo = 1;
	private int pageSize = DEFAULT_PAGE_SIZE; // 记录每页显示的数据数;
	private long recordCount = 0; // 记录总数;
	/**
	 * 用于静态内容替换 sql里面要有替换位置 $$代表替换位
	 */
	private String staticSqlPart;

	public FSPBean() {
		refreshPageControl();
	}

	public void refreshPageControl() {
		pageNo = 1;
		pageSize = DEFAULT_PAGE_SIZE;
	}

	private String pageFlag = NEGACTIVE_PAGINATION; // 1表示分页，0表示不分页
	private Map map = new HashMap();

	public Map getMap() {
		if(map!= null){
			for ( Object key : map.keySet()) {
				map.put(key, dealp(map.get(key)));
			}
		}
		return map;
	}
	private static Object dealp(Object object) {
		if (object instanceof String[]) {
			String[] ss = (String[]) object;
			if (ss[0] != null) {
				return ss[0];
			}
		} else if (object instanceof String) {
			return (String) object;
		} else if (object instanceof Integer) {
			return (Integer) object;
		} 
		return null;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getPageNo() {
		if (pageNo > 0) {
			return pageNo;
		}
		return 1;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if (pageSize > 0) {
			return pageSize;
		}
		return DEFAULT_PAGE_SIZE;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}

	public boolean isPagination() {
		if (getPageFlag() != null && getPageFlag().equals("1")) {
			return true;
		}
		return false;
	}

	public int getPageFirstResult() {
		if (isPagination()) {
			return (getPageNo() - 1) * getPageSize();
		}
		return 0;
	}

	/**
	 * 提供读取map里object为String的快捷方法
	 * 
	 * @param object
	 * @return
	 * @author fuying
	 * @date 2008-7-28 下午05:55:27
	 * @comment
	 */
	public String getString(Object object) {
		return FSPBean.getFspMapStr(getMap().get(object));
	}

	/**
	 * 此方法用于界面的查询变量，如fsp.map.name到后台之后value从String变成了String[]，所以在这里处理一下
	 */
	public static String getFspMapStr(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof String[]) {
			String[] ss = (String[]) object;
			if (ss[0] != null) {
				return ss[0];
			} else {
				return null;
			}

		} else if (object instanceof String) {
			return (String) object;
		} else {
			return String.valueOf(object);
			// throw new RuntimeException("this variable is not String[] or String");
		}
	}

	public void set(Object key, Object value) {
		map.put(key, value);
	}

	public void put(Object key, Object value) {
		map.put(key, value);
	}

	public Object get(Object key) {
		return map.get(key);
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public String getStaticSqlPart() {
		return staticSqlPart;
	}

	public void setStaticSqlPart(String staticSqlPart) {
		this.staticSqlPart = staticSqlPart;
	}

}