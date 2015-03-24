package com.xbd.oa.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 *	物流表 
 * @author fangwei
 * @version 创建时间：2015年3月24日  下午4:44:08
 */
@Entity
@Table(name = "oa_logistics")
public class OaLogistics extends CommonBean{
	private static final long serialVersionUID = 1L;

	private Integer oaOrderId;					//订单ID
	private String logisticsNum;				//物流单号
	private String logisticsCompany;			//物流公司
	private String deliveryPle;					//发货人
	private Float deliveryNum;					//发货数量
	private Date deliveryTime;					//发货日期
	private String carNum;						//车牌号
	private String remarks;						//备注
	private String fileUrl;						//装箱单URL

	@Column(name = "car_num",columnDefinition="varchar(50)")
	public String getCarNum() {
		return carNum;
	}

	@Column(name = "delivery_num",columnDefinition="float(10,2)")
	public Float getDeliveryNum() {
		return deliveryNum;
	}

	@Column(name = "delivery_ple",columnDefinition="varchar(50)")
	public String getDeliveryPle() {
		return deliveryPle;
	}

	@Column(name = "delivery_time",columnDefinition="timestamp null default null")
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	@Column(name = "file_url",columnDefinition="varchar(200)")
	public String getFileUrl() {
		return fileUrl;
	}

	@Column(name = "logistics_company",columnDefinition="varchar(50)")
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	@Column(name = "logistics_num",columnDefinition="varchar(50)")
	public String getLogisticsNum() {
		return logisticsNum;
	}

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "remarks",columnDefinition="varchar(50)")
	public String getRemarks() {
		return remarks;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public void setDeliveryNum(Float deliveryNum) {
		this.deliveryNum = deliveryNum;
	}

	public void setDeliveryPle(String deliveryPle) {
		this.deliveryPle = deliveryPle;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carNum == null) ? 0 : carNum.hashCode());
		result = prime * result + ((deliveryNum == null) ? 0 : deliveryNum.hashCode());
		result = prime * result + ((deliveryPle == null) ? 0 : deliveryPle.hashCode());
		result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result + ((fileUrl == null) ? 0 : fileUrl.hashCode());
		result = prime * result + ((logisticsCompany == null) ? 0 : logisticsCompany.hashCode());
		result = prime * result + ((logisticsNum == null) ? 0 : logisticsNum.hashCode());
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
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
		OaLogistics other = (OaLogistics) obj;
		if (carNum == null) {
			if (other.carNum != null)
				return false;
		} else if (!carNum.equals(other.carNum))
			return false;
		if (deliveryNum == null) {
			if (other.deliveryNum != null)
				return false;
		} else if (!deliveryNum.equals(other.deliveryNum))
			return false;
		if (deliveryPle == null) {
			if (other.deliveryPle != null)
				return false;
		} else if (!deliveryPle.equals(other.deliveryPle))
			return false;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (fileUrl == null) {
			if (other.fileUrl != null)
				return false;
		} else if (!fileUrl.equals(other.fileUrl))
			return false;
		if (logisticsCompany == null) {
			if (other.logisticsCompany != null)
				return false;
		} else if (!logisticsCompany.equals(other.logisticsCompany))
			return false;
		if (logisticsNum == null) {
			if (other.logisticsNum != null)
				return false;
		} else if (!logisticsNum.equals(other.logisticsNum))
			return false;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaLogistics [oaOrderId=" + oaOrderId + ", logisticsNum=" + logisticsNum + ", logisticsCompany=" + logisticsCompany + ", deliveryPle=" + deliveryPle + ", deliveryNum=" + deliveryNum + ", deliveryTime=" + deliveryTime + ", carNum=" + carNum + ", remarks=" + remarks + ", fileUrl=" + fileUrl + ", getId()=" + getId() + "]";
	}

}