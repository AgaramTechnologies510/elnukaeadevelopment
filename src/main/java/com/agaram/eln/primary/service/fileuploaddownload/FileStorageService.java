package com.agaram.eln.primary.service.fileuploaddownload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
///import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.exception.FileNotFoundException;
import com.agaram.eln.primary.exception.FileStorageException;
import com.agaram.eln.primary.model.methodsetup.CloudParserFile;
import com.agaram.eln.primary.property.FileStorageProperties;
import com.agaram.eln.primary.repository.methodsetup.CloudParserFileRepository;
import com.agaram.eln.primary.repository.methodsetup.MethodRepository;
import com.agaram.eln.primary.service.cloudFileManip.CloudFileManipulationservice;
import com.agaram.eln.primary.service.methodsetup.MethodService;
import com.mongodb.client.gridfs.model.GridFSFile;


import com.agaram.eln.primary.model.methodsetup.Method;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    
    @Autowired
    private CloudFileManipulationservice cloudFileManipulationservice;
    
    @Autowired
    private CloudParserFileRepository cloudparserfilerepository;
    
    
	@Autowired
	GridFsOperations gridFsOps;

	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	@Autowired
    private MethodService methodservice;
	
	@Autowired
	private MethodRepository methodrepo;
	
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
   
    public String storeFile(MultipartFile file , String tenant , Integer isMultitenant,String originalfilename,
    		Integer version) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
      
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
     
            String id = null;
    		System.out.println("in storefile function");

    		try {
    			id = cloudFileManipulationservice.storecloudfilesreturnUUID(file, "parserfile");
    			System.out.println("Blob ID"+id);
    			System.out.println("stored in blob");

    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		}

    		CloudParserFile objfile = new CloudParserFile();
    		objfile.setFileid(id);
            objfile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
    		objfile.setFilename(fileName);
    		objfile.setOriginalfilename(originalfilename);
    		objfile.setVersion(version);
    	//	objfile.setInstrawdataurl(instrawdataurl);

    		cloudparserfilerepository.save(objfile);
          
          return id;
      
    }


public String storeimportFile(MultipartFile file , String tenant , Integer isMultitenant,String originalfilename,Integer version,Integer methodkey) throws Exception {
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      //  final Method method = (Method)methodservice.findById(methodkey).getBody();
        
       Method methodobj = methodservice.findByMethodkey(methodkey);
        
    	final String rawData;

            String id = null;
          if(isMultitenant == 1) {
    		try {
    			id = cloudFileManipulationservice.storecloudfilesreturnUUID(file, "parserfile");
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		}

    		CloudParserFile objfile = new CloudParserFile();
    		objfile.setFileid(id);
            objfile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
    		objfile.setFilename(fileName);
    		objfile.setOriginalfilename(originalfilename);
    		objfile.setVersion(version);
    		

    		cloudparserfilerepository.save(objfile);
          }else {
        	  storeSQLFile(file,tenant,isMultitenant,originalfilename) ;
          }
    		if(isMultitenant == 1) {
    		rawData =  methodservice.getFileData(fileName,tenant,methodkey);
    		}
    		else {
    		rawData =  methodservice.getSQLFileData(fileName,methodkey);
    		}
    		
          return rawData;
      
    }

public String storeSQLFile(MultipartFile file , String tenant,Integer isMultitenant,String originalfilename) {
    // Normalize file name
	
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    System.out.print("going to storefile service");
    try {
        // Check if the file's name contains invalid characters
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        String fileid = fileName;
		GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileid)));
		if (largefile != null) {
			
			System.out.print("largefile exists");
			
			gridFsTemplate.delete(new Query(Criteria.where("filename").is(fileid)));
		}
		
		

		  gridFsTemplate.store(new ByteArrayInputStream(file.getBytes()), fileid, file.getContentType());
		  CloudParserFile objfile = new CloudParserFile();
	     	  objfile.setFileid(fileid);
          objfile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
	    	  objfile.setFilename(fileName);
	    	  objfile.setOriginalfilename(originalfilename);
	    	  
	    	 System.out.print("going to save in cloudparserfilerepository : " + objfile); 
		      cloudparserfilerepository.save(objfile);
		// return fileName;
		 return fileid;
    } catch (IOException ex) {
        throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
    }
}
    
    public static File stream2file (InputStream in,String filename,String ext) throws IOException {
        final File tempFile = File.createTempFile(filename, ext);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }    

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }
}
