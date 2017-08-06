package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Folder_;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolder_;
import org.debugroom.wedding.domain.entity.gallery.Photo_;
import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindRelatedPhotoByFolderId implements Specification<Photo>{
	
	private String folderId;
	
	@Override
	public Predicate toPredicate(Root<Photo> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Photo, PhotoRelatedFolder> joinPhotoRelatedFolder = 
				root.join(Photo_.photoRelatedFolders);
		Join<PhotoRelatedFolder, Folder> joinFolder = 
				joinPhotoRelatedFolder.join(PhotoRelatedFolder_.folder);
		return criteriaBuilder.equal(joinFolder.get(Folder_.folderId), folderId);
	}

}
