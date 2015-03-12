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
@Table(name = "time_rate_week")
public class TimeRateWeek implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "order_timeout")
	private Integer orderTimeout;
	
	@Column(name = "order_total")
	private Integer orderTotal;
	
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
	
	public Integer getOrderTimeout() {
		return orderTimeout;
	}
	public void setOrderTimeout(Integer orderTimeout) {
		this.orderTimeout = orderTimeout;
	}
	
	public Integer getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
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
	
@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TimeRateWeek)) {
			return false;
		}
		TimeRateWeek other = (TimeRateWeek) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "TimeRateWeek[id=" + id + "]";
    }
  }