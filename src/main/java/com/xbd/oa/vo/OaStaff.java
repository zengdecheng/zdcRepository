package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_staff")
public class OaStaff implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "linkphone")
	private String linkphone;
	
	@Column(name = "linkww")
	private String linkww;
	
	@Column(name = "login_name")
	private String loginName;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "oa_org")
	private Integer oaOrg;
	
	@Column(name = "oa_role")
	private Integer oaRole;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "state")
	private String state;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	
	public String getLinkww() {
		return linkww;
	}
	public void setLinkww(String linkww) {
		this.linkww = linkww;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Integer getOaOrg() {
		return oaOrg;
	}
	public void setOaOrg(Integer oaOrg) {
		this.oaOrg = oaOrg;
	}
	
	public Integer getOaRole() {
		return oaRole;
	}
	public void setOaRole(Integer oaRole) {
		this.oaRole = oaRole;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
		if (!(object instanceof OaStaff)) {
			return false;
		}
		OaStaff other = (OaStaff) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaStaff[id=" + id + "]";
    }
  }