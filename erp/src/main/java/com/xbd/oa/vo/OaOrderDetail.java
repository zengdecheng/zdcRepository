package com.xbd.oa.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 订单详情表
 * @author fangwei
 * @version 创建时间：2015年3月26日  下午5:00:01
 */
@Entity
@Table(name = "oa_order_detail")
public class OaOrderDetail extends CommonBean {
	private static final long serialVersionUID = 1L;

	private String attachment;				//excel附件
	private String otherFile;				//其他附件
	private String discrip;					//附件描述
	private String content;					//备注
	private Integer inx;					//顺序号
	private Integer oaOrder;				//OaOrderId
	private String operator;				//操作员
	private String pic;						//图片
	private String procId;					//流程ID
	private String taskId;					//任务ID
	private Timestamp wfPlanStart;			//计划开始时间
	private Timestamp wfRealFinish;			//实际结束时间
	private Timestamp wfRealStart;			//实际开始时间
	private String wfStep;					//节点ID
	private Long wfStepDuration;			//节点市场
	private String wfStepName;				//节点name
	private String smsRemind;				//短信提醒
	private String smsTimeout;				//短信超时
	private String worker;					//采购员
	private Timestamp workTime;				//工作时间
	private String backFlag;				//


	@Column(name = "attachment",columnDefinition="varchar(250)")
	public String getAttachment() {
		return attachment;
	}

	@Column(name = "back_flag", columnDefinition = "CHAR(1)")
	public String getBackFlag() {
		return backFlag;
	}

	@Column(name = "content",columnDefinition="varchar(1000)")
	public String getContent() {
		return content;
	}

	@Column(name = "discrip",columnDefinition="varchar(250)")
	public String getDiscrip() {
		return discrip;
	}

	@Column(name = "inx")
	public Integer getInx() {
		return inx;
	}

	@Column(name = "oa_order")
	public Integer getOaOrder() {
		return oaOrder;
	}

	@Column(name = "operator",columnDefinition="varchar(50)")
	public String getOperator() {
		return operator;
	}

	@Column(name = "other_file",columnDefinition="varchar(200)")
	public String getOtherFile() {
		return otherFile;
	}

	@Column(name = "pic",columnDefinition="varchar(250)")
	public String getPic() {
		return pic;
	}

	@Column(name = "proc_id",columnDefinition="varchar(50)")
	public String getProcId() {
		return procId;
	}

	@Column(name = "sms_remind", columnDefinition = "CHAR(1)")
	public String getSmsRemind() {
		return smsRemind;
	}

	@Column(name = "sms_timeout", columnDefinition = "CHAR(1)")
	public String getSmsTimeout() {
		return smsTimeout;
	}

	@Column(name = "task_id",columnDefinition="varchar(50)")
	public String getTaskId() {
		return taskId;
	}

	@Column(name = "wf_plan_start",columnDefinition="timestamp null default null")
	public Timestamp getWfPlanStart() {
		return wfPlanStart;
	}

	@Column(name = "wf_real_finish",columnDefinition="timestamp null default null")
	public Timestamp getWfRealFinish() {
		return wfRealFinish;
	}

	@Column(name = "wf_real_start",columnDefinition="timestamp null default null")
	public Timestamp getWfRealStart() {
		return wfRealStart;
	}

	@Column(name = "wf_step",columnDefinition="varchar(50)")
	public String getWfStep() {
		return wfStep;
	}

	@Column(name = "wf_step_duration",columnDefinition="bigint")
	public Long getWfStepDuration() {
		return wfStepDuration;
	}

	@Column(name = "wf_step_name",columnDefinition="varchar(50)")
	public String getWfStepName() {
		return wfStepName;
	}

	@Column(name = "worker",columnDefinition="varchar(50)")
	public String getWorker() {
		return worker;
	}

	@Column(name = "work_time",columnDefinition="timestamp null default null")
	public Timestamp getWorkTime() {
		return workTime;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDiscrip(String discrip) {
		this.discrip = discrip;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public void setSmsTimeout(String smsTimeout) {
		this.smsTimeout = smsTimeout;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setWfPlanStart(java.sql.Timestamp wfPlanStart) {
		this.wfPlanStart = wfPlanStart;
	}

	public void setWfRealFinish(java.sql.Timestamp wfRealFinish) {
		this.wfRealFinish = wfRealFinish;
	}

	public void setWfRealStart(java.sql.Timestamp wfRealStart) {
		this.wfRealStart = wfRealStart;
	}

	public void setWfStep(String wfStep) {
		this.wfStep = wfStep;
	}

	public void setWfStepDuration(Long wfStepDuration) {
		this.wfStepDuration = wfStepDuration;
	}

	public void setWfStepName(String wfStepName) {
		this.wfStepName = wfStepName;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public void setWorkTime(java.sql.Timestamp workTime) {
		this.workTime = workTime;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + ((backFlag == null) ? 0 : backFlag.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((discrip == null) ? 0 : discrip.hashCode());
		result = prime * result + ((inx == null) ? 0 : inx.hashCode());
		result = prime * result + ((oaOrder == null) ? 0 : oaOrder.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((otherFile == null) ? 0 : otherFile.hashCode());
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result + ((procId == null) ? 0 : procId.hashCode());
		result = prime * result + ((smsRemind == null) ? 0 : smsRemind.hashCode());
		result = prime * result + ((smsTimeout == null) ? 0 : smsTimeout.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result + ((wfPlanStart == null) ? 0 : wfPlanStart.hashCode());
		result = prime * result + ((wfRealFinish == null) ? 0 : wfRealFinish.hashCode());
		result = prime * result + ((wfRealStart == null) ? 0 : wfRealStart.hashCode());
		result = prime * result + ((wfStep == null) ? 0 : wfStep.hashCode());
		result = prime * result + ((wfStepDuration == null) ? 0 : wfStepDuration.hashCode());
		result = prime * result + ((wfStepName == null) ? 0 : wfStepName.hashCode());
		result = prime * result + ((workTime == null) ? 0 : workTime.hashCode());
		result = prime * result + ((worker == null) ? 0 : worker.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaOrderDetail other = (OaOrderDetail) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (backFlag == null) {
			if (other.backFlag != null)
				return false;
		} else if (!backFlag.equals(other.backFlag))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (discrip == null) {
			if (other.discrip != null)
				return false;
		} else if (!discrip.equals(other.discrip))
			return false;
		if (inx == null) {
			if (other.inx != null)
				return false;
		} else if (!inx.equals(other.inx))
			return false;
		if (oaOrder == null) {
			if (other.oaOrder != null)
				return false;
		} else if (!oaOrder.equals(other.oaOrder))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (otherFile == null) {
			if (other.otherFile != null)
				return false;
		} else if (!otherFile.equals(other.otherFile))
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (procId == null) {
			if (other.procId != null)
				return false;
		} else if (!procId.equals(other.procId))
			return false;
		if (smsRemind == null) {
			if (other.smsRemind != null)
				return false;
		} else if (!smsRemind.equals(other.smsRemind))
			return false;
		if (smsTimeout == null) {
			if (other.smsTimeout != null)
				return false;
		} else if (!smsTimeout.equals(other.smsTimeout))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		if (wfPlanStart == null) {
			if (other.wfPlanStart != null)
				return false;
		} else if (!wfPlanStart.equals(other.wfPlanStart))
			return false;
		if (wfRealFinish == null) {
			if (other.wfRealFinish != null)
				return false;
		} else if (!wfRealFinish.equals(other.wfRealFinish))
			return false;
		if (wfRealStart == null) {
			if (other.wfRealStart != null)
				return false;
		} else if (!wfRealStart.equals(other.wfRealStart))
			return false;
		if (wfStep == null) {
			if (other.wfStep != null)
				return false;
		} else if (!wfStep.equals(other.wfStep))
			return false;
		if (wfStepDuration == null) {
			if (other.wfStepDuration != null)
				return false;
		} else if (!wfStepDuration.equals(other.wfStepDuration))
			return false;
		if (wfStepName == null) {
			if (other.wfStepName != null)
				return false;
		} else if (!wfStepName.equals(other.wfStepName))
			return false;
		if (workTime == null) {
			if (other.workTime != null)
				return false;
		} else if (!workTime.equals(other.workTime))
			return false;
		if (worker == null) {
			if (other.worker != null)
				return false;
		} else if (!worker.equals(other.worker))
			return false;
		return true;
	}

	public String toString() {
		return "OaOrderDetail [attachment=" + attachment + ", otherFile=" + otherFile + ", discrip=" + discrip + ", content=" + content + ", inx=" + inx + ", oaOrder=" + oaOrder + ", operator=" + operator + ", pic=" + pic + ", procId=" + procId + ", taskId=" + taskId + ", wfPlanStart=" + wfPlanStart + ", wfRealFinish=" + wfRealFinish + ", wfRealStart=" + wfRealStart + ", wfStep=" + wfStep + ", wfStepDuration=" + wfStepDuration + ", wfStepName=" + wfStepName + ", smsRemind=" + smsRemind + ", smsTimeout=" + smsTimeout + ", worker=" + worker + ", workTime=" + workTime + ", backFlag=" + backFlag + ", id=" + id + "]";
	}
	
}