package org.debugroom.wedding.app.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UserSearchCriteria implements Serializable{

	private static final long serialVersionUID = 3824408675656917687L;

	public static interface GetNotInformationViewers{}
	public static interface GetNotRequestUsers{}
	public static interface GetNotFolderUsers{}
	public static interface GetFolderUsers{}
	
	public UserSearchCriteria(){
	}

	@NotNull(groups = {GetNotInformationViewers.class})
	@Size(min = 8, max = 8, groups={GetNotInformationViewers.class})
	@Pattern(regexp = "[0-9]*", groups = {GetNotInformationViewers.class})
	private String infoId;
	
	@NotNull(groups = {GetNotRequestUsers.class})
	@Size(min = 4, max = 4, groups={GetNotRequestUsers.class})
	@Pattern(regexp = "[0-9]*", groups = {GetNotRequestUsers.class})
	private String requestId;
	
	@NotNull(groups = {GetNotFolderUsers.class, GetFolderUsers.class})
	@Size(min = 12, max = 12, groups={GetNotFolderUsers.class, GetFolderUsers.class})
	@Pattern(regexp = "[0-9]*", groups = {GetNotFolderUsers.class, GetFolderUsers.class})
	private String folderId;
	@NotNull
	@Size(min=1, max=1024)
	@Pattern(regexp = "[-¥.¥_¥/a-zA-Z0-9]*")
	private String type;

}
