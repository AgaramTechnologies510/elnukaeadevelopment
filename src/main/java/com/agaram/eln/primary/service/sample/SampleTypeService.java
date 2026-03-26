package com.agaram.eln.primary.service.sample;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.model.sample.SampleType;
import com.agaram.eln.primary.repository.sample.SampleBarcodeMapRepository;
import com.agaram.eln.primary.repository.sample.SampleTypeRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class SampleTypeService {
	
	@Autowired
	SampleTypeRepository sampleTypeRepository;
	@Autowired
	SampleBarcodeMapRepository sampleBarcodeMapRepository;
	
	public ResponseEntity<Object> getSampleType(SampleType objSampleType) {
		List<SampleType> lstsampletype = objSampleType.getNsitecode().equals(0) 
				? sampleTypeRepository.findByNsampletypecodeNotAndNsitecodeNotAndNdefaultstatusOrderByNsampletypecodeDesc(-1, -1, 3)
				: sampleTypeRepository.findByNsampletypecodeNotAndNsitecodeAndNdefaultstatusOrderByNsampletypecodeDesc(
						-1, objSampleType.getNsitecode(), 3);
		return new ResponseEntity<>(lstsampletype, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getActiveSampleType(Integer nsitecode) {
		List<SampleType> lstgetSampleType = sampleTypeRepository
				.findByNsampletypecodeNotAndNstatusAndNsitecodeAndNdefaultstatusOrderByNsampletypecodeDesc(-1, 1, nsitecode, 3);
		return new ResponseEntity<>(lstgetSampleType, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> modifySampleType(SampleType objSampleType) throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		if(objSampleType.getNsampletypecode() == null) {
			
			List<SampleType> objlstTypes = sampleTypeRepository.
					findBySsampletypenameIgnoreCaseAndNsitecodeOrderByNsampletypecode(objSampleType.getSsampletypename(),objSampleType.getNsitecode());
			
			if(objlstTypes.isEmpty()) {
			
				objSampleType.setNdefaultstatus(3);
				objSampleType.setNstatus(1);
				objSampleType.setCreatedate(commonfunction.getCurrentUtcTime());
				objSampleType.setCreateby(objSampleType.getCreateby());
				objSampleType.setInfo("IDS_SUCCESS");
				sampleBarcodeMapRepository.saveAll(objSampleType.getLstbarcodes());
				sampleTypeRepository.save(objSampleType);
				return new ResponseEntity<>(objSampleType, HttpStatus.OK);
				
			}else {
				
				objSampleType.setInfo("IDS_FAIL_ALREADY_EXIST");
				return new ResponseEntity<>(objSampleType, HttpStatus.OK);
			}
		}
		else {
			List<SampleType> objlstTypes = sampleTypeRepository.findBySsampletypenameIgnoreCaseAndNsitecodeAndNsampletypecodeNot(
					objSampleType.getSsampletypename(),objSampleType.getNsitecode(),objSampleType.getNsampletypecode());
			
			if(objlstTypes.isEmpty()) {
				
				SampleType objMType = sampleTypeRepository.findByNsampletypecodeAndNstatus(objSampleType.getNsampletypecode(), 1);				
				
				objSampleType.setNdefaultstatus(objMType.getNdefaultstatus());
				objSampleType.setNstatus(1);
				objSampleType.setCreatedate(objMType.getCreatedate());
				objSampleType.setCreateby(objMType.getCreateby());
				objSampleType.setModifieddate(commonfunction.getCurrentUtcTime());
				
				sampleBarcodeMapRepository.saveAll(objSampleType.getLstbarcodes());
				sampleTypeRepository.save(objSampleType);
				
				objSampleType.setInfo("IDS_SUCCESS");
				return new ResponseEntity<>(objSampleType, HttpStatus.OK);
			}else {
				
				objSampleType.setInfo("IDS_FAIL_ALREADY_EXIST");
				return new ResponseEntity<>(objSampleType, HttpStatus.OK);
			}
		}				
		
	}
	
	public SampleType RetiredSample(SampleType objSampleType) {

		objSampleType.setNstatus(-1);
		try {
			objSampleType.setModifieddate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sampleTypeRepository.save(objSampleType);  	
		return objSampleType;
	}
	
	public ResponseEntity<Object> getMaterialTypeonId(SampleType objSampleType) {
		SampleType lsSampleType = sampleTypeRepository.findById(objSampleType.getNsampletypecode()).orElse(null);
		return new ResponseEntity<>(lsSampleType, HttpStatus.OK);
	}
}