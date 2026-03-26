package com.agaram.eln.primary.model.sheetManipulation;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "LsfilemapBarcode")
public class LsfilemapBarcode {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsfilemapbarcode_seq")
	@SequenceGenerator(name = "lsfilemapbarcode_seq", sequenceName = "lsfilemapbarcode_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "filebarcode")
	private int filebarcode;
	
	private Integer filecode;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String content;
	
	private String labelparameter;
	
	private Integer barcodeno;
	
	
	private String tagname;
	
	private String barocodename;

	public String getBarocodename() {
		return barocodename;
	}

	public void setBarocodename(String barocodename) {
		this.barocodename = barocodename;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public Integer getBarcodeno() {
		return barcodeno;
	}

	public void setBarcodeno(Integer barcodeno) {
		this.barcodeno = barcodeno;
	}

	public int getFilebarcode() {
		return filebarcode;
	}

	public void setFilebarcode(int filebarcode) {
		this.filebarcode = filebarcode;
	}

	public Integer getFilecode() {
		return filecode;
	}

	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLabelparameter() {
		return labelparameter;
	}

	public void setLabelparameter(String labelparameter) {
		this.labelparameter = labelparameter;
	}
}
