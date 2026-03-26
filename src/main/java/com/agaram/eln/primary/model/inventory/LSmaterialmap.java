package com.agaram.eln.primary.model.inventory;

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
@Table(name = "LSmaterialmap")
public class LSmaterialmap {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsmaterialmap_seq")
	@SequenceGenerator(name = "lsmaterialmap_seq", sequenceName = "lsmaterialmap_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "lsmaterialcode")
	private Integer lsmaterialcode;
	private Integer testcode;
	private Integer materialcode;
	
	@ManyToOne
	private LSmaterial LSmaterial;
	
	public LSmaterial getLSmaterial() {
		return LSmaterial;
	}
	public void setLSmaterial(LSmaterial lSmaterial) {
		LSmaterial = lSmaterial;
	}
	public Integer getLsmaterialcode() {
		return lsmaterialcode;
	}
	public void setLsmaterialcode(Integer lsmaterialcode) {
		this.lsmaterialcode = lsmaterialcode;
	}
	public Integer getTestcode() {
		return testcode;
	}
	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}
	public Integer getMaterialcode() {
		return materialcode;
	}
	public void setMaterialcode(Integer materialcode) {
		this.materialcode = materialcode;
	}
	
	
}
