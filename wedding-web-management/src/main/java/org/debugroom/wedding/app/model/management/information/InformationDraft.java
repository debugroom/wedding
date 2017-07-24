package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class InformationDraft implements Serializable {

	private static final long serialVersionUID = 2202362396260208157L;

	public InformationDraft(){
	}
	
	private Information information;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;

}
