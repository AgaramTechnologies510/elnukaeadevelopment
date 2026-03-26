package com.agaram.eln.primary.model.material;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "MappedTemplateFieldPropsMaterial")
public class MappedTemplateFieldPropsMaterial {
	
	@Id
	@Column(name = "nmappedtemplatefieldpropmaterialcode")
	private Integer nmappedtemplatefieldpropmaterialcode;
	@Column(name = "nmaterialconfigcode")
	private Integer nmaterialconfigcode;
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "jsondata", columnDefinition = "jsonb")
	private String jsondata;
	@Column(name = "nstatus")
	private Integer nstatus;
	
	public Integer getNmappedtemplatefieldpropmaterialcode() {
		return nmappedtemplatefieldpropmaterialcode;
	}
	public void setNmappedtemplatefieldpropmaterialcode(Integer nmappedtemplatefieldpropmaterialcode) {
		this.nmappedtemplatefieldpropmaterialcode = nmappedtemplatefieldpropmaterialcode;
	}
	public Integer getNmaterialconfigcode() {
		return nmaterialconfigcode;
	}
	public void setNmaterialconfigcode(Integer nmaterialconfigcode) {
		this.nmaterialconfigcode = nmaterialconfigcode;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public Integer getNstatus() {
		return nstatus;
	}
	public void setNstatus(Integer nstatus) {
		this.nstatus = nstatus;
	}
}
