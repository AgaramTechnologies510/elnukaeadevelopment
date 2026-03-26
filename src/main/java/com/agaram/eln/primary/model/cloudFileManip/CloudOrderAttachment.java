package com.agaram.eln.primary.model.cloudFileManip;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.bson.types.Binary;

//import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "LSOrderAttachmentfiles")
public class CloudOrderAttachment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lsorderattachmentfiles_seq")
	@SequenceGenerator(name = "lsorderattachmentfiles_seq", sequenceName = "lsorderattachmentfiles_seq", allocationSize = 1)
	@Basic(optional = false)
    private Integer id;
        
    private Binary file;

    private String fileid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Binary getFile() {
		return file;
	}

	public void setFile(Binary file) {
		this.file = file;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	
    
    
}
