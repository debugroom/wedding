package org.debugroom.wedding.app.web.galley;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;

@Component
public class GalleryContentsUploadHelper implements FileUploadHelper{

	@Value("${gallery.image.rootdirectory}")
	private String uploadRootDirectory;
	
	@Value("${gallery.image.original.directory}")
	private String uploadDirectory;

	@Override
	public String saveFile(MultipartFile multipartFile, String userId) throws BusinessException {
		String uploadDirectoryContextPath = new StringBuilder()
									.append(userId)
									.append(uploadDirectory)
									.toString();
		String uploadDirectoryAbsolutePath = new StringBuilder()
									.append(uploadRootDirectory)
									.append("/")
									.append(uploadDirectoryContextPath)
									.toString();
		
		File uploadFile = new File(uploadDirectoryAbsolutePath, multipartFile.getOriginalFilename());
		
		try{
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);
		}catch(IOException e){
			throw new BusinessException("galleryContentsUploadHelper.error.0001", null, userId);
		}
		return new StringBuilder()
				.append(uploadDirectoryContextPath)
				.append("/")
				.append(multipartFile.getOriginalFilename())
				.toString();
	}
	
	
}
