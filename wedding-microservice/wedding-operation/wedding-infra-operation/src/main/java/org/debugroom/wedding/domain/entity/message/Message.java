package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@Table("message")
public class Message implements Serializable{

	private static final long serialVersionUID = -5541495173945343008L;

	public Message(){}
	
	@PrimaryKey
	private MessagePK messagepk;
	@Column("comment")
	private String comment;
	@Column("userId")
	private String userId;
	@Column("photo")
	private Photo photo;
	@Column("movie")
	private Movie movie;
	@Column("like_count")
	private int likeCount;
	@Column("ver")
	private int ver;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
	@Transient
	private User user;

}
