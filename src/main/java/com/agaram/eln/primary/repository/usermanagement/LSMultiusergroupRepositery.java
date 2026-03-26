package com.agaram.eln.primary.repository.usermanagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.usermanagement.LSMultiusergroup;
import com.agaram.eln.primary.model.usermanagement.LSusergroup;

import jakarta.transaction.Transactional;

public interface LSMultiusergroupRepositery extends JpaRepository<LSMultiusergroup, Integer> {

	public LSMultiusergroup findBymultiusergroupcode(Integer multiusergroupcode);
	
	@Transactional
	public void deleteByusercode(Integer usercode);

	public List<LSMultiusergroup> findByusercode(Integer usercode);

	public List<LSMultiusergroup> findBylsusergroupIn(List<LSusergroup> usergroupcodelist);

	public List<LSMultiusergroup> findBylsusergroupAndUsercodeNot(LSusergroup lsusergroup, Integer usercode);

	public List<LSMultiusergroup> findBylsusergroup(LSusergroup lsusergroup);

	public long countByUsercodeAndDefaultusergroup(Integer usercode, int i);

	@Transactional
	@Modifying
	@Query(value = "delete from LSMultiusergroup where usercode is null", nativeQuery = true)
	int deleteUsercodeIsNull();
	

	

}
