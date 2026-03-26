package com.agaram.eln.primary.repository.usermanagement;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.usermanagement.LSnotification;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface LSnotificationRepository extends JpaRepository<LSnotification, Long> {
	public Long countByNotifationtoAndIsnewnotification(LSuserMaster lsuserMaster, Integer isnew);
	public Long countByNotifationtoAndIsnewnotificationAndNotificationfor(LSuserMaster lsuserMaster, Integer isnew,Integer notificationfor);
	public List<LSnotification> findFirst20ByNotifationtoOrderByNotificationcodeDesc(LSuserMaster lsuserMaster);
	public List<LSnotification> findFirst20ByNotifationtoAndNotificationforOrderByNotificationcodeDesc(LSuserMaster lsuserMaster,Integer notificationfor);
	public Long countByNotifationtoAndIsnewnotificationAndNotificationcodeGreaterThan(LSuserMaster lsuserMaster, Integer isnew, Long notificationcode);
	public List<LSnotification> findByNotifationtoAndNotificationcodeGreaterThanOrderByNotificationcodeDesc(LSuserMaster lsuserMaster, Long notificationcode);
	public List<LSnotification> findFirst20ByNotifationtoAndNotificationcodeLessThanOrderByNotificationcodeDesc(LSuserMaster lsuserMaster, Long notificationcode);
	public List<LSnotification> findFirst20ByNotifationtoAndNotificationcodeLessThanAndNotificationforOrderByNotificationcodeDesc(LSuserMaster lsuserMaster, Long notificationcode,Integer notificationfor);
	
	@Transactional
	@Modifying
	@Query("update LSnotification o set o.isnewnotification = 0 where o.notifationto = ?1 and notificationcode = ?2")
	void updatenotificationstatus(LSuserMaster lsuserMaster, Long notificationcode);
	
	@Transactional
	@Modifying
	@Query("update LSnotification o set o.isnewnotification = 0 where o.notifationto = ?1 and o.notificationfor = 1")
	void updatereadallnotificationstatusforme(LSuserMaster lsuserMaster);
	
	@Transactional
	@Modifying
	@Query("update LSnotification o set o.isnewnotification = 0 where o.notifationto = ?1 and o.notificationfor = 2")
	void updatereadallnotificationstatusforteam(LSuserMaster lsuserMaster);
	
	public LSnotification findByRepositorycodeAndRepositorydatacode(Integer repositorycode, Integer repositorydatacode);
	public LSnotification findByRepositorycodeAndRepositorydatacodeAndNotificationdetils(Integer repositorycode,
			Integer repositorydatacode, String details);
	public Object countByNotifationtoAndIsnewnotificationAndNotificationforOrNotifationtoAndIsnewnotificationAndNotificationfor(
			LSuserMaster lsuserMaster, int i, int j, LSuserMaster lsuserMaster2, int k, int l);
	public Object countByNotifationtoAndIsnewnotificationAndNotificationforOrNotifationtoAndIsnewnotificationAndNotificationforAndSitecode(
			LSuserMaster notifationto, int i, int j, LSuserMaster notifationto2, int k, int l, Integer sitecode);
	public Object countByNotifationtoAndIsnewnotificationAndNotificationforAndSitecode(LSuserMaster notifationto, int i,
			int j, Integer sitecode);
	public List<LSnotification> findFirst20ByNotifationtoAndNotificationforAndSitecodeOrderByNotificationcodeDesc(
			LSuserMaster lsuserMaster, Integer notifyfor, Integer sitecode);
	
	public Object countByNotifationtoAndIsnewnotificationAndNotificationforAndSitecodeOrNotifationtoAndIsnewnotificationAndNotificationforAndSitecode(
			LSuserMaster lsuserMaster, int i, int j, Integer sitecode, LSuserMaster lsuserMaster2, int k, int l,
			Integer sitecode2);
	public List<LSnotification> findFirst20ByNotifationtoAndNotificationcodeLessThanAndNotificationforAndSitecodeOrderByNotificationcodeDesc(
			LSuserMaster notifationto, Long notificationcode, int notificationfor, Integer sitecode);

}
