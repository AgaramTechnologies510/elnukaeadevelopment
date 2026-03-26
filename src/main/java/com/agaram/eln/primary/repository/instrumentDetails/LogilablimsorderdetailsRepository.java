package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrderDetails;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrdermastersh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabordermaster;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborders;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborderssh;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail.ordersinterface;
import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.sheetManipulation.LSfile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplemaster;
import com.agaram.eln.primary.model.sheetManipulation.LStestmasterlocal;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LogilablimsorderdetailsRepository extends JpaRepository<LSlogilablimsorderdetail, Long> {
	
	LogilabOrderDetails findByBatchcode(Long batchcode);	
	
	@Transactional
	@Modifying
	@Query(value="update LSlogilablimsorderdetail set lockeduser=?1 ,lockedusername=?2 ,activeuser=?3 where batchcode=?4",nativeQuery=true)
	void UpdateOrderData(Integer usercode, String username, Integer activeuser, Long long1);

	@Transactional
	@Modifying
	@Query(value = "update LSlogilablimsorderdetail set lockeduser = null, lockedusername = null, activeuser = null where batchcode = ?1", nativeQuery = true)
	void UpdateOrderOnunlockData(Long batchcode);
	
	@Transactional
	@Query(value="select * from LSlogilablimsorderdetail where batchcode= ?1 ", nativeQuery=true)
	List<LSlogilablimsorderdetail> getOrderDetails(Long batchcode);

	long countByOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusNotOrOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusIsNull(
			String string, int i, Date fromdate, Date todate, int j, String string2, int k, Date fromdate2,
			Date todate2);

	long countByOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusNotOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusIsNull(
			String string, int i, LSuserMaster objuser, Date fromdate, Date todate, int j, String string2, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2);

	long countByOrderflagAndLsprojectmasterIsNullAndViewoptionAndOrdercancellIsNullAndCreatedtimestampBetweenAndLsuserMasterInAndAssignedtoIsNullAndApprovelstatusNotOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndOrdercancellIsNullAndCreatedtimestampBetweenAndLsuserMasterInAndAssignedtoIsNullAndApprovelstatusIsNull(
			String string, int i, Date fromdate, Date todate, List<LSuserMaster> usernotify, int j, String string2,
			int k, Date fromdate2, Date todate2, List<LSuserMaster> usernotify2);

	long countByOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusNotOrOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusIsNull(
			String string, LSuserMaster objuser, LSuserMaster objuser2, Date fromdate, Date todate, int i,
			String string2, LSuserMaster objuser3, LSuserMaster objuser4, Date fromdate2, Date todate2);

	long countByOrderflagAndAssignedtoAndCreatedtimestampBetweenAndApprovelstatusNotOrOrderflagAndAssignedtoAndCreatedtimestampBetweenAndApprovelstatusIsNull(
			String string, LSuserMaster objuser, Date fromdate, Date todate, int i, String string2,
			LSuserMaster objuser2, Date fromdate2, Date todate2);

	long countByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndOrdercancellIsNullAndAssignedtoIsNullAndApprovelstatusNotOrOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndOrdercancellIsNullAndAssignedtoIsNullAndApprovelstatusIsNull(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int i, String string2,
			List<LSprojectmaster> lstproject2, Date fromdate2, Date todate2);

	long countByOrderflagAndLsprojectmasterInAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusNotOrOrderflagAndLsprojectmasterInAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusIsNull(
			String string, List<LSprojectmaster> lstproject, int i, LSuserMaster objuser, Date fromdate, Date todate,
			int j, String string2, List<LSprojectmaster> lstproject2, int k, LSuserMaster objuser2, Date fromdate2,
			Date todate2);
	
	@Transactional
	@Modifying
	@Query(value = "WITH TeamProjects AS ( " +
	        "   SELECT DISTINCT lsprojectmaster_projectcode " +
	        "   FROM LSlogilablimsorderdetail " +
	        "   WHERE lsprojectmaster_projectcode IN ( " +
	        "       SELECT projectcode " +
	        "       FROM LSprojectmaster " +
	        "       WHERE lsusersteam_teamcode IN ( " +
	        "           SELECT DISTINCT teamcode " +
	        "           FROM LSuserteammapping " +
	        "           WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL AND lssitemaster_sitecode = ?16) " +
	        "       AND status = 1 " +
	        ") " +
	        ") " +
	        "SELECT * FROM LSlogilablimsorderdetail o " +
	        "WHERE"+
	        "(o.orderflag = ?1 AND sitecode = ?16 AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "AND ( " +
	        "   (filetype = ?2 AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)) " +
	        
	        "   OR "+
	        "   (lsprojectmaster_projectcode IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL AND createdtimestamp BETWEEN ?3 AND ?4 " +
			 "       AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL) " +
			 "       AND (viewoption = ?8 AND teamselected = ?17 AND batchcode IN ?15)"+ 
			 "   ) " +
			 
			 "   OR "+
	        "   (lsprojectmaster_projectcode IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL AND createdtimestamp BETWEEN ?3 AND ?4" +
	        "       AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL) " +
	        "       AND ((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
	        "             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
	        "             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = ?18)"+
	        "           ) " +
	        "   ) " +
	   
	        "   OR (lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND assignedto_usercode IS NOT NULL AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)) " +
	        "   OR (assignedto_usercode = ?5 AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)) " +
	        "   OR (o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects) " +
	        "       AND ordercancell IS NULL AND assignedto_usercode IS NULL AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)) " +
	        "   OR (o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects) " +
	        "       AND lsusermaster_usercode = ?5 AND ordercancell IS NULL AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)) " +
	        "   OR (lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN " +
	        "       (SELECT DISTINCT elnmaterial_nmaterialcode " +
	        "        FROM lslogilablimsorderdetail " +
	        "        WHERE elnmaterial_nmaterialcode IN " +
	        "              (SELECT m.nmaterialcode " +
	        "               FROM elnmaterial m " +
	        "               WHERE m.nsitecode = ?10)) " +
	        "       AND ordercancell IS NULL AND assignedto_usercode IS NULL AND lsusermaster_usercode != ?5 " +
	        "       AND (o.Approvelstatus != ?13 OR o.Approvelstatus IS NULL)"+
	        "		AND (viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8)"+
	        "	) " +
	        ")) " +
	      "OR "+
	        "( " +
	        "    (o.orderflag = ?14 AND filetype = ?2 AND sitecode = ?16 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL) " +
	        "    OR (o.orderflag = ?14 AND sitecode = ?16 AND lsprojectmaster_projectcode IS NULL AND " +
	        "        ("+
			"		((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
            "             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
            "             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = ?18)"+
            "       ) " +
	        "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
	        "         ((approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
	        "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
	        "		) " +
	        "    ) " +
	        
			  "    OR (o.orderflag = ?14 AND sitecode = ?16 AND lsprojectmaster_projectcode IS NULL AND " +
			  "        ("+
			  "         (viewoption = ?8 AND batchcode IN ?15 AND teamselected =?17)"+
			  "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
			  "         ((approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
			  "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
			  "		) " +
			  "    ) " +
  
  
	        "    OR (o.orderflag = ?14 AND sitecode = ?16 AND " +
	        "        ((lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL) " +
	        "         OR (assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4)) " +
	        "    ) " +
	        "    OR (o.orderflag = ?14 AND sitecode = ?16 AND " +
	        "        (o.lsprojectmaster_projectcode IN ( " +
	        "            SELECT lsprojectmaster_projectcode FROM LSlogilablimsorderdetail " +
	        "            WHERE lsprojectmaster_projectcode IN ( " +
	        "                SELECT DISTINCT projectcode FROM LSprojectmaster " +
	        "                WHERE lsusersteam_teamcode IN ( " +
	        "                    SELECT teamcode FROM LSuserteammapping " +
	        "                    WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL" +
	        "                ) AND status = 1 " +
	        "            )" +
	        "        ) AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "        AND approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
	        "    ) " +
	        "    OR (o.orderflag = ?14 AND sitecode = ?16 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
	        "            ) " +
	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "            AND approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
	        "            AND lsusermaster_usercode != ?5 " +
	        "			 AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
	        "        ) " +
	        "    ) " +
	        "    OR (o.orderflag = ?14 AND sitecode = ?16 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
	        "            ) " +
	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "            AND approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
	        "            AND lsusermaster_usercode != ?5 " +
	        "			 AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
	        "        ) " +
	        "    ) " +
	        ") " +
	        "  OR(" + 
	        
		  " (lsprojectmaster_projectcode IS NULL AND sitecode = ?16 AND "
			+ "("+
				"(viewoption = ?8 AND batchcode IN ?15 AND lsusermaster_usercode IN (?9) AND teamselected =?17)"
				+ "AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?13 AND assignedto_usercode IS NULL"+
			"	)"
			+ ")"
	
	        + "OR (lsprojectmaster_projectcode IS NULL AND sitecode = ?16 AND "
			+ "        ("+
			"((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
			"             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
			"             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected =?18)"+
			"       ) " 
			+ "   AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?13 AND assignedto_usercode IS NULL"+
			"		)"
			+ ")"
			+" OR (lsusermaster_usercode = ?5 AND sitecode = ?16 AND  assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL AND approvelstatus =?13)"
			+ "OR(assignedto_usercode = ?5 AND sitecode = ?16 AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?13)"
			+ "OR(approvelstatus =?13 AND sitecode = ?16 AND o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects ) AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL)"
			+ ")" +

			" OR (" 
				+ " (lsprojectmaster_projectcode IS NULL AND sitecode = ?16 AND "
				+ " ("+
				"(viewoption = ?8 AND batchcode IN ?15 AND teamselected =?17)"
				+ " AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL "+
				")"
				+ ")"
				+"OR(lsprojectmaster_projectcode IS NULL AND sitecode = ?16 AND "
				+ "        ("+
				"((viewoption = ?6 AND lsusermaster_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL) OR " +
				"             (viewoption = ?7 AND lsusermaster_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL) OR " +
				"             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected =?18 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL)"+
				"       ) " 
				+ ")"
				+ ")"
			+"OR (ordercancell =?6 AND sitecode = ?16 AND o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects ) AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL)"
			+"OR(lsusermaster_usercode = ?5 AND sitecode = ?16 AND assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL AND ordercancell =?6)"
			+"OR(assignedto_usercode = ?5 AND sitecode = ?16 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 )"+
			")" +
	        "ORDER BY batchcode DESC " +
	        "OFFSET ?11 ROWS FETCH NEXT ?12 ROWS ONLY", nativeQuery = true)
	List<LSlogilablimsorderdetail> getLSlogilablimsorderdetaildashboardforallorders(
	        String orderFlag,
	        int fileType,
	        Date fromDate,
	        Date toDate,
	        Integer integer,
	        int viewOption1,
	        int viewOption2,
	        int viewOption3,
	        List<Integer> usercodelist,
	        Integer integer2,
	        int offset,
	        Integer pageSize,
	        Integer approvalStatus,
	        String completedOrderFlag,
	        List<Long> selectedbatchcode, Integer sitecode,
	        Boolean teamtrue,Boolean teamfalse);

//	@Transactional
//	@Modifying
//	@Query(value = "WITH TeamProjects AS ( " +
//	        "   SELECT DISTINCT lsprojectmaster_projectcode " +
//	        "   FROM LSlogilablimsorderdetail " +
//	        "   WHERE lsprojectmaster_projectcode IN ( " +
//	        "       SELECT projectcode " +
//	        "       FROM LSprojectmaster " +
//	        "       WHERE lsusersteam_teamcode IN ( " +
//	        "           SELECT DISTINCT teamcode " +
//	        "           FROM LSuserteammapping " +
//	        "           WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL) " +
//	        "       AND status = 1 " +
//	        ") " +
//	        ") " +
//	        "SELECT * FROM LSlogilablimsorderdetail o " +
//	        "WHERE"+
//	        "( " +
//	        "    (o.orderflag = ?14 AND o.orderflag != ?1 AND filetype = ?2 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL) " +
//	        "    OR (o.orderflag = ?14 AND lsprojectmaster_projectcode IS NULL AND " +
//	        "        ("+
//			"		((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
//            "             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
//            "             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = false)"+
//            "       ) " +
//	        "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
//	        "         ((approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
//	        "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
//	        "		) " +
//	        "    ) " +
//	        
//			  "    OR (o.orderflag = ?14 AND lsprojectmaster_projectcode IS NULL AND " +
//			  "        ("+
//			  "         (viewoption = ?8 AND batchcode IN ?15 AND teamselected = true)"+
//			  "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
//			  "         ((approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
//			  "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
//			  "		) " +
//			  "    ) " +
//  
//  
//	        "    OR (o.orderflag = ?14 AND " +
//	        "        ((lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL) " +
//	        "         OR (assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4)) " +
//	        "    ) " +
//	        "    OR (o.orderflag = ?14 AND " +
//	        "        (o.lsprojectmaster_projectcode IN ( " +
//	        "            SELECT lsprojectmaster_projectcode FROM LSlogilablimsorderdetail " +
//	        "            WHERE lsprojectmaster_projectcode IN ( " +
//	        "                SELECT DISTINCT projectcode FROM LSprojectmaster " +
//	        "                WHERE lsusersteam_teamcode IN ( " +
//	        "                    SELECT teamcode FROM LSuserteammapping " +
//	        "                    WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL" +
//	        "                ) AND status = 1 " +
//	        "            )" +
//	        "        ) AND createdtimestamp BETWEEN ?3 AND ?4 " +
//	        "        AND approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
//	        "    ) " +
//	        "    OR (o.orderflag = ?14 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
//	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
//	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
//	        "            ) " +
//	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
//	        "            AND approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
//	        "            AND lsusermaster_usercode != ?5 " +
//	        "			 AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
//	        "        ) " +
//	        "    ) " +
//	        "    OR (o.orderflag = ?14 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
//	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
//	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
//	        "            ) " +
//	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
//	        "            AND approvelstatus != ?13 AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
//	        "            AND lsusermaster_usercode != ?5 " +
//	        "			AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
//	        "        ) " +
//	        "    ) " +
//	        ") " +
//	        "ORDER BY batchcode DESC " +
//	        "OFFSET ?11 ROWS FETCH NEXT ?12 ROWS ONLY", nativeQuery = true)
//	List<LSlogilablimsorderdetail> getLSlogilablimsorderdetaildashboardforallorders(
//	        String orderFlag,
//	        int fileType,
//	        Date fromDate,
//	        Date toDate,
//	        LSuserMaster user,
//	        int viewOption1,
//	        int viewOption2,
//	        int viewOption3,
//	        List<LSuserMaster> userNotify,
//	        LSSiteMaster siteMaster,
//	        int offset,
//	        Integer pageSize,
//	        Integer approvalStatus,
//	        String completedOrderFlag,
//	        List<Long> selectedbatchcode);
	
		List<LogilabOrderDetails> findByFiletypeAndLsfileInAndApprovelstatusNotOrFiletypeAndLsfileInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			int filetype, List<LSfile> lSfiles, int i, int filetype2, List<LSfile> lSfiles2);

	List<LogilabOrderDetails> findByLsprojectmasterInAndFiletypeAndLsfileInAndApprovelstatusNotAndOrdercancellIsNullOrLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfileInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<LSprojectmaster> projects, int filetype, List<LSfile> lSfiles, int i, List<LSprojectmaster> projects2,
			int filetype2, List<LSfile> lSfiles2);

	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndLssamplemasterIsNullAndFiletypeAndAssignedtoIsNullAndLsfileInOrderByBatchcodeDesc(
			int filetype, List<LSfile> lSfiles);

	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileInOrderByBatchcodeDesc(
			List<Elnmaterial> materials, int filetype, int i, List<LSfile> lSfiles);

	List<LogilabOrderDetails> findByFiletypeAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			Integer filetype, int i, Integer filetype2);

	List<LogilabOrderDetails> findByFiletypeAndLsprojectmasterInAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndLsprojectmasterInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			Integer filetype, List<LSprojectmaster> lstproject, int i, Integer filetype2,
			List<LSprojectmaster> lstproject2);

	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoption(
			List<Elnmaterial> nmaterialcode, Integer filetype, int i);//ku
	
	long countByLsuserMasterIn(List<LSuserMaster> lstuser);

	long countByLsprojectmaster(LSprojectmaster objproject);
	long countByLstestmasterlocal(LStestmasterlocal objtest);
	long countByFiletype(Integer filetype);

	List<Logilaborders> findByBatchcodeIn(List<Long> selectedteambatchCodeList);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Long directorycode2, int j,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, List<Long> selectedteambatchCodeList);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Long directorycode2, int j,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, Long directorycode3, int k, Date fromdate3,
			Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList);

	List<LSlogilablimsorderdetail> findByDirectorycodeAndViewoption(Long directorycode, int i);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Long directorycode2, int j,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, List<Long> selectedteambatchCodeList);

	Collection<? extends Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2, Date fromdate2, Date todate2,
			Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList);

	Collection<? extends Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Long directorycode2, int j, Integer filetype2, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, Long directorycode3, int k, Integer filetype3, Date fromdate3,
			Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndLsselectedTeamIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndLsselectedTeamIsNotNullAndBatchcodeInAndDirectorycodeInOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, List<LSuserMaster> lstuserMaster,
			String orderflag, Integer filetype, List<Long> directory_Code2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster2, String orderflag2, Integer filetype2,
			List<Long> selectedteambatchCodeList, List<Long> selectedteamdirctoryList);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, List<LSuserMaster> lstuserMaster,
			String orderflag, Integer filetype);


	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			List<Long> selectedteamdirctoryList, int i, Date fromdate, Date todate, List<LSuserMaster> lstuserMaster,
			String orderflag, Integer filetype, List<Long> selectedteambatchCodeList);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3);


	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
			Integer testcode3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
			Integer testcode3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
			int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
			Integer approvelstatus3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, String orderflag3, Integer filetype3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndLsselectedTeamIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
			Integer approvelstatus3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndBatchcodeNotInOrBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, List<LSuserMaster> lstuserMaster,
			String orderflag, Integer filetype, List<Long> selectedteambatchCodeList,
			List<Long> selectedteambatchCodeList2, List<Long> directory_Code2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster2, String orderflag2, Integer filetype2);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3, boolean b, boolean c,
			List<Long> directory_Code4, int l, Date fromdate4, Date todate4, List<LSuserMaster> distinctLsuserMasters,
			String orderflag4, Integer filetype4);

//	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTeamselectedOrLsprojectmasterIsNullAndDirectorycodeInOrViewoptionAndTeamselectedAndCreatedtimestampBetweenAndBatchcodeInOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterOrderByBatchcodeDesc(
//			int i, Date fromdate, Date todate, LSuserMaster objuser, boolean b, List<Long> directorycode, int j,
//			boolean c, Date fromdate2, Date todate2, List<Long> selectedteambatchCodeList, LSuserMaster objuser2,
//			List<Long> directorycode2, int k, Date fromdate3, Date todate3, List<Long> directorycode3, int l,
//			Date fromdate4, Date todate4, LSuserMaster objuser3, List<Long> directorycode4, int m, Date fromdate5,
//			Date todate5, LSuserMaster objuser4, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTeamselectedAndOrderflagAndOrdercancellIsNullOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, List<Long> directorycode, boolean b,
			int j, boolean c, List<Long> selectedteambatchCodeList, Date fromdate2, Date todate2, LSuserMaster objuser2,
			String string2, List<Long> directorycode2, int k, Date fromdate3, Date todate3, String string3,
			List<Long> directorycode3, int l, Date fromdate4, Date todate4, LSuserMaster objuser3, String string4,
			List<Long> directorycode4, int m, Date fromdate5, Date todate5, LSuserMaster objuser4, boolean d,
			String string5, int n, boolean e, List<Long> selectedteambatchCodeList2, Date fromdate6, Date todate6,
			LSuserMaster objuser5, String string6, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, List<Long> directorycode, boolean b,
			int j, boolean c, List<Long> selectedteambatchCodeList, Date fromdate2, Date todate2, LSuserMaster objuser2,
			String string2, List<Long> directorycode2, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, List<Long> directorycode,
			boolean b);

	List<LogilabOrdermastersh> findByViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInOrderByBatchcodeDesc(
			int i, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, LSuserMaster objuser,
			String string, List<Long> directorycode);

	List<LogilabOrdermastersh> findByViewoptionAndTeamselectedAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullOrLsprojectmasterIsNullAndDirectorycodeInOrderByBatchcodeDesc(
			int i, boolean b, Date fromdate, Date todate, LSuserMaster objuser, String string, List<Long> directorycode,
			Pageable pageable);

	
	
	
	
	
	
	
	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, List<Long> directory_Code2,
			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, String orderflag2,
			List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3,
			String orderflag3, boolean b, boolean c, List<Long> selectedteambatchCodeList, List<Long> directory_Code4,
			int l, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, String orderflag4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, List<Long> directory_Code2,
			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, String orderflag2,
			List<Long> directory_Code3, int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster,
			String orderflag3, boolean b, boolean c, List<Long> selectedteambatchCodeList, List<Long> directory_Code4,
			int l, Date fromdate4, Date todate4, List<LSuserMaster> lstuserMaster2, String orderflag4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
			Integer approvelstatus3, boolean b, boolean c, List<Long> selectedteambatchCodeList,
			List<Long> directory_Code4, int l, Date fromdate4, Date todate4, List<LSuserMaster> lstuserMaster2,
			String orderflag4, Integer filetype4, Integer approvelstatus4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
			int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
			Integer approvelstatus3, boolean b, boolean c, List<Long> selectedteambatchCodeList,
			List<Long> directory_Code4, int l, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4,
			String orderflag4, Integer filetype4, Integer approvelstatus4);



	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
			Integer testcode3, boolean b, boolean c, List<Long> selectedteambatchCodeList, List<Long> directory_Code4,
			int l, Date fromdate4, Date todate4, List<LSuserMaster> lstuserMaster2, String orderflag4,
			Integer filetype4, Integer testcode4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
			Integer testcode3, boolean b, boolean c, List<Long> selectedteambatchCodeList, List<Long> directory_Code4,
			int l, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, String orderflag4, Integer filetype4,
			Integer testcode4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, String orderflag3, Integer filetype3, boolean b, boolean c,
			List<Long> selectedteambatchCodeList, List<Long> directory_Code4, int l, LSuserMaster lsuserMaster3,
			Date fromdate4, Date todate4, String orderflag4, Integer filetype4);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3, boolean b);
	
//	@Transactional
//	@Modifying
//	@Query(value = 
//    "SELECT * FROM LSlogilablimsorderdetail o " +
//    "WHERE " +
//    "    (" +
//    "        o.orderflag = ?1 " +
//    "        AND o.filetype = ?2 " +
//    "        AND o.createdtimestamp BETWEEN ?3 AND ?4 " +
//    "        AND o.assignedto_usercode IS NULL " +
//    "    ) " +
//    "    OR (" +
//    "        o.orderflag = ?1 " +
//    "        AND o.lsprojectmaster_projectcode IS NULL " +
//    "        AND ((" +
//    "            (o.viewoption = ?7 OR o.viewoption = ?8 OR (o.viewoption = ?9 AND o.teamselected = false)) " +
//    "            AND o.lsusermaster_usercode = ?5 " +
//    "            AND o.createdtimestamp BETWEEN ?3 AND ?4 " +
//    "            AND (" +
//    "                (o.approvelstatus != ?9 AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "                OR (o.approvelstatus IS NULL AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "            ) " +
//    "        ) " +
//    "        OR (" +
//    "            (o.viewoption = ?9 AND o.teamselected = true AND o.batchcode IN (?12)) " +
//    "            AND o.createdtimestamp BETWEEN ?3 AND ?4 " +
//    "            AND (" +
//    "                (o.approvelstatus != ?6 AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "                OR (o.approvelstatus IS NULL AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "            ) " +
//    "        ) " +
//    "    ) " +
//    "    OR (" +
//    "        o.orderflag = ?1 " +
//    "        AND o.lsprojectmaster_projectcode IN (?13) " +
//    "        AND o.createdtimestamp BETWEEN ?3 AND ?4 " +
//    "        AND (" +
//    "            (o.approvelstatus != ?6 AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "            OR (o.approvelstatus IS NULL AND o.ordercancell IS NULL AND o.assignedto_usercode IS NULL) " +
//    "        ) " +
//    "    )) " +
//    "ORDER BY o.batchcode DESC " +
//    "LIMIT ?11 OFFSET ?10",
//    nativeQuery = true
//)
//	List<LSlogilablimsorderdetail> getLSlogilablimsorderdetaildashboardforcompleted(String string, int i, Date fromdate,
//			Date todate,LSuserMaster objuser , int j, int k, int l, int m,int n,
//			Integer pageperorder,List<Long> batchcode, List<LSprojectmaster> lstproject, Integer integer);
	
	@Transactional
	@Modifying
	@Query(value = 
    "SELECT * FROM LSlogilablimsorderdetail o " +
    "WHERE " +
    "    (" +
    "        o.orderflag = ?1 " +
    "        AND filetype = ?2 " +
    "        AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "        AND assignedto_usercode IS NULL " +
    "    ) " +
    "    OR (" +
    "        o.orderflag = ?1 " +
    "        AND lsprojectmaster_projectcode IS NULL " +
    "        AND ((" +
    "            (viewoption = ?7 OR viewoption = ?8 OR (viewoption = ?9 AND teamselected=false)) " +
    "            AND lsusermaster_usercode = ?5 " +
    "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "            AND (" +
    "                (approvelstatus != ?6 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
    "                OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
    "            ) " +
    "        ) " +
    "		OR (" +
    " 			(viewoption = ?9 AND teamselected=true AND batchcode IN ?12) " +
    " 			AND createdtimestamp BETWEEN ?3 AND ?4 " +
    " 			AND (" +
    " 			(approvelstatus != ?6 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
    " 			OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
    " 			) " +
    "		)) " +
    "    ) " +
    "    OR (" +
    "        o.orderflag = ?1 " +
    "        AND (" +
    "            (lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL) " +
    "            OR (assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4) " +
    "        ) " +
    "    ) " +
    "    OR (" +
    "        o.orderflag = ?1 " +
    "        AND (" +
    "            (" +
    "                o.lsprojectmaster_projectcode IN (" +
    "                    SELECT lsprojectmaster_projectcode " +
    "                    FROM LSlogilablimsorderdetail " +
    "                    WHERE lsprojectmaster_projectcode IN (" +
    "                        SELECT DISTINCT projectcode " +
    "                        FROM LSprojectmaster " +
    "                        WHERE lsusersteam_teamcode IN (" +
    "                            SELECT teamcode " +
    "                            FROM LSuserteammapping " +
    "                            WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL " +
    "                        ) AND status = 1 " +
    "                    ) " +
    "                ) " +
    "                AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "                AND approvelstatus != ?6 " +
    "                AND ordercancell IS NULL " +
    "                AND assignedto_usercode IS NULL " +
    "            ) " +
    "            OR (" +
    "                o.lsprojectmaster_projectcode IN (" +
    "                    SELECT lsprojectmaster_projectcode " +
    "                    FROM LSlogilablimsorderdetail " +
    "                    WHERE lsprojectmaster_projectcode IN (" +
    "                        SELECT DISTINCT projectcode " +
    "                        FROM LSprojectmaster " +
    "                        WHERE lsusersteam_teamcode IN (" +
    "                            SELECT teamcode " +
    "                            FROM LSuserteammapping " +
    "                            WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL " +
    "                        ) AND status = 1 " +
    "                    ) " +
    "                ) " +
    "                AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "                AND approvelstatus IS NULL " +
    "                AND ordercancell IS NULL " +
    "                AND assignedto_usercode IS NULL " +
    "            ) " +
    "            OR (" +
    "                o.lsprojectmaster_projectcode IN (" +
    "                    SELECT lsprojectmaster_projectcode " +
    "                    FROM LSlogilablimsorderdetail " +
    "                    WHERE lsprojectmaster_projectcode IN (" +
    "                        SELECT DISTINCT projectcode " +
    "                        FROM LSprojectmaster " +
    "                        WHERE lsusersteam_teamcode IN (" +
    "                            SELECT teamcode " +
    "                            FROM LSuserteammapping " +
    "                            WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL " +
    "                        ) AND status = 1 " +
    "                    ) " +
    "                ) " +
    "                AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "                AND viewoption = ?9 " +
    "                AND lsusermaster_usercode = ?5 " +
    "                AND approvelstatus != ?6 " +
    "                AND ordercancell IS NULL " +
    "                AND assignedto_usercode IS NULL " +
    "            ) " +
    "        ) " +
    "    ) " +
    "    OR (" +
    "        o.orderflag = ?1 " +
    "        AND lsprojectmaster_projectcode IS NULL " +
    "        AND elnmaterial_nmaterialcode IN (" +
    "            SELECT DISTINCT elnmaterial_nmaterialcode " +
    "            FROM lslogilablimsorderdetail " +
    "            WHERE elnmaterial_nmaterialcode IN (" +
    "                SELECT m.nmaterialcode " +
    "                FROM elnmaterial m " +
    "                WHERE m.nsitecode = ?10 " +
    "            ) " +
    "        ) " +
    "        AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "        AND approvelstatus IS NULL " +
    "        AND ordercancell IS NULL " +
    "        AND assignedto_usercode IS NULL " +
    "        AND lsusermaster_usercode != ?5 " +
    "    ) " +
    "    OR (" +
    "        o.orderflag = ?1 " +
    "        AND lsprojectmaster_projectcode IS NULL " +
    "        AND elnmaterial_nmaterialcode IN (" +
    "            SELECT DISTINCT elnmaterial_nmaterialcode " +
    "            FROM lslogilablimsorderdetail " +
    "            WHERE elnmaterial_nmaterialcode IN (" +
    "                SELECT m.nmaterialcode " +
    "                FROM elnmaterial m " +
    "                WHERE m.nsitecode = ?10 " +
    "            ) " +
    "        ) " +
    "        AND createdtimestamp BETWEEN ?3 AND ?4 " +
    "        AND approvelstatus != ?6 " +
    "        AND ordercancell IS NULL " +
    "        AND assignedto_usercode IS NULL " +
    "        AND lsusermaster_usercode != ?5 " +
    "    ) " +
    "ORDER BY batchcode DESC " +
    "OFFSET ?11 ROWS FETCH NEXT ?12 ROWS ONLY", nativeQuery = true
)

	List<LSlogilablimsorderdetail> getLSlogilablimsorderdetaildashboardforcompleted(String string, int i, Date fromdate,
			Date todate, LSuserMaster objuser, int j, int k, int l, int m, LSSiteMaster lssitemaster, int n,
			Integer pageperorder);

	List<LogilabOrdermastersh> findByLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNull(
			int i, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, int j);

	List<Logilaborderssh> findByTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			boolean b, List<Long> selectedteambatchCodeList, List<Long> directory_Code, int i, Date fromdate,
			Date todate, List<LSuserMaster> lstuserMaster, String orderflag, Integer filetype);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTeamselectedAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3, boolean b);


	List<Logilaborderssh> findByOrderflagAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNull(
			String orderflag, Integer filetype, LSprojectmaster lsprojectmaster, Date fromdate, Date todate);

	List<LogilabOrdermastersh> findByTeamselectedAndBatchcodeInAndLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterOrderByBatchcodeDesc(
			boolean b, List<Long> selectedteambatchCodeList, List<Long> directorycode, int i, Date fromdate,
			Date todate, LSuserMaster objuser, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndTeamselectedOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndTeamselectedOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, Integer testcode, boolean b,
			List<Long> directorycode, int j, Date fromdate2, Date todate2, Integer testcode2, List<Long> directorycode2,
			int k, Date fromdate3, Date todate3, LSuserMaster objuser2, Integer testcode3, List<Long> directorycode3,
			int l, Date fromdate4, Date todate4, LSuserMaster objuser3, Integer testcode4, boolean c, int m, boolean d,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, LSuserMaster objuser4,
			Integer testcode5, List<Long> directorycode4, boolean e, List<Long> selectedteambatchCodeList2, int n,
			Date fromdate6, Date todate6, LSuserMaster objuser5, Integer testcode6, Pageable pageable);



	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterInAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterInAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate, Date todate, int i,
			List<LSuserMaster> lstuserMaster, boolean b, boolean c, List<Long> selectedteambatchCodeList,
			String orderflag2, List<Elnmaterial> currentChunk2, Integer filetype2, Date fromdate2, Date todate2, int j,
			List<LSuserMaster> lstuserMaster2);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndElnmaterialIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTeamselectedAndAssignedtoIsNullAndElnmaterialIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, boolean b, List<Long> selectedteambatchCodeList,
			List<Long> directory_Code3, int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster,
			String orderflag3, Integer filetype3, List<Long> directory_Code4, int l, Date fromdate4, Date todate4,
			List<LSuserMaster> lstuserMaster2, String orderflag4, Integer filetype4, boolean c);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate, Date todate, int i,
			boolean b, boolean c, List<Long> selectedteambatchCodeList, String orderflag2,
			List<Elnmaterial> currentChunk2, Integer filetype2, Date fromdate2, Date todate2, int j);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<Elnmaterial> nmaterialcode, Integer filetype, Date fromdate, Date todate, int i,
			LSuserMaster lsuserMaster, boolean b, boolean c, List<Long> selectedteambatchCodeList, String orderflag2,
			List<Elnmaterial> nmaterialcode2, Integer filetype2, Date fromdate2, Date todate2, int j);

	Long countBySitecode(Integer sitecode);

	LSlogilablimsorderdetail findBySequenceid(String batchid);

	List<Logilabordermaster> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseAndTestnameNotContainingIgnoreCaseOrViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordIsNullAndTestnameIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseAndTestnameNotContainingIgnoreCaseOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordIsNullAndTestnameIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseAndTestnameNotContainingIgnoreCaseOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordIsNullAndTestnameIsNullOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordNotContainingIgnoreCaseAndTestnameNotContainingIgnoreCaseOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndBatchidContainingIgnoreCaseAndSequenceidContainingIgnoreCaseAndKeywordIsNullAndTestnameIsNullOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String searchkeywords, String searchkeywords2,
			String searchkeywords3, String searchkeywords4, int j, Date fromdate2, Date todate2, LSuserMaster objuser2,
			String searchkeywords5, String searchkeywords6, List<Long> directorycode, int k, Date fromdate3,
			Date todate3, String searchkeywords7, String searchkeywords8, String searchkeywords9,
			String searchkeywords10, List<Long> directorycode2, int l, Date fromdate4, Date todate4,
			String searchkeywords11, String searchkeywords12, List<Long> directorycode3, int m, Date fromdate5,
			Date todate5, LSuserMaster objuser3, String searchkeywords13, String searchkeywords14,
			String searchkeywords15, String searchkeywords16, List<Long> directorycode4, int n, Date fromdate6,
			Date todate6, LSuserMaster objuser4, String searchkeywords17, String searchkeywords18,
			List<Long> directorycode5, int o, Date fromdate7, Date todate7, LSuserMaster objuser5,
			String searchkeywords19, String searchkeywords20, String searchkeywords21, String searchkeywords22,
			List<Long> directorycode6, int p, Date fromdate8, Date todate8, LSuserMaster objuser6,
			String searchkeywords23, String searchkeywords24, Pageable pageable);

	long countByOrderflagAndTeamselectedAndLsprojectmasterIsNullAndViewoptionAndBatchcodeInAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrderByBatchcodeDesc(
			String string, boolean b, int i, List<Long> selectedteambatchCodeList, Date fromdate, Date todate,
			Integer sitecode);

	long countByLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndSitecode(
			int i, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, int j,
			Integer sitecode);

	List<LSlogilablimsorderdetail> findByOrderflagAndTeamselectedAndLsprojectmasterIsNullAndViewoptionAndBatchcodeInAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrderByBatchcodeDesc(
			String string, boolean b, int i, List<Long> selectedteambatchCodeList, Date fromdate, Date todate,
			Integer sitecode, Pageable pageableobj);

	List<LSlogilablimsorderdetail> findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecode(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int i, Integer sitecode,
			Pageable pageableobj);

	List<LSlogilablimsorderdetail> findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecode(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode,
			Pageable pageableobj);

	long countByViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecode(
			int i, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, LSuserMaster objuser,
			String string, Integer sitecode, List<Long> directorycode, boolean c, List<Long> selectedteambatchCodeList2,
			int j, Date fromdate2, Date todate2, LSuserMaster objuser2, String string2, Integer sitecode2);

	long countByViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrderByBatchcodeDesc(
			int i, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, LSuserMaster objuser,
			int j, Integer sitecode, List<Long> directorycode, boolean c, List<Long> selectedteambatchCodeList2, int k,
			Date fromdate2, Date todate2, LSuserMaster objuser2, int l, Integer sitecode2);
	long countByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndTeamselectedAndSitecodeOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, boolean b, Integer sitecode,
			List<Long> directorycode, int j, Date fromdate2, Date todate2, String string2, Integer sitecode2,
			List<Long> directorycode2, int k, Date fromdate3, Date todate3, LSuserMaster objuser2, String string3,
			Integer sitecode3, List<Long> directorycode3, int l, Date fromdate4, Date todate4, LSuserMaster objuser3,
			String string4, boolean c, Integer sitecode4, int m, boolean d, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, LSuserMaster objuser4, String string5, Integer sitecode5,
			List<Long> directorycode4, boolean e, List<Long> selectedteambatchCodeList2, int n, Date fromdate6,
			Date todate6, LSuserMaster objuser5, String string6, Integer sitecode6);

	long countByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, int j, Integer sitecode, List<Long> directorycode,
			int k, Date fromdate2, Date todate2, int l, Integer sitecode2, List<Long> directorycode2, int m,
			Date fromdate3, Date todate3, LSuserMaster objuser2, int n, Integer sitecode3, List<Long> directorycode3,
			int o, Date fromdate4, Date todate4, LSuserMaster objuser3, int p, Integer sitecode4);

	long countByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndTeamselectedAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, int j, boolean b, Integer sitecode,
			List<Long> directorycode, int k, Date fromdate2, Date todate2, int l, Integer sitecode2,
			List<Long> directorycode2, int m, Date fromdate3, Date todate3, LSuserMaster objuser2, int n,
			Integer sitecode3, List<Long> directorycode3, int o, Date fromdate4, Date todate4, LSuserMaster objuser3,
			int p, boolean c, Integer sitecode4);
	
	long countByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndTeamselectedAndSitecode(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, int j, Integer sitecode, int k,
			Date fromdate2, Date todate2, LSuserMaster objuser2, String string2, boolean b, Integer sitecode2,
			List<Long> directorycode, int l, Date fromdate3, Date todate3, String string3, int m, Integer sitecode3,
			List<Long> directorycode2, int n, Date fromdate4, Date todate4, String string4, Integer sitecode4,
			List<Long> directorycode3, int o, Date fromdate5, Date todate5, LSuserMaster objuser3, String string5,
			int p, Integer sitecode5, List<Long> directorycode4, int q, Date fromdate6, Date todate6,
			LSuserMaster objuser4, String string6, Integer sitecode6, List<Long> directorycode5, int r, Date fromdate7,
			Date todate7, LSuserMaster objuser5, String string7, int s, Integer sitecode7, List<Long> directorycode6,
			int t, Date fromdate8, Date todate8, LSuserMaster objuser6, String string8, boolean c, Integer sitecode8);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTeamselectedAndSitecodeOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, boolean b, Integer sitecode,
			List<Long> directorycode, int j, Date fromdate2, Date todate2, Integer sitecode2, List<Long> directorycode2,
			int k, Date fromdate3, Date todate3,  Integer sitecode3, List<Long> directorycode3,
			int l, Date fromdate4, Date todate4, LSuserMaster objuser3, boolean c, Integer sitecode4, int m, boolean d,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, LSuserMaster objuser4,
			Integer sitecode5, List<Long> directorycode4, boolean e, List<Long> selectedteambatchCodeList2, int n,
			Date fromdate6, Date todate6, LSuserMaster objuser5, Integer sitecode6, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndTeamselectedAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndOrdercancellIsNullAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, boolean b, Integer sitecode,
			List<Long> directorycode, int j, Date fromdate2, Date todate2, String string2, Integer sitecode2,
			List<Long> directorycode2, int k, Date fromdate3, Date todate3, LSuserMaster objuser2, String string3,
			Integer sitecode3, List<Long> directorycode3, boolean c, int l, Date fromdate4, Date todate4,
			LSuserMaster objuser3, String string4, Integer sitecode4, int m, boolean d,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, LSuserMaster objuser4, String string5,
			Integer sitecode5, List<Long> directorycode4, boolean e, List<Long> selectedteambatchCodeList2, int n,
			Date fromdate6, Date todate6, LSuserMaster objuser5, String string6, Integer sitecode6, Pageable pageable);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancell(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, Integer site2,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, int l, Integer site3,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, int n, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, boolean b, int p, Integer site5,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletype(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j,
			Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer filetype2, int l,
			Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3, int n,
			Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, Integer filetype4,
			boolean b, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5,
			Date todate5, int q, Integer filetype5, LSuserMaster lsuserMaster3, Integer site6,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, Integer filetype6,
			LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s, Integer filetype7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflag(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype,
			String orderflag, int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k,
			Integer filetype2, String orderflag2, int l, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3,
			Date todate3, int m, Integer filetype3, String orderflag3, int n, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, Integer filetype4, String orderflag4,
			boolean b, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5,
			Date todate5, int q, Integer filetype5, String orderflag5, LSuserMaster lsuserMaster3, Integer site6,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, Integer filetype6, String orderflag6,
			LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s, Integer filetype7,
			String orderflag7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndApprovelstatus(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j,
			int k, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, Integer filetype2,
			int m, int n, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o,
			Integer filetype3, int p, int q, Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4,
			Date todate4, int r, Integer filetype4, int s, boolean b, int t, Integer site5, boolean c,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u, Integer filetype5, int v,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int w,
			Integer filetype6, int x, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int y,
			Integer filetype7, int z);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndOrderflagAndApprovelstatus(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j,
			int k, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, String orderflag2,
			int m, int n, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o,
			String orderflag3, int p, int q, Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4,
			Date todate4, int r, String orderflag4, int s, boolean b, int t, Integer site5, boolean c,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u, String orderflag5, int v,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int w,
			String orderflag6, int x, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int y,
			String orderflag7, int z);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndApprovelstatus(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, int k,
			Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, int m, int n, Integer site3,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o, int p, int q, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int r, int s, boolean b, int t,
			Integer site5, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u, int v,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int w,
			int x, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int y, int z);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatus(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype,
			String orderflag, int j, int k, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			int l, Integer filetype2, String orderflag2, int m, int n, Integer site3, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, int o, Integer filetype3, String orderflag3, int p, int q, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int r, Integer filetype4, String orderflag4,
			int s, boolean b, int t, Integer site5, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5,
			Date todate5, int u, Integer filetype5, String orderflag5, int v, LSuserMaster lsuserMaster3, Integer site6,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int w, Integer filetype6, String orderflag6,
			int x, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int y, Integer filetype7,
			String orderflag7, int z);

	List<LogilabOrdermastersh> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNull(
			int i, Integer site, boolean b, List<Long> selectedteambatchCodeList, Date fromdate, Date todate, int j);

	List<LogilabOrdermastersh> findByLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancell(
			int i, Integer site, LSuserMaster objuser, Date fromdate, Date todate, int j, int k, Integer site2,
			List<LSprojectmaster> lstproject, Date fromdate2, Date todate2, int l, Integer site3, LSuserMaster objuser2,
			Date fromdate3, Date todate3, int m, int n, Integer site4, boolean b, LSuserMaster objuser3, Date fromdate4,
			Date todate4, int o, LSuserMaster objuser4, Integer site5, LSuserMaster objuser5, Date fromdate5,
			Date todate5, int p, LSuserMaster objuser6, Integer site6, Date fromdate6, Date todate6, int q);

	
	//kumu
	

	List<LogilabOrdermastersh> findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndLsprojectmasterInAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndLsprojectmasterIsNullAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrOrderflagAndAssignedtoAndCreatedtimestampBetweenOrOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndOrdercancellIsNullAndAssignedtoIsNullOrOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterInAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndOrdercancellIsNullAndCreatedtimestampBetweenAndLsuserMasterInAndAssignedtoIsNullOrOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrOrderflagAndAssignedtoAndCreatedtimestampBetweenOrApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusOrOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellOrderByBatchcodeDesc(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int i, String string2,
			List<LSprojectmaster> lstproject2, Date fromdate2, Date todate2, String string3, int j, Date fromdate3,
			Date todate3, String string4, int k, LSuserMaster objuser, Date fromdate4, Date todate4, int l,
			String string5, int m, LSuserMaster objuser2, Date fromdate5, Date todate5, int n, String string6, int o,
			LSuserMaster objuser3, Date fromdate6, Date todate6, int p, List<LSprojectmaster> lstproject3,
			String string7, int q, LSuserMaster objuser4, Date fromdate7, Date todate7, String string8, int r,
			LSuserMaster objuser5, Date fromdate8, Date todate8, String string9, int s, LSuserMaster objuser6,
			Date fromdate9, Date todate9, String string10, LSuserMaster objuser7, LSuserMaster objuser8,
			Date fromdate10, Date todate10, String string11, LSuserMaster objuser9, Date fromdate11, Date todate11,
			String string12, List<LSprojectmaster> lstproject4, Date fromdate12, Date todate12, String string13, int t,
			Date fromdate13, Date todate13, String string14, int u, LSuserMaster objuser10, Date fromdate14,
			Date todate14, String string15, int v, LSuserMaster objuser11, Date fromdate15, Date todate15,
			String string16, List<LSprojectmaster> lstproject5, int w, LSuserMaster objuser12, Date fromdate16,
			Date todate16, String string17, int x, Date fromdate17, Date todate17, List<LSuserMaster> list,
			String string18, LSuserMaster objuser13, LSuserMaster objuser14, Date fromdate18, Date todate18,
			String string19, LSuserMaster objuser15, Date fromdate19, Date todate19, int y,
			List<LSprojectmaster> lstproject6, Date fromdate20, Date todate20, int z, LSuserMaster objuser16,
			Date fromdate21, Date todate21, int a, int b, LSuserMaster objuser17, Date fromdate22, Date todate22, int c,
			int d, LSuserMaster objuser18, Date fromdate23, Date todate23, int e, int f, LSuserMaster objuser19,
			Date fromdate24, Date todate24, int g, LSuserMaster objuser20, LSuserMaster objuser21, Date fromdate25,
			Date todate25, int h, LSuserMaster objuser22, Date fromdate26, Date todate26, int int1, int int2,
			List<LSprojectmaster> lstproject7, Date fromdate27, Date todate27, int int3, LSuserMaster objuser23,
			Date fromdate28, Date todate28, int int4, int int5, LSuserMaster objuser24, Date fromdate29, Date todate29,
			int int6, int int7, LSuserMaster objuser25, Date fromdate30, Date todate30, int int8,
			LSuserMaster objuser26, LSuserMaster objuser27, Date fromdate31, Date todate31, int int9,
			LSuserMaster objuser28, Date fromdate32, Date todate32, int int10, Pageable pageable);

	List<Logilaborders> findByElnmaterialAndViewoptionAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrElnmaterialAndViewoptionAndLsuserMasterAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			Elnmaterial elnmaterial, int i, LStestmasterlocal lstestmasterlocal, int j, Date fromdate, Date todate,
			Elnmaterial elnmaterial2, int k, LSuserMaster lsuserMaster, int l, Date fromdate2, Date todate2);
	
	List<LogilabOrdermastersh> findByOrderflagAndLockeduserIsNotNullAndLockeduserInAndAssignedtoIsNullOrderByBatchcodeDesc(
			String string, List<Integer> usercode);	

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, Elnmaterial elnmaterial2, LStestmasterlocal lstestmasterlocal2, Date fromdate2,
			Date todate2, int i, Elnmaterial elnmaterial3, LStestmasterlocal lstestmasterlocal3, Date fromdate3,
			Date todate3, int j, LSuserMaster lsuserMaster, Elnmaterial elnmaterial4,
			LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k,
			List<LSuserMaster> lstuserMaster);

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeAndOrderflagOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, Integer filetype, String orderflag, Elnmaterial elnmaterial2,
			LStestmasterlocal lstestmasterlocal2, Date fromdate2, Date todate2, int i, Integer filetype2,
			String orderflag2, Elnmaterial elnmaterial3, LStestmasterlocal lstestmasterlocal3, Date fromdate3,
			Date todate3, int j, LSuserMaster lsuserMaster, Integer filetype3, String orderflag3,
			Elnmaterial elnmaterial4, LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k,
			List<LSuserMaster> lstuserMaster, Integer filetype4, String orderflag4);

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndOrderflagAndApprovelstatusOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, String orderflag, Integer approvelstatus, Elnmaterial elnmaterial2,
			LStestmasterlocal lstestmasterlocal2, Date fromdate2, Date todate2, int i, String orderflag2,
			Integer approvelstatus2, Elnmaterial elnmaterial3, LStestmasterlocal lstestmasterlocal3, Date fromdate3,
			Date todate3, int j, LSuserMaster lsuserMaster, String orderflag3, Integer approvelstatus3,
			Elnmaterial elnmaterial4, LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k,
			List<LSuserMaster> lstuserMaster, String orderflag4, Integer approvelstatus4);

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndOrderflagOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, String orderflag, Elnmaterial elnmaterial2,
			LStestmasterlocal lstestmasterlocal2, Date fromdate2, Date todate2, int i, String orderflag2,
			Elnmaterial elnmaterial3, LStestmasterlocal lstestmasterlocal3, Date fromdate3, Date todate3, int j,
			LSuserMaster lsuserMaster, String orderflag3, Elnmaterial elnmaterial4,
			LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k, List<LSuserMaster> lstuserMaster,
			String orderflag4);

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, Integer filetype, Elnmaterial elnmaterial2,
			LStestmasterlocal lstestmasterlocal2, Date fromdate2, Date todate2, int i, Integer filetype2,
			Elnmaterial elnmaterial3, LStestmasterlocal lstestmasterlocal3, Date fromdate3, Date todate3, int j,
			LSuserMaster lsuserMaster, Integer filetype3, Elnmaterial elnmaterial4,
			LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k, List<LSuserMaster> lstuserMaster,
			Integer filetype4);

	List<Logilaborders> findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeAndOrderflagAndApprovelstatusOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject, Elnmaterial elnmaterial, LStestmasterlocal lstestmasterlocal,
			Date fromdate, Date todate, Integer filetype, String orderflag, Integer approvelstatus,
			Elnmaterial elnmaterial2, LStestmasterlocal lstestmasterlocal2, Date fromdate2, Date todate2, int i,
			Integer filetype2, String orderflag2, Integer approvelstatus2, Elnmaterial elnmaterial3,
			LStestmasterlocal lstestmasterlocal3, Date fromdate3, Date todate3, int j, LSuserMaster lsuserMaster,
			Integer filetype3, String orderflag3, Integer approvelstatus3, Elnmaterial elnmaterial4,
			LStestmasterlocal lstestmasterlocal4, Date fromdate4, Date todate4, int k, List<LSuserMaster> lstuserMaster,
			Integer filetype4, String orderflag4, Integer approvelstatus4);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, LSuserMaster lsuserMaster,
//			Date fromdate2, Date todate2, int k, int l, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m,
//			int n, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int o, LSuserMaster lsuserMaster4,
//			LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int p, LSuserMaster lsuserMaster6, Date fromdate6,
//			Date todate6, int q);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletype(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j,
//			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer filetype2, int l,
//			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3, int n,
//			LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int o, Integer filetype4,
//			LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int p,
//			Integer filetype5, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int q, Integer filetype6);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflag(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, String orderflag,
//			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer filetype2, String orderflag2,
//			int l, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3,
//			String orderflag3, int n, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int o,
//			Integer filetype4, String orderflag4, LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5,
//			Date fromdate5, Date todate5, int p, Integer filetype5, String orderflag5, LSuserMaster lsuserMaster6,
//			Date fromdate6, Date todate6, int q, Integer filetype6, String orderflag6);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndApprovelstatus(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j, int k,
//			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, Integer filetype2, int m, int n,
//			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o, Integer filetype3, int p, int q,
//			LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int r, Integer filetype4, int s,
//			LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int t,
//			Integer filetype5, int u, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int v,
//			Integer filetype6, int w);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflag(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j,
//			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, String orderflag2, int l,
//			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, String orderflag3, int n,
//			LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int o, String orderflag4,
//			LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int p,
//			String orderflag5, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int q, String orderflag6);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflagAndApprovelstatus(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j, int k,
//			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, String orderflag2, int m, int n,
//			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o, String orderflag3, int p, int q,
//			LSuserMaster lsuserMaster3, Date fromdate4, Date todate4, int r, String orderflag4, int s,
//			LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int t,
//			String orderflag5, int u, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int v,
//			String orderflag6, int w);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndApprovelstatus(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, int k,
//			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, int m, int n, LSuserMaster lsuserMaster2,
//			Date fromdate3, Date todate3, int o, int p, int q, LSuserMaster lsuserMaster3, Date fromdate4, Date todate4,
//			int r, int s, LSuserMaster lsuserMaster4, LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int t,
//			int u, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int v, int w);

//	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatus(
//			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, String orderflag,
//			int j, int k, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, Integer filetype2,
//			String orderflag2, int m, int n, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o,
//			Integer filetype3, String orderflag3, int p, int q, LSuserMaster lsuserMaster3, Date fromdate4,
//			Date todate4, int r, Integer filetype4, String orderflag4, int s, LSuserMaster lsuserMaster4,
//			LSuserMaster lsuserMaster5, Date fromdate5, Date todate5, int t, Integer filetype5, String orderflag5,
//			int u, LSuserMaster lsuserMaster6, Date fromdate6, Date todate6, int v, Integer filetype6,
//			String orderflag6, int w);

//	List<LogilabOrdermastersh> LsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
//			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, int k, List<LSprojectmaster> lstproject,
//			Date fromdate2, Date todate2, int l, LSuserMaster objuser2, Date fromdate3, Date todate3, int m, int n,
//			LSuserMaster objuser3, Date fromdate4, Date todate4, int o, LSuserMaster objuser4, LSuserMaster objuser5,
//			Date fromdate5, Date todate5, int p, LSuserMaster objuser6, Date fromdate6, Date todate6, int q,
//			Pageable pageable);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
			String orderflag, List<Elnmaterial> currentChunk, Date fromdate, Date todate, int i);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<Elnmaterial> currentChunk, Date fromdate, Date todate, int i,
			LSuserMaster lsuserMaster);

	List<Logilaborderssh> findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
			String orderflag, Integer approvelstatus, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate,
			Date todate, int i);

	List<Logilaborderssh> findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, Integer approvelstatus, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate,
			Date todate, int i, LSuserMaster lsuserMaster);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndTestcodeAndElnmaterialInAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoption(
			String orderflag, Integer testcode, List<Elnmaterial> currentChunk, Integer filetype,
			LSprojectmaster lsprojectmaster, Date fromdate, Date todate, int i);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndTestcodeAndElnmaterialInAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterOrderByBatchcodeDesc(
			String orderflag, Integer testcode, List<Elnmaterial> currentChunk, Integer filetype,
			LSprojectmaster lsprojectmaster, Date fromdate, Date todate, int i, LSuserMaster lsuserMaster);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNull(
			String orderflag, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate, Date todate, int i,
			LSuserMaster lsuserMaster);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
			String orderflag, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate, Date todate, int i);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate, Date todate, int i,
			LSuserMaster lsuserMaster);
//
//	List<Logilaborderssh> findByOrderflagAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNull(
//			String orderflag, Integer filetype, LSprojectmaster lsprojectmaster, Date fromdate, Date todate);

	List<Logilaborderssh> findByOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoption(
			String orderflag, Integer testcode, Integer filetype, Date fromdate, Date todate,
			List<Elnmaterial> currentChunk, String orderflag2, Integer testcode2, List<Elnmaterial> currentChunk2,
			Integer filetype2, Date fromdate2, Date todate2, int i);

	List<Logilaborderssh> findByOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterOrderByBatchcodeDesc(
			String orderflag, Integer testcode, List<Elnmaterial> currentChunk, Integer filetype, Date fromdate,
			Date todate, int i, LSuserMaster lsuserMaster);

	List<ordersinterface> findByOrderflagAndLsprojectmasterInAndLsworkflowInAndCreatedtimestampBetweenOrAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			String string, List<LSprojectmaster> lstproject, List<LSworkflow> lstworkflow, Date fromdate, Date todate,
			LSuserMaster objuser, Date fromdate2, Date todate2);

//	List<LSlogilablimsorderdetail> findByLsuserMasterAndRepeat(LSuserMaster lsuserMaster, boolean b);

	@Transactional
	List<LSlogilablimsorderdetail> findByBatchcodeInOrderByBatchcodeAsc(List<Long> batchcodeauto);


	List<LSlogilablimsorderdetail> findByBatchcodeInAndOrderflag(List<Long> batchcode, String string);

	@Transactional
	@Query(value = "select ordercancell from LSlogilablimsorderdetail where batchid = ?1", nativeQuery = true)
	String getRetirestatus(String templatename);

	List<LSlogilablimsorderdetail> findByBatchidInAndOrderflag(List<String> batchid, String string);

	List<LSlogilablimsorderdetail> findByLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfileAndOrdercancellNot(
			List<LSprojectmaster> lstproject, int filetype, LSfile lSfile, int i);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
//			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
//			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
//			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
//			Integer testcode3);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
//			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
//			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
//			int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, String orderflag3, Integer filetype3,
//			Integer approvelstatus3);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, List<Long> directory_Code2,
//			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, String orderflag2,
//			List<Long> directory_Code3, int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster,
//			String orderflag3);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, List<Long> directory_Code2,
//			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, String orderflag2,
//			List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3,
//			String orderflag3);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
//			Integer testcode, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
//			Date todate2, String orderflag2, Integer filetype2, Integer testcode2, List<Long> directory_Code3, int k,
//			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
//			Integer testcode3);

//	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
//			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
//			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
//			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
//			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
//			Integer approvelstatus3);

	List<LSlogilablimsorderdetail> findByLsprojectmasterIsNullAndLssamplemasterIsNullAndFiletypeAndAssignedtoIsNullAndLsfileOrderByBatchcodeDesc(
			int filetype, LSfile lSfile);

	List<LSlogilablimsorderdetail> findByLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrderByBatchcodeDesc(
			List<LSsamplemaster> currentChunk, int filetype, int viewOption, LSfile lSfile);

//	List<Logilaborders> findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//			String orderflag, List<LSprojectmaster> lstproject, Integer filetype, Date fromdate, Date todate);

	List<Logilaborderssh> findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			String orderflag, List<LSprojectmaster> lstproject, Integer filetype, Date fromdate, Date todate);

	List<LSlogilablimsorderdetail> findByRepeatAndAutoregistercountGreaterThan(boolean b, int i);

	List<LSlogilablimsorderdetail> findByFiletypeAndOrderflagAndLsprojectmasterInAndApprovelstatusNotAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
			Integer filetype, String orderflag, List<LSprojectmaster> lstproject, int i, Date fromdate, Date todate);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			String orderflag2, Integer filetype2, List<Long> directory_Code3, int k, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, String orderflag3, Integer filetype3);

//	List<Logilaborders> findByLsfileIn(List<LSfile> files);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Long directorycode2, int j,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2, Date fromdate2, Date todate2);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Long directorycode2, int j, Integer filetype2, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster);

	List<Logilaborders> findByLsfileInAndCreatedtimestampBetweenOrderByBatchcodeDesc(List<LSfile> files, Date fromdate,
			Date todate);

	List<LSlogilablimsorderdetail> findByFiletypeAndLsfileAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndLsfileAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			int filetype1, LSfile lSfile1, int i, int filetype2, LSfile lSfile2);

	List<LSlogilablimsorderdetail> findByLsprojectmasterInAndFiletypeAndLsfileAndApprovelstatusNotAndOrdercancellIsNullOrLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfileAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<LSprojectmaster> lstproject1, int filetype1, LSfile lSfile1, int i, List<LSprojectmaster> lstproject2,
			int filetype2, LSfile lSfile2);

//	List<LSlogilablimsorderdetail> findByFiletypeAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
//			Integer filetype1, int i, Integer filetype2);

//	List<LSlogilablimsorderdetail> findByFiletypeAndLsprojectmasterInAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndLsprojectmasterInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
//			Integer filetype1, List<LSprojectmaster> lstproject1, int i, Integer filetype2,
//			List<LSprojectmaster> lstproject2);

	List<Logilaborders> findByOrderflagAndLsfileInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
			String string, List<LSfile> files, Date fromdate, Date todate);

	List<LSlogilablimsorderdetail> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrderByBatchcodeDesc(
			List<Elnmaterial> currentChunk, int filetype, int viewOption, LSfile lSfile);

//	List<LSlogilablimsorderdetail> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoption(
//			List<Elnmaterial> currentChunk, Integer filetype, int i);

	@Transactional
	@Modifying
	@Query("update LSlogilablimsorderdetail o set o.lockeduser = null, o.lockedusername = null, o.activeuser = null where o.batchcode in (?1)")
	int updateLockedUser(List<Long> batcode);


	List<LSlogilablimsorderdetail> countByOrderflagAndLsprojectmasterIn(String orderflag,
			List<LSprojectmaster> lstprojectmaster);

	List<LogilabOrdermastersh> LsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, int k, List<LSprojectmaster> lstproject,
			Date fromdate2, Date todate2, int l, LSuserMaster objuser2, Date fromdate3, Date todate3, int m, int n,
			boolean b, LSuserMaster objuser3, Date fromdate4, Date todate4, int o, LSuserMaster objuser4,
			LSuserMaster objuser5, Date fromdate5, Date todate5, int p, LSuserMaster objuser6, Date fromdate6,
			Date todate6, int q);


	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, LSuserMaster lsuserMaster,
			Date fromdate2, Date todate2, int k, int l, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m,
			int n, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, LSuserMaster lsuserMaster3,
			LSuserMaster lsuserMaster4, Date fromdate5, Date todate5, int p, LSuserMaster lsuserMaster5, Date fromdate6,
			Date todate6, int q);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletype(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer filetype2, int l,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3, int n,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, Integer filetype4, boolean b, int p,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q, Integer filetype5,
			LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			Integer filetype6, LSuserMaster lsuserMaster5, Date fromdate7, Date todate7, int s, Integer filetype7);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflag(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, String orderflag,
			int j, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer filetype2, String orderflag2,
			int l, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3,
			String orderflag3, int n, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o,
			Integer filetype4, String orderflag4, boolean b, int p, boolean c, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, int q, Integer filetype5, String orderflag5, LSuserMaster lsuserMaster3,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, Integer filetype6, String orderflag6,
			LSuserMaster lsuserMaster5, Date fromdate7, Date todate7, int s, Integer filetype7, String orderflag7);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndApprovelstatus(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, int j, int k,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, Integer filetype2, int m, int n,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o, Integer filetype3, int p, int q,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int r, Integer filetype4, int s, boolean b,
			int t, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u,
			Integer filetype5, int v, LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4, Date fromdate6,
			Date todate6, int w, Integer filetype6, int x, LSuserMaster lsuserMaster5, Date fromdate7, Date todate7,
			int y, Integer filetype7, int z);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflag(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, String orderflag2, int l,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, String orderflag3, int n,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, String orderflag4, boolean b, int p,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q, String orderflag5,
			LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			String orderflag6, LSuserMaster lsuserMaster5, Date fromdate7, Date todate7, int s, String orderflag7);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflagAndApprovelstatus(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j, int k,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, String orderflag2, int m, int n,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o, String orderflag3, int p, int q,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int r, String orderflag4, int s, boolean b,
			int t, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u,
			String orderflag5, int v, LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4, Date fromdate6,
			Date todate6, int w, String orderflag6, int x, LSuserMaster lsuserMaster5, Date fromdate7, Date todate7,
			int y, String orderflag7, int z);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndApprovelstatus(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, int k,
			LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, int m, int n, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, int o, int p, int q, List<LSuserMaster> lstuserMaster, Date fromdate4,
			Date todate4, int r, int s, boolean b, int t, boolean c, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, int u, int v, LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4,
			Date fromdate6, Date todate6, int w, int x, LSuserMaster lsuserMaster5, Date fromdate7, Date todate7, int y,
			int z);

	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatus(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype, String orderflag,
			int j, int k, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int l, Integer filetype2,
			String orderflag2, int m, int n, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int o,
			Integer filetype3, String orderflag3, int p, int q, List<LSuserMaster> lstuserMaster, Date fromdate4,
			Date todate4, int r, Integer filetype4, String orderflag4, int s, boolean b, int t, boolean c,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int u, Integer filetype5,
			String orderflag5, int v, LSuserMaster lsuserMaster3, LSuserMaster lsuserMaster4, Date fromdate6,
			Date todate6, int w, Integer filetype6, String orderflag6, int x, LSuserMaster lsuserMaster5,
			Date fromdate7, Date todate7, int y, Integer filetype7, String orderflag7, int z);


	List<Logilaborderssh> findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int j, LSuserMaster lsuserMaster,
			Date fromdate2, Date todate2, int k, int l, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m,
			int n, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, boolean b, int p, boolean c,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q, LSuserMaster lsuserMaster3,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, LSuserMaster lsuserMaster5, Date fromdate7,
			Date todate7, int s);


	@Transactional
	@Modifying
	@Query(value = "WITH TeamProjects AS ( " +
	        "   SELECT DISTINCT lsprojectmaster_projectcode " +
	        "   FROM LSlogilablimsorderdetail " +
	        "   WHERE lsprojectmaster_projectcode IN ( " +
	        "       SELECT projectcode " +
	        "       FROM LSprojectmaster " +
	        "       WHERE lsusersteam_teamcode IN ( " +
	        "           SELECT DISTINCT teamcode " +
	        "           FROM LSuserteammapping " +
	        "           WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL) " +
	        "       AND status = 1 " +
	        ") " +
	        ") " +
	        "SELECT * FROM LSlogilablimsorderdetail o " +
	        "WHERE"+
	        "(o.orderflag = ?1 AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "AND ( " +
	        "   (filetype = ?2 AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)) " +
	        
	        "   OR "+
	        "   (lsprojectmaster_projectcode IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL AND createdtimestamp BETWEEN ?3 AND ?4 " +
			 "       AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL) " +
			 "       AND (viewoption = ?8 AND teamselected = true AND batchcode IN ?13)"+ 
			 "   ) " +
			 
			 "   OR "+
	        "   (lsprojectmaster_projectcode IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL AND createdtimestamp BETWEEN ?3 AND ?4" +
	        "       AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL) " +
	        "       AND ((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
	        "             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
	        "             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = false)"+
	        "           ) " +
	        "   ) " +
	   
	        "   OR (lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND assignedto_usercode IS NOT NULL AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)) " +
	        "   OR (assignedto_usercode = ?5 AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)) " +
	        "   OR (o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects) " +
	        "       AND ordercancell IS NULL AND assignedto_usercode IS NULL AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)) " +
	        "   OR (o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects) " +
	        "       AND lsusermaster_usercode = ?5 AND ordercancell IS NULL AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)) " +
	        "   OR (lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN " +
	        "       (SELECT DISTINCT elnmaterial_nmaterialcode " +
	        "        FROM lslogilablimsorderdetail " +
	        "        WHERE elnmaterial_nmaterialcode IN " +
	        "              (SELECT m.nmaterialcode " +
	        "               FROM elnmaterial m " +
	        "               WHERE m.nsitecode = ?10)) " +
	        "       AND ordercancell IS NULL AND assignedto_usercode IS NULL AND lsusermaster_usercode != ?5 " +
	        "       AND (o.Approvelstatus != ?11 OR o.Approvelstatus IS NULL)"+
	        "		AND (viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8)"+
	        "	) " +
	        ")) " +
	      "OR "+
	        "( " +
	        "    (o.orderflag = ?12 AND filetype = ?2 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL) " +
	        "    OR (o.orderflag = ?12 AND lsprojectmaster_projectcode IS NULL AND " +
	        "        ("+
			"		((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
            "             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
            "             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = false)"+
            "       ) " +
	        "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
	        "         ((approvelstatus != ?11 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
	        "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
	        "		) " +
	        "    ) " +
	        
			  "    OR (o.orderflag = ?12 AND lsprojectmaster_projectcode IS NULL AND " +
			  "        ("+
			  "         (viewoption = ?8 AND batchcode IN ?13 AND teamselected = true)"+
			  "         AND createdtimestamp BETWEEN ?3 AND ?4 AND " +
			  "         ((approvelstatus != ?11 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
			  "         OR (approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL))"+
			  "		) " +
			  "    ) " +
  
  
	        "    OR (o.orderflag = ?12 AND " +
	        "        ((lsusermaster_usercode = ?5 AND assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL) " +
	        "         OR (assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4)) " +
	        "    ) " +
	        "    OR (o.orderflag = ?12 AND " +
	        "        (o.lsprojectmaster_projectcode IN ( " +
	        "            SELECT lsprojectmaster_projectcode FROM LSlogilablimsorderdetail " +
	        "            WHERE lsprojectmaster_projectcode IN ( " +
	        "                SELECT DISTINCT projectcode FROM LSprojectmaster " +
	        "                WHERE lsusersteam_teamcode IN ( " +
	        "                    SELECT teamcode FROM LSuserteammapping " +
	        "                    WHERE lsuserMaster_usercode = ?5 AND teamcode IS NOT NULL" +
	        "                ) AND status = 1 " +
	        "            )" +
	        "        ) AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "        AND approvelstatus != ?11 AND ordercancell IS NULL AND assignedto_usercode IS NULL) " +
	        "    ) " +
	        "    OR (o.orderflag = ?12 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
	        "            ) " +
	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "            AND approvelstatus IS NULL AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
	        "            AND lsusermaster_usercode != ?5 " +
	        "			 AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
	        "        ) " +
	        "    ) " +
	        "    OR (o.orderflag = ?12 AND lsprojectmaster_projectcode IS NULL AND elnmaterial_nmaterialcode IN (" +
	        "            SELECT DISTINCT elnmaterial_nmaterialcode FROM lslogilablimsorderdetail WHERE elnmaterial_nmaterialcode IN (" +
	        "                SELECT m.nmaterialcode FROM elnmaterial m WHERE m.nsitecode = ?10 " +
	        "            ) " +
	        "            AND createdtimestamp BETWEEN ?3 AND ?4 " +
	        "            AND approvelstatus != ?11 AND ordercancell IS NULL AND assignedto_usercode IS NULL " +
	        "            AND lsusermaster_usercode != ?5 " +
	        "			 AND viewoption != ?6 AND viewoption != ?7 AND viewoption != ?8"+
	        "        ) " +
	        "    ) " +
	        ") " +
	        "  OR(" + 
	        
		  " (lsprojectmaster_projectcode IS NULL AND "
			+ "("+
				"(viewoption = ?8 AND batchcode IN ?13 AND lsusermaster_usercode IN (?9) AND teamselected = true)"
				+ "AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?11 AND assignedto_usercode IS NULL"+
			"	)"
			+ ")"
	
	        + "OR (lsprojectmaster_projectcode IS NULL AND "
			+ "        ("+
			"((viewoption = ?6 AND lsusermaster_usercode = ?5) OR " +
			"             (viewoption = ?7 AND lsusermaster_usercode = ?5) OR " +
			"             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = false)"+
			"       ) " 
			+ "   AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?11 AND assignedto_usercode IS NULL"+
			"		)"
			+ ")"
			+" OR (lsusermaster_usercode = ?5 AND  assignedto_usercode != ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL AND approvelstatus =?11)"
			+ "OR(assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND approvelstatus =?11)"
			+ "OR(approvelstatus =?11 AND o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects ) AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL)"
			+ ")" +

			" OR (" 
				+ " (lsprojectmaster_projectcode IS NULL AND "
				+ " ("+
				"(viewoption = ?8 AND batchcode IN ?13 AND teamselected = true)"
				+ " AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL "+
				")"
				+ ")"
				+"OR(lsprojectmaster_projectcode IS NULL AND "
				+ "        ("+
				"((viewoption = ?6 AND lsusermaster_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL) OR " +
				"             (viewoption = ?7 AND lsusermaster_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL) OR " +
				"             (viewoption = ?8 AND lsusermaster_usercode IN (?9) AND teamselected = false AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 AND assignedto_usercode IS NULL)"+
				"       ) " 
				+ ")"
				+ ")"
			+"OR (ordercancell =?6 AND o.lsprojectmaster_projectcode IN (SELECT lsprojectmaster_projectcode FROM TeamProjects ) AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NULL)"
			+"OR(lsusermaster_usercode = ?5 AND assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND assignedto_usercode IS NOT NULL AND ordercancell =?6)"
			+"OR(assignedto_usercode = ?5 AND createdtimestamp BETWEEN ?3 AND ?4 AND ordercancell =?6 )"+
			")" +
	        "ORDER BY batchcode DESC " , nativeQuery = true)
	List<LSlogilablimsorderdetail> getLSlogilablimsorderdetaildashboardforallorders(
	        String orderFlag,
	        int fileType,
	        Date fromDate,
	        Date toDate,
	        LSuserMaster user,
	        int viewOption1,
	        int viewOption2,
	        int viewOption3,
	        List<LSuserMaster> userNotify,
	        LSSiteMaster siteMaster,
	        Integer approvalStatus,
	        String completedOrderFlag,
	        List<Long> selectedbatchcode);
	
	
	List<LogilabOrdermastersh> findByCreatedtimestampBetweenAndSitecodeOrderByBatchcodeDesc(Date fromdate, Date todate, Integer site);


	long countByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecode(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, int i, Integer sitecode);

	long countByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecode(
			String string, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode);

	long countByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecode(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, Integer sitecode, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2, int l, Integer sitecode2, int m, LSuserMaster objuser3,
			Date fromdate3, Date todate3, int n, Integer sitecode3, int o, LSuserMaster objuser4, Date fromdate4,
			Date todate4, int p, Integer sitecode4, LSuserMaster objuser5, LSuserMaster objuser6, Date fromdate5,
			Date todate5, int q, Integer sitecode5, LSuserMaster objuser7, Date fromdate6, Date todate6, int r,
			Integer sitecode6);


	long countByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(int i,
			List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode);

	long countByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndTeamselectedAndLsprojectmasterIsNullAndAssignedtoIsNullAndSitecodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndSitecodeOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndSitecode(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, int j,
			LSuserMaster objuser, Date fromdate2, Date todate2, int k, Integer sitecode2, int l, LSuserMaster objuser2,
			Date fromdate3, Date todate3, int m, Integer sitecode3, int n, LSuserMaster objuser3, Date fromdate4,
			Date todate4, int o, boolean b, Integer sitecode4, LSuserMaster objuser4, LSuserMaster objuser5,
			Date fromdate5, Date todate5, int p, Integer sitecode5, LSuserMaster objuser6, Date fromdate6, Date todate6,
			int q, Integer sitecode6);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, LSuserMaster lsuserMaster2, Date fromdate, Date todate);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, LSuserMaster lsuserMaster2,
			Date fromdate, Date todate);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Date fromdate, Date todate);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, Date fromdate,
			Date todate);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, Date fromdate, Date todate);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, Date fromdate, Date todate);



	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndApprovelstatusAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, int j, Integer sitecode, List<Long> directorycode,
			int k, Date fromdate2, Date todate2, int l, Integer sitecode2, List<Long> directorycode2, int m,
			Date fromdate3, Date todate3, LSuserMaster objuser2, int n, Integer sitecode3, List<Long> directorycode3,
			int o, Date fromdate4, Date todate4, LSuserMaster objuser3, int p, Integer sitecode4, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndTeamselectedAndSitecodeOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrdercancellAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, int j, boolean b, Integer sitecode, int k,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate2, Date todate2, LSuserMaster objuser2, int l,
			Integer sitecode2, List<Long> directorycode, int m, Date fromdate3, Date todate3, int n, Integer sitecode3,
			List<Long> directorycode2, int o, Date fromdate4, Date todate4, LSuserMaster objuser3, int p,
			Integer sitecode4, List<Long> directorycode3, int q, Date fromdate5, Date todate5, LSuserMaster objuser4,
			int r, boolean d, Integer sitecode5, List<Long> directorycode4, boolean e,
			List<Long> selectedteambatchCodeList2, int s, Date fromdate6, Date todate6, LSuserMaster objuser5, int t,
			Integer sitecode6, Pageable pageable);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby, Integer sitecode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer sitecode,
			Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2, Date todate2, Integer sitecode2);

	List<LogilabOrdermastersh> findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecode(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, Integer sitecode, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2, int l, Integer sitecode2, int m, LSuserMaster objuser3,
			Date fromdate3, Date todate3, int n, Integer sitecode3, int o, LSuserMaster objuser4, Date fromdate4,
			Date todate4, int p, Integer sitecode4, LSuserMaster objuser5, LSuserMaster objuser6, Date fromdate5,
			Date todate5, int q, Integer sitecode5, LSuserMaster objuser7, Date fromdate6, Date todate6, int r,
			Integer sitecode6, Pageable pageable);

	List<LogilabOrdermastersh> findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, Pageable pageable);

	List<LogilabOrdermastersh> findByViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndTeamselectedAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndTeamselectedAndOrdercancellIsNullAndSitecodeOrViewoptionAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndDirectorycodeInAndTeamselectedAndBatchcodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrderByBatchcodeDesc(
			int i, Date fromdate, Date todate, LSuserMaster objuser, String string, int j, Integer sitecode, int k,
			Date fromdate2, Date todate2, LSuserMaster objuser2, String string2, boolean b, Integer sitecode2,
			List<Long> directorycode, int l, Date fromdate3, Date todate3, String string3, int m, Integer sitecode3,
			List<Long> directorycode2, int n, Date fromdate4, Date todate4, String string4, Integer sitecode4,
			List<Long> directorycode3, int o, Date fromdate5, Date todate5, LSuserMaster objuser3, String string5,
			int p, Integer sitecode5, List<Long> directorycode4, int q, Date fromdate6, Date todate6,
			LSuserMaster objuser4, String string6, Integer sitecode6, List<Long> directorycode5, int r, Date fromdate7,
			Date todate7, LSuserMaster objuser5, String string7, int s, Integer sitecode7, List<Long> directorycode6,
			int t, Date fromdate8, Date todate8, LSuserMaster objuser6, String string8, boolean c, Integer sitecode8,
			int u, boolean d, List<Long> selectedteambatchCodeList, Date fromdate9, Date todate9, LSuserMaster objuser7,
			String string9, Integer sitecode9, List<Long> directorycode7, boolean e,
			List<Long> selectedteambatchCodeList2, int v, Date fromdate10, Date todate10, LSuserMaster objuser8,
			String string10, Integer sitecode10, Pageable pageable);

	List<Logilaborders> findByFiletypeAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
			Integer filetype, LSprojectmaster lsprojectmaster, LStestmasterlocal lstestmasterlocal, Date fromdate,
			Date todate);

	List<Logilaborderssh> findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
			List<Long> directory_Code, int i, Date fromdate, Date todate, String orderflag, Integer filetype,
			Integer approvelstatus, List<Long> directory_Code2, int j, LSuserMaster lsuserMaster, Date fromdate2,
			Date todate2, String orderflag2, Integer filetype2, Integer approvelstatus2, List<Long> directory_Code3,
			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster, String orderflag3, Integer filetype3,
			Integer approvelstatus3, boolean b, boolean c, List<Long> selectedteambatchCodeList,
			List<Long> directory_Code4, int l, List<LSuserMaster> lstuserMaster2, Date fromdate4, Date todate4,
			String orderflag4, Integer filetype4, Integer approvelstatus4);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndOrderflag(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, String orderflag, int j,
			Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, String orderflag2, int l,
			Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, String orderflag3, int n,
			Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, String orderflag4,
			boolean b, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList, Date fromdate5,
			Date todate5, int q, String orderflag5, LSuserMaster lsuserMaster3, Integer site6,
			LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, String orderflag6,
			LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s, String orderflag7);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSprojectmaster lsprojectmaster);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2,
			Date todate2, LSprojectmaster lsprojectmaster2, Long directorycode3, int k, LSuserMaster createdby3,
			Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2, Long directorycode3, int k,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList,
			LSprojectmaster lsprojectmaster3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer testcode,
			Long directorycode2, int j, Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster,
			Integer testcode2, Long directorycode3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList, Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer testcode,
			Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2, Date todate2, Integer testcode2,
			Long directorycode3, int k, LSuserMaster createdby3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSprojectmaster lsprojectmaster, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, Long directorycode2, int j, LSuserMaster createdby2,
			Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, Integer testcode2, Long directorycode3,
			int k, LSuserMaster createdby3, Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList,
			LSprojectmaster lsprojectmaster3, Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, Long directorycode2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2, Integer testcode2, Long directorycode3,
			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, Integer testcode3);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndLsprojectmaster(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype,
			LSprojectmaster proselected, int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			int k, Integer filetype2, LSprojectmaster proselected2, int l, Integer site3, LSuserMaster lsuserMaster2,
			Date fromdate3, Date todate3, int m, Integer filetype3, LSprojectmaster proselected3, int n, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, Integer filetype4, boolean b,
			LSprojectmaster proselected4, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, int q, Integer filetype5, LSprojectmaster proselected5,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			Integer filetype6, LSprojectmaster proselected6, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7,
			Date todate7, int s, Integer filetype7, LSprojectmaster proselected7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndTestcodeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndTestcode(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype,
			Integer testselected, int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k,
			Integer filetype2, Integer testselected2, int l, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3,
			Date todate3, int m, Integer filetype3, Integer testselected3, int n, Integer site4,
			List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4, int o, Integer filetype4, boolean b,
			Integer testselected4, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, int q, Integer filetype5, Integer testselected5, LSuserMaster lsuserMaster3,
			Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r, Integer filetype6,
			Integer testselected6, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s,
			Integer filetype7, Integer testselected7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndTestcodeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndTestcodeAndLsprojectmaster(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer filetype,
			Integer testselected, LSprojectmaster proselected, int j, Integer site2, LSuserMaster lsuserMaster,
			Date fromdate2, Date todate2, int k, Integer filetype2, Integer testselected2, LSprojectmaster proselected2,
			int l, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer filetype3,
			Integer testselected3, LSprojectmaster proselected3, int n, Integer site4, List<LSuserMaster> lstuserMaster,
			Date fromdate4, Date todate4, int o, Integer filetype4, boolean b, Integer testselected4,
			LSprojectmaster proselected4, int p, Integer site5, boolean c, List<Long> selectedteambatchCodeList,
			Date fromdate5, Date todate5, int q, Integer filetype5, Integer testselected5, LSprojectmaster proselected5,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			Integer filetype6, Integer testselected6, LSprojectmaster proselected6, LSuserMaster lsuserMaster5,
			Integer site7, Date fromdate7, Date todate7, int s, Integer filetype7, Integer testselected7,
			LSprojectmaster proselected7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmaster(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate,
			LSprojectmaster proselected, int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			int k, LSprojectmaster proselected2, int l, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3,
			Date todate3, int m, LSprojectmaster proselected3, int n, Integer site4, List<LSuserMaster> lstuserMaster,
			Date fromdate4, Date todate4, int o, boolean b, LSprojectmaster proselected4, int p, Integer site5,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q,
			LSprojectmaster proselected5, LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4,
			Date fromdate6, Date todate6, int r, LSprojectmaster proselected6, LSuserMaster lsuserMaster5,
			Integer site7, Date fromdate7, Date todate7, int s, LSprojectmaster proselected7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTestcodeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndTestcodeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcode(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer testselected,
			int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2, int k, Integer testselected2,
			int l, Integer site3, LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m,
			Integer testselected3, int n, Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4, Date todate4,
			int o, boolean b, Integer testselected4, int p, Integer site5, boolean c,
			List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q, Integer testselected5,
			LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4, Date fromdate6, Date todate6, int r,
			Integer testselected6, LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s,
			Integer testselected7);

	List<Logilaborderssh> findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndTestcodeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcodeAndLsprojectmaster(
			int i, Integer site, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer testselected,
			LSprojectmaster proselected, int j, Integer site2, LSuserMaster lsuserMaster, Date fromdate2, Date todate2,
			int k, Integer testselected2, LSprojectmaster proselected2, int l, Integer site3,
			LSuserMaster lsuserMaster2, Date fromdate3, Date todate3, int m, Integer testselected3,
			LSprojectmaster proselected3, int n, Integer site4, List<LSuserMaster> lstuserMaster, Date fromdate4,
			Date todate4, int o, boolean b, Integer testselected4, LSprojectmaster proselected4, int p, Integer site5,
			boolean c, List<Long> selectedteambatchCodeList, Date fromdate5, Date todate5, int q, Integer testselected5,
			LSprojectmaster proselected5, LSuserMaster lsuserMaster3, Integer site6, LSuserMaster lsuserMaster4,
			Date fromdate6, Date todate6, int r, Integer testselected6, LSprojectmaster proselected6,
			LSuserMaster lsuserMaster5, Integer site7, Date fromdate7, Date todate7, int s, Integer testselected7,
			LSprojectmaster proselected7);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, LSuserMaster lsuserMaster2, Date fromdate, Date todate,
			LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, LSuserMaster lsuserMaster2, Date fromdate, Date todate,
			Integer testselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, LSuserMaster lsuserMaster2, Date fromdate, Date todate,
			Integer testselected, LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, LSuserMaster lsuserMaster2,
			Date fromdate, Date todate, LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, LSuserMaster lsuserMaster2,
			Date fromdate, Date todate, Integer testselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, LSuserMaster lsuserMaster2,
			Date fromdate, Date todate, LSprojectmaster proselected, Integer testselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, Integer testselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, Integer testselected, LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, LSprojectmaster proselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, Integer testselected);

	List<Logilaborderssh> findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, LSuserMaster lsuserMaster2, Date fromdate,
			Date todate, Integer testselected, LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Date fromdate, Date todate, LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Date fromdate, Date todate, Integer testselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Date fromdate, Date todate, Integer testselected,
			LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, Date fromdate, Date todate,
			LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, Date fromdate, Date todate,
			Integer testselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, String orderflag, Date fromdate, Date todate,
			Integer testselected, LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, Date fromdate, Date todate,
			Integer testselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, Date fromdate, Date todate,
			LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, String orderflag, Date fromdate, Date todate,
			Integer testselected, LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, Date fromdate, Date todate,
			Integer testselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster proselected);

	List<Logilaborderssh> findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
			LSuserMaster lsuserMaster, Integer sitecode, Integer filetype, Date fromdate, Date todate,
			Integer testselected, LSprojectmaster proselected);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Integer testcode, Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2, Date fromdate2,
			Date todate2, Integer testcode2, Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3,
			Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList, Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Integer testcode, Long directorycode2, int j, Integer filetype2, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, Integer testcode2, Long directorycode3, int k, Integer filetype3,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList,
			Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSprojectmaster lsprojectmaster);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2,
			Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, Long directorycode3, int k,
			LSuserMaster createdby3, Integer filetype3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Long directorycode2, int j, Integer filetype2, Date fromdate2,
			Date todate2, List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2, Long directorycode3,
			int k, Integer filetype3, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSprojectmaster lsprojectmaster,
			Integer testcode);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, Long directorycode2, int j, LSuserMaster createdby2,
			Integer filetype2, Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, Integer testcode2,
			Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, Integer testcode3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, Long directorycode2, int j, Integer filetype2,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2,
			Integer testcode2, Long directorycode3, int k, Integer filetype3, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3,
			Integer testcode3);


	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, String orderflag);

	Collection<? extends Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer sitecode,
			String orderflag, Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2, Date todate2,
			Integer sitecode2, String orderflag2);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby, Integer sitecode,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, String orderflag,
			Long directorycode2, int j, Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster,
			String orderflag2, Long directorycode3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSprojectmaster lsprojectmaster, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, String orderflag, Long directorycode2, int j, LSuserMaster createdby2,
			Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, String orderflag2, Long directorycode3,
			int k, LSuserMaster createdby3, Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList,
			LSprojectmaster lsprojectmaster3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, String orderflag, Long directorycode2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2, String orderflag2, Long directorycode3,
			int k, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSprojectmaster lsprojectmaster, Integer testcode,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag, Long directorycode2, int j,
			LSuserMaster createdby2, Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, Integer testcode2,
			String orderflag2, Long directorycode3, int k, LSuserMaster createdby3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, Integer testcode3,
			String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag, Long directorycode2, int j,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2,
			Integer testcode2, String orderflag2, Long directorycode3, int k, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3,
			Integer testcode3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, LSuserMaster createdby, Integer testcode,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer testcode, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer testcode,
			String orderflag, Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2, Date todate2,
			Integer testcode2, String orderflag2, Long directorycode3, int k, LSuserMaster createdby3, Date fromdate3,
			Date todate3, List<Long> selectedteambatchCodeList, Integer testcode3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer testcode,
			String orderflag, Long directorycode2, int j, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, Integer testcode2, String orderflag2, Long directorycode3, int k,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList,
			Integer testcode3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			String orderflag, Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2, Date fromdate2,
			Date todate2, String orderflag2, Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3,
			Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			String orderflag, Long directorycode2, int j, Integer filetype2, Date fromdate2, Date todate2,
			List<LSuserMaster> lstuserMaster, String orderflag2, Long directorycode3, int k, Integer filetype3,
			Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList,
			String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			Integer testcode, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, Integer testcode,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Integer testcode, String orderflag, Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2,
			Date fromdate2, Date todate2, Integer testcode2, String orderflag2, Long directorycode3, int k,
			LSuserMaster createdby3, Integer filetype3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, Integer testcode3, String orderflag3);

	

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSprojectmaster lsprojectmaster,
			String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, String orderflag, Long directorycode2, int j, Integer filetype2,
			Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster, LSprojectmaster lsprojectmaster2,
			String orderflag2, Long directorycode3, int k, Integer filetype3, Date fromdate3, Date todate3,
			List<LSuserMaster> lstuserMaster2, List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3,
			String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, String orderflag, Long directorycode2, int j, LSuserMaster createdby2,
			Integer filetype2, Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2, String orderflag2,
			Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3, Date fromdate3, Date todate3,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSuserMaster createdby,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, LSprojectmaster lsprojectmaster,
			Integer testcode, String orderflag);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag, Long directorycode2, int j,
			LSuserMaster createdby2, Integer filetype2, Date fromdate2, Date todate2, LSprojectmaster lsprojectmaster2,
			Integer testcode2, String orderflag2, Long directorycode3, int k, LSuserMaster createdby3,
			Integer filetype3, Date fromdate3, Date todate3, List<Long> selectedteambatchCodeList,
			LSprojectmaster lsprojectmaster3, Integer testcode3, String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			LSprojectmaster lsprojectmaster, Integer testcode, String orderflag, Long directorycode2, int j,
			Integer filetype2, Date fromdate2, Date todate2, List<LSuserMaster> lstuserMaster,
			LSprojectmaster lsprojectmaster2, Integer testcode2, String orderflag2, Long directorycode3, int k,
			Integer filetype3, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2,
			List<Long> selectedteambatchCodeList, LSprojectmaster lsprojectmaster3, Integer testcode3,
			String orderflag3);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			Integer testcode, String orderflag, Long directorycode2, int j, Integer filetype2, Date fromdate2,
			Date todate2, List<LSuserMaster> lstuserMaster, Integer testcode2, String orderflag2, Long directorycode3,
			int k, Integer filetype3, Date fromdate3, Date todate3, List<LSuserMaster> lstuserMaster2,
			List<Long> selectedteambatchCodeList, Integer testcode3, String orderflag3);

	List<Logilaborderssh> findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecode(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, Integer sitecode, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2, int l, Integer sitecode2, int m, LSuserMaster objuser3,
			Date fromdate3, Date todate3, int n, Integer sitecode3, int o, LSuserMaster objuser4, Date fromdate4,
			Date todate4, int p, Integer sitecode4, LSuserMaster objuser5, LSuserMaster objuser6, Date fromdate5,
			Date todate5, int q, Integer sitecode5, LSuserMaster objuser7, Date fromdate6, Date todate6, int r,
			Integer sitecode6);

	List<Logilaborderssh> findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode);

	List<Logilaborderssh> findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeAndFiletypeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeAndFiletype(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, Integer sitecode, Integer filetype, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2, int l, Integer sitecode2, Integer filetype2, int m,
			LSuserMaster objuser3, Date fromdate3, Date todate3, int n, Integer sitecode3, Integer filetype3, int o,
			LSuserMaster objuser4, Date fromdate4, Date todate4, int p, Integer sitecode4, Integer filetype4,
			LSuserMaster objuser5, LSuserMaster objuser6, Date fromdate5, Date todate5, int q, Integer sitecode5,
			Integer filetype5, LSuserMaster objuser7, Date fromdate6, Date todate6, int r, Integer sitecode6,
			Integer filetype6);

	List<Logilaborderssh> findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndFiletype(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, Integer filetype);

	List<Logilaborderssh> findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeAndTestcodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeAndTestcode(
			int i, LSuserMaster objuser, Date fromdate, Date todate, int j, Integer sitecode, Integer testcode, int k,
			LSuserMaster objuser2, Date fromdate2, Date todate2, int l, Integer sitecode2, Integer testcode2, int m,
			LSuserMaster objuser3, Date fromdate3, Date todate3, int n, Integer sitecode3, Integer testcode3, int o,
			LSuserMaster objuser4, Date fromdate4, Date todate4, int p, Integer sitecode4, Integer testcode4,
			LSuserMaster objuser5, LSuserMaster objuser6, Date fromdate5, Date todate5, int q, Integer sitecode5,
			Integer testcode5, LSuserMaster objuser7, Date fromdate6, Date todate6, int r, Integer sitecode6,
			Integer testcode6);

	List<Logilaborderssh> findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndTestcode(
			int i, List<LSprojectmaster> lstproject, Date fromdate, Date todate, Integer sitecode, Integer testcode);

	List<Logilaborderssh> findByApprovelstatusAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(
			int i, LSprojectmaster lsprojectmaster, Date fromdate, Date todate, Integer sitecode);

	List<Logilaborderssh> findByApprovelstatusAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndTestcodeAndFiletype(
			int i, LSprojectmaster lsprojectmaster, Date fromdate, Date todate, Integer sitecode, Integer testcode,
			Integer filetype);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, List<LSprojectmaster> lstproject,
			Long directorycode2, int j, Date fromdate2, Date todate2, Integer sitecode2);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer sitecode,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2,
			Date todate2, Integer sitecode2, Long directorycode3, int k, LSuserMaster createdby3, Date fromdate3,
			Date todate3, Integer sitecode3, List<LSprojectmaster> lstproject2, Long directorycode4, int l,
			LSuserMaster createdby4, Date fromdate4, Date todate4, Integer sitecode4);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer sitecode, String orderflag,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, Date fromdate2, Date todate2,
			Integer sitecode2, String orderflag2);

	List<Logilaborders>  findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer sitecode,
			String orderflag, List<LSprojectmaster> lstproject, Long directorycode2, int j, LSuserMaster createdby2,
			Date fromdate2, Date todate2, Integer sitecode2, String orderflag2, Long directorycode3, int k,
			LSuserMaster createdby3, Date fromdate3, Date todate3, Integer sitecode3, String orderflag3,
			List<LSprojectmaster> lstproject2, Long directorycode4, int l, LSuserMaster createdby4, Date fromdate4,
			Date todate4, Integer sitecode4, String orderflag4);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterInOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, Integer filetype, Date fromdate, Date todate, List<LSprojectmaster> lstproject,
			Long directorycode2, int j, Integer filetype2, Date fromdate2, Date todate2);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Integer filetype, Date fromdate, Date todate,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, LSuserMaster createdby2, Integer filetype2,
			Date fromdate2, Date todate2, Long directorycode3, int k, LSuserMaster createdby3, Integer filetype3,
			Date fromdate3, Date todate3, List<LSprojectmaster> lstproject2, Long directorycode4, int l,
			LSuserMaster createdby4, Integer filetype4, Date fromdate4, Date todate4, Long directorycode5, int m,
			LSuserMaster createdby5, Integer filetype5, Date fromdate5, Date todate5,
			List<Long> selectedteambatchCodeList);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
			Long directorycode, int i, LSuserMaster createdby, Date fromdate, Date todate, Integer testcode,
			List<LSprojectmaster> lstproject, Long directorycode2, int j, LSuserMaster createdby2, Date fromdate2,
			Date todate2, Integer testcode2, Long directorycode3, int k, LSuserMaster createdby3, Date fromdate3,
			Date todate3, Integer testcode3, List<LSprojectmaster> lstproject2, Long directorycode4, int l,
			LSuserMaster createdby4, Date fromdate4, Date todate4, Integer testcode4, Long directorycode5, int m,
			LSuserMaster createdby5, Date fromdate5, Date todate5, List<Long> selectedteambatchCodeList,
			Integer testcode5);

	List<Logilaborders> findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
			Long directorycode, int i, Date fromdate, Date todate, Integer testcode, List<LSprojectmaster> lstproject,
			Long directorycode2, int j, Date fromdate2, Date todate2, Integer testcode2);

	List<LSlogilablimsorderdetail> findByOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTeamselectedAndApprovelstatusIsNullAndLsprojectmasterIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndSitecodeAndApprovelstatusNotOrOrderflagAndAssignedtoAndCreatedtimestampBetweenAndSitecodeAndApprovelstatusNotOrderByBatchcodeDesc(
			String string, int i, Date fromdate, Date todate, Integer sitecode, String string2, int j,
			LSuserMaster objuser, Date fromdate2, Date todate2, int k, Integer sitecode2, String string3, int l,
			LSuserMaster objuser2, Date fromdate3, Date todate3, int m, Integer sitecode3, String string4, int n,
			LSuserMaster objuser3, Date fromdate4, Date todate4, Integer sitecode4, String string5, int o,
			LSuserMaster objuser4, Date fromdate5, Date todate5, Integer sitecode5, String string6, int p,
			LSuserMaster objuser5, Date fromdate6, Date todate6, boolean b, Integer sitecode6, String string7,
			LSuserMaster objuser6, LSuserMaster objuser7, Date fromdate7, Date todate7, Integer sitecode7, int q,
			String string8, LSuserMaster objuser8, Date fromdate8, Date todate8, Integer sitecode8, int r,
			Pageable pageableobj);

	long countByOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusNotAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTeamselectedAndApprovelstatusIsNullAndLsprojectmasterIsNullAndOrdercancellIsNullAndAssignedtoIsNullAndSitecodeOrOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndSitecodeAndApprovelstatusNotOrOrderflagAndAssignedtoAndCreatedtimestampBetweenAndSitecodeAndApprovelstatusNotOrderByBatchcodeDesc(
			String string, int i, Date fromdate, Date todate, Integer sitecode, String string2, int j,
			LSuserMaster objuser, Date fromdate2, Date todate2, int k, Integer sitecode2, String string3, int l,
			LSuserMaster objuser2, Date fromdate3, Date todate3, int m, Integer sitecode3, String string4, int n,
			LSuserMaster objuser3, Date fromdate4, Date todate4, Integer sitecode4, String string5, int o,
			LSuserMaster objuser4, Date fromdate5, Date todate5, Integer sitecode5, String string6, int p,
			LSuserMaster objuser5, Date fromdate6, Date todate6, boolean b, Integer sitecode6, String string7,
			LSuserMaster objuser6, LSuserMaster objuser7, Date fromdate7, Date todate7, Integer sitecode7, int q,
			String string8, LSuserMaster objuser8, Date fromdate8, Date todate8, Integer sitecode8, int r);


	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecode(
			List<Elnmaterial> nmaterialcode, Integer filetype, int i, int j, Integer sitecode);

	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeAndLsuserMaster(
			List<Elnmaterial> nmaterialcode, Integer filetype, int i, int j, Integer sitecode,
			LSuserMaster objorder);

	List<LogilabOrderDetails> findByLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecode(
			Integer filetype, int i, int j, Integer sitecode, Integer filetype2, int k, Integer sitecode2,
			Integer filetype3, int l, int m, Integer sitecode3, Integer filetype4, int n, Integer sitecode4);

	
}
