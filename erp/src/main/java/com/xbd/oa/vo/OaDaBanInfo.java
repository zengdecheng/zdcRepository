package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

@Entity
@Table(name = "oa_da_ban_info")
public class OaDaBanInfo extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Float buffon;			//布封
	private Float buyerLoss;		//采购损
	private String component;		//成分
	private String cpMemo;			//核价备注
	private Float cpLoss;			//核价损耗率
	private Float cpPrice;			//打扮大货单价
	private Float cpShearPrice;		//散剪总金额
	private Float cpTotalPrice;		//大货总金额
	private Integer deliveryTime;	//货期
	private Float deviation;		//空差
	private Float goodsPrice;		//大货单价
	private String goodsUnit;		//大货单位
	private String itMemo;			//技术备注
	private Integer oaMaterialList;	//用料搭配ID
	private Float paperTube;		//纸筒
	private String position;		//位置
	private String purMemo;			//采购备注
	private Float shearPrice;		//散剪单价
	private String unit;			//单位
	private Float unitNum;			//单件用量
	private Integer weight;			//克重

	@Column(name = "buffon",columnDefinition="float(10,2)")
	public Float getBuffon() {
		return buffon;
	}

	@Column(name = "buyer_loss",columnDefinition="float(10,2)")
	public Float getBuyerLoss() {
		return buyerLoss;
	}

	@Column(name = "component",columnDefinition="varchar(200)")
	public String getComponent() {
		return component;
	}

	@Column(name = "cp_loss",columnDefinition="float(10,2)")
	public Float getCpLoss() {
		return cpLoss;
	}

	@Column(name = "cp_memo",columnDefinition="varchar(1000)")
	public String getCpMemo() {
		return cpMemo;
	}

	@Column(name = "cp_price",columnDefinition="float(10,3)")
	public Float getCpPrice() {
		return cpPrice;
	}

	@Column(name = "cp_shear_price",columnDefinition="float(10,2)")
	public Float getCpShearPrice() {
		return cpShearPrice;
	}

	@Column(name = "cp_total_price",columnDefinition="float(10,2)")
	public Float getCpTotalPrice() {
		return cpTotalPrice;
	}

	@Column(name = "delivery_time")
	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	@Column(name = "deviation",columnDefinition="float(10,2)")
	public Float getDeviation() {
		return deviation;
	}
	@Column(name = "goods_price",columnDefinition="float(10,3)")
	public Float getGoodsPrice() {
		return goodsPrice;
	}

	@Column(name = "goods_unit",columnDefinition="varchar(20)")
	public String getGoodsUnit() {
		return goodsUnit;
	}

	@Column(name = "it_memo",columnDefinition="varchar(1000)")
	public String getItMemo() {
		return itMemo;
	}

	@Column(name = "oa_material_list")
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}

	@Column(name = "paper_tube",columnDefinition="float(10,2)")
	public Float getPaperTube() {
		return paperTube;
	}

	@Column(name = "position",columnDefinition="varchar(200)")
	public String getPosition() {
		return position;
	}

	@Column(name = "pur_memo",columnDefinition="varchar(1000)")
	public String getPurMemo() {
		return purMemo;
	}

	@Column(name = "shear_price",columnDefinition="float(10,3)")
	public Float getShearPrice() {
		return shearPrice;
	}

	@Column(name = "unit",columnDefinition="varchar(20)")
	public String getUnit() {
		return unit;
	}

	@Column(name = "unit_num",columnDefinition="float(10,2)")
	public Float getUnitNum() {
		return unitNum;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setBuffon(Float buffon) {
		this.buffon = buffon;
	}

	public void setBuyerLoss(Float buyerLoss) {
		this.buyerLoss = buyerLoss;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public void setCpLoss(Float cpLoss) {
		this.cpLoss = cpLoss;
	}

	public void setCpMemo(String cpMemo) {
		this.cpMemo = cpMemo;
	}

	public void setCpPrice(Float cpPrice) {
		this.cpPrice = cpPrice;
	}

	public void setCpShearPrice(Float cpShearPrice) {
		this.cpShearPrice = cpShearPrice;
	}

	public void setCpTotalPrice(Float cpTotalPrice) {
		this.cpTotalPrice = cpTotalPrice;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public void setDeviation(Float deviation) {
		this.deviation = deviation;
	}

	public void setGoodsPrice(Float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public void setItMemo(String itMemo) {
		this.itMemo = itMemo;
	}

	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}

	public void setPaperTube(Float paperTube) {
		this.paperTube = paperTube;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setPurMemo(String purMemo) {
		this.purMemo = purMemo;
	}

	public void setShearPrice(Float shearPrice) {
		this.shearPrice = shearPrice;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setUnitNum(Float unitNum) {
		this.unitNum = unitNum;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffon == null) ? 0 : buffon.hashCode());
		result = prime * result + ((buyerLoss == null) ? 0 : buyerLoss.hashCode());
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((cpLoss == null) ? 0 : cpLoss.hashCode());
		result = prime * result + ((cpMemo == null) ? 0 : cpMemo.hashCode());
		result = prime * result + ((cpPrice == null) ? 0 : cpPrice.hashCode());
		result = prime * result + ((cpShearPrice == null) ? 0 : cpShearPrice.hashCode());
		result = prime * result + ((cpTotalPrice == null) ? 0 : cpTotalPrice.hashCode());
		result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
		result = prime * result + ((deviation == null) ? 0 : deviation.hashCode());
		result = prime * result + ((goodsPrice == null) ? 0 : goodsPrice.hashCode());
		result = prime * result + ((goodsUnit == null) ? 0 : goodsUnit.hashCode());
		result = prime * result + ((itMemo == null) ? 0 : itMemo.hashCode());
		result = prime * result + ((oaMaterialList == null) ? 0 : oaMaterialList.hashCode());
		result = prime * result + ((paperTube == null) ? 0 : paperTube.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((purMemo == null) ? 0 : purMemo.hashCode());
		result = prime * result + ((shearPrice == null) ? 0 : shearPrice.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + ((unitNum == null) ? 0 : unitNum.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		OaDaBanInfo other = (OaDaBanInfo) obj;
		if (buffon == null) {
			if (other.buffon != null)
				return false;
		} else if (!buffon.equals(other.buffon))
			return false;
		if (buyerLoss == null) {
			if (other.buyerLoss != null)
				return false;
		} else if (!buyerLoss.equals(other.buyerLoss))
			return false;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (cpLoss == null) {
			if (other.cpLoss != null)
				return false;
		} else if (!cpLoss.equals(other.cpLoss))
			return false;
		if (cpMemo == null) {
			if (other.cpMemo != null)
				return false;
		} else if (!cpMemo.equals(other.cpMemo))
			return false;
		if (cpPrice == null) {
			if (other.cpPrice != null)
				return false;
		} else if (!cpPrice.equals(other.cpPrice))
			return false;
		if (cpShearPrice == null) {
			if (other.cpShearPrice != null)
				return false;
		} else if (!cpShearPrice.equals(other.cpShearPrice))
			return false;
		if (cpTotalPrice == null) {
			if (other.cpTotalPrice != null)
				return false;
		} else if (!cpTotalPrice.equals(other.cpTotalPrice))
			return false;
		if (deliveryTime == null) {
			if (other.deliveryTime != null)
				return false;
		} else if (!deliveryTime.equals(other.deliveryTime))
			return false;
		if (deviation == null) {
			if (other.deviation != null)
				return false;
		} else if (!deviation.equals(other.deviation))
			return false;
		if (goodsPrice == null) {
			if (other.goodsPrice != null)
				return false;
		} else if (!goodsPrice.equals(other.goodsPrice))
			return false;
		if (goodsUnit == null) {
			if (other.goodsUnit != null)
				return false;
		} else if (!goodsUnit.equals(other.goodsUnit))
			return false;
		if (itMemo == null) {
			if (other.itMemo != null)
				return false;
		} else if (!itMemo.equals(other.itMemo))
			return false;
		if (oaMaterialList == null) {
			if (other.oaMaterialList != null)
				return false;
		} else if (!oaMaterialList.equals(other.oaMaterialList))
			return false;
		if (paperTube == null) {
			if (other.paperTube != null)
				return false;
		} else if (!paperTube.equals(other.paperTube))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (purMemo == null) {
			if (other.purMemo != null)
				return false;
		} else if (!purMemo.equals(other.purMemo))
			return false;
		if (shearPrice == null) {
			if (other.shearPrice != null)
				return false;
		} else if (!shearPrice.equals(other.shearPrice))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (unitNum == null) {
			if (other.unitNum != null)
				return false;
		} else if (!unitNum.equals(other.unitNum))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	public String toString() {
		return "OaDaBanInfo [buffon=" + buffon + ", buyerLoss=" + buyerLoss + ", component=" + component + ", cpMemo=" + cpMemo + ", cpLoss=" + cpLoss + ", cpPrice=" + cpPrice + ", cpShearPrice=" + cpShearPrice + ", cpTotalPrice=" + cpTotalPrice + ", deliveryTime=" + deliveryTime + ", deviation=" + deviation + ", goodsPrice=" + goodsPrice + ", goodsUnit=" + goodsUnit + ", itMemo=" + itMemo + ", oaMaterialList=" + oaMaterialList + ", paperTube=" + paperTube + ", position=" + position + ", purMemo=" + purMemo + ", shearPrice=" + shearPrice + ", unit=" + unit + ", unitNum=" + unitNum + ", weight=" + weight + ", getId()=" + getId() + "]";
	}
}