package com.agaram.eln.primary.model.instrumentDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "lsresultfororders")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class Lsresultfororders {
	
	@Id
	private long id;
	
	@Column(columnDefinition = "numeric(17,0)", name = "batchcode")
	private Long batchcode;
	
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

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
	}	
	
}