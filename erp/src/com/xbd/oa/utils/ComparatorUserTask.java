package com.xbd.oa.utils;

import java.util.Comparator;

import org.activiti.bpmn.model.UserTask;

public class ComparatorUserTask implements Comparator{

	 public int compare(Object arg0, Object arg1) {
		 UserTask user0=(UserTask)arg0;
		 UserTask user1=(UserTask)arg1;

	   //首先比较年龄，如果年龄相同，则比较名字

	  int flag = getUserTaskInx(user0.getId()) - getUserTaskInx(user1.getId());
	  return flag;
	 }
	 private Integer getUserTaskInx(String userTaskId){
			if (userTaskId != null && userTaskId.lastIndexOf("_") > 0) {
				return Integer.parseInt(userTaskId.substring(userTaskId.lastIndexOf("_") + 1));
			} else {
				return null;
			}
		}
	 
}