package com.agaram.eln.primary.controller.material;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.global.FileDTO;
import com.agaram.eln.primary.model.material.ElnmaterialInventory;
import com.agaram.eln.primary.model.material.ElnmaterialInventory.InventoryInterface;
import com.agaram.eln.primary.model.material.ElnresultUsedMaterial;
import com.agaram.eln.primary.model.material.MaterilaInventoryLinks;
import com.agaram.eln.primary.model.sample.ElnresultUsedSample;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;
import com.agaram.eln.primary.service.material.MaterialInventoryService;
import com.agaram.eln.primary.service.material.MaterialService;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "materialinventory")
public class MaterialInventoryController {

	@Autowired
	private MaterialInventoryService materialInventoryService;
	
	@Autowired
	private MaterialService materialService;

	@PostMapping(value = "/getMaterialInventory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) materialInventoryService.getMaterialInventory(nsiteInteger);
	}

	@PostMapping("/getMaterialInventorycombo")
	public ResponseEntity<Object> getMaterialInventorycombo(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		Integer nmaterialtypecode = (Integer) inputMap.get("nmaterialtypecode");
		Integer nmaterialcatcode = null;
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		if (inputMap.containsKey("nmaterialcatcode")) {
			nmaterialcatcode = (int) inputMap.get("nmaterialcatcode");
		}
		return (ResponseEntity<Object>) materialInventoryService.getMaterialInventorycombo(nmaterialtypecode,nmaterialcatcode,nsiteInteger);
	}

	@PostMapping(value = "/getMaterialInventoryByID")
	public ResponseEntity<Object> getMaterialInventoryByID(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getMaterialInventoryByID(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInventoryIDByDate")
	public ResponseEntity<Object> getMaterialInventoryIDByDate(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getMaterialInventoryIDByDate(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInvCombo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialInvCombo(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer ntypecode = (Integer) inputMap.get("nmaterialtypecode");
		Integer nmaterialcatcode = (Integer) inputMap.get("nmaterialcatcode");
		Integer nflag = (Integer) inputMap.get("nflag");
		
		if(nflag == 1) {
			return (ResponseEntity<Object>) materialInventoryService.getMaterialInvCombo(ntypecode,nflag);
		}else {
			return (ResponseEntity<Object>) materialInventoryService.getMaterialInvCombo(nmaterialcatcode,nflag);
		}
	}
	
	@PostMapping(value = "/getMaterialTypeDesign", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeDesign(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getMaterialTypeDesign(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInventoryBySearchField")
	public ResponseEntity<Object> getMaterialInventoryBySearchField(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getMaterialInventoryBySearchField(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInventoryByStorageID")
	public ResponseEntity<Object> getMaterialInventoryByStorageID(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getMaterialInventoryByStorageID(inputMap);
	}

	@PostMapping(value = "/createMaterialInventory")
	public ResponseEntity<Object> createMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.createMaterialInventory(inputMap);
	}
	
	@PostMapping(value = "/updateMaterialInventory")
	public ResponseEntity<Object> UpdateMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.UpdateMaterialInventory(inputMap);
	}

	@PostMapping(value = "/getQuantityTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getQuantityTransaction(@RequestBody Map<String, Object> inputMap) throws Exception {
		return (ResponseEntity<Object>) materialInventoryService.getQuantityTransaction(inputMap);
	}

	@PostMapping(value = "/getMaterialInventoryDetails")
	public ResponseEntity<Object> getMaterialInventoryDetails(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return materialInventoryService.getMaterialInventoryDetails(inputMap);
	}

	@PostMapping(value = "/deleteMaterialInventory")
	public ResponseEntity<Object> deleteMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.deleteMaterialInventory(inputMap);
	}

	@PostMapping(value = "/updateMaterialStatus")
	public ResponseEntity<Object> updateMaterialStatus(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.updateMaterialStatus(inputMap);
	}
	
	@PostMapping(value = "/deleteMaterialStatus")
	public ResponseEntity<Object> deleteMaterialStatus(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.updateMaterialStatus(inputMap);
	}

	@PostMapping(value = "/getMaterialInventoryEdit")
	public ResponseEntity<Object> getMaterialInventoryEdit(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getMaterialInventoryEdit(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInventorySearchByID", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialInventorySearchByID(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getMaterialInventorySearchByID(inputMap);
	}
	
	@PostMapping("/CloudUploadattachments")
	public ElnmaterialInventory CloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Integer nmaterialinventorycode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate,@RequestParam Integer isMultitenant)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return materialInventoryService.CloudUploadattachments(file, nmaterialinventorycode, filename, fileexe, usercode, currentdate,isMultitenant);
	}
	
	@PostMapping(value = "/getAttachments")
	public Map<String, Object> getAttachments(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getAttachments(inputMap);
	}
	
	@PostMapping(value = "/getMaterialInventorytransDetails")
	public ResponseEntity<Object> getMaterialInventorytransDetails(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return materialInventoryService.getMaterialInventorytransDetails(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByIdBarCode")
	public ResponseEntity<Object> getElnMaterialInventoryByIdBarCode(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return materialInventoryService.getElnMaterialInventoryByIdBarCode(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByIdBarCodeFilter")
	public ResponseEntity<Object> getElnMaterialInventoryByIdBarCodeFilter(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return materialInventoryService.getElnMaterialInventoryByIdBarCodeFilter(inputMap);
	}
	
	/**
	 * Added by sathishkumar chandrasekar for new inventory changes 
	 * dated on 18-10-2023
	 */
	
	@PostMapping(value = "/getElnMaterialInventory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getElnMaterialInventory(inputMap);
	}
	@PostMapping(value = "/getElnMaterialInventoryonprotocol", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialInventoryonprotocol(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getElnMaterialInventoryonprotocol(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialInventoryByFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getElnMaterialInventoryByFilter(inputMap);
	}
	@PostMapping(value = "/getElnMaterialInventoryByFilterForprotocol", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialInventoryByFilterForprotocol(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getElnMaterialInventoryByFilterForprotocol(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialByFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialByFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialService.getElnMaterialByFilter(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByStorage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getElnMaterialInventoryByStorage(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getElnMaterialInventoryByStorage(inputMap);
	}
	
	@PostMapping(value = "/getELNInventoryProps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getELNInventoryProps(@RequestBody Map<String, Object> inputMap) throws Exception {

		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		
		return (ResponseEntity<Object>) materialInventoryService.getELNInventoryProps(nsiteInteger);
	}
	
	@PostMapping(value = "/getELNMaterialTypeBasedCat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeBasedCat(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getMaterialTypeBasedCat(inputMap);
	}
	
	@PostMapping(value = "/getELNMaterialCatBasedMaterial", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getELNMaterialCatBasedMaterial(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) materialInventoryService.getELNMaterialCatBasedMaterial(inputMap);
	}
	
	@PostMapping(value = "/createElnMaterialInventory")
	public ResponseEntity<Object> createElnMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.createElnMaterialInventory(inputMap);
	}
	
	@PostMapping(value = "/updateElnMaterialInventory")
	public ResponseEntity<Object> updateElnMaterialInventory(@RequestBody ElnmaterialInventory objElnmaterialInventory) throws Exception {
		return materialInventoryService.updateElnMaterialInventory(objElnmaterialInventory);
	}
	
	@PostMapping(value = "/updateElnMaterialInventoryStock")
	public ResponseEntity<Object> updateElnMaterialInventoryStock(@RequestBody ElnmaterialInventory objElnmaterialInventory) throws Exception {
		return materialInventoryService.updateElnMaterialInventoryStock(objElnmaterialInventory);
	}
	
	@PostMapping(value = "/getPathOnInventory")
	public ResponseEntity<Object> getPathOnInventory(@RequestBody Integer objElnmaterialInventory) throws Exception {
		return materialInventoryService.getPathOnInventory(objElnmaterialInventory);
	}
	
	@PostMapping(value = "/OsearchElnMaterialInventory")
	public ResponseEntity<Object> OsearchElnMaterialInventory(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.OsearchElnMaterialInventory(inputMap);
	}
	
	@PostMapping(value = "/getELNMaterialBySearchField")
	public ResponseEntity<Object> getELNMaterialBySearchField(@RequestBody Map<String, Object> inputMap) throws Exception {

		return materialInventoryService.getELNMaterialBySearchField(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryById")
	public ResponseEntity<Object> getElnMaterialInventoryById(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.getElnMaterialInventoryById(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByMaterial")
	public ResponseEntity<Object> getElnMaterialInventoryByMaterial(@RequestBody List<Integer> lstMaterial) throws Exception {
		return materialInventoryService.getElnMaterialInventoryByMaterial(lstMaterial);
	}
	
	@PostMapping(value = "/getInventorytransactionhistory")
	public ResponseEntity<Object> getInventorytransactionhistory(@RequestBody ElnresultUsedMaterial resultusedmaterial) {
		return materialInventoryService.getInventorytransactionhistory(resultusedmaterial);
	}
	
	@PostMapping(value = "/getInventorysampletransactionhistory")
	public ResponseEntity<Object>getInventorysampletransactionhistory(@RequestBody ElnresultUsedSample resultusedsample) {
		return materialInventoryService.getInventorysampletransactionhistory(resultusedsample);
	}
	
	@PostMapping("/uploadinvimages")
	public Map<String, Object> uploadInvimages(@RequestParam MultipartFile file,
			@RequestParam String originurl, @RequestParam String username,
			@RequestParam String sitecode,@RequestParam("selectedMaterial") Integer nmaterialcatcode,
			@RequestParam Integer usercode,@RequestParam String smiles
			,@RequestParam String moljson)throws Exception {
		return materialInventoryService.uploadInvimages(file, originurl, username, sitecode,nmaterialcatcode,usercode,smiles,moljson);
	}
	
	@PostMapping("/updateinvimages")
	public Map<String, Object> updateinvimages(@RequestParam MultipartFile file,
			@RequestParam String fileid, @RequestParam String username,
			@RequestParam String sitecode,@RequestParam("selectedMaterial") Integer nmaterialcatcode,
			@RequestParam Integer usercode,@RequestParam String smiles
			,@RequestParam String moljson)throws Exception {
		return materialInventoryService.updateinvimages(file, fileid, username, sitecode,nmaterialcatcode,usercode,smiles,moljson);
	}
	
	@PostMapping(value = "downloadinvimagesFileDTO")
	public List<FileDTO> downloadinvimagesFileDTO(@RequestBody Map<String, Object> inputMap)
			throws IllegalStateException, IOException {
		
		String tenant = inputMap.get("tenant").toString();
		Integer nmaterialcode = Integer.parseInt(inputMap.get("nmaterialcode").toString());
		
		List<FileDTO> returnObj = materialInventoryService.downloadinvimagesFileDTO(tenant,nmaterialcode);

		return returnObj;
	}
	
	@PostMapping("/deleteinvimages")
	public void deleteinvimages(@RequestBody Map<String, Object> inputMap)throws Exception {
		String fileName = inputMap.get("fileName").toString();
		materialInventoryService.deleteinvimages(fileName);
	}
	
	@PostMapping("/uploadInvimagesSql")
	public Map<String, Object> uploadInvimagesSql(@RequestParam MultipartFile file,
			@RequestParam String originurl, @RequestParam String username,
			@RequestParam String sitecode,@RequestParam("selectedMaterial") Integer nmaterialcatcode,
			@RequestParam Integer usercode,@RequestParam String smiles
			,@RequestParam String moljson) throws IOException {
		return materialInventoryService.uploadInvimagesSql(file, originurl, username, sitecode,nmaterialcatcode,usercode,smiles,moljson);
	}
	
	@PostMapping("/updateinvimagesSql")
	public Map<String, Object> updateinvimagesSql(@RequestParam MultipartFile file,
			@RequestParam String fileid, @RequestParam String username,
			@RequestParam String sitecode,@RequestParam("selectedMaterial") Integer nmaterialcatcode,
			@RequestParam Integer usercode,@RequestParam String smiles
			,@RequestParam String moljson)throws Exception {
		return materialInventoryService.updateinvimagesSql(file, fileid, username, sitecode,nmaterialcatcode,usercode,smiles,moljson);
	}
	
	@PostMapping(value = "downloadinvimagesSQLFileDTO")
	public List<FileDTO> downloadinvimagesSQLFileDTO(@RequestBody Map<String, Object> inputMap) throws IllegalStateException, IOException {

		Integer nmaterialcode = Integer.parseInt(inputMap.get("nmaterialcode").toString());
		
		List<FileDTO> returnObj = materialInventoryService.downloadinvimagesSQLFileDTO(nmaterialcode);
		
		return returnObj;
	}
	
	@PostMapping("/deleteinvimagesSQL")
	public void deleteinvimagesSQL(@RequestBody Map<String, Object> inputMap)throws Exception {
		String fileName = inputMap.get("fileName").toString();
		materialInventoryService.deleteinvimagesSQL(fileName);
	}
	@PostMapping("/insertLinkforInvertory")
	public ResponseEntity<Object> insertLinkforInvertory(@RequestBody MaterilaInventoryLinks objInv) throws Exception {
		return materialInventoryService.insertLinkforInvertory(objInv);
	}

	@PostMapping("/getLinksForInventory")
	public ResponseEntity<Object> getLinksForInventory(@RequestBody MaterilaInventoryLinks objInv) throws Exception {
		return materialInventoryService.getLinksforInvertory(objInv);
	}
	
	@PostMapping("/deleteLinkforInventory")
	public ResponseEntity<Object> deleteLinkforInvertory(@RequestBody MaterilaInventoryLinks objInv) throws Exception {
		return materialInventoryService.deleteLinkforInvertory(objInv);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryCount", produces = MediaType.APPLICATION_JSON_VALUE)
	public long getElnMaterialInventoryCount(@RequestBody LSSiteMaster inputMap) throws Exception {
		
		return  materialInventoryService.getElnMaterialInventoryCount(inputMap);
	}
	
	@PostMapping(value = "/getElnMateriallInventoryByFilter")
	public ResponseEntity<Object> getElnMateriallInventoryByFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		return materialInventoryService.getElnMateriallInventoryByFilter(inputMap);
	}
	
	@PostMapping(value = "/getElnMaterialInventoryByMaterialforreducequantity")
	public ResponseEntity<Object> getElnMaterialInventoryByMaterialforreducequantity(@RequestBody List<Integer> lstMaterial) throws Exception {
		return materialInventoryService.getElnMaterialInventoryByMaterialforreducequantity(lstMaterial);
	}
	
	@PostMapping(value = "/getinventoryList")
	public List<InventoryInterface> getinventoryList(@RequestBody List<Integer> inputMap) throws Exception {

		return materialInventoryService.getinventoryList(inputMap);
	}
	
	@PostMapping(value = "/getMaterialAllInventory")
	public ResponseEntity<Object> getMaterialAllInventory(@RequestBody LoggedUser objuser) throws Exception {
		return materialInventoryService.getMaterialAllInventory(objuser);
	}

}