package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 品类表
 * @author fangwei
 * @version 创建时间：2015年3月23日  下午5:14:29
 */
@Entity(name = "oa_category")
public class OaCategory extends CommonBean{

	private static final long serialVersionUID = 1L;

	private String name;				//名称			
	private String code;				//编号
	private String status;				//状态
	private Long dahuoCyc;				//大货生产周期
	private Long dabanCyc;				//打版生产周期
	private Long embroidery;			//绣花时间
	private Long washwaterTime;			//洗水时间
	private Long printingTime;			//印花时间
	private Long foldingTime;			//缩折/打条时间
	private Long dalanTime;				//打揽时间
	private Long beadsTime;				//订珠时间
	private Long otherTime;				//其他时间
	private String remark;				//备注
	private String explainTxt;			//说明
	private Long paymentWait;			//贷款等待时间
	private Long sellWait;				//销售等待时间
	
	@Column(name = "beads_time",columnDefinition="BIGINT(13)")
	public Long getBeadsTime() {
		return beadsTime;
	}

	@Column(name = "code",length = 50)
	public String getCode() {
		return code;
	}
	
	@Column(name = "daban_cyc",columnDefinition="BIGINT(13)")
	public Long getDabanCyc() {
		return dabanCyc;
	}

	@Column(name = "dahuo_cyc",columnDefinition="BIGINT(13)")
	public Long getDahuoCyc() {
		return dahuoCyc;
	}
	@Column(name = "dalan_time",columnDefinition="BIGINT(13)")
	public Long getDalanTime() {
		return dalanTime;
	}

	@Column(name = "embroidery",columnDefinition="BIGINT(13)")
	public Long getEmbroidery() {
		return embroidery;
	}

	@Column(name = "explain_txt",length = 200)
	public String getExplainTxt() {
		return explainTxt;
	}

	@Column(name = "folding_time",columnDefinition="BIGINT(13)")
	public Long getFoldingTime() {
		return foldingTime;
	}

	@Column(name = "name",length = 20)
	public String getName() {
		return name;
	}

	@Column(name = "other_time",columnDefinition="BIGINT(13)")
	public Long getOtherTime() {
		return otherTime;
	}

	@Column(name = "payment_wait",columnDefinition="BIGINT(13)")
	public Long getPaymentWait() {
		return paymentWait;
	}

	@Column(name = "printing_time",columnDefinition="BIGINT(13)")
	public Long getPrintingTime() {
		return printingTime;
	}

	
	@Column(name = "remark",length = 200)
	public String getRemark() {
		return remark;
	}

	@Column(name = "sell_wait",columnDefinition="BIGINT(13)")
	public Long getSellWait() {
		return sellWait;
	}

	@Column(name = "status",columnDefinition="CHAR(1)")
	public String getStatus() {
		return status;
	}

	@Column(name = "washwater_time",columnDefinition="BIGINT(13)")
	public Long getWashwaterTime() {
		return washwaterTime;
	}

	public void setBeadsTime(Long beadsTime) {
		this.beadsTime = beadsTime;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDabanCyc(Long dabanCyc) {
		this.dabanCyc = dabanCyc;
	}

	public void setDahuoCyc(Long dahuoCyc) {
		this.dahuoCyc = dahuoCyc;
	}

	public void setDalanTime(Long dalanTime) {
		this.dalanTime = dalanTime;
	}

	public void setEmbroidery(Long embroidery) {
		this.embroidery = embroidery;
	}

	public void setExplainTxt(String explainTxt) {
		this.explainTxt = explainTxt;
	}

	public void setFoldingTime(Long foldingTime) {
		this.foldingTime = foldingTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOtherTime(Long otherTime) {
		this.otherTime = otherTime;
	}

	public void setPaymentWait(Long paymentWait) {
		this.paymentWait = paymentWait;
	}

	public void setPrintingTime(Long printingTime) {
		this.printingTime = printingTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSellWait(Long sellWait) {
		this.sellWait = sellWait;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public void setWashwaterTime(Long washwaterTime) {
		this.washwaterTime = washwaterTime;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beadsTime == null) ? 0 : beadsTime.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((dabanCyc == null) ? 0 : dabanCyc.hashCode());
		result = prime * result + ((dahuoCyc == null) ? 0 : dahuoCyc.hashCode());
		result = prime * result + ((dalanTime == null) ? 0 : dalanTime.hashCode());
		result = prime * result + ((embroidery == null) ? 0 : embroidery.hashCode());
		result = prime * result + ((explainTxt == null) ? 0 : explainTxt.hashCode());
		result = prime * result + ((foldingTime == null) ? 0 : foldingTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((otherTime == null) ? 0 : otherTime.hashCode());
		result = prime * result + ((paymentWait == null) ? 0 : paymentWait.hashCode());
		result = prime * result + ((printingTime == null) ? 0 : printingTime.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((sellWait == null) ? 0 : sellWait.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((washwaterTime == null) ? 0 : washwaterTime.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaCategory other = (OaCategory) obj;
		if (beadsTime == null) {
			if (other.beadsTime != null)
				return false;
		} else if (!beadsTime.equals(other.beadsTime))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dabanCyc == null) {
			if (other.dabanCyc != null)
				return false;
		} else if (!dabanCyc.equals(other.dabanCyc))
			return false;
		if (dahuoCyc == null) {
			if (other.dahuoCyc != null)
				return false;
		} else if (!dahuoCyc.equals(other.dahuoCyc))
			return false;
		if (dalanTime == null) {
			if (other.dalanTime != null)
				return false;
		} else if (!dalanTime.equals(other.dalanTime))
			return false;
		if (embroidery == null) {
			if (other.embroidery != null)
				return false;
		} else if (!embroidery.equals(other.embroidery))
			return false;
		if (explainTxt == null) {
			if (other.explainTxt != null)
				return false;
		} else if (!explainTxt.equals(other.explainTxt))
			return false;
		if (foldingTime == null) {
			if (other.foldingTime != null)
				return false;
		} else if (!foldingTime.equals(other.foldingTime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (otherTime == null) {
			if (other.otherTime != null)
				return false;
		} else if (!otherTime.equals(other.otherTime))
			return false;
		if (paymentWait == null) {
			if (other.paymentWait != null)
				return false;
		} else if (!paymentWait.equals(other.paymentWait))
			return false;
		if (printingTime == null) {
			if (other.printingTime != null)
				return false;
		} else if (!printingTime.equals(other.printingTime))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sellWait == null) {
			if (other.sellWait != null)
				return false;
		} else if (!sellWait.equals(other.sellWait))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (washwaterTime == null) {
			if (other.washwaterTime != null)
				return false;
		} else if (!washwaterTime.equals(other.washwaterTime))
			return false;
		return true;
	}

	public String toString() {
		return "OaCategory [name=" + name + ", code=" + code + ", status=" + status + ", dahuoCyc=" + dahuoCyc + ", dabanCyc=" + dabanCyc + ", embroidery=" + embroidery + ", washwaterTime=" + washwaterTime + ", printingTime=" + printingTime + ", foldingTime=" + foldingTime + ", dalanTime=" + dalanTime + ", beadsTime=" + beadsTime + ", otherTime=" + otherTime + ", remark=" + remark + ", explainTxt=" + explainTxt + ", paymentWait=" + paymentWait + ", sellWait=" + sellWait + "]";
	}
}