package org.debugroom.wedding.domain.basic.model;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.Infomation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author org.debugroom
 *
 */
@AllArgsConstructor
@Data
@Builder
public class PortalInfoOutput {

	private User user;
	private List<Infomation> infomationList;

	
}
