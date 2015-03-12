package com.xbd.oa.vo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_da_huo_info")
public class OaDaHuoInfo implements Serializable {
	@Column(name = "buffon")
	private Float buffon;
	
	@Column(name = "buyer_loss")
	private Float buyerLoss;
	
	@Column(name = "deviation")
	private Float deviation;
	
	@Column(name = "freight")
	private Float freight;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "it_memo")
	private String itMemo;
	
	@Column(name = "pur_memo")
	private String purMemo;
	
	@Column(name = "need_num")
	private Float needNum;
	
	@Column(name = "num")
	private Float num;
	
	@Column(name = "oa_material_list")
	private Integer oaMaterialList;
	
	@Column(name = "org")
	private String org;
	
	@Column(name = "paper_tube")
	private Float paperTube;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "test_price")
	private Float testPrice;
	
	@Column(name = "total")
	private Float total;
	
	@Column(name = "total_price")
	private Float totalPrice;
	
	@Column(name = "unit_num")
	private Float unitNum;
	

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
	
	public Float getDeviation() {
		return deviation;
	}
	public void setDeviation(Float deviation) {
		this.deviation = deviation;
	}
	
	public Float getFreight() {
		return freight;
	}
	public void setFreight(Float freight) {
		this.freight = freight;
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
	public String getPurMemo() {
		return purMemo;
	}
	public void setPurMemo(String purMemo) {
		this.purMemo = purMemo;
	}
	public Float getNeedNum() {
		return needNum;
	}
	public void setNeedNum(Float needNum) {
		this.needNum = needNum;
	}
	
	public Float getNum() {
		return num;
	}
	public void setNum(Float num) {
		this.num = num;
	}
	
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}
	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
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
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Float getTestPrice() {
		return testPrice;
	}
	public void setTestPrice(Float testPrice) {
		this.testPrice = testPrice;
	}
	
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Float getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(Float unitNum) {
		this.unitNum = unitNum;
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
		if (!(object instanceof OaDaHuoInfo)) {
			return false;
		}
		OaDaHuoInfo other = (OaDaHuoInfo) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaDaHuoInfo[id=" + id + "]";
    }
  }