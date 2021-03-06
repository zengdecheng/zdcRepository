package com.xbd.oa.workflow;

import java.util.Iterator;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;

import com.xbd.oa.utils.BizUtil;

public class OaLastStepHandler implements TaskListener {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupName;
	public void notify(DelegateTask delegateTask) {
		Integer oaOrderDetailInt = (Integer)delegateTask.getVariable("oa_order_detail");//上一节点的oa_order_detail 的id
		BizUtil.lastEditBizOrderAndDetail(oaOrderDetailInt);
	}
	/**
	 * 如果这个订单有曾经某个环节有人做过就直接指派给该人
	 * 如果没有人做过，就指派个设置的改组组长
	 * @param delegateTask
	 */
	private String setAssignment(DelegateTask delegateTask,Integer oaOrderInt){
		// Execute custom identity lookups here

	    // and then for example call following methods:
		//System.out.println(delegateTask.getCandidates());
		String adminName = "";
		groupName = "";
		String assigneeName = "";
		
		for (Iterator iterator = delegateTask.getCandidates().iterator(); iterator.hasNext();) {
			IdentityLinkEntity identityLinkEntity = (IdentityLinkEntity) iterator.next();
			groupName = identityLinkEntity.getGroupId();
			assigneeName = identityLinkEntity.getUserId();
		}

		if(assigneeName != null && !assigneeName.equals("")){
			adminName = assigneeName;
		}else{
			adminName = BizUtil.getAssigneeByGroup(groupName,delegateTask.getProcessInstanceId(),oaOrderInt);
		}
		if(adminName != null){
			delegateTask.setAssignee(adminName);
		}else{
			throw new RuntimeException("Workflow assignee is null");
		}
		return adminName;
	}
	
}
