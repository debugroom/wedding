package org.debugroom.wedding.app.model.management.request;

import java.util.List;

import lombok.Data;

@Data
public class UpdateRequestResult {

	private List<String> updateParamList;
	private RequestDetail beforeEntity;
	private RequestDetail afterEntity;
	
}
