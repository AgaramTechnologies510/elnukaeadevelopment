package com.agaram.eln.primary.model.protocols;

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
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@SuppressWarnings("unused")
@Entity(name = "Lsprotocolshareto")
@Table(name = "Lsprotocolshareto")
public class Lsprotocolshareto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolshareto_sharetoprotocolcode_seq")
	@SequenceGenerator(name = "lsprotocolshareto_sharetoprotocolcode_seq", sequenceName = "lsprotocolshareto_sharetoprotocolcode_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "sharetoprotocolcode")
	private Long sharetoprotocolcode;

	@Column(columnDefinition = "varchar(250)")
	private String sharebyunifiedid;
	@Column(columnDefinition = "varchar(250)")
	private String sharetounifiedid;

	@Column(columnDefinition = "varchar(250)")
	private String sharebyusername;

	@Column(columnDefinition = "varchar(250)")
	private String sharetousername;

	private Long shareprotocolcode;

	@Column(columnDefinition = "varchar(250)")
	private String shareprotocolname;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private String shareitemdetails;

	private Date sharedon;

	private Date unsharedon;
	private int sharerights = 0;

	private int sharestatus = 0;

	@Transient
	LSuserMaster objLoggeduser;

	@Transient
	LScfttransaction objsilentaudit;

	@Transient
	LScfttransaction objmanualaudit;
	
	@Transient
	LSuserMaster sharetousercode;
	
	@Transient
	private Response response;
	
	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	private Integer sitecode;

	public Integer getSitecode() {
		return sitecode;
	}

	public void setSitecode(Integer sitecode) {
		this.sitecode = sitecode;
	}

	public LSuserMaster getSharetousercode() {
		return sharetousercode;
	}

	public void setSharetousercode(LSuserMaster sharetousercode) {
		this.sharetousercode = sharetousercode;
	}

	@Transient
	private Integer ismultitenant;

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

	public int getSharerights() {
		return sharerights;
	}

	public int getSharestatus() {
		return sharestatus;
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

	public void setSharerights(int sharerights) {
		this.sharerights = sharerights;
	}

	public void setSharestatus(int sharestatus) {
		this.sharestatus = sharestatus;
	}

	public Long getSharetoprotocolcode() {
		return sharetoprotocolcode;
	}

	public void setSharetoprotocolcode(Long sharetoprotocolcode) {
		this.sharetoprotocolcode = sharetoprotocolcode;
	}

	public Long getShareprotocolcode() {
		return shareprotocolcode;
	}

	public void setShareprotocolcode(Long shareprotocolcode) {
		this.shareprotocolcode = shareprotocolcode;
	}

	public String getShareprotocolname() {
		return shareprotocolname;
	}

	public void setShareprotocolname(String shareprotocolname) {
		this.shareprotocolname = shareprotocolname;
	}

	public LSuserMaster getObjLoggeduser() {
		return objLoggeduser;
	}

	public void setObjLoggeduser(LSuserMaster objLoggeduser) {
		this.objLoggeduser = objLoggeduser;
	}

	public LScfttransaction getObjsilentaudit() {
		return objsilentaudit;
	}

	public void setObjsilentaudit(LScfttransaction objsilentaudit) {
		this.objsilentaudit = objsilentaudit;
	}

	public LScfttransaction getObjmanualaudit() {
		return objmanualaudit;
	}

	public void setObjmanualaudit(LScfttransaction objmanualaudit) {
		this.objmanualaudit = objmanualaudit;
	}

	public Integer getIsmultitenant() {
		return ismultitenant;
	}

	public void setIsmultitenant(Integer ismultitenant) {
		this.ismultitenant = ismultitenant;
	}
	private Integer retirestatus;

	public Integer getRetirestatus() {
		return retirestatus;
	}

	public void setRetirestatus(Integer retirestatus) {
		this.retirestatus = retirestatus;
	}
}