package org.debugroom.wedding.app.web.profile;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.profile.ProfileManagementService;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class ProfileController {

	@ModelAttribute
	public EditProfileForm setUpForm(){
		return EditProfileForm.builder().build();
	}
	@Inject
	ProfileManagementService profileManagementService;
	
	@RequestMapping(method = RequestMethod.GET, value="/profile")
	public String profilePortal(Model model){
		
		try {
			model.addAttribute(profileManagementService.getUserProfile("00000001"));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return "profile/portal";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/profile/edit")
	public String editProfile(EditProfileForm editProfileForm){
		editProfileForm.toString();
		return "profile/edit";
	}
	

}
