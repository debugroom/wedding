package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedUser;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedUser_;
import org.debugroom.wedding.domain.entity.gallery.Photo_;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.entity.gallery.User_;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindViewablePhotoByUserId implements Specification<Photo>{
	
	private String userId;

	@Override
	public Predicate toPredicate(Root<Photo> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Photo, PhotoRelatedUser> joinPhotoRelatedUser = root.join(Photo_.photoRelatedUsers);
		Join<PhotoRelatedUser, User> joinUser = joinPhotoRelatedUser.join(PhotoRelatedUser_.usr);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}

}
