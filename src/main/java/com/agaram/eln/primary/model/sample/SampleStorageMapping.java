package com.agaram.eln.primary.model.sample;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.samplestoragelocation.SampleStorageLocation;

@Entity
@Table(name = "samplestoragemapping")
public class SampleStorageMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "samplestoragemapping_seq")
	@SequenceGenerator(name = "samplestoragemapping_seq", sequenceName = "samplestoragemapping_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "mappedid") 
	private Integer samplemapid;
	
	private String storagepath;

	public String id;
	
	@OneToOne
	private Sample sample;
	
	@Transient
	private String samplename;
	
	public String getSamplename() {
		return samplename;
	}

	public void setSamplename(String samplename) {
		this.samplename = samplename;
	}

	@OneToOne
	@JoinColumn(name = "samplestoragelocationkey", referencedColumnName = "samplestoragelocationkey")
	private SampleStorageLocation samplestoragelocationkey;

	public Integer getSamplemapid() {
		return samplemapid;
	}

	public void setSamplemapid(Integer samplemapid) {
		this.samplemapid = samplemapid;
	}

	public String getStoragepath() {
		return storagepath;
	}

	public void setStoragepath(String storagepath) {
		this.storagepath = storagepath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public SampleStorageLocation getSamplestoragelocationkey() {
		return samplestoragelocationkey;
	}

	public void setSamplestoragelocationkey(SampleStorageLocation samplestoragelocationkey) {
		this.samplestoragelocationkey = samplestoragelocationkey;
	}
	
	
}
