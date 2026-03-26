package com.agaram.eln.primary.controller.equipment;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.equipment.Equipment;
import com.agaram.eln.primary.service.equipment.EquipmentService;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;
	
	@PostMapping(value = "/getEquipment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipment(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) equipmentService.getEquipment(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentOnTransaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentOnTransaction(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) equipmentService.getEquipmentOnTransaction(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentByFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentByFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		
		return (ResponseEntity<Object>) equipmentService.getEquipmentByFilter(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentPropsForFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentPropsForFilter(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) equipmentService.getEquipmentPropsForFilter(nsiteInteger);
	}
	
	@PostMapping(value = "/getEquipmentProps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentProps(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) equipmentService.getEquipmentProps(nsiteInteger);
	}
	
	@PostMapping(value = "/getEquipmentTransactionProps", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentTransactionProps(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) equipmentService.getEquipmentTransactionProps(nsiteInteger);
	}
	
	@PostMapping(value = "/getEquipmentTypeBasedCat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentTypeBasedCat(@RequestBody Map<String, Object> inputMap) throws Exception {
		return (ResponseEntity<Object>) equipmentService.getEquipmentTypeBasedCat(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentTypeBasedCatOnTrans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentTypeBasedCatOnTrans(@RequestBody Map<String, Object> inputMap) throws Exception {
		return (ResponseEntity<Object>) equipmentService.getEquipmentTypeBasedCatOnTrans(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentCatBased", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentCatBased(@RequestBody Map<String, Object> inputMap) throws Exception {
		return (ResponseEntity<Object>) equipmentService.getEquipmentCatBased(inputMap);
	}
	
	@PostMapping(value = "/getEquipmentCatBasedOnTrans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEquipmentCatBasedOnTrans(@RequestBody Map<String, Object> inputMap) throws Exception {
		return (ResponseEntity<Object>) equipmentService.getEquipmentCatBasedOnTrans(inputMap);
	}
	
	@PostMapping("/createEquipment")
	public ResponseEntity<Object> createEquipment(@RequestBody Equipment obj) throws Exception {
		return equipmentService.createEquipment(obj);
	}
	
	@PostMapping("/updateELNEquipment")
	public ResponseEntity<Object> updateELNEquipment(@RequestBody Equipment obj) throws Exception {

		return equipmentService.updateELNEquipment(obj);
	}
	
	@PostMapping("/updateEquipment")
	public ResponseEntity<Object> updateEquipment(@RequestBody Equipment obj) throws Exception {

		return equipmentService.updateEquipment(obj);
	}
	
	@PostMapping("/updateStatus")
	public ResponseEntity<Object> updateStatus(@RequestBody Equipment obj) throws Exception {

		return equipmentService.updateStatus(obj);
	}
	
	@PostMapping("/updateElnEquipmentCallibrate")
	public ResponseEntity<Object> updateElnEquipmentCallibrate(@RequestBody Equipment obj) throws Exception {

		return equipmentService.updateElnEquipmentCallibrate(obj);
	}
	
	@PostMapping("/updateElnEquipmentMaintanance")
	public ResponseEntity<Object> updateElnEquipmentMaintanance(@RequestBody Equipment obj) throws Exception {
		return equipmentService.updateElnEquipmentMaintanance(obj);
	}
	
	@PostMapping("/getEquipmentBySearchField")
	public ResponseEntity<Object> getEquipmentBySearchField(@RequestBody Map<String, Object> inputMap) throws Exception {
		return equipmentService.getEquipmentBySearchField(inputMap);
	}
	
	@PostMapping("/OsearchEquipment")
	public ResponseEntity<Object> OsearchEquipment(@RequestBody Map<String, Object> inputMap) throws Exception {
		return equipmentService.OsearchEquipment(inputMap);
	}
	
	@PostMapping("/onGetEquipmentSelect")
	public ResponseEntity<Object> onGetEquipmentSelect(@RequestBody Map<String, Object> inputMap)
			throws Exception {
		return equipmentService.onGetEquipmentSelect(inputMap);
	}
	
	@PostMapping("/onCheckItsUsed")
	public Boolean onCheckItsUsed(@RequestBody Equipment obj)
			throws Exception {
		return equipmentService.onCheckItsUsed(obj);
	}
	
	@PostMapping("/updatesequenceondefault")
	public Boolean updatesequenceondefault(@RequestBody Equipment obj)
			throws Exception {
		return equipmentService.updatesequenceondefault(obj);
	}
	
	@PostMapping(value = "/EquipmentImportDatatoStore")
	public ResponseEntity<Map<String, Object>>EquipmentImportDatatoStore(@RequestBody Map<String, Object> inputMap) throws ParseException{
		return equipmentService.EquipmentImportDatatoStore(inputMap);
		
	}
	
}
