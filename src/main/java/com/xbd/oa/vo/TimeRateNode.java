package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "time_rate_node")
public class TimeRateNode implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "node")
	private Integer node;
	
	@Column(name = "node_timeout")
	private Integer nodeTimeout;
	
	@Column(name = "node_total")
	private Integer nodeTotal;
	
	@Column(name = "node_name")
	private String nodeName;
	
	@Column(name = "time_rate")
	private Float timeRate;
	
	@Column(name = "week_no")
	private Integer weekNo;
	
	@Column(name = "day_week")
	private Integer dayWeek;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNode() {
		return node;
	}
	public void setNode(Integer node) {
		this.node = node;
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
	
	public Float getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(Float timeRate) {
		this.timeRate = timeRate;
	}
	
	public Integer getWeekNo() {
		return weekNo;
	}
	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}
	
	public Integer getDayWeek() {
		return dayWeek;
	}
	public void setDayWeek(Integer dayWeek) {
		this.dayWeek = dayWeek;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
		if (!(object instanceof TimeRateNode)) {
			return false;
		}
		TimeRateNode other = (TimeRateNode) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "TimeRateNode[id=" + id + "]";
    }
  }