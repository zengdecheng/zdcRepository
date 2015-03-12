package com.xbd.oa.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_qa")
public class OaQa implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "oa_order_id")
	private Integer oaOrderId;

	@Column(name = "qualified_num_info")
	private String qualifiedNumInfo;

	@Column(name = "unqualified_num_info")
	private String unqualifiedNumInfo;

	@Column(name = "unqualified_total")
	private Float unqualifiedTotal;

	@Column(name = "qualified_total")
	private Float qualifiedTotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOaOrderId() {
		return oaOrderId;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public String getQualifiedNumInfo() {
		return qualifiedNumInfo;
	}

	public void setQualifiedNumInfo(String qualifiedNumInfo) {
		this.qualifiedNumInfo = qualifiedNumInfo;
	}

	public String getUnqualifiedNumInfo() {
		return unqualifiedNumInfo;
	}

	public void setUnqualifiedNumInfo(String unqualifiedNumInfo) {
		this.unqualifiedNumInfo = unqualifiedNumInfo;
	}

	public Float getUnqualifiedTotal() {
		return unqualifiedTotal;
	}

	public void setUnqualifiedTotal(Float unqualifiedTotal) {
		this.unqualifiedTotal = unqualifiedTotal;
	}

	public Float getQualifiedTotal() {
		return qualifiedTotal;
	}

	public void setQualifiedTotal(Float qualifiedTotal) {
		this.qualifiedTotal = qualifiedTotal;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof OaQa)) {
			return false;
		}
		OaQa other = (OaQa) object;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaQa[id=" + id + "]";
	}
}