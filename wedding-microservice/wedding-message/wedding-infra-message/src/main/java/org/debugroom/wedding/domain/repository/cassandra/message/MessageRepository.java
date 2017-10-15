package org.debugroom.wedding.domain.repository.cassandra.message;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.message.Message;
import org.debugroom.wedding.domain.entity.message.MessagePK;

public interface MessageRepository extends CrudRepository<Message, MessagePK>{
	
	public List<Message> findByMessagepkMessageBoardId(Long messageBoardId);

	@Query("select max(message_no) from message where message_board_id = ?0")
	public Long findTopByMessageBoardIdOrderByMeesageNo(Long messageBoardId);

	@Query("select count(message_no) from message where message_board_id = ?0")
	public Long countByMessagepkMessageBoardId(Long messageBoardId);

}
