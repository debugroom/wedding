package org.debugroom.wedding.app.model.management.information;

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

	private static final long serialVersionUID = -3351063819219134063L;

	public InformationDetail(){
	}

	private Information information;
	private String messageBody;
	private List<User> checkedAddUsers;
	private List<User> checkedDeleteUsers;
	private List<User> accessedUsers;
	private List<User> noAccessedUsers;
	private String messageBodyUrl;
	private String noAccessedUsersUrl;

}
