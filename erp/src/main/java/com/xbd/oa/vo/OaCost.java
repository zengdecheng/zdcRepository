package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.xbd.oa.vo.base.CommonBean;
/**
 * 成本表
 * @author fangwei
 * @version 创建时间：2015年3月23日  下午5:14:29
 */
@Entity(name = "oa_cost")
public class OaCost extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Float bhe1;					//B合计1
	private Float bhe2;					//B合计2
	private Float bhe3;					//B合计3
	private Float btotal1;				//总报价B1
	private Float btotal2;				//总报价B2
	private Float btotal3;				//总报价B3
	private Float d1;					//D1
	private Float d2;					//D2
	private Float d3;					//D3
	private Float dtotal1;				//总报价D1
	private Float dtotal2;				//总报价D2
	private Float dtotal3;				//总报价D3
	private Float minuteWageRate;		//分钟工资率
	private Float mGoodsPrice;			//大货物料合计
	private Float mShearPrice;			//散剪物料合计
	private Integer oaOrderId;			//订单ID
	private Float oEmbroider;			//绣花
	private String oMemo;				//备注
	private Integer orderNum1;			//订单量1
	private Integer orderNum2;			//订单量2
	private Integer orderNum3;			//订单量3
	private Float oStamp;				//印花
	private Float oWash;				//洗水
	private String pCutting;			//裁剪
	private String pFactoryMatch;		//工厂匹配系数
	private String pLast;				//后整
	private String pMemo;				//备注
	private String pSew;				//缝制
	private Float pSam;					//SAM
	private Float waiXieTotalPrice;		//外协总价

	@Column(name = "b_he1",columnDefinition="float(10,2)")
	public Float getBhe1() {
		return bhe1;
	}

	@Column(name = "b_he2",columnDefinition="float(10,2)")
	public Float getBhe2() {
		return bhe2;
	}

	@Column(name = "b_he3",columnDefinition="float(10,2)")
	public Float getBhe3() {
		return bhe3;
	}
	@Column(name = "b_total1",columnDefinition="float(10,2)")
	public Float getBtotal1() {
		return btotal1;
	}

	@Column(name = "b_total2",columnDefinition="float(10,2)")
	public Float getBtotal2() {
		return btotal2;
	}

	@Column(name = "b_total3",columnDefinition="float(10,2)")
	public Float getBtotal3() {
		return btotal3;
	}
	
	@Column(name = "D1",columnDefinition="float(10,2)")
	public Float getD1() {
		return d1;
	}

	@Column(name = "D2",columnDefinition="float(10,2)")
	public Float getD2() {
		return d2;
	}

	@Column(name = "D3",columnDefinition="float(10,2)")
	public Float getD3() {
		return d3;
	}

	@Column(name = "d_total1",columnDefinition="float(10,2)")
	public Float getDtotal1() {
		return dtotal1;
	}

	@Column(name = "d_total2",columnDefinition="float(10,2)")
	public Float getDtotal2() {
		return dtotal2;
	}

	@Column(name = "d_total3",columnDefinition="float(10,2)")
	public Float getDtotal3() {
		return dtotal3;
	}

	@Column(name = "m_goods_price",columnDefinition="float(10,2)")
	public Float getMGoodsPrice() {
		return mGoodsPrice;
	}

	@Column(name = "minute_wage_rate",columnDefinition="float(10,2)")
	public Float getMinuteWageRate() {
		return minuteWageRate;
	}

	@Column(name = "m_shear_price",columnDefinition="float(10,2)")
	public Float getMShearPrice() {
		return mShearPrice;
	}
	
	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "o_embroider",columnDefinition="float(10,2)")
	public Float getOEmbroider() {
		return oEmbroider;
	}

	@Column(name = "o_memo",columnDefinition="varchar(1000)")
	public String getOMemo() {
		return oMemo;
	}

	@Column(name = "order_num1")
	public Integer getOrderNum1() {
		return orderNum1;
	}
	@Column(name = "order_num2")
	public Integer getOrderNum2() {
		return orderNum2;
	}

	@Column(name = "order_num3")
	public Integer getOrderNum3() {
		return orderNum3;
	}

	@Column(name = "o_stamp",columnDefinition="float(10,2)")
	public Float getOStamp() {
		return oStamp;
	}

	@Column(name = "o_wash",columnDefinition="float(10,2)")
	public Float getOWash() {
		return oWash;
	}

	@Column(name = "p_cutting", length = 100)
	public String getPCutting() {
		return pCutting;
	}

	@Column(name = "p_factory_match",columnDefinition="varchar(1000)")
	public String getpFactoryMatch() {
		return pFactoryMatch;
	}

	@Column(name = "p_last",length = 100)
	public String getPLast() {
		return pLast;
	}
	
	@Column(name = "p_memo",columnDefinition="varchar(1000)")
	public String getPMemo() {
		return pMemo;
	}

	@Column(name = "p_sam",columnDefinition="float(10,2)")
	public Float getpSam() {
		return pSam;
	}

	@Column(name = "p_sew",length = 100)
	public String getPSew() {
		return pSew;
	}

	@Column(name = "wai_xie_total_price" ,columnDefinition="float(10,2)")
	public Float getWaiXieTotalPrice() {
		return waiXieTotalPrice;
	}

	public void setBhe1(Float bhe1) {
		this.bhe1 = bhe1;
	}

	public void setBhe2(Float bhe2) {
		this.bhe2 = bhe2;
	}

	public void setBhe3(Float bhe3) {
		this.bhe3 = bhe3;
	}

	public void setBtotal1(Float btotal1) {
		this.btotal1 = btotal1;
	}

	public void setBtotal2(Float btotal2) {
		this.btotal2 = btotal2;
	}

	public void setBtotal3(Float btotal3) {
		this.btotal3 = btotal3;
	}

	public void setD1(Float d1) {
		this.d1 = d1;
	}

	public void setD2(Float d2) {
		this.d2 = d2;
	}

	public void setD3(Float d3) {
		this.d3 = d3;
	}

	public void setDtotal1(Float dtotal1) {
		this.dtotal1 = dtotal1;
	}

	public void setDtotal2(Float dtotal2) {
		this.dtotal2 = dtotal2;
	}

	public void setDtotal3(Float dtotal3) {
		this.dtotal3 = dtotal3;
	}

	public void setMGoodsPrice(Float mGoodsPrice) {
		this.mGoodsPrice = mGoodsPrice;
	}

	public void setMinuteWageRate(Float minuteWageRate) {
		this.minuteWageRate = minuteWageRate;
	}

	public void setMShearPrice(Float mShearPrice) {
		this.mShearPrice = mShearPrice;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setOEmbroider(Float oEmbroider) {
		this.oEmbroider = oEmbroider;
	}

	public void setOMemo(String oMemo) {
		this.oMemo = oMemo;
	}

	public void setOrderNum1(Integer orderNum1) {
		this.orderNum1 = orderNum1;
	}

	public void setOrderNum2(Integer orderNum2) {
		this.orderNum2 = orderNum2;
	}

	public void setOrderNum3(Integer orderNum3) {
		this.orderNum3 = orderNum3;
	}

	public void setOStamp(Float oStamp) {
		this.oStamp = oStamp;
	}

	public void setOWash(Float oWash) {
		this.oWash = oWash;
	}

	public void setPCutting(String pCutting) {
		this.pCutting = pCutting;
	}

	public void setPFactoryMatch(String pFactoryMatch) {
		this.pFactoryMatch = pFactoryMatch;
	}

	public void setPLast(String pLast) {
		this.pLast = pLast;
	}

	public void setPMemo(String pMemo) {
		this.pMemo = pMemo;
	}

	public void setPSam(Float pSam) {
		this.pSam = pSam;
	}

	public void setPSew(String pSew) {
		this.pSew = pSew;
	}

	public void setWaiXieTotalPrice(Float waiXieTotalPrice) {
		this.waiXieTotalPrice = waiXieTotalPrice;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bhe1 == null) ? 0 : bhe1.hashCode());
		result = prime * result + ((bhe2 == null) ? 0 : bhe2.hashCode());
		result = prime * result + ((bhe3 == null) ? 0 : bhe3.hashCode());
		result = prime * result + ((btotal1 == null) ? 0 : btotal1.hashCode());
		result = prime * result + ((btotal2 == null) ? 0 : btotal2.hashCode());
		result = prime * result + ((btotal3 == null) ? 0 : btotal3.hashCode());
		result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
		result = prime * result + ((d2 == null) ? 0 : d2.hashCode());
		result = prime * result + ((d3 == null) ? 0 : d3.hashCode());
		result = prime * result + ((dtotal1 == null) ? 0 : dtotal1.hashCode());
		result = prime * result + ((dtotal2 == null) ? 0 : dtotal2.hashCode());
		result = prime * result + ((dtotal3 == null) ? 0 : dtotal3.hashCode());
		result = prime * result + ((mGoodsPrice == null) ? 0 : mGoodsPrice.hashCode());
		result = prime * result + ((mShearPrice == null) ? 0 : mShearPrice.hashCode());
		result = prime * result + ((minuteWageRate == null) ? 0 : minuteWageRate.hashCode());
		result = prime * result + ((oEmbroider == null) ? 0 : oEmbroider.hashCode());
		result = prime * result + ((oMemo == null) ? 0 : oMemo.hashCode());
		result = prime * result + ((oStamp == null) ? 0 : oStamp.hashCode());
		result = prime * result + ((oWash == null) ? 0 : oWash.hashCode());
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((orderNum1 == null) ? 0 : orderNum1.hashCode());
		result = prime * result + ((orderNum2 == null) ? 0 : orderNum2.hashCode());
		result = prime * result + ((orderNum3 == null) ? 0 : orderNum3.hashCode());
		result = prime * result + ((pCutting == null) ? 0 : pCutting.hashCode());
		result = prime * result + ((pFactoryMatch == null) ? 0 : pFactoryMatch.hashCode());
		result = prime * result + ((pLast == null) ? 0 : pLast.hashCode());
		result = prime * result + ((pMemo == null) ? 0 : pMemo.hashCode());
		result = prime * result + ((pSam == null) ? 0 : pSam.hashCode());
		result = prime * result + ((pSew == null) ? 0 : pSew.hashCode());
		result = prime * result + ((waiXieTotalPrice == null) ? 0 : waiXieTotalPrice.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaCost other = (OaCost) obj;
		if (bhe1 == null) {
			if (other.bhe1 != null)
				return false;
		} else if (!bhe1.equals(other.bhe1))
			return false;
		if (bhe2 == null) {
			if (other.bhe2 != null)
				return false;
		} else if (!bhe2.equals(other.bhe2))
			return false;
		if (bhe3 == null) {
			if (other.bhe3 != null)
				return false;
		} else if (!bhe3.equals(other.bhe3))
			return false;
		if (btotal1 == null) {
			if (other.btotal1 != null)
				return false;
		} else if (!btotal1.equals(other.btotal1))
			return false;
		if (btotal2 == null) {
			if (other.btotal2 != null)
				return false;
		} else if (!btotal2.equals(other.btotal2))
			return false;
		if (btotal3 == null) {
			if (other.btotal3 != null)
				return false;
		} else if (!btotal3.equals(other.btotal3))
			return false;
		if (d1 == null) {
			if (other.d1 != null)
				return false;
		} else if (!d1.equals(other.d1))
			return false;
		if (d2 == null) {
			if (other.d2 != null)
				return false;
		} else if (!d2.equals(other.d2))
			return false;
		if (d3 == null) {
			if (other.d3 != null)
				return false;
		} else if (!d3.equals(other.d3))
			return false;
		if (dtotal1 == null) {
			if (other.dtotal1 != null)
				return false;
		} else if (!dtotal1.equals(other.dtotal1))
			return false;
		if (dtotal2 == null) {
			if (other.dtotal2 != null)
				return false;
		} else if (!dtotal2.equals(other.dtotal2))
			return false;
		if (dtotal3 == null) {
			if (other.dtotal3 != null)
				return false;
		} else if (!dtotal3.equals(other.dtotal3))
			return false;
		if (mGoodsPrice == null) {
			if (other.mGoodsPrice != null)
				return false;
		} else if (!mGoodsPrice.equals(other.mGoodsPrice))
			return false;
		if (mShearPrice == null) {
			if (other.mShearPrice != null)
				return false;
		} else if (!mShearPrice.equals(other.mShearPrice))
			return false;
		if (minuteWageRate == null) {
			if (other.minuteWageRate != null)
				return false;
		} else if (!minuteWageRate.equals(other.minuteWageRate))
			return false;
		if (oEmbroider == null) {
			if (other.oEmbroider != null)
				return false;
		} else if (!oEmbroider.equals(other.oEmbroider))
			return false;
		if (oMemo == null) {
			if (other.oMemo != null)
				return false;
		} else if (!oMemo.equals(other.oMemo))
			return false;
		if (oStamp == null) {
			if (other.oStamp != null)
				return false;
		} else if (!oStamp.equals(other.oStamp))
			return false;
		if (oWash == null) {
			if (other.oWash != null)
				return false;
		} else if (!oWash.equals(other.oWash))
			return false;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (orderNum1 == null) {
			if (other.orderNum1 != null)
				return false;
		} else if (!orderNum1.equals(other.orderNum1))
			return false;
		if (orderNum2 == null) {
			if (other.orderNum2 != null)
				return false;
		} else if (!orderNum2.equals(other.orderNum2))
			return false;
		if (orderNum3 == null) {
			if (other.orderNum3 != null)
				return false;
		} else if (!orderNum3.equals(other.orderNum3))
			return false;
		if (pCutting == null) {
			if (other.pCutting != null)
				return false;
		} else if (!pCutting.equals(other.pCutting))
			return false;
		if (pFactoryMatch == null) {
			if (other.pFactoryMatch != null)
				return false;
		} else if (!pFactoryMatch.equals(other.pFactoryMatch))
			return false;
		if (pLast == null) {
			if (other.pLast != null)
				return false;
		} else if (!pLast.equals(other.pLast))
			return false;
		if (pMemo == null) {
			if (other.pMemo != null)
				return false;
		} else if (!pMemo.equals(other.pMemo))
			return false;
		if (pSam == null) {
			if (other.pSam != null)
				return false;
		} else if (!pSam.equals(other.pSam))
			return false;
		if (pSew == null) {
			if (other.pSew != null)
				return false;
		} else if (!pSew.equals(other.pSew))
			return false;
		if (waiXieTotalPrice == null) {
			if (other.waiXieTotalPrice != null)
				return false;
		} else if (!waiXieTotalPrice.equals(other.waiXieTotalPrice))
			return false;
		return true;
	}

	public String toString() {
		return "OaCost [bhe1=" + bhe1 + ", bhe2=" + bhe2 + ", bhe3=" + bhe3 + ", btotal1=" + btotal1 + ", btotal2=" + btotal2 + ", btotal3=" + btotal3 + ", d1=" + d1 + ", d2=" + d2 + ", d3=" + d3 + ", dtotal1=" + dtotal1 + ", dtotal2=" + dtotal2 + ", dtotal3=" + dtotal3 + ", minuteWageRate=" + minuteWageRate + ", mGoodsPrice=" + mGoodsPrice + ", mShearPrice=" + mShearPrice + ", oaOrderId=" + oaOrderId + ", oEmbroider=" + oEmbroider + ", oMemo=" + oMemo + ", orderNum1=" + orderNum1 + ", orderNum2=" + orderNum2 + ", orderNum3=" + orderNum3 + ", oStamp=" + oStamp + ", oWash=" + oWash + ", pCutting=" + pCutting + ", pFactoryMatch=" + pFactoryMatch + ", pLast=" + pLast + ", pMemo=" + pMemo + ", pSew=" + pSew + ", pSam=" + pSam + ", waiXieTotalPrice=" + waiXieTotalPrice + "]";
	}
}