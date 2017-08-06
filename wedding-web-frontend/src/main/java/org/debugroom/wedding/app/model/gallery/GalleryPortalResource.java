package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GalleryPortalResource implements Serializable{

	private static final long serialVersionUID = 1085194311928785966L;

	public GalleryPortalResource(){
	}

	private List<Photo> photographs;
	private List<Photo> randomPhotographs;
	private List<Movie> movies;
	private List<Folder> folders;
	
}
