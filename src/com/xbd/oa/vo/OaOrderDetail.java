package com.xbd.oa.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_order_detail")
public class OaOrderDetail implements Serializable {
	@Column(name = "attachment")
	private String attachment;

	@Column(name = "other_file")
	private String otherFile;

	@Column(name = "discrip")
	private String discrip;

	@Column(name = "content")
	private String content;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "inx")
	private Integer inx;

	@Column(name = "oa_order")
	private Integer oaOrder;

	@Column(name = "operator")
	private String operator;

	@Column(name = "pic")
	private String pic;

	@Column(name = "proc_id")
	private String procId;

	@Column(name = "task_id")
	private String taskId;

	@Column(name = "wf_plan_start")
	private java.sql.Timestamp wfPlanStart;

	@Column(insertable = false, name = "wf_real_finish")
	private java.sql.Timestamp wfRealFinish;

	@Column(name = "wf_real_start")
	private java.sql.Timestamp wfRealStart;

	@Column(name = "wf_step")
	private String wfStep;

	@Column(name = "wf_step_duration")
	private Long wfStepDuration;

	@Column(name = "wf_step_name")
	private String wfStepName;

	@Column(name = "sms_remind")
	private String smsRemind;

	@Column(name = "sms_timeout")
	private String smsTimeout;
	
	@Column(name="worker")
	private String worker;
	
	@Column(name="work_time")
	private java.sql.Timestamp workTime;
	
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInx() {
		return inx;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	public Integer getOaOrder() {
		return oaOrder;
	}

	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public java.sql.Timestamp getWfPlanStart() {
		return wfPlanStart;
	}

	public void setWfPlanStart(java.sql.Timestamp wfPlanStart) {
		this.wfPlanStart = wfPlanStart;
	}

	public java.sql.Timestamp getWfRealFinish() {
		return wfRealFinish;
	}

	public void setWfRealFinish(java.sql.Timestamp wfRealFinish) {
		this.wfRealFinish = wfRealFinish;
	}

	public String getOtherFile() {
		return otherFile;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}

	public java.sql.Timestamp getWfRealStart() {
		return wfRealStart;
	}

	public void setWfRealStart(java.sql.Timestamp wfRealStart) {
		this.wfRealStart = wfRealStart;
	}

	public String getWfStep() {
		return wfStep;
	}

	public void setWfStep(String wfStep) {
		this.wfStep = wfStep;
	}

	public Long getWfStepDuration() {
		return wfStepDuration;
	}

	public void setWfStepDuration(Long wfStepDuration) {
		this.wfStepDuration = wfStepDuration;
	}

	public String getWfStepName() {
		return wfStepName;
	}

	public void setWfStepName(String wfStepName) {
		this.wfStepName = wfStepName;
	}

	public String getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getSmsTimeout() {
		return smsTimeout;
	}

	public void setSmsTimeout(String smsTimeout) {
		this.smsTimeout = smsTimeout;
	}

	public String getDiscrip() {
		return discrip;
	}

	public void setDiscrip(String discrip) {
		this.discrip = discrip;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public java.sql.Timestamp getWorkTime() {
		return workTime;
	}

	public void setWorkTime(java.sql.Timestamp workTime) {
		this.workTime = workTime;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof OaOrderDetail)) {
			return false;
		}
		OaOrderDetail other = (OaOrderDetail) object;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaOrderDetail[id=" + id + "]";
	}
}