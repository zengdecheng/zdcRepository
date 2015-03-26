package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 用户表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午6:18:25
 */
@Entity
@Table(name = "oa_staff")
public class OaStaff extends CommonBean {
	private static final long serialVersionUID = 1L;

	private String linkphone;
	private String linkww;
	private String loginName;
	private String memo;
	private Integer oaOrg;
	private Integer oaRole;
	private String password;
	private String state;

	@Column(name = "linkphone",columnDefinition="varchar(20)")
	public String getLinkphone() {
		return linkphone;
	}

	@Column(name = "linkww",columnDefinition="varchar(20)")
	public String getLinkww() {
		return linkww;
	}

	@Column(name = "login_name",columnDefinition="varchar(50)")
	public String getLoginName() {
		return loginName;
	}

	@Column(name = "memo",columnDefinition="varchar(200)")
	public String getMemo() {
		return memo;
	}

	@Column(name = "oa_org")
	public Integer getOaOrg() {
		return oaOrg;
	}

	@Column(name = "oa_role")
	public Integer getOaRole() {
		return oaRole;
	}

	@Column(name = "password",columnDefinition="varchar(50)")
	public String getPassword() {
		return password;
	}

	@Column(name = "state",columnDefinition="char(1)")
	public String getState() {
		return state;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public void setLinkww(String linkww) {
		this.linkww = linkww;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setOaOrg(Integer oaOrg) {
		this.oaOrg = oaOrg;
	}

	public void setOaRole(Integer oaRole) {
		this.oaRole = oaRole;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((linkphone == null) ? 0 : linkphone.hashCode());
		result = prime * result + ((linkww == null) ? 0 : linkww.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((oaOrg == null) ? 0 : oaOrg.hashCode());
		result = prime * result + ((oaRole == null) ? 0 : oaRole.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		OaStaff other = (OaStaff) obj;
		if (linkphone == null) {
			if (other.linkphone != null)
				return false;
		} else if (!linkphone.equals(other.linkphone))
			return false;
		if (linkww == null) {
			if (other.linkww != null)
				return false;
		} else if (!linkww.equals(other.linkww))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (oaOrg == null) {
			if (other.oaOrg != null)
				return false;
		} else if (!oaOrg.equals(other.oaOrg))
			return false;
		if (oaRole == null) {
			if (other.oaRole != null)
				return false;
		} else if (!oaRole.equals(other.oaRole))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OaStaff [linkphone=" + linkphone + ", linkww=" + linkww + ", loginName=" + loginName + ", memo=" + memo + ", oaOrg=" + oaOrg + ", oaRole=" + oaRole + ", password=" + password + ", state=" + state + ", id=" + id + "]";
	}
	
}