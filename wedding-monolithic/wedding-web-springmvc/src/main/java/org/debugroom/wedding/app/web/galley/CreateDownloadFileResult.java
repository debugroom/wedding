package org.debugroom.wedding.app.web.galley;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class CreateDownloadFileResult {

	public CreateDownloadFileResult(){
	}
	
	List<String> messages;
	
}
