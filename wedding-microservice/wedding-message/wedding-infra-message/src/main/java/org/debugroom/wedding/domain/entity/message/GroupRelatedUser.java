package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Table("group_related_user")
public class GroupRelatedUser implements Serializable{

	private static final long serialVersionUID = 3339956744780041779L;

	public GroupRelatedUser(){
	}
	
	@PrimaryKey("groupRelatedUserpk")
	private GroupRelatedUserPK groupRelatedUserpk;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
}
