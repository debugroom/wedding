package org.debugroom.wedding.domain.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import org.debugroom.wedding.domain.entity.Request;

public interface RequestRepository extends JpaRepository<Request, String>{
}
