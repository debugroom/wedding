package org.debugroom.wedding.app.web.galley;

import java.util.List;

import org.debugroom.wedding.domain.model.entity.Folder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteFolderResult {

	public DeleteFolderResult(){
	}
	
	Folder folder;
	List<String> messages;
	
}
