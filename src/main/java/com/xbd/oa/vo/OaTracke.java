package com.xbd.oa.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 异动表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:29:21
 */
@Entity
@Table(name = "oa_tracke")
public class OaTracke extends CommonBean {

	private static final long serialVersionUID = 1L;

	private String memo;
	private String node;
	private Integer oaOrder;
	private Date time;
	private String user;

	@Column(name = "memo",columnDefinition="varchar(200)")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "node",columnDefinition="varchar(20)")
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Column(name = "oa_order")
	public Integer getOaOrder() {
		return oaOrder;
	}

	public void setOaOrder(Integer oaOrder) {
		this.oaOrder = oaOrder;
	}

	@Column(name = "time",columnDefinition="timestamp null default null")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "user",columnDefinition="varchar(20)")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime * result + ((oaOrder == null) ? 0 : oaOrder.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		OaTracke other = (OaTracke) obj;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (oaOrder == null) {
			if (other.oaOrder != null)
				return false;
		} else if (!oaOrder.equals(other.oaOrder))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaTracke [memo=" + memo + ", node=" + node + ", oaOrder=" + oaOrder + ", time=" + time + ", user=" + user + ", id=" + id + "]";
	}
	
}