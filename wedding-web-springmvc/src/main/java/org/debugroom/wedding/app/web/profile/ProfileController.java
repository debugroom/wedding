package org.debugroom.wedding.app.web.profile;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;
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
	Mapper mapper;
	
	@Inject
	@Named("profileImageUploadHelper")
	FileUploadHelper fileUploadHelper;
	
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
	public String editProfile(@Validated EditProfileForm editProfileForm,
				Errors errors, Model model){
		if(errors.hasErrors()){
			return "profile";
		}
		try {
			User user = mapper.map(editProfileForm, User.class);
			if(null != editProfileForm.getNewImageFile()){
				user.setImageFilePath(fileUploadHelper.saveFile(
					editProfileForm.getNewImageFile(), editProfileForm.getUserId()));
			}
			model.addAttribute(profileManagementService.updateUserProfile(user));
		} catch (MappingException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return "profile/edit";
	}

}
