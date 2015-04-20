package com.xbd.oa.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.LazyDynaMap;
import org.springframework.context.ApplicationContext;

import com.xbd.erp.base.utils.XBDUtils;

public class WorkFlowUtil {
	protected static ApplicationContext context;
	protected static ProcessEngine processEngine;
	
	protected static FormService formService;
	protected static RepositoryService repositoryService;
	protected static RuntimeService runtimeService;
	protected static TaskService taskService;
	protected static HistoryService historyService;
	protected static ManagementService managementService;
	protected static IdentityService identityService;

	static {
		if (context == null) {
			context =XBDUtils.context;
			formService = (FormService) context.getBean("formService");
			repositoryService = (RepositoryService) context.getBean("repositoryService");
			runtimeService = (RuntimeService) context.getBean("runtimeService");
			taskService = (TaskService) context.getBean("taskService");
			historyService = (HistoryService) context.getBean("historyService");
			managementService = (ManagementService) context.getBean("managementService");
			identityService = (IdentityService) context.getBean("identityService");
		}
	}

	private static List<LazyDynaMap> processDefinitionList;

	public static String startWf(String processInstanceByKey) {
		return runtimeService.startProcessInstanceByKey(processInstanceByKey).getId();
	}

	public static void completeTask(String taskId) {
		taskService.complete(taskId);
	}

	public static Task getOnlyCurTaskByProcessInstanceId(String processInstanceId) {
		List<Task> listTask = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task task : listTask) {
			return task;
		}
		return null;
	}

	/**
	 * 返回 流程定义列表 LazyDynaMap<"id","key","name">
	 * 
	 * @return
	 */
	public static List<LazyDynaMap> getProcessDefinitionList() {
		if (processDefinitionList == null) {
			refreshProcessDefinitionList();
		}
		return processDefinitionList;
	}

	public synchronized static void refreshProcessDefinitionList() {
		processDefinitionList = new ArrayList<LazyDynaMap>();
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().latestVersion().list();
		for (ProcessDefinition processDefinition : processDefinitions) {
			LazyDynaMap map = new LazyDynaMap();
			map.set("id", processDefinition.getId());
			map.set("key", processDefinition.getKey());
			map.set("name", processDefinition.getName());
			processDefinitionList.add(map);
		}
	}

	/**
	 * 返回 一个流程 用户任务的节点列表 LazyDynaMap<"id","name">
	 * 
	 * @param processDefinitionId
	 *            流程定义Id
	 * @return
	 */
	public static List<LazyDynaMap> getTasksDefinitionList(String processDefinitionkey) {
		List<LazyDynaMap> rtnlist = new ArrayList<LazyDynaMap>();
		String lastProcessDefinitionId = "";
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionkey).latestVersion();// ().processDefinitionKey("oawf1");
		if (processDefinitionQuery.list().size() > 0) {
			lastProcessDefinitionId = processDefinitionQuery.list().get(0).getId();
		}
		BpmnModel bpmnModel = repositoryService.getBpmnModel(lastProcessDefinitionId);
		List<UserTask> list = bpmnModel.getMainProcess().findFlowElementsOfType(UserTask.class);
		ComparatorUserTask comparator = new ComparatorUserTask();
		Collections.sort(list, comparator);
		for (UserTask userTask : list) {
			// System.out.println(userTask.getId() + userTask.getName());
			LazyDynaMap map = new LazyDynaMap();
			map.set("id", userTask.getId());
			map.set("name", userTask.getName());
			rtnlist.add(map);
		}
		return rtnlist;
	}

	/**
	 * 判断是否能流程回退
	 * 
	 * @param taskId
	 * @return
	 */
	public static boolean ifTurnTransitionBack(String taskId) {
		try {
			ActivityImpl sourceTaskActivity = findActivitiImpl(taskId, null);
			// 根据流向确定来向，然后得到来向的ActivityImpl，确定为destTaskActivity
			PvmTransition pvmTransition = sourceTaskActivity.getIncomingTransitions().get(0);
			ActivityImpl destTaskActivity = (ActivityImpl) pvmTransition.getSource();
			if ("exclusivegateway1".equals(destTaskActivity.getId())) {
				pvmTransition = destTaskActivity.getIncomingTransitions().get(0);
				destTaskActivity = (ActivityImpl) pvmTransition.getSource();
			}
			return ifTurnTransition(destTaskActivity.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断是否能跳转到此节点
	 * 
	 * @param destTaskActivity
	 * @return
	 */
	private static boolean ifTurnTransition(ActivityImpl destTaskActivity) {
		return ifTurnTransition(destTaskActivity.getId());
	}

	/**
	 * 判断是否能跳转到此节点，如果节点的最后一位序号小于2则不能跳转（如果节点没有序号，不符合a_xxxxxxx_1这种规则，都不能跳转）
	 * 
	 * @param destKey
	 * @return
	 */
	private static boolean ifTurnTransition(String destKey) {
		if (destKey != null && !destKey.equals("")) {
			Integer inx;
			try {
				inx = Integer.valueOf(destKey.substring(destKey.lastIndexOf("_") + 1));
				if (inx >= 2) {
					return true;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return false;
			}

		}
		return false;
	}

	/**
	 * 流程转向
	 * 
	 * @param taskId
	 * @param destKey
	 * @param variables
	 */
	public static void turnTransition(String taskId, String destKey, Map<String, Object> variables) {
		try {
			ActivityImpl sourceTaskActivity = findActivitiImpl(taskId, null);
			ActivityImpl destTaskActivity = findActivitiImpl(taskId, destKey);
			turnTransition(taskId, sourceTaskActivity, destTaskActivity, variables);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 流程打回
	 * 
	 * @param taskId
	 * @param variables
	 */
	public static void turnTransitionBack(String taskId, Map<String, Object> variables) {
		try {
			ActivityImpl sourceTaskActivity = findActivitiImpl(taskId, null);
			// 根据流向确定来向，然后得到来向的ActivityImpl，确定为destTaskActivity
			PvmTransition pvmTransition = null;
			if (sourceTaskActivity.getIncomingTransitions().size() == 1) {
				pvmTransition = sourceTaskActivity.getIncomingTransitions().get(0);
			} else {
				String caseStr = (String) runtimeService.getVariable(findTaskById(taskId).getProcessInstanceId(), "group");
				if (caseStr.equals("0")) {
					pvmTransition = sourceTaskActivity.getIncomingTransitions().get(0);
				} else {
					pvmTransition = sourceTaskActivity.getIncomingTransitions().get(1);
				}

			}
			ActivityImpl destTaskActivity = (ActivityImpl) pvmTransition.getSource();
			if ("exclusivegateway1".equals(destTaskActivity.getId())) {
				pvmTransition = destTaskActivity.getIncomingTransitions().get(0);
				destTaskActivity = (ActivityImpl) pvmTransition.getSource();
			}
			turnTransition(taskId, sourceTaskActivity, destTaskActivity, variables);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private static TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("taskId :" + taskId + " is not found!");// 任务实例
		}
		return task;
	}

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private static ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("process instance is not found!(taskId:" + taskId + ")");// 流程定义未找到!
		}

		return processDefinition;
	}

	/**
	 * 根据taskId查找流程定义，再根据activityKey查找到要跳转的节点实例，并返回
	 * 
	 * @param taskId
	 *            当前任务节点的id，用来查流程定义
	 * @param activityKey
	 *            用来查找的流程节点定义key
	 * @return
	 * @throws Exception
	 */
	private static ActivityImpl findActivitiImpl(String taskId, String activityKey) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

		// 获取当前活动节点ID
		if (activityKey == null) {
			activityKey = findTaskById(taskId).getTaskDefinitionKey();
		}
		// 根据流程定义，获取该流程实例的结束节点
		if (activityKey.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityKey);

		return activityImpl;
	}

	private static void turnTransition(String curTaskId, ActivityImpl sourceTaskActivity, ActivityImpl destTaskActivity, Map<String, Object> variables) {
		if (!ifTurnTransition(destTaskActivity)) {
			throw new RuntimeException(destTaskActivity.getId() + "is not turn transition f!");
		}
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(sourceTaskActivity);
		// 创建新流向
		TransitionImpl newTransition = sourceTaskActivity.createOutgoingTransition();
		// 设置新流向的目标节点
		newTransition.setDestination(destTaskActivity);
		// 执行转向任务
		taskService.complete(curTaskId, variables);
		// 删除目标节点新流入
		destTaskActivity.getIncomingTransitions().remove(newTransition);
		// 还原以前流向
		restoreTransition(sourceTaskActivity, oriPvmTransitionList);
	}

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private static List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private static void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}

}
