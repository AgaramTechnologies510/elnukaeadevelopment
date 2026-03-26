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
import com.agaram.eln.primary.model.sheetManipulation.LSsheetworkflow;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity(name = "LSprotocolworkflowhistory")
@Table(name = "LSprotocolworkflowhistory")
public class LSprotocolworkflowhistory {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolworkflowhistory_seq")
	@SequenceGenerator(name = "lsprotocolworkflowhistory_seq", sequenceName = "lsprotocolworkflowhistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "historycode")
	private Integer historycode;
	
	@Column(name = "approvelstatus") 
	private Integer approvelstatus;
	
	@Column(name = "protocolmastercode")
	private Integer protocolmastercode;
	
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
	private LSprotocolworkflow currentworkflow;
	
	@ManyToOne
	private LSsheetworkflow lssheetworkflow;
	
	
	@ManyToOne
	private ElnprotocolTemplateworkflow elnprotocoltemplateworkflow;
	
	@ManyToOne
	private ElnprotocolTemplateworkflow previousprotocoltemplateworkflowcode;
	
	private String templatename;
	
	private Integer templateversionNumber;
	

	public ElnprotocolTemplateworkflow getPreviousprotocoltemplateworkflowcode() {
		return previousprotocoltemplateworkflowcode;
	}

	public void setPreviousprotocoltemplateworkflowcode(ElnprotocolTemplateworkflow previousprotocoltemplateworkflowcode) {
		this.previousprotocoltemplateworkflowcode = previousprotocoltemplateworkflowcode;
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

	public ElnprotocolTemplateworkflow getElnprotocoltemplateworkflow() {
		return elnprotocoltemplateworkflow;
	}

	public void setElnprotocoltemplateworkflow(ElnprotocolTemplateworkflow elnprotocoltemplateworkflow) {
		this.elnprotocoltemplateworkflow = elnprotocoltemplateworkflow;
	}

	public LSsheetworkflow getLssheetworkflow() {
		return lssheetworkflow;
	}

	public void setLssheetworkflow(LSsheetworkflow lssheetworkflow) {
		this.lssheetworkflow = lssheetworkflow;
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

	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}

	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}

	public LSprotocolworkflow getCurrentworkflow() {
		return currentworkflow;
	}

	public void setCurrentworkflow(LSprotocolworkflow currentworkflow) {
		this.currentworkflow = currentworkflow;
	}

	
}
