package com.agaram.eln.primary.repository.cloudProtocol;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.cloudProtocol.CloudLSprotocolstepInfo;

public interface CloudLSprotocolstepInfoRepository extends JpaRepository<CloudLSprotocolstepInfo, Integer> {

	public CloudLSprotocolstepInfo findTop1ById(Integer integer);

}
