package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_timebase_entry")
public class OaTimebaseEntry implements Serializable {
	@Column(name = "calculate_duration")
	private Long calculateDuration;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "oa_timebase")
	private Integer oaTimebase;
	
	@Column(name = "step")
	private String step;
	
	@Column(name = "step_duration")
	private Long stepDuration;
	
	@Column(name = "step_name")
	private String stepName;
	
	public Long getCalculateDuration() {
		return calculateDuration;
	}
	public void setCalculateDuration(Long calculateDuration) {
		this.calculateDuration = calculateDuration;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public Long getStepDuration() {
		return stepDuration;
	}
	public void setStepDuration(Long stepDuration) {
		this.stepDuration = stepDuration;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOaTimebase() {
		return oaTimebase;
	}
	public void setOaTimebase(Integer oaTimebase) {
		this.oaTimebase = oaTimebase;
	}
	
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
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
		if (!(object instanceof OaTimebaseEntry)) {
			return false;
		}
		OaTimebaseEntry other = (OaTimebaseEntry) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaTimebaseEntry[id=" + id + "]";
    }
  }