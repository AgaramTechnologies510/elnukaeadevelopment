package com.agaram.eln.primary.fetchmodel.getorders;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.agaram.eln.primary.model.instrumentDetails.LSOrdernotification;
import com.agaram.eln.primary.model.instrumentDetails.LsAutoregister;
import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.sheetManipulation.LSfile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplemaster;
import com.agaram.eln.primary.model.sheetManipulation.LStestmasterlocal;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public class Logilabordermaster implements Comparable<Logilabordermaster> {
	private Long batchcode;
	private String batchid;
	List<LSworkflow> lstworkflow;
	private Integer workflowcode;
	private boolean canuserprocess;
	private String testname;
	private String filename;
	private String projectname;
	private String samplename;
	private Integer filetype;
	private String orderflag;
	private Date createdtimestamp;
	private Date completedtimestamp;
	private LSworkflow lsworkflow;
	private String keyword;
	private Integer ordercancell;
	private Integer filecode;
	private LSuserMaster assignedto;
	private Integer viewoption;
	private LSuserMaster createdby;
	private Integer testcode;
	private Integer approvelstatus;
	private LSOrdernotification lsordernotification;
	private Integer ordersaved;
	private Boolean repeat;
	private LsAutoregister lsautoregisterorders;
	private Boolean sentforapprovel;
	private String approvelaccept;
	private Integer autoregistercount;
	private String materialname;
	private String sequenceid;
	private Boolean teamselected;
	private String tittle;
	private Integer app;
	private LSworkflow prevwfw;
	private Integer wau;

	public Logilabordermaster(Long batchcode, String batchid, LSworkflow lsworkflow, String testname, LSfile lsfile,
			LSsamplemaster lssamplemaster, LSprojectmaster lsprojectmaster, Integer filetype, String orderflag,
			LSuserMaster assignedto, Date createdtimestamp, Date completedtimestamp, String keyword,
			LStestmasterlocal lstestmasterlocal, Integer ordercancell, Integer viewoption, LSuserMaster lsuserMaster,
			Integer testcode, Integer approvelstatus, LSOrdernotification lsordernotification, Integer ordersaved,
			Boolean repeat, LsAutoregister lsautoregisterorders, Boolean sentforapprovel, String approvelaccept,
			Integer autoregistercount, Elnmaterial elnmaterial,Boolean teamselected,String sequenceid,String tittle,Integer approved,LSworkflow previousapprovedworkflow,Integer workflowapprovedusercode) {
		
		this.batchcode = batchcode;
		this.batchid = batchid;
		this.workflowcode = lsworkflow != null ? lsworkflow.getWorkflowcode() : null;
		this.testname = lstestmasterlocal != null ? lstestmasterlocal.getTestname() : testname;
		this.filename = lsfile != null ? lsfile.getFilenameuser() : null;
		this.projectname = lsprojectmaster != null ? lsprojectmaster.getProjectname() : null;
		this.samplename = lssamplemaster != null ? lssamplemaster.getSamplename() : null;
		this.filetype = filetype;
		this.filecode = lsfile != null ? lsfile.getFilecode() : -1;
		this.orderflag = orderflag;
		this.createdtimestamp = createdtimestamp;
		this.completedtimestamp = completedtimestamp;
		this.keyword = keyword;
		this.lsworkflow = lsworkflow != null
				? new LSworkflow(lsworkflow.getWorkflowcode(), lsworkflow.getWorkflowname(),lsworkflow.getSelfapproval())
				: null;
		this.ordercancell = ordercancell;
		this.assignedto = assignedto;
		this.testcode = testcode;
		this.viewoption = viewoption;
		this.createdby = lsuserMaster;
		this.approvelstatus = approvelstatus;
		this.lsordernotification = lsordernotification;
		this.ordersaved = ordersaved;
		this.repeat = repeat;
		this.lsautoregisterorders = lsautoregisterorders;
		this.sentforapprovel = sentforapprovel;
		this.approvelaccept = approvelaccept;
		this.autoregistercount = autoregistercount;
		this.materialname = elnmaterial != null ? elnmaterial.getSmaterialname() : null;
		this.sequenceid = sequenceid;
		this.teamselected = teamselected;
		this.tittle=tittle;
		this.app = approved;
		this.prevwfw= previousapprovedworkflow;
		this.wau =workflowapprovedusercode;
	}

	public LSworkflow getPrevwfw() {
		return prevwfw;
	}

	public void setPrevwfw(LSworkflow prevwfw) {
		this.prevwfw = prevwfw;
	}

	public Integer getWau() {
		return wau;
	}

	public void setWau(Integer wau) {
		this.wau = wau;
	}

	public Integer getApp() {
		return app;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	
	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public Boolean getSentforapprovel() {
		return sentforapprovel;
	}

	public void setSentforapprovel(Boolean sentforapprovel) {
		this.sentforapprovel = sentforapprovel;
	}

	public String getApprovelaccept() {
		return approvelaccept;
	}

	public void setApprovelaccept(String approvelaccept) {
		this.approvelaccept = approvelaccept;
	}

	public Integer getApprovelstatus() {
		return approvelstatus;
	}

	public void setApprovelstatus(Integer approvelstatus) {
		this.approvelstatus = approvelstatus;
	}

	public LSOrdernotification getLsordernotification() {
		return lsordernotification;
	}

	public void setLsordernotification(LSOrdernotification lsordernotification) {
		this.lsordernotification = lsordernotification;
	}

	public Boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}

	public LsAutoregister getLsautoregisterorders() {
		return lsautoregisterorders;
	}

	public void setLsautoregisterorders(LsAutoregister lsautoregisterorders) {
		this.lsautoregisterorders = lsautoregisterorders;
	}

	public LSuserMaster getCreatedby() {
		return createdby;
	}

	public void setCreatedby(LSuserMaster createdby) {
		this.createdby = createdby;
	}

	public Integer getViewoption() {
		return viewoption;
	}

	public void setViewoption(Integer viewoption) {
		this.viewoption = viewoption;
	}

	public Integer getTestcode() {
		return testcode;
	}

	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}

	public Integer getOrdercancell() {
		return ordercancell;
	}

	public void setOrdercancell(Integer ordercancell) {
		this.ordercancell = ordercancell;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
	}

	public String getBatchid() {

		if (this.batchid != null) {
			return this.batchid;
		} else {
			String Batchid = "ELN" + this.batchcode;

			if (this.filetype == 3) {
				Batchid = "RESEARCH" + this.batchcode;
			} else if (this.filetype == 4) {
				Batchid = "EXCEL" + this.batchcode;
			} else if (this.filetype == 5) {
				Batchid = "VALIDATE" + this.batchcode;
			} else if (this.filetype == 0) {
				Batchid = batchid;
			}
			return Batchid;
		}

	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public Integer getWorkflowcode() {
		return workflowcode;
	}

	public void setWorkflowcode(Integer workflowcode) {
		this.workflowcode = workflowcode;
	}

	public List<LSworkflow> getLstworkflow() {
		return lstworkflow;
	}

//	public void setLstworkflow(List<LSworkflow> lstworkflow) {
//		
//		if(lstworkflow != null && this.workflowcode !=null && lstworkflow.size() >0)
//		{
//			this.setCanuserprocess(lstworkflow.stream().map(LSworkflow::getWorkflowcode).anyMatch(this.workflowcode::equals));
////			if(lstworkflow.contains(this.workflowcode))
////			{
////				this.setCanuserprocess(true);
////			}
////			else
////			{
////				this.setCanuserprocess(false);
////			}
//		}
//		else
//		{
//			this.setCanuserprocess(false);
//		}
//		this.lstworkflow = null;
//	}
	
	
	public void setLstworkflow(List<LSworkflow> lstworkflow) {
		if (lstworkflow != null && this.workflowcode != null && lstworkflow.size() > 0) {
			List<Integer> lstworkflowcode = new ArrayList<Integer>();
			if (lstworkflow != null && lstworkflow.size() > 0) {
				lstworkflowcode = lstworkflow.stream().map(LSworkflow::getWorkflowcode).collect(Collectors.toList());

				if (lstworkflowcode.contains(this.workflowcode)) {
					this.setCanuserprocess(true);
				} else {
					this.setCanuserprocess(false);
				}
			} else {
				this.setCanuserprocess(false);
			}
		} else {
			this.setCanuserprocess(false);
		}
		this.lstworkflow = null;
	}
	
	
	
	public void setLstworkflow(List<LSworkflow> lstworkflow, List<LSworkflow> workflowobj, LSuserMaster user) {

//		if (lstworkflow != null && this.workflowcode != null && lstworkflow.size() > 0) {
//			// if(lstworkflow.contains(this.lsworkflow))
//
//			List<Integer> lstworkflowcode = new ArrayList<Integer>();
//			if (lstworkflow != null && lstworkflow.size() > 0) {
//				lstworkflowcode = lstworkflow.stream().map(LSworkflow::getWorkflowcode).collect(Collectors.toList());
//
//				if (lstworkflowcode.contains(this.workflowcode)) {
//					this.setCanuserprocess(true);
//				} else {
//					this.setCanuserprocess(false);
//				}
//			} else {
//				this.setCanuserprocess(false);
//			}
//		} else {
//			this.setCanuserprocess(false);
//		}
		
	    if (lstworkflow != null && this.workflowcode != null && !lstworkflow.isEmpty()) {
	        List<Integer> lstworkflowcode = lstworkflow.stream()
	                .map(LSworkflow::getWorkflowcode)
	                .collect(Collectors.toList());

	        if (this.workflowcode!=null && lstworkflowcode.contains(this.workflowcode)) {
	            int previousworkflowIndex = -1;
	            if (this.getPrevwfw() != null) {
	                previousworkflowIndex = IntStream.range(0, workflowobj.size())
	                        .filter(i -> workflowobj.get(i).getWorkflowcode() == this.getPrevwfw().getWorkflowcode())
	                        .findFirst()
	                        .orElse(-1);
	            }
	            int currentworkflowIndex = -1;
	            if (this.getWorkflowcode() != null) {
	                currentworkflowIndex = IntStream.range(0, workflowobj.size())
	                        .filter(i -> workflowobj.get(i).getWorkflowcode() == this.getWorkflowcode())
	                        .findFirst()
	                        .orElse(-1);
	            }
	            boolean selfapproval = false;
	            if (this.getLsworkflow() != null && Boolean.TRUE.equals(this.getLsworkflow().getSelfapproval())) {
	                selfapproval = true;
	            } else if (this.getLsworkflow() != null && Boolean.FALSE.equals(this.getLsworkflow().getSelfapproval())) {
	                Integer wau = this.getWau();
	                int usercode = user != null ? user.getUsercode() : -1;

	                if (((wau != null && wau == usercode && (this.app !=null && this.app ==2 ? previousworkflowIndex - 1 != currentworkflowIndex: previousworkflowIndex + 1 != currentworkflowIndex))
	                        || (wau != null && wau != usercode))
	                        || (previousworkflowIndex == -1 || wau == null)) {
	                    selfapproval = true;
	                }
	            }
	            this.setCanuserprocess(selfapproval);
	        } else {
	            this.setCanuserprocess(false);
	        }
	    } else {
	        this.setCanuserprocess(false);
	    }
		this.lstworkflow = null;
	}

	public Date getCompletedtimestamp() {
		return completedtimestamp;
	}

	public void setCompletedtimestamp(Date completedtimestamp) {
		this.completedtimestamp = completedtimestamp;
	}

	public boolean isCanuserprocess() {
		return canuserprocess;
	}

	public void setCanuserprocess(boolean canuserprocess) {
		this.canuserprocess = canuserprocess;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getSamplename() {
		return samplename;
	}

	public void setSamplename(String samplename) {
		this.samplename = samplename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getFiletype() {
		return filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	public Integer getFilecode() {
		return filecode;
	}

	public void setFilecode(Integer filecode) {
		this.filecode = filecode;
	}

	public String getOrderflag() {
		return orderflag;
	}

	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}

	public Date getCreatedtimestamp() {
		return createdtimestamp;
	}

	public void setCreatedtimestamp(Date createdtimestamp) {
		this.createdtimestamp = createdtimestamp;
	}

	@Override
	public int compareTo(Logilabordermaster o) {
		return this.getBatchcode().compareTo(o.getBatchcode());
	}

	public LSworkflow getLsworkflow() {
		return lsworkflow;
	}

	public void setLsworkflow(LSworkflow lsworkflow) {
		this.lsworkflow = lsworkflow;
	}

	public LSuserMaster getAssignedto() {
		return assignedto;
	}

	public void setAssignedto(LSuserMaster assignedto) {
		this.assignedto = assignedto;
	}

	public Integer getOrdersaved() {
		return ordersaved;
	}

	public void setOrdersaved(Integer ordersaved) {
		this.ordersaved = ordersaved;
	}

	public Integer getAutoregistercount() {
		return autoregistercount;
	}

	public void setAutoregistercount(Integer autoregistercount) {
		this.autoregistercount = autoregistercount;
	}

	public String getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(String sequenceid) {
		this.sequenceid = sequenceid;
	}

	public Boolean getTeamselected() {
		return teamselected;
	}

	public void setTeamselected(Boolean teamselected) {
		this.teamselected = teamselected;
	}

}
