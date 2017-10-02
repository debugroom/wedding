package org.debugroom.wedding.domain.repository.cassandra.message;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessagePK;

public interface MessageRepository extends CrudRepository<Message, MessagePK>{
	
	public List<Message> findByMessagepkMessageBoardId(Long messageBoardId);

}
