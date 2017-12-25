package org.debugroom.wedding.domain.repository.jpa.operation;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.operation.RequestStatus;
import org.debugroom.wedding.domain.entity.operation.RequestStatusPK;

public interface RequestStatusRepository 
	extends JpaRepository<RequestStatus, RequestStatusPK>{
}
