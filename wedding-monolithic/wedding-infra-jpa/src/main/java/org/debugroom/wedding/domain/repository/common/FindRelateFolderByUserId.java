package org.debugroom.wedding.domain.repository.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Folder_;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.User_;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolder;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolder_;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindRelateFolderByUserId implements Specification<Folder>{

	private String userId;

	@Override
	public Predicate toPredicate(Root<Folder> root, 
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Join<Folder, UserRelatedFolder> joinUserRelatedFolder = root.join(Folder_.userRelatedFolders);
		Join<UserRelatedFolder, User> joinUser = joinUserRelatedFolder.join(UserRelatedFolder_.usr);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}
	
}
