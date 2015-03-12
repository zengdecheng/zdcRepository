package com.xbd.oa.vo;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_qi_tao_detail")
public class OaQiTaoDetail implements Serializable {
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "department")
	private String department;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_qi_tao_id")
	private Integer oaQiTaoId;
	
	@Column(name = "operator")
	private String operator;
	
	@Column(name = "project")
	private String project;
	
	@Column(name = "tracke")
	private String tracke;
	

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOaQiTaoId() {
		return oaQiTaoId;
	}
	public void setOaQiTaoId(Integer oaQiTaoId) {
		this.oaQiTaoId = oaQiTaoId;
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
	public String getTracke() {
		return tracke;
	}
	public void setTracke(String tracke) {
		this.tracke = tracke;
	}
	
@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof OaQiTaoDetail)) {
			return false;
		}
		OaQiTaoDetail other = (OaQiTaoDetail) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaQiTaoDetail[id=" + id + "]";
    }
  }