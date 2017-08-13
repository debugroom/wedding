package org.debugroom.wedding.app.web.gallery;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.debugroom.wedding.app.web.gallery.helper.DownloadHelper;

@RestController
@RequestMapping("/api/v1")
public class GalleryController {
	
	@Inject
	DownloadHelper downloadHelper;
	
	@CrossOrigin
	@RequestMapping(value="/gallery/archive", method=RequestMethod.GET,
			params="accessKey")
	public HttpEntity<InputStreamResource> getArchive(@RequestParam String accessKey) 
			throws FileNotFoundException{
		
		File file = downloadHelper.getDownloadFile(accessKey);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		httpHeaders.setContentLength(file.length());
		httpHeaders.setContentDispositionFormData("attachment", file.getName());

		InputStreamResource inputStreamResource = new InputStreamResource(
				new BufferedInputStream(new FileInputStream(file)));
		
		return new ResponseEntity<InputStreamResource>(
				inputStreamResource, httpHeaders, HttpStatus.OK);
		
	}

}

