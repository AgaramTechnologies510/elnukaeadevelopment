package com.agaram.eln.primary.controller.equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agaram.eln.primary.model.equipment.EquipmentType;
import com.agaram.eln.primary.service.equipment.EquipmentTypeService;

@RestController
@RequestMapping("/equipmentType")
public class EquipmentTypeController {

	@Autowired
	private EquipmentTypeService equipmentTypeService;
	
	@PostMapping(value = "/getEquipmentype", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentype(@RequestBody EquipmentType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) equipmentTypeService.getEquipmentype(objMaterialType);
	}
	
	@PostMapping(value = "/createEquipmentType", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createEquipmentType(@RequestBody EquipmentType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) equipmentTypeService.createEquipmentType(objMaterialType);
	}
	
	@PostMapping(value = "/getEquipmentTypeField", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentTypeField(@RequestBody EquipmentType objMaterialType) throws Exception {		
		return (ResponseEntity<Object>) equipmentTypeService.getEquipmentTypeField(objMaterialType);
	}

}
