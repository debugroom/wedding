package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.model.gallery.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.model.gallery.UpdateFolderForm.UpdateFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Folder implements Serializable{

	private static final long serialVersionUID = 6250266913908338828L;

	public Folder(){
	}

	@NotNull
	@Size(min=12, max=12)
	@Pattern(regexp="[0-9]*")
	private String folderId;
	@NotNull(groups={CreateFolder.class, UpdateFolder.class})
	@Size(min=1, max=256, groups={CreateFolder.class, UpdateFolder.class})
	@Pattern(regexp="^[^ =#$%&./<>?¥^¥~¥[¥]¥(¥)¥¥]+$", groups={CreateFolder.class, UpdateFolder.class})
	private String folderName;
	@Pattern(regexp="[0-9]*")
	private String parentFolderId;
	
}
