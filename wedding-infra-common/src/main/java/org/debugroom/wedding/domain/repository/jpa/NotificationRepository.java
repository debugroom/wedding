package org.debugroom.wedding.domain.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.wedding.domain.entity.Notification;
import org.debugroom.wedding.domain.entity.NotificationPK;

public interface NotificationRepository extends JpaRepository<Notification, NotificationPK>{

	public List<Notification> findByIdInfoIdOrderByIdUserIdAsc(String infoId);
	
	@Modifying
	@Transactional
	@Query("UPDATE Notification n SET n.isAccessed = :isAccessed "
			+ "WHERE n.id.infoId = :infoId")
	public void updateIsAccessedNyInfoId(@Param("isAccessed") boolean isAccessee,
			@Param("infoId") String infoId);

}
