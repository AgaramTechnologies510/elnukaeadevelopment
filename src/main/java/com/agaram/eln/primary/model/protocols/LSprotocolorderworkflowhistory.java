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
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity(name = "LSprotocolorderworkflowhistory")
@Table(name = "LSprotocolorderworkflowhistory")
public class LSprotocolorderworkflowhistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolorderworkflowhistory_seq")
	@SequenceGenerator(name = "lsprotocolorderworkflowhistory_seq", sequenceName = "lsprotocolorderworkflowhistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "historycode")
	private Integer historycode;
	
	@Column(name = "approvelstatus") 
	private Integer approvelstatus;
	
	@Column(name = "protocolordercode")
	private Integer protocolordercode;
	
	@Column(columnDefinition = "varchar(250)")
	private String action;
	
	@Column(columnDefinition = "varchar(250)")
	private String comment;
	
	
	@ManyToOne 
	private LSuserMaster createby;
	
	//@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	
	@ManyToOne
	private LSprotocolworkflow currentworkflow;
	
	@ManyToOne
	private LSworkflow lsworkflow;
	
	@ManyToOne
	private Elnprotocolworkflow elnprotocolworkflow;

	
	public Elnprotocolworkflow getElnprotocolworkflow() {
		return elnprotocolworkflow;
	}


	public void setElnprotocolworkflow(Elnprotocolworkflow elnprotocolworkflow) {
		this.elnprotocolworkflow = elnprotocolworkflow;
	}


	public LSworkflow getLsworkflow() {
		return lsworkflow;
	}


	public void setLsworkflow(LSworkflow lsworkflow) {
		this.lsworkflow = lsworkflow;
	}


	@Transient
	LScfttransaction objsilentaudit;


	public Integer getHistorycode() {
		return historycode;
	}


	public Integer getApprovelstatus() {
		return approvelstatus;
	}


	public Integer getProtocolordercode() {
		return protocolordercode;
	}


	public String getAction() {
		return action;
	}


	public String getComment() {
		return comment;
	}


	public LSuserMaster getCreateby() {
		return createby;
	}


	public Date getCreatedate() {
		return createdate;
	}


	public LSprotocolworkflow getCurrentworkflow() {
		return currentworkflow;
	}


	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}


	public void setHistorycode(Integer historycode) {
		this.historycode = historycode;
	}


	public void setApprovelstatus(Integer approvelstatus) {
		this.approvelstatus = approvelstatus;
	}


	public void setProtocolordercode(Integer protocolordercode) {
		this.protocolordercode = protocolordercode;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}


	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}


	public void setCurrentworkflow(LSprotocolworkflow currentworkflow) {
		this.currentworkflow = currentworkflow;
	}


	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}
	
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	private Date fromdate; 
	
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	private Date todate;

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	} 
}
