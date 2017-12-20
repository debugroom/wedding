package org.debugroom.wedding.app.web.adapter.docker.dev;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.debugroom.wedding.app.web.adapter.docker.GalleryServiceAdapterController;
import org.debugroom.wedding.app.web.security.CustomUserDetails;

@Profile("local")
@Controller
public class GalleryServiceAdapterDevController {

	private static final String PATH_PREFIX = "";
	
	@Inject
	GalleryServiceAdapterController parentController;
	
	@RequestMapping(method=RequestMethod.GET, value="/gallery/{userId:[0-9]+}")
	public String gallery(@PathVariable String userId, Model model,
			@AuthenticationPrincipal CustomUserDetails customUserDetails){
		return new StringBuilder(PATH_PREFIX)
				.append(parentController.gallery(userId, model, customUserDetails))
				.toString();
	}

}
