package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.app.model.profile.PortalResource;
import org.debugroom.wedding.app.model.profile.UpdateUserResult;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.domain.entity.User;

@Controller
public class ProfileServiceAdapterController {

	private static final String APP_NAME = "api/v1";

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
	MessageSource messageSource;
	
	@ModelAttribute
	public EditProfileForm setUpEditProfileForm(){
		return EditProfileForm.builder().build();
	}
	
	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value="/profile/{userId:[0-9]+}", method=RequestMethod.GET)
	public String profile(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			model.addAttribute("errorCode", "9000");
			return "common/error";
		}
		String serviceName = "profile";
		RestTemplate restTemplate = new RestTemplate();
		PortalResource portalResource = PortalResource.builder() 
				.user(restTemplate.getForObject(
							RequestBuilder.buildUriComponents(serviceName, 
									new StringBuilder()
									.append(APP_NAME)
									.append("/profile/{userId}")
									.toString(), provider).expand(userId).toUri(),
							User.class))
				.build();
		model.addAttribute(portalResource);
		return "profile/portal";
	}

	@RequestMapping(value="/profile/{userId:[0-9]+}", method=RequestMethod.POST)
	public String profile(@PathVariable String userId, 
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@Validated EditProfileForm editProfileForm, 
			BindingResult bindingResult, Model model, Locale locale){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			model.addAttribute("errorCode", "9000");
			return "common/error";
		}
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
		String serviceName = "profile";
		String serviceName2 = "message";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.exchange(RequestBuilder.buildUriComponents(serviceName2, 
				new StringBuilder().append(APP_NAME)
				.append("/user/{userId}")
				.toString(), provider)
				.expand(userId).toUri(), HttpMethod.PUT, 
				new HttpEntity<org.debugroom.wedding.app.model.profile.User>
		(editProfileForm.getUser()), User.class);
		model.addAttribute("updateResult", restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
							.append(APP_NAME)
							.append("/profile/{userId}")
							.toString(), provider).expand(userId).toUri(),
				HttpMethod.PUT, new HttpEntity<EditProfileForm>(editProfileForm), 
				UpdateUserResult.class).getBody());

		return "profile/result";
	}

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/profile/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getProfileImage(@PathVariable String userId,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			if(customUserDetails.getUser().getAuthorityLevel() != 9){
				return ResponseEntity.badRequest().body(null);
			}
		}
		String serviceName = "profile";
		RestTemplate restTemplate = new RestTemplate();
		User user = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/profile/{userId}")
						.toString(), provider).expand(userId).toUri(), User.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getProfileImage(user);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

}
