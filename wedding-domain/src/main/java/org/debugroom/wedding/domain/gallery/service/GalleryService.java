package org.debugroom.wedding.domain.gallery.service;

import java.awt.image.BufferedImage;
import java.util.List;

import org.debugroom.wedding.domain.gallery.model.Media;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.gallery.model.FolderDraft;
import org.debugroom.wedding.domain.gallery.model.PortalOutput;
import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Movie;
import org.debugroom.wedding.domain.model.entity.Photo;
import org.debugroom.wedding.domain.model.entity.User;

public interface GalleryService {

	public List<Photo> getPhotographsByUser(User userId);

	public List<Movie> getMovies(String userId);

	public List<Folder> getFoldersWithPhotographs(String userId);
	
	public List<Folder>  getFolders(User user);

	public List<Photo>  getPhotographsByFolder(Folder folder);

	public PortalOutput getPortalOutput(User user);
	
	public List<Photo> getRandomPhotographs(List<Photo> photographs);

	public BufferedImage getPhotograph(String photoId);
	
	public BufferedImage getPhotoThumbnail(String photoId);

	public List<User> getUsers(Folder folder, boolean isViewers);
	
	public Folder createFolder(FolderDraft folderDraft);

	public Folder deleteFolder(String folderId);

	public Folder updateFolder(FolderDraft folderDraft);
	
	public Media saveMedia(Media media) throws BusinessException;

	public Photo savePhotograph(Photo photo, String folderId);
	
	public Movie saveMovie(Movie movie);
	
	public void createPhotoThumbnail(Photo photo, Media media) throws BusinessException;
	
}
