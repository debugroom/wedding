package org.debugroom.wedding.domain.model.management;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InformationDraft implements Serializable{

	private static final long serialVersionUID = 5902671256051360714L;

	public InformationDraft(){
	}
	
	private Information information;
	private String infoName;
	private List<User> viewUsers;
	private List<User> excludeUsers;
	
}
