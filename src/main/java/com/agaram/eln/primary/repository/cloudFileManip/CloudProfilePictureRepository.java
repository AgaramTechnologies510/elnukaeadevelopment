package com.agaram.eln.primary.repository.cloudFileManip;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.cloudFileManip.CloudProfilePicture;

public interface CloudProfilePictureRepository extends JpaRepository<CloudProfilePicture, Integer> {
 public CloudProfilePicture findTop1ById(Integer Id);
 @Transactional
 public void deleteById(Integer Id);
}
