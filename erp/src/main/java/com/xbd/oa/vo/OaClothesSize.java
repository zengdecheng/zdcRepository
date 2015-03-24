package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 尺寸表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月23日 下午5:14:29
 */
@Entity
@Table(name = "oa_clothes_size")
public class OaClothesSize extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Integer oaOrderId; // 订单ID
	private String sampleSize; // 订单基码
	private String type; // 款式分类
	private String unit; // 单位

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "sample_size", length = 20)
	public String getSampleSize() {
		return sampleSize;
	}

	@Column(name = "type", length = 20)
	public String getType() {
		return type;
	}

	@Column(name = "unit", length = 20)
	public String getUnit() {
		return unit;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((sampleSize == null) ? 0 : sampleSize.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaClothesSize other = (OaClothesSize) obj;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (sampleSize == null) {
			if (other.sampleSize != null)
				return false;
		} else if (!sampleSize.equals(other.sampleSize))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	public String toString() {
		return "OaClothesSize [oaOrderId=" + oaOrderId + ", sampleSize=" + sampleSize + ", type=" + type + ", unit=" + unit + "]";
	}

}