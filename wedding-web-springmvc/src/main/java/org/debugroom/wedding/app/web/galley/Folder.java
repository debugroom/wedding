package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.debugroom.wedding.app.web.galley.CreateFolderForm.CreateFolder;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Folder implements Serializable{

	public Folder(){
	}
	
	@NotNull(groups = {Default.class})
	@Size(min=12, max=12, groups = {Default.class, CreateFolder.class})
	@Pattern(regexp = "[0-9]*", groups = {Default.class, CreateFolder.class})
	private String folderId;

	@NotNull(groups = {Default.class, CreateFolder.class})
	@Size(min=1, max=256, groups = {Default.class, CreateFolder.class})
	private String folderName;
	
}
