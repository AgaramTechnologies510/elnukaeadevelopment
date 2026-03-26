package com.agaram.eln.primary.model.archieve;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "lsprojectarchieve")
public class LsProjectarchieve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "projectarchievecode") 
	private Integer projectarchievecode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifieddate;
	
	@Column(columnDefinition = "varchar(100)")
	private String filenameuuid;
	
	@Column(columnDefinition = "varchar(100)")
	private String projectname;
	
	@ManyToOne 
	private LSuserMaster archieveby;
	
	@ManyToOne 
	private LSSiteMaster lssitemaster;
	
	@Transient
	private Response response;

	public Integer getProjectarchievecode() {
		return projectarchievecode;
	}

	public void setProjectarchievecode(Integer projectarchievecode) {
		this.projectarchievecode = projectarchievecode;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public String getFilenameuuid() {
		return filenameuuid;
	}

	public void setFilenameuuid(String filenameuuid) {
		this.filenameuuid = filenameuuid;
	}

	public LSuserMaster getArchieveby() {
		return archieveby;
	}

	public void setArchieveby(LSuserMaster archieveby) {
		this.archieveby = archieveby;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public LSSiteMaster getLssitemaster() {
		return lssitemaster;
	}

	public void setLssitemaster(LSSiteMaster lssitemaster) {
		this.lssitemaster = lssitemaster;
	}
	
	
}
