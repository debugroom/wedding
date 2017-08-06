package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class Information implements Serializable{

	private static final long serialVersionUID = -6236716116260709098L;

	public Information(){
	}
	
	private String infoId;
	private String infoName;
	private String infoPagePath;
	private String title;
	private Date registratedDate;
	private Date releaseDate;
	private List<User> checkedUsers;
	private List<User> users;
	
}
