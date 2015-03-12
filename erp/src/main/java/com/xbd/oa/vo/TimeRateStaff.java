package com.xbd.oa.vo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial") 
@Entity
@Table(name = "time_rate_staff")
public class TimeRateStaff implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "node_timeout")
	private Integer nodeTimeout;
	
	@Column(name = "node_total")
	private Integer nodeTotal;
	
	@Column(name = "oa_staff")
	private Integer oaStaff;
	
	@Column(name = "operator")
	private String operator;
	
	@Column(name = "org")
	private String org;
	
	@Column(name = "time_rate")
	private Float timeRate;
	
	@Column(name = "week_no")
	private Integer weekNo;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNodeTimeout() {
		return nodeTimeout;
	}
	public void setNodeTimeout(Integer nodeTimeout) {
		this.nodeTimeout = nodeTimeout;
	}
	
	public Integer getNodeTotal() {
		return nodeTotal;
	}
	public void setNodeTotal(Integer nodeTotal) {
		this.nodeTotal = nodeTotal;
	}
	
	public Integer getOaStaff() {
		return oaStaff;
	}
	public void setOaStaff(Integer oaStaff) {
		this.oaStaff = oaStaff;
	}
	
	public Float getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(Float rate) {
		this.timeRate = rate;
	}
	
	public Integer getWeekNo() {
		return weekNo;
	}
	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
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
		if (!(object instanceof TimeRateStaff)) {
			return false;
		}
		TimeRateStaff other = (TimeRateStaff) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "TimeRateStaff[id=" + id + "]";
    }
  }