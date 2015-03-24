package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.xbd.oa.vo.base.CommonBean;

/**
 * oa_客供料明细
 * @author fangwei
 * @version 创建时间：2015年3月24日  上午11:36:32
 */
@Entity(name = "oa_cus_material_list")
public class OaCusMaterialList extends CommonBean{
	private static final long serialVersionUID = 1L;

	private Float amount;				//用料
	private Float consume;				//客供损耗
	private String isComplete;			//是否齐全
	private String materialName;		//物料名称
	private String memo;				//位置说明 备注
	private Float total;				//合计
	private Integer oaOrderId;			//订单ID
	private String orderNum;			//订单数量内容

	@Column(name = "amount",columnDefinition="float(10,2)")
	public Float getAmount() {
		return amount;
	}
	@Column(name = "consume",columnDefinition="float(10,2)")
	public Float getConsume() {
		return consume;
	}

	@Column(name = "is_complete",columnDefinition="CHAR(1)")
	public String getIsComplete() {
		return isComplete;
	}

	@Column(name = "material_name",columnDefinition="varchar(20)")
	public String getMaterialName() {
		return materialName;
	}

	@Column(name = "memo",columnDefinition="varchar(50)")
	public String getMemo() {
		return memo;
	}

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "order_num",columnDefinition="varchar(1000)")
	public String getOrderNum() {
		return orderNum;
	}

	@Column(name = "total",columnDefinition="float(10,2)")
	public Float getTotal() {
		return total;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public void setConsume(Float consume) {
		this.consume = consume;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((consume == null) ? 0 : consume.hashCode());
		result = prime * result + ((isComplete == null) ? 0 : isComplete.hashCode());
		result = prime * result + ((materialName == null) ? 0 : materialName.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((orderNum == null) ? 0 : orderNum.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaCusMaterialList other = (OaCusMaterialList) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (consume == null) {
			if (other.consume != null)
				return false;
		} else if (!consume.equals(other.consume))
			return false;
		if (isComplete == null) {
			if (other.isComplete != null)
				return false;
		} else if (!isComplete.equals(other.isComplete))
			return false;
		if (materialName == null) {
			if (other.materialName != null)
				return false;
		} else if (!materialName.equals(other.materialName))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (orderNum == null) {
			if (other.orderNum != null)
				return false;
		} else if (!orderNum.equals(other.orderNum))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
	
	public String toString() {
		return "OaCusMaterialList [amount=" + amount + ", consume=" + consume + ", isComplete=" + isComplete + ", materialName=" + materialName + ", memo=" + memo + ", total=" + total + ", oaOrderId=" + oaOrderId + ", orderNum=" + orderNum + ", getId()=" + getId() + "]";
	}
}