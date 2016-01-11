package org.debugroom.wedding.domain.repository.basic;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.debugroom.wedding.domain.model.entity.Infomation;

public interface InfomationRepository extends JpaRepository<Infomation, String>{

	@Query("SELECT i FROM Infomation i, User u, Notification n "
			+ "WHERE u.userId = :userId "
			+ "and u.userId = n.id.userId "
			+ "and n.id.infoId = i.infoId "
			+ "and i.releaseDate < :releaseDate "
			+ "ORDER BY i.infoId")
	public List<Infomation> findUserByUserIdAndReleaseDateLessThan(
			@Param("userId") String userId, @Param("releaseDate") Date releaseDate);

}
