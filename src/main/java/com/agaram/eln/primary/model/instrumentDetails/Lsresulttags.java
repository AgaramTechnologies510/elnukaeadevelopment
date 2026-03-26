package com.agaram.eln.primary.model.instrumentDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "Lsresulttags")
@Convert(attributeName = "entityAttrName", converter = JsonStringType.class)
public class Lsresulttags {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
	private String content;
	
	private Long orderid;
	
	private String resultvalue;

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

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getResultvalue() {
		return resultvalue;
	}

	public void setResultvalue(String resultvalue) {
		this.resultvalue = resultvalue;
	}

}