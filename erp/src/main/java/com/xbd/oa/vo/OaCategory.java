package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_category")
public class OaCategory implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "dahuo_cyc")
	private Float dahuoCyc;
	
	@Column(name = "daban_cyc")
	private Float dabanCyc;
	
	@Column(name = "embroidery")
	private Float embroidery;

	@Column(name = "washwater_time")
	private Float washwaterTime;
	
	@Column(name = "printing_time")
	private Float printingTime;
	
	@Column(name = "folding_time")
	private Float foldingTime;

	@Column(name = "dalan_time")
	private Float dalanTime;
	
	@Column(name = "beads_time")
	private Float beadsTime;
	
	@Column(name = "other_time")
	private Float otherTime;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="explain_txt")
	private String explainTxt;
	
	@Column(name="payment_wait")
	private Float paymentWait;
	
	@Column(name="sell_wait")
	private Float sellWait;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Float getDahuoCyc() {
		return dahuoCyc;
	}

	public void setDahuoCyc(Float dahuoCyc) {
		this.dahuoCyc = dahuoCyc;
	}

	public Float getDabanCyc() {
		return dabanCyc;
	}

	public void setDabanCyc(Float dabanCyc) {
		this.dabanCyc = dabanCyc;
	}

	public Float getEmbroidery() {
		return embroidery;
	}

	public void setEmbroidery(Float embroidery) {
		this.embroidery = embroidery;
	}

	public Float getWashwaterTime() {
		return washwaterTime;
	}

	public void setWashwaterTime(Float washwaterTime) {
		this.washwaterTime = washwaterTime;
	}

	public Float getPrintingTime() {
		return printingTime;
	}

	public void setPrintingTime(Float printingTime) {
		this.printingTime = printingTime;
	}

	public Float getFoldingTime() {
		return foldingTime;
	}

	public void setFoldingTime(Float foldingTime) {
		this.foldingTime = foldingTime;
	}

	public Float getDalanTime() {
		return dalanTime;
	}

	public void setDalanTime(Float dalanTime) {
		this.dalanTime = dalanTime;
	}

	public Float getBeadsTime() {
		return beadsTime;
	}

	public void setBeadsTime(Float beadsTime) {
		this.beadsTime = beadsTime;
	}

	public Float getOtherTime() {
		return otherTime;
	}

	public void setOtherTime(Float otherTime) {
		this.otherTime = otherTime;
	}

public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

public String getExplainTxt() {
		return explainTxt;
	}

	public void setExplainTxt(String explainTxt) {
		this.explainTxt = explainTxt;
	}

	public Float getPaymentWait() {
		return paymentWait;
	}

	public void setPaymentWait(Float paymentWait) {
		this.paymentWait = paymentWait;
	}

public Float getSellWait() {
		return sellWait;
	}

	public void setSellWait(Float sellWait) {
		this.sellWait = sellWait;
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
		if (!(object instanceof OaCategory)) {
			return false;
		}
		OaCategory other = (OaCategory) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaCategory[id=" + id + "]";
    }
  }