package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.instrumentDetails.Lsordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsordersharedby.LsordersharebyProjection;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LsordersharedbyRepository extends JpaRepository<Lsordersharedby, Long>{
	Lsordersharedby findBySharedbycode(Long sharedbycode);
	public List<Lsordersharedby> findBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(String unifiedid, Integer ordertype, Integer sharestatus);
	public LsordersharebyProjection findBySharebyunifiedidAndSharetounifiedidAndOrdertypeAndSharebatchcode(String sharebyunifiedid, String sharetounifiedid, Integer ordertype, Long sharebatchcode);
	
	public long countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(String unifiedid, Integer ordertype, Integer sharestatus);
	
	public List<Lsordersharedby> findBySharebyunifiedidAndSharedonBetweenOrUnsharedonBetween(String unifiedid, Date fromdate, Date todate, Date unshrfromdate, Date unshrtodate);
	
	public List<Lsordersharedby> findBySharebyunifiedidAndSharedonBetweenOrSharebyunifiedidAndUnsharedonBetween(String unifiedid, Date fromdate, Date todate,String sharebyunifiedid, Date unshrfromdate, Date unshrtodate);
	
	public List<Lsordersharedby> findByUsersharedbyAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(LSuserMaster lssharedby, Integer sharestatus, Date fromdate, Date todate);
	
	public List<Lsordersharedby> findByUsersharedbyAndUsersharedonAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(LSuserMaster lssharedby, LSuserMaster lssharedto, Integer sharestatus, Date fromdate, Date todate);
	
	public List<Lsordersharedby> findByUsersharedbyAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(LSuserMaster lsselecteduser, Integer filetype, int i, Date fromdate, Date todate);
	public List<Lsordersharedby> findByUsersharedbyAndUsersharedonAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
			LSuserMaster lsloginuser, LSuserMaster lsselecteduser, Integer filetype, int i, Date fromdate, Date todate);
	public List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate);
	public List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate);

	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			Integer selecttest);
	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject);
	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			Integer selecttest, LSprojectmaster selectproject);
	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate, Integer selecttest);
	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject);
	List<LsordersharebyProjection> findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharedbycodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject, Integer selecttest);
			

}
