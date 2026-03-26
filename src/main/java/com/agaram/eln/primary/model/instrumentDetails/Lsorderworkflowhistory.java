package com.agaram.eln.primary.model.instrumentDetails;

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

@Entity(name = "Lsorderworkflowhistory")
@Table(name = "Lsorderworkflowhistory")
public class Lsorderworkflowhistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsorderworkflowhistory_seq")
	@SequenceGenerator(name = "lsorderworkflowhistory_seq", sequenceName = "lsorderworkflowhistory_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "historycode")
	private Integer historycode;
	
	@Column(name = "approvelstatus") 
	private Integer approvelstatus;
	
	@Column(columnDefinition = "numeric(17,0)",name = "batchcode") 
	private Long batchcode;
	
	@Column(columnDefinition = "varchar(250)")
	private String action;
	
	@Column(columnDefinition = "varchar(250)")
	private String comment;
	
	@ManyToOne 
	private LSuserMaster createby;
	
//	@Column(columnDefinition = "datetime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	
	@ManyToOne
	private LSworkflow currentworkflow;
	
	@Transient
	LScfttransaction objsilentaudit;

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public Integer getHistorycode() {
		return historycode;
	}

	public void setHistorycode(Integer historycode) {
		this.historycode = historycode;
	}

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
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

	public Integer getApprovelstatus() {
		return approvelstatus;
	}

	public void setApprovelstatus(Integer approvelstatus) {
		this.approvelstatus = approvelstatus;
	}

	public LSworkflow getCurrentworkflow() {
		return currentworkflow;
	}

	public void setCurrentworkflow(LSworkflow currentworkflow) {
		this.currentworkflow = currentworkflow;
	}
	
	
}
