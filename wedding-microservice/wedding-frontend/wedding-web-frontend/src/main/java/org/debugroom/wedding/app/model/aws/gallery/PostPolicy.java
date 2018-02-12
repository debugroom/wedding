package org.debugroom.wedding.app.model.aws.gallery;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PostPolicy implements Serializable{

	private static final long serialVersionUID = -7565132725332414465L;

	public PostPolicy(){
	}

	private String expiration;
	private String[][] conditions;

}
