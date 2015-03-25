package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_material_list")
public class OaMaterialList implements Serializable {
	@Column(name = "color")
	private String color;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "material_name")
	private String materialName;
	
	@Column(name = "material_prop")
	private String materialProp;
	
	@Column(name = "order_num")
	private Float orderNum;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "supplier_addr")
	private String supplierAddr;
	
	@Column(name = "supplier_name")
	private String supplierName;
	
	@Column(name = "supplier_tel")
	private String supplierTel;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "oa_order_id")
	private Integer oaOrderId;

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	
	public String getMaterialProp() {
		return materialProp;
	}
	public void setMaterialProp(String materialProp) {
		this.materialProp = materialProp;
	}
	
	public Float getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Float orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getSupplierAddr() {
		return supplierAddr;
	}
	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getSupplierTel() {
		return supplierTel;
	}
	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getOaOrderId() {
		return oaOrderId;
	}
	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
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
		if (!(object instanceof OaMaterialList)) {
			return false;
		}
		OaMaterialList other = (OaMaterialList) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaMaterialList[id=" + id + "]";
    }
  }