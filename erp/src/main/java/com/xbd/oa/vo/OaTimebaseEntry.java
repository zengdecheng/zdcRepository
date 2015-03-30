package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:34:35
 */
@Entity
@Table(name = "oa_timebase_entry")
public class OaTimebaseEntry extends CommonBean {

	private static final long serialVersionUID = 1L;

	private Long calculateDuration;
	private Integer oaTimebase;
	private String step;
	private Long stepDuration;
	private String stepName;

	@Column(name = "calculate_duration",columnDefinition="bigint(20)")
	public Long getCalculateDuration() {
		return calculateDuration;
	}

	@Column(name = "oa_timebase")
	public Integer getOaTimebase() {
		return oaTimebase;
	}

	@Column(name = "step",columnDefinition="varchar(50)")
	public String getStep() {
		return step;
	}

	@Column(name = "step_duration",columnDefinition="bigint(20)")
	public Long getStepDuration() {
		return stepDuration;
	}

	@Column(name = "step_name",columnDefinition="varchar(50)")
	public String getStepName() {
		return stepName;
	}

	public void setCalculateDuration(Long calculateDuration) {
		this.calculateDuration = calculateDuration;
	}

	public void setOaTimebase(Integer oaTimebase) {
		this.oaTimebase = oaTimebase;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public void setStepDuration(Long stepDuration) {
		this.stepDuration = stepDuration;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	@Override
	public String toString() {
		return "OaTimebaseEntry [calculateDuration=" + calculateDuration + ", oaTimebase=" + oaTimebase + ", step=" + step + ", stepDuration=" + stepDuration + ", stepName=" + stepName + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calculateDuration == null) ? 0 : calculateDuration.hashCode());
		result = prime * result + ((oaTimebase == null) ? 0 : oaTimebase.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		result = prime * result + ((stepDuration == null) ? 0 : stepDuration.hashCode());
		result = prime * result + ((stepName == null) ? 0 : stepName.hashCode());
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
		OaTimebaseEntry other = (OaTimebaseEntry) obj;
		if (calculateDuration == null) {
			if (other.calculateDuration != null)
				return false;
		} else if (!calculateDuration.equals(other.calculateDuration))
			return false;
		if (oaTimebase == null) {
			if (other.oaTimebase != null)
				return false;
		} else if (!oaTimebase.equals(other.oaTimebase))
			return false;
		if (step == null) {
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		if (stepDuration == null) {
			if (other.stepDuration != null)
				return false;
		} else if (!stepDuration.equals(other.stepDuration))
			return false;
		if (stepName == null) {
			if (other.stepName != null)
				return false;
		} else if (!stepName.equals(other.stepName))
			return false;
		return true;
	}

}