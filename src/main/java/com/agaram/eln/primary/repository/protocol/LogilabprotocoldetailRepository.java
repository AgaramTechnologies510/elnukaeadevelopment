package com.agaram.eln.primary.repository.protocol;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.fetchmodel.getorders.LogilabProtocolOrderssh;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

import jakarta.transaction.Transactional;

public interface LogilabprotocoldetailRepository extends JpaRepository<LSlogilabprotocoldetail, Long> {



	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, Long directorycode2, int k, Integer protocoltype2, String orderflag2,
			int l, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			Long directorycode3, int m, Integer protocoltype3, String orderflag3, int n, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int o, Integer protocoltype4,
			String orderflag4, int p, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, Long directorycode2, int j, Integer protocoltype2, String orderflag2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			Long directorycode3, int k, Integer protocoltype3, String orderflag3, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, Long directorycode2, int k, Integer protocoltype2, String orderflag2,
			int l, LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			Long directorycode3, int m, Integer protocoltype3, String orderflag3, int n, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int o, Integer protocoltype4,
			String orderflag4, int p, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, Long directorycode2, int j, Integer protocoltype2, String orderflag2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			Long directorycode3, int k, Integer protocoltype3, String orderflag3, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int m, String orderflag3, int n, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int o, String orderflag4, int p,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int j, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int k, String orderflag3, LSuserMaster createdby2, Date fromdate3,
			Date todate3, Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4,
			Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int m, String orderflag3, int n, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int o, String orderflag4, int p, Date fromdate4, Date todate4,
			Integer[] lstuserMaster2, Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int j, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Long directorycode3, int k, String orderflag3, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int l, String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2,
			Integer sitecode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int m,
			String orderflag3, int n, LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3,
			boolean b, LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int o, String orderflag4, int p, LSuserMaster createdby3, Date fromdate4, Date todate4,
			Integer sitecode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int j, String orderflag2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int k,
			String orderflag3, LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int m,
			String orderflag3, int n, Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3,
			boolean b, LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList,
			Long directorycode4, int o, String orderflag4, int p, Date fromdate4, Date todate4,
			Integer[] lstuserMaster2, Integer sitecode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int j, String orderflag2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int k,
			String orderflag3, Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Long directorycode2, int j, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, Long directorycode3, int k, String orderflag3, Date fromdate3,
			Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, String orderflag4, Date fromdate4,
			Date todate4, Integer[] lstuserMaster2, Integer sitecode4, Integer testcode4);
	
	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Long directorycode2, int j, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, Long directorycode3, int k, String orderflag3,
			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l, String orderflag4,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2, Long directorycode3, int m,
			String orderflag3, int n, Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3,
			boolean b, Integer testcode3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int o, String orderflag4, int p, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, Long directorycode2, int k, String orderflag2, int l, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2, Long directorycode3, int m,
			String orderflag3, int n, LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3,
			boolean b, Integer testcode3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4,
			int o, String orderflag4, int p, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4);
	
	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, LSprojectmaster project, Long directorycode2, int k, String orderflag2, int l,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			LSprojectmaster project2, Long directorycode3, int m, String orderflag3, int n, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int o, String orderflag4, int p,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			LSprojectmaster project, Long directorycode2, int j, String orderflag2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2, LSprojectmaster project2,
			Long directorycode3, int k, String orderflag3, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, String orderflag4,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			LSprojectmaster project, Long directorycode2, int j, String orderflag2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2, LSprojectmaster project2,
			Long directorycode3, int k, String orderflag3, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, String orderflag4, Date fromdate4,
			Date todate4, Integer[] lstuserMaster2, Integer sitecode4, Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, LSprojectmaster project, Long directorycode2, int k,
			Integer protocoltype2, String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, LSprojectmaster project2, Long directorycode3, int m,
			Integer protocoltype3, String orderflag3, int n, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int o, Integer protocoltype4,
			String orderflag4, int p, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, LSprojectmaster project, Long directorycode2, int j,
			Integer protocoltype2, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, LSprojectmaster project2, Long directorycode3, int k,
			Integer protocoltype3, String orderflag3, Date fromdate3, Date todate3, Integer[] lstuserMaster,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			String orderflag4, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, LSprojectmaster project, Long directorycode2, int k,
			Integer protocoltype2, String orderflag2, int l, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, LSprojectmaster project2, Long directorycode3, int m,
			Integer protocoltype3, String orderflag3, int n, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int o, Integer protocoltype4,
			String orderflag4, int p, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, Integer testcode, LSprojectmaster project, Long directorycode2, int j,
			Integer protocoltype2, String orderflag2, LSuserMaster createdby, Date fromdate2, Date todate2,
			Integer sitecode2, Integer testcode2, LSprojectmaster project2, Long directorycode3, int k,
			Integer protocoltype3, String orderflag3, LSuserMaster createdby2, Date fromdate3, Date todate3,
			Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3, boolean c,
			List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			String orderflag4, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, LSprojectmaster project, Long directorycode2, int k, String orderflag2, int l,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			LSprojectmaster project2, Long directorycode3, int m, String orderflag3, int n, Date fromdate3,
			Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int o,
			String orderflag4, int p, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			Integer testcode4, LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int k,
			Integer protocoltype3, LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			Integer protocoltype4, LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby,
			Date fromdate2, Date todate2, Integer sitecode2, LSprojectmaster project2, Long directorycode3, int k,
			Integer protocoltype3, Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b,
			LSprojectmaster project3, boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l,
			Integer protocoltype4, Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby, Date fromdate2,
			Date todate2, Integer sitecode2, Integer testcode2, Long directorycode3, int k, Integer protocoltype3,
			LSuserMaster createdby2, Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, Long directorycode2, int j, Integer protocoltype2, LSuserMaster createdby, Date fromdate2,
			Date todate2, Integer sitecode2, Integer testcode2, Long directorycode3, int k, Integer protocoltype3,
			Date fromdate3, Date todate3, Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4, Integer testcode4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			LSprojectmaster project2, Long directorycode3, int k, Integer protocoltype3, LSuserMaster createdby2,
			Date fromdate3, Date todate3, Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			LSuserMaster createdby3, Date fromdate4, Date todate4, Integer sitecode4, Integer testcode4,
			LSprojectmaster project4);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, Date fromdate, Date todate, Integer sitecode,
			Integer testcode, LSprojectmaster project, Long directorycode2, int j, Integer protocoltype2,
			LSuserMaster createdby, Date fromdate2, Date todate2, Integer sitecode2, Integer testcode2,
			LSprojectmaster project2, Long directorycode3, int k, Integer protocoltype3, Date fromdate3, Date todate3,
			Integer[] lstuserMaster, Integer sitecode3, boolean b, Integer testcode3, LSprojectmaster project3,
			boolean c, List<Long> selectedteamprotcolorderList, Long directorycode4, int l, Integer protocoltype4,
			Date fromdate4, Date todate4, Integer[] lstuserMaster2, Integer sitecode4, Integer testcode4,
			LSprojectmaster project4);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedOrTeamselectedAndProtocolordercodeInAndRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInOrderByProtocolordercodeDesc(
			int i, Integer integer, int j, Date fromdate, Date todate, int k, Integer integer2,
			int l, Integer usercode, Date fromdate2, Date todate2, int m, Integer integer3, int n,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer integer4, int p, Date fromdate4,
			Date todate4, List<Integer> userlist2);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInOrderByProtocolordercodeDesc(
			int i, Integer integer, Date fromdate, Date todate, List<LSprojectmaster> lstproject);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster project);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, LSprojectmaster project, Integer testcode,
			Integer protocoltype);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndTestcodeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer testcode, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer testcode2, int m, Integer sitecode3, int n,
			Date fromdate3, Date todate3, List<Integer> userlist, boolean b, Integer testcode3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer testcode4);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndTestcodeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject, Integer testcode);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndProtocoltypeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatebyAndCreatedtimestampBetweenAndProtocoltypeOrRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndTeamselectedAndProtocoltypeOrTeamselectedAndProtocolordercodeInAndRejectedAndSitecodeAndLsprojectmasterIsNullAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, int j, Date fromdate, Date todate, Integer protocoltype, int k, Integer sitecode2,
			int l, Integer usercode, Date fromdate2, Date todate2, Integer protocoltype2, int m, Integer sitecode3,
			int n, Date fromdate3, Date todate3, List<Integer> userlist, boolean b, Integer protocoltype3, boolean c,
			List<Long> selectedteamprotcolorderList, int o, Integer sitecode4, int p, Date fromdate4, Date todate4,
			List<Integer> userlist2, Integer protocoltype4);

	List<LogilabProtocolOrderssh> findByRejectedAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterInAndProtocoltypeOrderByProtocolordercodeDesc(
			int i, Integer sitecode, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Integer protocoltype);

	List<LSlogilabprotocoldetail> findByOrderflagAndLsprojectmasterAndOrdercancellIsNull(String string,
			LSprojectmaster objClass);

	List<LSlogilabprotocoldetail> findByLsprojectmaster(LSprojectmaster objClass);
	
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
	
	@Transactional
	@Modifying
	@Query("update LSlogilabprotocoldetail o set o.fileuri =?1,o.fileuid=?2,o.containerstored=?3 where o.protocolordercode = ?4")
	void saveUpdateOrderContent(String fileURI, String fileUUID, int i,Long protocolordercode);
	
	@Transactional
	@Modifying
	@Query("UPDATE LSlogilabprotocoldetail l SET l.versionno = ?1 WHERE l.protocolordercode = ?2")
	void updateVersion(Integer versionno, Long protocolordercode);

	@Transactional
	@Modifying
	@Query(value ="update LSlogilabprotocoldetail  set versionno = ?1,approved = ?2,elnprotocolworkflow_workflowcode = ?3 where protocolordercode = ?4",nativeQuery = true)
	void UpdateValuesWorkflow(Integer versionno, Integer approved, Integer workflowcode, Long protocolordercode);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, int j, Date fromdate, Date todate, Integer sitecode,
			List<LSprojectmaster> lstproject, Long directorycode2, int k, String orderflag2, int l, Date fromdate2,
			Date todate2, Integer sitecode2, Long directorycode3, int m, String orderflag3, int n,
			LSuserMaster createdby, Date fromdate3, Date todate3, Integer sitecode3, List<LSprojectmaster> lstproject2,
			Long directorycode4, int o, String orderflag4, int p, LSuserMaster createdby2, Date fromdate4, Date todate4,
			Integer sitecode4, Long directorycode5, int q, String orderflag5, int r, LSuserMaster createdby3,
			Date fromdate5, Date todate5, Integer sitecode5, boolean b, List<LSprojectmaster> lstproject3,
			Long directorycode6, int s, String orderflag6, int t, LSuserMaster createdby4, Date fromdate6, Date todate6,
			Integer sitecode6, boolean c, boolean d, List<Long> selectedteamprotcolorderList, Long directorycode7,
			int u, String orderflag7, int v, LSuserMaster createdby5, Date fromdate7, Date todate7, Integer sitecode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, String orderflag, Date fromdate, Date todate, Integer sitecode,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, String orderflag2, Date fromdate2,
			Date todate2, Integer sitecode2, Long directorycode3, int k, String orderflag3, LSuserMaster createdby,
			Date fromdate3, Date todate3, Integer sitecode3, List<LSprojectmaster> lstproject2, Long directorycode4,
			int l, String orderflag4, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4,
			Long directorycode5, int m, String orderflag5, LSuserMaster createdby3, Date fromdate5, Date todate5,
			Integer sitecode5, boolean b, List<LSprojectmaster> lstproject3, Long directorycode6, int n,
			String orderflag6, LSuserMaster createdby4, Date fromdate6, Date todate6, Integer sitecode6, boolean c,
			boolean d, List<Long> selectedteamprotcolorderList, Long directorycode7, int o, String orderflag7,
			LSuserMaster createdby5, Date fromdate7, Date todate7, Integer sitecode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, int j, Date fromdate, Date todate,
			Integer sitecode, List<LSprojectmaster> lstproject, Long directorycode2, int k, Integer protocoltype2,
			String orderflag2, int l, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int m,
			Integer protocoltype3, String orderflag3, int n, LSuserMaster createdby, Date fromdate3, Date todate3,
			Integer sitecode3, List<LSprojectmaster> lstproject2, Long directorycode4, int o, Integer protocoltype4,
			String orderflag4, int p, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4,
			Long directorycode5, int q, Integer protocoltype5, String orderflag5, int r, LSuserMaster createdby3,
			Date fromdate5, Date todate5, Integer sitecode5, boolean b, List<LSprojectmaster> lstproject3,
			Long directorycode6, int s, Integer protocoltype6, String orderflag6, int t, LSuserMaster createdby4,
			Date fromdate6, Date todate6, Integer sitecode6, boolean c, boolean d,
			List<Long> selectedteamprotcolorderList, Long directorycode7, int u, Integer protocoltype7,
			String orderflag7, int v, LSuserMaster createdby5, Date fromdate7, Date todate7, Integer sitecode7);

	List<LSlogilabprotocoldetail> findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
			Long directorycode, int i, Integer protocoltype, String orderflag, Date fromdate, Date todate,
			Integer sitecode, List<LSprojectmaster> lstproject, Long directorycode2, int j, Integer protocoltype2,
			String orderflag2, Date fromdate2, Date todate2, Integer sitecode2, Long directorycode3, int k,
			Integer protocoltype3, String orderflag3, LSuserMaster createdby, Date fromdate3, Date todate3,
			Integer sitecode3, List<LSprojectmaster> lstproject2, Long directorycode4, int l, Integer protocoltype4,
			String orderflag4, LSuserMaster createdby2, Date fromdate4, Date todate4, Integer sitecode4,
			Long directorycode5, int m, Integer protocoltype5, String orderflag5, LSuserMaster createdby3,
			Date fromdate5, Date todate5, Integer sitecode5, boolean b, List<LSprojectmaster> lstproject3,
			Long directorycode6, int n, Integer protocoltype6, String orderflag6, LSuserMaster createdby4,
			Date fromdate6, Date todate6, Integer sitecode6, boolean c, boolean d,
			List<Long> selectedteamprotcolorderList, Long directorycode7, int o, Integer protocoltype7,
			String orderflag7, LSuserMaster createdby5, Date fromdate7, Date todate7, Integer sitecode7);

	@Transactional
	@Modifying
	@Query("UPDATE LSlogilabprotocoldetail  SET repeat = ?1 WHERE protocolordercode = ?2 ")
	void updateRepeat(boolean b, Long batchcode);

	



}
