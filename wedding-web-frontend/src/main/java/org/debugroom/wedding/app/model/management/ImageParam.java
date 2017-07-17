package org.debugroom.wedding.app.model.management;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class ImageParam implements Serializable{

	private static final long serialVersionUID = -1616441755780235090L;

	public ImageParam(){
	}

	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String userId;

	@NotNull
	@Size(min=1, max=1024)
	@Pattern(regexp = "[-¥.¥/a-zA-Z0-9]*")
	private String imageFilePath;

}
