package com.agaram.eln.primary.controller.sample;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.model.sample.Sample;
import com.agaram.eln.primary.model.sample.SampleCategory;
import com.agaram.eln.primary.model.sample.SampleLinks;
import com.agaram.eln.primary.model.sample.SampleProjectHistory;
import com.agaram.eln.primary.service.sample.SampleService;

@RestController
@RequestMapping("/sample")
public class SampleController {
	

	@Autowired
	private SampleService objSampleService;
	
	@PostMapping(value = "/getSampleonCategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSampleonCategory(@RequestBody SampleCategory objsamplecat) throws Exception {

		return (ResponseEntity<Object>) objSampleService.getSampleonCategory(objsamplecat);
	}
	@PostMapping(value = "/getSampleonCategoryFillter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSampleonCategory(@RequestBody Map<String, Object> inputMap) throws Exception {

		return (ResponseEntity<Object>) objSampleService.getSampleonCategoryFillter(inputMap);
	}

	@PostMapping(value = "/getSampleonSite", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSampleonSite(@RequestBody Sample objsample) throws Exception {

		return (ResponseEntity<Object>) objSampleService.getSampleonSite(objsample);
	}
	
	@PostMapping("/createSample")
	public ResponseEntity<Object> createSample(@RequestBody Sample obj) throws Exception {

		return objSampleService.createSample(obj);
	}

	@PostMapping("/updateSample")
	public ResponseEntity<Object> updateSample(@RequestBody Sample obj) throws Exception {

		return objSampleService.updateSample(obj);
	}
	
	@PostMapping("/getchildsample")
	public ResponseEntity<Object> getchildsample(@RequestBody Sample[] obj) throws Exception {
		return objSampleService.getchildsample(Arrays.asList(obj));
	}

	@PostMapping("/getSampleAttachments")
	public Map<String, Object> getSampleAttachments(@RequestBody Map<String, Object> inputMap) throws Exception {
		return objSampleService.getSampleAttachments(inputMap);
	}
	@PostMapping("/getLinksOnSample")
	public ResponseEntity<Object> getLinksOnSample(@RequestBody SampleLinks sampleLinks)throws Exception
	{
		return objSampleService.getLinksOnSample(sampleLinks);
	}
	@PostMapping("/sampleCloudUploadattachments")
	public Sample sampleCloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam Integer nsampletypecode,
			@RequestParam Integer nsamplecatcode,
			@RequestParam Integer samplecode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer isMultitenant,@RequestParam Integer nsitecode)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return objSampleService.sampleCloudUploadattachments(file, nsampletypecode, nsamplecatcode,
				samplecode, filename, fileexe, usercode, currentdate, isMultitenant,nsitecode);
	}
	@PostMapping("/uploadLinkforSample")
	public ResponseEntity<Object> uploadLinkforSample(@RequestBody SampleLinks SampleLinks) throws ParseException
	{
		return objSampleService.uploadLinkforSample(SampleLinks);
	}
	
	@PostMapping("/deleteLinkforSample")
	public ResponseEntity<Object> deleteLinkforSample(@RequestBody SampleLinks SampleLinks)
	{
		return objSampleService.deleteLinkforSample(SampleLinks);
	}
	@PostMapping("/updateAssignedProjectOnSample")
	public ResponseEntity<Object> updateAssignedProjectOnSample(@RequestBody Sample sam)throws Exception
	{
		return objSampleService.updateAssignedProjectOnSample(sam);
	}
	@PostMapping("/getAssignedTaskOnSample")
	public ResponseEntity<Object> getAssignedTaskOnSample(@RequestBody Sample sam)throws Exception
	{
		return objSampleService.getAssignedTaskOnSample(sam);
	}
	
	@PostMapping("/updatemsampleprojecthistory")
	public ResponseEntity<Object> updatemsampleprojecthistory(@RequestBody SampleProjectHistory[] samplelist)throws Exception
	{
		return objSampleService.updatemsampleprojecthistory(samplelist);
	}
	@PostMapping(value = "/getSampleProps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSampleProps(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) objSampleService.getSampleProps(nsiteInteger);
	}
	
	@PostMapping("/getSampleCategoryByIdBarCodeFilter")
	public ResponseEntity<Object> getSampleCategoryByIdBarCodeFilter(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return objSampleService.getSampleCategoryByIdBarCodeFilter(inputMap);
	}
	
	@PostMapping("/updateSampleExpiry")
	public List<Sample> updateSampleExpiry(@RequestBody Sample[] objSample) throws Exception {
		return objSampleService.updateSampleExpiry(objSample);
	}

	@PostMapping(value = "/ImportDatatoStoreonSample")
	public ResponseEntity<Map<String, Object>>ImportDatatoStoreonSample(@RequestBody Map<String, Object> inputMap) throws ParseException{
		return objSampleService.ImportDatatoStoreonSample(inputMap);
		
	}
	
}
