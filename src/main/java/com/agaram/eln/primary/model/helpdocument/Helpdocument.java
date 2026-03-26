package com.agaram.eln.primary.model.helpdocument;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "Helpdocument")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class Helpdocument {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "helpdocument_seq")
	@SequenceGenerator(name = "helpdocument_seq", sequenceName = "helpdocument_seq", allocationSize = 1)
	@Basic(optional = false)
	private Integer id;

	@JdbcTypeCode(SqlTypes.JSON)
	 @Column(columnDefinition = "jsonb")
		public String lshelpdocumentcontent;
	 
	@Column(columnDefinition = "varchar(255)")
	 public String documentname;
	
	private Integer nodecode;
	
	private Integer filetype=0;
	
	public String getPagename() {
		return pagename;
	}
	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
	@Transient
	private String pagename;
	
	@Column(columnDefinition = "varchar(255)")
	 public String fileref;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLshelpdocumentcontent() {
		return lshelpdocumentcontent;
	}
	public void setLshelpdocumentcontent(String lshelpdocumentcontent) {
		this.lshelpdocumentcontent = lshelpdocumentcontent;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public Integer getNodecode() {
		return nodecode;
	}
	public void setNodecode(Integer nodecode) {
		this.nodecode = nodecode;
	}
	public Integer getFiletype() {
		return filetype;
	}
	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}
	public String getFileref() {
		return fileref;
	}
	public void setFileref(String fileref) {
		this.fileref = fileref;
	}

}
