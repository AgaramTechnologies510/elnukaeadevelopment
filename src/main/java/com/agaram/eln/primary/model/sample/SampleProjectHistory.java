package com.agaram.eln.primary.model.sample;


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
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "sampleprojecthistory")
public class SampleProjectHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sampleprojecthistory_seq")
	@SequenceGenerator(name = "sampleprojecthistory_seq", sequenceName = "sampleprojecthistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "sampleprojectcode")
	private Integer sampleprojectcode;
	
	private Integer samplecode;
	
	@ManyToOne
	private LSprojectmaster lsproject;
	
	private Date createddate;
	
	@ManyToOne
	private LSuserMaster createby;
	
	private String description;
	
	@Transient
	private String samplename;

	public String getSamplename() {
		return samplename;
	}

	public Integer getSampleprojectcode() {
		return sampleprojectcode;
	}

	public void setSampleprojectcode(Integer sampleprojectcode) {
		this.sampleprojectcode = sampleprojectcode;
	}

	public Integer getsamplecode() {
		return samplecode;
	}

	public void setsamplecode(Integer samplecode) {
		this.samplecode = samplecode;
	}

	public LSprojectmaster getLsproject() {
		return lsproject;
	}

	public void setLsproject(LSprojectmaster lsproject) {
		this.lsproject = lsproject;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public LSuserMaster getCreateby() {
		return createby;
	}

	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
