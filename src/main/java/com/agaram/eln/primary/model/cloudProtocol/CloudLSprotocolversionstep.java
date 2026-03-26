package com.agaram.eln.primary.model.cloudProtocol;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "CloudLSprotocolversionstep")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class CloudLSprotocolversionstep {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cloudlsprotocolversionstep_seq")
	@SequenceGenerator(name = "cloudlsprotocolversionstep_seq", sequenceName = "cloudlsprotocolversionstep_seq", allocationSize = 1)
	public Integer idversioncode;
	
	private Integer id;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	public String lsprotocolstepInfo;
	
	@Column(columnDefinition = "varchar(100)")
	private String versionname;
	
	private Integer versionno;
	
	public String getVersionname() {
		return versionname;
	}
	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
	public Integer status;
	
	public Integer protocolmastercode;
	
	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}
	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}
	public Integer getIdversioncode() {
		return idversioncode;
	}
	public void setIdversioncode(Integer idversioncode) {
		this.idversioncode = idversioncode;
	}
	
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
	public Integer getVersionno() {
		return versionno;
	}
	public void setVersionno(Integer versionno) {
		this.versionno = versionno;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}


