package org.debugroom.wedding.domain.model.portal;

import java.util.List;

import org.debugroom.wedding.domain.entity.Information;
import org.debugroom.wedding.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PortalInfoOutput {

	private User user;
	private List<Information> informationList;
	
	
}
