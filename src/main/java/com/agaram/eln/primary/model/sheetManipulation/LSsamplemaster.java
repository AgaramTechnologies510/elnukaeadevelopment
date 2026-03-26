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
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "LSsamplemaster")
public class LSsamplemaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lssamplemaster_seq")
	@SequenceGenerator(name = "lssamplemaster_seq", sequenceName = "lssamplemaster_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "samplecode") 
	private Integer samplecode;
	
	@Column(columnDefinition = "varchar(100)")
	private String samplename;
	private Integer status;
	
	private String samplecategory;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	@ManyToOne
	private LSSiteMaster lssitemaster;
	
	@ManyToOne
	private LSuserMaster createby;

	@Column(columnDefinition = "varchar(20)")
	private String samplestatus;
	
	@Transient
	private Response response;
	
	@Transient
	LScfttransaction objsilentaudit;
	
	@Transient
	LScfttransaction objmanualaudit;
	
	@ManyToOne
	private LSuserMaster modifiedby;
	
	@Transient
	LoggedUser objuser;
	
	@Transient
	LSuserMaster LSuserMaster;
	
	public LSSiteMaster getLssitemaster() {
		return lssitemaster;
	}

	public void setLssitemaster(LSSiteMaster lssitemaster) {
		this.lssitemaster = lssitemaster;
	}
	
	public String getSamplestatus() {
		if(samplestatus != null)
		{
		return  samplestatus.trim().equals("A")?"Active":"Retired";
		}
		else
		{
			return "";
		}
	}

	public void setSamplestatus(String samplestatus) {
		this.samplestatus = samplestatus;
	}
	
	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}

	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}
	
	public LSuserMaster getLSuserMaster() {
		return LSuserMaster;
	}

	public void setLSuserMaster(LSuserMaster lSuserMaster) {
		LSuserMaster = lSuserMaster;
	}

	public LoggedUser getObjuser() {
		return objuser;
	}

	public void setObjuser(LoggedUser objuser) {
		this.objuser = objuser;
	}

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}
	
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public Integer getSamplecode() {
		return samplecode;
	}
	public void setSamplecode(Integer samplecode) {
		this.samplecode = samplecode;
	}
	public String getSamplename() {
		return samplename;
	}
	public void setSamplename(String samplename) {
		this.samplename = samplename;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public LSuserMaster getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(LSuserMaster modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getSamplecategory() {
		return samplecategory;
	}

	public void setSamplecategory(String samplecategory) {
		this.samplecategory = samplecategory;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public LSuserMaster getCreateby() {
		return createby;
	}

	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}
	
	
}
