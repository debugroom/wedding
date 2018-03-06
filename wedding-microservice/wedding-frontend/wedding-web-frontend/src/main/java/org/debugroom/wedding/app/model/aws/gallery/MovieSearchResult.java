package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class MovieSearchResult implements Serializable{

	private static final long serialVersionUID = -7658400162796036678L;

	public MovieSearchResult(){
	}
	
	private String folderId;
	private String folderName;
	private String requestContextPath;
	private List<Movie> movies;
	private List<String> messages;

}
