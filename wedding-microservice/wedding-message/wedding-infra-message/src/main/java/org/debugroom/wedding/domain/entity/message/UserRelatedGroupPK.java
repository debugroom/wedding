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
public class UserRelatedGroupPK implements Serializable{

	private static final long serialVersionUID = 5590021774410746604L;

	public UserRelatedGroupPK(){}
	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;
    @PrimaryKeyColumn(name = "group_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long groupId;

}
