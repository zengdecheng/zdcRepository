package com.xbd.oa.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "oa_process_explain")
public class OaProcessExplain implements Serializable {
	@Column(name = "cut_art")
	private String cutArt;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "oa_order_id")
	private Integer oaOrderId;

	@Column(name = "sewing_pic")
	private String sewingPic;

	@Column(name = "sewing")
	private String sewing;

	@Column(name = "special_art")
	private String specialArt;

	@Column(name = "tail_button")
	private String tailButton;

	@Column(name = "measure_pic")
	private String measurePic;

	@Column(name = "tail_ironing")
	private String tailIroning;

	@Column(name = "tail_card")
	private String tailCard;

	@Column(name = "tail_packaging")
	private String tailPackaging;

	public String getCutArt() {
		return cutArt;
	}

	public void setCutArt(String cutArt) {
		this.cutArt = cutArt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOaOrderId() {
		return oaOrderId;
	}

	public void setOaOrderId(Integer oaOrderId) {
		this.oaOrderId = oaOrderId;
	}

	public String getSewing() {
		return sewing;
	}

	public void setSewing(String sewing) {
		this.sewing = sewing;
	}

	public String getSpecialArt() {
		return specialArt;
	}

	public void setSpecialArt(String specialArt) {
		this.specialArt = specialArt;
	}

	public String getSewingPic() {
		return sewingPic;
	}

	public void setSewingPic(String sewingPic) {
		this.sewingPic = sewingPic;
	}

	public String getTailButton() {
		return tailButton;
	}

	public void setTailButton(String tailButton) {
		this.tailButton = tailButton;
	}

	public String getMeasurePic() {
		return measurePic;
	}

	public void setMeasurePic(String measurePic) {
		this.measurePic = measurePic;
	}

	public String getTailIroning() {
		return tailIroning;
	}

	public void setTailIroning(String tailIroning) {
		this.tailIroning = tailIroning;
	}

	public String getTailCard() {
		return tailCard;
	}

	public void setTailCard(String tailCard) {
		this.tailCard = tailCard;
	}

	public String getTailPackaging() {
		return tailPackaging;
	}

	public void setTailPackaging(String tailPackaging) {
		this.tailPackaging = tailPackaging;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OaProcessExplain)) {
			return false;
		}
		OaProcessExplain other = (OaProcessExplain) object;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id)))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaProcessExplain[id=" + id + "]";
	}
}