package org.debugroom.wedding.app.model.portal;

import java.util.List;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.domain.entity.portal.Request;

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
