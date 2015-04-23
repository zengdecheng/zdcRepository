package com.xbd.test.activiti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiTestCase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.xbd.oa.utils.DateUtils;

//public class TestActiviti extends PluggableActivitiTestCase {
public class TestActiviti extends ActivitiTestCase {

	public static final Logger logger = Logger.getLogger(TestActiviti.class);

	// 部署+启动
	// @org.activiti.engine.test.Deployment(resources = "diagrams/oawf3.bpmn")
	// public void testDeploymentAndStart(){
	// runtimeService.startProcessInstanceByKey("oawf3");
	// logger.error("aaaaaaaaaaaaaaa");
	// }

	// 部署流程定义 从classPath
	public void deploymentProcessDefinition() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/diagrams.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("第二个流程图")
		// .addClasspathResource("diagrams/XbdERP.bpmn")
		// .addClasspathResource("diagrams/XbdERP.png")
				.addZipInputStream(zipInputStream).deploy();
		logger.error(deployment.getId());
		logger.error(deployment.getDeploymentTime());
		logger.error(deployment.getName());
	}

	// 部署流程
	public void testDeployment() {
		Deployment dp = repositoryService.createDeployment().addClasspathResource("diagrams/oawf3.bpmn").name("oawf3.bpmn").deploy();
		logger.error("DeploymentId:" + dp.getId());
	}

	// 启动流程
	public void testStartProcess() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Integer", 1);
		map.put("Date", DateUtils.getCurrentDate());
		map.put("String", "Test!!!");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oawf2", map);
		logger.error("ProcessInstanceId:" + processInstance.getId());
		logger.error("deploymentId:" + processInstance.getProcessInstanceId());
		logger.error("businessKey:" + processInstance.getBusinessKey());
		logger.error("ProcessDefinitionId:" + processInstance.getProcessDefinitionId());
		logger.error("ProcessDefinitionKey:" + processInstance.getActivityId());
		logger.error("ProcessDefinitionName:" + processInstance.getProcessDefinitionId());
		logger.error("ProcessVariables:" + processInstance.getProcessVariables());
		logger.error("processInstanceId:" + processInstance.getId());
		logger.error("processInstanceName:" + processInstance.getName());
	}

	// 查询流程定义
	public void testfindProcessDefinition() {
		List<ProcessDefinition> list2 = repositoryService.createProcessDefinitionQuery()
		/** 指定查询条件 */
		// .deploymentId(deploymentId) //使用部署对象的ID查询
		// .processDefinitionId(processDefinitionId) //使用流程定义ID查询
		// .processDefinitionKey(processDefinitionKey) //使用流程定义ID查询
		// .processDefinitionNameLike(processDefinitionNameLike) //使用流程定义ID查询

				/** 排序 */
				.orderByProcessDefinitionId().asc()
				// .orderByProcessDefinitionKey()
				/** 最新版本 **/
				// .latestVersion()
				/** 返回结果集 */
				.list(); // 返回一个集合列表，封装流程定义
		// .singleResult(); //返回唯一结果集
		// .count();//返回结果集数量
		// .listPage(firstResult, maxResults);//分页查询
		for (ProcessDefinition pd : list2) {
			logger.error("id:" + pd.getId() + ",");
			logger.error("name:" + pd.getName() + ",");
			logger.error("key:" + pd.getKey() + ",");
			logger.error("version:" + pd.getVersion());
			logger.error("ResourceName:" + pd.getResourceName());
			logger.error("DiagramResourceName:" + pd.getDiagramResourceName());
			logger.error("DeploymentId:" + pd.getDeploymentId());
			logger.error("Description:" + pd.getDescription());
		}
	}

	// 得到所有的任务
	public void testGetTask() {
		List<Task> tasks = taskService.createTaskQuery().list();
		for (Task task : tasks) {
			logger.error("Task available: " + task.getName() + "   id:" + task.getId());
		}
	}

	// 分配任务
	public void testClaim() {
		taskService.claim("60007", "zhangdsa");
	}

	// 将个人任务退回到组任务，提前，之前一定是个组任务
	public void testSetAssigee() {
		taskService.setAssignee("60007", null);
	}

	// 向组任务中添加成员
	public void testAddGroupUser() {
		taskService.addCandidateUser("60007", "sdafasdf");
	}

	// 向组任务中减少成员
	public void testDeleteGroupUser() {
		taskService.deleteCandidateUser("60007", "asdf");
	}

	// 获取流程变量
	public void testGetVariables() {
		String taskId = "60002";
		Map<String, Object> map = taskService.getVariables(taskId);
		logger.error(map);
		List<String> list = new ArrayList<String>();
		list.add("调休天数");
		list.add("调休日期");
		map = taskService.getVariables(taskId, list);
		logger.error(map);
		Date date = (Date) taskService.getVariableLocal(taskId, "调休天数");
		logger.error(date);
		map = runtimeService.getVariables("47501");
		logger.error(map);
	}

	// 下一步
	public void testFinshTask() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("22503", "finsh22503");
		taskService.complete("22507");
	}

	// ReceiveTask 下一步
	public void testReceiveTask() {
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("receiveTask");
		Execution ex2 = runtimeService.createExecutionQuery().activityId("receivetask1").processInstanceId(pi.getId()).singleResult();
		runtimeService.signal(ex2.getId());
	}

	// 查看个人任务
	public void testFindPersonalTask() {
		List<Task> list = taskService.createTaskQuery().taskAssignee("333").list();
		for (Task task : list) {
			logger.error("Task available: " + task.getName() + "   id:" + task.getId());
		}
	}

	// 查看组任务
	public void testFindGroupTask() {
		List<Task> list = taskService.createTaskQuery().taskCandidateGroup("111").list();
		for (Task task : list) {
			logger.error("Task available: " + task.getName() + "   id:" + task.getId());
		}
	}

	// 判断流程是否结束
	public void testIsProcessInstanceEnded() {
		assertProcessEnded("15001");
	}

	public void testIsProcessEnd() {
		String taskId = "47501";
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
		logger.error(pi);
	}
	public void testFindHisPersonTask1() {
		String processInstanceId = "35001";
		List<HistoricIdentityLink> list = processEngine.getHistoryService().getHistoricIdentityLinksForProcessInstance(processInstanceId);
		logger.error(list);
	}
	
	// 查询历史任务
	public void testFindHisPersonTask2() {
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery().list();
		logger.error(list);
	}

	public void testFindHistoryTask() {
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskAssignee("111").list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance ht : list) {
				logger.error(ht.getId());
				logger.error(ht.getName());
				logger.error(ht.getProcessDefinitionId());
				logger.error(ht.getProcessInstanceId());
				logger.error(ht.getProcessVariables());
				logger.error(ht.getStartTime());
				logger.error(ht.getEndTime());
				logger.error(ht.getDueDate());
				logger.error(ht.getDurationInMillis());
			}
		}
	}

	// 删除流程定义
	public void testDelAllDeployment() {
		List<Deployment> dps = repositoryService.createDeploymentQuery().list();
		for (Deployment dp : dps) {
			repositoryService.deleteDeployment(dp.getId(), true);
		}
	}

	// 输出流程图
	public void testViewImage() throws Exception {
		String deploymentId = "168101";
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		if (imageName != null) {
			File f = new File("e:/" + imageName);
			InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
			FileUtils.copyInputStreamToFile(in, f);
		}
	}
	
	public void testOutputViewImage() throws FileNotFoundException{
		OutputStream os= new FileOutputStream(new File("E:/11"));
		String deploymentId= "12501";
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
	
	//删除流程实例
	public void testDeleteProcessByProcessID() {
		String processInstanceID = "2501";
		runtimeService.deleteProcessInstance(processInstanceID, "爱的方式撒地方三点");
	}
	
	//动态跳转
	public void testJumpTask(){
		TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;  
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("aaa", "aaaa");
		taskServiceImpl.getCommandExecutor().execute(new JumpTask("2501", "b_create_yangyi_1",map));  
	}

	// 查询流程变量的历史表
	public void testFindHistoryProcessVariables() {
		List<HistoricVariableInstance> list = processEngine.getHistoryService().createHistoricVariableInstanceQuery().variableName("调休日期").list();
		System.out.println(list);
	}

	// 模拟设置和获取流程变量的场景
	public void testSetAndGetVariables() {
		// 与流程实例，执行对象，正在执行的
		RuntimeService runtimeService = processEngine.getRuntimeService();

		// 与流程任务 正在执行的
		TaskService taskService = processEngine.getTaskService();

		// 设置流程变量
		// runtimeService.setVariable(executionId, variableName, value);//表示使用执行对象ID，和流程变量的名称，设置流程变量，一次设置一个值
		// runtimeService.setVariables(executionId, variables); //一次设置多个

		// taskService.setVariable(taskId, variableName, value);
		// taskService.setVariables(taskId, variables);

		// runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)//启动流程实例的同时，可以设置流程变量
		// taskService.complete(taskId, variables); //完成任务同时设置流程变量

		// 获取流程变量
		// taskService.getVariable(taskId, variableName)
		// taskService.getVariables(taskId)
		// runtimeService.getVariable(executionId, variableName)
		// runtimeService.getVariables(executionId)
	}
}
