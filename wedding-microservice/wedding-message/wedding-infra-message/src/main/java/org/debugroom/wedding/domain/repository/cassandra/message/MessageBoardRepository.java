package org.debugroom.wedding.domain.repository.cassandra.message;

import org.springframework.data.repository.CrudRepository;

import org.debugroom.wedding.domain.entity.message.MessageBoard;

public interface MessageBoardRepository extends CrudRepository<MessageBoard, Long>{
}
