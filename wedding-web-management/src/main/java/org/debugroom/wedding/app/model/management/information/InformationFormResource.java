package org.debugroom.wedding.app.model.management.information;

import java.util.List;

import org.debugroom.wedding.domain.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class InformationFormResource {

	public InformationFormResource(){
	}
	
	private List<User> users;
	
}
