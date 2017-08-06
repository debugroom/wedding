package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class LoginIdSearchResult implements Serializable{
	
	private static final long serialVersionUID = 7476414350063654790L;

	public LoginIdSearchResult(){
	}

	private String loginId;
	private List<String> messages;
	private boolean exists;
	
}
