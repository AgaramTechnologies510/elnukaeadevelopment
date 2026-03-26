package com.agaram.eln.primary.repository.cloudFileManip;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.cloudFileManip.CloudSheetCreation;

public interface CloudSheetCreationRepository extends JpaRepository<CloudSheetCreation, Long> {
	public CloudSheetCreation findTop1ById(Long Id);

	public CloudSheetCreation findByIdAndContainerstored(long filecode, int i);
}
