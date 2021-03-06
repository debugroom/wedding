package org.debugroom.wedding.domain.service.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.MovieRelatedFolderPK;
import org.debugroom.wedding.domain.entity.gallery.Photo;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.PhotoRelatedFolderPK;
import org.debugroom.wedding.domain.entity.gallery.User;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolder;
import org.debugroom.wedding.domain.entity.gallery.UserRelatedFolderPK;
import org.debugroom.wedding.domain.model.gallery.FolderDraft;
import org.debugroom.wedding.domain.model.gallery.Media;
import org.debugroom.wedding.domain.repository.jpa.gallery.FolderRepository;
import org.debugroom.wedding.domain.repository.jpa.gallery.GalleryUserRepository;
import org.debugroom.wedding.domain.repository.jpa.gallery.MovieRepository;
import org.debugroom.wedding.domain.repository.jpa.gallery.PhotoRepository;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindFolderUsersByFolderId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindNotFolderUsersByFolderId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindRelatedFolderByUserId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindRelatedFolderWithViewablePhotoByUserId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindRelatedMovieByFolderId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindRelatedPhotoByFolderId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindViewableMovieByUserId;
import org.debugroom.wedding.domain.repository.jpa.spec.gallery.FindViewablePhotoByUserId;
import org.debugroom.wedding.domain.service.common.DateUtil;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("galleryService")
public class GalleryServiceImpl implements GalleryService{
	
	@Inject
	GalleryDomainProperties properties;
	
	@Inject
	PhotoRepository photoRepository;

	@Inject
	MovieRepository movieRepository;
	
	@Inject
	FolderRepository folderRepository;

	@Inject
	GalleryUserRepository galleryUserRepository;
	
	@Override
	public List<Photo> getPhotographsByUser(User user) {
		return photoRepository.findAll(
				FindViewablePhotoByUserId.builder()
											.userId(user.getUserId())
											.build());
	}

	@Override
	public List<Movie> getMovies(String userId) {
		return movieRepository.findAll(
				FindViewableMovieByUserId.builder()
				.userId(userId).build());
	}

	@Override
	public List<Folder> getFoldersWithPhotographs(String userId) {
		return folderRepository.findAll(
				FindRelatedFolderWithViewablePhotoByUserId
				.builder().userId(userId).build());
	}

	@Override
	public List<Folder> getFolders(User user) {
		return folderRepository.findAll(FindRelatedFolderByUserId.builder()
				.userId(user.getUserId()).build());
	}

	@Override
	public List<Photo> getPhotographsByFolder(Folder folder) {
		return photoRepository.findAll(FindRelatedPhotoByFolderId.builder()
				.folderId(folder.getFolderId()).build());
	}

	@Override
	public List<Movie> getMoviesByFolder(Folder folder){
		return movieRepository.findAll(FindRelatedMovieByFolderId.builder()
				.folderId(folder.getFolderId()).build());
	}

	@Override
	public String getPhotographUrl(String photoId) {
		return photoRepository.findOne(photoId).getFilePath();
	}

	@Override
	public String getPhotoThumbnailUrl(String photoId) {
		return photoRepository.findOne(photoId).getThumbnailFilePath();
	}

	@Override
	public List<User> getUsers(Folder folder, boolean isViewers) {
		if(isViewers){
			return galleryUserRepository.findAll(FindFolderUsersByFolderId.builder()
					.folderId(folder.getFolderId()).build());
		}else{
			return galleryUserRepository.findAll(FindNotFolderUsersByFolderId.builder()
					.folderId(folder.getFolderId()).build());
		}
	}

	@Override
	public Folder createFolder(FolderDraft folderDraft) {
		Folder folder = folderDraft.getFolder();
		String newFolderId = getNewFolderId();
		folder.setFolderId(newFolderId);
		folder.setParentFolderId("000000000000");
		folder.setUsr(galleryUserRepository.findOne(folderDraft.getUserId()));
		folder.setLastUpdatedDate(DateUtil.getCurrentDate());
		
		Set<UserRelatedFolder> userRelatedFolders = new HashSet<UserRelatedFolder>();
		
		for(User user : folderDraft.getUsers()){
			userRelatedFolders.add(UserRelatedFolder.builder()
									.id(UserRelatedFolderPK.builder()
												.userId(user.getUserId())
												.folderId(newFolderId)
												.build())
									.lastUpdatedDate(DateUtil.getCurrentDate())
									.ver(0)
									.build());
		}
		folder.setUserRelatedFolders(userRelatedFolders);
		return folderRepository.save(folder);
	}

	@Override
	public Folder updateFolder(FolderDraft folderDraft) {
		Folder folder = folderDraft.getFolder();
		
		Folder updateTargetFolder = folderRepository.findOne(folder.getFolderId());
		
		if(!updateTargetFolder.getFolderName().equals(folder.getFolderName())){
			updateTargetFolder.setFolderName(folder.getFolderName());
			updateTargetFolder.setLastUpdatedDate(DateUtil.getCurrentDate());
		}
		
		if(null != folderDraft.getCheckedAddUsers()){
			for(User viewUser : folderDraft.getCheckedAddUsers()){
				if(null != viewUser){
					updateTargetFolder.addUserRelatedFolder(
							UserRelatedFolder.builder()
								.id(UserRelatedFolderPK.builder()
										.folderId(folder.getFolderId())
										.userId(viewUser.getUserId())
										.build())
								.lastUpdatedDate(DateUtil.getCurrentDate())
								.ver(0)
								.build());
				}
			}
		}
		
		if(null != folderDraft.getCheckedDeleteUsers()){
			for(User excludeUser : folderDraft.getCheckedDeleteUsers()){
				if(null != excludeUser){
					Iterator<UserRelatedFolder> iterator = 
							updateTargetFolder.getUserRelatedFolders().iterator();
					while(iterator.hasNext()){
						UserRelatedFolder userRelatedFolder = iterator.next();
						if(excludeUser.getUserId().equals(
								userRelatedFolder.getId().getUserId())){
							iterator.remove();
						}
					}
				}
			}
		}
		return updateTargetFolder;
	}

	@Override
	public Folder deleteFolder(String folderId) {
		Folder folder = folderRepository.findOne(folderId);
		folderRepository.delete(folder);
		return folder;
	}

	@Override
	@Retryable(value={DataIntegrityViolationException.class},
	maxAttempts=10000, backoff=@Backoff(delay=500))
	public Media saveMedia(Media media) throws BusinessException {
		switch(media.getMediaType()){
			case PHOTOGRAPH:
				Photo photo = Photo.builder()
									.filePath(media.getFilePath())
									.thumbnailFilePath(media.getThumbnailFilePath())
									.photoRelatedFolders(new HashSet<PhotoRelatedFolder>())
									.build();
				savePhotograph(photo, media.getFolderId());
				media.setMediaId(photo.getPhotoId());
				break;
			case MOVIE:
				Movie movie = Movie.builder()
						.filePath(media.getFilePath())
						.thumbnailFilePath(media.getThumbnailFilePath())
						.movieRelatedFolders(new HashSet<MovieRelatedFolder>())
						.build();
				saveMovie(movie, media.getFolderId());
				media.setMediaId(movie.getMovieId());
				break;
			default:
				break;
		}
		return media;
	}

	@Override
	public synchronized Photo savePhotograph(Photo photo, String folderId) 
			throws BusinessException {
		String newPhotoId = getNewPhotoId();
		photo.setPhotoId(newPhotoId);
		photo.setLastUpdatedDate(DateUtil.getCurrentDate());
		photo.setIsControled(false);
		photo.addPhotoRelatedFolder(
				PhotoRelatedFolder.builder()
					.id(PhotoRelatedFolderPK.builder()
							.folderId(folderId)
							.photoId(newPhotoId)
							.build())
					.lastUpdatedDate(DateUtil.getCurrentDate())
					.ver(0)
					.build());
		return photoRepository.saveAndFlush(photo);
	}

	@Override
	@Transactional
	public Movie saveMovie(Movie movie, String folderId) {
		String newMovieId = getNewMovieId();
		movie.setMovieId(newMovieId);
		movie.setLastUpdatedDate(DateUtil.getCurrentDate());
		movie.setIsControled(false);
		movie.addMovieRelatedFolder(
				MovieRelatedFolder.builder()
				.id(MovieRelatedFolderPK.builder()
						.folderId(folderId)
						.movieId(newMovieId)
						.build())
				.lastUpdatedDate(DateUtil.getCurrentDate())
				.ver(0)
				.build());
		return movieRepository.saveAndFlush(movie);
	}

	@Override
	public List<Photo> getRandomPhotographs(User user) {
		List<Photo> photographs = getPhotographsByUser(user);
		List<Photo> randomPhotographs = new ArrayList<Photo>();
		if(0 != photographs.size()){
			Random random = new Random();
			Map<Integer, Object> duplicateMap = new HashMap<Integer, Object>();
			for(int i = 0; i < properties.getNumberOfPhotoForGallery() ; i++){
				int randomNumber = random.nextInt(photographs.size());
				if(duplicateMap.containsKey(randomNumber)){
					if(duplicateMap.size() != photographs.size()){
						i--;
					}
				}else{
					randomPhotographs.add(photographs.get(randomNumber));
					duplicateMap.put(randomNumber, null);
				}
			}
		}
		return randomPhotographs;
	}

	@Override
	public List<Movie> getRandomMovies(User user){
		List<Movie> movies = getMovies(user.getUserId());
		List<Movie> randomMovies = new ArrayList<Movie>();
		if(0 != movies.size()){
			Random random = new Random();
			Map<Integer, Object> duplicateMap = new HashMap<Integer, Object>();
			for(int i = 0; i < properties.getNumberOfMovieForGallery(); i++){
				int randomNumber = random.nextInt(movies.size());
				if(duplicateMap.containsKey(randomNumber)){
					if(duplicateMap.size() != movies.size()){
						i--;
					}
				}else{
					randomMovies.add(movies.get(randomNumber));
					duplicateMap.put(randomNumber, null);
				}
			}
		}
		return randomMovies;
	}

	private String getNewFolderId(){
		Folder folder = folderRepository.findTopByOrderByFolderIdDesc();
		StringBuilder stringBuilder = new StringBuilder().append("000000000000");
		if(null != folder){
			if(!"000000000000".equals(folder.getFolderId())){
				stringBuilder.append(Integer.parseInt(StringUtils.stripStart(
						folder.getFolderId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
		}
		String sequence	= stringBuilder.toString();
		return StringUtils.substring(
				sequence, sequence.length()-12, sequence.length());
	}

	private String getNewPhotoId(){
		Photo photo = photoRepository.findTopByPhotoIdLikeOrderByPhotoIdDesc("0%");
		StringBuilder stringBuilder = new StringBuilder().append("0000000000");
		if(null != photo){
			if(!"0000000000".equals(photo.getPhotoId())){
				stringBuilder.append(Integer.parseInt(StringUtils.stripStart(
										photo.getPhotoId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
		}
		String sequence = stringBuilder.toString();
		return StringUtils.substring(
				sequence, sequence.length()-10, sequence.length());
	}

	private String getNewMovieId(){
		Movie movie = movieRepository.findTopByMovieIdLikeOrderByMovieIdDesc("0%");
		StringBuilder stringBuilder = new StringBuilder().append("0000000000");
		if(null != movie){
			if(!"0000000000".equals(movie.getMovieId())){
				stringBuilder.append(Integer.parseInt(StringUtils.stripStart(
										movie.getMovieId(), "0"))+1);
			}else{
				stringBuilder.append("1");
			}
		}
		String sequence = stringBuilder.toString();
		return StringUtils.substring(
				sequence, sequence.length()-10, sequence.length());
	}

	@Override
	public Photo getPhotograph(String photoId) {
		return photoRepository.findOne(photoId);
	}

	@Override
	public Photo deletePhotograph(String photoId) {
		Photo photo = getPhotograph(photoId);
		photoRepository.delete(photo);
		return photo;
	}

	@Override
	public Movie deleteMovie(String movieId) {
		Movie movie = getMovie(movieId);
		movieRepository.delete(movie);
		return movie;
	}

	@Override
	public Movie getMovie(String movieId) {
		return movieRepository.findOne(movieId);
	}

}
