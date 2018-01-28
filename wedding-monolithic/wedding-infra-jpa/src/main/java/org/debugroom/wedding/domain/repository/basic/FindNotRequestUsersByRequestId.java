package org.debugroom.wedding.domain.repository.basic;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.User_;
import org.debugroom.wedding.domain.model.entity.Request;
import org.debugroom.wedding.domain.model.entity.Request_;
import org.debugroom.wedding.domain.model.entity.RequestStatus;
import org.debugroom.wedding.domain.model.entity.RequestStatus_;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindNotRequestUsersByRequestId implements Specification<User>{

	private String requestId;

	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		Path<Object> path = root.get("userId");
		Subquery<User> subQuery = criteriaBuilder.createQuery().subquery(User.class);
		Root<User> subQueryRoot = subQuery.from(User.class);
		Join<User, RequestStatus> subQueryJoinRequestStatus = subQueryRoot.join(User_.requestStatuses);
		Join<RequestStatus, Request> subQueryJoinRequest = subQueryJoinRequestStatus.join(RequestStatus_.request);
		Predicate subQueryPredicate = criteriaBuilder.equal(subQueryJoinRequest.get(Request_.requestId), requestId);
		subQuery.select(subQueryRoot.get("userId"));
		subQuery.where(subQueryPredicate);
		
		return criteriaBuilder.not(criteriaBuilder.in(path).value(subQuery));
	}
	
}
