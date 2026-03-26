package com.agaram.eln.primary.repository.sheetManipulation;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.agaram.eln.primary.model.sheetManipulation.LSfileelnmethod;

public interface LSfileelnmethodRepository extends JpaRepository<LSfileelnmethod, Integer> {
	@Transactional
	@Modifying
	@Query("delete from LSfileelnmethod where filecode = ?1")
	void deleteByfilecode(Integer filecode);
	
	List<LSfileelnmethod> findByFilecode(Integer filecode);

}
