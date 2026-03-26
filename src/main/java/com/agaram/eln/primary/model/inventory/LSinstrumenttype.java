package com.agaram.eln.primary.model.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "LSinstrumenttype")
public class LSinstrumenttype {
	@Id
	private Integer instrumenttypecode;
	private Integer masterauditcode;
	private Integer status;
	@Column(columnDefinition = "text")
	private String description;
	@Column(columnDefinition = "varchar(100)")
	private String instrumenttypename;
	public Integer getInstrumenttypecode() {
		return instrumenttypecode;
	}
	public void setInstrumenttypecode(Integer instrumenttypecode) {
		this.instrumenttypecode = instrumenttypecode;
	}
	public Integer getMasterauditcode() {
		return masterauditcode;
	}
	public void setMasterauditcode(Integer masterauditcode) {
		this.masterauditcode = masterauditcode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInstrumenttypename() {
		return instrumenttypename;
	}
	public void setInstrumenttypename(String instrumenttypename) {
		this.instrumenttypename = instrumenttypename;
	}
	
}
