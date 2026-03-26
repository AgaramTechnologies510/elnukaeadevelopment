package com.agaram.eln.primary.model.PrinterSettings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name ="printerdetails")
public class printerdetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "printerdetails_printid_seq")
	@SequenceGenerator(name = "printerdetails_printid_seq", sequenceName = "printerdetails_printid_seq", allocationSize = 1)
	private Long printerId;
	private String printerKey;
	private String printerName;
	

	public Long getPrinterId() {
		return printerId;
	}

	public void setPrinterId(Long printerId) {
		this.printerId = printerId;
	}

	public String getPrinterKey() {
		return printerKey;
	}

	public void setPrinterKey(String printerKey) {
		this.printerKey = printerKey;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}


	
}
