package com.agaram.eln.primary.model.sheetManipulation;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.agaram.eln.primary.model.equipment.Equipment;
import com.agaram.eln.primary.model.instrumentsetup.InstrumentMaster;
import com.agaram.eln.primary.model.methodsetup.Method;

@Entity
@Table(name = "LSfileelnmethod")
public class LSfileelnmethod {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Basic(optional = false)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsfileelnmethod_fileelnmethodcode_seq")
	@SequenceGenerator(name = "lsfileelnmethod_fileelnmethodcode_seq", sequenceName = "lsfileelnmethod_fileelnmethodcode_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "fileelnmethodcode")
	private Integer fileelnmethodcode;
	
	private Integer filecode;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "methodkey")
	private Method method;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instmasterkey")
	private InstrumentMaster instrument;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nequipmentcode", nullable = false)
	private Equipment equipment;
	
	public Integer getFileelnmethodcode() {
		return fileelnmethodcode;
	}

	public void setFileelnmethodcode(Integer fileelnmethodcode) {
		this.fileelnmethodcode = fileelnmethodcode;
	}

	public Integer getFilecode() {
		return filecode;
	}

	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public InstrumentMaster getInstrument() {
		return instrument;
	}

	public void setInstrument(InstrumentMaster instrument) {
		this.instrument = instrument;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
