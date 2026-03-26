package com.agaram.eln.primary.repository.fileManipulation;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agaram.eln.primary.model.fileManipulation.SheetorderlimsRefrence;;

public interface SheetorderlimsRefrenceRepository extends MongoRepository<SheetorderlimsRefrence, String>{

	public SheetorderlimsRefrence findTop1ById(String Id);
	
}
