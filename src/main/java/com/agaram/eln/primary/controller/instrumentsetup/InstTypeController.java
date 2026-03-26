package com.agaram.eln.primary.controller.instrumentsetup;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.instrumentsetup.InstrumentType;
import com.agaram.eln.primary.service.instrumentsetup.InstTypeService;

/**
 * This controller is used to dispatch the input request to its relevant method to access
 * the InstType Service methods.
 * @author ATE153
 * @version 1.0.0
 * @since   11- Nov- 2019
 */
@RestController
public class InstTypeController {
	
	@Autowired
	InstTypeService typeService;
	
	/**
     * This method is used to retrieve list of all instrument types
     * @return list of all instrument types
     */
    @PostMapping("/getInstType")
    public List<InstrumentType> getInstType()throws Exception {
        return  typeService.getInstType();
    }

}
