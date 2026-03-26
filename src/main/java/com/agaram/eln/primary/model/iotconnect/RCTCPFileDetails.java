package com.agaram.eln.primary.model.iotconnect;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="RCTCPFileDetails")
public class RCTCPFileDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer filecode;
	public String FileUUID;
	public String filename;
	public Integer methodkey;
	public Integer instrumentkey;
	public Integer nequipmentcode;
	
	public Integer getFilecode() {
		return filecode;
	}
	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}
	
	public String getFileUUID() {
		return FileUUID;
	}
	public void setFileUUID(String fileUUID) {
		FileUUID = fileUUID;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getMethodkey() {
		return methodkey;
	}
	public void setMethodkey(Integer methodkey) {
		this.methodkey = methodkey;
	}
	public Integer getInstrumentkey() {
		return instrumentkey;
	}
	public void setInstrumentkey(Integer instrumentkey) {
		this.instrumentkey = instrumentkey;
	}
	public Integer getNequipmentcode() {
		return nequipmentcode;
	}
	public void setNequipmentcode(Integer nequipmentcode) {
		this.nequipmentcode = nequipmentcode;
	}
}
