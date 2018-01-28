package org.debugroom.wedding.app.model.gallery;

import java.io.Serializable;

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

	private String userId;

}
