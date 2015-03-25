package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_clothes_size")
public class OaClothesSize implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_order_id")
	private Integer oaOrderId;
	
	@Column(name = "sample_size")
	private String sampleSize;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "unit")
	private String unit;
	

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
	
	public String getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
		if (!(object instanceof OaClothesSize)) {
			return false;
		}
		OaClothesSize other = (OaClothesSize) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaClothesSize[id=" + id + "]";
    }
  }