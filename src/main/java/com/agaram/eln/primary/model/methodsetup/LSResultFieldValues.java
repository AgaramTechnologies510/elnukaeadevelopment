package com.agaram.eln.primary.model.methodsetup;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lsresultfieldvalues")
public class LSResultFieldValues {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	
	private Integer sno;
		
	private Integer resultid;

	private Integer resseqno;
	
	@Column(columnDefinition = "varchar(100)")
	private String fieldname;
	
	
	@Column(columnDefinition = "varchar(100)")
	private String fieldvalue;


	public Integer getResultid() {
		return resultid;
	}

	public void setResultid(Integer resultid) {
		this.resultid = resultid;
	}

	public Integer getResseqno() {
		return resseqno;
	}

	public void setResseqno(Integer resseqno) {
		this.resseqno = resseqno;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public Integer getSno() {
		return sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}


	
}


