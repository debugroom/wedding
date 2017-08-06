package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateDownloadFileForm implements Serializable{

	private static final long serialVersionUID = 4344017831467845375L;

	public CreateDownloadFileForm(){
	}
	
	@Valid
	private List<Media> media;
	
}
