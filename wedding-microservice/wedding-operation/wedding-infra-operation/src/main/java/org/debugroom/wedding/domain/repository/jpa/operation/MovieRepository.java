package org.debugroom.wedding.domain.repository.jpa.operation;

import org.debugroom.wedding.domain.entity.operation.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String>{

}
