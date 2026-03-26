package com.agaram.eln.primary.model.sheetManipulation;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "LSworkflow")
public class LSworkflow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "workflowcode") 
	private int workflowcode;

	@Column(columnDefinition = "varchar(120)")
	private String workflowname;
	
	@OneToMany
	@JoinColumn(name="workflowcode")
	private List<LSworkflowgroupmapping> lsworkflowgroupmapping;
	
	@Transient
	private LScfttransaction objsilentaudit;
	
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	private Response response;
	
	private Boolean selfapproval = false;


	public Boolean getSelfapproval() {
		return selfapproval;
	}

	public void setSelfapproval(Boolean selfapproval) {
		this.selfapproval = selfapproval;
	}
	
	public  LSworkflow() {};
	
	public  LSworkflow(int workflowcode,String workflowname) {
		this.workflowcode=workflowcode;
		this.workflowname=workflowname;
	};
	
	public  LSworkflow(int workflowcode,String workflowname,Boolean selfapproval) {
		this.workflowcode=workflowcode;
		this.workflowname=workflowname;
		this.selfapproval = selfapproval;
	};
	
	
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

	public LSuserMaster getLSuserMaster() {
		return LSuserMaster;
	}

	public void setLSuserMaster(LSuserMaster lSuserMaster) {
		LSuserMaster = lSuserMaster;
	}

	@Transient
	LoggedUser objuser;
	
	@Transient
	LSuserMaster LSuserMaster;
	
	
	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public int getWorkflowcode() {
		return workflowcode;
	}

	public void setWorkflowcode(int workflowcode) {
		this.workflowcode = workflowcode;
	}

	public String getWorkflowname() {
		return workflowname;
	}

	public void setWorkflowname(String workflowname) {
		this.workflowname = workflowname;
	}

	public List<LSworkflowgroupmapping> getLsworkflowgroupmapping() {
		return lsworkflowgroupmapping;
	}

	public void setLsworkflowgroupmapping(List<LSworkflowgroupmapping> lsworkflowgroupmapping) {
		this.lsworkflowgroupmapping = lsworkflowgroupmapping;
	}
	@ManyToOne 
	private LSSiteMaster lssitemaster;
	@Transient
	private LScfttransaction objmanualaudit;
	public LSSiteMaster getLssitemaster() {
		return lssitemaster;
	}

	public void setLssitemaster(LSSiteMaster lssitemaster) {
		this.lssitemaster = lssitemaster;
	}

	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}

	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}
	
	
}
