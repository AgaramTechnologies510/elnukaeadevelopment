package com.agaram.eln.primary.model.material;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "materialprojecthistory")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialProjectHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialprojecthistory_seq")
	@SequenceGenerator(name = "materialprojecthistory_seq", sequenceName = "materialprojecthistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "materialprojectcode")
	private Integer materialprojectcode;
	
	private Integer nmaterialcode;
	
	@ManyToOne
	private LSprojectmaster lsproject;
	
	private Date createddate;
	
	@ManyToOne
	private LSuserMaster createby;
	
	private String description;
	
	@Transient
	private String materialname;

	

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public Integer getMaterialprojectcode() {
		return materialprojectcode;
	}

	public void setMaterialprojectcode(Integer materialprojectcode) {
		this.materialprojectcode = materialprojectcode;
	}

	public Integer getNmaterialcode() {
		return nmaterialcode;
	}

	public void setNmaterialcode(Integer nmaterialcode) {
		this.nmaterialcode = nmaterialcode;
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
