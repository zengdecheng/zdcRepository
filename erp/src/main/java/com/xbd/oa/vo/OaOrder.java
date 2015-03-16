package com.xbd.oa.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_order")
public class OaOrder implements Serializable {
	@Column(name = "begin_time")
	private java.sql.Timestamp beginTime;

	@Column(name = "city")
	private String city;

	@Column(name = "cloth_class")
	private String clothClass;

	@Column(name = "cus_code")
	private String cusCode;

	@Column(name = "sample_size")
	private String sampleSize;

	@Column(name = "cus_id")
	private Integer cusId;

	@Column(name = "cus_name")
	private String cusName;

	@Column(name = "end_time")
	private java.sql.Timestamp endTime;

	@Column(name = "except_finish")
	private java.sql.Timestamp exceptFinish;

	@Column(name = "file_url")
	private String fileUrl;

	@Column(name = "his_opt")
	private String hisOpt;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "is_urgent")
	private String isUrgent;

	@Column(name = "sell_memo")
	private String sellMemo;

	@Column(name = "contract_special_memo")
	private String contractSpecialMemo;

	@Column(name = "memo")
	private String memo;

	@Column(name = "mr_name")
	private String mrName;

	@Column(name = "oa_order_detail")
	private Integer oaOrderDetail;

	@Column(name = "oa_order_num_id")
	private Integer oaOrderNumId;

	@Column(name = "operator")
	private String operator;

	@Column(name = "order_code")
	private String orderCode;

	@Column(name = "pay_type")
	private String payType;

	@Column(name = "pic_url")
	private String picUrl;

	@Column(name = "picture_back")
	private String pictureBack;

	@Column(name = "picture_front")
	private String pictureFront;

	@Column(name = "price_max")
	private Float priceMax;

	@Column(name = "price_min")
	private Float priceMin;

	@Column(name = "sales")
	private String sales;

	@Column(name = "sell_order_code")
	private String sellOrderCode;

	@Column(name = "sell_order_id")
	private Integer sellOrderId;

	@Column(name = "sendType")
	private String sendtype;

	@Column(name = "status")
	private String status;

	@Column(name = "style_class")
	private String styleClass;

	@Column(name = "style_code")
	private String styleCode;

	@Column(name = "style_craft")
	private String styleCraft;

	@Column(name = "style_desc")
	private String styleDesc;

	@Column(name = "style_id")
	private Integer styleId;

	@Column(name = "style_type")
	private String styleType;

	@Column(name = "style_url")
	private String styleUrl;

	@Column(name = "tel")
	private String tel;

	@Column(name = "time_rate")
	private Float timeRate;

	@Column(name = "contract_amount")
	private Float contractAmount;

	@Column(name = "confirm_staff")
	private String confirmStaff;

	@Column(name = "tpe_name")
	private String tpeName;

	@Column(name = "type")
	private String type;

	@Column(name = "want_cnt")
	private Integer wantCnt;

	@Column(name = "wf_plan_start")
	private java.sql.Timestamp wfPlanStart;

	@Column(name = "wf_real_start")
	private java.sql.Timestamp wfRealStart;

	@Column(name = "wf_step")
	private String wfStep;

	@Column(name = "wf_step_duration")
	private Long wfStepDuration;

	@Column(name = "wf_step_name")
	private String wfStepName;

	@Column(name = "want_dh_cnt")
	private Integer wantDhcnt;

	@Column(name = "related_order_code")
	private String relatedOrderCode;

	@Column(name = "related_order_type")
	private String relatedOrderType;

	@Column(name = "related_order_id")
	private Integer relatedOrderId;

	@Column(name = "terminate_user")
	private String terminateUser;

	@Column(name = "terminate_memo")
	private String terminateMemo;

	@Column(name = "terminate_time")
	private java.sql.Timestamp terminateTime;

	@Column(name = "repeat_reason")
	private String repeatReason;

	@Column(name = "repeat_num")
	private Integer repeatNum;

	@Column(name = "is_preproduct")
	private String isPreproduct;

	@Column(name = "pre_version_date")
	private Timestamp preVersionDate;

	@Column(name = "preproduct_days")
	private Integer PreproductDays;

	@Column(name = "sell_ready_time")
	private Float sellReadyTime;

	@Column(name = "standard_time")
	private Float standardTime;

	@Column(name = "craft_time")
	private Float craftTime;

	@Column(name = "feeding_time")
	private Timestamp feedingTime;

	public String getRelatedOrderCode() {
		return relatedOrderCode;
	}

	public void setRelatedOrderCode(String relatedOrderCode) {
		this.relatedOrderCode = relatedOrderCode;
	}

	public String getRelatedOrderType() {
		return relatedOrderType;
	}

	public void setRelatedOrderType(String relatedOrderType) {
		this.relatedOrderType = relatedOrderType;
	}

	public Integer getRelatedOrderId() {
		return relatedOrderId;
	}

	public void setRelatedOrderId(Integer relatedOrderId) {
		this.relatedOrderId = relatedOrderId;
	}

	public java.sql.Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(java.sql.Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getClothClass() {
		return clothClass;
	}

	public void setClothClass(String clothClass) {
		this.clothClass = clothClass;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public Integer getCusId() {
		return cusId;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public java.sql.Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}

	public java.sql.Timestamp getExceptFinish() {
		return exceptFinish;
	}

	public void setExceptFinish(java.sql.Timestamp exceptFinish) {
		this.exceptFinish = exceptFinish;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getHisOpt() {
		return hisOpt;
	}

	public void setHisOpt(String hisOpt) {
		this.hisOpt = hisOpt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public String getSellMemo() {
		return sellMemo;
	}

	public void setSellMemo(String sellMemo) {
		this.sellMemo = sellMemo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMrName() {
		return mrName;
	}

	public void setMrName(String mrName) {
		this.mrName = mrName;
	}

	public Integer getOaOrderDetail() {
		return oaOrderDetail;
	}

	public void setOaOrderDetail(Integer oaOrderDetail) {
		this.oaOrderDetail = oaOrderDetail;
	}

	public Integer getOaOrderNumId() {
		return oaOrderNumId;
	}

	public void setOaOrderNumId(Integer oaOrderNumId) {
		this.oaOrderNumId = oaOrderNumId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPictureBack() {
		return pictureBack;
	}

	public void setPictureBack(String pictureBack) {
		this.pictureBack = pictureBack;
	}

	public String getPictureFront() {
		return pictureFront;
	}

	public void setPictureFront(String pictureFront) {
		this.pictureFront = pictureFront;
	}

	public Float getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Float priceMax) {
		this.priceMax = priceMax;
	}

	public Float getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Float priceMin) {
		this.priceMin = priceMin;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getSellOrderCode() {
		return sellOrderCode;
	}

	public void setSellOrderCode(String sellOrderCode) {
		this.sellOrderCode = sellOrderCode;
	}

	public Integer getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(Integer sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	public String getStyleCraft() {
		return styleCraft;
	}

	public void setStyleCraft(String styleCraft) {
		this.styleCraft = styleCraft;
	}

	public String getStyleDesc() {
		return styleDesc;
	}

	public void setStyleDesc(String styleDesc) {
		this.styleDesc = styleDesc;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public String getStyleType() {
		return styleType;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	public String getStyleUrl() {
		return styleUrl;
	}

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Float getTimeRate() {
		return timeRate;
	}

	public void setTimeRate(Float timeRate) {
		this.timeRate = timeRate;
	}

	public Float getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Float contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getTpeName() {
		return tpeName;
	}

	public void setTpeName(String tpeName) {
		this.tpeName = tpeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getWantCnt() {
		return wantCnt;
	}

	public void setWantCnt(Integer wantCnt) {
		this.wantCnt = wantCnt;
	}

	public java.sql.Timestamp getWfPlanStart() {
		return wfPlanStart;
	}

	public void setWfPlanStart(java.sql.Timestamp wfPlanStart) {
		this.wfPlanStart = wfPlanStart;
	}

	public java.sql.Timestamp getWfRealStart() {
		return wfRealStart;
	}

	public void setWfRealStart(java.sql.Timestamp wfRealStart) {
		this.wfRealStart = wfRealStart;
	}

	public String getWfStep() {
		return wfStep;
	}

	public void setWfStep(String wfStep) {
		this.wfStep = wfStep;
	}

	public Long getWfStepDuration() {
		return wfStepDuration;
	}

	public void setWfStepDuration(Long wfStepDuration) {
		this.wfStepDuration = wfStepDuration;
	}

	public String getWfStepName() {
		return wfStepName;
	}

	public void setWfStepName(String wfStepName) {
		this.wfStepName = wfStepName;
	}

	public String getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getConfirmStaff() {
		return confirmStaff;
	}

	public void setConfirmStaff(String confirmStaff) {
		this.confirmStaff = confirmStaff;
	}

	public Integer getWantDhcnt() {
		return wantDhcnt;
	}

	public void setWantDhcnt(Integer wantDhcnt) {
		this.wantDhcnt = wantDhcnt;
	}

	public String getContractSpecialMemo() {
		return contractSpecialMemo;
	}

	public void setContractSpecialMemo(String contractSpecialMemo) {
		this.contractSpecialMemo = contractSpecialMemo;
	}

	public String getTerminateUser() {
		return terminateUser;
	}

	public void setTerminateUser(String terminateUser) {
		this.terminateUser = terminateUser;
	}

	public String getTerminateMemo() {
		return terminateMemo;
	}

	public void setTerminateMemo(String terminateMemo) {
		this.terminateMemo = terminateMemo;
	}

	public java.sql.Timestamp getTerminateTime() {
		return terminateTime;
	}

	public void setTerminateTime(java.sql.Timestamp terminateTime) {
		this.terminateTime = terminateTime;
	}

	public String getRepeatReason() {
		return repeatReason;
	}

	public void setRepeatReason(String repeatReason) {
		this.repeatReason = repeatReason;
	}

	public Integer getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	public String getIsPreproduct() {
		return isPreproduct;
	}

	public void setIsPreproduct(String isPreproduct) {
		this.isPreproduct = isPreproduct;
	}

	public Timestamp getPreVersionDate() {
		return preVersionDate;
	}

	public void setPreVersionDate(Timestamp preVersionDate) {
		this.preVersionDate = preVersionDate;
	}

	public Integer getPreproductDays() {
		return PreproductDays;
	}

	public void setPreproductDays(Integer preproductDays) {
		PreproductDays = preproductDays;
	}

	public Float getSellReadyTime() {
		return sellReadyTime;
	}

	public void setSellReadyTime(Float sellReadyTime) {
		this.sellReadyTime = sellReadyTime;
	}

	public Float getStandardTime() {
		return standardTime;
	}

	public void setStandardTime(Float standardTime) {
		this.standardTime = standardTime;
	}

	public Float getCraftTime() {
		return craftTime;
	}

	public void setCraftTime(Float craftTime) {
		this.craftTime = craftTime;
	}

	public Timestamp getFeedingTime() {
		return feedingTime;
	}

	public void setFeedingTime(Timestamp feedingTime) {
		this.feedingTime = feedingTime;
	}

	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof OaOrder)) {
			return false;
		}
		OaOrder other = (OaOrder) object;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	public String toString() {
		return "OaOrder[id=" + id + "]";
	}
}