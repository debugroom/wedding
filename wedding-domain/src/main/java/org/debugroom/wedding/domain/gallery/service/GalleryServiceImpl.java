package org.debugroom.wedding.domain.gallery.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.debugroom.framework.common.exception.SystemException;
import org.debugroom.wedding.domain.common.DomainProperties;
import org.debugroom.wedding.domain.gallery.model.FolderDraft;
import org.debugroom.wedding.domain.gallery.model.PortalOutput;
import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Movie;
import org.debugroom.wedding.domain.model.entity.Photo;
import org.debugroom.wedding.domain.model.entity.User;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolder;
import org.debugroom.wedding.domain.model.entity.UserRelatedFolderPK;
import org.debugroom.wedding.domain.repository.common.FindFolderUsersByFolderId;
import org.debugroom.wedding.domain.repository.common.FindNotFolderUsersByFolderId;
import org.debugroom.wedding.domain.repository.common.FindRelateFolderByUserId;
import org.debugroom.wedding.domain.repository.common.FolderRepository;
import org.debugroom.wedding.domain.repository.common.UserRepository;
import org.debugroom.wedding.domain.repository.photo.FindRelatedFolderWithViewablePhotoByUserId;
import org.debugroom.wedding.domain.repository.photo.FindRelatedPhotoByFolderId;
import org.debugroom.wedding.domain.repository.photo.FindViewablePhotoByUserId;
import org.debugroom.wedding.domain.repository.photo.PhotoRepository;

@Transactional
@Service("galleryService")
public class GalleryServiceImpl implements GalleryService{

	@Inject
	DomainProperties domainProperties;
			
	@Inject
	PhotoRepository photoRepository;
	
	@Inject
	FolderRepository folderRepository;

	@Inject
	UserRepository userRepository;

	@Override
	public List<Photo> getRandomPhotographs(List<Photo> photographs) {
		List<Photo> randomPhotographs = new ArrayList<Photo>();
		if(0 != photographs.size()){
			Random random = new Random();
			Map<Integer, Object> duplicateMap = new HashMap<Integer, Object>();
			for(int i = 0; i < domainProperties.getNumberOfPhotoForGallery() ; i++){
				int randomNumber = random.nextInt(photographs.size());
				if(duplicateMap.containsKey(randomNumber)){
					i--;
				}else{
					randomPhotographs.add(photographs.get(randomNumber));
					duplicateMap.put(randomNumber, null);
				}
			}
		}
		return randomPhotographs;
	}

	@Override
	public List<Photo> getPhotographsByUser(User user) {
		List<Photo> photographs = photoRepository.findAll(
			FindViewablePhotoByUserId.builder()
										.userId(user.getUserId())
										.build());
		return photographs;
	}

	@Override
	public List<Movie> getMovies(String userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Folder> getFoldersWithPhotographs(String userId) {
		return folderRepository.findAll(
				FindRelatedFolderWithViewablePhotoByUserId.builder()
					.userId(userId)
					.build());
	}

	@Override
	public PortalOutput getPortalOutput(User user) {
		List<Photo> photographs = getPhotographsByUser(user);
		return PortalOutput.builder()
							.folders(getFolders(user))
							.randomPhotographs(getRandomPhotographs(photographs))
							.build();
	}

	@Override
	public BufferedImage getPhotograph(String photoId) {
		Photo photo = photoRepository.findOne(photoId);
		String imageFilePath = new StringBuilder()
						.append(domainProperties.getGalleryImageRootDirectory())
						.append("/")
						.append(photo.getFilePath())
						.toString();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageFilePath));
		} catch (IOException e) {
			throw new SystemException("galleryService.error.0001", e);
		}
		return image;
	}

	@Override
	public BufferedImage getPhotoThumbnail(String photoId) {
		Photo photo = photoRepository.findOne(photoId);
		String imageThumbnailFilePath = new StringBuilder()
				.append(domainProperties.getGalleryImageRootDirectory())
				.append("/")
				.append(photo.getThumbnailFilePath())
				.toString();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(imageThumbnailFilePath));
		} catch (IOException e) {
			throw new SystemException("galleryService.error.0001", e);
		}
		return image;
	}

	@Override
	public List<Folder> getFolders(User user) {
		return folderRepository.findAll(FindRelateFolderByUserId.builder()
				.userId(user.getUserId()).build());
	}

	@Override
	public List<Photo> getPhotographsByFolder(Folder folder) {
		return photoRepository.findAll(FindRelatedPhotoByFolderId.builder()
				.folderId(folder.getFolderId()).build());
	}

	@Override
	public List<User> getUsers(Folder folder, boolean isViewers) {
		if(isViewers){
		return userRepository.findAll(FindFolderUsersByFolderId.builder()
				.folderId(folder.getFolderId()).build());
		}else{
		return userRepository.findAll(FindNotFolderUsersByFolderId.builder()
				.folderId(folder.getFolderId()).build());
		}
	}

	@Override
	public Folder createFolder(FolderDraft folderDraft) {
		
		Folder folder = folderDraft.getFolder();
		
		String sequence = new StringBuilder()
								.append("000000000000")
								.append(folderRepository.count())
								.toString();
		String newFolderId = StringUtils.substring(sequence, 
				sequence.length() -12, sequence.length()); 

		folder.setFolderId(newFolderId);
		folder.setParentFolderId("000000000000");
		folder.setUsr(userRepository.findOne(folderDraft.getUserId()));
		folder.setLastUpdatedDate(new Timestamp(
				Calendar.getInstance().getTimeInMillis()));
		
		Set<UserRelatedFolder> userRelatedFolders = new HashSet<UserRelatedFolder>();
		
		for(User user : folderDraft.getUsers()){
			userRelatedFolders.add(UserRelatedFolder.builder()
									.id(UserRelatedFolderPK.builder()
											.userId(user.getUserId())
											.folderId(newFolderId)
											.build())
									.lastUpdatedDate(new Timestamp(
											Calendar.getInstance().getTimeInMillis()))
									.ver(0)
									.build());
		}
		folder.setUserRelatedFolders(userRelatedFolders);
		return folderRepository.save(folder);
	}

	@Override
	public Folder deleteFolder(String folderId) {
		Folder folder = folderRepository.findOne(folderId);
		folderRepository.delete(folder);
		return folder;
	}

	@Override
	public Folder updateFolder(FolderDraft folderDraft) {
		Folder folder = folderDraft.getFolder();
		
		Folder updateTargetFolder = folderRepository.findOne(folder.getFolderId());
		
		if(!updateTargetFolder.getFolderName().equals(folder.getFolderName())){
			updateTargetFolder.setFolderName(folder.getFolderName());
			updateTargetFolder.setLastUpdatedDate(
					new Timestamp(Calendar.getInstance().getTimeInMillis()));
		}
		
		if(null != folderDraft.getCheckedAddUsers()){
			for(User viewUser : folderDraft.getCheckedAddUsers()){
				if(null != viewUser){
					updateTargetFolder.addUserRelatedFolder(UserRelatedFolder.builder()
																.id(UserRelatedFolderPK
																	.builder()
																	.folderId(folder.getFolderId())
																	.userId(viewUser.getUserId())
																	.build())
															.lastUpdatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()))
															.ver(0)
															.build());
				}
			}
		}
		
		if(null != folderDraft.getCheckedDeleteUsers()){
			for(User excludeUser : folderDraft.getCheckedDeleteUsers()){
				if(null != excludeUser){
					Iterator<UserRelatedFolder> iterator = updateTargetFolder.getUserRelatedFolders().iterator();
					while(iterator.hasNext()){
						UserRelatedFolder userRelatedFolder = iterator.next();
						if(excludeUser.getUserId().equals(userRelatedFolder.getId().getUserId())){
							iterator.remove();
						}
					}
				}
			}
		}
		return updateTargetFolder;
	}

}
