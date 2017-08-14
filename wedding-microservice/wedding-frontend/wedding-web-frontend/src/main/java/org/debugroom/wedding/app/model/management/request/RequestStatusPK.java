package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestStatusPK implements Serializable{

	private static final long serialVersionUID = 1701295816575825610L;

	public RequestStatusPK(){
	}

	private String requestId;
	private String userId;
	
}
