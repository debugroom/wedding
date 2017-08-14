package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.FileDownloadException;
import org.debugroom.wedding.app.model.UserSearchCriteria;
import org.debugroom.wedding.app.model.UserSearchResult;
import org.debugroom.wedding.app.model.gallery.CreateFolderForm;
import org.debugroom.wedding.app.model.gallery.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.model.gallery.CreateFolderResult;
import org.debugroom.wedding.app.model.gallery.DeleteFolderForm;
import org.debugroom.wedding.app.model.gallery.DeleteFolderResult;
import org.debugroom.wedding.app.model.gallery.DeleteMediaForm;
import org.debugroom.wedding.app.model.gallery.DeleteMediaResult;
import org.debugroom.wedding.app.model.gallery.DownloadMediaForm;
import org.debugroom.wedding.app.model.gallery.Folder;
import org.debugroom.wedding.app.model.gallery.GalleryPortalResource;
import org.debugroom.wedding.app.model.gallery.Media;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.app.model.gallery.PhotoSearchResult;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm.UpdateFolder;
import org.debugroom.wedding.app.model.gallery.UpdateFolderResult;
import org.debugroom.wedding.app.model.gallery.UploadFileForm;
import org.debugroom.wedding.app.model.gallery.UploadFileResult;
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
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.app.model.profile.UpdateUserResult;
import org.debugroom.wedding.app.web.helper.InformationMessageBodyHelper;
import org.debugroom.wedding.app.web.helper.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.helper.ProfileImageUploadHelper;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.app.web.validator.PasswordEqualsValidator;
import org.debugroom.wedding.domain.entity.User;
import org.debugroom.wedding.external.api.AddressSearch;

@Controller
public class ServiceAdpaterController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";

	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	ProfileImageUploadHelper uploadHelper;
	
	@Inject
	ImageDownloadHelper downloadHelper;
	
	@Inject
	InformationMessageBodyHelper informationMessageBodyHelper;

	@Inject
	GalleryContentsUploadHelper galleryContentsUploadHelper;
	
	@Inject
	AddressSearch addressSearch;
	
	@Inject
	PasswordEqualsValidator passwordEqualsValidator;
	
	@Inject
	MessageSource messageSource;
	
	@Value("${info.root.directory}")
	private String infoRootDirectory;

	@ModelAttribute
	public EditProfileForm setUpEditProfileForm(){
		return EditProfileForm.builder().build();
	}

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
	
	@RequestMapping(value="/portal/{userId:[0-9]+}", method=RequestMethod.GET)
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
	

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/portal/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getPortalImage(@PathVariable String userId){
		String serviceName = "portal";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/profile/")
											.append("{userId}")
											.toString())
									.build()
									.expand(userId);
		User user = restTemplate.getForObject(uriComponents.toUri(),User.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getProfileImage(user);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(value="/information/{infoId:[0-9]+}", method=RequestMethod.GET)
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
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
			UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
					.host(provider.getIpAddr("frontend"))
					.port(provider.getPort("frontend"))
					.path(new StringBuilder()
							.append("/information/body/")
							.append(information.getInfoId())
							.toString())
					.build();
			resultInformation.setInfoRootPath(uriComponents.toString());
			model.addAttribute(resultInformation);

		} catch (RestClientException e) {
			// TODO Consideration exception handling
			e.printStackTrace();
		}
		return "portal/information";
	}

	@RequestMapping(value="/information/body/{infoId:[0-9]+}", method=RequestMethod.GET,
			produces="text/html;charset=UTF-8")
	@ResponseBody
	public String infomationBody(@PathVariable String infoId){
		String serviceName = "information";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/information/")
											.append(infoId)
											.toString())
									.build();
		try {
			return informationMessageBodyHelper.getMessageBody(restTemplate.getForObject(
					uriComponents.toUri(), org.debugroom.wedding.domain.entity.Information.class));
		} catch (RestClientException | BusinessException e) {
			return "error";
		}
	}

	@RequestMapping(value="/profile/{userId:[0-9]+}", method=RequestMethod.GET)
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

	@RequestMapping(value="/profile/{userId:[0-9]+}", method=RequestMethod.POST)
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
					UpdateUserResult.class).getBody());
		} catch (RestClientException | URISyntaxException e) {
			e.printStackTrace();
		}

		return "profile/result";
	}

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/profile/image/{userId:[0-9]+}/{imageFileExtension}")
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
			return ResponseEntity.badRequest().body(null);
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

	@RequestMapping(method=RequestMethod.POST, value="/management/user/new/{userId:[0-9]+}")
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
											.append("/user/")
											.append(newUserForm.getUserId())
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

	@RequestMapping(method=RequestMethod.GET, 
			value="/management/user/new/{userId:[0-9]+}",
			params="complete")
	public String saveUserComplete(){
		return "management/user/saveComplete";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/user/{userId:[0-9]+}")
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
	
	@RequestMapping(method = RequestMethod.POST, value="/management/edit/user/{userId:[0-9]+}")
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append("information")
												.toString())
										.queryParams(params)
										.build();
		model.addAttribute("page", restTemplate.getForObject(
				uriComponents.toUri(), InformationPageImpl.class));
		return "management/information/portal";
	}

	@RequestMapping(method=RequestMethod.GET, value="/management/information/new")
	public String newInformationForm(Model model){
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/information/form")
												.toString())
										.build();
		model.addAttribute("users", restTemplate.getForObject(
				uriComponents.toUri(), InformationFormResource.class).getUsers());
		return "management/information/form";
	}

	@RequestMapping(method=RequestMethod.POST, value="/management/information/draft/new")
	public String newInformationDarft(
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append("information/draft/new")
												.toString())
										.build();
		
		InformationDraft informationDraft = restTemplate.postForObject(
				uriComponents.toUri(), newInformationForm, InformationDraft.class);

		try {

			informationMessageBodyHelper.saveMessageBody(informationDraft, 
					newInformationForm.getMessageBody());

			uriComponentsBuilder = UriComponentsBuilder.newInstance();
			uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
					.host(provider.getIpAddr("frontend"))
					.port(provider.getPort("frontend"))
					.path(new StringBuilder()
							.append("/information/body/")
							.append(informationDraft.getInformation().getInfoId())
							.append("?temp&infoPagePath=")
							.append(informationDraft.getInformation().getInfoPagePath())
							.toString())
					.build();
		
			informationDraft.setTempInfoUrl(uriComponents.toString());
	
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
		InformationDraft informationDraft = 
				mapper.map(newInformationForm, InformationDraft.class);
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
					.host(provider.getIpAddr("frontend"))
					.port(provider.getPort("frontend"))
					.path(new StringBuilder()
							.append("/information/body/")
							.append(informationDraft.getInformation().getInfoId())
							.append("?temp&infoPagePath=")
							.append(informationDraft.getInformation().getInfoPagePath())
							.toString())
					.build();
		informationDraft.setTempInfoUrl(uriComponents.toString());
		
		if(bindingResult.hasErrors()){
			model.addAttribute(informationDraft);
			model.addAttribute(BindingResult.class.getName() + ".informationDraft", bindingResult);
		}

		if(!"save".equals(newInformationForm.getType())){
			model.addAttribute(newInformationForm);
			return newInformationForm(model);
		}

		RestTemplate restTemplate = new RestTemplate();

		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/information/")
												.append(informationDraft
														.getInformation()
														.getInfoId())
												.toString())
										.build();	
		
		try{
			redirectAttributes.addFlashAttribute(
				InformationResource
					.builder()
					.information(
						restTemplate.postForObject(
								uriComponents.toUri(), newInformationForm,
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/information/")
												.append(informationDetailForm.getInfoId())
												.toString())
										.build();

		InformationDetail informationDetail = restTemplate.getForObject(
				uriComponents.toUri(), InformationDetail.class);

		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr("frontend"))
										.port(provider.getPort("frontend"))
										.path(new StringBuilder()
												.append("/information/body/")
												.append(informationDetailForm.getInfoId())
												.toString())
										.build();

		informationDetail.setMessageBodyUrl(uriComponents.toString());

		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr("frontend"))
										.port(provider.getPort("frontend"))
										.path(new StringBuilder()
												.append("/search/users?type=not-information-viewers&infoId=")
												.toString())
										.build();

		informationDetail.setNoAccessedUsersUrl(uriComponents.toString());
		
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
	
		String serviceName = null;
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		UriComponents uriComponents = null;
		switch (userSearchCriteria.getType()){
			case "not-information-viewers" :
				serviceName = "management";
				params.set("not-information-viewers", "");
				params.set("infoId", userSearchCriteria.getInfoId());
				uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/users")
												.toString())
										.queryParams(params)
										.build();
				return ResponseEntity.status(HttpStatus.OK).body(restTemplate.getForObject(
					uriComponents.toUri(), UserSearchResult.class));
			case "not-request-users" :
				serviceName = "management";
				params.set("not-request-users", "");
				params.set("requestId", userSearchCriteria.getRequestId());
				uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/users")
												.toString())
										.queryParams(params)
										.build();
				return ResponseEntity.status(HttpStatus.OK).body(restTemplate.getForObject(
					uriComponents.toUri(), UserSearchResult.class));
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
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
			UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
											.host(provider.getIpAddr("frontend"))
											.port(provider.getPort("frontend"))
											.path(new StringBuilder()
													.append("/search/users?type=not-information-viewers&infoId=")
													.toString())
											.build();
			informationDetail.setNoAccessedUsersUrl(uriComponents.toString());
			uriComponentsBuilder = UriComponentsBuilder.newInstance();
			uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
											.host(provider.getIpAddr("frontend"))
											.port(provider.getPort("frontend"))
											.path(new StringBuilder()
													.append("/information/body/")
													.append(informationDetail
															.getInformation()
															.getInfoId())
													.toString())
											.build();
			informationDetail.setMessageBodyUrl(uriComponents.toString());
			model.addAttribute(informationDetail);
			model.addAttribute(BindingResult.class.getName() + ".informationDetail", bindingResult);
			return "/management/information/detail";
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/information/")
												.append(updateInformationForm.getInformation().getInfoId())
												.toString())
										.build();	
		
		try{

			UpdateInformationResult updateInformationResult = 
					restTemplate.exchange(uriComponents.toUri(), HttpMethod.PUT, 
							new HttpEntity<UpdateInformationForm>(updateInformationForm), 
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/information/")
											.append(deleteInformationForm.getInfoId())
											.toString())
									.build();
		try{
			InformationDetail informationDetail = InformationDetail
					.builder()
					.information(restTemplate.exchange(
						uriComponents.toUri(), HttpMethod.DELETE, 
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/")
												.append("requests")
												.toString())
										.queryParams(params)
										.build();
		Page<Request> page = restTemplate.getForObject(uriComponents.toUri(), RequestPageImpl.class);
		model.addAttribute("page", page);

		return "management/request/portal";
	}

	@RequestMapping(method = RequestMethod.GET, value="/management/request/new")
	public String newRequestForm(Model model){
		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/form")
												.toString())
										.build();
		model.addAttribute("users", restTemplate.getForObject(
				uriComponents.toUri(), RequestFormResource.class).getUsers());
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/draft/new")
												.toString())
										.build();

		RequestDraft requestDraft = restTemplate.postForObject(
				uriComponents.toUri(), newRequestForm, RequestDraft.class);
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/")
												.append(newRequestForm.getRequestId())
												.toString())
										.build();

		Request request = restTemplate.postForObject(
				uriComponents.toUri(), newRequestForm, Request.class);
		
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
											.host(provider.getIpAddr(serviceName))
											.port(provider.getPort(serviceName))
											.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/body/")
												.append(newRequestForm.getRequestId())
												.toString())
										.build();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String requestContents = restTemplate.exchange(uriComponents.toUri(), 
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
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/")
												.append(requestDetailForm.getRequestId())
												.toString())
										.build();
		RequestDetail requestDetail = restTemplate.getForObject(
				uriComponents.toUri(), RequestDetail.class);

		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/body/")
												.append(requestDetailForm.getRequestId())
												.toString())
										.build();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		requestDetail.getRequest().setRequestContents(
				restTemplate.exchange(uriComponents.toUri(), 
				HttpMethod.GET, httpEntity, String.class).getBody());
		
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr("frontend"))
										.port(provider.getPort("frontend"))
										.path(new StringBuilder()
												.append("/search/users?type=not-request-users&requestId=")
												.toString())
										.build();
		
		requestDetail.setNotRequestUsersUrl(uriComponents.toString());
		
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
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
			UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr("frontend"))
										.port(provider.getPort("frontend"))
										.path(new StringBuilder()
												.append("/search/users?type=not-request-users&requestId=")
												.toString())
										.build();
			requestDetail.setNotRequestUsersUrl(uriComponents.toString());
			model.addAttribute(requestDetail);
			model.addAttribute(BindingResult.class.getName() + ".requestDetail", bindingResult);
			return "/management/request/detail";
		}

		String serviceName = "management";
		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/body/")
												.append(updateRequestForm.getRequest().getRequestId())
												.toString())
										.build();	

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String beforeRequestContents = restTemplate.exchange(uriComponents.toUri(), 
				HttpMethod.GET, httpEntity, String.class).getBody();
	
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/")
												.append(updateRequestForm.getRequest().getRequestId())
												.toString())
										.build();	
		
		try{

			UpdateRequestResult updateRequestResult = 
					restTemplate.exchange(uriComponents.toUri(), HttpMethod.PUT, 
							new HttpEntity<UpdateRequestForm>(updateRequestForm), 
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
		
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/request/body/")
												.append(deleteRequestForm.getRequestId())
												.toString())
										.build();	

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

		HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);

		String deleteRequestContents = restTemplate.exchange(uriComponents.toUri(), 
				HttpMethod.GET, httpEntity, String.class).getBody();

		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/request/")
											.append(deleteRequestForm.getRequestId())
											.toString())
									.build();
		try{
			RequestDetail requestDetail = RequestDetail
					.builder()
					.request(restTemplate.exchange(
						uriComponents.toUri(), HttpMethod.DELETE, 
						new HttpEntity<DeleteRequestForm>(deleteRequestForm), 
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

	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/{userId:[0-9]+}")
	public String gallery(@PathVariable String userId, Model model){

		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/portal/")
												.append(userId)
												.toString())
										.build();
		model.addAttribute(restTemplate.getForObject(uriComponents.toUri(), 
				GalleryPortalResource.class));
		return "gallery/portal";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/photo-thumbnail/{photoId:[0-9]+}",
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE })
	@ResponseBody
	public ResponseEntity<BufferedImage> getPhotoThumbnail(
			@Validated Photo photo, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/photo/")
												.append(photo.getPhotoId())
												.toString())
										.build();
		Photo target = restTemplate.getForObject(uriComponents.toUri(), Photo.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getGalleryThumbnailImage(target);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/photo/{photoId:[0-9]+}/{fileName}",
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
	@ResponseBody
	public ResponseEntity<BufferedImage> getPhoto(
			@Validated Photo photo, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/photo/")
												.append(photo.getPhotoId())
												.toString())
										.build();
		Photo target = restTemplate.getForObject(uriComponents.toUri(), Photo.class);		
		
		BufferedImage image = null;
		try {
			image = downloadHelper.getGalleryImage(target);
		} catch (BusinessException e) {
			return ResponseEntity.ok().body(null);
		}
		// TODO Springの不具合の可能性あり。設定方法を改めて検証する。レスポンスヘッダにコンテンツタイプをセットしても、
		//　　HttpEntityMethodProcessor.handleReturnValue()内で上書きされる。
		// AbstractMessageConverterMethodProcessor.writeWithMessageConverters()のselectedMediaTypeを設定するロジックがおかしい？
		/*
		 * 	for (MediaType mediaType : mediaTypes) {
			    if (mediaType.isConcrete()) {
				    selectedMediaType = mediaType;
				    break;                                   <----コンテンツタイプが設定されていればそちらを優先すべき？
			    }
			    else if (mediaType.equals(MediaType.ALL) || mediaType.equals(MEDIA_TYPE_APPLICATION)) {
				    selectedMediaType = MediaType.APPLICATION_OCTET_STREAM;
				    break;
			    }
		    }
		 * 
		 */
		// 暫定手段として、リクエストパスに拡張子を含めて、ContentNegotiationConfigurerで拡張子のメディアタイプが
		// 選ばれるようにconfigureContentNegotiationをオーバライドして設定しておく
	    ResponseEntity<BufferedImage> entity =  ResponseEntity.ok().headers(downloadHelper.getHeaders(target)).body(image);
	    return entity;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/photographs/{folderId}")
	public ResponseEntity<PhotoSearchResult> getPhotographs(@Validated Folder folder,
			BindingResult bindingResult){
		
		PhotoSearchResult photoSearchResult = mapper.map(folder, PhotoSearchResult.class);
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			photoSearchResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(photoSearchResult);
		}

		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/folder/")
												.append(folder.getFolderId())
												.append("/photographs")
												.toString())
										.build();

		Photo[] photographs = restTemplate.getForObject(uriComponents.toUri(), Photo[].class);
		photoSearchResult.setPhotographs(Arrays.asList(photographs));
		
		photoSearchResult.setRequestContextPath(getFrontendServerUri().toString());
	
		return ResponseEntity.status(HttpStatus.OK).body(photoSearchResult);

	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/folder/viewers/{folderId}")
	public ResponseEntity<UserSearchResult> getFolderViewers(
			@Validated UserSearchCriteria userSearchCriteria, BindingResult bindingResult){
		
		UserSearchResult userSearchResult = mapper.map(
				userSearchCriteria, UserSearchResult.class);
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}

		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/folder/")
												.append(userSearchCriteria.getFolderId())
												.append("/viewers")
												.toString())
										.build();
		
		User[] users = restTemplate.getForObject(uriComponents.toUri(), User[].class);
		userSearchResult.setUsers(Arrays.asList(users));

		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);

	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/folder/no-viewers/{folderId}")
	public ResponseEntity<UserSearchResult> getFolderNoViewers(
			@Validated UserSearchCriteria userSearchCriteria, BindingResult bindingResult){
		
		UserSearchResult userSearchResult = mapper.map(
				userSearchCriteria, UserSearchResult.class);
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}

		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/folder/")
												.append(userSearchCriteria.getFolderId())
												.append("/no-viewers")
												.toString())
										.build();

		User[] users = restTemplate.getForObject(uriComponents.toUri(), User[].class);
		userSearchResult.setUsers(Arrays.asList(users));

		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/gallery/folder/new",
			consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateFolderResult> createFolder(
			@Validated(CreateFolder.class) @RequestBody CreateFolderForm createFolderForm,
			BindingResult bindingResult){
		
		CreateFolderResult createFolderResult = mapper.map(
				createFolderForm,  CreateFolderResult.class);
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			createFolderResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createFolderResult);
		}

		String serviceName = "gallery";
	
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/folder/new")
												.toString())
										.build();

		//TODO setUserId form http session.
		createFolderForm.setUserId("00000001");
		
		createFolderResult.setFolder(restTemplate.postForObject(
				uriComponents.toUri(), createFolderForm, Folder.class));

		createFolderResult.setRequestContextPath(getFrontendServerUri().toString());
		
		return ResponseEntity.status(HttpStatus.OK).body(createFolderResult);
		
	}

	@RequestMapping(method=RequestMethod.PUT, value="/gallery/folders/{folderId}")
	public ResponseEntity<UpdateFolderResult> updateFolder(
			@Validated(UpdateFolder.class) @RequestBody UpdateFolderForm updateFolderForm,
			BindingResult bindingResult){
		
		UpdateFolderResult updateFolderResult = 
				mapper.map(updateFolderForm, UpdateFolderResult.class);

		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			updateFolderResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateFolderResult);
		}
		
		String serviceName = "gallery";
	
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
										.host(provider.getIpAddr(serviceName))
										.port(provider.getPort(serviceName))
										.path(new StringBuilder()
												.append(APP_NAME)
												.append("/folder/")
												.append(updateFolderForm.getFolder().getFolderId())
												.toString())
										.build();

		//TODO setUserId form http session.
		updateFolderForm.setUserId("00000001");
		
		updateFolderResult.setFolder(restTemplate.exchange(uriComponents.toUri(), 
				HttpMethod.PUT, new HttpEntity<UpdateFolderForm>(updateFolderForm), 
				Folder.class).getBody());

		updateFolderResult.setRequestContextPath(getFrontendServerUri().toString());
		
		return ResponseEntity.status(HttpStatus.OK).body(updateFolderResult);

	}

	@RequestMapping(method=RequestMethod.DELETE, value="/gallery/folders/{folderId}")
	public ResponseEntity<DeleteFolderResult> deleteFolder(
			@Validated DeleteFolderForm deleteFolderForm, BindingResult bindingResult){
		
		DeleteFolderResult deleteFolderResult = 
				mapper.map(deleteFolderForm, DeleteFolderResult.class);
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			deleteFolderResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.OK).body(deleteFolderResult);
		}
	
		String serviceName = "gallery";

		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/folder/")
											.append(deleteFolderForm.getFolderId())
											.toString())
									.build();

		deleteFolderResult.setFolder(restTemplate.exchange(uriComponents.toUri(), 
				HttpMethod.DELETE, new HttpEntity<DeleteFolderForm>(deleteFolderForm), 
				Folder.class).getBody());
		
		return ResponseEntity.status(HttpStatus.OK).body(deleteFolderResult);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			value = "/gallery/media/{mediaId:[0-9]+}/{fileName:[0-9a-zA-Z.]+}")
	public String getMedia(@PathVariable String mediaId, @PathVariable String fileName){
		String mediaType = downloadHelper.getMediaType(fileName);
		return new StringBuilder()
				.append("forward:/gallery/")
				.append(mediaType)
				.append("/")
				.append(mediaId)
				.append("/")
				.append(fileName)
				.toString();
	}	

	@RequestMapping(method=RequestMethod.POST, value="/gallery/upload")
	public ResponseEntity<UploadFileResult> uploadFile(
			@Validated UploadFileForm uploadFileForm, BindingResult bindingResult){
		
		UploadFileResult uploadFileResult = new UploadFileResult();
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			uploadFileResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uploadFileResult);
		}
		
		MultipartFile multipartFile = uploadFileForm.getUploadFile();
		
		try {
			//TODO get Userid from session.
			Media media = galleryContentsUploadHelper.createMedia(
					multipartFile, "00000001", uploadFileForm.getFolderId());

			String serviceName = "gallery";

			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
			UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/media/new")
											.toString())
									.build();
			uploadFileResult.setMedia(restTemplate.postForObject(
					uriComponents.toUri(), media, Media.class));
			uploadFileResult.setRequestContextPath(getFrontendServerUri().toString());
		} catch (BusinessException e) {
			List<String> messages = new ArrayList<String>();
			uploadFileResult.setMessages(messages);
			messages.add(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uploadFileResult);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(uploadFileResult);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/gallery/media",
			consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<DeleteMediaResult> deleteMedia(@RequestBody 
			@Validated DeleteMediaForm deleteMediaForm, BindingResult bindingResult){
		
		DeleteMediaResult deleteMediaResult = new DeleteMediaResult();
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			deleteMediaResult.setMessages(messages);
			for(FieldError fieldError: bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteMediaResult);
		}

		String serviceName = "gallery";

		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = null;
		UriComponents uriComponents = null;
		
		List<Photo> photographs = new ArrayList<Photo>();
		deleteMediaResult.setPhotographs(photographs);
		List<Movie> movies = new ArrayList<Movie>();
		deleteMediaResult.setMovies(movies);
		for(Photo photo : deleteMediaForm.getPhotographs()){
			uriComponentsBuilder = UriComponentsBuilder.newInstance();
			uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/photo/")
											.append(photo.getPhotoId())
											.toString())
									.build();
			photographs.add(restTemplate.exchange(
					uriComponents.toUri(), HttpMethod.DELETE, null, Photo.class).getBody());
		}
		for(Movie movie: deleteMediaForm.getMovies()){
			uriComponentsBuilder = UriComponentsBuilder.newInstance();
			uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
									.host(provider.getIpAddr(serviceName))
									.port(provider.getPort(serviceName))
									.path(new StringBuilder()
											.append(APP_NAME)
											.append("/movie/")
											.append(movie.getMovieId())
											.toString())
									.build();
			movies.add(restTemplate.exchange(
					uriComponents.toUri(), HttpMethod.DELETE, null, Movie.class).getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(deleteMediaResult);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/gallery/media",
			consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> downloadMedia(@RequestBody @Validated DownloadMediaForm downloadMediaForm, 
			BindingResult bindingResult, RedirectAttributes redirectAttributes) 
					throws FileDownloadException{
		
		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			throw new FileDownloadException(messages);
		}
		
		String serviceName = "gallery-batch";
		
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(new StringBuilder()
						.append(APP_NAME)
						.append("/gallery/archive")
						.toString())
				.build();
		
		String accessKey = restTemplate.postForObject(
				uriComponents.toUri(), downloadMediaForm, String.class);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("accessKey", accessKey);
		uriComponentsBuilder = UriComponentsBuilder.newInstance();
		uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
				.host(provider.getIpAddr(serviceName))
				.port(provider.getPort(serviceName))
				.path(new StringBuilder()
						.append(APP_NAME)
						.append("/gallery/archive")
						.toString())
				.queryParams(params)
				.build();
		

		return ResponseEntity.ok().body(uriComponents.toString());

	}
	
	@ExceptionHandler(FileDownloadException.class)
	public ResponseEntity<List<String>> handleBindingResultError(
			final FileDownloadException exception){
		return new ResponseEntity<List<String>>(exception.getMessages(), 
				HttpStatus.BAD_REQUEST);
	}
	
	private URI getFrontendServerUri(){
		String serviceName = "frontend";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme(PROTOCOL)
								.host(provider.getIpAddr(serviceName))
								.port(provider.getPort(serviceName))
								.build();
		return uriComponents.toUri();
	}
}