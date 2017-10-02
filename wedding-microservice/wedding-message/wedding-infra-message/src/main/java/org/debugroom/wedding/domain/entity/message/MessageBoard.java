package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Table("message_board")
public class MessageBoard implements Serializable{

	private static final long serialVersionUID = 1404769610062864757L;

	public MessageBoard(){
	}
	
	@PrimaryKey("message_board_id")
	private Long messageBoardId;
	@Column("title")
	private String title;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
	@Transient
	private Group group;
	
}
