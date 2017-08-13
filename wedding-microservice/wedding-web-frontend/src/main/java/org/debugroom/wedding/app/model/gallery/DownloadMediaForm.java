package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DownloadMediaForm implements Serializable{

	private static final long serialVersionUID = 372900092701980634L;
	
	public DownloadMediaForm(){
	}

	@Valid
	private List<Photo> photographs;
	@Valid
	private List<Movie> movies;

}
