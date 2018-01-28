package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InformationDraft implements Serializable{

	private static final long serialVersionUID = -8341455076099990931L;

	public InformationDraft(){
	}
	
	private Information information;
	private String infoName;
	private String tempInfoUrl;
	private List<User> viewUsers;
	private List<User> excludeUsers;
	
}
