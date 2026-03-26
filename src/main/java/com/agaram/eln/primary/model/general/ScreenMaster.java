package com.agaram.eln.primary.model.general;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ScreenMaster")
public class ScreenMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screenmaster_seq")
	@SequenceGenerator(name = "screenmaster_seq", sequenceName = "screenmaster_seq", allocationSize = 1)
	private Integer screencode;
	private String screenname;
	public Integer getScreencode() {
		return screencode;
	}
	public void setScreencode(Integer screencode) {
		this.screencode = screencode;
	}
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	
	
}
