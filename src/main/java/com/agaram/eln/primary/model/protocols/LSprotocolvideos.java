package com.agaram.eln.primary.model.protocols;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="LSprotocolvideos")
public class LSprotocolvideos {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolvideos_seq")
	@SequenceGenerator(name = "lsprotocolvideos_seq", sequenceName = "lsprotocolvideos_seq", allocationSize = 1)
	public Integer protocolstepvideoscode;
	public Integer protocolstepcode;
	public Integer protocolmastercode;
	public Integer stepno;
	public String protocolstepname;
	public String fileid;
	public String extension;
	public String filename;
	public Integer getProtocolstepvideoscode() {
		return protocolstepvideoscode;
	}
	public Integer getProtocolstepcode() {
		return protocolstepcode;
	}
	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}
	public Integer getStepno() {
		return stepno;
	}
	public String getProtocolstepname() {
		return protocolstepname;
	}
	public String getFileid() {
		return fileid;
	}
	public String getExtension() {
		return extension;
	}
	public String getFilename() {
		return filename;
	}
	public void setProtocolstepvideoscode(Integer protocolstepvideoscode) {
		this.protocolstepvideoscode = protocolstepvideoscode;
	}
	public void setProtocolstepcode(Integer protocolstepcode) {
		this.protocolstepcode = protocolstepcode;
	}
	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}
	public void setStepno(Integer stepno) {
		this.stepno = stepno;
	}
	public void setProtocolstepname(String protocolstepname) {
		this.protocolstepname = protocolstepname;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
