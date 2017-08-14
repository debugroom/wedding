package org.debugroom.wedding.domain.repository.jpa.gallery;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.debugroom.wedding.domain.entity.gallery.Photo;

public interface PhotoRepository extends JpaRepository<Photo, String>,
												JpaSpecificationExecutor<Photo>{

	@Override
	@Cacheable(cacheNames="photo")
	public Photo findOne(String photoId);
	
	public List<Photo> findByPhotoIdIn(List<String> photoIds);
	
	public Photo findTopByPhotoIdLikeOrderByPhotoIdDesc(String photoId);

}
