package com.agaram.eln.primary.model.instrumentDetails;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;


@Entity(name = "LSprotocolfolderfiles")
@Table(name = "LSprotocolfolderfiles")
public class LSprotocolfolderfiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "folderfilecode")
	private Integer folderfilecode;
	
	@Column(name = "directorycode",columnDefinition = "numeric(17,0)") 
	private Long directorycode;
	
	private String filefor;
	
	private String filename;
	
	private Long filesize;
	
	private String uuid;
	
	private Date createdtimestamp;
	
	@ManyToOne 
	private LSuserMaster createby;
	
	@ManyToOne 
	private LSSiteMaster lssitemaster;
	
	private Integer fileviewfor;
	
	@Transient
	private Date fromdate;
	
	@Transient
	private Date todate;
	
	@Transient
	private String folderpath;
	
	private Integer version;
	
	public String getFolderpath() {
		return folderpath;
	}

	public void setFolderpath(String folderpath) {
		this.folderpath = folderpath;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Integer getFolderfilecode() {
		return folderfilecode;
	}

	public void setFolderfilecode(Integer folderfilecode) {
		this.folderfilecode = folderfilecode;
	}

	public Long getDirectorycode() {
		return directorycode;
	}

	public void setDirectorycode(Long directorycode) {
		this.directorycode = directorycode;
	}

	public String getFilefor() {
		return filefor;
	}

	public void setFilefor(String filefor) {
		this.filefor = filefor;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreatedtimestamp() {
		return createdtimestamp;
	}

	public void setCreatedtimestamp(Date createdtimestamp) {
		this.createdtimestamp = createdtimestamp;
	}

	public LSuserMaster getCreateby() {
		return createby;
	}

	public void setCreateby(LSuserMaster createby) {
		this.createby = createby;
	}

	public LSSiteMaster getLssitemaster() {
		return lssitemaster;
	}

	public void setLssitemaster(LSSiteMaster lssitemaster) {
		this.lssitemaster = lssitemaster;
	}

	public Integer getFileviewfor() {
		return fileviewfor;
	}

	public void setFileviewfor(Integer fileviewfor) {
		this.fileviewfor = fileviewfor;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
