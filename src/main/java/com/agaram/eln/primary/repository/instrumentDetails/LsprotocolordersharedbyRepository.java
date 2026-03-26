package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordersharedby.ProtocolShareByInterface;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;

public interface LsprotocolordersharedbyRepository extends JpaRepository<Lsprotocolordersharedby, Integer>{

	ProtocolShareByInterface findBySharebyunifiedidAndSharetounifiedidAndProtocoltypeAndShareprotocolordercode(
			String sharebyunifiedid, String sharetounifiedid, Integer protocoltype, Long shareprotocolordercode);

//	List<Lsprotocolordersharedby> findBySharebyunifiedidAndProtocoltypeAndSharestatusOrderBySharedbytoprotocolordercodeDesc(
//			String sharebyunifiedid, Integer protocoltype, int i);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndProtocoltypeAndSharestatus(String sharebyunifiedid,
			Integer protocoltype, int i);

	Lsprotocolordersharedby findBySharedbytoprotocolordercode(Long sharedbytoprotocolordercode);

//	int countBySharebyunifiedidAndProtocoltypeAndSharestatusOrderBySharedbytoprotocolordercodeDesc(
//			String unifielduserid, Integer protocoltype, int i);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String sharebyunifiedid, Integer protocoltype, int i, Date fromdate, Date todate);

	int countBySharebyunifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String sharebyunifiedid, Integer protocoltype, int i, Date fromdate, Date fromdate2);

	Lsprotocolordersharedby findByShareprotocolordercodeAndSharestatus(Long shareprotocolordercode, int i);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String string, int i, Date fromdate, Date todate);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, String unifieduserid2, int i, Date fromdate, Date todate);

	Lsprotocolordersharedby findByShareprotocolordercode(Long protocolordercode);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, String orderflag, int i, Date fromdate, Date todate);

	List<Lsprotocolordersharedby>  findBySharebyunifiedidAndProtocoltypeAndOrderflagAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, Integer protocoltype, String orderflag, String unifieduserid2, int i, Date fromdate,
			Date todate);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndOrderflagAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, String orderflag, String unifieduserid2, int i, Date fromdate, Date todate);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndProtocoltypeAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, Integer protocoltype, String unifieduserid2, int i, Date fromdate, Date todate);

	List<Lsprotocolordersharedby> findBySharebyunifiedidAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String unifieduserid, Integer protocoltype, String orderflag, int i, Date fromdate, Date todate);

	List<Lsprotocolordersharedby> findByShareprotocolordercodeIn(ArrayList<Long> ordercode);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			Integer testcode);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			Integer testcode, LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate,
			LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate, Integer testcode);


	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate,
			LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate, Integer testcode);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<ProtocolShareByInterface> findBySharebyunifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharedbytoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	

	
	

}
