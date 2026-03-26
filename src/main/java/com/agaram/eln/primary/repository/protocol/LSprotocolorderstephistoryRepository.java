package com.agaram.eln.primary.repository.protocol;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.protocols.LSprotocolorderstephistory;

public interface LSprotocolorderstephistoryRepository extends JpaRepository<LSprotocolorderstephistory, Integer> {

	List<LSprotocolorderstephistory> findByProtocolordercode(Long protocolordercode);

	List<LSprotocolorderstephistory> findByBatchcode(Long batchcode);

	List<LSprotocolorderstephistory> findByProtocolordercodeOrderByProtocolorderstephistorycodeDesc(
			Long protocolordercode);

	List<LSprotocolorderstephistory> findByProtocolordercodeAndStepstartdateBetweenOrderByProtocolorderstephistorycodeDesc(
			Long protocolordercode, Date fromdate, Date todate);

	List<LSprotocolorderstephistory>findByProtocolordercodeAndStependdateBetweenOrderByProtocolorderstephistorycodeDesc(
			Long protocolordercode, Date fromdate, Date todate);

	List<LSprotocolorderstephistory> findByBatchcodeAndStepstartdateBetweenAndStependdateIsNullOrBatchcodeAndStepstartdateIsNullAndStependdateBetween(
			Long batchcode, Date fromDate, Date toDate, Long batchcode2, Date fromDate2, Date toDate2);


}
