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
@Table("message_board_related_group")
public class MessageBoardRelatedGroup implements Serializable{

	private static final long serialVersionUID = 7318147032538008555L;

	public MessageBoardRelatedGroup(){
	}

	@PrimaryKey
	private MessageBoardRelatedGroupPK messageBoardRelatedGrouppk;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
	
}
