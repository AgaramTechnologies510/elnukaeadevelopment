package com.agaram.eln.primary.model.communicationsetting;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "parity")
public class Parity implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id 
	@Column(name = "paritykey", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parity_paritykey_seq")
	@SequenceGenerator(name = "parity_paritykey_seq", sequenceName = "parity_paritykey_seq", allocationSize = 1)
	private Integer paritykey;

	private String parityname;
	
	private int status=1;

	public Integer getParitykey() {
		return paritykey;
	}

	public void setParitykey(Integer paritykey) {
		this.paritykey = paritykey;
	}

	public String getParityname() {
		return parityname;
	}

	public void setParityname(String parityname) {
		this.parityname = parityname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
	
}
