package org.debugroom.wedding.app.web.management.user;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;
import org.debugroom.wedding.app.web.common.external.AddressSearchCriteria;
import org.debugroom.wedding.app.web.common.external.AddressSearchCriteria.SearchAddress;
import org.debugroom.wedding.app.web.common.external.AddressSearchResult;
import org.debugroom.wedding.app.web.common.model.LoginIdSearchCriteria;
import org.debugroom.wedding.app.web.common.model.LoginIdSearchResult;
import org.debugroom.wedding.app.web.management.user.EditUserForm.GetUser;
import org.debugroom.wedding.app.web.management.user.EditUserForm.UpdateUser;
import org.debugroom.wedding.app.web.management.user.NewUserForm.ConfirmUser;
import org.debugroom.wedding.app.web.management.user.NewUserForm.SaveUser;
import org.debugroom.wedding.common.external.api.AddressSearch;
import org.debugroom.wedding.domain.management.service.UserManagementService;
import org.debugroom.wedding.domain.model.entity.User;

/**
 * 
 * @author org.debugroom
 *
 */
@Controller
public class UserManagementController {

	@ModelAttribute
	public EditUserForm setUpEditUserForm(){
		return EditUserForm.builder().build();
	}

	@ModelAttribute
	public NewUserForm setUpNewUserForm(){
		return NewUserForm.builder().build();
	}

	@Inject
	UserManagementService userManagementService;
	
	@Inject
	AddressSearch addressSearch;

	@Inject
	@Named("profileImageUploadHelper")
	FileUploadHelper fileUploadHelper;

	@Inject
	Mapper mapper;
	
	@Inject
	MessageSource messageSource;
	
	@Inject
	@Named("management.user.passwordEqualsValidator")
	PasswordEqualsValidator passwordEqualsValidator;
	
	@InitBinder(value = {"editUserForm", "newUserForm"})
	public void initBinder(WebDataBinder binder){
		binder.addValidators(passwordEqualsValidator);
	}
	
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
			model.addAttribute("errorCode", e.getCode());
		}

		if("update".equals(editUserForm.getType())){
			return "management/user/edit";
		}else{
			return "management/user/delete";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/user/{userId}")
	public String updateUser(@Validated(UpdateUser.class) EditUserForm editUserForm,
								BindingResult bindingResult, Model model){

		User user = mapper.map(editUserForm, User.class);
		if(bindingResult.hasErrors()){
			model.addAttribute(user);
			model.addAttribute(BindingResult.class.getName() + ".user", bindingResult);
			return "management/user/edit";
		}
		try {
			model.addAttribute(userManagementService.updateUser(user));
		} catch (BusinessException e) {
			model.addAttribute("errorCode", e.getCode());
			return "management/user/edit";
		}
		return "management/user/result";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/management/user/new")
	public String newUserForm(Model model){
		return "management/user/form";
	}

	@RequestMapping(method = RequestMethod.GET, value="/user", params="loginId")
	public ResponseEntity<LoginIdSearchResult> exists(
			@Validated LoginIdSearchCriteria loginIdSearchCriteria,
			Errors errors, Locale locale){
		
		String loginId = loginIdSearchCriteria.getLoginId();
		LoginIdSearchResult loginIdSearchResult = LoginIdSearchResult.builder()
													.loginId(loginId).build();

		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			loginIdSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginIdSearchResult);
		}
		
		if(userManagementService.existsUser(loginId)){
			loginIdSearchResult.setExists(true);
		}
		return ResponseEntity.status(HttpStatus.OK).body(loginIdSearchResult);
	}

	
	@RequestMapping(method = RequestMethod.GET, value="/address")
	@ResponseBody
	public ResponseEntity<AddressSearchResult> getAddress(@Validated(SearchAddress.class) 
				AddressSearchCriteria addressSearchCriteria, Errors errors, Locale locale) {
		
		AddressSearchResult addressSearchResult = AddressSearchResult.builder().build();
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			addressSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressSearchResult);
		}else{
			try{
				addressSearchResult.setAddress(
						addressSearch.getAddress(addressSearchCriteria.getZipCode()));
			}catch(BusinessException e){
				List<String> messages = new ArrayList<String>();
				addressSearchResult.setMessages(messages);
				messages.add(messageSource.getMessage(e.getCode(), e.getArgs(), locale));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressSearchResult);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(addressSearchResult);
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/user/profile/new")
	public String newUserProfile(@Validated(ConfirmUser.class) NewUserForm newUserForm,
			BindingResult bindingResult, Model model, Locale locale){
		
		User user = mapper.map(newUserForm, User.class);

		if(bindingResult.hasErrors()){
			model.addAttribute(user);
			model.addAttribute(BindingResult.class.getName() + ".user", bindingResult);
			return "management/user/form";
		}

		try {
			model.addAttribute(userManagementService.createUserProfile(user));
			user.setImageFilePath(fileUploadHelper.saveFile(
					newUserForm.getNewImageFile(), user.getUserId()));
		} catch (BusinessException e) {
			model.addAttribute("errorMessage", 
					messageSource.getMessage(e.getCode(), e.getArgs(), locale));
			return "management/user/form";
		}
		return "management/user/confirm";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/management/user/new/{userId}")
	public String saveUser(@Validated(SaveUser.class) NewUserForm newUserForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes){

		User user = mapper.map(newUserForm, User.class);

		if(bindingResult.hasErrors()){
			model.addAttribute(user);
			model.addAttribute(BindingResult.class.getName() + ".user", bindingResult);
			return "management/user/confirm";
		}

		if(!"save".equals(newUserForm.getType())){
			model.addAttribute(user);
			return "management/user/form";
		}
		
		try {
			redirectAttributes.addFlashAttribute(userManagementService.saveUser(user));
		} catch (BusinessException e) {
			model.addAttribute("errorCode", e.getCode());
			return "common/error";
		}

		return new StringBuilder().append("redirect:")
									.append(user.getUserId())
									.append("?complete")
									.toString();
	}

	@RequestMapping(method = RequestMethod.GET, 
			value = "/management/user/new/{userId}",
			params = "complete")
	public String saveComplete(){
		return "management/user/saveComplete";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/management/user/delete/{userId}")
	public String deleteConfirm(@Validated DeleteUserForm deleteUserForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		
		if(!"delete".equals(deleteUserForm.getType())){
			return "redirect:/management/user/portal";
		}
		
		redirectAttributes.addFlashAttribute(
				userManagementService.deleteUser(
						deleteUserForm.getUserId()));

		return new StringBuilder().append("redirect:")
				.append(deleteUserForm.getUserId())
				.append("?complete")
				.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, 
			value = "/management/user/delete/{userId}",
			params = "complete")
	public String deleteComplete(){
		return "management/user/deleteComplete";
	}

}

