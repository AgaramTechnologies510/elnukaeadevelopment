package com.agaram.eln.primary.repository.fileManipulation;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agaram.eln.primary.model.fileManipulation.Fileimagestemp;

public interface FileimagestempRepository extends MongoRepository<Fileimagestemp, Integer> {
	Fileimagestemp findByFileid(String fileid);
}
