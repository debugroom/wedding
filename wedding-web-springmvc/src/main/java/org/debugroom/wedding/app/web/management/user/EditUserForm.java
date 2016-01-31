package org.debugroom.wedding.app.web.management.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditUserForm {

	@NotNull
	@Size
	@Pattern(regexp = "[0-9]*")
	private String userId;
	
}
