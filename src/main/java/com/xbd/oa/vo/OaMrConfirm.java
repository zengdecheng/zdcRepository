package com.xbd.oa.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_mr_confirm")
public class OaMrConfirm implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "if_repeat")
	private String ifRepeat; // 是否需要复版；0是需要，1是不需要

	@Column(name = "if_qualified")
	private Integer ifQualified; // 是否合格；0是合格，1是不合格

	@Column(name = "unqualified_reason")
	private String unqualifiedReason;

	@Column(name = "oa_order")
	private int oaOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIfRepeat() {
		return ifRepeat;
	}

	public void setIfRepeat(String ifRepeat) {
		this.ifRepeat = ifRepeat;
	}

	public Integer getIfQualified() {
		return ifQualified;
	}

	public void setIfQualified(Integer ifQualified) {
		this.ifQualified = ifQualified;
	}

	public String getUnqualifiedReason() {
		return unqualifiedReason;
	}

	public void setUnqualifiedReason(String unqualifiedReason) {
		this.unqualifiedReason = unqualifiedReason;
	}

	public int getOaOrder() {
		return oaOrder;
	}

	public void setOaOrder(int oaOrder) {
		this.oaOrder = oaOrder;
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
		if (!(object instanceof OaMrConfirm)) {
			return false;
		}
		OaMrConfirm other = (OaMrConfirm) object;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaMrConfirm[id=" + id + "]";
	}
}