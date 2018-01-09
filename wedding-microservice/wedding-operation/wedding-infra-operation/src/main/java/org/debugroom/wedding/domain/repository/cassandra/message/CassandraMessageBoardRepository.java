package org.debugroom.wedding.domain.repository.cassandra.message;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.message.MessageBoard;

public interface CassandraMessageBoardRepository extends CrudRepository<MessageBoard, Long>{
	
	@Query("select max(message_board_id) from message_board")
	public long findTopByOrderByMessageBoardId();

}
