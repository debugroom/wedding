package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UploadFilePreProcessResult implements Serializable{

	private static final long serialVersionUID = 1460157614112900855L;

	public UploadFilePreProcessResult(){
	}
	
	private DirectUploadAuthorization imageUploadAuthorization;
	private DirectUploadAuthorization movieUploadAuthorization;
	private List<String> messages;

}
