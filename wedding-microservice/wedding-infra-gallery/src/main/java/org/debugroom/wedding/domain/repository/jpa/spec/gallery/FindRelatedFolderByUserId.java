package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Folder_;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolder_;
import org.debugroom.wedding.domain.entity.gallery.User_;
import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindRelatedFolderByUserId implements Specification<Folder>{
	
	private String userId;
	
	@Override
	public Predicate toPredicate(Root<Folder> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Folder, UserRelatedFolder> joinUserRelatedFolder = 
				root.join(Folder_.userRelatedFolders);
		Join<UserRelatedFolder, User> joinUser = 
				joinUserRelatedFolder.join(UserRelatedFolder_.usr);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}

}
