package org.debugroom.wedding.app.web.validator;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import org.debugroom.wedding.app.model.management.EditUserForm;
import org.debugroom.wedding.app.model.management.EditUserForm.UpdateUser;
import org.debugroom.wedding.app.model.management.NewUserForm;
import org.debugroom.wedding.app.model.management.NewUserForm.ConfirmUser;
import org.debugroom.wedding.app.model.management.NewUserForm.SaveUser;

@Component
public class PasswordEqualsValidator implements SmartValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return EditUserForm.class.isAssignableFrom(clazz) 
				|| NewUserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		validate(target, errors, new Object[]{});
	}

	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		
		if(ArrayUtils.contains(validationHints, ConfirmUser.class)
				|| ArrayUtils.contains(validationHints, SaveUser.class)){
			
			NewUserForm form = (NewUserForm)target;
			String password = form.getCredentials().get(0).getCredentialKey();
			String confirmPassword = form.getCredentials().get(1).getCredentialKey();
			
			if(!password.equals(confirmPassword)){
				errors.rejectValue("credentials[0].credentialKey", 
					"org.debugroom.wedding.app.web.validator.PasswordEqualsValidator",
					"password and confirm password must be same.");
			}

		}
		
		if(ArrayUtils.contains(validationHints, UpdateUser.class)){
			EditUserForm form = (EditUserForm)target;
			String password = form.getCredentials().get(0).getCredentialKey();
			String confirmPassword = form.getCredentials().get(1).getCredentialKey();
			
			if(!password.equals(confirmPassword)){
				errors.rejectValue("credentials[0].credentialKey", 
					"org.debugroom.wedding.app.web.validator.PasswordEqualsValidator",
					"password and confirm password must be same.");
			}
			
		}
	}

}
