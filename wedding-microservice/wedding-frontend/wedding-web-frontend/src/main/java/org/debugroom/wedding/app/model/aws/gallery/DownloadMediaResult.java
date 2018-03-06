package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DownloadMediaResult implements Serializable{

	private static final long serialVersionUID = -7776898710869281789L;

	public DownloadMediaResult(){
	}

	List<Photo> photographs;
	List<Movie> movies;
	List<String> messages;
	
}
