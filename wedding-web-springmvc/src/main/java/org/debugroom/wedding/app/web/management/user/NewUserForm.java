package org.debugroom.wedding.app.web.management.user;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.Valid;

import org.debugroom.framework.spring.webmvc.fileupload.validation.annotation.UploadFileMaxSize;
import org.debugroom.framework.spring.webmvc.fileupload.validation.annotation.UploadFileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class NewUserForm implements Serializable{

	private static final long serialVersionUID = 1L;

	public static interface ConfirmUser{}
	public static interface SaveUser{}

	@NotNull(groups = {SaveUser.class})
	@Size(groups = {SaveUser.class})
	@Pattern(regexp = "[0-9]*", groups = {SaveUser.class})
	private String userId;

	@NotNull(groups = {ConfirmUser.class, SaveUser.class})
	@Size(min=1, max=50, groups = {ConfirmUser.class, SaveUser.class})
	private String userName;

	@NotNull(groups = {SaveUser.class})
	@Size(min=1, max=1024, groups = SaveUser.class)
	@Pattern(regexp = "[-¥.¥/a-zA-Z0-9]*", groups = {SaveUser.class})
	private String imageFilePath;

	@UploadFileNotEmpty(groups = {ConfirmUser.class})
	@UploadFileMaxSize(value = 10000000, groups = {ConfirmUser.class})
	private MultipartFile newImageFile;

	@NotNull(groups = {ConfirmUser.class, SaveUser.class})
	@Size(min=1, max=32, groups = {ConfirmUser.class, SaveUser.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {ConfirmUser.class, SaveUser.class})
	private String loginId;

	@NotNull(groups = {ConfirmUser.class, SaveUser.class})
	@Min(value = 0, groups = {ConfirmUser.class, SaveUser.class})
	@Max(value = 9, groups = {ConfirmUser.class, SaveUser.class})
	private Integer authorityLevel;

	@Valid
	private Address address;

	@Valid
	private List<Email> emails;

	@Valid
	private List<Credential> credentials;	
	
}
