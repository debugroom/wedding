package org.debugroom.wedding.app.batch.gallery.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DownloadMedia implements Serializable{

	private static final long serialVersionUID = 3892825493357498124L;

	public DownloadMedia(){
	}

	private List<Photo> photographs;
	private List<Movie> movies;

}
