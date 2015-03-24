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

}