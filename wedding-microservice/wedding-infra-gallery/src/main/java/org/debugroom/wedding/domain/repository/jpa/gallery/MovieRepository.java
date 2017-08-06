package org.debugroom.wedding.domain.repository.jpa.gallery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.gallery.Movie;

public interface MovieRepository extends JpaRepository<Movie, String>{

	public List<Movie> findByMovieIdIn(List<String> movieIds);
	
}
