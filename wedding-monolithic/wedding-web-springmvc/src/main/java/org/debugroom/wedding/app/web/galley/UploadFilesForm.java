package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class UploadFilesForm implements Serializable{

	private static final long serialVersionUID = 6227989266609214257L;
	
	public UploadFilesForm(){
	}

	@Valid
	List<UploadFileForm> uploadFileForms;
	
}
