package com.agaram.eln.primary.model.equipment;

import java.util.Date;

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

import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "equipmenthistory")
public class EquipmentHistory{

	@Id
	@Column(name = "nequipmenthistorycode")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipmenthistory_nequipmenthistorycode_seq")
	@SequenceGenerator(name = "equipmenthistory_nequipmenthistorycode_seq", sequenceName = "equipmenthistory_nequipmenthistorycode_seq", allocationSize = 1)
	private Integer nequipmenthistorycode;

	@Column(name = "nequipmentcode")
	private Integer nequipmentcode;
	@Column(name = "historytype")
	private Integer historytype;
	
	@Column(name = "nstatus", nullable = false)
	private Integer nstatus = 1;
	
	@ManyToOne
	private LSuserMaster createdby;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createddate;
	
	private Date callibrationdate;
	private Date manintanancedate;
	private Date lastcallibrated;
	private Date lastmaintained;
	
	@Transient
	Response response;

	public Integer getNequipmenthistorycode() {
		return nequipmenthistorycode;
	}

	public void setNequipmenthistorycode(Integer nequipmenthistorycode) {
		this.nequipmenthistorycode = nequipmenthistorycode;
	}

	public Integer getNequipmentcode() {
		return nequipmentcode;
	}

	public void setNequipmentcode(Integer nequipmentcode) {
		this.nequipmentcode = nequipmentcode;
	}

	public Integer getNstatus() {
		return nstatus;
	}

	public void setNstatus(Integer nstatus) {
		this.nstatus = nstatus;
	}

	public LSuserMaster getCreatedby() {
		return createdby;
	}

	public void setCreatedby(LSuserMaster createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getCallibrationdate() {
		return callibrationdate;
	}

	public void setCallibrationdate(Date callibrationdate) {
		this.callibrationdate = callibrationdate;
	}

	public Date getManintanancedate() {
		return manintanancedate;
	}

	public void setManintanancedate(Date manintanancedate) {
		this.manintanancedate = manintanancedate;
	}

	public Date getLastcallibrated() {
		return lastcallibrated;
	}

	public void setLastcallibrated(Date lastcallibrated) {
		this.lastcallibrated = lastcallibrated;
	}

	public Date getLastmaintained() {
		return lastmaintained;
	}

	public void setLastmaintained(Date lastmaintained) {
		this.lastmaintained = lastmaintained;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Integer getHistorytype() {
		return historytype;
	}

	public void setHistorytype(Integer historytype) {
		this.historytype = historytype;
	}
}
