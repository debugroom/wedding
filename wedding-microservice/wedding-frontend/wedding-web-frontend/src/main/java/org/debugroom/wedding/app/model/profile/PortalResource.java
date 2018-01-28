package org.debugroom.wedding.app.model.profile;

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

}

