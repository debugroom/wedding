package org.debugroom.wedding.app.model.management.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class EmailPK implements Serializable{

	private static final long serialVersionUID = -5487835161288443901L;

	public EmailPK(){
	}
	
	private String userId;
	private Integer emailId;

}
