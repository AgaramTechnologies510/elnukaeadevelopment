package com.agaram.eln.primary.model.protocols;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "LSprotocolmethod")
public class LSprotocolmethod{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolmethod_sequence")
	@SequenceGenerator(name = "lsprotocolmethod_sequence", sequenceName = "lsprotocolmethod_sequence", allocationSize = 1)
	public Integer protocolmethodcode;
	
	public Integer protocolmastercode;

	@Column(columnDefinition = "varchar(120)")
	private String methodid;
	
	@Column(columnDefinition = "varchar(120)")
	private String instrumentid;
	
	public Integer stepcode;
	
	public Integer sectioncode;

	public Integer getProtocolmethodcode() {
		return protocolmethodcode;
	}

	public void setProtocolmethodcode(Integer protocolmethodcode) {
		this.protocolmethodcode = protocolmethodcode;
	}

	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}

	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}

	public String getMethodid() {
		return methodid;
	}

	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}

	public String getInstrumentid() {
		return instrumentid;
	}

	public void setInstrumentid(String instrumentid) {
		this.instrumentid = instrumentid;
	}

	public Integer getStepcode() {
		return stepcode;
	}

	public void setStepcode(Integer stepcode) {
		this.stepcode = stepcode;
	}

	public Integer getSectioncode() {
		return sectioncode;
	}

	public void setSectioncode(Integer sectioncode) {
		this.sectioncode = sectioncode;
	}
	
}
