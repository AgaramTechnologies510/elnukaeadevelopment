package com.agaram.eln.primary.model.sequence;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SequenceTableProjectLevel")
public class SequenceTableProjectLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Integer sequencecodeprojectlevel;
	private Long projectsequence;
	private Integer projectcode;
	
	public Integer getSequencecodeprojectlevel() {
		return sequencecodeprojectlevel;
	}
	public void setSequencecodeprojectlevel(Integer sequencecodeprojectlevel) {
		this.sequencecodeprojectlevel = sequencecodeprojectlevel;
	}
	public Long getProjectsequence() {
		return projectsequence;
	}
	public void setProjectsequence(Long projectsequence) {
		this.projectsequence = projectsequence;
	}
	public Integer getProjectcode() {
		return projectcode;
	}
	public void setProjectcode(Integer projectcode) {
		this.projectcode = projectcode;
	}
	
	
}
