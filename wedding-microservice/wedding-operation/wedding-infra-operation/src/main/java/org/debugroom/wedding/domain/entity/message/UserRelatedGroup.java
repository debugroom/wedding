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
@Table("user_related_group")
public class UserRelatedGroup implements Serializable{

	private static final long serialVersionUID = 4928856288719075908L;

	public UserRelatedGroup(){
	}
	
	@PrimaryKey("userRelatedGrouppk")
	private UserRelatedGroupPK userRelatedGrouppk;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;

	
}
