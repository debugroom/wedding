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
	
	public UserSearchCriteria(){
	}

	@Size(min = 8, max = 8)
	@Pattern(regexp = "[0-9]*")
	private String infoId;
	@Size(min = 4, max = 4)
	@Pattern(regexp = "[0-9]*")
	private String requestId;
	@Size(min = 12, max = 12)
	@Pattern(regexp = "[0-9]*")
	private String folderId;
	@NotNull
	@Size(min=1, max=1024)
	@Pattern(regexp = "[-¥.¥_¥/a-zA-Z0-9]*")
	private String type;

}
