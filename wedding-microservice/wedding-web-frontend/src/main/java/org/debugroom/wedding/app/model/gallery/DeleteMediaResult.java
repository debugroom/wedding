package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DeleteMediaResult implements Serializable{

	private static final long serialVersionUID = 1762153452679318706L;
	public DeleteMediaResult(){
	}
	
	List<Photo> photographs;
	List<Movie> movies;
	List<String> messages;

}
