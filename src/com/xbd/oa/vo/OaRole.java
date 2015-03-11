package com.xbd.oa.vo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial") 
@Entity
@Table(name = "oa_role")
public class OaRole implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "state")
	private String state;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		if (!(object instanceof OaRole)) {
			return false;
		}
		OaRole other = (OaRole) object;
		if (this.id != other.id
				&& (this.id == null || !this.id
						.equals(other.id)))
			return false;
		return true;
	}
@Override
	 public String toString() {
        return "OaRole[id=" + id + "]";
    }
  }