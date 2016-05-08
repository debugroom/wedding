package org.debugroom.wedding.app.web.galley;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.debugroom.wedding.app.web.galley.CreateFolderForm.CreateFolder;
import org.debugroom.wedding.app.web.galley.UpdateFolderForm.UpdateFolder;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User implements Serializable{
	
	private static final long serialVersionUID = 954269278820000980L;

	public User(){
	}
	
	@NotNull(groups = {CreateFolder.class})
	@Size(min=8, max=8, groups = {CreateFolder.class, UpdateFolder.class})
	@Pattern(regexp = "[0-9]*", groups = {CreateFolder.class, UpdateFolder.class})
	private String userId;
	
}
