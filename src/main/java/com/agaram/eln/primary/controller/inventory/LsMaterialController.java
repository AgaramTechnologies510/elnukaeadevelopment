package com.agaram.eln.primary.controller.inventory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.agaram.eln.primary.model.instrumentDetails.LSinstrumentmaster;
import com.agaram.eln.primary.model.inventory.LSinstrument;
import com.agaram.eln.primary.model.inventory.LSmaterial;
import com.agaram.eln.primary.model.inventory.LSsection;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.service.inventory.LsMaterialService;

@RestController
@RequestMapping(value = "/Material", method = RequestMethod.POST)
public class LsMaterialController {
	@Autowired
    private LsMaterialService materialService;
	
	
	@GetMapping("/GetSection")
	public List<LSsection> getSection()throws Exception {
		return materialService.getSection();
	}
	
	/**
	 *For Get Masters Materials and Instruments
	 * 
	 * @param objMap
	 * @return List<class>
	 */
	
	@PostMapping("/getlsMaterial")
	public List<LSmaterial> getlsMaterial(@RequestBody LSuserMaster objuser)throws Exception {
		return materialService.getlsMaterial(objuser);
	}
	
	@PostMapping("/getLsInstrument")
	public List<LSinstrument> getLsInstrument(@RequestBody LSuserMaster objuser)throws Exception {
		return materialService.getLsInstrument(objuser);
	}
	
	@PostMapping("/getLsInstrumentMaster")
	public List<LSinstrumentmaster> getLsInstrumentMaster(@RequestBody LSuserMaster objuser)throws Exception {
		return materialService.getLsInstrumentMaster(objuser);
	}
	
	@PostMapping("/getEquipmentDetails")
	public Map<String, Object> getEquipmentDetails(@RequestBody Map<String, Object> objMap)throws Exception
	{
		return materialService.getEquipmentDetails(objMap);
	}
	
	@PostMapping("/getMaterialDetails")
	public Map<String, Object> getMaterialDetails(@RequestBody Map<String, Object> objMap) throws Exception
	{
		return materialService.getMaterialDetails(objMap);
	}
}
