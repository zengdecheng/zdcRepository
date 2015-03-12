package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_cost")
public class OaCost implements Serializable {
	@Column(name = "D1")
	private Float d1;
	
	@Column(name = "D2")
	private Float d2;
	
	@Column(name = "D3")
	private Float d3;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "m_goods_price")
	private Float mGoodsPrice;
	
	@Column(name = "m_shear_price")
	private Float mShearPrice;
	
	@Column(name = "o_embroider")
	private Float oEmbroider;
	
	@Column(name = "o_memo")
	private String oMemo;
	
	@Column(name = "o_stamp")
	private Float oStamp;
	
	@Column(name = "o_wash")
	private Float oWash;
	
	@Column(name = "oa_order_id")
	private Integer oaOrderId;
	
	@Column(name = "p_factory_match")
	private String pFactoryMatch;
	
	@Column(name = "p_cutting")
	private String pCutting;
	
	@Column(name = "p_last")
	private String pLast;
	
	@Column(name = "p_memo")
	private String pMemo;
	
	@Column(name = "p_sew")
	private String pSew;
	
	@Column(name = "order_num1")
	private Integer orderNum1;
	
	@Column(name = "order_num2")
	private Integer orderNum2;
	
	@Column(name = "order_num3")
	private Integer orderNum3;
	
	@Column(name = "p_sam")
	private Float pSam;
	
	@Column(name = "minute_wage_rate")
	private Float minuteWageRate;
	
	@Column(name = "b_he1")
	private Float bhe1;
	
	@Column(name = "b_he2")
	private Float bhe2;
	
	@Column(name = "b_he3")
	private Float bhe3;
	
	@Column(name = "b_total1")
	private Float btotal1;
	
	@Column(name = "b_total2")
	private Float btotal2;
	
	@Column(name = "b_total3")
	private Float btotal3;

	@Column(name = "d_total1")
	private Float dtotal1;
	
	@Column(name = "d_total2")
	private Float dtotal2;
	
	@Column(name = "d_total3")
	private Float dtotal3;
	
	@Column(name="wai_xie_total_price")
	private Float waiXieTotalPrice;
	
	public Float getD1() {
		return d1;
	}
	public void setD1(Float d1) {
		this.d1 = d1;
	}
	
	public Float getD2() {
		return d2;
	}
	public void setD2(Float d2) {
		this.d2 = d2;
	}
	
	public Float getD3() {
		return d3;
	}
	public void setD3(Float d3) {
		this.d3 = d3;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Float getMGoodsPrice() {
		return mGoodsPrice;
	}
	public void setMGoodsPrice(Float mGoodsPrice) {
		this.mGoodsPrice = mGoodsPrice;
	}
	
	public Float getMShearPrice() {
		return mShearPrice;
	}
	public void setMShearPrice(Float mShearPrice) {
		this.mShearPrice = mShearPrice;
	}
	
	public Float getOEmbroider() {
		return oEmbroider;
	}
	public void setOEmbroider(Float oEmbroider) {
		this.oEmbroider = oEmbroider;
	}
	
	public String getOMemo() {
		return oMemo;
	}
	public void setOMemo(String oMemo) {
		this.oMemo = oMemo;
	}
	
	public Float getOStamp() {
		return oStamp;
	}
	public void setOStamp(Float oStamp) {
		this.oStamp = oStamp;
	}
	
	public Float getOWash() {
		return oWash;
	}
	public void setOWash(Float oWash) {
		this.oWash = oWash;
	}
	
	public Integer getOaOrderId() {
		return oaOrderId;
	}
	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}
	
	public String getPCutting() {
		return pCutting;
	}
	public void setPCutting(String pCutting) {
		this.pCutting = pCutting;
	}
	
	public String getPLast() {
		return pLast;
	}
	public void setPLast(String pLast) {
		this.pLast = pLast;
	}
	
	public String getPMemo() {
		return pMemo;
	}
	public void setPMemo(String pMemo) {
		this.pMemo = pMemo;
	}
	
	public String getPSew() {
		return pSew;
	}
	public void setPSew(String pSew) {
		this.pSew = pSew;
	}
	
	
	
	public Integer getOrderNum1() {
		return orderNum1;
	}
	public void setOrderNum1(Integer orderNum1) {
		this.orderNum1 = orderNum1;
	}
	public Integer getOrderNum2() {
		return orderNum2;
	}
	public void setOrderNum2(Integer orderNum2) {
		this.orderNum2 = orderNum2;
	}
	public Integer getOrderNum3() {
		return orderNum3;
	}
	public void setOrderNum3(Integer orderNum3) {
		this.orderNum3 = orderNum3;
	}
	public Float getWaiXieTotalPrice() {
		return waiXieTotalPrice;
	}
	public void setWaiXieTotalPrice(Float waiXieTotalPrice) {
		this.waiXieTotalPrice = waiXieTotalPrice;
	}
	public Float getBhe1() {
		return bhe1;
	}
	public void setBhe1(Float bhe1) {
		this.bhe1 = bhe1;
	}
	public Float getBhe2() {
		return bhe2;
	}
	public void setBhe2(Float bhe2) {
		this.bhe2 = bhe2;
	}
	public Float getBhe3() {
		return bhe3;
	}
	public void setBhe3(Float bhe3) {
		this.bhe3 = bhe3;
	}
	public Float getBtotal1() {
		return btotal1;
	}
	public void setBtotal1(Float btotal1) {
		this.btotal1 = btotal1;
	}
	public Float getBtotal2() {
		return btotal2;
	}
	public void setBtotal2(Float btotal2) {
		this.btotal2 = btotal2;
	}
	public Float getBtotal3() {
		return btotal3;
	}
	public void setBtotal3(Float btotal3) {
		this.btotal3 = btotal3;
	}
	public Float getDtotal1() {
		return dtotal1;
	}
	public void setDtotal1(Float dtotal1) {
		this.dtotal1 = dtotal1;
	}
	public Float getDtotal2() {
		return dtotal2;
	}
	public void setDtotal2(Float dtotal2) {
		this.dtotal2 = dtotal2;
	}
	public Float getDtotal3() {
		return dtotal3;
	}
	public void setDtotal3(Float dtotal3) {
		this.dtotal3 = dtotal3;
	}
	public String getPFactoryMatch() {
		return pFactoryMatch;
	}
	public void setPFactoryMatch(String pFactoryMatch) {
		this.pFactoryMatch = pFactoryMatch;
	}
	public Float getPSam() {
		return pSam;
	}
	public void setPSam(Float pSam) {
		this.pSam = pSam;
	}
	public Float getMinuteWageRate() {
		return minuteWageRate;
	}
	public void setMinuteWageRate(Float minuteWageRate) {
		this.minuteWageRate = minuteWageRate;
	}
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

@Override
	public boolean equals(Object object) {
		if (!(object instanceof OaCost)) {
			return false;
		}
		OaCost other = (OaCost) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaCost[id=" + id + "]";
    }
  }