package org.debugroom.wedding.app.web.helper;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.debugroom.wedding.domain.entity.gallery.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoDownloadHelper {

	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	
	private static final String FILE_SEPALATOR = System.getProperty("file.separator");

	public Path getDownloadVideoPath(Movie movie){
		return  Paths.get(new StringBuilder()
				.append(galleryRootDirectory)
				.append(FILE_SEPALATOR)
				.append(movie.getFilePath())
				.toString());
	}

}
