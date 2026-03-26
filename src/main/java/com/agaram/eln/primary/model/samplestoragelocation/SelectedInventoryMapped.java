package com.agaram.eln.primary.model.samplestoragelocation;

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

@Entity(name = "selectedinventorymapped")
@Table(name = "selectedinventorymapped")
public class SelectedInventoryMapped {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selectedinventorymapped_seq")
	@SequenceGenerator(name = "selectedinventorymapped_seq", sequenceName = "selectedinventorymapped_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "mappedid") 
	private Integer mappedid;
	
	private String storagepath;

	public String id;
	
	private Integer nmaterialinventorycode;
	
	@ManyToOne
	@JoinColumn(name = "samplestoragelocationkey", referencedColumnName = "samplestoragelocationkey")
	private SampleStorageLocation samplestoragelocationkey;
	
	public String getStoragepath() {
		return storagepath;
	}

	public void setStoragepath(String storagepath) {
		this.storagepath = storagepath;
	}

	public SampleStorageLocation getSamplestoragelocationkey() {
		return samplestoragelocationkey;
	}

	public void setSamplestoragelocationkey(SampleStorageLocation samplestoragelocationkey) {
		this.samplestoragelocationkey = samplestoragelocationkey;
	}

	public Integer getMappedid() {
		return mappedid;
	}

	public void setMappedid(Integer mappedid) {
		this.mappedid = mappedid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNmaterialinventorycode() {
		return nmaterialinventorycode;
	}

	public void setNmaterialinventorycode(Integer nmaterialinventorycode) {
		this.nmaterialinventorycode = nmaterialinventorycode;
	}
}