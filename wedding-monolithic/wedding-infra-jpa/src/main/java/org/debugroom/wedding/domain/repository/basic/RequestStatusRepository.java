package org.debugroom.wedding.domain.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.debugroom.wedding.domain.model.entity.RequestStatus;
import org.debugroom.wedding.domain.model.entity.RequestStatusPK;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, RequestStatusPK>{

	@Modifying
	@Transactional
	@Query("UPDATE RequestStatus r SET r.isAnswered = :isAnswered "
			+ "WHERE r.id.requestId = :requestId")
	public void updateIsAnsweredByRequestId(@Param("isAnswered") boolean isAnswered,
			@Param("requestId") String requestId);
	
}
