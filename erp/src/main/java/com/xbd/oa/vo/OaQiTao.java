package com.xbd.oa.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 齐套表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:13:43
 */
@Entity
@Table(name = "oa_qi_tao")
public class OaQiTao extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Integer oaOrderId;			//订单ID
	private Date qitaoReceiveTime;		//齐套接收时间
	private Date qitaoSendTime;			//齐套外发时间

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qitao_receive_time", columnDefinition = "timestamp null default null ")
	public Date getQitaoReceiveTime() {
		return qitaoReceiveTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "qitao_send_time", columnDefinition = "timestamp null default null ")
	public Date getQitaoSendTime() {
		return qitaoSendTime;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setQitaoReceiveTime(Date qitaoReceiveTime) {
		this.qitaoReceiveTime = qitaoReceiveTime;
	}

	public void setQitaoSendTime(Date qitaoSendTime) {
		this.qitaoSendTime = qitaoSendTime;
	}

	@Override
	public String toString() {
		return "OaQiTao [oaOrderId=" + oaOrderId + ", qitaoReceiveTime=" + qitaoReceiveTime + ", qitaoSendTime=" + qitaoSendTime + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((qitaoReceiveTime == null) ? 0 : qitaoReceiveTime.hashCode());
		result = prime * result + ((qitaoSendTime == null) ? 0 : qitaoSendTime.hashCode());
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
		OaQiTao other = (OaQiTao) obj;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (qitaoReceiveTime == null) {
			if (other.qitaoReceiveTime != null)
				return false;
		} else if (!qitaoReceiveTime.equals(other.qitaoReceiveTime))
			return false;
		if (qitaoSendTime == null) {
			if (other.qitaoSendTime != null)
				return false;
		} else if (!qitaoSendTime.equals(other.qitaoSendTime))
			return false;
		return true;
	}
	
}