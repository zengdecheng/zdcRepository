package com.xbd.oa.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xbd.oa.vo.base.CommonBean;

/**
 * CQC表
 * @author fangwei
 * @version 创建时间：2015年3月24日 上午10:39:46
 */
@Entity(name = "oa_cqc")
public class OaCqc extends CommonBean {
	private static final long serialVersionUID = 1L;

	private Float applyUnitNum;			//申购 实际单件用量
	private Float lossBundles;			//损耗 捆条布
	private String lossMemo;			//损耗 备注
	private Float lossOddments;			//损耗 预料
	private Float lossYiyou;			//损耗 衣友布损
	private Float lossCompany;			//损耗 公司损耗
	private Float lossOther;			//损耗 预留配布及用途
	private Integer oaMaterialList;		//用料搭配ID
	private String receiveMemo;			//签收 备注
	private Float receiveNum;			//签收 验布实收数量
	private String receiveRate;			//签收 布次率
	private Date receiveTime;			//签收 到布时间
	private String shearNumInfo;		//裁剪 数量信息

	@Column(name = "apply_unit_num",columnDefinition="float(10,2)")
	public Float getApplyUnitNum() {
		return applyUnitNum;
	}

	@Column(name = "loss_bundles",columnDefinition="float(10,2)")
	public Float getLossBundles() {
		return lossBundles;
	}

	@Column(name = "loss_company",columnDefinition="float(10,2)")
	public Float getLossCompany() {
		return lossCompany;
	}

	@Column(name = "loss_memo",columnDefinition="varchar(500)")
	public String getLossMemo() {
		return lossMemo;
	}

	@Column(name = "loss_oddments",columnDefinition="float(10,2)")
	public Float getLossOddments() {
		return lossOddments;
	}

	@Column(name = "loss_other",columnDefinition="float(10,2)")
	public Float getLossOther() {
		return lossOther;
	}

	@Column(name = "loss_yiyou",columnDefinition="float(10,2)")
	public Float getLossYiyou() {
		return lossYiyou;
	}

	@Column(name = "oa_material_list")
	public Integer getOaMaterialList() {
		return oaMaterialList;
	}

	@Column(name = "receive_memo",columnDefinition="varchar(500)")
	public String getReceiveMemo() {
		return receiveMemo;
	}

	@Column(name = "receive_num",columnDefinition="float(10,2)")
	public Float getReceiveNum() {
		return receiveNum;
	}

	@Column(name = "receive_rate",columnDefinition="varchar(100)")
	public String getReceiveRate() {
		return receiveRate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "receive_time",columnDefinition="timestamp null default null ")
	public Date getReceiveTime() {
		return receiveTime;
	}

	@Column(name = "shear_num_info",columnDefinition="varchar(200)")
	public String getShearNumInfo() {
		return shearNumInfo;
	}

	public void setApplyUnitNum(Float applyUnitNum) {
		this.applyUnitNum = applyUnitNum;
	}

	public void setLossBundles(Float lossBundles) {
		this.lossBundles = lossBundles;
	}

	public void setLossCompany(Float lossCompany) {
		this.lossCompany = lossCompany;
	}

	public void setLossMemo(String lossMemo) {
		this.lossMemo = lossMemo;
	}

	public void setLossOddments(Float lossOddments) {
		this.lossOddments = lossOddments;
	}

	public void setLossOther(Float lossOther) {
		this.lossOther = lossOther;
	}

	public void setLossYiyou(Float lossYiyou) {
		this.lossYiyou = lossYiyou;
	}

	public void setOaMaterialList(Integer oaMaterialList) {
		this.oaMaterialList = oaMaterialList;
	}

	public void setReceiveMemo(String receiveMemo) {
		this.receiveMemo = receiveMemo;
	}

	public void setReceiveNum(Float receiveNum) {
		this.receiveNum = receiveNum;
	}

	public void setReceiveRate(String receiveRate) {
		this.receiveRate = receiveRate;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public void setShearNumInfo(String shearNumInfo) {
		this.shearNumInfo = shearNumInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applyUnitNum == null) ? 0 : applyUnitNum.hashCode());
		result = prime * result + ((lossBundles == null) ? 0 : lossBundles.hashCode());
		result = prime * result + ((lossCompany == null) ? 0 : lossCompany.hashCode());
		result = prime * result + ((lossMemo == null) ? 0 : lossMemo.hashCode());
		result = prime * result + ((lossOddments == null) ? 0 : lossOddments.hashCode());
		result = prime * result + ((lossOther == null) ? 0 : lossOther.hashCode());
		result = prime * result + ((lossYiyou == null) ? 0 : lossYiyou.hashCode());
		result = prime * result + ((oaMaterialList == null) ? 0 : oaMaterialList.hashCode());
		result = prime * result + ((receiveMemo == null) ? 0 : receiveMemo.hashCode());
		result = prime * result + ((receiveNum == null) ? 0 : receiveNum.hashCode());
		result = prime * result + ((receiveRate == null) ? 0 : receiveRate.hashCode());
		result = prime * result + ((receiveTime == null) ? 0 : receiveTime.hashCode());
		result = prime * result + ((shearNumInfo == null) ? 0 : shearNumInfo.hashCode());
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
		OaCqc other = (OaCqc) obj;
		if (applyUnitNum == null) {
			if (other.applyUnitNum != null)
				return false;
		} else if (!applyUnitNum.equals(other.applyUnitNum))
			return false;
		if (lossBundles == null) {
			if (other.lossBundles != null)
				return false;
		} else if (!lossBundles.equals(other.lossBundles))
			return false;
		if (lossCompany == null) {
			if (other.lossCompany != null)
				return false;
		} else if (!lossCompany.equals(other.lossCompany))
			return false;
		if (lossMemo == null) {
			if (other.lossMemo != null)
				return false;
		} else if (!lossMemo.equals(other.lossMemo))
			return false;
		if (lossOddments == null) {
			if (other.lossOddments != null)
				return false;
		} else if (!lossOddments.equals(other.lossOddments))
			return false;
		if (lossOther == null) {
			if (other.lossOther != null)
				return false;
		} else if (!lossOther.equals(other.lossOther))
			return false;
		if (lossYiyou == null) {
			if (other.lossYiyou != null)
				return false;
		} else if (!lossYiyou.equals(other.lossYiyou))
			return false;
		if (oaMaterialList == null) {
			if (other.oaMaterialList != null)
				return false;
		} else if (!oaMaterialList.equals(other.oaMaterialList))
			return false;
		if (receiveMemo == null) {
			if (other.receiveMemo != null)
				return false;
		} else if (!receiveMemo.equals(other.receiveMemo))
			return false;
		if (receiveNum == null) {
			if (other.receiveNum != null)
				return false;
		} else if (!receiveNum.equals(other.receiveNum))
			return false;
		if (receiveRate == null) {
			if (other.receiveRate != null)
				return false;
		} else if (!receiveRate.equals(other.receiveRate))
			return false;
		if (receiveTime == null) {
			if (other.receiveTime != null)
				return false;
		} else if (!receiveTime.equals(other.receiveTime))
			return false;
		if (shearNumInfo == null) {
			if (other.shearNumInfo != null)
				return false;
		} else if (!shearNumInfo.equals(other.shearNumInfo))
			return false;
		return true;
	}

	public String toString() {
		return "OaCqc [applyUnitNum=" + applyUnitNum + ", lossBundles=" + lossBundles + ", lossMemo=" + lossMemo + ", lossOddments=" + lossOddments + ", lossYiyou=" + lossYiyou + ", lossCompany=" + lossCompany + ", lossOther=" + lossOther + ", oaMaterialList=" + oaMaterialList + ", receiveMemo=" + receiveMemo + ", receiveNum=" + receiveNum + ", receiveRate=" + receiveRate + ", receiveTime=" + receiveTime + ", shearNumInfo=" + shearNumInfo + ", getId()=" + getId() + "]";
	}
}