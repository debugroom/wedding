package org.debugroom.wedding.domain.repository.jpa.spec.gallery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Folder_;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedFolder_;
import org.debugroom.wedding.domain.entity.gallery.Movie_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class FindRelatedMovieByFolderId implements Specification<Movie>{
	
	private String folderId;

	@Override
	public Predicate toPredicate(Root<Movie> root, 
			CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Join<Movie, MovieRelatedFolder> joinMovieRelatedFolder =
				root.join(Movie_.movieRelatedFolders);
		Join<MovieRelatedFolder, Folder> joinFolder =
				joinMovieRelatedFolder.join(MovieRelatedFolder_.folder);
		return criteriaBuilder.equal(joinFolder.get(Folder_.folderId), folderId);
	}

}
