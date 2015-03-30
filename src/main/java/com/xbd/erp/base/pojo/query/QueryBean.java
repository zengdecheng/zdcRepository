package com.xbd.erp.base.pojo.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBean {
	private List<FunctionBean> functions;
	private Map<String,FunctionBean> mapFunc;//方便得到fCreator不用循环了
	public void initmapFunc(){
		mapFunc = new HashMap<String,FunctionBean>();
		if(functions.size()>0){
			for(FunctionBean function: functions){
					mapFunc.put(function.getName(), function);
			}	
		}
	}

	public Map<String, FunctionBean> getMapFunc() {
		return mapFunc;
	}

	public void setMapFunc(Map<String, FunctionBean> mapFunc) {
		this.mapFunc = mapFunc;
	}

	public List<FunctionBean> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionBean> functions) {
		this.functions = functions;
	}
}
