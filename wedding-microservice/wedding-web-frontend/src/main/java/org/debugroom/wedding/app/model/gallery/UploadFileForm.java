package org.debugroom.wedding.app.model.gallery;

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
public class UploadFileForm implements Serializable{

	public UploadFileForm(){
	}
	
	@NotNull
	@Size(min=12, max=12)
	@Pattern(regexp = "[0-9]*")
	private String folderId;
	
	@UploadFileNotEmpty
	private MultipartFile uploadFile;
	
}
