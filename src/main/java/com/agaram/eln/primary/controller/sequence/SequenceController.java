package com.agaram.eln.primary.controller.sequence;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.fetchmodel.sequence.SequenceTablesh;
import com.agaram.eln.primary.model.sequence.SequenceTable;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.service.sequence.SequenceService;

@RestController
@RequestMapping(value = "/Sequence", method = RequestMethod.POST)
public class SequenceController {
	
	@Autowired
    private SequenceService sequenceservice;
    
	@PostMapping("/getAllSequence")
	public List<SequenceTablesh> getAllSequence(@RequestBody LSuserMaster objClass)
	{
		return sequenceservice.getAllSequence();
	}
	
	@PostMapping("/updatesequence")
	public SequenceTable updatesequence(@RequestBody SequenceTable objClass) throws ParseException
	{
		return sequenceservice.updatesequence(objClass);
	}
}
