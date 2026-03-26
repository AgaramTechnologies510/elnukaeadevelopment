package com.agaram.eln.primary.repository.protocol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.protocols.LSprotocolvideos;
import com.agaram.eln.primary.model.protocols.ProtocolImage;

public interface LSprotocolvideosRepository extends JpaRepository<LSprotocolvideos, Integer>{

	ProtocolImage findByFileid(String fileid);

	List<LSprotocolvideos> findByProtocolstepcode(Integer protocolstepcode);

}
