package org.debugroom.wedding.domain.repository.cassandra.message;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoard;
import org.debugroom.wedding.domain.entity.message.GroupRelatedMessageBoardPK;

public interface GroupRelatedMessageBoardRepository extends 
					CrudRepository<GroupRelatedMessageBoard, GroupRelatedMessageBoardPK>{

	public List<GroupRelatedMessageBoard> findByGroupRelatedMessageBoardpkGroupId(Long groupId);

}
