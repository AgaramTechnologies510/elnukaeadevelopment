package com.agaram.eln.primary.model.cfr;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "LSpreferences")
public class LSpreferences {
	//unique = true
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lspreferences_seq")
	@SequenceGenerator(name = "lspreferences_seq", sequenceName = "lspreferences_seq", allocationSize = 1)
	@Basic(optional = false)
	private Integer serialno;
	private String tasksettings;
	private String valuesettings;
	private String valueencrypted;
	
	@Transient
	LScfttransaction objsilentaudit;

	@Transient
	private Response response;

	@Transient
	LoggedUser objuser;

	@Transient
	private LSuserMaster lsusermaster;
	
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
	public LoggedUser getObjuser() {
		return objuser;
	}
	public void setObjuser(LoggedUser objuser) {
		this.objuser = objuser;
	}

	public LSuserMaster getLsusermaster() {
		return lsusermaster;
	}
	public void setLsusermaster(LSuserMaster lsusermaster) {
		this.lsusermaster = lsusermaster;
	}
	public Integer getSerialno() {
		return serialno;
	}
	public void setSerialno(Integer serialno) {
		this.serialno = serialno;
	}
	public String getTasksettings() {
		return tasksettings;
	}
	public void setTasksettings(String tasksettings) {
		this.tasksettings = tasksettings;
	}
	public String getValuesettings() {
		return valuesettings;
	}
	public void setValuesettings(String valuesettings) {
		this.valuesettings = valuesettings;
	}
	public String getValueencrypted() {
		return valueencrypted;
	}
	public void setValueencrypted(String valueencrypted) {
		this.valueencrypted = valueencrypted;
	}
}
