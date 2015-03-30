package com.xbd.oa.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 齐套详情表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:07:56
 */
@Entity
@Table(name = "oa_qi_tao_detail")
public class OaQiTaoDetail extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Date createTime;			//创建时间
	private String department;			//责任部门
	private Integer oaQiTaoId;			//齐套ID
	private String operator;			//责任人
	private String project;				//项目
	private String tracke;				//异动

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", columnDefinition = "timestamp null default null ")
	public Date getCreateTime() {
		return createTime;
	}

	@Column(name = "department", columnDefinition="varchar(20)")
	public String getDepartment() {
		return department;
	}

	@Column(name = "oa_qi_tao_id")
	public Integer getOaQiTaoId() {
		return oaQiTaoId;
	}

	@Column(name = "operator", columnDefinition = "varchar(20)")
	public String getOperator() {
		return operator;
	}

	@Column(name = "project", columnDefinition = "varchar(100)")
	public String getProject() {
		return project;
	}

	@Column(name = "tracke", columnDefinition = "varchar(200)")
	public String getTracke() {
		return tracke;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setOaQiTaoId(Integer oaQiTaoId) {
		this.oaQiTaoId = oaQiTaoId;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public void setTracke(String tracke) {
		this.tracke = tracke;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((oaQiTaoId == null) ? 0 : oaQiTaoId.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((tracke == null) ? 0 : tracke.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaQiTaoDetail other = (OaQiTaoDetail) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (oaQiTaoId == null) {
			if (other.oaQiTaoId != null)
				return false;
		} else if (!oaQiTaoId.equals(other.oaQiTaoId))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (tracke == null) {
			if (other.tracke != null)
				return false;
		} else if (!tracke.equals(other.tracke))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaQiTaoDetail [createTime=" + createTime + ", department=" + department + ", oaQiTaoId=" + oaQiTaoId + ", operator=" + operator + ", project=" + project + ", tracke=" + tracke + ", id=" + id + "]";
	}
	
}