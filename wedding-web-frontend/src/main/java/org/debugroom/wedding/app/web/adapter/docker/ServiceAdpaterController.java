package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.management.AddressSearchCriteria;
import org.debugroom.wedding.app.model.management.AddressSearchCriteria.SearchAddress;
import org.debugroom.wedding.app.model.management.NewUserForm.ConfirmUser;
import org.debugroom.wedding.app.model.management.NewUserForm.SaveUser;
import org.debugroom.wedding.app.model.management.AddressSearchResult;
import org.debugroom.wedding.app.model.management.DeleteUserForm;
import org.debugroom.wedding.app.model.management.EditUserForm;
import org.debugroom.wedding.app.model.management.EditUserForm.GetUser;
import org.debugroom.wedding.app.model.management.EditUserForm.UpdateUser;
import org.debugroom.wedding.app.model.management.ImageParam;
import org.debugroom.wedding.app.model.management.LoginIdSearchCriteria;
import org.debugroom.wedding.app.model.management.LoginIdSearchResult;
import org.debugroom.wedding.app.model.management.NewUserForm;
import org.debugroom.wedding.app.model.management.PageParam;
import org.debugroom.wedding.app.model.management.UserPageImpl;
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.app.model.profile.UpdateResult;
import org.debugroom.wedding.app.web.EnvProperties;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.helper.ImageUploadHelper;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.app.web.validator.PasswordEqualsValidator;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.external.api.AddressSearch;

@Controller
public class ServiceAdpaterController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";

	@ModelAttribute
	public EditProfileForm setUpEditProfileForm(){
		return EditProfileForm.builder().build();
	}
	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	ImageUploadHelper uploadHelper;
	
	@Inject
	ImageDownloadHelper downloadHelper;
	
	@Inject
	AddressSearch addressSearch;
	
	@Inject
	PasswordEqualsValidator passwordEqualsValidator;
	
	@Inject
	MessageSource messageSource;
	
	@Inject
	EnvProperties envProperties;
	
	@ModelAttribute
	public NewUserForm setUpNewUserForm(){
		return NewUserForm.builder().build();
	}
	
	@ModelAttribute
	public EditUserForm setUpEditUserForm(){
		return EditUserForm.builder().build();
	}

	@InitBinder(value={"editUserForm", "newUserForm"})
	public void initBinder(WebDataBinder binder){
		binder.addValidators(passwordEqualsValidator);
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() throws RestClientException, URISyntaxException{
		String serviceName = "login";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getForObject(
				RequestBuilder.getGetRequest(provider.getPath(serviceName), 
						APP_NAME, serviceName, null, null),
				String.class);
		return serviceName;
	}
	
	@RequestMapping(value="/portal/{userId}", method=RequestMethod.GET)
	public String portal(@PathVariable String userId, Model model) 
			throws URISyntaxException{
		String serviceName = "portal";
		Map<Integer, String> pathVariableMap = new HashMap<Integer, String>();
		pathVariableMap.put(0, userId);
		RestTemplate restTemplate = new RestTemplate();
		try {
			PortalResource portalResource = restTemplate.getForObject(
					RequestBuilder.getGetRequest(provider.getPath(serviceName), 
							APP_NAME, serviceName, null, pathVariableMap),
					PortalResource.class);
			model.addAttribute(portalResource);
		} catch (RestClientException e) {
			// TODO Consideration exception handling
			e.printStackTrace();
		}
		return "portal/portal";
	}
	
	@RequestMapping(value="/information/{infoId}", method=RequestMethod.GET)
	public String information(@Validated Information information, Model model) 
			throws URISyntaxException{
		String serviceName = "information";
		Map<Integer, String> pathVariableMap = new HashMap<Integer, String>();
		pathVariableMap.put(0, information.getInfoId());
		RestTemplate restTemplate = new RestTemplate();
		try{
			Information resultInformation = restTemplate.getForObject(
					RequestBuilder.getGetRequest(provider.getPath(serviceName), 
							APP_NAME, serviceName, null, pathVariableMap),
					Information.class);
			resultInformation.setInfoRootPath(envProperties.getInfoRootPath());
			model.addAttribute(resultInformation);

		} catch (RestClientException e) {
			// TODO Consideration exception handling
			e.printStackTrace();
		}
		return "portal/information";
	}

	@RequestMapping(value="/profile/{userId}", method=RequestMethod.GET)
	public String profile(@PathVariable String userId, Model model){
		String serviceName = "profile";
		Map<Integer, String> pathVariableMap = new HashMap<Integer, String>();
		pathVariableMap.put(0, userId);
		RestTemplate restTemplate = new RestTemplate();
		try {
			PortalResource portalResource = PortalResource.builder() 
					.user(restTemplate.getForObject(
					RequestBuilder.getGetRequest(provider.getPath(serviceName), 
					APP_NAME, serviceName, null, pathVariableMap),
					User.class))
					.build();
			model.addAttribute(portalResource);
		} catch (RestClientException | URISyntaxException e) {
			e.printStackTrace();
		}
		return "profile/portal";
	}

	@RequestMapping(value="/profile/{userId}", method=RequestMethod.POST)
	public String profile(@PathVariable String userId, 
			@Validated EditProfileForm editProfileForm, 
			BindingResult bindingResult, Model model, Locale locale){

		String serviceName = "profile";
		Map<Integer, String> pathVariableMap = new HashMap<Integer, String>();
		pathVariableMap.put(0, userId);
		if(bindingResult.hasErrors()){
			model.addAttribute(mapper.map(editProfileForm, 
					org.debugroom.wedding.app.model.profile.PortalResource.class));
			model.addAttribute(BindingResult.class.getName() + ".portalResource", bindingResult);
			return "profile/portal";
		}
		if(null != editProfileForm.getNewImageFile()){
			try {
				editProfileForm.getUser().setImageFilePath(uploadHelper.saveFile(
						editProfileForm.getNewImageFile(), userId));
			} catch (BusinessException e) {
				model.addAttribute(mapper.map(editProfileForm, 
					org.debugroom.wedding.app.model.profile.PortalResource.class));
				model.addAttribute("errorMessage", 
					messageSource.getMessage(e.getCode(), e.getArgs(), locale));
				return "profile/portal";
			}
			editProfileForm.setNewImageFile(null);
		}
		RestTemplate restTemplate = new RestTemplate();
		try {
			model.addAttribute(restTemplate.exchange(
					RequestBuilder.getPathVariableHttpRequestURI(provider.getPath(serviceName), 
							APP_NAME, serviceName, pathVariableMap), HttpMethod.PUT,
					new HttpEntity<EditProfileForm>(editProfileForm), 
					UpdateResult.class).getBody());
		} catch (RestClientException | URISyntaxException e) {
			e.printStackTrace();
		}

		return "profile/result";
	}

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/profile/image/{userId}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getProfileImage(@PathVariable String userId){
		String serviceName = "profile";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/")
											.append(serviceName)
											.append("/")
											.append("{userId}")
											.toString())
									.build()
									.expand(userId);
		User user = restTemplate.getForObject(uriComponents.toUri(),User.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getProfileImage(user);
		} catch (BusinessException e) {
			ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append("users")
												.toString())
										.queryParams(params)
										.build();
		Page<User> page = restTemplate.getForObject(uriComponents.toUri(), UserPageImpl.class);
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
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append("users")
												.append("/")
												.append(loginId)
												.toString())
										.build();
		User user = restTemplate.getForObject(uriComponents.toUri(), User.class);
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

	@RequestMapping(method = RequestMethod.GET, value="/image/{userId}/{imageFileExtension}")
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/user/profile/new")
											.toString())
									.build();
		user = restTemplate.postForObject(uriComponents.toUri(), user, User.class);
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

	@RequestMapping(method=RequestMethod.POST, value="/management/user/new/{userId}")
	public String saveUser(@Validated(SaveUser.class) NewUserForm newUserForm,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
		
		String serviceName = "management";

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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/user/new")
											.toString())
									.build();
		try{
			redirectAttributes.addFlashAttribute("newUser", 
				restTemplate.postForObject(uriComponents.toUri(), user, User.class));
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

	@RequestMapping(method=RequestMethod.GET, value="/management/user/new/{userId}")
	public String saveComplete(){
		return "management/user/saveComplete";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/user/{userId}")
	public String getUser(@Validated(GetUser.class) EditUserForm editUserForm,
			Errors errors, Model model){
		
		String serviceName = "management";
		
		if(errors.hasErrors()){
			return "management/user/portal";
		}
	
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/user/")
											.append(editUserForm.getUserId())
											.toString())
									.build();
		User user = null;
		try{
			user = restTemplate.getForObject(uriComponents.toUri(), User.class);
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
	
	@RequestMapping(method = RequestMethod.POST, value="/management/edit/user/{userId}")
	public String updateUser(@Validated(UpdateUser.class) EditUserForm editUserForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes, Locale locale){
		
		String serviceName = "management";

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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/user/")
											.append(editUserForm.getUserId())
											.toString())
									.build();


		try{
			redirectAttributes.addFlashAttribute("updateResult", 
					restTemplate.exchange(uriComponents.toUri(), 
							HttpMethod.PUT, new HttpEntity<User>(user), 
							UpdateResult.class).getBody());
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
	
	@RequestMapping(method = RequestMethod.GET, value="/management/edit/user/{userId}")
	public String editComplete(){
		return "management/user/editComplete";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/user/delete/{userId}")
	public String deleteConfirm(@Validated DeleteUserForm deleteUserForm,
			BindingResult bindingResult, Model model, 
			RedirectAttributes redirectAttributes){
		
		String serviceName = "management";

		if(bindingResult.hasErrors()){
			return "common/error";
		}

		if(!"delete".equals(deleteUserForm.getType())){
			return "redirect:/management/user/portal";
		}

		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/user/")
											.append(deleteUserForm.getUserId())
											.toString())
									.build();
		try{
			redirectAttributes.addFlashAttribute("deleteUser", 
					restTemplate.exchange(uriComponents.toUri(), 
					HttpMethod.DELETE, new HttpEntity<User>(
						mapper.map(deleteUserForm, User.class)), User.class).getBody());
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
			value="/management/user/delete/{userId}",params = "complete")
	public String deleteComplete(){
		return "management/user/deleteComplete";
	}

}
