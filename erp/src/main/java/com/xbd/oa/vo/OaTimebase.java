package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:37:06
 */
@Entity
@Table(name = "oa_timebase")
public class OaTimebase extends CommonBean {

	private static final long serialVersionUID = 1L;

	private String clothClass;
	private String defineId;
	private String defineKey;
	private String name;
	private Long totalDuration;

	@Column(name = "cloth_class",columnDefinition="varchar(50)")
	public String getClothClass() {
		return clothClass;
	}

	@Column(name = "define_id",columnDefinition="varchar(50)")
	public String getDefineId() {
		return defineId;
	}

	@Column(name = "define_key",columnDefinition="varchar(50)")
	public String getDefineKey() {
		return defineKey;
	}

	@Column(name = "name",columnDefinition="varchar(50)")
	public String getName() {
		return name;
	}

	@Column(name = "total_duration",columnDefinition="bigint(20)")
	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setClothClass(String clothClass) {
		this.clothClass = clothClass;
	}

	public void setDefineId(String defineId) {
		this.defineId = defineId;
	}

	public void setDefineKey(String defineKey) {
		this.defineKey = defineKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}

	@Override
	public String toString() {
		return "OaTimebase [clothClass=" + clothClass + ", defineId=" + defineId + ", defineKey=" + defineKey + ", name=" + name + ", totalDuration=" + totalDuration + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothClass == null) ? 0 : clothClass.hashCode());
		result = prime * result + ((defineId == null) ? 0 : defineId.hashCode());
		result = prime * result + ((defineKey == null) ? 0 : defineKey.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((totalDuration == null) ? 0 : totalDuration.hashCode());
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
		OaTimebase other = (OaTimebase) obj;
		if (clothClass == null) {
			if (other.clothClass != null)
				return false;
		} else if (!clothClass.equals(other.clothClass))
			return false;
		if (defineId == null) {
			if (other.defineId != null)
				return false;
		} else if (!defineId.equals(other.defineId))
			return false;
		if (defineKey == null) {
			if (other.defineKey != null)
				return false;
		} else if (!defineKey.equals(other.defineKey))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (totalDuration == null) {
			if (other.totalDuration != null)
				return false;
		} else if (!totalDuration.equals(other.totalDuration))
			return false;
		return true;
	}
	
}