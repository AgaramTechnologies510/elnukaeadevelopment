package com.agaram.eln.primary.repository.fileManipulation;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agaram.eln.primary.model.fileManipulation.ProfilePicture;

public interface ProfilePictureRepository extends MongoRepository<ProfilePicture, Integer> {
 public ProfilePicture findTop1ById(Integer Id);
 public void deleteById(Integer Id);
}
