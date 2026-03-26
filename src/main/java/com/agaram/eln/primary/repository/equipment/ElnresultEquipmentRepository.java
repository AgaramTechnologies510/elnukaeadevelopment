package com.agaram.eln.primary.repository.equipment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.equipment.ElnresultEquipment;

public interface ElnresultEquipmentRepository extends JpaRepository<ElnresultEquipment, Integer>{

	List<ElnresultEquipment> findByNequipmentcodeAndCreateddateBetween(Integer nequipmentcode, Date fromdate,
			Date todate);
	
	List<ElnresultEquipment> findByNequipmentcode(Integer nequipmentcode);
	
	ElnresultEquipment findByOrdercodeAndNresultequipmentcodeAndIscompleteAndIsreturnedAndTemplatecodeIsNotNullOrderByNresultequipmentcodeDesc(
			Long valueOf, Integer nresultequipmentcode, boolean b, boolean c);

	List<ElnresultEquipment> findByNequipmentcodeAndCreateddateBetweenAndIscompleteAndIsreturned(Integer nequipmentcode,
			Date fromdate, Date todate, boolean b, boolean c);

	List<ElnresultEquipment> findByOrdercodeAndIscompleteAndIsreturnedAndTemplatecodeIsNotNullAndNequipmentcodeInOrderByNresultequipmentcodeDesc(
			Long valueOf, boolean b, boolean c, List<Integer> nequipmentcode);

	List<ElnresultEquipment> findByNequipmentcodeAndAndIscompleteAndIsreturned(Integer nequipmentcode, boolean b,
			boolean c);

	List<ElnresultEquipment> findByOrdercodeAndIscompleteAndIsreturnedAndTemplatecodeIsNotNullAndTemplatecodeIsNotOrderByNresultequipmentcodeDesc(
			Long valueOf, boolean b, boolean c, int i);

	List<ElnresultEquipment> findByOrdercodeAndIscompleteAndIsreturnedAndTemplatecodeIsNotNullAndTemplatecodeIsNotAndNequipmentcodeInOrderByNresultequipmentcodeDesc(
			Long valueOf, boolean b, boolean c, int i, List<Integer> nequipmentcode);

	List<ElnresultEquipment> findByOrdercodeAndIscompleteAndIsreturnedAndTemplatecodeOrderByNresultequipmentcodeDesc(
			Long valueOf, boolean b, boolean c, int i);



}
