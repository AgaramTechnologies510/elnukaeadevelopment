package com.agaram.eln.primary.model.webParser;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Lswebparserinstrumenttype")
public class Lswebparserinstrumenttype implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name = "insttypekey", nullable = false)
	private Integer insttypekey;
	
	@Column(name = "insttypename")
	private String insttypename;
	
	@Column(name = "status")
	private int status=1;
	
	public Lswebparserinstrumenttype() {}

	public Integer getInsttypekey() {
		return insttypekey;
	}

	public void setInsttypekey(Integer insttypekey) {
		this.insttypekey = insttypekey;
	}

	public String getInsttypename() {
		return insttypename;
	}

	public void setInsttypename(String insttypename) {
		this.insttypename = insttypename;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
