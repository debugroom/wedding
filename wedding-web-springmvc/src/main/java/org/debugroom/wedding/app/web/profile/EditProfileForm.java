package org.debugroom.wedding.app.web.profile;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Address;
import org.debugroom.wedding.domain.model.entity.Email;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditProfileForm {

	private String userId;
	private String userName;
	private String loginId;
	private Address address;
	private List<Email> emails;
	private String updateParam;
	

}
