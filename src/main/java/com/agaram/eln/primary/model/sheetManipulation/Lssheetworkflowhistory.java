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

@Entity(name = "Lssheetworkflowhistory")
@Table(name = "Lssheetworkflowhistory")
public class Lssheetworkflowhistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lssheetworkflowhistory_seq")
	@SequenceGenerator(name = "lssheetworkflowhistory_seq", sequenceName = "lssheetworkflowhistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "historycode")
	private Integer historycode;
	
	@Column(name = "approvelstatus") 
	private Integer approvelstatus;
	
	@Column(name = "filecode")
	private Integer filecode;
	
	@Column(columnDefinition = "varchar(250)")
	private String action;
	
	@Column(columnDefinition = "varchar(250)")
	private String comment;
	
	@Transient
	LScfttransaction objsilentaudit;
	
	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	@ManyToOne 
	private LSuserMaster createby;
	
	//@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	
	@ManyToOne
	private LSsheetworkflow currentworkflow;
	
	@ManyToOne
	private LSsheetworkflow previousworkflowcode;
	
	private String templatename;
	
	private Integer templateversionNumber;

	public LSsheetworkflow getPreviousworkflowcode() {
		return previousworkflowcode;
	}

	public void setPreviousworkflowcode(LSsheetworkflow previousworkflowcode) {
		this.previousworkflowcode = previousworkflowcode;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	public Integer getTemplateversionNumber() {
		return templateversionNumber;
	}

	public void setTemplateversionNumber(Integer templateversionNumber) {
		this.templateversionNumber = templateversionNumber;
	}

	public Integer getHistorycode() {
		return historycode;
	}

	public void setHistorycode(Integer historycode) {
		this.historycode = historycode;
	}

	public Integer getApprovelstatus() {
		return approvelstatus;
	}

	public void setApprovelstatus(Integer approvelstatus) {
		this.approvelstatus = approvelstatus;
	}

	public Integer getFilecode() {
		return filecode;
	}

	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LSuserMaster getCreateby() {
		return createby;
	}

	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public LSsheetworkflow getCurrentworkflow() {
		return currentworkflow;
	}

	public void setCurrentworkflow(LSsheetworkflow currentworkflow) {
		this.currentworkflow = currentworkflow;
	}
	
	
}
