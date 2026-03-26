package com.agaram.eln.primary.model.methodsetup;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="CloudParserFile")
public class CloudParserFile {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cloudparserfile_seq")
	@SequenceGenerator(name = "cloudparserfile_seq", sequenceName = "cloudparserfile_seq", allocationSize = 1)
	public Integer parserfilecode;
	public String fileid;
	public String extension;
	public String filename;
	public String originalfilename;
	//private Binary file;
	public Integer version=1;
	 
	public Integer getParserfilecode() {
		return parserfilecode;
	}
	public void setParserfilecode(Integer parserfilecode) {
		this.parserfilecode = parserfilecode;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
//	public Binary getFile() {
//		return file;
//	}
//	public void setFile(Binary file) {
//		this.file = file;
//	}
	public String getOriginalfilename() {
		return originalfilename;
	}
	public void setOriginalfilename(String originalfilename) {
		this.originalfilename = originalfilename;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
