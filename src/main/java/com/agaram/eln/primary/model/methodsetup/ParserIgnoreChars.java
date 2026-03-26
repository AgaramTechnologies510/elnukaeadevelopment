package com.agaram.eln.primary.model.methodsetup;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is used to map the fields of 'parserignorechars' table of the Database.
 * @author ATE153
 * @version 1.0.0
 * @since   01- May- 2020
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Entity
@Table(name = "parserignorechars")
public class ParserIgnoreChars implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@Id 
//	@Column(name = "parserignorecharskey", nullable = false)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer parserignorecharskey;
	@Id 
	@Column(name = "parserignorecharskey", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parserignorechars_parserignorecharskey_seq")
	@SequenceGenerator(name = "parserignorechars_parserignorecharskey_seq", sequenceName = "parserignorechars_parserignorecharskey_seq", allocationSize = 1)
	private Integer parserignorecharskey;
	
	@Column(name = "ignorechars")
	private String ignorechars;

	public Integer getParserignorecharskey() {
		return parserignorecharskey;
	}

	public void setParserignorecharskey(Integer parserignorecharskey) {
		this.parserignorecharskey = parserignorecharskey;
	}

	public String getIgnorechars() {
		return ignorechars;
	}

	public void setIgnorechars(String ignorechars) {
		this.ignorechars = ignorechars;
	}	
}
