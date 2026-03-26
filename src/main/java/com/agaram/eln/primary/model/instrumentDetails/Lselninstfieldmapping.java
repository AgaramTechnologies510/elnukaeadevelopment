package com.agaram.eln.primary.model.instrumentDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Lselninstfieldmapping")
public class Lselninstfieldmapping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lselninstfieldmapping_seq")
	@SequenceGenerator(name = "lselninstfieldmapping_seq", sequenceName = "lselninstfieldmapping_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "instfieldmapcode")
	private Integer instfieldmapcode;
	
	private Integer fieldcode;
	
//	@ManyToOne 
//	private LsMethodFields lsMethodFields;

	@ManyToOne
	@JoinColumn(columnDefinition = "nvarchar(12)")
	private LsMappedFields lsMappedFields;
	
	public Integer getInstfieldmapcode() {
		return instfieldmapcode;
	}

	public void setInstfieldmapcode(Integer instfieldmapcode) {
		this.instfieldmapcode = instfieldmapcode;
	}

	public Integer getFieldcode() {
		return fieldcode;
	}

	public void setFieldcode(Integer fieldcode) {
		this.fieldcode = fieldcode;
	}

	public LsMappedFields getLsMappedFields() {
		return lsMappedFields;
	}

	public void setLsMappedFields(LsMappedFields lsMappedFields) {
		this.lsMappedFields = lsMappedFields;
	}

//	public LsMethodFields getLsMethodFields() {
//		return lsMethodFields;
//	}
//
//	public void setLsMethodFields(LsMethodFields lsMethodFields) {
//		this.lsMethodFields = lsMethodFields;
//	}
	
	
}
