package com.agaram.eln.primary.model.instrumentDetails;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSusersteam;

@Entity
@Table(name = "LSSelectedTeam")
public class LSSelectedTeam {
	
private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "selectionid", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer selectionid;

	@ManyToOne
	private LSusersteam userteam;
	
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name="teamcode")
//	private List<LSuserteammapping> lsuserteammapping;

	@Column(columnDefinition = "numeric(17,0)", name = "batchcode")
	private Long batchcode;
	
	@ManyToOne
	private LSSiteMaster sitemaster;

	@ManyToOne
	private Elnmaterial elnmaterial;
	
	private Long directorycode;
	
	@Transient
	private List<Long> lstdirectorycode;
	
	@Column(name = "CreatedTimeStamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdtimestamp;
	
	public List<Long> getLstdirectorycode() {
		return lstdirectorycode;
	}

	public void setLstdirectorycode(List<Long> lstdirectorycode) {
		this.lstdirectorycode = lstdirectorycode;
	}

	public Long getDirectorycode() {
		return directorycode;
	}

	public void setDirectorycode(Long directorycode) {
		this.directorycode = directorycode;
	}

	public Integer getSelectionid() {
		return selectionid;
	}

	public void setSelectionid(Integer selectionid) {
		this.selectionid = selectionid;
	}

	public LSusersteam getUserteam() {
		return userteam;
	}

	public void setUserteam(LSusersteam userteam) {
		this.userteam = userteam;
	}

//	public List<LSuserteammapping> getLsuserteammapping() {
//		return lsuserteammapping;
//	}
//
//	public void setLsuserteammapping(List<LSuserteammapping> lsuserteammapping) {
//		this.lsuserteammapping = lsuserteammapping;
//	}

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
	}

	public LSSiteMaster getSitemaster() {
		return sitemaster;
	}

	public void setSitemaster(LSSiteMaster sitemaster) {
		this.sitemaster = sitemaster;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Elnmaterial getElnmaterial() {
		return elnmaterial;
	}

	public void setElnmaterial(Elnmaterial elnmaterial) {
		this.elnmaterial = elnmaterial;
	}

	public Date getCreatedtimestamp() {
		return createdtimestamp;
	}

	public void setCreatedtimestamp(Date createdtimestamp) {
		this.createdtimestamp = createdtimestamp;
	}

	
}
