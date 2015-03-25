package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_da_ban_info")
public class OaDaBanInfo implements Serializable {
	@Column(name = "buffon")
	private Float buffon;
	
	@Column(name = "buyer_loss")
	private Float buyerLoss;
	
	@Column(name = "component")
	private String component;
	
	@Column(name = "cp_loss")
	private Float cpLoss;
	
	@Column(name = "cp_memo")
	private String cpMemo;
	
	@Column(name = "cp_price")
	private Float cpPrice;
	
	@Column(name = "cp_shear_price")
	private Float cpShearPrice;
	
	@Column(name = "cp_total_price")
	private Float cpTotalPrice;
	
	@Column(name = "delivery_time")
	private Integer deliveryTime;
	
	@Column(name = "deviation")
	private Float deviation;
	
	@Column(name = "goods_price")
	private Float goodsPrice;
	
	@Column(name = "goods_unit")
	private String goodsUnit;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "it_memo")
	private String itMemo;
	
	@Column(name = "oa_material_list")
	private Integer oaMaterialList;
	
	@Column(name = "paper_tube")
	private Float paperTube;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "pur_memo")
	private String purMemo;
	
	@Column(name = "shear_price")
	private Float shearPrice;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "unit_num")
	private Float unitNum;
	
	@Column(name = "weight")
	private Integer weight;
	

	public Float getBuffon() {
		return buffon;
	}
	public void setBuffon(Float buffon) {
		this.buffon = buffon;
	}
	
	public Float getBuyerLoss() {
		return buyerLoss;
	}
	public void setBuyerLoss(Float buyerLoss) {
		this.buyerLoss = buyerLoss;
	}
	
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	
	public Float getCpLoss() {
		return cpLoss;
	}
	public void setCpLoss(Float cpLoss) {
		this.cpLoss = cpLoss;
	}
	
	public String getCpMemo() {
		return cpMemo;
	}
	public void setCpMemo(String cpMemo) {
		this.cpMemo = cpMemo;
	}
	
	public Float getCpPrice() {
		return cpPrice;
	}
	public void setCpPrice(Float cpPrice) {
		this.cpPrice = cpPrice;
	}
	
	public Float getCpShearPrice() {
		return cpShearPrice;
	}
	public void setCpShearPrice(Float cpShearPrice) {
		this.cpShearPrice = cpShearPrice;
	}
	
	public Float getCpTotalPrice() {
		return cpTotalPrice;
	}
	public void setCpTotalPrice(Float cpTotalPrice) {
		this.cpTotalPrice = cpTotalPrice;
	}
	
	public Integer getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public Float getDeviation() {
		return deviation;
	}
	public void setDeviation(Float deviation) {
		this.deviation = deviation;
	}
	
	public Float getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getItMemo() {
		return itMemo;
	}
	public void setItMemo(String itMemo) {
		this.itMemo = itMemo;
	}
	
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}
	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}
	
	public Float getPaperTube() {
		return paperTube;
	}
	public void setPaperTube(Float paperTube) {
		this.paperTube = paperTube;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPurMemo() {
		return purMemo;
	}
	public void setPurMemo(String purMemo) {
		this.purMemo = purMemo;
	}
	
	public Float getShearPrice() {
		return shearPrice;
	}
	public void setShearPrice(Float shearPrice) {
		this.shearPrice = shearPrice;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Float getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(Float unitNum) {
		this.unitNum = unitNum;
	}
	
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
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
		if (!(object instanceof OaDaBanInfo)) {
			return false;
		}
		OaDaBanInfo other = (OaDaBanInfo) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaDaBanInfo[id=" + id + "]";
    }
  }