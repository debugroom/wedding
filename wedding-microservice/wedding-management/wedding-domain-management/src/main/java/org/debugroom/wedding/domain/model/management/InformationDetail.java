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
public class InformationDetail implements Serializable{

	private static final long serialVersionUID = 6248987058678677481L;

	public InformationDetail(){
	}

	private Information information;
	private List<User> accessedUsers;
	private List<User> noAccessedUsers;
	

}
