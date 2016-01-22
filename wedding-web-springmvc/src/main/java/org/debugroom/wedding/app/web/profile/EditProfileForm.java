package org.debugroom.wedding.app.web.profile;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.web.common.model.Address;
import org.debugroom.wedding.app.web.common.model.Email;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditProfileForm {

	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]")
	private String userId;
	@NotNull
	@Size(min=1, max=50)
	private String userName;
	@NotNull
	@Size(min=1, max=32)
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String loginId;
	@NotNull
	private Address address;
	@NotNull
	private List<Email> emails;
	

}
