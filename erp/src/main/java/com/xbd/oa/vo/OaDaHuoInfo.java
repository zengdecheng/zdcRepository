package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 大货清单表
 * @author fangwei
 * @version 创建时间：2015年3月24日  下午4:17:36
 */
@Entity
@Table(name = "oa_da_huo_info")
public class OaDaHuoInfo extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Float buffon;				//布封
	private Float buyerLoss;			//采购损
	private Float deviation;			//空差
	private Float freight;				//运费
	private String itMemo;				//技术备注
	private String purMemo;				//采购备注
	private Float needNum;				//需求数量
	private Float num;					//采购数量
	private Integer oaMaterialList;		//用料搭配ID
	private String org;					//采购单位
	private Float paperTube;			//纸筒
	private String position;			//位置
	private Float price;				//单价
	private Float testPrice;			//验布费用
	private Float total;				//合计
	private Float totalPrice;			//总金额
	private Float unitNum;				//单件用量

	@Column(name = "buffon", columnDefinition="float(10,2)")
	public Float getBuffon() {
		return buffon;
	}

	@Column(name = "buyer_loss", columnDefinition="float(10,2)")
	public Float getBuyerLoss() {
		return buyerLoss;
	}

	@Column(name = "deviation", columnDefinition="float(10,2)")
	public Float getDeviation() {
		return deviation;
	}

	@Column(name = "freight", columnDefinition="float(10,2)")
	public Float getFreight() {
		return freight;
	}

	@Column(name = "it_memo", columnDefinition="varchar(1000)")
	public String getItMemo() {
		return itMemo;
	}

	@Column(name = "need_num", columnDefinition="float(10,2)")
	public Float getNeedNum() {
		return needNum;
	}

	@Column(name = "num", columnDefinition="float(10,2)")
	public Float getNum() {
		return num;
	}

	@Column(name = "oa_material_list")
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}

	@Column(name = "org",columnDefinition="varchar(20)")
	public String getOrg() {
		return org;
	}

	@Column(name = "paper_tube", columnDefinition="float(10,2)")
	public Float getPaperTube() {
		return paperTube;
	}

	@Column(name = "position", columnDefinition="varchar(200)")
	public String getPosition() {
		return position;
	}

	@Column(name = "price", columnDefinition="float(10,2)")
	public Float getPrice() {
		return price;
	}

	@Column(name = "pur_memo", columnDefinition="varchar(1000)")
	public String getPurMemo() {
		return purMemo;
	}

	@Column(name = "test_price", columnDefinition="float(10,2)")
	public Float getTestPrice() {
		return testPrice;
	}

	@Column(name = "total", columnDefinition="float(10,2)")
	public Float getTotal() {
		return total;
	}

	@Column(name = "total_price", columnDefinition="float(10,2)")
	public Float getTotalPrice() {
		return totalPrice;
	}

	@Column(name = "unit_num", columnDefinition="float(10,2)")
	public Float getUnitNum() {
		return unitNum;
	}

	public void setBuffon(Float buffon) {
		this.buffon = buffon;
	}

	public void setBuyerLoss(Float buyerLoss) {
		this.buyerLoss = buyerLoss;
	}

	public void setDeviation(Float deviation) {
		this.deviation = deviation;
	}

	public void setFreight(Float freight) {
		this.freight = freight;
	}

	public void setItMemo(String itMemo) {
		this.itMemo = itMemo;
	}

	public void setNeedNum(Float needNum) {
		this.needNum = needNum;
	}

	public void setNum(Float num) {
		this.num = num;
	}

	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public void setPaperTube(Float paperTube) {
		this.paperTube = paperTube;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setPurMemo(String purMemo) {
		this.purMemo = purMemo;
	}

	public void setTestPrice(Float testPrice) {
		this.testPrice = testPrice;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setUnitNum(Float unitNum) {
		this.unitNum = unitNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buffon == null) ? 0 : buffon.hashCode());
		result = prime * result + ((buyerLoss == null) ? 0 : buyerLoss.hashCode());
		result = prime * result + ((deviation == null) ? 0 : deviation.hashCode());
		result = prime * result + ((freight == null) ? 0 : freight.hashCode());
		result = prime * result + ((itMemo == null) ? 0 : itMemo.hashCode());
		result = prime * result + ((needNum == null) ? 0 : needNum.hashCode());
		result = prime * result + ((num == null) ? 0 : num.hashCode());
		result = prime * result + ((oaMaterialList == null) ? 0 : oaMaterialList.hashCode());
		result = prime * result + ((org == null) ? 0 : org.hashCode());
		result = prime * result + ((paperTube == null) ? 0 : paperTube.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((purMemo == null) ? 0 : purMemo.hashCode());
		result = prime * result + ((testPrice == null) ? 0 : testPrice.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
		result = prime * result + ((unitNum == null) ? 0 : unitNum.hashCode());
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
		OaDaHuoInfo other = (OaDaHuoInfo) obj;
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
		if (deviation == null) {
			if (other.deviation != null)
				return false;
		} else if (!deviation.equals(other.deviation))
			return false;
		if (freight == null) {
			if (other.freight != null)
				return false;
		} else if (!freight.equals(other.freight))
			return false;
		if (itMemo == null) {
			if (other.itMemo != null)
				return false;
		} else if (!itMemo.equals(other.itMemo))
			return false;
		if (needNum == null) {
			if (other.needNum != null)
				return false;
		} else if (!needNum.equals(other.needNum))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		if (oaMaterialList == null) {
			if (other.oaMaterialList != null)
				return false;
		} else if (!oaMaterialList.equals(other.oaMaterialList))
			return false;
		if (org == null) {
			if (other.org != null)
				return false;
		} else if (!org.equals(other.org))
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
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (purMemo == null) {
			if (other.purMemo != null)
				return false;
		} else if (!purMemo.equals(other.purMemo))
			return false;
		if (testPrice == null) {
			if (other.testPrice != null)
				return false;
		} else if (!testPrice.equals(other.testPrice))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (totalPrice == null) {
			if (other.totalPrice != null)
				return false;
		} else if (!totalPrice.equals(other.totalPrice))
			return false;
		if (unitNum == null) {
			if (other.unitNum != null)
				return false;
		} else if (!unitNum.equals(other.unitNum))
			return false;
		return true;
	}

	public String toString() {
		return "OaDaHuoInfo [buffon=" + buffon + ", buyerLoss=" + buyerLoss + ", deviation=" + deviation + ", freight=" + freight + ", itMemo=" + itMemo + ", purMemo=" + purMemo + ", needNum=" + needNum + ", num=" + num + ", oaMaterialList=" + oaMaterialList + ", org=" + org + ", paperTube=" + paperTube + ", position=" + position + ", price=" + price + ", testPrice=" + testPrice + ", total=" + total + ", totalPrice=" + totalPrice + ", unitNum=" + unitNum + ", getId()=" + getId() + "]";
	}

}