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
@Table(name = "oa_qi_tao")
public class OaQiTao implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_order_id")
	private Integer oaOrderId;
	
	@Column(name = "qitao_receive_time")
	private Date qitaoReceiveTime;
	
	@Column(name = "qitao_send_time")
	private Date qitaoSendTime;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOaOrderId() {
		return oaOrderId;
	}
	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}
	
	public Date getQitaoReceiveTime() {
		return qitaoReceiveTime;
	}
	public void setQitaoReceiveTime(Date qitaoReceiveTime) {
		this.qitaoReceiveTime = qitaoReceiveTime;
	}
	
	public Date getQitaoSendTime() {
		return qitaoSendTime;
	}
	public void setQitaoSendTime(Date qitaoSendTime) {
		this.qitaoSendTime = qitaoSendTime;
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
		if (!(object instanceof OaQiTao)) {
			return false;
		}
		OaQiTao other = (OaQiTao) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaQiTao[id=" + id + "]";
    }
  }