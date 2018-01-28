package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@PrimaryKeyClass
public class MessageBoardRelatedGroupPK implements Serializable{

	private static final long serialVersionUID = -7764053164909051929L;

	public MessageBoardRelatedGroupPK(){}

    @PrimaryKeyColumn(name = "group_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private Long groupId;
    @PrimaryKeyColumn(name = "message_board_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long messageBoardId;
	
}
