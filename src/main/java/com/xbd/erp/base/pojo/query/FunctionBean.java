package com.xbd.erp.base.pojo.query;

import java.util.List;

public class FunctionBean {
	private String name;
	private String type;
	private String sql;
	private List<ParserBean> parsers;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ParserBean> getParsers() {
		return parsers;
	}
	public void setParsers(List<ParserBean> parsers) {
		this.parsers = parsers;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
