package com.agaram.eln.primary.service.sequence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.fetchmodel.sequence.SequenceTablesh;
import com.agaram.eln.primary.model.sequence.SequenceTable;
import com.agaram.eln.primary.repository.sequence.SequenceTableRepository;

@Service
public class SequenceService {
	@Autowired
	private SequenceTableRepository sequencetableRepository;
	
	public List<SequenceTablesh> getAllSequence()
	{
		return sequencetableRepository.findBySequencecodeNotOrderBySequencecode(-1);
	}
	
	public SequenceTable updatesequence(SequenceTable objClass) throws ParseException
	{
		Date currentdate = commonfunction.getCurrentUtcTime();
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");

		SequenceTable seqobj = sequencetableRepository.findBySequencecode(objClass.getSequencecode());
		
		if(objClass.getResetperiod() != null  && seqobj.getResetperiod() != objClass.getResetperiod()) {
			if( objClass.getResetperiod() == 3) {
				objClass.setSequencemonth(Integer.parseInt(month.format(currentdate)));
			}else if(objClass.getResetperiod() == 4) {
				objClass.setSequenceyear(Integer.parseInt(year.format(currentdate)));
			}
		}
		Integer monthVal = objClass.getSequencemonth() != null ? objClass.getSequencemonth() : seqobj.getSequencemonth();
		Integer yearVal  = objClass.getSequenceyear()  != null ? objClass.getSequenceyear()  : seqobj.getSequenceyear();

		sequencetableRepository.updatesequencedata(objClass.getResetperiod(),objClass.getSequenceview(),
				 objClass.getSequenceformat(),objClass.getSeperator(),objClass.getSequencecode(),monthVal,yearVal);
		
		return objClass;
	}
}
