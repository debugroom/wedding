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
public class MessagePK implements Serializable{

	public MessagePK(){}
	
	private static final long serialVersionUID = 4834383696378189920L;

	@PrimaryKeyColumn(name = "message_board_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long messageBoardId;
	@PrimaryKeyColumn(name = "message_no", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private Long messageNo;
	
}
