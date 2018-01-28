package org.debugroom.wedding.domain.repository.photo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Folder_;
import org.debugroom.wedding.domain.model.entity.Photo;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedFolder;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedFolder_;
import org.debugroom.wedding.domain.model.entity.Photo_;

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
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Join<Photo, PhotoRelatedFolder> joinPhotoRelatedFolder = root.join(Photo_.photoRelatedFolders);
		Join<PhotoRelatedFolder, Folder> joinFolder = joinPhotoRelatedFolder.join(PhotoRelatedFolder_.folder);
		return criteriaBuilder.equal(joinFolder.get(Folder_.folderId), folderId);
	}

}
