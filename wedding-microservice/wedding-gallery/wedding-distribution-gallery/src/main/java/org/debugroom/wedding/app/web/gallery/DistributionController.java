package org.debugroom.wedding.app.web.gallery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.debugroom.wedding.app.web.helper.VideoDownloadHelper;
import org.debugroom.wedding.domain.service.gallery.GalleryService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DistributionController {

	@Inject
	GalleryService galleryService;
	
	@Inject
	VideoDownloadHelper videoDownloadHelper;
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET, value="/movie/preview/{movieId:[0-9]+}/{fileName:[.a-zA-Z0-9]+}")
	public ResponseEntity<byte[]> getPreview(@PathVariable String movieId) throws IOException{
		
		Path path = null;
		byte[] image = null;
		try{
			path = videoDownloadHelper.getDownloadVideoPath(galleryService.getMovie(movieId));
			image = Files.readAllBytes(path);
		}catch (Exception e){
			log.info(this.getClass().getName() + "IOException file : " + path.getFileName() + ": size : " + image.length);
			throw e;
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(image.length);
		
		return new ResponseEntity<byte[]>(image, httpHeaders, HttpStatus.OK);

	}

}
