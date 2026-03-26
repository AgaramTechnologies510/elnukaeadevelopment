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
@Table(name = "CloudLSprotocolorderversionstep")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class CloudLSprotocolorderversionstep {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cloudlsprotocolorderversionstep_seq")
	@SequenceGenerator(name = "cloudlsprotocolorderversionstep_seq", sequenceName = "cloudlsprotocolorderversionstep_seq", allocationSize = 1)
	public Integer idversioncode;
	
	private Integer protocolorderstepversioncode;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	public String lsprotocolstepInfo;
	
	@Column(columnDefinition = "varchar(100)")
	private String versionname;
	
	private Integer versionno;
	
	public Integer status;
	
	private Long protocolordercode;

	public Integer getIdversioncode() {
		return idversioncode;
	}

	public Integer getProtocolorderstepversioncode() {
		return protocolorderstepversioncode;
	}

	public String getLsprotocolstepInfo() {
		return lsprotocolstepInfo;
	}

	public String getVersionname() {
		return versionname;
	}

	public Integer getVersionno() {
		return versionno;
	}

	public Integer getStatus() {
		return status;
	}


	public void setIdversioncode(Integer idversioncode) {
		this.idversioncode = idversioncode;
	}

	public void setProtocolorderstepversioncode(Integer protocolorderstepversioncode) {
		this.protocolorderstepversioncode = protocolorderstepversioncode;
	}

	public void setLsprotocolstepInfo(String lsprotocolstepInfo) {
		this.lsprotocolstepInfo = lsprotocolstepInfo;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public void setVersionno(Integer versionno) {
		this.versionno = versionno;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getProtocolordercode() {
		return protocolordercode;
	}

	public void setProtocolordercode(Long protocolordercode) {
		this.protocolordercode = protocolordercode;
	}


}
