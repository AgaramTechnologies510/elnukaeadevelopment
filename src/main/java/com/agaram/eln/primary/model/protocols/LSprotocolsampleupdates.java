package com.agaram.eln.primary.model.protocols;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="LSprotocolsampleupdates")
public class LSprotocolsampleupdates {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolsampleupdates_seq")
	@SequenceGenerator(name = "lsprotocolsampleupdates_seq", sequenceName = "lsprotocolsampleupdates_seq", allocationSize = 1)
	@Basic(optional = false)
	public Integer protocolsamplecode;
	public String protocolsampletype;
	public String protocolsample;
	public String protocolsampleusedDetail;
	public Integer protocolstepcode;
	public Integer protocolmastercode;
	public Integer indexof;
	public Integer usedquantity;
	public Integer repositorydatacode;
	@Temporal(TemporalType.TIMESTAMP)
	public Date createddate;
	public Integer usercode;
	public Integer status;
	public String consumefieldkey;
	public String unit;
	

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getConsumefieldkey() {
		return consumefieldkey;
	}
	public void setConsumefieldkey(String consumefieldkey) {
		this.consumefieldkey = consumefieldkey;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRepositorydatacode() {
		return repositorydatacode;
	}
	public void setRepositorydatacode(Integer repositorydatacode) {
		this.repositorydatacode = repositorydatacode;
	}
	public Integer getUsedquantity() {
		return usedquantity;
	}
	public void setUsedquantity(Integer usedquantity) {
		this.usedquantity = usedquantity;
	}


	public Integer getIndexof() {
		return indexof;
	}
	public void setIndexof(Integer indexof) {
		this.indexof = indexof;
	}
	public Integer getProtocolsamplecode() {
		return protocolsamplecode;
	}
	public void setProtocolsamplecode(Integer protocolsamplecode) {
		this.protocolsamplecode = protocolsamplecode;
	}
	public String getProtocolsampletype() {
		return protocolsampletype;
	}
	public void setProtocolsampletype(String protocolsampletype) {
		this.protocolsampletype = protocolsampletype;
	}
	public String getProtocolsample() {
		return protocolsample;
	}
	public void setProtocolsample(String protocolsample) {
		this.protocolsample = protocolsample;
	}
	public String getProtocolsampleusedDetail() {
		return protocolsampleusedDetail;
	}
	public void setProtocolsampleusedDetail(String protocolsampleusedDetail) {
		this.protocolsampleusedDetail = protocolsampleusedDetail;
	}
	public Integer getProtocolstepcode() {
		return protocolstepcode;
	}
	public void setProtocolstepcode(Integer protocolstepcode) {
		this.protocolstepcode = protocolstepcode;
	}
	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}
	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	
	
}
