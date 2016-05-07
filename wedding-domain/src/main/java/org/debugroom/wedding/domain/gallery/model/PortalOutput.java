package org.debugroom.wedding.domain.gallery.model;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Folder;
import org.debugroom.wedding.domain.model.entity.Movie;
import org.debugroom.wedding.domain.model.entity.Photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PortalOutput {

	private List<Photo> photographs;
	private List<Photo> randomPhotographs;
	private List<Movie> movies;
	private List<Folder> folders;
	
}
