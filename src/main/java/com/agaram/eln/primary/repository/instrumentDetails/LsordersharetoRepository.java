package com.agaram.eln.primary.repository.instrumentDetails;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.instrumentDetails.Lsordershareto;
import com.agaram.eln.primary.model.instrumentDetails.Lsordershareto.LsordersharetoProjection;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LsordersharetoRepository extends JpaRepository<Lsordershareto,Long>{
	public List<Lsordershareto> findBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(String unifiedid, Integer ordertype, Integer sharestatus);
	public List<Lsordershareto.LsordersharetoProjection> findBySharebyunifiedidAndSharetounifiedidAndOrdertypeAndSharebatchcode(String sharebyunifiedid, String sharetounifiedid, Integer ordertype, Long sharebatchcode);
	
	public long countBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(String unifiedid, Integer ordertype, Integer sharestatus);
	
	public List<Lsordershareto> findBySharetounifiedidAndSharedonBetweenAndSharestatus(String unifiedid, Date fromdate, Date todate, Integer sharestatus);
	
	public List<Lsordershareto> findByUsersharedonAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(LSuserMaster lssharedto, Integer sharestatus, Date fromdate, Date todate);
	public List<Lsordershareto> findByUsersharedonAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(LSuserMaster lsselecteduser, Integer filetype, int i, Date fromdate, Date todate);
	
	
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate);
	
	
	public List<Lsordershareto> findBySharetounifiedidAndSharedonBetweenAndSharestatusAndSitecode(String unifieduserid, Date fromdate,
			Date todate, int i, Integer sitecode);
	
	public LsordersharetoProjection findBySharebatchcodeAndSharestatusAndUsersharedon(Long batchcode, int i,
			LSuserMaster objLoggeduser);
	
	Lsordershareto findBySharetocode(Long sharetocode);
	
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate, Integer selecttest);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject, Integer selecttest);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			Integer selecttest);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject);
	public List<LsordersharetoProjection> findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharetocodeDesc(
			LSuserMaster lsselecteduser, Integer sitecode, Integer filetype, int i, Date fromdate, Date todate,
			LSprojectmaster selectproject, Integer selecttest);
	
	
}
