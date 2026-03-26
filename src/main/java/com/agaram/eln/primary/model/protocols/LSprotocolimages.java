package com.agaram.eln.primary.model.protocols;

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
@Table(name="LSprotocolimages")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class LSprotocolimages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolimages_seq")
	@SequenceGenerator(name = "lsprotocolimages_seq", sequenceName = "lsprotocolimages_seq", allocationSize = 1)
	public Integer protocolstepimagecode;
	public Integer protocolstepcode;
	public Integer protocolmastercode;
	public Integer stepno;
	public String protocolstepname;
	public String fileid;
	public String extension;
	public String filename;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	public String src;
	
	public Boolean islinkimage;
	
	public Boolean isIslinkimage() {
		return islinkimage;
	}
	public void setIslinkimage(Boolean islinkimage) {
		this.islinkimage = islinkimage;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public Integer getProtocolstepimagecode() {
		return protocolstepimagecode;
	}
	public void setProtocolstepimagecode(Integer protocolstepimagecode) {
		this.protocolstepimagecode = protocolstepimagecode;
	}
	public Integer getProtocolstepcode() {
		return protocolstepcode;
	}
	public void setProtocolstepcode(Integer protocolstepcode) {
		this.protocolstepcode = protocolstepcode;
	}
	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}
	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}
	public Integer getStepno() {
		return stepno;
	}
	public void setStepno(Integer stepno) {
		this.stepno = stepno;
	}
	public String getProtocolstepname() {
		return protocolstepname;
	}
	public void setProtocolstepname(String protocolstepname) {
		this.protocolstepname = protocolstepname;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	

}
