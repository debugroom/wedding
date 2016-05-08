package org.debugroom.wedding.app.web.galley;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria;
import org.debugroom.wedding.app.web.common.model.UserSearchResult;
import org.debugroom.wedding.app.web.common.util.RequestContextUtil;
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria.GetFolderUsers;
import org.debugroom.wedding.app.web.common.model.UserSearchCriteria.GetNotFolderUsers;
import org.debugroom.wedding.app.web.galley.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.web.galley.UpdateFolderForm.UpdateFolder;
import org.debugroom.wedding.domain.gallery.model.FolderDraft;
import org.debugroom.wedding.domain.gallery.service.GalleryService;
import org.debugroom.wedding.domain.model.entity.User;
import org.dozer.Mapper;

@Slf4j
@Controller
public class GalleryController {

	@Inject
	Mapper mapper;
	
	@Inject
	GalleryService galleryService;
	
	@RequestMapping(method = RequestMethod.GET, value="/gallery")
	public String gallery(Model model){
        model.addAttribute(galleryService.getPortalOutput(User.builder().userId("00000000").build()));
		return "gallery/portal";
	}
	
	@RequestMapping(method = RequestMethod.GET, 
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE },
			value="/gallery/photo/{photoId}")
	@ResponseBody
	public BufferedImage getPhoto(@Validated Photo photo, 
			BindingResult bindingResult, HttpServletResponse response){
		if(bindingResult.hasErrors()){
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		return galleryService.getPhotograph(photo.getPhotoId());
	}

	@RequestMapping(method = RequestMethod.GET, 
			headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE },
			value="/gallery/photo-thumbnail/{photoId}")
	@ResponseBody
	public BufferedImage getPhotoThumbnail(@Validated Photo photo, 
			BindingResult bindingResult, HttpServletResponse response){
		if(bindingResult.hasErrors()){
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return null;
		}
		return galleryService.getPhotoThumbnail(photo.getPhotoId());
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/gallery/photographs/{folderId}")
	public ResponseEntity<PhotoSearchResult> getPhotographs(@Validated Folder folder,
			Errors errors, HttpServletRequest request){
		
		PhotoSearchResult photoSearchResult = mapper.map(folder, PhotoSearchResult.class);

		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			photoSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					photoSearchResult);
		}

		try {
			photoSearchResult.setRequestContextPath(
					RequestContextUtil.getRequestContextPath(request));
		} catch (MalformedURLException | URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					photoSearchResult);
		}
		photoSearchResult.setPhotographs(galleryService.getPhotographsByFolder(
				mapper.map(folder, org.debugroom.wedding.domain.model.entity.Folder.class)));
		
		return ResponseEntity.status(HttpStatus.OK).body(photoSearchResult);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/gallery/folder/viewers/{folderId}")
	public ResponseEntity<UserSearchResult> getFolderViewers(
			@Validated(GetFolderUsers.class) UserSearchCriteria userSearchCriteria,
			Errors errors){
		
		UserSearchResult userSearchResult = mapper.map(userSearchCriteria, UserSearchResult.class);
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}
		
		userSearchResult.setUsers(galleryService.getUsers(
				mapper.map(userSearchCriteria, org.debugroom.wedding.domain.model.entity.Folder.class), true));
		
		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);
	}
	@RequestMapping(method = RequestMethod.GET, value="/gallery/folder/no-viewers/{folderId}")
	public ResponseEntity<UserSearchResult> getFolderNoViewers(
			@Validated(GetNotFolderUsers.class) UserSearchCriteria userSearchCriteria,
			Errors errors){
		
		UserSearchResult userSearchResult = mapper.map(userSearchCriteria, UserSearchResult.class);
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			userSearchResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userSearchResult);
		}
		
		userSearchResult.setUsers(galleryService.getUsers(
				mapper.map(userSearchCriteria, org.debugroom.wedding.domain.model.entity.Folder.class), false));
		
		return ResponseEntity.status(HttpStatus.OK).body(userSearchResult);

	}

	@RequestMapping(method = RequestMethod.POST, value="/gallery/folder/new", consumes =  "application/json")
	public ResponseEntity<CreateFolderResult> createFolder(
			@Validated(CreateFolder.class) @RequestBody CreateFolderForm createFolderForm,
			Errors errors, HttpServletRequest request){
		
		CreateFolderResult createFolderResult = mapper.map(createFolderForm, CreateFolderResult.class);
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			createFolderResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createFolderResult);
		}
		
		try {
			createFolderResult.setRequestContextPath(
					RequestContextUtil.getRequestContextPath(request));
		} catch (MalformedURLException | URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createFolderResult);
		}
		
		FolderDraft folderDraft = mapper.map(createFolderForm, FolderDraft.class);
		folderDraft.setUserId("00000000");

		createFolderResult.setFolder(galleryService.createFolder(folderDraft));

		return ResponseEntity.status(HttpStatus.OK).body(createFolderResult);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/gallery/folders/{folderId}")
	public ResponseEntity<UpdateFolderResult> updateFolder(
			@Validated(UpdateFolder.class) @RequestBody UpdateFolderForm updateFolderForm,
			Errors errors, HttpServletRequest request){
		
		UpdateFolderResult updateFolderResult = mapper.map(updateFolderForm, UpdateFolderResult.class);
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			updateFolderResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateFolderResult);
		}
		
		try {
			updateFolderResult.setRequestContextPath(
					RequestContextUtil.getRequestContextPath(request));
		} catch (MalformedURLException | URISyntaxException e) {
			ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updateFolderResult);
		}
		
		updateFolderResult.setFolder(galleryService.updateFolder(
				mapper.map(updateFolderForm, FolderDraft.class)));
		
		return ResponseEntity.status(HttpStatus.OK).body(updateFolderResult);
		
	}

	@RequestMapping(method = RequestMethod.DELETE, value="/gallery/folders/{folderId}")
	public ResponseEntity<DeleteFolderResult> deleteFolder(
			@Validated DeleteFolderForm deleteFolderForm,
			Errors errors){
		
		DeleteFolderResult deleteFolderResult = mapper.map(deleteFolderForm, DeleteFolderResult.class);
		
		if(errors.hasErrors()){
			List<String> messages = new ArrayList<String>();
			deleteFolderResult.setMessages(messages);
			for(FieldError fieldError : errors.getFieldErrors()){
				messages.add(fieldError.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deleteFolderResult);
		}
		
		deleteFolderResult.setFolder(galleryService.deleteFolder(
				deleteFolderForm.getFolderId()));
		
		return ResponseEntity.status(HttpStatus.OK).body(deleteFolderResult);

	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
       log.warn("Returning HTTP 400 Bad Request", e);
	}
}
