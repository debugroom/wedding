package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Folder_;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolder_;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolder_;
import org.debugroom.wedding.domain.entity.gallery.User_;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindRelatedFolderWithViewablePhotoByUserId implements Specification<Folder>{

	private String userId;
	
	@Override
	public Predicate toPredicate(Root<Folder> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Folder, UserRelatedFolder> joinUserRelatedFolder = root.join(Folder_.userRelatedFolders);
		Join<UserRelatedFolder, User> joinUser = joinUserRelatedFolder.join(UserRelatedFolder_.usr);
		Join<Folder, PhotoRelatedFolder> joinPhotoRelatedFolder = root.join(Folder_.photoRelatedFolders);
		joinPhotoRelatedFolder.join(PhotoRelatedFolder_.photo);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}

}
