package com.agaram.eln.primary.model.usermanagement;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "LSMultisites")
public class LSMultisites {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private int multisitecode;
	private Integer usercode;
	@ManyToOne 
	private LSSiteMaster lssiteMaster;
	private Integer defaultsiteMaster;
	
	@Transient
	private Integer multiusergroupcode;
	
	public int getMultisitecode() {
		return multisitecode;
	}
	public void setMultisitecode(int multisitecode) {
		this.multisitecode = multisitecode;
	}
	public Integer getMultiusergroupcode() {
		return multiusergroupcode;
	}
	public void setMultiusergroupcode(Integer multiusergroupcode) {
		this.multiusergroupcode = multiusergroupcode;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public LSSiteMaster getLssiteMaster() {
		return lssiteMaster;
	}
	public void setLssiteMaster(LSSiteMaster lssiteMaster) {
		this.lssiteMaster = lssiteMaster;
	}
	public Integer getDefaultsiteMaster() {
		return defaultsiteMaster;
	}
	public void setDefaultsiteMaster(Integer defaultsiteMaster) {
		this.defaultsiteMaster = defaultsiteMaster;
	}

	
}
