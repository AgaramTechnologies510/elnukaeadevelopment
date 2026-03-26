package com.agaram.eln.primary.model.communicationsetting;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "handshake")
public class HandShake implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@Column(name = "handshakekey", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer handshakekey;
	
	private String handshakename;
	
	private int status=1;

	public Integer getHandshakekey() {
		return handshakekey;
	}

	public String getHandshakename() {
		return handshakename;
	}

	public void setHandshakename(String handshakename) {
		this.handshakename = handshakename;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setHandshakekey(Integer handshakekey) {
		this.handshakekey = handshakekey;
	}

	


}
