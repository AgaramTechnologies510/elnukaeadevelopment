package com.agaram.eln.primary.model.sheetManipulation;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.agaram.eln.primary.model.usermanagement.LSusergroup;

@Entity
@Table(name = "LSsheetworkflowgroupmap")
public class LSsheetworkflowgroupmap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lssheetworkflowgroupmap_seq")
	@SequenceGenerator(name = "lssheetworkflowgroupmap_seq", sequenceName = "lssheetworkflowgroupmap_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "workflowmapid")
	private Integer workflowmapid;

	private Integer workflowcode;
	
	@ManyToOne 
	private LSusergroup lsusergroup;
	public Integer getWorkflowmapid() {
		return workflowmapid;
	}
	public void setWorkflowmapid(Integer workflowmapid) {
		this.workflowmapid = workflowmapid;
	}
	public Integer getWorkflowcode() {
		return workflowcode;
	}
	public void setWorkflowcode(Integer workflowcode) {
		this.workflowcode = workflowcode;
	}
	public LSusergroup getLsusergroup() {
		return lsusergroup;
	}
	public void setLsusergroup(LSusergroup lsusergroup) {
		this.lsusergroup = lsusergroup;
	}
	

}
