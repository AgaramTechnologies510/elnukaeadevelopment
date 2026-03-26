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
@Table(name = "LSequipmentmap")
public class LSequipmentmap {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsequipmentmap_seq")
	@SequenceGenerator(name = "lsequipmentmap_seq", sequenceName = "lsequipmentmap_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "equipmentcode")
	private Integer equipmentcode;
	private Integer testcode;
	private Integer instrumentcode;
	
	@ManyToOne
	private LSinstrument LSinstrument;
	
	public LSinstrument getLSinstrument() {
		return LSinstrument;
	}
	public void setLSinstrument(LSinstrument lSinstrument) {
		LSinstrument = lSinstrument;
	}
	public Integer getEquipmentcode() {
		return equipmentcode;
	}
	public void setEquipmentcode(Integer equipmentcode) {
		this.equipmentcode = equipmentcode;
	}
	public Integer getTestcode() {
		return testcode;
	}
	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}
	public Integer getInstrumentcode() {
		return instrumentcode;
	}
	public void setInstrumentcode(Integer instrumentcode) {
		this.instrumentcode = instrumentcode;
	}
	
	
	
}
