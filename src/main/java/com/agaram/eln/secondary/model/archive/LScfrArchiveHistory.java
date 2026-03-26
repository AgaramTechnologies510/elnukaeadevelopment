package com.agaram.eln.secondary.model.archive;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "LScfrArchiveHistory")
public class LScfrArchiveHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lscfrarchivehistory_seq")
	@SequenceGenerator(name = "lscfrarchivehistory_seq", sequenceName = "lscfrarchivehistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "archivecode")
	private Integer archivecode;
	
//	@Column(columnDefinition = "date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date archivedate;
	
	@Column(columnDefinition = "varchar(255)")
	private String discription;
	
	private int archiveusercode;
	
	@Column(columnDefinition = "varchar(255)")
	private String archiveusername;
	
//	@OneToMany
//	@JoinColumn(name="archivecode")
	@Transient
	private List<LScfrachivetransaction> lscfrachivetransaction;
	
	@Transient
	LScfttransaction objsilentaudit;

	@Transient
	LScfttransaction objmanualaudit;
	@Transient
	LoggedUser objuser;
	
	public LoggedUser getObjuser() {
		return objuser;
	}
	public void setObjuser(LoggedUser objuser) {
		this.objuser = objuser;
	}
	
	public Integer getArchivecode() {
		return archivecode;
	}

	public void setArchivecode(Integer archivecode) {
		this.archivecode = archivecode;
	}

	public Date getArchivedate() {
		return archivedate;
	}

	public void setArchivedate(Date archivedate) {
		this.archivedate = archivedate;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public int getArchiveusercode() {
		return archiveusercode;
	}

	public void setArchiveusercode(int archiveusercode) {
		this.archiveusercode = archiveusercode;
	}

	public String getArchiveusername() {
		return archiveusername;
	}

	public void setArchiveusername(String archiveusername) {
		this.archiveusername = archiveusername;
	}
	
	public List<LScfrachivetransaction> getLscfrachivetransaction() {
		return lscfrachivetransaction;
	}

	public void setLscfrachivetransaction(List<LScfrachivetransaction> lscfrachivetransaction) {
		this.lscfrachivetransaction = lscfrachivetransaction;
	}

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}

	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}

}
