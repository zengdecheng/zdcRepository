package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 加工说明
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午5:55:26
 */

@Entity
@Table(name = "oa_process_explain")
public class OaProcessExplain extends CommonBean {
	private static final long serialVersionUID = 1L;

	private String cutArt;					//裁床工艺
	private Integer oaOrderId;				//订单ID
	private String sewingPic;				//车缝说明图片
	private String sewing;					//车缝要求
	private String specialArt;				//特殊工艺
	private String tailButton;				//尾部工艺
	private String measurePic;				//度量图
	private String tailIroning;				//尾部工艺 大烫
	private String tailCard;				//尾部工艺 吊牌
	private String tailPackaging;			//尾部工艺 包装

	@Column(name = "cut_art",columnDefinition="varchar(1000)")
	public String getCutArt() {
		return cutArt;
	}

	@Column(name = "measure_pic",columnDefinition="varchar(200)")
	public String getMeasurePic() {
		return measurePic;
	}

	@Column(name = "oa_order_id")
	public Integer getOaOrderId() {
		return oaOrderId;
	}

	@Column(name = "sewing",columnDefinition="varchar(1000)")
	public String getSewing() {
		return sewing;
	}

	@Column(name = "sewing_pic",columnDefinition="varchar(200)")
	public String getSewingPic() {
		return sewingPic;
	}

	@Column(name = "special_art",columnDefinition="varchar(1000)")
	public String getSpecialArt() {
		return specialArt;
	}

	@Column(name = "tail_button",columnDefinition="varchar(1000)")
	public String getTailButton() {
		return tailButton;
	}

	@Column(name = "tail_card",columnDefinition="varchar(1000)")
	public String getTailCard() {
		return tailCard;
	}

	@Column(name = "tail_ironing",columnDefinition="varchar(1000)")
	public String getTailIroning() {
		return tailIroning;
	}

	@Column(name = "tail_packaging",columnDefinition="varchar(1000)")
	public String getTailPackaging() {
		return tailPackaging;
	}

	public void setCutArt(String cutArt) {
		this.cutArt = cutArt;
	}

	public void setMeasurePic(String measurePic) {
		this.measurePic = measurePic;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public void setSewing(String sewing) {
		this.sewing = sewing;
	}

	public void setSewingPic(String sewingPic) {
		this.sewingPic = sewingPic;
	}

	public void setSpecialArt(String specialArt) {
		this.specialArt = specialArt;
	}

	public void setTailButton(String tailButton) {
		this.tailButton = tailButton;
	}

	public void setTailCard(String tailCard) {
		this.tailCard = tailCard;
	}

	public void setTailIroning(String tailIroning) {
		this.tailIroning = tailIroning;
	}

	public void setTailPackaging(String tailPackaging) {
		this.tailPackaging = tailPackaging;
	}

	public String toString() {
		return "OaProcessExplain [cutArt=" + cutArt + ", oaOrderId=" + oaOrderId + ", sewingPic=" + sewingPic + ", sewing=" + sewing + ", specialArt=" + specialArt + ", tailButton=" + tailButton + ", measurePic=" + measurePic + ", tailIroning=" + tailIroning + ", tailCard=" + tailCard + ", tailPackaging=" + tailPackaging + ", id=" + id + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cutArt == null) ? 0 : cutArt.hashCode());
		result = prime * result + ((measurePic == null) ? 0 : measurePic.hashCode());
		result = prime * result + ((oaOrderId == null) ? 0 : oaOrderId.hashCode());
		result = prime * result + ((sewing == null) ? 0 : sewing.hashCode());
		result = prime * result + ((sewingPic == null) ? 0 : sewingPic.hashCode());
		result = prime * result + ((specialArt == null) ? 0 : specialArt.hashCode());
		result = prime * result + ((tailButton == null) ? 0 : tailButton.hashCode());
		result = prime * result + ((tailCard == null) ? 0 : tailCard.hashCode());
		result = prime * result + ((tailIroning == null) ? 0 : tailIroning.hashCode());
		result = prime * result + ((tailPackaging == null) ? 0 : tailPackaging.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaProcessExplain other = (OaProcessExplain) obj;
		if (cutArt == null) {
			if (other.cutArt != null)
				return false;
		} else if (!cutArt.equals(other.cutArt))
			return false;
		if (measurePic == null) {
			if (other.measurePic != null)
				return false;
		} else if (!measurePic.equals(other.measurePic))
			return false;
		if (oaOrderId == null) {
			if (other.oaOrderId != null)
				return false;
		} else if (!oaOrderId.equals(other.oaOrderId))
			return false;
		if (sewing == null) {
			if (other.sewing != null)
				return false;
		} else if (!sewing.equals(other.sewing))
			return false;
		if (sewingPic == null) {
			if (other.sewingPic != null)
				return false;
		} else if (!sewingPic.equals(other.sewingPic))
			return false;
		if (specialArt == null) {
			if (other.specialArt != null)
				return false;
		} else if (!specialArt.equals(other.specialArt))
			return false;
		if (tailButton == null) {
			if (other.tailButton != null)
				return false;
		} else if (!tailButton.equals(other.tailButton))
			return false;
		if (tailCard == null) {
			if (other.tailCard != null)
				return false;
		} else if (!tailCard.equals(other.tailCard))
			return false;
		if (tailIroning == null) {
			if (other.tailIroning != null)
				return false;
		} else if (!tailIroning.equals(other.tailIroning))
			return false;
		if (tailPackaging == null) {
			if (other.tailPackaging != null)
				return false;
		} else if (!tailPackaging.equals(other.tailPackaging))
			return false;
		return true;
	}

}