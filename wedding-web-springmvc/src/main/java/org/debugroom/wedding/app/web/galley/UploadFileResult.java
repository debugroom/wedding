package org.debugroom.wedding.app.web.galley;

import java.util.List;

import org.debugroom.wedding.domain.gallery.model.Media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class UploadFileResult {

	public UploadFileResult(){
	}

	private Media media;
	private String requestContextPath;
	private List<String> messages;

}
