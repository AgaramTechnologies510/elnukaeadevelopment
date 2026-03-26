package com.agaram.eln.primary.fetchmodel.getorders;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class TagStructure {
	private String tagname;
	private int colindex;
	private int rowindex;
	private String tagvalue;
	private int ismultitag;
	private String tagsheetname;
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public int getColindex() {
		return colindex;
	}
	public void setColindex(int colindex) {
		this.colindex = colindex;
	}
	public int getRowindex() {
		return rowindex;
	}
	public void setRowindex(int rowindex) {
		this.rowindex = rowindex;
	}
	public String getTagvalue() {
		return tagvalue;
	}
	public void setTagvalue(String tagvalue) {
		this.tagvalue = tagvalue;
	}
	public int getIsmultitag() {
		return ismultitag;
	}
	public void setIsmultitag(int ismultitag) {
		this.ismultitag = ismultitag;
	}
	public String getTagsheetname() {
		return tagsheetname;
	}
	public void setTagsheetname(String tagsheetname) {
		this.tagsheetname = tagsheetname;
	}
	
	
}
