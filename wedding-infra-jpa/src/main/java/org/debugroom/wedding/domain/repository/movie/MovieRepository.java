package org.debugroom.wedding.domain.repository.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.model.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, String>{
}
