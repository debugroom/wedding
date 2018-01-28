package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Table("group")
public class Group implements Serializable{

	private static final long serialVersionUID = 1717164973585521910L;

	public Group(){
	}

	@PrimaryKey("group_id")
	private Long groupId;
	@Column("group_name")
	private String groupName;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
	@Transient
	private List<User> users;
	
}
