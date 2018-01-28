package org.debugroom.wedding.domain.repository.cassandra.message;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.debugroom.wedding.domain.entity.message.MessageBoardRelatedGroup;
import org.debugroom.wedding.domain.entity.message.MessageBoardRelatedGroupPK;

public interface CassandraMessageBoardRelatedGroupRepository 
		extends CrudRepository<MessageBoardRelatedGroup, MessageBoardRelatedGroupPK>{

	public List<MessageBoardRelatedGroup> 
		findByMessageBoardRelatedGrouppkMessageBoardId(Long messageBoardId);

}
