package org.debugroom.wedding.app.web.adapter.docker.aws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.FileDownloadException;
import org.debugroom.wedding.app.model.aws.gallery.AwsResource;
import org.debugroom.wedding.app.model.aws.gallery.DownloadMediaResult;
import org.debugroom.wedding.app.model.aws.gallery.Movie;
import org.debugroom.wedding.app.model.aws.gallery.MovieSearchResult;
import org.debugroom.wedding.app.model.aws.gallery.UploadFileForm;
import org.debugroom.wedding.app.model.aws.gallery.UploadFilePreProcessResult;
import org.debugroom.wedding.app.model.aws.gallery.UploadFileResult;
import org.debugroom.wedding.app.model.aws.gallery.Photo;
import org.debugroom.wedding.app.model.aws.gallery.PhotoSearchResult;
import org.debugroom.wedding.app.model.gallery.DownloadMediaForm;
import org.debugroom.wedding.app.model.gallery.Folder;
import org.debugroom.wedding.app.model.gallery.Media;
import org.debugroom.wedding.app.web.adapter.docker.GalleryServiceAdapterController;
import org.debugroom.wedding.app.web.adapter.docker.provider.ConnectPathProvider;
import org.debugroom.wedding.app.web.helper.aws.GalleryContentsDownloadHelper;
import org.debugroom.wedding.app.web.helper.aws.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;
import org.debugroom.wedding.app.web.util.RequestBuilder;
import org.dozer.Mapper;

@Controller
public class GalleryServiceAdapterAwsController {

	private static final String PATH_PREFIX = "aws/";
	private static final String APP_NAME = "api/v1";
	
	@Value("${gallery.distribution.server}")
	private String distributionServerUrl;

	@Value("${gallery.root.directory}")
	private String galleryRootPath;
	
	@Value("${server.contextPath}")
	private String contextPath;
	
	@Inject
	GalleryServiceAdapterController parentController;
	
	@Inject
	GalleryContentsUploadHelper uploadHelper;
	
	@Inject
	GalleryContentsDownloadHelper downloadHelper;

	@Inject
	ConnectPathProvider provider;
	
	@Inject
	Mapper mapper;
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/{userId:[0-9]+}")
	public String gallery(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		model.addAttribute("awsResource", AwsResource.builder()
				.distributionServerUrl(distributionServerUrl)
				.galleryRootPath(galleryRootPath)
				.build());
		return new StringBuilder(PATH_PREFIX)
				.append(parentController.gallery(userId, model, customUserDetails))
				.toString();
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/upload/authorization/{folderId:[0-9]+}")
	public ResponseEntity<UploadFilePreProcessResult> uploadFilePreProcess(
			@AuthenticationPrincipal CustomUserDetails customUserDetails,
			@PathVariable("folderId") String folderId){
		
		String userId = customUserDetails.getUser().getUserId();
		String imageFileUploadDirectory = uploadHelper
				.createImageDirectUploadDirectories(userId);
		String movieFileUploadDirectory = uploadHelper
				.createMovieDirectUploadDirectories(userId);
		
		UploadFilePreProcessResult uploadFilePreProcessResult = new UploadFilePreProcessResult();
		uploadFilePreProcessResult.setImageUploadAuthorization(
				uploadHelper.createDirectUploadAuthorization(
						imageFileUploadDirectory, folderId, userId));
		uploadFilePreProcessResult.setMovieUploadAuthorization(
				uploadHelper.createDirectUploadAuthorization(
						movieFileUploadDirectory, folderId, userId));
		
		return ResponseEntity.status(HttpStatus.OK).body(uploadFilePreProcessResult);

	}

	/*
	@RequestMapping(method=RequestMethod.POST, value="/gallery/upload")
	public ResponseEntity<UploadFileResult> uploadFileInfo(
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

		try {
			Media media = uploadHelper.createMedia(uploadFileForm.getFileName(), 
					customUserDetails.getUser().getUserId(), uploadFileForm.getFolderId());
			String serviceName = "gallery";
			RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
			uploadFileResult.setMedia(restTemplate.postForObject(
					RequestBuilder.buildUriComponents(serviceName, 
							new StringBuilder()
							.append(APP_NAME)
							.append("/media/new")
							.toString(), provider).toUri(), media, Media.class));
			uploadFileResult.setThumbnailPresignedUrl(
					downloadHelper.getPresignedUrl(media.getThumbnailFilePath()).toString());
		} catch (BusinessException e) {
			List<String> messages = new ArrayList<String>();
			uploadFileResult.setMessages(messages);
			messages.add(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uploadFileResult);
		}
		return ResponseEntity.status(HttpStatus.OK).body(uploadFileResult);
	}
	 */

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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Photo[] photographs = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/photographs")
						.toString(), provider)
				.expand(folder.getFolderId()).toUri(), Photo[].class);
		photoSearchResult.setPhotographs(Arrays.asList(photographs));
//		downloadHelper.setPhotoThumbnailPresignedUrls(photoSearchResult.getPhotographs());
		photoSearchResult.setRequestContextPath(contextPath);
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
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Movie[] movies = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/folder/{folderId}/movies")
						.toString(), provider)
				.expand(folder.getFolderId()).toUri(), Movie[].class);
		movieSearchResult.setMovies(Arrays.asList(movies));
//		downloadHelper.setMovieThumbnailPresignedUrls(movieSearchResult.getMovies());
		movieSearchResult.setRequestContextPath(contextPath);
		return ResponseEntity.status(HttpStatus.OK).body(movieSearchResult);
		
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/photo/{photoId:[0-9]+}")
	public ResponseEntity<Photo> getPhoto(@Validated Photo photo, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Photo target = restTemplate.getForObject( 
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/photo/{photoId}")
						.toString(), provider)
				.expand(photo.getPhotoId()).toUri(), Photo.class);
		target.setPresignedUrl(downloadHelper.getPresignedUrl(target.getFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
 
	@RequestMapping(method=RequestMethod.GET, value="/gallery/photo/{photoId:[0-9]+}/thumbnail")
	public ResponseEntity<Photo> getPhotoThumbnail(@Validated Photo photo, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Photo target = restTemplate.getForObject( 
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/photo/{photoId}")
						.toString(), provider)
				.expand(photo.getPhotoId()).toUri(), Photo.class);
		target.setThumbnailPresignedUrl(downloadHelper.getPresignedUrl(target.getThumbnailFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/movie/{movieId:[0-9]+}")
	public ResponseEntity<Movie> getMovie(@Validated Movie movie, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Movie target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/movie/{movieId}")
						.toString(), provider)
				.expand(movie.getMovieId()).toUri(), Movie.class);
		target.setPresignedUrl(downloadHelper.getPresignedUrl(target.getFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/movie/{movieId:[0-9]+}/thumbnail")
	public ResponseEntity<Movie> getMovieThumbnail(@Validated Movie movie, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Movie target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/movie/{movieId}")
						.toString(), provider)
				.expand(movie.getMovieId()).toUri(), Movie.class);
		target.setThumbnailPresignedUrl(downloadHelper.getPresignedUrl(target.getThumbnailFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/download/photo/{photoId:[0-9]+}")
	public ResponseEntity<Photo> getDownloadPhoto(@Validated Photo photo, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Photo target = restTemplate.getForObject( 
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/photo/{photoId}")
						.toString(), provider)
				.expand(photo.getPhotoId()).toUri(), Photo.class);
		target.setPresignedUrl(downloadHelper.getDownloadPresignedUrl(target.getFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}
 
	@RequestMapping(method=RequestMethod.GET, value="/gallery/download/movie/{movieId:[0-9]+}")
	public ResponseEntity<Movie> getDownloadMovie(@Validated Movie movie, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return ResponseEntity.badRequest().body(null);
		}
		String serviceName = "gallery";
		RestTemplate restTemplate = RequestBuilder.getMDCLoggableRestTemplate();
		Movie target = restTemplate.getForObject(
				RequestBuilder.buildUriComponents(serviceName, 
						new StringBuilder()
						.append(APP_NAME)
						.append("/movie/{movieId}")
						.toString(), provider)
				.expand(movie.getMovieId()).toUri(), Movie.class);
		target.setPresignedUrl(downloadHelper.getDownloadPresignedUrl(target.getFilePath()).toString());
		return ResponseEntity.status(HttpStatus.OK).body(target);
	}

}
