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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import org.debugroom.wedding.app.model.gallery.DownloadMediaForm;
import org.debugroom.wedding.app.model.gallery.Folder;
import org.debugroom.wedding.app.model.gallery.GalleryPortalResource;
import org.debugroom.wedding.app.model.gallery.Media;
import org.debugroom.wedding.app.model.gallery.Movie;
import org.debugroom.wedding.app.model.gallery.MovieSearchResult;
import org.debugroom.wedding.app.model.gallery.Photo;
import org.debugroom.wedding.app.model.gallery.PhotoSearchResult;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm;
import org.debugroom.wedding.app.model.gallery.UpdateFolderResult;
import org.debugroom.wedding.app.model.gallery.UploadFileForm;
import org.debugroom.wedding.app.model.gallery.UploadFileResult;
import org.debugroom.wedding.app.model.gallery.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm.UpdateFolder;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.helper.ImageDownloadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.debugroom.wedding.domain.entity.User;

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
	
	@Inject
	GalleryContentsUploadHelper galleryContentsUploadHelper;
	
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
		RestTemplate restTemplate = new RestTemplate();
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
		Photo target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/photo/{photoId}")
						.toString(), provider)
				.expand(photo.getPhotoId()).toUri(), Photo.class);
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
		Photo target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/photo/{photoId}")
						.toString(), provider)
				.expand(photo.getPhotoId()).toUri(), Photo.class);		
		
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
		Photo[] photographs = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/photographs")
						.toString(), provider)
				.expand(folder.getFolderId()).toUri(), Photo[].class);
		photoSearchResult.setPhotographs(Arrays.asList(photographs));
		photoSearchResult.setRequestContextPath(getFrontendServerUri().toString());
		return ResponseEntity.status(HttpStatus.OK).body(photoSearchResult);
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/movies/{folderId}")
	public ResponseEntity<MovieSearchResult> getMovies(@Validated Folder folder,
			BindingResult bindingResult){
		
		MovieSearchResult movieSearchResult = mapper.map(folder, MovieSearchResult.class);

		if(bindingResult.hasErrors()){
			List<String> messages = new ArrayList<String>();
			movieSearchResult.setMessages(messages);
			for(FieldError fieldError : bindingResult.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(movieSearchResult);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		Movie[] movies = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/movies")
						.toString(), provider)
				.expand(folder.getFolderId()).toUri(), Movie[].class);
		movieSearchResult.setMovies(Arrays.asList(movies));
		movieSearchResult.setRequestContextPath(getFrontendServerUri().toString());
		return ResponseEntity.status(HttpStatus.OK).body(movieSearchResult);
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
		RestTemplate restTemplate = new RestTemplate();
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
	
		RestTemplate restTemplate = new RestTemplate();
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
		RestTemplate restTemplate = new RestTemplate();
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
		RestTemplate restTemplate = new RestTemplate();
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
	
	@RequestMapping(method=RequestMethod.GET,
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
			RestTemplate restTemplate = new RestTemplate();
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
		String accessKey = restTemplate.postForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/gallery/archive")
						.toString(), provider).toUri(), downloadMediaForm, String.class);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("accessKey", accessKey);
		return ResponseEntity.ok().body(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/gallery/archive")
						.toString(), provider, params).toString()
				);
	}
	
	@ExceptionHandler(FileDownloadException.class)
	public ResponseEntity<List<String>> handleBindingResultError(
			final FileDownloadException exception){
		return new ResponseEntity<List<String>>(exception.getMessages(), 
				HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/gallery/movie/{movieId:[0-9]+}/{fileName:[.a-zA-Z0-9]+}")
	public String getMovie(
			@Validated Movie movie, @PathVariable String fileName, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "common/error";
		}
		String serviceName = "gallery-distribution";
		return new StringBuilder().append("redirect:")
				.append(RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/movie/preview/{movieId}/")
						.append(fileName)
						.toString(), provider)
						.expand(movie.getMovieId()).toString())
				.toString();
	} 
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/gallery/movie-thumbnail/{movieId:[0-9]+}",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
	@ResponseBody
	public ResponseEntity<BufferedImage> getMovieThumbnail(
			@Validated Movie movie, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = new RestTemplate();
		Movie target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/movie/{movieId}")
						.toString(), provider)
				.expand(movie.getMovieId()).toUri(), Movie.class);
		BufferedImage image = null;
		try {
			image = downloadHelper.getGalleryThumbnailImage(target);
		} catch(BusinessException e){
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok().body(image);
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
