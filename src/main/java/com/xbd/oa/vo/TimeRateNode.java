package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:27:03
 */
@Entity
@Table(name = "time_rate_node")
public class TimeRateNode extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Integer node;
	private Integer nodeTimeout;
	private Integer nodeTotal;
	private String nodeName;
	private Float timeRate;
	private Integer weekNo;
	private Integer dayWeek;

	@Column(name = "day_week")
	public Integer getDayWeek() {
		return dayWeek;
	}

	@Column(name = "node")
	public Integer getNode() {
		return node;
	}

	@Column(name = "node_name",columnDefinition="varchar(20)")
	public String getNodeName() {
		return nodeName;
	}

	@Column(name = "node_timeout")
	public Integer getNodeTimeout() {
		return nodeTimeout;
	}

	@Column(name = "node_total")
	public Integer getNodeTotal() {
		return nodeTotal;
	}

	@Column(name = "time_rate",columnDefinition="float(5,2)")
	public Float getTimeRate() {
		return timeRate;
	}

	@Column(name = "week_no")
	public Integer getWeekNo() {
		return weekNo;
	}

	public void setDayWeek(Integer dayWeek) {
		this.dayWeek = dayWeek;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setNodeTimeout(Integer nodeTimeout) {
		this.nodeTimeout = nodeTimeout;
	}

	public void setNodeTotal(Integer nodeTotal) {
		this.nodeTotal = nodeTotal;
	}

	public void setTimeRate(Float timeRate) {
		this.timeRate = timeRate;
	}

	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayWeek == null) ? 0 : dayWeek.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((nodeTimeout == null) ? 0 : nodeTimeout.hashCode());
		result = prime * result + ((nodeTotal == null) ? 0 : nodeTotal.hashCode());
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
		TimeRateNode other = (TimeRateNode) obj;
		if (dayWeek == null) {
			if (other.dayWeek != null)
				return false;
		} else if (!dayWeek.equals(other.dayWeek))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
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

	@Override
	public String toString() {
		return "TimeRateNode [node=" + node + ", nodeTimeout=" + nodeTimeout + ", nodeTotal=" + nodeTotal + ", nodeName=" + nodeName + ", timeRate=" + timeRate + ", weekNo=" + weekNo + ", dayWeek=" + dayWeek + ", id=" + id + "]";
	}
	
}