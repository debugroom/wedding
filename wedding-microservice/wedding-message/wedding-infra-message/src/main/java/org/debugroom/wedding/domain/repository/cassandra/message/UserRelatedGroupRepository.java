package org.debugroom.wedding.domain.repository.cassandra.message;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.debugroom.wedding.domain.entity.message.UserRelatedGroup;
import org.debugroom.wedding.domain.entity.message.UserRelatedGroupPK;

public interface UserRelatedGroupRepository extends 
					CrudRepository<UserRelatedGroup, UserRelatedGroupPK>{

	public List<UserRelatedGroup> findByUserRelatedGrouppkUserId(String userId);
	
}
