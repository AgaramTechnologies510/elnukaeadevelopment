package com.agaram.eln.primary.model.reports.reportviewer;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "Cloudreports")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class Cloudreports {
	@Id
	private Long reportcode;
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String reporttemplatecontent;
	public Long getReportcode() {
		return reportcode;
	}
	public void setReportcode(Long reportcode) {
		this.reportcode = reportcode;
	}
	public String getReporttemplatecontent() {
		return reporttemplatecontent;
	}
	public void setReporttemplatecontent(String reporttemplatecontent) {
		this.reporttemplatecontent = reporttemplatecontent;
	}
	
	
}
