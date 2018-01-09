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
public class GroupRelatedMessageBoardPK implements Serializable{

	private static final long serialVersionUID = -4585544845115836907L;

	public GroupRelatedMessageBoardPK(){}
	
    @PrimaryKeyColumn(name = "group_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long groupId;
    @PrimaryKeyColumn(name = "message_board_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long messageBoardId;
	
}
