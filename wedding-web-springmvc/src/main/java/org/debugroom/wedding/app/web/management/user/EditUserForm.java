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
public class EditUserForm implements Serializable{

	private static final long serialVersionUID = -8949437312594560366L;
	
	public static interface GetUser{}
	public static interface UpdateUser{}

	@NotNull(groups = {Default.class, GetUser.class, UpdateUser.class})
	@Size(groups = {Default.class, GetUser.class, UpdateUser.class})
	@Pattern(regexp = "[0-9]*", groups = {Default.class, GetUser.class, UpdateUser.class})
	private String userId;
	@NotNull(groups = {UpdateUser.class})
	@Size(min=1, max=50, groups = {UpdateUser.class})
	private String userName;
	@NotNull(groups = {UpdateUser.class})
	@Size(min=1, max=1024)
	@Pattern(regexp = "[-¥.¥/a-zA-Z0-9]*", groups = {UpdateUser.class})
	private String imageFilePath;
	@UploadFileNotEmpty(groups = {UpdateUser.class})
	@UploadFileMaxSize(value = 10000000, groups = {UpdateUser.class})
	private MultipartFile newImageFile;
	@NotNull(groups = {UpdateUser.class})
	@Size(min=1, max=32, groups = {UpdateUser.class})
	@Pattern(regexp = "[a-zA-Z0-9¥.¥-]*", groups = {UpdateUser.class})
	private String loginId;
	@NotNull(groups = {UpdateUser.class})
	@Min(value = 0, groups = UpdateUser.class)
	@Max(value = 9, groups = UpdateUser.class)
	private Integer authorityLevel;
	@Valid
	private Address address;
	@Valid
	private List<Email> emails;
	@Valid
	private List<Credential> credentials;
	@Valid
	private List<Group> grps; 
	
}
