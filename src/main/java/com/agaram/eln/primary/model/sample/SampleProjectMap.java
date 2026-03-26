package com.agaram.eln.primary.model.sample;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;

@Entity
@Table(name = "sampleprojectmap")
public class SampleProjectMap {

	@Id
	@Column(name = "sampleprojectcode")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sampleprojectcode;
	
	private Integer samplecode;
	
	@ManyToOne
	private LSprojectmaster lsproject;
	
	@Transient
	private String samplename;

	public String getSamplename() {
		return samplename;
	}

	public void setSamplename(String samplename) {
		this.samplename = samplename;
	}

	public Integer getSampleprojectcode() {
		return sampleprojectcode;
	}

	public void setSampleprojectcode(Integer sampleprojectcode) {
		this.sampleprojectcode = sampleprojectcode;
	}

	public Integer getSamplecode() {
		return samplecode;
	}

	public void setSamplecode(Integer samplecode) {
		this.samplecode = samplecode;
	}

	public LSprojectmaster getLsproject() {
		return lsproject;
	}

	public void setLsproject(LSprojectmaster lsproject) {
		this.lsproject = lsproject;
	}
	
	
}
