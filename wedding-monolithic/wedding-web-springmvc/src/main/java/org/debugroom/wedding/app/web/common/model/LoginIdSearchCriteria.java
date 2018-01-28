package org.debugroom.wedding.app.web.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginIdSearchCriteria implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 32)
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String loginId;

}
