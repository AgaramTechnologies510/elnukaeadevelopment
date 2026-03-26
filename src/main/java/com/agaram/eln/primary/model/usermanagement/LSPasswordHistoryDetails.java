package com.agaram.eln.primary.model.usermanagement;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "LSpasswordhistorydetails")
public class LSPasswordHistoryDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lspasswordhistorydetails_seq")
	@SequenceGenerator(name = "lspasswordhistorydetails_seq", sequenceName = "lspasswordhistorydetails_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "passwordcode")
	private Integer passwordcode;
	@Column(columnDefinition = "varchar(255)")
	private String password;
	@Temporal(TemporalType.TIMESTAMP)
	private Date passwordcreatedate;
	
	@ManyToOne
	private LSuserMaster lsusermaster;
	
	public Integer getPasswordcode() {
		return passwordcode;
	}
	public void setPasswordcode(Integer passwordcode) {
		this.passwordcode = passwordcode;
	}
	public LSuserMaster getLsusermaster() {
//		if(this.objuser!=null)
//			{
//				this.lsusermaster = this.objuser.getLsusermaster();
//			}
		return lsusermaster;
	}
	public void setLsusermaster(LSuserMaster lsusermaster) {
		this.lsusermaster = lsusermaster;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getPasswordcreatedate() {
		return passwordcreatedate;
	}
	public void setPasswordcreatedate(Date passwordcreatedate) {
		this.passwordcreatedate = passwordcreatedate;
	}
}
