package org.debugroom.wedding.domain.repository.cassandra.message;

import java.util.Map;

import org.debugroom.wedding.domain.entity.message.User;

public interface UserRepositoryCustom {

	public Map<String, User> findAllForMap();

}
