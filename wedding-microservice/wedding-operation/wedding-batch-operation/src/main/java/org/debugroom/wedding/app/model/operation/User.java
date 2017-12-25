package org.debugroom.wedding.app.model.operation;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	public User(){
	}
	
	private String userId;
	private Integer authorityLevel;
	private boolean isBrideSide;
	private String imageFilePath;
	private Timestamp lastLoginDate;
	private Timestamp lastUpdateDate;
	private String loginId;
	private String firstName;
	private String lastname;
	private Integer ver;
	
}
