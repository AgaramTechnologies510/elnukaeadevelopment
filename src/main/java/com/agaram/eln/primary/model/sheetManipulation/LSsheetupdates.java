package com.agaram.eln.primary.model.sheetManipulation;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "LSsheetupdates")
public class LSsheetupdates {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lssheetupdates_seq")
	@SequenceGenerator(name = "lssheetupdates_seq", sequenceName = "lssheetupdates_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "sheetcode")
	private Integer sheetcode;
	@Column(columnDefinition = "text")
	private String sheetcomment;
	@Temporal(TemporalType.TIMESTAMP)
	private Date sheetmodifiedDate;
	@Column(name = "filecode")
	private Integer filecode;
	
	@ManyToOne 
	private LSuserMaster modifiedby;

	@Transient
	LScfttransaction objsilentaudit;
	
	@Column(columnDefinition = "text")
	private String templateobjects;
	
	public String getTemplateobjects() {
		return templateobjects;
	}

	public void setTemplateobjects(String templateobjects) {
		this.templateobjects = templateobjects;
	}

	
	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public Integer getSheetcode() {
		return sheetcode;
	}

	public void setSheetcode(Integer sheetcode) {
		this.sheetcode = sheetcode;
	}

	public String getSheetcomment() {
		return sheetcomment;
	}

	public void setSheetcomment(String sheetcomment) {
		this.sheetcomment = sheetcomment;
	}

	public Date getSheetmodifiedDate() {
		return sheetmodifiedDate;
	}

	public void setSheetmodifiedDate(Date sheetmodifiedDate) {
		this.sheetmodifiedDate = sheetmodifiedDate;
	}

	public Integer getFilecode() {
		return filecode;
	}

	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}

	public LSuserMaster getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(LSuserMaster modifiedby) {
		this.modifiedby = modifiedby;
	}
	
}
