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
@Table(name = "oa_logistics")
public class OaLogistics implements Serializable {
	@Column(name = "oa_order_id")
	private Integer oaOrderId;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "logistics_num")
	private String logisticsNum;
	
	@Column(name = "logistics_company")
	private String logisticsCompany;
	
	@Column(name = "delivery_ple")
	private String deliveryPle;

	@Column(name = "delivery_num")
	private Float deliveryNum;
	
	@Column(name = "delivery_time")
	private Date deliveryTime;
	
	@Column(name = "car_num")
	private String carNum;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "file_url")
	private String fileUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getDeliveryPle() {
		return deliveryPle;
	}

	public void setDeliveryPle(String deliveryPle) {
		this.deliveryPle = deliveryPle;
	}

	public Float getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(Float deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getOaOrderId() {
		return oaOrderId;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
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
		if (!(object instanceof OaLogistics)) {
			return false;
		}
		OaLogistics other = (OaLogistics) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
	@Override
	 public String toString() {
        return "OaLogistics[id=" + id + "]";
    }
  }