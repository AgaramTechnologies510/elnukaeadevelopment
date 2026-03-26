package com.agaram.eln.primary.model.protocols;
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
@Table(name = "LSprotocolupdates")
public class LSprotocolupdates {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolupdates_seq")
	@SequenceGenerator(name = "lsprotocolupdates_seq", sequenceName = "lsprotocolupdates_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "protocolcode")
	private Integer protocolcode;
	@Column(columnDefinition = "text")
	private String protocolcomment;
	@Temporal(TemporalType.TIMESTAMP)
	private Date protocolmodifiedDate;
	@Column(name = "protocolmastercode")
	private Integer protocolmastercode;
	
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

	public Integer getProtocolcode() {
		return protocolcode;
	}

	public void setProtocolcode(Integer protocolcode) {
		this.protocolcode = protocolcode;
	}

	public String getProtocolcomment() {
		return protocolcomment;
	}

	public void setProtocolcomment(String protocolcomment) {
		this.protocolcomment = protocolcomment;
	}

	public Date getProtocolmodifiedDate() {
		return protocolmodifiedDate;
	}

	public void setProtocolmodifiedDate(Date protocolmodifiedDate) {
		this.protocolmodifiedDate = protocolmodifiedDate;
	}

	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}

	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}

	public LSuserMaster getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(LSuserMaster modifiedby) {
		this.modifiedby = modifiedby;
	}

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	
}
