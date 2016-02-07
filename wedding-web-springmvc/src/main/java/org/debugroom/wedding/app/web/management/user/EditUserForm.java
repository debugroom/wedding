package org.debugroom.wedding.app.web.management.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditUserForm implements Serializable{

	private static final long serialVersionUID = -8949437312594560366L;
	
	@NotNull
	@Size
	@Pattern(regexp = "[0-9]*")
	private String userId;
	
}
