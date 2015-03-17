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
	private Long dahuoCyc;

	@Column(name = "daban_cyc")
	private Long dabanCyc;

	@Column(name = "embroidery")
	private Long embroidery;

	@Column(name = "washwater_time")
	private Long washwaterTime;

	@Column(name = "printing_time")
	private Long printingTime;

	@Column(name = "folding_time")
	private Long foldingTime;

	@Column(name = "dalan_time")
	private Long dalanTime;

	@Column(name = "beads_time")
	private Long beadsTime;

	@Column(name = "other_time")
	private Long otherTime;

	@Column(name = "remark")
	private String remark;

	@Column(name = "explain_txt")
	private String explainTxt;

	@Column(name = "payment_wait")
	private Long paymentWait;

	@Column(name = "sell_wait")
	private Long sellWait;

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

	public Long getDahuoCyc() {
		return dahuoCyc;
	}

	public void setDahuoCyc(Long dahuoCyc) {
		this.dahuoCyc = dahuoCyc;
	}

	public Long getDabanCyc() {
		return dabanCyc;
	}

	public void setDabanCyc(Long dabanCyc) {
		this.dabanCyc = dabanCyc;
	}

	public Long getEmbroidery() {
		return embroidery;
	}

	public void setEmbroidery(Long embroidery) {
		this.embroidery = embroidery;
	}

	public Long getWashwaterTime() {
		return washwaterTime;
	}

	public void setWashwaterTime(Long washwaterTime) {
		this.washwaterTime = washwaterTime;
	}

	public Long getPrintingTime() {
		return printingTime;
	}

	public void setPrintingTime(Long printingTime) {
		this.printingTime = printingTime;
	}

	public Long getFoldingTime() {
		return foldingTime;
	}

	public void setFoldingTime(Long foldingTime) {
		this.foldingTime = foldingTime;
	}

	public Long getDalanTime() {
		return dalanTime;
	}

	public void setDalanTime(Long dalanTime) {
		this.dalanTime = dalanTime;
	}

	public Long getBeadsTime() {
		return beadsTime;
	}

	public void setBeadsTime(Long beadsTime) {
		this.beadsTime = beadsTime;
	}

	public Long getOtherTime() {
		return otherTime;
	}

	public void setOtherTime(Long otherTime) {
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

	public Long getPaymentWait() {
		return paymentWait;
	}

	public void setPaymentWait(Long paymentWait) {
		this.paymentWait = paymentWait;
	}

	public Long getSellWait() {
		return sellWait;
	}

	public void setSellWait(Long sellWait) {
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
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaCategory[id=" + id + "]";
	}
}