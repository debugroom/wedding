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
public class FindFolderUsersByFolderId implements Specification<User>{
	
	private String folderId;
	
	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		
		Join<User, UserRelatedFolder> joinUserRelatedFolder = 
				root.join(User_.userRelatedFolders);
		Join<UserRelatedFolder, Folder> joinFolder = 
				joinUserRelatedFolder.join(UserRelatedFolder_.folder);
		return criteriaBuilder.equal(joinFolder.get(Folder_.folderId), folderId);

	}

}
