package com.agaram.eln.primary.model.instrumentDetails;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "LsOrderSampleUpdate")
public class LsOrderSampleUpdate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsordersampleupdate_seq")
	@SequenceGenerator(name = "lsordersampleupdate_seq", sequenceName = "lsordersampleupdate_seq", allocationSize = 1)
	@Basic(optional = false)
	public Integer ordersamplecode;
	public String ordersampletype;
	public String ordersample;
	public String ordersampleusedDetail;
	@Column(columnDefinition = "text")
	public String ordersampleinfo;
	@Column(columnDefinition = "numeric(17,0)",name = "batchcode") 
	public Long batchcode;
	@Temporal(TemporalType.TIMESTAMP)
	public Date createddate;
	public Integer usercode;
	public Integer inventoryused;
	public Integer repositorycode;
	public Integer repositorydatacode;
	public Integer quantityused=0;
	public String historydetails;
	public String createdbyusername;
	public String screenmodule;
	@Transient
	public LSuserMaster lsusermaster;
	public String getHistorydetails() {
		return historydetails;
	}
	@Transient
	public String batchid;
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getCreatedbyusername() {
		return createdbyusername;
	}
	public String getScreenmodule() {
		return screenmodule;
	}
	public void setHistorydetails(String historydetails) {
		this.historydetails = historydetails;
	}
	public void setCreatedbyusername(String createdbyusername) {
		this.createdbyusername = createdbyusername;
	}
	public LSuserMaster getLsusermaster() {
		return lsusermaster;
	}
	public void setLsusermaster(LSuserMaster lsusermaster) {
		this.lsusermaster = lsusermaster;
	}
	public void setScreenmodule(String screenmodule) {
		this.screenmodule = screenmodule;
	}
	public Integer getOrdersamplecode() {
		return ordersamplecode;
	}
	public void setOrdersamplecode(Integer ordersamplecode) {
		this.ordersamplecode = ordersamplecode;
	}
	public String getOrdersampletype() {
		return ordersampletype;
	}
	public void setOrdersampletype(String ordersampletype) {
		this.ordersampletype = ordersampletype;
	}
	public String getOrdersample() {
		return ordersample;
	}
	public void setOrdersample(String ordersample) {
		this.ordersample = ordersample;
	}
	public String getOrdersampleusedDetail() {
		return ordersampleusedDetail;
	}
	public void setOrdersampleusedDetail(String ordersampleusedDetail) {
		this.ordersampleusedDetail = ordersampleusedDetail;
	}
	public String getOrdersampleinfo() {
		return ordersampleinfo;
	}
	public void setOrdersampleinfo(String ordersampleinfo) {
		this.ordersampleinfo = ordersampleinfo;
	}
	public Long getBatchcode() {
		return batchcode;
	}
	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
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
	public Integer getRepositorycode() {
		return repositorycode;
	}
	public void setRepositorycode(Integer repositorycode) {
		this.repositorycode = repositorycode;
	}
	public Integer getRepositorydatacode() {
		return repositorydatacode;
	}
	public void setRepositorydatacode(Integer repositorydatacode) {
		this.repositorydatacode = repositorydatacode;
	}
	public Integer getQuantityused() {
		return quantityused;
	}
	public void setQuantityused(Integer quantityused) {
		this.quantityused = quantityused;
	}
	public Integer getInventoryused() {
		return inventoryused;
	}
	public void setInventoryused(Integer inventoryused) {
		this.inventoryused = inventoryused;
	}	
	
}
