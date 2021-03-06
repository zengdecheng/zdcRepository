package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * QA表
 * @author fangwei
 * @version 创建时间：2015年3月26日  下午6:04:05
 */
@Entity
@Table(name = "oa_qa")
public class OaQa extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Integer oaOrderId;					//订单ID
	private String qualifiedNumInfo;			//合格 数量信息
	private String unqualifiedNumInfo;			//次品 数量信息
	private Float unqualifiedTotal;				//合格总计
	private Float qualifiedTotal;				//次品总计
    private Float gyQualified;                  //工艺次品
    private Float blQualified;                  //布料次品
    private Float hdQualified;                  //后道次品
    private Float cpQualified;                  //车缝次品
    private Float hdLost;                       //后道丢失
    private Float otherQualified;               //其他次品

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "qualified_num_info",columnDefinition="varchar(200)")
	public String getQualifiedNumInfo() {
		return qualifiedNumInfo;
	}

	@Column(name = "qualified_total",columnDefinition="float(10,2)")
	public Float getQualifiedTotal() {
		return qualifiedTotal;
	}

	@Column(name = "unqualified_num_info",columnDefinition="varchar(200)")
	public String getUnqualifiedNumInfo() {
		return unqualifiedNumInfo;
	}

	@Column(name = "unqualified_total",columnDefinition="float(10,2)")
	public Float getUnqualifiedTotal() {
		return unqualifiedTotal;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setQualifiedNumInfo(String qualifiedNumInfo) {
		this.qualifiedNumInfo = qualifiedNumInfo;
	}

	public void setQualifiedTotal(Float qualifiedTotal) {
		this.qualifiedTotal = qualifiedTotal;
	}

	public void setUnqualifiedNumInfo(String unqualifiedNumInfo) {
		this.unqualifiedNumInfo = unqualifiedNumInfo;
	}

	public void setUnqualifiedTotal(Float unqualifiedTotal) {
		this.unqualifiedTotal = unqualifiedTotal;
	}

    @Column(name = "gy_qualified",columnDefinition="float(10,2)")
    public Float getGyQualified() {
        return gyQualified;
    }

    public void setGyQualified(Float gyQualified) {
        this.gyQualified = gyQualified;
    }

    @Column(name = "bl_qualified",columnDefinition="float(10,2)")
    public Float getBlQualified() {
        return blQualified;
    }

    public void setBlQualified(Float blQualified) {
        this.blQualified = blQualified;
    }

    @Column(name = "hd_qualified",columnDefinition="float(10,2)")
    public Float getHdQualified() {
        return hdQualified;
    }

    public void setHdQualified(Float hdQualified) {
        this.hdQualified = hdQualified;
    }

    @Column(name = "cp_qualified",columnDefinition="float(10,2)")
    public Float getCpQualified() {
        return cpQualified;
    }

    public void setCpQualified(Float cpQualified) {
        this.cpQualified = cpQualified;
    }

    @Column(name = "hd_lost",columnDefinition="float(10,2)")
    public Float getHdLost() {
        return hdLost;
    }

    public void setHdLost(Float hdLost) {
        this.hdLost = hdLost;
    }

    @Column(name = "other_qualified",columnDefinition="float(10,2)")
    public Float getOtherQualified() {
        return otherQualified;
    }

    public void setOtherQualified(Float otherQualified) {
        this.otherQualified = otherQualified;
    }


    @Override
	public String toString() {
		return "OaQa [oaOrderId=" + oaOrderId + ", qualifiedNumInfo=" + qualifiedNumInfo + ", unqualifiedNumInfo=" + unqualifiedNumInfo + ", unqualifiedTotal=" + unqualifiedTotal + ", qualifiedTotal=" + qualifiedTotal + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((qualifiedNumInfo == null) ? 0 : qualifiedNumInfo.hashCode());
		result = prime * result + ((qualifiedTotal == null) ? 0 : qualifiedTotal.hashCode());
		result = prime * result + ((unqualifiedNumInfo == null) ? 0 : unqualifiedNumInfo.hashCode());
		result = prime * result + ((unqualifiedTotal == null) ? 0 : unqualifiedTotal.hashCode());
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
		OaQa other = (OaQa) obj;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (qualifiedNumInfo == null) {
			if (other.qualifiedNumInfo != null)
				return false;
		} else if (!qualifiedNumInfo.equals(other.qualifiedNumInfo))
			return false;
		if (qualifiedTotal == null) {
			if (other.qualifiedTotal != null)
				return false;
		} else if (!qualifiedTotal.equals(other.qualifiedTotal))
			return false;
		if (unqualifiedNumInfo == null) {
			if (other.unqualifiedNumInfo != null)
				return false;
		} else if (!unqualifiedNumInfo.equals(other.unqualifiedNumInfo))
			return false;
		if (unqualifiedTotal == null) {
			if (other.unqualifiedTotal != null)
				return false;
		} else if (!unqualifiedTotal.equals(other.unqualifiedTotal))
			return false;
		return true;
	}

}