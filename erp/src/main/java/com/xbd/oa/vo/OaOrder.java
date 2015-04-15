package com.xbd.oa.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 订单表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月24日 下午5:34:23
 */
@Entity
@Table(name = "oa_order")
public class OaOrder extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Timestamp beginTime; // 开始时间
	private String city; // 所属区域
	private String clothClass; // 二级品类
	private String cusCode; // 客户编码
	private String sampleSize; // 样版码数
	private Integer cusId; // 客户ID
	private String cusName; // 客户名称
	private Timestamp endTime; // 结束时间
	private Timestamp exceptFinish; // 预计交期
	private String fileUrl; // 文件地址
	private String hisOpt; // 历史参数
	private String isUrgent; // 是否加急
	private String sellMemo; // 销售备注
	private String contractSpecialMemo; // 合同特殊说明
	private String memo; // MR备注
	private String mrName; // mr跟单
	private Integer oaOrderDetail; // 详情ID
	private Integer oaOrderNumId; // 数量信息ID
	private String operator; // 操作员
	private String orderCode; // 合同号
	private String payType; // 支付类型
	private String picUrl; // 图片地址
	private String pictureBack; // 款式背图
	private String pictureFront; // 款式正图
	private Float priceMax; // 期望最高价
	private Float priceMin; // 期望最低价
	private String sales; // 销售
	private String sellOrderCode; // 订单号
	private Integer sellOrderId; // 订单ID
	private String sendtype; // 送货方式
	private String status; // 状态
	private String styleClass; // 一级品类
	private String styleCode; // 款式号
	private String styleCraft; // 款式工艺
	private String styleDesc; // 款式描述
	private Integer styleId; // 款式ID
	private String styleType; // 款式类型
	private String styleUrl; // 样图地址
	private String tel; // 客户手机
	private Float timeRate; // 加急率
	private Float contractAmount; // 订单总价
	private String confirmStaff; // 确认客户
	private String tpeName; // TPE
	private String type; // 订单类型
	private Integer wantCnt; // 订单预计数量
	private Timestamp wfPlanStart; // 计划开始时间
	private Timestamp wfRealStart; // 真实开始时间
	private String wfStep; // 流程节点key
	private Long wfStepDuration; // 流程持续时间
	private String wfStepName; // 流程节点名称
	private Integer wantDhcnt; // 大货预计数量
	private String relatedOrderCode; // 关联订单编号
	private String relatedOrderType; // 关联订单类型
	private Integer relatedOrderId; // 关联订单ID
	private String terminateUser; // 终止操作人
	private String terminateMemo; // 终止原因
	private Timestamp terminateTime; // 终止时间
	private String repeatReason; // 重复打版原因
	private Integer repeatNum; // 重复打版次数
	private String isPreproduct; // 是否需要产前版
	private Timestamp preVersionDate; // 产前版时间
	private Integer PreproductDays; // 产前版完成天数
	private Long sellReadyTime; // 销售准备时间
	private Long standardTime; // 标准工艺时间
	private Long craftTime; // 特殊工艺时间
	private Timestamp feedingTime; // 建议投料日期
	private Timestamp goodsTime; // 货期
	private String isSpecialFabric; // 是否特殊面料采购
	private String isOverOrder; // 是否翻单

	@Column(name = "begin_time",columnDefinition="timestamp null default null")
	public Timestamp getBeginTime() {
		return beginTime;
	}

	@Column(name = "city", columnDefinition="varchar(200)")
	public String getCity() {
		return city;
	}

	@Column(name = "cloth_class",columnDefinition="varchar(20)")
	public String getClothClass() {
		return clothClass;
	}

	@Column(name = "confirm_staff",columnDefinition="varchar(20)")
	public String getConfirmStaff() {
		return confirmStaff;
	}

	@Column(name = "contract_amount",columnDefinition="float(10,2)")
	public Float getContractAmount() {
		return contractAmount;
	}

	@Column(name = "contract_special_memo",columnDefinition="varchar(255)")
	public String getContractSpecialMemo() {
		return contractSpecialMemo;
	}

	@Column(name = "craft_time",columnDefinition="bigint")
	public Long getCraftTime() {
		return craftTime;
	}

	@Column(name = "cus_code",columnDefinition="varchar(20)")
	public String getCusCode() {
		return cusCode;
	}

	@Column(name = "cus_id")
	public Integer getCusId() {
		return cusId;
	}

	@Column(name = "cus_name",columnDefinition="varchar(20)")
	public String getCusName() {
		return cusName;
	}
	@Column(name = "end_time",columnDefinition="timestamp null default null")
	public Timestamp getEndTime() {
		return endTime;
	}
	@Column(name = "except_finish",columnDefinition="timestamp null default null")
	public Timestamp getExceptFinish() {
		return exceptFinish;
	}
	@Column(name = "feeding_time",columnDefinition="timestamp null default null")
	public Timestamp getFeedingTime() {
		return feedingTime;
	}

	@Column(name = "file_url",columnDefinition="varchar(200)")
	public String getFileUrl() {
		return fileUrl;
	}
	@Column(name = "goods_time",columnDefinition="timestamp null default null")
	public Timestamp getGoodsTime() {
		return goodsTime;
	}

	@Column(name = "his_opt",columnDefinition="varchar(500)")
	public String getHisOpt() {
		return hisOpt;
	}

	@Column(name = "is_preproduct", columnDefinition = "CHAR(1)")
	public String getIsPreproduct() {
		return isPreproduct;
	}

	@Column(name = "is_urgent", columnDefinition = "CHAR(1)")
	public String getIsUrgent() {
		return isUrgent;
	}

	@Column(name = "memo",columnDefinition="varchar(1000)")
	public String getMemo() {
		return memo;
	}

	@Column(name = "mr_name",columnDefinition="varchar(20)")
	public String getMrName() {
		return mrName;
	}

	@Column(name = "oa_order_detail")
	public Integer getOaOrderDetail() {
		return oaOrderDetail;
	}

	@Column(name = "oa_order_num_id")
	public Integer getOaOrderNumId() {
		return oaOrderNumId;
	}

	@Column(name = "operator",columnDefinition="varchar(20)")
	public String getOperator() {
		return operator;
	}

	@Column(name = "order_code",columnDefinition="varchar(50)")
	public String getOrderCode() {
		return orderCode;
	}

	@Column(name = "pay_type",columnDefinition="varchar(50)")
	public String getPayType() {
		return payType;
	}

	@Column(name = "picture_back",columnDefinition="varchar(200)")
	public String getPictureBack() {
		return pictureBack;
	}

	@Column(name = "picture_front",columnDefinition="varchar(200)")
	public String getPictureFront() {
		return pictureFront;
	}

	@Column(name = "pic_url",columnDefinition="varchar(200)")
	public String getPicUrl() {
		return picUrl;
	}

	@Column(name = "preproduct_days")
	public Integer getPreproductDays() {
		return PreproductDays;
	}
	@Column(name = "pre_version_date",columnDefinition="timestamp null default null")
	public Timestamp getPreVersionDate() {
		return preVersionDate;
	}

	@Column(name = "price_max",columnDefinition="float(10,2)")
	public Float getPriceMax() {
		return priceMax;
	}

	@Column(name = "price_min",columnDefinition="float(10,2)")
	public Float getPriceMin() {
		return priceMin;
	}

	@Column(name = "related_order_code",columnDefinition="varchar(50)")
	public String getRelatedOrderCode() {
		return relatedOrderCode;
	}

	@Column(name = "related_order_id")
	public Integer getRelatedOrderId() {
		return relatedOrderId;
	}

	@Column(name = "related_order_type", columnDefinition = "CHAR(1)")
	public String getRelatedOrderType() {
		return relatedOrderType;
	}

	@Column(name = "repeat_num", columnDefinition = "CHAR(1)")
	public Integer getRepeatNum() {
		return repeatNum;
	}

	@Column(name = "repeat_reason",columnDefinition="varchar(255)")
	public String getRepeatReason() {
		return repeatReason;
	}

	@Column(name = "sales",columnDefinition="varchar(20)")
	public String getSales() {
		return sales;
	}

	@Column(name = "sample_size",columnDefinition="varchar(20)")
	public String getSampleSize() {
		return sampleSize;
	}

	@Column(name = "sell_memo",columnDefinition="varchar(500)")
	public String getSellMemo() {
		return sellMemo;
	}

	@Column(name = "sell_order_code",columnDefinition="varchar(50)")
	public String getSellOrderCode() {
		return sellOrderCode;
	}

	@Column(name = "sell_order_id")
	public Integer getSellOrderId() {
		return sellOrderId;
	}

	@Column(name = "sell_ready_time",columnDefinition="bigint")
	public Long getSellReadyTime() {
		return sellReadyTime;
	}

	@Column(name = "sendType",columnDefinition="varchar(50)")
	public String getSendtype() {
		return sendtype;
	}

	@Column(name = "standard_time",columnDefinition="bigint")
	public Long getStandardTime() {
		return standardTime;
	}

	@Column(name = "status", columnDefinition = "CHAR(1)")
	public String getStatus() {
		return status;
	}

	@Column(name = "style_class",columnDefinition="varchar(20)")
	public String getStyleClass() {
		return styleClass;
	}

	@Column(name = "style_code",columnDefinition="varchar(20)")
	public String getStyleCode() {
		return styleCode;
	}

	@Column(name = "style_craft",columnDefinition="varchar(500)")
	public String getStyleCraft() {
		return styleCraft;
	}

	@Column(name = "style_desc",columnDefinition="varchar(200)")
	public String getStyleDesc() {
		return styleDesc;
	}

	@Column(name = "style_id")
	public Integer getStyleId() {
		return styleId;
	}

	@Column(name = "style_type",columnDefinition="varchar(20)")
	public String getStyleType() {
		return styleType;
	}

	@Column(name = "style_url",columnDefinition="varchar(200)")
	public String getStyleUrl() {
		return styleUrl;
	}

	@Column(name = "tel",columnDefinition="varchar(20)")
	public String getTel() {
		return tel;
	}

	@Column(name = "terminate_memo",columnDefinition="varchar(500)")
	public String getTerminateMemo() {
		return terminateMemo;
	}
	@Column(name = "terminate_time",columnDefinition="timestamp null default null")
	public Timestamp getTerminateTime() {
		return terminateTime;
	}

	@Column(name = "terminate_user",columnDefinition="varchar(20)")
	public String getTerminateUser() {
		return terminateUser;
	}

	@Column(name = "time_rate",columnDefinition="float(10,2)")
	public Float getTimeRate() {
		return timeRate;
	}

	@Column(name = "tpe_name",columnDefinition="varchar(20)")
	public String getTpeName() {
		return tpeName;
	}

	@Column(name = "type", columnDefinition = "CHAR(1)")
	public String getType() {
		return type;
	}

	@Column(name = "want_cnt")
	public Integer getWantCnt() {
		return wantCnt;
	}

	@Column(name = "want_dh_cnt")
	public Integer getWantDhcnt() {
		return wantDhcnt;
	}
	
	@Column(name = "wf_plan_start",columnDefinition="timestamp null default null")
	public Timestamp getWfPlanStart() {
		return wfPlanStart;
	}
	
	@Column(name = "wf_real_start",columnDefinition="timestamp null default null")
	public Timestamp getWfRealStart() {
		return wfRealStart;
	}

	@Column(name = "wf_step",columnDefinition="varchar(50)")
	public String getWfStep() {
		return wfStep;
	}

	@Column(name = "wf_step_duration",columnDefinition="bigint")
	public Long getWfStepDuration() {
		return wfStepDuration;
	}

	@Column(name = "wf_step_name",columnDefinition="varchar(50)")
	public String getWfStepName() {
		return wfStepName;
	}

	@Column(name = "is_special_fabric",columnDefinition="char(1)")
	public String getIsSpecialFabric() {
		return isSpecialFabric;
	}
	
	@Column(name = "is_over_order",columnDefinition="char(1)")
	public String getIsOverOrder() {
		return isOverOrder;
	}

	public void setIsSpecialFabric(String isSpecialFabric) {
		this.isSpecialFabric = isSpecialFabric;
	}

	public void setIsOverOrder(String isOverOrder) {
		this.isOverOrder = isOverOrder;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setClothClass(String clothClass) {
		this.clothClass = clothClass;
	}

	public void setConfirmStaff(String confirmStaff) {
		this.confirmStaff = confirmStaff;
	}

	public void setContractAmount(Float contractAmount) {
		this.contractAmount = contractAmount;
	}

	public void setContractSpecialMemo(String contractSpecialMemo) {
		this.contractSpecialMemo = contractSpecialMemo;
	}

	public void setCraftTime(Long craftTime) {
		this.craftTime = craftTime;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setExceptFinish(Timestamp exceptFinish) {
		this.exceptFinish = exceptFinish;
	}

	public void setFeedingTime(Timestamp feedingTime) {
		this.feedingTime = feedingTime;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setGoodsTime(Timestamp goodsTime) {
		this.goodsTime = goodsTime;
	}

	public void setHisOpt(String hisOpt) {
		this.hisOpt = hisOpt;
	}

	public void setIsPreproduct(String isPreproduct) {
		this.isPreproduct = isPreproduct;
	}

	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setMrName(String mrName) {
		this.mrName = mrName;
	}

	public void setOaOrderDetail(Integer oaOrderDetail) {
		this.oaOrderDetail = oaOrderDetail;
	}

	public void setOaOrderNumId(Integer oaOrderNumId) {
		this.oaOrderNumId = oaOrderNumId;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public void setPictureBack(String pictureBack) {
		this.pictureBack = pictureBack;
	}

	public void setPictureFront(String pictureFront) {
		this.pictureFront = pictureFront;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setPreproductDays(Integer preproductDays) {
		PreproductDays = preproductDays;
	}

	public void setPreVersionDate(Timestamp preVersionDate) {
		this.preVersionDate = preVersionDate;
	}

	public void setPriceMax(Float priceMax) {
		this.priceMax = priceMax;
	}

	public void setPriceMin(Float priceMin) {
		this.priceMin = priceMin;
	}

	public void setRelatedOrderCode(String relatedOrderCode) {
		this.relatedOrderCode = relatedOrderCode;
	}

	public void setRelatedOrderId(Integer relatedOrderId) {
		this.relatedOrderId = relatedOrderId;
	}

	public void setRelatedOrderType(String relatedOrderType) {
		this.relatedOrderType = relatedOrderType;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	public void setRepeatReason(String repeatReason) {
		this.repeatReason = repeatReason;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}

	public void setSellMemo(String sellMemo) {
		this.sellMemo = sellMemo;
	}

	public void setSellOrderCode(String sellOrderCode) {
		this.sellOrderCode = sellOrderCode;
	}

	public void setSellOrderId(Integer sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public void setSellReadyTime(Long sellReadyTime) {
		this.sellReadyTime = sellReadyTime;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public void setStandardTime(Long standardTime) {
		this.standardTime = standardTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setStyleCode(String styleCode) {
		this.styleCode = styleCode;
	}

	public void setStyleCraft(String styleCraft) {
		this.styleCraft = styleCraft;
	}

	public void setStyleDesc(String styleDesc) {
		this.styleDesc = styleDesc;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	public void setStyleUrl(String styleUrl) {
		this.styleUrl = styleUrl;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setTerminateMemo(String terminateMemo) {
		this.terminateMemo = terminateMemo;
	}

	public void setTerminateTime(Timestamp terminateTime) {
		this.terminateTime = terminateTime;
	}

	public void setTerminateUser(String terminateUser) {
		this.terminateUser = terminateUser;
	}

	public void setTimeRate(Float timeRate) {
		this.timeRate = timeRate;
	}

	public void setTpeName(String tpeName) {
		this.tpeName = tpeName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWantCnt(Integer wantCnt) {
		this.wantCnt = wantCnt;
	}

	public void setWantDhcnt(Integer wantDhcnt) {
		this.wantDhcnt = wantDhcnt;
	}

	public void setWfPlanStart(Timestamp wfPlanStart) {
		this.wfPlanStart = wfPlanStart;
	}

	public void setWfRealStart(Timestamp wfRealStart) {
		this.wfRealStart = wfRealStart;
	}

	public void setWfStep(String wfStep) {
		this.wfStep = wfStep;
	}

	public void setWfStepDuration(Long wfStepDuration) {
		this.wfStepDuration = wfStepDuration;
	}

	public void setWfStepName(String wfStepName) {
		this.wfStepName = wfStepName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((PreproductDays == null) ? 0 : PreproductDays.hashCode());
		result = prime * result + ((beginTime == null) ? 0 : beginTime.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((clothClass == null) ? 0 : clothClass.hashCode());
		result = prime * result + ((confirmStaff == null) ? 0 : confirmStaff.hashCode());
		result = prime * result + ((contractAmount == null) ? 0 : contractAmount.hashCode());
		result = prime * result + ((contractSpecialMemo == null) ? 0 : contractSpecialMemo.hashCode());
		result = prime * result + ((craftTime == null) ? 0 : craftTime.hashCode());
		result = prime * result + ((cusCode == null) ? 0 : cusCode.hashCode());
		result = prime * result + ((cusId == null) ? 0 : cusId.hashCode());
		result = prime * result + ((cusName == null) ? 0 : cusName.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((exceptFinish == null) ? 0 : exceptFinish.hashCode());
		result = prime * result + ((feedingTime == null) ? 0 : feedingTime.hashCode());
		result = prime * result + ((fileUrl == null) ? 0 : fileUrl.hashCode());
		result = prime * result + ((goodsTime == null) ? 0 : goodsTime.hashCode());
		result = prime * result + ((hisOpt == null) ? 0 : hisOpt.hashCode());
		result = prime * result + ((isPreproduct == null) ? 0 : isPreproduct.hashCode());
		result = prime * result + ((isUrgent == null) ? 0 : isUrgent.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((mrName == null) ? 0 : mrName.hashCode());
		result = prime * result + ((oaOrderDetail == null) ? 0 : oaOrderDetail.hashCode());
		result = prime * result + ((oaOrderNumId == null) ? 0 : oaOrderNumId.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((orderCode == null) ? 0 : orderCode.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result + ((picUrl == null) ? 0 : picUrl.hashCode());
		result = prime * result + ((pictureBack == null) ? 0 : pictureBack.hashCode());
		result = prime * result + ((pictureFront == null) ? 0 : pictureFront.hashCode());
		result = prime * result + ((preVersionDate == null) ? 0 : preVersionDate.hashCode());
		result = prime * result + ((priceMax == null) ? 0 : priceMax.hashCode());
		result = prime * result + ((priceMin == null) ? 0 : priceMin.hashCode());
		result = prime * result + ((relatedOrderCode == null) ? 0 : relatedOrderCode.hashCode());
		result = prime * result + ((relatedOrderId == null) ? 0 : relatedOrderId.hashCode());
		result = prime * result + ((relatedOrderType == null) ? 0 : relatedOrderType.hashCode());
		result = prime * result + ((repeatNum == null) ? 0 : repeatNum.hashCode());
		result = prime * result + ((repeatReason == null) ? 0 : repeatReason.hashCode());
		result = prime * result + ((sales == null) ? 0 : sales.hashCode());
		result = prime * result + ((sampleSize == null) ? 0 : sampleSize.hashCode());
		result = prime * result + ((sellMemo == null) ? 0 : sellMemo.hashCode());
		result = prime * result + ((sellOrderCode == null) ? 0 : sellOrderCode.hashCode());
		result = prime * result + ((sellOrderId == null) ? 0 : sellOrderId.hashCode());
		result = prime * result + ((sellReadyTime == null) ? 0 : sellReadyTime.hashCode());
		result = prime * result + ((sendtype == null) ? 0 : sendtype.hashCode());
		result = prime * result + ((standardTime == null) ? 0 : standardTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((styleClass == null) ? 0 : styleClass.hashCode());
		result = prime * result + ((styleCode == null) ? 0 : styleCode.hashCode());
		result = prime * result + ((styleCraft == null) ? 0 : styleCraft.hashCode());
		result = prime * result + ((styleDesc == null) ? 0 : styleDesc.hashCode());
		result = prime * result + ((styleId == null) ? 0 : styleId.hashCode());
		result = prime * result + ((styleType == null) ? 0 : styleType.hashCode());
		result = prime * result + ((styleUrl == null) ? 0 : styleUrl.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((terminateMemo == null) ? 0 : terminateMemo.hashCode());
		result = prime * result + ((terminateTime == null) ? 0 : terminateTime.hashCode());
		result = prime * result + ((terminateUser == null) ? 0 : terminateUser.hashCode());
		result = prime * result + ((timeRate == null) ? 0 : timeRate.hashCode());
		result = prime * result + ((tpeName == null) ? 0 : tpeName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((wantCnt == null) ? 0 : wantCnt.hashCode());
		result = prime * result + ((wantDhcnt == null) ? 0 : wantDhcnt.hashCode());
		result = prime * result + ((wfPlanStart == null) ? 0 : wfPlanStart.hashCode());
		result = prime * result + ((wfRealStart == null) ? 0 : wfRealStart.hashCode());
		result = prime * result + ((wfStep == null) ? 0 : wfStep.hashCode());
		result = prime * result + ((wfStepDuration == null) ? 0 : wfStepDuration.hashCode());
		result = prime * result + ((wfStepName == null) ? 0 : wfStepName.hashCode());
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
		OaOrder other = (OaOrder) obj;
		if (PreproductDays == null) {
			if (other.PreproductDays != null)
				return false;
		} else if (!PreproductDays.equals(other.PreproductDays))
			return false;
		if (beginTime == null) {
			if (other.beginTime != null)
				return false;
		} else if (!beginTime.equals(other.beginTime))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (clothClass == null) {
			if (other.clothClass != null)
				return false;
		} else if (!clothClass.equals(other.clothClass))
			return false;
		if (confirmStaff == null) {
			if (other.confirmStaff != null)
				return false;
		} else if (!confirmStaff.equals(other.confirmStaff))
			return false;
		if (contractAmount == null) {
			if (other.contractAmount != null)
				return false;
		} else if (!contractAmount.equals(other.contractAmount))
			return false;
		if (contractSpecialMemo == null) {
			if (other.contractSpecialMemo != null)
				return false;
		} else if (!contractSpecialMemo.equals(other.contractSpecialMemo))
			return false;
		if (craftTime == null) {
			if (other.craftTime != null)
				return false;
		} else if (!craftTime.equals(other.craftTime))
			return false;
		if (cusCode == null) {
			if (other.cusCode != null)
				return false;
		} else if (!cusCode.equals(other.cusCode))
			return false;
		if (cusId == null) {
			if (other.cusId != null)
				return false;
		} else if (!cusId.equals(other.cusId))
			return false;
		if (cusName == null) {
			if (other.cusName != null)
				return false;
		} else if (!cusName.equals(other.cusName))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (exceptFinish == null) {
			if (other.exceptFinish != null)
				return false;
		} else if (!exceptFinish.equals(other.exceptFinish))
			return false;
		if (feedingTime == null) {
			if (other.feedingTime != null)
				return false;
		} else if (!feedingTime.equals(other.feedingTime))
			return false;
		if (fileUrl == null) {
			if (other.fileUrl != null)
				return false;
		} else if (!fileUrl.equals(other.fileUrl))
			return false;
		if (goodsTime == null) {
			if (other.goodsTime != null)
				return false;
		} else if (!goodsTime.equals(other.goodsTime))
			return false;
		if (hisOpt == null) {
			if (other.hisOpt != null)
				return false;
		} else if (!hisOpt.equals(other.hisOpt))
			return false;
		if (isPreproduct == null) {
			if (other.isPreproduct != null)
				return false;
		} else if (!isPreproduct.equals(other.isPreproduct))
			return false;
		if (isUrgent == null) {
			if (other.isUrgent != null)
				return false;
		} else if (!isUrgent.equals(other.isUrgent))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (mrName == null) {
			if (other.mrName != null)
				return false;
		} else if (!mrName.equals(other.mrName))
			return false;
		if (oaOrderDetail == null) {
			if (other.oaOrderDetail != null)
				return false;
		} else if (!oaOrderDetail.equals(other.oaOrderDetail))
			return false;
		if (oaOrderNumId == null) {
			if (other.oaOrderNumId != null)
				return false;
		} else if (!oaOrderNumId.equals(other.oaOrderNumId))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (orderCode == null) {
			if (other.orderCode != null)
				return false;
		} else if (!orderCode.equals(other.orderCode))
			return false;
		if (payType == null) {
			if (other.payType != null)
				return false;
		} else if (!payType.equals(other.payType))
			return false;
		if (picUrl == null) {
			if (other.picUrl != null)
				return false;
		} else if (!picUrl.equals(other.picUrl))
			return false;
		if (pictureBack == null) {
			if (other.pictureBack != null)
				return false;
		} else if (!pictureBack.equals(other.pictureBack))
			return false;
		if (pictureFront == null) {
			if (other.pictureFront != null)
				return false;
		} else if (!pictureFront.equals(other.pictureFront))
			return false;
		if (preVersionDate == null) {
			if (other.preVersionDate != null)
				return false;
		} else if (!preVersionDate.equals(other.preVersionDate))
			return false;
		if (priceMax == null) {
			if (other.priceMax != null)
				return false;
		} else if (!priceMax.equals(other.priceMax))
			return false;
		if (priceMin == null) {
			if (other.priceMin != null)
				return false;
		} else if (!priceMin.equals(other.priceMin))
			return false;
		if (relatedOrderCode == null) {
			if (other.relatedOrderCode != null)
				return false;
		} else if (!relatedOrderCode.equals(other.relatedOrderCode))
			return false;
		if (relatedOrderId == null) {
			if (other.relatedOrderId != null)
				return false;
		} else if (!relatedOrderId.equals(other.relatedOrderId))
			return false;
		if (relatedOrderType == null) {
			if (other.relatedOrderType != null)
				return false;
		} else if (!relatedOrderType.equals(other.relatedOrderType))
			return false;
		if (repeatNum == null) {
			if (other.repeatNum != null)
				return false;
		} else if (!repeatNum.equals(other.repeatNum))
			return false;
		if (repeatReason == null) {
			if (other.repeatReason != null)
				return false;
		} else if (!repeatReason.equals(other.repeatReason))
			return false;
		if (sales == null) {
			if (other.sales != null)
				return false;
		} else if (!sales.equals(other.sales))
			return false;
		if (sampleSize == null) {
			if (other.sampleSize != null)
				return false;
		} else if (!sampleSize.equals(other.sampleSize))
			return false;
		if (sellMemo == null) {
			if (other.sellMemo != null)
				return false;
		} else if (!sellMemo.equals(other.sellMemo))
			return false;
		if (sellOrderCode == null) {
			if (other.sellOrderCode != null)
				return false;
		} else if (!sellOrderCode.equals(other.sellOrderCode))
			return false;
		if (sellOrderId == null) {
			if (other.sellOrderId != null)
				return false;
		} else if (!sellOrderId.equals(other.sellOrderId))
			return false;
		if (sellReadyTime == null) {
			if (other.sellReadyTime != null)
				return false;
		} else if (!sellReadyTime.equals(other.sellReadyTime))
			return false;
		if (sendtype == null) {
			if (other.sendtype != null)
				return false;
		} else if (!sendtype.equals(other.sendtype))
			return false;
		if (standardTime == null) {
			if (other.standardTime != null)
				return false;
		} else if (!standardTime.equals(other.standardTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (styleClass == null) {
			if (other.styleClass != null)
				return false;
		} else if (!styleClass.equals(other.styleClass))
			return false;
		if (styleCode == null) {
			if (other.styleCode != null)
				return false;
		} else if (!styleCode.equals(other.styleCode))
			return false;
		if (styleCraft == null) {
			if (other.styleCraft != null)
				return false;
		} else if (!styleCraft.equals(other.styleCraft))
			return false;
		if (styleDesc == null) {
			if (other.styleDesc != null)
				return false;
		} else if (!styleDesc.equals(other.styleDesc))
			return false;
		if (styleId == null) {
			if (other.styleId != null)
				return false;
		} else if (!styleId.equals(other.styleId))
			return false;
		if (styleType == null) {
			if (other.styleType != null)
				return false;
		} else if (!styleType.equals(other.styleType))
			return false;
		if (styleUrl == null) {
			if (other.styleUrl != null)
				return false;
		} else if (!styleUrl.equals(other.styleUrl))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (terminateMemo == null) {
			if (other.terminateMemo != null)
				return false;
		} else if (!terminateMemo.equals(other.terminateMemo))
			return false;
		if (terminateTime == null) {
			if (other.terminateTime != null)
				return false;
		} else if (!terminateTime.equals(other.terminateTime))
			return false;
		if (terminateUser == null) {
			if (other.terminateUser != null)
				return false;
		} else if (!terminateUser.equals(other.terminateUser))
			return false;
		if (timeRate == null) {
			if (other.timeRate != null)
				return false;
		} else if (!timeRate.equals(other.timeRate))
			return false;
		if (tpeName == null) {
			if (other.tpeName != null)
				return false;
		} else if (!tpeName.equals(other.tpeName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (wantCnt == null) {
			if (other.wantCnt != null)
				return false;
		} else if (!wantCnt.equals(other.wantCnt))
			return false;
		if (wantDhcnt == null) {
			if (other.wantDhcnt != null)
				return false;
		} else if (!wantDhcnt.equals(other.wantDhcnt))
			return false;
		if (wfPlanStart == null) {
			if (other.wfPlanStart != null)
				return false;
		} else if (!wfPlanStart.equals(other.wfPlanStart))
			return false;
		if (wfRealStart == null) {
			if (other.wfRealStart != null)
				return false;
		} else if (!wfRealStart.equals(other.wfRealStart))
			return false;
		if (wfStep == null) {
			if (other.wfStep != null)
				return false;
		} else if (!wfStep.equals(other.wfStep))
			return false;
		if (wfStepDuration == null) {
			if (other.wfStepDuration != null)
				return false;
		} else if (!wfStepDuration.equals(other.wfStepDuration))
			return false;
		if (wfStepName == null) {
			if (other.wfStepName != null)
				return false;
		} else if (!wfStepName.equals(other.wfStepName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaOrder [beginTime=" + beginTime + ", city=" + city + ", clothClass=" + clothClass + ", cusCode=" + cusCode + ", sampleSize=" + sampleSize + ", cusId=" + cusId + ", cusName=" + cusName + ", endTime=" + endTime + ", exceptFinish=" + exceptFinish + ", fileUrl=" + fileUrl + ", hisOpt=" + hisOpt + ", isUrgent=" + isUrgent + ", sellMemo=" + sellMemo + ", contractSpecialMemo=" + contractSpecialMemo + ", memo=" + memo + ", mrName=" + mrName + ", oaOrderDetail=" + oaOrderDetail + ", oaOrderNumId=" + oaOrderNumId + ", operator=" + operator + ", orderCode=" + orderCode + ", payType=" + payType + ", picUrl=" + picUrl + ", pictureBack=" + pictureBack + ", pictureFront=" + pictureFront + ", priceMax=" + priceMax + ", priceMin=" + priceMin + ", sales=" + sales + ", sellOrderCode=" + sellOrderCode + ", sellOrderId=" + sellOrderId + ", sendtype=" + sendtype + ", status=" + status + ", styleClass=" + styleClass + ", styleCode=" + styleCode + ", styleCraft=" + styleCraft
				+ ", styleDesc=" + styleDesc + ", styleId=" + styleId + ", styleType=" + styleType + ", styleUrl=" + styleUrl + ", tel=" + tel + ", timeRate=" + timeRate + ", contractAmount=" + contractAmount + ", confirmStaff=" + confirmStaff + ", tpeName=" + tpeName + ", type=" + type + ", wantCnt=" + wantCnt + ", wfPlanStart=" + wfPlanStart + ", wfRealStart=" + wfRealStart + ", wfStep=" + wfStep + ", wfStepDuration=" + wfStepDuration + ", wfStepName=" + wfStepName + ", wantDhcnt=" + wantDhcnt + ", relatedOrderCode=" + relatedOrderCode + ", relatedOrderType=" + relatedOrderType + ", relatedOrderId=" + relatedOrderId + ", terminateUser=" + terminateUser + ", terminateMemo=" + terminateMemo + ", terminateTime=" + terminateTime + ", repeatReason=" + repeatReason + ", repeatNum=" + repeatNum + ", isPreproduct=" + isPreproduct + ", preVersionDate=" + preVersionDate + ", PreproductDays=" + PreproductDays + ", sellReadyTime=" + sellReadyTime + ", standardTime=" + standardTime
				+ ", craftTime=" + craftTime + ", feedingTime=" + feedingTime + ", goodsTime=" + goodsTime + ", getId()=" + getId() + "]";
	}

}