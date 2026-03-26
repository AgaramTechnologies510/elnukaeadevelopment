package com.agaram.eln.primary.controller.material;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.agaram.eln.primary.model.material.Manufacturer;
import com.agaram.eln.primary.service.material.ManufacturerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/manufacturer", method = RequestMethod.POST)
public class ManufacturerController {
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	ObjectMapper objMapper = new ObjectMapper();
	
	@PostMapping(value = "/getManufacturer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getManufacturer(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) manufacturerService.getManufacturer(nsiteInteger);
	}
	
	@PostMapping("/createManufacturer")
	public ResponseEntity<Object> createManufacturer(@RequestBody Map<String, Object> inputMap) throws Exception {
		Manufacturer objManufacturer = objMapper.convertValue(inputMap.get("manufacturer"), new TypeReference<Manufacturer>() {});
		return manufacturerService.createManufacturer(objManufacturer);
	}

	@PostMapping("/updateManufacturer")
	public ResponseEntity<Object> updateManufacturer(@RequestBody Map<String, Object> inputMap) throws Exception {
		Manufacturer objManufacturer = objMapper.convertValue(inputMap.get("manufacturer"), new TypeReference<Manufacturer>() {	});
		return manufacturerService.updateManufacturer(objManufacturer);
	}

	@PostMapping("/deleteManufacturer")
	public ResponseEntity<Object> deleteManufacturer(@RequestBody Map<String, Object> inputMap) throws Exception {
		Manufacturer objManufacturer = objMapper.convertValue(inputMap.get("manufacturer"), new TypeReference<Manufacturer>() { });
		return manufacturerService.deleteManufacturer(objManufacturer);
	}

	@PostMapping("/getActiveManufacturerById")
	public Manufacturer getActiveManufacturerById(@RequestBody Map<String, Object> inputMap) throws Exception {
		int nunitCode = (int) inputMap.get("nsuppliercode");
		return manufacturerService.getActiveManufacturerById(nunitCode);
	}
}