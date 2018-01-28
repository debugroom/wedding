package org.debugroom.wedding.app.model.management.user;

import java.util.List;

import org.debugroom.wedding.domain.entity.User;

import lombok.Data;

@Data
public class UpdateUserResult {

	private List<String> updateParamList;
	private User beforeEntity;
	private User afterEntity;
	
}

