package com.xbd.erp.base.activiti;

import java.util.HashMap;
import java.util.List;

public class LockKeys extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	private static LockKeys lockKeys=new LockKeys();

	
	public static final synchronized boolean lockVariable(String variable) {
		if (lockKeys.containsKey(variable))
			return false;
		lockKeys.put(variable, variable);
		return true;
	}

	public static final synchronized boolean unlockVariable(String variable) {
		lockKeys.remove(variable);
		return true;
	}

	public static final synchronized boolean lockVariables(List<String> variables) {
		if (null == variables || variables.size() == 0)
			return true;
		for (String variable : variables) {
			if (lockKeys.containsKey(variable))
				return false;
		}
		for (String variable : variables) {
			lockKeys.put(variable, variable);
		}
		return true;
	}

	public static final synchronized boolean unlockVariables(List<String> variables) {
		if (null == variables || variables.size() == 0)
			return true;
		for (String variable : variables) {
			lockKeys.remove(variable);
		}
		return true;
	}
}
