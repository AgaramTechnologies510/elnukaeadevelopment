package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.instrumentDetails.LSSheetOrderStructure;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LSSheetOrderStructureRepository extends JpaRepository<LSSheetOrderStructure, Long> {
	@Transactional
	@Modifying
	@Query(value = "UPDATE LSSheetOrderStructure SET " +
            "parentdircode = ?1, " +
            "path = ?2, " +
            "directoryname = ?4, " +
            "modifiedby_usercode = ?5, " +
            "datemodified = ?6 " +
            "WHERE directorycode = ?3", nativeQuery = true)
	void updatedirectory(Long parentdircode, String path, Long directorycode, String directoryname, Integer usercode, Date modDate);

	@Transactional
	@Modifying
	@Query("delete from LSSheetOrderStructure o where o.directorycode = ?1")
	void deletedirectory(Long directorycode);

	LSSheetOrderStructure findByParentdircodeAndDirectoryname(Long parentdircode, String directoryname);



	List<LSSheetOrderStructure> findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(LSSiteMaster site,Integer siteviewopt,LSuserMaster createduser,Integer userviewopt);
	
	List<LSSheetOrderStructure> findByDirectorycodeIn(List<Long> directorycode);



	LSSheetOrderStructure findByParentdircodeAndDirectorynameIgnoreCase(Long parentdircode, String directoryname);


	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, String search_Key, LSuserMaster objusermaster, int j, String search_Key2,
			LSuserMaster objusermaster2, int k, String search_Key3, Pageable pageable);

	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrSitemasterAndViewoptionAndCreatedbyInAndDirectorynameLikeIgnoreCaseOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, String search_Key, LSuserMaster objusermaster, int j, String search_Key2,
			LSSiteMaster lssitemaster2, int k, List<LSuserMaster> usernotify, String search_Key3, Pageable pageable);

	List<LSSheetOrderStructure> findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionAndDirectorycodeNotOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, LSuserMaster lsuserMaster, int j, LSuserMaster lsuserMaster2, int k,
			Long l);

	List<LSSheetOrderStructure> findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInAndDirectorycodeNotOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, LSuserMaster lsuserMaster, int j, LSSiteMaster lssitemaster2, int k,
			List<LSuserMaster> lstuserMaster, Long l);



	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster, int j,
			LSuserMaster lsuserMaster2, int k);


	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster, int j);

	LSSheetOrderStructure findByDirectorycodeAndParentdircodeAndDirectorynameNotAndSitemaster(Long directorycode,
			Long parentdircode, String directoryname, LSSiteMaster sitemaster);

	LSSheetOrderStructure findByDirectorynameIgnoreCaseAndParentdircodeAndSitemaster(String directoryname,
			Long parentdircode, LSSiteMaster sitemaster);

	LSSheetOrderStructure findByDirectorycodeAndParentdircodeAndDirectorynameNot(Long directorycode, Long parentdircode,
			String directoryname);

	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrCreatedbyAndSitemasterAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster,
			LSSiteMaster lssitemaster2, int j, LSuserMaster lsuserMaster2, LSSiteMaster lssitemaster3, int k);


	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyInAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
			LSSiteMaster activeusersite, int i, List<Long> immutableNegativeValues, List<LSuserMaster> lstusers, int j,
			LSSiteMaster activeusersite2, int k, List<LSuserMaster> lstusers2);

	List<LSSheetOrderStructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
			LSSiteMaster activeusersite, int i, List<Long> immutableNegativeValues, LSuserMaster lsusermaster, int j,
			LSSiteMaster activeusersite2, int k, List<LSuserMaster> usernotify);

	List<LSSheetOrderStructure> findByDirectorycode(Long directorycode);

	
}