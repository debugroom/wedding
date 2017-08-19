package org.debugroom.wedding.app.web.gallery;

import org.dozer.Mapper;
import org.dozer.MappingException;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.app.model.gallery.GalleryPortalResource;
import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.model.gallery.FolderDraft;
import org.debugroom.wedding.domain.model.gallery.Media;
import org.debugroom.wedding.domain.service.gallery.GalleryService;


@RestController
@RequestMapping("/api/v1")
public class GalleryRestController {

	@Inject
	Mapper mapper;
	
	@Inject
	GalleryService galleryService;
	
	@RequestMapping(method=RequestMethod.GET, value="/portal/{userId}")
	public GalleryPortalResource getGalleryPortalResource(@ModelAttribute 
			org.debugroom.wedding.app.model.gallery.User user){
		User inputUser = mapper.map(user, User.class);
		return GalleryPortalResource.builder()
				.randomPhotographs(galleryService.getRandomPhotographs(inputUser))
				.randomMovies(galleryService.getRandomMovies(inputUser))
				.folders(galleryService.getFolders(inputUser))
				.build();
	}

	@RequestMapping(method=RequestMethod.GET, value="/photo/{photoId}")
	public Photo getPhotograph(@PathVariable String photoId){
		return galleryService.getPhotograph(photoId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/folder/{folderId}/photographs")
	public List<Photo> getPhotoGraphsByFolderId(@PathVariable String folderId){
		return galleryService.getPhotographsByFolder(
				Folder.builder().folderId(folderId).build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/folder/{folderId}/movies")
	public List<Movie> getMoviesByFoleder(@PathVariable String folderId){
		return galleryService.getMoviesByFolder(
				Folder.builder().folderId(folderId).build());
	}

	@RequestMapping(method=RequestMethod.GET, value="/folder/{folderId}/viewers")
	public List<User> getUsersByFolderId(@PathVariable String folderId){
		return galleryService.getUsers(
				Folder.builder().folderId(folderId).build(), true);
	}

	@RequestMapping(method=RequestMethod.GET, value="/folder/{folderId}/no-viewers")
	public List<User> getNotUsersByFolderId(@PathVariable String folderId){
		return galleryService.getUsers(
				Folder.builder().folderId(folderId).build(), false);
	}

	@RequestMapping(method=RequestMethod.POST, value="/folder/new")
	public Folder createFolder(@RequestBody 
			org.debugroom.wedding.app.model.gallery.FolderDraft folderDraft){
		return galleryService.createFolder(mapper.map(
				folderDraft, FolderDraft.class));
	}

	@RequestMapping(method=RequestMethod.PUT, value="/folder/{folderId}")
	public Folder updateFolder(@RequestBody
			org.debugroom.wedding.app.model.gallery.FolderDraft folderDraft){
		return galleryService.updateFolder(mapper.map(
				folderDraft, FolderDraft.class));
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/folder/{folderId}")
	public Folder deleteFolder(@PathVariable String folderId){
		return galleryService.deleteFolder(folderId);
	}

	@RequestMapping(method=RequestMethod.POST, value="/media/new")
	public Media saveMedia(@RequestBody
			org.debugroom.wedding.app.model.gallery.Media media){
		try {
			return galleryService.saveMedia(mapper.map(media, Media.class));
		} catch (BusinessException e) {
			return saveMedia(media);
		}
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/photo/{photoId}")
	public Photo deletePhoto(@PathVariable String photoId){
		return galleryService.deletePhotograph(photoId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/movie/{movieId}")
	public Movie getMovie(@PathVariable String movieId){
		return galleryService.getMovie(movieId);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/movie/{movieId}")
	public Movie deleteMovie(@PathVariable String movieId){
		return galleryService.deleteMovie(movieId);
	}

}
