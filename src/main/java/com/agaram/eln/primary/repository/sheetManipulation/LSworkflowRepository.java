package com.agaram.eln.primary.repository.sheetManipulation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflowgroupmapping;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;

public interface LSworkflowRepository  extends JpaRepository<LSworkflow, Integer>{

	public LSworkflow findTopByOrderByWorkflowcodeDesc();
	public LSworkflow findTopByOrderByWorkflowcodeAsc();
	public LSworkflow findTopByAndLssitemasterOrderByWorkflowcodeDesc(LSSiteMaster lssitemaster);
	public LSworkflow findTopByAndLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);
	public List<LSworkflow> findByLsworkflowgroupmappingInOrderByWorkflowcodeDesc(List<LSworkflowgroupmapping> lsworkflowgroupmapping);
	public List<LSworkflow> findBylssitemaster(LSSiteMaster LSSiteMaster);
	public List<LSworkflow> findByLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);
	public List<LSworkflow> findByLsworkflowgroupmappingIn(List<LSworkflowgroupmapping> lsworkflowgroupmapping);
	public LSworkflow findByworkflowcode(Integer workflowcode);
	public long countByLssitemaster(LSSiteMaster lssitemaster);
	public LSworkflow findTop1ByLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);
	public List<LSworkflow> findByLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);
	public List<LSworkflow> findByLsworkflowgroupmappingInAndStatus(List<LSworkflowgroupmapping> lsworkflowgroupmapping,
			int i);
	public long countByLssitemasterAndStatus(LSSiteMaster lssitemaster, int i);
	public LSworkflow findTopByAndLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);
	public LSworkflow findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(LSSiteMaster lssitemaster, int i);

}
