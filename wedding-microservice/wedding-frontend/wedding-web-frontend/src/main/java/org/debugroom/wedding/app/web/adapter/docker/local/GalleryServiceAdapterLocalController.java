package org.debugroom.wedding.app.web.adapter.docker.local;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.gallery.Media;
import org.debugroom.wedding.app.model.gallery.UploadFileForm;
import org.debugroom.wedding.app.model.gallery.UploadFileResult;
import org.debugroom.wedding.app.web.adapter.docker.GalleryServiceAdapterController;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;

@Profile("local")
@Controller
public class GalleryServiceAdapterLocalController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";
	private static final String PATH_PREFIX = "";
	
	@Inject
	GalleryServiceAdapterController parentController;
	
	@Inject
	ConnectPathProvider provider;

	@Inject
	GalleryContentsUploadHelper galleryContentsUploadHelper;
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/{userId:[0-9]+}")
	public String gallery(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		return new StringBuilder(PATH_PREFIX)
				.append(parentController.gallery(userId, model, customUserDetails))
				.toString();
	}

	@RequestMapping(method=RequestMethod.POST, value="/gallery/upload")
	public ResponseEntity<UploadFileResult> uploadFile(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
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
					multipartFile, customUserDetails.getUser().getUserId(), 
					uploadFileForm.getFolderId());

			String serviceName = "gallery";
			RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
			uploadFileResult.setMedia(restTemplate.postForObject(
					RequestBuilder.buildUriComponents(serviceName, 
							new StringBuilder()
							.append(APP_NAME)
							.append("/media/new")
							.toString(), provider).toUri(), media, Media.class));
			uploadFileResult.setRequestContextPath(getFrontendServerUri().toString());
		} catch (BusinessException e) {
			List<String> messages = new ArrayList<String>();
			uploadFileResult.setMessages(messages);
			messages.add(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uploadFileResult);
		}
		return ResponseEntity.status(HttpStatus.OK).body(uploadFileResult);
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
