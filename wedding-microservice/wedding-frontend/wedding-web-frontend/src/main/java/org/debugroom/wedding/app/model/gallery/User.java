package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class User implements Serializable{

	private static final long serialVersionUID = -5506134623883927722L;

	public User(){
	}
	
	@NotNull
	@Size(min=8, max=8)
	@Pattern(regexp = "[0-9]*")
	private String userId;
	
}
