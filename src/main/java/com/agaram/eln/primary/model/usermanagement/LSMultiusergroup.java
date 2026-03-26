package com.agaram.eln.primary.model.usermanagement;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "LSMultiusergroup")
public class LSMultiusergroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "multiusergroupcode") 
	private int multiusergroupcode;

	@Column(name = "usercode")
//	@JsonBackReference
	private Integer usercode;
	@ManyToOne 
	private LSusergroup lsusergroup;
	@Column(name = "defaultusergroup")
	private Integer defaultusergroup;
	public Integer getDefaultusergroup() {
		return defaultusergroup;
	}
	public void setDefaultusergroup(Integer defaultusergroup) {
		this.defaultusergroup = defaultusergroup;
	}

	public Integer getUsercode() {
		return usercode;
	}
	public int getMultiusergroupcode() {
		return multiusergroupcode;
	}
	public void setMultiusergroupcode(int multiusergroupcode) {
		this.multiusergroupcode = multiusergroupcode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public LSusergroup getLsusergroup() {
		return lsusergroup;
	}
	public void setLsusergroup(LSusergroup lsusergroup) {
		this.lsusergroup = lsusergroup;
	}
}
