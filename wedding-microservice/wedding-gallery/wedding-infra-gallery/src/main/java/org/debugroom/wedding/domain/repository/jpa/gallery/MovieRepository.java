package org.debugroom.wedding.domain.repository.jpa.gallery;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.debugroom.wedding.domain.entity.gallery.Movie;

public interface MovieRepository extends JpaRepository<Movie, String>, 
											JpaSpecificationExecutor<Movie>{

	@Override
	@Cacheable(cacheNames="movie")
	public Movie findOne(String movieId);

	public List<Movie> findByMovieIdIn(List<String> movieIds);
	
	public Movie findTopByMovieIdLikeOrderByMovieIdDesc(String movieId);

}
