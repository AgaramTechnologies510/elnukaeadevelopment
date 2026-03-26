package com.agaram.eln.primary.model.instrumentDetails;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "Lselninstrumentmaster")
public class Lselninstrumentmaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "instrumentcode")
	private Integer instrumentcode;
	@Column(columnDefinition = "varchar(255)")
	private String instrumentname;
	private Integer status;
	@ManyToOne
	private LSSiteMaster lssitemaster;

	public LSSiteMaster getLssitemaster() {
		return lssitemaster;
	}

	public void setLssitemaster(LSSiteMaster lssitemaster) {
		this.lssitemaster = lssitemaster;
	}
	@OneToMany
	@JoinColumn(name="instrumentcode")
	private List<Lselninstrumentfields> lsfields;
	
	@OneToMany
	@JoinColumn(name="instrumentcode")
	private List<LselnInstrumentmapping> lselnInstrumentmapping;
	
	@Transient
	private Response response;
	
	@Transient
	LScfttransaction objsilentaudit;
	
	@Transient
	LScfttransaction objmanualaudit;
	
	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}
	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}
	@Transient
	LoggedUser objuser;
	
	@Transient
	LSuserMaster LSuserMaster;
	
	@ManyToOne
	private	LSuserMaster modifiedby;
	
	public Integer getInstrumentcode() {
		return instrumentcode;
	}
	public void setInstrumentcode(Integer instrumentcode) {
		this.instrumentcode = instrumentcode;
	}
	public String getInstrumentname() {
		return instrumentname;
	}
	public void setInstrumentname(String instrumentname) {
		this.instrumentname = instrumentname;
	}
	public List<Lselninstrumentfields> getLsfields() {
		return lsfields;
	}
	public void setLsfields(List<Lselninstrumentfields> lsfields) {
		this.lsfields = lsfields;
	}
	public List<LselnInstrumentmapping> getLselnInstrumentmapping() {
		return lselnInstrumentmapping;
	}
	public void setLselnInstrumentmapping(List<LselnInstrumentmapping> lselnInstrumentmapping) {
		this.lselnInstrumentmapping = lselnInstrumentmapping;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}
	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}
	public LoggedUser getObjuser() {
		return objuser;
	}
	public void setObjuser(LoggedUser objuser) {
		this.objuser = objuser;
	}
	public LSuserMaster getLSuserMaster() {
		return LSuserMaster;
	}
	public void setLSuserMaster(LSuserMaster lSuserMaster) {
		LSuserMaster = lSuserMaster;
	}

	public LSuserMaster getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(LSuserMaster modifiedby) {
		this.modifiedby = modifiedby;
	}
	
}
