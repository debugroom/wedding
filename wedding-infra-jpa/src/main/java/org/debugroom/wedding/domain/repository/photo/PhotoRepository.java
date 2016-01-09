package org.debugroom.wedding.domain.repository.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.model.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, String>{
}
