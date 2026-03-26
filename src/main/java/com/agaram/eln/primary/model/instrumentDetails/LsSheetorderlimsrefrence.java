package com.agaram.eln.primary.model.instrumentDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.bson.types.Binary;

@Entity(name = "LsSheetorderlimsrefrence")
@Table(name = "LsSheetorderlimsrefrence")
public class LsSheetorderlimsrefrence {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lssheetorderlimsrefrence_seq")
	@SequenceGenerator(name = "lssheetorderlimsrefrence_seq", sequenceName = "lssheetorderlimsrefrence_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "refrencecode")
	private Long refrencecode;
	
	@Column(columnDefinition = "varchar(250)", name = "batchid")
	private String batchid;
	
	@Column(columnDefinition = "numeric(17,0)",name = "filecode") 
	private Long batchcode;
	
	@Column(columnDefinition = "varchar(250)", name = "filename")
	private String filename;
	
	@Column(columnDefinition = "varchar(250)", name = "fileid")
	private String fileid;
	
	@Column(name = "testcode") 
	private Integer testcode;
	
//	@Column(name = "filecode") 
//	private Integer filecode;
	
	@Transient
	private Binary file;

	public Long getRefrencecode() {
		return refrencecode;
	}

	public void setRefrencecode(Long refrencecode) {
		this.refrencecode = refrencecode;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

//	public Integer getFilecode() {
//		return filecode;
//	}
//
//	public void setFilecode(Integer filecode) {
//		this.filecode = filecode;
//	}

	public Integer getTestcode() {
		return testcode;
	}

	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}

	public Binary getFile() {
		return file;
	}

	public void setFile(Binary file) {
		this.file = file;
	}
	
	
}