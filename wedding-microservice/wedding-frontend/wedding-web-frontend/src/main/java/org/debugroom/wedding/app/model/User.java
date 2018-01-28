package org.debugroom.wedding.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = 972466690567753059L;

	public User(){
	}
	
	private String userId;
	private Integer authorityLevel;
	private boolean isBrideSide;
	private String imageFilePath;
	private Timestamp lastLoginDate;
	private Timestamp lastUpdatedDate;
	private String loginId;
	private String firstName;
	private String lastName;
	
}
