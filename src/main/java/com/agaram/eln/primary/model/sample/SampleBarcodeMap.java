package com.agaram.eln.primary.model.sample;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.agaram.eln.primary.model.barcode.BarcodeMaster;

@Entity
@Table(name = "samplebarcodemap")
public class SampleBarcodeMap {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "samplebarcodemap_seq")
	@SequenceGenerator(name = "samplebarcodemap_seq", sequenceName = "samplebarcodemap_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "barcodemapid")
	private Integer barcodemapid;
	
	private Integer nsampletypecode;
	
	@ManyToOne
	private BarcodeMaster barcode;

	public Integer getBarcodemapid() {
		return barcodemapid;
	}

	public void setBarcodemapid(Integer barcodemapid) {
		this.barcodemapid = barcodemapid;
	}

	public Integer getNsampletypecode() {
		return nsampletypecode;
	}

	public void setNsampletypecode(Integer nsampletypecode) {
		this.nsampletypecode = nsampletypecode;
	}

	public BarcodeMaster getBarcode() {
		return barcode;
	}

	public void setBarcode(BarcodeMaster barcode) {
		this.barcode = barcode;
	}
	
	
}
