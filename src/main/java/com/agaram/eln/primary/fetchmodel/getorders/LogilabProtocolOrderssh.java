package com.agaram.eln.primary.fetchmodel.getorders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.agaram.eln.primary.model.instrumentDetails.LSOrdernotification;
import com.agaram.eln.primary.model.instrumentDetails.LsAutoregister;
import com.agaram.eln.primary.model.masters.Lsrepositories;
import com.agaram.eln.primary.model.masters.Lsrepositoriesdata;
import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.material.ElnmaterialInventory;
import com.agaram.eln.primary.model.protocols.Elnprotocolworkflow;
import com.agaram.eln.primary.model.protocols.LSprotocolmaster;
import com.agaram.eln.primary.model.protocols.LSprotocolworkflow;
import com.agaram.eln.primary.model.sample.Sample;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplemaster;
import com.agaram.eln.primary.model.sheetManipulation.LStestmasterlocal;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public class LogilabProtocolOrderssh implements Comparable<LogilabProtocolOrderssh> {

	private Long pc;
	private String pn;

	private String of;
	private Integer pt;

	private Date ct;
	private Date cot;

	private String sn;
	private LSprotocolmaster lpm;
	private String pjn;
	private String pmn;
	private LSprojectmaster lpjm;

	private String kw;
	private Integer tc;
	private String tn;
	private Long dc;
	private Integer cb;

	private String rin;
	private LSuserMaster at;
	private String rn;
	private Integer a;
	private Integer r;
	private Integer vo;

	private String mn;
	private String min;
	private LSprotocolworkflow lpw;
	private Integer wc;
	private boolean cp;
	private Sample sam;
	private Integer oc;
	private Integer os;
	private LSuserMaster osb;
	private Date oso;
	private Integer lu;
	private String lun;
	private Integer vn;
	List<Elnprotocolworkflow> lsepw;
	private Elnprotocolworkflow lepw;
	private LSOrdernotification lon;
	private LsAutoregister lar;
	private Boolean re;
	private Boolean sfa;
	private String aa;
	private Integer arc;
	private LSuserMaster lum;
	private Long ase;
	private Long sse;
	private Long pse;
	private Long tse;
	private Long ose;
	private String sid;
	private Date modifieddate;
	private String modifiedby;
	private String tle;
	private boolean isclone = false;
	private Integer tvno;
	private Elnprotocolworkflow prevwfw;
	private Integer wau;
	
	public LogilabProtocolOrderssh(Long protocolordercode, Integer Testcode, String protoclordername, String orderflag,
			Integer protocoltype, Date createdtimestamp, Date completedtimestamp, LSprotocolmaster lsprotocolmaster,
			LSprotocolworkflow lSprotocolworkflow, Sample sample, LSprojectmaster lsprojectmaster,
			String keyword, Long directorycode, Integer createby, LSuserMaster assignedto,
			Lsrepositoriesdata lsrepositoriesdata, Lsrepositories lsrepositories,
			Elnmaterial elnmaterial,ElnmaterialInventory elnmaterialinventory, Integer approved, Integer rejected,
			Integer ordercancell, Integer viewoption, Integer orderstarted, LSuserMaster orderstartedby,
			Date orderstartedon,Integer lockeduser,String lockedusername, Integer versionno,Elnprotocolworkflow elnprotocolworkflow,
			LSOrdernotification lsordernotification,LsAutoregister lsautoregister,Boolean repeat,
			Boolean sentforapprovel,String approvelaccept,Integer autoregistercount, LSuserMaster lsuserMaster, LStestmasterlocal lstestmasterlocal, 
			Long applicationsequence, Long sitesequence, Long projectsequence, Long tasksequence, Long ordertypesequence,String sequenceid,String modifiedby,Date modifieddate,String tittle,boolean isclone
			,Integer templateversionno,Elnprotocolworkflow previousapprovedworkflow,Integer workflowapprovedusercode) {

		this.pc = protocolordercode;
		this.tc = Testcode;
		this.pn = protoclordername;
		this.of = orderflag;
		this.wc = elnprotocolworkflow != null ? elnprotocolworkflow.getWorkflowcode() : null;
		this.pt = protocoltype;
		this.ct = createdtimestamp;
		this.cot = completedtimestamp;
		this.pmn = lsprotocolmaster != null ? lsprotocolmaster.getProtocolmastername() : "";
		this.sn = sample != null ? sample.getSamplename() : "";
		this.pjn = lsprojectmaster != null ? lsprojectmaster.getProjectname() : "";
		this.tn = lstestmasterlocal != null ? lstestmasterlocal.getTestname() : "";
		this.kw = keyword;
		this.dc = directorycode;
		this.lpjm = lsprojectmaster;
		this.cb = createby;
		this.at = assignedto;
		this.sam = sample != null ? sample : null;
		this.rin = lsrepositoriesdata != null ? lsrepositoriesdata.getRepositoryitemname() : null;
		this.rn = lsrepositories != null ? lsrepositories.getRepositoryname() : null;
		this.mn = elnmaterial != null ? elnmaterial.getSmaterialname() : null;
		this.min = elnmaterialinventory != null ? elnmaterialinventory.getSinventoryid() : null;
		this.dc = directorycode;
		this.lpm = lsprotocolmaster;
		this.a = approved;
		this.r = rejected;
		this.oc = ordercancell;
		this.vo = viewoption;
		this.os = orderstarted != null && orderstarted == 1 ? orderstarted : 0;
		this.osb = orderstartedby != null ? orderstartedby : null;
		this.oso = orderstartedon != null ? orderstartedon : null;
		this.lu=lockeduser!=null?lockeduser:null;
		this.lun=lockedusername!=null?lockedusername:null;
		this.vn = versionno;
		this.lepw=elnprotocolworkflow;
        this.lon=lsordernotification != null ? lsordernotification :null;
        this.lar=lsautoregister != null ? lsautoregister :null;
        this.re = repeat;
		this.sfa=sentforapprovel;
		this.aa=approvelaccept;
		this.arc=autoregistercount;
		this.lum = lsuserMaster!=null? new LSuserMaster(lsuserMaster.getUsercode(),lsuserMaster.getUsername(),lsuserMaster.getLssitemaster()):null;
		this.ase = applicationsequence;
		this.sse = sitesequence;
		this.pse = projectsequence;
		this.tse = tasksequence;
		this.ose = ordertypesequence;
		this.sid = sequenceid;
		this.modifieddate=modifieddate;
		this.modifiedby = modifiedby;
		this.tle=tittle;
		this.isclone = isclone;
		this.tvno = templateversionno != null ? templateversionno : null;
		this.prevwfw= previousapprovedworkflow;
		this.wau =workflowapprovedusercode;
	}
	
	public Elnprotocolworkflow getPrevwfw() {
		return prevwfw;
	}

	public void setPrevwfw(Elnprotocolworkflow prevwfw) {
		this.prevwfw = prevwfw;
	}

	public Integer getWau() {
		return wau;
	}

	public void setWau(Integer wau) {
		this.wau = wau;
	}

	public Integer getTvno() {
		return tvno;
	}

	public void setTvno(Integer tvno) {
		this.tvno = tvno;
	}

	public boolean isIsclone() {
		return isclone;
	}

	public void setIsclone(boolean isclone) {
		this.isclone = isclone;
	}

	public String getTle() {
		return tle;
	}

	public void setTle(String tle) {
		this.tle = tle;
	}

	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public Date getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	@Override
	public int compareTo(LogilabProtocolOrderssh o) {
		return this.getPc().compareTo(o.getPc());
	}
	
	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public Long getPc() {
		return pc;
	}

	public void setPc(Long pc) {
		this.pc = pc;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getOf() {
		return of;
	}

	public void setOf(String of) {
		this.of = of;
	}

	public Integer getPt() {
		return pt;
	}

	public void setPt(Integer pt) {
		this.pt = pt;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}

	public Date getCot() {
		return cot;
	}

	public void setCot(Date cot) {
		this.cot = cot;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public LSprotocolmaster getLpm() {
		return lpm;
	}

	public void setLpm(LSprotocolmaster lpm) {
		this.lpm = lpm;
	}

	public String getPjn() {
		return pjn;
	}

	public void setPjn(String pjn) {
		this.pjn = pjn;
	}

	public String getPmn() {
		return pmn;
	}

	public void setPmn(String pmn) {
		this.pmn = pmn;
	}

	public LSprojectmaster getLpjm() {
		return lpjm;
	}

	public void setLpjm(LSprojectmaster lpjm) {
		this.lpjm = lpjm;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public Integer getTc() {
		return tc;
	}

	public void setTc(Integer tc) {
		this.tc = tc;
	}

	public Long getDc() {
		return dc;
	}

	public void setDc(Long dc) {
		this.dc = dc;
	}

	public Integer getCb() {
		return cb;
	}

	public void setCb(Integer cb) {
		this.cb = cb;
	}

	public String getRin() {
		return rin;
	}

	public void setRin(String rin) {
		this.rin = rin;
	}

	public LSuserMaster getAt() {
		return at;
	}

	public void setAt(LSuserMaster at) {
		this.at = at;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getVo() {
		return vo;
	}

	public void setVo(Integer vo) {
		this.vo = vo;
	}

	public String getMn() {
		return mn;
	}

	public void setMn(String mn) {
		this.mn = mn;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public LSprotocolworkflow getLpw() {
		return lpw;
	}

	public void setLpw(LSprotocolworkflow lpw) {
		this.lpw = lpw;
	}

	public Integer getWc() {
		return wc;
	}

	public void setWc(Integer wc) {
		this.wc = wc;
	}

	public boolean isCp() {
		return cp;
	}

	public void setCp(boolean cp) {
		this.cp = cp;
	}

	public Sample getSam() {
		return sam;
	}

	public void setSam(Sample sam) {
		this.sam = sam;
	}

	public Integer getOc() {
		return oc;
	}

	public void setOc(Integer oc) {
		this.oc = oc;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public LSuserMaster getOsb() {
		return osb;
	}

	public void setOsb(LSuserMaster osb) {
		this.osb = osb;
	}

	public Date getOso() {
		return oso;
	}

	public void setOso(Date oso) {
		this.oso = oso;
	}

	public Integer getLu() {
		return lu;
	}

	public void setLu(Integer lu) {
		this.lu = lu;
	}

	public String getLun() {
		return lun;
	}

	public void setLun(String lun) {
		this.lun = lun;
	}

	public Integer getVn() {
		return vn;
	}

	public void setVn(Integer vn) {
		this.vn = vn;
	}

	public List<Elnprotocolworkflow> getLsepw() {
		return lsepw;
	}

	public void setLsepw(List<Elnprotocolworkflow> lstworkflow, List<Elnprotocolworkflow> workflowobj, LSuserMaster user) {
//		if (lsepw != null && this.wc != null && lsepw.size() > 0) {
//
//			List<Integer> lstworkflowcode = new ArrayList<Integer>();
//			if (lsepw != null && lsepw.size() > 0) {
//				lstworkflowcode = lsepw.stream().map(Elnprotocolworkflow::getWorkflowcode).collect(Collectors.toList());
//
//				if (lstworkflowcode.contains(this.wc)) {
//					this.setCp(true);
//				} else {
//					this.setCp(false);
//				}
//			} else {
//				this.setCp(false);
//			}
//		} else {
//			this.setCp(false);
//		}
		
		
	    if (lstworkflow != null && this.wc != null && !lstworkflow.isEmpty()) {
	        List<Integer> lstworkflowcode = lstworkflow.stream()
	                .map(Elnprotocolworkflow::getWorkflowcode)
	                .collect(Collectors.toList());

	        if (this.wc != null && lstworkflowcode.contains(this.wc)) {
	            int previousworkflowIndex = -1;
	            if (this.getPrevwfw() != null) {
	                previousworkflowIndex = IntStream.range(0, workflowobj.size())
	                        .filter(i -> workflowobj.get(i).getWorkflowcode() == this.getPrevwfw().getWorkflowcode())
	                        .findFirst()
	                        .orElse(-1);
	            }
	            int currentworkflowIndex = -1;
	            if (this.getWc() != null) {
	                currentworkflowIndex = IntStream.range(0, workflowobj.size())
	                        .filter(i -> workflowobj.get(i).getWorkflowcode() == this.getWc())
	                        .findFirst()
	                        .orElse(-1);
	            }
	            boolean selfapproval = false;
	            if (this.getLepw() != null && Boolean.TRUE.equals(this.getLepw().getSelfapproval())) {
	                selfapproval = true;
	            } else if (this.getLepw() != null && Boolean.FALSE.equals(this.getLepw().getSelfapproval())) {
	                Integer wau = this.getWau();
	                int usercode = user != null ? user.getUsercode() : -1;

	                if (((wau != null && wau == usercode && (this.a !=null &&  this.a ==2 ? previousworkflowIndex - 1 != currentworkflowIndex: previousworkflowIndex + 1 != currentworkflowIndex))
	                        || (wau != null && wau != usercode))
	                        || (previousworkflowIndex == -1 || wau == null)) {
	                    selfapproval = true;
	                }
	            }
	            this.setCp(selfapproval);
	        } else {
	            this.setCp(false);
	        }
	    } else {
	        this.setCp(false);
	    }
		this.lsepw = null;
	}

	public Elnprotocolworkflow getLepw() {
		return lepw;
	}

	public void setLepw(Elnprotocolworkflow lepw) {
		this.lepw = lepw;
	}

	public LSOrdernotification getLon() {
		return lon;
	}

	public void setLon(LSOrdernotification lon) {
		this.lon = lon;
	}

	public LsAutoregister getLar() {
		return lar;
	}

	public void setLar(LsAutoregister lar) {
		this.lar = lar;
	}

	public Boolean getRe() {
		return re;
	}

	public void setRe(Boolean re) {
		this.re = re;
	}

	public Boolean getSfa() {
		return sfa;
	}

	public void setSfa(Boolean sfa) {
		this.sfa = sfa;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public Integer getArc() {
		return arc;
	}

	public void setArc(Integer arc) {
		this.arc = arc;
	}

	public LSuserMaster getLum() {
		return lum;
	}

	public void setLum(LSuserMaster lum) {
		this.lum = lum;
	}

	public Long getAse() {
		return ase;
	}

	public void setAse(Long ase) {
		this.ase = ase;
	}

	public Long getSse() {
		return sse;
	}

	public void setSse(Long sse) {
		this.sse = sse;
	}

	public Long getPse() {
		return pse;
	}

	public void setPse(Long pse) {
		this.pse = pse;
	}

	public Long getTse() {
		return tse;
	}

	public void setTse(Long tse) {
		this.tse = tse;
	}

	public Long getOse() {
		return ose;
	}

	public void setOse(Long ose) {
		this.ose = ose;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	
}
