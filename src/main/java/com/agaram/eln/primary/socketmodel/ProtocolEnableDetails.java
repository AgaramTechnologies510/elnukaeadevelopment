package com.agaram.eln.primary.socketmodel;

import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public class ProtocolEnableDetails {
	private String editorid;
	private String tabid;
	private LSuserMaster lsusermaster;
	private Integer protocolcode;
	private String modifiedcontent;
	private String editorname;
	private boolean isautosave;
	private Integer protocolscreen;
	private String tenantid;
	private String fieldkeyid;
	private String stepid;
	private String sectionid;
	private String sendid;
	
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	public String getEditorid() {
		return editorid;
	}
	public void setEditorid(String editorid) {
		this.editorid = editorid;
	}
	public String getTabid() {
		return tabid;
	}
	public void setTabid(String tabid) {
		this.tabid = tabid;
	}
	public LSuserMaster getLsusermaster() {
		return lsusermaster;
	}
	public void setLsusermaster(LSuserMaster lsusermaster) {
		this.lsusermaster = lsusermaster;
	}
	public Integer getProtocolcode() {
		return protocolcode;
	}
	public void setProtocolcode(Integer protocolcode) {
		this.protocolcode = protocolcode;
	}
	public String getModifiedcontent() {
		return modifiedcontent;
	}
	public void setModifiedcontent(String modifiedcontent) {
		this.modifiedcontent = modifiedcontent;
	}
	public String getEditorname() {
		return editorname;
	}
	public void setEditorname(String editorname) {
		this.editorname = editorname;
	}
	public boolean isIsautosave() {
		return isautosave;
	}
	public void setIsautosave(boolean isautosave) {
		this.isautosave = isautosave;
	}
	public Integer getProtocolscreen() {
		return protocolscreen;
	}
	public void setProtocolscreen(Integer protocolscreen) {
		this.protocolscreen = protocolscreen;
	}
	public String getTenantid() {
		return tenantid;
	}
	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}
	public String getFieldkeyid() {
		return fieldkeyid;
	}
	public void setFieldkeyid(String fieldkeyid) {
		this.fieldkeyid = fieldkeyid;
	}
	public String getStepid() {
		return stepid;
	}
	public void setStepid(String stepid) {
		this.stepid = stepid;
	}
	public String getSectionid() {
		return sectionid;
	}
	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}
	
}
