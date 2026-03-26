package com.agaram.eln.primary.model.material;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "materiallinks")
public class MaterialLinks {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materiallinks_seq")
	@SequenceGenerator(name = "materiallinks_seq", sequenceName = "materiallinks_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "materiallinkcode")
	private Integer materiallinkcode;
	
	@Column(name = "nmaterialcode")
	private Integer nmaterialcode;

	@Column(name = "nmaterialcatcode")
	private Integer nmaterialcatcode; 
	
	@Column(name = "nmaterialtypecode")
	private Integer nmaterialtypecode;
	
	@Column(name = "nstatus")
	private Integer nstatus;
	
	@ManyToOne 
	private LSuserMaster createby;
	
	private Date createddate;
	
	private Integer nsitecode;
	
	@Column(columnDefinition = "varchar(500)", name = "link")
	private String link;

	public Integer getMateriallinkcode() {
		return materiallinkcode;
	}

	public void setMateriallinkcode(Integer materiallinkcode) {
		this.materiallinkcode = materiallinkcode;
	}

	public Integer getNmaterialcode() {
		return nmaterialcode;
	}

	public void setNmaterialcode(Integer nmaterialcode) {
		this.nmaterialcode = nmaterialcode;
	}

	public Integer getNmaterialcatcode() {
		return nmaterialcatcode;
	}

	public void setNmaterialcatcode(Integer nmaterialcatcode) {
		this.nmaterialcatcode = nmaterialcatcode;
	}

	public Integer getNmaterialtypecode() {
		return nmaterialtypecode;
	}

	public void setNmaterialtypecode(Integer nmaterialtypecode) {
		this.nmaterialtypecode = nmaterialtypecode;
	}

	public Integer getNstatus() {
		return nstatus;
	}

	public void setNstatus(Integer nstatus) {
		this.nstatus = nstatus;
	}

	public LSuserMaster getCreateby() {
		return createby;
	}

	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Integer getNsitecode() {
		return nsitecode;
	}

	public void setNsitecode(Integer nsitecode) {
		this.nsitecode = nsitecode;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
