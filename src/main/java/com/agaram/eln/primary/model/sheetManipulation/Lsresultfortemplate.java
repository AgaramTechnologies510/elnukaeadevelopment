package com.agaram.eln.primary.model.sheetManipulation;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lsresultfortemplate")

public class Lsresultfortemplate {
	
	@Id
	private long id;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String content;
	
	private Integer contentstored;

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

	public Integer getContentstored() {
		return contentstored;
	}

	public void setContentstored(Integer contentstored) {
		this.contentstored = contentstored;
	}	
}