package com.agaram.eln.primary.repository.protocol;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agaram.eln.primary.model.protocols.Protocolvideos;

public interface ProtocolvideosRepository extends MongoRepository<Protocolvideos, Integer> {

	Protocolvideos findByFileid(String fileid);

}
