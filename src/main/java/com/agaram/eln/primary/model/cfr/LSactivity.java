package com.agaram.eln.primary.model.cfr;

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
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "LSactivity")
public class LSactivity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsactivity_seq")
	@SequenceGenerator(name = "lsactivity_seq", sequenceName = "lsactivity_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "activitycode")
	private Integer activitycode;
	
	@Column(columnDefinition = "varchar(255)")
	private String activity;
	
	@ManyToOne 
	private LSuserMaster activityby;
	
	@Transient
	LScfttransaction objsilentaudit;
	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}
	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}
	@Temporal(TemporalType.TIMESTAMP)
	private Date activityDate;
	
	public Integer getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(Integer activitycode) {
		this.activitycode = activitycode;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public LSuserMaster getActivityby() {
		return activityby;
	}
	public void setActivityby(LSuserMaster activityby) {
		this.activityby = activityby;
	}
	
	
}
