package org.debugroom.wedding.domain.repository.jpa.operation;

import org.debugroom.wedding.domain.entity.operation.Photo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String>{
}
