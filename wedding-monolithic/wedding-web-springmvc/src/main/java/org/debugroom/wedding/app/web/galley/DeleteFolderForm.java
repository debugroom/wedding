package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteFolderForm implements Serializable{

	private static final long serialVersionUID = -5866918813493548772L;

	public DeleteFolderForm(){
	}
	
	@NotNull(groups = {Default.class})
	@Size(min=12, max=12, groups = {Default.class})
	@Pattern(regexp = "[0-9]*", groups = {Default.class})
	private String folderId;
	
}
