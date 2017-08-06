package org.debugroom.wedding.domain.repository.basic;

import org.debugroom.wedding.domain.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, String>{
}
