package org.debugroom.wedding.domain.repository.jpa.gallery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.debugroom.wedding.domain.entity.gallery.User;

public interface GalleryUserRepository extends JpaRepository<User, String>, 
													JpaSpecificationExecutor<User>{
}
