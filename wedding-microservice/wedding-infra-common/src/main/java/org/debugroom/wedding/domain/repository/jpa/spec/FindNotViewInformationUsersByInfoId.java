package org.debugroom.wedding.domain.repository.jpa.spec;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.entity.Notification_;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.User_;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindNotViewInformationUsersByInfoId implements Specification<User>{

	private String infoId;

	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Path<Object> path = root.get("userId");
		Subquery<User> subQuery = criteriaBuilder.createQuery().subquery(User.class);
		Root<User> subQueryRoot = subQuery.from(User.class);
		Join<User, Notification> subQueryJoinNotification = subQueryRoot.join(User_.notifications);
		Join<Notification, Information> subQueryJoinInformation = 
				subQueryJoinNotification.join(Notification_.information);
		Predicate subQueryPredicate = criteriaBuilder.equal(
				subQueryJoinInformation.get("infoId"), infoId);
		subQuery.select(subQueryRoot.get("userId"));
		subQuery.where(subQueryPredicate);
		return criteriaBuilder.not(criteriaBuilder.in(path).value(subQuery));
	}
	
}
