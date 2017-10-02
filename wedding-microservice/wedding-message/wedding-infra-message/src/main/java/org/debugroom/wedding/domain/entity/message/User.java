package org.debugroom.wedding.domain.entity.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table("users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -843220492889190437L;

	public User(){
	}

	@PrimaryKey("user_id")
	private String userId;
	@Column("last_name")
	private String lastName;
	@Column("first_name")
	private String firstName;
	@Column("login_id")
	private String loginId;
	@Column("authority_level")
	private Integer authorityLevel;
	@Column("is_bride_side")
	private boolean isBrideSide;
	@Column("image_file_path")
	private String imageFilePath;
	@Column("last_login_date")
	private Date lastLoginDate;
	@Column("last_updated_date")
	private Date lastUpdatedDate;
	@Column("ver")
	private Integer ver;
	@Transient
	List<Group> groups;

}
