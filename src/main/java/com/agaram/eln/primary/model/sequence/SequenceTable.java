package com.agaram.eln.primary.model.sequence;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sequencetable")
public class SequenceTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Integer sequencecode;
	private String screenname;
	
	private Integer resetperiod;
	private Integer sequenceview;
	private String sequenceformat;
	private String seperator;
	
	@OneToMany
	@JoinColumn(name="sequencecode")
	private List<SequenceTableSite> sequencetablesite;
	
	@OneToMany
	@JoinColumn(name="sequencecode")
	private List<SequenceTableProject> sequencetableproject;
	
	@OneToMany
	@JoinColumn(name="sequencecode")
	private List<SequenceTableTask> sequencesabletask;
	
	@OneToMany
	@JoinColumn(name="sequencecode")
	private List<SequenceTableOrderType> sequencetableordertype;
	
	private Long applicationsequence;
	
	private Integer sequenceday;
	private Integer sequencemonth;
	private Integer sequenceyear;
	
	public Integer getSequencecode() {
		return sequencecode;
	}
	public void setSequencecode(Integer sequencecode) {
		this.sequencecode = sequencecode;
	}
	public Integer getResetperiod() {
		return resetperiod;
	}
	public void setResetperiod(Integer resetperiod) {
		this.resetperiod = resetperiod;
	}
	public Integer getSequenceview() {
		return sequenceview;
	}
	public void setSequenceview(Integer sequenceview) {
		this.sequenceview = sequenceview;
	}
	public String getSequenceformat() {
		return sequenceformat;
	}
	public void setSequenceformat(String sequenceformat) {
		this.sequenceformat = sequenceformat;
	}
	public Long getApplicationsequence() {
		return applicationsequence;
	}
	public void setApplicationsequence(Long applicationsequence) {
		this.applicationsequence = applicationsequence;
	}
	
	public Integer getSequenceday() {
		return sequenceday;
	}
	public void setSequenceday(Integer sequenceday) {
		this.sequenceday = sequenceday;
	}
	public Integer getSequencemonth() {
		return sequencemonth;
	}
	public void setSequencemonth(Integer sequencemonth) {
		this.sequencemonth = sequencemonth;
	}
	public Integer getSequenceyear() {
		return sequenceyear;
	}
	public void setSequenceyear(Integer sequenceyear) {
		this.sequenceyear = sequenceyear;
	}
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	public List<SequenceTableSite> getSequencetablesite() {
		return sequencetablesite;
	}
	public void setSequencetablesite(List<SequenceTableSite> sequencetablesite) {
		this.sequencetablesite = sequencetablesite;
	}
	public List<SequenceTableProject> getSequencetableproject() {
		return sequencetableproject;
	}
	public void setSequencetableproject(List<SequenceTableProject> sequencetableproject) {
		this.sequencetableproject = sequencetableproject;
	}
	public List<SequenceTableTask> getSequencesabletask() {
		return sequencesabletask;
	}
	public void setSequencesabletask(List<SequenceTableTask> sequencesabletask) {
		this.sequencesabletask = sequencesabletask;
	}
	public List<SequenceTableOrderType> getSequencetableordertype() {
		return sequencetableordertype;
	}
	public void setSequencetableordertype(List<SequenceTableOrderType> sequencetableordertype) {
		this.sequencetableordertype = sequencetableordertype;
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	
}
