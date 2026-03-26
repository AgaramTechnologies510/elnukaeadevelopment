package com.agaram.eln.primary.model.reports.reportdesigner;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "Cloudreporttemplate")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class Cloudreporttemplate {
	@Id
	private Long templatecode;
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String templatecontent;
	
	public Long getTemplatecode() {
		return templatecode;
	}
	public void setTemplatecode(Long templatecode) {
		this.templatecode = templatecode;
	}
	public String getTemplatecontent() {
		return templatecontent;
	}
	public void setTemplatecontent(String templatecontent) {
		this.templatecontent = templatecontent;
	}
	
	
}
