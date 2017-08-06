package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteMediaForm implements Serializable{

	private static final long serialVersionUID = -2185330928649280126L;

	public DeleteMediaForm(){
	}

	@Valid
	private List<Photo> photographs;
	@Valid
	private List<Movie> movies;

	
}
