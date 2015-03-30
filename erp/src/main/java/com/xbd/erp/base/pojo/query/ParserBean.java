package com.xbd.erp.base.pojo.query;

import java.util.List;

public class ParserBean {
	private String index;
	private String way;
	private String methodname;
	private List<ParamBean> params;

	public List<ParamBean> getParams() {
		return params;
	}

	public void setParams(List<ParamBean> params) {
		this.params = params;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	
}
