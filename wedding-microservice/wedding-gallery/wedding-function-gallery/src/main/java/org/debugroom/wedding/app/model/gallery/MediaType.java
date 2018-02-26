package org.debugroom.wedding.app.model.gallery;

import org.apache.commons.lang3.StringUtils;

public enum MediaType {
	PHOTOGRAPH,
	MOVIE,
	OTHER,
	ZIP;
	
	public static MediaType getMediaType(String keyName){
		String extension = StringUtils.substringAfterLast(keyName, ".");
		switch(extension.toLowerCase()){
		case "jpg":
			return MediaType.PHOTOGRAPH;
		case "jpeg":
			return MediaType.PHOTOGRAPH;
		case "png":
			return MediaType.PHOTOGRAPH;
		case "gif":
			return MediaType.PHOTOGRAPH;
		case "mov":
			return MediaType.MOVIE;
		case "mp4":
			return MediaType.MOVIE;
		case "wmv":
			return MediaType.MOVIE;
	    default:
		}
    	return MediaType.OTHER;
	}
}
