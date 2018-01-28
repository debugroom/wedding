package org.debugroom.wedding.app.model.management.information;

import java.util.List;

import lombok.Data;

@Data
public class UpdateInformationResult {

	private List<String> updateParamList;
	private InformationDetail beforeEntity;
	private String beforeMessageBody;
	private InformationDetail afterEntity;
	private String afterMessageBody;
	
}
