package org.debugroom.wedding.app.web.adapter.docker.aws;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.debugroom.wedding.app.web.adapter.docker.GalleryServiceAdapterController;
import org.debugroom.wedding.app.web.security.CustomUserDetails;

@Profile("aws")
@Controller
public class GalleryServiceAdapterAwsController {

	private static final String PATH_PREFIX = "aws/";
	
	@Value("${gallery.distribution.server}")
	private String distributionServerUrl;

	@Value("${gallery.root.Path}")
	private String galleryRootPath;
	
	@Inject
	GalleryServiceAdapterController parentController;
	
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

}
