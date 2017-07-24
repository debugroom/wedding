package org.debugroom.wedding.app.model.profile;

import java.util.List;

import lombok.Data;

@Data
public class UpdateUserResult {

	private List<String> updateParamList;
	private User beforeEntity;
	private User afterEntity;
	
}
