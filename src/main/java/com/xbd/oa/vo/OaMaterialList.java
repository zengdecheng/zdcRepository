package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 用料搭配明细表
 * @author fangwei
 * @version 创建时间：2015年3月24日  下午4:52:15
 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((materialName == null) ? 0 : materialName.hashCode());
		result = prime * result + ((materialProp == null) ? 0 : materialProp.hashCode());
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((orderNum == null) ? 0 : orderNum.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((supplierAddr == null) ? 0 : supplierAddr.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		result = prime * result + ((supplierTel == null) ? 0 : supplierTel.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		OaMaterialList other = (OaMaterialList) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (materialName == null) {
			if (other.materialName != null)
				return false;
		} else if (!materialName.equals(other.materialName))
			return false;
		if (materialProp == null) {
			if (other.materialProp != null)
				return false;
		} else if (!materialProp.equals(other.materialProp))
			return false;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (orderNum == null) {
			if (other.orderNum != null)
				return false;
		} else if (!orderNum.equals(other.orderNum))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (supplierAddr == null) {
			if (other.supplierAddr != null)
				return false;
		} else if (!supplierAddr.equals(other.supplierAddr))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		if (supplierTel == null) {
			if (other.supplierTel != null)
				return false;
		} else if (!supplierTel.equals(other.supplierTel))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String toString() {
		return "OaMaterialList [color=" + color + ", materialName=" + materialName + ", materialProp=" + materialProp + ", orderNum=" + orderNum + ", position=" + position + ", supplierAddr=" + supplierAddr + ", supplierName=" + supplierName + ", supplierTel=" + supplierTel + ", type=" + type + ", oaOrderId=" + oaOrderId + ", getId()=" + getId() + "]";
	}

}