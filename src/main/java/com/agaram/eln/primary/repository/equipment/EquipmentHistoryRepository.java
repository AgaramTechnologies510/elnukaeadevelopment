package com.agaram.eln.primary.repository.equipment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.equipment.EquipmentHistory;

public interface EquipmentHistoryRepository extends JpaRepository<EquipmentHistory, Integer>{

	List<EquipmentHistory> findByNequipmentcodeAndHistorytypeAndCreateddateBetween(Integer nequipmentcode, int i,
			Date fromdate, Date todate);

}
