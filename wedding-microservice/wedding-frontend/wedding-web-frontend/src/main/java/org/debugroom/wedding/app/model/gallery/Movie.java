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
public class Movie implements Serializable{

	private static final long serialVersionUID = -2857202666703453085L;

	public Movie(){
	}

	@NotNull
	@Size(min=10, max=10)
	@Pattern(regexp="[0-9]*")
	private String movieId;
	private String filePath;
	private String thumbnailFilePath;
	private Boolean isControled;

}
