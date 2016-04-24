package org.debugroom.wedding.app.web.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserSearchCriteria implements Serializable{

	private static final long serialVersionUID = 6558984504193422804L;

	@NotNull
	@Size(min = 8, max = 8)
	@Pattern(regexp = "[0-9]*")
	private String infoId;
	
}
