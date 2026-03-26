package com.agaram.eln.primary.model.communicationsetting;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="resultsamplefrom")
public class ResultSampleFrom implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name ="resultsamplekey" , nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer resultsamplekey;
	
	private String resultsamplename;
	
	private int status=1;

	public Integer getResultsamplekey() {
		return resultsamplekey;
	}

	public void setResultsamplekey(Integer resultsamplekey) {
		this.resultsamplekey = resultsamplekey;
	}

	public String getResultsamplename() {
		return resultsamplename;
	}

	public void setResultsamplename(String resultsamplename) {
		this.resultsamplename = resultsamplename;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
