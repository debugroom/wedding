package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.dozer.MappingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.app.model.profile.EditProfileForm;
import org.debugroom.wedding.app.model.profile.UpdateResult;
import org.debugroom.wedding.app.web.EnvProperties;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.helper.ImageUploadHelper;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.domain.entity.User;

@Controller
public class ServiceAdpaterController {

	private static final String APP_NAME = "api/v1";

	@ModelAttribute
	public EditProfileForm setUpForm(){
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
	EnvProperties envProperties;
	
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
			BindingResult bindingResult, Model model){

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
				e.printStackTrace();
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
			value = "/profile/image/{userId}/{imageFileExtention}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getImage(@PathVariable String userId){
		String serviceName = "profile";
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme("http")
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
		BufferedImage image = downloadHelper.getProfileImage(user);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
	
	}
}
