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
@Table("group_related_message_board")
public class GroupRelatedMessageBoard implements Serializable {

	private static final long serialVersionUID = -1571998926315222748L;

	public GroupRelatedMessageBoard(){}
	
	@PrimaryKey
	private GroupRelatedMessageBoardPK groupRelatedMessageBoardpk;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;

}
