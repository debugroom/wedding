package org.debugroom.wedding.app.web.adapter.docker.aws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.debugroom.wedding.app.model.aws.gallery.AwsResource;
import org.debugroom.wedding.app.model.aws.gallery.DirectUploadAuthorization;
import org.debugroom.wedding.app.model.aws.gallery.UploadFileForm;
import org.debugroom.wedding.app.model.aws.gallery.UploadFilePreProcessResult;
import org.debugroom.wedding.app.model.gallery.UploadFileResult;
import org.debugroom.wedding.app.web.adapter.docker.GalleryServiceAdapterController;
import org.debugroom.wedding.app.web.helper.aws.GalleryContentsUploadHelper;
import org.debugroom.wedding.app.web.security.CustomUserDetails;

@Controller
public class GalleryServiceAdapterAwsController {

	private static final String PATH_PREFIX = "aws/";
	
	@Value("${gallery.distribution.server}")
	private String distributionServerUrl;

	@Value("${gallery.root.directory}")
	private String galleryRootPath;
	
	@Inject
	GalleryServiceAdapterController parentController;
	
	@Inject
	GalleryContentsUploadHelper uploadHelper;
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/{userId:[0-9]+}")
	public String gallery(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		model.addAttribute("awsResource", AwsResource.builder()
				.distributionServerUrl(distributionServerUrl)
				.galleryRootPath(galleryRootPath)
				.build());
		return new StringBuilder(PATH_PREFIX)
				.append(parentController.gallery(userId, model, customUserDetails))
				.toString();
	}

	@RequestMapping(method=RequestMethod.GET, value="/gallery/upload/authorization")
	public ResponseEntity<UploadFilePreProcessResult> uploadFilePreProcess(
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		
		String imageFileUploadDirectory = uploadHelper.createImageDirectUploadDirectories(
				customUserDetails.getUser().getUserId());
		String movieFileUploadDirectory = uploadHelper.createMovieDirectUploadDirectories(
				customUserDetails.getUser().getUserId());
		
		UploadFilePreProcessResult uploadFilePreProcessResult = new UploadFilePreProcessResult();
		uploadFilePreProcessResult.setImageUploadAuthorization(
				uploadHelper.createDirectUploadAuthorization(imageFileUploadDirectory));
		uploadFilePreProcessResult.setMovieUploadAuthorization(
				uploadHelper.createDirectUploadAuthorization(movieFileUploadDirectory));
		
		return ResponseEntity.status(HttpStatus.OK).body(uploadFilePreProcessResult);

	}

}
