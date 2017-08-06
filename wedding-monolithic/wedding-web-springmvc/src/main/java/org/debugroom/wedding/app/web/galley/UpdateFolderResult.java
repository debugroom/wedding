package org.debugroom.wedding.app.web.galley;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Builder
@Data
public class UpdateFolderResult {

	public UpdateFolderResult(){
	}
	
	Folder folder;
	String requestContextPath;
	List<String> messages;

}
