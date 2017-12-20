package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.portal.AnswerRequest;
import org.debugroom.wedding.app.model.portal.AnswerRequestResult;
import org.debugroom.wedding.app.model.portal.Information;
import org.debugroom.wedding.app.model.portal.PortalResource;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.helper.InformationMessageBodyHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.app.model.Menu;
import org.debugroom.wedding.domain.entity.User;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class PortalServiceAdapterController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";

	@Value("${server.contextPath}")
	private String contextPath;

	@Value("${protocol}")
	private String protocol;
	
	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;
	
	@Inject
	ImageDownloadHelper downloadHelper;

	@Inject
	InformationMessageBodyHelper informationMessageBodyHelper;
	
	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() throws RestClientException, URISyntaxException{
		return "login";
	}
	
	@RequestMapping(value="/portal/{userId:[0-9]+}", method=RequestMethod.GET)
	public String portal(@PathVariable String userId, Model model, 
			@AuthenticationPrincipal CustomUserDetails customUserDetails) 
			throws URISyntaxException{
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			model.addAttribute("errorCode", "9000");
			return "common/error";
		}
		String serviceName = "portal";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		PortalResource portalResource = restTemplate.getForObject(
			RequestBuilder.buildUriComponents(serviceName, 
				new StringBuilder()
				.append(APP_NAME)
				.append("/portal/{userId}")
				.toString(), provider).expand(userId).toUri(), 
			PortalResource.class);
			model.addAttribute(portalResource);
		return "portal/portal";
	}
	

	@RequestMapping(method = RequestMethod.GET,
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE},
			value = "/portal/image/{userId:[0-9]+}/{imageFileExtension}")
	@ResponseBody
	public ResponseEntity<BufferedImage> getPortalImage(@PathVariable String userId,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "portal";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		User user = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/profile/{userId}")
						.toString(), provider).expand(userId).toUri(), 
				User.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getProfileImage(user);
		} catch (BusinessException e) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
	}

	@RequestMapping(value="/information/{infoId:[0-9]+}", method=RequestMethod.GET)
	public String information( 
			@Validated Information information, Model model) 
			throws URISyntaxException{
		String serviceName = "information";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Information resultInformation = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/information/{infoId}")
						.toString(), provider).expand(information.getInfoId()).toUri(),
				Information.class);
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("infoId", information.getInfoId());
		resultInformation.setInfoRootPath(
				RequestBuilder.buildUriComponents(protocol, "frontend", 
						new StringBuilder()
						.append(contextPath)
						.append("/information/body/{infoId}")
						.toString(), provider, uriVariables).toString());
		model.addAttribute(resultInformation);
		return "portal/information";
	}

	@RequestMapping(value="/information/body/{infoId:[0-9]+}", method=RequestMethod.GET,
			produces="text/html;charset=UTF-8")
	@ResponseBody
	public String infomationBody(@PathVariable String infoId){
		String serviceName = "information";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		try {
			return informationMessageBodyHelper.getMessageBody(restTemplate.getForObject(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/information/{infoId}")
								.toString(), provider).expand(infoId).toUri(),
						org.debugroom.wedding.domain.entity.Information.class));
		} catch (RestClientException | BusinessException e) {
			e.printStackTrace();
		}
		return "error";
	}
	

	@RequestMapping(method = RequestMethod.POST, 
			value="/information/{userId:[0-9]+}/{requestId:[0-9]+}/status")
	@ResponseBody
	public ResponseEntity<AnswerRequestResult> answerRequest(@PathVariable String userId,
			@PathVariable String requestId, @RequestBody AnswerRequest answerRequest, 
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "portal";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		paramMap.put("requestId", requestId);
		return ResponseEntity.ok().body(AnswerRequestResult.builder()
				.isApproved(restTemplate.exchange(
						RequestBuilder.buildUriComponents(serviceName, 
								new StringBuilder()
								.append(APP_NAME)
								.append("/{userId}/request/{requestId}")
								.toString(), provider)
						.expand(paramMap).toUri(), HttpMethod.PUT, 
						new HttpEntity<AnswerRequest>(answerRequest), Boolean.class).getBody())
				.build());
	}

	public List<Menu> getUsableMenu(String userId){
		String serviceName = "portal";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Menu[] menu = restTemplate.getForObject(RequestBuilder.buildUriComponents(
				serviceName, new StringBuilder()
				.append(APP_NAME)
				.append("/{userId}/menu").toString(), provider)
				.expand(paramMap).toUri(), Menu[].class);
		return Arrays.asList(menu);
	}

}

