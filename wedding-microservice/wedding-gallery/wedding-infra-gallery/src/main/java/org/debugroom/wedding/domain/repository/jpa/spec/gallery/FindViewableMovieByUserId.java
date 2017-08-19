package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import lombok.Data;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.entity.gallery.User_;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedUser;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedUser_;
import org.debugroom.wedding.domain.entity.gallery.Movie_;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindViewableMovieByUserId implements Specification<Movie>{

	private String userId;

	@Override
	public Predicate toPredicate(Root<Movie> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Movie, MovieRelatedUser> joinMovieRelatedUser = root.join(Movie_.movieRelatedUsers);
		Join<MovieRelatedUser, User> joinUser = joinMovieRelatedUser.join(MovieRelatedUser_.usr);
		return criteriaBuilder.equal(joinUser.get(User_.userId), userId);
	}
	
}
