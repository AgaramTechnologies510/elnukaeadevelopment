package com.agaram.eln.primary.model.cloudFileManip;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;


@Entity
@Table(name = "LSOrderVersionfiles")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class CloudOrderVersion {
	@Id
	private long id;
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String content;
	private String fileuid;
	private Integer containerstored;
	private String fileuri;
	public String getFileuri() {
		return fileuri;
	}
	public void setFileuri(String fileuri) {
		this.fileuri = fileuri;
	}
	public String getFileuid() {
		return fileuid;
	}
	public void setFileuid(String fileuid) {
		this.fileuid = fileuid;
	}
	public Integer getContainerstored() {
		return containerstored;
	}
	public void setContainerstored(Integer containerstored) {
		this.containerstored = containerstored;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
