package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 订单数量表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午5:48:34
 */
@Entity
@Table(name = "oa_order_num")
public class OaOrderNum extends CommonBean {

	private static final long serialVersionUID = 1L;

	private String numInfo;

	private String title;

	@Column(name = "num_info",columnDefinition="varchar(1000)")
	public String getNumInfo() {
		return numInfo;
	}

	public void setNumInfo(String numInfo) {
		this.numInfo = numInfo;
	}

	@Column(name = "title",columnDefinition="varchar(200)")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "OaOrderNum [numInfo=" + numInfo + ", title=" + title + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numInfo == null) ? 0 : numInfo.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		OaOrderNum other = (OaOrderNum) obj;
		if (numInfo == null) {
			if (other.numInfo != null)
				return false;
		} else if (!numInfo.equals(other.numInfo))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}