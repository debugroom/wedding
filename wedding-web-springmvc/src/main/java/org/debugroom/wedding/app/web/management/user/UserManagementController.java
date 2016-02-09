package org.debugroom.wedding.app.web.management.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.debugroom.wedding.app.web.management.user.EditUserForm.GetUser;
import org.debugroom.wedding.app.web.management.user.EditUserForm.UpdateUser;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.service.management.UserManagementService;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class UserManagementController {

	@ModelAttribute
	public EditUserForm setUpForm(){
		return EditUserForm.builder().build();
	}

	@Inject
	UserManagementService userManagementService;
	
	@Inject
	Mapper mapper;
	
	@RequestMapping(method = RequestMethod.GET, value="/management/user/portal")
	public String userManagementPortal(@PageableDefault (
										page=0,
										size=5,
										direction = Direction.ASC,
										sort = {"userId"}
										) Pageable pageable, Model model){
		model.addAttribute("page", userManagementService.getUsersUsingPage(pageable));
		return "management/user/portal";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/user/{userId}")
	public String getUser(@Validated(GetUser.class) EditUserForm editUserForm, 
								Errors errors, Model model){

		if(errors.hasErrors()){
			return "management/user/portal";
		}
		try {
			model.addAttribute(userManagementService.getUserProfile(
					editUserForm.getUserId()));
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return "management/user/edit";
	}

	@RequestMapping(method = RequestMethod.PUT, value="/management/user/{userId}")
	public String updateUser(@Validated(UpdateUser.class) EditUserForm editUserForm,
								Errors errors, Model model){
		if(errors.hasErrors()){
			return "management/user/edit";
		}
		
		return "management/user/result";
	}
}
