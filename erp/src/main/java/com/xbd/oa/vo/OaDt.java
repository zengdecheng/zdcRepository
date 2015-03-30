package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 品类详情
 * @author fangwei
 * @version 创建时间：2015年3月24日  下午4:32:22
 */
@Entity
@Table(name = "oa_dt")
public class OaDt extends CommonBean{

	private static final long serialVersionUID = 1L;

	private String type;			//类型
	private String code;			//代码
	private String value;			//品类名称
	private Integer inx;			//序号
	private String memo;			//备注

	@Column(name = "code",columnDefinition="varchar(20)")
	public String getCode() {
		return code;
	}

	@Column(name = "inx")
	public Integer getInx() {
		return inx;
	}

	@Column(name = "memo",columnDefinition="varchar(500)")
	public String getMemo() {
		return memo;
	}

	@Column(name = "type",columnDefinition="varchar(2)")
	public String getType() {
		return type;
	}

	@Column(name = "value",columnDefinition="varchar(50)")
	public String getValue() {
		return value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((inx == null) ? 0 : inx.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OaDt other = (OaDt) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (inx == null) {
			if (other.inx != null)
				return false;
		} else if (!inx.equals(other.inx))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toString() {
		return "OaDt [type=" + type + ", code=" + code + ", value=" + value + ", inx=" + inx + ", memo=" + memo + ", getId()=" + getId() + "]";
	}

}