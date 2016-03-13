package org.debugroom.wedding.app.web.profile;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("profile.passwordEqualsValidator")
public class PasswordEqualsValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return EditProfileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		EditProfileForm form = (EditProfileForm)target;
		String password = form.getCredentials().get(0).getCredentialKey();
		String confirmPassword = form.getCredentials().get(1).getCredentialKey();
	
		if(!password.equals(confirmPassword)){
			errors.rejectValue("credentials[0].credentialKey", 
				"PasswordEqualsValidator.newUserForm.credentialKey",
				"password and confirm password must be same.");
		}
	}

}
