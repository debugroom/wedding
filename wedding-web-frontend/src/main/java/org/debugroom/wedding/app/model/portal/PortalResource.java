package org.debugroom.wedding.app.model.portal;

import java.util.List;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;

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
	private List<Information> informationList;
	
}
