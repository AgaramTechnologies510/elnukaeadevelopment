package com.agaram.eln.primary.repository.sheetManipulation;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.sheetManipulation.LSsheetupdates;

public interface LSsheetupdatesRepository  extends JpaRepository<LSsheetupdates, Integer> {
	public List<LSsheetupdates> findByfilecode(Integer filecode);

	public List<LSsheetupdates> findByfilecodeAndSheetmodifiedDateBetween(Integer filecode, Date fromDate, Date toDate);


}
