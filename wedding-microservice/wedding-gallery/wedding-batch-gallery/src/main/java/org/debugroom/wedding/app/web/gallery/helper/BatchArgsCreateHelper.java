package org.debugroom.wedding.app.web.gallery.helper;

import org.springframework.stereotype.Component;

import org.debugroom.wedding.app.batch.gallery.model.DownloadMedia;
import org.debugroom.wedding.app.batch.gallery.model.Movie;
import org.debugroom.wedding.app.batch.gallery.model.Photo;

@Component
public class BatchArgsCreateHelper {

	public String getPhotoArgs(DownloadMedia downloadMedia){
		StringBuilder stringBuilder = new StringBuilder();
		int index = 0;
		for(Photo photo : downloadMedia.getPhotographs()){
			if(index != 0){
				stringBuilder.append(",");
			}
			stringBuilder.append(photo.getPhotoId());
			index++;
		}
		return stringBuilder.toString();
	}
	
	public String getMovieArgs(DownloadMedia downloadMedia){
		StringBuilder stringBuilder = new StringBuilder();
		int index = 0;
		for(Movie movie : downloadMedia.getMovies()){
			if(index != 0){
				stringBuilder.append(",");
			}
			stringBuilder.append(movie.getMovieId());
			index++;
		}
		return stringBuilder.toString();
	}

}
