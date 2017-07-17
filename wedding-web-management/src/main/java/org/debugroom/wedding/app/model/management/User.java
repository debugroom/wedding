package org.debugroom.wedding.app.model.management;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class User implements Serializable{

	private static final long serialVersionUID = 1705933002811013690L;

	public User(){
	}
	
	private String userId;
	private String lastName;
	private String firstName;
	private String imageFilePath;
	private String loginId;
	private Integer authorityLevel;
	private boolean isBrideSide;
	private Address address;
	private List<Email> emails;
	private List<Credential> credentials;
	
}
