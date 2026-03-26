package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolorderstructure;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;


public interface LsprotocolOrderStructureRepository extends JpaRepository<Lsprotocolorderstructure, Long> {

	Lsprotocolorderstructure findByDirectorycodeAndParentdircodeAndDirectorynameNot(Long directorycode, Long parentdircode,
			String directoryname);

	Lsprotocolorderstructure findByParentdircodeAndDirectoryname(Long parentdircode, String directoryname);

	@Transactional
	@Modifying
	@Query(value = "UPDATE lsprotocolorderstructure SET " +
            "parentdircode = ?1, " +
            "path = ?2, " +
            "directoryname = ?4, " +
            "modifiedby_usercode = ?5, " +
            "datemodified = ?6 " +
            "WHERE directorycode = ?3", nativeQuery = true)
	void updatedirectory(Long parentdircode , String path, Long directorycode, String directoryname, Integer usercode, Date modDate);

	List<Lsprotocolorderstructure> findByDirectorycodeIn(List<Long> lstfoldersid);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, LSuserMaster lsuserMaster, int j);



	Lsprotocolorderstructure findByParentdircodeAndDirectorynameIgnoreCase(Long parentdircode, String directoryname);


	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, String search_Key, LSuserMaster objusermaster, int j, String search_Key2,
			LSuserMaster objusermaster2, int k, String search_Key3, Pageable pageable);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorynameLikeIgnoreCaseOrCreatedbyAndViewoptionAndDirectorynameLikeIgnoreCaseOrSitemasterAndViewoptionAndCreatedbyInAndDirectorynameLikeIgnoreCaseOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, String search_Key, LSuserMaster objusermaster, int j, String search_Key2,
			LSSiteMaster lssitemaster2, int k, List<LSuserMaster> usernotify, String search_Key3, Pageable pageable);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster, int j,
			LSuserMaster lsuserMaster2, int k);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster, int j,
			LSSiteMaster lssitemaster2, int k, List<LSuserMaster> lstuserMaster);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster, int j);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster,
			LSSiteMaster lssitemaster2, int j, LSSiteMaster lssitemaster3, int k, List<LSuserMaster> lstuserMaster);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrCreatedbyAndSitemasterAndViewoptionOrderByDirectorycode(
			LSSiteMaster lssitemaster, int i, List<Long> immutableNegativeValues, LSuserMaster lsuserMaster,
			LSSiteMaster lssitemaster2, int j, LSuserMaster lsuserMaster2, LSSiteMaster lssitemaster3, int k);

	List<Lsprotocolorderstructure> findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyInAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
			LSSiteMaster activeusersite, int i, List<Long> immutableNegativeValues, List<LSuserMaster> lstusers, int j,
			LSSiteMaster activeusersite2, int k, List<LSuserMaster> lstusers2);

	Lsprotocolorderstructure findByDirectorycodeAndParentdircodeAndDirectorynameNotAndSitemaster(Long directorycode,
			Long parentdircode, String directoryname, LSSiteMaster sitemaster);

	Lsprotocolorderstructure findByParentdircodeAndDirectorynameIgnoreCaseAndSitemaster(Long parentdircode,
			String directoryname, LSSiteMaster sitemaster);

	List<Lsprotocolorderstructure> findByDirectorycode(Long parentdircode);


	

}
