package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_tpe")
public class OaTpe implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_order_id")
	private Integer oaOrderId;
	
	@Column(name = "sewing_factory")
	private String sewingFactory;
	
	@Column(name = "sewing_num")
	private String sewingNum;
	
	@Column(name = "sewing_total")
	private Float sewingTotal;
	

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
	
	public String getSewingFactory() {
		return sewingFactory;
	}
	public void setSewingFactory(String sewingFactory) {
		this.sewingFactory = sewingFactory;
	}
	
	public String getSewingNum() {
		return sewingNum;
	}
	public void setSewingNum(String sewingNum) {
		this.sewingNum = sewingNum;
	}
	
	public Float getSewingTotal() {
		return sewingTotal;
	}
	public void setSewingTotal(Float sewingTotal) {
		this.sewingTotal = sewingTotal;
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
		if (!(object instanceof OaTpe)) {
			return false;
		}
		OaTpe other = (OaTpe) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaTpe[id=" + id + "]";
    }
  }