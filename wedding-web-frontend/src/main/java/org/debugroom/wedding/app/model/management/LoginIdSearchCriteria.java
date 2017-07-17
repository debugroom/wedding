package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class LoginIdSearchCriteria implements Serializable{

	private static final long serialVersionUID = -5307440807788175376L;

	public LoginIdSearchCriteria(){
	}

	@NotNull
	@Size(min = 1, max = 32)
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String loginId;

}
