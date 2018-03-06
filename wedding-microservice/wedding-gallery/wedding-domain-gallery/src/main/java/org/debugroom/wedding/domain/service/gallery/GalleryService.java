package org.debugroom.wedding.domain.service.gallery;

import java.util.List;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.model.gallery.FolderDraft;
import org.debugroom.wedding.domain.model.gallery.Media;

public interface GalleryService {

	public Photo getPhotograph(String photoId);
	
	public List<Photo> getRandomPhotographs(User user);
	
	public List<Photo> getPhotographsByUser(User user);
	
	public List<Movie> getRandomMovies(User user);

	public List<Movie> getMovies(String userId);
	
	public List<Folder> getFoldersWithPhotographs(String userId);
	
	public List<Folder> getFolders(User user);
	
	public List<Photo> getPhotographsByFolder(Folder folder);
	
	public List<Movie> getMoviesByFolder(Folder folder);

	public String getPhotographUrl(String photoId);
	
	public String getPhotoThumbnailUrl(String photoId);
	
	public List<User> getUsers(Folder folder, boolean isViewers);
	
	public Folder createFolder(FolderDraft folderDraft);

	public Folder updateFolder(FolderDraft folderDraft);

	public Folder deleteFolder(String folderId);
	
	public Media saveMedia(Media media) throws BusinessException;
	
	public Photo savePhotograph(Photo photo, String folderId) throws BusinessException;
	
	public Movie saveMovie(Movie movie, String folderId);
	
	public Photo deletePhotograph(String photoId);
	
	public Movie deleteMovie(String movieId);
	
	public Movie getMovie(String movieId);
	
	public List<Photo> getPhotographs();

	public List<Movie> getMovies();

}

