package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Builder
@Data
public class MovieSearchResult implements Serializable{

	private static final long serialVersionUID = 385492618373274217L;
	public MovieSearchResult(){
	}
	
	private String folderId;
	private String folderName;
	private String requestContextPath;
	private List<Movie> movies;
	private List<String> messages;
	
}
