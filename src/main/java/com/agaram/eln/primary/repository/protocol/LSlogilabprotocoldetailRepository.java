package com.agaram.eln.primary.repository.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agaram.eln.primary.fetchmodel.getorders.LogilabProtocolOrderssh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabprotocolorders;
import com.agaram.eln.primary.fetchmodel.getorders.ProtocolOrdersDashboard;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolorderstructure;
import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.protocols.Elnprotocolworkflow;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail.Protocolorder;
import com.agaram.eln.primary.model.protocols.LSprotocolmaster;
import com.agaram.eln.primary.model.protocols.LSprotocolworkflow;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplemaster;
import com.agaram.eln.primary.model.sheetManipulation.LStestmasterlocal;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LSlogilabprotocoldetailRepository extends JpaRepository<LSlogilabprotocoldetail, Long> {

	List<LSlogilabprotocoldetail> findByProtocoltypeAndOrderflag(Integer protocotype, String orderflag);

	/**
	 * Added by sathishkumar chandrasekar
	 * 
	 * for getting protocol orders in dashboard
	 * 
	 * @param orderflag
	 * @return
	 */

	List<Logilabprotocolorders> findByProtocoltype(Integer protocotype);

	long countByOrderflag(String orderflg);

	int countByProtocoltypeAndOrderflag(Integer protocoltype, String string);

	@Transactional
	@Modifying
	@Query(value = "select * from "
			+ "LSlogilabprotocoldetail where protocoltype = ?1 and sitecode=?2 and orderflag = ?3 and createdtimestamp BETWEEN ?4 and ?5 and assignedto_usercode IS NULL  ORDER BY createdtimestamp DESC offset 10 row", nativeQuery = true)
	List<LSlogilabprotocoldetail> getProtocoltypeAndSitecodeAndOrderflagAndCreatedtimestampBetween(Integer protocoltype,
			Integer sitecode, String string, Date fromdate, Date todate);

	@Transactional
	@Modifying
	@Query(value = "select * from "
			+ "LSlogilabprotocoldetail where protocoltype = ?1 and lsprotocolworkflow_workflowcode =?2 and orderflag = ?3 "
			+ "and createdtimestamp BETWEEN ?4 and ?5  ORDER BY createdtimestamp DESC offset 10 row", nativeQuery = true)
	List<LSlogilabprotocoldetail> getProtocoltypeAndLsprotocolworkflowAndOrderflagAndCreatedtimestampBetween(
			Integer protocoltype, Integer workflowcode, String string, Date fromdate, Date todate);

	@Query("select lsorder from LSlogilabprotocoldetail lsorder where lsorder.lsprotocolmaster IN (:protocolmastercodeArray)")
	List<LSlogilabprotocoldetail> findByLsprotocolmaster(
			@Param("protocolmastercodeArray") List<LSprotocolmaster> protocolmastercodeArray);

	@Transactional
	@Modifying
	@Query("update LSlogilabprotocoldetail set elnprotocolworkflow = :workflow, approved= :approved , rejected= :rejected ,previousapprovedworkflow= :elnprotocolworkflow,workflowapprovedusercode= :integer "
			+ "where protocolordercode in (:protocolordercode)")
	public void updateFileWorkflow(@Param("workflow") Elnprotocolworkflow lSworkflow,
			@Param("approved") Integer approved, @Param("rejected") Integer rejected,
			@Param("protocolordercode") Long protocolordercode, @Param("elnprotocolworkflow") Elnprotocolworkflow elnprotocolworkflow,@Param("integer") Integer integer);

	LSlogilabprotocoldetail findByProtocolordercodeAndProtoclordername(Long protocolordercode, String protoclordername);

	public List<Logilabprotocolorders> findFirst20ByProtocolordercodeLessThanOrderByProtocolordercodeDesc(
			Long protocolordercode);

	public List<Logilabprotocolorders> findFirst20ByOrderByProtocolordercodeDesc();

	public List<Logilabprotocolorders> findFirst20ByProtocolordercodeLessThanAndLsprojectmasterInOrderByProtocolordercodeDesc(
			Long protocolordercode, List<LSprojectmaster> lstproject);

	public List<Logilabprotocolorders> findFirst20ByLsprojectmasterInOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject);

	public Long countByLsprojectmasterIn(List<LSprojectmaster> lstproject);

	public Integer deleteByLsprojectmaster(LSprojectmaster lsproject);

	public List<LogilabProtocolOrderssh> findByLsprojectmasterOrderByProtocolordercodeDesc(LSprojectmaster lsproject);

	List<LSlogilabprotocoldetail> findTop10ByProtocoltypeAndOrderflagAndLSprotocolworkflowAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, String string, LSprotocolworkflow lsprotocolworkflow, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findTop10ByOrderflagAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			String string, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findTop10ByOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			String string, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate);

	LSlogilabprotocoldetail findByProtocolordercode(Long protocolordercode);

	Logilabprotocolorders findByProtocolordercodeOrderByProtocolordercodeDesc(Long protocolordercode);

	int countByProtocoltypeAndSitecodeAndOrderflagAndLsuserMasterAndCreatedtimestampBetween(Integer protocoltype,
			Integer sitecode, String string, LSuserMaster lsuserMaster, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findTop10ByProtocoltypeAndSitecodeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, String string, LSuserMaster lsuserMaster, Date fromdate,
			Date todate);

	List<LSlogilabprotocoldetail> findTop10ByProtocoltypeAndSitecodeAndOrderflagAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, String string, Date fromdate, Date todate);

	int countByProtocoltypeAndSitecodeAndOrderflagAndAssignedtoIsNullAndCreatedtimestampBetween(Integer protocoltype,
			Integer sitecode, String string, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByProtocoltypeAndSitecodeAndOrderflagAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, String string, Date fromdate, Date todate);

	int countByProtocoltypeAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetween(Integer protocoltype,
			Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate);

	int countByProtocoltypeAndSitecodeAndAssignedtoAndCreatedtimestampBetween(Integer protocoltype, Integer sitecode,
			LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndAssignedtoAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findTop10ByProtocoltypeAndSitecodeAndOrderflagAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByCompletedtimestampDesc(
			Integer protocoltype, Integer sitecode, String string, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByProtocolordercodeInAndOrderflag(ArrayList<Long> log, String orderflag);

	List<Logilabprotocolorders> findByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(int i,
			List<LSprojectmaster> lstproject);

	List<Logilabprotocolorders> findByOrderdisplaytypeAndLssamplemasterInAndTestcodeIsNotNull(int i,
			List<LSsamplemaster> lstsample);

	@Transactional
	@Modifying
	@Query("update LSlogilabprotocoldetail o set o.directorycode = ?1 where o.directorycode = ?2")
	void updateparentdirectory(Long newdirectorycode, Long olddirectorycode);

	@Transactional
	@Modifying
	@Query("update LSlogilabprotocoldetail o set o.directorycode = ?1 where o.protocolordercode in (?2)")
	void updatedirectory(Long directorycode, List<Long> protocolordercode);

	@Transactional
	@Modifying
	@Query("update LSlogilabprotocoldetail o set o.directorycode = ?1 where o.protocolordercode = ?2")
	void updatesingledirectory(Long directorycode, Long batchcode);

	List<Logilabprotocolorders> findByOrderdisplaytypeAndLssamplemasterInAndViewoptionAndTestcodeIsNotNullOrOrderdisplaytypeAndLsuserMasterAndViewoptionAndLssamplemasterInAndTestcodeIsNotNull(
			int i, List<LSsamplemaster> lstsample, int j, int k, LSuserMaster objusermaster, int l,
			List<LSsamplemaster> lstsample2);

	List<Logilabprotocolorders> findByAssignedtoOrderByProtocolordercodeDesc(LSuserMaster lsloginuser);

	List<Logilabprotocolorders> findByAssignedtoAndLsuserMasterOrderByProtocolordercodeDesc(LSuserMaster lsselecteduser,
			LSuserMaster lsloginuser);

//	@Transactional
//	@Modifying
//	@Query(value = "select distinct LSlogilabprotocoldetail.testcode, LSlogilabprotocoldetail.lsprojectmaster_projectcode,  (select testname from lstestmasterlocal where testcode = LSlogilabprotocoldetail.testcode) as testname  from LSlogilabprotocoldetail as LSlogilabprotocoldetail"
//			+ " where LSlogilabprotocoldetail.testcode is not null and assignedto_usercode is null and LSlogilabprotocoldetail.lsprojectmaster_projectcode is not null and LSlogilabprotocoldetail.lsprojectmaster_projectcode in (?1)", nativeQuery = true)
//	public ArrayList<List<Object>>  getLstestmasterlocalByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(List<Integer> lsprojectcode);

	@Transactional
	@Modifying
	@Query(value = "SELECT DISTINCT l.testcode, l.lsprojectmaster_projectcode, CAST(t.testname AS varchar(250)) AS testname "
			+ "FROM LSlogilabprotocoldetail l " + "JOIN lstestmasterlocal t ON t.testcode = l.testcode "
			+ "WHERE l.testcode IS NOT NULL " + "AND l.assignedto_usercode IS NULL "
			+ "AND l.lsprojectmaster_projectcode IS NOT NULL "
			+ "AND l.lsprojectmaster_projectcode IN (?1)", nativeQuery = true)
	public ArrayList<List<Object>> getLstestmasterlocalByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(
			List<Integer> lsprojectcode);

	@Transactional
	@Modifying
	@Query(value = "select distinct LSlogilabprotocoldetail.testcode, LSlogilabprotocoldetail.lssamplemaster_samplecode, CAST((select testname from lstestmasterlocal where testcode =  LSlogilabprotocoldetail.testcode) as varchar(10))as testname from LSlogilabprotocoldetail as LSlogilabprotocoldetail"
			+ " where LSlogilabprotocoldetail.testcode is not null and assignedto_usercode is null and LSlogilabprotocoldetail.lssamplemaster_samplecode is not null and LSlogilabprotocoldetail.lssamplemaster_samplecode in (?1)", nativeQuery = true)
	public ArrayList<List<Object>> getLstestmasterlocalByOrderdisplaytypeAndLSsamplemasterInAndTestcodeIsNotNull(
			List<Integer> lssamplecode);

	List<Logilabprotocolorders> findByDirectorycodeOrderByProtocolordercodeDesc(Long directorycode);

//	List<Logilabprotocolorders> findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//			LSprojectmaster lsprojectmaster, Integer testcode, int i, Date fromdate, Date todate);
//
//

	List<LSlogilabprotocoldetail> findByAssignedtoAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsloginuser, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsselecteduser, LSuserMaster lsloginuser, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByProtocolordercodeDesc(
			String orderflag, List<LSprojectmaster> lstproject, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, LSuserMaster lsuserMaster, Integer testcode1, int l,
			Integer protocoltype, Date fromdate2, Date todate2);

	List<LSlogilabprotocoldetail> findByProtocolordercodeIn(ArrayList<Long> log);

	List<LSlogilabprotocoldetail> findByProtocolordercodeInAndProtocoltype(ArrayList<Long> log, Integer protocoltype);

	@Transactional
	@Modifying
	@Query(value = "select protocolordercode from "
			+ "LSlogilabprotocoldetail where assignedto_usercode = ?1 and createdtimestamp BETWEEN ?2 and ?3 order by protocolordercode desc", nativeQuery = true)
	List<Integer> getAssignedtoAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(LSuserMaster lsloginuser,
			Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, LSuserMaster lsuserMaster, Integer testcode1, int l,
			Integer protocoltype, String orderflag, Date fromdate2, Date todate2);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, LSuserMaster lsuserMaster, Integer testcode1, int l,
			String orderflag, Date fromdate2, Date todate2);

	List<LSlogilabprotocoldetail> findByAssignedtoAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsloginuser, Integer protocoltype, String orderflag, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsloginuser, String orderflag, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsloginuser, Integer protocoltype, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsselectedfulluser, Integer protocoltype, String orderflag, LSuserMaster lsloginuser,
			Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsselectedfulluser, String orderflag, LSuserMaster lsloginuser, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsselectedfulluser, Integer protocoltype, LSuserMaster lsloginuser, Date fromdate,
			Date todate);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, LSuserMaster lsuserMaster, Integer testcode1, int l,
			Integer protocoltype, String orderflag, int m, Date fromdate2, Date todate2);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, LSuserMaster lsuserMaster, Integer testcode1, int l,
			String orderflag, int m, Date fromdate2, Date todate2);

	List<Logilabprotocolorders> findByOrderflagAndRejectedAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByProtocolordercodeDesc(
			String orderflag, int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByAssignedtoAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSuserMaster lsloginuser, Integer protocoltype, String string, int i, Date fromdate, Date todate);

	List<Logilabprotocolorders> findBySitecodeAndAssignedtoAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndAssignedtoAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster assignedto, Date fromdate,
			Date todate);

	List<Logilabprotocolorders> findBySitecodeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer sitecode, String orderflag, LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findBySitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster lsuserMaster,
			LSuserMaster assignedto, Date fromdate, Date todate);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			String orderflag, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate);

	List<Logilabprotocolorders> findByOrderflagAndDirectorycodeAndLsprojectmasterAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByProtocolordercodeDesc(
			String orderflag, Long directorycode, LSprojectmaster lsprojectmaster, Integer protocoltype, Date fromdate,
			Date todate);

//	directorybased

	List<Logilabprotocolorders> findByDirectorycodeInAndViewoptionAndAssignedtoIsNullAndCreatedtimestampBetweenOrDirectorycodeInAndViewoptionAndAssignedtoIsNullAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			List<Long> directorycode, int i, Date fromdate, Date todate, List<Long> directorycode2, int j,
			LSuserMaster objuser, Date fromdate2, Date todate2);

	List<Logilabprotocolorders> findByDirectorycodeInAndViewoptionAndAssignedtoIsNullAndCreatedtimestampBetweenOrDirectorycodeInAndViewoptionAndAssignedtoIsNullAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeInAndViewoptionAndAssignedtoIsNullAndCreatedtimestampBetweenAndTeamcodeInOrderByProtocolordercodeDesc(
			List<Long> directorycode, int i, Date fromdate, Date todate, List<Long> directorycode2, int j,
			LSuserMaster objuser, Date fromdate2, Date todate2, List<Long> directorycode3, int k, Date fromdate3,
			Date todate3, List<Integer> teamcode);

	List<Logilabprotocolorders> findFirst20ByLsprojectmasterInOrDirectorycodeInOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, List<Long> directorycode);

	public Long countByLsprojectmasterInOrDirectorycodeIn(List<LSprojectmaster> lstproject, List<Long> directorycode);

	List<Logilabprotocolorders> findFirst20ByProtocolordercodeLessThanAndLsprojectmasterInOrProtocolordercodeLessThanAndDirectorycodeInOrderByProtocolordercodeDesc(
			Long protocolordercode, List<LSprojectmaster> lstproject, Long protocolordercode2,
			List<Long> directorycode);

//	List<Logilabprotocolorders> findBySitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterIn(
//			Integer sitecode, java.util.Date fromdate2, java.util.Date todate, List<LSprojectmaster> lstproject);

//	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterIn(
//			String string, Integer sitecode, java.util.Date fromdate2, java.util.Date todate,
//			List<LSprojectmaster> lstproject);

	Object countBySitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(Integer sitecode, Date fromdate, Date todate,
			List<LSprojectmaster> lstproject);

//	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(String string, Integer sitecode,
//			Date fromdate, Date todate, List<LSprojectmaster> lstproject);	
//	
	Object countByApprovelstatusNotAndOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(Integer i,
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	Object countByOrdercancellNotAndApprovelstatusNotAndOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(
			int i, int j, String string, Integer sitecode, Date fromdate, Date todate,
			List<LSprojectmaster> lstproject);

	Object countByApprovelstatusAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i, Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	Object countByOrdercancellNotAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i, Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject);

//	Object countByOrdercancellIsNullAndOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndApprovelstatusNotOrApprovelstatusIsNull(
//			int i, String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			int j);

	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndApprovelstatusNotOrApprovelstatusIsNullAndOrdercancellIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int i);

	List<Logilabprotocolorders> findByApprovelstatusAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i,
			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i,
//			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Long directorycode2, int j, LSuserMaster createdby,
			Date fromdate2, Date todate2);

//	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndApprovelstatusNotAndOrdercancellIsNull(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int i);

//	List<LSlogilabprotocoldetail>  countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNull(String string,
//			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int i);
//	
//	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNull(String string,
//			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

//	List<Logilabprotocolorders> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i,
//			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<Logilabprotocolorders> findBySitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, Date fromdate2, Date todate2, List<LSprojectmaster> lstproject2, int i,
			Integer sitecode3, Date fromdate3, Date todate3, List<LSprojectmaster> lstproject3, int j,
			Integer sitecode4, Date fromdate4, Date todate4, List<LSprojectmaster> lstproject4);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

//	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndRejectedIsNullOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			String string2, Integer sitecode2, Date fromdate2, Date todate2, List<LSprojectmaster> lstproject2, int i,
//			Integer sitecode3, Date fromdate3, Date todate3, List<LSprojectmaster> lstproject3, int j,
//			Integer sitecode4, Date fromdate4, Date todate4, List<LSprojectmaster> lstproject4);

//	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndRejectedIsNullOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			String string2, Integer sitecode2, Date fromdate2, Date todate2, List<LSprojectmaster> lstproject2, int i,
//			Integer sitecode3, Date fromdate3, Date todate3, List<LSprojectmaster> lstproject3, int j,
//			Integer sitecode4, Date fromdate4, Date todate4, List<LSprojectmaster> lstproject4, Pageable pageable);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Pageable pageable);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			String string2, Integer sitecode2, Date fromdate2, Date todate2, LSprojectmaster lstprojectforfilter2,
			int i, Integer sitecode3, Date fromdate3, Date todate3, LSprojectmaster lstprojectforfilter3, int j,
			Integer sitecode4, Date fromdate4, Date todate4, LSprojectmaster lstprojectforfilter4, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmaster(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			String string2, Integer sitecode2, Date fromdate2, Date todate2, LSprojectmaster lstprojectforfilter2,
			int i, Integer sitecode3, Date fromdate3, Date todate3, LSprojectmaster lstprojectforfilter3, int j,
			Integer sitecode4, Date fromdate4, Date todate4, LSprojectmaster lstprojectforfilter4);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcodeOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullAndTestcodeOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode, String string2, Integer sitecode2, Date fromdate2, Date todate2,
			LSprojectmaster lstprojectforfilter2, Integer testcode2, int i, Integer sitecode3, Date fromdate3,
			Date todate3, LSprojectmaster lstprojectforfilter3, Integer testcode3, int j, Integer sitecode4,
			Date fromdate4, Date todate4, LSprojectmaster lstprojectforfilter4, Integer testcode4, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcodeOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullAndTestcodeOrRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcode(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode, String string2, Integer sitecode2, Date fromdate2, Date todate2,
			LSprojectmaster lstprojectforfilter2, Integer testcode2, int i, Integer sitecode3, Date fromdate3,
			Date todate3, LSprojectmaster lstprojectforfilter3, Integer testcode3, int j, Integer sitecode4,
			Date fromdate4, Date todate4, LSprojectmaster lstprojectforfilter4, Integer testcode4);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndRejectedIsNullAndTestcode(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcode(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Pageable pageable);

	long countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmaster(int i, Integer sitecode, Date fromdate,
			Date todate, LSprojectmaster lstprojectforfilter);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter, Integer testcode,
			Pageable pageable);

	long countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcode(int i, Integer sitecode,
			Date fromdate, Date todate, LSprojectmaster lstprojectforfilter, Integer testcode);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Pageable pageable);

	long countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmaster(int i, Integer sitecode,
			Date fromdate, Date todate, LSprojectmaster lstprojectforfilter);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter, Integer testcode,
			Pageable pageable);

	long countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcode(int i, Integer sitecode,
			Date fromdate, Date todate, LSprojectmaster lstprojectforfilter, Integer testcode);

	long countBySitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcode(Integer sitecode, Date fromdate,
			Date todate, List<LSprojectmaster> lstproject, Integer testcode);

	long countBySitecodeAndCreatedtimestampBetweenAndLsprojectmaster(Integer sitecode, Date fromdate, Date todate,
			LSprojectmaster lstprojectforfilter);

	long countBySitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcode(Integer sitecode, Date fromdate,
			Date todate, LSprojectmaster lstprojectforfilter, Integer testcode);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode, String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, Integer testcode2,
			String string3, Integer sitecode3, int j, Date fromdate3, Integer usercode, Date todate3, Integer testcode3,
			String string4, Integer sitecode4, List<LSprojectmaster> lstproject2, int k, Date fromdate4, Date todate4,
			Integer testcode4, Pageable pageable);

//	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			Integer testcode, String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, Integer testcode2,
//			String string3, Integer sitecode3, int j, Date fromdate3, Integer usercode, Date todate3, Integer testcode3,
//			String string4, Integer sitecode4, List<LSprojectmaster> lstproject2, int k, Date fromdate4, Date todate4,
//			Integer testcode4);

	List<Logilabprotocolorders> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			Integer sitecode2, int k, Date fromdate2, Date todate2, int l, Integer sitecode3, int m, Integer usercode,
			Date fromdate3, Date todate3, int n, Integer sitecode4, List<LSprojectmaster> lstproject2, int o,
			Date fromdate4, Date todate4, Pageable pageable);

	long countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i, Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullAndTestcode(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode);

	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode);

	long countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcode(int i, Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode);

	long countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterIn(int i, Integer sitecode,
			Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	long countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcode(int i,
			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4,
			List<LSprojectmaster> lstproject2, int k, Date fromdate4, Date todate4, Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4,
			List<LSprojectmaster> lstproject2, int k, Date fromdate4, Date todate4);

//	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer usercode, Date fromdate2, Date todate2,
//			int k, Integer usercode2, Date fromdate3, Date todate3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer usercode, Date fromdate2, Date todate2,
			int k, Integer usercode2, Date fromdate3, Date todate3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, Integer testcode, int j, Integer usercode,
			Date fromdate2, Date todate2, Integer testcode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			Integer testcode3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, Integer testcode, int j, Integer usercode,
			Date fromdate2, Date todate2, Integer testcode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			Integer testcode3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, Integer testcode, int j,
			Integer usercode, Date fromdate2, Date todate2, String string2, Integer testcode2, int k, Integer usercode2,
			Date fromdate3, Date todate3, String string3, Integer testcode3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, Integer testcode, int j,
			Integer usercode, Date fromdate2, Date todate2, String string2, Integer testcode2, int k, Integer usercode2,
			Date fromdate3, Date todate3, String string3, Integer testcode3);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, int j, Integer usercode, Date fromdate2,
			Date todate2, String string2, int k, Integer usercode2, Date fromdate3, Date todate3, String string3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, Integer testcode, int j,
			Integer usercode, Date fromdate2, Date todate2, String string2, Integer testcode2, int k, Integer usercode2,
			Date fromdate3, Date todate3, String string3, Integer testcode3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, Integer testcode, int j,
			Integer usercode, Date fromdate2, Date todate2, String string2, Integer testcode2, int k, Integer usercode2,
			Date fromdate3, Date todate3, String string3, Integer testcode3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer testcode, int k, Integer usercode,
			Date fromdate2, Date todate2, int l, Integer testcode2, int m, Integer usercode2, Date fromdate3,
			Date todate3, int n, Integer testcode3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer testcode, int k, Integer usercode,
			Date fromdate2, Date todate2, int l, Integer testcode2, int m, Integer usercode2, Date fromdate3,
			Date todate3, int n, Integer testcode3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndRejectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer testcode, int k, Integer usercode,
			Date fromdate2, Date todate2, int l, Integer testcode2, int m, Integer usercode2, Date fromdate3,
			Date todate3, int n, Integer testcode3, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndRejectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer testcode, int k, Integer usercode,
			Date fromdate2, Date todate2, int l, Integer testcode2, int m, Integer usercode2, Date fromdate3,
			Date todate3, int n, Integer testcode3);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4,
			List<LSprojectmaster> lstproject2, int k, Date fromdate4, Date todate4);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Pageable pageable);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterAndOrdercancellIsNullAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, LSprojectmaster lstprojectforfilter,
			Integer testcode, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeAndRejectedIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeAndRejectedIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, Integer testcode, int j,
			Integer usercode, Date fromdate2, Date todate2, String string2, Integer testcode2, int k, Integer usercode2,
			Date fromdate3, Date todate3, String string3, Integer testcode3);

//	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
//			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4, int k,
//			Date fromdate4, Date todate4, List<Integer> userlist);

	List<LSlogilabprotocoldetail> findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSsamplemaster lssamplemaster, int i, Integer testcode, int j, Date fromdate, Date todate,
			LSsamplemaster lssamplemaster2, int k, Integer testcode2, int l, Date fromdate2, Date todate2,
			LSsamplemaster lssamplemaster3, int m, LSuserMaster lsuserMaster, Integer testcode3, int n, Date fromdate3,
			Date todate3);


	List<Logilabprotocolorders> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String searchkeywords, String searchkeywords2, int j,
			Integer sitecode2, Date fromdate2, Date todate2, String searchkeywords3, String searchkeywords4, int k,
			Integer usercode, Date fromdate3, Date todate3, String searchkeywords5, String searchkeywords6, int l,
			Integer usercode2, Date fromdate4, Date todate4, String searchkeywords7, String searchkeywords8, int m,
			Integer usercode3, Date fromdate5, Date todate5, String searchkeywords9, String searchkeywords10, int n,
			Integer usercode4, Date fromdate6, Date todate6, String searchkeywords11, String searchkeywords12,
			Pageable pageableprotocol);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String searchkeywords, String searchkeywords2, int j,
			Integer sitecode2, Date fromdate2, Date todate2, String searchkeywords3, String searchkeywords4, int k,
			Integer usercode, Date fromdate3, Date todate3, String searchkeywords5, String searchkeywords6, int l,
			Integer usercode2, Date fromdate4, Date todate4, String searchkeywords7, String searchkeywords8, int m,
			Integer usercode3, Date fromdate5, Date todate5, String searchkeywords9, String searchkeywords10, int n,
			Integer usercode4, Date fromdate6, Date todate6, String searchkeywords11, String searchkeywords12);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer sitecode2, Integer usercode,
			Date fromdate2, Date todate2, int k, Integer sitecode3, Date fromdate3, Date todate3,
			List<Integer> userlist, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyIn(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer sitecode2, Integer usercode,
			Date fromdate2, Date todate2, int k, Integer sitecode3, Date fromdate3, Date todate3,
			List<Integer> userlist);

	long countByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetween(List<LSprojectmaster> lstproject,
			int i, Integer sitecode, Date fromdate, Date todate);

	long countByLsprojectmasterInAndCreatedtimestampBetweenAndSitecode(List<LSprojectmaster> lstproject, Date fromdate,
			Date todate, Integer sitecode);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, Integer testcode, int j, Integer sitecode2,
			Integer usercode, Date fromdate2, Date todate2, Integer testcode2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate, Integer testcode,
			Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndTestcode(
			int i, Integer sitecode, Date fromdate, Date todate, Integer testcode, int j, Integer sitecode2,
			Integer usercode, Date fromdate2, Date todate2, Integer testcode2);

	long countByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndTestcode(List<LSprojectmaster> lstproject,
			Date fromdate, Date todate, Integer sitecode, Integer testcode);

	long countByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndTestcode(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate, Integer testcode);

//	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
//			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
//			Date fromdate3, Date todate3, List<Integer> userlist, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Pageable pageable);

	long countByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyIn(
			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
			Date fromdate3, Date todate3, List<Integer> userlist);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNull(String string,
			Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode, String string2,
			Integer sitecode2, int j, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNullAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			String string, Integer sitecode, List<LSprojectmaster> lstproject, int i, Date fromdate, Date todate,
			Integer testcode, Pageable pageable);

	long countByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcode(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode, String string2,
			Integer sitecode2, int j, Integer usercode, Date fromdate2, Date todate2, Integer testcode2);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNullAndTestcode(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode);

	long countByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcode(
			String string, Integer sitecode, List<LSprojectmaster> lstproject, int i, Date fromdate, Date todate,
			Integer testcode);

//	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
//			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
//			Date fromdate3, Date todate3, List<Integer> userlist, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Pageable pageable);

	long countByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyIn(
			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
			Date fromdate3, Date todate3, List<Integer> userlist);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode, String string2,
			Integer sitecode2, int j, Date fromdate2, Integer usercode, Date todate2, Integer testcode2,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, List<LSprojectmaster> lstproject, int i, Date fromdate, Date todate,
			Integer testcode, Pageable pageable);

	long countByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode, String string2,
			Integer sitecode2, int j, Integer usercode, Date fromdate2, Date todate2, Integer testcode2);

	long countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterInAndOrdercancellIsNullAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer testcode);

	long countByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNull(
			String string, Integer sitecode, List<LSprojectmaster> lstproject, int i, Date fromdate, Date todate,
			Integer testcode);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist, Pageable pageable);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Pageable pageable);

	long countByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyIn(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, List<LSprojectmaster> lstproject, int j, Date fromdate, Date todate,
			Integer testcode, Pageable pageable);

	long countByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcode(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2);

	long countByRejectedAndSitecodeAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcode(int i,
			Integer sitecode, List<LSprojectmaster> lstproject, int j, Date fromdate, Date todate, Integer testcode);

//	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
//			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
//			Date todate3, List<Integer> userlist, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Pageable pageable);

	long countByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyIn(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode,
			Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, List<LSprojectmaster> lstproject, int j, Date fromdate, Date todate,
			Integer testcode, Pageable pageable);

	long countByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcode(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2);

	long countByOrdercancellAndSitecodeAndLsprojectmasterInAndViewoptionAndCreatedtimestampBetweenAndTestcode(int i,
			Integer sitecode, List<LSprojectmaster> lstproject, int j, Date fromdate, Date todate, Integer testcode);

//	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNullOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
//			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4, int k,
//			Date fromdate4, Date todate4, List<Integer> userlist);
//
//
//	Object countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
//			Integer sitecode2, int k, Date fromdate2, Date todate2, int l, Integer sitecode3, int m, Integer usercode,
//			Date fromdate3, Date todate3, int n, Integer sitecode4, int o, Date fromdate4, Date todate4,
//			List<Integer> userlist);
//
//
//	Object countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
//			Integer sitecode2, int k, Date fromdate2, Date todate2, int l, Integer sitecode3, int m, Integer usercode,
//			Date fromdate3, Date todate3, int n, Integer sitecode4, int o, Date fromdate4, Date todate4,
//			List<Integer> userlist);

	List<Logilabprotocolorders> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String searchkeywords, String searchkeywords2, int j,
			Integer sitecode2, Date fromdate2, Date todate2, String searchkeywords3, String searchkeywords4, int k,
			Integer sitecode3, Integer usercode, Date fromdate3, Date todate3, String searchkeywords5,
			String searchkeywords6, int l, Integer sitecode4, Integer usercode2, Date fromdate4, Date todate4,
			String searchkeywords7, String searchkeywords8, int m, Integer sitecode5, Date fromdate5, Date todate5,
			List<Integer> userlist, String searchkeywords9, String searchkeywords10, int n, Integer sitecode6,
			Date fromdate6, Date todate6, List<Integer> userlist2, String searchkeywords11, String searchkeywords12,
			Pageable pageable);

	List<Logilabprotocolorders> findByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, String searchkeywords,
			String searchkeywords2, Pageable pageable);

	List<Logilabprotocolorders> findByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, String searchkeywords,
			String searchkeywords2, Pageable pageable);

	List<Logilabprotocolorders> findByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate,
			String searchkeywords, String searchkeywords2, Pageable pageable);

	List<Logilabprotocolorders> findByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate,
			String searchkeywords, String searchkeywords2, Pageable pageable);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String searchkeywords, String searchkeywords2, int j,
			Integer sitecode2, Date fromdate2, Date todate2, String searchkeywords3, String searchkeywords4, int k,
			Integer sitecode3, Integer usercode, Date fromdate3, Date todate3, String searchkeywords5,
			String searchkeywords6, int l, Integer sitecode4, Integer usercode2, Date fromdate4, Date todate4,
			String searchkeywords7, String searchkeywords8, int m, Integer sitecode5, Date fromdate5, Date todate5,
			List<Integer> userlist, String searchkeywords9, String searchkeywords10, int n, Integer sitecode6,
			Date fromdate6, Date todate6, List<Integer> userlist2, String searchkeywords11, String searchkeywords12);

	long countByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, String searchkeywords,
			String searchkeywords2);

	long countByLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, String searchkeywords,
			String searchkeywords2);

	long countByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate,
			String searchkeywords, String searchkeywords2);

	long countByLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndProtoclordernameNotContainingIgnoreCaseAndKeywordContainingIgnoreCaseOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, Date fromdate, Date todate,
			String searchkeywords, String searchkeywords2);

//	List<Logilabprotocolorders> findByOrderflagAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNull(
//			String orderflag, List<LSprojectmaster> lstproject, Integer protocoltype, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findBylsprojectmasterAndTestcodeInAndLssamplemasterIn(LSprojectmaster lsproject,
			List<Integer> testid, List<LSsamplemaster> lssample);

	List<LSlogilabprotocoldetail> findBylsprojectmasterAndTestcodeIn(LSprojectmaster lsproject, List<Integer> testid);

	List<LSlogilabprotocoldetail> findByLsprotocolmasterInAndTestcodeIn(List<LSprotocolmaster> lstpm,
			List<Integer> testid);

	List<LSlogilabprotocoldetail> findByProtocolordercodeOrderByProtocolordercodeAsc(Long protocolordercode);

	List<LSlogilabprotocoldetail> findByLssamplemasterIn(List<LSsamplemaster> lsSampleLst);

	@Transactional
	@Modifying
	@Query(value = "update LSlogilabprotocoldetail set lockeduser=null,lockedusername=null where protocolordercode in(?1)")
	void Updatelockedusersonptocolorders(List<Long> protocolorderscode);

	List<Logilabprotocolorders> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndLockeduserIsNotNullAndOrderflagAndLockeduserNotOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndLockeduserIsNotNullAndOrderflagAndLockeduserNotOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyInAndLockeduserIsNotNullAndOrderflagAndLockeduserNotOrderByProtocolordercodeDesc(
			int i, Integer sitecode, String string, Integer usercode, int j, Integer sitecode2, Integer usercode2,
			String string2, Integer usercode3, int k, Integer sitecode3, List<Integer> userlist, String string3,
			Integer usercode4);

	@Transactional
	@Modifying
	@Query(value = "update LSlogilabprotocoldetail  set elnprotocolworkflow_workflowcode = null where elnprotocolworkflow_workflowcode = ?1", nativeQuery = true)
	void setWorkflownullforcompletedorder(Integer lsworkflow);

	long countByelnprotocolworkflowAndOrderflag(Elnprotocolworkflow objflow, String string);

	@Transactional
	@Modifying
	@Query(value = "select distinct LSlogilabprotocoldetail.testcode, LSlogilabprotocoldetail.elnmaterial_nmaterialcode,  CAST((select testname from lstestmasterlocal where testcode =  LSlogilabprotocoldetail.testcode) as varchar(250))as testname  from LSlogilabprotocoldetail as LSlogilabprotocoldetail"
			+ " where LSlogilabprotocoldetail.testcode is not null and LSlogilabprotocoldetail.elnmaterial_nmaterialcode is not null and LSlogilabprotocoldetail.elnmaterial_nmaterialcode in (select nmaterialcode from elnmaterial where nsitecode=?1)", nativeQuery = true)
	public List<Object> getLstestmasterlocalAndmaterialByOrderdisplaytypeAndLSsamplemasterInAndTestcodeIsNotNull(
			Integer sitecode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndAndLockeduserIsNotNullAndAssignedtoIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode);

	List<Logilabprotocolorders> findByLsprojectmasterInAndViewoptionAndSitecodeAndLockeduserIsNotNullAndOrderflagAndLockeduserOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, int i, Integer sitecode, String string, Integer usercode);

	List<LSlogilabprotocoldetail> findByActiveuserIn(List<Integer> activeuser);

	List<Logilabprotocolorders> findByLsprojectmasterInAndSitecodeAndLockeduserIsNotNullAndOrderflagOrderByProtocolordercodeDesc(
			List<LSprojectmaster> lstproject, Integer sitecode, String string);

	List<Protocolorder> findByOrderflagAndLsprojectmasterInAndElnprotocolworkflowInAndCreatedtimestampBetween(
			String string, List<LSprojectmaster> lstproject, List<Elnprotocolworkflow> lstworkflow_protocol,
			Date fromdate, Date todate);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyIn(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2, Date todate2, int i, Elnmaterial elnmaterial3,
			Integer testcode3, Date fromdate3, Date todate3, int j, LSuserMaster lsuserMaster, Elnmaterial elnmaterial4,
			Integer testcode4, Date fromdate4, Date todate4, int k, List<Integer> userlist);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltypeAndOrderflagAndRejected(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			Integer protocoltype, String orderflag, int i, Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2,
			Date todate2, int j, Integer protocoltype2, String orderflag2, int k, Elnmaterial elnmaterial3,
			Integer testcode3, Date fromdate3, Date todate3, int l, LSuserMaster lSuserMaster, Integer protocoltype3,
			String string, int m, Elnmaterial elnmaterial4, Integer testcode4, Date fromdate4, Date todate4, int n,
			List<Integer> userlist, Integer protocoltype4, String orderflag4, int o);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltypeAndOrderflag(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			Integer protocoltype, String orderflag, Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2,
			Date todate2, int i, Integer protocoltype2, String orderflag2, Elnmaterial elnmaterial3, Integer testcode3,
			Date fromdate3, Date todate3, int j, LSuserMaster lsuserMaster, Integer protocoltype3, String orderflag3,
			Elnmaterial elnmaterial4, Integer testcode4, Date fromdate4, Date todate4, int k, List<Integer> userlist,
			Integer protocoltype4, String orderflag4);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndOrderflagAndRejected(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			String orderflag, int i, Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2, Date todate2, int j,
			String orderflag2, int k, Elnmaterial elnmaterial3, Integer testcode3, Date fromdate3, Date todate3, int l,
			LSuserMaster lSuserMaster, String string, int m, Elnmaterial elnmaterial4, Integer testcode4,
			Date fromdate4, Date todate4, int n, List<Integer> userlist, String orderflag4, int o);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndOrderflag(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			String orderflag, Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2, Date todate2, int i,
			String orderflag2, Elnmaterial elnmaterial3, Integer testcode3, Date fromdate3, Date todate3, int j,
			LSuserMaster lsuserMaster, String orderflag3, Elnmaterial elnmaterial4, Integer testcode4, Date fromdate4,
			Date todate4, int k, List<Integer> userlist, String orderflag4);

	List<Logilabprotocolorders> findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltype(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, Integer testcode, Date fromdate, Date todate,
			Integer protocoltype, Elnmaterial elnmaterial2, Integer testcode2, Date fromdate2, Date todate2, int i,
			Integer protocoltype2, Elnmaterial elnmaterial3, Integer testcode3, Date fromdate3, Date todate3, int j,
			LSuserMaster lsuserMaster, Integer protocoltype3, Elnmaterial elnmaterial4, Integer testcode4,
			Date fromdate4, Date todate4, int k, List<Integer> userlist, Integer protocoltype4);
//List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
//			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
//			Date todate3, List<Integer> userlist);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer protocoltype);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, int k, Integer sitecode2,
//			int l, Integer usercode, Date fromdate2, Date todate2, String orderflag2, int m, Integer sitecode3, int n,
//			Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndRejectedOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, int l, Integer sitecode2, int m,
//			Integer usercode, Date fromdate2, Date todate2, int n, int o, Integer sitecode3, int p, Date fromdate3,
//			Date todate3, List<Integer> userlist, int q);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, String orderflag, int k,
//			Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2,
//			String orderflag2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3, List<Integer> userlist,
//			Integer protocoltype3, String orderflag3);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			String orderflag);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, int k, int l,
//			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2, int n,
//			int o, Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist,
//			Integer protocoltype3, int q);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			int j);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, int k, int l,
//			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, String orderflag2, int n, int o,
//			Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3, int q);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			int j);

//	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, String orderflag, int k,
//			int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2,
//			String orderflag2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
//			List<Integer> userlist, Integer protocoltype3, String orderflag3, int q);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			String orderflag, int j);

	List<Protocolorder> findByOrderflagAndLsprojectmasterInAndElnprotocolworkflowInAndAssignedtoIsNullAndCreatedtimestampBetweenOrSitecodeAndAssignedtoAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(
			String string, List<LSprojectmaster> lstproject, List<Elnprotocolworkflow> lstworkflow_protocol,
			Date fromdate, Date todate, Integer sitecode, LSuserMaster objuser, Date fromdate2, Date todate2);

	@Transactional
	@Query(value = "select ordercancell from LSlogilabprotocoldetail where protocolordername = ?1", nativeQuery = true)
	String getRetirestatus(String templatename);

	List<LSlogilabprotocoldetail> findByProtocolordercodeInAndOrderflag(List<Long> batchcode, String orderflag);

	List<LSlogilabprotocoldetail> findByProtoclordernameInAndOrderflag(List<String> batchid, String string);

	List<LSlogilabprotocoldetail> findByLsuserMasterAndRepeat(LSuserMaster lsuserMaster, boolean b);

	@Transactional
	List<LSlogilabprotocoldetail> findByProtocolordercodeIn(List<Long> protocolordercodeauto);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndOrdercancellIsNull(
			String orderflag, Integer protocoltype, Date fromdate, Date todate, List<Elnmaterial> currentChunk, int i);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyAndOrdercancellIsNull(
			String orderflag, Integer protocoltype, Date fromdate, Date todate, List<Elnmaterial> currentChunk, int i,
			Integer usercode);

//	List<LogilabProtocolOrderssh>  findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
//			String orderflag, Integer protocoltype, Date fromdate, Date todate, List<Elnmaterial> currentChunk, int i,
//			List<Integer> userlist);

	List<LogilabProtocolOrderssh> findByOrderflagAndRejectedAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, int i, List<LSprojectmaster> lstproject, Integer protocoltype, Date fromdate, Date todate,
			String orderflag2, int j, Integer protocoltype2, Date fromdate2, Date todate2,
			List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, LSprojectmaster lsprojectmaster, Integer protocoltype, Date fromdate, Date todate,
			String orderflag2, Integer protocoltype2, Date fromdate2, Date todate2, List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterInAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, List<LSprojectmaster> lstproject, Integer testcode, Integer protocoltype, Date fromdate,
			Date todate, String orderflag2, Integer testcode2, Integer protocoltype2, Date fromdate2, Date todate2,
			List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, LSprojectmaster lsprojectmaster, Integer testcode, Integer protocoltype, Date fromdate,
			Date todate, String orderflag2, Integer testcode2, Integer protocoltype2, Date fromdate2, Date todate2,
			List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndRejectedAndLsprojectmasterInAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, int i, List<LSprojectmaster> lstproject, Integer testcode, Integer protocoltype,
			Date fromdate, Date todate, String orderflag2, int j, Integer testcode2, Integer protocoltype2,
			Date fromdate2, Date todate2, List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndRejectedAndLsprojectmasterAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, int i, LSprojectmaster lsprojectmaster, Integer protocoltype, Date fromdate, Date todate,
			String orderflag2, int j, Integer protocoltype2, Date fromdate2, Date todate2,
			List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndRejectedAndLsprojectmasterAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
			String orderflag, int i, LSprojectmaster lsprojectmaster, Integer testcode, Integer protocoltype,
			Date fromdate, Date todate, String orderflag2, int j, Integer testcode2, Integer protocoltype2,
			Date fromdate2, Date todate2, List<Elnmaterial> currentChunk);

	List<LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
			String orderflag, List<LSprojectmaster> lstproject, Integer protocoltype, Date fromdate, Date todate);

	List<LSlogilabprotocoldetail> findByRepeatAndAutoregistercountGreaterThan(boolean b, int i);

	@Transactional
	@Modifying
	@Query(value = "SELECT * FROM lslogilabprotocoldetail o "
			+ "INNER JOIN lsprotocolmaster l ON o.lsprotocolmaster_protocolmastercode = l.protocolmastercode "
			+ "INNER JOIN lstestmasterlocal m ON m.testcode = o.testcode " + "WHERE ("
			+ "  o.lsprojectmaster_projectcode IN (" + "    SELECT lsprojectmaster_projectcode "
			+ "    FROM LSlogilablimsorderdetail " + "    WHERE lsprojectmaster_projectcode IN ("
			+ "      SELECT projectcode FROM LSprojectmaster " + "      WHERE lsusersteam_teamcode IN ("
			+ "        SELECT teamcode FROM LSuserteammapping WHERE lsuserMaster_usercode = ?1" + "      )" + "    )"
			+ "  )" + "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)" + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ ")" + " OR (" + "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) " + "  AND o.viewoption = ?4 "
			+ "  AND o.ordercancell IS NULL" + ")" + " OR (" + "(" + "  o.lsprojectmaster_projectcode IS NULL "
			+ "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = false " + "  AND o.createby = ?1 " + "  AND o.ordercancell IS NULL" + ")" + "OR"
			+ "(" + "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = true " + "  AND o.protocolordercode IN (?10)" + "  AND o.createby = ?1 "
			+ "  AND o.ordercancell IS NULL" + ")" + ")" + " OR (" + "(" + "  o.lsprojectmaster_projectcode IS NULL "
			+ "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = false " + "  AND o.createby IN (?6) " + "  AND o.ordercancell IS NULL" + ")"
			+ "OR" + "(" + "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = true " + "  AND o.protocolordercode IN (?10)" + "  AND o.createby IN (?6) "
			+ "  AND o.ordercancell IS NULL" + ")" + ")" + " OR (" + "  o.directorycode IN (?7) "
			+ "  AND o.viewoption = ?3 " + "  AND o.lsprojectmaster_projectcode IS NULL "
			+ "  AND o.assignedto_usercode IS NULL " + "  AND o.ordercancell IS NULL " + "  AND ("
			+ "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)"
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )" + ")" + " OR (" + "  o.directorycode IN (?7) "
			+ "  AND o.viewoption = ?4 " + "  AND o.lsuserMaster_usercode = ?1 "
			+ "  AND o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)" + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ ")" + " OR (" + "  (" + "  o.directorycode IN (?7) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = false" + "  AND o.createby IN (?6) "
			+ "  AND o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)" + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ "  )" + "OR" + "  (" + "  o.directorycode IN (?7) " + "  AND o.viewoption = ?5 "
			+ "  AND o.teamselected = true" + "  AND o.protocolordercode IN (?10)" + "  AND o.createby IN (?6) "
			+ "  AND o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "	 OR LOWER(o.sequenceid) LIKE LOWER(?2)" + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ "  )" + ")ORDER BY protocolordercode DESC OFFSET ?8 ROWS FETCH NEXT ?9 ROWS ONLY ", nativeQuery = true)
	List<LSlogilabprotocoldetail> getSearchedRecords(Integer integer, String searchkeywords, int i, int j, int k,
			List<Integer> userlist, List<Lsprotocolorderstructure> lstdir, int l, Integer integer2,
			List<Long> selectedprotoorder);

	@Transactional
//	@Modifying
	@Query(value = "SELECT count(*) FROM lslogilabprotocoldetail o "
			+ "INNER JOIN lsprotocolmaster l ON o.lsprotocolmaster_protocolmastercode = l.protocolmastercode "
			+ "INNER JOIN lstestmasterlocal m ON m.testcode = o.testcode " + "WHERE ("
			+ "  o.lsprojectmaster_projectcode IN (" + "    SELECT lsprojectmaster_projectcode "
			+ "    FROM LSlogilablimsorderdetail " + "    WHERE lsprojectmaster_projectcode IN ("
			+ "      SELECT projectcode FROM LSprojectmaster " + "      WHERE lsusersteam_teamcode IN ("
			+ "        SELECT teamcode FROM LSuserteammapping WHERE lsuserMaster_usercode = ?1" + "      )" + "    )"
			+ "  )" + "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )" + ")" + " OR ("
			+ "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) "
			+ "  AND o.viewoption = ?4 " + "  AND o.ordercancell IS NULL" + ")" + " OR ("
			+ "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) "
			+ "  AND o.viewoption = ?5 " + "  AND o.createby = ?1 " + "  AND o.ordercancell IS NULL" + ")" + " OR ("
			+ "  o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.elnmaterial_nmaterialcode IN (SELECT nmaterialcode FROM elnmaterial WHERE nsitecode = ?3) "
			+ "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  ) "
			+ "  AND o.viewoption = ?5 " + "  AND o.createby IN (?6) " + "  AND o.ordercancell IS NULL" + ")" + " OR ("
			+ "  o.directorycode IN (?7) " + "  AND o.viewoption = ?3 " + "  AND o.lsprojectmaster_projectcode IS NULL "
			+ "  AND o.assignedto_usercode IS NULL " + "  AND o.ordercancell IS NULL " + "  AND ("
			+ "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ ")" + " OR (" + "  o.directorycode IN (?7) " + "  AND o.viewoption = ?4 "
			+ "  AND o.lsuserMaster_usercode = ?1 " + "  AND o.lsprojectmaster_projectcode IS NULL "
			+ "  AND o.assignedto_usercode IS NULL " + "  AND o.ordercancell IS NULL " + "  AND ("
			+ "    LOWER(o.protocolordername) LIKE LOWER(?2) " + "    OR LOWER(m.testname) LIKE LOWER(?2) "
			+ "    OR LOWER(o.keyword) LIKE LOWER(?2) " + "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )"
			+ ")" + " OR (" + "  o.directorycode IN (?7) " + "  AND o.viewoption = ?5 " + "  AND o.createby IN (?6) "
			+ "  AND o.lsprojectmaster_projectcode IS NULL " + "  AND o.assignedto_usercode IS NULL "
			+ "  AND o.ordercancell IS NULL " + "  AND (" + "    LOWER(o.protocolordername) LIKE LOWER(?2) "
			+ "    OR LOWER(m.testname) LIKE LOWER(?2) " + "    OR LOWER(o.keyword) LIKE LOWER(?2) "
			+ "    OR LOWER(l.protocolmastername) LIKE LOWER(?2)" + "  )" + ")", nativeQuery = true)
	public Long getcountSearchedRecords(Integer integer, String searchkeywords, int i, int j, int k,
			List<Integer> userlist, List<Lsprotocolorderstructure> lstdir);

//	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
//			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
//			Date fromdate3, Date todate3, List<Integer> userlist, String string4, Integer sitecode4, Date fromdate4,
//			Date todate4, List<LSprojectmaster> lstproject, Pageable pageable);

//	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInOrLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
//			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer sitecode2, Integer usercode,
//			Date fromdate2, Date todate2, int k, Integer sitecode3, Date fromdate3, Date todate3,
//			List<Integer> userlist, List<LSprojectmaster> lstproject, int l, Integer sitecode4, Date fromdate4,
//			Date todate4, List<LSprojectmaster> lstproject2, Date fromdate5, Date todate5, Integer sitecode5,
//			Pageable pageable);

//	List<LogilabProtocolOrderssh> findByLsprojectmasterOrderByProtocolordercodeDesc(LSprojectmaster lsprojectmaster);
	long countByLsuserMasterIn(List<LSuserMaster> lstuser);

	long countByLsprojectmaster(LSprojectmaster objproject);

	long countByLstestmasterlocal(LStestmasterlocal objtest);

	long countByProtocoltype(Integer filetype);

//	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
//			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Long directorycode2, int j,
//			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
//			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b, boolean c,
//			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, LSuserMaster createdby3,
//			Date fromdate4, Date todate4, Integer sitecode4);

//	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
//			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
//			Integer sitecode, Long directorycode2, int k, Integer protocoltype2, String orderflag2, int l,
//			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int m,
//			Integer protocoltype3, String orderflag3, int n, LSuserMaster createdby2, Date fromdate3, Date todate3,
//			Integer sitecode3);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Long directorycode2, int j,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, LSuserMaster createdby3,
			Date fromdate4, Date todate4, Integer sitecode4);

//	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
//			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
//			Integer sitecode, Long directorycode2, int j, Integer protocoltype2, String orderflag2,
//			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
//			Integer protocoltype3, String orderflag3, LSuserMaster createdby2, Date fromdate3, Date todate3,
//			Integer sitecode3);
//
	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Long directorycode2, int j,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Date fromdate4, Date todate4,
			Integer[] lstuserMaster2, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Long directorycode2, int k, Integer protocoltype2, String orderflag2, int l,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int m,
			Integer protocoltype3, String orderflag3, int n, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int o, Integer protocoltype4, String orderflag4, int p, LSuserMaster createdby3, Date fromdate4,
			Date todate4, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Long directorycode2, int j, Integer protocoltype2, String orderflag2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			Integer protocoltype3, String orderflag3, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int l, Integer protocoltype4, String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4,
			Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Long directorycode2, int k, Integer protocoltype2, String orderflag2, int l,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int m,
			Integer protocoltype3, String orderflag3, int n, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int o, Integer protocoltype4, String orderflag4, int p, Date fromdate4, Date todate4,
			Integer[] lstuserMaster2, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Long directorycode2, int j, Integer protocoltype2, String orderflag2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			Integer protocoltype3, String orderflag3, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int l, Integer protocoltype4, String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2,
			Integer sitecode4);

//	
	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int k, Integer protocoltype3, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int k, Integer protocoltype3, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, Integer protocoltype4, Date fromdate4, Date todate4, Integer[] lstuserMaster2,
			Integer sitecode4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o,
			Integer sitecode4, int p, Date fromdate4, Date todate4, List<Integer> userlist2);

//	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
//			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
//			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
//			Date fromdate3, Date todate3, String orderflag3, Integer protocoltype3);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2, int m, Integer sitecode3,
			int n, Date fromdate3, Date todate3, List<Integer> userlist, Integer protocoltype3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer protocoltype4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, String orderflag2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, int l, Integer sitecode2, int m,
			Integer usercode, Date fromdate2, Date todate2, int n, int o, Integer sitecode3, int p, Date fromdate3,
			Date todate3, List<Integer> userlist, int q, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			int r, Integer sitecode4, int s, Date fromdate4, Date todate4, List<Integer> userlist2, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, String orderflag, int k,
			Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2,
			String orderflag2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3, List<Integer> userlist,
			Integer protocoltype3, String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			int o, Integer sitecode4, int p, Date fromdate4, Date todate4, List<Integer> userlist2,
			Integer protocoltype4, String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, int k, int l,
			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2, int n,
			int o, Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist,
			Integer protocoltype3, int q, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int r,
			Integer sitecode4, int s, Date fromdate4, Date todate4, List<Integer> userlist2, Integer protocoltype4,
			int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, int k, int l,
			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, String orderflag2, int n, int o,
			Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3, int q,
			boolean b, boolean c, List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s,
			Date fromdate4, Date todate4, List<Integer> userlist2, String orderflag4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndOrderflagAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, String orderflag, int k,
			int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2,
			String orderflag2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
			List<Integer> userlist, Integer protocoltype3, String orderflag3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer protocoltype4, String orderflag4, int t);

	List<LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<Integer> userlist, String orderflag3, Integer protocoltype3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int l, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer protocoltype4);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, String orderflag3, Integer protocoltype3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int l, LSuserMaster lsuserMaster3,
			Date fromdate4, Date todate4, String orderflag4, Integer protocoltype4);

	List<LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype, int j,
			List<Long> directory_Code2, int k, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, int l, List<Long> directory_Code3, int m,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer protocoltype3, int n,
			boolean b, boolean c, List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int o,
			LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, String orderflag4, Integer protocoltype4, int p);

	List<LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype, int j,
			List<Long> directory_Code2, int k, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, int l, List<Long> directory_Code3, int m, Date fromdate3,
			Date todate3, List<Integer> userlist, String orderflag3, Integer protocoltype3, int n, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int o, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer protocoltype4, int p);

	List<LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer protocoltype2, Integer testcode2, List<Long> directory_Code3,
			int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer protocoltype3,
			Integer testcode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			List<Long> directory_Code4, int l, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4,
			String orderflag4, Integer protocoltype4, Integer testcode4);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer protocoltype2, Integer testcode2, List<Long> directory_Code3,
			int k, Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3, Integer protocoltype3,
			Integer testcode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			List<Long> directory_Code4, int l, Date fromdate4, Date todate4, List<Integer> userlist2, String orderflag4,
			Integer protocoltype4, Integer testcode4);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype, int j,
			Integer testcode, List<Long> directory_Code2, int k, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer protocoltype2, int l, Integer testcode2,
			List<Long> directory_Code3, int m, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3,
			String orderflag3, Integer protocoltype3, int n, Integer testcode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int o, LSuserMaster lsuserMaster3,
			Date fromdate4, Date todate4, String orderflag4, Integer protocoltype4, int p, Integer testcode4);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype, int j,
			Integer testcode, List<Long> directory_Code2, int k, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer protocoltype2, int l, Integer testcode2,
			List<Long> directory_Code3, int m, Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3,
			Integer protocoltype3, int n, Integer testcode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int o, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer protocoltype4, int p, Integer testcode4);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatebyAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndCreatebyInOrLsprojectmasterInAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterInAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer sitecode2, Integer usercode,
			Date fromdate2, Date todate2, int k, Integer sitecode3, Date fromdate3, Date todate3,
			List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int l,
			Integer sitecode4, Date fromdate4, Date todate4, List<Integer> userlist2, List<LSprojectmaster> lstproject,
			int m, Integer sitecode5, Date fromdate5, Date todate5, List<LSprojectmaster> lstproject2, Date fromdate6,
			Date todate6, Integer sitecode6, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, String string4, Integer sitecode4, int l, Date fromdate4,
			Date todate4, List<Integer> userlist2, String string5, Integer sitecode5, Date fromdate5, Date todate5,
			List<LSprojectmaster> lstproject, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o,
			Integer sitecode4, int p, Date fromdate4, Date todate4, List<Integer> userlist2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, int k, Integer sitecode2, int l,
			Integer usercode, Date fromdate2, Date todate2, int m, Integer sitecode3, int n, Date fromdate3,
			Date todate3, List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o,
			Integer sitecode4, int p, Date fromdate4, Date todate4, List<Integer> userlist2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, String string4, Integer sitecode4, int l, Date fromdate4,
			Date todate4, List<Integer> userlist2, Pageable pageable);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, String string2, Integer sitecode2,
			int j, Integer usercode, Date fromdate2, Date todate2, String string3, Integer sitecode3, int k,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, String string4, Integer sitecode4, int l, Date fromdate4,
			Date todate4, List<Integer> userlist2, Pageable pageable);

	Object countByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			Integer sitecode2, int k, Date fromdate2, Date todate2, int l, Integer sitecode3, int m, Integer usercode,
			Date fromdate3, Date todate3, int n, Integer sitecode4, int o, Date fromdate4, Date todate4,
			List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int p,
			Integer sitecode5, int q, Date fromdate5, Date todate5, List<Integer> userlist2);

	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedIsNullOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndRejectedIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4, int k,
			Date fromdate4, Date todate4, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, String string5, Integer sitecode5, int l, Date fromdate5,
			Date todate5, List<Integer> userlist2);

//	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrderByProtocolordercodeDesc(
//			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
//			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
//			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4, int k,
//			Date fromdate4, Date todate4, List<Integer> userlist, boolean b, boolean c,
//			List<Long> selectedteamprotcolorderList, String string5, Integer sitecode5, int l, Date fromdate5,
//			Date todate5, List<Integer> userlist2);

	Object countByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			Integer sitecode2, int k, Date fromdate2, Date todate2, int l, Integer sitecode3, int m, Integer usercode,
			Date fromdate3, Date todate3, int n, Integer sitecode4, int o, Date fromdate4, Date todate4,
			List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int p,
			Integer sitecode5, int q, Date fromdate5, Date todate5, List<Integer> userlist2);

	Object countByOrderflagAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrdercancellIsNullAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndRejectedIsNullAndCreatebyInOrderByProtocolordercodeDesc(
			String string, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			String string2, Integer sitecode2, int i, Date fromdate2, Date todate2, String string3, Integer sitecode3,
			int j, Integer usercode, Date fromdate3, Date todate3, String string4, Integer sitecode4, int k,
			Date fromdate4, Date todate4, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, String string5, Integer sitecode5, int l, Date fromdate5,
			Date todate5, List<Integer> userlist2);

	Collection<? extends LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			String orderflag, Integer protocoltype, Date fromdate, Date todate, List<Elnmaterial> currentChunk, int i,
			List<Integer> userlist);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<Integer> userlist, String orderflag3, Integer protocoltype3);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndElnmaterialIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<Integer> userlist, String orderflag3, Integer protocoltype3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int l, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer protocoltype4);

	Collection<? extends LogilabProtocolOrderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer protocoltype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer protocoltype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, String orderflag3, Integer protocoltype3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, List<Long> directory_Code4, int l, LSuserMaster lsuserMaster3,
			Date fromdate4, Date todate4, String orderflag4, Integer protocoltype4);

	Collection<? extends LogilabProtocolOrderssh> findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndTeamselectedAndOrdercancellIsNullOrTeamselectedAndProtocolordercodeInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
			String orderflag, Integer protocoltype, Date fromdate, Date todate, List<Elnmaterial> currentChunk, int i,
			List<Integer> userlist, boolean b, boolean c, List<Long> selectedteamprotcolorderList, String orderflag2,
			Integer protocoltype2, Date fromdate2, Date todate2, List<Elnmaterial> currentChunk2, int j,
			List<Integer> userlist2);

	Long countBySitecode(Integer integer);

	List<LogilabProtocolOrderssh> findBySitecodeAndCreatedtimestampBetweenOrderByCreatedtimestampDesc(Integer sitecode,
			Date fromdate, Date todate);

	LSlogilabprotocoldetail findBySequenceid(String batchid);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndRejectedIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndRejectedIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndRejectedIsNullAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, int j, Integer usercode, Date fromdate2,
			Date todate2, String string2, Integer sitecode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			String string3, Integer sitecode3);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, int j, Integer usercode, Date fromdate2,
			Date todate2, String string2, Integer sitecode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			String string3, Integer sitecode3);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndRejectedOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, int k, Integer usercode, Date fromdate2,
			Date todate2, int l, Integer sitecode2, int m, Integer usercode2, Date fromdate3, Date todate3, int n,
			Integer sitecode3);

	long countByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrdercancellOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, int k, Integer usercode, Date fromdate2,
			Date todate2, int l, Integer sitecode2, int m, Integer usercode2, Date fromdate3, Date todate3, int n,
			Integer sitecode3);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTeamselectedAndSitecodeOrTeamselectedAndProtocolordercodeInAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, Integer usercode, Date fromdate2, Date todate2,
			Integer sitecode2, int k, Integer usercode2, Date fromdate3, Date todate3, boolean b, Integer sitecode3,
			boolean c, List<Long> selectedteamprotcolorderList, int l, Integer usercode3, Date fromdate4, Date todate4,
			Integer sitecode4, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndRejectedIsNullAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, int j, Integer usercode, Date fromdate2,
			Date todate2, String string2, Integer sitecode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			String string3, Integer sitecode3, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, String string, int j, Integer usercode, Date fromdate2,
			Date todate2, String string2, Integer sitecode2, int k, Integer usercode2, Date fromdate3, Date todate3,
			String string3, Integer sitecode3, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndRejectedOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndRejectedAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, int k, Integer usercode, Date fromdate2,
			Date todate2, int l, Integer sitecode2, int m, Integer usercode2, Date fromdate3, Date todate3, int n,
			Integer sitecode3, Pageable pageable);

	List<ProtocolOrdersDashboard> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndCreatedtimestampBetweenAndOrdercancellOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, int j, int k, Integer usercode, Date fromdate2,
			Date todate2, int l, Integer sitecode2, int m, Integer usercode2, Date fromdate3, Date todate3, int n,
			Integer sitecode3, Pageable pageable);
	
	List<LSlogilabprotocoldetail> findByCreatedtimestampBetweenOrderByProtocolordercodeDesc(Date fromdate, Date todate);

	LogilabProtocolOrderssh findTop1ByProtocolordercode(Long protocolordercode);

	List<ProtocolOrdersDashboard> findByOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrOrderflagAndSitecodeAndOrdercancellIsNullAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndRejectedIsNullOrderByProtocolordercodeDesc(
			String string, Integer sitecode, int i, Date fromdate, Date todate, Integer testcode, String string2,
			Integer sitecode2, int j, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			Pageable pageable);
	
	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, Date fromdate, Date todate, LSprojectmaster lsprojectmaster2, int j,
			Date fromdate2, Date todate2);


	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, Integer protocoltype, String orderflag, int j, Date fromdate,
			Date todate);


	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate);


	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, String orderflag, int j, Date fromdate, Date todate);


	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, String orderflag, Date fromdate, Date todate);


	List<Logilabprotocolorders> findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
			LSprojectmaster lsprojectmaster, int i, Integer protocoltype, Date fromdate, Date todate);

	long countBySitecodeAndElnprotocolworkflowIsNullAndApprovedIsNullAndOrderflagAndOrdercancellIsNull(Integer sitecode,
			String string);


	List<LSlogilabprotocoldetail> findBySitecodeAndElnprotocolworkflowIsNullAndApprovedIsNullAndOrderflagAndOrdercancellIsNull(
			Integer sitecode, String string);


	@Transactional
	@Modifying
	@Query(value ="UPDATE LSlogilabprotocoldetail  SET elnprotocolworkflow_workflowcode =?3 WHERE sitecode = ?1 AND protocolordercode IN (?2)", nativeQuery = true)
	void setWorkflownullfororders(Integer integer, List<Long> ordercode, int workflowcode);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, LSprojectmaster lsprojectmaster,
			Long directorycode2, int j, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster lsprojectmaster2, Long directorycode3, int k, LSuserMaster createdby2, Date fromdate3,
			Date todate3, Integer sitecode3, boolean b, LSprojectmaster lsprojectmaster3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, LSuserMaster createdby3,
			Date fromdate4, Date todate4, Integer sitecode4, LSprojectmaster lsprojectmaster4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, LSprojectmaster lsprojectmaster,
			Long directorycode2, int j, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster lsprojectmaster2, Long directorycode3, int k, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, LSprojectmaster lsprojectmaster3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Date fromdate4, Date todate4,
			Integer[] lstuserMaster2, Integer sitecode4, LSprojectmaster lsprojectmaster4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Long directorycode2, int j, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			Integer testcode2, Long directorycode3, int k, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, Integer testcode3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Long directorycode2, int j, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			Integer testcode2, Long directorycode3, int k, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, Integer testcode3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, LSuserMaster createdby, Date fromdate2,
			Date todate2, Integer sitecode2, Integer testcode2, LSprojectmaster lsprojectmaster2, Long directorycode3,
			int k, LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b,
			Integer testcode3, LSprojectmaster lsprojectmaster3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4, LSprojectmaster lsprojectmaster4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, LSuserMaster createdby, Date fromdate2,
			Date todate2, Integer sitecode2, Integer testcode2, LSprojectmaster lsprojectmaster2, Long directorycode3,
			int k, Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b,
			Integer testcode3, LSprojectmaster lsprojectmaster3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4, LSprojectmaster lsprojectmaster4);
	
//	long countByElnprotocolworkflowAndOrderflagAndApprovedIsNullOrElnprotocolworkflowAndOrderflagAndApproved(
//			Elnprotocolworkflow objflow, String string, Elnprotocolworkflow objflow2, String string2, int i);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer testcode4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, LSprojectmaster project,
			int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			LSprojectmaster project2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3,
			List<Integer> userlist, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer testcode4, LSprojectmaster project4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndLsprojectmasterOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, LSprojectmaster project, int k,
			Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, LSprojectmaster project2, int m,
			Integer sitecode3, int n, Date fromdate3, Date todate3, List<Integer> userlist, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, int o, Integer sitecode4,
			int p, Date fromdate4, Date todate4, List<Integer> userlist2, LSprojectmaster project4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, LSprojectmaster project,
			Integer protocoltype, int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2,
			Integer testcode2, LSprojectmaster project2, Integer protocoltype2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, List<Integer> userlist, Integer protocoltype3, boolean b, Integer testcode3,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, int o, Integer sitecode4,
			int p, Date fromdate4, Date todate4, Integer testcode4, LSprojectmaster project4, List<Integer> userlist2,
			Integer protocoltype4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, Integer protocoltype, int k,
			Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			Integer protocoltype2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3,
			List<Integer> userlist, Integer protocoltype3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			Integer testcode4, List<Integer> userlist2, Integer protocoltype4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndLsprojectmasterAndProtocoltypeOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, LSprojectmaster project, Integer protocoltype,
			int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, LSprojectmaster project2,
			Integer protocoltype2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3,
			List<Integer> userlist, Integer protocoltype3, boolean b, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			LSprojectmaster project4, List<Integer> userlist2, Integer protocoltype4);

	List<Logilabprotocolorders>  findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, LSprojectmaster project,
			String orderflag, int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2,
			Integer testcode2, LSprojectmaster project2, String orderflag2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, Integer testcode3, LSprojectmaster project3, List<Integer> userlist,
			String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o, Integer sitecode4,
			int p, Date fromdate4, Date todate4, Integer testcode4, LSprojectmaster project4, List<Integer> userlist2,
			String orderflag4);

	List<Logilabprotocolorders>  findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, String orderflag, int k,
			Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			String orderflag2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3, Integer testcode3,
			List<Integer> userlist, String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			int o, Integer sitecode4, int p, Date fromdate4, Date todate4, Integer testcode4, List<Integer> userlist2,
			String orderflag4);

	List<Logilabprotocolorders>  findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, LSprojectmaster project, String orderflag,
			int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2, LSprojectmaster project2,
			String orderflag2, int m, Integer sitecode3, int n, Date fromdate3, Date todate3, LSprojectmaster project3,
			List<Integer> userlist, String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			int o, Integer sitecode4, int p, Date fromdate4, Date todate4, LSprojectmaster project4,
			List<Integer> userlist2, String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, LSprojectmaster project,
			int k, int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer testcode2,
			LSprojectmaster project2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
			Integer testcode3, LSprojectmaster project3, List<Integer> userlist, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			Integer testcode4, LSprojectmaster project4, List<Integer> userlist2, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, int l,
			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer testcode2, int n, int o,
			Integer sitecode3, int p, Date fromdate3, Date todate3, Integer testcode3, List<Integer> userlist, int q,
			boolean b, boolean c, List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s,
			Date fromdate4, Date todate4, Integer testcode4, List<Integer> userlist2, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndRejectedAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, int j,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, LSprojectmaster project, int k, int l,
			Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, LSprojectmaster project2, int n,
			int o, Integer sitecode3, int p, Date fromdate3, Date todate3, LSprojectmaster project3,
			List<Integer> userlist, int q, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int r,
			Integer sitecode4, int s, Date fromdate4, Date todate4, LSprojectmaster project4, List<Integer> userlist2,
			int t);


	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndProtocoltypeAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterAndCreatebyInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, LSprojectmaster project,
			Integer protocoltype, String orderflag, int k, Integer sitecode2, int l, Integer usercode, Date fromdate2,
			Date todate2, Integer testcode2, LSprojectmaster project2, Integer protocoltype2, String orderflag2, int m,
			Integer sitecode3, int n, Date fromdate3, Date todate3, Integer testcode3, LSprojectmaster project3,
			List<Integer> userlist, Integer protocoltype3, String orderflag3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			Integer testcode4, LSprojectmaster project4, List<Integer> userlist2, Integer protocoltype4,
			String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndProtocoltypeAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndCreatebyInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, Integer protocoltype,
			String orderflag, int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2,
			Integer testcode2, Integer protocoltype2, String orderflag2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, Integer testcode3, List<Integer> userlist, Integer protocoltype3,
			String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o, Integer sitecode4,
			int p, Date fromdate4, Date todate4, Integer testcode4, List<Integer> userlist2, Integer protocoltype4,
			String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndOrderflagAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			String orderflag, Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndOrderflagAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			String orderflag, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndLsprojectmasterAndProtocoltypeAndOrderflagOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndProtocoltypeAndOrderflagAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndCreatebyInAndProtocoltypeAndOrderflagOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, LSprojectmaster project, Integer protocoltype,
			String orderflag, int k, Integer sitecode2, int l, Integer usercode, Date fromdate2, Date todate2,
			LSprojectmaster project2, Integer protocoltype2, String orderflag2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, LSprojectmaster project3, List<Integer> userlist, Integer protocoltype3,
			String orderflag3, boolean b, boolean c, List<Long> selectedteamprotcolorderList, int o, Integer sitecode4,
			int p, Date fromdate4, Date todate4, LSprojectmaster project4, List<Integer> userlist2,
			Integer protocoltype4, String orderflag4);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndLsprojectmasterAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndLsprojectmasterAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, LSprojectmaster project,
			int k, int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2,
			Integer protocoltype2, LSprojectmaster project2, int n, int o, Integer sitecode3, int p, Date fromdate3,
			Date todate3, List<Integer> userlist, Integer protocoltype3, LSprojectmaster project3, int q, boolean b,
			boolean c, List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4,
			Date todate4, List<Integer> userlist2, Integer protocoltype4, LSprojectmaster project4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndRejectedAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			int j, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTestcodeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTestcodeAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, Integer testcode, int k,
			int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2,
			Integer testcode2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
			List<Integer> userlist, Integer protocoltype3, Integer testcode3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer protocoltype4, Integer testcode4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			int j, Integer testcode);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTestcodeAndLsprojectmasterAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeAndTestcodeAndLsprojectmasterAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, Integer testcode,
			LSprojectmaster project, int k, int l, Integer sitecode2, int m, Integer usercode, Date fromdate2,
			Date todate2, Integer protocoltype2, Integer testcode2, LSprojectmaster project2, int n, int o,
			Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist, Integer protocoltype3,
			Integer testcode3, LSprojectmaster project3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer protocoltype4, Integer testcode4, LSprojectmaster project4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndRejectedAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			int j, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndRejectedAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			int j, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndTestcodeAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndTestcodeAndLsprojectmasterAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndTestcodeAndLsprojectmasterAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, Integer testcode,
			LSprojectmaster project, int k, int l, Integer sitecode2, int m, Integer usercode, Date fromdate2,
			Date todate2, String orderflag2, Integer testcode2, LSprojectmaster project2, int n, int o,
			Integer sitecode3, int p, Date fromdate3, Date todate3, List<Integer> userlist, String orderflag3,
			Integer testcode3, LSprojectmaster project3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer testcode4, LSprojectmaster project4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndTestcodeAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndTestcodeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndTestcodeAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, Integer testcode, int k,
			int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, String orderflag2,
			Integer testcode2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
			List<Integer> userlist, String orderflag3, Integer testcode3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, Integer testcode4, int t);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			int j, Integer testcode);

	Collection<? extends Logilabprotocolorders> findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndOrderflagAndRejectedAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, String orderflag,
			int j, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterAndRejectedOrOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndOrdercancellAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterAndRejectedOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, String orderflag, LSprojectmaster project,
			int k, int l, Integer sitecode2, int m, Integer usercode, Date fromdate2, Date todate2, String orderflag2,
			LSprojectmaster project2, int n, int o, Integer sitecode3, int p, Date fromdate3, Date todate3,
			List<Integer> userlist, String orderflag3, LSprojectmaster project3, int q, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int r, Integer sitecode4, int s, Date fromdate4, Date todate4,
			List<Integer> userlist2, String orderflag4, LSprojectmaster project4, int t);

	List<Logilabprotocolorders>  findByOrdercancellAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeAndOrderflagAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer protocoltype,
			String orderflag, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findBySitecodeAndAssignedtoAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate, LSprojectmaster project);

	List<Logilabprotocolorders> findBySitecodeAndAssignedtoAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode);

	List<Logilabprotocolorders> findBySitecodeAndAssignedtoAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster assignedto, Date fromdate,
			Date todate, LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findBySitecodeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, String orderflag, LSuserMaster assignedto, Date fromdate, Date todate,
			LSprojectmaster project);

	List<Logilabprotocolorders> findBySitecodeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer sitecode, String orderflag, LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode);

	List<Logilabprotocolorders> findBySitecodeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, String orderflag, LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate,
			LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate,
			Integer testcode);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndAssignedtoAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster assignedto, Date fromdate, Date todate,
			LSprojectmaster project, Integer testcode);

	List<Logilabprotocolorders> findBySitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate,
			LSprojectmaster project);

	List<Logilabprotocolorders> findBySitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate,
			Integer testcode);

	List<Logilabprotocolorders> findBySitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate, Date todate,
			Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster lsuserMaster,
			LSuserMaster assignedto, Date fromdate, Date todate, LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster lsuserMaster,
			LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode);

	List<Logilabprotocolorders> findByProtocoltypeAndOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, String orderflag, Integer sitecode, LSuserMaster lsuserMaster,
			LSuserMaster assignedto, Date fromdate, Date todate, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			String orderflag, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, LSprojectmaster project);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			String orderflag, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode);

	List<Logilabprotocolorders> findByOrderflagAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			String orderflag, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode, LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, LSprojectmaster project);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode);

	List<Logilabprotocolorders> findByProtocoltypeAndSitecodeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByCreatedtimestampDesc(
			Integer protocoltype, Integer sitecode, LSuserMaster lsuserMaster, LSuserMaster assignedto, Date fromdate,
			Date todate, Integer testcode, LSprojectmaster project);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, LSprojectmaster project, Long directorycode2, int k, Integer protocoltype2,
			String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster project2, Long directorycode3, int m, Integer protocoltype3, String orderflag3, int n,
			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int o,
			Integer protocoltype4, String orderflag4, int p, LSuserMaster createdby3, Date fromdate4, Date todate4,
			Integer sitecode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2,
			String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster project2, Long directorycode3, int k, Integer protocoltype3, String orderflag3,
			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			Integer protocoltype4, String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4,
			Integer sitecode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, LSprojectmaster project, Long directorycode2, int k, Integer protocoltype2,
			String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster project2, Long directorycode3, int m, Integer protocoltype3, String orderflag3, int n,
			Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int o,
			Integer protocoltype4, String orderflag4, int p, Date fromdate4, Date todate4, Integer[] lstuserMaster2,
			Integer sitecode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2,
			String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2,
			LSprojectmaster project2, Long directorycode3, int k, Integer protocoltype3, String orderflag3,
			Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			Integer protocoltype4, String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2,
			Integer sitecode4, LSprojectmaster project4);

	long countByElnprotocolworkflowAndOrderflagAndApprovedIsNullAndOrdercancellIsNullOrElnprotocolworkflowAndOrderflagAndApprovedAndOrdercancellIsNull(
			Elnprotocolworkflow objflow, String string, Elnprotocolworkflow objflow2, String string2, int i);

	List<LogilabProtocolOrderssh> findByOrderflagAndProtocolordercodeInOrderByProtocolordercodeDesc(String orderflag,
			List<Long> protocolordercode);

	List<LogilabProtocolOrderssh> findByOrderflagAndProtocolordercodeInAndProtocoltype(String orderflag,
			List<Long> protocolordercode, Integer protocoltype);

	LSlogilabprotocoldetail findByprotoclordername(String protocolname);


	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, Long directorycode3, int k, LSuserMaster createdby, Date fromdate3,
			Date todate3, Integer sitecode3, Integer testcode3, List<LSprojectmaster> lstproject2, Long directorycode4,
			int l, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4,
			Long directorycode5, int m, LSuserMaster createdby3, Date fromdate5, Date todate5, Integer sitecode5,
			boolean b, Integer testcode5, List<LSprojectmaster> lstproject3, Long directorycode6, int n,
			LSuserMaster createdby4, Date fromdate6, Date todate6, Integer sitecode6, boolean c, Integer testcode6,
			boolean d, List<Long> selectedteamprotcolorderList, Long directorycode7, int o, LSuserMaster createdby5,
			Date fromdate7, Date todate7, Integer sitecode7, Integer testcode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, List<LSprojectmaster> lstproject,
			Long directorycode2, int j, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			LSuserMaster createdby, Date fromdate3, Date todate3, Integer sitecode3, List<LSprojectmaster> lstproject2,
			Long directorycode4, int l, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4,
			Long directorycode5, int m, Date fromdate5, Date todate5, Integer[] lstuserMaster, Integer sitecode5,
			boolean b, List<LSprojectmaster> lstproject3, Long directorycode6, int n, Date fromdate6, Date todate6,
			Integer[] lstuserMaster2, Integer sitecode6, boolean c, boolean d, List<Long> selectedteamprotcolorderList,
			Long directorycode7, int o, Date fromdate7, Date todate7, Integer[] lstuserMaster3, Integer sitecode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, List<LSprojectmaster> lstproject,
			Long directorycode2, int j, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			LSuserMaster createdby, Date fromdate3, Date todate3, Integer sitecode3, List<LSprojectmaster> lstproject2,
			Long directorycode4, int l, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4,
			Long directorycode5, int m, LSuserMaster createdby3, Date fromdate5, Date todate5, Integer sitecode5,
			boolean b, List<LSprojectmaster> lstproject3, Long directorycode6, int n, LSuserMaster createdby4,
			Date fromdate6, Date todate6, Integer sitecode6, boolean c, boolean d,
			List<Long> selectedteamprotcolorderList, Long directorycode7, int o, LSuserMaster createdby5,
			Date fromdate7, Date todate7, Integer sitecode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, Long directorycode3, int k, LSuserMaster createdby, Date fromdate3,
			Date todate3, Integer sitecode3, Integer testcode3, List<LSprojectmaster> lstproject2, Long directorycode4,
			int l, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4,
			Long directorycode5, int m, Date fromdate5, Date todate5, Integer[] lstuserMaster, Integer sitecode5,
			boolean b, Integer testcode5, List<LSprojectmaster> lstproject3, Long directorycode6, int n, Date fromdate6,
			Date todate6, Integer[] lstuserMaster2, Integer sitecode6, boolean c, Integer testcode6, boolean d,
			List<Long> selectedteamprotcolorderList, Long directorycode7, int o, Date fromdate7, Date todate7,
			Integer[] lstuserMaster3, Integer sitecode7, Integer testcode7);

	LSlogilabprotocoldetail findTopByOrderByProtocolordercodeDesc();
	@Transactional
	@Modifying
	@Query("UPDATE LSlogilabprotocoldetail  SET repeat = ?1 WHERE protocolordercode = ?2 ")
	void updateRepeat(boolean b, Long batchcode);

		@Transactional
		@Modifying
		@Query("update LSlogilabprotocoldetail o " +
		       "set o.sequenceid = ?1, o.repeat = ?2, o.autoregistercount = ?3, o.protoclordername = ?4 " +
		       "where o.protocolordercode = ?5")
		void sevaluesByProtocolordercode(String seq,
		                                 Boolean repeat,
		                                 Integer autoregistercount,
		                                 String protocolordername,
		                                 Long protocolordercode);

}
