package com.agaram.eln.primary.model.notification;



import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Email")
public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
	@SequenceGenerator(name = "email_seq", sequenceName = "email_seq", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	
	private String mailfrom;
	
	private String mailto;
	
	private String mailcc;
	
	private String subject;
	
	@Column(columnDefinition = "varchar(5000)")
	private String mailcontent;
	
	@Transient
	private String usergroupname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}

	public String getMailto() {
		return mailto;
	}

	public void setMailto(String mailto) {
		this.mailto = mailto;
	}

	public String getMailcc() {
		return mailcc;
	}

	public void setMailcc(String mailcc) {
		this.mailcc = mailcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMailcontent() {
		return mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}
	

}
