package org.debugroom.wedding.domain.repository.cassandra.message;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.message.User;

public interface CassandraUserRepository extends CrudRepository<User, String>{
	
	public List<User> findByUserIdIn(List<String> userIds);

}
