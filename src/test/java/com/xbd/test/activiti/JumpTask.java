package com.xbd.test.activiti;

import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.task.Comment;

public class JumpTask implements Command<Comment> {  
    
    protected String executionId;  
    protected String activityId;  
    protected Map<String,Object> variables;
      
    
    public JumpTask(String executionId, String activityId) {  
    	this.executionId = executionId;  
    	this.activityId = activityId;  
    }  
    
    public JumpTask(String executionId, String activityId,Map<String,Object> variables) {  
    	this.executionId = executionId;  
    	this.activityId = activityId;  
    	this.variables = variables;  
    }  
    
    public Comment execute(CommandContext commandContext) {  
        for (TaskEntity taskEntity : Context.getCommandContext().getTaskEntityManager().findTasksByExecutionId(executionId)) {  
            Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "jump", false);  
        }  
        ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);  
        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();  
        ActivityImpl activity = processDefinition.findActivity(activityId);  
        executionEntity.setVariables(variables);
        executionEntity.executeActivity(activity);  
        return null;  
    }  
  
}  