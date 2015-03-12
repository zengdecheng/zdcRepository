package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_timebase")
public class OaTimebase implements Serializable {
	@Column(name = "cloth_class")
	private String clothClass;
	
	@Column(name = "define_id")
	private String defineId;
	
	@Column(name = "define_key")
	private String defineKey;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "total_duration")
	private Long totalDuration;
	

	public String getClothClass() {
		return clothClass;
	}
	public void setClothClass(String clothClass) {
		this.clothClass = clothClass;
	}
	
	public String getDefineId() {
		return defineId;
	}
	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}
	
	public String getDefineKey() {
		return defineKey;
	}
	public void setDefineKey(String defineKey) {
		this.defineKey = defineKey;
	}
	
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
   public Long getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
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
		if (!(object instanceof OaTimebase)) {
			return false;
		}
		OaTimebase other = (OaTimebase) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaTimebase[id=" + id + "]";
    }
  }