package org.debugroom.wedding.app.web.common.model;

import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class LoginIdSearchResult {

	public LoginIdSearchResult(){}
	
	private String loginId;
	private List<String> messages;
	private boolean exists;

}
