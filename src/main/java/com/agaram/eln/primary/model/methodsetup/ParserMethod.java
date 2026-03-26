package com.agaram.eln.primary.model.methodsetup;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class is used to map the fields of 'parsermethod' table of the Database.
 * @author ATE153
 * @version 1.0.0
 * @since   18- Mar- 2020
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@XmlRootElement  (name = "parsermethod")
@XmlType(propOrder = { "parsermethodkey", "parsermethodname", "parsermethodtype"})
@Entity
@Table(name = "parsermethod")
public class ParserMethod implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "parsermethodkey", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer parsermethodkey;
	
	@Column(name = "parsermethodname")
	private String parsermethodname;
	
	@Column(name = "parsermethodtype")
	private int parsermethodtype;

	public Integer getParsermethodkey() {
		return parsermethodkey;
	}

	public void setParsermethodkey(Integer parsermethodkey) {
		this.parsermethodkey = parsermethodkey;
	}

	public String getParsermethodname() {
		return parsermethodname;
	}

	public void setParsermethodname(String parsermethodname) {
		this.parsermethodname = parsermethodname;
	}

	public int getParsermethodtype() {
		return parsermethodtype;
	}

	public void setParsermethodtype(int parsermethodtype) {
		this.parsermethodtype = parsermethodtype;
	}		
	
}
