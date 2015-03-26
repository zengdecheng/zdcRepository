package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * TPE表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:31:56
 */
@Entity
@Table(name = "oa_tpe")
public class OaTpe extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Integer oaOrderId;
	private String sewingFactory;
	private String sewingNum;
	private Float sewingTotal;

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "sewing_factory",columnDefinition="varchar(100)")
	public String getSewingFactory() {
		return sewingFactory;
	}

	@Column(name = "sewing_num",columnDefinition="varchar(500)")
	public String getSewingNum() {
		return sewingNum;
	}

	@Column(name = "sewing_total",columnDefinition="float(10,2)")
	public Float getSewingTotal() {
		return sewingTotal;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setSewingFactory(String sewingFactory) {
		this.sewingFactory = sewingFactory;
	}

	public void setSewingNum(String sewingNum) {
		this.sewingNum = sewingNum;
	}

	public void setSewingTotal(Float sewingTotal) {
		this.sewingTotal = sewingTotal;
	}

	@Override
	public String toString() {
		return "OaTpe [oaOrderId=" + oaOrderId + ", sewingFactory=" + sewingFactory + ", sewingNum=" + sewingNum + ", sewingTotal=" + sewingTotal + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((sewingFactory == null) ? 0 : sewingFactory.hashCode());
		result = prime * result + ((sewingNum == null) ? 0 : sewingNum.hashCode());
		result = prime * result + ((sewingTotal == null) ? 0 : sewingTotal.hashCode());
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
		OaTpe other = (OaTpe) obj;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (sewingFactory == null) {
			if (other.sewingFactory != null)
				return false;
		} else if (!sewingFactory.equals(other.sewingFactory))
			return false;
		if (sewingNum == null) {
			if (other.sewingNum != null)
				return false;
		} else if (!sewingNum.equals(other.sewingNum))
			return false;
		if (sewingTotal == null) {
			if (other.sewingTotal != null)
				return false;
		} else if (!sewingTotal.equals(other.sewingTotal))
			return false;
		return true;
	}

}