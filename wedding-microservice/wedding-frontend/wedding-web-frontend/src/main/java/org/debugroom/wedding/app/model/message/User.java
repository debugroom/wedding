package org.debugroom.wedding.app.model.message;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{

	private static final long serialVersionUID = 1133792047736873294L;

	public User(){
	}

	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String userId;
	private String lastName;
	private String firstName;
	private String loginId;
	private Integer authorityLevel;
	private boolean isBrideSide;
	private String imageFilePath;
	private Date lastLoginDate;
	
}
