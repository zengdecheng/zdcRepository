package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * mr确认表
 * @author fangwei
 * @version 创建时间：2015年3月24日  下午4:53:40
 */
@Entity
@Table(name = "oa_mr_confirm")
public class OaMrConfirm extends CommonBean{

	private static final long serialVersionUID = 1L;

	private String ifRepeat;			 	// 是否需要复版；0是需要，1是不需要
	private String ifQualified; 			// 是否合格；0是合格，1是不合格
	private String unqualifiedReason;		// 不合格原因
	private Integer oaOrder;				// 订单ID

	@Column(name = "if_qualified", columnDefinition = "CHAR(1)")
	public String getIfQualified() {
		return ifQualified;
	}

	@Column(name = "if_repeat", columnDefinition = "CHAR(1)")
	public String getIfRepeat() {
		return ifRepeat;
	}

	@Column(name = "oa_order")
	public Integer getOaOrder() {
		return oaOrder;
	}

	@Column(name = "unqualified_reason", columnDefinition = "varchar(500)")
	public String getUnqualifiedReason() {
		return unqualifiedReason;
	}

	public void setIfQualified(String ifQualified) {
		this.ifQualified = ifQualified;
	}

	public void setIfRepeat(String ifRepeat) {
		this.ifRepeat = ifRepeat;
	}

	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}

	public void setUnqualifiedReason(String unqualifiedReason) {
		this.unqualifiedReason = unqualifiedReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ifQualified == null) ? 0 : ifQualified.hashCode());
		result = prime * result + ((ifRepeat == null) ? 0 : ifRepeat.hashCode());
		result = prime * result + ((oaOrder == null) ? 0 : oaOrder.hashCode());
		result = prime * result + ((unqualifiedReason == null) ? 0 : unqualifiedReason.hashCode());
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
		OaMrConfirm other = (OaMrConfirm) obj;
		if (ifQualified == null) {
			if (other.ifQualified != null)
				return false;
		} else if (!ifQualified.equals(other.ifQualified))
			return false;
		if (ifRepeat == null) {
			if (other.ifRepeat != null)
				return false;
		} else if (!ifRepeat.equals(other.ifRepeat))
			return false;
		if (oaOrder == null) {
			if (other.oaOrder != null)
				return false;
		} else if (!oaOrder.equals(other.oaOrder))
			return false;
		if (unqualifiedReason == null) {
			if (other.unqualifiedReason != null)
				return false;
		} else if (!unqualifiedReason.equals(other.unqualifiedReason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaMrConfirm [ifRepeat=" + ifRepeat + ", ifQualified=" + ifQualified + ", unqualifiedReason=" + unqualifiedReason + ", oaOrder=" + oaOrder + ", getId()=" + getId() + "]";
	}

}