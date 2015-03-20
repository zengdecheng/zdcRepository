package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_clothes_size_detail")
public class OaClothesSizeDetail implements Serializable {
	@Column(name = "cloth_size")
	private String clothSize;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_order")
	private Integer oaOrder;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "sample_page_size")
	private String samplePageSize;
	
	@Column(name = "tolerance")
	private String tolerance;
	

	public String getClothSize() {
		return clothSize;
	}
	public void setClothSize(String clothSize) {
		this.clothSize = clothSize;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOaOrder() {
		return oaOrder;
	}
	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getSamplePageSize() {
		return samplePageSize;
	}
	public void setSamplePageSize(String samplePageSize) {
		this.samplePageSize = samplePageSize;
	}
	
	public String getTolerance() {
		return tolerance;
	}
	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
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
		if (!(object instanceof OaClothesSizeDetail)) {
			return false;
		}
		OaClothesSizeDetail other = (OaClothesSizeDetail) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaClothesSizeDetail[id=" + id + "]";
    }
  }