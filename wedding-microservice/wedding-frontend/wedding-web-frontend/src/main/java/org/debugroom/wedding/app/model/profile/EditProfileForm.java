package org.debugroom.wedding.app.model.profile;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import org.debugroom.framework.spring.webmvc.fileupload.validation.annotation.UploadFileMaxSize;
import org.debugroom.framework.spring.webmvc.fileupload.validation.annotation.UploadFileNotEmpty;
import org.debugroom.framework.spring.webmvc.fileupload.validation.annotation.UploadFileRequired;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class EditProfileForm implements Serializable{

	private static final long serialVersionUID = 4530559091948139771L;

	@Valid
	private User user;
	@UploadFileNotEmpty
	@UploadFileMaxSize(10000000)
	private MultipartFile newImageFile;
	
}
