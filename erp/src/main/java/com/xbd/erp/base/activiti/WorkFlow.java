package com.xbd.erp.base.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xbd.erp.base.utils.XBDUtils;
import com.xbd.test.activiti.JumpTask;

public class WorkFlow{
	public static ApplicationContext context;
	public static ProcessEngine processEngine;
	public static FormService formService;
	public static RepositoryService repositoryService;
	public static RuntimeService runtimeService;
	public static TaskService taskService;
	public static HistoryService historyService;
	public static ManagementService managementService;
	public static IdentityService identityService;

	private static final String COMPLETED = "completed";
	
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
	
	
//	private static DateFormat dateFormatYMDHMS = new SimpleDateFormat(DateUtil.ALL_24H);
//	private static DateFormat dateFormatYMD = new SimpleDateFormat(DateUtil.YMD);
	
	
	public static void deploymentByZip(ZipInputStream zipInputStream){
		repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
	}
	
	public static void deploymentByZip(ZipInputStream zipInputStream,String category,String tenantId){
		repositoryService.createDeployment().addZipInputStream(zipInputStream).category(category).tenantId(tenantId).deploy();
	}
	
	public static void deploymentByBpmn(String bpmnPath){
		repositoryService.createDeployment().addClasspathResource(bpmnPath).deploy();
	}
	
	public static void deploymentByBpmn(String bpmnPath,String category,String tenantId){
		repositoryService.createDeployment().addClasspathResource(bpmnPath).category(category).tenantId(tenantId).deploy();
	}
	
	public static ProcessInstance startProcess(String processDefinitionKey){
		return runtimeService.startProcessInstanceByKey(processDefinitionKey);
	}
	
	public static ProcessInstance startProcess(String processDefinitionKey, Map<String,Object> variabes){
		return  runtimeService.startProcessInstanceByKey(processDefinitionKey,variabes);
	}
	
	public static List<ProcessDefinition> getAllProcessDefinition(){
		return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().list();
	}
	
	public static List<ProcessDefinition> getProcessDefinitionByPage(int firstResult,int maxResults){
		return repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().listPage(firstResult,maxResults);
	}
	
	public static List<Task> getAllTask(){
		return taskService.createTaskQuery().orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getPersonTask(String assignee){
		return taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();
	}
	
	public static void claimTask(String taskId,String userId){
		taskService.claim(taskId, userId);
	}
	
	public static void delAssignee(String taskId){
		taskService.setAssignee(taskId, null);
	}
	
	public static void addGroupUser(String taskId,String userId){
		taskService.addCandidateUser(taskId, userId);
	}
	
	public static void delGroupUser(String taskId,String userId){
		taskService.deleteCandidateUser(taskId, userId);
	}
	
	public static Map<String,Object> getVariablesLocal(String taskId){
		return taskService.getVariablesLocal(taskId);
	}
	
	public static Map<String,Object> getVariablesLocal(String taskId,List<String> params){
		return taskService.getVariablesLocal(taskId, params);
	}

	public static Object getVariableLocal(String taskId,String variableName){
		return taskService.getVariableLocal(taskId, variableName);
	}
	
	public static Map<String,Object> getVariables(String taskId){
		return taskService.getVariables(taskId);
	}
	
	public static Map<String,Object> getVariables(String taskId,List<String> params){
		return taskService.getVariables(taskId, params);
	}
	
	public static Object getVariable(String taskId,String variableName){
		return taskService.getVariable(taskId, variableName);
	}
	
	public static List<HistoricVariableInstance> getHisVariableByName(String variableValue){
		 return processEngine.getHistoryService().createHistoricVariableInstanceQuery().variableName(variableValue).orderByProcessInstanceId().desc().list();
	}
	
	public static List<HistoricVariableInstance> getHisVariableByNameLike(String variableValue){
		return processEngine.getHistoryService().createHistoricVariableInstanceQuery().variableNameLike(variableValue).orderByProcessInstanceId().desc().list();
	}
	
	public static List<HistoricVariableInstance> getHisVariableByObject(String variableName,Object variableValue){
		return processEngine.getHistoryService().createHistoricVariableInstanceQuery().variableValueEquals(variableName,variableValue).orderByProcessInstanceId().desc().list();
	}
	
	public static void nextUserTask(String taskId){
		taskService.complete(taskId);
	}
	
	public static void nextUserTask(String taskId,Map<String,Object> variables){
		taskService.complete(taskId, variables);
	}
	
	public static void nextUserTask(String taskId,Map<String,Object> variables,boolean localScope){
		taskService.complete(taskId, variables, localScope);
	}
	
	public static void nextReceiveTask(String executionId){
		runtimeService.signal(executionId);
	}
	public static void nextReceiveTask(String executionId,Map<String,Object> processVariables){
		runtimeService.signal(executionId,processVariables);
	}
	
	public static List<Task> getPersonalTask(String assignee){
		return processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getPersonalTaskLike(String assignee){
		return processEngine.getTaskService().createTaskQuery().taskAssigneeLike(assignee).orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getPersonalTaskByPage(String assignee,int firstResult,int maxResults){
		return processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().listPage(firstResult, maxResults);
	}
	public static List<Task> getPersonalTaskLikeByPage(String assignee,int firstResult,int maxResults){
		return processEngine.getTaskService().createTaskQuery().taskAssigneeLike(assignee).orderByTaskCreateTime().desc().listPage(firstResult, maxResults);
	}
	
	public static List<Task> getTasksByProcessInstanceId(String processInstanceId){
		return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
	}
	
	public static Task getTaskByTaskId(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	public static List<HistoricTaskInstance> getHisTaskByPerson(String assignee){
		return historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list();
	}
	
	public static List<HistoricTaskInstance> getHisTaskByPersonLike(String assignee){
		return historyService.createHistoricTaskInstanceQuery().taskAssigneeLikeIgnoreCase(assignee).orderByTaskCreateTime().desc().list();
	}
	
	public static List<HistoricTaskInstance> getHisTaskByGroup(String candidateGroup){
		return historyService.createHistoricTaskInstanceQuery().taskCandidateGroup(candidateGroup).orderByTaskCreateTime().desc().list();
	}
	
	public static List<HistoricTaskInstance> getHisTaskByGroups(List<String> candidateGroups){
		return historyService.createHistoricTaskInstanceQuery().taskCandidateGroupIn(candidateGroups).orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getGroupTask(String candidateGroup){
		return taskService.createTaskQuery().taskCandidateGroup(candidateGroup).orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getGroupsTask(List<String> candidateGroups){
		return taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups).orderByTaskCreateTime().desc().list();
	}
	
	public static List<Task> getGroupTaskByPage(String candidateGroup,int firstResult,int maxResults){
		return taskService.createTaskQuery().taskCandidateGroup(candidateGroup).orderByTaskCreateTime().desc().listPage(firstResult, maxResults);
	}
	
	public static List<Task> getGroupsTaskByPage(List<String> candidateGroups,int firstResult,int maxResults){
		return taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups).orderByTaskCreateTime().desc().listPage(firstResult, maxResults);
	}
	
	public static void getViewImage(OutputStream os,String deploymentId){
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		if (imageName != null) {
			InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
			int c;
			try {
	            while((c=in.read())!=-1){
	                os.write(c);
	            }
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.flush();
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	} 
	
	public static void delAllDeployment(){
		List<Deployment> dps = repositoryService.createDeploymentQuery().list();
		for (Deployment dp : dps) {
			repositoryService.deleteDeployment(dp.getId(), true);
		}
	}
	public static void delDeployment(String deploymentId){
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**
	 * 通过流程实例ID删除流程实例
	 * @param processInstanceID
	 */
	public static void deleteProcessByProcessID(String processInstanceID) {
		if (null != processInstanceID){
			runtimeService.deleteProcessInstance(processInstanceID, COMPLETED);
		}
	}
	
	/**
	 * 通过流程实例ID、删除原因删除流程实例
	 * @param processInstanceID
	 * @param deleteReason
	 */
	public static void deleteProcessByProcessID(String processInstanceID,String deleteReason) {
		if (null != processInstanceID){
			runtimeService.deleteProcessInstance(processInstanceID, deleteReason);
		}
	}
	
	public static void jumpTaskByExecutionId(String executionId,String activityId){
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;  
		taskServiceImpl.getCommandExecutor().execute(new JumpTask(executionId, activityId));
	}
	
	public static void jumpTaskByExecutionId(String executionId,String activityId,Map<String,Object> variables){
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;  
		taskServiceImpl.getCommandExecutor().execute(new JumpTask(executionId, activityId, variables));
	}
	
	public static void jumpTaskByTaskId(String taskId,String activityId){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		jumpTaskByExecutionId(task.getExecutionId(),activityId);
	}
	
	public static void jumpTaskByTaskId(String taskId,String activityId,Map<String,Object> variables){
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		jumpTaskByExecutionId(task.getExecutionId(),activityId,variables);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//检查乐观锁
	// 返回执行订单行集合与未执行订单行集合
//	public List<String>[] beforeDoProcess(List<String> orderIDs, List<Integer> orderLocker,boolean isNeedAllPass) {
//		if (null == orderIDs || orderIDs.size() == 0 || null == orderLocker || orderLocker.size() == 0 || orderLocker.size() != orderIDs.size())
//			return null;
//
//		// 锁定所有订单行
//		if (!LockKeys.lockVariables(orderIDs)) {
//			return null;
//		}
//		// 校验所有订单行时间戳或校验乐观锁
//		if (!checkLocker(orderIDs, orderLocker)) {
//			afterDoProcess(orderIDs);
//			return null;
//		}
//
//		// 获得所有订单行对应的业务流程
//		List<ProcessInstance> processInstances = getAllProcessInstances(orderIDs);
//		if (null == processInstances) {
//			afterDoProcess(orderIDs);
//			return null;
//		}
//		// 检查所有流程，在指定任务点，滤出某流向分支的可通过流程
//		List<String> passProcessLineIDs = new ArrayList<String>();
//		List<String> notpassProcessLineIDs = getNotPassProcessInstancesLineIDs(processInstances, taskDefinitionKey, operateVariables, passProcessLineIDs);
//		// 检查在指定任务点，向下的各条或指定分支，是否要求全部订单行流向为true才可共同执行
//		// 对需要全部共同执行的，如果有不满足的，报出不满足订单行号，走向afterDoProcess
//		if (isNeedAllPass && null != notpassProcessLineIDs || passProcessLineIDs.size() == 0) {
//			afterDoProcess(orderIDs);
//			return null;
//		}
//		// 其他为可执行的订单行号，后继执行该行的更新语句，也可考虑由传入更新语句以交由流程自己执行
//		return new List[] { passProcessLineIDs, notpassProcessLineIDs };
//	}

	// 校验所有订单行时间戳或校验乐观锁
//	public boolean checkLocker(List<String> orderIDs, List<Integer> orderLockers) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select * from oa_order_detail");
//		for (int i = 0; i < orderIDs.size(); i++) {
//			if (i == 0) {
//				sb.append(" where ID='").append(orderIDs.get(0)).append("' and VERSION='").append(orderLockers.get(0)).append("'");
//			} else
//				sb.append(" or ID='").append(orderIDs.get(i)).append("' and VERSION='").append(orderLockers.get(i)).append("'");
//		}
//		Integer size = baseDao.countBySql(sb.toString(),null);
//		if (orderIDs.size() == size) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	
//
//	// 传入可执行的订单行id集合
//	public static boolean doProcess(List<String> passProcessLineIDs, List<String> notpassProcessLineIDs, String taskDefinitionKey) {
//		// public static static void doProcess(List<String> passProcessLineIDs,
//		// List<String> notpassProcessLineIDs, String taskDefinitionKey, String
//		// operateVariable,
//		// boolean isNeedAllPass) {
//		// 获得所有订单行对应的业务流程
//		try {
//			List<ProcessInstance> processInstances = getAllProcessInstances(passProcessLineIDs);
//			// 循环所有订单行执行
//			for (ProcessInstance processInstance : processInstances) {
//				// 对可执行的订单行号更新过后，执行任务点的complete命令
//				// 在该任务点的complete中，依据任务点和当前的流向开关，决定是否临时关闭其他流向开关并备份
//				// 在该任务点的complete中，依据任务点和当前的流向开关，检查下一任务点是否有本流程任务，如果有，关闭下一流程的转向开关
//				// 在该任务点的complete中，依据任务点和当前的流向开关，检查返回控制:
//				// 1－如果该任务流向已结束，关闭返回流程变量开关；
//				// 2－如果该任务点已有超过2个任务（说明前一流向有返回任务点）则关闭返回流程变量开关
//				List<Task> tasks = getTaskByProcessInstanceIdTaskDefinitionKey(processInstance.getProcessInstanceId(), taskDefinitionKey);
//				if (tasks.size() >= 0) {
//					doTask(tasks.get(0), getProcessVariablesByProcessInstance(processInstance));
//				}
//				// 在后继的网关（如果有的话）决定是否关闭此方向的流程变量
//				// 如果有，恢复其他临时关闭的流向开关，并清除备份
//				// 针对该任务点和流程变量开关，以及订单行数据，依据流程类型约定，打开其他流程变量开关
//				// 发出通知，应该做在流程配置中
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	public static void afterDoProcess(List<String> orderLineIDs) {
//		// private static static void afterDoProcess(List<String> orderLineIDs,
//		// List<ProcessInstance> passProcessInstances, List<String>
//		// notpassProcessLineIDs,
//		// String taskDefinitionKey, String operateVariable, boolean
//		// isNeedAllPass) {
//		// 检查通过和未通过订单行号集合，准备向前台报出该行号集
//		// 取消锁定订单行
//		LockKeys.unlockVariables(orderLineIDs);
//	}
//
//	// 依指定订单行号，对指定流程变量置值
//	public static void setVariableByOrderLineID(String orderLineID, String variable, Object value) {
//		if (null == orderLineID || null == variable)
//			return;
//		ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//		if (null == processInstance)
//			return;
//		Map<String, Object> variables = getProcessVariablesByProcessInstance(processInstance);
//		if (null == variables)
//			return;
//		if (null == value)
//			variables.remove(variable);
//		else
//			variables.put(variable, value);
//		runtimeService.setVariables(processInstance.getProcessInstanceId(), variables);
//	}
//
//	// 依指定订单行号，更新流程变量集合
//	public static void setVariablesByOrderLineID(String orderLineID, Map<String, Object> variables) {
//		if (null == orderLineID || null == variables)
//			return;
//		ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//		if (null == processInstance)
//			return;
//		runtimeService.setVariables(processInstance.getProcessInstanceId(), variables);
//	}
//
//	// 依流程id,对指定流程变量置值
//	public static void setVariableByProcessID(String processInstanceID, String variable, Object value) {
//		if (null == processInstanceID || null == variable)
//			return;
//		Map<String, Object> variables = runtimeService.getVariables(processInstanceID);
//		if (null == variables)
//			return;
//		if (null == value)
//			variables.remove(variable);
//		else
//			variables.put(variable, value);
//		runtimeService.setVariables(processInstanceID, variables);
//	}
//
//	// 依流程ID返回流程变量
//	public static Map<String, Object> getVariableByProcessID(String processInstanceID) {
//		if (null == processInstanceID)
//			return null;
//		Map<String, Object> variables = runtimeService.getVariables(processInstanceID);
//		return variables;
//	}
//
//	// 依订单行号返回流程变量
//	public static Map<String, Object> getVariableByOrderLineID(String orderLineID) {
//		if (null == orderLineID)
//			return null;
//		ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//		if (null == processInstance)
//			return null;
//		return getProcessVariablesByProcessInstance(processInstance);
//		// return getProcessVariablesByProcessInstance(processInstance);
//	}
//
//	private static Map<String, Object> getProcessVariablesByProcessInstance(ProcessInstance processInstance) {
//		return runtimeService.getVariables(processInstance.getProcessInstanceId());
//	}

	// 依传入的订单行号获得所有流程，未查到的则不取得
//	private static List<ProcessInstance> getAllProcessInstances(List<String> orderIDs) {
//		if (null == orderIDs || orderIDs.size() == 0)
//			return null;
//		List<ProcessInstance> processInstances = new ArrayList<ProcessInstance>();
//		for (String orderLineID : orderIDs) {
//			ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//			if (null != processInstance)
//				processInstances.add(processInstance);
//		}
//		if (processInstances.size() == 0)
//			return null;
//		return processInstances;
//	}
//
//	// 指定订单行号和流程类型，启动流程
//	public static ProcessInstance startProcessByProcessTypeOrderLineID(String orderLineID, String type, String sendCompanyID, String receiveCompanyID, String payCompanyID,
//			String gainCompanyID) {
//		if (null == orderLineID || null == type)
//			return null;
//		if (null != getProcessInstanceByOrderID(orderLineID))
//			return null;
//		Map<String, Object> variableMap = getInitVariableByType(type);
//		if (null != variableMap) {
//			variableMap.put(ProcessVariable.SendCompanyID, sendCompanyID);
//			variableMap.put(ProcessVariable.ReceiveCompanyID, receiveCompanyID);
//			variableMap.put(ProcessVariable.PayCompanyID, payCompanyID);
//			variableMap.put(ProcessVariable.GainCompanyID, gainCompanyID);
//		}
//		ProcessInstance processInstance = createProcessByLastProcessDefinition(ProcessVariable.ProcessName, orderLineID, variableMap);
//		return processInstance;
//	}
//
//	private static Map<String, Object> getInitVariableByType(String type) {
//		Map<String, Object> variableMap = null;
//		if (type.equals(ProcessVariable.FirstInvSecondMoney))
//			variableMap = ProcessVariable.getFirstSendSecondPayInitVariables();
//		else if (type.equals(ProcessVariable.FirstMoneySecondInvPermitLoan))
//			variableMap = ProcessVariable.getFirstPaySecondSendInitVariables();
//		else
//			variableMap = ProcessVariable.getAllClosedVariables();
//		return variableMap;
//	}
//
//	// 更新流程变量，依流程类型
//	public static void updateVariableByProcessType(String type, Map<String, Object> variableMap) {
//		if (null == type || null == variableMap)
//			return;
//		Map<String, Object> fromMap = getInitVariableByType(type);
//		combineMap(fromMap, variableMap);
//	}
//
//	private static void combineMap(Map<String, Object> fromMap, Map<String, Object> toMap) {
//		if (null == fromMap || null == toMap)
//			return;
//		Iterator<Entry<String, Object>> it = fromMap.entrySet().iterator();
//		while (it.hasNext()) {
//			Entry<String, Object> entry = it.next();
//			toMap.put(entry.getKey(), entry.getValue());
//		}
//	}
//
//	// 依最新流程定义，启动流程
//	private static ProcessInstance createProcessByLastProcessDefinition(String processDefinitionKey, String businessKey, Map<String, Object> variableMap) {
//		if (null == variableMap || null == businessKey || null == processDefinitionKey)
//			return null;
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variableMap);
//		return processInstance;
//	}
//
//	// 依流程检查指定流程变量值,返回未通过的流程对应的订单行id
//	private static List<String> getNotPassProcessInstancesLineIDs(List<ProcessInstance> processInstances, String taskDefinitionKey, String[] operateVariables,
//			List<String> passProcessLineIDs) {
//		if (null == processInstances || processInstances.size() == 0 || null == passProcessLineIDs || null == taskDefinitionKey) {
//			return null;
//		}
//		// List<ProcessInstance> notpassOrderLineProcessInstances = null;
//		List<String> notpassProcessLineIDs = null;
//		for (ProcessInstance processInstance : processInstances) {
//			List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processInstanceId(processInstance.getProcessInstanceId()).list();
//			if (tasks.size() == 0) {
//				// 如果该流程未落在该节点，则加入nopass，否则继续
//				notpassProcessLineIDs = addToNotPassOrderLineIDList(passProcessLineIDs, notpassProcessLineIDs, processInstance.getBusinessKey());
//				continue;
//			}
//			if (null == operateVariables || operateVariables.length == 0) {
//				// if (null != passProcessLineIDs)
//				passProcessLineIDs.add(processInstance.getBusinessKey());
//			} else {
//				Map<String, Object> processVariable = getProcessVariablesByProcessInstance(processInstance);
//				// Map<String, Object> processVariable = runtimeService.getVariables(processInstance.getProcessInstanceId());
//				for (String operateVariable : operateVariables) {
//					Boolean operateVariableValue = (Boolean) processVariable.get(operateVariable);
//					if (null == operateVariableValue) {
//						notpassProcessLineIDs = addToNotPassOrderLineIDList(passProcessLineIDs, notpassProcessLineIDs, processInstance.getBusinessKey());
//					} else if (!operateVariableValue) {
//						// 有一个开关为false
//						notpassProcessLineIDs = addToNotPassOrderLineIDList(passProcessLineIDs, notpassProcessLineIDs, processInstance.getBusinessKey());
//					} else {
//						// 可以通过某一开关
//						String orderlineID = processInstance.getBusinessKey();
//						passProcessLineIDs.add(orderlineID);
//						if (null != notpassProcessLineIDs && notpassProcessLineIDs.contains(orderlineID)) {
//							notpassProcessLineIDs.remove(orderlineID);
//						}
//					}
//				}
//			}
//		}
//		return notpassProcessLineIDs;
//	}
//
//	private static List<String> addToNotPassOrderLineIDList(List<String> passProcessLineIDs, List<String> notpassProcessLineIDs, String businessKey) {
//		if (!passProcessLineIDs.contains(businessKey)) {// 在通过列表中无此值
//			if (null == notpassProcessLineIDs)
//				notpassProcessLineIDs = new ArrayList<String>();
//			notpassProcessLineIDs.add(businessKey);
//		}
//		return notpassProcessLineIDs;
//	}
//
//	// 指定任务名和流程变量，决定该变量是否必须全部为true方可共同处理
//	// private static boolean isOperateVariableNeedAllPass(String taskDefinitionKey, String operateVariable) {
//	// if (taskDefinitionKey == null || operateVariable == null)
//	// return false;
//	// if (taskDefinitionKey.equals(TaskIDs.ContractStart)) {// 起点
//	// if (operateVariable.equals(ProcessVariable.PermitStart2Loan)) {
//	// // 转向融资
//	// return true;
//	// }
//	// }
//	// return false;
//	// }
//
//	// 由订单行号取得流程实例
//	public static ProcessInstance getProcessInstanceByOrderID(String businessKey) {
//		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
//		if (processInstances.size() == 0)
//			return null;
//		return processInstances.get(0);
//	}
//
//	// 判断指定流程，在指定任务点是否有任务
//	private static boolean isTaskHasProcessInstanceID(String processInstanceID, String taskDefinitionKey) {
//		// 检查本流程实例是否指定任务点有未完成任务，如果有则不再进入
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceID).taskDefinitionKey(taskDefinitionKey).list();
//		if (tasks.size() > 0)
//			return true;
//		return false;
//	}
//
//	// 判断指定流程，在指定任务点是否有多于一个任务
//	private static boolean isTaskHasMoreProcessInstanceID(String processInstanceID, String taskDefinitionKey) {
//		// 检查本流程实例是否指定任务点有多于一个未完成任务，如果有则不再进入
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceID).taskDefinitionKey(taskDefinitionKey).list();
//		if (tasks.size() <= 1)
//			return false;
//		return true;
//	}
//
//	private static List<Task> getTaskByProcessInstanceIdTaskDefinitionKey(String processInstanceId, String taskDefinitionKey) {
//		List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processInstanceId(processInstanceId).list();
//		return tasks;
//	}
//
//	private static void doTask(Task task, Map<String, Object> variables) {
//		// 执行某一任务
//		taskService.complete(task.getId(), variables);
//	}
//
//	// 在该任务点的complete中，依据任务点和当前的流向开关，检查下一任务点是否有本流程任务，如果有，关闭下一流程的转向开关
//	public static void checkAndCloseNextEntryIfExistTask(String processInstanceID, String currenttaskDefinitionKey, Map<String, Object> variables, String variableOut) {
//		Boolean permit = (Boolean) variables.get(variableOut);
//		if (null != permit && permit) {
//			// 本次为进入发货
//			// 确实允许进入发货
//			// 备份状态
//			// backupVariable(variables);
//			// 暂时停止其他流转，待任务结束时另行恢复
//			// variables.put(ProcessVariable.PermitStart2LockPay, false);
//			// variables.put(ProcessVariable.PermitStart2Loan, false);
//			// DealProcesses.runtimeService.setVariables(delegateTask.getProcessInstanceId(),
//			// variables);
//
//			// 在该任务点的complete中，依据任务点和当前的流向开关，检查下一任务点是否有本流程任务，如果有，关闭下一流程的转向开关
//			Set<String> nextTaskDefinitionKeys = TaskIDs.getNextTaskDefinitionKeys(currenttaskDefinitionKey, variableOut);
//			boolean update = false;
//			if (null != nextTaskDefinitionKeys) {
//				Iterator<String> it = nextTaskDefinitionKeys.iterator();
//				while (it.hasNext()) {
//					String taskDefinitionKey = it.next();
//					boolean hasTaskInNext = isTaskHasProcessInstanceID(processInstanceID, taskDefinitionKey);
//					if (hasTaskInNext) {
//						if (variables.containsKey(variableOut) && (Boolean) variables.get(variableOut)) {
//							variables.put(variableOut, false);
//							update = true;
//						}
//					} else {
//						if (variables.containsKey(variableOut) && !(Boolean) variables.get(variableOut)) {
//							variables.put(variableOut, true);
//							update = true;
//						}
//					}
//				}
//				// variables.put(ProcessVariable.PermitLock2HumanUnlock, true);
//				if (update)
//					runtimeService.setVariables(processInstanceID, variables);
//			}
//		}
//	}
//
//	// 在该任务点的complete中，依据任务点和当前的流向开关，检查本任务点是否有多于一个本流程任务，如果有，关闭转回开关
//	// public static void checkAndCloseBackEntryIfExistMoreTask(String processInstanceID, String currenttaskDefinitionKey, Map<String, Object> variables, String variableOut)
//	// {
//	// String variableBack = TaskIDs.getBackVariableByTaskDefinitionKey(currenttaskDefinitionKey, variableOut);
//	// boolean hasTaskInNext = isTaskHasMoreProcessInstanceID(processInstanceID, currenttaskDefinitionKey);
//	// if (hasTaskInNext) {
//	// variables.put(variableBack, false);
//	// runtimeService.setVariables(processInstanceID, variables);
//	// } else {
//	// // 不打开，由调用者在订单数据中自行决定是否打开
//	// // variables.put(variableBack, true);
//	// }
//	// }
//
//	// 依任务定义key和公司id获得任务列表
//	private static List<Task> getTaskByTaskDefinitionKeyCompanyID(String taskDefinitionKey, String companyID) {
//		// List<Task> tasks =
//		// taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).list();
//		List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.SendCompanyID, companyID).list();
//		tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.ReceiveCompanyID, companyID).list());
//		tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.PayCompanyID, companyID).list());
//		tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.GainCompanyID, companyID).list());
//		tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.ThirdCompanyID, companyID).list());
//		tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(ProcessVariable.ParentPayCompanyID, companyID).list());
//		return tasks;
//	}
//
//	// 依任务定义key和公司id及公司角色获得任务列表
//	private static List<Task> getTaskByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		// List<Task> tasks =
//		// taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).list();
//		List<Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey).processVariableValueEquals(companyType, companyID).list();
//		return tasks;
//	}
//
//	// 依任务定义key和公司id及公司角色获得历史任务列表
//	private static List<HistoricTaskInstance> getHistoricTaskByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().finished().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(companyType, companyID).list();
//		return tasks;
//	}
//
//	// 依任务定义key和公司id及公司角色获得所有任务列表 2014-09-03
//	private static List<HistoricTaskInstance> getAllTaskByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(companyType, companyID).list();
//		return tasks;
//	}
//
//	// 依task获得订单行id
//	private static List<String> getOrderLineIDsByTask(List<Task> tasks) {
//		if (null == tasks || tasks.size() == 0)
//			return null;
//		Set<String> processInstanceIDs = new HashSet<String>();
//		for (Task task : tasks) {
//			processInstanceIDs.add(task.getProcessInstanceId());
//		}
//		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processInstanceIds(processInstanceIDs).list();
//		if (null == processInstances || processInstances.size() == 0)
//			return null;
//		List<String> orderLineIDs = new ArrayList<String>();
//		for (ProcessInstance processInstance : processInstances) {
//			orderLineIDs.add(processInstance.getBusinessKey());
//		}
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得订单行列表
//	// 暂时关闭此方法，转由getOrderLineIDsByTaskDefinitionKeyCompanyIDCompanyType此方法实现只查一个指定公司变量
//	@SuppressWarnings("unused")
//	private static List<String> getOrderLineIDsByTaskDefinitionKeyCompanyID(String taskDefinitionKey, String companyID) {
//		List<Task> tasks = getTaskByTaskDefinitionKeyCompanyID(taskDefinitionKey, companyID);
//		List<String> orderLineIDs = getOrderLineIDsByTask(tasks);
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得订单行列表
//	// 指定的变量可有ProcessVariable.SendCompanyID,ProcessVariable.ReceiveCompanyID,ProcessVariable.PayCompanyID,
//	// ProcessVariable.GainCompanyID,ProcessVariable.ThirdCompanyID,ProcessVariable.ParentPayCompanyID
//	public static List<String> getOrderLineIDsByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		List<Task> tasks = getTaskByTaskDefinitionKeyCompanyIDCompanyType(taskDefinitionKey, companyID, companyType);
//		List<String> orderLineIDs = getOrderLineIDsByTask(tasks);
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得历史订单行列表
//	// 指定的变量可有ProcessVariable.SendCompanyID,ProcessVariable.ReceiveCompanyID,ProcessVariable.PayCompanyID,
//	// ProcessVariable.GainCompanyID,ProcessVariable.ThirdCompanyID,ProcessVariable.ParentPayCompanyID
//	public static List<String> getHistoricOrderLineIDsByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		List<HistoricTaskInstance> tasks = getHistoricTaskByTaskDefinitionKeyCompanyIDCompanyType(taskDefinitionKey, companyID, companyType);
//		List<String> orderLineIDs = getOrderLineIDsByHistoricTask(tasks);
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得所有订单行列表 20140903
//	// 指定的变量可有ProcessVariable.SendCompanyID,ProcessVariable.ReceiveCompanyID,ProcessVariable.PayCompanyID,
//	// ProcessVariable.GainCompanyID,ProcessVariable.ThirdCompanyID,ProcessVariable.ParentPayCompanyID
//	public static List<String> getAllOrderLineIDsByTaskDefinitionKeyCompanyIDCompanyType(String taskDefinitionKey, String companyID, String companyType) {
//		List<HistoricTaskInstance> tasks = getAllTaskByTaskDefinitionKeyCompanyIDCompanyType(taskDefinitionKey, companyID, companyType);
//		List<String> orderLineIDs = getOrderLineIDsByHistoricTask(tasks);
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得历史订单行列表
//	// 暂时关闭此方法，转由getHistoricOrderLineIDsByTaskDefinitionKeyCompanyIDCompanyType此方法实现只查一个指定公司变量
//	@SuppressWarnings("unused")
//	private static List<String> getHistoricOrderLineIDsByTaskDefinitionKeyCompanyID(String taskDefinitionKey, String companyID) {
//		List<HistoricTaskInstance> tasks = getHistoricTaskByTaskDefinitionKeyCompanyID(taskDefinitionKey, companyID);
//		List<String> orderLineIDs = getOrderLineIDsByHistoricTask(tasks);
//		return orderLineIDs;
//	}
//
//	// 依任务定义key和公司id获得历史任务列表
//	private static List<HistoricTaskInstance> getHistoricTaskByTaskDefinitionKeyCompanyID(String taskDefinitionKey, String companyID) {
//		// List<Task> tasks =
//		// historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey).list();
//		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().finished().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.SendCompanyID, companyID).list();
//		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.ReceiveCompanyID, companyID).list());
//		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.PayCompanyID, companyID).list());
//		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.GainCompanyID, companyID).list());
//		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.ThirdCompanyID, companyID).list());
//		tasks.addAll(historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(taskDefinitionKey)
//				.processVariableValueEquals(ProcessVariable.ParentPayCompanyID, companyID).list());
//		return tasks;
//	}
//
//	// 依历史task获得历史订单行id
//	private static List<String> getOrderLineIDsByHistoricTask(List<HistoricTaskInstance> tasks) {
//		if (null == tasks || tasks.size() == 0)
//			return null;
//		Set<String> processInstanceIDs = new HashSet<String>();
//		for (HistoricTaskInstance task : tasks) {
//			processInstanceIDs.add(task.getProcessInstanceId());
//		}
//		List<HistoricProcessInstance> processInstances = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIDs).list();
//		if (null == processInstances || processInstances.size() == 0)
//			return null;
//		List<String> orderLineIDs = new ArrayList<String>();
//		for (HistoricProcessInstance processInstance : processInstances) {
//			orderLineIDs.add(processInstance.getBusinessKey());
//		}
//		return orderLineIDs;
//	}
//
//	// 创建新流程定义，同时清除过往定义及所生成的所有流程实例
//	public static void createNewDefinition(String filepathname) {
//		// long num = repositoryService.createProcessDefinitionQuery().count();
//		// if (num > 0) {
//		// deleteAllProcessDefinition();
//		// }
//		// org.activiti.engine.repository.Deployment deployment =
//		repositoryService.createDeployment().name("nongxiaoProcess:" + dateFormat.format(new Date())).addClasspathResource(filepathname).deploy();
//	}
//
//	// 创建新流程定义，同时清除过往定义及所生成的所有流程实例s
//	public static void createNewDefinition(String definitionName, InputStream stream) {
//		// long num = repositoryService.createProcessDefinitionQuery().count();
//		// if (num > 0) {
//		// deleteAllProcessDefinition();
//		// }
//		// org.activiti.engine.repository.Deployment deployment =
//		// repositoryService.createDeployment().addClasspathResource(filepathname).deploy();
//		// org.activiti.engine.repository.Deployment deployment =
//		repositoryService.createDeployment().name("nongxiaoProcess:" + dateFormat.format(new Date())).addInputStream(definitionName, stream).deploy();
//		// (filepathname).deploy();
//	}
//
//	@SuppressWarnings("deprecation")
//	private static void deleteProcessDefinition(String deploymentId) {
//		// repositoryService.deleteDeployment(deploymentId);
//		repositoryService.deleteDeploymentCascade(deploymentId);
//	}
//
//	@SuppressWarnings("unused")
//	private static void deleteAllProcessDefinition() {
//		// 删除过往的定义
//		DeploymentQuery query = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc();
//		List<String> deploymentid = new ArrayList<String>();
//		for (Deployment deployment : query.list()) {
//			deploymentid.add(deployment.getId());
//		}
//		for (int i = 0; i < deploymentid.size(); i++) {
//			deleteProcessDefinition(deploymentid.get(i));
//		}
//	}
//
//	// 查询所有的流程定义
//	public static List<List<String>> showAllProcessDefinition() {
//		long num = repositoryService.createProcessDefinitionQuery().count();
//		if (num == 0)
//			return null;
//		// System.out.println("流程定义数量: " + num);
//		DeploymentQuery query = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc();
//		List<List<String>> result = new ArrayList<List<String>>();
//		for (Deployment deployment : query.list()) {
//			List<String> row = new ArrayList<String>();
//			row.add(deployment.getId());
//			row.add(deployment.getName());
//			row.add(dateTimeFormat.format(deployment.getDeploymentTime()));
//			result.add(row);
//			// System.out.println("Deploymentid:" + deployment.getId() +
//			// " name:" + deployment.getName() + " time:" +
//			// deployment.getDeploymentTime());
//		}
//		return result;
//	}
//
//	
//	 * public static void afterPropertiesSet() throws Exception { if (category == null){ throw new FatalBeanException("Missing property: category"); } if (deploymentResources !=
//	 * null) { RepositoryService repositoryService = appCtx.getBean(RepositoryService.class); for (Resource r : deploymentResources) { String deploymentName = category + "_"
//	 * + r.getFilename(); String resourceName = r.getFilename(); boolean doDeploy = true; List<Deployment> deployments = repositoryService.createDeploymentQuery
//	 * ().deploymentName(deploymentName).orderByDeploymenTime().desc().list(); if (!deployments.isEmpty()) { Deployment existing = deployments.get(0); try { InputStream in =
//	 * repositoryService.getResourceAsStream(existing.getId(), resourceName); if (in != null) { File f = File.createTempFile("deployment","xml", new
//	 * File(System.getProperty("java.io.tmpdir"))); f.deleteOnExit(); OutputStream out=new FileOutputStream(f); IOUtils.copy(in,out); in.close(); out.close(); doDeploy =
//	 * (FileUtils.checksumCRC32(f) != FileUtils.checksumCRC32(r.getFile())); } else throw new ActivitiException("Unable to read resource " + resourceName +
//	 * ", input stream is null"); } catch (ActivitiException ex) { //do nothing, simply re-deploy LOGGER.error("Unable to read " + resourceName + " of deployment " +
//	 * existing.getName() + ", id: " + existing.getId() + ", will re-deploy"); } } if (doDeploy) { repositoryService.createDeployment() .name(deploymentName)
//	 * .addInputStream(resourceName, r.getInputStream()) .deploy(); LOGGER.warn("Deployed BPMN20 resource " + r.getFilename()); } } } }
//	 
//
//	// 由订单行号获得所有落在的任务ID
//	public static Set<String> getAllTaskIDByOrderLineID(String orderLineID) {
//		if (null == orderLineID)
//			return null;
//		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(orderLineID).list();
//		if (null == tasks || tasks.size() == 0)
//			return null;
//		Set<String> allTasksID = new HashSet<String>();
//		for (Task task : tasks) {
//			allTasksID.add(task.getTaskDefinitionKey());
//		}
//		return allTasksID;
//	}
//
//	// 由流程ID获得所有任务
//	public static List<Task> getAllTaskByProcessInstanceID(String processInstanceID) {
//		if (null == processInstanceID)
//			return null;
//		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceID).list();
//		if (null == tasks || tasks.size() == 0)
//			return null;
//		return tasks;
//	}
//
//	// 由订单行号获得所有落在的任务Name
//	public static Set<String> getAllTaskNameByOrderLineID(String orderLineID) {
//		if (null == orderLineID)
//			return null;
//		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(orderLineID).list();
//		if (null == tasks || tasks.size() == 0)
//			return null;
//		Set<String> allTasksID = new HashSet<String>();
//		for (Task task : tasks) {
//			String name = TaskIDs.getTaskNameByID(task.getTaskDefinitionKey());
//			if (null != name)
//				allTasksID.add(name);
//		}
//		return allTasksID;
//	}
//
//	// 检查指定订单行号的流程是否仍存活
//	public static boolean isProcessActiveByOrderLineID(String orderLineID) {
//		if (null == orderLineID)
//			return false;
//		ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//		if (null == processInstance)
//			return false;
//		return !processInstance.isEnded();
//	}
//
//	@SuppressWarnings("unused")
//	private static void deleteAllTasksByProcessInstanceID(String processInstanceID) {
//		List<Task> tasks = getAllTaskByProcessInstanceID(processInstanceID);
//		// dealProcesses.getRuntimeService().suspendProcessInstanceById(processInstanceID);
//		// dealProcesses.getRuntimeService().deleteProcessInstance(processInstanceID, Completed);
//		if (null != tasks) {
//			for (Task task : tasks) {
//				// if (task.getTaskDefinitionKey().equals(TaskIDs.PayConfirm) || task.getTaskDefinitionKey().equals(TaskIDs.SignInv))
//				// continue;
//				taskService.deleteTask(task.getId(), Completed);
//			}
//		}
//
//	}
//
//	public static void deleteAllTasksByOrderLineIDs(List<String> orderLineIDs) {
//		if (null == orderLineIDs || orderLineIDs.size() == 0)
//			return;
//		for (String orderLineID : orderLineIDs) {
//			ProcessInstance processInstance = getProcessInstanceByOrderID(orderLineID);
//			if (null == processInstance)
//				continue;
//			String processInstanceID = processInstance.getProcessInstanceId();
//			runtimeService.deleteProcessInstance(processInstanceID, Completed);
//			// deleteAllTasksByProcessInstanceID(processInstanceID);
//		}
//	}
}
