package com.agaram.eln.primary.model.material;

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
@Table(name = "materialprojectmap")
public class MaterialProjectMap {

	@Id
	@Column(name = "materialprojectcode")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer materialprojectcode;
	
	private Integer nmaterialcode;
	
	@ManyToOne
	private LSprojectmaster lsproject;
	
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
	
	
}
