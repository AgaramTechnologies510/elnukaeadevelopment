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
@Table(name = "LselnInstrumentmapping")
public class LselnInstrumentmapping {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lselninstrumentmapping_seq")
	@SequenceGenerator(name = "lselninstrumentmapping_seq", sequenceName = "lselninstrumentmapping_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "instrumentmapcode")
	private Integer instrumentmapcode;
	
	private Integer instrumentcode;
	
//	@ManyToOne 
//	private LSinstruments lsinstruments;

	@ManyToOne
	@JoinColumn(columnDefinition = "nvarchar(50)")
	private LsMappedInstruments lsMappedInstruments;

	public LsMappedInstruments getLsMappedInstruments() {
		return lsMappedInstruments;
	}

	public void setLsMappedInstruments(LsMappedInstruments lsMappedInstruments) {
		this.lsMappedInstruments = lsMappedInstruments;
	}

	public Integer getInstrumentmapcode() {
		return instrumentmapcode;
	}

	public void setInstrumentmapcode(Integer instrumentmapcode) {
		this.instrumentmapcode = instrumentmapcode;
	}

	public Integer getInstrumentcode() {
		return instrumentcode;
	}

	public void setInstrumentcode(Integer instrumentcode) {
		this.instrumentcode = instrumentcode;
	}

//	public LSinstruments getLsinstruments() {
//		return lsinstruments;
//	}
//
//	public void setLsinstruments(LSinstruments lsinstruments) {
//		this.lsinstruments = lsinstruments;
//	}
}
