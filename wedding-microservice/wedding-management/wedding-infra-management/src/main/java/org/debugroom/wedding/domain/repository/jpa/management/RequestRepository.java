package org.debugroom.wedding.domain.repository.jpa.management;

import org.debugroom.wedding.domain.entity.management.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, String>{
	
	public Request findTopByOrderByRequestIdDesc();

}
