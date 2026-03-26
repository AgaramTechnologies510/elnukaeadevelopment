package com.agaram.eln.primary.model.templates;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "LsUnmappedTags")
public class LsUnmappedTags {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsunmappedtags_seq")
	@SequenceGenerator(name = "lsunmappedtags_seq", sequenceName = "lsunmappedtags_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "tagcode") 
	private Integer tagcode;
	@Column(name = "TagID") 
	private Integer tagid;
	@Column(name = "templatecode")
	private Integer templatecode;
	@Column(name = "TagName",columnDefinition = "varchar(100)") 
	private String tagname;
	@Column(name = "NonHierarchyStatus") 
	private Integer nonhierarchystatus;
	@Transient
	@Column(name = "sSiteCode",columnDefinition = "char(10)") 
	private String sitecode;
	public Integer getTagcode() {
		return tagcode;
	}
	public void setTagcode(Integer tagcode) {
		this.tagcode = tagcode;
	}
	public Integer getTagid() {
		return tagid;
	}
	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}
	public Integer getTemplatecode() {
		return templatecode;
	}
	public void setTemplatecode(Integer templatecode) {
		this.templatecode = templatecode;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public Integer getNonhierarchystatus() {
		return nonhierarchystatus;
	}
	public void setNonhierarchystatus(Integer nonhierarchystatus) {
		this.nonhierarchystatus = nonhierarchystatus;
	}
	public String getSitecode() {
		return sitecode;
	}
	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}
}
