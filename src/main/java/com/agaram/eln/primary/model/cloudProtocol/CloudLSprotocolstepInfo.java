package com.agaram.eln.primary.model.cloudProtocol;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "LSprotocolstepInfoCloud")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class CloudLSprotocolstepInfo {
	@Id
	private Integer id;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	public String lsprotocolstepInfo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLsprotocolstepInfo() {
		return lsprotocolstepInfo;
	}
	public void setLsprotocolstepInfo(String lsprotocolstepInfo) {
		this.lsprotocolstepInfo = lsprotocolstepInfo;
	}
}
