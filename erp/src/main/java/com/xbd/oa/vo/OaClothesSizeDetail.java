package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.xbd.oa.vo.base.CommonBean;
/**
 * 尺寸表详情
 * @author fangwei
 * @version 创建时间：2015年3月23日  下午5:14:29
 */
@Entity(name = "oa_clothes_size_detail")
public class OaClothesSizeDetail extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Integer oaOrder;			//订单ID
	private String clothSize;			//款式尺寸
	private String position;			//位置
	private String samplePageSize;		//基码纸样尺寸
	private String tolerance;			//公差
	
	@Column(name = "oa_order")
	public Integer getOaOrder() {
		return oaOrder;
	}

	@Column(name = "cloth_size",length = 200)
	public String getClothSize() {
		return clothSize;
	}

	@Column(name = "position",length = 50)
	public String getPosition() {
		return position;
	}

	@Column(name = "sample_page_size", length = 20)
	public String getSamplePageSize() {
		return samplePageSize;
	}

	@Column(name = "tolerance" , length = 20)
	public String getTolerance() {
		return tolerance;
	}

	public void setClothSize(String clothSize) {
		this.clothSize = clothSize;
	}

	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSamplePageSize(String samplePageSize) {
		this.samplePageSize = samplePageSize;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothSize == null) ? 0 : clothSize.hashCode());
		result = prime * result + ((oaOrder == null) ? 0 : oaOrder.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((samplePageSize == null) ? 0 : samplePageSize.hashCode());
		result = prime * result + ((tolerance == null) ? 0 : tolerance.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaClothesSizeDetail other = (OaClothesSizeDetail) obj;
		if (clothSize == null) {
			if (other.clothSize != null)
				return false;
		} else if (!clothSize.equals(other.clothSize))
			return false;
		if (oaOrder == null) {
			if (other.oaOrder != null)
				return false;
		} else if (!oaOrder.equals(other.oaOrder))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (samplePageSize == null) {
			if (other.samplePageSize != null)
				return false;
		} else if (!samplePageSize.equals(other.samplePageSize))
			return false;
		if (tolerance == null) {
			if (other.tolerance != null)
				return false;
		} else if (!tolerance.equals(other.tolerance))
			return false;
		return true;
	}

	public String toString() {
		return "OaClothesSizeDetail [oaOrder=" + oaOrder + ", clothSize=" + clothSize + ", position=" + position + ", samplePageSize=" + samplePageSize + ", tolerance=" + tolerance + "]";
	}
}