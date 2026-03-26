package com.agaram.eln.primary.model.templates;

import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

//import lombok.Data;

@Entity
//@Data
@DynamicUpdate
@Table(name = "LsUnmappedTemplate")
public class LsUnmappedTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "templatecode") 
	private Integer templatecode;
	@Column(name = "templatename",columnDefinition = "varchar(100)") 
	private String templatename;
	@OneToMany
	@JoinColumn(name="templatecode")
	private List<LsUnmappedTags> lstags;
	
	public Integer getTemplatecode() {
		return templatecode;
	}
	public void setTemplatecode(Integer templatecode) {
		this.templatecode = templatecode;
	}
	public String getTemplatename() {
		return templatename;
	}
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	public List<LsUnmappedTags> getLstags() {
		return lstags;
	}
	public void setLstags(List<LsUnmappedTags> lstags) {
		this.lstags = lstags;
	}
}