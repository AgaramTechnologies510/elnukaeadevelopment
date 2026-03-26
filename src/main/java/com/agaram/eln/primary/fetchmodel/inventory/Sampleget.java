package com.agaram.eln.primary.fetchmodel.inventory;

import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public class Sampleget {

    private Integer samplecode;          
    private String samplename;           
    private String sequenceid;           
    private Double nqtynotification;     
    private Double quantity;            
    private Integer ntransactionstatus;  
    private LSuserMaster createby;       

    public Sampleget(Integer samplecode, String samplename, String sequenceid,
            Double nqtynotification, Double quantity, Integer ntransactionstatus, LSuserMaster createby) {
		this.samplecode = samplecode;
		this.samplename = samplename;
		this.sequenceid = sequenceid;
		this.nqtynotification = nqtynotification;
		this.quantity = quantity;
		this.ntransactionstatus = ntransactionstatus;
		this.createby = createby;
	}

    public Integer getSamplecode() {
        return samplecode;
    }
    public void setSamplecode(Integer samplecode) {
        this.samplecode = samplecode;
    }

    public String getSamplename() {
        return samplename;
    }
    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getSequenceid() {
        return sequenceid;
    }
    public void setSequenceid(String sequenceid) {
        this.sequenceid = sequenceid;
    }

    public Double getNqtynotification() {
        return nqtynotification;
    }
    public void setNqtynotification(Double nqtynotification) {
        this.nqtynotification = nqtynotification;
    }

    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getNtransactionstatus() {
        return ntransactionstatus;
    }
    public void setNtransactionstatus(Integer ntransactionstatus) {
        this.ntransactionstatus = ntransactionstatus;
    }

    public LSuserMaster getCreateby() {
        return createby;
    }
    public void setCreateby(LSuserMaster createby) {
        this.createby = createby;
    }
}
