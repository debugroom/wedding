package org.debugroom.wedding.domain.repository.jpa;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.debugroom.wedding.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String>, 
											JpaSpecificationExecutor<User>{
	
	@Cacheable(cacheNames="existUser")
	long countByUserId(String userId);
	
	long countByloginId(String loginId);

	@Query("SELECT u FROM User u, Information i, Notification n "
			+ "WHERE i.infoId = :infoId "
			+ "AND i.infoId = n.id.infoId "
			+ "AND n.id.userId = u.userId "
			+ "AND n.isAccessed = :isAccessed "
			+ "ORDER BY u.userId")
	public List<User> findUsersByInfoIdAndIsAccessed(
			@Param("infoId") String infoId, @Param("isAccessed") boolean isAccessed);
	
	public User findTopByOrderByUserIdDesc();

	public User findOneByLoginId(String loginId);

}
