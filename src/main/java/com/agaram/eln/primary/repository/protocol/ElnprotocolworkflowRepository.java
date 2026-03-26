package com.agaram.eln.primary.repository.protocol;

import java.util.List;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.protocols.Elnprotocolworkflow;
import com.agaram.eln.primary.model.protocols.Elnprotocolworkflowgroupmap;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;

public interface ElnprotocolworkflowRepository extends JpaRepository<Elnprotocolworkflow, Integer>{

	List<Elnprotocolworkflow> findByLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);

	List<Elnprotocolworkflow> findByelnprotocolworkflowgroupmapIn(List<Elnprotocolworkflowgroupmap> lsworkflowgroupmapping);

	List<Elnprotocolworkflow> findByelnprotocolworkflowgroupmapInOrderByWorkflowcodeDesc(
			List<Elnprotocolworkflowgroupmap> elnprotocolworkflowgroupmapobj);

	Elnprotocolworkflow findTopByAndLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster site);

	long countByLssitemaster(LSSiteMaster lssitemaster);
	
	Elnprotocolworkflow findTop1ByLssitemasterOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster);

	List<Elnprotocolworkflow> findByLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);

	List<Elnprotocolworkflow> findByelnprotocolworkflowgroupmapInAndStatus(List<Elnprotocolworkflowgroupmap> lstp_workflow,
			int i);

	long countByLssitemasterAndStatus(LSSiteMaster lssitemaster, int i);

	Elnprotocolworkflow findTop1ByLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster lssitemaster, int i);

	Elnprotocolworkflow findTopByAndLssitemasterAndStatusOrderByWorkflowcodeAsc(LSSiteMaster site, int i);

}
