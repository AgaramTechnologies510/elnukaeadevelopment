package com.agaram.eln.primary.model.sequence;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sequencetabletask")
public class SequenceTableTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Integer sequencecodetask;
	private Integer sequencecode;
	private Long tasksequence;
	private Integer testcode;
	
	public Integer getSequencecodetask() {
		return sequencecodetask;
	}
	public void setSequencecodetask(Integer sequencecodetask) {
		this.sequencecodetask = sequencecodetask;
	}
	public Integer getSequencecode() {
		return sequencecode;
	}
	public void setSequencecode(Integer sequencecode) {
		this.sequencecode = sequencecode;
	}
	public Long getTasksequence() {
		return tasksequence;
	}
	public void setTasksequence(Long tasksequence) {
		this.tasksequence = tasksequence;
	}
	public Integer getTestcode() {
		return testcode;
	}
	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}
	
	
}
