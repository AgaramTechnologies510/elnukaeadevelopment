package com.agaram.eln.primary.model.sheetManipulation;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity(name = "Lsfilesharedby")
@Table(name = "Lsfilesharedby")
public class Lsfilesharedby {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsfilesharedby_seq")
	@SequenceGenerator(name = "lsfilesharedby_seq", sequenceName = "lsfilesharedby_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "sharedbytofilecode")
	private Long sharedbytofilecode;

	@Column(name = "sharetofilecode")
	private Long sharetofilecode;

	@Column(columnDefinition = "varchar(250)")
	private String sharebyunifiedid;

	@Column(columnDefinition = "varchar(250)")
	private String sharetounifiedid;

	@Column(columnDefinition = "varchar(250)")
	private String sharebyusername;

	@Column(columnDefinition = "varchar(250)")
	private String sharetousername;

	private Long sharefilecode;

	@Column(columnDefinition = "varchar(250)")
	private String sharefilename;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private String shareitemdetails;

	private Date sharedon;

	private Date unsharedon;

	private Date sharemodifiedon;

	private int sharerights = 0;

	private int sharestatus = 0;

	@Transient
	LSuserMaster objLoggeduser;
	
	private Integer sitecode;

	public Integer getSitecode() {
		return sitecode;
	}

	public void setSitecode(Integer sitecode) {
		this.sitecode = sitecode;
	}
	@Transient
	LScfttransaction objsilentaudit;

	@Transient
	LScfttransaction objmanualaudit;

	@Transient
	private Integer ismultitenant;
	
	@Transient
	private LSfile objfile;

	public String getSharebyunifiedid() {
		return sharebyunifiedid;
	}

	public String getSharetounifiedid() {
		return sharetounifiedid;
	}

	public String getSharebyusername() {
		return sharebyusername;
	}

	public String getSharetousername() {
		return sharetousername;
	}

	public String getShareitemdetails() {
		return shareitemdetails;
	}

	public Date getSharedon() {
		return sharedon;
	}

	public Date getUnsharedon() {
		return unsharedon;
	}

	public Date getSharemodifiedon() {
		return sharemodifiedon;
	}

	public int getSharerights() {
		return sharerights;
	}

	public int getSharestatus() {
		return sharestatus;
	}

	public LSuserMaster getObjLoggeduser() {
		return objLoggeduser;
	}

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}

	public Integer getIsmultitenant() {
		return ismultitenant;
	}

	public void setSharebyunifiedid(String sharebyunifiedid) {
		this.sharebyunifiedid = sharebyunifiedid;
	}

	public void setSharetounifiedid(String sharetounifiedid) {
		this.sharetounifiedid = sharetounifiedid;
	}

	public void setSharebyusername(String sharebyusername) {
		this.sharebyusername = sharebyusername;
	}

	public void setSharetousername(String sharetousername) {
		this.sharetousername = sharetousername;
	}

	public void setShareitemdetails(String shareitemdetails) {
		this.shareitemdetails = shareitemdetails;
	}

	public void setSharedon(Date sharedon) {
		this.sharedon = sharedon;
	}

	public void setUnsharedon(Date unsharedon) {
		this.unsharedon = unsharedon;
	}

	public void setSharemodifiedon(Date sharemodifiedon) {
		this.sharemodifiedon = sharemodifiedon;
	}

	public void setSharerights(int sharerights) {
		this.sharerights = sharerights;
	}

	public void setSharestatus(int sharestatus) {
		this.sharestatus = sharestatus;
	}

	public void setObjLoggeduser(LSuserMaster objLoggeduser) {
		this.objLoggeduser = objLoggeduser;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}

	public void setIsmultitenant(Integer ismultitenant) {
		this.ismultitenant = ismultitenant;
	}

	public Long getSharedbytofilecode() {
		return sharedbytofilecode;
	}

	public void setSharedbytofilecode(Long sharedbytofilecode) {
		this.sharedbytofilecode = sharedbytofilecode;
	}

	public Long getSharetofilecode() {
		return sharetofilecode;
	}

	public void setSharetofilecode(Long sharetofilecode) {
		this.sharetofilecode = sharetofilecode;
	}

	public Long getSharefilecode() {
		return sharefilecode;
	}

	public void setSharefilecode(Long sharefilecode) {
		this.sharefilecode = sharefilecode;
	}

	public String getSharefilename() {
		return sharefilename;
	}

	public void setSharefilename(String sharefilename) {
		this.sharefilename = sharefilename;
	}

	public LSfile getObjfile() {
		return objfile;
	}

	public void setObjfile(LSfile objfile) {
		this.objfile = objfile;
	}
	private Integer retirestatus;

	public Integer getRetirestatus() {
		return retirestatus;
	}

	public void setRetirestatus(Integer retirestatus) {
		this.retirestatus = retirestatus;
	}
}