package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User implements Serializable{

	private static final long serialVersionUID = -7918649882435890290L;

	public User(){
	}

	private String userId;
	private Integer authorityLevel;
	private String lastName;
	private String firstName;
	private String imageFilePath;
	private String loginId;
	private boolean isBrideSide;
	private Timestamp lastLoginDate;
	private Timestamp lastUpdatedDate;
	private Address address;
	private List<Credential> credentials;
	private List<Email> emails;
	private List<Group> grps;
	
}
