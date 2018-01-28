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
public class GroupRelatedUserPK implements Serializable{

	private static final long serialVersionUID = -8527392243276045044L;

	public GroupRelatedUserPK(){}
	
	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private String userId;
	@PrimaryKeyColumn(name = "group_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long groupId;
}
