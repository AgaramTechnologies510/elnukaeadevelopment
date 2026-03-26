package com.agaram.eln.primary.repository.cfr;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.cfr.LScfrsettings;
import com.agaram.eln.primary.model.cfr.embeddable.CfrsettingId;

public interface LScfrsettingsRepository  extends JpaRepository<LScfrsettings, CfrsettingId> {
	
}

