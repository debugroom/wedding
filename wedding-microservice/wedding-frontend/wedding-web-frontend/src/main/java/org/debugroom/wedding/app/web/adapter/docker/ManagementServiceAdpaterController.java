package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;
import org.debugroom.wedding.app.model.UserSearchCriteria;
import org.debugroom.wedding.app.model.UserSearchResult;
import org.debugroom.wedding.app.model.management.PageParam;
import org.debugroom.wedding.app.model.management.information.DeleteInformationForm;
import org.debugroom.wedding.app.model.management.information.Information.GetMessageBody;
import org.debugroom.wedding.app.model.management.information.Information.UpdateInformation;
import org.debugroom.wedding.app.model.management.information.InformationDetail;
import org.debugroom.wedding.app.model.management.information.InformationDetailForm;
import org.debugroom.wedding.app.model.management.information.InformationDraft;
import org.debugroom.wedding.app.model.management.information.InformationFormResource;
import org.debugroom.wedding.app.model.management.information.InformationPageImpl;
import org.debugroom.wedding.app.model.management.information.InformationResource;
import org.debugroom.wedding.app.model.management.information.UpdateInformationForm;
import org.debugroom.wedding.app.model.management.information.UpdateInformationResult;
import org.debugroom.wedding.app.model.management.information.NewInformationForm;
import org.debugroom.wedding.app.model.management.information.InformationDetailForm.GetInformation;
import org.debugroom.wedding.app.model.management.information.NewInformationForm.ConfirmInformation;
import org.debugroom.wedding.app.model.management.information.NewInformationForm.SaveInformation;
import org.debugroom.wedding.app.model.management.request.DeleteRequestForm;
import org.debugroom.wedding.app.model.management.request.NewRequestForm;
import org.debugroom.wedding.app.model.management.request.NewRequestForm.ConfirmRequest;
import org.debugroom.wedding.app.model.management.request.NewRequestForm.SaveRequest;
import org.debugroom.wedding.app.model.management.request.Request;
import org.debugroom.wedding.app.model.management.request.RequestDetail;
import org.debugroom.wedding.app.model.management.request.RequestDetailForm;
import org.debugroom.wedding.app.model.management.request.RequestDraft;
import org.debugroom.wedding.app.model.management.request.RequestFormResource;
import org.debugroom.wedding.app.model.management.request.RequestPageImpl;
import org.debugroom.wedding.app.model.management.request.UpdateRequestForm;
import org.debugroom.wedding.app.model.management.request.UpdateRequestResult;
import org.debugroom.wedding.app.model.management.user.AddressSearchCriteria;
import org.debugroom.wedding.app.model.management.user.AddressSearchResult;
import org.debugroom.wedding.app.model.management.user.DeleteUserForm;
import org.debugroom.wedding.app.model.management.user.EditUserForm;
import org.debugroom.wedding.app.model.management.user.ImageParam;
import org.debugroom.wedding.app.model.management.user.LoginIdSearchCriteria;
import org.debugroom.wedding.app.model.management.user.LoginIdSearchResult;
import org.debugroom.wedding.app.model.management.user.NewUserForm;
import org.debugroom.wedding.app.model.management.user.UserPageImpl;
import org.debugroom.wedding.app.model.management.user.AddressSearchCriteria.SearchAddress;
import org.debugroom.wedding.app.model.management.user.EditUserForm.GetUser;
import org.debugroom.wedding.app.model.management.user.EditUserForm.UpdateUser;
import org.debugroom.wedding.app.model.management.user.NewUserForm.ConfirmUser;
import org.debugroom.wedding.app.model.management.user.NewUserForm.SaveUser;
import org.debugroom.wedding.app.model.management.user.UpdateUserResult;
import org.debugroom.wedding.app.web.helper.InformationMessageBodyHelper;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.app.web.validator.PasswordEqualsValidator;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.external.api.AddressSearch;

@Controller
public class ManagementServiceAdpaterController {

	private static final String APP_NAME = "api/v1";

	@Value("${protocol}")
	private String protocol;
	
	@Value("${server.contextPath}")
	private String contextPath;
	
	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	@Named("profileImageUploadHelper")
	FileUploadHelper uploadHelper;
	
	@Inject
	ImageDownloadHelper downloadHelper;
	
	@Inject
	InformationMessageBodyHelper informationMessageBodyHelper;

	@Inject
	AddressSearch addressSearch;
	
	@Inject
	PasswordEqualsValidator passwordEqualsValidator;
	
	@Inject
	MessageSource messageSource;
	
	@Value("${info.root.directory}")
	private String infoRootDirectory;

	@ModelAttribute
	public NewUserForm setUpNewUserForm(){
		return NewUserForm.builder().build();
	}
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm(){
		return EditUserForm.builder().build();
	}

	@ModelAttribute
	public NewInformationForm setUpNewInformationForm(){
		return NewInformationForm.builder().build();
	}

	@ModelAttribute
	public InformationDetailForm setUpInformationDetailForm(){
		return InformationDetailForm.builder().build();
	}

	@ModelAttribute
	public NewRequestForm setUpNewRequestForm(){
		return NewRequestForm.builder().build();
	}

	@ModelAttribute
	public RequestDetailForm setUpRequestDetailForm(){
		return RequestDetailForm.builder().build();
	}

	@InitBinder(value={"editUserForm", "newUserForm"})
	public void initBinder(WebDataBinder binder){
		binder.addValidators(passwordEqualsValidator);
	}

	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/management/user/portal")
	public String userManagementPortal(@Validated PageParam pageParam,
			BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()){
			return "management/user/portal";
		}
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("page", Integer.toString(pageParam.getPage()));
	    params.set("size", Integer.toString(pageParam.getSize()));
		Page<User> page = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/users")
						.toString(), provider, params).toUri(), UserPageImpl.class);
		model.addAttribute("page", page);

		return "management/user/portal";

	}
	
	@RequestMapping(method = RequestMethod.GET, value="/management/user/new")
	public String managemanetUserForm(){
		return "/management/user/form";
	}

	@RequestMapping(method = RequestMethod.GET, value="/search/user")
	public ResponseEntity<LoginIdSearchResult> exists(
			@Validated LoginIdSearchCriteria loginIdSearchCriteria,
			Errors errors, Locale locale){
		
		String loginId = loginIdSearchCriteria.getLoginId();
		LoginIdSearchResult loginIdSearchResult = 
				LoginIdSearchResult.builder().loginId(loginId).build();
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			loginIdSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginIdSearchResult);
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
					new StringBuilder()
					.append(APP_NAME)
					.append("/users/{loginId}")
					.toString(), provider).expand(loginId).toUri(),
				User.class);
		if(user.getUserId() != null){
			loginIdSearchResult.setExists(true);
		}
		return ResponseEntity.status(HttpStatus.OK).body(loginIdSearchResult);
	}

	@RequestMapping(method = RequestMethod.GET, value="/address/{zipCode}")
	@ResponseBody
	public ResponseEntity<AddressSearchResult> getAddress(
			@Validated(SearchAddress.class) AddressSearchCriteria addressSearchCriteria, 
			Errors errors, Locale locale){
		
		AddressSearchResult addressSearchResult = AddressSearchResult.builder().build();
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			addressSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressSearchResult);
		}else{
			try {
				addressSearchResult.setAddress(
						addressSearch.getAddress(addressSearchCriteria.getZipCode()));
			} catch (BusinessException e) {
				List<String> messages = new ArrayList<String>();
				addressSearchResult.setMessages(messages);
				messages.add(messageSource.getMessage(e.getCode(), e.getArgs(), locale));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressSearchResult);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(addressSearchResult);
	}

	@RequestMapping(method = RequestMethod.GET, value="/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> image(@Validated ImageParam imageParam, 
			BindingResult bindingResult){
		BufferedImage image = null;
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		try {
			image = downloadHelper.getProfileImage(User.builder()
					.userId(imageParam.getUserId())
					.imageFilePath(imageParam.getImageFilePath())
					.build());
		} catch (BusinessException e) {
			ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/user/profile/new")
	public String newUserProfile(@Validated(ConfirmUser.class) NewUserForm newUserForm,
			BindingResult bindingResult, Model model, Locale locale){
		String serviceName = "management";
		User user = mapper.map(newUserForm, User.class);
		if(bindingResult.hasErrors()){
			model.addAttribute(newUserForm);
			model.addAttribute(BindingResult.class.getName() + ".newUserForm", bindingResult);
			return "management/user/form";
		}
		RestTemplate restTemplate = new RestTemplate();
		user = restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/user/profile/new")
						.toString(), provider).toUri(), 
				user, User.class);
		if(null != newUserForm.getNewImageFile()){
			try {
				user.setImageFilePath(uploadHelper.saveFile(
						newUserForm.getNewImageFile(), user.getUserId()));
			} catch (BusinessException e) {
				model.addAttribute(user);
				model.addAttribute("errorMessage", 
					messageSource.getMessage(e.getCode(), e.getArgs(), locale));
				return "management/user/form";
			}
		}
		model.addAttribute("newUser", user);
		return "management/user/confirm";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/user/new/{userId:[0-9]+}")
	public String saveUser(@Validated(SaveUser.class) NewUserForm newUserForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		String serviceName = "management";
		String serviceName2 = "message";

		User user = mapper.map(newUserForm, User.class);
		
		if(bindingResult.hasErrors()){
			model.addAttribute("newUser", user);
			model.addAttribute(BindingResult.class.getName() + ".newUser", bindingResult);
			return "management/user/confirm";
		}
		
		if(!"save".equals(newUserForm.getType())){
			model.addAttribute("newUser", user);
			return "management/user/form";
		}
		RestTemplate restTemplate = new RestTemplate();
		try{
			restTemplate.postForObject(
					RequestBuilder.buildUriComponents(serviceName2,
							new StringBuilder()
							.append(APP_NAME)
							.append("/user/new")
							.toString(), provider).toUri(), user, User.class);
			redirectAttributes.addFlashAttribute("newUser", 
				restTemplate.postForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/user/{userId}")
								.toString(), provider)
						.expand(newUserForm.getUserId()).toUri(),
						user, User.class));
		} catch (Exception e){
			//TODO Using Business Exception for optimistic rock error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append(user.getUserId())
					.append("?complete")
					.toString();
	}

	@RequestMapping(method=RequestMethod.GET, 
			value="/management/user/new/{userId:[0-9]+}",
			params="complete")
	public String saveUserComplete(){
		return "management/user/saveComplete";
	}

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/management/profile/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getManagementProfileImage(@PathVariable String userId,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(customUserDetails.getUser().getAuthorityLevel() != 9){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/profile/{userId}")
						.toString(), provider).expand(userId).toUri(), User.class);
		BufferedImage image = null;
		try{
			image = downloadHelper.getProfileImage(user);
		}catch(BusinessException e){
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/user/{userId:[0-9]+}")
	public String getUser(@Validated(GetUser.class) EditUserForm editUserForm,
			Errors errors, Model model){
		
		String serviceName = "management";
		
		if(errors.hasErrors()){
			return "management/user/portal";
		}
		User user = null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			user = restTemplate.getForObject(
					RequestBuilder.buildUriComponents(serviceName, 
					new StringBuilder()
					.append(APP_NAME)
					.append("/user/{userId}")
					.toString(), provider)
					.expand(editUserForm.getUserId()).toUri(), User.class);
		} catch (Exception e){
			//TODO Using Business Exception for optimistic rock error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
	
		if("update".equals(editUserForm.getType())){
			model.addAttribute("editUser", user);
			return "management/user/edit";
		}else{
			model.addAttribute("deleteUser", user);
			return "management/user/delete";
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/management/edit/user/{userId:[0-9]+}")
	public String updateUser(@Validated(UpdateUser.class) EditUserForm editUserForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes, Locale locale){
		
		String serviceName = "management";
		String serviceName2 = "message";

		User user = mapper.map(editUserForm, User.class);
		if(bindingResult.hasErrors()){
			model.addAttribute("editUser", user);
			model.addAttribute(BindingResult.class.getName() + ".user", bindingResult);
			return "management/user/edit";
		}
		
		if(!"update".equals(editUserForm.getType())){
			return "redirect:/management/user/portal";
		}

		if(null != editUserForm.getNewImageFile()){
			try {
				user.setImageFilePath(uploadHelper.saveFile(
					editUserForm.getNewImageFile(), editUserForm.getUserId()));
			} catch (BusinessException e) {
				model.addAttribute("editUser", user);
				model.addAttribute("errorMessage", 
					messageSource.getMessage(e.getCode(), e.getArgs(), locale));
				return "management/user/edit";
			}
		}

		RestTemplate restTemplate = new RestTemplate();
		try{
			restTemplate.exchange(RequestBuilder.buildUriComponents(serviceName2, 
					new StringBuilder()
					.append(APP_NAME)
					.append("/user/{userId}")
					.toString(), provider)
					.expand(editUserForm.getUserId()).toUri(), HttpMethod.PUT,
					new HttpEntity<User>(user), User.class);
			redirectAttributes.addFlashAttribute("updateResult", 
					restTemplate.exchange(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/user/{userId}")
									.toString(), provider)
							.expand(editUserForm.getUserId()).toUri(),
							HttpMethod.PUT, new HttpEntity<User>(user), 
							UpdateUserResult.class).getBody());
		} catch (Exception e){
			//TODO Using Business Exception for optimistic rock error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append(user.getUserId())
					.append("?complete")
					.toString();
	}
	
	@RequestMapping(method = RequestMethod.GET, 
			value="/management/edit/user/{userId:[0-9]+}",
			params="complete")
	public String editUserComplete(){
		return "management/user/editComplete";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/user/delete/{userId:[0-9]+}")
	public String deleteConfirm(@Validated DeleteUserForm deleteUserForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes){
		
		String serviceName = "management";
		String serviceName2 = "message";

		User deleteUser = mapper.map(deleteUserForm,  User.class);
		if(bindingResult.hasErrors()){
			return "common/error";
		}

		if(!"delete".equals(deleteUserForm.getType())){
			return "redirect:/management/user/portal";
		}

		RestTemplate restTemplate = new RestTemplate();
		try{
			restTemplate.exchange(RequestBuilder.buildUriComponents(serviceName2, 
					new StringBuilder()
					.append(APP_NAME)
					.append("/user/{userId}")
					.toString(), provider)
					.expand(deleteUserForm.getUserId()).toUri(), 
					HttpMethod.DELETE, new HttpEntity<User>(deleteUser), User.class);
			redirectAttributes.addFlashAttribute("deleteUser", 
					restTemplate.exchange(RequestBuilder.buildUriComponents(serviceName, 
							new StringBuilder()
							.append(APP_NAME)
							.append("/user/{userId}")
							.toString(), provider)
							.expand(deleteUserForm.getUserId()).toUri(),
					HttpMethod.DELETE, new HttpEntity<User>(
						deleteUser), User.class).getBody());
		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append(deleteUserForm.getUserId())
					.append("?complete")
					.toString();
	}

	@RequestMapping(method=RequestMethod.GET, 
			value="/management/user/delete/{userId:[0-9]+}",params = "complete")
	public String deleteUserComplete(){
		return "management/user/deleteComplete";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/information/portal")
	public String informationManagementPortal(@Validated PageParam pageParam, 
			BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("page", Integer.toString(pageParam.getPage()));
	    params.set("size", Integer.toString(pageParam.getSize()));
		model.addAttribute("page", restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/information")
						.toString(), provider, params).toUri(), InformationPageImpl.class));
		return "management/information/portal";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/information/new")
	public String newInformationForm(Model model){
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		model.addAttribute("users", restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/information/form")
						.toString(), provider).toUri(), InformationFormResource.class).getUsers());
		return "management/information/form";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/information/draft/new")
	public String newInformationDraft(
			@Validated(ConfirmInformation.class) NewInformationForm newInformationForm,
			BindingResult bindingResult, Model model, Locale locale){
		
		String serviceName = "management";
		
		if(bindingResult.hasErrors()){
			model.addAttribute(newInformationForm);
			model.addAttribute(
					BindingResult.class.getName() + ".information", bindingResult);
			return newInformationForm(model);
		}

		RestTemplate restTemplate = new RestTemplate();
		InformationDraft informationDraft = restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/information/draft/new")
						.toString(), provider).toUri(), newInformationForm, InformationDraft.class);

		try {
			informationMessageBodyHelper.saveMessageBody(informationDraft, 
					newInformationForm.getMessageBody());

			Map<String, String> uriVariables = new HashMap<String, String>();
			uriVariables.put("infoId", informationDraft.getInformation().getInfoId());

			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.set("temp", null);
			params.set("infoPagePath", informationDraft.getInformation().getInfoPagePath());
			informationDraft.setTempInfoUrl(RequestBuilder.buildUriComponents(
					protocol, "frontend", 
					new StringBuilder()
					.append(contextPath)
					.append("/information/body/{infoId}")
					.toString(), provider, uriVariables, params).toString());
			model.addAttribute(informationDraft);
		} catch (BusinessException e) {
			model.addAttribute("errorMessage", messageSource.getMessage(
					e.getCode(), e.getArgs(), locale));
			model.addAttribute(newInformationForm);
			return "management/information/form";
		}
		return "management/information/confirm";
	}

	@RequestMapping(value="/information/body/{infoId:[0-9]+}", method=RequestMethod.GET,
			produces="text/html;charset=UTF-8", params="temp")
	@ResponseBody
	public String infomationBody(@Validated(GetMessageBody.class)
			org.debugroom.wedding.app.model.management.information.Information information){
		try {
			return informationMessageBodyHelper.getMessageBody(mapper.map(information, 
					org.debugroom.wedding.domain.entity.Information.class));
		} catch (MappingException | BusinessException e) {
			return "error";
		}
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/information/new")
	public String saveInformation(
			@Validated(SaveInformation.class) NewInformationForm newInformationForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes, Locale locale){

		String serviceName = "management";
//		InformationDraft informationDraft = 
//				mapper.map(newInformationForm, InformationDraft.class);
		InformationDraft informationDraft = 
				InformationDraft.builder()
				.information(
						org.debugroom.wedding.app.model.management.information.Information.builder()
						.infoId(newInformationForm.getInfoId())
						.infoPagePath(newInformationForm.getInfoPagePath())
						.title(newInformationForm.getTitle())
						.messageBody(newInformationForm.getMessageBody())
						.registratedDate(newInformationForm.getRegistratedDate())
						.releaseDate(newInformationForm.getReleaseDate())
						.build())
				.infoName(newInformationForm.getInfoName())
				.viewUsers(newInformationForm.getCheckedUsers())
				.build();
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("infoId", informationDraft.getInformation().getInfoId());
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("temp", null);
		params.set("infoPagePath", informationDraft.getInformation().getInfoPagePath());
		informationDraft.setTempInfoUrl(
				RequestBuilder.buildUriComponents(protocol, "frontend", 
						new StringBuilder()
						.append(contextPath)
						.append("/information/body/{infoId}")
						.toString(), provider, uriVariables ,params).toString());
		
		if(bindingResult.hasErrors()){
			model.addAttribute(informationDraft);
			model.addAttribute(BindingResult.class.getName() + ".informationDraft", bindingResult);
		}

		if(!"save".equals(newInformationForm.getType())){
			model.addAttribute(newInformationForm);
			return newInformationForm(model);
		}

		RestTemplate restTemplate = new RestTemplate();
		try{
			redirectAttributes.addFlashAttribute(
				InformationResource
					.builder()
					.information(
						restTemplate.postForObject(
								RequestBuilder.buildUriComponents(serviceName, 
										new StringBuilder()
										.append(APP_NAME)
										.append("/information/{infoId}")
										.toString(), provider)
								.expand(informationDraft.getInformation().getInfoId())
								.toUri(), newInformationForm,
								org.debugroom.wedding.domain.entity.Information.class))
					.messageBodyUrl(informationDraft.getTempInfoUrl())
					.build());
			redirectAttributes.addFlashAttribute("users", 
					newInformationForm.getCheckedUsers());
		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append("?complete")
					.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET,
			value="/management/information/new",
			params="complete")
	public String saveInformationComplete(){
		return "management/information/saveComplete";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/information/{infoId:[0-9]+}")
	public String getInformation(
			@Validated(GetInformation.class) InformationDetailForm informationDetailForm, 
			BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			return informationManagementPortal(
					PageParam.builder().build(), bindingResult, model);
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();

		InformationDetail informationDetail = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/information/{infoId}")
						.toString(), provider)
				.expand(informationDetailForm.getInfoId()).toUri(), InformationDetail.class);

		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("infoId", informationDetailForm.getInfoId());
		informationDetail.setMessageBodyUrl(
				RequestBuilder.buildUriComponents(protocol, "frontend", 
						new StringBuilder()
						.append("/information/body/{infoId}")
						.toString(), provider, uriVariables).toString());

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("type", "not-information-viewers");
		params.set("infoId", "");
		informationDetail.setNoAccessedUsersUrl(
				RequestBuilder.getServicePath("frontend", 
						new StringBuilder()
						.append("/search/users")
						.toString(), provider, params));
		model.addAttribute(informationDetail);
		if("detail".equals(informationDetailForm.getType())){
			return "management/information/detail";
		}else{
			return "management/information/delete";
		}

	}
	
	@RequestMapping(method=RequestMethod.GET, value="/search/users", params="type")
	public ResponseEntity<UserSearchResult> searchUsers(
			@Validated UserSearchCriteria userSearchCriteria, Errors errors, Locale locale){
		
		UserSearchResult userSearchResult = UserSearchResult.builder()
				.infoId(userSearchCriteria.getInfoId())
				.requestId(userSearchCriteria.getRequestId())
				.folderId(userSearchCriteria.getFolderId())
				.build();
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}

		RestTemplate restTemplate = new RestTemplate();
		String serviceName = null;
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		switch (userSearchCriteria.getType()){
			case "not-information-viewers" :
				serviceName = "management";
				params.set("not-information-viewers", "");
				params.set("infoId", userSearchCriteria.getInfoId());
				return ResponseEntity.status(HttpStatus.OK).body(restTemplate.getForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/users")
								.toString(), provider, params)
						.toUri(), UserSearchResult.class));
			case "not-request-users" :
				serviceName = "management";
				params.set("not-request-users", "");
				params.set("requestId", userSearchCriteria.getRequestId());
				return ResponseEntity.status(HttpStatus.OK).body(restTemplate.getForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/users")
								.toString(), provider, params)
						.toUri(), UserSearchResult.class));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/management/information/{information.infoId:[0-9]+}")
	public String updateInformation(
			@Validated(UpdateInformation.class) UpdateInformationForm updateInformationForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
			
		if(!"update".equals(updateInformationForm.getType())){
			return informationManagementPortal(
					PageParam.builder().page(0).size(10).build(), bindingResult, model);
		}
	
		if(bindingResult.hasErrors()){
			InformationDetail informationDetail = 
					mapper.map(updateInformationForm, InformationDetail.class);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.set("type", "not-information-viewers");
			params.set("infoId", "");
			informationDetail.setNoAccessedUsersUrl(
					RequestBuilder.getServicePath("frontend", 
							new StringBuilder()
							.append("/search/users")
							.toString(), provider, params));

			Map<String, String> uriVariables = new HashMap<String, String>();
			uriVariables.put("infoId", informationDetail.getInformation().getInfoId());
			informationDetail.setMessageBodyUrl(
					RequestBuilder.buildUriComponents(protocol, "frontend", 
							new StringBuilder()
							.append(contextPath)
							.append("/information/body/{infoId}")
							.toString(), provider, uriVariables).toString());
			model.addAttribute(informationDetail);
			model.addAttribute(BindingResult.class.getName() + ".informationDetail", bindingResult);
			return "/management/information/detail";
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();

		try{

			UpdateInformationResult updateInformationResult = 
					restTemplate.exchange(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/information/{infoId}")
									.toString(), provider)
							.expand(updateInformationForm.getInformation().getInfoId()).toUri(),
							HttpMethod.PUT, new HttpEntity<UpdateInformationForm>(updateInformationForm), 
							UpdateInformationResult.class).getBody();
			
			updateInformationResult.setBeforeMessageBody(
					informationMessageBodyHelper.getMessageBody(
							updateInformationResult.getBeforeEntity().getInformation()));

			String messageBody = updateInformationForm.getInformation().getMessageBody();

			if(informationMessageBodyHelper.updateMessageBody(
					updateInformationResult.getAfterEntity().getInformation(), messageBody)){
				updateInformationResult.getUpdateParamList().add("messageBody");
				updateInformationResult.setAfterMessageBody(messageBody);
			}

			redirectAttributes.addFlashAttribute("updateResult", updateInformationResult);
		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder().append("redirect:")
				.append(updateInformationForm.getInformation().getInfoId())
				.append("?complete")
				.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/management/information/{information.infoId:[0-9]+}", params="complete")
	public String updatInformationComplete(){
		return "management/information/updateComplete";
	}

	@RequestMapping(method=RequestMethod.POST,
			value="/management/information/delete/{infoId:[0-9]+}")
	public String deleteConfirm(@Validated DeleteInformationForm deleteInformationForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		
		if(!"delete".equals(deleteInformationForm.getType())){
			return informationManagementPortal(
					PageParam.builder().page(0).size(10).build(), bindingResult, model);
		}

		String serviceName = "management";

		RestTemplate restTemplate = new RestTemplate();
		try{
			InformationDetail informationDetail = InformationDetail
					.builder()
					.information(restTemplate.exchange(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/information/{infoId}")
									.toString(), provider)
							.expand(deleteInformationForm.getInfoId()).toUri(), HttpMethod.DELETE, 
						new HttpEntity<DeleteInformationForm>(deleteInformationForm), 
						org.debugroom.wedding.domain.entity.Information.class).getBody())
					.build();
			informationDetail.setMessageBody(informationMessageBodyHelper
					.getMessageBody(informationDetail.getInformation()));
			redirectAttributes.addFlashAttribute(informationDetail);
		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append(deleteInformationForm.getInfoId())
					.append("?complete")
					.toString();
	}

	@RequestMapping(method=RequestMethod.GET, 
			value="/management/information/delete/{infoId:[0-9]+}", params="complete")
	public String deleteInformationComplete(){
		return "management/information/deleteComplete";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/request/portal")
	public String requestManagementPortal(@Validated PageParam pageParam,
			BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("page", Integer.toString(pageParam.getPage()));
	    params.set("size", Integer.toString(pageParam.getSize()));
		Page<Request> page = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/requests")
						.toString(), provider, params).toUri(), RequestPageImpl.class);
		model.addAttribute("page", page);

		return "management/request/portal";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/request/new")
	public String newRequestForm(Model model){
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		model.addAttribute("users", restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/form")
						.toString(), provider).toUri(), RequestFormResource.class).getUsers());
		return "management/request/form";
	}

	@RequestMapping(method = RequestMethod.POST, value="/management/request/draft/new")
	public String newRequestDraft(
			@Validated(ConfirmRequest.class) NewRequestForm newRequestForm,
			BindingResult bindingResult, Model model, Locale locale){

		String serviceName = "management";
		
		if(bindingResult.hasErrors()){
			model.addAttribute(newRequestForm);
			model.addAttribute(
					BindingResult.class.getName() + ".request", bindingResult);
			return newRequestForm(model);
		}

		RestTemplate restTemplate = new RestTemplate();
		RequestDraft requestDraft = restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/draft/new")
						.toString(), provider).toUri(), newRequestForm, RequestDraft.class);
		requestDraft.getRequest().setRequestContents(newRequestForm.getRequestContents());
		model.addAttribute(requestDraft);
		
		return "management/request/confirm";

	}

	@RequestMapping(method=RequestMethod.POST, value="/management/request/new")
	public String saveRequest(@Validated(SaveRequest.class) NewRequestForm newRequestForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes, Locale locale){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("requestDraft", mapper.map(newRequestForm, RequestDraft.class));
			model.addAttribute(BindingResult.class.getName() + ".requestDraft", bindingResult);
			return "management/request/confirm";
		}
		
		if(!"save".equals(newRequestForm.getType())){
			model.addAttribute(newRequestForm);
			return newRequestForm(model);
		}
		
		String serviceName = "management";
	
		RestTemplate restTemplate = new RestTemplate();

		Request request = restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/{requestId}")
						.toString(), provider)
				.expand(newRequestForm.getRequestId()).toUri(), 
				newRequestForm, Request.class);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String requestContents = restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/body/{requestId}")
						.toString(), provider)
				.expand(newRequestForm.getRequestId()).toUri(),
				HttpMethod.GET, httpEntity, String.class).getBody();
		request.setRequestContents(requestContents);

		redirectAttributes.addFlashAttribute(request);
		redirectAttributes.addFlashAttribute("users", newRequestForm.getCheckedUsers());

		return "redirect:new?complete";

	}

	@RequestMapping(method=RequestMethod.GET, 
			value="/management/request/new", params="complete")
	public String saveRequestComplete(){
		return "management/request/saveComplete";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/request/{requestId:[0-9]+}")
	public String getRequest(@Validated RequestDetailForm requestDetailForm,
			BindingResult bindingResult, Model model){
		
		if(bindingResult.hasErrors()){
			return requestManagementPortal(
					PageParam.builder().page(0).size(10).build(), bindingResult, model);
		}
		
		String serviceName = "management";
		
		RestTemplate restTemplate = new RestTemplate();
		RequestDetail requestDetail = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/{requestId}")
						.toString(), provider)
				.expand(requestDetailForm.getRequestId()).toUri(), RequestDetail.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		requestDetail.getRequest().setRequestContents(
				restTemplate.exchange(RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/body/{requestId}")
						.toString(), provider)
					.expand(requestDetailForm.getRequestId()).toUri(), 
				HttpMethod.GET, httpEntity, String.class).getBody());
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("type", "not-request-users");
		params.set("requestId", "");
		requestDetail.setNotRequestUsersUrl(
				RequestBuilder.getServicePath("frontend", 
					new StringBuilder()
					.append("/search/users")
					.toString(), provider, params));
		model.addAttribute(requestDetail);
		
		if("detail".equals(requestDetailForm.getType())){
			return "management/request/detail";
		}else{
			return "management/request/delete";
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/management/request/{requestId:[0-9]+}")
	public String updateRequest(@Validated UpdateRequestForm updateRequestForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
			
		if(!"update".equals(updateRequestForm.getType())){
			return requestManagementPortal(
					PageParam.builder().page(0).size(10).build(), bindingResult, model);
		}

		if(bindingResult.hasErrors()){
			RequestDetail requestDetail = mapper.map(updateRequestForm, RequestDetail.class);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.set("type", "not-request-users");
			params.set("requestId", "");
			requestDetail.setNotRequestUsersUrl(
					RequestBuilder.getServicePath("frontend", 
							new StringBuilder()
							.append("/search/users")
							.toString(), provider, params));
			model.addAttribute(requestDetail);
			model.addAttribute(BindingResult.class.getName() + ".requestDetail", bindingResult);
			return "/management/request/detail";
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String beforeRequestContents = restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/body/{requestId}")
						.toString(), provider)
				.expand(updateRequestForm.getRequest().getRequestId()).toUri(),
				HttpMethod.GET, httpEntity, String.class).getBody();
		try{
			UpdateRequestResult updateRequestResult = 
					restTemplate.exchange(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/request/{requestId}")
									.toString(), provider)
							.expand(updateRequestForm.getRequest().getRequestId()).toUri(), 
							HttpMethod.PUT, new HttpEntity<UpdateRequestForm>(updateRequestForm), 
							UpdateRequestResult.class).getBody();

			updateRequestResult.getBeforeEntity().getRequest()
				.setRequestContents(beforeRequestContents);
			updateRequestResult.getAfterEntity().getRequest()
				.setRequestContents(updateRequestForm.getRequest().getRequestContents());

			redirectAttributes.addFlashAttribute("updateResult", updateRequestResult);

		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		
		return new StringBuilder().append("redirect:")
				.append(updateRequestForm.getRequest().getRequestId())
				.append("?complete")
				.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/management/request/{requestId:[0-9]+}", params="complete")
	public String updateRequestComplete(){
		return "management/request/updateComplete";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/request/delete/{requestId:[0-9]+}")
	public String deleteConfirm(@Validated DeleteRequestForm deleteRequestForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		
		if(!"delete".equals(deleteRequestForm.getType())){
			return requestManagementPortal(
					PageParam.builder().page(0).size(10).build(), bindingResult, model);
		}
		
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String deleteRequestContents = restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/request/body/{requestId}")
						.toString(), provider)
				.expand(deleteRequestForm.getRequestId()).toUri(),
				HttpMethod.GET, httpEntity, String.class).getBody();

		try{
			RequestDetail requestDetail = RequestDetail
					.builder()
					.request(restTemplate.exchange(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/request/{requestId}")
									.toString(), provider)
							.expand(deleteRequestForm.getRequestId()).toUri(), 
							HttpMethod.DELETE, new HttpEntity<DeleteRequestForm>(deleteRequestForm), 
						org.debugroom.wedding.app.model.management.request.Request.class)
						.getBody())
					.build();
			requestDetail.getRequest().setRequestContents(deleteRequestContents);
			redirectAttributes.addFlashAttribute(requestDetail);
		} catch (Exception e){
			//TODO Using Business Exception for serverside error.
			model.addAttribute("errorCode", "");
			return "common/error";
		}
		return new StringBuilder()
					.append("redirect:")
					.append(deleteRequestForm.getRequestId())
					.append("?complete")
					.toString();
	}

	@RequestMapping(method=RequestMethod.GET,
			value="/management/request/delete/{requestId:[0-9]+}",
			params="complete")
	public String deleteComplete(){
		return "management/request/deleteComplete";
	}

}