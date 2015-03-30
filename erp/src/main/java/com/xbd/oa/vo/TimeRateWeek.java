package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:22:36
 */
@Entity
@Table(name = "time_rate_week")
public class TimeRateWeek extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Integer orderTimeout;
	private Integer orderTotal;
	private Float timeRate;
	private Integer weekNo;

	@Column(name = "order_timeout")
	public Integer getOrderTimeout() {
		return orderTimeout;
	}

	@Column(name = "order_total")
	public Integer getOrderTotal() {
		return orderTotal;
	}

	@Column(name = "time_rate",columnDefinition="float(5,2)")
	public Float getTimeRate() {
		return timeRate;
	}

	@Column(name = "week_no")
	public Integer getWeekNo() {
		return weekNo;
	}

	public void setOrderTimeout(Integer orderTimeout) {
		this.orderTimeout = orderTimeout;
	}

	public void setOrderTotal(Integer orderTotal) {
		this.orderTotal = orderTotal;
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
		result = prime * result + ((orderTimeout == null) ? 0 : orderTimeout.hashCode());
		result = prime * result + ((orderTotal == null) ? 0 : orderTotal.hashCode());
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
		TimeRateWeek other = (TimeRateWeek) obj;
		if (orderTimeout == null) {
			if (other.orderTimeout != null)
				return false;
		} else if (!orderTimeout.equals(other.orderTimeout))
			return false;
		if (orderTotal == null) {
			if (other.orderTotal != null)
				return false;
		} else if (!orderTotal.equals(other.orderTotal))
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
		return "TimeRateWeek [orderTimeout=" + orderTimeout + ", orderTotal=" + orderTotal + ", timeRate=" + timeRate + ", weekNo=" + weekNo + ", id=" + id + "]";
	}
	
}