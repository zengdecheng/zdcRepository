package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

@Entity
@Table(name = "oa_material_list")
public class OaMaterialList extends CommonBean{

	private static final long serialVersionUID = 1L;

	private String color;				//色号
	private String materialName;		//面料名称
	private String materialProp;		//面料属性
	private Float orderNum;				//订单数量
	private String position;			//位置
	private String supplierAddr;		//供应商地址
	private String supplierName;		//供应商名称
	private String supplierTel;			//供应商电话
	private String type;				//主/辅料
	private Integer oaOrderId;			//订单ID
	

	@Column(name = "color",columnDefinition="varchar(20)")
	public String getColor() {
		return color;
	}

	@Column(name = "material_name",columnDefinition="varchar(20)")
	public String getMaterialName() {
		return materialName;
	}

	@Column(name = "material_prop",columnDefinition="varchar(20)")
	public String getMaterialProp() {
		return materialProp;
	}

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "order_num",columnDefinition="float(10,2)")
	public Float getOrderNum() {
		return orderNum;
	}

	@Column(name = "position",columnDefinition="varchar(20)")
	public String getPosition() {
		return position;
	}

	@Column(name = "supplier_addr",columnDefinition="varchar(50)")
	public String getSupplierAddr() {
		return supplierAddr;
	}

	@Column(name = "supplier_name",columnDefinition="varchar(20)")
	public String getSupplierName() {
		return supplierName;
	}

	@Column(name = "supplier_tel",columnDefinition="varchar(20)")
	public String getSupplierTel() {
		return supplierTel;
	}

	@Column(name = "type",columnDefinition="varchar(20)")
	public String getType() {
		return type;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setMaterialProp(String materialProp) {
		this.materialProp = materialProp;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setOrderNum(Float orderNum) {
		this.orderNum = orderNum;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSupplierAddr(String supplierAddr) {
		this.supplierAddr = supplierAddr;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}

	public void setType(String type) {
		this.type = type;
	}
}