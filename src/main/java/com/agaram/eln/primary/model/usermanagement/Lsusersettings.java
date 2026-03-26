package com.agaram.eln.primary.model.usermanagement;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Lsusersettings")
public class Lsusersettings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsusersettings_seq")
	@SequenceGenerator(name = "lsusersettings_seq", sequenceName = "lsusersettings_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "userid")
	private Integer userid;
	
	private Integer usercode;
	
	private String DFormat;
	
//	@OneToOne
//	@JoinColumn(name = "usercode")
//	private LSuserMaster lsusermaster;
//
//	public LSuserMaster getLsusermaster() {
//		return lsusermaster;
//	}
//
//	public void setLsusermaster(LSuserMaster lsusermaster) {
//		this.lsusermaster = lsusermaster;
//	}

	public Integer getUsercode() {
		return usercode;
	}

	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getDFormat() {
		return DFormat;
	}

	public void setDFormat(String dFormat) {
		DFormat = dFormat;
	}
}