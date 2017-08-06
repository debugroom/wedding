package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Photo implements Serializable{

	private static final long serialVersionUID = 847582648724312875L;

	public Photo(){
	}

	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp="[0-9]*")
	private String photoId;
	private String filePath;
	private Boolean isControled;
	private String thumbnailFilePath;
	
}
