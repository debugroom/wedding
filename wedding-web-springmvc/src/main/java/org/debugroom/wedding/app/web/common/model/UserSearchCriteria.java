package org.debugroom.wedding.app.web.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserSearchCriteria implements Serializable{

	private static final long serialVersionUID = 6558984504193422804L;

	public static interface GetNotInfomationViewers{}
	public static interface GetNotRequestUsers{}
	
	@NotNull(groups = {GetNotInfomationViewers.class})
	@Size(min = 8, max = 8, groups={GetNotInfomationViewers.class})
	@Pattern(regexp = "[0-9]*", groups = {GetNotInfomationViewers.class})
	private String infoId;
	
	@NotNull(groups = {GetNotRequestUsers.class})
	@Size(min = 4, max = 4, groups={GetNotRequestUsers.class})
	@Pattern(regexp = "[0-9]*", groups = {GetNotRequestUsers.class})
	private String requestId;
	
}
