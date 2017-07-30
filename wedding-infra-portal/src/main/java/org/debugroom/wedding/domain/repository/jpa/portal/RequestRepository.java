package org.debugroom.wedding.domain.repository.jpa.portal;

import org.debugroom.wedding.domain.entity.portal.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, String>{
}
