package com.agaram.eln.primary.model.report;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="LSdocreportsversionhistory")
public class LSdocreportsversionhistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsdocreportsversionhistory_seq")
	@SequenceGenerator(name = "lsdocreportsversionhistory_seq", sequenceName = "lsdocreportsversionhistory_seq", allocationSize = 1)
	@Basic(optional = false)
	private Integer docReportsversionhistoryCode;
	private Integer docReportsCode;
	@Column(columnDefinition = "varchar(120)")
	private String fileName;
	private Integer parentversionNo;
	private Integer versionNo;
	private Integer status;
	public Integer getDocReportsversionhistoryCode() {
		return docReportsversionhistoryCode;
	}
	public void setDocReportsversionhistoryCode(Integer docReportsversionhistoryCode) {
		this.docReportsversionhistoryCode = docReportsversionhistoryCode;
	}
	public Integer getDocReportsCode() {
		return docReportsCode;
	}
	public void setDocReportsCode(Integer docReportsCode) {
		this.docReportsCode = docReportsCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getParentversionNo() {
		return parentversionNo;
	}
	public void setParentversionNo(Integer parentversionNo) {
		this.parentversionNo = parentversionNo;
	}
	
}
