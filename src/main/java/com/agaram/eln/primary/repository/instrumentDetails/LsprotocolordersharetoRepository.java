package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordershareto;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordershareto.ProtocolOrderShareToInterface;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LsprotocolordersharetoRepository extends JpaRepository<Lsprotocolordershareto, Integer>{

//	ProtocolOrderShareToInterface findBySharebyunifiedidAndSharetounifiedidAndProtocoltypeAndShareprotocolordercode(
//			String sharebyunifiedid, String sharetounifiedid, Integer protocoltype, Long shareprotocolordercode);
	public List<ProtocolOrderShareToInterface>findBySharebyunifiedidAndSharetounifiedidAndProtocoltypeAndShareprotocolordercode(
			String sharebyunifiedid, String sharetounifiedid, Integer protocoltype, Long shareprotocolordercode);

//	List<Lsprotocolordershareto> findBySharetounifiedidAndProtocoltypeAndSharestatusOrderBySharetoprotocolordercodeDesc(
//			String sharebyunifiedid, Integer protocoltype, int i);

	Lsprotocolordershareto findBySharetoprotocolordercode(Long sharetoprotocolordercode);

//	int countBySharetounifiedidAndProtocoltypeAndSharestatusOrderBySharetoprotocolordercodeDesc(String unifielduserid,
//			Integer protocoltype, int i);

	List<Lsprotocolordershareto> findBySharetounifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String sharetounifiedid, Integer protocoltype, int i, Date fromdate, Date todate);

	int countBySharetounifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String sharebyunifiedid, Integer protocoltype, int i, Date fromdate, Date fromdate2);

	Lsprotocolordershareto findBySharetoprotocolordercodeAndSharestatus(Long sharetoprotocolordercode, int i);

	Object findBySharetounifiedidAndSharestatusOrderBySharetoprotocolordercodeDesc(LSuserMaster lsselecteduser, int i);

	List<Lsprotocolordershareto> findBySharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String string, int i, Date fromdate, Date todate);

	Lsprotocolordershareto findByShareprotocolordercode(Long protocolordercode);

	List<Lsprotocolordershareto> findBySharetounifiedidAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String unifieduserid, String orderflag, int i, Date fromdate, Date todate);

	List<Lsprotocolordershareto> findBySharetounifiedidAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String unifieduserid, Integer protocoltype, String orderflag, int i, Date fromdate, Date todate);

	List<Lsprotocolordershareto> findByShareprotocolordercodeIn(ArrayList<Long> ordercode);

	List<Lsprotocolordershareto> findBySharetounifiedidAndSharedonBetweenAndSharestatus(String unifieduserid, Date fromdate, Date todate,
			int i);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate);

	List<Lsprotocolordershareto> findBySharetounifiedidAndSharedonBetweenAndSharestatusAndSitecode(String unifieduserid, Date fromdate,
			Date todate, int i, Integer sitecode);

	Lsprotocolordershareto findByShareprotocolordercodeAndSharestatusAndSharetousername(Long protocolordercode, int i,
			String lockedusername);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, LSprojectmaster project);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			LSprojectmaster project);




	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			Integer testcode, LSprojectmaster project);




	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate, Integer testcode);



	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate,
			LSprojectmaster project);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate, Integer testcode);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_TestcodeOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, Integer integer, String orderflag, int i, Date fromdate, Date todate,
			Integer testcode);

	List<ProtocolOrderShareToInterface> findBySharetounifiedidAndSitecodeAndOrderflagAndSharestatusAndSharedonBetweenAndProtocolorders_LsprojectmasterOrderBySharetoprotocolordercodeDesc(
			String string, Integer sitecode, String orderflag, int i, Date fromdate, Date todate,
			LSprojectmaster project);

	Lsprotocolordershareto findTopByShareprotocolordercodeAndSharestatusAndSharetousername(Long protocolordercode,
			int i, String lockedusername);

	


	

}
