package com.agaram.eln.primary.model.adsconnection;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tblADSSettings")
public class Tbladssettings {
	
	@Id
	@Basic(optional = false)
	@Column(name = "LDAPLocationID")
	private String ldapocationid;
	
	@Transient
	public String sloginid;
	@Transient
    public String spassword;
    @Column(name="LDAPServerDomainName")
    public String ldapserverdomainname;
    @Column(name="LDAPLocation")
    public String ldaplocation;
    @Column(name="LDAPStatus")
    public Integer ldaptatus;
    @Column(name="CreatedDate")
    public Date createddate;
    @Column(name="LastSyncDate")
    public Date lastsyncdate;
    @Column(name="GroupName")
    public String groupname;
	public String getLdapocationid() {
		return ldapocationid;
	}
	public void setLdapocationid(String ldapocationid) {
		this.ldapocationid = ldapocationid;
	}
	public String getSloginid() {
		return sloginid;
	}
	public void setSloginid(String sloginid) {
		this.sloginid = sloginid;
	}
	public String getSpassword() {
		return spassword;
	}
	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}
	public String getLdapserverdomainname() {
		return ldapserverdomainname;
	}
	public void setLdapserverdomainname(String ldapserverdomainname) {
		this.ldapserverdomainname = ldapserverdomainname;
	}
	public String getLdaplocation() {
		return ldaplocation;
	}
	public void setLdaplocation(String ldaplocation) {
		this.ldaplocation = ldaplocation;
	}
	public Integer getLdaptatus() {
		return ldaptatus;
	}
	public void setLdaptatus(Integer ldaptatus) {
		this.ldaptatus = ldaptatus;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Date getLastsyncdate() {
		return lastsyncdate;
	}
	public void setLastsyncdate(Date lastsyncdate) {
		this.lastsyncdate = lastsyncdate;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}