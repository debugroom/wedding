package org.debugroom.wedding.domain.repository.cassandra.message;

import java.util.List;

import org.debugroom.wedding.domain.entity.message.GroupRelatedUser;
import org.debugroom.wedding.domain.entity.message.GroupRelatedUserPK;
import org.springframework.data.repository.CrudRepository;

public interface CassandraGroupRelatedUserRepository 
			extends CrudRepository<GroupRelatedUser, GroupRelatedUserPK>{

	public List<GroupRelatedUser> findByGroupRelatedUserpkGroupId(Long groupId);

}
