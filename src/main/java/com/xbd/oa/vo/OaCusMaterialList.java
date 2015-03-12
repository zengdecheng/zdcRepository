package com.xbd.oa.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_cus_material_list")
public class OaCusMaterialList implements Serializable {
	@Column(name = "amount")
	private Float amount;

	@Column(name = "consume")
	private Float consume;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "is_complete")
	private String isComplete;

	@Column(name = "material_name")
	private String materialName;

	@Column(name = "memo")
	private String memo;

	@Column(name = "oa_order_num_id")
	private String oaOrderNumId;

	@Column(name = "total")
	private Float total;

	@Column(name = "oa_order_id")
	private Integer oaOrderId;

	@Column(name = "order_num")
	private String orderNum;

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getConsume() {
		return consume;
	}

	public void setConsume(Float consume) {
		this.consume = consume;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOaOrderNumId() {
		return oaOrderNumId;
	}

	public void setOaOrderNumId(String oaOrderNumId) {
		this.oaOrderNumId = oaOrderNumId;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getOaOrderId() {
		return oaOrderId;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OaCusMaterialList)) {
			return false;
		}
		OaCusMaterialList other = (OaCusMaterialList) object;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaCusMaterialList[id=" + id + "]";
	}
}