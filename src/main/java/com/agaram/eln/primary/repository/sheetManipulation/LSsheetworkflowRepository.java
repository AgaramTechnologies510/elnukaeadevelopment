package com.agaram.eln.primary.repository.sheetManipulation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.sheetManipulation.LSsheetworkflow;
import com.agaram.eln.primary.model.sheetManipulation.LSsheetworkflowgroupmap;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;

import jakarta.transaction.Transactional;

public interface LSsheetworkflowRepository  extends JpaRepository<LSsheetworkflow, Integer>{

	public LSsheetworkflow findTopByOrderByWorkflowcodeDesc();
	public LSsheetworkflow findTopByOrderByWorkflowcodeAsc();
	public LSsheetworkflow findTopByAndLssitemasterOrderByWorkflowcodeDesc(LSSiteMaster lssitemaster);
	public LSsheetworkflow findTopByAndLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);
	public List<LSsheetworkflow> findByLssheetworkflowgroupmapIn(List<LSsheetworkflowgroupmap> lssheetworkflowgroupmap);
	public List<LSsheetworkflow> findBylssitemaster(LSSiteMaster LSSiteMaster);
	public LSsheetworkflow findByLssitemaster_sitecode(Integer sitecode);
   
	//public LSsheetworkflow findByLssitemaster_sitecode(Integer sitecode);
	public LSsheetworkflow findTopByAndLssitemaster_sitecodeOrderByWorkflowcodeAsc(Integer sitecode);
	public List<LSsheetworkflow> findBylssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);
	public List<LSsheetworkflow> findBylssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);
	public LSsheetworkflow findTopByAndLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);
	public long countByLssitemaster(LSSiteMaster lsSiteMaster);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE LSfile SET lssheetworkflow_workflowcode = ?1 WHERE lssitemaster_sitecode = ?2 And lssheetworkflow_workflowcode is null And filecode in(?3)",nativeQuery = true)
	public void updateWorkflowcode(int workflowCode, Integer sitecode, List<Integer> filecode);
	public long countByLssitemasterAndStatus(LSSiteMaster lssitemaster, int i);

}
	