package org.debugroom.wedding.domain.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.debugroom.wedding.domain.model.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	long countByUserId(String userId);
	
	long countByloginId(String loginId);

}
