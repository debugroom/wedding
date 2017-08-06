package org.debugroom.wedding.domain.repository.jpa.spec;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.Information_;
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
public class FindUsersByInfoIdAndIsAccessed implements Specification<User>{

	private String infoId;
	private boolean isAccessed;
	@Override
	public Predicate toPredicate(Root<User> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Join<User, Notification> joinNotification = root.join(User_.notifications);
		predicates.add(criteriaBuilder.equal(
				joinNotification.get(Notification_.isAccessed), isAccessed));
		Join<Notification, Information> joinInformation =
				joinNotification.join(Notification_.information);
		predicates.add(criteriaBuilder.equal(joinInformation.get(Information_.infoId), infoId));

		return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
	}
	
}
