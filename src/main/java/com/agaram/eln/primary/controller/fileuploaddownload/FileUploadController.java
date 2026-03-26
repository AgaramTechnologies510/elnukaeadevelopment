package com.agaram.eln.primary.controller.fileuploaddownload;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.agaram.eln.primary.repository.cfr.LScfttransactionRepository;
import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.methodsetup.Method;
import com.agaram.eln.primary.model.methodsetup.MethodVersion;
import com.agaram.eln.primary.payload.Response;
import com.agaram.eln.primary.repository.methodsetup.MethodRepository;
import com.agaram.eln.primary.repository.methodsetup.MethodVersionRepository;
import com.agaram.eln.primary.service.fileuploaddownload.FileStorageService;


/**
 * This class holds method to upload file  in "uploads" directory
 * of the application
 * @author ATE153
 * @version 1.0.0
 * @since   05- Dec- 2019
 *
 */
@RestController
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;  

    @Autowired
    private MethodRepository methodrepo; 
    
    @Autowired
    private MethodVersionRepository methodversionrepo; 
    

    @Autowired
    private LScfttransactionRepository cftrepo; 
    
    public FileStorageService getFileStorageService() {
		return fileStorageService;
	}

	public void setFileStorageService(FileStorageService fileStorageService)throws Exception {
		this.fileStorageService = fileStorageService;
	}

	/**
	 * This method invokes FileStorageService to store file in specified location and then
	 * returns its file name
	 * @param file [MultipartFile]
	 * @return uploaded file name
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	
	@PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam MultipartFile file ,@RequestParam String tenant,
    		@RequestParam Integer isMultitenant,@RequestParam String originalfilename,@RequestParam Integer version,
    		@RequestParam String instrawdataurl) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException{
		
		System.out.print("going to storefile");
        String Blobid = fileStorageService.storeFile(file,tenant, isMultitenant,originalfilename,version);
		
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(Blobid)
                .toUriString();

        return new Response(Blobid, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
	@PostMapping("/sqluploadFile")
    public Response sqluploadFile(@RequestParam MultipartFile file ,@RequestParam String tenant,
    		@RequestParam Integer isMultitenant,@RequestParam String originalfilename){
		
		System.out.print("going to storefile");
        String Blobid = fileStorageService.storeSQLFile(file,tenant, isMultitenant,originalfilename);
		
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(Blobid)
                .toUriString();

        return new Response(Blobid, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

	@PostMapping("/uploadimportFile")
    public String uploadimportFile(@RequestParam MultipartFile file ,@RequestParam String tenant,
    		@RequestParam Integer isMultitenant,@RequestParam String originalfilename,
    		@RequestParam Integer version
    		,@RequestParam Integer methodkey,
    		@RequestParam String filename,@RequestParam String instrawdataurl,
    		@RequestParam Integer sitecode,@RequestParam Integer usercode
    		) throws Exception{
		
		final String rawData = fileStorageService.storeimportFile(file,tenant, isMultitenant,originalfilename,version,methodkey);
		
		List<Method> method = new ArrayList<Method>();
		Method newobj = new Method();
		MethodVersion obj = new MethodVersion();
		
		ArrayList<MethodVersion> methodversion = new ArrayList<MethodVersion>();
	    methodversion =methodversionrepo.findByMethodkey(methodkey);
		
		obj.setFilename(filename);
		obj.setMethodkey(methodkey);
		obj.setInstrawdataurl(instrawdataurl);
		obj.setVersion(version);
		methodversionrepo.save(obj);	

		method=methodrepo.findByMethodkey(methodkey);
		

		List <MethodVersion> metverobj = new ArrayList<MethodVersion>();
		metverobj.add(obj);
		metverobj.addAll(methodversion);
				
		newobj.setCreatedby(method.get(0).getCreatedby());
		newobj.setCreateddate(method.get(0).getCreateddate());
		newobj.setMethodkey(method.get(0).getMethodkey());
        newobj.setMethodname(method.get(0).getMethodname());
		newobj.setInstrawdataurl(instrawdataurl);
		newobj.setFilename(filename);
		newobj.setSite(method.get(0).getSite());
		newobj.setParser(method.get(0).getParser());
		newobj.setSamplesplit(method.get(0).getSamplesplit());
		newobj.setStatus(method.get(0).getStatus());
		newobj.setVersion(version);
		newobj.setMethodversion(metverobj);
		
		methodrepo.save(newobj);		
		Date Date = new Date();
				
		LScfttransaction auditobj = new LScfttransaction();
		auditobj.setActions("IDS_TSK_IMPORT");
		auditobj.setTransactiondate(Date);
		auditobj.setComments("Filename: "+originalfilename+ "is imported for Method :" +method.get(0).getMethodname()+" and versioned as " +version  );
		auditobj.setTableName("Method");
		auditobj.setModuleName("Method Master");
		auditobj.setSystemcoments("Audittrail.Audittrailhistory.Audittype.IDS_AUDIT_SYSTEMGENERATED");
		auditobj.setLssitemaster(sitecode);
		auditobj.setLsuserMaster(usercode);
		
		cftrepo.save(auditobj);
		
		return rawData;

    }
	
}
