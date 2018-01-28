package org.debugroom.wedding.domain.model.portal;

import java.util.List;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.portal.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PortalInfoOutput {

	public PortalInfoOutput(){
	}

	private User user;
	private boolean isUnWatched;
	private List<Information> informationList;
	private boolean isNotAnswered;
	private List<Request> requestList;
	
}
