package com.agaram.eln.primary.controller.material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.material.MaterialConfig;
import com.agaram.eln.primary.model.material.MaterialType;
import com.agaram.eln.primary.service.material.MaterialTypeService;

@RestController
@RequestMapping("/materialType")
public class MaterialTypeController {
	@Autowired
	private MaterialTypeService objMaterialTypeService;
	
	@PostMapping(value = "/getMaterialType", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialType(@RequestBody MaterialType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.getMaterialType(objMaterialType);
	}
	
	@PostMapping(value = "/getMaterialTypeonId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeonId(@RequestBody MaterialType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.getMaterialTypeonId(objMaterialType);
	}
	
	@PostMapping(value = "/getMaterialTypeField", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMaterialTypeField(@RequestBody MaterialType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.getMaterialTypeField(objMaterialType);
	}
	
	@PostMapping(value = "/createMaterialType", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createMaterialType(@RequestBody MaterialType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.createMaterialType(objMaterialType);
	}
	
	@PostMapping(value = "/updateMaterialTypeField", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateMaterialTypeField(@RequestBody MaterialConfig objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.updateMaterialTypeField(objMaterialType);
	}
	
	@PostMapping(value ="/syncSamplestoType", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> syncSamplestoType() throws Exception {		
		return (ResponseEntity<Object>) objMaterialTypeService.syncSamplestoType();
	}
	@PostMapping("/RetiredMaterial")
	public MaterialType RetiredMaterial(@RequestBody MaterialType objMaterialType) throws Exception {
		return objMaterialTypeService.RetiredMaterial(objMaterialType);
	}
}
