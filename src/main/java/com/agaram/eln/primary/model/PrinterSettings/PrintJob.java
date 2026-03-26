package com.agaram.eln.primary.model.PrinterSettings;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name ="PrintJob")
public class PrintJob {

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "printjob_printid_seq")
		@SequenceGenerator(name = "printjob_printid_seq", sequenceName = "printjob_printid_seq", allocationSize = 1)
	    private Long printId;

	    @ManyToOne 
		private LSuserMaster printBy;

	    private String printerName;
	    private String printerKey;
	    private String printUUID;
	    
	   

	    public Long getPrintId() {
	        return printId;
	    }

	    public void setPrintId(Long printId) {
	        this.printId = printId;
	    }

	    public LSuserMaster getPrintBy() {
	        return printBy;
	    }

	    public void setPrintBy(LSuserMaster printBy) {
	        this.printBy = printBy;
	    }

	    public String getPrinterName() {
	        return printerName;
	    }

	    public void setPrinterName(String printerName) {
	        this.printerName = printerName;
	    }

	    public String getPrinterKey() {
	        return printerKey;
	    }

	    public void setPrinterKey(String printerKey) {
	        this.printerKey = printerKey;
	    }

	    public String getPrintUUID() {
	        return printUUID;
	    }

	    public void setPrintUUID(String printUUID) {
	        this.printUUID = printUUID;
	    }
	
}
