package com.agaram.eln.primary.model.material;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "materialconfig")
public class MaterialConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "nmaterialconfigcode")
	private Integer nmaterialconfigcode;
	@Column(name = "nformcode")
	private Integer nformcode;
	@Column(name = "nmaterialtypecode")
	private Integer nmaterialtypecode;
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "jsondata", columnDefinition = "jsonb")
	private List<Object> jsondata;

	@Column(name = "nstatus")
	private Integer nstatus;

	public Integer getNformcode() {
		return nformcode;
	}

	public void setNformcode(Integer nformcode) {
		this.nformcode = nformcode;
	}

	public Integer getNmaterialtypecode() {
		return nmaterialtypecode;
	}

	public void setNmaterialtypecode(Integer nmaterialtypecode) {
		this.nmaterialtypecode = nmaterialtypecode;
	}

	public Integer getNmaterialconfigcode() {
		return nmaterialconfigcode;
	}

	public void setNmaterialconfigcode(Integer nmaterialconfigcode) {
		this.nmaterialconfigcode = nmaterialconfigcode;
	}

	public List<Object> getJsondata() {
		return jsondata;
	}

	public void setJsondata(List<Object> jsondata) {
		this.jsondata = jsondata;
	}

	public Integer getNstatus() {
		return nstatus;
	}

	public void setNstatus(Integer nstatus) {
		this.nstatus = nstatus;
	}
}