package org.debugroom.wedding.domain.repository.jpa.spec.management;

import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.management.RequestStatus;
import org.debugroom.wedding.domain.entity.management.RequestStatus_;
import org.debugroom.wedding.domain.entity.management.Request_;
import org.debugroom.wedding.domain.entity.management.User;
import org.debugroom.wedding.domain.entity.management.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

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
		Subquery<User> subquery = criteriaBuilder.createQuery().subquery(User.class);
		Root<User> subqueryRoot = subquery.from(User.class);
		Join<User, RequestStatus> subqueryJoinRequestStatus = 
				subqueryRoot.join(User_.requestStatuses);
		Join<RequestStatus, Request> subqueryJoinRequest = 
				subqueryJoinRequestStatus.join(RequestStatus_.request);
		Predicate subqueryPredicate = criteriaBuilder.equal(
				subqueryJoinRequest.get(Request_.requestId), requestId);
		subquery.select(subqueryRoot.get("userId"));
		subquery.where(subqueryPredicate);
		
		return criteriaBuilder.not(criteriaBuilder.in(path).value(subquery));

	}

}
