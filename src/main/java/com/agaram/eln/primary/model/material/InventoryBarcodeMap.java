package com.agaram.eln.primary.model.material;

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
@Table(name = "inventorybarcodemap")
public class InventoryBarcodeMap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventorybarcodemap_seq")
	@SequenceGenerator(name = "inventorybarcodemap_seq", sequenceName = "inventorybarcodemap_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "barcodemapid")
	private Integer barcodemapid;
	
	private Integer nmaterialtypecode;
	
	@ManyToOne
	private BarcodeMaster barcode;

	public Integer getBarcodemapid() {
		return barcodemapid;
	}

	public void setBarcodemapid(Integer barcodemapid) {
		this.barcodemapid = barcodemapid;
	}

	public Integer getNmaterialtypecode() {
		return nmaterialtypecode;
	}

	public void setNmaterialtypecode(Integer nmaterialtypecode) {
		this.nmaterialtypecode = nmaterialtypecode;
	}

	public BarcodeMaster getBarcode() {
		return barcode;
	}

	public void setBarcode(BarcodeMaster barcode) {
		this.barcode = barcode;
	}
	
	
}
