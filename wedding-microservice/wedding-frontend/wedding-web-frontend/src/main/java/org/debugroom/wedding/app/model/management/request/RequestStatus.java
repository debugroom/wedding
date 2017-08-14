package org.debugroom.wedding.app.model.management.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class RequestStatus implements Serializable {

	private static final long serialVersionUID = -7574399788483204476L;

	public RequestStatus(){
	}
	
	private RequestStatusPK id;
	private Boolean isAnswered;
	private Boolean isApproved;

}
