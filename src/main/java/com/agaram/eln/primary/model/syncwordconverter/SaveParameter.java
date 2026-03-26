package com.agaram.eln.primary.model.syncwordconverter;

import jakarta.persistence.Transient;

public class SaveParameter {
	private String _content;
	private String _fileName;
	
	@Transient
	private Integer ismultitenant;
	
	@Transient
	private String screen;
	

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}
	

	public Integer getIsmultitenant() {
		return ismultitenant;
	}

	public void setIsmultitenant(Integer ismultitenant) {
		this.ismultitenant = ismultitenant;
	}

	public String getContent() {
		return _content;
	}

	public String setContent(String value) {
		_content = value;
		return value;
	}

	public String getFileName() {
		return _fileName;
	}

	public String setFileName(String value) {
		_fileName = value;
		return value;
	}
}
