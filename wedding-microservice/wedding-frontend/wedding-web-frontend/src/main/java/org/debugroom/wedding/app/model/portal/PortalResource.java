package org.debugroom.wedding.app.model.portal;

import java.util.List;

import org.debugroom.wedding.app.model.User;
import org.debugroom.wedding.app.model.portal.Information;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PortalResource {
	
	public PortalResource(){
	}

	private User user;
	private boolean isUnWatched;
	private List<Information> informationList;
	private boolean isNotAnswered;
	private List<Request> requestList;
	
}
