package org.debugroom.wedding.domain.repository.jpa.spec.management;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.debugroom.wedding.domain.entity.management.Request;
import org.debugroom.wedding.domain.entity.management.RequestStatus;
import org.debugroom.wedding.domain.entity.management.RequestStatus_;
import org.debugroom.wedding.domain.entity.management.Request_;
import org.debugroom.wedding.domain.entity.management.User;
import org.debugroom.wedding.domain.entity.management.User_;
import org.springframework.data.jpa.domain.Specification;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindUsersByRequestIdAndIsApproved implements Specification<User>{

	private String requestId;
	private boolean isApproved;
	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Join<User, RequestStatus> joinRequestStatus = root.join(User_.requestStatuses);
		predicates.add(criteriaBuilder.equal(
				joinRequestStatus.get(RequestStatus_.isApproved), isApproved));
		Join<RequestStatus, Request> joinRequest = 
				joinRequestStatus.join(RequestStatus_.request);
		predicates.add(criteriaBuilder.equal(joinRequest.get(Request_.requestId), requestId));

		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

	}
	
	
}
