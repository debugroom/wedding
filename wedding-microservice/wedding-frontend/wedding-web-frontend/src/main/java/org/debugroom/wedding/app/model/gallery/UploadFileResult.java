package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UploadFileResult implements Serializable{

	private static final long serialVersionUID = 9048658465270087979L;

	public UploadFileResult(){
	}
	
	private Media media;
	private String requestContextPath;
	private List<String> messages;

}
