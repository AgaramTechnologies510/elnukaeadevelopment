package com.agaram.eln.primary.model.usermanagement;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "LSuserteammapping")
public class LSuserteammapping {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsuserteammapping_seq")
	@SequenceGenerator(name = "lsuserteammapping_seq", sequenceName = "lsuserteammapping_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "userteammapcode")
	private Integer userteammapcode;
			
	private Integer teamcode;
	
	@ManyToOne
	private LSuserMaster lsuserMaster;
	public Integer getUserteammapcode() {
		return userteammapcode;
	}
	public void setUserteammapcode(Integer userteammapcode) {
		this.userteammapcode = userteammapcode;
	}
	
	public LSuserMaster getLsuserMaster() {
		return lsuserMaster;
	}
	public void setLsuserMaster(LSuserMaster lsuserMaster) {
		this.lsuserMaster = lsuserMaster;
	}
	public Integer getTeamcode() {
		return teamcode;
	}
	public void setTeamcode(Integer teamcode) {
		this.teamcode = teamcode;
	}
	
}
