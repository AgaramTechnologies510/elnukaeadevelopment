package com.agaram.eln.primary.model.instrumentDetails;

import java.util.Date;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.agaram.eln.primary.model.equipment.Equipment;
import com.agaram.eln.primary.model.instrumentsetup.InstrumentMaster;
import com.agaram.eln.primary.model.methodsetup.Method;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

@Entity
@Table(name = "LSOrderElnMethod")
public class LSOrderElnMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsorderelnmethod_seq")
	@SequenceGenerator(name = "lsorderelnmethod_seq", sequenceName = "lsorderelnmethod_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "orderelnmethodcode")
	private Integer orderelnmethodcode;
	
	@Column(columnDefinition = "numeric(17,0)", name = "batchcode")
	private Long batchcode;

	@Column(columnDefinition = "varchar(250)", name = "BatchID")
	private String batchid;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "methodkey")
	private Method method;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "instmasterkey", nullable = true)
	private InstrumentMaster instrument;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nequipmentcode", nullable = false)
	private Equipment equipment;
	
	@Column(name = "CreatedTimeStamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdtimestamp;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "usercode")
	private LSuserMaster createdby;

    private String sequenceid;
	
	@Transient
	private String displayText;
	
	public String getSequenceid() {
		return sequenceid;
	}

	public void setSequenceid(String sequenceid) {
		this.sequenceid = sequenceid;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public Integer getOrderelnmethodcode() {
		return orderelnmethodcode;
	}

	public void setOrderelnmethodcode(Integer orderelnmethodcode) {
		this.orderelnmethodcode = orderelnmethodcode;
	}

	public Long getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(Long batchcode) {
		this.batchcode = batchcode;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public InstrumentMaster getInstrument() {
		return instrument;
	}

	public void setInstrument(InstrumentMaster instrument) {
		this.instrument = instrument;
	}

	public Date getCreatedtimestamp() {
		return createdtimestamp;
	}

	public void setCreatedtimestamp(Date createdtimestamp) {
		this.createdtimestamp = createdtimestamp;
	}

	public LSuserMaster getCreatedby() {
		return createdby;
	}

	public void setCreatedby(LSuserMaster createdby) {
		this.createdby = createdby;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
