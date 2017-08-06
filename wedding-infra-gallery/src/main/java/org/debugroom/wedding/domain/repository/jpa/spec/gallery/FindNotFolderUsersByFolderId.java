package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

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
public class FindNotFolderUsersByFolderId implements Specification<User>{
	
	private String folderId;
	
	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		
		Path<Object> path = root.get("userId");
		Subquery<User> subQuery = criteriaBuilder.createQuery().subquery(User.class);
		Root<User> subQueryRoot = subQuery.from(User.class);
		Join<User, UserRelatedFolder> subQueryJoinUserRelatedFolder = 
				subQueryRoot.join(User_.userRelatedFolders);
		Join<UserRelatedFolder, Folder> subQueryJoinFolder = 
				subQueryJoinUserRelatedFolder.join(UserRelatedFolder_.folder);
		Predicate subQueryPredicate = criteriaBuilder.equal(
				subQueryJoinFolder.get(Folder_.folderId), folderId);
		subQuery.select(subQueryRoot.get("userId"));
		subQuery.where(subQueryPredicate);
		
		return criteriaBuilder.not(criteriaBuilder.in(path).value(subQuery));

	}

}
