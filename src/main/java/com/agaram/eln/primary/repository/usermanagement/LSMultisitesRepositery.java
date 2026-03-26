package com.agaram.eln.primary.repository.usermanagement;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.usermanagement.LSMultisites;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;

public interface LSMultisitesRepositery extends JpaRepository<LSMultisites, Integer>{

	List<LSMultisites> findByLssiteMaster(LSSiteMaster lssitemaster);

	@Transactional
	void deleteByusercode(Integer usercode);

	List<LSMultisites> findByLssiteMasterIn(List<LSSiteMaster> siteobj);

	List<LSMultisites> findByusercodeIn(List<Integer> usercode);

	List<LSMultisites> findByLssiteMasterAndUsercode(LSSiteMaster objsite, Integer userID);

	@Transactional
	@Modifying
	@Query(value="select usercode from lsmultisites where lssitemaster_sitecode=?1",nativeQuery = true)
	List<Integer> getusernameandusercode(Integer integer);

	LSMultisites findTop1Byusercode(Integer usercode);

	List<LSMultisites> findByusercodeInAndLssiteMaster(List<Integer> usermasterlist, LSSiteMaster sitemaster);

	List<LSMultisites> findByLssiteMasterAndUsercodeIsNotNull(LSSiteMaster objsite);
	@Transactional
	@Modifying
	@Query(value = "delete from LSMultisites where usercode is null", nativeQuery = true)
	int deleteUsercodeIsNull();
}
