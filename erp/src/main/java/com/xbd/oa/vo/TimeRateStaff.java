package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:24:20
 */
@Entity
@Table(name = "time_rate_staff")
public class TimeRateStaff extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Integer nodeTimeout;
	private Integer nodeTotal;
	private Integer oaStaff;
	private String operator;
	private String org;
	private Float timeRate;

	@Column(name = "week_no")
	private Integer weekNo;

	@Column(name = "node_timeout")
	public Integer getNodeTimeout() {
		return nodeTimeout;
	}

	@Column(name = "node_total")
	public Integer getNodeTotal() {
		return nodeTotal;
	}

	@Column(name = "oa_staff")
	public Integer getOaStaff() {
		return oaStaff;
	}

	@Column(name = "operator",columnDefinition="varchar(50)")
	public String getOperator() {
		return operator;
	}

	@Column(name = "org",columnDefinition="varchar(50)")
	public String getOrg() {
		return org;
	}

	@Column(name = "time_rate",columnDefinition="float(5,2)")
	public Float getTimeRate() {
		return timeRate;
	}

	public Integer getWeekNo() {
		return weekNo;
	}

	public void setNodeTimeout(Integer nodeTimeout) {
		this.nodeTimeout = nodeTimeout;
	}

	public void setNodeTotal(Integer nodeTotal) {
		this.nodeTotal = nodeTotal;
	}

	public void setOaStaff(Integer oaStaff) {
		this.oaStaff = oaStaff;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public void setTimeRate(Float rate) {
		this.timeRate = rate;
	}

	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}

	@Override
	public String toString() {
		return "TimeRateStaff [nodeTimeout=" + nodeTimeout + ", nodeTotal=" + nodeTotal + ", oaStaff=" + oaStaff + ", operator=" + operator + ", org=" + org + ", timeRate=" + timeRate + ", weekNo=" + weekNo + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeTimeout == null) ? 0 : nodeTimeout.hashCode());
		result = prime * result + ((nodeTotal == null) ? 0 : nodeTotal.hashCode());
		result = prime * result + ((oaStaff == null) ? 0 : oaStaff.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((timeRate == null) ? 0 : timeRate.hashCode());
		result = prime * result + ((weekNo == null) ? 0 : weekNo.hashCode());
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
		TimeRateStaff other = (TimeRateStaff) obj;
		if (nodeTimeout == null) {
			if (other.nodeTimeout != null)
				return false;
		} else if (!nodeTimeout.equals(other.nodeTimeout))
			return false;
		if (nodeTotal == null) {
			if (other.nodeTotal != null)
				return false;
		} else if (!nodeTotal.equals(other.nodeTotal))
			return false;
		if (oaStaff == null) {
			if (other.oaStaff != null)
				return false;
		} else if (!oaStaff.equals(other.oaStaff))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
			return false;
		if (timeRate == null) {
			if (other.timeRate != null)
				return false;
		} else if (!timeRate.equals(other.timeRate))
			return false;
		if (weekNo == null) {
			if (other.weekNo != null)
				return false;
		} else if (!weekNo.equals(other.weekNo))
			return false;
		return true;
	}
	
}