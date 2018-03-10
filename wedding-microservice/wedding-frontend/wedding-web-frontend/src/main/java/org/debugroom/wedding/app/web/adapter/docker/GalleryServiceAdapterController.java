package org.debugroom.wedding.app.web.adapter.docker;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.FileDownloadException;
import org.debugroom.wedding.app.model.UserSearchCriteria;
import org.debugroom.wedding.app.model.UserSearchResult;
import org.debugroom.wedding.app.model.gallery.CreateFolderForm;
import org.debugroom.wedding.app.model.gallery.CreateFolderResult;
import org.debugroom.wedding.app.model.gallery.DeleteFolderForm;
import org.debugroom.wedding.app.model.gallery.DeleteFolderResult;
import org.debugroom.wedding.app.model.gallery.DeleteMediaForm;
import org.debugroom.wedding.app.model.gallery.DeleteMediaResult;
import org.debugroom.wedding.app.model.gallery.Folder;
import org.debugroom.wedding.app.model.gallery.GalleryPortalResource;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm;
import org.debugroom.wedding.app.model.gallery.UpdateFolderResult;
import org.debugroom.wedding.app.model.gallery.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm.UpdateFolder;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.app.model.User;

@Controller
public class GalleryServiceAdapterController {

	private static final String PROTOCOL = "http";
	private static final String APP_NAME = "api/v1";

	@Value("${server.contextPath}")
	private String contextPath;

	@Inject
	Mapper mapper;
	
	@Inject
	ConnectPathProvider provider;

	@Inject
	ImageDownloadHelper downloadHelper;
	
	@InitBinder
	public void initBinderForDate(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	public String gallery(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		if(!userId.equals(customUserDetails.getUser().getUserId())){
			model.addAttribute("errorCode", "9000");
			return "common/error";
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		model.addAttribute("user", customUserDetails.getUser());
		model.addAttribute(restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/portal/{userId}")
						.toString(), provider).expand(userId).toUri(),
				GalleryPortalResource.class));
		return "gallery/portal";
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		User[] users = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/viewers")
						.toString(), provider)
				.expand(userSearchCriteria.getFolderId()).toUri(), User[].class);
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		User[] users = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/no-viewers")
						.toString(), provider)
				.expand(userSearchCriteria.getFolderId()).toUri(), User[].class);
		userSearchResult.setUsers(Arrays.asList(users));

		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/gallery/folder/new",
			consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateFolderResult> createFolder(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
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
	
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		//TODO setUserId form http session.
		createFolderForm.setUserId(customUserDetails.getUser().getUserId());
		createFolderResult.setFolder(restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/new")
						.toString(), provider).toUri(), createFolderForm, Folder.class));
		createFolderResult.setRequestContextPath(getFrontendServerUri().toString());
		return ResponseEntity.status(HttpStatus.OK).body(createFolderResult);
	}

	@RequestMapping(method=RequestMethod.PUT, value="/gallery/folders/{folderId}")
	public ResponseEntity<UpdateFolderResult> updateFolder(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		//TODO setUserId form http session.
		updateFolderForm.setUserId(customUserDetails.getUser().getUserId());
		updateFolderResult.setFolder(restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}")
						.toString(), provider)
				.expand(updateFolderForm.getFolder().getFolderId()).toUri(),
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		deleteFolderResult.setFolder(restTemplate.exchange(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}")
						.toString(), provider)
				.expand(deleteFolderForm.getFolderId()).toUri(),
				HttpMethod.DELETE, new HttpEntity<DeleteFolderForm>(deleteFolderForm), 
				Folder.class).getBody());
		return ResponseEntity.status(HttpStatus.OK).body(deleteFolderResult);
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		List<Photo> photographs = new ArrayList<Photo>();
		deleteMediaResult.setPhotographs(photographs);
		List<Movie> movies = new ArrayList<Movie>();
		deleteMediaResult.setMovies(movies);
		for(Photo photo : deleteMediaForm.getPhotographs()){
			photographs.add(restTemplate.exchange(
					RequestBuilder.buildUriComponents(serviceName, 
							new StringBuilder()
							.append(APP_NAME)
							.append("/photo/{photoId}")
							.toString(), provider)
					.expand(photo.getPhotoId()).toUri(), 
					HttpMethod.DELETE, null, Photo.class).getBody());
		}
		for(Movie movie: deleteMediaForm.getMovies()){
			movies.add(restTemplate.exchange(
						RequestBuilder.buildUriComponents(serviceName, 
							new StringBuilder()
							.append(APP_NAME)
							.append("/movie/{movieId}")
							.toString(), provider)
					.expand(movie.getMovieId()).toUri(), 
					HttpMethod.DELETE, null, Movie.class).getBody());
		}
		return ResponseEntity.status(HttpStatus.OK).body(deleteMediaResult);
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
