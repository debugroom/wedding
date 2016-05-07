package org.debugroom.wedding.domain.repository.photo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Folder_;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolder;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolder_;
import org.debugroom.wedding.domain.model.entity.Photo;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedFolder;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedFolder_;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedUser;
import org.debugroom.wedding.domain.model.entity.PhotoRelatedUser_;
import org.debugroom.wedding.domain.model.entity.Photo_;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.User_;

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
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Join<Folder, UserRelatedFolder> joinUserRelatedFolder = root.join(Folder_.userRelatedFolders);
		Join<UserRelatedFolder, User> joinUser = joinUserRelatedFolder.join(UserRelatedFolder_.usr);
		Join<Folder, PhotoRelatedFolder> joinPhotoRelatedFolder = root.join(Folder_.photoRelatedFolders);
		joinPhotoRelatedFolder.join(PhotoRelatedFolder_.photo);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}
	
}
