package org.debugroom.wedding.app.web.profile;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

	private static final long serialVersionUID = 5030915253383688158L;

	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String userId;
	@NotNull
	@Size(min=1, max=50)
	private String userName;
	@NotNull
	@Size(min=1, max=1024)
	@Pattern(regexp = "[-¥.¥/a-zA-Z0-9]*")
	private String imageFilePath;
	@UploadFileNotEmpty
	@UploadFileMaxSize(10000000)
	private MultipartFile newImageFile;
	@NotNull
	@Size(min=1, max=32)
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*")
	private String loginId;
	@Valid
	private Address address;
	@Valid
	private List<Credential> credentials;
	@Valid
	private List<Email> emails;
	

}
