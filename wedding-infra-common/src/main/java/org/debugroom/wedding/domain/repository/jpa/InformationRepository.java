package org.debugroom.wedding.domain.repository.jpa;

import org.debugroom.wedding.domain.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InformationRepository extends JpaRepository<Information, String>{

	@Query("SELECT i FROM Information i, User u, Notification n "
			+ "WHERE u.userId = :userId "
			+ "and u.userId = n.id.userId "
			+ "and n.id.infoId = i.infoId "
			+ "and i.releaseDate < :releaseDate "
			+ "ORDER BY i.infoId")
	public List<Information> findUserByUserIdAndReleaseDateLessThan(
			@Param("userId") String userId, @Param("releaseDate") Date releaseDate);
	
}
