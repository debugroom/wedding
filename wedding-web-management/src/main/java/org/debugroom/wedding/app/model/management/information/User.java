package org.debugroom.wedding.app.model.management.information;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@Builder
public class User implements Serializable{

	private static final long serialVersionUID = -3219928038897177284L;

    public User(){
	}

	private String userId;
	private String lastName;
	private String firstName;
	
}
