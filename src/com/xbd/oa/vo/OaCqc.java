package com.xbd.oa.vo;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_cqc")
public class OaCqc implements Serializable {
	@Column(name = "apply_unit_num")
	private Float applyUnitNum;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "loss_bundles")
	private Float lossBundles;
	
	@Column(name = "loss_memo")
	private String lossMemo;
	
	@Column(name = "loss_oddments")
	private Float lossOddments;

	@Column(name = "loss_yiyou")
	private Float lossYiyou;
	
	@Column(name = "loss_company")
	private Float lossCompany;
	
	@Column(name = "loss_other")
	private Float lossOther;
	
	@Column(name = "oa_material_list")
	private Integer oaMaterialList;
	
	@Column(name = "receive_memo")
	private String receiveMemo;
	
	@Column(name = "receive_num")
	private Float receiveNum;
	
	@Column(name = "receive_rate")
	private String receiveRate;
	
	@Column(name = "receive_time")
	private Date receiveTime;
	
	@Column(name = "shear_num_info")
	private String shearNumInfo;
	

	public Float getApplyUnitNum() {
		return applyUnitNum;
	}
	public void setApplyUnitNum(Float applyUnitNum) {
		this.applyUnitNum = applyUnitNum;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Float getLossYiyou() {
		return lossYiyou;
	}
	public void setLossYiyou(Float lossYiyou) {
		this.lossYiyou = lossYiyou;
	}
	public Float getLossCompany() {
		return lossCompany;
	}
	public void setLossCompany(Float lossCompany) {
		this.lossCompany = lossCompany;
	}
	public Float getLossBundles() {
		return lossBundles;
	}
	public void setLossBundles(Float lossBundles) {
		this.lossBundles = lossBundles;
	}
	
	public String getLossMemo() {
		return lossMemo;
	}
	public void setLossMemo(String lossMemo) {
		this.lossMemo = lossMemo;
	}
	
	public Float getLossOddments() {
		return lossOddments;
	}
	public void setLossOddments(Float lossOddments) {
		this.lossOddments = lossOddments;
	}
	
	public Float getLossOther() {
		return lossOther;
	}
	public void setLossOther(Float lossOther) {
		this.lossOther = lossOther;
	}
	
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}
	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}
	
	public String getReceiveMemo() {
		return receiveMemo;
	}
	public void setReceiveMemo(String receiveMemo) {
		this.receiveMemo = receiveMemo;
	}
	
	public Float getReceiveNum() {
		return receiveNum;
	}
	public void setReceiveNum(Float receiveNum) {
		this.receiveNum = receiveNum;
	}
	
	public String getReceiveRate() {
		return receiveRate;
	}
	public void setReceiveRate(String receiveRate) {
		this.receiveRate = receiveRate;
	}
	
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	
	public String getShearNumInfo() {
		return shearNumInfo;
	}
	public void setShearNumInfo(String shearNumInfo) {
		this.shearNumInfo = shearNumInfo;
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
		if (!(object instanceof OaCqc)) {
			return false;
		}
		OaCqc other = (OaCqc) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaCqc[id=" + id + "]";
    }
  }