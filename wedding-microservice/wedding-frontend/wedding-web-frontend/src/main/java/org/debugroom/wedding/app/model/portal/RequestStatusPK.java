package org.debugroom.wedding.app.model.portal;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestStatusPK implements Serializable{

	private static final long serialVersionUID = 1282136079667660747L;

	public RequestStatusPK(){
	}
	
	private String requestId;
	private String userId;

}
