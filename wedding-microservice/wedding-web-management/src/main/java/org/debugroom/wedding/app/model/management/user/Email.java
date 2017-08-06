package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Data
public class Email implements Serializable{

	private static final long serialVersionUID = -1708815582718264842L;

	public Email(){
	}
	
	private EmailPK id;
	private String email;

}
