package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import org.debugroom.wedding.domain.entity.gallery.Folder;
import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.debugroom.wedding.domain.entity.gallery.Photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class GalleryPortalResource implements Serializable{

	private static final long serialVersionUID = 3073267271530995651L;

	public GalleryPortalResource(){
	}

	private List<Photo> photographs;
	private List<Photo> randomPhotographs;
	private List<Movie> movies;
	private List<Movie> randomMovies;
	private List<Folder> folders;

}
