package com.agaram.eln.primary.controller.material;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.material.ElnresultUsedMaterial;
import com.agaram.eln.primary.model.material.Material;
import com.agaram.eln.primary.model.material.MaterialLinks;
import com.agaram.eln.primary.model.material.MaterialProjectHistory;
import com.agaram.eln.primary.model.sample.ElnresultUsedSample;
import com.agaram.eln.primary.service.material.MaterialService;

@RestController
@RequestMapping("/material")
public class MaterialController {

	@Autowired
	private MaterialService objMaterialService;

	@PostMapping(value = "/getMaterial", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialType(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialType(nsiteInteger);
	}
	
	@PostMapping(value = "/getMaterialonCategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialonCategory(@RequestBody Map<String, Object> inputMap) throws Exception {

		return (ResponseEntity<Object>) objMaterialService.getMaterialonCategory(inputMap);
	}
	
	@PostMapping(value = "/getMaterialDesign", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialDesign(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer ntypecode = (Integer) inputMap.get("nmaterialcode");
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialDesign(ntypecode);
	}	
	
	@PostMapping(value = "/getMaterialTypeDesign", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeDesign(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer ntypecode = (Integer) inputMap.get("ntypecode");
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialTypeDesign(ntypecode);
	}	
	
	@PostMapping("/getMaterialcombo")
	public ResponseEntity<Object> getMaterialcombo(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nmaterialtypecode = (Integer) inputMap.get("nmaterialtypecode");
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) objMaterialService.getMaterialcombo(nmaterialtypecode,nsiteInteger);
	}

	@PostMapping("/getMaterialByTypeCode")
	public ResponseEntity<Object> getMaterialByTypeCode(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getMaterialByTypeCode(inputMap);
	}
	
	@PostMapping("/getMaterialBySearchField")
	public ResponseEntity<Object> getMaterialBySearchField(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getMaterialBySearchField(inputMap);
	}
	
	@PostMapping("/getMaterialByTypeCodeByDate")
	public ResponseEntity<Object> getMaterialByTypeCodeByDate(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getMaterialByTypeCodeByDate(inputMap);
	}

	@PostMapping("/createMaterial")
	public ResponseEntity<Object> createMaterial(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.createMaterial(inputMap);
	}

	@PostMapping("/getMaterialDetails")
	public ResponseEntity<Object> getMaterialDetails(@RequestBody Map<String, Object> inputMap) throws Exception {
		return objMaterialService.getMaterialDetails(inputMap);
	}

	@PostMapping("/deleteMaterial")
	public ResponseEntity<Object> deleteMaterial(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.deleteMaterial(inputMap);
	}

	@PostMapping("/getMaterialEdit")
	public ResponseEntity<Object> getMaterialEdit(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getMaterialEdit(inputMap);
	}

	@PostMapping(value = "/getMaterialByID", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialByID(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialByID(inputMap);
	}
	
	@PostMapping("/updateMaterial")
	public ResponseEntity<Object> UpdateMaterial(@RequestBody Map<String, Object> inputMap) throws Exception {
		return objMaterialService.UpdateMaterial(inputMap);
	}
	
	@PostMapping("/CloudUploadattachments")
	public Elnmaterial CloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Integer nmaterialcatcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate,@RequestParam Integer isMultitenant)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return objMaterialService.CloudUploadattachments(file, nmaterialcatcode, filename, fileexe, usercode, currentdate,isMultitenant);
	}
	
	@PostMapping("/getAttachments")
	public Map<String, Object> getAttachments(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getAttachments(inputMap);
	}
	
	@PostMapping("/cloudUploadFilesWithTags")
	public Material cloudUploadFilesWithTags(@RequestParam MultipartFile file,
			@RequestParam("order") Integer nmaterialcatcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate,@RequestParam Integer isMultitenant)
			throws IOException {
		return objMaterialService.cloudUploadFilesWithTags(file, nmaterialcatcode, filename, fileexe, usercode, currentdate,isMultitenant);
	}
	
	/**
	 * 
	 * done material by Sathishkumar chandrasekar 15-10-2023
	 * 
	 * 
	 */
	
	@PostMapping(value = "/getElnMaterial", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterial(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) objMaterialService.getElnMaterial(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialByFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialByFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) objMaterialService.getElnMaterialByFilter(inputMap);
	}
	
	@PostMapping(value = "/getELNMaterialPropsForFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getELNMaterialPropsForFilter(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) objMaterialService.getELNMaterialPropsForFilter(nsiteInteger);
	}
	
	@PostMapping(value = "/getELNPropsAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getELNPropsAll(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) objMaterialService.getELNPropsAll(nsiteInteger);
	}
	
	@PostMapping(value = "/getELNMaterialProps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialProps(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialProps(nsiteInteger);
	}
	
	@PostMapping(value = "/getELNMaterialTypeBasedCat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeBasedCat(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) objMaterialService.getMaterialTypeBasedCat(inputMap);
	}
	
	@PostMapping("/createElnMaterial")
	public ResponseEntity<Object> createElnMaterial(@RequestBody Elnmaterial obj) throws Exception {

		return objMaterialService.createElnMaterial(obj);
	}
	
	@PostMapping("/updateElnMaterial")
	public ResponseEntity<Object> updateElnMaterial(@RequestBody Elnmaterial obj) throws Exception {

		return objMaterialService.updateElnMaterial(obj);
	}
	
	@PostMapping("/getELNMaterialBySearchField")
	public ResponseEntity<Object> getELNMaterialBySearchField(@RequestBody Map<String, Object> inputMap) throws Exception {

		return objMaterialService.getELNMaterialBySearchField(inputMap);
	}
	
	@PostMapping("/getElnMaterialOnProtocol")
	public ResponseEntity<Object> getElnMaterialOnProtocol(@RequestBody ElnresultUsedMaterial inputMap) throws Exception {

		return objMaterialService.getElnMaterialOnProtocol(inputMap);
	}
	
	@PostMapping("/geMaterialtAttachments")
	public Map<String, Object> geMaterialtAttachments(@RequestBody Map<String, Object> inputMap) throws Exception {
		return objMaterialService.geMaterialtAttachments(inputMap);
	}

	@PostMapping("/materialCloudUploadattachments")
	public Elnmaterial materialCloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam Integer nmaterialtypecode,
			@RequestParam Integer nmaterialcatcode,
			@RequestParam Integer nmaterialcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer isMultitenant,@RequestParam Integer nsitecode)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return objMaterialService.materialCloudUploadattachments(file, nmaterialtypecode, nmaterialcatcode,
				nmaterialcode, filename, fileexe, usercode, currentdate, isMultitenant,nsitecode);
	}

	@GetMapping("/materialView/{param}/{fileid}")
	public ResponseEntity<InputStreamResource> materialView(@PathVariable String param, @PathVariable String fileid)
			throws IOException {
		return objMaterialService.materialView(param, fileid);
	}
	
	@PostMapping("/updateAssignedProjectOnMaterial")
	public ResponseEntity<Object> updateAssignedTaskOnMaterial(@RequestBody Elnmaterial material)throws Exception
	{
		return objMaterialService.updateAssignedTaskOnMaterial(material);
	}
	
	@PostMapping("/getAssignedProjectOnMaterial")
	public ResponseEntity<Object> getAssignedTaskOnMaterial(@RequestBody Elnmaterial material)throws Exception
	{
		return objMaterialService.getAssignedTaskOnMaterial(material);
	}
	
	@PostMapping("/uploadLinkforMaterial")
	public ResponseEntity<Object> uploadLinkforMaterial(@RequestBody MaterialLinks materiallink)throws Exception
	{
		return objMaterialService.uploadLinkforMaterial(materiallink);
	}
	
	@PostMapping("/getLinksOnMaterial")
	public ResponseEntity<Object> getLinksOnMaterial(@RequestBody MaterialLinks materiallink)throws Exception
	{
		return objMaterialService.getLinksOnMaterial(materiallink);
	}
	
	@PostMapping("/deleteLinkforMaterial")
	public ResponseEntity<Object> deleteLinkforMaterial(@RequestBody MaterialLinks materiallink)throws Exception
	{
		return objMaterialService.deleteLinkforMaterial(materiallink);
	}
	
	@PostMapping("/updatematerialprojecthistory")
	public ResponseEntity<Object> updatematerialprojecthistory(@RequestBody MaterialProjectHistory[] materiallist)throws Exception
	{
		return objMaterialService.updatematerialprojecthistory(materiallist);
	}
	@PostMapping("/geSampleList")
	public ResponseEntity<Object> geSampleList(@RequestBody ElnresultUsedSample inputMap) throws Exception {
		return objMaterialService.getSampleList(inputMap);
	}
	
	
}
