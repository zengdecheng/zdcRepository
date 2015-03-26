package com.xbd.oa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xbd.oa.vo.base.CommonBean;

/**
 * 组织机构表
 * 
 * @author fangwei
 * @version 创建时间：2015年3月26日 下午5:50:51
 */
@Entity
@Table(name = "oa_org")
public class OaOrg extends CommonBean {
	private static final long serialVersionUID = 1L;

	private String admin; // 管理员
	private String name; // 组织名称
	private Integer pid; // 父ID

	@Column(name = "admin", columnDefinition = "varchar(50)")
	public String getAdmin() {
		return admin;
	}

	@Column(name = "name", columnDefinition = "varchar(20)")
	public String getName() {
		return name;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return pid;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "OaOrg [admin=" + admin + ", name=" + name + ", pid=" + pid + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
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
		OaOrg other = (OaOrg) obj;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
}