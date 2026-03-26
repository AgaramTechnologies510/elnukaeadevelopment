package com.agaram.eln.primary.controller.material;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agaram.eln.primary.model.material.Supplier;
import com.agaram.eln.primary.service.material.SupplierService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	ObjectMapper objMapper = new ObjectMapper();
	
	@Autowired
	private SupplierService supplierService;
	
	@PostMapping(value = "/getSupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSupplier(@RequestBody Map<String, Object> inputMap) throws Exception {
		Integer nsiteInteger = (Integer) inputMap.get("nsitecode");
		return (ResponseEntity<Object>) supplierService.getSupplier(nsiteInteger);
	}
	
	@PostMapping("/createSupplier")
	public ResponseEntity<Object> createSupplier(@RequestBody Map<String, Object> inputMap) throws Exception {
		Supplier objSupplier = objMapper.convertValue(inputMap.get("supplier"), new TypeReference<Supplier>() {});
		return supplierService.createSupplier(objSupplier);
	}

	@PostMapping("/updateSupplier")
	public ResponseEntity<Object> updateSupplier(@RequestBody Map<String, Object> inputMap) throws Exception {
		Supplier objSupplier = objMapper.convertValue(inputMap.get("supplier"), new TypeReference<Supplier>() {	});
		return supplierService.updateSupplier(objSupplier);
	}

	@PostMapping("/deleteSupplier")
	public ResponseEntity<Object> deleteSupplier(@RequestBody Map<String, Object> inputMap) throws Exception {
		Supplier objSupplier = objMapper.convertValue(inputMap.get("supplier"), new TypeReference<Supplier>() { });
		return supplierService.deleteSupplier(objSupplier);
	}

	@PostMapping("/getActiveSupplierById")
	public Supplier getActiveSupplierById(@RequestBody Map<String, Object> inputMap) throws Exception {
		int nunitCode = (int) inputMap.get("nsuppliercode");
		return supplierService.getActiveSupplierById(nunitCode);
	}

}