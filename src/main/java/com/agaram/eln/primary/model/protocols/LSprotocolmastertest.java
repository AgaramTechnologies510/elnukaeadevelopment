package com.agaram.eln.primary.model.protocols;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;

@Entity
@Table(name = "LSprotocolmastertest")
public class LSprotocolmastertest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsprotocolmastertest_seq")
	@SequenceGenerator(name = "lsprotocolmastertest_seq", sequenceName = "lsprotocolmastertest_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "protocoltestcode")
	private int protocoltestcode;
	
	private Integer protocolmastercode;
	
	private Integer testtype;
	
	private Integer testcode;
	
	@Transient
	private Response response;
	
	@Transient
	private String Testname;
	
	public String getTestname() {
		return Testname;
	}

	public void setTestname(String testname) {
		Testname = testname;
	}

	@Transient
	LSuserMaster LSuserMaster;
	
	@Transient
	private Integer sitecode;
	
	
	public Integer getSitecode() {
		return sitecode;
	}

	public void setSitecode(Integer sitecode) {
		this.sitecode = sitecode;
	}

	@Transient
	LoggedUser objuser;
	
	@Transient
	LSuserMaster objLoggeduser;

	public LSuserMaster getObjLoggeduser() {
		return objLoggeduser;
	}

	public void setObjLoggeduser(LSuserMaster objLoggeduser) {
		this.objLoggeduser = objLoggeduser;
	}

	public int getProtocoltestcode() {
		return protocoltestcode;
	}

	public void setProtocoltestcode(int protocoltestcode) {
		this.protocoltestcode = protocoltestcode;
	}

	public Integer getProtocolmastercode() {
		return protocolmastercode;
	}

	public void setProtocolmastercode(Integer protocolmastercode) {
		this.protocolmastercode = protocolmastercode;
	}

	public Integer getTesttype() {
		return testtype;
	}

	public void setTesttype(Integer testtype) {
		this.testtype = testtype;
	}

	public Integer getTestcode() {
		return testcode;
	}

	public void setTestcode(Integer testcode) {
		this.testcode = testcode;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public LSuserMaster getLSuserMaster() {
		return LSuserMaster;
	}

	public void setLSuserMaster(LSuserMaster lSuserMaster) {
		LSuserMaster = lSuserMaster;
	}

	public LoggedUser getObjuser() {
		return objuser;
	}

	public void setObjuser(LoggedUser objuser) {
		this.objuser = objuser;
	}	
}
